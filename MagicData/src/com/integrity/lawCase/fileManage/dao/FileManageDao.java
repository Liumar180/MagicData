package com.integrity.lawCase.fileManage.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.solr.common.SolrDocument;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;

public class FileManageDao extends HibernateDaoSupport{
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Calendar rightNow = Calendar.getInstance();
	public void edit(FilesObject filesObject) {
		getHibernateTemplate().update(filesObject);
	}
	public FilesObject getFileinfoByid(long id){
		return (FilesObject) getHibernateTemplate().get(FilesObject.class, id);
	}
	public void delete(long id) {
		getHibernateTemplate().bulkUpdate("DELETE FROM FilesObject where id='"+id+"'");
	}
	/**
	 * 分页查询案件
	 * @param page
	 * @return
	 */
	public List<FilesObject> findFileList(PageModel<FilesObject> page, FilesObject p2,final List<SolrDocument> ids,final boolean nothave) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		final FilesObject p=p2;
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			StringBuffer hql = new StringBuffer();
			hql.append("from FilesObject where 1=1");
			if(ids != null && ids.size() != 0){
				hql.append(" and id in(");
				Set<String> set = new HashSet<String>();
				for(int i=0;i<ids.size();i++){
					List<String> pid = new ArrayList<String>();
					try {
						pid = (List<String>) ids.get(i).getFieldValue("attr_parentid");
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(pid != null && pid.size() != 0){
						set.add(pid.get(0));
					}
					if(i==0){
						hql.append(ids.get(i).getFieldValue("id"));
					}else{
						hql.append(","+ids.get(i).getFieldValue("id"));
					}
				}
				for(String pid:set){
					hql.append(","+pid);
				}
				hql.append(")");
			}else{
				if(nothave){
					hql.append(" and id = -1 ");//在solr库中不存在数据
				}
			}
			if(p.getAnnexName()!=null&&!"".equals(p.getAnnexName())){
				hql.append(" and  annexName like '%"+p.getAnnexName().replaceAll("'", "\"")+"%'");
			}
			if(p.getFileContents()!=null&&!"".equals(p.getFileContents())){
				hql.append("  and fileContents like '%"+p.getFileContents().replaceAll("'", "\"")+"%'");
			}
			if(p.getDirection()!=null&&!"".equals(p.getDirection())){
				hql.append(" and  direction ='"+p.getDirection()+"'");
			}
			if(p.getFileName()!=null&&!"".equals(p.getFileName())){
				hql.append("  and fileName like '%"+p.getFileName().replaceAll("'", "\"")+"%'");
			}
			if(p.getRemarkes1()!=null&&!"".equals(p.getRemarkes1())){
				hql.append("  and remarkes1 like '%"+p.getRemarkes1().replaceAll("'", "\"")+"%'");
			}
			if(p.getRemarkes2()!=null&&!"".equals(p.getRemarkes2())){
				hql.append("  and remarkes2 like '%"+p.getRemarkes2().replaceAll("'", "\"")+"%'");
			}
			if(p.getRemarkes3()!=null&&!"".equals(p.getRemarkes3())){
				hql.append("  and remarkes3  ='"+p.getRemarkes3()+"'");
			}
			if(p.getResponsiblePerson()!=null&&!"".equals(p.getResponsiblePerson())){
				hql.append(" and  responsiblePerson like '%"+p.getResponsiblePerson().replaceAll("'", "\"")+"%'");
			}
			if(p.getCreateTime()!=null&&!"".equals(p.getCreateTime())){
			        rightNow.setTime(p.getCreateTime());
			        rightNow.add(Calendar.YEAR,+1);//日期减1年
				hql.append(" and  createTime>='"+simpleDateFormat.format(p.getCreateTime())+"' and createTime<'"+simpleDateFormat.format(rightNow.getTime())+"'");
			}
			hql.append(" Order By createTime Desc");
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
	public int findRowCount(FilesObject p){
		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from FilesObject where 1=1 ");
		if(p.getAnnexName()!=null&&!"".equals(p.getAnnexName())){
			hql.append(" and  annexName like '%"+p.getAnnexName().replaceAll("'", "\"")+"%'");
		}
		if(p.getFileContents()!=null&&!"".equals(p.getFileContents())){
			hql.append("  and fileContents like '%"+p.getFileContents().replaceAll("'", "\"")+"%'");
		}
		if(p.getDirection()!=null&&!"".equals(p.getDirection())){
			hql.append(" and  direction ='"+p.getDirection()+"'");
		}
		if(p.getFileName()!=null&&!"".equals(p.getFileName())){
			hql.append("  and fileName like '%"+p.getFileName().replaceAll("'", "\"")+"%'");
		}
		if(p.getRemarkes1()!=null&&!"".equals(p.getRemarkes1())){
			hql.append("  and remarkes1 like '%"+p.getRemarkes1().replaceAll("'", "\"")+"%'");
		}
		if(p.getRemarkes2()!=null&&!"".equals(p.getRemarkes2())){
			hql.append("  and remarkes2 like '%"+p.getRemarkes2().replaceAll("'", "\"")+"%'");
		}
		if(p.getRemarkes3()!=null&&!"".equals(p.getRemarkes3())){
			hql.append("  and remarkes3  ='"+p.getRemarkes3()+"'");
		}
		if(p.getResponsiblePerson()!=null&&!"".equals(p.getResponsiblePerson())){
			hql.append(" and  responsiblePerson like '%"+p.getResponsiblePerson().replaceAll("'", "\"")+"%'");
		}
		if(p.getCreateTime()!=null&&!"".equals(p.getCreateTime())){
		        rightNow.setTime(p.getCreateTime());
		        rightNow.add(Calendar.YEAR,+1);//日期减1年
			hql.append(" and  createTime>='"+simpleDateFormat.format(p.getCreateTime())+"' and createTime<'"+simpleDateFormat.format(rightNow.getTime())+"'");
		}
		hql.append(" Order By createTime Desc");
		Number num = (Number) getHibernateTemplate().find(hql.toString()).iterator().next();
		return  num.intValue();
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
	public List<FilesObject> searchFname(long id,String name){
		String hql="from FilesObject where  id<>"+id+" and fileName like '%"+name.replaceAll("'", "\"")+"%'";
		return getHibernateTemplate().find(hql); 
	}
}
