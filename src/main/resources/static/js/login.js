var Login = function () {

    return {

        init: function () {

            /**
             * 登录表单验证
             */
            $('.login-form').validate({
                errorElement: 'span',
                errorClass: 'help-inline',
                focusInvalid: false,
                rules: {
                    username: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    remember: {
                        required: false
                    }
                },

                messages: {
                    username: {
                        required: "用户名不能为空"
                    },
                    password: {
                        required: "密码不能为空"
                    }
                },
                //当表单提交时，如果错误，弹出提示信息
                invalidHandler: function (event, validator) {
                    $('.alert-error', $('.login-form')).show();
                },
                //高亮显示Input输入框，并添加错误样式
                highlight: function (element) {
                    $(element).closest('.control-group').addClass('error');
                },
                //验证通过后移除错误样式
                success: function (label) {
                    label.closest('.control-group').removeClass('error');
                    label.remove();
                },
                //指定错误信息的显示位置
                errorPlacement: function (error, element) {
                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
                }
            });

            /**
             * 点击Enter按键时，验证通过后提交表单，否则返回登陆页面
             */
            $('.login-form input').keypress(function (e) {
                if (e.which == 13) {
                    if ($('.login-form').validate().form()) {
                        window.location.href = "/login";
                    }
                    return false;
                }
            });

            /**
             * 忘记密码的表单验证
             */
            $('.forget-form').validate({
                errorElement: 'label',
                errorClass: 'help-inline',
                focusInvalid: false,
                ignore: "",
                rules: {
                    email: {
                        required: true,
                        email: true
                    }
                },

                messages: {
                    email: {
                        required: "邮箱不能为空"
                    }
                },

                invalidHandler: function (event, validator) {
                },
                //高亮显示input输入框，添加错误样式
                highlight: function (element) {
                    $(element).closest('.control-group').addClass('error');
                },
                //验证成功后移除错误信息
                success: function (label) {
                    label.closest('.control-group').removeClass('error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
                }
            });

            /**
             * 点击Enter按键时，验证通过后提交表单，否则返回登陆页面
             */
            $('.forget-form input').keypress(function (e) {
                if (e.which == 13) {
                    if ($('.forget-form').validate().form()) {
                        window.location.href = "/login";
                    }
                    return false;
                }
            });

            /**
             * 点击忘记密码按钮时，隐藏登录表单，显示忘记密码表单
             */
            jQuery('#forget-password').click(function () {
                jQuery('.login-form').hide();
                jQuery('.forget-form').show();
            });

            /**
             * 点击返回按钮时，显示登录表单，隐藏忘记密码表单
             */
            jQuery('#back-btn').click(function () {
                jQuery('.login-form').show();
                jQuery('.forget-form').hide();
            });

            /**
             * 注册表单的验证
             */
            $('.register-form').validate({
                errorElement: 'label',
                errorClass: 'help-inline',
                focusInvalid: false,
                ignore: "",
                rules: {
                    username: {
                        required: true
                    },
                    password: {
                        required: true
                    },
                    rpassword: {
                        equalTo: "#register_password"
                    },
                    email: {
                        required: true,
                        email: true
                    }
                },

                messages: {
                    username: {
                        required: "用户名不能为空"
                    },
                    password: {
                        required: "密码不能为空"
                    },
                    rpassword: {
                        equalTo: "确认密码与输入的密码不一致"
                    },
                    email: {
                        required: "邮箱不能为空",
                        email: "请输入正确的邮箱格式"
                    }
                },
                //在表单提交时，隐藏错误信息提示
                invalidHandler: function (event, validator) {
                },
                //高亮显示input输入框，添加错误样式
                highlight: function (element) {
                    $(element).closest('.control-group').addClass('error');
                },
                //验证成功后移除错误信息
                success: function (label) {
                    label.closest('.control-group').removeClass('error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
                }
            });

            /**
             * 点击创建账户时，显示注册表单，隐藏登录表单
             */
            jQuery('#register-btn').click(function () {
                jQuery('.login-form').hide();
                jQuery('.register-form').show();
            });

            /**
             * 点击注册表单中的返回按钮时，隐藏注册表单，显示登录表单
             */
            jQuery('#register-back-btn').click(function () {
                jQuery('.login-form').show();
                jQuery('.register-form').hide();
            });

            /**
             * 定义图片切换规则
             */
            $.backstretch([
                "static/image/bg/1.jpg",
                "static/image/bg/2.jpg",
                "static/image/bg/3.jpg",
                "static/image/bg/4.jpg"
            ], {
                fade: 1000,
                duration: 6000
            });
        }

    };

}();