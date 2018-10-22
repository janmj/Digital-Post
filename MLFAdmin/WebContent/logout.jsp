<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>logout</title>
</head>
<style>
.infoblockheader{
	width: 160px;
	margin-top: 100px;
	margin-left: 100px;
	font-family: verdana;
	font-size: 16pt;
	color:white;
	background-color: red;
}
.infoblock{
	width: 800px;
	margin-top: 10px;
	margin-left: 100px;
	font-family: verdana;
	font-size: 12pt;
}
</style>
<body>

<f:view >
	<h:outputText value="#{userconfBean.logout()}"/>
	<div class="infoblockheader">
	Du er nå logget ut
	</div>
	<div class="infoblock">
	Av sikkerhetsgrunner bør du nå lukke dette vinduet.	
	Evt. kan du logge på igjen <a href="/MLFAdmin/">her</a>
	</div>
</f:view>
</body>
</html>