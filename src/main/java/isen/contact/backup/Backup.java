package isen.contact.backup;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import isen.contact.entities.Person;

public class Backup {
	
	public void exportDAO(List<Person> database) {
		
	}
	
	public List<Person> importDAO(Path backupFilePath){
		List<Person> list = new ArrayList<>();
		return list;
	}

}
