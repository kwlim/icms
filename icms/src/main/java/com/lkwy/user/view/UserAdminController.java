package com.lkwy.user.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.group.entity.Group;
import com.lkwy.group.service.GroupService;
import com.lkwy.user.entity.User;
import com.lkwy.user.entity.UserDTO;
import com.lkwy.user.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAdminController {
    
    static final Logger LOGGER = LoggerFactory.getLogger(UserAdminController.class);
    
    @Autowired
    UserService userService;
    
    @Autowired
    GroupService groupService;
    
    @ModelAttribute("allGroupList")
    public List<Group> getAllGroup(){
        return groupService.getAllGroup();
    }
    
    @RequestMapping(value="new", method=RequestMethod.GET)
    public String newUser(ModelMap model){
        
        UserDTO form = new UserDTO();
        form.setEdit(false);
        
        model.addAttribute("userDTO", form);
        
        return "user/new";
    }
    
    @RequestMapping(value="edit/{id}", method=RequestMethod.GET)
    public String editUser(ModelMap model, @PathVariable("id") String id){
        
        User user = userService.getUserById(id);
        
        UserDTO form = new UserDTO();
        form.setEdit(true);
        form.setGroupId(user.getGroup().getId());
        
        try{
            PropertyUtils.copyProperties(form, user);
            form.setPassword("");
            form.setGroupId(user.getGroup().getId());
        }
        catch(Exception e){
            LOGGER.error("Error in copying properties to user form", e);
        }
        
        model.addAttribute("userDTO", form);
        
        return "user/new";
    }
    
    @RequestMapping(value="save/submit", method=RequestMethod.POST)
    public String submitUser(ModelMap model, HttpServletRequest request, @Valid UserDTO userForm, BindingResult result){
        
        if(invalidUserForm(model, userForm, result)){
            model.addAttribute("userDTO", userForm);
            return "user/new";
            
        }else{

            try {
                if (StringUtils.isEmpty(userForm.getId())) {
                    userService.addUser(userForm);
                } else {
                    userService.updateUser(userForm);
                }
            } catch (DataIntegrityViolationException e) {
                model.addAttribute("userDTO", userForm);
                result.rejectValue("username", "user.error.duplicate.username");
                
                return "user/new";
            } catch(Exception e){
                model.addAttribute("userDTO", userForm);
                model.addAttribute("error", "user.error.general");
                
                return "user/new";
            }

            return "redirect:/user/list";
        }
    }
    
    protected boolean invalidUserForm(ModelMap model, UserDTO userForm, BindingResult result){
        boolean invalid = false;
        
        if(result.hasErrors()){
            invalid = true;
        }
        
        if(!userForm.isEdit()){
            if(StringUtils.isEmpty(userForm.getPassword())){
                result.rejectValue("password", "NotEmpty.userDTO.password");
                invalid = true;
            }
            if(StringUtils.isEmpty(userForm.getRetypePassword())){
                result.rejectValue("retypePassword", "NotEmpty.userDTO.retypePassword");
                invalid = true;
            }
        }
        
        if (!StringUtils.isEmpty(userForm.getPassword())) {
            if (!StringUtils.equals(userForm.getPassword(), userForm.getRetypePassword())) {
                result.rejectValue("retypePassword", "NotEqual.userDTO.retypePassword");
                invalid = true;
            }
        }
        
        return invalid;
    }
    
    @RequestMapping(value="list", method=RequestMethod.GET)
    public String list(ModelMap model, HttpServletRequest request, 
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "firstname", required = false) String firstname,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "groupId", required = false) String groupId){
        
        String id = "user";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "username", "firstName", "lastName", "modifiedDate"}, "username");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("username", username);
        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("groupId", groupId);
        model.addAttribute("id", id);
        
        Pageable page = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<User> returnPage = userService.getUserByUsernameFirstnameLastnameGroup(username, firstname, lastname, groupId, page); 
        
        model.put("rows", returnPage);
        model.put("size", (int)returnPage.getTotalElements());
        
        return "user/list";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
    
}
