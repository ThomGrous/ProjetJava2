package isen.contact.entities;

import java.time.LocalDate;

public class Person {
	private int id;
	private String lastName;
	private String firstName;
	private String nickName;
	private String phoneNumber;
	private String address;
	private String emailAddress;
	private LocalDate birthDate;
	

	public Person(String lastName, String firstName, String nickName, String phoneNumber, String address,
			String emailAddress, LocalDate birthDate) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.emailAddress = emailAddress;
		this.birthDate = birthDate;
	}
	
	public Person(int id, String lastName, String firstName, String nickName, String phoneNumber, String address,
			String emailAddress, LocalDate birthDate) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.emailAddress = emailAddress;
		this.birthDate = birthDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	
}
