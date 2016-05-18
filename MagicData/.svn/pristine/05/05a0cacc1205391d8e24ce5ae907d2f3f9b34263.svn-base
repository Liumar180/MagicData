$.ajaxSetup({
	cache:false,
	contentType:"application/x-www-form-urlencoded;charset=utf-8",  
	complete:function(XHR,textStatus){ 
		var resText = XHR.responseText;  
        if(resText=='ajaxSessionTimeOut'){  
        	window.location = "login.jsp";
        }else if(resText=='licenseExpired'){
        	window.location = "auth.jsp";
        }
	}
});