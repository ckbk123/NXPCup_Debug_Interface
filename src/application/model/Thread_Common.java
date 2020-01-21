package application.model;

import java.util.concurrent.Semaphore;

import application.Interface;

import java.util.LinkedList;
import java.util.Queue;

public class Thread_Common {
	// attributes
	private static Thread bluetooth_thread; 
	private static Interface interface_class; 
	private static Semaphore debug_data_semaphore;
	private static Semaphore queue_access_semaphore;
	private static Semaphore fetching_url_semaphore;
	private static Queue<Byte> command_queue;
	private static Semaphore refresh_semaphore;
	private static int refresh;
	private static int run_comm;
	private static int read_file_status;
	private static int scanning;
	private static int finding;
	private static int connecting;
	private static int interface_on;
	
	// getter + setter
	public static Thread getBluetooth_thread() {
		return bluetooth_thread;
	}
	public static void setBluetooth_thread(Thread bluetooth_thread) {
		Thread_Common.bluetooth_thread = bluetooth_thread;
	}
	public static Interface getInterface_class() {
		return interface_class;
	}
	public static void setInterface_class(Interface interface_class) {
		Thread_Common.interface_class = interface_class;
	}
	public static Semaphore getDebug_data_semaphore() {
		return debug_data_semaphore;
	}
	public static void setDebug_data_semaphore(Semaphore debug_data_semaphore) {
		Thread_Common.debug_data_semaphore = debug_data_semaphore;
	}
	public static Semaphore getQueue_access_semaphore() {
		return queue_access_semaphore;
	}
	public static void setQueue_access_semaphore(Semaphore queue_access_semaphore) {
		Thread_Common.queue_access_semaphore = queue_access_semaphore;
	}

	public static Queue<Byte> getCommand_queue() {
		return command_queue;
	}

	public static void setCommand_queue(Queue<Byte> command_queue) {
		Thread_Common.command_queue = command_queue;
	}
	
	public static Semaphore getRefresh_semaphore() {
		return refresh_semaphore;
	}

	public static void setRefresh_semaphore(Semaphore refresh_semaphore) {
		Thread_Common.refresh_semaphore = refresh_semaphore;
	}
	public static Semaphore getFetching_url_semaphore() {
		return fetching_url_semaphore;
	}
	public static void setFetching_url_semaphore(Semaphore fetching_url_semaphore) {
		Thread_Common.fetching_url_semaphore = fetching_url_semaphore;
	}
	public static int getRefresh() {
		return refresh;
	}
	public static void setRefresh(int refresh) {
		Thread_Common.refresh = refresh;
	}

	public static int getRun_comm() {
		return run_comm;
	}

	public static void setRun_comm(int run_comm) {
		Thread_Common.run_comm = run_comm;
	}
	
	public static void closeBluetoothComm() {
		Thread_Common.run_comm = 0;
	}
	public static int getRead_file_status() {
		return read_file_status;
	}
	public static void setRead_file_status(int read_file_status) {
		Thread_Common.read_file_status = read_file_status;
	}
	public static int getScanning() {
		return scanning;
	}
	public static void setScanning(int scanning) {
		Thread_Common.scanning = scanning;
	}
	public static int getFinding() {
		return finding;
	}
	public static void setFinding(int finding) {
		Thread_Common.finding = finding;
	}
	public static int getConnecting() {
		return connecting;
	}
	public static void setConnecting(int connecting) {
		Thread_Common.connecting = connecting;
	}
	public static int getInterface_on() {
		return interface_on;
	}
	public static void setInterface_on(int interface_on) {
		Thread_Common.interface_on = interface_on;
	}
	
	// init the thread common package
	public static void init() {
		System.out.println("The thread common variables are initialized");
		bluetooth_thread = null;
		interface_class = null;
		read_file_status = 0;
		run_comm = 1;
		refresh = 0;
		command_queue = new LinkedList<Byte>();
		queue_access_semaphore = new Semaphore(1);
		refresh_semaphore = new Semaphore(1);
		fetching_url_semaphore = new Semaphore(1);
		scanning = -1;
		finding = 0;
		connecting = 0;
		interface_on = 0;
	}

}
