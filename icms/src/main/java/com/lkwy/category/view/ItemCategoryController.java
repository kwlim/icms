package com.lkwy.category.view;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lkwy.category.entity.ItemCategory;
import com.lkwy.category.service.ItemCategoryService;
import com.lkwy.common.util.DisplayTagUtil;

@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController {
	
	@Autowired
	ItemCategoryService itemCatService;
	
	@RequestMapping("new")
	public String newItemCategory(ModelMap model){
		
		ItemCategory category = new ItemCategory();
		model.addAttribute("itemCategory", category);
		
		return "itemcategory/new";
	}
	
	@RequestMapping("edit/{id}")
	public String editItemCategory(ModelMap model, @PathVariable("id") String id){
		
		ItemCategory category = itemCatService.getCategoryById(id);
		model.addAttribute("itemCategory", category);
		
		return "itemcategory/new";
	}
	
	@RequestMapping(value="save/submit", method=RequestMethod.POST)
	public String submitItemCategory(ModelMap model, RedirectAttributes redirectAttributes, @Valid ItemCategory itemCategory, BindingResult result){
		
		if(result.hasErrors()){
			model.addAttribute("itemCategory", itemCategory);
			return "itemcategory/new";
		}
		
		itemCatService.saveCategory(itemCategory);
		
		if(StringUtils.isEmpty(itemCategory.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "itemcategory.new.success.message");
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "itemcategory.edit.success.message");
		}
		
		return "redirect:/itemCategory/";
	}
	
	@RequestMapping("delete")
    public String deleteItemCategory(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                itemCatService.deleteCategory(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "itemcategory.delete.success.message");
        }
        return "redirect:/itemCategory/";
	}
	
	@RequestMapping(value={"/",""})
	public String list(ModelMap model, HttpServletRequest request, @RequestParam(value = "name", required = false) String name){
		
		String id = "itemCategory";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "name", "modifiedDate"}, "name");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<ItemCategory> page = itemCatService.getCategoryByName(name, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "itemcategory/list";
	}
	
}
