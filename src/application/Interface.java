package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import application.model.Bluetooth_Ressource;
import application.model.Debug_Data;
import application.model.Monitoring_Class;
import application.model.Thread_Common;
import application.view.Camera_Graph;
import application.view.Control_Package;
import application.view.Monitoring_Table;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Interface {
	private ScheduledExecutorService scheduledExecutorService;
	private ObservableList<Monitoring_Class> monitoring_list = FXCollections.observableArrayList();
	final static char[] debug_sequence = {
			0xFF,  // reset signal
			0xFE,  // camera 1 request
			0xFD,  // camera 2 request
			0x00,  // monitoring variables request
			0x01,
			0x02,
			0x03,
			0x04,
			0x05,
			0x06,
			0x07,
			0x08,
			0x09,
			0x0A,
			0x0B,
			0x0C,
			0x0D,
			0x0E,
			0x0F,
			0x10,
			0x11,
			0x12,
			0x13
		};
	
	public void start() {
		/* generate graphic elements to be assembled */
		// the camera section
		Stage primaryStage = new Stage();
		
		Camera_Graph camera_1_graph = new Camera_Graph();
		Camera_Graph camera_2_graph = new Camera_Graph();
		AreaChart<Number, Number> area_chart_1 = camera_1_graph.generateGraph("Camera 1", 5, 5, 500, 300);
		AreaChart<Number, Number> area_chart_2 = camera_2_graph.generateGraph("Camera 2", 5, 300, 500, 300);
		
		// the constant monitoring section
		Monitoring_Table monitoring_table_object = new Monitoring_Table();
		TableView<Monitoring_Class> monitoring_table = monitoring_table_object.generateMonitoringTable(550, 5, 400, 300);
		monitoring_list.addAll(Debug_Data.getMonitoring_package());
		monitoring_table.setItems(monitoring_list);
				
		// the control variable section
		Control_Package control_package_1 = new Control_Package();
		control_package_1.generateControlPackage(550, 320);
		
		Control_Package control_package_2 = new Control_Package();
		control_package_2.generateControlPackage(550, 380);
		
		Control_Package control_package_3 = new Control_Package();
		control_package_3.generateControlPackage(550, 440);
		
		Control_Package control_package_4 = new Control_Package();
		control_package_4.generateControlPackage(550, 500);
		
		/* generate the final assembled pane */
		Pane root = new Pane(area_chart_1, area_chart_2, monitoring_table, 
				control_package_1.getControl_list_object().getControl_choicebox(),
				control_package_1.getRead_button_object().getRead_button(),
				control_package_1.getRead_result_object().getRead_result(),
				control_package_1.getWrite_button_object().getWrite_button(),
				control_package_1.getWrite_field_object().getWrite_field(),
				
				control_package_2.getControl_list_object().getControl_choicebox(),
				control_package_2.getRead_button_object().getRead_button(),
				control_package_2.getRead_result_object().getRead_result(),
				control_package_2.getWrite_button_object().getWrite_button(),
				control_package_2.getWrite_field_object().getWrite_field(),
				
				control_package_3.getControl_list_object().getControl_choicebox(),
				control_package_3.getRead_button_object().getRead_button(),
				control_package_3.getRead_result_object().getRead_result(),
				control_package_3.getWrite_button_object().getWrite_button(),
				control_package_3.getWrite_field_object().getWrite_field(),
				
				control_package_4.getControl_list_object().getControl_choicebox(),
				control_package_4.getRead_button_object().getRead_button(),
				control_package_4.getRead_result_object().getRead_result(),
				control_package_4.getWrite_button_object().getWrite_button(),
				control_package_4.getWrite_field_object().getWrite_field()
				);
		Scene scene = new Scene(root, 1000, 600);
		primaryStage.setTitle("NXP Cup Debugger - INSA Toulouse - CKBK 2019");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
				
		primaryStage.setOnCloseRequest(event -> {
			// TODO handle exit of this thing to ensure that reinit another run is ok
			Thread_Common.closeBluetoothComm();
			Bluetooth_Ressource.setStreamConnection(null);
			primaryStage.close();
			scheduledExecutorService.shutdown();
			scheduledExecutorService = null;
			Thread_Common.setInterface_on(0);
		});
		
		/* schedule for a refreshing the app */
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(()-> {
			Platform.runLater(()-> {
				System.out.println("Interface OK");
				/* read cam data */
				camera_1_graph.getSeries().getData().clear();
				camera_2_graph.getSeries().getData().clear();
				for (int j = 0; j < 128; ++j) camera_1_graph.getSeries().getData().add(new Data<Number, Number>(j, Byte.toUnsignedInt(Debug_Data.getCamera_data_1()[j])));
				for (int j = 0; j < 128; ++j) camera_2_graph.getSeries().getData().add(new Data<Number, Number>(j, Byte.toUnsignedInt(Debug_Data.getCamera_data_2()[j])));
				
				/* read a new the monitoring package for update */
				monitoring_table.getItems().clear();
				monitoring_list.addAll(Debug_Data.getMonitoring_package());
				monitoring_table.setItems(monitoring_list);
				
				// debug the current control chosen
				try {
					Thread_Common.getRefresh_semaphore().acquire();
					if (Thread_Common.getRefresh() == 1) {
						Thread_Common.setRefresh(0);  // clear refresh signal
						// update label field in control package
						control_package_1.getRead_result_object().getRead_result().setText(Debug_Data.getControl_variables()[control_package_1.getCurrent_control_chosen()]);
						control_package_2.getRead_result_object().getRead_result().setText(Debug_Data.getControl_variables()[control_package_2.getCurrent_control_chosen()]);
						control_package_3.getRead_result_object().getRead_result().setText(Debug_Data.getControl_variables()[control_package_3.getCurrent_control_chosen()]);
						control_package_4.getRead_result_object().getRead_result().setText(Debug_Data.getControl_variables()[control_package_4.getCurrent_control_chosen()]);
					}
					Thread_Common.getRefresh_semaphore().release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}, 0, 200, TimeUnit.MILLISECONDS);
	}
}
