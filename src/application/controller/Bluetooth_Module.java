package application.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import application.model.Debug_Data;
import application.model.Thread_Common;
import application.model.Bluetooth_Ressource;

public class Bluetooth_Module extends Thread {
	// full monitoring sequence
	private final static char[] debug_sequence = {
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
	
	// some variables needed to initialize the bluetooth comm
    private String hc05Url;
    private DiscoveryListener my_discovery_listener;
    
    // constructor
    public Bluetooth_Module() {
    	my_discovery_listener= new DiscoveryListener() {
		    @Override
		    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		        try {			        	
                    String name = btDevice.getFriendlyName(false);
                    String add = btDevice.getBluetoothAddress();
                    System.out.format("%s (%s)\n", name, add);
                    Bluetooth_Ressource.getAvailable_comm_address_list().add(add);
        			Bluetooth_Ressource.getAvailable_comm_name_list().add(name);
        			Bluetooth_Ressource.getAvailable_comm_objects().add(btDevice);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }

		    @Override
		    public void inquiryCompleted(int discType) {
		        Thread_Common.setScanning(0);
		    }

		    @Override
		    public void serviceSearchCompleted(int transID, int respCode) {
		    	Thread_Common.getFetching_url_semaphore().release();
		    }

		    @Override
		    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		    	for (int i = 0; i < servRecord.length; i++) {
		            hc05Url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
		            if (hc05Url != null) {
		                break; //take the first one
		            }
		        }
		    }
		};
    }
    
    public void scanBluetoothComms() {
    	Thread_Common.setScanning(1);
    	ArrayList<RemoteDevice> device_buffer = new ArrayList<RemoteDevice>();
    	ArrayList<String> name_buffer = new ArrayList<String>();
    	ArrayList<String> add_buffer = new ArrayList<String>();
        try {
			LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, my_discovery_listener);
			// copy these to the main bluetooth ressource class
			Bluetooth_Ressource.setAvailable_comm_address_list(add_buffer);
			Bluetooth_Ressource.setAvailable_comm_name_list(name_buffer);
			Bluetooth_Ressource.setAvailable_comm_objects(device_buffer);
		} catch (BluetoothStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public void findURLOfBluetoothDevice() {
    	UUID uuid = new UUID(0x1101); //scan for btspp://... services (as HC-05 offers it)
        UUID[] searchUuidSet = new UUID[]{uuid};
        int[] attrIDs = new int[]{
            0x0100 // service name
        };
        try {
			Thread_Common.getFetching_url_semaphore().acquire();		
			LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(attrIDs, searchUuidSet, Bluetooth_Ressource.getAvailable_comm_objects().get(Bluetooth_Ressource.getCurrent_comm_selected()), my_discovery_listener);
			Bluetooth_Ressource.setLast_available_url(hc05Url);     
			hc05Url = null;
		} catch (BluetoothStateException e1) {
			// TODO Auto-generated catch block
			System.out.println("Problem at Bluetooth stack");
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Semaphore interrupted in serviceSearch is interrupted");
			e.printStackTrace();
		}
    }
    
    public void connectToBluetoothDevice() {
    	try {
    		if (Bluetooth_Ressource.getLast_available_url() != null)
			Bluetooth_Ressource.setStreamConnection((StreamConnection) Connector.open(Bluetooth_Ressource.getLast_available_url()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // thread main program
    @Override 
    public void run() {
    	try {
    		Thread_Common.setRun_comm(1);
    		StreamConnection streamConnection = Bluetooth_Ressource.getStreamConnection();
    		InputStream is = streamConnection.openInputStream();
    		OutputStream os = streamConnection.openOutputStream();
    		
    		while (Thread_Common.getRun_comm() == 1) { 		
       			for (int i = 0; i < debug_sequence.length; ++i) {
       			// acquire the semaphore to access the queue
       				Thread_Common.getQueue_access_semaphore().acquire();
       				byte command_frame;
       				// check if the queue is empty or not
       				if (Thread_Common.getCommand_queue().isEmpty() == false) {
       					byte[] buffer = {0,0,0,0};
       					// extract the next command frame in queue
       					command_frame = Thread_Common.getCommand_queue().remove();
       					// determine which control variable it is
       					int control_index = (int)command_frame & 0x0000003F;
       					
       					if ( (((int)command_frame & 0x000000C0) >> 6) == 1 ) {  // read frame
       						// send the read command (prefix 0x40 and 6 bits of control variable index)
       						os.write(command_frame);
       						// read the 4 bytes read via Bluetooth
       						for (int j = 0; j < 4; ++j) {
       							buffer[j] = (byte)is.read();
       						}
       						// if it is an int data, convert to int before storing as string. The same for float
       						if (Debug_Data.getControl_type()[control_index].equals("int")) {
       							Debug_Data.setControl_variable(Integer.toString(Data_Concat.bytes_to_int(buffer)),control_index);
       						}else if (Debug_Data.getControl_type()[control_index].equals("float")) {
       							Debug_Data.setControl_variable(Float.toString(Data_Concat.bytes_to_float(buffer)),control_index);
       						}
       						// require a refresh signal to interface on control variable list
       						Thread_Common.getRefresh_semaphore().acquire();
       						Thread_Common.setRefresh(1);
       						Thread_Common.getRefresh_semaphore().release();
       					
       					} else if ( (((int)command_frame & 0x000000C0) >> 6) == 2 ) { // write frame
       						// send the write command (prefix 0x80 and 6 bits of control variable index)
       						os.write(command_frame);
       						// if it is a write frame, the next 4 bytes in the queue is the data. Just extract then send
       						for (int k = 0; k < 4; ++k) {
       							os.write(Thread_Common.getCommand_queue().remove());
       						}
       					}
       				}
					Thread_Common.getQueue_access_semaphore().release();
       				
       				// debug sequence frame
    				os.write(debug_sequence[i]); // send signal
    				if (debug_sequence[i] == 0xFE) {  // camera data 1 receive protocol
    					for (int j = 0; j < 128; ++j) Debug_Data.setCamera_data_1(j ,(byte) is.read());
    				}else if (debug_sequence[i] == 0xFD) { // camera data 2 receive protocol
    					for (int x = 0; x < 128; ++x) Debug_Data.setCamera_data_2(x, (byte) is.read());
    				}else if (debug_sequence[i] >= 0x00 && debug_sequence[i] <= 0x13) {
    					byte[] bluetooth_data = {0,0,0,0}; // monitoring variable receive protocol
    					for (int k = 0; k < 4; ++k) bluetooth_data[k] = (byte) is.read();
    					if (Debug_Data.getMonitoring_package()[debug_sequence[i]].getMonitoring_type().equals("int")) {
    						Debug_Data.getMonitoring_package()[debug_sequence[i]].setMonitoring_variable(Integer.toString(Data_Concat.bytes_to_int(bluetooth_data)));
    					}else {
							Debug_Data.getMonitoring_package()[debug_sequence[i]].setMonitoring_variable(Float.toString(Data_Concat.bytes_to_float(bluetooth_data)));
    					}
    				}else if (debug_sequence[i] == 0xFF) {
    					@SuppressWarnings("unused")
						int temp_buffer = is.read();
    				}
    			}    
       			System.out.println("Comm running");
    		}
    		
    		os.write(0xFF); // reset signal to module, reinit all comm variable on KL25Z to initial condition. This is guaranteed to be outside of a valid comm frame
   			// close the bluetooth comm
   			os.close();
   			is.close();
   			streamConnection.close();
        } catch (Exception ex) {
            Logger.getLogger(Bluetooth_Module.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
