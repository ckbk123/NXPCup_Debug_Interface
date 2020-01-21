package application.view.debug_interface_controller;

import javafx.scene.control.Label;

public class Scan_Status {
	// attributes
	private Label scan_status_text;
	
	// getter + setter
	public Label getScan_status_text() {
		return scan_status_text;
	}

	public void setScan_status_text(Label scan_status_text) {
		this.scan_status_text = scan_status_text;
	}

	// method
	public Label generateScanStatus(int posX, int posY, int sizeX, int sizeY) {
		scan_status_text = new Label("No scan");
		scan_status_text.setLayoutX(posX);
		scan_status_text.setLayoutY(posY);
		scan_status_text.setPrefSize(sizeX, sizeY);
		scan_status_text.setMinSize(sizeX, sizeY);
		
		return scan_status_text;
	}
}
