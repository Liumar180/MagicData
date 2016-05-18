package com.integrity.dataSmart.util.email;

import org.apache.cassandra.cli.CliParser.newColumnFamily_return;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AnalyticalHTML {
	/**
	 * 　*　【获取HTML内容】 　
	 */
	public String  getHtmlText(Document doc){
		return doc.body().text();
	}
	/**
	 * 　*　【获取链接地址】 　
	 */
	public String  getLinkHref( Element link){
		return link.attr("href");
	}
	/**
	 * 　*　【获取链接地址中的文本】 　
	 */
	public String  getLinkText( Element link){
		return link.text();
	}
	/**
	 * 　*　【获取链接地址中的文本】 　
	 */
	public String  getLinkInnerH( Element link){
		return link.html();
	}
 public static void main(String args[]){
	 String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
	 Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
	 Element link = doc.select("a").first();//查找第一个a元素
	 AnalyticalHTML analyticalHTML = new AnalyticalHTML(); 
	 String text = analyticalHTML.getHtmlText(doc); // "An example link"//取得字符串中的文本
	 System.out.println("text  "+text);
	 String linkHref = analyticalHTML.getLinkHref(link); // "http://example.com/"//取得链接地址
	 System.out.println("linkHref  "+linkHref);
	 String linkText = analyticalHTML.getLinkText(link); // "example""//取得链接地址中的文本
	 System.out.println("linkText  "+linkText);
//	 String linkOuterH = link.outerHtml(); 
//	 System.out.println("linkOuterH  "+linkOuterH);
	     // "<a href="http://example.com"><b>example</b></a>"
	 String linkInnerH = analyticalHTML.getLinkInnerH(link); // "<b>example</b>"//取得链接内的html内容
	 System.out.println("linkInnerH  "+linkInnerH);
 }
}
