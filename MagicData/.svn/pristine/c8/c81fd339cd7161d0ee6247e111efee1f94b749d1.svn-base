package com.integrity.lawCase.peopleManage.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.firebirdsql.jdbc.parser.JaybirdSqlParser.nullValue_return;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.peopleManage.bean.Documentnumberobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.peopleManage.bean.Peoplevirtualobject;
import com.integrity.lawCase.peopleManage.bean.Phonenumberobject;



public class PeopleManageDao  extends HibernateDaoSupport {
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Calendar rightNow = Calendar.getInstance();
	public void save(Peopleobject peopleobject) {
		getHibernateTemplate().save(peopleobject);
	}
	public void edit(Peopleobject peopleobject) {
		getHibernateTemplate().update(peopleobject);
	}
	public void delete(long id) {
		getHibernateTemplate().bulkUpdate("DELETE FROM Peopleobject where id='"+id+"'");
		getHibernateTemplate().bulkUpdate("DELETE FROM Peoplevirtualobject where poid='"+id+"'");
		getHibernateTemplate().bulkUpdate("DELETE FROM Documentnumberobject where poid='"+id+"'");
		getHibernateTemplate().bulkUpdate("DELETE FROM Phonenumberobject where poid='"+id+"'");
	}
	public void savePV(Peoplevirtualobject peopleobject) {
		getHibernateTemplate().save(peopleobject);
	}
	public void editPV(Peoplevirtualobject peopleobject) {
		getHibernateTemplate().update(peopleobject);
	}
	public void deletePV(long id) {
		getHibernateTemplate().bulkUpdate("DELETE FROM Peoplevirtualobject where pvid='"+id+"'");
	}
	public void saveDN(Documentnumberobject documentnumberobject) {
		getHibernateTemplate().save(documentnumberobject);
	}
	public void editDN(Documentnumberobject documentnumberobject) {
		getHibernateTemplate().update(documentnumberobject);
	}
	public void deleteDN(long id) {
		getHibernateTemplate().bulkUpdate("DELETE FROM Documentnumberobject where dnid='"+id+"'");
	}
	public void savePN(Phonenumberobject phonenumberobject) {
		getHibernateTemplate().save(phonenumberobject);
	}
	public void editPN(Phonenumberobject phonenumberobject) {
		getHibernateTemplate().update(phonenumberobject);
	}
	public void deletePN(long id) {
		getHibernateTemplate().bulkUpdate("DELETE FROM Phonenumberobject where pnid='"+id+"'");
	}
	@SuppressWarnings("rawtypes")
	public List findPeopleCardList(int pageNo,int pageSize,Peopleobject peopleobject) {
		Query query= getSession().createQuery("from Peopleobject");
		if(pageNo==0) pageNo=1;
		query.setFirstResult((pageNo - 1) * pageSize);//就是说你的从第几条开始
		query.setMaxResults(pageSize);//页面默认显示的条数
		return query.list(); 
	}
	public Peopleobject getPeopleinfoByid(long id){
		return (Peopleobject) getHibernateTemplate().get(Peopleobject.class, id);
	}
	public Peoplevirtualobject getPVinfoByid(long id){
		return (Peoplevirtualobject) getHibernateTemplate().get(Peoplevirtualobject.class, id);
	}
	public List<Peoplevirtualobject> getPVinfoByPoid(long id){
		String hql="from Peoplevirtualobject where poid='"+id+"'";
		return getHibernateTemplate().find(hql); 
	}
	public Documentnumberobject getDNinfoByid(long id){
		return (Documentnumberobject) getHibernateTemplate().get(Documentnumberobject.class, id);
	}
	public List<Documentnumberobject> getDNinfoByPoid(long id){
		String hql="from Documentnumberobject where poid='"+id+"'";
		return getHibernateTemplate().find(hql); 
	}
	public Phonenumberobject getPNinfoByid(long id){
		return (Phonenumberobject) getHibernateTemplate().get(Phonenumberobject.class, id);
	}
	public List<Phonenumberobject> getPNinfoByPoid(long id){
		String hql="from Phonenumberobject where poid='"+id+"'";
		return getHibernateTemplate().find(hql); 
	}
	/**
	 * 分页查询案件
	 * @param page
	 * @return
	 */
	public List<Peopleobject> findPeopleList(PageModel<Peopleobject> page, Peopleobject p2) {
		
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		final Peopleobject p=p2;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			StringBuffer hql = new StringBuffer();
			hql.append("from Peopleobject where 1=1");
			if(p.getPoalias()!=null&&!"".equals(p.getPoalias())){
				hql.append(" and  poalias like '%"+p.getPoalias().replaceAll("'", "\"")+"%'");
			}
			if(p.getPocnname()!=null&&!"".equals(p.getPocnname())){
				hql.append("  and pocnname like '%"+p.getPocnname().replaceAll("'", "\"")+"%'");
			}
			if(p.getPocontrolstatus()!=null&&!"".equals(p.getPocontrolstatus())){
				hql.append(" and  pocontrolstatus ='"+p.getPocontrolstatus()+"'");
			}
			if(p.getPocountry()!=null&&!"".equals(p.getPocountry())){
				hql.append("  and pocountry ='"+p.getPocountry()+"'");
			}
			if(p.getPodirectionof()!=null&&!"".equals(p.getPodirectionof())){
				hql.append("  and podirectionof ='"+p.getPodirectionof()+"'");
			}
			if(p.getPodutyman()!=null&&!"".equals(p.getPodutyman())){
				hql.append(" and  podutyman like '%"+p.getPodutyman().replaceAll("'", "\"")+"%'");
			}
			if(p.getPoenname()!=null&&!"".equals(p.getPoenname())){
				hql.append(" and  poenname like '%"+p.getPoenname().replaceAll("'", "\"")+"%'");
			}
			if(p.getPohukou()!=null&&!"".equals(p.getPohukou())){
				hql.append(" and  pohukou like '%"+p.getPohukou().replaceAll("'", "\"")+"%'");
			}
			if(p.getPolocation()!=null&&!"".equals(p.getPolocation())){
				hql.append(" and  polocation like '%"+p.getPolocation().replaceAll("'", "\"")+"%'");
			}
			if(p.getPonamespell()!=null&&!"".equals(p.getPonamespell())){
				hql.append(" and  ponamespell like '%"+p.getPonamespell().replaceAll("'", "\"")+"%'");
			}
			if(p.getPonational()!=null&&!"".equals(p.getPonational())){
				hql.append(" and  ponational ='"+p.getPonational()+"'");
			}
			if(p.getPopersonstatus()!=null&&!"".equals(p.getPopersonstatus())){
				hql.append("  and popersonstatus ='"+p.getPopersonstatus()+"'");
			}
			if(p.getPosex()!=null&&!"".equals(p.getPosex())){
				hql.append(" and  posex ='"+p.getPosex()+"'");
			}
			if(p.getPoimportantlevel()!=null&&!"".equals(p.getPoimportantlevel())){
				hql.append(" and  poimportantlevel ='"+p.getPoimportantlevel()+"'");
			}
			if(p.getPocreateday()!=null&&!"".equals(p.getPocreateday())){
			        rightNow.setTime(p.getPocreateday());
			        rightNow.add(Calendar.YEAR,+1);//日期减1年
				hql.append(" and  pocreateday>='"+simpleDateFormat.format(p.getPocreateday())+"' and pocreateday<'"+simpleDateFormat.format(rightNow.getTime())+"'");
			}
			hql.append(" Order By pocreateday Desc");
			Query query = session.createQuery(hql.toString()); 
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		return list;
	}
	
	/**
	 * 案件记录总数
	 * @return
	 */
	public int findRowCount(Peopleobject p){
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from Peopleobject where 1=1 ");
		if(p.getPoalias()!=null&&!"".equals(p.getPoalias())){
			hql.append(" and  poalias like '%"+p.getPoalias().replaceAll("'", "\"")+"%'");
		}
		if(p.getPocnname()!=null&&!"".equals(p.getPocnname())){
			hql.append("  and pocnname like '%"+p.getPocnname().replaceAll("'", "\"")+"%'");
		}
		if(p.getPocontrolstatus()!=null&&!"".equals(p.getPocontrolstatus())){
			hql.append(" and  pocontrolstatus ='"+p.getPocontrolstatus()+"'");
		}
		if(p.getPocountry()!=null&&!"".equals(p.getPocountry())){
			hql.append("  and pocountry ='"+p.getPocountry()+"'");
		}
		if(p.getPodirectionof()!=null&&!"".equals(p.getPodirectionof())){
			hql.append("  and podirectionof ='"+p.getPodirectionof()+"'");
		}
		if(p.getPodutyman()!=null&&!"".equals(p.getPodutyman())){
			hql.append(" and  podutyman like '%"+p.getPodutyman().replaceAll("'", "\"")+"%'");
		}
		if(p.getPoenname()!=null&&!"".equals(p.getPoenname())){
			hql.append(" and  poenname like '%"+p.getPoenname().replaceAll("'", "\"")+"%'");
		}
		if(p.getPohukou()!=null&&!"".equals(p.getPohukou())){
			hql.append(" and  pohukou like '%"+p.getPohukou().replaceAll("'", "\"")+"%'");
		}
		if(p.getPolocation()!=null&&!"".equals(p.getPolocation())){
			hql.append(" and  polocation like '%"+p.getPolocation().replaceAll("'", "\"")+"%'");
		}
		if(p.getPonamespell()!=null&&!"".equals(p.getPonamespell())){
			hql.append(" and  ponamespell like '%"+p.getPonamespell().replaceAll("'", "\"")+"%'");
		}
		if(p.getPonational()!=null&&!"".equals(p.getPonational())){
			hql.append(" and  ponational ='"+p.getPonational()+"'");
		}
		if(p.getPopersonstatus()!=null&&!"".equals(p.getPopersonstatus())){
			hql.append("  and popersonstatus ='"+p.getPopersonstatus()+"'");
		}
		if(p.getPosex()!=null&&!"".equals(p.getPosex())){
			hql.append(" and  posex ='"+p.getPosex()+"'");
		}
		if(p.getPoimportantlevel()!=null&&!"".equals(p.getPoimportantlevel())){
			hql.append(" and  poimportantlevel ='"+p.getPoimportantlevel()+"'");
		}
		if(p.getPocreateday()!=null&&!"".equals(p.getPocreateday())){
			 
	        rightNow.setTime(p.getPocreateday());
	        rightNow.add(Calendar.YEAR,+1);//日期减1年
		hql.append(" and  pocreateday>='"+simpleDateFormat.format(p.getPocreateday())+"' and pocreateday<'"+simpleDateFormat.format(rightNow.getTime())+"'");
	}
		hql.append(" Order By pocreateday Desc");
		Number num = (Number) getHibernateTemplate().find(hql.toString()).iterator().next();
		return  num.intValue();
	}
	public List<Peopleobject> searchPname(long id,String name){
		String hql="from Peopleobject where  id<>"+id+" and pocnname like '%"+name.replaceAll("'", "\"")+"%'";
		return getHibernateTemplate().find(hql); 
	}
	/**
	 * 根据案件id查询工作配置
	 * @param id
	 * @return
	 */
	public List<WorkAllocation> findWorkAllocationByCaseId(Long pId) {
		String hql="from WorkAllocation where caseId=?";
		return getHibernateTemplate().find(hql,pId);
	}
}
