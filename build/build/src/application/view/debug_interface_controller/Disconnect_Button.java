package application.view.debug_interface_controller;

import javafx.scene.control.Button;

public class Disconnect_Button {
	private Button disconnect_button;

	// getter + setter
	public Button getDisconnect_button() {
		return disconnect_button;
	}

	public void setDisconnect_button(Button disconnect_button) {
		this.disconnect_button = disconnect_button;
	}
	
	// generate the disconnect button
	public Button generateDisconnectButton(int posX, int posY, int sizeX, int sizeY) {
		disconnect_button = new Button("Disconnect");
		
		disconnect_button.setLayoutX(posX);
		disconnect_button.setLayoutY(posY);
		disconnect_button.setPrefSize(sizeX, sizeY);
		disconnect_button.setMinSize(sizeX, sizeY);
		
		return disconnect_button;
	}
	
}
