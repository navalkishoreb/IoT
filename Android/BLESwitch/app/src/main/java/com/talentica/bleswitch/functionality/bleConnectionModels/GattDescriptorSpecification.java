package com.talentica.bleswitch.functionality.bleConnectionModels;

import java.util.HashMap;

/**
 * Created by NavalB on 24-05-2016.
 */
public class GattDescriptorSpecification {
	private static HashMap<String,String> descriptorSpecificationMap;
	static {
		descriptorSpecificationMap = new HashMap<>();
		descriptorSpecificationMap.put("2900", "Characteristic Extended Properties");
		descriptorSpecificationMap.put("2901", "Characteristic User Description");
		descriptorSpecificationMap.put("2902", "Client Characteristic Configuration");
		descriptorSpecificationMap.put("2903", "Server Characteristic Configuration");
		descriptorSpecificationMap.put("2904", "Characteristic Presentation Format");
		descriptorSpecificationMap.put("2905", "Characteristic Aggregate Format");
		descriptorSpecificationMap.put("2906", "Valid Range");
		descriptorSpecificationMap.put("2907", "External Report Reference");
	}




	public static String getSpecificationName(String assignedNumber){
		if(descriptorSpecificationMap.containsKey(assignedNumber)){
			return descriptorSpecificationMap.get(assignedNumber);
		}
		return "<unknown_descriptor_specification>";
	}
}
