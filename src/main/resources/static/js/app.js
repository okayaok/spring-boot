/**
 * 用来处理Layout或者基础函数的核心Script
 * @type {{init, fixContentHeight, setEqualHeight, scrollTo, scrollTop, blockUI, unblockUI, initUniform, initChosenSelect, initFancybox, getActualVal, getURLParameter, isTouchDevice, isIE8, isRTL, getLayoutColorCode}}
 */
var App = function () {

    // IE mode
    var isRTL = false;
    var isIE8 = false;
    var isIE9 = false;
    var isIE10 = false;

    //定义导航菜单栏的最大宽度,单位: px
    var sidebarWidth = 225;
    //定义导航菜单栏的收缩之后的宽度, 单位: px
    var sidebarCollapsedWidth = 35;

    var responsiveHandlers = [];

    //设置Layout的主题颜色
    var layoutColorCodes = {
        'blue': '#4b8df8',
        'grey': '#555555',
        'light-grey': '#fafafa'
    };

    var handleInit = function () {

        if ($('body').css('direction') === 'rtl') {
            isRTL = true;
        }

        isIE8 = !!navigator.userAgent.match(/MSIE 8.0/);
        isIE9 = !!navigator.userAgent.match(/MSIE 9.0/);
        isIE10 = !!navigator.userAgent.match(/MSIE 10/);

        if (isIE10) {
            jQuery('html').addClass('ie10'); // detect IE10 version
        }
    };

    var handleDesktopTabletContents = function () {
        //使用“响应类”和平板电脑模式应用类循环所有页面元素
        if ($(window).width() <= 1280 || $('body').hasClass('page-boxed')) {
            $(".responsive").each(function () {
                var forTablet = $(this).attr('data-tablet');
                var forDesktop = $(this).attr('data-desktop');
                if (forTablet) {
                    $(this).removeClass(forDesktop);
                    $(this).addClass(forTablet);
                }
            });
        }

        //使用“响应类”和桌面模式应用的类循环所有页面元素
        if ($(window).width() > 1280 && $('body').hasClass('page-boxed') === false) {
            $(".responsive").each(function () {
                var forTablet = $(this).attr('data-tablet');
                var forDesktop = $(this).attr('data-desktop');
                if (forTablet) {
                    $(this).removeClass(forTablet);
                    $(this).addClass(forDesktop);
                }
            });
        }
    };

    var handleSidebarState = function () {
        //当窗口宽度小于980时，移除边栏切换功能（对于手机和表格来说）
        if ($(window).width() < 980) {
            $('body').removeClass("page-sidebar-closed");
        }
    };

    var runResponsiveHandlers = function () {
        //重新初始化其他订阅元素
        for (var i in responsiveHandlers) {
            var each = responsiveHandlers[i];
            each.call();
        }
    };

    var handleResponsive = function () {
        handleTooltips();
        handleSidebarState();
        handleDesktopTabletContents();
        handleSidebarAndContentHeight();
        handleFixedSidebar();
        runResponsiveHandlers();
    };

    var handleResponsiveOnInit = function () {
        handleSidebarState();
        handleDesktopTabletContents();
        handleSidebarAndContentHeight();
    };

    /**
     * 针对IE8进行响应式调整
     */
    var handleResponsiveOnResize = function () {
        var resize;
        if (isIE8) {
            var currheight;
            $(window).resize(function () {
                if (currheight === document.documentElement.clientHeight) {
                    return; //quite event since only body resized not window.
                }
                if (resize) {
                    clearTimeout(resize);
                }
                resize = setTimeout(function () {
                    handleResponsive();
                }, 50); // wait 50ms until window resize finishes.                
                currheight = document.documentElement.clientHeight; // store last body client height
            });
        } else {
            $(window).resize(function () {
                if (resize) {
                    clearTimeout(resize);
                }
                resize = setTimeout(function () {
                    console.log('resize');
                    handleResponsive();
                }, 50); // wait 50ms until window resize finishes.
            });
        }
    };

    //* BEGIN:CORE HANDLERS *//

    /**
     * 该函数在屏幕进行调整或者手机屏幕旋转时，响应设置Layout。
     * 处理侧边菜单栏和文本显示高度，以及footer显示高度设置
     */
    var handleSidebarAndContentHeight = function () {
        var content = $('.page-content');
        var sidebar = $('.page-sidebar');
        var body = $('body');
        var height;

        if (body.hasClass("page-footer-fixed") === true && body.hasClass("page-sidebar-fixed") === false) {
            var available_height = $(window).height() - $('.footer').height();
            if (content.height() < available_height) {
                content.attr('style', 'min-height:' + available_height + 'px !important');
            }
        } else {
            if (body.hasClass('page-sidebar-fixed')) {
                height = calculateFixedSidebarViewportHeight();
            } else {
                height = sidebar.height() + 20;
            }
            if (height >= content.height()) {
                content.attr('style', 'min-height:' + height + 'px !important');
            }
        }
    };

    /**
     * 处理侧边菜单栏
     */
    var handleSidebarMenu = function () {
        jQuery('.page-sidebar').on('click', 'li > a', function (e) {
            if ($(this).next().hasClass('sub-menu') === false) {
                if (!$('.btn-navbar').hasClass('collapsed')) {
                    $('.btn-navbar').click();
                }
                return;
            }

            var parent = $(this).parent().parent();

            parent.children('li.open').children('a').children('.arrow').removeClass('open');
            parent.children('li.open').children('.sub-menu').slideUp(200);
            parent.children('li.open').removeClass('open');

            var sub = jQuery(this).next();
            if (sub.is(":visible")) {
                jQuery('.arrow', jQuery(this)).removeClass("open");
                jQuery(this).parent().removeClass("open");
                sub.slideUp(200, function () {
                    handleSidebarAndContentHeight();
                });
            } else {
                jQuery('.arrow', jQuery(this)).addClass("open");
                jQuery(this).parent().addClass("open");
                sub.slideDown(200, function () {
                    handleSidebarAndContentHeight();
                });
            }

            e.preventDefault();
        });

    };

    /**
     * 计算固定之后侧边导航栏的高度
     *
     * @returns {number} 侧边导航栏的高度
     * @private
     */
    var calculateFixedSidebarViewportHeight = function () {
        var sidebarHeight = $(window).height() - $('.header').height() + 1;
        if ($('body').hasClass("page-footer-fixed")) {
            sidebarHeight = sidebarHeight - $('.footer').height();
        }

        return sidebarHeight;
    };

    /**
     * 处理固定后的侧边栏
     */
    var handleFixedSidebar = function () {
        var menu = $('.page-sidebar-menu');
        //在更新高度之前先摧毁之前创建的实例
        if (menu.parent('.slimScrollDiv').size() === 1) {
            menu.slimScroll({
                destroy: true
            });
            menu.removeAttr('style');
            $('.page-sidebar').removeAttr('style');
        }

        if ($('.page-sidebar-fixed').size() === 0) {
            handleSidebarAndContentHeight();
            return;
        }

        if ($(window).width() >= 980) {
            var sidebarHeight = calculateFixedSidebarViewportHeight();

            menu.slimScroll({
                size: '7px',
                color: '#a1b2bd',
                opacity: .3,
                position: isRTL ? 'left' : ($('.page-sidebar-on-right').size() === 1 ? 'left' : 'right'),
                height: sidebarHeight,
                allowPageScroll: false,
                disableFadeOut: false
            });
            handleSidebarAndContentHeight();
        }
    };

    /**
     * 当鼠标悬停在侧边栏上的处理函数
     */
    var handleFixedSidebarHover = function () {
        if ($('body').hasClass('page-sidebar-fixed') === false) {
            return;
        }

        $('.page-sidebar').off('mouseenter').on('mouseenter', function () {
            var body = $('body');

            if ((body.hasClass('page-sidebar-closed') === false || body.hasClass('page-sidebar-fixed') === false) || $(this).hasClass('page-sidebar-hovering')) {
                return;
            }

            body.removeClass('page-sidebar-closed').addClass('page-sidebar-hover-on');
            $(this).addClass('page-sidebar-hovering');
            $(this).animate({
                width: sidebarWidth
            }, 400, '', function () {
                $(this).removeClass('page-sidebar-hovering');
            });
        });

        $('.page-sidebar').off('mouseleave').on('mouseleave', function () {
            var body = $('body');

            if ((body.hasClass('page-sidebar-hover-on') === false || body.hasClass('page-sidebar-fixed') === false) || $(this).hasClass('page-sidebar-hovering')) {
                return;
            }

            $(this).addClass('page-sidebar-hovering');
            $(this).animate({
                width: sidebarCollapsedWidth
            }, 400, '', function () {
                $('body').addClass('page-sidebar-closed').removeClass('page-sidebar-hover-on');
                $(this).removeClass('page-sidebar-hovering');
            });
        });
    };

    /**
     * 点击侧边菜单栏切换按钮时显示或者隐藏该菜单
     */
    var handleSidebarToggle = function () {
        // 隐藏或者显示侧边导航栏
        $('.page-sidebar').on('click', '.sidebar-toggler', function (e) {
            var body = $('body');
            var sidebar = $('.page-sidebar');

            if ((body.hasClass("page-sidebar-hover-on") && body.hasClass('page-sidebar-fixed')) || sidebar.hasClass('page-sidebar-hovering')) {
                body.removeClass('page-sidebar-hover-on');
                sidebar.css('width', '').hide().show();
                e.stopPropagation();
                runResponsiveHandlers();
                return;
            }

            $(".sidebar-search", sidebar).removeClass("open");

            if (body.hasClass("page-sidebar-closed")) {
                body.removeClass("page-sidebar-closed");
                if (body.hasClass('page-sidebar-fixed')) {
                    sidebar.css('width', '');
                }
            } else {
                body.addClass("page-sidebar-closed");
            }
            runResponsiveHandlers();
        });

        // handle the search bar close
        $('.page-sidebar').on('click', '.sidebar-search .remove', function (e) {
            e.preventDefault();
            $('.sidebar-search').removeClass("open");
        });

        // handle the search query submit on enter press
        $('.page-sidebar').on('keypress', '.sidebar-search input', function (e) {
            if (e.which === 13) {
                window.location.href = "extra_search.html";
                return false; //<---- Add this line
            }
        });

        // handle the search submit
        $('.sidebar-search .submit').on('click', function (e) {
            e.preventDefault();

            if ($('body').hasClass("page-sidebar-closed")) {
                if ($('.sidebar-search').hasClass('open') === false) {
                    if ($('.page-sidebar-fixed').size() === 1) {
                        $('.page-sidebar .sidebar-toggler').click(); //trigger sidebar toggle button
                    }
                    $('.sidebar-search').addClass("open");
                } else {
                    window.location.href = "/";
                }
            } else {
                window.location.href = "/";
            }
        });
    };

    /**
     * 点击切换按钮时，显示或者隐藏搜索选项中的横线样式
     */
    var handleHorizontalMenu = function () {
        //处理搜索菜单
        $('.header').on('click', '.hor-menu .hor-menu-search-form-toggler', function (e) {
            if ($(this).hasClass('hide')) {
                $(this).removeClass('hide');
                $('.header .hor-menu .search-form').hide();
            } else {
                $(this).addClass('hide');
                $('.header .hor-menu .search-form').show();
            }
            e.preventDefault();
        });

        //点击搜索按钮时处理方式
        $('.header').on('click', '.hor-menu .search-form .btn', function (e) {
            window.location.href = "/";
            e.preventDefault();
        });

        //处理菜单栏中的搜素选项，当敲击回车键时，提交跳转路径
        $('.header').on('keypress', '.hor-menu .search-form input', function (e) {
            if (e.which === 13) {
                window.location.href = "/";
                return false;
            }
        });
    };

    /**
     * 点击跳到顶部按钮时，返回页面顶部
     */
    var handleGoTop = function () {
        jQuery('.footer').on('click', '.go-top', function (e) {
            App.scrollTo();
            e.preventDefault();
        });
    };

    var handleUniform = function () {
        if (!jQuery().uniform) {
            return;
        }
        var test = $("input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
        if (test.size() > 0) {
            test.each(function () {
                if ($(this).parents(".checker").size() === 0) {
                    $(this).show();
                    $(this).uniform();
                }
            });
        }
    };

    /*
     处理工具类的方法
     */
    var handleTooltips = function () {
        //如果是触摸设备，为了防止和点击事件有冲突，可以跳过一些工具
        if (App.isTouchDevice()) {
            jQuery('.tooltips:not(.no-tooltip-on-touch-device)').tooltip();
        } else {
            jQuery('.tooltips').tooltip();
        }
    };

    /**
     * 处理下拉菜单
     */
    var handleDropdown = function () {
        $('body').on('click', '.dropdown-menu.hold-on-click', function (e) {
            e.stopPropagation();
        })
    };

    /*
     主题设置
     */
    var handleTheme = function () {

        var panel = $('.color-panel');

        $('.sidebar-option', panel).val("fixed");
        $('.header-option', panel).val("fixed");
        $('.footer-option', panel).val("fixed");

        /**
         * 设置Layout样式
         */
        var setLayout = function () {

            var sidebarOption = $('.sidebar-option', panel).val();
            var headerOption = $('.header-option', panel).val();
            var footerOption = $('.footer-option', panel).val();

            if (sidebarOption === "fixed" && headerOption === "default") {
                //使用toastr弹出警告信息提示框
                toastr.warning('当顶部的导航栏滚动时，左边的侧边栏必须随之滚动！', '警告', {positionClass: 'toast-top-center'});
                $('.sidebar-option', panel).val("default");
                sidebarOption = 'default';
            }

            //Layout改变时，响应处理
            runResponsiveHandlers();

            //导航栏设置
            if (headerOption === 'fixed') {
                $("body").addClass("page-header-fixed");
                $(".header").removeClass("navbar-static-top").addClass("navbar-fixed-top");
            } else {
                $("body").removeClass("page-header-fixed");
                $(".header").removeClass("navbar-fixed-top").addClass("navbar-static-top");
            }

            //菜单栏设置
            if (sidebarOption === 'fixed') {
                $("body").addClass("page-sidebar-fixed");
            } else {
                $("body").removeClass("page-sidebar-fixed");
            }

            //Footer设置
            if (footerOption === 'fixed') {
                $("body").addClass("page-footer-fixed");
            } else {
                $("body").removeClass("page-footer-fixed");
            }

            handleSidebarAndContentHeight();
            handleFixedSidebar();
            handleFixedSidebarHover();
        };

        /**
         * 改变主题颜色
         */
        var setColor = function (color) {
            $('#style_color').attr("href", "/static/css/color/" + color + ".css");
            $.cookie('style_color', color);
        };

        $('.icon-color', panel).click(function () {
            $('.color-mode').show();
            $('.icon-color-close').show();
        });

        $('.icon-color-close', panel).click(function () {
            $('.color-mode').hide();
            $('.icon-color-close').hide();
        });

        $('li', panel).click(function () {
            var color = $(this).attr("data-style");
            setColor(color);
            $('.inline li', panel).removeClass("current");
            $(this).addClass("current");
        });

        $('.header-option, .sidebar-option, .footer-option', panel).change(setLayout);
    };

    var handleFixInputPlaceholderForIE = function () {
        //fix html5 placeholder attribute for ie7 & ie8
        if (isIE8 || isIE9) { // ie7&ie8
            // this is html5 placeholder fix for inputs, inputs with placeholder-no-fix class will be skipped(e.g: we need this for password fields)
            jQuery('input[placeholder]:not(.placeholder-no-fix), textarea[placeholder]:not(.placeholder-no-fix)').each(function () {

                var input = jQuery(this);

                if (input.val() === '' && input.attr("placeholder") !== '') {
                    input.addClass("placeholder").val(input.attr('placeholder'));
                }

                input.focus(function () {
                    if (input.val() === input.attr('placeholder')) {
                        input.val('');
                    }
                });

                input.blur(function () {
                    if (input.val() === '' || input.val() === input.attr('placeholder')) {
                        input.val(input.attr('placeholder'));
                    }
                });
            });
        }
    };

    //* END:CORE HANDLERS *//

    return {

        //main function to initiate template pages
        init: function () {

            //core handlers
            handleInit();
            handleResponsiveOnResize(); // set and handle responsive    
            handleUniform();
            handleResponsiveOnInit(); // handler responsive elements on page load

            //layout handlers
            handleFixedSidebar(); // handles fixed sidebar menu
            handleFixedSidebarHover(); // handles fixed sidebar on hover effect
            handleSidebarMenu(); // handles main menu
            handleHorizontalMenu(); // handles horizontal menu
            handleSidebarToggle(); // handles sidebar hide/show
            handleFixInputPlaceholderForIE(); // fixes/enables html5 placeholder attribute for IE9, IE8
            handleGoTop(); //handles scroll to top functionality in the footer
            handleTheme(); // handles style customer tool

            //ui component handlers
            handleDropdown(); // handle dropdowns
            handleTooltips(); // handle bootstrap tooltips

        },

        fixContentHeight: function () {
            handleSidebarAndContentHeight();
        },

        // useful function to make equal height for contacts stand side by side
        setEqualHeight: function (els) {
            var tallestEl = 0;
            els = jQuery(els);
            els.each(function () {
                var currentHeight = $(this).height();
                if (currentHeight > tallestEl) {
                    tallestColumn = currentHeight;
                }
            });
            els.height(tallestEl);
        },

        // wrapper function to scroll to an element
        scrollTo: function (el, offeset) {
            pos = el ? el.offset().top : 0;
            jQuery('html,body').animate({
                scrollTop: pos + (offeset ? offeset : 0)
            }, 'slow');
        },

        scrollTop: function () {
            App.scrollTo();
        },

        // wrapper function to  block element(indicate loading)
        blockUI: function (el, centerY) {
            var el = jQuery(el);
            el.block({
                message: '<img src="/static/image/ajax-loading.gif" align="">',
                centerY: centerY !== undefined ? centerY : true,
                css: {
                    top: '10%',
                    border: 'none',
                    padding: '2px',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: '#000',
                    opacity: 0.05,
                    cursor: 'wait'
                }
            });
        },

        // wrapper function to  un-block element(finish loading)
        unblockUI: function (el) {
            jQuery(el).unblock({
                onUnblock: function () {
                    jQuery(el).removeAttr("style");
                }
            });
        },

        // initializes uniform elements
        initUniform: function (els) {

            if (els) {
                jQuery(els).each(function () {
                    if ($(this).parents(".checker").size() === 0) {
                        $(this).show();
                        $(this).uniform();
                    }
                });
            } else {
                handleUniform();
            }

        },

        // initializes chosen dropdown
        initChosenSelect: function (els) {
            $(els).chosen({
                allow_single_deselect: true
            });
        },

        getActualVal: function (el) {
            var el = jQuery(el);
            if (el.val() === el.attr("placeholder")) {
                return "";
            }

            return el.val();
        },

        getURLParameter: function (paramName) {
            var searchString = window.location.search.substring(1),
                i, val, params = searchString.split("&");

            for (i = 0; i < params.length; i++) {
                val = params[i].split("=");
                if (val[0] === paramName) {
                    return unescape(val[1]);
                }
            }
            return null;
        },

        // check for device touch support
        isTouchDevice: function () {
            try {
                document.createEvent("TouchEvent");
                return true;
            } catch (e) {
                return false;
            }
        },

        isIE8: function () {
            return isIE8;
        },

        isRTL: function () {
            return isRTL;
        },

        getLayoutColorCode: function (name) {
            if (layoutColorCodes[name]) {
                return layoutColorCodes[name];
            } else {
                return '';
            }
        }

    };

}();