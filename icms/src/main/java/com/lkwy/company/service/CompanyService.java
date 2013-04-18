package com.lkwy.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lkwy.company.dao.ICompanyRepository;
import com.lkwy.company.entity.Company;

@Service
public class CompanyService {
	
	public static String FIX_COM_KEY = "icms_com";
	
	@Autowired
	private ICompanyRepository companyRepo;
	
	public Company saveCompany(Company company){
		return companyRepo.save(company);
	}
	
	/**
	 * get company by key, 
	 * create new if not exist
	 * 
	 * @param companyKey
	 * @return
	 */
	public Company getCompanyCreateIfNotExist(){
		
		Company com = companyRepo.findOneByCompanyKey(FIX_COM_KEY);
		if(com == null){
			com = companyRepo.save(new Company(FIX_COM_KEY));
		}
		
		return com;
	}

}
