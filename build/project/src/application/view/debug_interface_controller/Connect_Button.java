package application.view.debug_interface_controller;

import javafx.scene.control.Button;

public class Connect_Button {
	private Button connect_button;

	public Button getConnect_button() {
		return connect_button;
	}

	public void setConnect_button(Button connect_button) {
		this.connect_button = connect_button;
	}
	
	public Button generateConnectButton(int posX, int posY, int sizeX, int sizeY) {
		connect_button = new Button("Connect");
		connect_button.setLayoutX(posX);
		connect_button.setLayoutY(posY);
		connect_button.setPrefSize(sizeX, sizeY);
		connect_button.setMinSize(sizeX, sizeY);
		
		return connect_button;
	}
}
