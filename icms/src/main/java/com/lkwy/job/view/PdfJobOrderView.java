package com.lkwy.job.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lkwy.company.entity.Company;
import com.lkwy.company.service.CompanyService;
import com.lkwy.job.entity.JobItem;
import com.lkwy.job.entity.JobOrder;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfJobOrderView extends AbstractPdfView{

	static final Logger LOGGER = LoggerFactory.getLogger(PdfJobOrderView.class);
	
	protected static Font titleFont = new Font(Font.HELVETICA, 15, Font.BOLD);
	protected static Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
	private Document document;
	private PdfPCell cell;
	
	@Autowired
	private CompanyService companyService;
	
	private Company company;
	private JobOrder job;
	
	@Override
	protected Document newDocument() {
		Rectangle pagesize = new Rectangle(617.95f, 793.7f);
		return new Document(pagesize, 10f, 10f, 10f, 10f);
//		return new Document(PageSize.A4.rotate(), 10f, 10f, 10f, 10f);
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		LOGGER.debug("buildPdfDocument");
		document.setMargins(10, 10, 10, 10);
		this.document = document;
		
		job = (JobOrder)model.get("jobOrder");
		company = companyService.getCompanyCreateIfNotExist();
		
		createHeader();
		
		createJobItems();
		
		
		
	}
	
	public void createJobItems() throws DocumentException{
		PdfPTable jobItemsTable = new PdfPTable(5);
		jobItemsTable.setWidths(new int[]{4, 1, 1, 1, 1});
		PdfPCell cell;
		
		DecimalFormat df = new DecimalFormat( "#,###,###,##0.00" );
		
		List<JobItem> itemList = job.getJobItemList();
		
		cell = new PdfPCell(new Phrase("Description"));
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		cell = new PdfPCell(new Phrase("Unit"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		cell = new PdfPCell(new Phrase("Unit/Price"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		cell = new PdfPCell(new Phrase("Labour"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		cell = new PdfPCell(new Phrase("Total"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		
		float itemTotal = 0;
		
		for(JobItem item: itemList){
			
			cell = new PdfPCell(new Phrase(item.getItem().getName()));
			cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
			jobItemsTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(String.valueOf(item.getUnit())));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
			jobItemsTable.addCell(cell);
			
			float pricePerUnit = (convertFloat(item.getStockPrice()) * item.getUnit() + convertFloat(item.getMarkup()))/item.getUnit();
			cell = new PdfPCell(new Phrase(df.format(convertFloat(pricePerUnit))));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
			jobItemsTable.addCell(cell);
			
			cell = new PdfPCell(new Phrase(df.format(convertFloat(item.getLabour()))));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
			jobItemsTable.addCell(cell);
			
			float total = convertFloat(item.getStockPrice()) * item.getUnit() + convertFloat(item.getMarkup()) + convertFloat(item.getLabour());
			cell = new PdfPCell(new Phrase(df.format(convertFloat(total))));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
			jobItemsTable.addCell(cell);
			
			itemTotal+= total;
		}
		
		cell = new PdfPCell(new Phrase("Total"));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		
		cell.setColspan(4);
		jobItemsTable.addCell(cell);
		cell = new PdfPCell(new Phrase(df.format(itemTotal)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Discount(RM)"));
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setColspan(4);
		jobItemsTable.addCell(cell);
		if(job.getDiscount() == null){
			cell = new PdfPCell(new Phrase(""));
		}else{
			cell = new PdfPCell(new Phrase(df.format(job.getDiscount())));
		}
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Grand Total"));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		cell.setColspan(4);
		jobItemsTable.addCell(cell);
		cell = new PdfPCell(new Phrase(df.format(job.getPrice())));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.TOP|Rectangle.BOTTOM);
		jobItemsTable.addCell(cell);
		
		document.add(jobItemsTable);
	}
	
	protected float convertFloat(Float object){
		if(object == null){
			return 0;
		}else{
			return object;
		}
	}
	
	public void createHeader() throws DocumentException{
		PdfPTable headerTable = new PdfPTable(2);
		headerTable.setWidths(new int[]{2, 1});
		PdfPCell cell;
		
		cell = new PdfPCell(getAddress());
		cell.setPaddingBottom(10f);
		cell.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(cell);
		
		cell = new PdfPCell(getJobDetails());
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setPaddingBottom(10f);
		headerTable.addCell(cell);
		
		document.add(headerTable);
		
	}
	
	protected Paragraph getJobDetails(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Paragraph jobDetails = new Paragraph();
		
		StringBuilder jobNumber = new StringBuilder("Job Number: ");
		jobNumber.append(job.getJobNumber());
		jobDetails.add(jobNumber.toString());
		jobDetails.add(Chunk.NEWLINE);
		
		StringBuilder date = new StringBuilder("Date: ");
		date.append(sdf.format(new Date()));
		jobDetails.add(date.toString());
		jobDetails.add(Chunk.NEWLINE);
		
		StringBuilder customer = new StringBuilder("Vehicle Number: ");
		customer.append(job.getCustomer().getCarPlateNumber());
		jobDetails.add(customer.toString());
		jobDetails.add(Chunk.NEWLINE);
		
		StringBuilder mileage = new StringBuilder("Mileage: ");
		if(job.getMillage() != null){
			mileage.append(job.getMillage());
		}
		jobDetails.add(mileage.toString());
		jobDetails.add(Chunk.NEWLINE);
		
		return jobDetails;
	}
	
	protected Paragraph getAddress(){
		Paragraph address = new Paragraph();
		StringBuilder companyAndRegNumber = new StringBuilder(company.getName());
		companyAndRegNumber.append("(").append(company.getRegistrationNumber()).append(")");
		address.add(new Phrase(companyAndRegNumber.toString(), normalFont));
		address.add(Chunk.NEWLINE);
		
		address.add(new Phrase(company.getAddress(), normalFont));
		address.add(Chunk.NEWLINE);
		
		StringBuilder phone = new StringBuilder("Phone Number (");
		phone.append(company.getPhoneNumber()).append(")");
		address.add(new Phrase(phone.toString(), normalFont));
		address.add(Chunk.NEWLINE);
		
		StringBuilder fax = new StringBuilder("Fax Number (");
		fax.append(company.getFaxNumber()).append(")");
		address.add(new Phrase(fax.toString(), normalFont));
		
		return address;
	}

}
