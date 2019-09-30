<html>
<head>
	<meta charset="utf-8">
	<title>Freemarker入门小DEMO </title>
</head>
<body>
<#include "head.ftl">
<#--我只是一个注释，我不会有任何输出  -->
${name},你好。${message}
<br>
<#assign linkman="fangchenyong">
联系人：${linkman}
<br>
<#assign info={"mobile":"177064401022",'address':'杭州市西湖区'} >
联系方式：${info.mobile}
<br>
地址：${info.address}
<br>
<#if success=true>
  你已通过实名认证
<#else>  
  你未通过实名认证
</#if>
<br>
----商品价格表----<br>
<#list goodsList as goods>
  ${goods_index+1} 商品名称： ${goods.name} 价格：${goods.price}<br>
</#list>
共  ${goodsList?size}  条记录
<br>
<#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
	<#assign data=text?eval />
	开户行：${data.bank}  账号：${data.account}
<br>
当前日期：${today?date} <br>
当前时间：${today?time} <br>   
当前日期+时间：${today?datetime} <br>        
日期格式化：  ${today?string("yyyy年MM月")}<br>
累计积分：${point}<br>
累计积分：${point?c}<br>
<#if aaa??>
  aaa变量存在
<#else>
  aaa变量不存在
</#if>
<br>
FreeMarker表达式中完全支持算术运算,FreeMarker支持的算术运算符包括:+, - , * , / , %
<br>
逻辑运算符有如下几个: <br>
逻辑与:&& <br>
逻辑或:|| <br>
逻辑非:! <br>
逻辑运算符只能作用于布尔值,否则将产生错误 <br>


表达式中支持的比较运算符有如下几个: <br>
1  =或者==:判断两个值是否相等. <br>
2  !=:判断两个值是否不等. <br>
3  >或者gt:判断左边值是否大于右边值 <br>
4  >=或者gte:判断左边值是否大于等于右边值 <br>
5  <或者lt:判断左边值是否小于右边值 <br>
6  <=或者lte:判断左边值是否小于等于右边值 <br>
注意:  =和!=可以用于字符串,数值和日期来比较是否相等,但=和!=两边必须是相同类型的值,否则会产生错误,而且FreeMarker是精确比较,"x","x ","X"是不等的.其它的运行符可以作用于数字和日期,但不能作用于字符串,大部分的时候,使用gt等字母运算符代替>会有更好的效果,因为 FreeMarker会把>解释成FTL标签的结束字符,当然,也可以使用括号来避免这种情况
<br>
 ${aaa!'-'}

</body>
</html>
