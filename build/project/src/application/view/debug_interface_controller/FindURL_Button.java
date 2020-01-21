package application.view.debug_interface_controller;

import javafx.scene.control.Button;

public class FindURL_Button {
	private Button findurl_button;

	public Button getConnect_button() {
		return findurl_button;
	}

	public void setConnect_button(Button connect_button) {
		this.findurl_button = connect_button;
	}
	
	public Button generateConnectButton(int posX, int posY, int sizeX, int sizeY) {
		findurl_button = new Button("Find URL");
		findurl_button.setLayoutX(posX);
		findurl_button.setLayoutY(posY);
		findurl_button.setPrefSize(sizeX, sizeY);
		findurl_button.setMinSize(sizeX, sizeY);
		
		return findurl_button;
	}
}
