package com.lkwy.purchase.view;

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
import com.lkwy.purchase.entity.PurchaseOrder;
import com.lkwy.purchase.entity.StockOrder;
import com.lkwy.purchase.service.PurchaseOrderService;
import com.lkwy.vendor.entity.Vendor;
import com.lkwy.vendor.service.VendorService;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
	
	@Autowired
	VendorService vendorService;
	
	@Autowired
	PurchaseOrderService poService;
	
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
	
	@RequestMapping(value="/addItem", method=RequestMethod.POST)
	public String addItem(ModelMap model, RedirectAttributes redirectAttributes,
			@Valid StockOrder stockOrder, BindingResult result){
		
		//if no item is selected.
		if(stockOrder.getItem() == null || StringUtils.isEmpty(stockOrder.getItem().getId())){
			
			PurchaseOrder po = poService.getPoById(stockOrder.getPurchaseOrder().getId());
			model.addAttribute("purchaseOrder", po);
			
			stockOrder.setPurchaseOrder(po);
			model.addAttribute("stockOrder", stockOrder);
			
			if(StringUtils.isNotEmpty(stockOrder.getItem().getCategory().getId()) || StringUtils.isNotEmpty(stockOrder.getItem().getBrand().getId())){
				Page<Item> resultList = itemService.getItemByCategoryIdBrandId(stockOrder.getItem().getCategory().getId(), stockOrder.getItem().getBrand().getId());
				model.addAttribute("validationReloadFailItemList", resultList.getContent());
			}
			
			result.rejectValue("item.id", "NotNull.purchaseOrder.item");
			return "po/new";
		}
		
		LOGGER.debug("{}|{}", stockOrder.getPurchaseOrder().getId(), stockOrder.getItem().getId());
		poService.saveStockOrder(stockOrder);
		
		redirectAttributes.addFlashAttribute("message", "po.item.add.success.message");
		
		return "redirect:/po/edit/"+stockOrder.getPurchaseOrder().getId();
	}
	
	protected boolean submittionHasErrors(ModelMap model, PurchaseOrder po, BindingResult result){
		boolean submittionError = false;
		
		submittionError = result.hasErrors();
		
		if(po.getVendor() == null || StringUtils.isEmpty(po.getVendor().getId())){
			submittionError = true;
			result.rejectValue("vendor.id", "NotNull.purchaseOrder.vendor");
		}
		
		return submittionError;
	}
	
	@RequestMapping("/deleteItem/{poId}/{soId}")
	public String deletePoItem(ModelMap model, RedirectAttributes redirectAttributes, 
			@PathVariable("poId") String poId, @PathVariable("soId") String soId){
		
		LOGGER.debug("deletePoItem|{}|{}", poId, soId);
		poService.deleteStockOrder(soId);
		
		redirectAttributes.addFlashAttribute("message", "po.item.delete.success.message");
		
		return "redirect:/po/edit/"+poId;
	}
	
	@RequestMapping(value="/saveItem", method=RequestMethod.POST)
	public String submitPoItems(ModelMap model, RedirectAttributes redirectAttributes, @Valid PurchaseOrder po, BindingResult result){
		
		if(result.hasErrors()){
			
			model.addAttribute("purchaseOrder", po);
			
			StockOrder so = new StockOrder();
			so.setPurchaseOrder(po);
			model.addAttribute("stockOrder", so);
			
			return "po/new";
		}
		
		if(po.getStockOrderList() != null && !po.getStockOrderList().isEmpty()){
			for(StockOrder so: po.getStockOrderList()){
				LOGGER.debug("saving po items: {}|{}|{}", new Object[]{so.getId(), so.getQuantity(), so.getUnitPrice()});
				poService.saveStockOrder(so);
			}
			
			PurchaseOrder dbPo = poService.getPoById(po.getId());
			dbPo.setPrice(po.getPrice());
			poService.savePo(dbPo);
			
			redirectAttributes.addFlashAttribute("message", "po.item.update.success.message");
		}
		
		return "redirect:/po/edit/"+po.getId();
	}
	
	@RequestMapping(value="/save/submit", method=RequestMethod.POST)
	public String submitPo(ModelMap model, RedirectAttributes redirectAttributes, @Valid PurchaseOrder po, BindingResult result){
		
		if(submittionHasErrors(model, po, result)){
			model.addAttribute("purchaseOrder", po);
			
			PurchaseOrder dbPo = poService.getPoById(po.getId());
			if(dbPo != null && dbPo.getStockOrderList() != null){
				po.setStockOrderList(dbPo.getStockOrderList());
			}
			
			StockOrder so = new StockOrder();
			so.setPurchaseOrder(po);
			model.addAttribute("stockOrder", so);
			return "po/new";
		}
		
		PurchaseOrder savedPo = null;
		if(StringUtils.isEmpty(po.getId())){
			savedPo = poService.savePo(po);
		}else{
			
			PurchaseOrder dbPo = poService.getPoById(po.getId());
			dbPo.setVendor(po.getVendor());
			dbPo.setPoDate(po.getPoDate());
			dbPo.setPoNumber(po.getPoNumber());
			dbPo.setRemark(po.getRemark());
			
			savedPo = poService.savePo(dbPo);
		}
		
		if(StringUtils.isEmpty(po.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "po.new.success.message");
			return "redirect:/po/edit/"+savedPo.getId();
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "po.edit.success.message");
		}
		
		return "redirect:/po/";
		
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deletePo(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                poService.deletePo(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "po.delete.success.message");
        }
        return "redirect:/po/";
	}
	
	@RequestMapping("/edit/{id}")
	public String editPo(ModelMap model, @PathVariable("id") String id){
		initExistingPo(model, id);
		
		return "po/new";
	}
	
	@RequestMapping("/popupview/{id}")
	public String popupViewPo(ModelMap model, @PathVariable("id") String id){
		PurchaseOrder po = poService.getPoById(id);
		model.addAttribute("purchase", po);

        model.addAttribute("stockList", po.getStockOrderList());
        
        model.addAttribute("popup", true);
        
		return "po/view";
	}
	
	@RequestMapping("/view/{id}")
	public String viewPo(ModelMap model, @PathVariable("id") String id){
		PurchaseOrder po = poService.getPoById(id);
		model.addAttribute("purchase", po);

        model.addAttribute("stockList", po.getStockOrderList());
		return "po/view";
	}
	
	public void initExistingPo(ModelMap model, String id){
		PurchaseOrder po = poService.getPoById(id);
		model.addAttribute("purchaseOrder", po);
		
		StockOrder so = new StockOrder();
		so.setPurchaseOrder(po);
		model.addAttribute("stockOrder", so);
	}
	
	@RequestMapping("/new")
	public String newPo(ModelMap model, @RequestParam(value="vendorId", required=false) String vendorId){
		
		PurchaseOrder po = new PurchaseOrder();
		po.setPoDate(new Date());
		
		if(StringUtils.isNotEmpty(vendorId)){
			Vendor vendor = vendorService.getVendorById(vendorId);
			po.setVendor(vendor);
		}
		
		model.addAttribute("purchaseOrder", po);
		
		return "po/new";
	}
	
	@RequestMapping(value={"/", ""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value="poNumber", required=false) String poNumber, 
			@RequestParam(value="poDateFrom", required=false) Date poDateFrom,
			@RequestParam(value="poDateTo", required=false) Date poDateTo,
			@RequestParam(value="vendorId", required=false) String vendorId){
		
		String id = "po";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "poNumber", "vendor.companyName", "price", "poDate", "modifiedDate"}, "poDate");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, true);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("poNumber", poNumber);
        model.addAttribute("poDateFrom", poDateFrom);
        model.addAttribute("poDateTo", poDateTo);
        model.addAttribute("vendorId", vendorId);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<PurchaseOrder> page = poService.getPoByPoNumberDateVendor(poNumber, poDateFrom, poDateTo, vendorId, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "po/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }

}
