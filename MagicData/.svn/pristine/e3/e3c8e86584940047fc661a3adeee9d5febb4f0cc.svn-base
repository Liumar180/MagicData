$(function() {
    
        /*点击关闭按钮*/
    $(".historytccBox b").click(function(event) {
        $(".historytccBox").hide();
        event.preventDefault();
    });
    /*点击取消按钮*/
    $(".historyCon .qxBtn").click(function() {
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