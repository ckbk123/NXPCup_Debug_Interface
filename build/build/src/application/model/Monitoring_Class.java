package application.model;

public class Monitoring_Class {
	private String monitoring_name;
	private String monitoring_type;
	private String monitoring_variable;
	
	Monitoring_Class (String monitoring_name, String monitoring_type, String monitoring_variable) {
		this.monitoring_name = monitoring_name;
		this.monitoring_type = monitoring_type;
		this.monitoring_variable = monitoring_variable;
	}

	public String getMonitoring_name() {
		return monitoring_name;
	}


	public void setMonitoring_name(String monitoring_name) {
		this.monitoring_name = monitoring_name;
	}


	public String getMonitoring_type() {
		return monitoring_type;
	}


	public void setMonitoring_type(String monitoring_type) {
		this.monitoring_type = monitoring_type;
	}


	public String getMonitoring_variable() {
		return monitoring_variable;
	}


	public void setMonitoring_variable(String monitoring_variable) {
		this.monitoring_variable = monitoring_variable;
	}
}
