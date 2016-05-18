package connectToTitan

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import java.util.ArrayList
import java.sql.DriverManager
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

import com.thinkaurelius.titan.core.TitanGraph
import com.thinkaurelius.titan.core.TitanFactory
import com.tinkerpop.blueprints.Vertex
import com.thinkaurelius.titan.core.attribute.Cmp
import org.apache.tinkerpop.gremlin.structure.T
import com.thinkaurelius.titan.core.SchemaViolationException

object TitanQuery {
  def main(args: Array[String]): Unit = {
    var graph: TitanGraph = null
    val titanConf = new org.apache.commons.configuration.BaseConfiguration();

    println("开始连接数据库")
    titanConf.setProperty("storage.backend","hbase");
    titanConf.setProperty("storage.hostname","192.168.40.211");
    titanConf.setProperty("storage.tablename","titan");

    if (graph == null || !graph.isOpen()) {
      graph = TitanFactory.open(titanConf);
    }
    println("数据库已经连接成功");
    
//    val tx3 = graph.newTransaction();
//    var personNew_1  = tx3.addVertex(T.label, "Person", "PersonID","11", "phoneNum", "2");
//    //var personNew_2  = tx.addVertex(T.label, "person", "1","11", "phoneNum", "2");
//    tx3.commit()
//    
//    val tx2 = graph.newTransaction();
//    try{
//    var personNew_2  = tx2.addVertex(T.label, "Person", "PersonID","11", "phoneNum", "2");
//    //var personNew_2  = tx.addVertex(T.label, "person", "1","11", "phoneNum", "2");
//    
//    tx2.commit()
//    }catch
//    {
//      case e: SchemaViolationException=> println("************SchemaViolationException**************")
//    }
    
    //graph.close()
        
    val tx = graph.newTransaction();
    
    
   
    val vertices = tx.query().vertices();
    
    var total = 0
    val  iter =  vertices.iterator();
    while (iter.hasNext()){
      val ve = iter.next();
      println("----------------------------ve------------------------");
      println(ve);
      var set = ve.query().properties();
      var x = set.iterator();
      while (x.hasNext()) {
         //println("##########################################");
         println(x.next());
      }
      total += 1
    }
    
    println("*****************************************total vertexes"+total.toString());
      
    println(vertices);
    
    //    tx.getVertexLabel("email")
    
   // println("shutdown!!!!");
    /*
    val  iter =  vertices.iterator();
    while(iter.hasNext()){
      val ve = iter.next();
      println(ve);
      var set = ve.query().properties();
      var x = set.iterator();
      while (x.hasNext()) {
         println(x.next());
      }
    }*/
    

    val edges = tx.query().edges();
    val edges_itor = edges.iterator();
    
    total = 0
    while (edges_itor.hasNext()){
      var ve = edges_itor.next();
      println("******************************edge******************************");
      println(ve);
      total += 1
    }
    println("*****************************************total edges:"+total.toString());
    println("shutdown!!!!");
    println("################################");

    /*
    for (TitanVertex v : vertices) {
      //System.out.println(v.id());
      System.out.println("********************************************************");
      System.out.println(v);
     //System.out.println(v.toString());
     
      for(TitanVertexProperty p : v.query().properties()) {
        System.out.println(p);
      }
    }*/

    /*val  iter =  vertices.iterator();
    while(iter.hasNext()){
      val ve= iter.next();
  
      val i = set.iterator();//先迭代出来 
      while(i.hasNext()){//遍历  
  
        println(i.next()+" "+ve.getProperty(i.next()));  
      }  */
    
    graph.close()
    
  }
  
}