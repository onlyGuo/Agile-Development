<!doctype html>
<html lang="zh-cn">
<head>
	<meta charset="UTF-8">
	<title>登录-敏捷开发系统</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="stylesheet" href="./css/font.css">
	<link rel="stylesheet" href="./css/xadmin.css">
    <script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="./lib/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/xadmin.js"></script>

</head>
<body class="login-bg">
    
    <div class="login">
        <div class="message">敏捷开发系统1.0</div>
        <div id="darkbannerwrap"></div>
        
        <form class="layui-form" >
            <input name="username" placeholder="用户名"  type="text" class="layui-input" >
            <hr class="hr15">
            <input name="password" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <button class="layui-btn" style="width: 100%;font-size: 18px;line-height: 24px;height: 45px;" type="button">登录</button>
            <hr class="hr20" >
        </form>
    </div>

	<script type="text/javascript" src="/js/ajax/core.js"></script>
	<script type="text/javascript" src="/js/layerAjaxMsg/default.js"></script>
    <script>
    
    	$(function(){
    		$(".layui-form .layui-btn").click(function(){
    			var param = {
    					account:$(".layui-form input[name='username']").val(),
    					password:$(".layui-form input[name='password']").val()
    			};
        		H.post("doLogin", param, function(res){
        		    setCookie("auth_token", res.responseBody.token, 1);
        			location.href = "/project/";
        		});
        	});
    	});
    </script>
</body>
</html>