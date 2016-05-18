package com.integrity.dataSmart.dataImport.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.integrity.dataSmart.dataImport.util.TitanStructureUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Titan结构维护action
 * @author RenSx
 *
 */
public class TitanStruAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(TitanStruAction.class);

	private File[] file;              //文件  
    private String[] fileFileName;    //文件名
    private String[] filePath;
    private String datas;
    private String showArr;
    private String aliasAll;
    
    private String fullJson;
    
    private String[] fileIdsImg;
    
    private List<Map<String,Object>> titanList = null;
    
    private Map<String, List<String>> queryableMap = null;
    
    private Map<String, List<String>> defaultShow = null;
    
    private Map<String, List<String>> parentType = null;
    
    private Map<String, Map<String, String>> titanAlias = null;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	
	private boolean result = false;
	
	/**
	 * 上传图片+添加节点
	 * @return
	 */
	public String uploadImgs(){
		String imgPath = ServletActionContext.getServletContext().getRealPath(File.separator+"images"+File.separator+"img"+File.separator);
		File file = new File(imgPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println(fileIdsImg);
		try{
			if(this.file!=null){
				File[] f = this.getFile(); 
				filePath = new String[f.length];
				for(int i=0;i<f.length;i++){
//					String name = fileFileName[i];
					String name = fileIdsImg[i]+".png";
					FileInputStream is = new FileInputStream(f[i]);
					FileOutputStream os = new FileOutputStream(imgPath+"\\"+name);
					byte[] b = new byte[1024];
					int length=0;
					while((length=is.read(b))!=-1){
						os.write(b,0,length);
					}
					is.close();
					os.flush();
					os.close();
					filePath[i] = imgPath+"\\"+name; 
				}
			}
			TitanStructureUtil.updateDefaultShow(showArr.replace("'", "\""));
			result = true;
		}catch(Exception e){
			logger.error("上传图片+添加节点 异常",e);
			result = false;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加节点，接收参数fullJson是全部完整的结构
	 * @return
	 */
	public String updateTitanVertexFull(){
		if(fullJson==null||"".equals(fullJson)){
			return SUCCESS;
		}
		try{
			result = TitanStructureUtil.updateTitanVertexFull(fullJson);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 添加节点
	 * @return String
	 */
	public String addTitanVertex(){
		String vertexName = request.getParameter("vertexName");
		String vertexNameParent = request.getParameter("vertexNameParent");
		String vertexJson = request.getParameter("vertexJson");
		titanList = TitanStructureUtil.addTitanVertex(vertexName, vertexJson);
		if(vertexNameParent!=null){
			TitanStructureUtil.addChildrenTypeToParentType(vertexName,vertexNameParent);
		}
		return SUCCESS;
	}
	/**
	 * 修改节点
	 * @return String
	 */
	public String updateTitanVertex(){
		String vertexName = request.getParameter("vertexName");
		String vertexJson = request.getParameter("vertexJson");
		titanList = TitanStructureUtil.updateTitanVertex(vertexName, vertexJson);
		return SUCCESS;
	}
	/**
	 * 删除节点
	 * @return
	 */
	public String deleteTitanVertex(){
		String vertexName = request.getParameter("vertexName");
		titanList = TitanStructureUtil.deleteTitanVertex(vertexName);
		return SUCCESS;
	}
	/**
	 * 添加边
	 * @return String
	 */
	public String addTitanEdge(){
		String edgeName = request.getParameter("edgeName");
		String edgeJson = request.getParameter("edgeJson");
		titanList = TitanStructureUtil.addTitanEdge(edgeName, edgeJson);
		return SUCCESS;
	}
	/**
	 * 修改边
	 * @return String
	 */
	public String updateTitanEdge(){
		String edgeName = request.getParameter("edgeName");
		String edgeJson = request.getParameter("edgeJson");
		titanList = TitanStructureUtil.updateTitanEdge(edgeName, edgeJson);
		return SUCCESS;
	}
	/**
	 * 删除边
	 * @return
	 */
	public String deleteTitanEdge(){
		String edgeName = request.getParameter("edgeName");
		titanList = TitanStructureUtil.deleteTitanEdge(edgeName);
		return SUCCESS;
	}
	
	/**
	 * 获取可查询属性
	 * @return
	 */
	public String findQueryableMap(){
		queryableMap = TitanStructureUtil.findQueryableMap();
		return SUCCESS;
	}
	
	/**
	 * 获取默认显示属性
	 * @return
	 */
	public String findDefaultShow(){
		defaultShow = TitanStructureUtil.getDefaultShow();
		return SUCCESS;
	}
	
	/**
	 * 获取别名
	 * @return
	 */
	public String findTitanAlias(){
		titanAlias = TitanStructureUtil.getTitanAlias();
		return SUCCESS;
	}
	
	/**
	 * 修改别名
	 * @return
	 */
	public String updateTitanAlias(){
		try{
			result = TitanStructureUtil.updateTitanAlias(aliasAll);
		}catch(Exception e){
			result = false;
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取节点父类型
	 * @return
	 */
	public String findParentType(){
		parentType = TitanStructureUtil.getParentType();
		return SUCCESS;
	}
	
	public List<Map<String, Object>> getTitanList() {
		return titanList;
	}
	public void setTitanList(List<Map<String, Object>> titanList) {
		this.titanList = titanList;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	@JSON(serialize=false)
	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}
	@JSON(serialize=false)
	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	@JSON(serialize=false)
	public String[] getFilePath() {
		return filePath;
	}
	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}
	@JSON(serialize=false)
	public String getDatas() {
		return datas;
	}
	public void setDatas(String datas) {
		this.datas = datas;
	}
	@JSON(serialize=false)
	public String[] getFileIdsImg() {
		return fileIdsImg;
	}
	public void setFileIdsImg(String[] fileIdsImg) {
		this.fileIdsImg = fileIdsImg;
	}
	@JSON(serialize=false)
	public String getFullJson() {
		return fullJson;
	}
	public void setFullJson(String fullJson) {
		this.fullJson = fullJson;
	}
	public void setQueryableMap(Map<String, List<String>> queryableMap) {
		this.queryableMap = queryableMap;
	}
	public Map<String, List<String>> getDefaultShow() {
		return defaultShow;
	}
	public void setDefaultShow(Map<String, List<String>> defaultShow) {
		this.defaultShow = defaultShow;
	}
	public Map<String, Map<String, String>> getTitanAlias() {
		return titanAlias;
	}
	public void setTitanAlias(Map<String, Map<String, String>> titanAlias) {
		this.titanAlias = titanAlias;
	}
	public Map<String, List<String>> getQueryableMap() {
		return queryableMap;
	}
	@JSON(serialize=false)
	public String getShowArr() {
		return showArr;
	}
	public void setShowArr(String showArr) {
		this.showArr = showArr;
	}
	@JSON(serialize=false)
	public String getAliasAll() {
		return aliasAll;
	}
	public void setAliasAll(String aliasAll) {
		this.aliasAll = aliasAll;
	}
	public Map<String, List<String>> getParentType() {
		return parentType;
	}
	public void setParentType(Map<String, List<String>> parentType) {
		this.parentType = parentType;
	}
	
}
