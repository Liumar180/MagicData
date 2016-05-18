<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%  
    String swfFilePath=session.getAttribute("swfpath").toString();
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >  
<html>  
<head> 
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<script type="text/javascript" src="scripts/showOnline/jquery.min.js"></script>  
<script type="text/javascript" src="scripts/showOnline/flexpaper.js"></script>  
<style type="text/css" media="screen">   
            html, body  { height:100%; }  
            body { margin:0; padding:0; overflow:auto; }     
            #flashContent { display:none; }  
        </style>   
  
<title>文档在线预览系统</title>  
</head>  
<body>   
      <div id="viewerPlaceHolder" class="flexpaper_viewer" style="width:100%;height:100%;"></div> 
          <script type="text/javascript"> 
            $('#viewerPlaceHolder').FlexPaperViewer(     
                    { config : {  
                    SWFFile : escape('<%=swfFilePath%>'),  
                    Scale : 1,   
                    ZoomTransition : 'easeOut',  
                    ZoomTime : 0.5,  
                    ZoomInterval : 0.2,  
                    FitPageOnLoad : false,  
                    FitWidthOnLoad : true,  
                    FullScreenAsMaxWindow : false,  
                    ProgressiveLoading : false,  
                    MinZoomSize : 0.5,  
                    MaxZoomSize : 2,  
                    SearchMatchAll : true,  
                    InitViewMode : 'Portrait',
                    ReaderingOrder :'false',
                    StartAtPage :'',
                    ViewModeToolsVisible : true,  
                    ZoomToolsVisible : true,  
                    NavToolsVisible : true,  
                    CursorToolsVisible : true,  
                    SearchToolsVisible : true,
                    
                    localeChain: 'zh_CN'  
                    }});    
            </script>              
         
</body>  
</html>