package com.integrity.lawCase.hostManage.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.util.DateFormat;
import com.integrity.lawCase.caseManage.bean.CaseObject;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.hostManage.bean.DomainsituationObject;
import com.integrity.lawCase.hostManage.bean.HostsObject;
import com.integrity.lawCase.hostManage.bean.LoopholesObject;
import com.integrity.lawCase.hostManage.service.HostManageService;
import com.integrity.lawCase.organizationManage.bean.Organizationobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.FilesToZip;
import com.integrity.lawCase.util.MapUtils;
import com.integrity.lawCase.util.PageSetValueUtil;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Liubf
 * 主机管理action
 *
 */
@SuppressWarnings("serial")
public class HostManageAction extends ActionSupport {
	private Logger logger = Logger.getLogger(HostManageAction.class);
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	private HostManageService hostManageService;
	PageModel<HostsObject> pageModel;
	FilesToZip filesToZip=new FilesToZip();
	private String ids;
	private List<HostsObject> hostList;
	private InputStream inputStream;
	private UserService userService;
	private int pageNo;
	private int pageSize;
	private HostsObject hosts;
	private DomainsituationObject domains;
	private LoopholesObject loopholes;
	private String rootType;
	private String rootId;
	
	//获取字典表信息
	private ServletContext sc = ServletActionContext.getServletContext();
	@SuppressWarnings("unchecked")
	Map<String,Map<String,String>> allMap = (Map<String, Map<String, String>>) sc.getAttribute(ConstantManage.DATADICTIONARY);
	Map<String,String> direction = allMap.get(ConstantManage.DIRECTION);//方向
	Map<String,String> level = allMap.get(ConstantManage.LEVEL);//等级
	Map<String,String> hostStatus = allMap.get(ConstantManage.HOSTSTATUS);//状态
	Map<String,String> hostType = allMap.get(ConstantManage.HOSTTYPE);//主机类别
	Map<String,String> controlState = allMap.get(ConstantManage.CONTROLSTATUS);//控制类型
	Map<String,String> type_property = (Map<String, String>) sc.getAttribute(ConstantManage.TYPE_PROPERTY);
	

	/**
	 * @return
	 * to添加主机信息 页面
	 */
	public String addHost(){
		String point = request.getParameter("point");
		request.setAttribute("point", point);
		List<AuthUser> userList = userService.findAllUser();
		request.setAttribute("userList", userList);
		PageSetValueUtil.selectMoreSet(request, userList, direction);
		
		//关联对象相关
		if(StringUtils.isNotBlank(rootId)){
			request.setAttribute("rootId", rootId);
			request.setAttribute("hostType", ConstantManage.HOSTOBJECTTYPE);
			request.setAttribute("rootType", rootType);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @param ho
	 * @param response
	 * @return
	 * 查询主机卡片信息
	 */
	public String findHostCards(){
		response.setContentType("text/html;charset=utf-8");//解决中文乱码
		String pn = request.getParameter("pageNo");
		String ps = request.getParameter("pageSize");
		if(pn != null && pn != ""){
			pageNo = Integer.valueOf(pn);
		}else{
			pageNo = 1;
		}
		if(ps != null && ps != ""){
			pageSize=Integer.valueOf(ps);
		}else{
			pageSize= 50;
		}
		PageModel<HostsObject> pageModel= new PageModel<HostsObject>(); 
		HostsObject hosts = new HostsObject();
		//备用传递参数
		List<HostsObject> cardsList = hostManageService.findHostCards((pageNo-1)*pageSize, pageSize,hosts);
		for(int i=0;i<cardsList.size();i++){
			cardsList.get(i).setImportantLevel(level.get(cardsList.get(i).getImportantLevel()));
			cardsList.get(i).setHostState(hostStatus.get(cardsList.get(i).getHostState()));
			cardsList.get(i).setHostType(hostType.get(cardsList.get(i).getHostType()));
			cardsList.get(i).setControlState(controlState.get(cardsList.get(i).getControlState()));
			
			String direct = cardsList.get(i).getDirections();
			StringBuffer temp = new StringBuffer();
			if (StringUtils.isNotBlank(direct)) {
				String[] arr = direct.split(",");
				for (String code : arr) {
					temp.append(direction.get(code)+",");
				}
				String directNames = temp.toString();
				cardsList.get(i).setDirections(directNames.substring(0,directNames.length()-1));
			}
		}
		pageModel.setList(cardsList);
		Integer count = hostManageService.TotalRecords(hosts);
		pageModel.setList(cardsList);
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		pageModel.setTotalPage(((count+pageSize-1)/pageSize));
		pageModel.setTotalRecords(count);
		
		request.setAttribute("pageModel", pageModel);
		
		return "success";
	}
	
	/**
	 * @return
	 * 主机列表数据
	 */
	public String searchHostsList(){
		response.setContentType("text/html;charset=utf-8");//解决中文乱码
		HostsObject hosts = new HostsObject();
		String hostName = request.getParameter("hostName");
		String hostIp = request.getParameter("hostIp");
		String importLevel = request.getParameter("importLevel");
		String hType = request.getParameter("hostType");
		String hStatus = request.getParameter("hostStatus");
		String conStatus = request.getParameter("conStatus");
		String directions = request.getParameter("directions");
		String person = request.getParameter("person");
		String provider = request.getParameter("provider");
		hosts.setHostName(hostName);
		hosts.setHostIp(hostIp);
		hosts.setHostState(hStatus);
		hosts.setHostType(hType);
		hosts.setControlState(conStatus);
		hosts.setImportantLevel(importLevel);
		hosts.setDirections(directions);
		hosts.setResponsiblePerson(person);
		hosts.setProvider(provider);
		String datas = request.getParameter("datas");
		if(StringUtils.isNotEmpty(datas)){
			try {
				datas = URLDecoder.decode(datas,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String[] s = datas.split(",");
			for(int i=1;i<s.length;i++){
				if(s[i].indexOf("direction") != -1){
					String[] dir = s[i].split("-");
					String dir1=MapUtils.getKeyByValue(dir[0], direction);
					hosts.setDirections(dir1.trim());
				}else if(s[i].indexOf("level") != -1){
					String[] lev = s[i].split("-");
					String lev1=MapUtils.getKeyByValue(lev[0], level);
					hosts.setImportantLevel(lev1.trim());
				}
			}
			
		}
		//获取查询条件
		pageModel = hostManageService.searchHostList(pageModel, hosts);
		
		List<HostsObject> newList = pageModel.getList();
		for(int i=0;i<newList.size();i++){
			newList.get(i).setImportantLevel(level.get(newList.get(i).getImportantLevel()));
			newList.get(i).setHostState(hostStatus.get(newList.get(i).getHostState()));
			newList.get(i).setHostType(hostType.get(newList.get(i).getHostType()));
			newList.get(i).setControlState(controlState.get(newList.get(i).getControlState()));
			String direct = newList.get(i).getDirections();
			StringBuffer temp = new StringBuffer();
			if (StringUtils.isNotBlank(direct)) {
				String[] arr = direct.split(",");
				for (String code : arr) {
					temp.append(direction.get(code)+",");
				}
				String directNames = temp.toString();
				newList.get(i).setDirections(directNames.substring(0,directNames.length()-1));
			}
		}
		pageModel.setList(newList);
		
		return SUCCESS;
	
	}
	/**
	 * @return
	 * 根据主机名 获取主机信息
	 */
	public String findHostByName(){
		try {
			String name = request.getParameter("hostName");
			String id = request.getParameter("hostId");
			if(StringUtils.isNotEmpty(name)){
				hostList =hostManageService.findHostByName(name,id);
			}
		} catch (Exception e) {
			logger.error("修改配置异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 查看主机详情（包括关联数据）
	 */
	public String searchHostDetails(){
		String relationDetails = request.getParameter("relationDetails");
		String id = request.getParameter("hid");
		if(StringUtils.isNotEmpty(id)){
			Long hid = Long.parseLong(id);
			hosts = hostManageService.searchDetails(hid);
			hosts.setImportantLevel(level.get(hosts.getImportantLevel()));
    		hosts.setHostState(hostStatus.get(hosts.getHostState()));
    		hosts.setHostType(hostType.get(hosts.getHostType()));
    		hosts.setControlState(controlState.get(hosts.getControlState()));
    		String direct = hosts.getDirections();
			StringBuffer temp = new StringBuffer();
			if (StringUtils.isNotBlank(direct)) {
				String[] arr = direct.split(",");
				for (String code : arr) {
					temp.append(direction.get(code)+",");
				}
				String directNames = temp.toString();
				hosts.setDirections(directNames.substring(0,directNames.length()-1));
			}
			request.setAttribute("hostDetails", hosts);
			
			List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
			request.setAttribute("rootType", ConstantManage.HOSTOBJECTTYPE);
			//连接配置参数获取
			RelationAction relationAction = PageSetValueUtil.relationActionSet(type_property,ConstantManage.HOSTOBJECTTYPE,id);
			request.setAttribute("relationAction", relationAction);
			
			//关联对象详细
			AllRelationWapper allRelation = hostManageService.findRelation(hid, ConstantManage.HOSTOBJECTTYPE);
			request.setAttribute("allRelation", allRelation);
			//根据id 获取主机域名情况和漏洞情况
			try {
				List<DomainsituationObject> domainList = hostManageService.findDmainList(hid);
				request.setAttribute("domainList", domainList);
				List<LoopholesObject> loopholesList = hostManageService.findLoopholesList(hid);
				request.setAttribute("loopholesList", loopholesList);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			
		}
		if(StringUtils.isNotEmpty(relationDetails)){
			return "RELATION";
		}else{
			return SUCCESS;
		}
	}
    /**
     * @return
     * 保存主机信息
     */
    public String saveHosts(){
    	response.setContentType("text/html;charset=UTF-8");//解决中文乱码
    	String id = request.getParameter("hid");
    	String hostIp = request.getParameter("hostIp");
    	String cs = request.getParameter("controlState");
    	String dc = request.getParameter("descriptionContents");
    	String hostName = request.getParameter("hostName");
    	String hostStatus = request.getParameter("hostState");
    	String hostType = request.getParameter("hostType");
    	String install = request.getParameter("installationService");
    	String location = request.getParameter("location");
    	String mac = request.getParameter("macAddress");
    	String operateSys = request.getParameter("operateSystem");
    	String provider = request.getParameter("provider");
    	String direction = request.getParameter("directions");
    	String leve = request.getParameter("importantLevel");
    	String person = request.getParameter("responsiblePerson");
    	String cTime =request.getParameter("cTime");
    	Date dt=new Date();
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
    	String nowTime="";
    	nowTime = df.format(dt);
    	if(StringUtils.isNotEmpty(id)){
    		HostsObject ho = new HostsObject();
        	ho.setHostIp(hostIp);//主机ip
        	ho.setCreateTime(DateFormat.getDateBy(cTime));//创建时间
        	ho.setControlState(cs);//控制状态
        	ho.setDescriptionContents(dc);//描述内容
        	ho.setHostName(hostName);//主机名称
        	ho.setHostState(hostStatus);//主机状态
        	ho.setHostType(hostType);//主机类型
        	ho.setInstallationService(install);//安装服务
        	ho.setLocation(location);//所在地
        	ho.setMacAddress(mac);//mac地址
        	ho.setOperateSystem(operateSys);//操作系统
        	ho.setProvider(provider);//提供商
        	ho.setDirections(direction);//所属方向
        	ho.setImportantLevel(leve);//重要等级
        	ho.setResponsiblePerson(person);
        	ho.setId(Long.parseLong(id));
    		if(ho != null){
    			hostManageService.updateHosts(ho);
    			try {
    				inputStream = new ByteArrayInputStream(String.valueOf(ho.getId()).getBytes("utf-8"));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    		
    	}else{
    		String RelationPoint = request.getParameter("relationPoint");
    		rootType = request.getParameter("rootType");
    		rootId = request.getParameter("rootId");
    		HostsObject ho = new HostsObject();
        	ho.setHostIp(hostIp);//主机ip
        	ho.setCreateTime(DateFormat.getDateBy(nowTime));//创建时间
        	ho.setControlState(cs);//控制状态
        	ho.setDescriptionContents(dc);//描述内容
        	if(StringUtils.isNotEmpty(RelationPoint)){
        		ho.setHostName(RelationPoint);//主机名称
        	}else{
        		ho.setHostName(hostName);//主机名称
        	}
        	ho.setHostState(hostStatus);//主机状态
        	ho.setHostType(hostType);//主机类型
        	ho.setInstallationService(install);//安装服务
        	ho.setLocation(location);//所在地
        	ho.setMacAddress(mac);//mac地址
        	ho.setOperateSystem(operateSys);//操作系统
        	ho.setProvider(provider);//提供商
        	ho.setDirections(direction);//所属方向
        	ho.setImportantLevel(leve);//重要等级
        	ho.setResponsiblePerson(person);
        	if(ho!= null){
        	//添加主机 同时 关联
        	if(StringUtils.isNotEmpty(RelationPoint)){
        		hostManageService.saveHostRelation(ho, rootType, rootId);
        	}else{
        		hostManageService.saveHosts(ho);
        	}
        	try {
        		inputStream = new ByteArrayInputStream(String.valueOf(ho.getId()).getBytes("utf-8"));
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        	}
    		
    	}
    	
    	
		return SUCCESS;
	}
    public  String forDomain(){
    	response.setContentType("text/html;charset=UTF-8");//解决中文乱码
    	String hostId = request.getParameter("hostId");
    	String hostName = request.getParameter("hostName");
    	try {
    		hostName = URLDecoder.decode(hostName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	request.setAttribute("hostId", hostId);
    	request.setAttribute("hostName",hostName );
    	return SUCCESS;
    }
    /**
     *保存域名情况
     */
    public String saveDomain(){
    	response.setContentType("text/html;charset=UTF-8");//解决中文乱码
    	Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
		String nowTime="";
		nowTime = df.format(dt);
    	String id = request.getParameter("dId");
    	String objName = request.getParameter("objectName");
    	String dataBaseType = request.getParameter("databaseType");
    	String language = request.getParameter("developeLanguage");
    	String domains = request.getParameter("domain");
    	String domainRe = request.getParameter("domainResolution");
    	String domainReSe = request.getParameter("domainResolutionServices");
    	String domainPro = request.getParameter("domainServiceProvider");
    	String expiredTime = request.getParameter("expiredTime");
    	String hostId = request.getParameter("hostId");
    	String regTime = request.getParameter("regTime");
    	String serviceType = request.getParameter("serviceType");
    	String remarks = request.getParameter("remarkes");
    	if(StringUtils.isNotEmpty(id)){
    		DomainsituationObject domain = new DomainsituationObject();
    		domain.setObjectName(objName);//对象名称
    		domain.setDatabaseType(dataBaseType);
    		domain.setDevelopeLanguage(language);//编程语言
    		domain.setDomain(domains);//域名
    		domain.setDomainResolution(domainRe);//域名解析机构
    		domain.setDomainResolutionServices(domainReSe);//域名解析机构
    		domain.setDomainServiceProvider(domainPro);
    		domain.setExpiredTime(expiredTime);//过期时间
    		domain.setHostId(Long.parseLong(hostId));//主机id
    		domain.setRegTime(regTime);//注册时间
    		domain.setServiceType(serviceType);//服务类型
    		domain.setRemarkes(remarks);//备注
    		domain.setCreateTime(DateFormat.getDateBy(nowTime));//创建时间
    		domain.setId(Long.parseLong(id));
    		if(domain != null){
    			hostManageService.updateDoamins(domain);
    			try {
    				inputStream = new ByteArrayInputStream(String.valueOf(domain.getHostId()).getBytes("utf-8"));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    	
    		
    	}else{
    		DomainsituationObject domain = new DomainsituationObject();
    		domain.setObjectName(objName);//对象名称
    		domain.setDatabaseType(dataBaseType);
    		domain.setDevelopeLanguage(language);//编程语言
    		domain.setDomain(domains);//域名
    		domain.setDomainResolution(domainRe);//域名解析机构
    		domain.setDomainResolutionServices(domainReSe);//域名解析机构
    		domain.setDomainServiceProvider(domainPro);
    		domain.setExpiredTime(expiredTime);//过期时间
    		domain.setHostId(Long.parseLong(hostId));//主机id
    		domain.setRegTime(regTime);//注册时间
    		domain.setServiceType(serviceType);//服务类型
    		domain.setRemarkes(remarks);//备注
    		domain.setCreateTime(DateFormat.getDateBy(nowTime));//创建时间
    		if(domain != null){
    			hostManageService.saveDomain(domain);
    			try {
    				inputStream = new ByteArrayInputStream(String.valueOf(domain.getHostId()).getBytes("utf-8"));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		
    	return SUCCESS;
    }
    public String  forLoopholes(){
    	response.setContentType("text/html;charset=UTF-8");//解决中文乱码
    	String hostId = request.getParameter("hostId");
    	String hostName = request.getParameter("hostName");
    	try {
    		hostName = URLDecoder.decode(hostName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	request.setAttribute("hostId", hostId);
    	request.setAttribute("hostName",hostName );
    	return SUCCESS;
    }
    /**
     * @return
     * 添加漏洞情况
     */
    public String saveLoopholes(){
    	response.setContentType("text/html;charset=UTF-8");//解决中文乱码
    	String id = request.getParameter("lId");
    	if(StringUtils.isNotEmpty(id)){
    		LoopholesObject Loopholes = new LoopholesObject();
    		Loopholes.setAuthorityType(request.getParameter("authorityType"));//权限类型
    		Loopholes.setHiddenDoor(request.getParameter("hiddenDoor"));//后门
    		Loopholes.setHostId(Long.parseLong(request.getParameter("hostId")));
    		Loopholes.setMethods(request.getParameter("methods"));//利用方法
    		Loopholes.setObjectName(request.getParameter("objectName"));//对象名称
    		Loopholes.setRemarks(request.getParameter("remarks"));//备注
    		Loopholes.setUseTools(request.getParameter("useTools"));//利用工具
    		Loopholes.setVulnerabilityDescription(request.getParameter("vulnerabilityDescription"));//漏洞描述
    		Date dt=new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
    		String nowTime="";
    		nowTime = df.format(dt);
    		Loopholes.setCreateTime(DateFormat.getDateBy(nowTime));
    		Loopholes.setId(Long.parseLong(id));
    		if(Loopholes != null){
    			hostManageService.updateLoopholes(Loopholes);
    			try {
    				inputStream = new ByteArrayInputStream(String.valueOf(Loopholes.getHostId()).getBytes("utf-8"));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    	}else{
    		LoopholesObject Loopholes = new LoopholesObject();
    		Loopholes.setAuthorityType(request.getParameter("authorityType"));//权限类型
    		Loopholes.setHiddenDoor(request.getParameter("hiddenDoor"));//后门
    		Loopholes.setHostId(Long.parseLong(request.getParameter("hostId")));
    		Loopholes.setMethods(request.getParameter("methods"));//利用方法
    		Loopholes.setObjectName(request.getParameter("objectName"));//对象名称
    		Loopholes.setRemarks(request.getParameter("remarks"));//备注
    		Loopholes.setUseTools(request.getParameter("useTools"));//利用工具
    		Loopholes.setVulnerabilityDescription(request.getParameter("vulnerabilityDescription"));//漏洞描述
    		Date dt=new Date();
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
    		String nowTime="";
    		nowTime = df.format(dt);
    		Loopholes.setCreateTime(DateFormat.getDateBy(nowTime));
    		if(Loopholes != null){
    			hostManageService.saveLoophole(Loopholes);
    			try {
    				inputStream = new ByteArrayInputStream(String.valueOf(Loopholes.getHostId()).getBytes("utf-8"));
    			} catch (UnsupportedEncodingException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	return SUCCESS;
    }
    /**
     * @param ho
     * @param response
     * @return
     *修改主机信息
     */
    public String updateHostsByid(){
    	String id = request.getParameter("hid");
    	String point = request.getParameter("point");
    	if(StringUtils.isNotEmpty(id)){
    		hosts = hostManageService.forUpdateHosts(Long.parseLong(id));
    		hosts.setImportantLevel(level.get(hosts.getImportantLevel()));
    		hosts.setHostState(hostStatus.get(hosts.getHostState()));
    		hosts.setHostType(hostType.get(hosts.getHostType()));
    		hosts.setControlState(controlState .get(hosts.getControlState()));
    		request.setAttribute("hosts", hosts);
    		
    		List<AuthUser> userList = userService.findAllUser();
			request.setAttribute("userList", userList);
			
			PageSetValueUtil.selectMoreSet(request, userList, direction);
    	}
    	request.setAttribute("point", point);
		return SUCCESS;
	}
    /**
     * @return
     * 修改域名情况
     */
    public String forUpdateDomain(){
    	String id = request.getParameter("did");
    	String hostName = request.getParameter("hostName");
    	try {
    		hostName = URLDecoder.decode(hostName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	if(StringUtils.isNotEmpty(id)){
    		domains = hostManageService.forUpdateDomain(Long.parseLong(id));
    		request.setAttribute("domains", domains);
    		request.setAttribute("hostId", domains.getHostId());
        	request.setAttribute("hostName",hostName );
    	}
    	return SUCCESS;
    }
    /**
     * @return
     * 修改漏洞情况
     */
    public String forUpdateLoopholes(){
    	String id = request.getParameter("lid");
    	String hostName = request.getParameter("hostName");
    	try {
    		hostName = URLDecoder.decode(hostName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	if(StringUtils.isNotEmpty(id)){
    		loopholes = hostManageService.forUpdateLoopholes(Long.parseLong(id));
    		request.setAttribute("loopholes", loopholes);
    		request.setAttribute("hostId", loopholes.getHostId());
    		request.setAttribute("hostName",hostName );
    	}
    	return SUCCESS;
    }
    
    /**
     * @param ho
     * @param response
     * @return
     * 删除主机信息以及 主机对象信息（包括域名和漏洞）
     */
    public void delHostsByid(){
    	String gmpId = request.getParameter("ids");
		String[] ids = gmpId.split(",");
		List<String>  Ids= Arrays.asList(ids);
		rootType = request.getParameter("rootType");
		
		hostManageService.delDomainByhostId(Ids);
		hostManageService.delLoopholesByhostId(Ids);
		//批量删除
		hostManageService.delHosts(Ids);
		//删除单个对象
		try {
			hostManageService.delAllRelactions(gmpId, rootType);
		} catch (Exception e) {
			logger.error("删除关联数据异常！"+e.getMessage());
		}
    	
	}
    
    /**
     * @return
     * 删除域名情况
     */
    public String delDomain(){
    	String ids = request.getParameter("did");
    	String hostId = request.getParameter("hostId");
    	if(StringUtils.isNotEmpty(ids)){
    		hostManageService.delDomain(Long.parseLong(ids));
    		try {
				inputStream = new ByteArrayInputStream(hostId.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
		return SUCCESS;
    }
    /**
     * @return
     * 删除漏洞情况
     */
    public String delLoopholes(){
    	String ids = request.getParameter("lid");
    	String hostId = request.getParameter("hostId");
    	if(StringUtils.isNotEmpty(ids)){
    		hostManageService.delLoopholes(Long.parseLong(ids));
    		try {
				inputStream = new ByteArrayInputStream(hostId.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
		return SUCCESS;
    }
    
    /**
     * @return
     * 删除单一关联关系
     */
    public String delSingleRelation(){
    	rootId = request.getParameter("hid");
    	rootType = request.getParameter("rootType");
    	String relId = request.getParameter("relId");
    	String relType = request.getParameter("relType");
    	if(StringUtils.isNotEmpty(rootId)){
    		hostManageService.delSingleRelation(rootId, rootType, relId, relType);
    		try {
				inputStream = new ByteArrayInputStream(rootId.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
    	return SUCCESS;
    }

    @JSON(serialize=false)
	public HostManageService getHostManageService() {
		return hostManageService;
	}

	public void setHostManageService(HostManageService hostManageService) {
		this.hostManageService = hostManageService;
	}
	@JSON(serialize=false)
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	@JSON(serialize=false)
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageModel<HostsObject> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<HostsObject> pageModel) {
		this.pageModel = pageModel;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void setHosts(HostsObject hosts) {
		this.hosts = hosts;
	}
	public void setDomains(DomainsituationObject domains) {
		this.domains = domains;
	}
	public void setLoopholes(LoopholesObject loopholes) {
		this.loopholes = loopholes;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getRootType() {
		return rootType;
	}
	public void setRootType(String rootType) {
		this.rootType = rootType;
	}
	public List<HostsObject> getHostList() {
		return hostList;
	}

	public void setHostList(List<HostsObject> hostList) {
		this.hostList = hostList;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	

}
