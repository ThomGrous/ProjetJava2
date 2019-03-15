package isen.contact.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import isen.contact.backup.Backup;
import isen.contact.entities.Person;

public class ContactDao {
	
	public List<Person> listAllPersonsInDAO(){
		List<Person> list = new ArrayList<>();
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			 try (Statement statement = connection.createStatement()) {
				 try (ResultSet results = statement.executeQuery("SELECT * from person")) {
					 while (results.next()) {
						 Person person = new Person(
								 results.getInt("idperson"),
								 results.getString("lastname"),
								 results.getString("firstname"),
								 results.getString("nickname"),
								 results.getString("phone_number"),
								 results.getString("address"),
								 results.getString("email_address"),
								 results.getDate("birth_date").toLocalDate());
						 list.add(person);
					 }
				 }
			 }
		 } catch (SQLException e) {
		 // Manage Exception
		 e.printStackTrace();
		 }
		return list;
	}
	
	public Person addPerson(Person personToAdd) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			 String sqlQuery = "insert into person(lastname,firstname,nickname,phone_number,address,email_address,"
			 		+ "birth_date) VALUES(?,?,?,?,?,?,?)";
			 try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				 statement.setString(1, personToAdd.getLastName());
				 statement.setString(2, personToAdd.getFirstName());
				 statement.setString(3, personToAdd.getNickName());
				 statement.setString(4, personToAdd.getPhoneNumber());
				 statement.setString(5, personToAdd.getAddress());
				 statement.setString(6, personToAdd.getEmailAddress());
				 statement.setDate(7, Date.valueOf(personToAdd.getBirthDate()));
				 statement.executeUpdate();
				 ResultSet ids = statement.getGeneratedKeys(); 
				 if (ids.next()) { 
					 personToAdd.setId(ids.getInt(1)); 
					 return personToAdd; 
				 } 
				 statement.close();

			 }
		}catch (SQLException e) {
		// Manage Exception
		e.printStackTrace();
		}
		return null;
	}
	
	public void deletePerson(Person personToDelete) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			 try (PreparedStatement statement = connection.prepareStatement(
					 "delete from person where idperson=?")) {
				 statement.setInt(1, personToDelete.getId());
				 statement.executeUpdate();
			 }
		 }catch (SQLException e) {
			 // Manage Exception
			 e.printStackTrace();
		 }

	}
	
	public Person updatePerson(Person personToUpdate) {
		this.deletePerson(personToUpdate);
		return this.addPerson(personToUpdate);
	}
	
	/**
	 * path coder en dur dans backup.importDao
	 * 
	 * (Methode de pharmacien-plombier)
	 * @return
	 */
	public List<Person> updateDatabase(){
		Backup backup = new Backup();
		List<Person> newDb = backup.importDAO();
		List<Person> oldDb = this.listAllPersonsInDAO();
		for(Person person : oldDb) {
			this.deletePerson(person);
		}
		for(Person person : newDb) {
			this.addPerson(person);
		}
		return newDb;
	}
	
	
}
