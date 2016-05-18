package otheer;


import com.thinkaurelius.titan.core.EdgeLabel;
import com.thinkaurelius.titan.core.Multiplicity;
import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.attribute.Geoshape;
import com.thinkaurelius.titan.core.schema.ConsistencyModifier;
import com.thinkaurelius.titan.core.schema.TitanGraphIndex;
import com.thinkaurelius.titan.core.schema.TitanManagement;





import org.apache.commons.configuration.BaseConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.io.File;

public class createIndex {
	public static void main(String args[]) {
		
		System.out.println("开始连接数据库。。");
        TitanFactory.Builder config = TitanFactory.build();
        config.set("storage.backend", "hbase");
        config.set("storage.hostname", "192.168.40.33");
        config.set("storage.tablename","titan");
        TitanGraph graph = config.open();
        System.out.println("数据库已连接");
       
		//Create Index
        boolean uniqueNameCompositeIndex = true;
		
    	System.out.println("开始创建Index");
        TitanManagement mgmt = graph.openManagement();
        
        final PropertyKey personID = mgmt.makePropertyKey("personID").dataType(String.class).make();
        TitanManagement.IndexBuilder nameIndexBuilder_1 = mgmt.buildIndex("personID", Vertex.class).addKey(personID);
        final PropertyKey emailID = mgmt.makePropertyKey("emailID").dataType(String.class).make();
        TitanManagement.IndexBuilder nameIndexBuilder_2 = mgmt.buildIndex("emailID", Vertex.class).addKey(emailID);
        if (uniqueNameCompositeIndex){
        	nameIndexBuilder_1.unique();
        	nameIndexBuilder_2.unique();
        }
        	
        TitanGraphIndex namei_1 = nameIndexBuilder_1.buildCompositeIndex();
        TitanGraphIndex namei_2 = nameIndexBuilder_2.buildCompositeIndex();
        
        mgmt.setConsistency(namei_1, ConsistencyModifier.FORK);
        mgmt.setConsistency(namei_2, ConsistencyModifier.FORK);
        
        final PropertyKey phoneNum = mgmt.makePropertyKey("phoneNum").dataType(String.class).make();

        final PropertyKey emailCount = mgmt.makePropertyKey("emailCount").dataType(String.class).make();

        /*if (null != mixedIndexName)
            mgmt.buildIndex("edges", Edge.class).addKey(reason).addKey(place).buildMixedIndex(mixedIndexName);*/

        mgmt.makeEdgeLabel("has").multiplicity(Multiplicity.MANY2ONE).make();

        mgmt.makeVertexLabel("person").make();
        mgmt.makeVertexLabel("email").make();

        System.out.println("提交");
        mgmt.commit();
    }

}
