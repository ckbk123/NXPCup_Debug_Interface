package application.view.control_package_subcomponents;

import javafx.scene.control.Button;

public class Read_Button {	
	private Button read_button;
	
	public Button getRead_button() {
		return read_button;
	}

	public void generateReadButton (int posX, int posY, int sizeX, int sizeY) {
		read_button = new Button("Read");
		
		read_button.setPrefSize(sizeX, sizeY);
		read_button.setMinSize(sizeX, sizeY);
		read_button.setLayoutX(posX);
		read_button.setLayoutY(posY);		
	}
}
