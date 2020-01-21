package application.view;

import application.controller.Data_Concat;
import application.model.Debug_Data;
import application.model.Thread_Common;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import application.view.control_package_subcomponents.*;

public class Control_Package {
	private Control_List control_list_object;
	private Read_Button read_button_object;
	private Read_Result read_result_object;
	private Write_Button write_button_object;
	private Write_Field write_field_object;
	private int current_control_chosen;
	private int read_result_buffer;
	
	public void generateControlPackage(int refX, int refY) {
		// generate all elements of a control package from a reference point in application
		control_list_object.generateControlBox(refX, refY, 100, 25);
		read_button_object.generateReadButton(refX + 350, refY, 50, 25);
		write_button_object.generateWriteButton(refX + 350, refY + 30, 50, 25);
		read_result_object.generateReadValueField(refX + 125, refY, 200, 25);
		write_field_object.generateWriteField(refX + 125, refY + 30, 200, 25);
		
		// setup for change listener that is used to update the control variable chosen by user
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					for (int i = 0; i < Debug_Data.getDof(); ++i) {
						if (newValue.equals(Debug_Data.getControl_names()[i])) {
							current_control_chosen = i;
							break;
						}
					}
				}
			}
		};
		control_list_object.getControl_choicebox().getSelectionModel().selectedItemProperty().addListener(changeListener);
		
		// set the read button on click will add a read frame to the queue
		read_button_object.getRead_button().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (current_control_chosen >= 0 && current_control_chosen < 64) {
						// generate the signal frame with the current chosen control variable
						Thread_Common.getQueue_access_semaphore().acquire();
						byte read_frame = (byte) (current_control_chosen | 0x40);
						Thread_Common.getCommand_queue().add(read_frame);
						Thread_Common.getQueue_access_semaphore().release();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		// set the write button on click will add a write frame to the queue
		write_button_object.getWrite_button().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int input_error = 0;
				try {
					if (current_control_chosen >= 0 && current_control_chosen < 64) {
						Thread_Common.getQueue_access_semaphore().acquire();
						byte read_frame = (byte) (current_control_chosen | 0x80);
						// this signal would be handled differently by the bluetooth interface: it will consider the next 4 bytes to be the data to be sent
						byte[] buffer = {0,0,0,0};
						try {
							if (Debug_Data.getControl_type()[current_control_chosen].equals("int")) {
								buffer = Data_Concat.int_to_bytes(Integer.parseInt(write_field_object.getWrite_field().getText()));
							}else if (Debug_Data.getControl_type()[current_control_chosen].equals("float")) {
								buffer = Data_Concat.float_to_bytes(Float.parseFloat(write_field_object.getWrite_field().getText()));
							}
						} catch (NumberFormatException e) {
							input_error = 1;
							System.out.println("Number format error");
							e.printStackTrace();
						}
						// add the 4 byte data frame to the queue
						if (input_error == 0) {
							Thread_Common.getCommand_queue().add(read_frame);
							for (int j = 0; j < 4; ++j) Thread_Common.getCommand_queue().add(buffer[3-j]);
						}
						Thread_Common.getQueue_access_semaphore().release();
					}
				}  catch (InterruptedException x) {
					System.out.println("Semaphore error when writing.");
					x.printStackTrace();
				}
			}
		});	
	}
		
	// getters to access the control list elements
	public Control_List getControl_list_object() {
		return control_list_object;
	}

	public Read_Button getRead_button_object() {
		return read_button_object;
	}

	public Read_Result getRead_result_object() {
		return read_result_object;
	}

	public Write_Button getWrite_button_object() {
		return write_button_object;
	}

	public Write_Field getWrite_field_object() {
		return write_field_object;
	}
	
	public int getCurrent_control_chosen() {
		return current_control_chosen;
	}

	public void setCurrent_control_chosen(int current_control_chosen) {
		this.current_control_chosen = current_control_chosen;
	}

	public int getRead_result_buffer() {
		return read_result_buffer;
	}

	public void setRead_result_buffer(int read_result_buffer) {
		this.read_result_buffer = read_result_buffer;
	}

	public Control_Package() {
		control_list_object = new Control_List();
		read_button_object = new Read_Button();
		read_result_object = new Read_Result();
		write_button_object = new Write_Button();
		write_field_object = new Write_Field();
		current_control_chosen = 0;
	}
}