package com.integrity.lawCase.relation.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.integrity.lawCase.relation.bean.AllRelation;

public class AllRelationDao extends HibernateDaoSupport{

	/** 获得指定ID的关联关系数据 */
	public AllRelation getAllRelationByType(String typeId,String type){
		String hql = "from AllRelation t where t.typeId = ? and t.type = ?";
		Object[] hqlValues = new Object[2];
		hqlValues[0] = Long.parseLong(typeId);
		hqlValues[1] = type;
		List<?> resultList = getHibernateTemplate().find(hql, hqlValues);
		if(null==resultList||resultList.size()<1){
			return null;
		}else{
			Object tempObj = resultList.get(0);
			if(null==tempObj){
				return null;
			}else{
				return (AllRelation)tempObj;
			}
		}
	}
	
	/** 获得指定ID的关联关系中所包含的所有对象集合 */
	public List<?> getRelationObjs(final String className,final String ids){
		if(null!=ids&&!"".equals(ids)){
			String[] idsArray = ids.split(",");
			final Long[] idsLong = new Long[idsArray.length];
			for(int i =0 ;i<idsArray.length;i++){
				idsLong[i] = Long.parseLong(idsArray[i]);
			}
			String hql = "from "+className+" t where t.id in ("+ids+")";
			return getHibernateTemplate().find(hql);
		}else{
			return null;
		}
		
	}
	
	/** 获得指定ID所对应的对象 */
	public Object getTypeObj(String className,String id){
		String hql = "from "+className+" t where t.id = ?";
		return getHibernateTemplate().find(hql, id);
	}
	
	/**新增关联关系对象*/
	public void saveAllRel(AllRelation tempAr){
		getHibernateTemplate().save(tempAr);
	}
	
	/**更新关联关系对象*/
	public void updateAllRel(AllRelation tempAr){
		getHibernateTemplate().update(tempAr);
	}
	
	/**删除关联关系对象*/
	public void delAllRel(AllRelation tempAr){
		getHibernateTemplate().delete(tempAr);
	}

}
