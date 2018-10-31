<html>
<head>
<meta charset="utf-8">
<title>FreeMaker Test</title>
</head>
<body>
	<#-- head指令 -->
	<#include 'head.ftl'>
	<#-- 我是注解，不会输出 -->
	<!-- htm注解 -->
	${name},你好 ${message}
	
	<#-- assign 定义简单类型 -->
	<#assign linkman="aqqje">
	<#-- assign 定义对象类型 -->
	<#assign info={'mobile':'13548522461','addr':'中国湖南'}>
	联系人：${linkman},手机：${info.mobile},地址：${info.addr}<br>
	
	<#-- if指令 -->
	<#if success=true>
		您已实名通过！<br>
	<#else>
		您未实名通过！<br>
	</#if>
	
	<#-- list指令 -->
	----商品列表----
	<#list goodList as good>
	${good_index+1} 商品名称：${good.name} 商品价格：${good.price}<br>
	</#list>
	
	<#-- 内建函数 -->
	
	<#-- 获取集合的大小 -->
	一共${goodList?size}条记录<br>
	
	<#-- 字符串转为JSON对象 -->
	<#assign user="{'bank':'工商银行','accout':'154852456956325485'}">
	<#assign data=user?eval>
	开户行：${data.bank},帐户：${data.accout}<br>
	
	<#-- 日期格式化 -->
	当前日期:${today?date}<br>
	当前时间:${today?time}<br>
	当前日期+时间:${today?datetime}<br>
	日期格式化：${today?string("yyyy年MM月")}<br>
	
	<#-- 数据格式 -->
	数据格式(默认)：${point}<br>
	数据格式(转化)：${point?c}<br>
	
	<#-- 空值处理运算符  ??-->
	<#if flag??>
		flag存在 ${flag}
	<#else>
		flag不存在
	</#if>
	
	${test ! '00000'}
</body>
</html>