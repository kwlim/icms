package com.lkwy.item.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.lkwy.brand.entity.Brand;
import com.lkwy.brand.service.BrandService;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.category.service.ItemCategoryService;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.item.entity.Item;
import com.lkwy.item.service.ItemService;
import com.lkwy.stock.entity.StockCheck;
import com.lkwy.stock.service.StockCheckService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	ItemCategoryService itemCatService;
	
	@Autowired
	BrandService brandService;
	
	@Autowired
	StockCheckService stockCheckService;
	
	@ModelAttribute("scTypeBringForward")
	public int getStockCheckTypeBringForward(){
		return StockCheck.STOCK_CHECK_TYPE.BRING_FORWARD.getValue();
	}
	
	@ModelAttribute("scTypePurchase")
	public int getStockCheckTypePurchase(){
		return StockCheck.STOCK_CHECK_TYPE.PURCHASE.getValue();
	}
	
	@ModelAttribute("scTypeSold")
	public int getStockCheckTypeSold(){
		return StockCheck.STOCK_CHECK_TYPE.JOB.getValue();
	}
	
	@ModelAttribute("allBrandList")
	public List<Brand> getAllBrand(){
		return brandService.getAllBrand();
	}
	
	@ModelAttribute("allCategoryList")
	public List<ItemCategory> getAllCategory(){
		return itemCatService.getAll(new Sort(Direction.ASC, "name"));
	}
	
	@ModelAttribute("monthList")
	public List<Integer> getMonthList(){
		List<Integer> monthList = Lists.newArrayList();
		for(int i=1; i<=12; i++){
			monthList.add(i);
		}
		return monthList;
	}
	
	@RequestMapping("new")
	public String newItemCategory(ModelMap model){
		
		Item item = new Item();
		model.addAttribute("item", item);
		
		return "item/new";
	}
	
	@RequestMapping("view/{id}")
	public String viewItem(ModelMap model, 
			@PathVariable("id") String id,
			@RequestParam(value="monthFrom", required=false) Integer monthFrom,
			@RequestParam(value="yearFrom", required=false) Integer yearFrom,
			@RequestParam(value="monthTo", required=false) Integer monthTo,
			@RequestParam(value="yearTo", required=false) Integer yearTo){
		
		Item item = itemService.getItemById(id);
		model.addAttribute("item", item);
		DateTime now = new DateTime();
		
		if(monthFrom == null && yearFrom == null){
			monthFrom = now.getMonthOfYear();
			yearFrom = now.getYear();
		}
		if(monthTo == null && yearTo == null){
			monthTo = now.getMonthOfYear();
			yearTo = now.getYear();
		}
		
		List<StockCheck> stockCheckList = stockCheckService.getStockCheckListForItemOnDateRange(id, monthFrom, yearFrom, monthTo, yearTo);
		model.addAttribute("stockCheckList", stockCheckList);
		
		model.addAttribute("monthFrom", monthFrom);
		model.addAttribute("yearFrom", yearFrom);
		model.addAttribute("monthTo", monthTo);
		model.addAttribute("yearTo", yearTo);
		
		return "item/view";
	}
	
	@RequestMapping("edit/{id}")
	public String editItem(ModelMap model, @PathVariable("id") String id){
		
		Item item = itemService.getItemById(id);
		model.addAttribute("item", item);
		
		return "item/new";
	}
	
	@RequestMapping("recalculatebf/{itemId}/{bfId}")
	public String recalculateItemBringForward(ModelMap model, 
			RedirectAttributes redirectAttributes,
			@PathVariable("itemId") String itemId,
			@PathVariable("bfId") String bfId,
			@RequestParam(value="monthFrom", required=false) Integer monthFrom,
			@RequestParam(value="yearFrom", required=false) Integer yearFrom,
			@RequestParam(value="monthTo", required=false) Integer monthTo,
			@RequestParam(value="yearTo", required=false) Integer yearTo){
		
		stockCheckService.regenerateStockBringForward(bfId);
		
		redirectAttributes.addFlashAttribute("message", "item.recalculatebf.success.message");
		
		StringBuilder redirect = new StringBuilder("redirect:/item/view/");
		redirect.append(itemId);
		if(monthFrom != null)
			redirect.append("?monthFrom").append(monthFrom);
		if(yearFrom != null)
			redirect.append("?yearFrom").append(yearFrom);
		if(monthTo != null)
			redirect.append("?monthTo").append(monthTo);
		if(yearTo != null)
			redirect.append("?yearTo").append(yearTo);
		
		return redirect.toString();
	}
	
	@RequestMapping(value="save/submit", method=RequestMethod.POST)
	public String submitItem(ModelMap model, RedirectAttributes redirectAttributes, @Valid Item item, BindingResult result){
		
		if(result.hasErrors()){
			model.addAttribute("item", item);
			return "item/new";
		}
		
		try{
			//set category = null when empty select box is selected to prevent jpa exception saying cannot find category(category with emtpy id)
			//set category = null when user select new category
			if(item.getCategory() != null){
				if(StringUtils.isEmpty(item.getCategory().getId())){
					item.setCategory(null);
				}else if(StringUtils.equals(item.getCategory().getId(), "new")){
					
					ItemCategory newCat = itemCatService.saveCategory(new ItemCategory(item.getNewCategoryName()));
					item.setCategory(newCat);
				}
			}
			if(item.getBrand() != null){
				if(StringUtils.isEmpty(item.getBrand().getId())){
					item.setBrand(null);
				}else if(StringUtils.equals(item.getBrand().getId(), "new")){
					
					Brand brand = brandService.addNewBrand(new Brand(item.getNewBrandName()));
					item.setBrand(brand);
				}
			}
			
			itemService.saveItem(item);
			
			if(StringUtils.isEmpty(item.getId())){
				//new
				redirectAttributes.addFlashAttribute("message", "item.new.success.message");
			}
			else{
				//update
				redirectAttributes.addFlashAttribute("message", "item.edit.success.message");
			}
			
		}
		catch (DataIntegrityViolationException e) {
			model.addAttribute("item", item);
            result.rejectValue("code", "Duplicate.item.code");
            LOGGER.error("error in saving item", e);
            return "item/new";
        } catch(Exception e){
        	model.addAttribute("item", item);
            model.addAttribute("error", "item.error.general");
            LOGGER.error("error in saving item", e);
            return "item/new";
        }
		
		return "redirect:/item/";
	}
	
	@RequestMapping("delete")
    public String deleteItem(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
			boolean allDel = true;
            for(String id: ids){
            	try{
            		itemService.deleteItem(id);
            	}
            	catch(Exception e){
            		LOGGER.error("Error in deleting item", e);
            		allDel = false;
            	}
            }
            
            if(allDel){
            	redirectAttributes.addFlashAttribute("message", "item.delete.success.message");
            }else{
            	redirectAttributes.addFlashAttribute("message", "item.partial.delete.success.message");
            }
        }
        return "redirect:/item/";
	}
	
	@RequestMapping(value={"/",""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value = "name", required = false) String name, 
			@RequestParam(value = "categoryId", required = false) String categoryId,
			@RequestParam(value = "brandId", required = false) String brandId){
		
		String id = "item";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "code", "name", "category.name", "brand.name", "modifiedDate"}, "code");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<Item> page = itemService.getItemByNameCodeCategoryId(name, categoryId, brandId, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "item/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
//		binder.registerCustomEditor(ItemCategory.class,  new ItemCategoryPropertyEditor());
        
    }

}
