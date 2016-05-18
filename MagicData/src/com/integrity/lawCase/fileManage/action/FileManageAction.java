package com.integrity.lawCase.fileManage.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.common.SolrDocument;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.integrity.dataSmart.common.page.PageModel;
import com.integrity.dataSmart.impAnalyImport.email2solr.SolrAllUtils;
import com.integrity.dataSmart.impAnalyImport.util.ContentType;
import com.integrity.dataSmart.impAnalyImport.util.FileTools;
import com.integrity.dataSmart.impAnalyImport.util.FileOnlineShow.DocConverter;
import com.integrity.dataSmart.util.jsonUtil.JacksonMapperUtil;
import com.integrity.lawCase.caseManage.bean.WorkAllocation;
import com.integrity.lawCase.common.ConstantManage;
import com.integrity.lawCase.fileManage.bean.FilesObject;
import com.integrity.lawCase.fileManage.service.FileManageService;
import com.integrity.lawCase.relation.pojo.RelationAction;
import com.integrity.lawCase.relation.wapper.AllRelationWapper;
import com.integrity.lawCase.util.ChineTo16Utils;
import com.integrity.lawCase.util.FilesToZip;
import com.integrity.lawCase.util.MD5FileUtil;
import com.integrity.lawCase.util.PageSetValueUtil;
import com.integrity.lawCase.util.TransformYears;
import com.integrity.system.auth.bean.AuthUser;
import com.integrity.system.auth.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class FileManageAction extends ActionSupport{
	private static final String FileManageService = null;
	private Logger logger = Logger.getLogger(FileManageAction.class);
	private ObjectMapper mapper = JacksonMapperUtil.getObjectMapper(); 
	private InputStream inputStream;
	private HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response=ServletActionContext.getResponse();
	private String from;
	FilesToZip filesToZip=new FilesToZip();
	public PageModel<FilesObject> pageModel;
	public FileManageService fileManageService;
	private UserService userService;
	public PageModel<FilesObject> pageModelfile;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
	MD5FileUtil md5=new MD5FileUtil();
	private String rootType;
	private String rootId;
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    private List<WorkAllocation> allocationList;
    private String ids;
	private int year;
	private int pageNo;
	private int pageSize;
	private Long id;//文件id
	private String direction;//
	private String responsiblePerson;
	private String annexUrl;
	private String annexName;
	private String fileName;
	private String fileContents;
	private Date createTime;
	private String remarkes1;
	private String remarkes2;
	private Integer remarkes3;
	private String relId;
    private String relType;
    /**文件上传*/
	private File fileField;
	
	//获取字典表信息
	private ServletContext sc = ServletActionContext.getServletContext();
	Map<String,Map<String,String>> allMap = (Map<String, Map<String, String>>) sc.getAttribute(ConstantManage.DATADICTIONARY);
	Map<String,String> FDIRECTION = allMap.get(ConstantManage.DIRECTION);//方向
	Map<String,String> type_property = (Map<String, String>) sc.getAttribute(ConstantManage.TYPE_PROPERTY);
	Properties swfdatas = (Properties) sc.getAttribute("swfUrlexe");
	public String fileMangeIndex(){
		fileMangeAllList();
			return SUCCESS;
		}
	/**
	 * 查询所有文件信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String fileMangeAllList(){
		pageSize=50;
		List<Integer> years = TransformYears.getYearsBySeveral();
		request.getSession().setAttribute("years", years);
		pageModel=new PageModel();
		pageModel.setPageNo(1);
		pageModel.setPageSize(pageSize);
		pageModel = fileManageService.findFilePageModel(pageModel,new FilesObject(),null,false);
		List flist= new ArrayList();
		StringBuffer sb=new StringBuffer();
		for (FilesObject filesObject : pageModel.getList()) {
			if(filesObject.getDirection()!=null&&!"".equals(filesObject.getDirection())){
				for (String object : filesObject.getDirection().split(",")) {
					sb.append(FDIRECTION.get(object)+",");
				}
				filesObject.setDirection(sb.toString().substring(0, sb.toString().length()-1));
			}
			flist.add(filesObject);
			sb=new StringBuffer();
		}
		pageModel.setList(flist);
		request.getSession().setAttribute("pageModel",pageModel);
		String json = null;
		try {
			
			json = mapper.writeValueAsString(pageModel.getList());
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 根据条件查询文件
	 * @return
	 */
	public String fileSeList(){
		String documentContent = request.getParameter("DocumentContent");//文件内容
		@SuppressWarnings("unused")
		List<SolrDocument> solrDocument = null;
		boolean isSearchSolrnull = false;
		if(StringUtils.isNotEmpty(documentContent)){
			SolrAllUtils.connection();
			solrDocument = SolrAllUtils.getFileFullDocment(documentContent, pageNo-1, pageSize);
			SolrAllUtils.close();
		}
		if(StringUtils.isNotBlank(documentContent)){
			if(solrDocument.equals("null") || solrDocument.size() ==0){
				isSearchSolrnull = true;
			}
		}
		FilesObject fileobject= new FilesObject();
		fileobject.setAnnexUrl(annexUrl);
		fileobject.setCreateTime(createTime);
		fileobject.setDirection(direction);
		fileobject.setFileContents(fileContents);
		fileobject.setFileName(fileName);
		fileobject.setResponsiblePerson(responsiblePerson);
		fileobject.setRemarkes1(remarkes1);
		fileobject.setRemarkes2(remarkes2);
		fileobject.setRemarkes3(remarkes3);
		pageModelfile=new PageModel();
		pageModelfile.setPageNo(pageNo);
		pageModelfile.setPageSize(pageSize);
		pageModelfile = fileManageService.findFilePageModel(pageModelfile,fileobject,solrDocument,isSearchSolrnull);
		if(pageModelfile.getList().size()==0){
			pageModelfile.setPageNo(pageModelfile.getTotalPage());
			pageModelfile =fileManageService.findFilePageModel(pageModelfile,fileobject,solrDocument,isSearchSolrnull);
		}
		List flist= new ArrayList();
		StringBuffer sb=new StringBuffer();
		for (FilesObject filebject : pageModelfile.getList()) {
			if(filebject.getDirection()!=null&&!"".equals(filebject.getDirection())){
				for (String object : filebject.getDirection().split(",")) {
					sb.append(FDIRECTION.get(object)+",");
				}
				filebject.setDirection(sb.toString().substring(0, sb.toString().length()-1));
			}
			flist.add(filebject);
			sb=new StringBuffer();
		}
		pageModelfile.setList(flist);
		List<AuthUser> userList = userService.findAllUser();
		request.setAttribute("userList", userList);
		request.getSession().setAttribute("pageModel",pageModelfile);
		String json = null;
		try {
			
			json = mapper.writeValueAsString(pageModelfile);
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询文件列表
	 * @return
	 */
	public String fileMangeList(){
		try {
			FilesObject fbject=new FilesObject();
			if(year!=0){
				fbject.setCreateTime(sdf.parse(year+1+"0000"));
			}
			if(direction!=null && !"null".equals(direction)){
				fbject.setDirection(direction);
			}
			pageModel = fileManageService.findFilePageModel(pageModel,fbject,null,false);
			List flist= new ArrayList();
			StringBuffer sb=new StringBuffer();
			for (FilesObject filesObject : pageModel.getList()) {
				if(filesObject.getDirection()!=null&&!"".equals(filesObject.getDirection())){
					for (String object : filesObject.getDirection().split(",")) {
						sb.append(FDIRECTION.get(object)+",");
					}
					filesObject.setDirection(sb.toString().substring(0, sb.toString().length()-1));
				}
				flist.add(filesObject);
				sb=new StringBuffer();
			}
			pageModel.setList(flist);
		} catch (Exception e) {
			logger.error("查询文件列表异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 文件关联添加
	 * @return
	 */
	public String viewFileAdd(){
		FilesObject filesObject= new FilesObject();
		filesObject.setId(id);
		request.getSession().setAttribute("fileinfo", new FilesObject());
		request.getSession().setAttribute("fileinfoR", filesObject);
		List<AuthUser> userList = userService.findAllUser();
		request.setAttribute("userList", userList);
		selectMoreSet(userList);
		//关联对象相关
		if(StringUtils.isNotBlank(rootId)){
			request.setAttribute("rootId", rootId);
			request.setAttribute("fileType", ConstantManage.FILEOBJECTTYPE);
			request.setAttribute("rootType", rootType);
		}
		return SUCCESS;
	}
	/**
	 * 文件关联查询
	 * @return
	 */
	public String viewFileAddRSearch(){
		StringBuffer json = new StringBuffer();
		try {
//			fileName= URLDecoder.decode(fileName, "UTF-8"); 
			if(!"".equals(fileName)){
				for (String a : fileManageService.searchFname(id,fileName)) {
					json.append(a+",");
				}
			}
			if (json != null) {
				inputStream = new ByteArrayInputStream(json.toString().getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	/**
	 * 多选下拉框值初始化
	 * @param userList
	 */
	private void selectMoreSet(List<AuthUser> userList){
		try {
			List<String> userIds = new ArrayList<String>();
			List<String> userNames = new ArrayList<String>();
			for (AuthUser user : userList) {
				userIds.add(user.getId()+"");
				userNames.add(user.getUserName());
			}
			String userIdsJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(userIds);
			request.setAttribute("userIds", userIdsJson);
			String userNamesJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(userNames);
			request.setAttribute("userNames", userNamesJson);
			//多选下拉框使用
			Set<Entry<String, String>> set = FDIRECTION.entrySet();
			List<String> directionKeys = new ArrayList<String>();
			List<String> directionTexts = new ArrayList<String>();
			for (Entry<String, String> entry : set) {
				directionKeys.add(entry.getKey());
				directionTexts.add(entry.getValue());
			}
			String directionKeysJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(directionKeys);
			request.setAttribute("directionKeys", directionKeysJson);
			String directionTextsJson = JacksonMapperUtil.getObjectMapper().writeValueAsString(directionTexts);
			request.setAttribute("directionTexts", directionTextsJson);
		} catch (JsonProcessingException e) {
			logger.error("json转换异常",e);
		}
	}
	/**
	 * 文件添加或修改
	 * @return
	 */
	public void fileMangeSave() throws IOException{
		boolean suc = false;
		String ysb;
		String realpath = "";
		Long idR = null;
		FileManageAction filemanageaction = new FileManageAction();
		try {
			String fileNamePath1 = null;
			String fileNamePath2 = null;
			FilesObject filesObject=new FilesObject();
			if(id!=null){ filesObject =fileManageService.getFileinfoByid(id);}
			filesObject.setDirection(direction);
			filesObject.setFileContents(fileContents);
			filesObject.setFileName(fileName);
			filesObject.setResponsiblePerson(responsiblePerson);
			String editUrl = filesObject.getAnnexUrl();
			if(id==null){id=(long) 0;}
			if(!"".equals(annexUrl)&&annexUrl!=null){
				if(annexUrl.indexOf("\\") == -1){
					annexUrl = "facker\\"+annexUrl;
				}
				if(annexUrl.indexOf("\\")>=0){
					if(id>0){
						annexName=filesObject.getAnnexName();
						
					}else{
						annexName=UUID.randomUUID().toString();
					}
					realpath = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+annexName+File.separator ;//获取服务器路径
					suc = uploadFile(fileName,fileField,realpath,editUrl);
					filesObject.setAnnexName(annexName);
					if(id>0){
						String fileNamePath = null;
						int a = editUrl.lastIndexOf(annexName);
						int b = editUrl.lastIndexOf("/")+1;
						if(a<b){
							fileNamePath = editUrl.substring(a,b);
							fileNamePath1 = editUrl.substring(a+annexName.length()+1, b);
							fileNamePath2 = fileNamePath1;
							fileNamePath1 += fileName;
							fileNamePath += fileName;
						}else{
							fileNamePath = fileName;
						}
						filesObject.setAnnexUrl(basePath+"images/uploadFile/"+fileNamePath);
						String targetPath = realpath + fileNamePath1;
						try {
							filesObject.setFileMD5(md5.getFileMD5String(new File(targetPath)));
						} catch (Exception e) {
							System.out.println("未获取到上传的文件");
						}
					}else{
						filesObject.setAnnexUrl(basePath+"images/uploadFile/"+annexName+"/"+fileName);
						String targetPath = realpath + fileName;
						filesObject.setFileMD5(md5.getFileMD5String(new File(targetPath)));
					}
				}else{
					filesObject.setAnnexUrl(fileManageService.getFileinfoByid(id).getAnnexUrl());
				}
			}else{
				filesObject.setAnnexUrl("");
			}
			//修改
			if(id>0){
				filesObject.setId(id);
				filesObject.setCreateTime(fileManageService.getFileinfoByid(id).getCreateTime());
				fileManageService.edit(filesObject);
				/***压缩包文件解压 zip rar**/
				ysb = fileName.substring(fileName.lastIndexOf(".")+1);
				if(ysb.equals("rar")){
					filemanageaction.unrar(realpath+File.separator+fileName, realpath,id,filesObject,fileManageService);
				}else if(ysb.equals("zip")){
					filemanageaction.unzip(realpath+File.separator+fileName, realpath,id,filesObject,fileManageService);
				}
				request.getSession().setAttribute("fileinfo", filesObject);
			}else{
				filesObject.setCreateTime(new Date());
				idR=fileManageService.saveR(filesObject);
				if(!"undefined".equals(rootId)){
					fileManageService.saveFileRelation(rootId, rootType, idR);
					request.getSession().setAttribute("fromtype", "detail");
				}else{
					request.getSession().setAttribute("fromtype", "save");
				}
				/***压缩包文件解压 zip rar**/
				ysb = fileName.substring(fileName.lastIndexOf(".")+1);
				if(ysb.equals("rar")){
					filemanageaction.unrar(realpath+File.separator+fileName, realpath,idR,filesObject,fileManageService);
				}else if(ysb.equals("zip")){
					filemanageaction.unzip(realpath+File.separator+fileName, realpath,idR,filesObject,fileManageService);
				}
				StringBuffer sb=new StringBuffer();
				if(filesObject.getDirection()!=null&&!"".equals(filesObject.getDirection())){
					for (String object : filesObject.getDirection().split(",")) {
						sb.append(FDIRECTION.get(object)+",");
					}
					filesObject.setDirection(sb.toString().substring(0, sb.toString().length()-1));
				}
				
				request.getSession().setAttribute("fileinfo", filesObject);
			}
			
			//存储文件到solr
			if(suc){
				if(!ysb.equals("zip") && !ysb.equals("rar")){
				Map<String,String> paramsMap = new HashMap<String,String>();
				if(id > 0){
					paramsMap.put("literal.id", id.toString());
				}else{
					paramsMap.put("literal.id", idR.toString());
				}
				SolrAllUtils.connection();
				if(id > 0){
					SolrAllUtils.indexFilesSolrCell(realpath+fileNamePath1, paramsMap);
				}else{
					SolrAllUtils.indexFilesSolrCell(realpath+fileName, paramsMap);
				}
				SolrAllUtils.close();
				
			    String fileType =  FileTools.getTypeByFileName(fileName);
				String contentType= ContentType.getNameByType(fileType);
				//script
				if (StringUtils.isNotBlank(contentType)) {
					String converFileName = null;
					if(id > 0){
						converFileName = realpath+fileNamePath2+fileName;
					}else{
						converFileName = realpath+fileName;
					}
					if(contentType.indexOf("script") != -1){
						String fn = fileName.substring(0,fileName.lastIndexOf("."));
						converFileName = realpath+fn+".txt";
					}
					if("txt".equals(fileType)){
						String fn = fileName.substring(0,fileName.lastIndexOf("."));
						converFileName = realpath+fn+".odt";
					}
				    //将文本转换为swf文件，用于在线预览；
				    DocConverter d=new DocConverter(converFileName,annexName);
					String exe = swfdatas.get("swf.exe").toString();
					String openIp = swfdatas.get("openoffice.ip").toString();
					d.conver(fileName,openIp,exe);
				}
			}
		}
			
		} catch (Exception e) {
			logger.error("上传文件异常",e);
		}
	
	}
	/**
	 * 上传文件
	 * @return
	 */
	public boolean uploadFile(String fileName,File fileField,String realpath,String editUrl){
		boolean isFinally = false;
		if(fileField == null){return true;}
		String fileType =  FileTools.getTypeByFileName(fileName);
		String contentType= ContentType.getNameByType(fileType);
		try {
		 FileInputStream input = new FileInputStream(fileField);
		 if(editUrl!= null){
			 String path = editUrl.substring(editUrl.lastIndexOf(annexName)+annexName.length()+1);
			 realpath = realpath+path;
		 }
		 File file =new File(realpath);
		 if(id>0 && editUrl!= null){
			 //修改时清空文件夹
			 deleteFile(file,null);
			 if("txt".equals(fileType)){
				 realpath = realpath.substring(0,realpath.lastIndexOf("."))+"odt";
				 file =new File(realpath);
				 deleteFile(file,null);
			 }
			 realpath = realpath.replaceAll("\\\\", "/");
			 realpath = realpath.substring(0,realpath.lastIndexOf("/")+1);
			 file = new File(realpath);
		 }
		if(!file .exists()  && !file .isDirectory()){       
		    file.mkdirs();  
		}
		FileOutputStream output1 = null;
		FileOutputStream outScript = null;
		FileOutputStream outTxt = null;
		
		
		if(contentType != null && contentType.indexOf("script") != -1){
			String sname = fileName.substring(0,fileName.lastIndexOf("."))+".txt";
			outScript = new FileOutputStream(realpath+sname);
		}
		if(fileType != null && fileType.equals("txt")){
			String t1 = fileName.substring(0,fileName.lastIndexOf("."))+".odt";
			outTxt = new FileOutputStream(realpath+t1);
		}
		
		if("swf".equals(fileType)){
			String fm = ChineTo16Utils.getUtf8(fileName.substring(0,fileName.lastIndexOf(".")))+".swf";
			output1 = new FileOutputStream(realpath+fm);
		}
		 FileOutputStream output = new FileOutputStream(realpath+fileName);
		 FileChannel in = input.getChannel();
		 FileChannel out = output.getChannel();
         in.transferTo(0, in.size(), out);
         if(output1 != null){
        	 FileChannel out1 = output1.getChannel();
        	 in.transferTo(0, in.size(), out1);
        	 out1.close();
        	 output1.close();
         }
         if(outScript != null){
        	 FileChannel outScr = outScript.getChannel();
        	 in.transferTo(0, in.size(), outScr);
        	 outScr.close();
        	 outScript.close();
         }
         
         if(outTxt != null){
        	 FileChannel outT = outTxt.getChannel();
        	 in.transferTo(0, in.size(), outT);
        	 outT.close();
        	 outTxt.close();
         }
         
         out.close();
         in.close();
         output.close();
		 input.close();
         isFinally = true;

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return isFinally;
	}
	/**
	 * 文件详细
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String fileMangeDetail(){
		FilesObject filesObject=fileManageService.getFileinfoByid(id);
			filesObject.setCreateTimeString(sdf2.format(filesObject.getCreateTime()));
		StringBuffer sb=new StringBuffer();
		if(filesObject.getDirection()!=null&&!"".equals(filesObject.getDirection())){
			for (String object : filesObject.getDirection().split(",")) {
				sb.append(FDIRECTION.get(object)+",");
			}
			filesObject.setDirection(sb.toString().substring(0, sb.toString().length()-1));
		}
		request.getSession().setAttribute("fileinfo", filesObject);
		request.getSession().setAttribute("fromtype", from);
		List<AuthUser> userList = userService.findAllUser();
		request.setAttribute("userList", userList);
		selectMoreSet(userList);
		//跳转链接
		RelationAction relationAction = PageSetValueUtil.relationActionSet(type_property,ConstantManage.FILEOBJECTTYPE,id+"");
		request.setAttribute("relationAction", relationAction);
		//关联对象详细
		AllRelationWapper allRelation = fileManageService.findRelation(id, ConstantManage.FILEOBJECTTYPE);
		request.setAttribute("allRelation", allRelation);
		/*工作配置*/
		allocationList = fileManageService.findWorkAllocationByCaseId(id);
		request.setAttribute("allocationList", allocationList);
		request.setAttribute("rootType", ConstantManage.FILEOBJECTTYPE);
		//对象类型
		Map<String, String> typeMap = ConstantManage.getObjectTypeMap();
		request.setAttribute("typeMap", typeMap);
		
		
		
		return SUCCESS;
	}
	/**
	 * 文件删除
	 * @return
	 */
	public void viewFileDel(){
		for (String i : ids.split(",")) {
			FilesObject filesObject=fileManageService.getFileinfoByid(Long.valueOf(i));
			String img=filesObject.getAnnexUrl();
			String annexName = filesObject.getAnnexName();
			String path = img.substring(img.lastIndexOf(annexName));
			String delP = img.substring(img.lastIndexOf(annexName), img.lastIndexOf(filesObject.getFileName())-1);
			String wjm = filesObject.getFileName().substring(0,filesObject.getFileName().lastIndexOf("."));
			String utf8name = ChineTo16Utils.getUtf8(wjm);
			String swf = utf8name+".swf";
			String delfp = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+""+delP;
			if(img!=null){
				img=ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+""+path;
				File dir = new File(img);  // 输入要删除的文件位置
				deleteFile(dir,delfp);
				String fileType =  FileTools.getTypeByFileName(path);
				if("txt".equals(fileType)){
					String odt = path.substring(0,path.lastIndexOf("."))+".odt";
					img = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+""+odt;
					dir = new File(img);  // 输入要删除的odt文件位置
					deleteFile(dir,delfp);
				}
				String swfPath = ServletActionContext.getServletContext().getRealPath("/")
						+"images"+File.separator+"uploadFile"+File.separator+annexName+File.separator+swf;
				String delswf = ServletActionContext.getServletContext().getRealPath("/")
						+"images"+File.separator+"uploadFile"+File.separator+annexName;
				File swfdir = new File(swfPath);
				deleteFile(swfdir,delswf);
			}
			fileManageService.delete(Long.valueOf(i));
		}
		//solr删除文件
		SolrAllUtils.connection();
		SolrAllUtils.deleteSolrObjectList(Arrays.asList(ids.split(",")));
		SolrAllUtils.close();
		fileManageService.delAllRelactions(ids, rootType);
	}

    private synchronized void deleteFile(File file,String path) {
        if(file.exists()){//判断文件是否存在  
         if(file.isFile()){//判断是否是文件 
          file.delete();//删除文件   
         }
        }else{
         logger.info("所删文件不存在");  
        }
        if(path != null){
        	File delfile = new File(path);
        	if(delfile.isDirectory()) {//如果它是一个目录  
                File[] files = delfile.listFiles();//声明目录下所有的文件 files[];
                if(files.length == 0){
                	delfile.delete();//删除文件夹  
                }
             }
        }
       } 
    /**
	 * 文件关联删除
	 * @return
	 */
	public String delRelationship(){
    	if(StringUtils.isNotEmpty(String.valueOf(id))){
    		fileManageService.delSingleRelation(String.valueOf(id), rootType, relId, relType);
    		try {
				inputStream = new ByteArrayInputStream(String.valueOf(id).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}finally{
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    	}
    	return SUCCESS;
	}

	/**
	 * @return
	 * 文件在线预览
	 */
	public String toShowIngOnline(){
		String fid = request.getParameter("fid");
		FilesObject fo = fileManageService.getFileinfoByid(Long.valueOf(fid));
		String annexN = fo.getAnnexName();
		String filename = fo.getFileName().substring(0,fo.getFileName().lastIndexOf(".")).trim();
		String rfilename = ChineTo16Utils.getUtf8(filename);
		
		String realpath = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+annexN+File.separator ;//获取服务器路径
		String uu = realpath+rfilename+".swf";
		boolean isH = isHaveSwf(uu);
		if(!isH){
			String Converterfile = realpath+fo.getFileName();
			String fileType =  FileTools.getTypeByFileName(fo.getFileName());
			String contentType= ContentType.getNameByType(fileType);
			if(contentType.indexOf("script") != -1){
				String sname = fo.getFileName().substring(0,fo.getFileName().lastIndexOf("."))+".txt";
				Converterfile = realpath+sname;
			}
			//将文本转换为swf文件，用于在线预览；
			DocConverter d=new DocConverter(Converterfile,annexN);
			String exe = swfdatas.get("swf.exe").toString();
			String openIp = swfdatas.get("openoffice.ip").toString();
			d.conver(fo.getFileName(),openIp,exe);
		}
		String directory = "images/uploadFile/"+fo.getAnnexName()+"/"+rfilename+".swf";
		request.getSession().setAttribute("swfpath", directory);  
		return SUCCESS;
	}
	/**
	 * @param path
	 * @return 判断是否存在swf文件
	 */
	private boolean isHaveSwf(String path){
		boolean istrue = false;
		File file = new File(path);
        if (file.exists()) {
         if (file.isFile()) {
          istrue = true;
         } else if (file.isDirectory()) {
          File[] files = file.listFiles();
          for (int i = 0;i < files.length;i ++) {
        	  istrue = true;
          }  
         }  
        }else{
         logger.info("swf文件不存在");
        }
       return istrue;
	}

    public synchronized void unrar(String tarFileName, String extPlace,Long parentId,FilesObject fo,FileManageService fms) throws Exception{  
    	unRarFile(tarFileName, extPlace,parentId,fo,fms);
    }
    public synchronized void unzip(String zipFileName, String extPlace,Long parentId,FilesObject fo,FileManageService fms) throws Exception{  
    	unZipFiles(zipFileName, extPlace,parentId,fo,fms);
    }
    /** 
     * 解压zip格式的压缩文件到指定位置 
     * @param zipFileName 压缩文件 
     * @param extPlace 解压目录 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public boolean unZipFiles(String zipFileName, String extPlace,Long parentId,FilesObject fo,FileManageService fms) throws Exception {
    	System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
    	String direction  = fo.getDirection();//所属方向
     	String Rperson = fo.getResponsiblePerson();//责任人
     	Date ctime = fo.getCreateTime();
     	String annexName = fo.getAnnexName().isEmpty() ?UUID.randomUUID().toString():fo.getAnnexName();
     	FilesObject filesobject = new FilesObject();
     	filesobject.setDirection(direction);
     	filesobject.setResponsiblePerson(Rperson);
     	filesobject.setCreateTime(ctime);
        try {  
            (new File(extPlace)).mkdirs();  
            File f = new File(zipFileName);
            ZipFile zipFile = new ZipFile(zipFileName,"GBK");  
            if((!f.exists()) && (f.length() <= 0)) {  
                throw new Exception("要解压的文件不存在!");  
            }  
            String strPath, gbkPath, strtemp;  
            File tempFile = new File(extPlace);  
            strPath = tempFile.getAbsolutePath();  
            Enumeration<?> e = zipFile.getEntries();
            while(e.hasMoreElements()){
                ZipEntry zipEnt = (ZipEntry) e.nextElement();
                gbkPath=zipEnt.getName();
                if(zipEnt.isDirectory()){  
                    strtemp = strPath + File.separator + gbkPath;  
                    File dir = new File(strtemp);  
                    dir.mkdirs();  
                    continue;  
                } else {
                    //读写文件  
                    InputStream is = zipFile.getInputStream(zipEnt);  
                    BufferedInputStream bis = new BufferedInputStream(is);  
                    gbkPath=zipEnt.getName();  
                    strtemp = strPath + File.separator + gbkPath;  
                  
                    //建目录  
                    String strsubdir = gbkPath;  
                    for(int i = 0; i < strsubdir.length(); i++) {  
                        if(strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {  
                            String temp = strPath + File.separator + strsubdir.substring(0, i);  
                            File subdir = new File(temp);  
                            if(!subdir.exists())  
                            subdir.mkdir();  
                        }  
                    }  
                    FileOutputStream fos = new FileOutputStream(strtemp);  
                    BufferedOutputStream bos = new BufferedOutputStream(fos);  
                    int c;  
                    while((c = bis.read()) != -1) {  
                        bos.write((byte) c);  
                    }
                    bos.close();  
                    fos.close();
                    //存入mysql
                    String fileName = zipEnt.toString().substring(zipEnt.toString().lastIndexOf("/")+1);
                  	filesobject.setFileName(fileName);
                  	filesobject.setAnnexName(annexName);
                  	filesobject.setParentId(parentId);
                  	String annexUrl;
                  	if(parentId > 0){
                  		String faPath = fms.getFileinfoByid(parentId).getAnnexUrl();
                  		annexUrl = faPath.substring(0,faPath.lastIndexOf("/")+1)+zipEnt.toString();
                  	}else{
                  		annexUrl =basePath+"images/uploadFile/"+annexName+"/"+zipEnt.toString();
                  	}
                  	filesobject.setAnnexUrl(annexUrl);
                  	String targetPath = extPlace+File.separator+zipEnt;
                  	filesobject.setFileMD5(md5.getFileMD5String(new File(targetPath)));
                  	Long fid = fms.saveR(filesobject);
                  	
                  	String realpath = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+annexName+File.separator;
    				Map<String,String> paramsMap = new HashMap<String,String>();
    				paramsMap.put("literal.id", fid.toString());
    				paramsMap.put("literal.parentId", parentId.toString());
    				SolrAllUtils.connection();
    				SolrAllUtils.indexFilesSolrCell(realpath+zipEnt.toString(), paramsMap);
    				SolrAllUtils.close();
    				
    			    String fileType =  FileTools.getTypeByFileName(fileName);
    				String contentType= ContentType.getNameByType(fileType);
    				//script
    				if (StringUtils.isNotBlank(contentType)) {
    					String name = zipEnt.toString().replaceAll("/", "\\\\");
    					String converFileName = realpath+name;
    					if(contentType.indexOf("script") != -1){
    						String fn = name.substring(0,name.lastIndexOf("."));
    						converFileName = realpath+fn+".txt";
    					}
    				    //将文本转换为swf文件，用于在线预览；
    				    DocConverter d=new DocConverter(converFileName,annexName);
    					String exe = swfdatas.get("swf.exe").toString();
    					String openIp = swfdatas.get("openoffice.ip").toString();
    					d.conver(fileName,openIp,exe);
    				}
    			
                }
            }
        } catch(Exception e) {
        	logger.info("该压缩文件已加密！");
            return false;
        } 
        return true;
    }  
    /** 
     * 根据原始rar路径，解压到指定文件夹下.      
     * @param srcRarPath 原始rar路径 
     * @param dstDirectoryPath 解压到的文件夹      
     */
     public boolean unRarFile(String srcRarPath, String dstDirectoryPath,Long parentId,FilesObject fo,FileManageService fms) {
    	String direction  = fo.getDirection();//所属方向
     	String Rperson = fo.getResponsiblePerson();//责任人
     	Date ctime = fo.getCreateTime();
     	String annexName = fo.getAnnexName().isEmpty() ?UUID.randomUUID().toString():fo.getAnnexName();
     	FilesObject filesobject = new FilesObject();
     	filesobject.setDirection(direction);
     	filesobject.setResponsiblePerson(Rperson);
     	filesobject.setCreateTime(ctime);
         if (!srcRarPath.toLowerCase().endsWith(".rar")) {
             System.out.println("非rar文件！");
             return false;
         }
         File dstDiretory = new File(dstDirectoryPath);
         if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
             dstDiretory.mkdirs();
         }
         Archive a = null;
         try {
             a = new Archive(new File(srcRarPath));
             if (a != null) {
                 //a.getMainHeader().print(); // 打印文件信息.
                 FileHeader fh = a.nextFileHeader();
                 while (fh != null) {
                	 String fileName = fh.getFileNameW().isEmpty()?fh.getFileNameString():fh.getFileNameW();
                	 fileName = fileName.replaceAll("\\\\", "/");
                     if (fh.isDirectory()) { // 文件夹 
                    	 File fol = new File(dstDirectoryPath + File.separator + fileName);
                         fol.mkdirs();
                     } else { // 文件
                    	File out = new File(dstDirectoryPath + File.separator + fileName.trim());
                         try { 
                             if (!out.exists()) {
                                 if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录. 
                                     out.getParentFile().mkdirs();
                                 }
                                 out.createNewFile();
                             }
                             FileOutputStream os = new FileOutputStream(out);
                             a.extractFile(fh, os);
                             os.close();
                             /************************/
                            String fname = fileName.substring(fileName.toString().lastIndexOf("/")+1);
                           	filesobject.setFileName(fname);
                           	filesobject.setAnnexName(annexName);
                           	filesobject.setParentId(parentId);
                           	String annexUrl;
                           	if(parentId > 0){
                           		String faPath = fms.getFileinfoByid(parentId).getAnnexUrl();
                           		annexUrl = faPath.substring(0,faPath.lastIndexOf("/")+1)+fileName;
                           	}else{
                           		annexUrl =basePath+"images/uploadFile/"+annexName+"/"+fileName;
                           	}
                           	filesobject.setAnnexUrl(annexUrl);
                           	String targetPath = dstDirectoryPath+File.separator+fileName;
                           	filesobject.setFileMD5(md5.getFileMD5String(new File(targetPath)));
                           	Long fid = fms.saveR(filesobject);
                           	
                           	String realpath = ServletActionContext.getServletContext().getRealPath("/")+"images"+File.separator+"uploadFile"+File.separator+annexName+File.separator;
             				Map<String,String> paramsMap = new HashMap<String,String>();
             				paramsMap.put("literal.id", fid.toString());
             				paramsMap.put("literal.parentId", parentId.toString());
             				SolrAllUtils.connection();
             				SolrAllUtils.indexFilesSolrCell(realpath+fileName, paramsMap);
             				SolrAllUtils.close();
             				
             			    String fileType =  FileTools.getTypeByFileName(fname);
             				String contentType= ContentType.getNameByType(fileType);
             				//script
             				if (StringUtils.isNotBlank(contentType)) {
             					fileName = fileName.replaceAll("/", "\\\\");
             					String converFileName = realpath+fileName;
             					if(contentType.indexOf("script") != -1){
             						String fn = fileName.substring(0,fileName.lastIndexOf("."));
             						converFileName = realpath+fn+".txt";
             					}
             				    //将文本转换为swf文件，用于在线预览；
             				    DocConverter d=new DocConverter(converFileName,annexName);
             					String exe = swfdatas.get("swf.exe").toString();
             					String openIp = swfdatas.get("openoffice.ip").toString();
             					d.conver(fname,openIp,exe);
             				}
                         } catch (Exception ex) {
                        	 logger.info("该压缩文件已加密！");
                             //ex.printStackTrace();
                         }
                        
                     }
                     fh = a.nextFileHeader();
                 }
                 a.close();
             }
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
         return true;
     }

	
	@JSON(serialize=false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	} @JSON(serialize=false)
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	} @JSON(serialize=false)
	public String getResponsiblePerson() {
		return responsiblePerson;
	}
	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	} @JSON(serialize=false)
	public String getAnnexUrl() {
		return annexUrl;
	}
	public void setAnnexUrl(String annexUrl) {
		this.annexUrl = annexUrl;
	} @JSON(serialize=false)
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	} @JSON(serialize=false)
	public String getFileContents() {
		return fileContents;
	}
	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	} @JSON(serialize=false)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	} @JSON(serialize=false)
	public String getRemarkes1() {
		return remarkes1;
	}
	public void setRemarkes1(String remarkes1) {
		this.remarkes1 = remarkes1;
	} @JSON(serialize=false)
	public String getRemarkes2() {
		return remarkes2;
	}
	public void setRemarkes2(String remarkes2) {
		this.remarkes2 = remarkes2;
	} @JSON(serialize=false)
	public Integer getRemarkes3() {
		return remarkes3;
	}
	public void setRemarkes3(Integer remarkes3) {
		this.remarkes3 = remarkes3;
	}
	@JSON(serialize=false)
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	@JSON(serialize=false)
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	@JSON(serialize=false)
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}@JSON(serialize=false)
	public FileManageService getFileManageService() {
		return fileManageService;
	}
	public void setFileManageService(FileManageService fileManageService) {
		this.fileManageService = fileManageService;
	}@JSON(serialize=false)
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}@JSON(serialize=false)
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}@JSON(serialize=false)
	public String getRootType() {
		return rootType;
	}
	public void setRootType(String rootType) {
		this.rootType = rootType;
	}@JSON(serialize=false)
	public String getRootId() {
		return rootId;
	}
	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	@JSON(serialize=false)
	public File getFileField() {
		return fileField;
	}
	public void setFileField(File fileField) {
		this.fileField = fileField;
	}
	@JSON(serialize=false)
	public String getAnnexName() {
		return annexName;
	}
	public void setAnnexName(String annexName) {
		this.annexName = annexName;
	}@JSON(serialize=false)
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	@JSON(serialize=false)
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
	@JSON(serialize=false)
	public String getRelType() {
		return relType;
	}
	public void setRelType(String relType) {
		this.relType = relType;
	}
	public PageModel<FilesObject> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<FilesObject> pageModel) {
		this.pageModel = pageModel;
	}

	
}
