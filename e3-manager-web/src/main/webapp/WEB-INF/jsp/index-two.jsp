<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/25
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>宜立方商城后台管理系统</title>
    <link rel="stylesheet" href="js/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/gray/easyui.css" />
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="css/e3.css" />
    <link rel="stylesheet" type="text/css" href="css/default.css" />
    <link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">宜立方商城后台管理系统</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="item-add">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    贤心
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;content('item-add')">商品管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">新增商品</a></dd>
                        <dd><a href="javascript:;">查询商品</a></dd>
                        <dd><a href="javascript:;">规格参数</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">网站内容管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">内容分类管理</a></dd>
                        <dd><a href="javascript:;">内容管理</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">索引库管理</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">solr索引库维护</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;" id="centent">内容主体区域</div>
    </div>

    <div class=layui"-footer">
        <!-- 底部固定区域 -->
        © layui.com - 底部固定区域
    </div>
</div>
<script src="js/layui/layui.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script>
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });
    function content(url){
        $.ajax({
            url:url,
            method:"post",
            data:{},
            statusCode: {
                404: function() {
                    layer.msg('错误码：404，找不到资源!', {icon: 5});
                }, 500: function(){
                    layer.msg('错误码：500，服务器内部错误!', {icon: 5});
                }
            },
            success:function(response){
                $(".layui-body #centent").html(response);
            }
        });
    }
</script>
</body>
</html>