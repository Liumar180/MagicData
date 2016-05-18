/**
 * 
 */
package com.integrity.dataSmart.dataImport.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.util.importData.JdbcUtils;
import com.integrity.login.util.PageInfo;
import com.mysql.jdbc.PreparedStatement;

/**
 * @author HanXue
 *
 */
public class DataImpTaskDao extends HibernateDaoSupport{
	
	/**
	 * 保存数据导入任务
	 * @param diTask 数据导入任务对象
	 */
	public Integer saveDataImpTask(DataImportTask diTask) {
		HibernateTemplate ht = this.getHibernateTemplate();
		Integer id = (Integer) ht.save(diTask);
		return id;
	}
	
	/**
	 * 更新数据导入任务
	 * @param diTask 数据导入任务对象
	 */
	public void updateDataImpTask(DataImportTask diTask) {
		getHibernateTemplate().update(diTask);
	}
	/**
	 * 根据ID查询任务信息
	 * @param id
	 * @return
	 */
	public DataImportTask findTaskById(Integer id){
		try {
			DataImportTask dit = (DataImportTask) this.getHibernateTemplate().get(DataImportTask.class, id);
			return dit;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 判断任务名称的唯一性（任务名称是否已经存在）
	 * @param taskName 任务名称
	 * @return true表示任务名称已经存在，false表示不存在
	 */
    public boolean checkTaskNameExist(String taskName,String taskId) {
		List<Object> taskList = null;
		try {
			String hql = "from DataImportTask s where s.taskName = ?";
			if(null!=taskId&&!"".equals(taskId.trim())){
				hql = hql+" and s.id"+taskId.trim();
			}
			taskList = getHibernateTemplate().find(hql, taskName);
		} catch (RuntimeException e) {
			return false;
		}
		if (taskList != null) {
			if (taskList.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
    
    /**
     * 通过任务名称获得数据导入任务对象
     * @param taskName
     * @return
     */
    public DataImportTask getDITaskByName(String taskName) {
		List<Object> taskList = null;
		try {
			String hql = "from DataImportTask s where s.taskName = ?";
			taskList = getHibernateTemplate().find(hql, taskName);
		} catch (RuntimeException e) {
			return null;
		}
		if (taskList != null) {
			if (taskList.size() > 0) {
				return (DataImportTask)taskList.get(0);
			}
		}
		return null;
	}
    
    /**
     * 通过任务Id获得数据导入任务对象
     * @param taskId
     * @return
     */
    public DataImportTask getDITaskById(Long taskId) {
    	String hql = "from DataImportTask s where s.id = "+taskId;
    	List<Object> obj = getHibernateTemplate().find(hql);
    	DataImportTask dt = (DataImportTask)obj.get(0);
		return dt;
	}
    /**
     * 通过任务Id获得数据导入任务对象
     * @param taskId
     * @return
     */
    public List<DataImportTask> getDITaskByDataConnId(Long dataConnId) {
    	String hql = "from DataImportTask s where s.dataConnId = "+dataConnId;
    	return getHibernateTemplate().find(hql);
	}
	/**
	 * 分页查询导入任务
	 * @param page 分页参数
	 * @return
	 */
	public List<DataImportTask> findImportTasks(PageInfo page) {
		final int pageNo = page.getPageNo();
		final int pagesize = page.getPageSize();
		List list = getHibernateTemplate().executeFind(new HibernateCallback() { 
			public Object doInHibernate(Session session) 
			throws HibernateException, SQLException { 
			String hql = "from DataImportTask ORDER BY borntime DESC"; 
			Query query = session.createQuery(hql); 
			query.setFirstResult((pageNo - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			List list = query.list(); 
			return list; 
			}
		}); 
		String sql="select count(*) from DataImportTask";
		Number num = (Number) getHibernateTemplate().find(sql).iterator().next();
		Integer rowCount = num.intValue();
		page.setTotalRecords(rowCount);
		int totalPages = 0;
		if (rowCount % page.getPageSize() == 0) {
			totalPages = rowCount / page.getPageSize();
		} else {
			totalPages = rowCount / page.getPageSize() + 1;
		}
		page.setTotalPages(totalPages);
		return list;
	}
	
	/**
	 * 删除单个导入任务
	 * @param entity
	 */
	public void deleteDTask(DataImportTask entity) {
		getHibernateTemplate().delete(entity);
	}
	
	/**
	 * 删除多个导入任务
	 * @param entity
	 */
	public void deleteDTaskList(List<DataImportTask> entityList) {
		getHibernateTemplate().deleteAll(entityList);
	}
	
	/**
	 * 根据任务ID删除单个导入任务
	 * @param entity
	 */
	public void deleteDTaskById(String id) {
		final String hql = "delete from DataImportTask t where t.id = "+id;
		getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						return query.executeUpdate();
					}
				});
	}

	/**
	 * 判断是否存在正在执行的任务
	 */
	public int getExecuteTask() {
		List<Object> taskList = null;
		try {
			String hql = "from DataImportTask s where s.runStatus = 1";
			taskList = getHibernateTemplate().find(hql);
			if (taskList != null) {
				return taskList.size();
			}
		} catch (RuntimeException e) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * @param diTask
	 * 根据任务的导入数据量，更新任务表
	 */
	public void toRefreshData(Integer taskId,Long ImpCounts) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = JdbcUtils.getConnection();
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sql = "update data_import_task d set d.importCount = ? where d.id=?"; 
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setLong(1, ImpCounts);
			ps.setInt(2, taskId);
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, ps, conn);
		}
        
	}

}
