package com.mmi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;

import com.mmi.bean.EmployeeDetails;
import com.mmi.bean.Employees;

public class CommonserviceImpl
{
	

	@Audit(action="CommonserviceImpl.auditDemo")
	public void auditDemo(@AuditField(field="userName") String name ) {
		
	  System.out.println("audit................"+name);

	}
	
	public boolean propertyFileCreater() {
			try {
				 Date date = new Date();
				 String localdate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toString();
				 Properties property = new Properties();
				 String filename = "xxxxxx.properties";
				 File file = new File("E:\\"+filename);
					 FileOutputStream fos = new FileOutputStream(file);
					 property.store(fos, "Auto Genrated Property File	-->	#### "+localdate);	//writing properites into properties file from Java
					 fos.close();
				 System.out.println(property);
				 return true;
			}catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	public boolean propertyFileWriter() {
		try {
			String filename = "xxxxxx.properties";
			File file = new File("E:\\"+filename);
			OutputStream output = new FileOutputStream(file);
			Properties property = new Properties();
			
			property.setProperty("Name","MY DUMMY TASK");
			property.setProperty("Location","C:/Users/CN003073/Desktop/Demo_Child.jar");
			property.setProperty("ClassName","com.mmi.Child.Child");
			property.setProperty("MethodName","run");
			property.setProperty("Active","false");
			property.setProperty("Cron","0/10 0 0 0 0 0");
	
			property.store(output, "data addeddddddd");
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean loadPropertyFile() {
		try {
				String filename = "xxxxxx.properties";
				File file = new File("E:\\"+filename);
				InputStream inputstream = new FileInputStream(file);
				Properties property = new Properties();
				property.load(inputstream);
				
				System.out.println(property.getProperty("Name"));
	            System.out.println(property.getProperty("Location"));
	            System.out.println(property.getProperty("Cron"));
	            System.out.println(property.getProperty("Active"));
	            System.out.println(property.getProperty("ClassName"));
	            System.out.println(property.getProperty("MethodName"));
	            
				return true;
			} catch (Exception e) {
				return false;
			}
	}
	public static void jaxbObjectToXML(EmployeeDetails employee) 
    {
        try
        {
        	JAXBContext jaxbContext = null; 
        	Marshaller jaxbMarshaller = null;
        	FileOutputStream outstream = null;
        	Employees employees =null;
        	
            //Get a file Object
            File xmlFile = new File("E:\\employee.xml");
            
            if(xmlFile.createNewFile()) {
            	jaxbContext = JAXBContext.newInstance(Employees.class);
            	employeeDetailList.add(employee);
            	employees.setEmployees(employeeDetailList);
            	jaxbMarshaller = jaxbContext.createMarshaller();
            	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	            outstream = new FileOutputStream(xmlFile);
	            jaxbMarshaller.marshal(employees, outstream);
	            outstream.close();
            	
            }else {
	        	InputStream inputstream = new FileInputStream(xmlFile);
	        	
	        	 //Create JAXB Context
	            jaxbContext = JAXBContext.newInstance(Employees.class);
	        	
	            //Create Unmarshaller
	            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	        	
	        	//Unmarshalling a xml file stream
	        	employees = (Employees)jaxbUnmarshaller.unmarshal(inputstream);
	        	
	        	//fetch a list object
	        	ArrayList<EmployeeDetails> employeeDetailList = employees.getEmployees();
	        	
	        	//add new object to previous list
	        	employeeDetailList.add(employee);
	        	
	        	//set updated list to object 
	        	employees.setEmployees(employeeDetailList);
	
	            //Create Marshaller        	
	            jaxbMarshaller = jaxbContext.createMarshaller();
	            
	            // To format XML
	            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	
	/*            
	            //If we DO NOT have JAXB annotated class
	            JAXBElement<EmployeeDetails> jaxbElement = new JAXBElement<EmployeeDetails>( new QName("", "employee"), EmployeeDetails.class, employee);
	            jaxbMarshaller.marshal(jaxbElement, System.out);
	*/
	
	            outstream = new FileOutputStream(xmlFile);
	            jaxbMarshaller.marshal(employees, outstream);
	            outstream.close();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
		}  
    }
	 
	public static void jaxbXmlFileToObject() {
        try
        {
        	File xmlFile = new File("E:\\employee.xml");
        	InputStream inputstream = new FileInputStream(xmlFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(EmployeeDetails.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            EmployeeDetails employee = (EmployeeDetails)jaxbUnmarshaller.unmarshal(inputstream);
             
            System.out.println("---------->>>>>>"+employee);
            System.err.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
            System.out.println(employee.getEmpId());
            System.out.println(employee.getFirstName());
            System.out.println(employee.getLastName());
            System.out.println(employee.getDepartmentId());
            System.out.println(employee.getDepartmentName());
            System.out.println(employee.getLevel());
        }catch (JAXBException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
