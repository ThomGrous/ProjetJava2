package isen.contact.views;

import isen.contact.entities.Person;
import isen.contact.services.PersonListService;
import isen.contact.util.PersonChangeListener;
import isen.contact.util.PersonValueFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HomeScreenController {

	private PersonListService personListService = new PersonListService(); 

	@FXML
	private TextField lastNameField;
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField nickNameField;
	@FXML
	private TextField adressField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextField birthDateField;
	
	
	@FXML
	private TableView<Person> personsTable;
	@FXML
	private TableColumn<Person,String> personColumn;
	
	@FXML
	private AnchorPane formPane;
	@FXML
	private Person currentPerson;
	
	@FXML
	private void refreshList() {
		personsTable.refresh();
		personsTable.getSelectionModel().clearSelection();
	}
	
	@FXML
	private void populateList() {
		personsTable.setItems(personListService.getPersons());
		refreshList();
	}
	
	
	@FXML
	private void initialize() {
		personColumn.setCellValueFactory(new PersonValueFactory());
		populateList();
		personsTable.getSelectionModel().selectedItemProperty().addListener(new PersonChangeListener() {

			@Override
			public void handleNewValue(Person person) {
				showPersonDetails(person);				
			}
			
		});
		
		resetView();
	}
	
	@FXML
	private void showPersonDetails(Person person) {
		if(person == null) formPane.setVisible(false);
		else {
			formPane.setVisible(true);
			currentPerson = person;
			
			lastNameField.setText(currentPerson.getLastName());
			firstNameField.setText(currentPerson.getFirstName());
			nickNameField.setText(currentPerson.getNickName());
			adressField.setText(currentPerson.getAddress());
			emailField.setText(currentPerson.getEmailAddress());
			phoneNumberField.setText(currentPerson.getPhoneNumber());
			birthDateField.setText(currentPerson.getBirthDate().toString()); // a voir la tete que ca a
			
		}
	}
	
	@FXML
	private void resetView() {
		showPersonDetails(null);
	}
	
	
	
	
	
	
    @FXML
    private void handleSaveButton() {
    	
    	currentPerson.setAddress(adressField.getText());
    	//currentPerson.setBirthDate(birthDateField.getText()); //probleme ici
    	currentPerson.setEmailAddress(emailField.getText());
    	currentPerson.setFirstName(firstNameField.getText());
    	currentPerson.setLastName(lastNameField.getText());
    	currentPerson.setNickName(nickNameField.getText());
    	currentPerson.setPhoneNumber(phoneNumberField.getText());
    	
    	personListService.updatePerson(currentPerson);
    	
    	resetView();
    	
    	
    	
    	
    }


    @FXML
    private void handleNewButton() {
    	
    	currentPerson.setAddress(adressField.getText());
    	//currentPerson.setBirthDate(birthDateField.getText()); //probleme ici
    	currentPerson.setEmailAddress(emailField.getText());
    	currentPerson.setFirstName(firstNameField.getText());
    	currentPerson.setLastName(lastNameField.getText());
    	currentPerson.setNickName(nickNameField.getText());
    	currentPerson.setPhoneNumber(phoneNumberField.getText());
    	
    	personListService.addPerson(currentPerson);
    	
    	resetView();
    	
   }


    @FXML
    private void handleDeleteButton() {
    	personListService.delPerson(currentPerson);
    }
	
	
}
