package application.model;

import java.util.ArrayList;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;

import application.controller.Bluetooth_Module;


public class Bluetooth_Ressource {
	// attributes
	private static StreamConnection streamConnection;
	private static int current_comm_selected;
	private static ArrayList<RemoteDevice> available_comm_objects;
	private static Bluetooth_Module bluetooth_module;
	private static ArrayList<String> available_comm_name_list;
	private static ArrayList<String> available_comm_address_list;
	private static String last_available_url; // this is what is important to establish comm
	
	// getter + setter
	public static StreamConnection getStreamConnection() {
		return streamConnection;
	}
	public static void setStreamConnection(StreamConnection streamConnection) {
		Bluetooth_Ressource.streamConnection = streamConnection;
	}
	public static int getCurrent_comm_selected() {
		return current_comm_selected;
	}
	public static void setCurrent_comm_selected(int current_comm_selected) {
		Bluetooth_Ressource.current_comm_selected = current_comm_selected;
	}
	public static ArrayList<RemoteDevice> getAvailable_comm_objects() {
		return available_comm_objects;
	}
	public static void setAvailable_comm_objects(ArrayList<RemoteDevice> available_comm_objects) {
		Bluetooth_Ressource.available_comm_objects = available_comm_objects;
	}
	public static Bluetooth_Module getBluetooth_module() {
		return bluetooth_module;
	}
	public static void setBluetooth_module(Bluetooth_Module bluetooth_module) {
		Bluetooth_Ressource.bluetooth_module = bluetooth_module;
	}

	public static ArrayList<String> getAvailable_comm_name_list() {
		return available_comm_name_list;
	}
	public static void setAvailable_comm_name_list(ArrayList<String> available_comm_name_list) {
		Bluetooth_Ressource.available_comm_name_list = available_comm_name_list;
	}
	public static ArrayList<String> getAvailable_comm_address_list() {
		return available_comm_address_list;
	}
	public static void setAvailable_comm_address_list(ArrayList<String> available_comm_address_list) {
		Bluetooth_Ressource.available_comm_address_list = available_comm_address_list;
	}
	public static String getLast_available_url() {
		return last_available_url;
	}
	public static void setLast_available_url(String last_available_url) {
		Bluetooth_Ressource.last_available_url = last_available_url;
	}		
	
	// initialize
	public static void init() {
		current_comm_selected = -1;
		bluetooth_module = new Bluetooth_Module();
		last_available_url = null;
	}
}
