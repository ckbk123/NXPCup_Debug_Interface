package application.view.control_package_subcomponents;

import application.model.Debug_Data;
import javafx.scene.control.ChoiceBox;

public class Control_List {
	private ChoiceBox<String> control_choicebox;
	
	public ChoiceBox<String> getControl_choicebox() {
		return control_choicebox;
	}

	public void generateControlBox(int posX, int posY, int sizeX, int sizeY) {
		control_choicebox = new ChoiceBox<String>();
		for (int i = 0; i < Debug_Data.getDof(); ++i) {
			control_choicebox.getItems().add(Debug_Data.getControl_names()[i]);
		}
		control_choicebox.setPrefSize(sizeX, sizeY);
		control_choicebox.setLayoutX(posX);
		control_choicebox.setLayoutY(posY);
	}
}
