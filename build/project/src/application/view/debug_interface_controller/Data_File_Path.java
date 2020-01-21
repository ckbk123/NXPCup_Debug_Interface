package application.view.debug_interface_controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Data_File_Path {
	// attributes
	private TextField path_field;
	private Label path_label;
	private String stored_path_string;
	
	// getter + setter
	public TextField getPath_field() {
		return path_field;
	}

	public void setPath_field(TextField path_field) {
		this.path_field = path_field;
	}

	public Label getPath_label() {
		return path_label;
	}

	public void setPath_label(Label path_label) {
		this.path_label = path_label;
	}

	public String getStored_path_string() {
		return stored_path_string;
	}

	public void setStored_path_string(String stored_path_string) {
		this.stored_path_string = stored_path_string;
	}
	
	// method
	public TextField generateDataFilePathField(int posX, int posY, int sizeX, int sizeY, int sizeX_label, int sizeY_label) {
		path_field = new TextField();
		path_label = new Label("Monitoring file path:");
		
		// positionning the 2 elements
		path_label.setLayoutX(posX);
		path_label.setLayoutY(posY);
		path_label.setPrefSize(sizeX_label, sizeY_label);
		path_label.setMinSize(sizeX_label, sizeY_label);
		
		path_field.setLayoutX(posX);
		path_field.setLayoutY(posY + sizeY_label);
		path_field.setPrefSize(sizeX, sizeY);
		path_field.setMinSize(sizeX, sizeY);
		
		return path_field;
	}
}
