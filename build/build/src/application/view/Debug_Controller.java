package application.view;

import application.view.debug_interface_controller.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import application.Interface;
import application.controller.Bluetooth_Module;
import application.controller.C_Debug_Data_Fetcher;
import application.model.Bluetooth_Ressource;
import application.model.Thread_Common;

public class Debug_Controller {
	// attributes
	private Button find_comm_button;
	private Label scan_status;
	private ChoiceBox<String> available_bt_comm_list;
	private Button findurl_button;
	private Button connect_button;
	private Button disconnect_button;
	private Label connection_status;
	private Label last_available_url_label;
	private Label last_available_url;
	private TextField data_file_path;
	private Label data_file_path_label;
	private Button load_data_button;
	private Label read_file_status;
	private Button launch_button;
	
	// getter + setter
	public Button getFind_comm_button() {
		return find_comm_button;
	}
	public void setFind_comm_button(Button find_comm_button) {
		this.find_comm_button = find_comm_button;
	}
	public Label getScan_status() {
		return scan_status;
	}
	public void setScan_status(Label scan_status) {
		this.scan_status = scan_status;
	}
	public ChoiceBox<String> getAvailable_bt_comm_list() {
		return available_bt_comm_list;
	}
	public void setAvailable_bt_comm_list(ChoiceBox<String> available_bt_comm_list) {
		this.available_bt_comm_list = available_bt_comm_list;
	}
	public Button getFindurl_button() {
		return findurl_button;
	}
	public void setFindurl_button(Button findurl_button) {
		this.findurl_button = findurl_button;
	}
	public Button getConnect_button() {
		return connect_button;
	}
	public void setConnect_button(Button connect_button) {
		this.connect_button = connect_button;
	}
	public Button getDisconnect_button() {
		return disconnect_button;
	}
	public void setDisconnect_button(Button disconnect_button) {
		this.disconnect_button = disconnect_button;
	}
	public Label getConnection_status() {
		return connection_status;
	}
	public void setConnection_status(Label connection_status) {
		this.connection_status = connection_status;
	}
	public Label getLast_available_url_label() {
		return last_available_url_label;
	}
	public void setLast_available_url_label(Label last_available_url_label) {
		this.last_available_url_label = last_available_url_label;
	}
	public Label getLast_available_url() {
		return last_available_url;
	}
	public void setLast_available_url(Label last_available_url) {
		this.last_available_url = last_available_url;
	}
	public TextField getData_file_path() {
		return data_file_path;
	}
	public void setData_file_path(TextField data_file_path) {
		this.data_file_path = data_file_path;
	}
	public Label getData_file_path_label() {
		return data_file_path_label;
	}
	public void setData_file_path_label(Label data_file_path_label) {
		this.data_file_path_label = data_file_path_label;
	}
	public Button getLoad_data_button() {
		return load_data_button;
	}
	public void setLoad_data_button(Button load_data_button) {
		this.load_data_button = load_data_button;
	}
	public Label getRead_file_status() {
		return read_file_status;
	}
	public void setRead_file_status(Label read_file_status) {
		this.read_file_status = read_file_status;
	}
	public Button getLaunch_button() {
		return launch_button;
	}
	public void setLaunch_button(Button launch_button) {
		this.launch_button = launch_button;
	}
	// method
	public void generateDebugController(int refX, int refY) {
		// generate all elements of the debug console
		Find_Comm_Button find_comm_button_object = new Find_Comm_Button();
		find_comm_button = find_comm_button_object.generateFindButton(refX, refY, 80, 25);
		
		Scan_Status scan_status_object = new Scan_Status();
		scan_status = scan_status_object.generateScanStatus(refX + 90, refY, 60, 25);
		
		Available_BT_Comm_List available_bt_comm_list_object = new Available_BT_Comm_List();
		available_bt_comm_list = available_bt_comm_list_object.generateAvailableBTCommList(refX + 170, refY, 100, 25);
		available_bt_comm_list.setDisable(true);
		
		FindURL_Button findurl_button_object = new FindURL_Button();
		findurl_button = findurl_button_object.generateConnectButton(refX + 380, refY, 90, 25);
		findurl_button.setDisable(true);
		
		Connect_Button connect_button_object = new Connect_Button();
		connect_button = connect_button_object.generateConnectButton(refX + 220, refY + 55, 80, 25);
		connect_button.setDisable(true);
		
		Disconnect_Button disconnect_button_object = new Disconnect_Button();
		disconnect_button = disconnect_button_object.generateDisconnectButton(refX + 310, refY + 55, 80, 25);
		disconnect_button.setDisable(true);
		
		Connection_Status connection_status_object = new Connection_Status();
		connection_status = connection_status_object.generateConnectionStatus(refX + 400, refY + 55, 70, 25);
		
		Last_Available_URL last_available_url_object = new Last_Available_URL();
		last_available_url = last_available_url_object.generateLastAvailableURLField(refX, refY + 30, 450, 25, 80, 25);
		last_available_url_label = last_available_url_object.getLast_available_url_field_label();
		
		// open the ressource file to see where is the last available URL
		BufferedReader url_reader;
		try {
			url_reader = new BufferedReader(new FileReader("resources\\url.txt"));
			Bluetooth_Ressource.setLast_available_url(url_reader.readLine());
			url_reader.close();
		} catch (IOException e) {
			System.out.println("Ressource file resources\\url.txt not found");
			e.printStackTrace();
		}
		
		Data_File_Path data_file_path_object = new Data_File_Path();
		data_file_path = data_file_path_object.generateDataFilePathField(refX, refY + 60, 470, 25, 200, 25);
		data_file_path_label = data_file_path_object.getPath_label();
		
		BufferedReader data_reader;
		try {
			data_reader = new BufferedReader(new FileReader("resources\\path.txt"));
			data_file_path.setText(data_reader.readLine());
			data_reader.close();
		} catch (IOException e) {
			System.out.println("Ressource file resources\\path.txt not found");
			e.printStackTrace();
		}
		
		Load_Data_Button load_data_button_object = new Load_Data_Button();
		load_data_button = load_data_button_object.generateLoadDataButton(refX + 100, refY + 115, 80, 25);
		
		Read_File_Status read_file_status_object = new Read_File_Status();
		read_file_status = read_file_status_object.generateReadFileStatus(refX + 190, refY + 115, 200, 25);
		
		Launch_Button launch_button_object = new Launch_Button();
		launch_button = launch_button_object.generateLaunchButton(refX + 400, refY + 115, 70, 25);
		
		// functionality of the scan comm: start a bluetooth device scan
		find_comm_button.setOnAction(event -> {		
			Bluetooth_Ressource.getBluetooth_module().scanBluetoothComms();
		});
		
		// add change listener to the choice box for comm devices
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					for (int i = 0; i < Bluetooth_Ressource.getAvailable_comm_name_list().size(); ++i) {
						if (newValue.equals(Bluetooth_Ressource.getAvailable_comm_name_list().get(i))) {
							Bluetooth_Ressource.setCurrent_comm_selected(i);
							break;
						}
					}
				}
			}
		};
		available_bt_comm_list.getSelectionModel().selectedItemProperty().addListener(changeListener);
		
		// find url of the object
		findurl_button.setOnAction(event -> {
			Bluetooth_Ressource.setLast_available_url(null);
			if (Bluetooth_Ressource.getCurrent_comm_selected() >= 0) {
				// for sum reason this has to be run twice to ensure that URL is found...
				Bluetooth_Ressource.getBluetooth_module().findURLOfBluetoothDevice();
				Bluetooth_Ressource.getBluetooth_module().findURLOfBluetoothDevice();
			}
		});
		
		// connect to the URL found
		connect_button.setOnAction(event -> {
			if (Bluetooth_Ressource.getStreamConnection() == null) {
				Thread_Common.setConnecting(1);
				Bluetooth_Ressource.getBluetooth_module().connectToBluetoothDevice();
				Thread_Common.setConnecting(0);
			}
		});
		
		// disconnect button to rapidly close the comm to the hc05 module
		disconnect_button.setOnAction(event -> {
			if (Bluetooth_Ressource.getStreamConnection() != null)
				try {
					Bluetooth_Ressource.getStreamConnection().close();
					Bluetooth_Ressource.setStreamConnection(null);
				} catch (IOException e) {
					System.out.println("Error trying to close connection");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			connection_status.setText("NOK");
		});
		
		// load data button action: launch the C_Debug_Data_Fetcher to load up the monitoring and control variables
		load_data_button.setOnAction(event -> {
			if (data_file_path.getText() != null) {
				C_Debug_Data_Fetcher.fetchData(data_file_path.getText());
				if (Thread_Common.getRead_file_status() == -1) {
					read_file_status.setText("NOK: invalid file path");
				}else if (Thread_Common.getRead_file_status() == -2) {
					read_file_status.setText("NOK: read error has occured");
				}else if (Thread_Common.getRead_file_status() == 0) {
					read_file_status.setText("NOK: cannot find variables");
				}else if (Thread_Common.getRead_file_status() == 1) {
					read_file_status.setText("OK: variables are loaded");
				}
			}
		});
		
		// launch button change state of program from 1 to 2
		launch_button.setOnAction(event -> {
			if (Bluetooth_Ressource.getStreamConnection() != null && Thread_Common.getRead_file_status() == 1) {
				Thread_Common.setInterface_on(1);
				Thread_Common.setInterface_class(null);
				Thread_Common.setInterface_class(new Interface());
				Thread_Common.getInterface_class().start();
				Thread_Common.setBluetooth_thread(null);
				Thread_Common.setBluetooth_thread(new Bluetooth_Module());
				Thread_Common.getBluetooth_thread().start();
			}
		});
	}
}
