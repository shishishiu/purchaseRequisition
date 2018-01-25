package com.osg.purchase.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.osg.purchase.entity.MemberEntity;
import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseItemEntity;

public class ExcelBuilder extends AbstractXlsxView  {


	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	       
	    PurchaseEntity entity = (PurchaseEntity) model.get("purchase");
	    File file = (File) model.get("template");
	    File logo = (File) model.get("logo");
	    

//	    Workbook templateworkbook = WorkbookFactory.create(file);
	    Sheet sheet = workbook.createSheet("REQUISICIÓN DE COMPRA");
	    int widthColumn = 1000;
	    for(int i=0; i <= 35; i++) {
		    sheet.setColumnWidth(i, widthColumn);
	    }

	    
	    byte[] logobyte = Files.readAllBytes(logo.toPath());
	    int pictureIdx = workbook.addPicture(logobyte, Workbook.PICTURE_TYPE_JPEG);
	    
	    Drawing drawing = sheet.createDrawingPatriarch();
	    CreationHelper helper = workbook.getCreationHelper();
	    //add a picture shape
	    ClientAnchor anchor = helper.createClientAnchor();
	    //set top-left corner of the picture,
	    //subsequent call of Picture#resize() will operate relative to it
	    anchor.setCol1(0);
	    anchor.setRow1(0);
	    Picture pict = drawing.createPicture(anchor, pictureIdx);

	    //auto-size picture relative to its top-left corner
	    pict.resize();
	    
	    
	    
		String fileName = new String("templatePurchase.xlsx".getBytes("MS932"), "ISO-8859-1");
	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	    
	    
	    CellStyle styleHeader = workbook.createCellStyle();
	    styleHeader.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
	    styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    styleHeader.setAlignment(HorizontalAlignment.CENTER);
	    
	    CellStyle stylePurchase = workbook.createCellStyle();
	    stylePurchase.setAlignment(HorizontalAlignment.CENTER);
	    stylePurchase.setBorderBottom(BorderStyle.THIN);
	    stylePurchase.setBorderLeft(BorderStyle.THIN);
	    stylePurchase.setBorderRight(BorderStyle.THIN);

	    stylePurchase.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    stylePurchase.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    stylePurchase.setRightBorderColor(IndexedColors.BLACK.getIndex());

	    
	    
	    CellStyle styleItemHeader = workbook.createCellStyle();
	    styleItemHeader.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
	    styleItemHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    styleItemHeader.setAlignment(HorizontalAlignment.CENTER);
	    
	    
	    Row title = sheet.createRow(0);
	    title.createCell(2).setCellValue("REQUISICIÓN DE COMPRA");	    
	    
	    Row purchaseHeader = sheet.createRow(3);
	    purchaseHeader.createCell(0).setCellValue("NOMBRE DEL SOLICITANTE");
	    purchaseHeader.createCell(7).setCellValue("FECHA MATERIAL SOLICITADO");
	    purchaseHeader.createCell(15).setCellValue("POSIBLE FECHA DE ENTREGA");
	    purchaseHeader.createCell(24).setCellValue("NAL. / IMP.");
	    purchaseHeader.createCell(27).setCellValue("COMPAÑÍA");

	    purchaseHeader.getCell(0).setCellStyle(styleHeader);
	    purchaseHeader.getCell(7).setCellStyle(styleHeader);
	    purchaseHeader.getCell(15).setCellStyle(styleHeader);
	    purchaseHeader.getCell(24).setCellStyle(styleHeader);
	    purchaseHeader.getCell(27).setCellStyle(styleHeader);


	    Row purchase = sheet.createRow(4);
	    purchase.createCell(0).setCellValue(entity.getUserId() + " " + entity.getMember().getUsername());
	    purchase.createCell(7).setCellValue(entity.getApplicatedAt());
	    purchase.createCell(15).setCellValue(entity.getDeliveryDate());
	    purchase.createCell(24).setCellValue(entity.getIsDomesticValue());
	    purchase.createCell(27).setCellValue(entity.getCompanyValue());

	    purchase.getCell(0).setCellStyle(stylePurchase);
	    purchase.getCell(7).setCellStyle(stylePurchase);
	    purchase.getCell(15).setCellStyle(stylePurchase);
	    purchase.getCell(24).setCellStyle(stylePurchase);
	    purchase.getCell(27).setCellStyle(stylePurchase);

	    Row purchaseItemHeader = sheet.createRow(6);
	    purchaseItemHeader.createCell(0).setCellValue("CANTIDAD");
	    purchaseItemHeader.createCell(1).setCellValue("UNIDAD");
	    purchaseItemHeader.createCell(2).setCellValue("PRODUCT NAME");
	    purchaseItemHeader.createCell(3).setCellValue("BRAND");
	    purchaseItemHeader.createCell(4).setCellValue("DESCRIPCIÓN");
	    purchaseItemHeader.createCell(5).setCellValue("UNIT PRICE");
	    purchaseItemHeader.createCell(6).setCellValue("TOTAL PRICE");
	    purchaseItemHeader.createCell(7).setCellValue("CURRENCY");

	    purchaseItemHeader.getCell(0).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(1).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(2).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(3).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(4).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(5).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(6).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(7).setCellStyle(styleItemHeader);

	    int rowcount = 7;
	    int totalPrice = 0;
	    for (PurchaseItemEntity ientity : entity.getPurchaseItemList()) {
		    Row purchaseItem = sheet.createRow(rowcount);
		    purchaseItem.createCell(0).setCellValue(ientity.getQuantity());
		    purchaseItem.createCell(1).setCellValue(ientity.getUnit());
		    purchaseItem.createCell(2).setCellValue(ientity.getProductName());
		    purchaseItem.createCell(3).setCellValue(ientity.getBrand());
		    purchaseItem.createCell(4).setCellValue(ientity.getNote());
		    purchaseItem.createCell(5).setCellValue(ientity.getUnitPrice());
		    purchaseItem.createCell(6).setCellValue(ientity.getTotalPrice());
		    purchaseItem.createCell(7).setCellValue(ientity.getCurrency());
	    	
	    	rowcount++;
	    	totalPrice += ientity.getTotalPrice();
	    }
		
	    Row footer = sheet.createRow(rowcount+1);
	    footer.createCell(6).setCellValue(totalPrice);

	    
	    int rowheader = 3;
	    int rowitem = 6;
	    CellRangeAddress headerNombre = new CellRangeAddress(rowheader, rowheader, 0, 6);
	    CellRangeAddress headerAppliedDate = new CellRangeAddress(rowheader, rowheader, 7, 14);
	    CellRangeAddress headerDeliveryDate = new CellRangeAddress(rowheader, rowheader, 15, 23);
	    CellRangeAddress headerNalImp = new CellRangeAddress(rowheader, rowheader, 24, 26);
	    CellRangeAddress headerCompany = new CellRangeAddress(rowheader, rowheader, 27, 29);
	    CellRangeAddress headerItemNombre = new CellRangeAddress(rowheader+1, rowheader+1, 0, 6);
	    CellRangeAddress headerItemAppliedDate = new CellRangeAddress(rowheader+1, rowheader+1, 7, 14);
	    CellRangeAddress headerItemDeliveryDate = new CellRangeAddress(rowheader+1, rowheader+1, 15, 23);
	    CellRangeAddress headerItemNalImp = new CellRangeAddress(rowheader+1, rowheader+1, 24, 26);
	    CellRangeAddress headerItemCompany = new CellRangeAddress(rowheader+1, rowheader+1, 27, 29);
	    CellRangeAddress itemQuantity = new CellRangeAddress(rowitem, rowitem, 0, 2);
	    CellRangeAddress itemUnit = new CellRangeAddress(rowitem, rowitem, 3, 4);
	    CellRangeAddress itemProductName = new CellRangeAddress(rowitem, rowitem, 4, 7);
	    CellRangeAddress itemBrand = new CellRangeAddress(rowitem, rowitem, 8, 10);
	    CellRangeAddress item = new CellRangeAddress(rowitem, rowitem, 8, 10);
	    
	    
	    sheet.addMergedRegion(headerNombre);
	    sheet.addMergedRegion(headerAppliedDate);
	    sheet.addMergedRegion(headerDeliveryDate);
	    sheet.addMergedRegion(headerNalImp);
	    sheet.addMergedRegion(headerCompany);
	    sheet.addMergedRegion(headerItemNombre);
	    sheet.addMergedRegion(headerItemAppliedDate);
	    sheet.addMergedRegion(headerItemDeliveryDate);
	    sheet.addMergedRegion(headerItemNalImp);
	    sheet.addMergedRegion(headerItemCompany);

	    for(CellRangeAddress address : sheet.getMergedRegions()) {

		    RegionUtil.setBorderBottom(BorderStyle.THIN, address, sheet);
		    RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(), address, sheet);
		    RegionUtil.setBorderTop(BorderStyle.THIN, address, sheet);
		    RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(), address, sheet);
		    RegionUtil.setBorderLeft(BorderStyle.THIN, address, sheet);
		    RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(), address, sheet);
		    RegionUtil.setBorderRight(BorderStyle.THIN, address, sheet);
		    RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(), address, sheet);

	    }
	    
	}

}
