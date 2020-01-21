package application.view.debug_interface_controller;

import javafx.scene.control.ChoiceBox;

public class Available_BT_Comm_List {
	private ChoiceBox<String> available_comm_list;

	public ChoiceBox<String> getAvailable_comm_list() {
		return available_comm_list;
	}

	public void setAvailable_comm_list(ChoiceBox<String> available_comm_list) {
		this.available_comm_list = available_comm_list;
	}
	
	public ChoiceBox<String> generateAvailableBTCommList(int posX, int posY, int sizeX, int sizeY) {
		available_comm_list = new ChoiceBox<String>();
		available_comm_list.setLayoutX(posX);
		available_comm_list.setLayoutY(posY);
		available_comm_list.setPrefSize(sizeX, sizeY);
		available_comm_list.setMinSize(sizeX, sizeY);
		
		return available_comm_list;
	}
}
