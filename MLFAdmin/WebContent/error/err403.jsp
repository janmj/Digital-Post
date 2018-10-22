<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<html>
<head>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ikke tilgang på resurs</title>
<style>
.infoheader{
	margin-top: 100px;
	margin-left: 100px;
	width: 250px;
	font-family: vardana;
	font-size: 22pt;
	color: white;
	background-color: red;
}
.infoblock{
	margin-left: 100px;
	font-family: vardana;
	font-size: 12pt;
	color: ;
}
</style>
</head>
<body>
<div class="infoheader">
Du har ikke tilgang!
</div>
<br>

<div class="infoblock">
Du har ikke tilgang på dette innholdet.<br>
Om du mener du burde ha det, kontakt system administrator.
<br>
<br>
<input type="button" onclick="window.history.back(-1)" value=" << -- Tilbake">
</div>
</body>
</html>