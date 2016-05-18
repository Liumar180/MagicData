package com.integrity.dataSmart.menu.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;








import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.MenuKey;
import com.integrity.dataSmart.menu.pojo.MenuPojo;

public class MenuManagerService {
	
	public List<MenuPojo> getMenuList(String dataPath, String type){
		
		List<MenuPojo> resultList = new ArrayList<MenuPojo>();
		
		Properties ipLLProperties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(dataPath+File.separator+MenuKey.MENU_COFIG_PATH);
			ipLLProperties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(null!=ipLLProperties&&ipLLProperties.size()>0){
			String lvOneStr = ipLLProperties.getProperty(type.toUpperCase() + "_MENU_LV1");
			if(null!=lvOneStr&&!"".equals(lvOneStr)&&(lvOneStr.trim()).length()>0){
				String[] tempStrs = (lvOneStr.trim()).split(",");
				for(int i = 0;i<tempStrs.length;i++){
					MenuPojo mp = new MenuPojo();
					String tempIco = ipLLProperties.getProperty(tempStrs[i]+MenuKey.MENU_ICOCLASS_KEY);
					if(null!=tempIco&&!"".equals(tempIco)&&(tempIco.trim()).length()>0){
						mp.setIcoClassString(tempIco.trim());
						String tempHref = ipLLProperties.getProperty(tempStrs[i]+MenuKey.MENU_HREF_KEY);
						if(null==tempHref){
							tempHref = "";
						}
						mp.setHrefString(tempHref);
						String lvTwoStr = ipLLProperties.getProperty(tempStrs[i]+MenuKey.LV_TWO_KEY);
						if(null!=lvTwoStr&&!"".equals(lvTwoStr)&&(lvTwoStr.trim()).length()>0){
							mp.setHasSubFlag(true);
							List<MenuPojo> subList = new ArrayList<MenuPojo>();
							String[] tempSubStrs = (lvTwoStr.trim()).split(",");
							int startAngle = i*(360/tempStrs.length)-43*(((tempSubStrs.length+1)/2)-1);//tempSubStrs.length/2
							for(int j = 0;j<tempSubStrs.length;j++){
								MenuPojo subMp = new MenuPojo();
								String tempSubIco = ipLLProperties.getProperty(tempSubStrs[j]+MenuKey.MENU_ICOCLASS_KEY);
								if(null!=tempSubIco&&!"".equals(tempSubIco)&&(tempSubIco.trim()).length()>0){
									subMp.setIcoClassString(tempSubIco.trim());
									String tempSubHref = ipLLProperties.getProperty(tempSubStrs[j]+MenuKey.MENU_HREF_KEY);
									if(null==tempSubHref){
										tempSubHref = "";
									}
									subMp.setHrefString(tempSubHref);
									String lvThreeStr = ipLLProperties.getProperty(tempSubStrs[j]+MenuKey.LV_THREE_KEY);
									if(null!=lvThreeStr&&!"".equals(lvThreeStr)&&(lvThreeStr.trim()).length()>0){
										subMp.setHasSubFlag(true);
										List<MenuPojo> sSubList = new ArrayList<MenuPojo>();
										String[] tempSSubStrs = (lvThreeStr.trim()).split(",");
										//43*j/2+10;
										int subStartAngle = startAngle+43*j/2+10;//43*j-19*(((tempSSubStrs.length+1)/2)-1);//tempSSubStrs.length/2
										for(int x = 0;x<tempSSubStrs.length;x++){
											MenuPojo sSubMp = new MenuPojo();
											String tempSSubIco = ipLLProperties.getProperty(tempSSubStrs[x]+MenuKey.MENU_ICOCLASS_KEY);
											if(null!=tempSSubIco&&!"".equals(tempSSubIco)&&(tempSSubIco.trim()).length()>0){
												sSubMp.setIcoClassString(tempSSubIco.trim());
												String tempSSubHref = ipLLProperties.getProperty(tempSSubStrs[x]+MenuKey.MENU_HREF_KEY);
												if(null==tempSSubHref){
													tempSSubHref = "";
												}
												sSubMp.setHrefString(tempSSubHref);
											}
											sSubMp.setHasSubFlag(false);
											sSubList.add(sSubMp);
										}
										int subEndAngle = subStartAngle+19*tempSSubStrs.length;
										//此处的17决定的元素之间的间距,等于subWheelmenus.js中fadeInSubIcon方法的单个元素旋转角度
										subMp.setAngleString("["+subStartAngle+","+subEndAngle+"]");
										subMp.setSubMenuPojos(sSubList);
									}else{
										subMp.setHasSubFlag(false);
									}
								}
								subList.add(subMp);
							}
							int endAngle = startAngle+43*tempSubStrs.length;//27
							//此处的23决定的元素之间的间距,等于subWheelmenus.js中fadeInSubIcon方法的单个元素旋转角度
							mp.setAngleString("["+startAngle+","+endAngle+"]");
							mp.setSubMenuPojos(subList);
						}else{
							mp.setHasSubFlag(false);
						}
					}
					resultList.add(mp);			
				}
			}
		}
		return resultList;
	}
	
	
	public List<String> getImpNames(String dataPath){
		List<String> nameList = new ArrayList<String>();
		int rootIndex = dataPath.indexOf("MagicData");
		String rootPath = dataPath.substring(0, rootIndex)+"MagicData"+File.separator+"images"+File.separator+"menu";
		File rootDir = new File(rootPath);
		if(rootDir.exists()&&rootDir.isDirectory()){
			File[] subFiles = rootDir.listFiles();
			if(null!=subFiles){
				for(int i=0;i<subFiles.length;i++){
					if(null!=subFiles[i]&&subFiles[i].exists()&&subFiles[i].isFile()){
						String tempName = subFiles[i].getName();
						nameList.add(tempName);
					}
				}
			}
		}
		return nameList;
	}
}
