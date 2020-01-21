package application.view;

import application.model.Monitoring_Class;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Monitoring_Table {
	// this function can only generate an empty table without any data, this needs to be done in the main application
	@SuppressWarnings("unchecked")
	public TableView<Monitoring_Class> generateMonitoringTable (int posX, int posY, int sizeX, int sizeY) {
		TableView<Monitoring_Class> table = new TableView<Monitoring_Class>();
		TableColumn<Monitoring_Class, String> data_type = new TableColumn<Monitoring_Class, String>("Data type");
		TableColumn<Monitoring_Class, String> variable_name = new TableColumn<Monitoring_Class, String>("Variable name");
		TableColumn<Monitoring_Class, String> value = new TableColumn<Monitoring_Class, String>("Value");
		
		data_type.setCellValueFactory(new PropertyValueFactory<Monitoring_Class, String>("monitoring_type"));
		variable_name.setCellValueFactory(new PropertyValueFactory<Monitoring_Class, String>("monitoring_name"));
		value.setCellValueFactory(new PropertyValueFactory<Monitoring_Class, String>("monitoring_variable"));
		
		data_type.setPrefWidth(sizeX/4);
		data_type.setMinWidth(sizeX/4);
		variable_name.setPrefWidth(3*sizeX/8);
		variable_name.setMinWidth(3*sizeX/8);
		value.setPrefWidth(3*sizeX/8);
		value.setMinWidth(3*sizeX/8);
		
		table.setMinSize(sizeX, sizeY);
		table.setPrefSize(sizeX, sizeY);
		table.setLayoutX(posX);
		table.setLayoutY(posY);
		table.setEditable(false);
		table.setVisible(true);
		table.getColumns().addAll(data_type, variable_name, value);
		
		return table;
	}
}
