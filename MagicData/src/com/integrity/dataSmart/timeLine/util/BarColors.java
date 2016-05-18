package com.integrity.dataSmart.timeLine.util;

import java.util.HashMap;
import java.util.Map;

import com.integrity.dataSmart.common.DataType;

public class BarColors {
	
	public static Map<String, String> getTypeColor() {
		Map<String, String> colorMap = new HashMap<String, String>();
		colorMap.put(DataType.EVENTCALL, DataType.CALLCOLOR);
		colorMap.put(DataType.EVENTEMAIL, DataType.EMAILCOLOR);
		colorMap.put(DataType.EVENTLOGIN, DataType.LOGINCOLOR);
		colorMap.put(DataType.EVENTSTAY, DataType.STAYCOLOR);
		colorMap.put(DataType.EVENTBBS, DataType.BBSCOLOR);
		colorMap.put(DataType.EVENTMEDICAL, DataType.MEDICALCOLOR);
		return colorMap;
	  }
}
