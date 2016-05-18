package com.integrity.dataSmart.titanGraph.dao;

import java.util.List;

import com.integrity.dataSmart.titanGraph.bean.GroupMembersBean;
import com.integrity.dataSmart.titanGraph.bean.QQFriend;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
public interface SearchQqRelationDao{
	/**
	 * @param qq
	 * @return
	 * 查询群组
	 */
	public List<Object> queryQqGroups(String qq);
	/**
	 * @param qqcccc
	 * @param graph
	 * @return
	 *查询titan群组数据
	 */
	public List<List<TitanVertex>> findGroupRelativeByqqNum(String qq,TitanTransaction action);
	/**
	 * @param qq
	 * @return
	 * 获取真实姓名
	 */
	public List<Object> realNameWeigth(String qq);
	/**
	 * @param qq
	 * @return
	 * 查询qq好友
	 */
	public List<QQFriend> queryQqFriends(String qq);
	/**
	 * @param qq
	 * @return
	 * 在titan查询好友
	 */
	public String queryQqFriends(String qq,String friQqNum);
	/**
	 * @param groupNum
	 * @return
	 * 查询群成员
	 */
	public List<GroupMembersBean> queryMembersByGnum(String groupNum);
	/**
	 * @param groqq
	 * @param graph
	 * @return
	 * 在titan中查询群成员
	 */
	public List<List<TitanVertex>> findGroupMemberBygroNum(String groqq,TitanTransaction action);
	
	/**
	 * @param groupNum
	 * @param MemberNum
	 * @return\
	 * 查询群成员备注信息
	 */
	public List<GroupMembersBean> findMemberRemark(String groupNum,String MemberNum);
	
}
