package utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	//	static Workbook book;
	//	static Sheet sheet;
	//	
	//	public static String TESTDATA_SHEET_PATH="C:\\Users\\Akshunya Jugran\\eclipse-workspace\\KalagatoAPI\\src\\main\\java\\kalagato\\TestData\\TestData.xlsx";
	//	
	//	public static Object[][] getTestData(String login) {
	//		FileInputStream file=null;
	//		try {
	//			file = new FileInputStream(TESTDATA_SHEET_PATH);
	//			} catch (FileNotFoundException e) {
	//				e.printStackTrace();
	//			} 
	//		try {
	//			book = WorkbookFactory.create(file);
	//		} catch(InvalidFormatException e) {
	//			e.printStackTrace();
	//		}catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//		
	//		sheet=book.getSheet(login);
	//		Object[][] data= new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
	//		for (int i=0;i<sheet.getLastRowNum();i++) {
	//			for (int k=0;k<sheet.getRow(0).getLastCellNum();k++) {
	//				data[i][k]=sheet.getRow(i+1).getCell(k).toString();
	//				
	//			}
	//		}
	//		return data;
	//	}

	public XSSFSheet ReadXSSFsheet(String excelPath, String... Sheet) throws IOException{
		//System.getProperty("user.dir") + 
		String filepath = excelPath;
		XSSFSheet sheet = null;
		FileInputStream fis = new FileInputStream(filepath);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		if(Sheet.length==0){
			sheet = workbook.getSheetAt(0);
		}
		else{
			sheet = workbook.getSheet(Sheet[0]);
		}
		return sheet;
	}

	//for flag

	public int findCol(XSSFSheet sheet, String cellContent) {

		int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
		int Flagcolno = 0;
		for(int i=0;i<noOfColumns;i++){
			Cell cell = sheet.getRow(0).getCell(i);
			if (cell != null) {
				// Found column and there is value in the cell.
				if(cell.getStringCellValue().contains(cellContent)){
					return Flagcolno;
				}
				else
					Flagcolno++;
			}
		}
		return 0;
	}

	//for string typecast

	public String getCellValueAsString(Cell cell) {
		String strCellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case STRING:
				strCellValue = cell.toString();
				break;
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					strCellValue = dateFormat.format(cell.getDateCellValue());
				} else {
					Double value = cell.getNumericCellValue();
					Long longValue = value.longValue();
					strCellValue = new String(longValue.toString());
				}
				break;
			case BOOLEAN:
				strCellValue = new String(new Boolean(
						cell.getBooleanCellValue()).toString());
				break;
			case BLANK:
				strCellValue = "";
				break;
			default:
				break;		
			}
		}
		return strCellValue;
	}
	public int findRow(XSSFSheet sheet, String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				getCellValueAsString(cell);
				if (getCellValueAsString(cell).trim().equals(cellContent)) {
					return row.getRowNum();  
				}
			}
		}               
		return 0;
	}

}


