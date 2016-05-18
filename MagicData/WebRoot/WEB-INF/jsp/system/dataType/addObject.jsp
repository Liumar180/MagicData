<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
	<base href="<%=basePath %>" >
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>添加对象</title>
		<link href="styles/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="styles/systemManage/css/common.css"/>
		<link rel="stylesheet" type="text/css" href="styles/systemManage/css/systemManage.css"/>
		<script type="text/javascript" src="scripts/lawCase/jquery-1.11.1.min.js"></script>		
		<script type="text/javascript" src="scripts/systemManage/systemManage.js" ></script>
		<script type="text/javascript" src="styles/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="styles/bootstrap/js/bootstrap-prompts-alert.js"></script> 
	</head>
	<body>
		<!--点击添加按钮出现的弹出层-->
		<div class="addCon addobjectCon">
			<div class="form-group">
			<input type="hidden" id="cTime" name="cTime" value="${objects.createTime}" >
			<input type="hidden" id="oId" name="oid" value="${objects.id}" />
				<label class="labelTit">对象名称</label>
				<input type="text" class="form-control" id="objName" value="${objects.name}" placeholder="请输入名称" />
			</div>
			<div class="form-group">
				<label class="labelTit">对象描述</label>
				<input type="text" class="form-control" id="objDetails" value="${objects.details}" placeholder="请输入描述信息" />
			</div>
			<div class="addobject"></div>
			<div class="hideDiv" id="pros">
			<c:forEach items="${lists}" var="my">
				<div>
                <div class="form-group">
                    <label class="labelTit">对象属性</label>
                    <c:choose>
                    <c:when test="${my.isIndex == 'true'}">
                    <input type="checkBox" checked="checked" class="checkInput" />
                    </c:when>
                    <c:otherwise>
                    <input type="checkBox" class="checkInput" />
                    </c:otherwise>
                    </c:choose>
                    <input type="text" style="width: 223px" id="fsx${my.id}" value="${my.proName}" class="form-control attrInput" placeholder="请输入对象属性" />
                </div>
                <div class="form-group">
                    <label class="labelTit">对象类型</label>
                    <select class="form-control select" id="fdx${my.id}">
							<c:choose>
							   <c:when test="${my.proType != null}">
							      	<option selected="selected">${my.proType}</option>
							      	<option>String</option>
			                        <option>Date</option>
			                        <option>Integer</option>
			                        <option>Number</option>
			                        <option>Boolean</option> 
			                        <option>Serializable</option>
			                        <option>Binary</option> 
							   </c:when>
							   <c:otherwise>
							   		<option>String</option>
			                        <option>Date</option>
			                        <option>Integer</option>
			                        <option>Number</option>
			                        <option>Boolean</option> 
			                        <option>Serializable</option>
			                        <option>Binary</option>  
							   </c:otherwise>
						  	</c:choose>
                    </select>
                </div>
                <div class="delObject" onclick="delectDIV('fsx${my.id}')"></div>
            </div>
			</c:forEach>
			</div>			
    		<div style="clear: both; margin-left: 30px">
    		<p style="color: red; font-weight: bold;">提示信息：</p>
    		<p>1、添加的对象一定要有至少一个属性，点击 <img src="images/systemManage/addpic.png"/> 按钮可追加一行，可进行属性的编辑添加；</p>
    		<p>2、对象属性的复选框 □ 默认不勾选，如果当前属性是可用来检索的属性，可在此属性后勾选复选框 ： <img src="images/systemManage/pro.png"/> ；</p>
    		</div>
    		<div class="btnDiv">
				<button class="btn btn-primary sureBtn" onclick="addObjectInfo()">保存</button>
				<button class="btn btn-default qxBtn">取消</button>
			</div>
		</div>
		<script type="text/javascript">
		var num=0; 
		var cartlist = {};
		var regPro = "^[A-Za-z]+$";
		function addObjectInfo(){
			var istrue = true;
			var pids = "";
			var pronames = "";
			var protypes = "";
			var pros ="";
			var ischeck = "";

			var arrays = "";
			var arrays2= "";
	        for (var keys in cartlist) {
	        	var value =cartlist[keys];
	        	arrays += $("#"+value).val()+","
	        	arrays2 += $("#dx"+keys).val()+",";
	        }
			var cTime = $('#cTime').val();
		    var oid=$('#oId').val();
			var name=$('#objName').val().trim();
			if(name.length==0){
				parent.hintManager.showHint("对不起，对象名不能为空或者为空格！");
	            return false;
		    }else if(name.length > 20){
		    	parent.hintManager.showHint("输入对象名称内容过长，请重新输入！");
		    	return false;
		    }else if(!name.match(regPro)){
		    	parent.hintManager.showHint("输入对象名称内容只能为英文字符，请重新输入！");
		    	return false;
		    }
			$(".attrInput").each(function(){
				var v1 = $(this).val().trim();
				if(v1.length == 0){
					parent.hintManager.showHint("对不起，属性名不能为空或者为空格！");
					istrue = false;
				}
				pronames +=v1+",";
				pros +=v1;
				var pid = $(this).attr("id");
				pids +=pid+",";
			})
			$(".select").each(function(){
				var v2 = $(this).val();
				protypes +=v2+",";
			})
			
			$(".checkInput").each(function(){
				var v3 = $(this).is(':checked');
				ischeck +=v3+",";
			})
			
			if(pids == ""){
				parent.hintManager.showHint("请添加对象属性信息(对象属性信息只能为英文字母、信息不能为空或空格)！");
	            return false;
			}
			if(!pros.match(regPro)){
				parent.hintManager.showHint("对象属性信息只能为英文字母！");
	            return false;
			}
			var details=$('#objDetails').val().trim();
			if(istrue){
			 $.ajax({
		         type:"post",
		         url:"ObjectManage/saveObject.action",
		         data:{
		        	 oid:oid,
		        	 name:name,
		        	 details:details,
		        	 cTime:cTime,
		        	 arrays:arrays,
		        	 arrays2:arrays2,
		        	 pids:pids,
		        	 pronames:pronames,
		        	 protypes:protypes,
		        	 ischeck:ischeck
		         
		         },
		         async : false,
		         dataType:"json",
		         success:function(data){
		            setCookie('ObjRefresh', 'yes',1);
		            //parent.text.location.reload();
		            $('.tccBox', parent.document).hide();
		         }
		     }); 
		}
		}
		function setCookie(c_name, value, expiredays){
        	var exdate=new Date();
        	exdate.setDate(exdate.getDate() + expiredays);
        	document.cookie=c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
        	}
		
		function cdel(key){
			delete cartlist[key];
		}
	    $(".addobject").click(function(event) {
	     num = num +1;
		 cartlist[num] = "sx"+num;
        var isEmpty = false;
        $(".attrInput").each(function(){
            isEmpty = isEmpty || $(this).val().length == 0 ;
        });
        
        if (isEmpty) {
        	parent.hintManager.showHint("请添加对象属性内容！");
            return;
        };
        $(".hideDiv").append(
            '<div>' + 
                '<div class="form-group">' + 
                    '<label class="labelTit">对象属性</label>' + 
                    '<input type="checkBox" class="checkInput" />' +
                    '<input type="text" style="width: 223px" id="sx'+num
                    +'" value="" class="form-control attrInput" placeholder="请输入对象属性" />' + 
                '</div>' + 
                '<div class="form-group">' + 
                    '<label class="labelTit">对象类型</label>' + 
                    '<select class="form-control select" id="dx'+num
                    +'">' + 
                        '<option>String</option>' + 
                        '<option>Date</option>' + 
                        '<option>Integer</option>' + 
                        '<option>Number</option>' + 
                        '<option>Boolean</option>' + 
                        '<option>Serializable</option>' + 
                        '<option>Binary</option>' +
                    '</select>' + 
                '</div>' + 
                '<div class="delObject" onclick="delectDIV(\''+num+'\')"></div>' + 
            '</div>');
        event.preventDefault();
    });
	   function delectDIV(id){
		   var ids ="";
		   if(id.indexOf("fsx") != -1){
			   ids = id.substring(3);
		   }
            cdel(id);//添加对象时，对属性的删除
             if(ids!= null && ids !=''){
				 confirm('是否确定删除？',function(){
					 $.ajax( {
							type : "POST",
							url : "ObjectManage/delProByid.action?ids="+ids,
							success : function(){
								$("#"+id).parent().parent().remove();
							},
							error:function(){
					        	parent.hintManager.showHint("删除属性异常！");
					         }
						});
				 });
					} 
            
            $("#sx"+id).parent().parent().remove();
	    }
	   function delePro(){
		   $("#sx"+id).parent().parent().remove();
	   }
	    
	   
		
		</script>
	</body>
</html>