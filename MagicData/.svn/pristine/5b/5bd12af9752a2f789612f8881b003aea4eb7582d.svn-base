
var normalizeCode = function (code) {
    code = code.replace(/&lt;/g, '<');
    code = code.replace(/&gt;/g, '>');
    code = code.replace(/&amp;/g, '&');
    return code;
}

var runCode = function () {
    var id = arguments[0];
    var code = document.getElementById(id).innerHTML;
    code = normalizeCode(code);
    if (code != "") {
        var newwin = window.open('', "_blank", '');
        newwin.document.open('text/html', 'replace');
        newwin.opener = null;
        newwin.document.write(code);
        newwin.document.close();
    }
}

var runCodes = function () {
    document.onclick = function (e) {
        e = e || window.event;
        var target = e.srcElement ? e.srcElement : e.target,
        tag = target.nodeName.toLowerCase();
        if ((tag == "button") && hasClass(target, "runcode")) {
            runCode(target.getAttribute("title"));
        }
    }
}

var hasClass = function (ele, cls) {
    return ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

runCodes();

function _jimmyClosePopUp() {
    document.getElementById("ajaxPopUp").style.display = "none";
    document.getElementById("ajaxBg").style.display = "none";
}

function _jimmyShowPopUp(type, src, height, width, title) {
    var _html = "";
    if (type == "img") {
        _html = "<img src='" + src + "' style='width:" + width + "px;height:" + height + "px;overflow:hidden' alt='" + src + "'/>";
    }
    else if (type == "swf") {
        _html = "<object data=\"" + src + "\" width='" + width + "' height='" + height + "' type=\"application/x-shockwave-flash\"><param name=\"data\" value=\"" + src + "\"/><param name=\"src\" value=\"" + src + "\"/><param name=\"allowFullScreen\" value=\"true\"/><param name=\"allowScriptAccess\" value=\"always\"/></object>";
    }
    else if (type == "xap") {
        _html = "<object data=\"data:application/x-silverlight-2,\" type=\"application/x-silverlight-2\" width=\"" + width + "\" height=\"" + height + "\"><param name=\"source\" value=\"" + src + "\"/><param name=\"minRuntimeVersion\" value=\"4.0.50401.0\" /><param name=\"autoUpgrade\" value=\"true\" /><a href=\"http://go.microsoft.com/fwlink/?LinkID=149156&v=4.0.50401.0\" style=\"text-decoration:none\"><img src=\"http://go.microsoft.com/fwlink/?LinkId=161376\" alt=\"Get Microsoft Silverlight\" style=\"border-style:none\"/></a></object>";
    }
    else if (type = "frame") {
        _html = "<iframe src='" + src + "' style='width:" + width + "px;height:" + height + "px;overflow:hidden' frameborder=\"0\" scrolling=\"no\"></iframe>";
    }

    if (title == undefined) {
        title = "µ¯³ö¿ò";
    }

    document.getElementById("ajaxTitle").innerHTML = title;

    var _body = document.getElementById("ajaxBody");
    _body.innerHTML = _html;


    var _container = document.getElementById("ajaxPopUp");
    _container.style.display = "";
    _container.style.marginLeft = "-" + (width / 2) + "px";
    _container.style.marginTop = "-" + (height / 2) + "px";
    document.getElementById("ajaxBg").style.display = "";

}