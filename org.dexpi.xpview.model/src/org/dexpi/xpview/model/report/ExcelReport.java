package org.dexpi.xpview.model.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.dexpi.xpview.model.PlantItem;
import org.dexpi.xpview.model.Property;
import org.dexpi.xpview.model.Workspace;

/**
 * Very simple excel reporting.
 * 
 * @author Arndt Teinert
 *
 */
public class ExcelReport {

	private static Logger log = Logger.getLogger(ExcelReport.class);

	protected ReportPropertyData propertyData;

	public ExcelReport(ReportPropertyData propertyData) {
		this.propertyData = propertyData;
	}

	public void report(File outputFile) throws IOException,
			FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(outputFile);

		XSSFSheet dataSheet = wb.createSheet("Data");
		int numColumns = createHeaderRow(dataSheet);
		createDataRows(dataSheet);

		for (int ii = 0; ii < numColumns; ii++) {
			dataSheet.autoSizeColumn(ii);
		}
	    
		wb.write(fileOut);
		fileOut.close();
	}

	protected int createHeaderRow(XSSFSheet dataSheet) {
		Row row = dataSheet.createRow((short) 0);
		int cellIndex = 0;

		// Create a new font and alter it.
		Font font = dataSheet.getWorkbook().createFont();
		font.setFontHeightInPoints((short) 14);
		font.setFontName("Calibri");
		font.setBoldweight((short) 1);
		
		// Fonts are set into a style so create a new one to use.
		XSSFCellStyle style = dataSheet.getWorkbook().createCellStyle();
		style.setFont(font);
	    style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
	    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
		for (ReportPropertyList propertyList : propertyData) {
			for (ReportProperty property : propertyList) {
				if (property.isChecked()) {
					Cell cell = row.createCell(cellIndex);
					cell.setCellValue(property.getProperty().getName());
					cell.setCellStyle(style);
					cellIndex += 1;
				}
			}
		}
		return cellIndex;
	}

	protected void createDataRows(XSSFSheet dataSheet) {
		int rowIndex = 1;
		
		List<PlantItem> plantItems = Workspace.getInstance().getPlant()
				.getAllPlantItems();
		for (PlantItem plantItem : plantItems) {
			if (propertyData.plantItemType.isAssignableFrom(plantItem.getClass())) {
				Row row = dataSheet.createRow((short) rowIndex);
				int cellIndex = 0;
				for (ReportPropertyList propertyList : propertyData) {
					for (ReportProperty reportProperty : propertyList) {
						if (reportProperty.isChecked()) {
							Property property = plantItem.getPropertyData()
									.getProperty(
											reportProperty.getProperty()
													.getName());
							if (property != null) {
								row.createCell(cellIndex).setCellValue(
										property.getValue());
							} else {
								log.info("Property "
										+ reportProperty.getProperty()
												.getName()
										+ " not found during export.");
							}
							cellIndex += 1;
						}
					}
				}
				rowIndex += 1;
			}
		}
	}

}
