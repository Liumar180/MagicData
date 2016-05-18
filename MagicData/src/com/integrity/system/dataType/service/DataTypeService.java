package com.integrity.system.dataType.service;

import java.util.List;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.system.dataType.bean.DataObject;
import com.integrity.system.dataType.bean.DataType;
import com.integrity.system.dataType.dao.DataTypeDao;

/**
 * @author liubf
 * 对象模块service
 *
 */
public class DataTypeService {
	
	private DataTypeDao dataTypeDao;
	public DataTypeDao getDataTypeDao() {
		return dataTypeDao;
	}
	public void setDataTypeDao(DataTypeDao dataTypeDao) {
		this.dataTypeDao = dataTypeDao;
	}

	/**
	 * @param page
	 * @param dataObject
	 * @return
	 * 获取对象列表
	 */
	public PageModel<DataObject> searchObjList(PageModel<DataObject> page,DataObject dataObject){
		List<DataObject> lst = dataTypeDao.searchObjlist(page,dataObject);
		for(int i=0;i<lst.size();i++){
			List<DataType> s = dataTypeDao.findDataList(lst.get(i).getId());
			StringBuffer str = new StringBuffer();
			if(s.size() != 0){
				for(int j=0;j<s.size();j++){
					if(s.get(j).getIsIndex().equals("true")){
						str.append(s.get(j).getProName()+"*（"+s.get(j).getProType()+"）"+",");
					}else{
						str.append(s.get(j).getProName()+"（"+s.get(j).getProType()+"）"+",");
					}
				}
				lst.get(i).setProperty(str.substring(0, str.length()-1));
			}
		}
		page.setTotalRecords(dataTypeDao.Acounts(dataObject));
		page.setTotalPage(page.getTotalPages());
		page.setList(lst);
		return page;
		
	}
	/**
	 * @param ho
	 * @param str1
	 * @param str2
	 * @param str3
	 * 保存对象
	 */
	public void saveDataObject(DataObject ho,String str1,String str2,String str3){
		//2String 3Date 5Integer 1Number 4Boolean 6Serializable 7Binary
		Long id = dataTypeDao.saveDataObject(ho);
		String[] proname = str1.split(",");
		String[] protype = str2.split(",");
		String[] check = str3.split(",");
		if(proname != null && proname.length != 0){
			for(int i=0;i<proname.length;i++){
				DataType dt = new DataType();
				dt.setProName(proname[i]);
				dt.setProType(protype[i]);
				dt.setObjId(id.toString());
				dt.setIsIndex(check[i]);
				dt.setProNum(compareBack(protype[i]));
				dataTypeDao.saveTypes(dt);
			}
		}
		
	}
	/**
	 * @param ho
	 * @param str1
	 * @param str2
	 * @param str3
	 * @param oid
	 * @param pid
	 * @param pronames
	 * @param protypes
	 * 更新对象
	 */
	public void updateDataObject(DataObject ho,String str1,String str2,String str3,String oid,
			String pid,String pronames,String protypes){
		int count = 0;
		dataTypeDao.updateDataObject(ho);
		String[] pids = pid.split(",");
		String[] pronames1 = pronames.split(",");
		String[] protypes1 = protypes.split(",");
		String[] check = str3.split(",");
		
		if(pids != null && !pids[0].equals("")){
			for(int i=0;i<pids.length;i++){
				if(pids[i].indexOf("fsx") != -1){
					count = count + 1;
				DataType dt = new DataType();
				dt.setId(Long.parseLong(pids[i].substring(3)));
				dt.setProName(pronames1[i]);
				dt.setProType(protypes1[i]);
				dt.setObjId(oid);
				dt.setIsIndex(check[i]);
				dt.setProNum(compareBack(protypes1[i]));
				dataTypeDao.updateTypes(dt);
				}
			}
		}
		
		//编辑时，新增数据；
		String[] proname = str1.split(",");
		String[] protype = str2.split(",");
		
		if(proname != null && !proname[0].equals("")){
			for(int i=0;i<proname.length;i++){
				DataType dt = new DataType();
				dt.setProName(proname[i]);
				dt.setProType(protype[i]);
				dt.setObjId(oid);
				dt.setIsIndex(check[count+i]);
				dt.setProNum(compareBack(protype[i]));
				dataTypeDao.saveTypes(dt);
			}
		}
	}
	public DataObject forUpdateObjs(Long id){
		return dataTypeDao.findDetails(id);
	}
	/**
	 * @param Id
	 * @return
	 * 获取对象属性
	 */
	public List<DataType> findProList(Long Id){
		return dataTypeDao.findDataList(Id);
	}
	/**
	 * @param ids
	 * 删除对象
	 */
	public void delObject(List<String> ids){
		dataTypeDao.delObjects(ids);
		//删除关联属性
		dataTypeDao.delTypes(ids);
		
	}
	/**
	 * @param ids
	 * 删除属性
	 */
	public void delProByid(List<String> ids){
		dataTypeDao.delType(ids);
	}
	/**
	 * @param str
	 * @return 获取返回类型 号
	 */
	public String compareBack(String str){
		String strs = "2";
		if(str.trim().equals("String")){
			return strs ="2";
		}else if(str.trim().equals("Date")){
			return strs ="3";
		}else if(str.trim().equals("Integer")){
			return strs ="5";
		}else if(str.trim().equals("Number")){
			return strs ="1";
		}else if(str.trim().equals("Boolean")){
			return strs ="4";
		}else if(str.trim().equals("Serializable")){
			return strs ="6";
		}else if(str.trim().equals("Binary")){
			return strs ="7";
		}
		return strs;
		
	}

}
