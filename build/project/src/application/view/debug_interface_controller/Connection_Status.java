package application.view.debug_interface_controller;

import javafx.scene.control.Label;

public class Connection_Status {
	private Label connection_status;
	
	// getter + setter
	public Label getConnection_status() {
		return connection_status;
	}

	public void setRead_file_status(Label read_file_status) {
		this.connection_status = read_file_status;
	}
	
	// generate the read file status field
	public Label generateConnectionStatus(int posX, int posY, int sizeX, int sizeY) {
		connection_status = new Label("NOK");
		
		connection_status.setPrefSize(sizeX, sizeY);
		connection_status.setMinSize(sizeX, sizeY);
		connection_status.setLayoutX(posX);
		connection_status.setLayoutY(posY);
		
		return connection_status;
	}
}
