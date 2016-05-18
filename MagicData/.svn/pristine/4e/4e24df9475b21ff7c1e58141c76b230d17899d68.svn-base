/// <reference path="jquery.Xslider.js" />
/// <reference path="jquery.js" />
/* ===== 神州中联插件 1.6 ===== */
/*
    szzlGalleryA:画廊效果A
    szzlScroll:图片滚动
    szzlToggle:Tab切换
    szzlXslider:图片切换
    szzlMenuTree:二、三级目录树
    szzlFlashPlay:Flash播放

    时间：2013年9月15日17:46:43
    功能：增加 szzlGalleryA 画廊效果A 2013年9月15日17:47:13
*/
(function ($) {
    /* ===== 画廊效果 ===== */
    $.fn.szzlGalleryA = function (options) {
        var settings = {
            prevBtn: ".prevBtn",
            nextBtn: ".nextBtn",
            leftBtn: ".aLeft",
            rightBtn: ".aRight",
            scrollBox: ".switcher",
            conbox: ".conbox",
            stag: 'img',            // 切换器标签 默认为a
            ctag: 'img',            // 内容标签 默认为<li>
            itemWidth: 0,           // 每个内容标签宽度 
            itemSpacing: 0,         // 内容标签间距 
            itemSets: ".imgList",
            current: 'setOn',       //当前切换器样式名称
            speed: 1500,
			picindex:"#picindex",
			piccount:"#piccount"
        };

        settings = $.extend({}, settings, options);

        var $conbox = $(this).find(settings.conbox),
            $scrollBox = $(this).find(settings.scrollBox),
            $itembox = $(this).find(settings.itemSets),
            $prevBtn = $(this).find(settings.prevBtn),
            $nextBtn = $(this).find(settings.nextBtn),
            $leftBtn = $(this).find(settings.leftBtn),
            $rightBtn = $(this).find(settings.rightBtn),
            $stag = $($scrollBox).find(settings.$stag),
            $picindex=$(this).find(settings.picindex),
			$piccount=$(this).find(settings.piccount),
			
            $contents = $conbox.find(settings.ctag),
            $imgs = $itembox.find(settings.stag),
            len = $contents.length,
            index = 0,
            last_index = len - 1,
            maxW = settings.itemWidth + settings.itemSpacing;

        $itembox.width(len * maxW);
        $scrollBox.scrollLeft(settings.itemSpacing);

        $prevBtn.click(left);
        $nextBtn.click(right);
        $leftBtn.click(left);
        $rightBtn.click(right);
		$piccount.text(len);

        $.each($contents, function (k, v) {
            (k === 0) ? $(this).css({ 'position': 'absolute', 'z-index': 9 }) : $(this).css({ 'position': 'absolute', 'z-index': 1, 'opacity': 0 });
        });

        function showImg() {
            $contents.eq(last_index).stop().animate({ 'opacity': 0 }, settings.speed / 2).css('z-index', 1)
                     .end()
                     .eq(index).css('z-index', 9).stop().animate({ 'opacity': 1 }, settings.speed / 2);

            $imgs.eq(index).addClass(settings.current).siblings(settings.stag).removeClass(settings.current);
            $picindex.text(index+1);
            if (index > 2) {
                var sl = (index * maxW) - (2 * maxW);
                $scrollBox.scrollLeft(sl);
            } else {
                $scrollBox.scrollLeft(settings.itemSpacing);
            }
        }

        $imgs.each(function ($e) {
            $(this).click(function () {
                last_index = index;
                index = $e;
                showImg();
            });
        });


        function left() {
            last_index = index;
            index--;

            if (index < 0) {
                index = len - 1;
            }
            showImg();
        }

        function right() {
            last_index = index;
            index++;

            if (index >= len) {
                index = 0;
            }
            showImg();
        }

    },
    /* ===== 图片滚动 ===== */
    $.fn.szzlScroll = function (options) {
        var settings = {
            prevBtn: ".prevBtn",
            nextBtn: ".nextBtn",
            scrollBox: ".imgList",
            conbox: ".conbox",
            ctag: 'li',         //内容标签 默认为<li>
            itemWidth: 221,     // 每个内容标签宽度 
            itemSpacing: 0,     // 内容标签间距 
            path: 'left',       // left,right,up,down
            space: 10,
            auto: true
        };
        settings = $.extend({}, settings, options);

        var html = $(settings.conbox).html();
        var len = $(settings.conbox + " " + settings.ctag).length;
        var width = (settings.itemWidth + settings.itemSpacing) * len;

        $(settings.conbox).html(html + html).width(width * 2);

        //if (settings.auto) {
        //    //自动滚动未完
        //上下左右分开未完成
        //}
        function leftScroll() {
        }

        var scrollLeft = $(settings.scrollBox).scrollLeft();

        function Marquee() {
            if (scrollLeft >= width) {
                scrollLeft = 0;
            } else {
                scrollLeft++;
            }
            $(settings.scrollBox).scrollLeft(scrollLeft);
        }

        function Marquee() {
            if (scrollLeft >= width) {
                scrollLeft = 0;
            } else {
                scrollLeft++;
            }
            $(settings.scrollBox).scrollLeft(scrollLeft);
        }

        function Marquee2() {
            if (scrollLeft <= 0) {
                scrollLeft = width;
            } else {
                scrollLeft--;
            }
            $(settings.scrollBox).scrollLeft(scrollLeft);
        }

        var MyMar = setInterval(Marquee, settings.space);

        $(settings.scrollBox).mouseover(function () {
            clearInterval(MyMar);
        }).mouseout(function () {
            MyMar = setInterval(Marquee, settings.space)
        });

        $(settings.prevBtn).mouseover(function () {
            clearInterval(MyMar);
        }).mouseout(function () {
            MyMar = setInterval(Marquee, settings.space)
        }).mousedown(function () {
            MyMar = setInterval(Marquee2, settings.space)
        }).mouseup(function () {
            clearInterval(MyMar);
        });

        $(settings.nextBtn).mouseover(function () {
            clearInterval(MyMar);
        }).mouseout(function () {
            MyMar = setInterval(Marquee, settings.space)
        }).mousedown(function () {
            MyMar = setInterval(Marquee, settings.space)
        }).mouseup(function () {
            clearInterval(MyMar);
        });
    },
    /* ===== Tab切换 ===== */
    $.fn.szzlToggle = function (options) {

        var settings = {
            conbox: ".conbox",
            switcher: ".switcher",
            ctag: 'div',
            stag: 'li',
            current: 'setOn'
        };
        settings = $.extend({}, settings, options);

        $(this).szzlXslider({
            affect: 'none',
            auto: false,
            conbox: settings.conbox,
            ctag: settings.ctag,
            switcher: settings.switcher,
            stag: settings.stag,
            current: settings.current
        });
    },
    /* ===== 图片切换 ===== */
    $.fn.szzlXslider = function (options) {
        var settings = {
            affect: 'scrolly', //效果  有scrollx|scrolly|fade|none
            speed: 1200, //动画速度
            space: 6000, //时间间隔
            auto: true, //自动滚动
            trigger: 'mouseover', //触发事件 注意用mouseover代替hover
            conbox: '#conbox', //内容容器id或class
            ctag: 'a', //内容标签 默认为<a>
            switcher: '#switcher', //切换触发器id或class
            stag: 'a', //切换器标签 默认为a
            current: 'setOn', //当前切换器样式名称
            rand: false //是否随机指定默认幻灯图片
        };
        settings = $.extend({}, settings, options);
        var index = 1;
        var last_index = 0;
        var $conbox = $(this).find(settings.conbox), $contents = $conbox.find(settings.ctag);
        var $switcher = $(this).find(settings.switcher), $stag = $switcher.find(settings.stag);
        if (settings.rand) { index = Math.floor(Math.random() * $contents.length); slide(); }
        if (settings.affect == 'fade') {
            $.each($contents, function (k, v) {
                (k === 0) ? $(this).css({ 'position': 'absolute', 'z-index': 9 }) : $(this).css({ 'position': 'absolute', 'z-index': 1, 'opacity': 0 });
            });
        }
        function slide() {
            if (index >= $contents.length) index = 0;
            $stag.removeClass(settings.current).eq(index).addClass(settings.current);
            switch (settings.affect) {
                case 'scrollx':
                    $conbox.width($contents.length * $contents.width());
                    $conbox.stop().animate({ left: -$contents.width() * index }, settings.speed);
                    break;
                case 'scrolly':
                    $contents.css({ display: 'block' });
                    $conbox.stop().animate({ top: -$contents.height() * index + 'px' }, settings.speed);
                    break;
                case 'fade':
                    $contents.eq(last_index).stop().animate({ 'opacity': 0 }, settings.speed / 2).css('z-index', 1)
							 .end()
							 .eq(index).css('z-index', 9).stop().animate({ 'opacity': 1 }, settings.speed / 2)
                    break;
                case 'none':
                    $contents.hide().eq(index).show();
                    break;
            }
            last_index = index;
            index++;
        };
        if (settings.auto) var Timer = setInterval(slide, settings.space);
        $stag.bind(settings.trigger, function () {
            _pause()
            index = $(this).index();
            slide();
            _continue()
        });
        $conbox.hover(_pause, _continue);
        function _pause() {
            clearInterval(Timer);
        }
        function _continue() {
            if (settings.auto) Timer = setInterval(slide, settings.space);
        }
    },
    /* ===== 二、三级目录树 ===== */
    $.fn.szzlMenuTree = function (options) {
        var settings = {
            twoTagName: "a.t2",
            twoParentTagName: "div.menuList",
            threeTagName: "a.t3",
            threeParentTagName: "dd",
            setOnClass: 'setOn',
            setOffClass: 'setOff'
        };
        settings = $.extend({}, settings, options);

        var ts = $(this);

        ts.find(settings.twoTagName).each(function () {
            $(this).click(function ($e) {
                var tl = $(this).parent(settings.twoParentTagName);
                if (!tl.hasClass(settings.setOnClass)) {
                    tl.addClass(settings.setOnClass).removeClass(settings.setOffClass).siblings(settings.twoParentTagName).removeClass(settings.setOnClass).addClass(settings.setOffClass);
                } else {
                    tl.removeClass(settings.setOnClass);
                }
                $e.preventDefault();
            });
        });

        ts.find(settings.threeTagName).each(function () {
            $(this).click(function ($e) {
                var tl = $(this).parent(settings.threeParentTagName);
                if (!tl.hasClass(settings.setOnClass)) {
                    ts.find(settings.threeParentTagName).removeClass(settings.setOnClass).addClass(settings.setOffClass);
                    tl.addClass(settings.setOnClass).removeClass(settings.setOffClass);
                } else {
                    tl.removeClass(settings.setOnClass);
                }
                $e.preventDefault();
            });
        });
    },
    /* ===== Flash播放 ===== */
     $.fn.szzlFlashPlay = function (options) {
        var defaults = {
            fileUrl: "",       /* 文件路径 */
            fileType: "swf",        /* 文件类型 */
            width: 1003,            /* 宽度 */
            height: 157,            /* 高度 */
            isTransparent: false,   /* 是透明的 */
            isNoBorder: false,      /* 是无边框的 */
            isAutoStart: false       /* 是自动播放 */

        }
        var options = $.extend(defaults, options);
        var obj = $(this);

        var objectHtml = "", embedHtml = "", objectClassid = "", codebase = "";
        switch (options.fileType) {
            case "swf":
                objectClassid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000";
                codebase = 'http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0';
                break;
            case "wmv":
                objectClassid = "CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6";
                codebase = '';
                break;
            case "mp4":
            case "wav":
                objectClassid = "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B";
                codebase = 'http://www.apple.com/qtactivex/qtplugin.cab';
                break;
            case "mp3":
                objectClassid = '';
                codebase = '';
                break;
            default: break;
        }

        embedHtml = '<embed  src="' + options.fileUrl + '" ' + (options.isAutoStart ? 'autostart="1"' : 'autostart="0"') + ' width="' + options.width + '" height="' + options.height + '" type="application/x-shockwave-flash" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" '
            + (options.isTransparent ? 'wmode="transparent"' : '') + ' '
            + (options.isNoBorder ? 'scale="noborder"' : '') + '></embed>';
        
        objectHtml = '<object classid="' + objectClassid + '" codebase="' + codebase + '" width="' + options.width + '" height="' + options.height + '">' +
          '<param name="SRC" value="' + options.fileUrl + '">' +
          '<param name="Movie" value="' + options.fileUrl + '">' +
          '<param name="url" value="' + options.fileUrl + '">' +
          '<param name="filename" value="' + options.fileUrl + '">' +
          '<param name="uiMode" value="full" />' +
          '<param name="quality" value="high">' +
          '<param name="autostart" value="' + (options.isAutoStart ? '1"' : '0') + '" />' +
          (options.isTransparent ? '<param name="wmode" value="transparent" />' : '') +
          (options.isNoBorder ? '<param name="SCALE" value="noborder" />' : '') +
          embedHtml +
          '</object>';
        
        obj.html(objectHtml);
    };
})(jQuery);