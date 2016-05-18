package com.integrity.dataSmart.titanGraph.action;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.integrity.dataSmart.common.DataType;
import com.integrity.dataSmart.titanGraph.bean.GroupMembersBean;
import com.integrity.dataSmart.titanGraph.bean.QQFriend;
import com.integrity.dataSmart.titanGraph.bean.QqGroupsBeans;
import com.integrity.dataSmart.titanGraph.service.SearchQqRelationService;
import com.integrity.dataSmart.util.titan.TitanGraphUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.TitanTransaction;
import com.thinkaurelius.titan.core.TitanVertex;
import com.thinkaurelius.titan.core.schema.TitanManagement;

/**
 * @author liubf
 * 查询qq关联信息action
 *`
 */
public class SearchQqRelationAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private SearchQqRelationService searchQqRelationService;
	
	private List<QQFriend> friendslist;
	private String qq;
	private String groupNum;
	private Map<String, Object> root;
	private TitanGraph graph = TitanGraphUtil.getInstance().getTitanGraph();
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DataType.DATEFORMATSTR);

	private Logger logger = Logger.getLogger(SearchQqRelationAction.class);
	
	public String searchQQFriendsByQq(){
		/*List<QQFriend> list = searchQqRelationService.queryQqFriendsFromOrcl(qq);
		String realName = searchQqRelationService.realNameWeigth(qq);
		String flag = searchQqRelationService.ReformQQFriends(list, qq,realName, graph);*/
		HttpSession hs = ServletActionContext.getRequest().getSession();
		TitanTransaction action = graph.newTransaction();
		TitanManagement tm = graph.getManagementSystem();
		List<List<TitanVertex>> list = searchQqRelationService.queryQqFriends(qq,action,tm);
		root = searchQqRelationService.queryQQFriendsJson(hs,list);
		tm.commit();
		action.commit();
		return SUCCESS;
	}
	/**
	 * @return String
	 * 通过qq查询相关群组
	 */
	public String searchgGroupsByQq(){
		HttpSession hs = ServletActionContext.getRequest().getSession();
		TitanTransaction action = graph.newTransaction();
		TitanManagement tm = graph.getManagementSystem();
		try {
		List<QqGroupsBeans> groups = searchQqRelationService.queryQqGroups(qq);
		String realName = searchQqRelationService.realNameWeigth(qq);
		searchQqRelationService.ReformGroups(groups, qq,realName, action,tm);
		root = searchQqRelationService.findGroupRelativeByqqNum(hs,qq,action);
		action.commit();
		tm.commit();
		} catch (Exception e) {
			logger.error("查询群组信息异常："+e.getMessage());
		}
		return SUCCESS;
	}


	/**
	 * @return String
	 * 通过群号查询群成员
	 */
	public String searchGroupMembers(){
		TitanTransaction action = graph.newTransaction();
		TitanManagement tm = graph.getManagementSystem();
		HttpSession hs = ServletActionContext.getRequest().getSession();
		try {
		List<GroupMembersBean> members  = searchQqRelationService.queryMembersByGnum(groupNum);
		if(members != null && members.size() !=0){
			searchQqRelationService.ReformGroupsMembers(members,groupNum,action,tm);
		}
		root = searchQqRelationService.findGroupMemberBygroNum(hs, groupNum,action);
		action.commit();
		tm.commit();
		} catch (Exception e) {
			logger.error("查询群成员信息异常："+e.getMessage());
		}
		return SUCCESS;
	}
	
	
	public SearchQqRelationService getSearchQqRelationService() {
		return searchQqRelationService;
	}
	public void setSearchQqRelationService(
			SearchQqRelationService searchQqRelationService) {
		this.searchQqRelationService = searchQqRelationService;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public List<QQFriend> getFriendslist() {
		return friendslist;
	}
	public void setFriendslist(List<QQFriend> friendslist) {
		this.friendslist = friendslist;
	}
	
	public Map<String, Object> getRoot() {
		return root;
	}
	public void setRoot(Map<String, Object> root) {
		this.root = root;
	}
	public String getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

}
