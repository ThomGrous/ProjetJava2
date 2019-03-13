package isen.contact;

import isen.contact.services.StageService;
import isen.contact.services.ViewService;
import javafx.application.Application;
import javafx.stage.Stage;

public class ContactApp extends Application {
	
	public ContactApp(){
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		StageService.initPrimaryStage(primaryStage);
		StageService.showView(ViewService.getView("HomeScreen"));

	}

	public static void main(String[] args) {

		launch(args);

	}

}
