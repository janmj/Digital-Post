<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List loggfil</title>
</head>
<body>
<style type="text/css">
	.scrollbody {
		overflow: auto;
	}
</style>

<f:view>
	<h:form>
		<jsp:include page="menu.jsp"/>
		<rich:panel id="logfile" bodyClass="scrollbody">
			<f:facet name="header">
				<h:outputText value="#{logFileBean.logfilepath}" />
			</f:facet>
				<pre>
					<h:outputText value="#{logFileBean.logfile}" />
				</pre>
		</rich:panel>
	</h:form>
</f:view>
</body>
</html>