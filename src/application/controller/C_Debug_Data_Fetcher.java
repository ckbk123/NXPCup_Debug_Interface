package application.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import application.model.Debug_Data;
import application.model.Thread_Common;

public class C_Debug_Data_Fetcher {
	private static String extractCVariableName(String input) {
		int i; // cut of index
		for (i = 0; i < input.length(); ++i) {
			if (input.charAt(i) == ' ' || input.charAt(i) == ';') {
				break;
			}
		}
		return input.substring(0,i).trim();
	}
	
	// this function reads and intepret the data.h source file to know which variables are to be monitored and controlled
	public static void fetchData(String filename) {
		Debug_Data.init(); // init or reinit the entire debug data package
		Thread_Common.setRead_file_status(0);
		BufferedReader reader;

		// filtering variables
		int case_counter = 0;
		int reading_monitoring_vars = 0;
		int monitoring_counter = 0;
		int reading_control_vars = 0;
		int control_counter = 0;
		
		try {
			reader = new BufferedReader (new FileReader(filename));
		
			String line = reader.readLine();
			while (line != null) {
				String buffer = line.trim();
				if (buffer.equals("/* CAMERA DATA */")) {
					++case_counter;
				}else if (buffer.equals("/* END CAMERA DATA */")) {
					++case_counter;
				}else if (buffer.equals("/* MONITORING */")) {
					++case_counter;
					reading_monitoring_vars = 1;
				}else if (buffer.equals("/* END MONITORING */")) {
					++case_counter;
					reading_monitoring_vars = 0;
				}else if (buffer.equals("/* CONTROL VARIABLES */")) {
					++case_counter;
					reading_control_vars = 1;
				}else if (buffer.equals("/* END CONTROL VARIABLES */")) {
					++case_counter;
					reading_control_vars = 0;
				}else if (reading_monitoring_vars == 1 && monitoring_counter < 20) { // monitoring_counter cut off at 20
					// crop out the type name and the variable name to be displayed later
					if (buffer.contains("int ")) {
						Debug_Data.getMonitoring_package()[monitoring_counter].setMonitoring_type("int");
						buffer = buffer.substring(4);
						Debug_Data.getMonitoring_package()[monitoring_counter++].setMonitoring_name(extractCVariableName(buffer));
					}else if (buffer.contains("float ")) {
						Debug_Data.getMonitoring_package()[monitoring_counter].setMonitoring_type("float");
						buffer = buffer.substring(6);
						Debug_Data.getMonitoring_package()[monitoring_counter++].setMonitoring_name(extractCVariableName(buffer));
					}
				}else if (reading_control_vars == 1 && control_counter < 64) { // control_counter cut off at 64
					// crop out the type name and the variable name to be displayed later
					Debug_Data.setDof(Debug_Data.getDof() + 1); 
					if (buffer.contains("int ")) {
						Debug_Data.getControl_type()[control_counter] = "int";
						buffer = buffer.substring(4);
						Debug_Data.getControl_names()[control_counter++] = extractCVariableName(buffer);
					}else if (buffer.contains("float ")) {
						Debug_Data.getControl_type()[control_counter] = "float";
						buffer = buffer.substring(6);
						Debug_Data.getControl_names()[control_counter++] = extractCVariableName(buffer);
					}
				}
				// read new line
				line = reader.readLine();
			}
			// if the program run till this point, no problem and counter == 6
			if (case_counter == 6) Thread_Common.setRead_file_status(1);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Thread_Common.setRead_file_status(-1);
			System.out.println("Invalid file path");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Thread_Common.setRead_file_status(-2);
			System.out.println("Problem occured during read file");
			e.printStackTrace();
		}
	}
}
