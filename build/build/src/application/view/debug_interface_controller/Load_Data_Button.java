package application.view.debug_interface_controller;

import javafx.scene.control.Button;

public class Load_Data_Button {
	private Button load_data_button;
	
	public Button generateLoadDataButton(int posX, int posY, int sizeX, int sizeY) {
		load_data_button = new Button("Load Data");
		
		load_data_button.setLayoutX(posX);
		load_data_button.setLayoutY(posY);
		load_data_button.setPrefSize(sizeX, sizeY);
		load_data_button.setMinSize(sizeX, sizeY);
		
		return load_data_button;
	}
} 
