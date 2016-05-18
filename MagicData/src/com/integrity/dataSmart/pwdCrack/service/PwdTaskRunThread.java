package com.integrity.dataSmart.pwdCrack.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.integrity.dataSmart.pwdCrack.bean.PwdTask;
import com.integrity.dataSmart.pwdCrack.dao.PwdTaskDao;

public class PwdTaskRunThread implements Runnable {

	private Logger logger = Logger.getLogger(PwdTaskRunThread.class);
	/**任务ID*/
	private String ptId;
	
	/**破解启动的线程数*/
	private String threadCount;
	
	/**彩虹表保存路径*/
	private String rainbowTablePath;
	
	/**Windows彩虹表工具目录*/
	private String rcrackiMtPathWin;
	
	/**服务器类型windows或linux*/
	private String systemType;
	
	private PwdTaskDao pwdTaskDao;
	
	public PwdTaskRunThread(String ptId, String threadCount,
			String rainbowTablePath, String rcrackiMtPathWin,
			String systemType,PwdTaskDao pwdTaskDao) {
		super();
		this.ptId = ptId;
		this.threadCount = threadCount;
		this.rainbowTablePath = rainbowTablePath;
		this.rcrackiMtPathWin = rcrackiMtPathWin;
		this.systemType = systemType;
		this.pwdTaskDao = pwdTaskDao;
	}

	@Override
	public void run() {
		PwdTask pt = pwdTaskDao.findById(ptId);
		if(null!=pt){
			// 1.计算进度保存到任务中2 如果出现错误需要该状态为执行失败3执行完成改状态为成功
			//runas /user:administrator cmd /k start 
			//C:\\Users\\Xashura\\Desktop\\cmd.lnk
			if(PwdRunTaskService.WINDOWS_SYSTEM_FLAG.equals(systemType)){
				String panStr = "D:";
			    String tempFileName = panStr+File.separator+pt.getUuid()+"_"+pt.getUserName()+"_"+pt.getPwdEncrypt();
				File tempBatFile = new File(tempFileName+".bat");//批处理文件
			    File tempLogFile = new File(tempFileName+".txt");//命令日志文件
			    BufferedReader read = null;
				 try {
					    if(!tempBatFile.exists()){
					    	tempBatFile.createNewFile();
					    }
					    if(!tempLogFile.exists()){
					    	tempLogFile.createNewFile();
					    }
					    /*cd C:\Program Files (x86)\rcracki_mt_0.7.0_win32_vc 
				        C: 
				        rcracki_mt.exe -h  1231 -t 4 D:\rainbowTables >  D:\222_222_1231.txt 
				        exit*/
					    String[] cmdStrs = new String[4];
				        cmdStrs[0] = "cd "+ rcrackiMtPathWin+" \r\n";//文件放在c盘x86用批处理来运行
				        cmdStrs[1] = "C: \r\n";
				        cmdStrs[2] = "rcracki_mt.exe -h  "+pt.getPwdEncrypt().trim()+" -t "+this.threadCount
				        		                 +" "+this.rainbowTablePath + " >  "+ tempLogFile.getAbsolutePath()+" \r\n";
				        cmdStrs[3] = "exit";
				        FileWriter fw = new FileWriter(tempBatFile);
				        for(String tempStr:cmdStrs){
				        	fw.write(tempStr);
				        }
				        fw.flush();
				        fw.close();
					    //cmd /c dir 是执行完dir命令后关闭命令窗口。 cmd /k dir 是执行完dir命令后不关闭命令窗口
					    //cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。 cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭
				        Process pro = Runtime.getRuntime().exec("cmd.exe /c start "+tempBatFile.getAbsolutePath());
				        String pwdResult = ""; 
				        FileReader  fread = new FileReader(tempLogFile);
				        read = new BufferedReader(fread);
				        boolean readyFlag = false;
				        boolean finishFlag = false;
				        do{
				        	readyFlag = read.ready();
				        }while(!readyFlag);
				        char[] tempChar = new char[1];
				        long timeCount = System.currentTimeMillis();
				        boolean finFlag = false;
				        if(read.ready()){
				        	read.read(tempChar);
				        	String result = tempChar[0]+"";
				        	Integer rainTableCount = 0;
				            Integer countInt = 0;
				            Integer pecentCount = 0;
				            String tmpResultStrI = "";
				            String tmpResultStrII = "";
				            boolean lvFlag = false;
				            while(result!=null){  
				            	if((result.equals("\r")&&!lvFlag)||(result.equals("\n")&&!lvFlag)){
				            		lvFlag = true;
				            		tmpResultStrII = tmpResultStrI;
				            		tmpResultStrI = "";
				            		//System.out.println("INFO:"+tmpResultStrII); 
				            		if("no rainbow table found".equals(tmpResultStrII.trim())){
				            			Thread.currentThread().sleep(5000);
				            			throw new RuntimeException("password crack no rainbow table found");
				            		}
				            		if(tmpResultStrII.indexOf("invalid hash")!=-1){
				            			Thread.currentThread().sleep(5000);
				            			throw new RuntimeException("password crack no hashes found");
				            		}
				            		logger.info("INFO:"+tmpResultStrII);
					                if(rainTableCount==0){
					                	rainTableCount =  writeRunPecent(tmpResultStrII);
					                }else{
					                	if(9==writeRunPecent(tmpResultStrII)){
					                		pecentCount = 90;
					                		finFlag = true;
					                		pt.setRunPercent(pecentCount);
					                		pwdTaskDao.updatePwdTask(pt);
					                	}else if(1==writeRunPecent(tmpResultStrII)){
					                		countInt += writeRunPecent(tmpResultStrII);
					                		if(countInt>0){
					                			pecentCount = (countInt*100)/(rainTableCount*2);
					                			pt.setRunPercent(pecentCount);
						                		pwdTaskDao.updatePwdTask(pt);
					                		}
					                	}
					                }
					                if(finFlag){
					                	int pwdEIndex = tmpResultStrII.indexOf(pt.getPwdEncrypt());
					                	if(pwdEIndex!=-1){
					                		int hexIndex = tmpResultStrII.indexOf("hex");
					                		String tempStr = tmpResultStrII.substring(pt.getPwdEncrypt().length(),hexIndex);
					                		tempStr = tempStr.replaceAll("<", "");
					                		tempStr = tempStr.replaceAll(">", "");
					                		pwdResult = tempStr.trim();
					                		finishFlag = true;
					                	}
					                }
				            	}else{
				            		lvFlag = false;
				            		if(!result.equals("\r")&&!result.equals("\n")){
				            			tmpResultStrI = tmpResultStrI+result;
				            		}
				            	}
				                if(!finishFlag){
				                	readyFlag = read.ready();
							        while(!readyFlag){
							        	readyFlag = read.ready();
							            long currTime = System.currentTimeMillis();
							        	if((currTime-timeCount)>5*60*1000){
							        		throw new RuntimeException("password crack time out");
							        	}
							        }
							        read.read(tempChar);
					                result = tempChar[0]+"";
				                }else{
				                	break;
				                }
				            }
				        } 
				        read.close();
				        if(finFlag){
				        	pt.setRunStatus(PwdTaskService.RUNFINISH);
				            pt.setRunPercent(100);
				            pt.setPwdCrack(pwdResult);
				        	pwdTaskDao.updatePwdTask(pt);
				        }else{
			            	pt.setRunStatus(PwdTaskService.RUNERROR);
				        	pt.setPwdCrack("");
				        	pwdTaskDao.updatePwdTask(pt);
			            }
			        	Thread.currentThread().sleep(1000);
			        	tempBatFile.delete();
				        tempLogFile.delete();
				    }catch(Exception e){
				    	pt.setRunStatus(PwdTaskService.RUNERROR);
				    	pt.setPwdCrack("");
			        	pwdTaskDao.updatePwdTask(pt);
			        	if(null!=read){
			        		try {
								read.close();
							} catch (IOException e1) {
								logger.error(e);
								e1.printStackTrace();
							}
			        	}
			        	if(tempBatFile.exists()){
			        		tempBatFile.delete();
					    }
					    if(tempLogFile.exists()){
					    	tempLogFile.delete();
					    }  
					    logger.error(e);
				        e.printStackTrace();
				    }
			}else if(PwdRunTaskService.LINUX_SYSTEM_FLAG.equals(systemType)){
				InputStream linuxIn = null;  
				String[] orderStr = new String[3];
				orderStr[0] = "/bin/sh";
				orderStr[1] = "-c";
				orderStr[2] = "rcracki  -h "+pt.getPwdEncrypt().trim()+" -t "+this.threadCount+" "+this.rainbowTablePath;
		        try {  
		            Process pro = Runtime.getRuntime().exec(orderStr);  
		            String pwdResult = ""; 
		            linuxIn = pro.getInputStream();  
		            BufferedReader read = new BufferedReader(new InputStreamReader(linuxIn));
		            String result = read.readLine();
		            Integer rainTableCount = 0;
		            Integer countInt = 0;
		            Integer pecentCount = 0;
		            boolean finFlag = false;
		            while(result!=null){  
		            	logger.info("INFO:"+result);
		            	System.out.println(result);
		                result = read.readLine();
		                if("no rainbow table found".equals(result.trim())){
	            			throw new RuntimeException("password crack no rainbow table found");
	            		}
		                if(result.indexOf("invalid hash")!=-1){
	            			throw new RuntimeException("password crack no hashes found");
	            		}
		                if(rainTableCount==0){
		                	rainTableCount =  writeRunPecent(result);
		                }else{
		                	if(9==writeRunPecent(result)){
		                		pecentCount = 90;
		                		finFlag = true;
		                		pt.setRunPercent(pecentCount);
		                		pwdTaskDao.updatePwdTask(pt);
		                	}else if(1==writeRunPecent(result)){
		                		countInt += writeRunPecent(result);
		                		if(countInt>0){
		                			pecentCount = (countInt*100)/(rainTableCount*2);
		                			pt.setRunPercent(pecentCount);
			                		pwdTaskDao.updatePwdTask(pt);
		                		}
		                	}
		                }
		                if(finFlag){
		                	int pwdEIndex = result.indexOf(pt.getPwdEncrypt());
		                	if(pwdEIndex!=-1){
		                		int hexIndex = result.indexOf("hex");
		                		String tempStr = result.substring(pt.getPwdEncrypt().length(),hexIndex);
		                		tempStr = tempStr.replaceAll("<", "");
		                		tempStr = tempStr.replaceAll(">", "");
		                		pwdResult = tempStr.trim();
		                		break;
		                	}
		                }
		                result = read.readLine();
		            }
		            if(finFlag){
		            	pt.setRunStatus(PwdTaskService.RUNFINISH);
			            pt.setRunPercent(100);
			            pt.setPwdCrack(pwdResult);
			        	pwdTaskDao.updatePwdTask(pt);
		            }else{
		            	pt.setRunStatus(PwdTaskService.RUNERROR);
			        	pt.setPwdCrack("");
			        	pwdTaskDao.updatePwdTask(pt);
		            }
		        } catch (Exception e) {  
		        	pt.setRunStatus(PwdTaskService.RUNERROR);
		        	pt.setPwdCrack("");
		        	pwdTaskDao.updatePwdTask(pt);
		        	logger.error(e);
		            e.printStackTrace();  
		        }  
			}
		}
	}
	
	private Integer writeRunPecent(String resultStr){
		if(null!=resultStr&&!"".equals(resultStr.trim())){
			int rainTIndex = resultStr.indexOf("rainbowtable files");
			if(rainTIndex!=-1){
				String countStr = resultStr.substring(rainTIndex-3, rainTIndex);
				if(null!=countStr){
					return Integer.parseInt(countStr.trim());
				}
			}else if(resultStr.indexOf(".rti")!=-1){
				return 1;
			}else if(resultStr.indexOf("cryptanalysis time")!=-1&&resultStr.indexOf("total")==-1){
				//"cryptanalysis time"非"total cryptanalysis time:"
				return 1;
			}else if(resultStr.indexOf("result")!=-1){
				return 9;
			}
		}
		return 0;
	}
}
