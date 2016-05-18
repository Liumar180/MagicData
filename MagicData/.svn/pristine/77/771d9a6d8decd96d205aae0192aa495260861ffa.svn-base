package com.integrity.dataSmart.impAnalyImport.analyticalmail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.integrity.dataSmart.impAnalyImport.bean.Email;
import com.integrity.dataSmart.impAnalyImport.util.DateFormat;
import com.integrity.dataSmart.impAnalyImport.util.ReplaceUtil;
import com.integrity.dataSmart.particple.code.Participles;


/**
 * 解析eml格式文件
 * 
 * @author lifeng
 * 
 *         2015年7月6日 下午6:56:09
 */
public class AnalyticalEML {

	private MimeMessage message = null;
	@SuppressWarnings("unused")
	private String saveAttachPath = "";// 附件下载后的存放目录
	private StringBuffer bodytext = new StringBuffer();
	// 存放邮件内容的StringBuffer对象
	private String dateformat = "yyyy-MM-dd HH:mm:ss";// 默认的日前显示格式
	private String encode = "GBK";
	SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddhhmm");
	ReadFileUtil readFileUtil = new ReadFileUtil();
	private StringBuffer fileString=new StringBuffer();
	/**
	 * 初始化解析对象  
	 * @param emlpath eml路径
	 * @throws Exception
	 */
	public AnalyticalEML(String emlpath) throws Exception {
		InputStream fis = new FileInputStream(emlpath);
		Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
	    MimeMessage message = new MimeMessage(mailSession,fis);
		this.message = message;
	}

	/**
	 * 　*　获得发件人的地址和姓名 　
	 */
	public String getFrom1() throws Exception {
		InternetAddress address[] = (InternetAddress[]) message.getFrom();
		String from ="";
		if(address != null){
			from = mimeUtilityUtil(address[0].getAddress());
		}
		return from;
	}

	/**
	 * 　*　获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
	 * 　*　"to"----收件人　"cc"---抄送人地址　"bcc"---密送人地址 　
	 * 
	 * @throws Exception
	 */
	public String getMailAddress(String type) {
		String mailaddr = "";
		try {
			String addtype = type.toUpperCase();
			InternetAddress[] address = null;
			if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BBC")) {
				if (addtype.equals("TO")) {
					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
				} else if (addtype.equals("CC")) {
					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
				} else {
					address = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
				}
				if (address != null) {
					for (int i = 0; i < address.length; i++) {
						String email = address[i].getAddress();
						if (email == null)
							email = "";
						else {
							email = mimeUtilityUtil(email);
						}
						
						mailaddr += "," + email;
					}
					if(mailaddr.length()>=1){
						mailaddr = mailaddr.substring(1);
					}else {
						mailaddr ="";
					}
					
				}
			} 
			mailaddr=mimeUtilityUtil(mailaddr);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return mailaddr;
	}

	/**
	 * 　　*　获得邮件主题 　　
	 */
	public String getSubject() {
		String subject = "";
		try {
			
			if(message.getSubject()==null){
				subject = "";
			}else{
				subject = mimeUtilityUtil(message.getSubject());
				if (subject == null)
					subject = "";
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return subject;
	}

	/**
	 * 　　*　获得邮件发送日期 　　
	 */
	public String getSendDate() throws Exception {
		if(message.getSentDate()!=null){
			Date senddate = new Date(message.getHeader("Date")[0]);
			SimpleDateFormat format = new SimpleDateFormat(dateformat);
			return format.format(senddate);
		}
		return "";
	}
	
	 public static String changeCharset(String str, String newCharset){
		 if (str != null) {
			 //用默认字符编码解码字符串。
			 byte[] bs;
			try {
				bs = str.getBytes();
				return new String(bs, newCharset);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }
		 return "";
	 }

	/**
	 * 　　*　解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
	 * 　　*　主要是根据MimeType类型的不同执行不同的操作，一步一步的解析 　　
	 */
	public void getMailContent(Part part) throws Exception {
		String contenttype = part.getContentType();
		int nameindex = contenttype.indexOf("name");
		boolean conname = false;
		if (nameindex != -1)
			conname = true;
		if (part.isMimeType("text/html") && !conname) {
			bodytext=new StringBuffer();
			bodytext.append(htmlAnalytical(Jsoup.clean((String) part.getContent(), Whitelist.basic()))); 
			conname = true;
		}
		if (part.isMimeType("text/plain") && !conname && bodytext.length()<=0) {
			bodytext=new StringBuffer();
			bodytext.append((String) part.getContent());
		}
		try {
			if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int i = 0; i < counts; i++) {
					getMailContent(multipart.getBodyPart(i));
				}
			} 
			if (part.isMimeType("message/rfc822")) {
				getMailContent((Part) part.getContent());
			}  
		}catch(MessagingException e){
			System.err.println("邮件解析正文问题。");
			System.out.println("邮件解析正文问题。");
		}
	}

	/**
	 * 　*　获得邮件正文内容 　　
	 * @throws UnsupportedEncodingException 
	 */
	public String getBodyText() throws UnsupportedEncodingException {
		return mimeUtilityUtil(bodytext.toString());
	}

	/**
	 * 　　*　判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false" 　　
	 * 
	 * @throws MessagingException
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replysign = false;
		String needreply[] = message.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * 　*　获得此邮件的Message-ID 　　
	 * 
	 * @throws MessagingException
	 */
	public String getMessageId() throws MessagingException {
		return message.getMessageID()==null?"":message.getMessageID().trim();
	}

	/**
	 * 　*　【判断此邮件是否已读，如果未读返回返回false,反之返回true】 　　
	 * 
	 * @throws MessagingException
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) message).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}
		return isnew;
	}

	/**
	 * 　*　判断此邮件是否包含附件 　
	 * 
	 * @throws MessagingException
	 */
	public boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		//String contentType = part.getContentType();
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			// 获取附件名称可能包含多个附件
			for (int j = 0; j < mp.getCount(); j++) {
				BodyPart mpart = mp.getBodyPart(j);
				String disposition = mpart.getDescription();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					attachflag = true;
				} else if (mpart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) mpart);
				} else {
					String contype = mpart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1)
						attachflag = true;
					if (contype.toLowerCase().indexOf("name") != -1)
						attachflag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	/**
	 * 　*　【保存附件】 　
	 * 
	 * @throws Exception
	 * @throws IOException
	 * @throws MessagingException
	 * @throws Exception
	 */
	public ArrayList<String> saveAttachMent(Part part,String filepath) throws Exception {
		ArrayList<String> fileNameList = new ArrayList<String>();
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int j = 0; j < mp.getCount(); j++) {
				String fileName = "";
				BodyPart mpart = mp.getBodyPart(j);
				String disposition = mpart.getDescription();
				if ((disposition != null) && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mpart.getFileName();
					if (fileName.toLowerCase().indexOf(encode) != -1) {
						fileName = mimeUtilityUtil(fileName);
					}
					saveFile(fileName, mpart.getInputStream(), filepath);
					fileNameList.add(getAttachPath() + "/" + fileName);
				} else if (mpart.isMimeType("multipart/*")) {
					fileName = mpart.getFileName();
				} else {
					fileName = mpart.getFileName();
					if ((fileName != null)) {
						fileName = mimeUtilityUtil(fileName);
						saveFile(ReplaceUtil.fileNameReplace(fileName.replaceAll("	", "").replaceAll("/", "").replaceAll(":", "").replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "")), mpart.getInputStream(), filepath);
						fileNameList.add(mimeUtilityUtil(ReplaceUtil.fileNameReplace(fileName.replaceAll("	", "").replaceAll("/", "").replaceAll(":", "").replaceAll("<", "").replaceAll(">", "").replaceAll("\"", ""))));
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachMent((Part) part.getContent(), filepath);
		}
		
		return fileNameList;
	}

	/**
	 * 　*　【设置附件存放路径】 　
	 */
	public void setAttachPath(String attachpath) {
		this.saveAttachPath = attachpath;
	}

	/**
	 * 　*　【设置日期显示格式】 　
	 */
	public void setDateFormat(String format) {
		this.dateformat = format;
	}

	/**
	 * 　*　【获得附件存放路径】 　
	 */

	public String getAttachPath() {
		return null;
	}

	/**
	 * 　*　【真正的保存附件到指定目录里】 　
	 */
	private void saveFile(String fileName, InputStream in,String filepath) throws Exception {
		String storedir = filepath;
		String separator = "/";
		
		File file = new File(storedir + separator);  //判断文件夹是否存在,如果不存在则创建文件夹 
		if (!file.exists()) {   
			file.mkdirs(); // 新建目录
		}
		fileName=fileName.replaceAll("	", "").replaceAll("/", "").replaceAll(":", "").replaceAll("<", "").replaceAll(">", "").replaceAll("\"", "");
		File strorefile = new File(storedir + separator + fileName);
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(strorefile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		}catch(FileNotFoundException e1){
			System.out.println("没有找到相应的文件");
			System.err.println("没有找到相应的文件");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			if(bos!=null){
				bos.close();
			}
			if(bis!=null){
				bis.close();
			}
			
		}
	}
	 private static ArrayList<String>  getMailList(String[] s) 
	   { 
		 ArrayList<String> mailList=new ArrayList<String>();
	    Pattern p=Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");//邮箱的正则表达式 
	    Matcher m=p.matcher(s[0]); 
	    while(m.find()) 
	    { 
	    	try {
				mailList.add(mimeUtilityUtil(m.group()));
			} catch (Exception e) {
				e.printStackTrace(System.out);
			} 
	    } 
	    return mailList;
	   }  
	/**
	 * 解析eml，将附件存入本地
	 * @param attachfilepath 附件存入本地路径
	 * @return Email对象
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public  Email analyticaleml(String attachfilepath,String emlpath) throws Exception{
		Email email = new Email();
		/**其他头信息开始**/
		//限定服务器返回给客户端喜爱的自然语言
		ArrayList<String> acceptLanguageList=new ArrayList<String>();
		if(message.getHeader("Accept-Language")!=null){
			for (int i = 0; i < message.getHeader("Accept-Language").length; i++) {
				acceptLanguageList.add(mimeUtilityUtil(message.getHeader("Accept-Language")[i].toString()));
			}
		}
		email.setAcceptLanguage(acceptLanguageList); 
		//描述了实体面向用户的自然语言
		ArrayList<String> contentLanguageList=new ArrayList<String>();
		if(message.getHeader("Content-Language")!=null){
			for (int i = 0; i < message.getHeader("Content-Language").length; i++) {
				contentLanguageList.add(mimeUtilityUtil(message.getHeader("Content-Language")[i].toString()));
			}
		}
		email.setContentLanguage(contentLanguageList); 
		//邮件传输编码方式
		ArrayList<String> contentTransferEncodingList=new ArrayList<String>();
		if(message.getHeader("Content-Transfer-Encoding")!=null){
			for (int i = 0; i < message.getHeader("Content-Transfer-Encoding").length; i++) {
				contentTransferEncodingList.add(mimeUtilityUtil(message.getHeader("Content-Transfer-Encoding")[i].toString()));
			}
		}
		email.setContentTransferEncoding(contentTransferEncodingList); 
		//邮件主体的大小
		ArrayList<String> contentLengthList=new ArrayList<String>();
		if(message.getHeader("Content-Length")!=null){
			for (int i = 0; i < message.getHeader("Content-Length").length; i++) {
				contentLengthList.add(mimeUtilityUtil(message.getHeader("Content-Length")[i].toString()));
			}
		}
		email.setContentLength(contentLengthList); 
		//邮件媒体类型
		ArrayList<String> contenttypeList=new ArrayList<String>();
		if(message.getHeader("Content-type")!=null){
			for (int i = 0; i < message.getHeader("Content-type").length; i++) {
				contenttypeList.add(mimeUtilityUtil(message.getHeader("Content-type")[i].toString()));
			}
		}
		email.setContenttype(contenttypeList); 
		//发送地址（目标邮件服务器添加）
		ArrayList<String> deliveredToList=new ArrayList<String>();
		if(message.getHeader("Delivered-To")!=null){
			for (int i = 0; i < message.getHeader("Delivered-To").length; i++) {
				deliveredToList.add(mimeUtilityUtil(message.getHeader("Delivered-To")[i].toString()));
			}
		}
		email.setDeliveredTo(deliveredToList); 
		//发送日期（目标邮件服务器添加）
		ArrayList<String> deliverydateList=new ArrayList<String>();
		if(message.getHeader("Delivery-date")!=null){
			for (int i = 0; i < message.getHeader("Delivery-date").length; i++) {
				deliverydateList.add(mimeUtilityUtil(message.getHeader("Delivery-date")[i].toString()));
			}
		}
		email.setDeliverydate(deliverydateList); 
		
		//错误信息回复地址
		ArrayList<String> errorsToList=new ArrayList<String>();
		if(message.getHeader("Errors-To")!=null){
			for (int i = 0; i < message.getHeader("Errors-To").length; i++) {
				errorsToList.add(mimeUtilityUtil(message.getHeader("Errors-To")[i].toString()));
			}
		}
		email.setErrorsTo(errorsToList);
		//邮件重要等级
		ArrayList<String> importanceList=new ArrayList<String>();
		if(message.getHeader("Importance")!=null){
			for (int i = 0; i < message.getHeader("Importance").length; i++) {
				importanceList.add(mimeUtilityUtil(message.getHeader("Importance")[i].toString()));
			}
		}
		email.setImportance(importanceList); 
		//  。。。。。
		ArrayList<String> inReplyToList=new ArrayList<String>();
		if(message.getHeader("In-Reply-To")!=null){
			for (int i = 0; i < message.getHeader("In-Reply-To").length; i++) {
				inReplyToList.add(mimeUtilityUtil(message.getHeader("In-Reply-To")[i].toString()));
			}
		}
		email.setInReplyTo(inReplyToList); 
	//  邮件列表
		ArrayList<String> mailinglistList=new ArrayList<String>();
		if(message.getHeader("Mailing-list")!=null){
			for (int i = 0; i < message.getHeader("Mailing-list").length; i++) {
				mailinglistList.add(mimeUtilityUtil(message.getHeader("Mailing-list")[i].toString()));
			}
		}
		email.setMailinglist(mailinglistList); 
		//  。。。。。
		ArrayList<String> receivedSPFList=new ArrayList<String>();
		if(message.getHeader("Received-SPF")!=null){
			for (int i = 0; i < message.getHeader("Received-SPF").length; i++) {
				receivedSPFList.add(mimeUtilityUtil(message.getHeader("Received-SPF")[i].toString()));
			}
		}
		email.setReceivedSPF(receivedSPFList); 
	//  回复地址（邮件的创建者添加）
		ArrayList<String> replyToList=new ArrayList<String>();
		if(message.getHeader("Reply-To")!=null){
			for (int i = 0; i < message.getHeader("Reply-To").length; i++) {
				replyToList.add(mimeUtilityUtil(message.getHeader("Reply-To")[i].toString()));
			}
		}
		email.setReplyTo(replyToList); 
		// 回信的地址（目标邮件服务器）
		ArrayList<String> returnpathList=new ArrayList<String>();
		if(message.getHeader("Return-path")!=null){
			for (int i = 0; i < message.getHeader("Return-path").length; i++) {
				returnpathList.add(mimeUtilityUtil(message.getHeader("Return-path")[i].toString()));
			}
		}
		email.setReturnpath(returnpathList);
		//邮件的实际发送者
		ArrayList<String> senderList=new ArrayList<String>();
		if(message.getHeader("Sender")!=null){
			for (int i = 0; i < message.getHeader("Sender").length; i++) {
				senderList.add(mimeUtilityUtil(message.getHeader("Sender")[i].toString()));
			}
		}
		email.setSender(senderList);
		//.....
		ArrayList<String> threadIndexList=new ArrayList<String>();
		if(message.getHeader("Thread-Index")!=null){
			for (int i = 0; i < message.getHeader("Thread-Index").length; i++) {
				threadIndexList.add(mimeUtilityUtil(message.getHeader("Thread-Index")[i].toString()));
			}
		}
		email.setThreadIndex(threadIndexList);
		// .......
		ArrayList<String> threadTopicList=new ArrayList<String>();
		if(message.getHeader("Thread-Topic")!=null){
			for (int i = 0; i < message.getHeader("Thread-Topic").length; i++) {
				returnpathList.add(mimeUtilityUtil(message.getHeader("Thread-Topic")[i].toString()));
			}
		}
		email.setThreadTopic(threadTopicList);
		//发起请求的用户代理的信息
		ArrayList<String> userAgentList=new ArrayList<String>();
		if(message.getHeader("User-Agent")!=null){
			for (int i = 0; i < message.getHeader("User-Agent").length; i++) {
				userAgentList.add(mimeUtilityUtil(message.getHeader("User-Agent")[i].toString()));
			}
		}
		email.setUserAgent(userAgentList);
		//客户端
		ArrayList<String> xMailerList=new ArrayList<String>();
		if(message.getHeader("X-Mailer")!=null){
			for (int i = 0; i < message.getHeader("X-Mailer").length; i++) {
				returnpathList.add(mimeUtilityUtil(message.getHeader("X-Mailer")[i].toString()));
			}
		}
		email.setXMailer(xMailerList);
		//在Mailstore中的信息
		ArrayList<String> xMailStoreFolderUTF7List=new ArrayList<String>();
		if(message.getHeader("X-MailStore-Folder-UTF7")!=null){
			for (int i = 0; i < message.getHeader("X-MailStore-Folder-UTF7").length; i++) {
				xMailStoreFolderUTF7List.add(mimeUtilityUtil(message.getHeader("X-MailStore-Folder-UTF7")[i].toString()));
			}
		}
		email.setXMailStoreFolderUTF7(xMailStoreFolderUTF7List);
		ArrayList<String> xMailStoreHeaderHashList=new ArrayList<String>();
		if(message.getHeader("X-MailStore-Header-Hash")!=null){
			for (int i = 0; i < message.getHeader("X-MailStore-Header-Hash").length; i++) {
				xMailStoreHeaderHashList.add(mimeUtilityUtil(message.getHeader("X-MailStore-Header-Hash")[i].toString()));
			}
		}
		email.setXMailStoreHeaderHash(xMailStoreHeaderHashList);
		ArrayList<String> xMailStoreMessageIDList=new ArrayList<String>();
		if(message.getHeader("X-MailStore-Message-ID")!=null){
			for (int i = 0; i < message.getHeader("X-MailStore-Message-ID").length; i++) {
				xMailStoreMessageIDList.add(mimeUtilityUtil(message.getHeader("X-MailStore-Message-ID")[i].toString()));
			}
		}
		email.setXMailStoreMessageID(xMailStoreMessageIDList);
		ArrayList<String> xMailStoreDateList=new ArrayList<String>();
		if(message.getHeader("X-MailStore-Date")!=null){
			for (int i = 0; i < message.getHeader("X-MailStore-Date").length; i++) {
				xMailStoreDateList.add(mimeUtilityUtil(message.getHeader("X-MailStore-Date")[i].toString()));
			}
		}
		email.setXMailStoreDate(xMailStoreDateList);
		ArrayList<String> xMailStoreMessageID=new ArrayList<String>();
		if(message.getHeader("X-MailStore-Message-ID")!=null){
			for (int i = 0; i < message.getHeader("X-MailStore-Message-ID").length; i++) {
				xMailStoreMessageID.add(mimeUtilityUtil(message.getHeader("X-MailStore-Message-ID")[i].toString()));
			}
		}
		email.setXMailStoreMessageID(xMailStoreMessageID);
		ArrayList<String> xMailStoreFlagsList=new ArrayList<String>();
		if(message.getHeader("X-MailStore-Flags")!=null){
			for (int i = 0; i < message.getHeader("X-MailStore-Flags").length; i++) {
				xMailStoreFlagsList.add(mimeUtilityUtil(message.getHeader("X-MailStore-Flags")[i].toString()));
			}
		}
		email.setXMailStoreFlags(xMailStoreFlagsList);
		
		
		/**其他头信息结束**/
		
			//主题
			//email.setTitle(this.getSubject());
		if(message.getHeader("Subject")!=null){
			email.setTitle(mimeUtilityUtil(message.getHeader("Subject")[0]));
		}
			
			//System.out.println(email.getTitle());
			//发送时间
//			email.setSendtime(String.valueOf(sdf.parse(this.getSendDate()).getTime()));
		DateFormat dateFormat=new  DateFormat();
		if(message.getHeader("Date")!=null){
			email.setSendtime(mimeUtilityUtil(dateFormat.getDateBy1(String.valueOf(message.getHeader("Date")[0]))));
			email.setuTCDate(mimeUtilityUtil(dateFormat.solrDate1(new Date(String.valueOf(message.getHeader("Date")[0])))));
		}
			//email.setMseDate(sdf.parse(this.getSendDate()).getTime());
			//发件人
		if(message.getFrom()!=null){
			email.setFrom(mimeUtilityUtil(message.getFrom()[0].toString()));
		}
		if(this.getFrom1()!=null){
			email.setFromMail(mimeUtilityUtil(this.getFrom1()));
		}
			
			//收件人 
			ArrayList<String> tosArrayList=new ArrayList<String>();
			if(message.getHeader("To")!=null){
				for (int i = 0; i < message.getHeader("To").length; i++) {
					tosArrayList.add(mimeUtilityUtil(message.getHeader("To")[i].toString()));
				}
				email.setToMails(getMailList(message.getHeader("To")));
			}
			email.setTos(tosArrayList);
			//收件人邮箱s
//			ArrayList<String> toMailList=new ArrayList<String>();
//			for (int i = 0; i < this.getMailAddress("TO").split(",").length; i++) {
//				toMailList.add(this.getMailAddress("TO").split(",")[i]);
//			}
			
			ArrayList<String> CCArrayList=new ArrayList<String>();
			//抄送地址
			if(message.getHeader("Cc")!=null){
				for (int i = 0; i < message.getHeader("Cc").length; i++) {
					CCArrayList.add(mimeUtilityUtil(message.getHeader("Cc")[i].toString()));
				}
				email.setCopyToMails(getMailList(message.getHeader("Cc")));
			}
			
			email.setCopyTos(CCArrayList);
			//抄送邮箱s
//			ArrayList<String> ccMailList=new ArrayList<String>();
//				for (int i = 0; i < this.getMailAddress("CC").split(",").length; i++) {
//					ccMailList.add(this.getMailAddress("CC").split(",")[i]);
//				}
			
			//密送地址
			ArrayList<String> BCCArrayList=new ArrayList<String>();
			if(message.getHeader("Bcc")!=null){
				for (int i = 0; i < message.getHeader("Bcc").length; i++) {
					BCCArrayList.add(mimeUtilityUtil(message.getHeader("Bcc")[i].toString()));
				}
				email.setEncryptToMails(getMailList(message.getHeader("Bcc")));
			}
			email.setEncryptTos(BCCArrayList);
			//密送地址s
//			ArrayList<String> bccMailList=new ArrayList<String>();
//			for (int i = 0; i < this.getMailAddress("BCC").split(",").length; i++) {
//				bccMailList.add(this.getMailAddress("BCC").split(",")[i]);
//			}
			
			//System.out.println("密送地址 :" + email.getEncryptTos());
			//邮件正文
			this.getMailContent((Part) message); // 根据内容的不同解析邮件
			email.setContent(mimeUtilityUtil(this.getBodyText()));
			//email.setContent(this.getBodyText());
			//System.out.println("邮件正文 : \r" + email.getContent());
			
			 // 保存附件并返回文件名称集合
//			for (Address string : message.getRecipients(Message.RecipientType.CC)) {
//				System.out.println("  ccc      "+string);
//			}
			email.setAttachfilenames(this.saveAttachMent((Part) message,attachfilepath));
			
			ArrayList<String> localPathList=new ArrayList<String>();
			ArrayList<String> filecontent=new ArrayList<String>();
			String pEs[]={"exe","dll","vxd","sys","vdm"};
			for (String string : email.getAttachfilenames()) {
				
				localPathList.add(mimeUtilityUtil(attachfilepath+File.separator+string));
				//解析附件
				string =readFileUtil.getDocument(attachfilepath+File.separator+string);
				fileString.append(mimeUtilityUtil(string));
				filecontent.add(mimeUtilityUtil(string));
			}
			//保存附件本地路径
			email.setLocalPaths(localPathList);
			//保存附件内容
			email.setAttachfileContents(filecontent);
			//关键字     
			email.setKeywords(Participles.getKeyWordsList(email.getContent()+fileString.toString(), 10));
			//System.out.println("关键字 :  " + email.getKeywords());
			//file MD5
			ArrayList<String> fileMD5List=new ArrayList<String>();
			for (String fileName : localPathList) {
				fileMD5List.add(mimeUtilityUtil(DigestUtils.md5Hex(new FileInputStream(new File(fileName)))));
			}
			 email.setAttachfileMd5s(fileMD5List);

			//  message id
			String messageID ="";
			if( message.getHeader("Message-ID")!=null){
				for (int i = 0; i < message.getHeader("Message-ID").length; i++) {
					messageID+=mimeUtilityUtil(message.getHeader("Message-ID")[i])+" ";
				}
			}
			email.setMessageID(messageID.trim());
			//System.out.println(message.getHeader("Message-ID")[0].toString());
			
			//Received
			List<String> receivedList = new ArrayList<String>();
			String receivedString="";
			if( message.getHeader("Received")!=null){
				for (int i = 0; i < message.getHeader("Received").length; i++) {
					 receivedList.add(mimeUtilityUtil(message.getHeader("Received")[i])); 
				}
			}
			
			email.setReceiveds(receivedList);
			//System.out.println("是否包含附件 :" + this.isContainAttach((Part) message));
			//System.out.println(email.getReceiveds());
			email.setEmlPath(emlpath);
			return email;
	}
	public static String mimeUtilityUtil(String string){
		StringBuffer string1=new StringBuffer();
		try {
			int start = string.indexOf("=?");
			int end = string.indexOf("?=");
			if (start > -1 && end > -1 && start<end) {
				string1.append(string.substring(0, start));
				while(start > -1 && end > -1 && start<end){
					string=MimeUtility.decodeWord(string.substring(start, string.length()));
					 start = string.indexOf("=?");
					 end = string.indexOf("?=");
					 if (start > -1 && end > -1 && start<end) {
						 string1.append(string.substring(0, start));
					 }else{
						 string1.append(string);
					 }
				}
			}else{
				string1.append(string);
			}
		} catch (Exception e) {
			string1.append(string);
			System.out.println("乱码解析出错");
			e.printStackTrace(System.out);
		}
		return string1.toString();
	}
	public static String removeA(String string){
		int start=string.indexOf("<a href=");
		if(start>-1){
			String string2=string.substring(start,string.length()-1);
			int end=string2.indexOf(">")+1;
			
			string=string.substring(0,start)+string.substring(start+end,string.length()-1);
			int start1=string.indexOf("<a href=");
			if(start1>-1){
				string=removeA(string);
			}
		}
		return string;
	}
	public static String htmlAnalytical(String string){
		
		return removeA(string.replaceAll("&nbsp;", " ").replaceAll("<b>","").replaceAll("</b>", "").replaceAll("<em>","").replaceAll("</em>", "").replaceAll("<i>","").replaceAll("</i>", "").replaceAll("<strong>","").replaceAll("</strong>", "").replaceAll("<u>","").replaceAll("</u>", "")
				.replaceAll("<blockquote>","").replaceAll("</blockquote>", "").replaceAll("<br>","").replaceAll("</br>", "").replaceAll("<cite>","").replaceAll("</cite>", "").replaceAll("<dd>","").replaceAll("</dd>", "").replaceAll("<code>","").replaceAll("</code>", "").replaceAll("<dl>","").replaceAll("</dl>", "").replaceAll("<dt>","").replaceAll("</dt>", "")
				.replaceAll("<li>","").replaceAll("</li>", "").replaceAll("<ol>","").replaceAll("</ol>", "").replaceAll("<p>","").replaceAll("</p>", "").replaceAll("<pre>","").replaceAll("</pre>", "").replaceAll("<q>","").replaceAll("</q>", "").replaceAll("<small>","").replaceAll("</small>", "").replaceAll("<strike>","").replaceAll("</strike>", "")
				.replaceAll("<sub>","").replaceAll("</sub>", "").replaceAll("<sup>","").replaceAll("</sup>", "").replaceAll("<ul>","").replaceAll("</ul>", "").replaceAll("<a>","").replaceAll("</a>", "").replaceAll("<span>", "").replaceAll("</span>", ""));
	}
	
	public static String formatTime(Long ms) {  
	    Integer ss = 1000;  
	    Integer mi = ss * 60;  
	    Integer hh = mi * 60;  
	    Integer dd = hh * 24;  
	  
	    Long day = ms / dd;  
	    Long hour = (ms - day * dd) / hh;  
	    Long minute = (ms - day * dd - hour * hh) / mi;  
	    Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
	      
	    StringBuffer sb = new StringBuffer();  
	    if(second > 0) {  
	        sb.append(second+"秒");  
	    }  
	    return sb.toString();  
	}  

	/**
	 * 　*　PraseMimeMessage类测试 　
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			String path="C:\\Users\\flrhkd\\Desktop\\20151104\\导入问题2\\eml4.eml";
			AnalyticalEML ae = new AnalyticalEML(path);
			Email e = ae.analyticaleml("C:\\Users\\flrhkd\\Desktop\\aaaaa",path);
					 System.out.println(e);
					//System.out.println(mimeUtilityUtil(" =?utf-8?B?5Zue5aSNOiDmiJHmmK/mtYvor5Xpgq7ku7Y=?= =?utf-8?B?5Zue5aSNOiDmiJHmmK/mtYvor5Xpgq7ku7Y=?="));
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
	}
}