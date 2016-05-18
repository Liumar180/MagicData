package connectToTitan

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import java.util.ArrayList
import java.sql.DriverManager
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import com.thinkaurelius.titan.core.EdgeLabel
import com.thinkaurelius.titan.core.Multiplicity
import com.thinkaurelius.titan.core.PropertyKey
import com.thinkaurelius.titan.core.TitanFactory
import com.thinkaurelius.titan.core.TitanGraph
import com.thinkaurelius.titan.core.TitanTransaction
import com.thinkaurelius.titan.core.attribute.Geoshape
import com.thinkaurelius.titan.core.schema.ConsistencyModifier
import com.thinkaurelius.titan.core.schema.TitanGraphIndex
import com.thinkaurelius.titan.core.schema.TitanManagement
import com.thinkaurelius.titan.core.TitanVertex
import com.thinkaurelius.titan.core.TitanEdge
import org.apache.tinkerpop.gremlin.process.traversal.Order
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.T
import org.apache.commons.configuration.BaseConfiguration
import java.util.Set
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.sql.Connection
//import java.util.Iterator
//import java.lang.Iterable
import java.util.concurrent.atomic.AtomicLong
import scala.Array
import scala.collection.mutable.ArrayBuffer
import com.tinkerpop.blueprints.Vertex
//import com.tinkerpop.blueprints._
import com.tinkerpop.blueprints.{Graph=>BPGraph}
import com.thinkaurelius.titan.core.TitanException
//import com.thinkaurelius.titan.diskstorage.locking.PermanentLockingException



object JDBCToGraphMax{
 
  def main(args: Array[String]): Unit = {
    
    println("开始连接Mysql数据库");
    val url = "jdbc:mysql://192.168.40.35:3306/titantest"
    val username = "root"
    val password = "YMiss8X%Zc"

    /*Driver program*/
    //构建spark 应用程序的运行环境SparkConf
    //val conf = new SparkConf().setAppName("JDBCToGraph")
    val conf = new SparkConf().setAppName("JDBCToGraph").setMaster("local[1]")
    //val conf = new SparkConf().setAppName("JDBCToGraph").setMaster("spark://master:7077")
    
    //初始化Sparkcontext
    val sc = new SparkContext(conf)  
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    
    //开始读数据
    val start_time = System.currentTimeMillis()
    
    println("Application begin on " + start_time + "*****************************")
    
    var conn : Connection = null
    var total_row_counts : Long = 0
    try { 
      conn = DriverManager.getConnection(url, username, password)
      val st = conn.createStatement()
      val resultset=st.executeQuery("select max(id) from test2");  
 
      if(resultset.next())  total_row_counts = resultset.getInt(1)
      
    }finally{
      if(conn != null) conn.close()
    }
    
    println("total_row_counts :"+total_row_counts)
    try {

      val StartOfEmailID = 1000000;
      val partition_num = 2
      //val total_row_counts = 10000000
      val row_block_size = 10
      
      // previous dependent rdd
      //var preJDBCRDD : RDD[(Int,(Int,Int,Int,String,String))] = null
      //var preTEdges: RDD[VertexId] = null
      var preVertexRdd : RDD[(VertexId, TVertex)] = null
      var preEdgeRDD : RDD[Edge[TEdge]] = null
      //var preEdgeRDD : RDD[(String,Edge[TEdge])] = null
      var preTEdges : RDD[VertexId] = null
      var preTriplets: RDD[EdgeTriplet[TVertex, TEdge]] = null
      var finalTriplets: RDD[EdgeTriplet[TVertex, TEdge]] = null
      
      var left_rows = total_row_counts
      while(left_rows > 0) {
        // RDD11
        val jdbcRDD = new JdbcRDD(sc, () => DriverManager.getConnection(url, username, password),
        "select id, Person, EmailID, Phone, EmailAcount from test2 where ? <= id and id <= ?",
        total_row_counts - left_rows + 1, total_row_counts - left_rows + row_block_size, partition_num, r => (r.getInt("id"),r.getInt("Person"),r.getInt("EmailID"), r.getString("Phone"),r.getString("EmailAcount")))
       
        // add myRDD's dependency to preJDBCRDD
        //val pairJdbcRDD = jdbcRDD.map(r=>(r._1,r))
        //pairJdbcRDD.cache
        //val myRDD = pairJdbcRDD.leftOuterJoin(preJDBCRDD).map{case(id:Int,(u,_))=>u}
        //preJDBCRDD = pairJdbcRDD

        // RDD12
        jdbcRDD.cache()
  	   	val tVertexRdd: RDD[(VertexId, TVertex)] = jdbcRDD.flatMap{ line => 
  	   	  // Person Vertex
  	   	  val tVertex = new TVertex("Person")
    	   	tVertex.addProperty("id",line._2.toString())
    	   	tVertex.addProperty("PersonID",line._2.toString())
    	   	tVertex.addProperty("Phone",line._4.toString())
    	   	
    	   	// Email Vertex
    	   	val eVertex = new TVertex("Email")
    	   	eVertex.addProperty("id",(line._3+StartOfEmailID).toString())
    	   	eVertex.addProperty("EmailID",line._3.toString())
    	   	eVertex.addProperty("EmailAcount",line._5.toString())
    	   	
    			List((line._2.toInt, tVertex),(line._3.toInt+StartOfEmailID, eVertex))
  		  }
    		val tEdgeRDD: RDD[Edge[TEdge]] = jdbcRDD.map{line => 
    		  // Edge between Person and Email
    			val tEdge = new TEdge()
    	   	tEdge.addProperty("id",line._1.toString())
    	   	tEdge.addProperty("Person",line._2.toString())
    	   	tEdge.addProperty("EmailID",line._3.toString())
    	   	tEdge.addProperty("Phone",line._4.toString())
    	   	tEdge.addProperty("EmailAcount",line._5.toString())
    			Edge(line._2.toInt, line._3.toInt+StartOfEmailID, tEdge)
    		}
    		
//    		if(preVertexRdd == null) preVertexRdd = tVertexRdd else preVertexRdd = tVertexRdd.leftOuterJoin(preVertexRdd).map({case(vid,(u,_))=>(vid,u)})
//    		preVertexRdd.cache
//    		if(preEdgeRDD == null) preEdgeRDD = tEdgeRDD else preEdgeRDD = tEdgeRDD.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}.leftOuterJoin(preEdgeRDD.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}).map{case(vid,(u,_))=>u}   		
//     		preEdgeRDD.cache
    		
//    		if(preVertexRdd == null) preVertexRdd = tVertexRdd else preVertexRdd = tVertexRdd.leftOuterJoin(preVertexRdd).map({case(vid,(u,_))=>(vid,u)})
//    		preVertexRdd.cache
//    		if(preEdgeRDD == null) preEdgeRDD = tEdgeRDD.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)} else preEdgeRDD = tEdgeRDD.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}.leftOuterJoin(preEdgeRDD).map{case(vid,(u,_))=>(vid,u)}
//    		preEdgeRDD.cache
    		
    		// Create the social graph of people
    		val tGraph: Graph[TVertex, TEdge] = Graph(tVertexRdd, tEdgeRDD)
    		//val tGraph: Graph[TVertex, TEdge] = Graph(preVertexRdd, preEdgeRDD)
    		   		
    		tGraph.cache()
    		
//    		tGraph.vertices.collect()
//    		tGraph.edges.collect()
//    		println("************************************************")
//    		println(tGraph.vertices.count())
//    		println(tGraph.edges.count())
//    		println("*********************vertices***********************")
//    		tGraph.vertices.foreach({case (id, tv)  =>
//    		  println("--------------------------------------------------")
//    		  println("id: " + id)
//    		  println("tv: " + tv.toString())
//    		  println("vtype: " + tv.vtype)
//    		  tv.properties.foreach({t=>
//    		    println(t._1+":"+t._2)
//    		  })
//    		})
//    		println("*********************edges***************************")
//    		tGraph.edges.foreach({te  =>
//    		  println("--------------------------------------------------")
//    		  println("te: " + te.toString())
//    		  te.attr.properties.foreach({t=>
//    		    println(t._1+":"+t._2)
//    		  })
//    		})
    		
    		//tGraph.vertices
      	val tVertices = tGraph.vertices.mapPartitionsWithIndex({ (pID: Int,vertexes: Iterator[(VertexId,TVertex)])	=>
            
            val graph = TitanGraphPool(pID)
            val tx = graph.newTransaction();
    
            println("Titan数据库已经连接成功，开始存储顶点数据...")
            val result = vertexes.map {case(vid,vertex) => 
              println("*******************"+pID)
              println("vid:"+vid)
              println("*******************Query "+vertex.vtype+" of "+vertex.properties("id"))
              //val query_vertex = tx.query().has("id",vertex.properties("id")).vertices()     
              val it_vertex = tx.query().has("id",vertex.properties("id")).vertices().iterator()
              //if(query_vertex == null || query_vertex.iterator() == null || !query_vertex.iterator().hasNext){
              if(it_vertex == null || !it_vertex.hasNext()) {
                println("*******************Query "+vertex.vtype+" of "+vertex.properties("id")+"(NotExisted)")
                val addedVertex =  graph.addVertex(T.label, vertex.vtype)
                vertex.properties.foreach({t=>
          		    addedVertex.property(t._1,t._2)
          		  })
          		  vertex._tid = addedVertex.longId()
              }else{
                vertex._tid = it_vertex.next().longId()
              }
        		  (vid,vertex)
            }.toList//.reduce(_+_)

            println("1111111111111111111111111-vertex"+result+"1111111111111111111111111111"+pID)
            println("prepare to commit transaction for vertex: "+graph.tx().toString())
            graph.tx().commit()
      	    result.toIterator
      	})
      	val convertedGraph = tGraph.joinVertices(tVertices.map({u=>(u._1,u._2)})){case (_, _, u)=>u}
     		
     		//if(preTriplets == null) preTriplets = convertedGraph.triplets else preTriplets = convertedGraph.triplets.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}.leftOuterJoin(preTriplets.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}).map{case(vid,(u,_))=>u}
    		//preTriplets.cache
    		
    		val tempTriplets = if(preTriplets == null) convertedGraph.triplets else convertedGraph.triplets.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}.leftOuterJoin(preTriplets.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}).map{case(vid,(u,_))=>u}
     	  
       	//val teges = convertedGraph.triplets.mapPartitionsWithIndex({(pID,tls)	=>
    		val teges = tempTriplets.mapPartitionsWithIndex({(pID,tls)	=>
       	    val graph = TitanGraphPool(pID)
       	    val tx = graph.newTransaction();
            println("Titan数据库已经连接成功,开始存储边数据...")

            val result = tls.map {tl => 
              println("*******************"+pID)
              println("vid:"+tl.attr.properties)
              val srcVertex = graph.vertices(tl.srcAttr._tid.asInstanceOf[AnyRef]).next()
              val dstVertex = graph.vertices(tl.dstAttr._tid.asInstanceOf[AnyRef]).next()
              val it_edge = tx.query().has("id",tl.attr.properties("id")).edges().iterator()
              //val it_edge = srcVertex.edges(Direction.OUT, "has")
              //if(it_edge != null && !it_edge.hasNext()) {
              //val query_edges = tx.query().has("id",tl.attr.properties("id")).edges()              
              //if(query_edges == null || !query_edges.iterator().hasNext){
              if(it_edge == null || !it_edge.hasNext()){
                val addedEdge = srcVertex.addEdge("has", dstVertex)
      
                tl.attr.properties.foreach({t=>
          		    addedEdge.property(t._1,t._2)
          		  })
              }
              //List.empty.iterator 
        		  tl
            }.toList
            println("1111111111111111111111111-edge"+result+"1111111111111111111111111111"+pID)
            println("prepare to commit transaction for edges: "+graph.tx().toString())
            
            graph.tx().commit()
            result.toIterator
      	})//.count() 
      	
      	
//      	if(preVertexRdd == null) preVertexRdd = tVertices else preVertexRdd = tVertices.leftOuterJoin(preVertexRdd).map({case(vid,(u,_))=>(vid,u)})
//    		preVertexRdd.cache
    		//if(preEdgeRDD == null) preEdgeRDD = teges.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)} else preEdgeRDD = teges.map{e=>(List(e.dstId,e.srcId,e).mkString("_"),e)}.leftOuterJoin(preEdgeRDD).map{case(vid,(u,_))=>(vid,u)}
    		//preEdgeRDD.cache

    		preTriplets = teges
      	
        left_rows -= row_block_size
        println("left_rows:",left_rows)       
      }
      
     // trigger the final action, result in a long chain of execution
     //if(preEdgeRDD != null) preEdgeRDD.count()
     if(preTriplets != null) preTriplets.count()
      //if(preTEdges != null) preTEdges.count()
  	 
     //println("77777777777777:"+preTriplets.toDebugString)
     val end_time = System.currentTimeMillis()
     val total_seconds = (end_time - start_time)/1000;
     println("total_seconds:"+total_seconds+" total_rows:"+total_row_counts+" speed(pcs):"+(total_row_counts/total_seconds))
  		 		     
    } 
    catch {
          case ex : java.util.NoSuchElementException => {
             println("####--NoSuchElement--#####");
          }
          case ex : TitanException =>{
             println("####--TitanException--#####");
          }
     }finally{
          //tx.commit();
          //tx.close()
          println("end"); 
          TitanGraphPool.release()
          //关闭Sparkcontext
          sc.stop();
     }
  }   
  
   case class TVertex(vtype:String) {
     var properties = Map[String,String]()
     var _tid : Long = 0 
     def addProperty(name:String,value:String) {
       properties += (name->value)
     }
   }
   
   case class TEdge() {
     var properties = Map[String,String]()
     def addProperty(name:String,value:String) {
       properties += (name->value)
     }
   }
  
}