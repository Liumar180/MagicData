package com.integrity.lawCase.fileManage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.fileManage.dao.FileManageDao;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.service.AllRelationService;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;


public class FileManageService {
	private FileManageDao fileManageDao;
	private AllRelationService allRelationService;
	public FileManageDao getFileManageDao() {
		return fileManageDao;
	}
	public void setFileManageDao(FileManageDao fileManageDao) {
		this.fileManageDao = fileManageDao;
	}
	public AllRelationService getAllRelationService() {
		return allRelationService;
	}
	public void setAllRelationService(AllRelationService allRelationService) {
		this.allRelationService = allRelationService;
	}
	public FilesObject getFileinfoByid(long id){
		return fileManageDao.getFileinfoByid(id);
	}
	public void edit(FilesObject filesObject) {
		fileManageDao.edit(filesObject);
	}
	public Long saveR(FilesObject filesObject) {
		return (Long)fileManageDao.getHibernateTemplate().save(filesObject);
	}
	public void saveFileRelation(String rootId, String rootType,long id) {
		allRelationService.addOrUpdateRelation(rootId, rootType, id, ConstantManage.FILEOBJECTTYPE);
	}
	/**
	 * 获取分页VO
	 * @param page
	 * @return
	 */
	public PageModel<FilesObject> findFilePageModel(PageModel<FilesObject> page, FilesObject f,List<SolrDocument> ids,boolean notHaveSolrData) {
		List<FilesObject> lst = fileManageDao.findFileList(page, f,ids,notHaveSolrData);
		page.setTotalRecords(fileManageDao.findRowCount(f));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
	}
	/**
	 * 根据当前节点的id及类型获得所有关联关系
	 * 
	 * @param typeId
	 *            当前节点ID
	 * @param type
	 *            当前节点的类型，类型取自ConstantManage内的类型常量
	 * @return AllRelationWapper包含节点的所有关联信息
	 */

	public AllRelationWapper findRelation(Long id, String type) {
		return allRelationService.getAllRelationForType(id+"", type);
	}
	/**
	 * 根据案件id查询工作配置
	 * @param id
	 * @return
	 */
	public List<WorkAllocation> findWorkAllocationByCaseId(Long caseId) {
		return fileManageDao.findWorkAllocationByCaseId(caseId);
	}
	public void delete(long id) {
		fileManageDao.delete(id);
	}
	/**
	 * @param rootId
	 * @param rootType
	 * 删除当前对象（主机） 所有关联
	 */
	public void delAllRelactions(String rootIds,String rootType){
		allRelationService.delAllRelationByIds(rootIds, rootType);
	}
	/**
	 * @param rootId
	 * @param rootType
	 * @param relId
	 * @param relType
	 * 删除 单一关联对象
	 */
	public void delSingleRelation(String rootId,String rootType,String relId,String relType){
		allRelationService.delRelation(rootId, rootType, relId, relType);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> searchFname(long id,String name){
		List namList=new ArrayList<String>();
		for (FilesObject p : fileManageDao.searchFname(id,name)) {
			namList.add(p.getId()+";"+p.getFileName());
		}
		return namList;
	}
}
