package application;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.model.Bluetooth_Ressource;
import application.model.Thread_Common;
import application.view.Debug_Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main_App extends Application {
	private ScheduledExecutorService scheduledExecutorService;
	private int last_scan_status;
	private int last_interface_on;
	
	@Override
	public void start(Stage primaryStage) {
		// everything about the debug controller 
		Debug_Controller debug_controller_object = new Debug_Controller();
		debug_controller_object.generateDebugController(15, 15);
		
		Pane debug_controller = new Pane(debug_controller_object.getFind_comm_button(),
							debug_controller_object.getAvailable_bt_comm_list(),
							debug_controller_object.getScan_status(),
							debug_controller_object.getFindurl_button(),
							debug_controller_object.getConnect_button(),
							debug_controller_object.getDisconnect_button(),
							debug_controller_object.getConnection_status(),
							debug_controller_object.getLast_available_url(),
							debug_controller_object.getLast_available_url_label(),
							debug_controller_object.getData_file_path(),
							debug_controller_object.getData_file_path_label(),
							debug_controller_object.getLoad_data_button(),
							debug_controller_object.getRead_file_status(),
							debug_controller_object.getLaunch_button());
		Scene scene1 = new Scene(debug_controller, 500, 170);
		
		
		// primary stage
		primaryStage.setTitle("Debug Interface Controller");
		primaryStage.setScene(scene1);
		primaryStage.setResizable(false);
		primaryStage.show();
			
		primaryStage.setOnCloseRequest(event -> {
			// terminate bluetooth comm (if brutal exit)
			Thread_Common.closeBluetoothComm();
			// clear the 2 resource files for overwrite
			PrintWriter pw;
			try {
				pw = new PrintWriter("resources\\path.txt");
				pw.close();
				pw = new PrintWriter("resources\\url.txt");
				pw.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("The resource files cannot be found.");
				e.printStackTrace();
			}
			// then write down the current configuration to these files
			FileWriter fw;
			try {
				fw = new FileWriter("resources\\path.txt");
				if (debug_controller_object.getData_file_path().getText() != null) fw.write(debug_controller_object.getData_file_path().getText());
				fw.close();
				fw = new FileWriter("resources\\url.txt");
				if (Bluetooth_Ressource.getLast_available_url() != null) fw.write(Bluetooth_Ressource.getLast_available_url());
				fw.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("The resource files cannot be found.");
				e.printStackTrace();
			} catch (IOException x) {
				System.out.println("Write error has occured. Data might be corrupted.");
				x.printStackTrace();
			}
			
			Platform.exit();
			System.exit(0);
		});
		
		// state variable
		last_scan_status = Thread_Common.getScanning();
		last_interface_on = Thread_Common.getInterface_on();
		
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(()-> {
			Platform.runLater(()-> {
				if (Thread_Common.getScanning() != last_scan_status) {
					if (Thread_Common.getScanning() == 1) {
						debug_controller_object.getFind_comm_button().setDisable(true);
						debug_controller_object.getAvailable_bt_comm_list().setDisable(true);
						debug_controller_object.getScan_status().setText("Scanning...");
					}else if (Thread_Common.getScanning() == -1) {
						debug_controller_object.getFind_comm_button().setDisable(true);
						debug_controller_object.getAvailable_bt_comm_list().setDisable(true);
					}else if (Thread_Common.getScanning() == 0) {
						debug_controller_object.getFind_comm_button().setDisable(false);
						debug_controller_object.getAvailable_bt_comm_list().setDisable(false);
						debug_controller_object.getScan_status().setText("Scan done");
						debug_controller_object.getAvailable_bt_comm_list().getItems().clear();
						for (int i = 0; i < Bluetooth_Ressource.getAvailable_comm_name_list().size(); ++i) {
							debug_controller_object.getAvailable_bt_comm_list().getItems().add(Bluetooth_Ressource.getAvailable_comm_name_list().get(i));
						}
						Bluetooth_Ressource.setCurrent_comm_selected(-1);
					}
					
				}				
				// update state for scan button and its status
				last_scan_status = Thread_Common.getScanning(); 
				
				// update the status of the find url button
				if (Bluetooth_Ressource.getCurrent_comm_selected() >= 0 && Thread_Common.getScanning() == 0) {
					debug_controller_object.getFindurl_button().setDisable(false);
				}else {
					debug_controller_object.getFindurl_button().setDisable(true);
				}
				
				// update the url field
				if (Bluetooth_Ressource.getLast_available_url() != null) {
					debug_controller_object.getLast_available_url().setText(Bluetooth_Ressource.getLast_available_url());
				}else {
					debug_controller_object.getLast_available_url().setText("No recent connection");
				}
				
				// update the status of the connect status
				if (Thread_Common.getConnecting() == 1) {
					debug_controller_object.getConnection_status().setText("Connecting...");
				}else {
					if (Bluetooth_Ressource.getStreamConnection() != null) {
						debug_controller_object.getConnection_status().setText("OK");
					}else {
						debug_controller_object.getConnection_status().setText("NOK");
					}
				}
				
				// monitoring for when ppl can connect
				if (Bluetooth_Ressource.getLast_available_url() == null || Thread_Common.getScanning() == 1) {
					debug_controller_object.getConnect_button().setDisable(true);
					debug_controller_object.getDisconnect_button().setDisable(true);
				}else {
					debug_controller_object.getConnect_button().setDisable(false);
					debug_controller_object.getDisconnect_button().setDisable(false);
				}
				
				// update launch button: need to have connection and data loaded
				if (Bluetooth_Ressource.getStreamConnection() != null && Thread_Common.getRead_file_status() == 1) {
					debug_controller_object.getLaunch_button().setDisable(false);
				}else {
					debug_controller_object.getLaunch_button().setDisable(true);
				}
				
				// hide the controller when user is working on interface
				if (last_interface_on != Thread_Common.getInterface_on()) {
					if (Thread_Common.getInterface_on() == 1) {
						primaryStage.hide();
					}else {
						primaryStage.show();
					}
				}
				last_interface_on = Thread_Common.getInterface_on();
			});
		}, 0, 200, TimeUnit.MILLISECONDS);
	}
	
	public static void main(String[] args) {
		Thread_Common.init();
		Bluetooth_Ressource.init();		
		launch(args);
	}
}
