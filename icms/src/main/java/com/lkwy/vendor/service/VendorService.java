package com.lkwy.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lkwy.common.util.CommonUtil;
import com.lkwy.vendor.dao.IVendorRepository;
import com.lkwy.vendor.dao.VendorSpecifications;
import com.lkwy.vendor.entity.Vendor;

@Service
public class VendorService {
	
	@Autowired
	IVendorRepository vendorRepo;
	
	public List<Vendor> getAllVendor(){
		return vendorRepo.findAll(new Sort(Sort.Direction.ASC, "companyName"));
	}
	
	public void deleteVendor(String id){
		vendorRepo.delete(id);
	}
	
	public Vendor getVendorById(String id){
		return vendorRepo.findOne(id);
	}
	
	public Vendor saveVendor(Vendor vendor){
		return vendorRepo.save(vendor);
	}
	
	public Page<Vendor> getVendorByCompanyNameContactPersonContactNumber(String input, Pageable pageable){
		String inputWildcard = CommonUtil.addWildCard(input);
		return vendorRepo.findAll(
				VendorSpecifications.byLikeCompanyNameContactPersonContactNumber(inputWildcard), pageable);
	}

}
