package application.model;

public class Debug_Data {
	/* main monitoring + control variables */
	
	// camera datas
	private static byte[] camera_data_1;
	private static byte[] camera_data_2;
	
	// monitoring package
	private static Monitoring_Class[] monitoring_package;
	
	// control datas
	private static String[] control_names;
	private static String[] control_type;
	private static String[] control_variables;   // this would be the last available from read signal
	
	// number of control variables
	private static int dof;
	
	/* getter and setter */	
	public static byte[] getCamera_data_1() {
		return camera_data_1;
	}

	public static void setCamera_data_1(int index, byte data) {
		Debug_Data.camera_data_1[index] = data;
	}

	public static byte[] getCamera_data_2() {
		return camera_data_2;
	}

	public static void setCamera_data_2(int index, byte data) {
		Debug_Data.camera_data_2[index] = data;
	}

	public static Monitoring_Class[] getMonitoring_package() {
		return monitoring_package;
	}

	public static void setMonitoring_package(Monitoring_Class[] monitoring_package) {
		Debug_Data.monitoring_package = monitoring_package;
	}

	public static String[] getControl_names() {
		return control_names;
	}

	public static void setControl_names(String[] control_names) {
		Debug_Data.control_names = control_names;
	}

	public static String[] getControl_type() {
		return control_type;
	}

	public static void setControl_type(String[] control_type) {
		Debug_Data.control_type = control_type;
	}

	public static String[] getControl_variables() {
		return control_variables;
	}

	public static void setControl_variable(String control_variable, int index) {
		Debug_Data.control_variables[index] = control_variable;
	}

	public static int getDof() {
		return dof;
	}

	public static void setDof(int dof) {
		Debug_Data.dof = dof;
	}

	/* init method to be called to initialize the class*/
	public static void init() {
		Debug_Data.camera_data_1 = new byte[128];
		Debug_Data.camera_data_2 = new byte[128];
		
		Debug_Data.monitoring_package = new Monitoring_Class[20];
		for (int i = 0; i < 20; i++) monitoring_package[i] = new Monitoring_Class("int", "variable name", "110198");
		
		Debug_Data.control_names = new String[64];
		Debug_Data.control_type = new String[64];
		Debug_Data.control_variables = new String[64];
		
		Debug_Data.dof = 0;
	}
}
