package isen.contact.views;

import isen.contact.services.StageService;

public class MainLayoutController {

	public void closeApplication() {
		StageService.closeStage();
	}
}
