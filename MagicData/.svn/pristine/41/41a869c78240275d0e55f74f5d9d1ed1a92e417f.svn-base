package connectToTitan

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
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
import java.util.Iterator
import java.lang.Iterable
import java.util.concurrent.atomic.AtomicLong
import scala.Array
import scala.collection.mutable.ArrayBuffer
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints._;
import com.thinkaurelius.titan.core.TitanException
import com.thinkaurelius.titan.diskstorage.locking.PermanentLockingException
import com.thinkaurelius.titan.diskstorage.locking.AbstractLocker
import com.thinkaurelius.titan.diskstorage.locking.TemporaryLockingException
import scala.io.Source



object SparkToJDBC{
 
  def main(args: Array[String]): Unit = {
    
    println("开始连接Mysql数据库");
    val url = "jdbc:mysql://192.168.40.35:3306/story"
    val username = "root"
    val password = "YMiss8X%Zc"
    
    val conf = new SparkConf().setAppName("ReadDataTest12").setMaster("local[2]")
    //val conf = new SparkConf().setAppName("ReadDataTest")
    
    //初始化Sparkcontext
    val sc = new SparkContext(conf)  
    Class.forName("com.mysql.jdbc.Driver").newInstance()
    
    var startTime_total : Long = 0;
    var endTime_total : Long = 0;
    
    var startTime_other :Long = 0;
    var endTime_other : Long = 0;
    var graph : TitanGraph= null
    var count = -1;
    
    try {
      val myRDD = new JdbcRDD(sc, () => DriverManager.getConnection(url, username, password),
        "select Person, EmailID, Phone, EmailAcount from has_conflict where ? <= Idlocaltion and Idlocaltion <= ?",
        0, 50000, 10, r => r.getInt("Person") + "," + r.getInt("EmailID") + "," + r.getString("Phone") + "," + r.getString("EmailAcount"));
      
      println("Mysql数据库已经连接成功");
      println("**********************");
      
      //开始读数据
      startTime_total = System.currentTimeMillis();
      
      //println("myRDD Parnum="+myRDD.getNumPartitions);
      myRDD.getNumPartitions
      val contactsContactLists = myRDD.mapPartitionsWithIndex({case(pID, signs) =>
          val titanConf = new org.apache.commons.configuration.BaseConfiguration();
          println("开始连接Titan数据库");
          titanConf.setProperty("storage.backend","hbase");
          titanConf.setProperty("storage.hostname","192.168.40.33");
          titanConf.setProperty("storage.tablename","titan");
          graph = TitanFactory.open(titanConf);

          println("Titan数据库已经连接成功");
          
          count = 0;
          startTime_other = System.currentTimeMillis();
          var personNew : TitanVertex= null;
          
          var tx = graph.newTransaction();
          
          var signsArray = signs.toArray;
          var recordIndex = signsArray.length;
          
          while ((recordIndex > 0) && (recordIndex <= signsArray.length)){ 
            var everyRecord = signsArray(signsArray.length - recordIndex).split(",");
            val personID : String = everyRecord(0);
            val emailID : String = everyRecord(1);
            val phone : String = everyRecord(2);
            val emailCount : String = everyRecord(3);
            count += 1; 
            
            if(count % 200 == 0){
              println("*****" + "提交" + count);
              try{
                tx.commit();
                tx = graph.newTransaction();
              }catch{
                case ex : PermanentLockingException => 
                  {
                    println("####--PermanentLockingException1--#####")
                  }
                case ex : TitanException => 
                  {
                    println("####--TitanException1----Begin repeat--####"); 
                    println("####--TitanException1 Begin at --" + recordIndex + " in " + signsArray.length);
                    tx = graph.newTransaction();
                    if ((recordIndex + 200) <= signsArray.length){
                      recordIndex += 200;
                    }else{
                      recordIndex = signsArray.length;
                    }
                  }
                }
              } 
              
              if (personID != null && !personID.equals("")){
                var person  = tx.query().has("personID",personID).vertices();
                val it_person = person.iterator();
                var personVertex : TitanVertex = null;
                
                if (person != null && !person.equals("") && it_person.hasNext()){
                   val tmp_person = it_person.next(); 
                   if(phone != null && !phone.equals("")){ 
                       tmp_person.property("phoneNum", phone);
                   }
                   personVertex = tmp_person;
                }else{
                   var personNew_1  = tx.addVertex(T.label, "person", "personID",personID, "phoneNum", phone);
                   personVertex = personNew_1;
                }
                
                var email_1 = tx.query().has("emailID",emailID).vertices();
                val it_email_1 = email_1.iterator();
                var emailVertex : TitanVertex = null;
                
                if (email_1 != null && !email_1.equals("") && it_email_1.hasNext()){
                  val tmp_email = it_email_1.next();
                  if(emailCount != null && !emailCount.equals("")){ 
                       tmp_email.property("emailCount", emailCount);
                  }
                  emailVertex = tmp_email;
                }else{
                  var emailNew_1 = tx.addVertex(T.label, "email", "emailID",emailID,"emailCount", emailCount);
                  emailVertex = emailNew_1;
                  personVertex.addEdge("has", emailVertex);
                }
              }
              recordIndex -= 1;
            }
            
            try{
              tx.commit();
            }catch{
              case ex : PermanentLockingException => 
                {
                  println("####--PermanentLockingException2--#####")
                  InToTitan(tx, graph, 200, signsArray);
                }
              case ex : TitanException => 
                {
                  println("####--TitanException2--#####");
                  InToTitan(tx, graph, 200, signsArray);
                }
            }
          
            endTime_other = System.currentTimeMillis();
            println("*********pID***********");
            println(pID + " used " + ((endTime_other - startTime_other) / 1000) + "s.");
            println("core's speed is " + (count * 1000 / (endTime_other - startTime_other))
                + "个/s.");
            var res = List [(T , T) ]() 
            res.iterator      
        })
       
        contactsContactLists.collect();
      
       }catch {
          case ex : Exception=>{
            println("*******" + ex.printStackTrace() + "MyException")
          }
       }finally{
          println("end");
          endTime_total = System.currentTimeMillis();
          println("Application begin on " + startTime_total + "*****************************" + "\n");
          println("Application end on " + endTime_total + "*****************************"+ "\n");
          println("Application used " + ((endTime_total - startTime_total) / 1000) + "\n");
          println("Application's speed is " + (count * 1000 / (endTime_total - startTime_total)) + "\n")
          //graph.close();
          sc.stop();
     }
  }
  
  def InToTitan(tx : TitanTransaction, graph : TitanGraph, 
      recordIndex : Int,  signsArray : Array[String]) {
    var count = 0;
    var personNew : TitanVertex= null;
    var Index = recordIndex;
    
    while ((Index > 0) && (signsArray.length > Index)){
        var temp = signsArray(signsArray.length - Index).split(",");
        val personID : String = temp(0);
        val emailID : String = temp(1);
        val phone : String = temp(2);
        val emailCount : String = temp(3);
        count += 1; 
        
        if(count % 200 == 0){
          println("*****" + "提交" + count);
          try{
            tx.commit();
          }catch{
            case ex : PermanentLockingException => {
              println("####--PermanentLockingException3--#####")
            }
            case ex : TitanException => {
              println("####--TitanException3--#####--Begin repeat--####"); 
              Index = 200;
              println("****--TitanException3--Begin at" + Index + " in " + signsArray.length);
            }
           }
         }
         
        if (personID != null && !personID.equals("")){
          var person  = tx.query().has("personID",personID).vertices();
          val it_person = person.iterator();
          var personVertex : TitanVertex = null;
          
          if (person != null && !person.equals("") && it_person.hasNext()){
             val tmp_person = it_person.next(); 
             if(phone != null && !phone.equals("")){ 
                 tmp_person.property("phoneNum", phone);
             }
             personVertex = tmp_person;
          }else{
             var personNew_1  = tx.addVertex(T.label, "person", "personID",personID, "phoneNum", phone);
             personVertex = personNew_1;
          }
            
          //var personNew_1 = tmp_person.query().labels("has").vertices().iterator();
          var email_1 = tx.query().has("emailID",emailID).vertices();
          val it_email_1 = email_1.iterator();
          var emailVertex : TitanVertex = null;
          
          if (email_1 != null && !email_1.equals("") && it_email_1.hasNext()){
            val tmp_email = it_email_1.next();
            if(emailCount != null && !emailCount.equals("")){ 
                 tmp_email.property("emailCount", emailCount);
            }
            emailVertex = tmp_email;
          }else{
            var emailNew_1 = tx.addVertex(T.label, "email", "emailID",emailID,"emailCount", emailCount);
            emailVertex = emailNew_1;
            personVertex.addEdge("has", emailVertex);
          }
        }
         Index -= 1;
      }
  }
}