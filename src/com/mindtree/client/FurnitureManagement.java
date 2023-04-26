package com.mindtree.client;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.mindtree.entity.Design;
import com.mindtree.entity.FurnitureCompany;
import com.mindtree.entity.NameCompare;
import com.mindtree.entity.PatternNameCompare;
import com.mindtree.exception.ApplicationException;
import com.mindtree.exception.ServiceException;
import com.mindtree.service.DesignService;
import com.mindtree.service.FurnitureComapanyService;
import com.mindtree.service.impl.DesignServiceImpl;
import com.mindtree.service.impl.FurnitureCompanyServiceImpl;

public class FurnitureManagement {
	private static Scanner scanner=new Scanner(System.in);
	private static final FurnitureComapanyService companyService=new FurnitureCompanyServiceImpl();
	private static final DesignService designService=new DesignServiceImpl();
	
	
	private static Set<Design> assignsDesignsToACompmany(FurnitureCompany company)
	{
		Set<Design> designs=new LinkedHashSet<Design>();
		System.out.println("Enter the how many designs has to be inserted to this company");
		byte number=scanner.nextByte();
		Design[] tempDesign=new Design[number];
		for (int i = 0; i < tempDesign.length; i++) {
			System.out.println("Enter the design name");
			String name=scanner.next();
			System.out.println("Enter the design pattern");
			String pattern=scanner.next();
			System.out.println("Enter the rating");
			double rating=scanner.nextDouble();
			tempDesign[i]=new Design(name,pattern,rating,company);
			designs.add(tempDesign[i]);
		}
		
		return designs;
	}
	private static void menu(int choice) throws ApplicationException
	{
		switch(choice)
		{
		case 1:System.out.println("Enter the id of furniture comapany");
		byte id=scanner.nextByte();
			FurnitureCompany furniture=null;
			try {
				furniture = companyService.getFurnitureCompanyByID(id);
			} catch (ServiceException e1) {
				throw new ApplicationException("problem occured while getting the details of the company please check once");
			}
		Set<Design> designs=assignsDesignsToACompmany(furniture);
			try {
				designs=designService.insertDesigns(designs);
				displayDesigns(designs);
			} catch (ServiceException e) {
				throw new ApplicationException("problem occured while inserting");
			}
		
			break;
		case 2:System.out.println("Enter the company id");
		id=scanner.nextByte();
			try {
				designs=designService.getDesignsByCompanyID(id);
				displayDesigns(designs);
			} catch (ServiceException e) {
				//e.printStackTrace();
				throw new ApplicationException("Problem Occured in the id please check the company id and re-Enter");
			}
			break;
		case 3:System.out.println("Enter the Design id");
		id=scanner.nextByte();
		System.out.println("Enter the rating which you want to update");
		double rating=scanner.nextDouble();
			String string="";
			try {
				string = designService.updateDesignRatingByID(id,rating);
			} catch (ServiceException e) {
				//e.printStackTrace();
				throw new ApplicationException("Problem Occured in the id please check the design id");
			}
		System.out.println(string);
			break;
		case 4:
			
			try {
				List<Design> list = designService.getAllDesigns();
				sortTheDesigns(list);
			} catch (ServiceException e) {
				//e.printStackTrace();
				throw new ApplicationException("Problem Occured while fetching the data");
			}
			break;
		case 5:System.out.println("Enter the rating");
		rating=scanner.nextDouble();
			try {
				List<Design> list=designService.ListAllDetailsUsingRating(rating);
				listDesigns(list);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new ApplicationException("Problem Occured in the rating, Please give the correct rating");
			}
			break;
			default:System.out.println("Enter the correct options");
		}
	}
	private static void sortTheDesigns(List<Design> list) {
		// TODO Auto-generated method stub
		PatternNameCompare patternnameCompare = new PatternNameCompare();
		Collections.sort(list, patternnameCompare);
		NameCompare nameCompare = new NameCompare();
		Collections.sort(list, nameCompare);
		for(Design design:list)
		{
			System.out.println(design.getName()+" : "+design.getDesignPattern()+" : "+design.getRating());
		}
	}
	private static void listDesigns(List<Design> designs) {
		// TODO Auto-generated method stub
		try {
			writeIntoExcel( designs);
			ReadFromExcel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for(Design design:designs)
//		{
//			System.out.println(design.getName()+" : "+design.getDesignPattern()+" : "+design.getRating()+" : "+design.getFurniture().getId()+" "+design.getFurniture().getName());
//		}
	}
	
	
	private static void displayDesigns(Set<Design> designs) {
		// TODO Auto-generated method stub
		writeDatasIntoFile( designs);
		readFile();
		
		System.out.println("------------------------------------------------------------------------------");
		System.out.println();
		try {
			writeToFile(designs);// serilaization
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Set<Design> tempDesigns=getFromFile();// deserialization
		for(Design design:tempDesigns)
		{
			System.out.println(design.getName()+" : "+design.getDesignPattern()+" : "+design.getRating());
		}
		
	}
	
	private static void writeIntoExcel(List<Design> designs) throws Exception
	{
		String path="D:\\FileHandling\\excel.xls";
		Workbook book=new HSSFWorkbook();
		Sheet sheet=book.createSheet("Invoice");
		String[] coloumnHeading={"DesignName","DesignPattern","Rating","FurnitureId","FurnitureName"};
		Row headerRow=sheet.createRow(0);
		for (int i = 0; i < coloumnHeading.length; i++) {
			Cell cell=headerRow.createCell(i);
			cell.setCellValue(coloumnHeading[i]);
		}
		
		sheet.createFreezePane(0, 1);
		int rownum=1;
		for (Design tempDesigns:designs) {
			Row row=sheet.createRow(rownum++);
			row.createCell(0).setCellValue(tempDesigns.getName());
			row.createCell(1).setCellValue(tempDesigns.getDesignPattern());
			row.createCell(2).setCellValue(tempDesigns.getRating());
			row.createCell(3).setCellValue(tempDesigns.getFurniture().getId());
			row.createCell(4).setCellValue(tempDesigns.getFurniture().getName());
			FileOutputStream fileOutput=new FileOutputStream(path);
			book.write(fileOutput);
		}
		
		for (int i = 0; i < coloumnHeading.length; i++) {
			sheet.autoSizeColumn(i);
		}
		book.close();
	}
	
	private static void ReadFromExcel()
	{
		String path="D:\\FileHandling\\excel.xls";
		try {
			FileInputStream file = new FileInputStream(new File(path));
			Workbook book = new HSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = book.sheetIterator();
			while(sheets.hasNext()) {
				Sheet sh = sheets.next();
				System.out.println("Sheet name is "+sh.getSheetName());
				System.out.println("---------");
				Iterator<Row> iterator = sh.iterator();
				while(iterator.hasNext()) {
					Row row = iterator.next();
					Iterator<Cell> cellIterator = row.iterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						System.out.print(cellValue+"\t");
					}
					System.out.println();
				}
			}
			book.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void writeToFile(Set<Design> designs) throws ApplicationException
	{
		try
		{
			File file=new File("D:\\FileHandling\\file.txt");
			FileOutputStream fileOutput=new FileOutputStream(file);
			BufferedOutputStream outputStream=new BufferedOutputStream(fileOutput);
			ObjectOutputStream objectOutput=new ObjectOutputStream(outputStream);
			
			objectOutput.writeObject(designs);
			objectOutput.flush();
			objectOutput.close();
			
		}
		catch(IOException e)
		{
			throw new ApplicationException();
		}
	}
	
	private static Set<Design> getFromFile()
	{
		Set<Design> designs=null;
		try{
			FileInputStream fileInput=new FileInputStream("D:\\FileHandling\\file.txt");
			BufferedInputStream read=new BufferedInputStream(fileInput);
			ObjectInputStream objectInput=new ObjectInputStream(read);
			designs=(Set<Design>) objectInput.readObject();
			objectInput.close();
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return designs;
	}
	
	private static void readFile()
	{
		File obj=new File("D:\\FileHandling\\file.txt");
		try {
			Scanner read=new Scanner(obj);
			while(read.hasNextLine())
			{
				String data=read.nextLine();
				System.out.println(data);
			}
			read.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeDatasIntoFile(Set<Design> designs)
	{
		try {
			FileWriter writer=new FileWriter("D:\\FileHandling\\file.txt");
			BufferedWriter buffWriter=new BufferedWriter(writer);
			for (Design tempDesigns : designs) {
				String string = "";
				string  = string + tempDesigns.getName()+" : "+tempDesigns.getDesignPattern()+" : "+tempDesigns.getRating();
				
				buffWriter.write(string);
				buffWriter.newLine();
			}
			buffWriter.close();
			writer.close();
			
		} catch (IOException e) {
			System.out.println("An error occured");
			e.printStackTrace();
		}
	}
	public static void main(String[] args)  {
		byte choice=0;
		do{
			System.out.println("Enter the below options");
			System.out.println("1. Adding designs to the furniture Company");
			System.out.println("2. Get all designs by company id");
			System.out.println("3. Update a design rating by design id");
			System.out.println("4. sort the design by design pattern name");
			System.out.println("5. Get all designs along with furniture company whose rating is gretaer than given value");
			choice=scanner.nextByte();
			try {
				menu(choice);
			} catch (ApplicationException e) {
				System.out.println(e.getMessage());
			}
		}while(choice!=6);
		
	}

}