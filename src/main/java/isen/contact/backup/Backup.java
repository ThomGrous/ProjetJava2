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
			OutputStream outputStream = new FileOutputStream("./src/main/resources/person.vcard"); //path output
			Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			
			bufferedWriter.write("BEGIN:VCARD"+"\n");
			bufferedWriter.write("VERSION:2.1"+"\n");
			
			for(Person contact : database) {
				String id=String.valueOf(contact.getId());
				bufferedWriter.write("ID;"+id +"\n");
				bufferedWriter.write("LastName;"+contact.getLastName()+"\n");
				bufferedWriter.write("FirstName;"+contact.getFirstName()+"\n");
				bufferedWriter.write("PhoneNumber;"+contact.getPhoneNumber()+"\n");
				bufferedWriter.write("NickName;" + contact.getNickName()+"\n");
				bufferedWriter.write("Address;"+contact.getAddress()+"\n");
				bufferedWriter.write("EmailAddress;"+contact.getEmailAddress()+"\n");
				bufferedWriter.write("BirthDate;"+contact.getBirthDate()+"\n");
				bufferedWriter.write("--\n");
			}
			bufferedWriter.write("END:VCARD"+"\n");
			bufferedWriter.flush();
			
			writer.close();
		}catch(IOException e){
			System.out.print(e);
		}
	}
	
	
	public List<Person> importDAO(){
		List<Person> list = new ArrayList<>();
		try {
			InputStream inputStream = new FileInputStream("./src/main/resources/person.vcard");
			Reader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			Person contact = new Person(0, "", "", "", "", "", "", null);
			while ((line = bufferedReader.readLine()) != null) {
				
				switch(line.charAt(0)) {
					case 'I': 
							if(line.charAt(1) == 'D') {
								String[] data = line.split(";");
								contact.setId(Integer.parseInt(data[1]));
							}
							break;
					
					case 'L': 
						if(line.charAt(1)=='a') {
							String[] data = line.split(";");
							contact.setLastName(data[1]);
						}
						break;
					case 'F': 
						if(line.charAt(1)=='i') {
						String[] data = line.split(";");
						contact.setFirstName(data[1]);
						}
						break;
					case 'N': 
						if(line.charAt(1)=='i') {
						String[] data = line.split(";");
						contact.setNickName(data[1]);
					}
						break;
					case 'P': 
						if(line.charAt(1)=='h') {
						String[] data = line.split(";");
						contact.setPhoneNumber(data[1]);
					}
						break;
					case 'A': 
						if(line.charAt(1)=='d') {
						String[] data = line.split(";");
						contact.setAddress(data[1]);
					}
						break;
					case 'E': 
						if(line.charAt(1)=='m') {
						String[] data = line.split(";");
						contact.setEmailAddress(data[1]);
					}
						break;
					case 'B': 
						if(line.charAt(1)=='i') {
						String[] data = line.split(";");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
						LocalDate localDate = LocalDate.parse(data[1], formatter);
						contact.setBirthDate(localDate);
					}
						break;
					case '-':
						if(contact.getId()!=0) {
							list.add(contact);
							contact = new Person(0, "", "", "", "", "", "", null);
							}
						break;
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
