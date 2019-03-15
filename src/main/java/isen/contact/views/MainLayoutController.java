package isen.contact.views;

import isen.contact.services.StageService;
import javafx.fxml.FXML;

public class MainLayoutController {
	

	@FXML
	public void closeApplication() {
		StageService.closeStage();
	}
}
