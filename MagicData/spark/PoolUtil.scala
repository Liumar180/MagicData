package connectToTitan

import com.thinkaurelius.titan.core.TitanGraph
import com.thinkaurelius.titan.core.TitanFactory

class PoolUtil {
  
}

object TitanGraphPool {

    var graphPool: Map[Int, TitanGraph] = Map()
//    sys.addShutdownHook {
//        graphPool.values.foreach { graph =>  graph.close() }
//    }

    // factory method
    def apply(partitionNum: Int): TitanGraph = {
        
        graphPool.getOrElse(partitionNum, {
            val titanConf = new org.apache.commons.configuration.BaseConfiguration();
            println("开始连接Titan数据库")
            titanConf.setProperty("storage.backend","hbase")
            titanConf.setProperty("storage.hostname","192.168.40.211")
            titanConf.setProperty("storage.tablename","titan")
            val graph = TitanFactory.open(titanConf)
            graphPool += (partitionNum->graph)
            graph
        })
    }
    
    def release() = graphPool.values.foreach { 
      graph =>  graph.close() 
    }
}
