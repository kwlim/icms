package com.lkwy.company.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkwy.company.entity.Company;

public interface ICompanyRepository extends JpaRepository<Company, String>{
	
	public Company findOneByCompanyKey(String companyKey);

}
