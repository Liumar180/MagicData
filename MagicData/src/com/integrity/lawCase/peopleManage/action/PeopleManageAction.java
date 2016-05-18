package com.integrity.lawCase.peopleManage.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.util.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.peopleManage.bean.Documentnumberobject;
import com.integrity.lawCase.peopleManage.bean.Peopleobject;
import com.integrity.lawCase.peopleManage.bean.Peoplevirtualobject;
import com.integrity.lawCase.peopleManage.bean.Phonenumberobject;
import com.integrity.lawCase.peopleManage.service.PeopleManageService;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.PageSetValueUtil;
import com.integrity.lawCase.util.TransformYears;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class PeopleManageAction extends ActionSupport {
	private ObjectMapper mapper = JacksonMapperUtil.getObjectMapper();
	private Logger logger = Logger.getLogger(this.getClass());
	private PeopleManageService peopleManageService;
	private InputStream inputStream;
	public PageModel<Peopleobject> pageModel;
	public PageModel<Peopleobject> pageModelpeople;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private String from;
	// public PageModel<Peopleobject> pageModelpeople;
	private HttpServletRequest requestP = ServletActionContext.getRequest();
	private UserService userService;
	private String rootType;
	private String rootId;
	private List<WorkAllocation> allocationList;
	private String inputtype;
	String path = requestP.getContextPath();
	String basePath = requestP.getScheme() + ":"+File.separator+File.separator  + requestP.getServerName()+ ":" + requestP.getServerPort() + path +File.separator;
	private String relId;
	private String relType;
	private File fileField;
	private String fileFieldFileName;

	@JSON(serialize = false)
	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	@JSON(serialize = false)
	public String getRelType() {
		return relType;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	@JSON(serialize = false)
	public PeopleManageService getPeopleManageService() {
		return peopleManageService;
	}

	public void setPeopleManageService(PeopleManageService peopleManageService) {
		this.peopleManageService = peopleManageService;
	}

	@JSON(serialize = false)
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PageModel<Peopleobject> getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel<Peopleobject> pageModel) {
		this.pageModel = pageModel;
	}

	// People
	private int year;
	private int pageNo;
	private int pageSize;
	private long id;
	private String ids;
	private String pocnname;
	private String ponamespell;
	private String poenname;
	private String pocontrolstatus;
	private String poalias;
	private String posex;
	private Date pobirthday;
	private Date pocreateday;
	private String pocountry;
	private String ponational;
	private String pohukou;
	private String polocation;
	private String podutyman;
	private String popersonstatus;
	private String poimportantlevel;
	private String podirectionof;
	private String poimage;
	private String podescription;
	private String poremark;

	// 虚拟身份
	private long poid;
	private String pvname;
	private String pvaccounttype;
	private String pvusername;
	private String pvcontrolstatus;
	private String pvcontrolmeasures;
	private String pvpassword;
	private String pvloginaddress;
	private String pvremark;

	// 证件
	private String dnname;
	private String dntype;
	private String dnnumber;
	private String dnremark;

	// 号码类型
	private String pnname;
	private String pntype;
	private String pnnumber;
	private String pnremark;

	// 获取字典表信息
	private ServletContext sc = ServletActionContext.getServletContext();
	Map<String, Map<String, String>> allMap = (Map<String, Map<String, String>>) sc
			.getAttribute(ConstantManage.DATADICTIONARY);
	Map<String, String> PNATION = allMap.get(ConstantManage.NATION);// 民族
	Map<String, String> PDIRECTION = allMap.get(ConstantManage.DIRECTION);// 方向
	Map<String, String> Plevel = allMap.get(ConstantManage.LEVEL);// 等级
	Map<String, String> PPERSONSTATUS = allMap.get(ConstantManage.PERSONSTATUS);// 状态
	Map<String, String> PCONTROLSTATUS = allMap
			.get(ConstantManage.CONTROLSTATUS);// 控制类型
	Map<String, String> PCOUNTRY = allMap.get(ConstantManage.COUNTRY);// 国籍
	Map<String, String> ACCOUNTTPYE = allMap.get(ConstantManage.ACCOUNTTPYE);// 控制类型
	Map<String, String> CONTROLMEASURES = allMap
			.get(ConstantManage.CONTROLMEASURES);// 控制策略
	Map<String, String> DCTYPE = allMap.get(ConstantManage.DCTYPE);// 证件类型
	Map<String, String> PHONETYPE = allMap.get(ConstantManage.PHONETYPE);// 电话类型
	Map<String, String> type_property = (Map<String, String>) sc
			.getAttribute(ConstantManage.TYPE_PROPERTY);

	/**
	 * 点击人员管理
	 * 
	 * @return
	 */
	public String peopleMangeIndex() {
		pageSize = 50;
		peopleMangeAllList();
		return SUCCESS;
	}

	/**
	 * 查询所有People信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String peopleMangeAllList() {
		List<Integer> years = TransformYears.getYearsBySeveral();
		requestP.getSession().setAttribute("years", years);
		pageModelpeople = new PageModel();
		pageModelpeople.setPageNo(1);
		pageModelpeople.setPageSize(pageSize);
		pageModelpeople = peopleManageService.findPeoplePageModel(
				pageModelpeople, new Peopleobject());
		List pelist = new ArrayList();
		StringBuffer sb = new StringBuffer();
		for (Peopleobject peopleobject : pageModelpeople.getList()) {
			peopleobject
					.setPocountry(PCOUNTRY.get(peopleobject.getPocountry()));
			if (peopleobject.getPocountry() == null) {
				peopleobject.setPocountry("");
			}
			peopleobject
					.setPonational(PNATION.get(peopleobject.getPonational()));
			if (peopleobject.getPonational() == null) {
				peopleobject.setPonational("");
			}
			peopleobject.setPopersonstatus(PPERSONSTATUS.get(peopleobject
					.getPopersonstatus()));
			if (peopleobject.getPopersonstatus() == null) {
				peopleobject.setPopersonstatus("");
			}
			peopleobject.setPoimportantlevel(Plevel.get(peopleobject
					.getPoimportantlevel()));
			if (peopleobject.getPoimportantlevel() == null) {
				peopleobject.setPoimportantlevel("");
			}
			if (peopleobject.getPodirectionof() != null
					&& !"".equals(peopleobject.getPodirectionof())) {
				for (String object : peopleobject.getPodirectionof().split(",")) {
					sb.append(PDIRECTION.get(object) + ",");
				}
				peopleobject.setPodirectionof(sb.toString().substring(0,
						sb.toString().length() - 1));
			}
			peopleobject.setPocontrolstatus(PCONTROLSTATUS.get(peopleobject
					.getPocontrolstatus()));
			pelist.add(peopleobject);
			sb = new StringBuffer();
		}
		pageModelpeople.setList(pelist);
		requestP.getSession().setAttribute("pageModel", pageModelpeople);
		String json = null;
		try {

			json = mapper.writeValueAsString(pageModelpeople.getList());
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 上传图片
	 * 
	 * @param poimage
	 * @param id
	 * @param realpath
	 * @param fileName
	 * @throws Exception
	 */
	public void uploadImg(String poimage, long id, String realpath,
			String fileName) throws Exception {

		if (poimage != null && !"".equals(poimage)) {
			File file = new File(realpath + fileName);
			if (id > 0) {
				if (file.isFile()) {
					file.delete();
				}
			}
			File target = new File(realpath, fileName);
			if (!target.exists()) {
				target.createNewFile();
				IOUtils.copy(fileField, target);
			}
		}
	}

	/**
	 * 保存于修改People信息
	 * 
	 * @return
	 */
	public String peopleMangeSave() {
		Peopleobject peopleobject = new Peopleobject();
		peopleobject.setPobirthday(pobirthday);
		peopleobject.setPocontrolstatus(pocontrolstatus);
		peopleobject.setPocountry(pocountry);
		peopleobject.setPoalias(poalias);
		peopleobject.setPocnname(pocnname);
		peopleobject.setPodescription(podescription);
		peopleobject.setPodirectionof(podirectionof);
		peopleobject.setPodutyman(podutyman);
		peopleobject.setPoenname(poenname);
		peopleobject.setPohukou(pohukou);

		peopleobject.setPoimportantlevel(poimportantlevel);
		peopleobject.setPolocation(polocation);
		peopleobject.setPonamespell(ponamespell);
		peopleobject.setPonational(ponational);
		peopleobject.setPopersonstatus(popersonstatus);
		peopleobject.setPoremark(poremark);
		peopleobject.setPosex(posex);

		if (poimage != null && !"".equals(poimage)) {
			if (id > 0) {
				String img = peopleManageService.getPeopleinfoByid(id)
						.getPoimage();
				if (img != null) {
					img = ServletActionContext.getServletContext().getRealPath(File.separator)+ "images"+ File.separator+ "uploadImg"
							+ File.separator+ "PeopleObjectImg"+ File.separator+ img.substring(img.lastIndexOf(File.separator) + 1);
					File f = new File(img); // 输入要删除的文件位置
					if (f.exists())
						f.delete();
				}
			}
			String fileName = fileFieldFileName.substring(0,fileFieldFileName.lastIndexOf("."))
					+ new Date().getTime()+ fileFieldFileName.substring(fileFieldFileName.lastIndexOf("."));
			String realpath = ServletActionContext.getServletContext().getRealPath(
							File.separator + "images" + File.separator+ "uploadImg" + File.separator+ "PeopleObjectImg" + File.separator);
			try {
				uploadImg(poimage, id, realpath, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			peopleobject.setPoimage(basePath + "images" + File.separator+ "uploadImg" + File.separator + "PeopleObjectImg"
					+ File.separator + fileName);
		} else {
			peopleobject.setPoimage(morenImg());
		}
		// 修改
		if (id > 0) {
			peopleobject.setId(id);
			peopleobject.setPocreateday(peopleManageService.getPeopleinfoByid(id).getPocreateday());
			peopleManageService.edit(peopleobject);
			requestP.getSession().setAttribute("personinfo", peopleobject);
			try {
				inputStream = new ByteArrayInputStream(String.valueOf(
						peopleobject.getId()).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return SUCCESS;
		} else {
			peopleobject.setPocreateday(new Date());
		}
		Long idR = peopleManageService.saveR(peopleobject);
		if (!"undefined".equals(rootId)) {
			peopleManageService.savePeopleRelation(rootId, rootType, idR);
			requestP.getSession().setAttribute("fromtype", "detail");
		} else {
			requestP.getSession().setAttribute("fromtype", "save");
		}
		requestP.getSession().setAttribute("personinfo", peopleobject);
		try {
			inputStream = new ByteArrayInputStream(String.valueOf(
					peopleobject.getId()).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存虚拟信息
	 * 
	 * @return
	 */
	public String viewPeoplePVSave() {
		Peoplevirtualobject pv = new Peoplevirtualobject();
		pv.setPoid(poid);
		pv.setPvaccounttype(pvaccounttype);
		pv.setPvcontrolmeasures(pvcontrolmeasures);
		pv.setPvcontrolstatus(pvcontrolstatus);
		pv.setPvloginaddress(pvloginaddress);
		pv.setPvname(pvname);
		pv.setPvpassword(pvpassword);
		pv.setPvremark(pvremark);
		pv.setPvusername(pvusername);
		// 修改
		if (id > 0) {
			pv.setPvid(id);
			peopleManageService.editPV(pv);
			requestP.getSession().setAttribute("peoplevirtual", pv);
			try {
				inputStream = new ByteArrayInputStream(String.valueOf(
						pv.getPoid()).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return SUCCESS;
		}
		peopleManageService.savePV(pv);
		requestP.getSession().setAttribute("peoplevirtual", pv);
		try {
			inputStream = new ByteArrayInputStream(String.valueOf(pv.getPoid())
					.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存证件信息
	 * 
	 * @return
	 */
	public String viewPeopleDNSave() {
		Documentnumberobject dn = new Documentnumberobject();
		dn.setDnname(dnname);
		dn.setDnnumber(dnnumber);
		dn.setDnremark(dnremark);
		dn.setDntype(dntype);
		dn.setPoid(poid);
		// 修改
		if (id > 0) {
			dn.setDnid(id);
			peopleManageService.editDN(dn);
			requestP.getSession().setAttribute("documentnumber", dn);
			try {
				inputStream = new ByteArrayInputStream(String.valueOf(
						dn.getPoid()).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return SUCCESS;
		}
		peopleManageService.saveDN(dn);
		requestP.getSession().setAttribute("documentnumber", dn);
		try {
			inputStream = new ByteArrayInputStream(String.valueOf(dn.getPoid())
					.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 保存电话
	 * 
	 * @return
	 */
	public String viewPeoplePNSave() {
		Phonenumberobject dn = new Phonenumberobject();
		dn.setPnname(pnname);
		dn.setPnnumber(pnnumber);
		dn.setPnremark(pnremark);
		dn.setPntype(pntype);
		dn.setPoid(poid);
		// 修改
		if (id > 0) {
			dn.setPnid(id);
			peopleManageService.editPN(dn);
			requestP.getSession().setAttribute("phonenumber", dn);
			try {
				inputStream = new ByteArrayInputStream(String.valueOf(
						dn.getPoid()).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return SUCCESS;
		}
		peopleManageService.savePN(dn);
		requestP.getSession().setAttribute("phonenumber", dn);
		try {
			inputStream = new ByteArrayInputStream(String.valueOf(dn.getPoid())
					.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 人员列表
	 * 
	 * @return
	 */
	public String peopleMangeList() {
		try {
			Peopleobject pbject = new Peopleobject();
			if (year != 0) {
				pbject.setPocreateday(sdf.parse(year + 1 + "0000"));
			}
			if (podirectionof != null && !"null".equals(podirectionof)) {
				pbject.setPodirectionof(podirectionof);
			}
			if (poimportantlevel != null && !"null".equals(poimportantlevel)) {
				pbject.setPoimportantlevel(poimportantlevel);
			}
			pageModel = peopleManageService.findPeoplePageModel(pageModel,
					pbject);
			List pelist = new ArrayList();
			StringBuffer sb = new StringBuffer();
			for (Peopleobject peopleobject : pageModel.getList()) {
				peopleobject.setPocountry(PCOUNTRY.get(peopleobject
						.getPocountry()));
				if (peopleobject.getPocountry() == null) {
					peopleobject.setPocountry("");
				}
				peopleobject.setPonational(PNATION.get(peopleobject
						.getPonational()));
				if (peopleobject.getPonational() == null) {
					peopleobject.setPonational("");
				}
				peopleobject.setPopersonstatus(PPERSONSTATUS.get(peopleobject
						.getPopersonstatus()));
				if (peopleobject.getPopersonstatus() == null) {
					peopleobject.setPopersonstatus("");
				}
				peopleobject.setPoimportantlevel(Plevel.get(peopleobject
						.getPoimportantlevel()));
				if (peopleobject.getPoimportantlevel() == null) {
					peopleobject.setPoimportantlevel("");
				}
				if (peopleobject.getPodirectionof() != null
						&& !"".equals(peopleobject.getPodirectionof())) {
					for (String object : peopleobject.getPodirectionof().split(
							",")) {
						sb.append(PDIRECTION.get(object) + ",");
					}
					peopleobject.setPodirectionof(sb.toString().substring(0,
							sb.toString().length() - 1));
				}
				peopleobject.setPocontrolstatus(PCONTROLSTATUS.get(peopleobject
						.getPocontrolstatus()));
				pelist.add(peopleobject);
				sb = new StringBuffer();
			}
			pageModel.setList(pelist);
		} catch (Exception e) {
			logger.error("查询人员列表异常", e);
		}
		return SUCCESS;
	}

	/**
	 * 人员详细信息
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String peopleMangeDetail() {
		Peopleobject poPeopleobject = peopleManageService.getPeopleinfoByid(id);
		poPeopleobject
				.setPocountry(PCOUNTRY.get(poPeopleobject.getPocountry()));
		if (poPeopleobject.getPocountry() == null) {
			poPeopleobject.setPocountry("");
		}
		poPeopleobject
				.setPonational(PNATION.get(poPeopleobject.getPonational()));
		if (poPeopleobject.getPonational() == null) {
			poPeopleobject.setPonational("");
		}
		poPeopleobject.setPopersonstatus(PPERSONSTATUS.get(poPeopleobject
				.getPopersonstatus()));
		if (poPeopleobject.getPopersonstatus() == null) {
			poPeopleobject.setPopersonstatus("");
		}
		poPeopleobject.setPoimportantlevel(Plevel.get(poPeopleobject
				.getPoimportantlevel()));
		if (poPeopleobject.getPoimportantlevel() == null) {
			poPeopleobject.setPoimportantlevel("");
		}
		StringBuffer sb = new StringBuffer();
		if (poPeopleobject.getPodirectionof() != null
				&& !"".equals(poPeopleobject.getPodirectionof())) {
			for (String object : poPeopleobject.getPodirectionof().split(",")) {
				sb.append(PDIRECTION.get(object) + ",");
			}
			poPeopleobject.setPodirectionof(sb.toString().substring(0,
					sb.toString().length() - 1));
		}
		poPeopleobject.setPocontrolstatus(PCONTROLSTATUS.get(poPeopleobject
				.getPocontrolstatus()));
		requestP.getSession().setAttribute("personinfo", poPeopleobject);
		requestP.getSession().setAttribute("fromtype", from);
		// 虚拟账号
		List<Peoplevirtualobject> pvList = peopleManageService
				.getPVinfoByPoid(id);
		for (Peoplevirtualobject peoplevirtualobject : pvList) {
			peoplevirtualobject.setPvaccounttype(ACCOUNTTPYE
					.get(peoplevirtualobject.getPvaccounttype()));
			peoplevirtualobject.setPvcontrolmeasures(CONTROLMEASURES
					.get(peoplevirtualobject.getPvcontrolmeasures()));
			peoplevirtualobject.setPvcontrolstatus(PCONTROLSTATUS
					.get(peoplevirtualobject.getPvcontrolstatus()));
		}
		requestP.getSession().setAttribute("peoplevirtualList", pvList);
		// 证件
		List<Documentnumberobject> dnList = peopleManageService
				.getDNinfoByPoid(id);
		for (Documentnumberobject documentnumberobject : dnList) {
			documentnumberobject.setDntype(DCTYPE.get(documentnumberobject
					.getDntype()));
		}
		requestP.getSession().setAttribute("documentnumberList", dnList);
		// 号码
		List<Phonenumberobject> pnList = peopleManageService
				.getPNinfoByPoid(id);
		for (Phonenumberobject phonenumberobject : pnList) {
			phonenumberobject.setPntype(PHONETYPE.get(phonenumberobject
					.getPntype()));
		}
		requestP.getSession().setAttribute("phonenumberList", pnList);
		List<AuthUser> userList = userService.findAllUser();
		requestP.setAttribute("userList", userList);
		selectMoreSet(userList);
		// 跳转链接
		RelationAction relationAction = PageSetValueUtil.relationActionSet(
				type_property, ConstantManage.PEOPLEOBJECTTYPE, id + "");
		requestP.setAttribute("relationAction", relationAction);
		// 关联对象详细
		AllRelationWapper allRelation = peopleManageService.findRelation(id,
				ConstantManage.PEOPLEOBJECTTYPE);
		requestP.setAttribute("allRelation", allRelation);
		/* 工作配置 */
		allocationList = peopleManageService.findWorkAllocationByCaseId(id);
		requestP.setAttribute("allocationList", allocationList);
		requestP.setAttribute("rootType", ConstantManage.PEOPLEOBJECTTYPE);
		return SUCCESS;
	}

	/**
	 * 添加人员关联
	 * 
	 * @return
	 */
	public String viewPeopleAdd() {
		Peopleobject peopleobject = new Peopleobject();
		peopleobject.setId(id);
		requestP.getSession().setAttribute("personinfo", new Peopleobject());
		requestP.getSession().setAttribute("personinfoR", peopleobject);
		List<AuthUser> userList = userService.findAllUser();
		requestP.setAttribute("userList", userList);
		selectMoreSet(userList);
		// 关联对象相关
		if (StringUtils.isNotBlank(rootId)) {
			requestP.setAttribute("rootId", rootId);
			requestP.setAttribute("peopleType", ConstantManage.PEOPLEOBJECTTYPE);
			requestP.setAttribute("rootType", rootType);
		}
		return SUCCESS;
	}

	/**
	 * 查询虚拟信息
	 * 
	 * @return
	 */
	public String viewPeoplePV() {
		if (id > 0) {
			requestP.getSession().setAttribute("peoplevirtual",
					peopleManageService.getPVinfoByid(id));
			return SUCCESS;
		} else {
			Peoplevirtualobject peoplevirtualobject = new Peoplevirtualobject();
			peoplevirtualobject.setPoid(poid);
			requestP.getSession().setAttribute("peoplevirtual",
					peoplevirtualobject);
			return SUCCESS;
		}

	}

	/**
	 * 查询证件信息
	 * 
	 * @return
	 */
	public String viewPeopleDN() {
		if (id > 0) {
			requestP.getSession().setAttribute("documentnumber",
					peopleManageService.getDNinfoByid(id));
			return SUCCESS;
		} else {
			Documentnumberobject documentnumberobject = new Documentnumberobject();
			documentnumberobject.setPoid(poid);
			requestP.getSession().setAttribute("documentnumber",
					documentnumberobject);
			return SUCCESS;
		}

	}

	/**
	 * 查询电话信息
	 * 
	 * @return
	 */
	public String viewPeoplePN() {
		if (id > 0) {
			requestP.getSession().setAttribute("phonenumber",
					peopleManageService.getPNinfoByid(id));
			return SUCCESS;
		} else {
			Phonenumberobject phonenumberobject = new Phonenumberobject();
			phonenumberobject.setPoid(poid);
			requestP.getSession()
					.setAttribute("phonenumber", phonenumberobject);
			return SUCCESS;
		}

	}

	/**
	 * 删除人员
	 * 
	 * @return
	 */
	public void viewPeopleDel() {
		for (String i : ids.split(",")) {
			String img = peopleManageService.getPeopleinfoByid(Long.valueOf(i))
					.getPoimage();
			if (img != null) {
				img = ServletActionContext.getServletContext().getRealPath(
						File.separator)
						+ "images"
						+ File.separator
						+ "uploadImg"
						+ File.separator
						+ "PeopleObjectImg"
						+ File.separator
						+ img.substring(img.lastIndexOf(File.separator) + 1);
				File f = new File(img); // 输入要删除的文件位置
				if (f.exists())
					f.delete();
			}
			peopleManageService.delete(Long.valueOf(i));
		}
		peopleManageService.delAllRelactions(ids, rootType);
	}

	/**
	 * 人员管理删除
	 * 
	 * @return
	 */
	public String delRelationship() {
		if (StringUtils.isNotEmpty(String.valueOf(id))) {
			peopleManageService.delSingleRelation(String.valueOf(id), rootType,
					relId, relType);
			try {
				inputStream = new ByteArrayInputStream(String.valueOf(id)
						.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	/**
	 * 删除虚拟信息
	 * 
	 * @return
	 */
	public void viewPVDel() {

		peopleManageService.deletePV(id);
	}

	/**
	 * 删除证件信息
	 * 
	 * @return
	 */
	public void viewDNDel() {
		peopleManageService.deleteDN(id);
	}

	/**
	 * 删除电话信息
	 * 
	 * @return
	 */
	public void viewPNDel() {
		peopleManageService.deletePN(id);
	}

	/**
	 * 根据条件查询人员
	 * 
	 * @return
	 */
	public String peopleSeList() {
		Peopleobject poPeopleobject = new Peopleobject();
		poPeopleobject.setPoalias(poalias);
		poPeopleobject.setPocnname(pocnname);
		poPeopleobject.setPocontrolstatus(pocontrolstatus);
		poPeopleobject.setPocountry(pocountry);
		poPeopleobject.setPodirectionof(podirectionof);
		poPeopleobject.setPoenname(poenname);
		poPeopleobject.setPodutyman(podutyman);
		poPeopleobject.setPohukou(pohukou);
		poPeopleobject.setPoimportantlevel(poimportantlevel);
		poPeopleobject.setPolocation(polocation);
		poPeopleobject.setPonamespell(ponamespell);
		poPeopleobject.setPonational(ponational);
		poPeopleobject.setPopersonstatus(popersonstatus);
		poPeopleobject.setPosex(posex);
		pageModelpeople = new PageModel();
		pageModelpeople.setPageNo(pageNo);
		pageModelpeople.setPageSize(pageSize);
		pageModelpeople = peopleManageService.findPeoplePageModel(
				pageModelpeople, poPeopleobject);
		if (pageModelpeople.getList().size() == 0) {
			pageModelpeople.setPageNo(pageModelpeople.getTotalPage());
			pageModelpeople = peopleManageService.findPeoplePageModel(
					pageModelpeople, poPeopleobject);
		}
		List pelist = new ArrayList();
		StringBuffer sb = new StringBuffer();
		for (Peopleobject peopleobject : pageModelpeople.getList()) {
			peopleobject
					.setPocountry(PCOUNTRY.get(peopleobject.getPocountry()));
			peopleobject
					.setPonational(PNATION.get(peopleobject.getPonational()));
			peopleobject.setPopersonstatus(PPERSONSTATUS.get(peopleobject
					.getPopersonstatus()));
			peopleobject.setPoimportantlevel(Plevel.get(peopleobject
					.getPoimportantlevel()));
			if (peopleobject.getPodirectionof() != null
					&& !"".equals(peopleobject.getPodirectionof())) {
				for (String object : peopleobject.getPodirectionof().split(",")) {
					sb.append(PDIRECTION.get(object) + ",");
				}
				peopleobject.setPodirectionof(sb.toString().substring(0,
						sb.toString().length() - 1));
			}
			peopleobject.setPocontrolstatus(PCONTROLSTATUS.get(peopleobject
					.getPocontrolstatus()));
			pelist.add(peopleobject);
			sb = new StringBuffer();
		}
		pageModelpeople.setList(pelist);
		List<AuthUser> userList = userService.findAllUser();
		requestP.setAttribute("userList", userList);
		requestP.getSession().setAttribute("pageModel", pageModelpeople);
		String json = null;
		try {

			json = mapper.writeValueAsString(pageModelpeople);
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 人员姓名实时查询
	 * 
	 * @return
	 */
	public String viewPeopleAddRSearch() {
		StringBuffer json = new StringBuffer();
		try {
			// pocnname= URLDecoder.decode(pocnname, "UTF-8");
			if (!"".equals(pocnname)) {
				for (String a : peopleManageService.searchPname(id, pocnname)) {
					json.append(a + ",");
				}
			}
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.toString()
						.getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 多选下拉框值初始化
	 * 
	 * @param userList
	 */
	private void selectMoreSet(List<AuthUser> userList) {
		try {
			List<String> userIds = new ArrayList<String>();
			List<String> userNames = new ArrayList<String>();
			for (AuthUser user : userList) {
				userIds.add(user.getId() + "");
				userNames.add(user.getUserName());
			}
			String userIdsJson = JacksonMapperUtil.getObjectMapper()
					.writeValueAsString(userIds);
			requestP.setAttribute("userIds", userIdsJson);
			String userNamesJson = JacksonMapperUtil.getObjectMapper()
					.writeValueAsString(userNames);
			requestP.setAttribute("userNames", userNamesJson);
			// 多选下拉框使用
			Set<Entry<String, String>> set = PDIRECTION.entrySet();
			List<String> directionKeys = new ArrayList<String>();
			List<String> directionTexts = new ArrayList<String>();
			for (Entry<String, String> entry : set) {
				directionKeys.add(entry.getKey());
				directionTexts.add(entry.getValue());
			}
			String directionKeysJson = JacksonMapperUtil.getObjectMapper()
					.writeValueAsString(directionKeys);
			requestP.setAttribute("directionKeys", directionKeysJson);
			String directionTextsJson = JacksonMapperUtil.getObjectMapper()
					.writeValueAsString(directionTexts);
			requestP.setAttribute("directionTexts", directionTextsJson);
		} catch (JsonProcessingException e) {
			logger.error("json转换异常", e);
		}
	}

	public void getSelects() {
		if ("责任人".equals(inputtype)) {
			List<AuthUser> userList = userService.findAllUser();
			requestP.setAttribute("userList", userList);
		} else if ("人员状况".equals(inputtype)) {

		} else if ("重要等级".equals(inputtype)) {

		} else if ("所属方向".equals(inputtype)) {

		} else if ("控制状况".equals(inputtype)) {

		} else if ("所在地".equals(inputtype)) {

		}
	}

	public String morenImg() {
		return basePath + "images" + File.separator + "lawCase"
				+ File.separator + "moren" + File.separator + "people.jpg";
	}

	@JSON(serialize = false)
	public String getPocnname() {
		return pocnname;
	}

	public void setPocnname(String pocnname) {
		this.pocnname = pocnname;
	}

	@JSON(serialize = false)
	public String getPonamespell() {
		return ponamespell;
	}

	@JSON(serialize = false)
	public void setPonamespell(String ponamespell) {
		this.ponamespell = ponamespell;
	}

	@JSON(serialize = false)
	public String getPoenname() {
		return poenname;
	}

	public void setPoenname(String poenname) {
		this.poenname = poenname;
	}

	@JSON(serialize = false)
	public String getPocontrolstatus() {
		return pocontrolstatus;
	}

	public void setPocontrolstatus(String pocontrolstatus) {
		this.pocontrolstatus = pocontrolstatus;
	}

	@JSON(serialize = false)
	public String getPoalias() {
		return poalias;
	}

	public void setPoalias(String poalias) {
		this.poalias = poalias;
	}

	@JSON(serialize = false)
	public String getPosex() {
		return posex;
	}

	public void setPosex(String posex) {
		this.posex = posex;
	}

	@JSON(serialize = false)
	public Date getPobirthday() {
		return pobirthday;
	}

	public void setPobirthday(Date pobirthday) {
		this.pobirthday = pobirthday;
	}

	@JSON(serialize = false)
	public Date getPocreateday() {
		return pocreateday;
	}

	public void setPocreateday(Date pocreateday) {
		this.pocreateday = pocreateday;
	}

	@JSON(serialize = false)
	public String getPocountry() {
		return pocountry;
	}

	@JSON(serialize = false)
	public void setPocountry(String pocountry) {
		this.pocountry = pocountry;
	}

	@JSON(serialize = false)
	public String getPonational() {
		return ponational;
	}

	public void setPonational(String ponational) {
		this.ponational = ponational;
	}

	@JSON(serialize = false)
	public String getPohukou() {
		return pohukou;
	}

	public void setPohukou(String pohukou) {
		this.pohukou = pohukou;
	}

	@JSON(serialize = false)
	public String getPolocation() {
		return polocation;
	}

	public void setPolocation(String polocation) {
		this.polocation = polocation;
	}

	@JSON(serialize = false)
	public String getPodutyman() {
		return podutyman;
	}

	public void setPodutyman(String podutyman) {
		this.podutyman = podutyman;
	}

	@JSON(serialize = false)
	public String getPopersonstatus() {
		return popersonstatus;
	}

	public void setPopersonstatus(String popersonstatus) {
		this.popersonstatus = popersonstatus;
	}

	@JSON(serialize = false)
	public String getPoimportantlevel() {
		return poimportantlevel;
	}

	public void setPoimportantlevel(String poimportantlevel) {
		this.poimportantlevel = poimportantlevel;
	}

	@JSON(serialize = false)
	public String getPodirectionof() {
		return podirectionof;
	}

	public void setPodirectionof(String podirectionof) {
		this.podirectionof = podirectionof;
	}

	@JSON(serialize = false)
	public String getPoimage() {
		return poimage;
	}

	public void setPoimage(String poimage) {
		this.poimage = poimage;
	}

	@JSON(serialize = false)
	public String getPodescription() {
		return podescription;
	}

	public void setPodescription(String podescription) {
		this.podescription = podescription;
	}

	@JSON(serialize = false)
	public String getPoremark() {
		return poremark;
	}

	public void setPoremark(String poremark) {
		this.poremark = poremark;
	}

	@JSON(serialize = false)
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@JSON(serialize = false)
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@JSON(serialize = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JSON(serialize = false)
	public ObjectMapper getMapper() {
		return mapper;
	}

	public void setMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@JSON(serialize = false)
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@JSON(serialize = false)
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@JSON(serialize = false)
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@JSON(serialize = false)
	public long getPoid() {
		return poid;
	}

	public void setPoid(long poid) {
		this.poid = poid;
	}

	@JSON(serialize = false)
	public String getPvname() {
		return pvname;
	}

	public void setPvname(String pvname) {
		this.pvname = pvname;
	}

	@JSON(serialize = false)
	public String getPvaccounttype() {
		return pvaccounttype;
	}

	public void setPvaccounttype(String pvaccounttype) {
		this.pvaccounttype = pvaccounttype;
	}

	@JSON(serialize = false)
	public String getPvusername() {
		return pvusername;
	}

	public void setPvusername(String pvusername) {
		this.pvusername = pvusername;
	}

	@JSON(serialize = false)
	public String getPvcontrolstatus() {
		return pvcontrolstatus;
	}

	public void setPvcontrolstatus(String pvcontrolstatus) {
		this.pvcontrolstatus = pvcontrolstatus;
	}

	@JSON(serialize = false)
	public String getPvcontrolmeasures() {
		return pvcontrolmeasures;
	}

	public void setPvcontrolmeasures(String pvcontrolmeasures) {
		this.pvcontrolmeasures = pvcontrolmeasures;
	}

	@JSON(serialize = false)
	public String getPvpassword() {
		return pvpassword;
	}

	public void setPvpassword(String pvpassword) {
		this.pvpassword = pvpassword;
	}

	@JSON(serialize = false)
	public String getPvloginaddress() {
		return pvloginaddress;
	}

	public void setPvloginaddress(String pvloginaddress) {
		this.pvloginaddress = pvloginaddress;
	}

	@JSON(serialize = false)
	public String getPvremark() {
		return pvremark;
	}

	public void setPvremark(String pvremark) {
		this.pvremark = pvremark;
	}

	@JSON(serialize = false)
	public String getDnname() {
		return dnname;
	}

	public void setDnname(String dnname) {
		this.dnname = dnname;
	}

	@JSON(serialize = false)
	public String getDntype() {
		return dntype;
	}

	public void setDntype(String dntype) {
		this.dntype = dntype;
	}

	@JSON(serialize = false)
	public String getDnnumber() {
		return dnnumber;
	}

	public void setDnnumber(String dnnumber) {
		this.dnnumber = dnnumber;
	}

	@JSON(serialize = false)
	public String getDnremark() {
		return dnremark;
	}

	public void setDnremark(String dnremark) {
		this.dnremark = dnremark;
	}

	@JSON(serialize = false)
	public String getPnname() {
		return pnname;
	}

	public void setPnname(String pnname) {
		this.pnname = pnname;
	}

	@JSON(serialize = false)
	public String getPntype() {
		return pntype;
	}

	public void setPntype(String pntype) {
		this.pntype = pntype;
	}

	@JSON(serialize = false)
	public String getPnnumber() {
		return pnnumber;
	}

	public void setPnnumber(String pnnumber) {
		this.pnnumber = pnnumber;
	}

	@JSON(serialize = false)
	public String getPnremark() {
		return pnremark;
	}

	public void setPnremark(String pnremark) {
		this.pnremark = pnremark;
	}

	@JSON(serialize = false)
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@JSON(serialize = false)
	public String getRootType() {
		return rootType;
	}

	public void setRootType(String rootType) {
		this.rootType = rootType;
	}

	@JSON(serialize = false)
	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	@JSON(serialize = false)
	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	@JSON(serialize = false)
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@JSON(serialize = false)
	public File getFileField() {
		return fileField;
	}

	public void setFileField(File fileField) {
		this.fileField = fileField;
	}

	@JSON(serialize = false)
	public String getFileFieldFileName() {
		return fileFieldFileName;
	}

	public void setFileFieldFileName(String fileFieldFileName) {
		this.fileFieldFileName = fileFieldFileName;
	}

}
