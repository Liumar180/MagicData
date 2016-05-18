package com.integrity.dataSmart.titanGraph.util;

import com.thinkaurelius.titan.core.TitanGraph;

public class ImpQqFriendsToTitan implements Runnable{
	TitanGraph graph;
    
    public ImpQqFriendsToTitan(TitanGraph graph){
        this.graph = graph;
    }
    
    @Override
	public void run() {
		graph.commit();
	}

}
