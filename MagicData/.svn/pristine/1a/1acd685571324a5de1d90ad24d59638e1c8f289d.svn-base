package com.integrity.dataSmart.impAnalyImport.hdfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.integrity.dataSmart.impAnalyImport.bean.Email;


public class TestHDFS {

	public static void main(String[] args) {
		try {
			ProcessHDFS hdfsutil = ProcessHDFS.getInstance("D:\\hadoop\\core-site.xml");
			System.out.println(new Date().getTime());
			Email email = new Email();
			ArrayList<String> files = new ArrayList<>();
			files.add("D:\\eml\\1437115849146\\fax21584.tif");
			files.add("D:\\eml\\1437115849146\\fax215842.tif");
			email.setAttachfilenames(files);
			hdfsutil.sendFile(email);
			System.out.println(new Date().getTime());
			ProcessHDFS.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}

}
