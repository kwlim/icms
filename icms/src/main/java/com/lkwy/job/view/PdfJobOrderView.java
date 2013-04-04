package com.lkwy.job.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfJobOrderView extends AbstractPdfView{

	static final Logger LOGGER = LoggerFactory.getLogger(PdfJobOrderView.class);
	
	protected static Font titleFont = new Font(Font.HELVETICA, 15, Font.BOLD);
	private Document document;
	private PdfPCell cell;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		LOGGER.debug("buildPdfDocument");
		
		document.setPageSize(PageSize.A4.rotate());
		document.newPage();
		
		this.document = document;
		
		document.add(new Paragraph("TEST", titleFont));
		
	}

}
