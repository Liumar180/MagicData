package com.integrity.dataSmart.impAnalyImport.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class Email {
	
	@Field("id")
	private String id;
	
	@Field("type")
	private String type;
	
	@Field("title")
	private String title;//主题
	
	@Field("content")
	private String content;//正文
	
	@Field("sendtime")
	private String sendtime;//发送时间
	
	@Field("from")
	private String from;//发件人
	
	private String fromMail;//发件人邮箱
	
	@Field("tos")
	private ArrayList<String> tos;//收件人
	
	private ArrayList<String> toMails;//收件人邮箱
	
	@Field("copyTos")
	private ArrayList<String> copyTos;//抄送
	
	private ArrayList<String> copyToMails;//抄送邮箱
	
	@Field("encryptTos")
	private ArrayList<String> encryptTos;//密送
	
	private ArrayList<String> encryptToMails;//密送邮箱
	
	@Field("attachfiles")
	private ArrayList<String> attachfilenames;//附件名称
	
	@Field("attachContents")
	private ArrayList<String> attachfileContents;//附件内容
	
	@Field("attachfileMd5s")
	private ArrayList<String> attachfileMd5s;//附件md5值
	
	@Field("vertexID")
	private String vertexID;//节点ID
	
	@Field("messageID")
	private String messageID;//messageID
	
	@Field("keywords")
	private List<String> keywords;//关键词
	
	@Field("showLabel")
	private List<String> showLabel;//显式标签 
	
	private List<String> localPaths;//本地附件存储路径
	
	@Field("emlPath")
	private String emlPath;//eml自身目录
	
	
	@Field("receiveds")
	private List<String> receiveds;//邮件轨迹  按顺序使用#隔开 顺序为from by with id for time   一共6个  
	@Field("acceptLanguage")
	private List<String> acceptLanguage;//限定服务器返回给客户端喜爱的自然语言
	@Field("contentLanguage")
	private List<String> contentLanguage;//描述了实体面向用户的自然语言
	@Field("contentLength")
	private List<String> contentLength;//邮件主体的大小
	@Field("contentTransferEncoding")
	private List<String> contentTransferEncoding;//邮件传输编码方式
	@Field("contenttype")
	private List<String> contenttype;//邮件媒体类型
	@Field("deliveredTo")
	private List<String> deliveredTo;//发送地址（目标邮件服务器添加）
	@Field("deliverydate")
	private List<String> deliverydate;//发送日期（目标邮件服务器添加）
	@Field("errorsTo")
	private List<String> errorsTo;//错误信息回复地址
	@Field("importance")
	private List<String> importance;//邮件重要等级
	@Field("inReplyTo")
	private List<String> inReplyTo;//
	@Field("mailinglist")
	private List<String> mailinglist;//邮件列表
	@Field("receivedSPF")
	private List<String> receivedSPF;//本地附件存储路径
	@Field("replyTo")
	private List<String> replyTo;//回复地址（邮件的创建者添加）
	@Field("returnpath")
	private List<String> returnpath;//回信的地址（目标邮件服务器）
	@Field("sender")
	private List<String> sender;//邮件的实际发送者
	@Field("threadIndex")
	private List<String> threadIndex;//本地附件存储路径
	@Field("threadTopic")
	private List<String> threadTopic;//本地附件存储路径
	@Field("userAgent")
	private List<String> userAgent;//发起请求的用户代理的信息
	@Field("xMailer")
	private List<String> xMailer;//客户端
	@Field("xMailStoreFolderUTF7")
	private List<String> xMailStoreFolderUTF7;//在Mailstore中的信息
	@Field("xMailStoreHeaderHash")
	private List<String> xMailStoreHeaderHash;//在Mailstore中的信息
	@Field("xMailStoreMessageID")
	private List<String> xMailStoreMessageID;//在Mailstore中的信息
	@Field("xMailStoreDate")
	private List<String> xMailStoreDate;//在Mailstore中的信息
	@Field("xMailStoreFlags")
	private List<String> xMailStoreFlags;//在Mailstore中的信息
	@Field("mseDate")
	private String uTCDate;//发送时间毫秒
	public Email() {}


	public List<String> getxMailer() {
		return xMailer;
	}


	public void setxMailer(List<String> xMailer) {
		this.xMailer = xMailer;
	}


	public List<String> getxMailStoreFolderUTF7() {
		return xMailStoreFolderUTF7;
	}


	public void setxMailStoreFolderUTF7(List<String> xMailStoreFolderUTF7) {
		this.xMailStoreFolderUTF7 = xMailStoreFolderUTF7;
	}


	public List<String> getxMailStoreHeaderHash() {
		return xMailStoreHeaderHash;
	}


	public void setxMailStoreHeaderHash(List<String> xMailStoreHeaderHash) {
		this.xMailStoreHeaderHash = xMailStoreHeaderHash;
	}


	public List<String> getxMailStoreMessageID() {
		return xMailStoreMessageID;
	}


	public void setxMailStoreMessageID(List<String> xMailStoreMessageID) {
		this.xMailStoreMessageID = xMailStoreMessageID;
	}


	public List<String> getxMailStoreDate() {
		return xMailStoreDate;
	}


	public void setxMailStoreDate(List<String> xMailStoreDate) {
		this.xMailStoreDate = xMailStoreDate;
	}


	public List<String> getxMailStoreFlags() {
		return xMailStoreFlags;
	}


	public void setxMailStoreFlags(List<String> xMailStoreFlags) {
		this.xMailStoreFlags = xMailStoreFlags;
	}


	public String getEmlPath() {
		return emlPath;
	}


	public void setEmlPath(String emlPath) {
		this.emlPath = emlPath;
	}


	public ArrayList<String> getAttachfileContents() {
		return attachfileContents;
	}

	public void setAttachfileContents(ArrayList<String> attachfileContents) {
		this.attachfileContents = attachfileContents;
	}

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public ArrayList<String> getToMails() {
		return toMails;
	}

	public void setToMails(ArrayList<String> toMails) {
		this.toMails = toMails;
	}

	public ArrayList<String> getCopyToMails() {
		return copyToMails;
	}

	public void setCopyToMails(ArrayList<String> copyToMails) {
		this.copyToMails = copyToMails;
	}

	public ArrayList<String> getEncryptToMails() {
		return encryptToMails;
	}

	public void setEncryptToMails(ArrayList<String> encryptToMails) {
		this.encryptToMails = encryptToMails;
	}

	public ArrayList<String> getAttachfileMd5s() {
		return attachfileMd5s;
	}

	public void setAttachfileMd5s(ArrayList<String> attachfileMd5s) {
		this.attachfileMd5s = attachfileMd5s;
	}

	public List<String> getLocalPaths() {
		return localPaths;
	}

	public void setLocalPaths(List<String> localPaths) {
		this.localPaths = localPaths;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public ArrayList<String> getTos() {
		return tos;
	}

	public void setTos(ArrayList<String> tos) {
		this.tos = tos;
	}

	public ArrayList<String> getAttachfilenames() {
		return attachfilenames;
	}

	
	public void setAttachfilenames(ArrayList<String> attachfilenames) {
		this.attachfilenames = attachfilenames;
	}

	public String getVertexID() {
		return vertexID;
	}

	
	public void setVertexID(String vertexID) {
		this.vertexID = vertexID;
	}

	public ArrayList<String> getCopyTos() {
		return copyTos;
	}

	public void setCopyTos(ArrayList<String> copyTos) {
		this.copyTos = copyTos;
	}

	public ArrayList<String> getEncryptTos() {
		return encryptTos;
	}

	public void setEncryptTos(ArrayList<String> encryptTos) {
		this.encryptTos = encryptTos;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getReceiveds() {
		return receiveds;
	}

	public void setReceiveds(List<String> receiveds) {
		this.receiveds = receiveds;
	}

	public List<String> getShowLabel() {
		return showLabel;
	}

	public void setShowLabel(List<String> showLabel) {
		this.showLabel = showLabel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
	public List<String> getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(List<String> acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public List<String> getContentLanguage() {
		return contentLanguage;
	}

	public void setContentLanguage(List<String> contentLanguage) {
		this.contentLanguage = contentLanguage;
	}

	public List<String> getContentLength() {
		return contentLength;
	}

	public void setContentLength(List<String> contentLength) {
		this.contentLength = contentLength;
	}

	public List<String> getContentTransferEncoding() {
		return contentTransferEncoding;
	}

	public void setContentTransferEncoding(List<String> contentTransferEncoding) {
		this.contentTransferEncoding = contentTransferEncoding;
	}

	public List<String> getContenttype() {
		return contenttype;
	}

	public void setContenttype(List<String> contenttype) {
		this.contenttype = contenttype;
	}

	public List<String> getDeliveredTo() {
		return deliveredTo;
	}

	public void setDeliveredTo(List<String> deliveredTo) {
		this.deliveredTo = deliveredTo;
	}

	public List<String> getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(List<String> deliverydate) {
		this.deliverydate = deliverydate;
	}

	public List<String> getErrorsTo() {
		return errorsTo;
	}

	public void setErrorsTo(List<String> errorsTo) {
		this.errorsTo = errorsTo;
	}

	public List<String> getImportance() {
		return importance;
	}

	public void setImportance(List<String> importance) {
		this.importance = importance;
	}

	public List<String> getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(List<String> inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public List<String> getMailinglist() {
		return mailinglist;
	}

	public void setMailinglist(List<String> mailinglist) {
		this.mailinglist = mailinglist;
	}

	public List<String> getReceivedSPF() {
		return receivedSPF;
	}

	public void setReceivedSPF(List<String> receivedSPF) {
		this.receivedSPF = receivedSPF;
	}

	public List<String> getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(List<String> replyTo) {
		this.replyTo = replyTo;
	}

	public List<String> getReturnpath() {
		return returnpath;
	}

	public void setReturnpath(List<String> returnpath) {
		this.returnpath = returnpath;
	}

	public List<String> getSender() {
		return sender;
	}

	public void setSender(List<String> sender) {
		this.sender = sender;
	}

	public List<String> getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(List<String> threadIndex) {
		this.threadIndex = threadIndex;
	}

	public List<String> getThreadTopic() {
		return threadTopic;
	}

	public void setThreadTopic(List<String> threadTopic) {
		this.threadTopic = threadTopic;
	}

	public List<String> getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(List<String> userAgent) {
		this.userAgent = userAgent;
	}

	public List<String> getXMailer() {
		return xMailer;
	}

	public void setXMailer(List<String> xMailer) {
		this.xMailer = xMailer;
	}

	public List<String> getXMailStoreFolderUTF7() {
		return xMailStoreFolderUTF7;
	}

	public void setXMailStoreFolderUTF7(List<String> xMailStoreFolderUTF7) {
		this.xMailStoreFolderUTF7 = xMailStoreFolderUTF7;
	}

	public List<String> getXMailStoreHeaderHash() {
		return xMailStoreHeaderHash;
	}

	public void setXMailStoreHeaderHash(List<String> xMailStoreHeaderHash) {
		this.xMailStoreHeaderHash = xMailStoreHeaderHash;
	}

	public List<String> getXMailStoreMessageID() {
		return xMailStoreMessageID;
	}

	public void setXMailStoreMessageID(List<String> xMailStoreMessageID) {
		this.xMailStoreMessageID = xMailStoreMessageID;
	}

	public List<String> getXMailStoreDate() {
		return xMailStoreDate;
	}

	public void setXMailStoreDate(List<String> xMailStoreDate) {
		this.xMailStoreDate = xMailStoreDate;
	}

	public List<String> getXMailStoreFlags() {
		return xMailStoreFlags;
	}

	public void setXMailStoreFlags(List<String> xMailStoreFlags) {
		this.xMailStoreFlags = xMailStoreFlags;
	}

	


	public String getuTCDate() {
		return uTCDate;
	}


	public void setuTCDate(String uTCDate) {
		this.uTCDate = uTCDate;
	}


	@Override
	public String toString() {
		return "Email [type=" + type + ", title=" + title + ", content="
				+ content + ", sendtime=" + sendtime + ", from=" + from
				+ ", tos=" + tos + ", copyTos=" + copyTos + ", encryptTos="
				+ encryptTos + ", attachfilenames=" + attachfilenames
				+ ", attachfileContents=" + attachfileContents + ", vertexID="
				+ vertexID + ", messageID=" + messageID + ", receiveds="
				+ receiveds+", uTCDate="
						+ uTCDate + "]";
	}

	
}
