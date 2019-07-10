package resources;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class dataDriven {
	
	public ArrayList<String> getData(String sheetName,String testcaseName) throws IOException
	{
				//fileInputStream argument
				ArrayList<String> arrayList=new ArrayList<String>();
				
				FileInputStream fis=new FileInputStream("src/main/java/resources/TestData.xlsx");
				XSSFWorkbook workbook=new XSSFWorkbook(fis);
				
				int sheetsCount=workbook.getNumberOfSheets();
				for(int i=0;i<sheetsCount;i++)
				{
					if(workbook.getSheetName(i).equalsIgnoreCase(sheetName))
							{
					XSSFSheet sheet=workbook.getSheetAt(i);
					
					//Identifying column by scanning the row
					Iterator<Row>  rows= sheet.iterator();
					Row firstrow= rows.next();
					Iterator<Cell> cell=firstrow.cellIterator();
					int k = 0;
					int coloumn = 0;
				while(cell.hasNext())
				{
					Cell cellValue=cell.next();
					
					if(cellValue.getStringCellValue().equalsIgnoreCase("Details"))
					{
						coloumn=k;
					}
					k++;
				}
				
				//once coloumn is identified then scan entire coloum to identify desired row
				while(rows.hasNext())
				{
					
					Row desiredrow=rows.next();
					
					if(desiredrow.getCell(coloumn).getStringCellValue().equalsIgnoreCase(testcaseName))
					{
						
						//once row is found pulling all the data of that row and feed into test
						
						Iterator<Cell>  cellValue=desiredrow.cellIterator();
						while(cellValue.hasNext())
						{
						Cell desiredCell=	cellValue.next();
						if(desiredCell.getCellTypeEnum()==CellType.STRING)
						{
							
							arrayList.add(desiredCell.getStringCellValue());
						}
						else{
							
							arrayList.add(NumberToTextConverter.toText(desiredCell.getNumericCellValue()));
						
						}
						}
					}
					
				
				}
			
							
				}
				}
				//returning the array list with pulled data
				return arrayList;
				
	}

	}
