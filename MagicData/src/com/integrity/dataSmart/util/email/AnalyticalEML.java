package com.integrity.dataSmart.util.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.particple.code.Participles;
import com.integrity.dataSmart.titanGraph.pojo.Email;

/**
 * 解析eml格式文件
 * 
 * @author lifeng
 * 
 *         2015年7月6日 下午6:56:09
 */
public class AnalyticalEML {

	private MimeMessage mimeMessage = null;
	private String saveAttachPath = "";// 附件下载后的存放目录
	private StringBuffer bodytext = new StringBuffer();
	// 存放邮件内容的StringBuffer对象
	private String dateformat = DataType.DATEFORMATSTR;// 默认的日前显示格式
	private String encode = "GBK";
	/**
	 * 　*　构造函数,初始化一个MimeMessage对象 　
	 */
	public AnalyticalEML() {
		
	}

	public AnalyticalEML(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

	/**
	 * 　*　获得发件人的地址和姓名 　
	 */
	public String getFrom1() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		
		/*String personal = address[0].getPersonal();
		if (personal == null) {
			personal = "";
		}
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
		 */
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
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
				} else if (addtype.equals("CC")) {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
				} else {
					address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
				}
				if (address != null) {
					for (int i = 0; i < address.length; i++) {
						String email = address[i].getAddress();
						if (email == null)
							email = "";
						else {
							email = MimeUtility.decodeText(email);
						}
						
						/*String personal = address[i].getPersonal();
						if (personal == null)
							personal = "";
						else {
							personal = MimeUtility.decodeText(personal);
						}*/
						//String compositeto = personal + "<" + email + ">";
						
						mailaddr += "," + email;
					}
					mailaddr = mailaddr.substring(1);
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailaddr;
	}

	/**
	 * 　　*　获得邮件主题 　　
	 */
	public String getSubject() {
		String subject = "";
		try {
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null)
				subject = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subject;
	}

	/**
	 * 　　*　获得邮件发送日期 　　
	 */
	public String getSendDate() throws Exception {
		Date senddate = mimeMessage.getSentDate();
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(senddate);
	}
	
	 public String changeCharset(String str, String newCharset){
		 if (str != null) {
			 //用默认字符编码解码字符串。
			 byte[] bs = str.getBytes();
			 //用新的字符编码生成字符串
			 try {
				 return new String(bs, newCharset);
			 } catch (UnsupportedEncodingException e) {
				 e.printStackTrace();
			 }
		 }
		 return null;
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
		if (part.isMimeType("text/plain") && !conname) {
			bodytext.append((String) part.getContent());
		} else if (part.isMimeType("text/html") && !conname) {
			//bodytext.append((String) part.getContent()); // TODO
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				getMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			getMailContent((Part) part.getContent());
		}
	}

	/**
	 * 　*　获得邮件正文内容 　　
	 */
	public String getBodyText() {
		return bodytext.toString();
	}

	/**
	 * 　　*　判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false" 　　
	 * 
	 * @throws MessagingException
	 */
	public boolean getReplySign() throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");
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
		return mimeMessage.getMessageID();
	}

	/**
	 * 　*　【判断此邮件是否已读，如果未读返回返回false,反之返回true】 　　
	 * 
	 * @throws MessagingException
	 */
	public boolean isNew() throws MessagingException {
		boolean isnew = false;
		Flags flags = ((Message) mimeMessage).getFlags();
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
						fileName = MimeUtility.decodeText(fileName);
					}
					saveFile(fileName, mpart.getInputStream(), filepath);
					fileNameList.add(getAttachPath() + "/" + fileName);
				} else if (mpart.isMimeType("multipart/*")) {
					fileName = mpart.getFileName();
				} else {
					fileName = mpart.getFileName();
					if ((fileName != null)) {
						fileName = MimeUtility.decodeText(fileName);
						saveFile(fileName, mpart.getInputStream(), filepath);
						fileNameList.add(getAttachPath() + "/" + fileName);
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bos.close();
			bis.close();
		}
	}
	public Email analyticaleml(String emlpath,String attachfilepath) throws Exception{
		Email email = new Email();
		 InputStream fis = new FileInputStream(emlpath);
		 Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
	        MimeMessage message = new MimeMessage(mailSession,fis);
			AnalyticalEML pmm = new AnalyticalEML((MimeMessage) message);
			//主题
			email.setTitle(pmm.getSubject());
			//System.out.println("主题 :" + email.getTitle());
			//发送时间
			email.setSendtime(pmm.getSendDate());
			//System.out.println("发送时间 :" + email.getSendtime());
			//发件人
			email.setFrom(pmm.getFrom1());
			//System.out.println("发件人 :" + email.getFrom());
			//收件人 
			ArrayList<String> tosArrayList=new ArrayList<String>();
			for (int i = 0; i < pmm.getMailAddress("TO").split(",").length; i++) {
				tosArrayList.add(pmm.getMailAddress("TO").split(",")[i]);
			}
			email.setTos(tosArrayList);
			//System.out.println("收件人 :" + email.getTos());
			ArrayList<String> CCArrayList=new ArrayList<String>();
			//抄送地址
			for (int i = 0; i < pmm.getMailAddress("CC").split(",").length; i++) {
				CCArrayList.add(pmm.getMailAddress("CC").split(",")[i]);
			}
			email.setCopyTos(CCArrayList);
			//System.out.println("抄送地址 :" + email.getCopyTos());
			//密送地址
			ArrayList<String> BCCArrayList=new ArrayList<String>();
			for (int i = 0; i < pmm.getMailAddress("BCC").split(",").length; i++) {
				BCCArrayList.add(pmm.getMailAddress("BCC").split(",")[i]);
			}
			email.setEncryptTos(BCCArrayList);
			//System.out.println("密送地址 :" + email.getEncryptTos());
			 // 保存附件并返回文件名称集合
			email.setAttachfilenames(pmm.saveAttachMent((Part) message,attachfilepath));
			ArrayList<String> localPathList=new ArrayList<String>();
			for (String string : localPathList) {
				localPathList.add(attachfilepath+"\\"+string);
			}
			email.setLocalPaths(localPathList);
			//  message id
			String messageID ="";
			for (int i = 0; i < message.getHeader("Message-ID").length; i++) {
				messageID+=message.getHeader("Message-ID")[i]+" ";
			}
			email.setMessageID(messageID);
			//System.out.println(message.getHeader("Message-ID")[0].toString());
			//邮件正文
			pmm.getMailContent((Part) message); // 根据内容的不同解析邮件
			email.setContent(pmm.getBodyText());
			//关键字 
			email.setKeywords(new Participles().getKeyWordsList(pmm.getBodyText(), 10));
			//Received
			List<String> receivedList = new ArrayList<String>();
			String receivedString="";
			for (int i = 0; i < message.getHeader("Received").length; i++) {
				receivedString=message.getHeader("Received")[i];
				if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 有by 有 with 有 id 有 for
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("with", "#").replaceAll("id", "#").replaceAll("for", "#").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 没有by 有 with 有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("with", "##").replaceAll("id", "#").replaceAll("for", "#").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 没有by 没有 with 有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("id", "###").replaceAll("for", "#").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 没有by 有 with 没有 id 有 for
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("with", "##").replaceAll("for", "##").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 没有by 没有 with 没有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("for", "####").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 没有by 没有 with 没有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll(";", "#####");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 有by 没有 with 没有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("for", "###").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 有by 没有 with 没有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll(";", "####");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 没有by 有 with 没有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("with", "####").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 没有by 有 with 有 id 没有 for
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("with", "##").replaceAll("id", "#").replaceAll(";", "##");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 没有by 没有 with 有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("id", "####").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")<0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")<0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 没有by 没有 with 没有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("for", "####").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 有by 没有 with 有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("id", "##").replaceAll(";", "##");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 有by 没有 with 有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("id", "##").replaceAll("for", "#").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//有 from 有by 有 with 没有 id 有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("with", "#").replaceAll("for", "##").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 有by 有 with 没有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("with", "#").replaceAll(";", "###");
				}else if(message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf("by ")>0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")==0&&message.getHeader("Received")[i].indexOf(" by")>0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//有 from 有by 有 with 有 id 没有 for 
					 receivedString=receivedString.replaceAll("from", "#").replaceAll("by", "#").replaceAll("with", "#").replaceAll("id", "#").replaceAll(";", "##");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//没有 from 有by 有 with 有 id 有 for
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("with", "#").replaceAll("id", "#").replaceAll("for", "#").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//没有 from 有by 没有 with 有 id 有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("id", "##").replaceAll("for", "#").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//没有 from 有by 没有 with 没有 id 有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("for", "###").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//没有 from 有by 没有 with 没有 id 没有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll(";", "####");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")<0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")<0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//没有 from 有by 没有 with 有 id 没有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("id", "###").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//没有 from 有by 有 with 没有 id 没有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("with", "###").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")<0&&message.getHeader("Received")[i].indexOf("for ")>0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")<0&&message.getHeader("Received")[i].indexOf(" for")>0){
					//没有 from 有by 有 with 没有 id 有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("with", "#").replaceAll("for", "##").replaceAll(";", "#");
				}else if(message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf("by ")>=0&&message.getHeader("Received")[i].indexOf("with ")>0&&message.getHeader("Received")[i].indexOf("id ")>0&&message.getHeader("Received")[i].indexOf("for ")<0||message.getHeader("Received")[i].indexOf("from")<0&&message.getHeader("Received")[i].indexOf(" by")>=0&&message.getHeader("Received")[i].indexOf(" with")>0&&message.getHeader("Received")[i].indexOf(" id")>0&&message.getHeader("Received")[i].indexOf(" for")<0){
					//没有 from 有by 有 with 有 id 没有 for 
					 receivedString=receivedString.replaceAll("by", "##").replaceAll("with", "#").replaceAll("id", "#").replaceAll(";", "##");
				}
				 receivedList.add(receivedString); 
			}
			email.setReceiveds(receivedList);
			return email;
	}
	/**
	 * 　*　PraseMimeMessage类测试 　
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		AnalyticalEML analyticalEML=new AnalyticalEML();
		analyticalEML.analyticaleml("C:\\Users\\flrhkd\\Desktop\\email解析\\eml\\新建文件夹\\163\\test.eml","C:\\Users\\flrhkd\\Desktop\\email解析\\附件");
//		
////		String path="C:\\Users\\flrhkd\\Desktop\\email解析\\eml";
////		  File file=new File(path);
////		  File[] tempList = file.listFiles();
////		  System.out.println("该目录下对象个数："+tempList.length);
////		  for (int i = 0; i < tempList.length; i++) {
////		   if (tempList[i].isFile()) {
////			   System.out.println("****************************************第" 	+ (i + 1) + "封邮件**********************************");
////			InputStream fis = new FileInputStream(tempList[i].toString());
////	        //Object emlObj = (Object)fis;
////	        Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
////	        MimeMessage message = new MimeMessage(mailSession,fis);
////			AnalyticalEML pmm = new AnalyticalEML((MimeMessage) message);
////			
////			System.out.println("主题 :" + pmm.getSubject());
////			//pmm.setDateFormat(DataType.DATEFORMATSTR);
////			System.out.println("发送时间 :" + pmm.getSendDate());
////			System.out.println("是否回执 :" + pmm.getReplySign());
////			System.out.println("是否包含附件 :" + pmm.isContainAttach((Part) message));
////			System.out.println("发件人 :" + pmm.getFrom1());
////			System.out.println("收件人 :" + pmm.getMailAddress("TO"));
////			
////			System.out.println("抄送地址 :" + pmm.getMailAddress("CC"));
////			System.out.println("密送地址 :" + pmm.getMailAddress("BCC"));
////			System.out.println("邮件ID :" + pmm.getMessageId());
////			pmm.getMailContent((Part) message); // 根据内容的不同解析邮件
////			//pmm.setAttachPath(pmm.getSubject() + EmailTools.formatTimeStampByDateFormat(new Timestamp(System.currentTimeMillis()), "yyyyMMddHHmmss")); // 设置邮件附件的保存路径
////			String filepathString="C:\\Users\\flrhkd\\Desktop\\email解析\\附件"; // 设置邮件附件的保存路径
////			List<String> fileNameList = pmm.saveAttachMent((Part) message,filepathString); // 保存附件并返回文件名称集合
////			System.out.println("文件名称 : " + fileNameList);
////			System.out.println("邮件正文 : \r" + pmm.getBodyText());
////			 String html = pmm.getBodyText();
////			 Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
////			String text = new AnalyticalHTML().getHtmlText(doc); // "An example link"//取得字符串中的文本
////			 System.out.println("邮件正文内容抽出 :  "+text);
////			 System.out.println("*********************************第" + (i + 1) + "封邮件结束*************************************");
////		   }
////		  }
//		  InputStream fis = new FileInputStream("C:\\Users\\flrhkd\\Desktop\\email解析\\eml\\新建文件夹\\hotmail\\3BD65009-00000007.eml");
//		  // InputStream fis = new FileInputStream("C:\\Users\\flrhkd\\Desktop\\email解析\\emls\\message-36-10312464 - 副本.eml");
//		  // InputStream fis = new FileInputStream("C:\\Users\\flrhkd\\Desktop\\email解析\\eml\\新建文件夹\\回复：test.eml");
//	        //Object emlObj = (Object)fis;
//	        Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
//	        MimeMessage message = new MimeMessage(mailSession,fis);
//			AnalyticalEML pmm = new AnalyticalEML((MimeMessage) message);
//			
//			System.out.println("主题 :" + pmm.getSubject());
//			//pmm.setDateFormat(DataType.DATEFORMATSTR);
//			//String[] headers={"Received","X-QQ-FEAT","X-QQ-SSF","X-HAS-ATTACH","X-QQ-BUSINESS-ORIGIN","X-Originating-IP","X-QQ-STYLE","X-QQ-mid","Mime-Version","Subject","Content-Transfer-Encoding","Date","Message-IDX-QQ-MIME","X-Mailer","X-QQ-Mailer","X-CM-TRANSID","Authentication-Results","X-Coremail-Antispam"};
//			String[] headers={"Received","Received-SPF","From","To","CC","Subject","Thread-Topic","Thread-Index","Date","Message-ID","Accept-Language","Content-Language","X-MS-Has-Attach","authentication-results","x-originating-ip","x-microsoft-antispam", "Content-Type","Return-Path","MIME-Version","Content-Transfer-Encoding"};
//			for (int j = 0; j < headers.length; j++) {
//				if(message.getHeader(headers[j])!=null){
//				for (int i = 0; i < message.getHeader(headers[j]).length; i++) {
//					 System.out.println(headers[j]+":  "+message.getHeader(headers[j])[i]);
//				}
//				}
//			}
//			
////			System.out.println("发送时间 :" + pmm.getSendDate());
////			System.out.println("是否回执 :" + pmm.getReplySign());
////			System.out.println("是否包含附件 :" + pmm.isContainAttach((Part) message));
////			System.out.println("发件人 :" + pmm.getFrom1());
////			System.out.println("收件人 :" + pmm.getMailAddress("TO"));
////			
////			System.out.println("抄送地址 :" + pmm.getMailAddress("CC"));
////			System.out.println("密送地址 :" + pmm.getMailAddress("BCC"));
////			System.out.println("邮件ID :" + pmm.getMessageId());
//			pmm.getMailContent((Part) message); // 根据内容的不同解析邮件
//			//pmm.setAttachPath(pmm.getSubject() + EmailTools.formatTimeStampByDateFormat(new Timestamp(System.currentTimeMillis()), "yyyyMMddHHmmss")); // 设置邮件附件的保存路径
//			String filepathString="C:\\Users\\flrhkd\\Desktop\\email解析\\附件"; // 设置邮件附件的保存路径
//			List<String> fileNameList = pmm.saveAttachMent((Part) message,filepathString); // 保存附件并返回文件名称集合
////			System.out.println("文件名称 : " + fileNameList);
//		System.out.println("邮件正文 : \r" + pmm.getBodyText());
//			//System.out.println(pmm.getBodyText().split("<hr").length);
////			 String html = pmm.getBodyText();
////			 Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现	
////			String text = new AnalyticalHTML().getHtmlText(doc); // "An example link"//取得字符串中的文本
////			 System.out.println("邮件正文内容抽出 :  "+text);
//			 
//			//String [] aStrings =html.split("发件人:");
//			// System.out.println("邮件正文内容抽出 :  "+text.split("发件人:").length);
////			 if(text.indexOf("From:")>=0&&text.indexOf("发件人:")<0){
////				 //只有"From"时
////				 for (int i = text.split("From:").length/2+text.split("From:").length%2; i <= text.split("From:").length-1; i++) {
////					 if(text.split("From:")[i].indexOf("Sent:")>0 && text.split("From:")[i].indexOf("To:")>0){
////						 System.out.println("--------------------------");
////						 System.out.println("From:"+text.split("From:")[i]);
////					 }
////					 
////				}
////			 }else if(text.indexOf("发件人:")>=0&&text.indexOf("From:")<0){
////				//只有"发件人"时
////				 for (int i = text.split("发件人:").length/2+text.split("发件人:").length%2; i <= text.split("发件人:").length-1; i++) {
////					 
////					 if(text.split("发件人:")[i].indexOf("收件人:")>0 && text.split("发件人:")[i].indexOf("发送时间:")>0){
////						 System.out.println("--------------------------");
////						 System.out.println("发件人:"+text.split("发件人:")[i]);
////					 } 
////				 
////				 }
////			 }else if(text.indexOf("发件人:")>=0&&text.indexOf("From:")>=0){
////				//两个都有的时候
////				 System.out.println(text.split("发件人:").length);
////				 for (int i = text.split("发件人:").length/2+text.split("发件人:").length%2; i <= text.split("发件人:").length-1; i++) {
////					 System.out.println("发件人:"+text.split("发件人:")[i]);
////				}
////			 }
//			 
//			 
////			 System.out.println("Message-ID:  "+message.getMessageID());
////			 System.out.println("AllHeaderLines :  "+message.getAllHeaderLines().nextElement());
////			 System.out.println("AllHeaders:  "+message.getAllHeaders().nextElement());
////			 System.out.println("AllRecipients:  "+message.getAllRecipients()[1]);
			 
	}
}