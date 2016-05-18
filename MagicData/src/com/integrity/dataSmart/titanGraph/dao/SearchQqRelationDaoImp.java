package com.integrity.dataSmart.titanGraph.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.titanGraph.action.SearchQqRelationAction;
import com.integrity.dataSmart.titanGraph.bean.GroupMembersBean;
import com.integrity.dataSmart.titanGraph.bean.QQFriend;
import com.integrity.dataSmart.util.titan.TitanGraphUtil;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

public class SearchQqRelationDaoImp extends HibernateDaoSupport implements SearchQqRelationDao{
	private Logger logger = Logger.getLogger(SearchQqRelationDaoImp.class);
	private TitanGraph graph = TitanGraphUtil.getInstance().getTitanGraph();
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<QQFriend> queryQqFriends(String qq){
		HibernateTemplate h = getHibernateTemplate();
		String hql = "FROM QQFriend  WHERE 1=1 AND qqNum="+qq;
	    List<QQFriend> list=h.find(hql);
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String queryQqFriends(String qq,String friQqNum){
		HibernateTemplate h = getHibernateTemplate();
	    String hql = "FROM QQFriend  WHERE 1=1 AND QQNUM="+qq+" AND FRIENDNUM="+friQqNum;
	    List<QQFriend> list=h.find(hql);
	    String nickname = "";
	    if(list!=null&&list.size()>0){
	    	QQFriend qqFriend = list.get(0);
	    	nickname = qqFriend.getFriendRemark();
	    }
	    if(nickname==null){
	    	nickname="";
	    }
		return nickname;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryQqGroups(String qq) {
		List<Object> list = new ArrayList<Object>();
		String hql =  "SELECT DISTINCT a.GROUPNUM," +
		    		" b.group_name,b.create_time,  a.MEMBERREMARK as myMark,a.MEMBERNUM as numid,b.owner_qq,"+
		    		"(select DISTINCT memberremark from TB_RNS_QQ_GROUPLIST"+
		    		" where membernum = b.owner_qq and groupnum = b.groupnum"+
		    		" and rownum = 1 ) as owner_name,b.group_desc"+
		    		" FROM TB_RNS_QQ_GROUPLIST a, TB_RNS_QQ_GROUP_DESC b"+
		    		" WHERE a.membernum = :qq and a.groupnum = b.groupnum(+)";
			
		Session ser = getHibernateTemplate().getSessionFactory().openSession();
		list = ser.createSQLQuery(hql).setParameter("qq", qq).list();
		ser.close();
		return list;
	
	}
	
	/**
	 * 根据qq查询群组关系
	 * @param qq qq号码
	 * @return List中的每个List的第一个为群组后面为群成员
	 */
	@Override
	public List<List<TitanVertex>> findGroupRelativeByqqNum(String qq,TitanTransaction action) {
		//用于群组去重
		List<String> temp = new ArrayList<String>();
		List<List<TitanVertex>> groups = new ArrayList<List<TitanVertex>>();
		@SuppressWarnings("unchecked")
		Iterable<TitanVertex> it = action.query().has("numid",qq).vertices();
		for (TitanVertex vertex : it) {
			if (DataType.IM.equals(vertex.getProperty("type"))) {//属性是IM的点
				Iterable<Vertex> groupIt = vertex.query().labels(DataType.GROUP).vertices();
				for (Vertex group : groupIt) {
					String groupNum = group.getProperty("groupnum");
					if (!temp.contains(groupNum)) {
						List<TitanVertex> groupList = new ArrayList<TitanVertex>();
						//当前qq
						TitanVertex qqnow = it.iterator().next();
						groupList.add(qqnow);
						groupList.add((TitanVertex) group);
						//群成员查询
						/*Iterable<Vertex> qqIt = group.query().labels(DataType.GROUP).vertices();
						  groupList.addAll(IteratorUtils.toList(qqIt.iterator()));
						 */
						
						groups.add(groupList);
						temp.add(groupNum);
					}
				}
			}
		}
		return groups;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> realNameWeigth(String qq) {
		List<Object> list = new ArrayList<Object>();
		String hql = "select FRIENDREMARK as realName ,count(*) as weight from ( "+
		" select FRIENDNUM,FRIENDREMARK from TB_RNS_QQ_FRIENDLIST where FRIENDNUM= "+qq+" and rownum < "+
		" 300"+
		" union all"+
		" select MEMBERNUM AS FRIENDNUM,MEMBERREMARK AS FRIENDREMARK from TB_RNS_QQ_GROUPLIST where MEMBERNUM= "+qq+" and "+
		" rownum < 50"+
		" ) t group by t.FRIENDREMARK ";
		Session ser = getHibernateTemplate().getSessionFactory().openSession();
		SQLQuery query = ser.createSQLQuery(hql);
		query.addScalar("realName",Hibernate.STRING);
		query.addScalar("weight",Hibernate.STRING);
		list = query.list();
		ser.close();
		return list;
	}
	@Override
	public List<GroupMembersBean> queryMembersByGnum(String groupNum) {
		String hql = "FROM GroupMembersBean WHERE GROUPNUM= "+groupNum+" and rownum < 2000";
	    @SuppressWarnings("unchecked")
		List<GroupMembersBean> query=getHibernateTemplate().find(hql);
		return query;
	}
	@Override
	public List<List<TitanVertex>> findGroupMemberBygroNum(String groqq,TitanTransaction action) {
		List<String> temp = new ArrayList<String>();
		List<List<TitanVertex>> qqMember = new ArrayList<List<TitanVertex>>();
		@SuppressWarnings("unchecked")
		Iterable<TitanVertex> it = action.query().has("groupnum",groqq).vertices();//群组
		for (TitanVertex vertex : it) {
			if ("Group".equals(vertex.getProperty("type"))) {//属性是GROUP的点
				Iterable<Vertex> numIt = vertex.query().labels(DataType.GROUP).vertices();//成员节点
				for (Vertex nums : numIt) {
					String numId = nums.getProperty("numid");
					if (!temp.contains(numId)) {
						List<TitanVertex> qqList = new ArrayList<TitanVertex>();
						//当前qq
						TitanVertex group1 = it.iterator().next();
						qqList.add(group1);
						qqList.add((TitanVertex) nums);
						TitanVertex person = (TitanVertex) nums.query().labels(new String[]{"own"}).vertices().iterator().next();
						qqList.add(person);
						//群成员查询
						/*Iterable<Vertex> qqIt = group.query().labels(DataType.GROUP).vertices();
						  groupList.addAll(IteratorUtils.toList(qqIt.iterator()));
						 */
						
						qqMember.add(qqList);
						temp.add(numId);
					}
				}
			}
		}
		return qqMember;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMembersBean> findMemberRemark(String groupNum, String MemberNum) {
		List<GroupMembersBean> query = null;
		if(StringUtils.isNotBlank(groupNum) && StringUtils.isNotBlank(MemberNum)){
			String hql = "FROM GroupMembersBean WHERE GROUPNUM= "+groupNum+" and MEMBERNUM = "+MemberNum;
			query=getHibernateTemplate().find(hql);
		}
		return query;
	}
}
