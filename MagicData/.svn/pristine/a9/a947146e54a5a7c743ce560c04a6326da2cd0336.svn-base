package com.integrity.dataSmart.map.util;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.eclipse.jdt.core.dom.ThisExpression;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

public class GeoIPUtil {
	
	private static DatabaseReader reader = null;
	
	public static DatabaseReader getInstance(){
		String CurrentClassFilePath = GeoIPUtil.class.getResource("").getPath();   
		int lastpath=CurrentClassFilePath.lastIndexOf("classes/");
	    String web_rootPath=CurrentClassFilePath.substring(0,lastpath);
		try {
			if(reader==null){
				File database = new File(web_rootPath+"geoip/GeoLite2-City.mmdb");
				reader = new DatabaseReader.Builder(database).build();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reader;
	}
	
	public Location getLocation(String ip){
		
		Location location = null;
		try {
			InetAddress ipAddress = InetAddress.getByName(ip);

			CityResponse response = reader.city(ipAddress);

			location = response.getLocation();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
		
	}

}
