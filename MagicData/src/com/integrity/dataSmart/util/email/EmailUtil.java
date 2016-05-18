package com.integrity.dataSmart.util.email;

import java.util.ArrayList;
import java.util.List;

import com.integrity.dataSmart.titanGraph.pojo.Email;

public class EmailUtil {
	
	public static Email emailUtil(Email email){
		if (email == null) {
			return null;
		}
		if(email.getCopyTos()==null){
			email.setCopyTos(new ArrayList<String>());
		}else{
			email.setCopyTos(replace(email.getCopyTos()));
		}
		if(email.getCopyToMails()==null){
			email.setCopyToMails(new ArrayList<String>());
		}else{
			email.setCopyToMails(replace(email.getCopyToMails()));
		}
		if(email.getContent()==null){
			email.setContent("");
		}else{
			email.setContent(replace(email.getContent()));
		}
		if(email.getDeliveredTo()==null){
			email.setDeliveredTo(new ArrayList<String>());
		}else{
			email.setDeliveredTo(replace(email.getDeliveredTo()));
		}
		if(email.getDeliverydate()==null){
			email.setDeliverydate(new ArrayList<String>());
		}else{
			email.setDeliverydate(replace(email.getDeliverydate()));
		}
		if(email.getFrom()==null){
			email.setFrom("");
		}else{
			email.setFrom(replace(email.getFrom()));
		}
		if(email.getFromMail()==null){
			email.setFromMail("");
		}else{
			email.setFromMail(replace(email.getFromMail()));
		}
		if(email.getKeywords()==null){
			email.setKeywords(new ArrayList<String>());
		}else{
			email.setKeywords(replace(email.getKeywords()));
		}
		if(email.getSender()==null){
			email.setSender(new ArrayList<String>());
		}else{
			email.setSender(replace(email.getSender()));
		}
		if(email.getSendtime()==null){
			email.setSendtime("");
		}else{
			email.setSendtime(replace(email.getSendtime()));
		}
		if(email.getTitle()==null){
			email.setTitle("");
		}else{
			email.setTitle(replace(email.getTitle()));
		}
		if(email.getToMails()==null){
			email.setToMails(new ArrayList<String>());
		}else{
			email.setToMails(replace(email.getToMails()));
		}
		if(email.getTos()==null){
			email.setTos(new ArrayList<String>());
		}else{
			email.setTos(replace(email.getTos()));
		}
		if(email.getAttachfilenames()==null){
			email.setAttachfilenames(new ArrayList<String>());
		}else{
			email.setAttachfilenames(replace(email.getAttachfilenames()));
		}
		return email;
	}
	/**
	 * html页面相关转义
	 * @param target
	 * @return
	 */
	public static ArrayList<String>  replace(List<String> target){
		ArrayList<String> list=new ArrayList<String>();
		for (String string : target) {
			list.add(string.replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("'","&#39;").replaceAll("\"","&quot;"));
		}
		return list;
	}
	/**
	 * html页面相关转义
	 * @param target
	 * @return
	 */
	public static String replace(String target){
		if(target!=null){
			target = target.replaceAll("&","&amp;");
			target = target.replaceAll("<","&lt;");
			target = target.replaceAll(">","&gt;");
			target = target.replaceAll("'","&#39;");
			target = target.replaceAll("\"","&quot;");
		}
	
		return target;
	}
	
}
