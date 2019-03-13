package isen.contact.services;

import java.util.List;

import isen.contact.dao.ContactDao;
import isen.contact.entities.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonListService {
	
	private ObservableList<Person> persons;
	private ContactDao dao = new ContactDao();

	
	public PersonListService() {
		persons = FXCollections.observableArrayList();
	}
	
	
	
	public void setPersons(List<Person> personsList) {
		persons = FXCollections.observableArrayList(personsList);
	}
	
	
	public ObservableList<Person> getPersons() {
		return FXCollections.observableArrayList(dao.listAllPersonsInDAO());
	}

	public void addPerson(Person person) {
		dao.addPerson(person);
	}

	public void delPerson(Person person) {
		dao.deletePerson(person);
	}
	
	public void updatePerson(Person person) {
		dao.updatePerson(person);
	}

}
