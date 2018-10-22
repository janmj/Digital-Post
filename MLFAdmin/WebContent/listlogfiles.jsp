<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<f:view>
	<h:form>
		<jsp:include page="menu.jsp"/>
		<rich:panel id="logfileslist">
			<f:facet name="header">
				<h:outputText value="Logg filer" />
			</f:facet>
			<rich:dataList var="lfiles" value="#{logListBean.logfileslist}">
				<h:outputLink id="listlogfile" value="listlogfile.jsf">
				<f:param name="logfilepath" value="#{lfiles.logfilepath}"/>
					<h:outputText value="#{lfiles.logfilename}" />
				</h:outputLink>
			</rich:dataList>
		</rich:panel>
	</h:form>
</f:view>
</body>
</html>