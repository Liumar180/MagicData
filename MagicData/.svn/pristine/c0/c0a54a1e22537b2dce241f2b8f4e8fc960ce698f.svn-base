package com.integrity.lawCase.hostManage.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.hostManage.bean.DomainsituationObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.hostManage.bean.LoopholesObject;

public class HostManageDao extends HibernateDaoSupport {
	/**
	 * @return
	 * 卡片列表
	 */
	/**
	 * @param pageNo
	 * @param pageSize
	 * @param hosts
	 * @return
	 * 主机信息卡片数据（初始化）
	 */
	@SuppressWarnings("unchecked")
	public List<HostsObject> findHostCards(int pageNo,int pageSize,HostsObject hosts) {
		String hql = "from HostsObject h order by createTime desc";
		Query query=getHibernateTemplate().getSessionFactory().openSession().createQuery(hql);
    	query.setFirstResult(pageNo);
    	query.setMaxResults(pageSize);
		return query.list();
		
	}
	
	/**
	 * @param page
	 * @param hosts
	 * @return
	 * 获取 主机信息列表数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HostsObject> searchHostlist(PageModel<HostsObject> page,final HostsObject hosts) {
		final int pageNo = page.getPageNo();
		final int pageSize = page.getPageSize();
		final String hostName = hosts.getHostName();
		final String hostIp = hosts.getHostIp();
		final String priver = hosts.getProvider();
		final String hostType = hosts.getHostType();
		final String hostStatus = hosts.getHostState();
		final String ControlState = hosts.getControlState();
		final String Directions = hosts.getDirections();
		final String Person = hosts.getResponsiblePerson();
		final String ImportantLevel = hosts.getImportantLevel();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from HostsObject h where 1=1 ";
			if(hostName != null && !"null".equals(hostName) && !"".equals(hostName)){
	    		hql+=" and h.hostName like '"+'%'+hostName+'%'+"' ";
	    	}
			if(hostIp != null && !"null".equals(hostIp) && !"".equals(hostIp)){
	    		hql+=" and h.hostIp = '"+hostIp+"' ";
	    	}
			if(priver != null && !"null".equals(priver) && !"".equals(priver)){
	    		hql+=" and h.provider = '"+priver+"' ";
	    	}
			if(hostType != null && !"null".equals(hostType) && !"".equals(hostType)){
				hql+=" and h.hostType = '"+hostType+"' ";
	    	}
			if(hostStatus != null && !"null".equals(hostStatus) && !"".equals(hostStatus)){
	    		hql+=" and h.hostState = '"+hostStatus+"' ";
	    	}
			if(ControlState != null && !"null".equals(ControlState) && !"".equals(ControlState)){
	    		hql+=" and h.controlState = '"+ControlState+"' ";
	    	}
			if(Directions != null && !"null".equals(Directions) && !"".equals(Directions)){
	    		hql+=" and h.directions like '"+'%'+Directions+'%'+"' ";
	    	}
			if(Person != null && !"null".equals(Person) && !"".equals(Person)){
	    		hql+=" and h.responsiblePerson = '"+Person+"' ";
	    	}
			if(ImportantLevel != null && !"null".equals(ImportantLevel) && !"".equals(ImportantLevel)){
	    		hql+=" and h.importantLevel = '"+ImportantLevel+"' ";
	    	}
			hql +=" order by createTime desc";
			Query query = session.createQuery(hql); 
			query.setFirstResult((pageNo - 1) * pageSize); 
			query.setMaxResults(pageSize); 
			return query.list();  
			}
		});
		return list; 
		
	}
	
	/**
	 * @param hosts
	 * @return
	 * 获取数据总数
	 */
	public Integer Acounts(HostsObject hosts) {
		String hostName = hosts.getHostName();
		String hostIp = hosts.getHostIp();
		String priver = hosts.getProvider();
		String hostType = hosts.getHostType();
		String hostStatus = hosts.getHostState();
		String ControlState = hosts.getControlState();
		String Directions = hosts.getDirections();
		String Person = hosts.getResponsiblePerson();
		String ImportantLevel = hosts.getImportantLevel();
		String hql = "select count(h.id) from HostsObject h where 1=1 ";
		if(hostName != null && !"null".equals(hostName) && !"".equals(hostName)){
    		hql+=" and h.hostName like '"+'%'+hostName+'%'+"' ";
    	}
		if(hostIp != null && !"null".equals(hostIp) && !"".equals(hostIp)){
    		hql+=" and h.hostIp = '"+hostIp+"' ";
    	}
		if(priver != null && !"null".equals(priver) && !"".equals(priver)){
    		hql+=" and h.provider = '"+priver+"' ";
    	}
		if(hostType != null && !"null".equals(hostType) && !"".equals(hostType)){
			hql+=" and h.hostType = '"+hostType+"' ";
    	}
		if(hostStatus != null && !"null".equals(hostStatus) && !"".equals(hostStatus)){
    		hql+=" and h.hostState = '"+hostStatus+"' ";
    	}
		if(ControlState != null && !"null".equals(ControlState) && !"".equals(ControlState)){
    		hql+=" and h.controlState = '"+ControlState+"' ";
    	}
		if(Directions != null && !"null".equals(Directions) && !"".equals(Directions)){
    		hql+=" and h.directions like '"+'%'+Directions+'%'+"' ";
    	}
		if(Person != null && !"null".equals(Person) && !"".equals(Person)){
    		hql+=" and h.responsiblePerson = '"+Person+"' ";
    	}
		if(ImportantLevel != null && !"null".equals(ImportantLevel) && !"".equals(ImportantLevel)){
    		hql+=" and h.importantLevel = '"+ImportantLevel+"' ";
    	}
		Number num = (Number) getHibernateTemplate().find(hql).iterator().next();
	    return num.intValue();
	}
	/**
	 * @param ho
	 * 保存主机信息
	 */
	public void save(HostsObject ho){
		getHibernateTemplate().save(ho);
	}
	/**
	 * @param ho
	 * 更新主机信息
	 */
	public void update(HostsObject ho){
		getHibernateTemplate().update(ho);
	}
	/**
	 * @param domain
	 * 更新域名情况信息
	 */
	public void updateDomain(DomainsituationObject domain){
		getHibernateTemplate().update(domain);
	}
	/**
	 * @param loopholes
	 * 更新漏洞情况信息
	 */
	public void updateLoopholes(LoopholesObject loopholes){
		getHibernateTemplate().update(loopholes);
	}
	/**
	 * 添加域名情况
	 * @param domain
	 */
	public void savedomain(DomainsituationObject domain){
		getHibernateTemplate().save(domain);
	}
	/**
	 * @param loophole
	 * 添加漏洞情况
	 */
	public void saveLoophole(LoopholesObject loophole){
		getHibernateTemplate().save(loophole);
	}

	/**
	 * @param entities
	 * 删除
	 */
	public void delHosts(List<String> ids) {
		String hql="delete from HostsObject where id in (:ids)"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameterList("ids", ids).executeUpdate();
	}
	
	/**
	 * @param id
	 * 删除域名
	 */
	public void delDomain(Long id){
		String hql="delete from DomainsituationObject where id = :ids"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameter("ids", id).executeUpdate();
	}
	/**
	 * @param hostId
	 * 通过主机id删除域名
	 */
	public void delDomainByhostId(List<String> hostId){
		String hql="delete from DomainsituationObject where hostId in (:ids)"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameterList("ids", hostId).executeUpdate();
	}
	/**
	 * @param id
	 * 删除漏洞
	 */
	public void delLoopholes(Long id){
		String hql="delete from LoopholesObject where id = :ids"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameter("ids", id).executeUpdate();
	}
	/**
	 * @param hostId
	 * 通过主机id删除漏洞
	 */
	public void delLoopholesByhostId(List<String> hostId){
		String hql="delete from LoopholesObject where hostId in (:ids)"; 
		Session del = getHibernateTemplate().getSessionFactory().openSession();
		del.createSQLQuery(hql).setParameterList("ids", hostId).executeUpdate();
	}
	/**
	 * @param id
	 * @return
	 * 查看详情
	 */
	public HostsObject findDetails(Long id){
		return (HostsObject) getHibernateTemplate().get(HostsObject.class, id);
		
	}
	/**
	 * @param id
	 * @return
	 * 根据id 查看域名情况信息
	 */
	public DomainsituationObject findDomainByid(Long id){
		return (DomainsituationObject) getHibernateTemplate().get(DomainsituationObject.class, id);
		
	}
	/**
	 * @param hostId
	 * @return
	 * 根据主机id 获取域名信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<DomainsituationObject> findDomainList(Long hostId){
		String hql = "";
		hql="from DomainsituationObject as h where h.hostId="+hostId; 
        List<DomainsituationObject> list = getHibernateTemplate().find(hql);
        return list;
	
	}
	/**
	 * @param id
	 * @return
	 * 根据id 获取漏洞情况信息
	 */
	public LoopholesObject findLoopholesByid(Long id){
		return (LoopholesObject) getHibernateTemplate().get(LoopholesObject.class, id);
		
	}
	/**
	 * @param hostId
	 * @return
	 * 根据主机id获取漏洞情况列表
	 */
	@SuppressWarnings("unchecked")
	public List<LoopholesObject> findLoopholesList(Long hostId){
		String hql = "";
		hql="from LoopholesObject as h where h.hostId="+hostId; 
        List<LoopholesObject> list = getHibernateTemplate().find(hql);
        return list;
		
	}
	/**
	 * @param name
	 * @return
	 * 根据主机名字模糊查询
	 */
	public List<HostsObject> findHostByName(String name,String id) {
		String hql="from HostsObject where id !="+id+" and hostName like ?";
		return getHibernateTemplate().find(hql,"%"+name+"%");
	}

}
