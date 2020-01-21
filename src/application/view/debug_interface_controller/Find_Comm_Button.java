package application.view.debug_interface_controller;

import javafx.scene.control.Button;

public class Find_Comm_Button {
	// attribute
	private Button find_comm_button;

	// getter + setter
	public Button getFind_comm_button() {
		return find_comm_button;
	}

	public void setFind_comm_button(Button find_comm_button) {
		this.find_comm_button = find_comm_button;
	}
	
	// method
	public Button generateFindButton(int posX, int posY, int sizeX, int sizeY) {
		find_comm_button = new Button("Scan comm");
		find_comm_button.setLayoutX(posX);
		find_comm_button.setLayoutY(posY);
		find_comm_button.setPrefSize(sizeX, sizeY);
		find_comm_button.setMinSize(sizeX, sizeY);

		return find_comm_button;
	}
}
