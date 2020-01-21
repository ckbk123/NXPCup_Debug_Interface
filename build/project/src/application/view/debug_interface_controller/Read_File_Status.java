package application.view.debug_interface_controller;

import javafx.scene.control.Label;

public class Read_File_Status {
	private Label read_file_status;
	
	// getter + setter
	public Label getRead_file_status() {
		return read_file_status;
	}

	public void setRead_file_status(Label read_file_status) {
		this.read_file_status = read_file_status;
	}
	
	// generate the read file status field
	public Label generateReadFileStatus(int posX, int posY, int sizeX, int sizeY) {
		read_file_status = new Label("NOK");
		
		read_file_status.setPrefSize(sizeX, sizeY);
		read_file_status.setMinSize(sizeX, sizeY);
		read_file_status.setLayoutX(posX);
		read_file_status.setLayoutY(posY);
		
		return read_file_status;
	}
}
