package com.integrity.dataSmart.titanGraph.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.integrity.dataSmart.titanGraph.bean.GroupMembersBean;
import com.integrity.dataSmart.titanGraph.bean.QQFriend;
import com.integrity.dataSmart.titanGraph.bean.QqGroupsBeans;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Vertex;

public interface SearchQqRelationService {
	/**
	 * @param qq
	 * @return
	 * 通过qq查询群组
	 */
	public List<QqGroupsBeans> queryQqGroups(String qq);
	/**
	 * @param hs
	 * @param qq
	 * @param graph
	 * @return
	 * 通过qq在titan查询群组数据
	 */
	public HashMap<String,Object> findGroupRelativeByqqNum(HttpSession hs,String qq,TitanTransaction action);
	/**
	 * @param hs
	 * @param groqq
	 * @param graph
	 * @return
	 * 通过群号查询群成员
	 */
	public HashMap<String,Object> findGroupMemberBygroNum(HttpSession hs,String groqq,TitanTransaction action);
	/**
	 * @param qq
	 * @return
	 * 通过qq分析获取真实姓名
	 */
	public String realNameWeigth(String qq);
    /**
     * @param beans
     * @param qq
     * @param name
     * @param graph
     * @return
     * 同步qq以及群组数据（Titan）
     */
    public String ReformGroups(List<QqGroupsBeans> beans,String qq,String name,TitanTransaction action,TitanManagement tm);
    /**
     * @param groupNum
     * @return
     * 通过群号获取群成员
     */
    public List<GroupMembersBean> queryMembersByGnum(String groupNum);
    /**
     * @param qq
     * @return
     * 通过qq查询好友数据
     */
    public List<QQFriend> queryQqFriendsFromOrcl(String qq);
    
    /**
     * @param qq
     * @param graph
     * @return
     * 在titan查询qq好友
     */
    public List<List<TitanVertex>> queryQqFriends(String qq, TitanTransaction action,TitanManagement tm);
    /**
     * @param hs
     * @param list
     * @return
     * 获取qq好友json数据
     */
    public Map<String,Object> queryQQFriendsJson(HttpSession hs,List<List<TitanVertex>> list);
    
    /**
     * @param GroupMembers
     * @param groupnum
     * @param graph
     * @return
     * 同步群组以及群成员数据（Titan）
     */
    public String ReformGroupsMembers(List<GroupMembersBean> GroupMembers,String groupnum,TitanTransaction action,TitanManagement tm);
}
