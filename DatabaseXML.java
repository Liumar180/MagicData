
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatabaseXML {
	public static void main(String[] agre){
		 
		System.out.println(DatabaseXML.getURLDriver("MYSQL")[1]);
	}
	public static String[] getURLDriver(String DBtype){
		String[] URLDriver = null;
		String url="";
		String driver="";
		 Element element = null;
		 URL s = DatabaseXML.class.getClassLoader().getResource("");
		 String path;
		 if(DatabaseXML.isWindowsSys()){
			 path = s.toString().substring(6);
		 }else{
			 path = s.toString().substring(5);
		 }
		 
		  // 可以使用绝对路劲
		  //File f = new File("src/com/integrity/service/import/DBType.xml");
		  File f = new File(path+"com/integrity/dataSmart/dataImport/service/DBType.xml");
/*		  FileInputStream is = null;
		  try {
			  is = new FileInputStream(f);
		  } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} */
		  // documentBuilder为抽象不能直接实例化(将XML文件转换为DOM文件)
		  DocumentBuilder db = null;
		  DocumentBuilderFactory dbf = null;
		  try {
		   // 返回documentBuilderFactory对象
		   dbf = DocumentBuilderFactory.newInstance();
		   // 返回db对象用documentBuilderFatory对象获得返回documentBuildr对象
		   db = dbf.newDocumentBuilder();

		   // 得到一个DOM并返回给document对象
		   Document dt = db.parse(f);
		   // 得到一个elment根元素
		   element = dt.getDocumentElement();

		   // 获得根元素下的子节点
		   NodeList childNodes = element.getChildNodes();
		   // 遍历这些子节点
		   for (int i = 0; i < childNodes.getLength(); i++) {
		    // 获得每个对应位置i的结点
		    Node node1 = childNodes.item(i);
		    if ("database".equals(node1.getNodeName())) {
		     // 如果节点的名称为"Account"，则输出Account元素属性type
		    	if(DBtype.equals(node1.getAttributes().getNamedItem("type").getNodeValue())){
		    		// 获得<Accounts>下的节点
				     NodeList nodeDetail = node1.getChildNodes();
				     // 遍历<Accounts>下的节点
				     for (int j = 0; j < nodeDetail.getLength(); j++) {
				      // 获得<Accounts>元素每一个节点
				      Node detail = nodeDetail.item(j);
				      if(detail.getNodeName().indexOf("url")>=0){
			    		  url=detail.getTextContent();
			    		 }
			    	  if(detail.getNodeName().indexOf("drive")>=0){
			    		  driver=detail.getTextContent();
			    		 }
				     }
		    	}
		     
		    }

		   }
		   URLDriver= new String[]{url,driver};
		  }

		  catch (Exception e) {
		   e.printStackTrace();
		  }

		return URLDriver;
	}
	private static boolean isWindowsSys(){
	       String p = System.getProperty("os.name");
	       return p.toLowerCase().indexOf("windows") >= 0 ? true : false;
	    }
}
