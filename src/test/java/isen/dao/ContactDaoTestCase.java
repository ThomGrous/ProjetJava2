package isen.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import isen.contact.dao.ContactDao;
import isen.contact.dao.DataSourceFactory;
import isen.contact.entities.Person;

public class ContactDaoTestCase {
	private ContactDao contact = new ContactDao();
	
	@Before
	public void initDb() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("insert into person(lastname,firstname,nickname,phone_number,address,email_address,"
		 		+ "birth_date) VALUES('Duban','Justin','Juju','0606060606','chez vulcain','juju.dudu@gmail.com',"
		 		+ "'1998-10-07')");
		stmt.executeUpdate("insert into person(lastname,firstname,nickname,phone_number,address,email_address,"
		 		+ "birth_date) VALUES('Grousseau','Thomas','Pas Tom','0607080910','chez moi','toto.gg@gmail.com',"
		 		+ "'1998-07-21')");
		stmt.executeUpdate("insert into person(lastname,firstname,nickname,phone_number,address,email_address,"
		 		+ "birth_date) VALUES('Zerbin','Axel','Axelexa le petit chat','0605040302','chez lui',"
		 		+ "'axou.zerb@gmail.com', '1998-10-05')");
		stmt.close();
		connection.close();
	}
	
	@Test
	 public void shouldListContacts() {
		 //WHEN
		 List<Person> list = contact.listAllPersonsInDAO();
		 //THEN
		 assertThat(list).hasSize(3);
		 assertThat(list).extracting("lastName","firstName", "nickName", "phoneNumber","address","emailAddress",
				 "birthDate").containsOnly(tuple("Duban","Justin","Juju","0606060606","chez vulcain",
				 "juju.dudu@gmail.com", LocalDate.of(1998, 10, 07)), tuple("Grousseau","Thomas","Pas Tom",
				 "0607080910","chez moi","toto.gg@gmail.com",LocalDate.of(1998, 07, 21)), tuple("Zerbin","Axel",
				 "Axelexa le petit chat","0605040302","chez lui","axou.zerb@gmail.com", LocalDate.of(1998, 10, 05)));
	 }
	
	@Test
	 public void shouldDeletePerson() throws SQLException {
		 //WHEN
		 Person personToDelete = new Person("Zerbin","Axel",
				 "Axelexa le petit chat","0605040302","chez lui","axou.zerb@gmail.com", LocalDate.of(1998, 10, 05));
		 Connection connection = DataSourceFactory.getDataSource().getConnection();
		 Statement statement = connection.createStatement();
		 ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='Zerbin'");
		 resultSet.next();
		 personToDelete.setId(resultSet.getInt("idperson"));
		 contact.deletePerson(personToDelete);
		 //THEN
		 List<Person> list = contact.listAllPersonsInDAO();
		 assertThat(list).hasSize(2);
		 assertThat(list).extracting("lastName","firstName", "nickName", "phoneNumber","address","emailAddress",
				 "birthDate").containsOnly(tuple("Duban","Justin","Juju","0606060606","chez vulcain",
				 "juju.dudu@gmail.com", LocalDate.of(1998, 10, 07)), tuple("Grousseau","Thomas","Pas Tom",
				 "0607080910","chez moi","toto.gg@gmail.com",LocalDate.of(1998, 07, 21)));
	 }
	
	@Test
	 public void shouldUpdatePerson() throws SQLException {
		 //WHEN
		Person personToDelete = new Person("Zerbin","Axel",
				 "Axelexa","0605040302","chez lui","axou.zerb@gmail.com", LocalDate.of(1998, 10, 05));
		 Connection connection = DataSourceFactory.getDataSource().getConnection();
		 Statement statement = connection.createStatement();
		 ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='Zerbin'");
		 resultSet.next();
		 personToDelete.setId(resultSet.getInt("idperson"));
		 contact.updatePerson(personToDelete);
		 //THEN
		 List<Person> list = contact.listAllPersonsInDAO();
		 assertThat(list).hasSize(3);
		 assertThat(list).extracting("lastName","firstName", "nickName", "phoneNumber","address","emailAddress",
				 "birthDate").containsOnly(tuple("Duban","Justin","Juju","0606060606","chez vulcain",
				 "juju.dudu@gmail.com", LocalDate.of(1998, 10, 07)), tuple("Grousseau","Thomas","Pas Tom",
				 "0607080910","chez moi","toto.gg@gmail.com",LocalDate.of(1998, 07, 21)), tuple("Zerbin","Axel",
				 "Axelexa","0605040302","chez lui","axou.zerb@gmail.com", LocalDate.of(1998, 10, 05)));
	 }	
	
	@Test
	 public void shouldAddPerson() throws Exception {
		// WHEN 
		contact.addPerson(new Person("Quelqu","un",
				 "lui","0101010101","qq part","qq.n@gmail.com", LocalDate.of(1900, 12, 12)));
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE lastname='Quelqu'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("lastname")).isEqualTo("Quelqu");
		assertThat(resultSet.getString("firstname")).isEqualTo("un");
		assertThat(resultSet.getString("nickname")).isEqualTo("lui");
		assertThat(resultSet.getString("phone_number")).isEqualTo("0101010101");
		assertThat(resultSet.getString("address")).isEqualTo("qq part");
		assertThat(resultSet.getString("email_address")).isEqualTo("qq.n@gmail.com");
		assertThat(resultSet.getDate("birth_date").toLocalDate()).isEqualTo(LocalDate.of(1900,12,12));
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	 }
}
