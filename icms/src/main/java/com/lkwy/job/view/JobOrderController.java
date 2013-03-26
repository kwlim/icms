package com.lkwy.job.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.lkwy.brand.entity.Brand;
import com.lkwy.brand.service.BrandService;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.category.service.ItemCategoryService;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.item.entity.Item;
import com.lkwy.item.service.ItemService;
import com.lkwy.job.service.JobOrderService;
import com.lkwy.purchase.entity.PurchaseOrder;
import com.lkwy.purchase.entity.StockOrder;
import com.lkwy.vendor.entity.Vendor;
import com.lkwy.vendor.service.VendorService;

@Controller
@RequestMapping("/jo")
public class JobOrderController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(JobOrderController.class);
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	JobOrderService poService;
	
	@Autowired
	ItemCategoryService itemCatService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	BrandService brandService;
	
	@ModelAttribute("allBrandList")
	public List<Brand> getAllBrand(){
		return brandService.getAllBrand();
	}
	
	@ModelAttribute("allCategoryList")
	public List<ItemCategory> getAllCategory(){
		return itemCatService.getAll(new Sort(Direction.ASC, "name"));
	}
	
	@ModelAttribute("allVendorList")
	public List<Vendor> getAllVendor(){
		return vendorService.getAllVendor();
	}

	@RequestMapping("/json/getItemList")
	public @ResponseBody List<Item> jsonGetItemList(String categoryId, String brandId){
		
		if(!StringUtils.isEmpty(categoryId) || !StringUtils.isEmpty(brandId)){
			Page<Item> resultList = itemService.getItemByCategoryIdBrandId(categoryId, brandId);
			return resultList.getContent();
		}
		
		return Lists.newArrayList();
	}
	
	@RequestMapping(value={"/", ""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value="joNumber", required=false) String joNumber, 
			@RequestParam(value="joDateFrom", required=false) Date joDateFrom,
			@RequestParam(value="joDateTo", required=false) Date joDateTo){
		
		String id = "jo";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "joNumber", "modifiedDate"}, "modifiedDate");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("joNumber", joNumber);
        model.addAttribute("joDateFrom", joDateFrom);
        model.addAttribute("joDateTo", joDateTo);
        model.addAttribute("id", id);
        
//        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
//        
//        Page<PurchaseOrder> page = poService.getPoByPoNumberDateVendor(poNumber, null, null, vendorId, pageable);
//        
//        model.put("rows", page);
//        model.put("size", (int)page.getTotalElements());
//		
		return "po/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }

}
