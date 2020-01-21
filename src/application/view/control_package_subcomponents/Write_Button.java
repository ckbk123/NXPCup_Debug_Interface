package application.view.control_package_subcomponents;

import javafx.scene.control.Button;

public class Write_Button {
	private Button write_button;
	
	public Button getWrite_button() {
		return write_button;
	}

	public void generateWriteButton(int posX, int posY, int sizeX, int sizeY) {
		write_button = new Button("Write");
		
		write_button.setPrefSize(sizeX, sizeY);
		write_button.setMinSize(sizeX, sizeY);
		write_button.setLayoutX(posX);
		write_button.setLayoutY(posY);
	}
}
