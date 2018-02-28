package com.osg.purchase.excel;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.osg.purchase.entity.PurchaseEntity;
import com.osg.purchase.entity.PurchaseItemEntity;

public class ExcelBuilder extends AbstractXlsxView  {


	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	       

	    PurchaseEntity entity = (PurchaseEntity) model.get("purchase");
	    File logo = (File) model.get("logo");

		String fileName = new String(("REQUISICIÓN DE COMPRA_"+ entity.getPurchaseId() +".xlsx").getBytes("MS932"), "ISO-8859-1");
	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

	    Sheet sheet = workbook.createSheet("REQUISICIÓN DE COMPRA");
	    
	    //set column width
	    int widthColumn = 1000;
	    for(int i=0; i <= 40; i++) {
 		    sheet.setColumnWidth(i, widthColumn);
	    }
	    //configuration of imprimir
	    sheet.setAutobreaks(true); 
	    sheet.setFitToPage(true); 
	    PrintSetup ps = sheet.getPrintSetup();
        ps.setFitWidth((short)1);
        ps.setFitHeight((short)0);
        ps.setLandscape(true);


	    //title style
	    CellStyle styleTitle = workbook.createCellStyle();
	    styleTitle.setAlignment(HorizontalAlignment.CENTER);
	    styleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
	    Font fontTitle = workbook.createFont();
	    fontTitle.setBold(true);
	    fontTitle.setFontHeightInPoints((short)15);
	    styleTitle.setFont(fontTitle);
	    //purchase id style
	    CellStyle stylePurchaseId = workbook.createCellStyle();
	    stylePurchaseId.setAlignment(HorizontalAlignment.CENTER);
	    stylePurchaseId.setVerticalAlignment(VerticalAlignment.CENTER);
	    Font fontPurchaseId = workbook.createFont();
	    fontPurchaseId.setBold(true);
	    fontPurchaseId.setColor(IndexedColors.RED.getIndex());
	    fontPurchaseId.setFontHeightInPoints((short)15);
	    stylePurchaseId.setFont(fontPurchaseId);

	    //purchase header style
	    CellStyle styleHeader = workbook.createCellStyle();
	    styleHeader.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
	    styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    styleHeader.setAlignment(HorizontalAlignment.CENTER);
	    styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);

	    //purchase style
	    CellStyle stylePurchase = workbook.createCellStyle();
	    stylePurchase.setAlignment(HorizontalAlignment.CENTER);
	    stylePurchase.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    //purchase item header style
	    CellStyle styleItemHeader = workbook.createCellStyle();
	    styleItemHeader.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
	    styleItemHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    styleItemHeader.setAlignment(HorizontalAlignment.CENTER);
	    styleItemHeader.setVerticalAlignment(VerticalAlignment.CENTER);
	    styleItemHeader.setWrapText(true);


	    //purchase item header style (no.)
	    CellStyle styleItemHeaderNo = workbook.createCellStyle();
	    styleItemHeaderNo.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
	    styleItemHeaderNo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    styleItemHeaderNo.setAlignment(HorizontalAlignment.CENTER);
	    styleItemHeaderNo.setVerticalAlignment(VerticalAlignment.CENTER);
	    styleItemHeaderNo.setBorderTop(BorderStyle.THIN);
	    styleItemHeaderNo.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    styleItemHeaderNo.setBorderLeft(BorderStyle.THIN);
	    styleItemHeaderNo.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    styleItemHeaderNo.setBorderBottom(BorderStyle.THIN);
	    styleItemHeaderNo.setBottomBorderColor(IndexedColors.BLACK.getIndex());

	    //purchase item style
	    CellStyle styleItem = workbook.createCellStyle();
	    styleItem.setAlignment(HorizontalAlignment.CENTER);
	    styleItem.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    //purchase item style (no.)
	    CellStyle styleItemNo = workbook.createCellStyle();
	    styleItemNo.setAlignment(HorizontalAlignment.CENTER);
	    styleItemNo.setVerticalAlignment(VerticalAlignment.CENTER);
	    styleItemNo.setBorderTop(BorderStyle.THIN);
	    styleItemNo.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    styleItemNo.setBorderLeft(BorderStyle.THIN);
	    styleItemNo.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    styleItemNo.setBorderBottom(BorderStyle.THIN);
	    styleItemNo.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    
	    //footer style
	    CellStyle styleFirm = workbook.createCellStyle();
	    styleFirm.setAlignment(HorizontalAlignment.CENTER);
	    styleFirm.setVerticalAlignment(VerticalAlignment.BOTTOM);
	    CellStyle styleTotal = workbook.createCellStyle();
	    styleTotal.setAlignment(HorizontalAlignment.CENTER);
	    styleTotal.setVerticalAlignment(VerticalAlignment.CENTER);

	    
	    //header
	    //insert osg logo
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

	    int rowNumber = 0;
	    int colTitleFrom = 2;
	    int colTitleTo = 34;
	    int colPurchaseIdFrom = 35;
	    int colPurchaseIdTo = 38;

	    Row title = sheet.createRow(rowNumber);
	    title.createCell(colTitleFrom).setCellValue("REQUISICIÓN DE COMPRA");
	    title.getCell(colTitleFrom).setCellStyle(styleTitle);

	    title.createCell(colPurchaseIdFrom).setCellValue(entity.getPurchaseId());
	    title.getCell(colPurchaseIdFrom).setCellStyle(stylePurchaseId);

	        
	    //contenido
	    int colNameFrom = 0;
	    int colNameTo = 6;
	    int colApplicatedAtFrom = 7;
	    int colApplicatedAtTo = 14;
	    int colDeliveryDateFrom = 15;
	    int colDeliveryDateTo = 23;
	    int colNalImpFrom = 24;
	    int colNalImpTo = 26;
	    int colCompanyFrom = 27;
	    int colCompanyTo = 29;
	    int colNoMaquinaFrom = 30;
	    int colNoMaquinaTo = 34;

	    rowNumber = rowNumber + 3;
	    Row purchaseHeader = sheet.createRow(rowNumber);
	    purchaseHeader.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
	    purchaseHeader.createCell(colNameFrom).setCellValue("NOMBRE DEL SOLICITANTE");
	    purchaseHeader.createCell(colApplicatedAtFrom).setCellValue("FECHA MATERIAL SOLICITADO");
	    purchaseHeader.createCell(colDeliveryDateFrom).setCellValue("POSIBLE FECHA DE ENTREGA");
	    purchaseHeader.createCell(colNalImpFrom).setCellValue("NAL. / IMP.");
	    purchaseHeader.createCell(colCompanyFrom).setCellValue("COMPAÑÍA");
	    purchaseHeader.createCell(colNoMaquinaFrom).setCellValue("No. Maquina");

	    purchaseHeader.getCell(colNameFrom).setCellStyle(styleHeader);
	    purchaseHeader.getCell(colApplicatedAtFrom).setCellStyle(styleHeader);
	    purchaseHeader.getCell(colDeliveryDateFrom).setCellStyle(styleHeader);
	    purchaseHeader.getCell(colNalImpFrom).setCellStyle(styleHeader);
	    purchaseHeader.getCell(colCompanyFrom).setCellStyle(styleHeader);
	    purchaseHeader.getCell(colNoMaquinaFrom).setCellStyle(styleHeader);


	    rowNumber = rowNumber + 1;
	    Row purchase = sheet.createRow(rowNumber);
	    purchase.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
	    purchase.createCell(colNameFrom).setCellValue(entity.getUserId() + " " + entity.getMember().getUsername());
	    purchase.createCell(colApplicatedAtFrom).setCellValue(entity.getApplicatedAt());
	    purchase.createCell(colDeliveryDateFrom).setCellValue(entity.getDeliveryDate());
	    purchase.createCell(colNalImpFrom).setCellValue(entity.getIsDomesticValue());
	    purchase.createCell(colCompanyFrom).setCellValue(entity.getCompanyValue());
	    purchase.createCell(colNoMaquinaFrom).setCellValue(entity.getMachineNo());

	    purchase.getCell(colNameFrom).setCellStyle(stylePurchase);
	    purchase.getCell(colApplicatedAtFrom).setCellStyle(stylePurchase);
	    purchase.getCell(colDeliveryDateFrom).setCellStyle(stylePurchase);
	    purchase.getCell(colNalImpFrom).setCellStyle(stylePurchase);
	    purchase.getCell(colCompanyFrom).setCellStyle(stylePurchase);
	    purchase.getCell(colNoMaquinaFrom).setCellStyle(stylePurchase);

	    int colNoFrom = 0;
	    int colQuantityFrom = 1;
	    int colQuantityTo = 3;
	    int colUnitFrom = 4;
	    int colUnitTo = 6;
	    int colProductNameFrom = 7;
	    int colProductNameTo = 10;
	    int colBrandFrom = 11;
	    int colBrandTo = 13;
	    int colNoteFrom = 14;
	    int colNoteTo = 23;

	    int colApplicationAreaFrom = 24;
	    int colApplicationAreaTo = 27;

	    int colUnitPriceFrom = 28;
	    int colUnitPriceTo = 31;
	    int colTotalPriceFrom = 32;
	    int colTotalPriceTo = 35;
	    int colCurrencyFrom = 36;
	    int colCurrencyTo= 38;

	    rowNumber = rowNumber + 2;
	    Row purchaseItemHeader = sheet.createRow(rowNumber);
	    purchaseItemHeader.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
	    purchaseItemHeader.createCell(colNoFrom).setCellValue("No");    
	    purchaseItemHeader.createCell(colQuantityFrom).setCellValue("CANTIDAD");
	    purchaseItemHeader.createCell(colUnitFrom).setCellValue("UNIDAD");
	    purchaseItemHeader.createCell(colProductNameFrom).setCellValue("PRODUCT NAME");
	    purchaseItemHeader.createCell(colBrandFrom).setCellValue("BRAND");
	    purchaseItemHeader.createCell(colNoteFrom).setCellValue("DESCRIPCIÓN");
	    purchaseItemHeader.createCell(colApplicationAreaFrom).setCellValue("AREA DE APLICACIÓN");
	    purchaseItemHeader.createCell(colUnitPriceFrom).setCellValue("UNIT PRICE");
	    purchaseItemHeader.createCell(colTotalPriceFrom).setCellValue("TOTAL PRICE");
	    purchaseItemHeader.createCell(colCurrencyFrom).setCellValue("CURRENCY");

	    purchaseItemHeader.getCell(colNoFrom).setCellStyle(styleItemHeaderNo);
	    purchaseItemHeader.getCell(colQuantityFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colUnitFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colProductNameFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colBrandFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colNoteFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colApplicationAreaFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colUnitPriceFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colTotalPriceFrom).setCellStyle(styleItemHeader);
	    purchaseItemHeader.getCell(colCurrencyFrom).setCellStyle(styleItemHeader);

	    int rowcount = rowNumber + 1;
	    int totalPrice = 0;
	    int no = 1;
	    for (PurchaseItemEntity ientity : entity.getPurchaseItemList()) {
		    Row purchaseItem = sheet.createRow(rowcount);
		    purchaseItem.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());

		    purchaseItem.createCell(colNoFrom).setCellValue(no);
		    purchaseItem.getCell(colNoFrom).setCellStyle(styleItemNo);

		    purchaseItem.createCell(colQuantityFrom).setCellValue(ientity.getQuantity());
		    purchaseItem.getCell(colQuantityFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colUnitFrom).setCellValue(ientity.getUnit());
		    purchaseItem.getCell(colUnitFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colProductNameFrom).setCellValue(ientity.getProductName());
		    purchaseItem.getCell(colProductNameFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colBrandFrom).setCellValue(ientity.getBrand());
		    purchaseItem.getCell(colBrandFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colNoteFrom).setCellValue(ientity.getNote());

		    purchaseItem.createCell(colApplicationAreaFrom).setCellValue(ientity.getApplicationArea());
		    purchaseItem.getCell(colApplicationAreaFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colUnitPriceFrom).setCellValue(ientity.getUnitPrice());
		    purchaseItem.getCell(colUnitPriceFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colTotalPriceFrom).setCellValue(ientity.getTotalPrice());
		    purchaseItem.getCell(colTotalPriceFrom).setCellStyle(styleItem);

		    purchaseItem.createCell(colCurrencyFrom).setCellValue(ientity.getCurrency());
		    purchaseItem.getCell(colCurrencyFrom).setCellStyle(styleItem);
	    	
	    	rowcount++;
	    	no++;
	    	totalPrice += ientity.getTotalPrice();
	    }
		

	    //footer
	    int colFirmApplicantFrom= 16;
	    int colFirmApplicantTo= 23;
	    int colFirmAuthorizeFrom= 25;
	    int colFirmAuthorizeTo= 32;
	    int colTotalFrom= 34;
	    int colTotalTo= 38;

	    rowNumber = rowNumber + entity.getPurchaseItemList().size() + 2;
	    Row footer = sheet.createRow(rowNumber);
	    footer.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
	    footer.createCell(colFirmApplicantFrom).setCellValue("FIRMA DEL SOLICITANTE");
	    footer.createCell(colFirmAuthorizeFrom).setCellValue("FIRMA DE AUTORIZACIÓN");
	    footer.createCell(colTotalFrom).setCellValue(totalPrice);
	    footer.getCell(colFirmApplicantFrom).setCellStyle(styleFirm);
	    footer.getCell(colFirmAuthorizeFrom).setCellStyle(styleFirm);
	    footer.getCell(colTotalFrom).setCellStyle(styleTotal);

	    //mergin cell
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, colTitleFrom, colTitleTo));
	    sheet.addMergedRegion(new CellRangeAddress(0, 1, colPurchaseIdFrom, colPurchaseIdTo));
	    
	    
	    int rowheader = 3;
	    int rowitem = 6;
	    sheet.addMergedRegion(new CellRangeAddress(rowheader, rowheader, colNameFrom, colNameTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader, rowheader, colApplicatedAtFrom, colApplicatedAtTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader, rowheader, colDeliveryDateFrom, colDeliveryDateTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader, rowheader, colNalImpFrom, colNalImpTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader, rowheader, colCompanyFrom, colCompanyTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader, rowheader, colNoMaquinaFrom, colNoMaquinaTo));

	    sheet.addMergedRegion(new CellRangeAddress(rowheader+1, rowheader+1, colNameFrom, colNameTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader+1, rowheader+1, colApplicatedAtFrom, colApplicatedAtTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader+1, rowheader+1, colDeliveryDateFrom, colDeliveryDateTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader+1, rowheader+1, colNalImpFrom, colNalImpTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader+1, rowheader+1, colCompanyFrom, colCompanyTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowheader+1, rowheader+1, colNoMaquinaFrom, colNoMaquinaTo));

	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colQuantityFrom, colQuantityTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colUnitFrom, colUnitTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colProductNameFrom, colProductNameTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colBrandFrom, colBrandTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colNoteFrom, colNoteTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colApplicationAreaFrom, colApplicationAreaTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colUnitPriceFrom, colUnitPriceTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colTotalPriceFrom, colTotalPriceTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowitem, rowitem, colCurrencyFrom, colCurrencyTo));

	    for(int i=1; i<=entity.getPurchaseItemList().size(); i++) {
	    	
	    	int r = rowitem+i;
	    	
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colQuantityFrom, colQuantityTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colUnitFrom, colUnitTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colProductNameFrom, colProductNameTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colBrandFrom, colBrandTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colNoteFrom, colNoteTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colApplicationAreaFrom, colApplicationAreaTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colUnitPriceFrom, colUnitPriceTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colTotalPriceFrom, colTotalPriceTo));
	    	sheet.addMergedRegion(new CellRangeAddress(r, r, colCurrencyFrom, colCurrencyTo));	    	
	    }

	    int rowFooter = rowitem + entity.getPurchaseItemList().size() + 2;
	    sheet.addMergedRegion(new CellRangeAddress(rowFooter, rowFooter+1, colFirmApplicantFrom, colFirmApplicantTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowFooter, rowFooter+1, colFirmAuthorizeFrom, colFirmAuthorizeTo));
	    sheet.addMergedRegion(new CellRangeAddress(rowFooter, rowFooter+1, colTotalFrom, colTotalTo));

	    
	    for(CellRangeAddress address : sheet.getMergedRegions()) {

	    	if(address.getFirstColumn() == 2 && address.getFirstRow() == 0) {
	    		continue;
	    	}
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
