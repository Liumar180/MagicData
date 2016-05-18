package com.integrity.dataSmart.titanGraph.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.impAnalyImport.bean.Resume;
import com.integrity.dataSmart.impAnalyImport.email2solr.SolrAllUtils;
import com.integrity.dataSmart.titanGraph.pojo.Email;
import com.integrity.dataSmart.titanGraph.service.SearchQqRelationService;
import com.integrity.dataSmart.util.email.EmailUtil;
import com.integrity.dataSmart.util.solr.Email2SolrUtil;
import com.integrity.dataSmart.util.titan.TitanGraphUtil;
import com.thinkaurelius.titan.core.Cardinality;
import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanGraphQuery;
import com.thinkaurelius.titan.core.TitanIndexQuery.Result;
import com.thinkaurelius.titan.core.TitanMultiVertexQuery;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.thinkaurelius.titan.core.attribute.Text;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import com.tinkerpop.pipes.PipeFunction;
import com.tinkerpop.pipes.branch.LoopPipe.LoopBundle;

public class SearchDetailDao {
	private Logger logger = Logger.getLogger(this.getClass());
	private static final String FULL_INDEX_FIELD_NMAE = "TitanFullIndexPhantomField";//solr 全文查询字段
	private static final Integer SEARCHPAGESIZE= 50;//查询返回最大条数
	private TitanGraph graph = TitanGraphUtil.getInstance().getTitanGraph();
	private SearchQqRelationService searchQqRelationService;
	public SearchQqRelationService getSearchQqRelationService() {
		return searchQqRelationService;
	}
	public void setSearchQqRelationService(
			SearchQqRelationService searchQqRelationService) {
		this.searchQqRelationService = searchQqRelationService;
	}

	/**
	 * 全文查询节点(titan-solr)
	 * @param content
	 * @return
	 */
	public List<Vertex> getVertexByFull(String content) {
		Iterable<Result<Vertex>> it = graph.indexQuery("vertices",String.format("v.%s:%s", FULL_INDEX_FIELD_NMAE,content)).limit(SEARCHPAGESIZE).vertices();
		List<Vertex> result = new ArrayList<Vertex>();
		for (Result<Vertex> e : it) {
			result.add(e.getElement());
		}
		return result;
	}
	
	/**
	 * 多条件查询(titan-solr)
	 * @param searchMap key为节点label，value为属性-值的map
	 * @return
	 */
	public List<Vertex> getVertexByMoreProperty(Map<String, Map<String, String>> searchMap) {
		List<Vertex> result = new ArrayList<Vertex>();
		Set<Entry<String, Map<String, String>>> set =searchMap.entrySet();
		for (Entry<String, Map<String, String>> entry : set) {
			String label = entry.getKey();
			//条件拼接
			TitanTransaction tx = graph.newTransaction();
			TitanGraphQuery query = tx.query().has("label",label);
			Map<String, String> proMap = entry.getValue();
			Set<Entry<String, String>> proSet = proMap.entrySet();
			for (Entry<String, String> proEntry : proSet) {
				String pro = proEntry.getKey();
				String value = proEntry.getValue();
				query = query.has(pro,Text.CONTAINS,value);
			}
			Iterable<Vertex> it = query.limit(SEARCHPAGESIZE).vertices();
			
			//动态编译查询不科学
			/*String code = "tx.query().has(\"label\",\""+label+"\")";
			Map<String, String> proMap = entry.getValue();
			Set<Entry<String, String>> proSet = proMap.entrySet();
			for (Entry<String, String> proEntry : proSet) {
				String pro = proEntry.getKey();
				String value = proEntry.getValue();
				code += ".has(\""+pro+"\",Text.CONTAINS,\""+value+"\")";
			}
			code += ".limit(50).vertices();";
			try {
				//动态编译执行
				Iterable<Vertex> it = (Iterable<Vertex>) Eval.eval(code);
			} catch (Exception e) {
				logger.error("动态执行查询异常",e);
			}*/
			result.addAll(IteratorUtils.toList(it.iterator()));
			return result;
		}
		
		return result;
	}
	
	/**
	 * 根据属性查询人物节点
	 * @param property
	 * @param content 
	 * @return
	 */
	public List<List<Vertex>> getVertexByProperty(String property, String content){
		List<List<Vertex>> allObjs = new ArrayList<List<Vertex>>();
		try {
			Iterable<Vertex> it = graph.getVertices(property, content);
			for (Vertex personV : it) {
				List<Vertex> result = new ArrayList<Vertex>();
				result.add(personV);
				Iterable<Vertex> relIterable = personV.query().labels("own").vertices();
				result.addAll(IteratorUtils.toList(relIterable.iterator()));
				
				allObjs.add(result);
			}
		} catch (Exception e) {
			logger.error("根据属性查询人物节点异常",e);
		}
		return allObjs;
	}
	
	/**
	 * 根据属性查询人物节点(own属性)
	 * @param property
	 * @param content 
	 * @return
	 */
	public List<List<Vertex>> getVertexByOwnProperty(String property, String content){
		List<List<Vertex>> allObjs = new ArrayList<List<Vertex>>();
		try {
			//属性
			Iterable<Vertex> it = graph.getVertices(property, content);
			if(it.iterator().hasNext()){//Titan里有暂时不管
				for (Vertex proV : it) {
					String rName = null;
					if("numid".equals(property)){
						rName = searchQqRelationService.realNameWeigth(content);
					}
					Iterable<Vertex>  iterable = proV.query().labels("own").vertices();
					Iterator<Vertex> iterator = iterable.iterator();
					while (iterator.hasNext()) {
						Vertex personV = iterator.next();
						boolean flag = false;
						for (List<Vertex> list: allObjs) {
							if (list.contains(personV)) {
								flag = true;
								break;
							}
						}
						if(flag) continue;
						List<Vertex> result = new ArrayList<Vertex>();
						if(rName != null && !"".equals(rName)){
								Object names = personV.getProperty("name");
								if (names == null || names.equals("")) {
									personV.setProperty("name", rName);
								}
								graph.commit();
						}
						result.add(personV);
						Iterable<Vertex> relIterable = personV.query().labels("own").vertices();
						result.addAll(IteratorUtils.toList(relIterable.iterator()));
						
						allObjs.add(result);
					}
				}
			}else if("numid".equals(property)){//Titan里没有
				String reg = "^[0-9]*[1-9][0-9]*$";
			    boolean isQQ =  Pattern.compile(reg).matcher(content).find();
				if(isQQ){
					String realname = searchQqRelationService.realNameWeigth(content);
					if(realname !=null &&  !"".equals(realname)){
						TitanTransaction action =graph.newTransaction();
						TitanManagement tm = graph.getManagementSystem();
						PropertyKey numid = null;
						PropertyKey nickname = null;
						PropertyKey type = null;
						PropertyKey name = null;//姓名
						if(!tm.containsPropertyKey("numid")){
							 numid = tm.makePropertyKey("numid").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
						 }
						if(!tm.containsGraphIndex("numid")){
							tm.buildIndex("numid", TitanVertex.class).addKey(numid).buildCompositeIndex();
						}
						if(!tm.containsPropertyKey("nickname")){
							 nickname = tm.makePropertyKey("nickname").dataType(String.class).cardinality ( Cardinality.SET ).make();
						 }
						if(!tm.containsGraphIndex("nickname")){
								tm.buildIndex("nickname", TitanVertex.class).addKey(nickname).buildCompositeIndex();
							}
						if(!tm.containsPropertyKey("type")){
							 type = tm.makePropertyKey("type").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
						 }
						if(!tm.containsGraphIndex("type")){
							tm.buildIndex("type", TitanVertex.class).addKey(type).buildCompositeIndex();
						}
						if(!tm.containsPropertyKey("name")){
							 numid = tm.makePropertyKey("name").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
						 }
						if(!tm.containsGraphIndex("name")){
							tm.buildIndex("name", TitanVertex.class).addKey(name).buildCompositeIndex();
						}
						tm.commit();
						TitanVertex person2 = action.addVertexWithLabel("Person");// 创建人物对象
						person2.setProperty("type", "Person");
						if (realname != null && !"".equals(realname)) {
							Object names = person2.getProperty("name");
							if (names == null || "".equals(names)) {
								person2.addProperty("name", realname);
							}
						}
						TitanVertex Qq = action.addVertexWithLabel("QQ");
						Qq.addProperty("type", "IM");
						Qq.addProperty("numid", content);
						action.addEdge(null, person2, Qq, "own");
						List<Vertex> result = new ArrayList<Vertex>();
						result.add(person2);
						Iterable<Vertex> relIterable = person2.query().labels("own").vertices();
						result.addAll(IteratorUtils.toList(relIterable.iterator()));
						allObjs.add(result);
						action.commit();
						graph.commit();
					}
				}
			}
			
			
		} catch (Exception e) {
			logger.error("根据属性(own属性)查询人物节点异常",e);
		}
		return allObjs;
	}
	
	/**
	 * 根据own节点id集合查询人物
	 * @param vidList 简历节点id集合
	 * @return
	 */
	public List<List<Vertex>> getVertexByOwnVertexId(List<String> vidList ) {
		List<List<Vertex>> allObjs = new ArrayList<List<Vertex>>();
		try {
			if (vidList  == null || vidList.size() == 0) return allObjs;
			for (String vidstr : vidList) {
				Long proId = null;
				try {
					proId = Long.parseLong(vidstr);
				} catch (Exception e) {
					logger.error("vertexid转换异常异常",e);
					continue;
				}
				if (proId == null) continue;
				Vertex proV = graph.getVertex(proId);
				if (proV == null) continue;
				Iterable<Vertex>  iterable = proV.query().labels("own").vertices();
				Iterator<Vertex> iterator = iterable.iterator();
				while (iterator.hasNext()) {
					Vertex personV = iterator.next();
					boolean flag = false;
					for (List<Vertex> list: allObjs) {
						if (list.contains(personV)) {
							flag = true;
							break;
						}
					}
					if(flag) continue;
					List<Vertex> result = new ArrayList<Vertex>();
					result.add(personV);
					Iterable<Vertex> relIterable = personV.query().labels("own").vertices();
					result.addAll(IteratorUtils.toList(relIterable.iterator()));
					
					allObjs.add(result);
				}
			}
		} catch (Exception e) {
			logger.error("根据own节点id集合查询人物异常",e);
		}
		return allObjs;
	}
	
	/**
	 * 根据已知节点id查询所有关系
	 * @param personId 
	 * @param endTime 
	 * @param startTime 
	 * @return
	 */
	public List<Vertex> getEventById(long personId) {
		List<Vertex> result = null;
		try {
			result = new ArrayList<Vertex>();
			Vertex vertex = graph.getVertex(personId);
			result.add(vertex);
			
//			Iterable<Vertex> relIterable = vertex.query().labels(DataType.ALLLABEL).interval("eventtime", startTime, endTime).vertices();
			Iterable<Vertex> relIterable = vertex.query().labels(DataType.ALLLABEL).vertices();
			result.addAll(IteratorUtils.toList(relIterable.iterator()));
//			String cmd2 = "g.v(id).outE.filter{it.label!=label}.inV";
			
		}  catch (Exception e) {
			logger.error("根据已知节点id查询所有关系异常",e);
		}
		return result;
	}
	
	/**
	 * 根据已知节点id和关系类型查询关系
	 * @param personId 
	 * @param types 
	 * @param endTime 
	 * @param startTime 
	 * @return
	 */
	public List<Vertex> getEventByIdType(long personId, String[] types) {
		List<Vertex> result = null;
		try {
			result = new ArrayList<Vertex>();
			Vertex vertex = graph.getVertex(personId);
			result.add(vertex);
//			Iterable<Vertex> relIterable = vertex.query().labels(types).interval("eventtime", startTime, endTime).vertices();
			Iterable<Vertex> relIterable = vertex.query().labels(types).vertices();
//			Iterable<Vertex> relIterable = vertex.query().direction(Direction.OUT).labels(type).vertices();
			result.addAll(IteratorUtils.toList(relIterable.iterator()));
//			String cmd2 = "g.v(id).outE.filter{it.label!=label}.filter{it.label==type}.inV";
			
		}  catch (Exception e) {
			logger.error("根据已知节点id和关系类型查询关系",e);
		}
		
		
		return result;
	}
	
	/**
	 * 根据已知节点id查询关系网络
	 * @param personId
	 * @return  List数组中共有四个对象：前两个为直接存储关系，后两个为事件关联关系
	 * 			第一个为关系边，类型为：ArrayList<Edge>;第二个为边对应的节点，类型为ArrayList<Vertex>（一个边对应一个点）
	 * 			第三个为人节点，类型为：ArrayList<Vertex>;第四个为事件节点，类型为ArrayList<ArrayList<Vertex>>
	 */
	public List<Object> getRelativeById(long personId) {
		
		List<Object> result = new ArrayList<Object>();
		//查询出节点
		Vertex v1 = graph.getVertex(personId);
		
		/*直接查询存储的关系*/
		List<Edge> friendEdges = new ArrayList<Edge>();
		List<Vertex> friends = new ArrayList<Vertex>();
		result.add(friendEdges);
		result.add(friends);
		Iterable<Edge> edgeIterable = v1.query().direction(Direction.OUT).labels(DataType.RELATIONAL).edges();
//		Iterable<Vertex> vertexIterable = v1.query().labels(DataType.RELATIONAL).vertices();
		for (Edge edge : edgeIterable) {
			Vertex friend = edge.getVertex(Direction.IN);
			if (friend != null) {
				friendEdges.add(edge);
				friends.add(friend);
			}
		}
		
		/*根据事件查询关系*/
		ArrayList<ArrayList<Vertex>> eventList = new ArrayList<ArrayList<Vertex>>();
		ArrayList<Vertex> personList = new ArrayList<Vertex>();
		//查询出所有邻居节点
		TitanMultiVertexQuery mq = graph.multiQuery();
		mq.direction(Direction.BOTH).labels(DataType.ALLLABEL);
		int count = 0;
		for (Vertex f : v1.getVertices(Direction.BOTH, DataType.ALLLABEL)) {
			mq.addVertex((TitanVertex) f);
			count++;
		}
		result.add(personList);
		result.add(eventList);
		if (count == 0) {
			return result;
		}

		Map<String, TitanVertex> dataMap = new HashMap<String, TitanVertex>();
		Map<String, TitanVertex> vertexMap = new HashMap<String, TitanVertex>();
		Map<String, ArrayList<Vertex>> eventMap = new HashMap<String, ArrayList<Vertex>>();

		Map<TitanVertex, Iterable<TitanVertex>> results = mq.vertices();

		for (Iterator iterator = results.keySet().iterator(); iterator.hasNext();) {
			TitanVertex tv = (TitanVertex) iterator.next();
			
			Iterable<TitanVertex> tv2 = results.get(tv);

			for (Iterator iterator2 = tv2.iterator(); iterator2.hasNext();) {
				TitanVertex type = (TitanVertex) iterator2.next();
				
				String key1 = type.getId().toString() + tv.getProperty("type");
				if (!(type.getId().toString().equals(v1.getId().toString()))) {
					if (!dataMap.containsKey(key1)) {
						dataMap.put(key1, type);
						if (!vertexMap.containsKey(type.getId().toString())) {
							vertexMap.put(type.getId().toString(), type);
						}
						if (!eventMap.containsKey(type.getId().toString())) {
							ArrayList<Vertex> tt = new ArrayList<Vertex>();
							tt.add(tv);
							eventMap.put(type.getId().toString(), tt);
						}
						/**
						 * modify by liubaofen 2016/02/18
						 * 查询关系网络时，将在关系边显示的类型中，添加事件类型的显示；
						 **/
						else {
							ArrayList<Vertex> tt = eventMap.get(type.getId().toString());
							tt.add(tv);
							eventMap.put(type.getId().toString(), tt);
						}

					}
				}
			}
		}

		for (Iterator iterator = vertexMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			personList.add(vertexMap.get(key));
			eventList.add(eventMap.get(key));
		}

		return result;
	}

	/**
	 * 根据已知节点id和关系标签查询相关关系(二次查询)
	 * @param id 
	 * @param type
	 * @return
	 */
	public List<Vertex> searchRelationByIdType(long id, String type){
		List<Vertex> result = null;
		try {
			result = new ArrayList<Vertex>();
			Iterable<Vertex> relIterable = null;
			if(DataType.EMAILTO.equals(type)){
				//查询收件人和抄送
				String[] types = {DataType.EMAILTO,DataType.EMAILCC};
				relIterable = graph.getVertex(id).query().labels(types).vertices();
			}else {
				relIterable = graph.getVertex(id).query().labels(type).vertices();
			}
			
			result = IteratorUtils.toList(relIterable.iterator());
//			String cmd = "g.v(id).in(lable)";
			
		}  catch (Exception e) {
			logger.error("根据已知节点id和关系标签查询相关关系(二次查询)异常",e);
		}
		
		return result;
	}
	
	/**
	 * 根据id查询人物节点
	 * @param personId
	 * @return
	 */
	public List<Vertex> getVertexsById(long personId){
		List<Vertex> result = null;
		try {
			result = new ArrayList<Vertex>();
			TitanVertex vertex = graph.getVertex(personId);
			result.add(vertex);
			
			Iterable<Vertex> relIterable = vertex.query().labels("own").vertices();
			result.addAll(IteratorUtils.toList(relIterable.iterator()));
		} catch (Exception e) {
			logger.error("根据属性查询人物节点异常",e);
		}
		return result;
	}
	
	/**
	 * 根据人物节点查询own节点
	 * @param person
	 * @return
	 */
	public List<Vertex> getPersonOwnVertexsById(Vertex person){
		List<Vertex> result = new ArrayList<Vertex>();
		try {
			Iterable<Vertex> relIterable = person.query().labels("own").vertices();
			result.addAll(IteratorUtils.toList(relIterable.iterator()));
		} catch (Exception e) {
			logger.error("根据人物节点查询own节点异常",e);
		}
		return result;
	}
	
	/**
	 * 根据id查询节点
	 * @param vertexId
	 * @return
	 */
	public Vertex getVertexById(long vertexId){
		TitanVertex vertex = null;
		try {
			vertex = graph.getVertex(vertexId);
		} catch (Exception e) {
			logger.error("根据id查询节点",e);
		}
		return vertex;
	}
	
	
	/**
	 * 根据id查询人物间关系
	 * @param personId
	 * @return
	 */
	public List<List> getRelationById(long personId1,long personId2){
		List<List> result = null;
		try{
		
		Vertex vt1 = graph.getVertex(personId1);
		Vertex vt2 = graph.getVertex(personId2);
		long count1 = vt1.query().labels(DataType.ALLLABEL).count();
		long count2 = vt2.query().labels(DataType.ALLLABEL).count();
		long tmp = 0;
		if(count1>count2){
			tmp=personId1;
			personId1=personId2;
			personId2=tmp;
		}
		
		final Vertex v1 = graph.getVertex(personId1);
		
		final Vertex v2 = graph.getVertex(personId2);
		
		final GremlinPipeline pipe = new GremlinPipeline(v1)
				.as("person")
				.both(DataType.ALLLABEL)
				.loop("person",
						new PipeFunction<LoopBundle<Vertex>, Boolean>() {
							@Override
							public Boolean compute(LoopBundle<Vertex> bundle) {
								return bundle.getLoops() <= 8
										&& bundle.getObject() != v2;
							}
						}).path();
		List<Vertex> vdatas = new ArrayList<Vertex>();
		List<Edge> edatas = new ArrayList<Edge>();
		
		if (pipe.hasNext()) {
			final ArrayList<Vertex> shortestPath = (ArrayList<Vertex>) pipe
					.next();
			for (final Vertex v : shortestPath) {
				vdatas.add(v);
				System.out.print(" -> " + v.getProperty("name"));
			}
		}
		
		for (int i = 0; i < vdatas.size(); i++) {
			if(i>vdatas.size()-2){
				break;
			}
			Vertex vtmp1 = vdatas.get(i);
			Vertex vtmp2 = vdatas.get(i+1);
			
			Iterable<Edge> edges = vtmp1.getEdges(Direction.BOTH,DataType.ALLLABEL);
			for (Iterator iterator = edges.iterator(); iterator.hasNext();) {
				Edge edge = (Edge) iterator.next();
				if(edge.getVertex(Direction.OUT).getId().equals(vtmp2.getId()) || edge.getVertex(Direction.IN).getId().equals(vtmp2.getId())){
					edatas.add(edge);
				}
			}
		}
		
		if(vdatas.size()>0){
			result = new ArrayList<>();
			result.add(vdatas);
			result.add(edatas);
		}
		
		}catch(Exception e){
			logger.error("根据id查询人物关系异常",e);
		}
		return result;
	}
	
	/**
	 * 根据人物ID查询群组关系
	 * @param personId 人物id
	 * @return List中的每个List的第一个为群组后面为群成员
	 */
	public List<List<Vertex>> findGroupRelativeById(long personId) {
		//用于群组去重
		List<String> temp = new ArrayList<String>();
		List<List<Vertex>> groups = new ArrayList<List<Vertex>>();
		Vertex person = graph.getVertex(personId);
		Iterable<Vertex> it = person.query().labels("own").vertices();
		for (Vertex vertex : it) {
			if (DataType.IM.equals(vertex.getProperty("type"))) {//人物下属性是IM的点
				Iterable<Vertex> groupIt = vertex.query().labels(DataType.GROUP).vertices();
				for (Vertex group : groupIt) {
					String groupNum = group.getProperty("groupnum");
					if (!temp.contains(groupNum)) {
						List<Vertex> groupList = new ArrayList<Vertex>();
						groupList.add(group);
						
						Iterable<Vertex> qqIt = group.query().labels(DataType.GROUP).vertices();
						groupList.addAll(IteratorUtils.toList(qqIt.iterator()));
						
						groups.add(groupList);
						temp.add(groupNum);
					}
				}
			}
		}
		return groups;
	}
	
	/**
	 * 根据节点id查询邮件
	 * @param vertexId
	 * @return
	 */
	public Email getEmailByID(long vertexId){
		Email email = null;
		try {
			Email2SolrUtil.connection();
			email = Email2SolrUtil.getEmailByVid(vertexId);
		} catch (Exception e) {
			logger.error("根据节点id查询邮件异常",e);
		}finally{
			Email2SolrUtil.close();
		}
		EmailUtil.emailUtil(email);
		return email;
	}
	/**
	 * 根据节点vertexId查询简历
	 * @param vertexId
	 * @return
	 */
	public Resume getResumeByVid(String vertexId){
		Resume resume = null;
		try {
			resume = SolrAllUtils.getResumeByVid(vertexId);
		} catch (Exception e) {
			logger.error("根据节点id查询邮件异常",e);
		}/*finally{
			SolrAllUtils.close();
		}*/
		return resume;
	}

	/**
	 * 根据条件查询邮件(只全文)
	 * @param content
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Email> getEmailsFullText(String content, int pageNo, int pageSize) {
		List<Email> beans = null;
		try {
			Email2SolrUtil.connection();
			beans = Email2SolrUtil.getEmailsFullText(content,pageNo,pageSize);
			if (beans!= null && beans.size()>0) {
				for (Email email : beans) {
					EmailUtil.emailUtil(email);
				}
			}
		} catch (Exception e) {
			logger.error("根据条件查询邮件异常",e);
		}finally{
			Email2SolrUtil.close();
		}
		return beans;
	}

	/**
	 * 根据条件查询邮件 分页
	 * @return
	 */
	public List<Email> getEmailsByCondition(int pageNo, int pageSize, String condition) {
		List<Email> beans = null;
		try {
			Email2SolrUtil.connection();
			beans = Email2SolrUtil.getEmailsByCondition(condition,pageNo,pageSize);
			if (beans!= null && beans.size()>0) {
				for (Email email : beans) {
					EmailUtil.emailUtil(email);
				}
			}
		} catch (Exception e) {
			logger.error("根据条件查询邮件异常",e);
		}finally{
			Email2SolrUtil.close();
		}
		return beans;
	}

	/**
	 * 查询目录树数据
	 * @return
	 */
	public List<Map<String, String>> getTreeJson() {
		List<Map<String,String>> root = new ArrayList<Map<String,String>>();
		try {
			Email2SolrUtil.connection();
			root = Email2SolrUtil.recursionSearchTree(root, "root");
		} catch (Exception e) {
			logger.error("查询邮件目录树数据异常",e);
		}finally{
			Email2SolrUtil.close();
		}
		
		return root;
	}

	/**
	 * 修改节点属性
	 * @param id
	 * @param property
	 * @param value
	 * @return
	 */
	public String updateVertex(Long id, String property, String value) {
		graph.rollback();
		Vertex vertex = graph.getVertex(id);
		String oldValue = vertex.getProperty(property);
		vertex.setProperty(property, value);
		graph.addVertex(vertex);
		graph.commit();
		return oldValue;
	}
	

}
