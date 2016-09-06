package com.talentica.blescanlib.scanning.bleConnectionModels;

import java.util.HashMap;

/**
 * Created by NavalB on 24-05-2016.
 */
public class GattServiceSpecification {
	private static HashMap<String,String> serviceSpecificationMap;
	static {
		serviceSpecificationMap = new HashMap<>();
		serviceSpecificationMap.put("1800","Generic Access");
		serviceSpecificationMap.put("1801","Generic Attribute");
		serviceSpecificationMap.put("1802","Immediate Alert");
		serviceSpecificationMap.put("1803","Link Loss");
		serviceSpecificationMap.put("1804","Tx Power");
		serviceSpecificationMap.put("1805","Current Time Service");
		serviceSpecificationMap.put("180F","Battery Service");
	}




	public static String getSpecificationName(String assignedNumber){
		if(serviceSpecificationMap.containsKey(assignedNumber)){
			return serviceSpecificationMap.get(assignedNumber);
		}
		return "<unknown_service_specification>";
	}
}
