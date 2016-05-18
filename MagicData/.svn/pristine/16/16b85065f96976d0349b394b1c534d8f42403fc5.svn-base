/**
 * 
 */
package com.integrity.dataSmart.dataImport.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.pentaho.di.core.RowMetaAndData;

import com.integrity.dataSmart.dataImport.bean.DataImportTask;
import com.integrity.dataSmart.dataImport.bean.TDatabase;
import com.integrity.dataSmart.dataImport.pojo.DataImpAddForm;
import com.integrity.dataSmart.dataImport.pojo.DataImpAddPojo;
import com.integrity.dataSmart.dataImport.service.DBService;
import com.integrity.dataSmart.dataImport.service.DataImpAddService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author HanXue
 * 
 */
public class DataImpAddAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	/**
	 * 新增数据导入的Service
	 */
	private DataImpAddService dataImpAddService;

	/**
	 * 操作数据库连接的Service
	 */
	private DBService dBService;


	/**
	 * 导入新增用Session内Key
	 */
	private static String dataImpSessKey = "DataImport_Add_Task";

	/**
	 * 导入新增用表单
	 */
	private DataImpAddForm addForm;

	/**
	 * SQL语句测试返回流
	 */
	private InputStream inputStream;

	/**
	 * SQL语句数据库连接Id
	 */
	private String dbConnId;

	/**
	 * SQL语句值
	 */
	private String sqlTest;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 用于数据库新增完成后回显
	 */
	private DataImportTask returnShowDIT;

	/**
	 * 是否为更新操作,1是0否
	 */
	private String updateFlag = "0";

	/**
	 * 任务ID
	 */
	private String taskId;

	/**
	 * 错误信息内容
	 */
	private String errorStr;
	
	/**
	 * 后退标识
	 */
	private String rebackFlag = "0";
	
	/**
	 * 首次编辑跳转
	 */
	private String firstUpdate = "0";

	/**
	 * 预加载数据库连接选择页
	 * @return
	 */
	public String getDImpAConPage() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = request.getSession();
		String webRealPath = ServletActionContext.getServletContext().getRealPath("/");
		boolean backDataFlag = false;
		// 如果是编辑 读取数据库中信息
		if ("1".equals(updateFlag.trim())) {
			if(null==taskId||"".equals(taskId.trim())){
				errorStr = "编辑导入任务时,任务ID不能为空";
				request.setAttribute("errorStr", errorStr);
				return ERROR;
			}
			Object tempObj = hs.getAttribute(dataImpSessKey);
			boolean reloadFlag = false;//是否需要重新读取数据
			if (tempObj == null
					||null==((DataImpAddPojo)tempObj).getTaskId()
					||!taskId.equals(((DataImpAddPojo)tempObj).getTaskId()+"")||"1".equals(firstUpdate)){
				//Session中无关联对象或Session中taskId与当前Id不同或重新编辑下，重读数据
				reloadFlag = true;
			}
			if (reloadFlag&&!"1".equals(rebackFlag)) {//重新读取数据
				DataImportTask dit = dataImpAddService.getDITaskById(Long.parseLong(taskId));
				DataImpAddPojo dataImpAddPojo = new DataImpAddPojo();
				hs.setAttribute(dataImpSessKey, dataImpAddPojo);
				dataImpAddPojo.setTaskId(dit.getId());
				dataImpAddPojo.setTaskName(dit.getTaskName());
				dataImpAddPojo.setDbConnId(dit.getDataConnId());
				dataImpAddPojo.setTaskType(dit.getTaskType());
				if(2==dit.getTaskType()){
					dataImpAddPojo.setUploadFile(dit.getUploadFile());
				}else{
					try {
						TDatabase tDatabase = dBService.findDB(dit.getDataConnId());
						if(null==tDatabase){
							errorStr = "连接相应数据库失败，此任务关联的数据库连接配置或已失效";
							request.setAttribute("errorStr", errorStr);
							return ERROR;
						}
					} catch (Exception e) {
						e.printStackTrace();
						errorStr = "连接相应数据库失败，此任务关联的数据库连接配置或已失效";
						request.setAttribute("errorStr", errorStr);
						return ERROR;
					}
					dataImpAddPojo.setSourceTable(dit.getSourceName());
					dataImpAddPojo.setTargetTable(dit.getTargetName());
					dataImpAddPojo.setSqlFlag(dit.getSourceType() == 1 ? true
							: false);
					dataImpAddPojo.setSqlText(dit.getSqlOrder());
					dataImpAddPojo.setCheckSourceFields(dit.getCheckSourceFields());
					dataImpAddPojo.setCheckSourceFieldsType(dit.getCheckSourceTypes());
					dataImpAddPojo.setCheckTargetFields(dit.getCheckTargetFields());
					dataImpAddPojo.setCheckTargetFieldsType(dit.getCheckTargetTypes());
					dataImpAddPojo.setTargetFields(dataImpAddService
							.getTargetTables(webRealPath, dit.getTargetName()));
				}
			}else{
				backDataFlag = true;
			}
		} else {
			if (hs.getAttribute(dataImpSessKey) == null||!"1".equals(rebackFlag)) {
				DataImpAddPojo dataImpAddPojo = new DataImpAddPojo();
				hs.setAttribute(dataImpSessKey, dataImpAddPojo);
			}else{
				backDataFlag = true;
			}
		}
		
		if(backDataFlag){//后退即上一步情况下 保存表单已经填写的数据
			DataImpAddPojo dataImpAddPojo = (DataImpAddPojo)hs.getAttribute(dataImpSessKey);
			String sourceTable = addForm.getSourceTable();
			String sqlFlag = addForm.getSqlFlag();
			String sqlText = addForm.getSqlText();
			String targetTable = addForm.getTargetTable();
			if(null!=targetTable&&!"".equals(targetTable)){
				dataImpAddPojo.setTargetTable(targetTable);
			}
			if("1".equals(sqlFlag)){
				dataImpAddPojo.setSqlFlag(true);
				if(null!=sqlText&&!"".equals(sqlText)){
					dataImpAddPojo.setSqlText(sqlText);
				}
			}else{
				dataImpAddPojo.setSqlFlag(false);
				if(null!=sourceTable&&!"".equals(sourceTable)){
					dataImpAddPojo.setSourceTable(sourceTable);
				}
			}
		}
		
		return SUCCESS;
	}

	
	/**
	 * 跳转至表选择页面
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public String nextDImpBTablePage() throws NumberFormatException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = request.getSession(false);

		if (null == addForm) {
			errorStr = "表单信息为空";
			request.setAttribute("errorStr", errorStr);
			return ERROR;// 表单数据为空
		}
		Integer  taskType = addForm.getTaskType();
		if(null==taskType){
			errorStr = "请选择正确的任务类型";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}else{
			if(1==taskType){
				if(null == addForm.getDbConnId()
						|| "".equals(addForm.getDbConnId())){
					errorStr = "数据库连接为空";
					request.setAttribute("errorStr", errorStr);
					return ERROR;
				}
			}else if(2==taskType){
				if(null == addForm.getUploadFile()
						|| "".equals(addForm.getUploadFile())){
					errorStr = ".eml文件上传目录为空";
					request.setAttribute("errorStr", errorStr);
					return ERROR;
				}
			}
		}
		
		if (null == hs) {
			errorStr = "登陆超时，请重新登陆后再进行操作";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		if (hs.getAttribute(dataImpSessKey) == null) {
			errorStr = "登陆超时，请重新登陆后再进行操作";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		// 编辑下必须包含原来的导入数据信息
		if ("1".equals(updateFlag)) {
			if (hs.getAttribute(dataImpSessKey) == null) {
				errorStr = "请选择正确的导入任务进行编辑";
				request.setAttribute("errorStr", errorStr);
				return ERROR;// 编辑时如果没有数据信息 就返回
			}
		}
		
		Map<TDatabase, Map<String, String>> connSource = null;
		Map<String, String> tableFields = null;
		updateFlag = updateFlag.trim();

		// 新增情况下非反复步骤时需要填充Session数据
		if (!"1".equals(updateFlag) && hs.getAttribute(dataImpSessKey) == null) {
			DataImpAddPojo dataImpAddPojo = new DataImpAddPojo();
			dataImpAddPojo.setTaskName(addForm.getTaskName());
			dataImpAddPojo.setDbConnId(Long.parseLong(addForm.getDbConnId()
					.trim()));
			dataImpAddPojo.setTaskType(addForm.getTaskType());
			// 获得所有表及字段信息，避免在新增过程中反复查找
			connSource = dataImpAddService.getSourceTables(Long
					.parseLong(addForm.getDbConnId()));
			if (null != connSource && connSource.size() > 0) {
				Set<TDatabase> connSet = connSource.keySet();
				if (null != connSet && connSet.size() > 0) {
					TDatabase conn = connSet.iterator().next();
					if (null != conn) {
						dataImpAddPojo.setDbConnName(conn.getConnectionName());
						tableFields = connSource.get(conn);
						dataImpAddPojo.setTableFields(tableFields);
					}
				}
			}
			hs.setAttribute(dataImpSessKey, dataImpAddPojo);
		}else if (hs.getAttribute(dataImpSessKey) != null) {// 编辑或新增反复步骤时
			//1.填充表单数据
			//2.读取关联该数据连接的数据库信息，此处要判断是否更换了数据库连接，在新增和更换下都要重新加载
			DataImpAddPojo dataImpAddPojo = (DataImpAddPojo) hs
					.getAttribute(dataImpSessKey);
			dataImpAddPojo.setTaskName(addForm.getTaskName());
			dataImpAddPojo.setTaskType(addForm.getTaskType());
			//如果是第三步后退的，需要保存第三步表单中填写的信息
			String checkSourceFormFields = addForm.getCheckSourceFields();
			String checkTargetFormFields = addForm.getCheckTargetFields();
			if(null!=checkSourceFormFields&&!"".equals(checkSourceFormFields)){
				dataImpAddPojo.setCheckSourceFields(checkSourceFormFields);
			}
			if(null!=checkTargetFormFields&&!"".equals(checkTargetFormFields)){
				dataImpAddPojo.setCheckTargetFields(checkTargetFormFields);
			}
			
			//判断是否更换了数据库连接
			boolean changeConnFlag = false;
			if (null == addForm.getDbConnId()) {
				errorStr = "没有选择数据连接，请选择一个数据库连接后再进行操作";
				request.setAttribute("errorStr", errorStr);
				return ERROR;
			}
			String formDbConnId = addForm.getDbConnId().trim();
			if (null != dataImpAddPojo.getDbConnId()) { // 如果更换了数据库连接
				// 如果addForm中的连接ID 与Session中的不符(说明更换了数据库连接)，则需要更新所有源Table信息
				if (!(dataImpAddPojo.getDbConnId().toString())
						.equals(formDbConnId)) {
					changeConnFlag = true;
				}
			} else {
				changeConnFlag = true;
			}
			if(changeConnFlag){//如果更换了数据库连接 清空Session中关联数据
				dataImpAddPojo.setDbConnId(Long.parseLong(formDbConnId));
				dataImpAddPojo.setSourceTable("");
				dataImpAddPojo.setTargetTable("");
				dataImpAddPojo.setSqlFlag(false);
				dataImpAddPojo.setSqlText("");
			}
			if (null == dataImpAddPojo.getTableFields()
					|| dataImpAddPojo.getTableFields().size() < 1
					|| changeConnFlag) {//重新获得数据库连接的表信息
				// 获得所有表及字段信息，避免在新增过程中反复查找
				// 如果addForm中的连接ID 与Session中的不符(说明更换了数据库连接)，则需要更新所有源Table信息
				connSource = dataImpAddService.getSourceTables(Long
						.parseLong(addForm.getDbConnId()));
				if (null != connSource && connSource.size() > 0) {
					Set<TDatabase> connSet = connSource.keySet();
					if (null != connSet && connSet.size() > 0
							&& null != (connSet.iterator().next())) {
						TDatabase conn = connSet.iterator().next();
						dataImpAddPojo.setDbConnName(conn.getConnectionName());
						tableFields = connSource.get(conn);
						dataImpAddPojo.setTableFields(tableFields);
						dataImpAddPojo.setSourceFields(null);
						if(!("1".equals(updateFlag)&&!changeConnFlag)){
							dataImpAddPojo.setSqlText(null);
							dataImpAddPojo.setCheckSourceFields(null);
							dataImpAddPojo.setCheckTargetFields(null);	
						}					
					} else {
						errorStr = "无法取得指定的数据库连接:数据库连接信息配置错误,或无访问权限";
						request.setAttribute("errorStr", errorStr);
						return ERROR;
					}
				} else {
					errorStr = "无法取得指定的数据库连接:数据库连接信息配置错误,或无访问权限";
					request.setAttribute("errorStr", errorStr);
					return ERROR;
				}
			} else {
				tableFields = dataImpAddPojo.getTableFields();
			}
		}

		if (null == tableFields || tableFields.size() < 1) {
			errorStr = "无法取得指定数据库连接的表信息:数据库连接信息配置错误,或无访问权限,或数据库中无表信息";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * @throws Exception
	 * @throws NumberFormatException
	 * @throws UnsupportedEncodingException
	 *             测试SQL语句正确性
	 * @return
	 * @throws
	 */
	public String sqlTest() throws NumberFormatException, Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = request.getSession();
		RowMetaAndData getOneRowBySQL = null;
		if (null == dbConnId || "".equals(dbConnId)
				|| !dbConnId.matches("[0-9]+")) {
			return ERROR;
		}
		getOneRowBySQL = dataImpAddService.getOneRowBySQL(
				Long.parseLong(dbConnId), sqlTest);
		if (getOneRowBySQL == null) {
			inputStream = new ByteArrayInputStream(
					"{\"total\":\"0\",\"rows\":[{\"result\":\"FAIL\"}]}"
							.getBytes("utf-8"));
			return SUCCESS;
		}
		inputStream = new ByteArrayInputStream(
				"{\"total\":\"0\",\"rows\":[{\"result\":\"SUCCESS\"}]}"
						.getBytes("utf-8"));
		//如果SQL语句测试成功，存储SQL语句关联的数据库表信息至Session
		if (hs.getAttribute(dataImpSessKey) != null) {
			DataImpAddPojo dataImpAddPojo = (DataImpAddPojo) hs
					.getAttribute(dataImpSessKey);
			Map<String, String> tableFields = dataImpAddPojo.getTableFields();
			dataImpAddPojo.setSqlFlag(true);
			dataImpAddPojo.setSourceTable("SQL语句");
			dataImpAddPojo.setSqlText(sqlTest);
			Map<String, String> tempSqlFields = dataImpAddService.createSourceFieldsForSQL(getOneRowBySQL);
			if(null!=tableFields){
				tableFields.put(DataImpAddService.SQL_TABLE_NAME, tempSqlFields.get(DataImpAddService.SQL_TABLE_NAME));
			}
		}
		return SUCCESS;
	}

	/**
	 * 跳转至字段选择页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String nextDImpCFieldPage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = request.getSession(false);
		String webRealPath = ServletActionContext.getServletContext().getRealPath("/");
		if (null == hs || hs.getAttribute(dataImpSessKey) == null) {
			errorStr = "登陆超时，请重新登陆后再进行操作";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		if (null == addForm) {
			errorStr = "表单信息为空，请填写正确的表单信息";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		if ("1".equals(updateFlag)) {// 编辑下必须包含原来的导入数据信息
			if (hs.getAttribute(dataImpSessKey) == null) {
				errorStr = "请选择正确的导入任务进行编辑";
				request.setAttribute("errorStr", errorStr);
				return ERROR;// 编辑时如果没有数据信息 就返回
			}
		}

		DataImpAddPojo dataImpAddPojo = (DataImpAddPojo) hs
				.getAttribute(dataImpSessKey);
		//此处为第二步回传的反复信息
		String checkSourceFormFields = addForm.getCheckSourceFields();
		String checkTargetFormFields = addForm.getCheckTargetFields();
		dataImpAddPojo.setCheckSourceFields(checkSourceFormFields);
		dataImpAddPojo.setCheckTargetFields(checkTargetFormFields);
		
		if(null==addForm.getTargetTable()||"".equals(addForm.getTargetTable().trim())){
			errorStr = "目标数据源为空，请正确填写的表单再进行操作";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		
		/*如果修改了目标数据库 需要清空选择项*/
		String formTargetTable = addForm.getTargetTable().trim();
		if(null==dataImpAddPojo.getTargetTable()||"".equals(dataImpAddPojo.getTargetTable().trim())||!dataImpAddPojo.getTargetTable().equals(formTargetTable)){
			dataImpAddPojo.setTargetTable(formTargetTable);
			dataImpAddPojo.setCheckSourceFields(null);
			dataImpAddPojo.setCheckTargetFields(null);
		}
		
		//对SQL语句变更的进行预读处理
		String formSourceTable = addForm.getSourceTable();
		String formSqlFlag = "0";
		if (null != addForm.getSqlFlag() && addForm.getSqlFlag().length() > 0) {
			formSqlFlag = addForm.getSqlFlag().trim();
		}
		String formSqlText = "";
		if (null != addForm.getSqlText()) {
			formSqlText = addForm.getSqlText().trim();
		}
		boolean flushFlag = false;// 是否刷新重读表信息
		boolean firstSqlReadFlag = false;//SQL语句是否是编辑时第一次加载
		Map<String, String> tableFields = dataImpAddPojo.getTableFields();
		String sourceFieldsStr = "";

		if (null != formSqlFlag && "1".equals(formSqlFlag)) {
			dataImpAddPojo.setSqlFlag(true);
			String dataSqlText = dataImpAddPojo.getSqlText();
			if (null == dataSqlText || "".equals(dataSqlText.trim())
					|| !dataSqlText.trim().equals(formSqlText)
					|| (null==tableFields.get(DataImpAddService.SQL_TABLE_NAME))
					|| ("".equals(tableFields.get(DataImpAddService.SQL_TABLE_NAME)))) {// 如果SQL语句发生变化
				firstSqlReadFlag = true;
				flushFlag = true;
			}else if (dataSqlText.trim().equals(formSqlText)
					&&( (null==tableFields.get(DataImpAddService.SQL_TABLE_NAME))|| ("".equals(tableFields.get(DataImpAddService.SQL_TABLE_NAME)))) ){// 如果SQL语句发生变化
				flushFlag = true;
			}
			if (flushFlag) {
				dataImpAddPojo.setSqlText(formSqlText);
				dataImpAddPojo.setSourceTable("SQL语句");
				// 如果是SQL语句 实时的取得包含的源数据字段
				Map<String, String> tempSqlFields = dataImpAddService.getSourceFieldsForSQL(
						dataImpAddPojo.getDbConnId(), formSqlText);
				sourceFieldsStr = tempSqlFields
						.get(DataImpAddService.SQL_TABLE_NAME);
				tableFields.put(DataImpAddService.SQL_TABLE_NAME, tempSqlFields.get(DataImpAddService.SQL_TABLE_NAME));
				if(!firstSqlReadFlag){
					dataImpAddPojo.setCheckSourceFields(null);
					dataImpAddPojo.setCheckTargetFields(null);
				}
			} else {
				sourceFieldsStr = tableFields.get(DataImpAddService.SQL_TABLE_NAME);
			}
		} else {
			dataImpAddPojo.setSqlFlag(false);
			dataImpAddPojo.setSqlText("");
			formSourceTable = formSourceTable.trim();
			String dataSourceTable = dataImpAddPojo.getSourceTable();
			if (null == dataSourceTable || "".equals(dataSourceTable.trim())
					|| !dataSourceTable.trim().equals(formSourceTable)) {// 如果数据源发生变化
				flushFlag = true;
			}
			if (flushFlag) {
				dataImpAddPojo.setSourceTable(formSourceTable);
				sourceFieldsStr = tableFields.get(formSourceTable);
				dataImpAddPojo.setCheckSourceFields(null);
				dataImpAddPojo.setCheckTargetFields(null);
			} else {
				sourceFieldsStr = tableFields.get(dataSourceTable);
			}
		}
		if (null == tableFields || tableFields.size() < 1) {
			errorStr = "无法取得指定数据库连接的表信息:数据库连接信息配置错误,或无访问权限,或数据库中无表信息";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		if (null == sourceFieldsStr || "".equals(sourceFieldsStr)) {
			errorStr = "无法取得指定表的字段信息:数据库连接信息配置错误,或无访问权限,或数据库中无表信息错误";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		String[] tempStrs = sourceFieldsStr.split(";");
		if (null != tempStrs && tempStrs.length > 0) {
			Map<String, String> sourceFields = new HashMap<String, String>();
			for (String tempStr : tempStrs) {
				String[] subTempStrs = tempStr.split(":");
				sourceFields.put(subTempStrs[0], subTempStrs[1]);
			}
			dataImpAddPojo.setSourceFields(sourceFields);
		}
		dataImpAddPojo.setTargetFields(dataImpAddService.getTargetTables(
				webRealPath, addForm.getTargetTable()));
		return SUCCESS;

	}

	/**
	 * 数据导入任务完成页面
	 * @return
	 */
	public String nextDImpDFinalPage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = request.getSession();
		String webRealPath = ServletActionContext.getServletContext().getRealPath("/");
		if (null == hs||hs.getAttribute(dataImpSessKey) == null) {
			errorStr = "登陆超时，请重新登陆后再进行操作";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		
		if (null == addForm) {
			errorStr = "表单信息为空，请填写正确的表单信息";
			request.setAttribute("errorStr", errorStr);
			return ERROR;
		}
		
		//整合表单信息 进行保存
		DataImpAddPojo dataImpAddPojo = (DataImpAddPojo) hs
				.getAttribute(dataImpSessKey);
		if(dataImpAddPojo!=null&&dataImpAddPojo.getTaskType()!=null&&dataImpAddPojo.getTaskType()==1){
			String checkSourceFields = addForm.getCheckSourceFields();
			String checkTargetFields = addForm.getCheckTargetFields();
			if (null != checkSourceFields && !"".equals(checkSourceFields)
					&& null != checkSourceFields
					&& !"".equals(checkTargetFields)) {
				boolean result = dataImpAddService.saveOrUpdateDImpTask(dataImpAddPojo, addForm,
						webRealPath,updateFlag);
				request.setAttribute("taskName", dataImpAddPojo.getTaskName());
				if (hs.getAttribute(dataImpSessKey) != null) {
					hs.removeAttribute(dataImpSessKey);
				}
				if(result){
					return SUCCESS;
				}else{
					errorStr = "操作失败，确认信息的正确性后再进行操作";
					request.setAttribute("errorStr", errorStr);
					return ERROR;
				}
				
			}else{
				errorStr = "操作错误，没有选择字段对应关系";
				request.setAttribute("errorStr", errorStr);
				return ERROR;
			}
		}else{
			dataImpAddService.saveOrUpdateDEmlTask(dataImpAddPojo.getTaskId(), addForm,updateFlag);
			request.setAttribute("taskName", dataImpAddPojo.getTaskName());
			if (hs.getAttribute(dataImpSessKey) != null) {
				hs.removeAttribute(dataImpSessKey);
			}
			return SUCCESS;
		}
	}

	/**
	 * 根据任务名称获得数据导入任务对象(用于数据库新增完成后回显)
	 * 
	 * @return
	 */
	public String showDataImpTaskForNew() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession hs = request.getSession();
		if (hs.getAttribute(dataImpSessKey) != null) {
			hs.removeAttribute(dataImpSessKey);
		}
		//returnShowDIT = dataImpAddService.getDITaskByName(taskName);
		return SUCCESS;
	}

	public DataImpAddForm getAddForm() {
		return addForm;
	}

	public void setAddForm(DataImpAddForm addForm) {
		this.addForm = addForm;
	}

	public void setDataImpAddService(DataImpAddService dataImpAddService) {
		this.dataImpAddService = dataImpAddService;
	}

	public void setdBService(DBService dBService) {
		this.dBService = dBService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getSqlTest() {
		return sqlTest;
	}

	public void setSqlTest(String sqlTest) {
		this.sqlTest = sqlTest;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public DataImportTask getReturnShowDIT() {
		return returnShowDIT;
	}

	public void setReturnShowDIT(DataImportTask returnShowDIT) {
		this.returnShowDIT = returnShowDIT;
	}

	public String getDbConnId() {
		return dbConnId;
	}

	public void setDbConnId(String dbConnId) {
		this.dbConnId = dbConnId;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getErrorStr() {
		return errorStr;
	}

	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}

	public String getRebackFlag() {
		return rebackFlag;
	}

	public void setRebackFlag(String rebackFlag) {
		this.rebackFlag = rebackFlag;
	}


	public String getFirstUpdate() {
		return firstUpdate;
	}


	public void setFirstUpdate(String firstUpdate) {
		this.firstUpdate = firstUpdate;
	}

	
}
