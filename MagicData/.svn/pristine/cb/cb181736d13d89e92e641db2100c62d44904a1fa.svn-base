$(function() {
    //左侧
    $(".leftCon ul li").click(function() {
        $(this).addClass("setOn").siblings("li").removeClass("setOn");
    });
   
        /*添加对象弹出层*/
    $(".objectSpan").click(function() {
            var ajData = {
                title: "添加对象",
                url: "ObjectManage/addObject.action",
                title1: ""
            };
            parent.parentPop(ajData);
        })
        /*点击关闭按钮*/
    $(".tccBox b").click(function(event) {
        $(".tccBox").hide();
        event.preventDefault();
    });
    /*点击取消按钮*/
    $(".addCon .qxBtn").click(function() {
        parent.dismissParentPop();
    });


    //弹出层js
    function fc() {
        var W = $(window).width();
        var WindowH = $(window).height();
        var wH = $(window).height();
        var H = $(document).height();
        if (W <= 1280) {
            $('.bg').css('width', '1280px')
        } else {
            $('.bg').css('width', '100%')
        }
        $('.bg').css('height', H);
    }
    fc();
    $(window).resize(function() {
        fc();
    });
    $(window).load(function() {
        fc();
    });
})