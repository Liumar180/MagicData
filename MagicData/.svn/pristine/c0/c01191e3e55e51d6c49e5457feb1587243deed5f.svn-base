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



object JDBCToGraph{
 
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
    
    // 1. load graph from titan
    val loadedGraph = LoadGrapFromTitan(sc)
    
    // 2. load vertex/edges from dbms
    
    // 3. merge the two graphs
    try {

      //Executor
      val myRDD = new JdbcRDD(sc, () => DriverManager.getConnection(url, username, password),
        "select id, Person, EmailID, Phone, EmailAcount from test2 where ? <= id and id <= ?",
        0, 10, 2, r => (r.getInt("id"),r.getInt("Person"),r.getInt("EmailID"), r.getString("Phone"),r.getString("EmailAcount")))
      
      println("Mysql数据库已经连接成功");
      println("**********************");
	    val StartOfEmailID = 1000000;
	    myRDD.cache()
	   	val tVertexRdd: RDD[(VertexId, TVertex)] = myRDD.flatMap{ line => 
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
  		val tEdgeRDD: RDD[Edge[TEdge]] = myRDD.map{line => 
  		  // Edge between Person and Edge
  			val tEdge = new TEdge()
  	   	tEdge.addProperty("id",line._1.toString())
  	   	tEdge.addProperty("Person",line._2.toString())
  	   	tEdge.addProperty("EmailID",line._3.toString())
  	   	tEdge.addProperty("Phone",line._4.toString())
  	   	tEdge.addProperty("EmailAcount",line._5.toString())
  			Edge(line._2.toInt, line._3.toInt+StartOfEmailID, tEdge)
  		}
  		
  		// Create the social graph of people
  		val tGraph: Graph[TVertex, TEdge] = Graph(tVertexRdd, tEdgeRDD)
  		tGraph.cache()
  		
  		// Merge the vertexes and edges ??
  		loadedGraph.vertices.union(tGraph.vertices).distinct()
  		loadedGraph.edges.union(tGraph.edges).distinct()
  		
  		//val mergedGraph = loadedGraph.joinVertices(tGraph.vertices)({(vid,lve , tve) => if(lve != None) lve else tve})
  		tGraph.vertices.collect()
  		tGraph.edges.collect()
  		println("************************************************")
  		println(tGraph.vertices.count())
  		println(tGraph.edges.count())
  		println("*********************vertices***********************")
  		tGraph.vertices.foreach({case (id, tv)  =>
  		  println("--------------------------------------------------")
  		  println("id: " + id)
  		  println("tv: " + tv.toString())
  		  println("vtype: " + tv.vtype)
  		  tv.properties.foreach({t=>
  		    println(t._1+":"+t._2)
  		  })
  		})
  		println("*********************edges***************************")
  		tGraph.edges.foreach({te  =>
  		  println("--------------------------------------------------")
  		  println("te: " + te.toString())
  		  te.attr.properties.foreach({t=>
  		    println(t._1+":"+t._2)
  		  })
  		})
  		
//  	val tVertices = tGraph.vertices.mapPartitionsWithIndex((index: Int, it: Iterator[TVertex]) =>
//  	  it.map(x => index + ", "+x).iterator
//  	  ).collect
        //var tx = graph.newTransaction();
      
//  	val tVertices = tGraph.vertices.mapPartitionsWithIndex((pID: Int,vertexes: Iterator[(VertexId,TVertex)])	=>
//
//        //println("count="+vertexes.count({_=>true}))
//
//        vertexes.map {case(vid,vertex) => 
//          println("*******************"+pID);
//          println("vid:"+vid);
//          //var addedVertex =  tx.addVertex(T.label, vertex.vtype);
//          //List.empty.iterator 
//    		  //(vid,vertex)
//    		  vertex
//        }
//  	)
//  	//tVertices.count()
//  	println("--------------------------------tVertices.colect"+tVertices.collect().toList.mkString+"-----------------------------------------")
  	//println("--------------------------------tVertices.count"+tVertices.count()+"-----------------------------------------")
  	
  		val tvs = tGraph.vertices.mapPartitionsWithIndex({(pID: Int,vertexes: Iterator[(VertexId,TVertex)])	=>
  		  
  		    val result = vertexes.map {case (vid,vertex) => 
  		      println("8888888888888888current pid:"+pID)
  		      println("vid:"+vid);
  		    }.toList
  		  
  		    result.iterator
      })
      tvs.count()
    
  		val tgs = tGraph.edges.mapPartitionsWithIndex({(pID,es)	=>
  		  
  		 val result = es.map { e => 
  		    println("9999999999999current pid:"+pID)
  		    println("srcID:"+e.srcId+"dstID"+e.dstId)
  		    }.toList
  		  
  		    result.iterator
  		})
  		tgs.count()
  		//tgs.
  		//teges.
    
  	// Save GraphX to Titan
  	//tGraph.vertices.mapPartitions({vertexes =>
  	val tVertices = tGraph.vertices.mapPartitionsWithIndex({ (pID: Int,vertexes: Iterator[(VertexId,TVertex)])	=>
//        val titanConf = new org.apache.commons.configuration.BaseConfiguration();
//        println("开始连接Titan数据库")
//        titanConf.setProperty("storage.backend","hbase")
//        titanConf.setProperty("storage.hostname","192.168.40.211")
//        titanConf.setProperty("storage.tablename","titan")
//        val graph = TitanFactory.open(titanConf)
        
        val graph = TitanGraphPool(pID)

        println("Titan数据库已经连接成功，开始存储顶点数据")
        //var tx = graph.newTransaction();
        
        //println("count="+vertexes.count({_=>true}))
        //val result = 
        val result = vertexes.map {case(vid,vertex) => 
          println("*******************"+pID)
          println("vid:"+vid)
          //var addedVertex =  tx.addVertex(T.label, vertex.vtype);
          var addedVertex =  graph.addVertex(T.label, vertex.vtype)
          vertex.properties.foreach({t=>
    		    addedVertex.property(t._1,t._2)
    		  })
          //List.empty.iterator 
    		  vertex._tid = addedVertex.longId()
    		  //(vid,vertex)
    		  (vid,vertex)
    		  //1
    		  //new TVertex("Test")
        }.toList//.reduce(_+_)
        //val saved_result = result.toList
        //println("######################"+result+"#########"+pID)
        //println("######################result.count:"+result.count(_=>true)+"#########"+pID)
        //val saved_result = result.toList
        //println("######################result:"+result.collect(_).toList.mkString+"#########"+pID)
        //tx.commit()
        println("prepare to commit transaction: "+graph.tx().toString())
//        val r= result.map({case(vid,vertex)=>
//          println("==============vid:"+vid+"================")
//          println("vertex:"+vertex._tid+"vtype:"+vertex.vtype)
//          })//.count(_=>true)        
        //println("r.count: "+r.count { _=>true })  
        graph.tx().commit()
        //graph.close()
        
        
        //println("++++++++++++++result: "+result.count(_=>true))
  	    //List.empty.iterator 
  	    result.toIterator
        //saved_result.toIterator
        //r
  	})
  	//tVertices.count()
  	//println("--------------------------------tVertices.colect"+tVertices.collect().toList.mkString+"-----------------------------------------")
  	//println("--------------------------------tVertices.count"+tVertices.count()+"-----------------------------------------")
  	
  	
//  	tVertices.foreach({case(vid,vertex)=>
//  	  println("vid:"+vid+"vertex:"+vertex._tid+"vtype"+vertex.vtype)
//  	  })
  	val convertedGraph = tGraph.joinVertices(tVertices.map({u=>(u._1,u._2)}))({case (vid, vd, u)=>
  	    u
  	  })
  	  /*
  		convertedGraph.cache()
  		convertedGraph.vertices.collect()
  		convertedGraph.edges.collect()
  		println("************************************************")
  		println(convertedGraph.vertices.count())
  		println(convertedGraph.edges.count())
  		println("*********************vertices***********************")
  		convertedGraph.vertices.foreach({case (id, tv)  =>
  		  println("--------------------------------------------------")
  		  println("id: " + id)
  		  println("tid: " + tv._tid)
  		  println("tv: " + tv.toString())
  		  println("vtype: " + tv.vtype)
  		  tv.properties.foreach({t=>
  		    println(t._1+":"+t._2)
  		  })
  		})
  		println("*********************edges***************************")
  		convertedGraph.edges.foreach({te  =>
  		  println("--------------------------------------------------")
  		  println("te: " + te.toString())
  		  te.attr.properties.foreach({t=>
  		    println(t._1+":"+t._2)
  		  })
  		})  
*/
//  	tGraph.triplets.mapPartitions({triplet =>
//  	    
//  	    List.empty.iterator 
//  	})
  	  
   	val teges = convertedGraph.triplets.mapPartitionsWithIndex({(pID,tls)	=>
//        val titanConf = new org.apache.commons.configuration.BaseConfiguration()
//        println("开始连接Titan数据库")
//        titanConf.setProperty("storage.backend","hbase")
//        titanConf.setProperty("storage.hostname","192.168.40.211")
//        titanConf.setProperty("storage.tablename","titan")
//        val graph = TitanFactory.open(titanConf)

   	    val graph = TitanGraphPool(pID)
        println("Titan数据库已经连接成功")
        //var tx = graph.newTransaction();
        
        //println("count="+vertexes.count({_=>true}))
        val result = tls.map {tl => 
          println("*******************"+pID)
          println("vid:"+tl.attr.properties)
          //var addedVertex =  tx.addVertex(T.label, vertex.vtype);
          val srcVertex = graph.vertices(tl.srcAttr._tid.asInstanceOf[AnyRef]).next()
          val dstVertex = graph.vertices(tl.dstAttr._tid.asInstanceOf[AnyRef]).next()
          var addedEdge = srcVertex.addEdge("has", dstVertex)

          tl.attr.properties.foreach({t=>
    		    addedEdge.property(t._1,t._2)
    		  })
          //List.empty.iterator 
    		  tl.dstId
        }.toList
        println("1111111111111111111111111"+result+"1111111111111111111111111111"+pID)
        //tx.commit()
        println("prepare to commit transaction: "+graph.tx().toString())
        
        graph.tx().commit()
        //graph.close()
        
  	    //List.empty.iterator 
        result.toIterator
  	})//.count() 	
  	
  	println("--------------------------------teges.colect"+teges.collect().toList.mkString+"-----------------------------------------")

  	
     val end_time = System.currentTimeMillis()
     val total_seconds = (end_time - start_time)/1000;
  		 		     
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
   

  
  def LoadGrapFromTitan(sc:SparkContext): Graph[TVertex, TEdge] = {
        val titanConf = new org.apache.commons.configuration.BaseConfiguration();
        println("开始连接Titan数据库")
        titanConf.setProperty("storage.backend","hbase")
        titanConf.setProperty("storage.hostname","192.168.40.211")
        titanConf.setProperty("storage.tablename","titan")
        val graph = TitanFactory.open(titanConf)    
        
        graph.close()
        
        val tx = graph.newTransaction()   
   
        val vertices = tx.query().vertices()
        
        val vertexArray = ArrayBuffer[(VertexId,TVertex)]()

        var total = 0
        val  iter =  vertices.iterator();
        while (iter.hasNext()){        
          val ve = iter.next();
          val tVertex = new TVertex(ve.label())
          println("----------------------------ve------------------------");
          println(ve);
          //var set = ve.query().properties()
          //var x = set.iterator()
          val x = ve.properties()
          while (x.hasNext()) {
              val p = x.next()
             //println("##########################################");
             //println(x.next());
              tVertex.properties += (p.key()->p.value().toString())
          }
          total += 1
          vertexArray.append((ve.property("id").toString().toLong,tVertex))
        }
        
        println("*****************************************total vertexes"+total.toString());

        println(vertices);
    
        val tVertexRdd: RDD[(VertexId,TVertex)] = sc.parallelize(vertexArray.toSeq)
    
        val edgeArray = ArrayBuffer[Edge[TEdge]]()
        val edges = tx.query().edges();
        val edges_itor = edges.iterator();
    
        total = 0
        while (edges_itor.hasNext()) {
          var ve = edges_itor.next();
          val tEdge = new TEdge()
          var x = ve.properties()
          while (x.hasNext()) {
              val p = x.next()
             //println("##########################################");
             //println(x.next());
              tEdge.properties += (p.key()->p.value().toString())
          }
          
          println("******************************edge******************************");
          println(ve);
          total += 1
          val edge = new Edge[TEdge]()
          edge.attr = tEdge
          edgeArray.append(edge)
        }
        println("*****************************************total edges:" + total.toString());
    
        val data = sc.parallelize(List(("Panda", 3), ("Kay", 6), ("Snail", 2)))
        data.saveAsSequenceFile("/rootPath/filename")

        val tEdgeRDD : RDD[Edge[TEdge]] = sc.parallelize(edgeArray.toSeq)
        
        val tGraph: Graph[TVertex, TEdge] = Graph(tVertexRdd, tEdgeRDD)
        
        tGraph.checkpoint()
        
        //tVertexRdd.saveAsSequenceFile()
        //tEdgeRDD.saveAsSequenceFile()
        tEdgeRDD.saveAsTextFile("")
        
        tGraph
  }
  
}