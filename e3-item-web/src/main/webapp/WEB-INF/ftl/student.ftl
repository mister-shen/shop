<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>freemarker测试</title>
</head>
<body>
    取学生的信息：<br />
    <label>姓名:</label>${stu.name!"姓名为空"}<br />
    <label>年龄:</label>${stu.age}<br />
    <label>生日:</label>${stu.date?string("yyyy/MM/dd")}<br />
    <label>生日:</label>${stu.date?datetime}<br />
    <label>地址:</label>${stu.addr}<br />

    <table border="1">
        <tr>
            <td>序号</td>
            <td>学号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>生日</td>
            <td>家庭住址</td>
        </tr>
        <#list stuList as student>
            <#if student_index % 2 == 0>
                <tr bgcolor="silver">
            <#else>
                <tr bgcolor="red">
            </#if>
                <td>${student_index+1}</td>
                <td>${student.id}</td>
                <td>${student.name!"姓名为空"}</td>
                <td>${student.age}</td>
                <td>${student.date?string("yyyy/MM/dd")}</td>
                <td>${student.addr}</td>
            </tr>
        </#list>
    </table>

    <#include "hello.ftl" />
</body>
</html>