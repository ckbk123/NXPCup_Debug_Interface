package application.controller;

import java.nio.*;

public class Data_Concat {
	public static int bytes_to_int (byte[] buffer) {
		return ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	public static float bytes_to_float (byte[] buffer) {
		return ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	
	public static byte[] int_to_bytes (int int_buffer) {
		return ByteBuffer.allocate(4).putInt(int_buffer).array();
	}
	
	public static byte[] float_to_bytes (float float_buffer) {
		return ByteBuffer.allocate(4).putFloat(float_buffer).array();
	}
}
