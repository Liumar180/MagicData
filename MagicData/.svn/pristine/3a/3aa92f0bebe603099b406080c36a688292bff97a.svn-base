package com.integrity.dataSmart.titanGraph.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.titanGraph.bean.GroupMembersBean;
import com.integrity.dataSmart.titanGraph.bean.QQFriend;
import com.integrity.dataSmart.titanGraph.bean.QqGroupsBeans;
import com.integrity.dataSmart.titanGraph.dao.SearchQqRelationDao;
import com.integrity.dataSmart.titanGraph.util.TitanLabelUtils;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.thinkaurelius.titan.core.Cardinality;
import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanEdge;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.VertexLabel;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class SearchQqRelationServiceImp implements SearchQqRelationService{
	private SearchQqRelationDao searchQqRelationDao;
	public SearchQqRelationDao getSearchQqRelationDao() {
		return searchQqRelationDao;
	}
	public void setSearchQqRelationDao(SearchQqRelationDao searchQqRelationDao) {
		this.searchQqRelationDao = searchQqRelationDao;
	}
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DataType.DATEFORMATSTR);
	private ObjectMapper mapper = JacksonMapperUtil.getObjectMapper();
	
	@Override
	public Map<String,Object> queryQQFriendsJson(HttpSession hs,List<List<TitanVertex>> list) {
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		if(nodeIdList==null){
			nodeIdList = new ArrayList<Object>();
		}
		Map<String,Object> root = new HashMap<String,Object>();
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
		root.put("nodes", nodeList);
		root.put("edges", edgeList);
		int source = -1;
		List<TitanVertex> firstL = list.get(0);
		TitanVertex qqV = firstL.get(0);
		String qq = null;
		try {
			qq = qqV.getProperty("numid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object orgQQId = qqV.getId();
		int orgQQIdIndex = nodeIdList.indexOf(orgQQId);
		Map<String, Object> nodeMap = new HashMap<String, Object>();
		for (int j = 1; j < list.size(); j++) {
			List<TitanVertex> innerList = list.get(j);
			int size = innerList.size();
			for (int i = 0; i < size; i++) {
				TitanVertex vertex = innerList.get(i);
				Object id = vertex.getId();
				nodeMap = new HashMap<String, Object>();
				nodeMap.put("id", vertex.getId());
				if (i == 0) {
					int index = nodeIdList.indexOf(id);
					String numid = ""; 
					if (index > -1) {
						numid = vertex.getProperty("numid");
					} else {
						numid = vertex.getProperty("numid");
						nodeMap.put("name", numid);
						List<String> values = vertex.getProperty("nickname");
						String nickname = getNickname(values);
						nodeMap.put("nickname", nickname);
						nodeMap.put("image", "/img/QQ.png");
						nodeMap.put("type", "QQ");
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						source = nodeIdList.size() - 1;
					}
					String nickname1 = searchQqRelationDao.queryQqFriends(qq, numid); 
					String nickname2 = searchQqRelationDao.queryQqFriends(numid, qq); 
					addLinkHandler(orgQQIdIndex, source, "nickname:"+nickname1, "out", edgeList);
					addLinkHandler(source, orgQQIdIndex, "nickname:"+nickname2, "out", edgeList);
				} else {
					int index = nodeIdList.indexOf(id);
					if (index > -1) {
					} else {
						nodeMap.put("name", vertex.getProperty("name"));
						nodeMap.put("idcard", vertex.getProperty("idcard"));
						nodeMap.put("county", vertex.getProperty("county"));
						nodeMap.put("type", "Person");
						nodeMap.put("image", "img/Person.png");
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						index = nodeIdList.size() - 1;
					}
					addLinkHandler(source, index, "own", "out", edgeList);
				}
			}
		}
		return root;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<List<TitanVertex>> queryQqFriends(String qq, TitanTransaction action,TitanManagement tm) {
		List<QQFriend> list = searchQqRelationDao.queryQqFriends(qq);
		List<TitanVertex> singleList = new ArrayList<TitanVertex>();
		List<List<TitanVertex>> friendList = new ArrayList<List<TitanVertex>>();
		//拿到本人QQ和Person
		singleList = getQqAndPersonVertex(qq,"",realNameWeigth(qq),action,tm);
		friendList.add(singleList);
		for(QQFriend qqFriend : list){
			String qqNum = qqFriend.getFriendNum();
			String nickname = qqFriend.getFriendRemark();
			String realname = realNameWeigth(qqNum);
			singleList = getQqAndPersonVertex(qqNum,nickname,realname,action,tm);
			friendList.add(singleList);
		}
		List<TitanVertex> listOrg = friendList.get(0);
		TitanVertex perOrg = listOrg.get(1);
		boolean isTrue = true;
		Iterator<Vertex> coh = null;
		try {
			coh = perOrg.query().labels(new String[] { "relational" }).has("relationtype",DataType.RELATIONTYPE_QQ).vertices().iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=1;i<friendList.size();i++){
			TitanVertex qqFriV = friendList.get(i).get(0);
			TitanVertex perFriV = friendList.get(i).get(1);
			String currentQqNum = qqFriV.getProperty("numid");
			String nicknameCurr = searchQqRelationDao.queryQqFriends(qq,currentQqNum);
			while (coh.hasNext()) {
				TitanVertex PP = (TitanVertex) coh.next();
				if (PP.getId() == perFriV.getId()) {
					System.out.println("存在relational边，且relationtype为1");
					isTrue = false;
					break;
				}
			}
			if (isTrue) {
				Edge s = action.addEdge(null, perOrg, perFriV,"relational");
				s.setProperty("relationtype",DataType.RELATIONTYPE_QQ);
				s.setProperty("nickname", nicknameCurr);
			}
		}
		/*ImpQqFriendsToTitan m = new ImpQqFriendsToTitan(graph);
		Thread t = new Thread(m);
		t.start();*/
		return friendList;
	}
	
	/**
	 * 获取QQ和Person节点List
	 * @param qqNum
	 * @param nickname
	 * @param realname
	 * @param graph
	 * @return
	 */
	public List<TitanVertex> getQqAndPersonVertex(String qqNum,String nickname1,String realname,TitanTransaction action,TitanManagement tm){
		/****声明对象属性****/
		 PropertyKey numid = null;//qq号
		 PropertyKey nickname = null;//昵称
		 PropertyKey type = null;//类型
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
		 if(!tm.containsPropertyKey("name")){
			 type = tm.makePropertyKey("name").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsGraphIndex("name")){
				tm.buildIndex("name", TitanVertex.class).addKey(name).buildCompositeIndex();
			 }
		 if(!tm.containsPropertyKey("type")){
			 type = tm.makePropertyKey("type").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsGraphIndex("type")){
				tm.buildIndex("type", TitanVertex.class).addKey(type).buildCompositeIndex();
			 }
		List<TitanVertex> singleList = new ArrayList<TitanVertex>();
		Object QqIte = null;
		QqIte = action.query().has("numid", qqNum).vertices();// 通过qq号查询
		TitanVertex tmp = null;
		boolean t =  false;
		if(QqIte != null){
			t = ((Iterable) QqIte).iterator().hasNext();
		}
		while (t) {
			tmp = (TitanVertex) ((Iterable) QqIte).iterator().next();
			break;
		}
		if (tmp != null) {
			if(tmp.getVertexLabel().toString().equals("QQ") && tmp.getProperty("type").toString().equals("IM")){
				//将获取到的qq对象的昵称进行补充
				try {
					List<String> nicknames = tmp.getProperty("nickname");
					boolean isWith = false;
					if(nickname1 !=null && !"".equals(nickname1)){
						for(String s:nicknames){
							if(s.equals(nickname1)){
								isWith = true;
								break;
							}
						}
						if(!isWith){
						tmp.addProperty("nickname", nickname1);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				TitanVertex person = null;
				Object perIte = null;
				boolean f =  false;
				perIte = tmp.query().labels(new String[] { "own" }).vertices();
				if(perIte != null){
					f = ((Iterable) perIte).iterator().hasNext();
				}
				while (f) {
					person = (TitanVertex) ((Iterable) perIte).iterator().next();
					break;
				}
				if (person != null) {
					if (realname != null && !realname.equals("")) {
						Object names = person.getProperty("name");
						if (names == null || "".equals(names)) {
							person.addProperty("name", realname);
						}
					}
				}else{
					person = action.addVertexWithLabel("Person");// 创建人物对象
					person.addProperty("type", "Person");
					if (realname != null && !"".equals(realname)) {
						Object names = person.getProperty("name");
						if (names == null || "".equals(names)) {
							person.addProperty("name", realname);
						}
					}
					action.addEdge(null, person, tmp, "own");
				}
				singleList.add(tmp);
				singleList.add(person);
			}
		}else{
			//QQ不存在(当做Person也不存在处理)
			TitanVertex person2 = action.addVertexWithLabel("Person");// 创建人物对象
			person2.setProperty("type", "Person");
			if (realname != null && !"".equals(realname)) {
				Object names = person2.getProperty("name");
				if (names == null || "".equals(names)) {
					person2.addProperty("name", realname);
				}
			}
			TitanVertex Qq = action.addVertexWithLabel("QQ");// 创建QQ
			Qq.addProperty("type", "IM");
			Qq.addProperty("numid", qqNum);
			if(nickname1!=null&&!"".equals(nickname1)){
				try {
					Qq.addProperty("nickname", nickname1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			action.addEdge(null, person2, Qq, "own");
			
			singleList.add(Qq);
			singleList.add(person2);
		}
		return singleList;
	}
	
	@Override
	public List<QQFriend> queryQqFriendsFromOrcl(String qq) {
		List<QQFriend> list = searchQqRelationDao.queryQqFriends(qq);
		return list;
	}

	/**
	 * 添加边
	 */
	private void addLinkHandler(long source, long target, String relation,
			String direction, List<Map<String, Object>> edgeList) {
		Map<String, Object> linksMap = new HashMap<String, Object>();
		linksMap.put("source", source);
		linksMap.put("target", target);
		linksMap.put("relation", relation);
		linksMap.put("direction", direction);
		linksMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
		edgeList.add(linksMap);
	}

	/**
	 * json 转换
	 * 
	 * @param root
	 * @return
	 */
	private String getJson(Map<String, Object> root) {
		String json = null;
		try {
			json = mapper.writeValueAsString(root);
		} catch (JsonProcessingException e) {
//			logger.error("转换json异常", e);
		}
		return json;
	}

	/**
	 * QQ 昵称转换
	 * 
	 * @param values
	 * @return
	 */
	private String getNickname(List<String> values) {
		if (values == null) {
			return "";
		}
		String nickname = "";
		for (String name : values) {
			nickname += name + " | ";
		}
		if (!"".equals(nickname)) {
			nickname = nickname.substring(0, nickname.length() - 3);
		}
		return nickname;
	}

	@Override
	public List<QqGroupsBeans> queryQqGroups(String qq) {
		List<Object> groups= searchQqRelationDao.queryQqGroups(qq);
		List<QqGroupsBeans> qqgroupsbeans = new ArrayList<QqGroupsBeans>();
		for(Object s:groups){
			Object[] groupsArrys = (Object[]) s;
			QqGroupsBeans ogb = new QqGroupsBeans();
			if(groupsArrys[0] != null){
				ogb.setGroupNum(groupsArrys[0].toString());
			}
			if(groupsArrys[1] != null){
			ogb.setGroup_Name(groupsArrys[1].toString());
			}
			if(groupsArrys[2] != null){
			ogb.setCreate_Time(groupsArrys[2].toString());
			}
			if(groupsArrys[3] != null){
			ogb.setMyMark(groupsArrys[3].toString());
			}
			if(groupsArrys[4] != null){
			ogb.setNumid(groupsArrys[4].toString());
			}
			if(groupsArrys[5] != null){
			ogb.setOwner_Qq(groupsArrys[5].toString());
			}
			if(groupsArrys[6] != null){
			ogb.setOwner_Name(groupsArrys[6].toString());
			}
			if(groupsArrys[7] != null){
			ogb.setGroup_Desc(groupsArrys[7].toString());
			}
			qqgroupsbeans.add(ogb);
		}
		return qqgroupsbeans;
	}

	@Override
	public HashMap<String,Object> findGroupRelativeByqqNum(HttpSession hs,String qq,TitanTransaction action) {
		@SuppressWarnings("unchecked")
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		List<List<TitanVertex>> titanGroup = searchQqRelationDao.findGroupRelativeByqqNum(qq,action);
		
		HashMap<String,Object> root = new HashMap<String,Object>();
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
		root.put("nodes", nodeList);
		root.put("edges", edgeList);
		int index = -1;
		int source = -1;
		String numid = null;
		for (int j = 0; j < titanGroup.size(); j++) {
			List<TitanVertex> list = titanGroup.get(j);
			int size = list.size();
			String ownerqq = "";
			for (int i = 0; i < size; i++) {
				TitanVertex vertex = list.get(i);
				Object id = vertex.getId();
				
				Map<String, Object> nodeMap = new HashMap<String, Object>();
				nodeMap.put("id", vertex.getId());
				if (i == 0) {
					numid = vertex.getProperty("numid");
					source = nodeIdList.indexOf(id);
					if (source == -1){
						nodeMap.put("name", numid); 
						String[] values = vertex.getProperty("nickname");
						String nickname = TitanLabelUtils.getNickname(values);
						nodeMap.put("nickname", nickname);
						nodeMap.put("image", "/img/QQ.png");
						nodeMap.put("type", "QQ");
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						source = nodeIdList.size()-1;
					}
					
				}else {
					String groupN = vertex.getProperty("groupnum");
					List<GroupMembersBean> MremarkList = searchQqRelationDao.findMemberRemark(groupN, qq);
					GroupMembersBean Mremark = null;
					if(MremarkList != null && MremarkList.size() != 0){
						Mremark = MremarkList.get(0);
					}
					index = nodeIdList.indexOf(id);
					if(index == -1){
						ownerqq = vertex.getProperty("ownerqq");
						nodeMap.put("num", vertex.getProperty("groupnum"));
						nodeMap.put("image", "/img/Group.png");
						nodeMap.put("type", "Group");
						try {
							nodeMap.put("createtime", simpleDateFormat.format(new Date(Long.parseLong(vertex.getProperty("createtime")+"000"))));
						} catch (Exception e) {
							nodeMap.put("createtime", "");
						}
						nodeMap.put("ownerqq", vertex.getProperty("ownerqq"));
						nodeMap.put("name", vertex.getProperty("groupname"));
						nodeMap.put("groupdesc", vertex.getProperty("groupdesc"));
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						index = nodeIdList.size()-1;
					}
					
					if (numid.equals(ownerqq)) {
						TitanLabelUtils.addLinkHandler(source, index, "groupOwner", "out", edgeList);
						if(Mremark != null){
							TitanLabelUtils.addLinkNickNameHandler(source, index, Mremark.getMemberRemark(), "out", edgeList);
						}
					}else {
						TitanLabelUtils.addLinkHandler(source, index, "qq", "out", edgeList);
						if(Mremark != null){
							TitanLabelUtils.addLinkNickNameHandler(source, index, Mremark.getMemberRemark(), "out", edgeList);
						}
					}
				}
			}
		}
		return root;
	}
	
	public String ReformGroups(List<QqGroupsBeans> groupsBeans,String qq,String name1,TitanTransaction action,TitanManagement tm){
		 PropertyKey numid = null;
		 PropertyKey nickname = null;
		 PropertyKey type = null;
		 PropertyKey groupnum = null;
		 PropertyKey createtime = null;
		 PropertyKey groupname = null;
		 PropertyKey ownerqq = null;
		 PropertyKey groupdesc = null;
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
		 if(!tm.containsPropertyKey("groupnum")){
			 groupnum = tm.makePropertyKey("groupnum").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsGraphIndex("groupnum")){
				tm.buildIndex("groupnum", TitanVertex.class).addKey(groupnum).buildCompositeIndex();
			 }
		 if(!tm.containsPropertyKey("createtime")){
			 createtime = tm.makePropertyKey("createtime").dataType(Long.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("groupname")){
			 groupname = tm.makePropertyKey("groupname").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("ownerqq")){
			 ownerqq = tm.makePropertyKey("ownerqq").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("groupdesc")){
			 groupdesc = tm.makePropertyKey("groupdesc").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		for(QqGroupsBeans beans:groupsBeans){
			String numid1  = qq;
			String memberMark = beans.getMyMark();
			String groupnum1 = beans.getGroupNum();//群号
			
			String ctime = beans.getCreate_Time();
			Long createtime1 = 0L;
			if(ctime != null && !ctime.equals("")){
				createtime1 = Long.valueOf(ctime);
			}
			
			String ownerqq1 = beans.getOwner_Qq();
			String groupname1 = beans.getGroup_Name();
			String groupdesc1 = beans.getGroup_Desc();
			String grouptype = "2";//群组类型
			String nickname1 = memberMark;
			
			boolean exception = false;
			Object QqIte = null;
			if(numid1 != null && !"".equals(numid1)){
				QqIte = action.query().has("numid",numid1).vertices();
				}
			TitanVertex tmp = null;
				boolean t =  false;
				if(QqIte != null){
					t = ((Iterable) QqIte).iterator().hasNext();
				}
				if(t){
				tmp = (TitanVertex)((Iterable)QqIte).iterator().next();
				Set<String> s = tmp.getPropertyKeys();
		       if(s != null){
		    	   boolean is = false;
					if(tmp.getVertexLabel().toString().equals("QQ")&&tmp.getProperty("type").toString().equals("IM")){
						is = true;
					}
				if(is){
					//获取人物信息？
					TitanVertex person =null;
					try{
					person = (TitanVertex)tmp.query().labels(new String[]{"own"}).vertices().iterator().next();
					}catch(java.util.NoSuchElementException e){
					e.printStackTrace();
					}
					if(person!=null){
					if(name1 != null && !name1.equals("")){
						Object names = person.getProperty("name");
						if(names ==null || names.equals("")){
							person.addProperty("name",name1);
						}
					}
					}
					List<String> nicknames = tmp.getProperty("nickname");
					boolean isWith = false;
					if(nickname1 !=null && !"".equals(nickname1)){
						for(String ss:nicknames){
							if(ss.equals(nickname1)){
								isWith = true;
								break;
							}
						}
						if(!isWith){
						tmp.addProperty("nickname", nickname1);
						}
					}
					
				TitanVertex group =null;
				try{
				Iterable<TitanVertex> groups = action.query().has("groupnum",groupnum1).vertices();
					
				Iterator<TitanVertex> gs = groups.iterator();
				if(gs.hasNext()){
						group = gs.next();
									
				if(group !=null){
					if(group.getProperty("groupnum").equals(groupnum1) && group.getProperty("type").equals("Group")){
	                    boolean isTrue = true;
						Iterator<Vertex> coh = tmp.query().labels(new String[]{"group"}).vertices().iterator();
						while(coh.hasNext()){
							TitanVertex GROUP = (TitanVertex) coh.next();
							if(GROUP == group){
								isTrue = false;
							}
						}
						if(isTrue){
							Edge e = action.addEdge(null, group, tmp, "group");
							e.setProperty("grouptype", grouptype);
							if(nickname1 != null && !nickname1.equals("")){
								e.setProperty("nickname", nickname1);
							}
						}
					}else{
						TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
						QGroup.addProperty("type", "Group");
						QGroup.addProperty("groupnum", groupnum1);
						if(ownerqq1 != null && !ownerqq1.equals("")){
							QGroup.addProperty("ownerqq", ownerqq1);
						}
						if(groupname1 != null && !groupname1.equals("")){
							QGroup.addProperty("groupname", groupname1);
						}
						if(groupdesc1 != null && !groupdesc1.equals("")){
							QGroup.addProperty("groupdesc", groupdesc1 );
						}
						if(createtime1 != null && !createtime1.equals("")){
							QGroup.addProperty("createtime", createtime1 );
						}
						
						Edge e = action.addEdge(null, QGroup, tmp, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname1 != null && !nickname1.equals("")){
							e.setProperty("nickname", nickname1);
						}
					}
	
				}else{
					TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
					QGroup.addProperty("type", "Group");
					QGroup.addProperty("groupnum", groupnum1);
					if(ownerqq1 != null && !ownerqq1.equals("")){
						QGroup.addProperty("ownerqq", ownerqq1);
					}
					if(groupname1 != null && !groupname1.equals("")){
						QGroup.addProperty("groupname", groupname1);
					}
					if(groupdesc1 != null && !groupdesc1.equals("")){
						QGroup.addProperty("groupdesc", groupdesc1 );
					}
					if(createtime1 != null && !createtime1.equals("")){
						QGroup.addProperty("createtime", createtime1 );
					}
					Edge e = action.addEdge(null, QGroup, tmp, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname1 != null && !nickname1.equals("")){
						e.setProperty("nickname", nickname1);
					}
				}
			}else{
				TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
				QGroup.addProperty("type","Group");
				QGroup.addProperty("groupnum", groupnum1);
				if(ownerqq1 != null && !ownerqq1.equals("")){
					QGroup.addProperty("ownerqq", ownerqq1);
				}
				if(groupname1 != null && !groupname1.equals("")){
					QGroup.addProperty("groupname", groupname1);
				}
				if(groupdesc1 != null && !groupdesc1.equals("")){
					QGroup.addProperty("groupdesc", groupdesc1 );
				}
				if(createtime1 != null && !createtime1.equals("")){
					QGroup.addProperty("createtime", createtime1 );
				}
				Edge e = action.addEdge(null,QGroup,tmp,"group");
				e.setProperty("grouptype", grouptype);
				if(nickname1 != null && !nickname1.equals("")){
					e.setProperty("nickname", nickname1);
				}
			}
			}catch(java.util.NoSuchElementException e){
			e.printStackTrace();
			}
				}
	    }
		}else{
			TitanVertex Qq3 = null;
			if(numid1 != null && !"".equals(numid1)){
				TitanVertex personNn = action.addVertexWithLabel("Person");
				personNn.addProperty("type", "Person");
				//人物信息补充？
				if(name1 != null && !name1.equals("")){
					personNn.addProperty("name",name1);
				}
				
				Qq3 = action.addVertexWithLabel("QQ");
				Qq3.addProperty("type","IM");
	
				if(numid1!=null && !"".equals(numid1)){
					Qq3.addProperty("numid",numid1);
				}
				if(nickname1 != null && !nickname1.equals("")){
					Qq3.addProperty("nickname", nickname1);
				}
				
				action.addEdge(null, personNn, Qq3, "own");
				
			}
			if(groupnum1 != null && !groupnum1.equals("")){
			TitanVertex group =null;
			try{
			@SuppressWarnings("unchecked")
			Iterable<TitanVertex> groups = action.query().has("groupnum",groupnum1).vertices();
			Iterator<TitanVertex> gs1 = groups.iterator();
			if(gs1.hasNext()){
				group = gs1.next();
				if(group !=null){
					if(group.getProperty("groupnum").equals(groupnum1) && group.getProperty("type").equals("Group")){
						if(Qq3 != null){
						Edge e = action.addEdge(null, group, Qq3, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname1 != null && !nickname1.equals("")){
							e.setProperty("nickname", nickname1);
						}
					 }
					}else{
						TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
						QGroup.addProperty("type", "Group");
						QGroup.addProperty("groupnum", groupnum1);
						if(ownerqq1 != null && !ownerqq1.equals("")){
							QGroup.addProperty("ownerqq", ownerqq1);
						}
						if(groupname1 != null && !groupname1.equals("")){
							QGroup.addProperty("groupname", groupname1);
						}
						if(groupdesc1 != null && !groupdesc1.equals("")){
							QGroup.addProperty("groupdesc", groupdesc1 );
						}
						if(createtime1 != null && !createtime1.equals("")){
							QGroup.addProperty("createtime", createtime1 );
						}
						if(Qq3 != null){
							Edge e = action.addEdge(null, QGroup, Qq3, "group");
							e.setProperty("grouptype", grouptype);
							if(nickname1 != null && !nickname1.equals("")){
								e.setProperty("nickname", nickname1);
							}
						}
					}
				}else{
					TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
					QGroup.addProperty("type", "Group");
					QGroup.addProperty("groupnum", groupnum1);
					if(ownerqq1 != null && !ownerqq1.equals("")){
						QGroup.addProperty("ownerqq", ownerqq1);
					}
					if(groupname1 != null && !groupname1.equals("")){
						QGroup.addProperty("groupname", groupname1);
					}
					if(groupdesc1 != null && !groupdesc1.equals("")){
						QGroup.addProperty("groupdesc", groupdesc1 );
					}
					if(createtime1 != null && !createtime1.equals("")){
						QGroup.addProperty("createtime", createtime1 );
					}
					if(Qq3 != null){
						Edge e = action.addEdge(null, QGroup, Qq3, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname1 != null && !nickname1.equals("")){
							e.setProperty("nickname", nickname1);
						}
					}
				}  
			}else{
	            
				TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
				QGroup.addProperty("type","Group");
				
				QGroup.addProperty("groupnum", groupnum1);
				if(ownerqq1 != null && !ownerqq1.equals("")){
					QGroup.addProperty("ownerqq", ownerqq1);
				}
				if(groupname1 != null && !groupname1.equals("")){
					QGroup.addProperty("groupname", groupname1);
				}
				if(groupdesc1 != null && !groupdesc1.equals("")){
					QGroup.addProperty("groupdesc", groupdesc1 );
				}
				if(createtime1 != null && !createtime1.equals("")){
					QGroup.addProperty("createtime", createtime1 );
				}
				if(Qq3 != null){
					Edge e = action.addEdge(null,QGroup,Qq3,"group");
					e.setProperty("grouptype", grouptype);
					if(nickname1 != null && !nickname1.equals("")){
						e.setProperty("nickname", nickname1);
					}
				}
			
			}
				}catch(java.util.NoSuchElementException e){
				e.printStackTrace();
				}
			}
			}
		 }
		return "OK";

	}

	@Override
	public String realNameWeigth(String qq) {
		List<Object> list = searchQqRelationDao.realNameWeigth(qq);
		int nums = 0;
		int key = 0;
		for(int i=0;i<list.size();i++){
			Object[] os = (Object[]) list.get(i);
			String weigth = os[1].toString();
			if(Integer.parseInt(weigth) > nums){
				nums = Integer.parseInt(weigth);
				key = i;
			}
		}
		if(list!=null&&list.size()>0){
			Object[] fin = (Object[])list.get(key);
			if(fin[0] != null){
				return fin[0].toString();
			}else{
				return "";
			}
		}
		return "";
	}

	@Override
	public List<GroupMembersBean> queryMembersByGnum(String groupNum) {
		return searchQqRelationDao.queryMembersByGnum(groupNum);
	}

	@Override
	public HashMap<String,Object> findGroupMemberBygroNum(HttpSession hs,String groqq,TitanTransaction action) {
		@SuppressWarnings("unchecked")
		List<Object> nodeIdList = (List<Object>) hs.getAttribute("nodeIdList");
		List<List<TitanVertex>> qqLists = searchQqRelationDao.findGroupMemberBygroNum(groqq, action);
		HashMap<String,Object> root = new HashMap<String,Object>();
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> edgeList = new ArrayList<Map<String,Object>>();
		root.put("nodes", nodeList);
		root.put("edges", edgeList);
		int index = -1;
		int source = -1;
		int index1 = -1;
		int source1 = -1;
		String numid = null;
		for (int j = 0; j < qqLists.size(); j++) {
			List<TitanVertex> list = qqLists.get(j);
			int size = list.size();
			String ownerqq = "";
			for (int i = 0; i < size; i++) {
				TitanVertex vertex = list.get(i);
				Object id = vertex.getId();
				
				Map<String, Object> nodeMap = new HashMap<String, Object>();
				nodeMap.put("id", vertex.getId());
				if (i == 0) {
					source = nodeIdList.indexOf(id);
					if (source == -1){
						ownerqq = vertex.getProperty("ownerqq");
						nodeMap.put("num", vertex.getProperty("groupnum"));
						nodeMap.put("image", "/img/Group.png");
						nodeMap.put("type", "Group");
						try {
							nodeMap.put("createtime", simpleDateFormat.format(new Date(Long.parseLong(vertex.getProperty("createtime")+"000"))));
						} catch (Exception e) {
							nodeMap.put("createtime", "");
						}
						nodeMap.put("ownerqq", vertex.getProperty("ownerqq"));
						nodeMap.put("name", vertex.getProperty("groupname"));
						nodeMap.put("groupdesc", vertex.getProperty("groupdesc"));
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						source = nodeIdList.size()-1;
					}
				}else if(i==1){
					numid = vertex.getProperty("numid");
					index = nodeIdList.indexOf(id);
					List<GroupMembersBean> MremarkList = searchQqRelationDao.findMemberRemark(groqq, numid);
					GroupMembersBean Mremark = null;
					if(MremarkList != null && MremarkList.size() != 0){
						Mremark = MremarkList.get(0);
					}
					if(index == -1){
						nodeMap.put("name", numid); 
						String[] values = vertex.getProperty("nickname");
						String nickname = TitanLabelUtils.getNickname(values);
						nodeMap.put("nickname", nickname);
						nodeMap.put("image", "/img/QQ.png");
						nodeMap.put("type", "QQ");
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						index = nodeIdList.size()-1;
						source1 = index;
						if (numid.equals(ownerqq)) {
							TitanLabelUtils.addLinkHandler(source, index, "groupOwner", "out", edgeList);
							if(Mremark != null){
								TitanLabelUtils.addLinkNickNameHandler(source, index, Mremark.getMemberRemark(), "out", edgeList);
							}
						}else{
							TitanLabelUtils.addLinkHandler(source, index, "qq", "out", edgeList);
							if(Mremark != null){
							    TitanLabelUtils.addLinkNickNameHandler(source, index, Mremark.getMemberRemark(), "out", edgeList);
							}
						}
					}
				}else{
					index1 = nodeIdList.indexOf(id);
					if(index1 == -1){
						nodeMap.put("name", vertex.getProperty("name"));
						nodeMap.put("idcard", vertex.getProperty("idcard")); 
						nodeMap.put("country", vertex.getProperty("country")); 
						nodeMap.put("image", "/img/Person.png");
						nodeMap.put("type", "Person");
						nodeMap.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
						nodeList.add(nodeMap);
						nodeIdList.add(id);
						index1 = nodeIdList.size()-1;
					
						TitanLabelUtils.addLinkHandler(source1, index1, "own", "in", edgeList);
					}
				}
			}
		}
		
		return root;
	}
	@Override
	public String ReformGroupsMembers(List<GroupMembersBean> GroupMembers,String groupnums,TitanTransaction action,TitanManagement tm) {
		 PropertyKey numid = null;
		 PropertyKey nickname = null;
		 PropertyKey type = null;
		 PropertyKey groupnum = null;
		 PropertyKey createtime = null;
		 PropertyKey groupname = null;
		 PropertyKey ownerqq = null;
		 PropertyKey groupdesc = null;
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
		 if(!tm.containsPropertyKey("groupnum")){
			 groupnum = tm.makePropertyKey("groupnum").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsGraphIndex("groupnum")){
				tm.buildIndex("groupnum", TitanVertex.class).addKey(groupnum).buildCompositeIndex();
			 }
		 if(!tm.containsPropertyKey("createtime")){
			 createtime = tm.makePropertyKey("createtime").dataType(Long.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("groupname")){
			 groupname = tm.makePropertyKey("groupname").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("ownerqq")){
			 ownerqq = tm.makePropertyKey("ownerqq").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		 if(!tm.containsPropertyKey("groupdesc")){
			 groupdesc = tm.makePropertyKey("groupdesc").dataType(String.class).cardinality ( Cardinality.SINGLE ).make();
		 }
		for(GroupMembersBean beans:GroupMembers){
		String numid1  = beans.getMemberNum();
		String name1 = realNameWeigth(numid1);
		String memberMark = beans.getMemberRemark();
		String groupnum1 = groupnums;//群号
		
		String ctime = "";
		Long createtime1 = 0L;
		if(ctime != null && !ctime.equals("")){
			createtime1 = Long.valueOf(ctime);
		}
		
		String grouptype = "2";//群组类型
		String nickname1 = memberMark;
		
		Object QqIte = null;
		if(numid1 != null && !"".equals(numid1)){
			QqIte = action.query().has("numid",numid1).vertices();
			}
		    TitanVertex tmp = null;
			boolean t =  false;
			if(QqIte != null){
				t = ((Iterable) QqIte).iterator().hasNext();
			}
			if(t){
			tmp = (TitanVertex)((Iterable)QqIte).iterator().next();
			Set<String> s = tmp.getPropertyKeys();
	       if(s != null){
	    	   boolean is = false;
				if(tmp.getVertexLabel().toString().equals("QQ")&&tmp.getProperty("type").toString().equals("IM")){
					is = true;
				}
			if(is){
				//获取人物信息？
				TitanVertex person =null;
				try{
				person = (TitanVertex)tmp.query().labels(new String[]{"own"}).vertices().iterator().next();
				}catch(java.util.NoSuchElementException e){
				e.printStackTrace();
				}
				if(person!=null){
				if(name1 != null && !name1.equals("")){
					Object names = person.getProperty("name");
					if(names ==null || names.equals("")){
						person.addProperty("name",name1);
					}
				}
				}else{
					TitanVertex pp = action.addVertexWithLabel("Person");
					pp.addProperty("type", "Person");
					if(name1 ==null || name1.equals("")){
					pp.addProperty("name", name1);
					}
					action.addEdge(null, pp, tmp, "own");
				}
			List<String> nicknames = tmp.getProperty("nickname");
			boolean isWith = false;
			if(nickname1 !=null && !"".equals(nickname1)){
				for(String ss:nicknames){
					if(ss.equals(nickname1)){
						isWith = true;
						break;
					}
				}
				if(!isWith){
				tmp.addProperty("nickname", nickname1);
				}
			}
			TitanVertex group =null;
			try{
			Iterable<TitanVertex> groups = action.query().has("groupnum",groupnum1).vertices();
				
			Iterator<TitanVertex> gs = groups.iterator();
			if(gs.hasNext()){
					group = gs.next();
								
			if(group !=null){
				if(group.getProperty("groupnum").equals(groupnum1) && group.getProperty("type").equals("Group")){
                    boolean isTrue = true;
					Iterator<Vertex> coh = tmp.query().labels(new String[]{"group"}).vertices().iterator();
					while(coh.hasNext()){
						TitanVertex GROUP = (TitanVertex)coh.next();
						if(GROUP == group){
							isTrue = false;
						}
					}
					if(isTrue){
						Edge e = action.addEdge(null, group, tmp, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname1 != null && !nickname1.equals("")){
							e.setProperty("nickname", nickname1);
						}
					}
				}else{
					TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
					QGroup.addProperty("type", "Group");
					QGroup.addProperty("groupnum", groupnum1);
					if(createtime1 != null && !createtime1.equals("")){
						QGroup.addProperty("createtime", createtime1 );
					}
					
					Edge e = action.addEdge(null, QGroup, tmp, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname1 != null && !nickname1.equals("")){
						e.setProperty("nickname", nickname1);
					}
				}

			}else{
				TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
				QGroup.addProperty("type", "Group");
				QGroup.addProperty("groupnum", groupnum1);
				if(createtime1 != null && !createtime1.equals("")){
					QGroup.addProperty("createtime", createtime1 );
				}
				Edge e = action.addEdge(null, QGroup, tmp, "group");
				e.setProperty("grouptype", grouptype);
				if(nickname1 != null && !nickname1.equals("")){
					e.setProperty("nickname", nickname1);
				}
			}
		}else{
			TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
			QGroup.addProperty("type","Group");
			QGroup.addProperty("groupnum", groupnum1);
			
			if(createtime1 != null && !createtime1.equals("")){
				QGroup.addProperty("createtime", createtime1 );
			}
			Edge e = action.addEdge(null,QGroup,tmp,"group");
			e.setProperty("grouptype", grouptype);
			if(nickname1 != null && !nickname1.equals("")){
				e.setProperty("nickname", nickname1);
			}
		}
		}catch(java.util.NoSuchElementException e){
		e.printStackTrace();
		}
			}
    }
	}else{
		TitanVertex Qq3 = null;
		if(numid1 != null && !"".equals(numid1)){
			TitanVertex personNn = action.addVertexWithLabel("Person");
			personNn.addProperty("type", "Person");
			//人物信息补充？
			if(name1 != null && !name1.equals("")){
				personNn.addProperty("name",name1);
			}
			
			Qq3 = action.addVertexWithLabel("QQ");
			Qq3.addProperty("type","IM");
			if(numid1!=null && !"".equals(numid1)){
				Qq3.addProperty("numid",numid1);
			}
			if(nickname1 != null && !nickname1.equals("")){
				Qq3.addProperty("nickname", nickname1);
			}
			
			action.addEdge(null, personNn, Qq3, "own");
			
		}
		if(groupnum1 != null && !groupnum1.equals("")){
		TitanVertex group =null;
		try{
		@SuppressWarnings("unchecked")
		Iterable<TitanVertex> groups = action.query().has("groupnum",groupnum1).vertices();
		Iterator<TitanVertex> gs1 = groups.iterator();
		if(gs1.hasNext()){
			group = gs1.next();
			if(group !=null){
				if(group.getProperty("groupnum").equals(groupnum1) && group.getProperty("type").equals("Group")){
					if(Qq3 != null){
					Edge e = action.addEdge(null, group, Qq3, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname1 != null && !nickname1.equals("")){
						e.setProperty("nickname", nickname1);
					}
				 }
				}else{
					TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
					QGroup.addProperty("type", "Group");
					QGroup.addProperty("groupnum", groupnum1);
					
					if(createtime1 != null && !createtime1.equals("")){
						QGroup.addProperty("createtime", createtime1 );
					}
					if(Qq3 != null){
						Edge e = action.addEdge(null, QGroup, Qq3, "group");
						e.setProperty("grouptype", grouptype);
						if(nickname1 != null && !nickname1.equals("")){
							e.setProperty("nickname", nickname1);
						}
					}
				}
			}else{
				TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
				QGroup.addProperty("type", "Group");
				QGroup.addProperty("groupnum", groupnum1);
				if(createtime1 != null && !createtime1.equals("")){
					QGroup.addProperty("createtime", createtime1 );
				}
				if(Qq3 != null){
					Edge e = action.addEdge(null, QGroup, Qq3, "group");
					e.setProperty("grouptype", grouptype);
					if(nickname1 != null && !nickname1.equals("")){
						e.setProperty("nickname", nickname1);
					}
				}
			}  
		}else{
            
			TitanVertex QGroup = action.addVertexWithLabel("QqGroup");
			QGroup.addProperty("type","Group");
			
			QGroup.addProperty("groupnum", groupnum1);
			if(createtime1 != null && !createtime1.equals("")){
				QGroup.addProperty("createtime", createtime1 );
			}
			if(Qq3 != null){
				Edge e = action.addEdge(null,QGroup,Qq3,"group");
				e.setProperty("grouptype", grouptype);
				if(nickname1 != null && !nickname1.equals("")){
					e.setProperty("nickname", nickname1);
				}
			}
		
		}
			}catch(java.util.NoSuchElementException e){
			e.printStackTrace();
			}
		}
		}
	 }
		return "OK";

	
	}


}
