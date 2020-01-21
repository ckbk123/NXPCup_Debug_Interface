package application.view.debug_interface_controller;

import javafx.scene.control.Label;

public class Last_Available_URL {
	// attributes
	private Label last_available_url_field;
	private Label last_available_url_field_label;
	
	private final String[] url_filler = {"Fetching...", "Null", "No @ available"};
	
	// getter + setter
	public Label getLast_available_url_field() {
		return last_available_url_field;
	}

	public void setLast_available_url_field(Label last_available_url_field) {
		this.last_available_url_field = last_available_url_field;
	}
	
	public Label getLast_available_url_field_label() {
		return last_available_url_field_label;
	}

	public void setLast_available_url_field_label(Label last_available_url_field_label) {
		this.last_available_url_field_label = last_available_url_field_label;
	}

	// methods
	public void changeState(int index) {
		last_available_url_field.setText(url_filler[index]);
	}
	
	public Label generateLastAvailableURLField(int posX, int posY, int sizeX, int sizeY, int sizeX_label, int sizeY_label) {
		last_available_url_field = new Label(url_filler[2]);
		last_available_url_field.setLayoutX(posX + sizeX_label + 5);
		last_available_url_field.setLayoutY(posY);
		last_available_url_field.setPrefSize(sizeX, sizeY);
		last_available_url_field.setMinSize(sizeX, sizeY);
		
		last_available_url_field_label = new Label("Recent URL: ");
		last_available_url_field_label.setLayoutX(posX);
		last_available_url_field_label.setLayoutY(posY);
		last_available_url_field_label.setPrefSize(sizeX_label, sizeY_label);
		last_available_url_field_label.setMinSize(sizeX_label, sizeY_label);
		return last_available_url_field;
	}
}
