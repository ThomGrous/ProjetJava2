package isen.contact.backup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import isen.contact.entities.Person;

public class Backup {
	
	public void exportDAO(List<Person> database) {
		try{
			OutputStream outputStream = new FileOutputStream("ProjetJava2-master/src/main/resources/person.vcard");
			Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			bufferedWriter.write("BEGIN:VCARD");
			bufferedWriter.write("VERSION:2.1");
			
			for(Person contact : database) {
				String id=String.valueOf(contact.getId());
				bufferedWriter.write("ID;"+id);
				bufferedWriter.write("LastName;"+contact.getLastName());
				bufferedWriter.write("FirstName;"+contact.getFirstName());
				bufferedWriter.write("PhoneNumber;"+contact.getPhoneNumber());
				bufferedWriter.write("Address;"+contact.getAddress());
				bufferedWriter.write("EmailAddress;"+contact.getEmailAddress());
				bufferedWriter.write("BirthDate;"+contact.getBirthDate());
			}
			bufferedWriter.write("END:VCARD");
			bufferedWriter.flush();
			
			writer.close();
		}catch(IOException e){
			System.out.print(e);
		}
	}
	
	
	public List<Person> importDAO(Path backupFilePath){
		List<Person> list = new ArrayList<>();
		try {
			InputStream inputStream = new FileInputStream("ProjetJava2-master/src/main/resources/person.vcard");
			Reader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			Person contact = new Person(0, "", "", "", "", "", "", null);
			Person vide = new Person(0, "", "", "", "", "", "", null);
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				
				switch(line.charAt(0)) {
					case 'I': if(contact.getId()!=0) {
									list.add(contact);
									contact.setLastName(vide.getLastName());
									contact.setFirstName(vide.getFirstName());
									contact.setNickName(vide.getNickName());
									contact.setPhoneNumber(vide.getPhoneNumber());
									contact.setAddress(vide.getAddress());
									contact.setEmailAddress(vide.getEmailAddress());
									contact.setBirthDate(vide.getBirthDate());
									}
					
					case 'L': if(line.charAt(1)=='a') {
						String[] data = line.split(";");
						contact.setLastName(data[1]);
						}
					case 'F': if(line.charAt(1)=='i') {
						String[] data = line.split(";");
						contact.setFirstName(data[1]);
						}
					case 'N': if(line.charAt(1)=='i') {
						String[] data = line.split(";");
						contact.setNickName(data[1]);
					}
					case 'P': if(line.charAt(1)=='h') {
						String[] data = line.split(";");
						contact.setPhoneNumber(data[1]);
					}
					case 'A': if(line.charAt(1)=='d') {
						String[] data = line.split(";");
						contact.setAddress(data[1]);
					}
					case 'E': if(line.charAt(1)=='m') {
						String[] data = line.split(";");
						contact.setEmailAddress(data[1]);
					}
					case 'B': if(line.charAt(1)=='i') {
						String[] data = line.split(";");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
						LocalDate localDate = LocalDate.parse(data[1], formatter);
						contact.setBirthDate(localDate);
					}
					default: break;
				}
				
			}
			inputStream.close();
			
		} catch(IOException e) {
			System.out.print(e);
		}
		return list;
	}
}
