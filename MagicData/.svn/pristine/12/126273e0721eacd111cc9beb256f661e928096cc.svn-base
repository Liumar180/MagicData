package com.integrity.dataSmart.util.dynamicInvoke;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.IteratorUtils;

import com.tinkerpop.blueprints.Vertex;

public class Eval {

	public static Object eval(String code) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("import com.integrity.dataSmart.util.titan.TitanGraphUtil;");
		sb.append("import com.thinkaurelius.titan.core.TitanGraph;");
	    sb.append("import com.thinkaurelius.titan.core.TitanTransaction;");
	    sb.append("import com.thinkaurelius.titan.core.attribute.Text;");
	    sb.append("public class Temp{");
	    sb.append("    private TitanGraph graph = TitanGraphUtil.getInstance().getTitanGraph();");
	    sb.append("    public Object getObject(){");
	    sb.append("        TitanTransaction tx = graph.newTransaction();");
	    sb.append("        return " + code );
	    sb.append("    }");
	    sb.append("}");
        
        long s1 = System.currentTimeMillis();
        // 编译加载class
        Class clazz = new MyClassLoader().findClass(sb.toString());
        long e1 = System.currentTimeMillis();
        System.out.println("加载time:"+(e1 -s1));
        Method method = clazz.getMethod("getObject");
        // 反射调用方法
        Object o = method.invoke(clazz.newInstance());
        long e3 = System.currentTimeMillis();
        System.out.println("反射time:"+(e3 -e1));
        return o;
    }
 
    public static void main(String[] args) throws Exception {
    	List<Vertex> result = new ArrayList<Vertex>();
    	/*String label = "Person";
		String code = "tx.query().has(\"label\",\""+label+"\")";
		Map<String, String> proMap = new HashMap<String, String>();
		proMap.put("name", "lilan");
		proMap.put("idcard", "123");
		Set<Entry<String, String>> proSet = proMap.entrySet();
		for (Entry<String, String> proEntry : proSet) {
			String pro = proEntry.getKey();
			String value = proEntry.getValue();
			code += ".has(\""+pro+"\"+Text.CONTAINS,\""+value+"\")";
		}
		code += ".limit(50).vertices();";*/
		String code = "graph.getVertices(\"name\",\"lilan\");";
		Iterable<Vertex> it = (Iterable<Vertex>) Eval.eval(code);
		result.addAll(IteratorUtils.toList(it.iterator()));
		for (Vertex karry : it) {
			Set<String> set = karry.getPropertyKeys();
			for (String key : set) {
				System.out.println("key:"+key+"|value:"+karry.getProperty(key));
			}
		}
        
    }

}
