
/// <reference path="jquery.js" />
// JavaScript Document
/// <reference path="szzlCommPlug.js" />
$(document).ready(function () {

    var speed = 800;
    // var time = 5000;
    var divImgScrollLeft = 0;
    var $leftBtn = $("a#prevBtn");
    var $rightBtn = $("a#nextBtn");
    var $divImgScroll = $("div#imgScroll2");
    var divImgScrollLength = $divImgScroll.find("div.imgDiv").length;
    var xiaoGuo = "easeOutCirc";//���ӵĻ���Ч��easeOutBounce easeInOutExpo easeOutCirc easeOutElastic
    var divWidth = $divImgScroll.find("div.imgDiv").width()+10;
    var $main = $("div.imgScroll");

    $leftBtn.click(function () {
        if (!$divImgScroll.is(":animated")) {
            $divImgScroll.animate({ "left": "+=" + divWidth + "px" }, speed, xiaoGuo, function () {
                var $divImgLast = $divImgScroll.find("div.imgDiv:last");
                $divImgLast.fadeOut("slow");
                $divImgLast.prependTo($divImgScroll);
                $divImgScroll.css("left", divImgScrollLeft).find("div.imgDiv:first").fadeIn("fast");
            });
        }
        return false;
    })

    $rightBtn.click(function () {
        if (!$divImgScroll.is(":animated")) {
            $divImgScroll.animate({ "left": "-=" + divWidth + "px" }, speed, xiaoGuo, function () {
                var $divImgFirst = $divImgScroll.find("div.imgDiv:first");
                $divImgFirst.fadeOut("fast");
                $divImgFirst.appendTo($divImgScroll);
                $divImgScroll.css("left", divImgScrollLeft).find("div.imgDiv:last").fadeIn("fast");
            });
        }
        return false;
    })

    // $main.hover(function () {
    //     clearInterval(id);
    // }, function () {
    //     id = setInterval(imgScrollFun, time);
    // }).trigger("mouseleave", time);

    function imgScrollFun() {

        $divImgScroll.animate({ "left": "-=" + divWidth + "px" }, speed, xiaoGuo, function () {
            var $divImgFirst = $divImgScroll.find("div.imgDiv:first");
            $divImgFirst.fadeOut("fast");
            $divImgFirst.appendTo($divImgScroll);
            $divImgScroll.css("left", divImgScrollLeft).find("div.imgDiv:last").fadeIn("fast");
        });
    }

});