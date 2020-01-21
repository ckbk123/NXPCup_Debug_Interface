package application.view.control_package_subcomponents;

import javafx.scene.control.Label;

public class Read_Result {
	private Label read_result;
	
	public void generateReadValueField(int posX, int posY, int sizeX, int sizeY) {
		read_result = new Label("Default");
		
		read_result.setLayoutX(posX);
		read_result.setLayoutY(posY);
		read_result.setPrefSize(sizeX, sizeY);
	}

	public Label getRead_result() {
		return read_result;
	}

	public void setRead_result(Label read_result) {
		this.read_result = read_result;
	}
	
}