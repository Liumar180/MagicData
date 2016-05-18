package com.integrity.dataSmart.test.twData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlMain {
	public static void main(String[] args) {
//		getTWPerson();
		getTWPerson_student();
	}
	
	/**
	 * TW-人员信息
	 * @return
	 */
	public static List<PersonTemp> getTWPerson(){
		List<PersonTemp> list = new ArrayList<PersonTemp>();
		try {
			File input = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\tw_人员信息\\人员信息.html");
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements links = doc.select("tr:gt(0)");
			for (Element element : links) {
				System.out.println("person-------------------------------------");
				PersonTemp p = new PersonTemp();
				Elements idcard = element.select("td:eq(0)");
				System.out.println("idcard--->"+idcard.text().replaceAll("[ |　]", " ").trim());
				p.setIdcard(idcard.text().replaceAll("[ |　]", " ").trim());
				
				Elements name = element.select("td:eq(1)");
				System.out.println("name--->"+name.text().replaceAll("[ |　]", " ").trim());
				p.setName(name.text().replaceAll("[ |　]", " ").trim());
				
				Elements masterIdcard = element.select("td:eq(4)");
				System.out.println("masterIdcard--->"+masterIdcard.text().replaceAll("[ |　]", " ").trim());
				p.setMasterIdcard(masterIdcard.text().replaceAll("[ |　]", " ").trim());
				
				Elements masterName = element.select("td:eq(6)");
				System.out.println("masterName--->"+masterName.text().replaceAll("[ |　]", " ").trim());
				p.setMasterName(masterName.text().replaceAll("[ |　]", " ").trim());
				
				Elements d1 = element.select("td:eq(8)");
				Elements d2 = element.select("td:eq(11)");
				String address = d1.text().replaceAll("[ |　]", " ").trim()+d2.text().replaceAll("[ |　]", " ").trim();
				System.out.println("address--->"+address);
				p.setAddress(address);
				
				Elements spouseName = element.select("td:eq(13)");
				System.out.println("spouseName--->"+spouseName.text().replaceAll("[ |　]", " ").trim());
				p.setSpouseName(spouseName.text().replaceAll("[ |　]", " ").trim());
				Elements fatherName = element.select("td:eq(14)");
				System.out.println("fatherName--->"+fatherName.text().replaceAll("[ |　]", " ").trim());
				p.setFatherName(fatherName.text().replaceAll("[ |　]", " ").trim());
				
				Elements motherName = element.select("td:eq(15)");
				System.out.println("motherName--->"+motherName.text().replaceAll("[ |　]", " ").trim());
				p.setMotherName(motherName.text().replaceAll("[ |　]", " ").trim());
				
				p.setCountry("中国.台湾");
				
				list.add(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * TW-父母档
	 * @return
	 */
	public static List<PersonTemp> getTWParent(){
		List<PersonTemp> list = new ArrayList<PersonTemp>();
		try {
			File input = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\tw_人员信息\\父母档.html");
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements links = doc.select("tr:gt(0)");
			for (Element element : links) {
				System.out.println("person-------------------------------------");
				PersonTemp p = new PersonTemp();
				Elements idcard = element.select("td:eq(0)");
				System.out.println("idcard--->"+idcard.text().replaceAll("[ |　]", " ").trim());
				p.setIdcard(idcard.text().replaceAll("[ |　]", " ").trim());
				
				Elements name = element.select("td:eq(1)");
				System.out.println("name--->"+name.text().replaceAll("[ |　]", " ").trim());
				p.setName(name.text().replaceAll("[ |　]", " ").trim());
				
				Elements fatherName = element.select("td:eq(4)");
				System.out.println("fatherName--->"+fatherName.text().replaceAll("[ |　|－]", " ").trim());
				p.setFatherName(fatherName.text().replaceAll("[ |　|－]", " ").trim());
				
				Elements motherName = element.select("td:eq(5)");
				System.out.println("motherName--->"+motherName.text().replaceAll("[ |　|－]", " ").trim());
				p.setMotherName(motherName.text().replaceAll("[ |　|－]", " ").trim());
				
				p.setCountry("中国.台湾");
				
				list.add(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * TW-出入境-students  sol nonsoliders
	 * @return
	 */
	public static List<PersonTemp> getTWPerson_student(){
		List<PersonTemp> list = new ArrayList<PersonTemp>();
		try {
			File input = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\tw_出入境/students.html");
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements links = doc.select("tr:gt(0)");
			for (Element element : links) {
				System.out.println("person-------------------------------------");
				PersonTemp p = new PersonTemp();
				Elements idcard = element.select("td:eq(0)");
				System.out.println("idcard--->"+idcard.text().replaceAll("[ |　]", " ").trim());
				p.setIdcard(idcard.text().replaceAll("[ |　]", " ").trim());
				
				Elements name = element.select("td:eq(2)");
				System.out.println("name--->"+name.text().replaceAll("[ |　]", " ").trim());
				p.setName(name.text().replaceAll("[ |　]", " ").trim());
				
				Elements d1 = element.select("td:eq(7)");
				Elements d2 = element.select("td:eq(10)");
				String address = d1.text().replaceAll("[ |　]", " ").trim()+" "+d2.text().replaceAll("[ |　]", " ").trim();
				System.out.println("address--->"+address);
				p.setAddress(address);
				
				p.setCountry("中国.台湾");
				
				list.add(p);
			}
			
			File input2 = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\tw_出入境/sol.html");
			Document doc2 = Jsoup.parse(input2, "UTF-8");
			Elements links2 = doc2.select("tr:gt(0)");
			for (Element element : links2) {
				System.out.println("person-------------------------------------");
				PersonTemp p = new PersonTemp();
				Elements idcard = element.select("td:eq(0)");
				System.out.println("idcard--->"+idcard.text().replaceAll("[ |　]", " ").trim());
				p.setIdcard(idcard.text().replaceAll("[ |　]", " ").trim());
				
				Elements name = element.select("td:eq(3)");
				System.out.println("name--->"+name.text().replaceAll("[ |　]", " ").trim());
				p.setName(name.text().replaceAll("[ |　]", " ").trim());
				
				Elements d1 = element.select("td:eq(6)");
				Elements d2 = element.select("td:eq(9)");
				String address = d1.text().replaceAll("[ |　]", " ").trim()+" "+d2.text().replaceAll("[ |　]", " ").trim();
				System.out.println("address--->"+address);
				p.setAddress(address);
				
				p.setCountry("中国.台湾");
				
				list.add(p);
			}
			
			File input3 = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\tw_出入境/nonsoliders.html");
			Document doc3 = Jsoup.parse(input3, "UTF-8");
			Elements links3 = doc3.select("tr:gt(0)");
			for (Element element : links3) {
				System.out.println("person-------------------------------------");
				PersonTemp p = new PersonTemp();
				Elements idcard = element.select("td:eq(0)");
				System.out.println("idcard--->"+idcard.text().replaceAll("[ |　]", " ").trim());
				p.setIdcard(idcard.text().replaceAll("[ |　]", " ").trim());
				
				Elements name = element.select("td:eq(3)");
				System.out.println("name--->"+name.text().replaceAll("[ |　]", " ").trim());
				p.setName(name.text().replaceAll("[ |　]", " ").trim());
				
				p.setCountry("中国.台湾");
				
				list.add(p);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * HK-CSL calldetail
	 * @return
	 */
	public static List<PhoneTemp> getCalldetail(){
		List<PhoneTemp> list = new ArrayList<PhoneTemp>();
		try {
			File file = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\CSL\\contactinfo.html");
			Document doc1 = Jsoup.parse(file, "UTF-8");
			Elements links1 = doc1.select("tr:eq(5)");
			String name = links1.select("td:eq(3)").text().replaceAll("[ |　]", " ").trim();
			String address = links1.select("td:eq(5)").text().replaceAll("[ |　]", " ").trim();
			String country = "中国.香港";
			System.out.println(name+"|"+address);
			
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			File input = new File("C:\\Users\\integrity\\Desktop\\date\\20160127_haitao\\CSL\\calldetail.html");
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements links = doc.select("tr:gt(0)");
			for (Element element : links) {
				System.out.println("PhoneTemp-------------------------------------");
				PhoneTemp p = new PhoneTemp();
				Elements phonenum = element.select("td:eq(1)");
				System.out.println("phonenum--->"+phonenum.text().replaceAll("[ |　]", " ").trim());
				p.setPhonenum(phonenum.text().replaceAll("[ |　]", " ").trim());
				
				Elements time1 = element.select("td:eq(6)");
				Elements time2 = element.select("td:eq(7)");
				String timeStr = time1.text().replaceAll("[ |　]", " ").trim()+" "+time2.text().replaceAll("[ |　]", " ").trim();
				long time = sf.parse(timeStr).getTime();
				SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(sf2.format(new Date(time)));
				p.setTime(time);
				
				Elements longTime = element.select("td:eq(9)");
				Long long1 = Long.parseLong(longTime.text().replaceAll("[ |　]", " ").trim())*60;
				System.out.println("longTime--->"+long1);
				p.setTimelong(long1);
				
				Elements to = element.select("td:eq(10)");
				System.out.println("to--->"+to.text().replaceAll("[ |　]", " ").trim());
				p.setTo(to.text().replaceAll("[ |　]", " ").trim());
				
				p.setName(name);
				p.setAddress(address);
				p.setCountry(country);
				
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
