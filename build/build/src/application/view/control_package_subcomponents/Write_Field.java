package application.view.control_package_subcomponents;

import javafx.scene.control.TextField;

public class Write_Field {
	private TextField write_field;
		
	public TextField getWrite_field() {
		return write_field;
	}

	public void generateWriteField(int posX, int posY, int sizeX, int sizeY) {
		write_field = new TextField("0");
		
		write_field.setLayoutX(posX);
		write_field.setLayoutY(posY);
		write_field.setPrefSize(sizeX, sizeY);
	}
}