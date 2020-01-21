package application.view.debug_interface_controller;

import javafx.scene.control.Button;

public class Launch_Button {
	private Button launch_button;
	
	public Button generateLaunchButton(int posX, int posY, int sizeX, int sizeY) {
		launch_button = new Button("Launch");
		
		launch_button.setLayoutX(posX);
		launch_button.setLayoutY(posY);
		launch_button.setPrefSize(sizeX, sizeY);
		launch_button.setMinSize(sizeX, sizeY);
		
		return launch_button;
	}
}
