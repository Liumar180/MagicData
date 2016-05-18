package com.integrity.dataSmart.util.dynamicInvoke;

import java.net.URI;
import java.util.Arrays;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

public class MyClassLoader {
	
    public Class<?> findClass(String str) throws ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 用于诊断源代码编译错误的对象
        DiagnosticCollector diagnostics = new DiagnosticCollector();
        // 内存中的源代码保存在一个从JavaFileObject继承的类中
        JavaFileObject file = new JavaSourceFromString("Temp", str.toString());
        
        Iterable compilationUnits = Arrays.asList(file);
        String flag = "-d";
//        String outDir = System.getProperty("user.dir") + "/" + "WebRoot\\WEB-INF\\classes";
        String outDir = MyClassLoader.class.getResource("/").getFile();
        Iterable<String> stringdir = Arrays.asList(flag, outDir); // 指定-d dir 参数
        // 建立一个编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null,
                stringdir, null, compilationUnits);
        // 编译源程序
        boolean result = task.call();
        if (result) {
            return Class.forName("Temp");
        }
        return null;
    }
	
	class JavaSourceFromString extends SimpleJavaFileObject{

	    private String code;
	 
	    public JavaSourceFromString(String name, String code) {
	        super(URI.create("string:///" + name.replace('.', '/')
	                + Kind.SOURCE.extension), Kind.SOURCE);
	        this.code = code;
	    }
	 
	    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
	        return code;
	    }
		
	}

}
