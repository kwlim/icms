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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lkwy.brand.entity.Brand;
import com.lkwy.brand.service.BrandService;
import com.lkwy.category.entity.ItemCategory;
import com.lkwy.category.service.ItemCategoryService;
import com.lkwy.common.util.DisplayTagUtil;
import com.lkwy.customer.entity.Customer;
import com.lkwy.customer.service.CustomerService;
import com.lkwy.item.entity.Item;
import com.lkwy.item.service.ItemService;
import com.lkwy.job.entity.JobItem;
import com.lkwy.job.entity.JobOrder;
import com.lkwy.job.service.JobOrderService;
import com.lkwy.purchase.entity.StockOrder;
import com.lkwy.purchase.service.PurchaseOrderService;

@Controller
@RequestMapping("/job")
public class JobOrderController {
	
	static final Logger LOGGER = LoggerFactory.getLogger(JobOrderController.class);
	
	@Autowired
	JobOrderService jobService;
	
	@Autowired
	PurchaseOrderService poService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ItemService itemService;

	@Autowired
	ItemCategoryService itemCatService;

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
	
	@ModelAttribute("allCustomerList")
	public List<Customer> getAllCustomer(){
		return customerService.getAllCustomer();
	}
	
	@RequestMapping("/deleteItem/{jobOrderId}/{jobItemId}")
	public String deleteJobItem(ModelMap model, @PathVariable("jobOrderId") String jobOrderId, @PathVariable("jobItemId") String jobItemId){
		
		LOGGER.debug("deleteJobItem|{}|{}", jobOrderId, jobItemId);
		jobService.deleteJobItem(jobItemId);
		
		return "redirect:/job/edit/"+jobOrderId;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deletePo(ModelMap model, RedirectAttributes redirectAttributes, @RequestParam(value = "id", required = false) String[] ids){
		if(ids !=  null && ids.length > 0){
            for(String id: ids){
                jobService.deleteJob(id);
            }
            
            redirectAttributes.addFlashAttribute("message", "job.delete.success.message");
        }
        return "redirect:/job/";
	}
	
	@RequestMapping(value="/saveItem", method=RequestMethod.POST)
	public String submitJobItems(ModelMap model, RedirectAttributes redirectAttributes, @Valid JobOrder job, BindingResult result){
		
		if(result.hasErrors()){
			
			model.addAttribute("jobOrder", job);
			
			JobItem jobItem = new JobItem();
			jobItem.setJobOrder(job);
			model.addAttribute("jobItem", jobItem);
			
			return "job/new";
		}
		
		if(job.getJobItemList() != null && !job.getJobItemList().isEmpty()){
			for(JobItem jobItem: job.getJobItemList()){
				LOGGER.debug("saving job items: {}|{}|{}|{}", new Object[]{jobItem.getId(), jobItem.getUnit(), jobItem.getMarkup(), jobItem.getLabour()});
				jobService.saveJobItem(jobItem);
			}
			
			JobOrder dbJobOrder = jobService.getJobById(job.getId());;
			dbJobOrder.setDiscount(job.getDiscount());
			dbJobOrder.setPrice(job.getPrice());
			jobService.saveJob(dbJobOrder);
		}
		
		return "redirect:/job/edit/"+job.getId();
	}
	
	@RequestMapping(value="/save/submit", method=RequestMethod.POST)
	public String submitJobOrder(ModelMap model, RedirectAttributes redirectAttributes, @Valid JobOrder job, BindingResult result){
		
		if(submittionHasErrors(model, job, result)){
			model.addAttribute("jobOrder", job);
			
			return "job/new";
		}
		
		JobOrder savedJob = null;
		if(StringUtils.isEmpty(job.getId())){
			savedJob = jobService.saveJob(job);
		}else{
			
			JobOrder dbJobOrder = jobService.getJobById(job.getId());
			dbJobOrder.setCustomer(job.getCustomer());
			dbJobOrder.setJobDate(job.getJobDate());
			dbJobOrder.setJobNumber(job.getJobNumber());
			dbJobOrder.setRemark(job.getRemark());
			
			savedJob = jobService.saveJob(dbJobOrder);
		}
		
		if(StringUtils.isEmpty(job.getId())){
			//new
			redirectAttributes.addFlashAttribute("message", "job.new.success.message");
			return "redirect:/job/edit/"+savedJob.getId();
		}
		else{
			//update
			redirectAttributes.addFlashAttribute("message", "job.edit.success.message");
		}
		
		return "redirect:/job/";
		
	}
	
	protected boolean submittionHasErrors(ModelMap model, JobOrder job, BindingResult result){
		boolean submittionError = false;
		
		submittionError = result.hasErrors();
		
		if(job.getCustomer() == null || StringUtils.isEmpty(job.getCustomer().getId())){
			submittionError = true;
			result.rejectValue("customer.id", "NotNull.job.customer");
		}
		
		return submittionError;
	}
	
	@RequestMapping(value="/addItem", method=RequestMethod.POST)
	public String addItem(ModelMap model, @Valid JobItem jobItem, BindingResult result){
		
		//if no item is selected.
		if(jobItem.getItem() == null || StringUtils.isEmpty(jobItem.getItem().getId())){
			
			JobOrder job = jobService.getJobById(jobItem.getJobOrder().getId());
			model.addAttribute("jobOrder", job);
			
			jobItem.setJobOrder(job);
			model.addAttribute("jobItem", jobItem);
			
			if(StringUtils.isNotEmpty(jobItem.getItem().getCategory().getId()) || StringUtils.isNotEmpty(jobItem.getItem().getBrand().getId())){
				Page<Item> resultList = itemService.getItemByCategoryIdBrandId(jobItem.getItem().getCategory().getId(), jobItem.getItem().getBrand().getId());
				model.addAttribute("validationReloadFailItemList", resultList.getContent());
			}
			
			result.rejectValue("item.id", "NotNull.purchaseOrder.item");
			return "job/new";
		}
		
		LOGGER.debug("{}|{}", jobItem.getJobOrder().getId(), jobItem.getItem().getId());
		
		//update job item with latest stock order price
		StockOrder stockOrder = poService.getLatestStockItem(jobItem.getItem().getId());
		if(stockOrder != null && stockOrder.getUnitPrice() != null){
			jobItem.setStockPrice(stockOrder.getUnitPrice());
		}else{
			jobItem.setStockPrice(0f);
		}
		
		jobItem.setUnit(1);
		
		jobService.saveJobItem(jobItem);
		
		return "redirect:/job/edit/"+jobItem.getJobOrder().getId();
	}
	
	@RequestMapping("/edit/{id}")
	public String editJob(ModelMap model, @PathVariable("id") String id){
		JobOrder job = jobService.getJobById(id);
		model.addAttribute("jobOrder", job);
		
		JobItem jobItem = new JobItem();
		jobItem.setJobOrder(job);
		model.addAttribute("jobItem", jobItem);
		
		getJobItemLatestStockPrice(job.getJobItemList());
		
		return "job/new";
	}
	
	@RequestMapping("/popupview/{id}")
	public String popupViewJob(ModelMap model, @PathVariable("id") String id){
		JobOrder job = jobService.getJobById(id);
		model.addAttribute("jobOrder", job);
		
		model.addAttribute("jobItemList", job.getJobItemList());
		
		model.addAttribute("popup", true);
		
		return "job/view";
	}
	
	@RequestMapping("/view/{id}")
	public String viewJob(ModelMap model, @PathVariable("id") String id){
		JobOrder job = jobService.getJobById(id);
		model.addAttribute("jobOrder", job);
		
		model.addAttribute("jobItemList", job.getJobItemList());
		
		return "job/view";
	}
	
	public void getJobItemLatestStockPrice(List<JobItem> jobItemList){
		if(jobItemList != null && !jobItemList.isEmpty()){
			
		}
	}
	
	@RequestMapping("/new")
	public String newJobOrder(ModelMap model){
		
		JobOrder job = new JobOrder();
		model.addAttribute("jobOrder", job);
		
		return "job/new";
	}
	
	@RequestMapping(value={"/", ""})
	public String list(ModelMap model, HttpServletRequest request, 
			@RequestParam(value="joNumberCustomer", required=false) String jobNumberCustomer, 
			@RequestParam(value="joDateFrom", required=false) Date jobDateFrom,
			@RequestParam(value="joDateTo", required=false) Date jobDateTo){
		
		String id = "job";
        String sort = DisplayTagUtil.getListSort(id, request, new String[]{"", "joNumber", "modifiedDate"}, "modifiedDate");
        Boolean desc = DisplayTagUtil.getListDesc(id, request, false);
        Integer start = DisplayTagUtil.getListStart(id, request, null);
        
        model.addAttribute("jobNumberCustomer", jobNumberCustomer);
        model.addAttribute("jobDateFrom", jobDateFrom);
        model.addAttribute("jobDateTo", jobDateTo);
        model.addAttribute("id", id);
        
        Pageable pageable = new PageRequest(start, DisplayTagUtil.DEFAULT_PAGE_SIZE, (desc != null && desc)?Sort.Direction.DESC:Sort.Direction.ASC, sort);
        
        Page<JobOrder> page = jobService.getJobOrderByJobNumberCarPlateJobDateRange(jobNumberCustomer, jobDateFrom, jobDateTo, pageable);
        
        model.put("rows", page);
        model.put("size", (int)page.getTotalElements());
		
		return "job/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }

}
