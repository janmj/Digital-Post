<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<rich:toolBar>
		<rich:dropDownMenu>
			<f:facet name="label">
				<h:panelGroup>
					<h:outputText value="Main" />
				</h:panelGroup>
			</f:facet>
			<rich:menuItem submitMode="none" value="Home" onclick="document.location.href='/MLFAdmin/main.jsf'">
				<h:outputLink value="/MLFAdmin/main.jsf"/>
			</rich:menuItem>
			<rich:menuItem submitMode="ajax" onclick="document.location.href='/MLFAdmin/logout.jsf'">
				<h:commandLink action="#{userconfBean.logout}" value="Logg ut"/>
			</rich:menuItem>
		</rich:dropDownMenu>
		<rich:dropDownMenu>
			<f:facet name="label">
				<h:panelGroup>
					<h:outputText value="Logg" />
				</h:panelGroup>
			</f:facet>
			<rich:menuItem submitMode="none" value="Logg filer" onclick="document.location.href='/MLFAdmin/listlogfiles.jsf'">
				<h:outputLink value="/MLFAdmin/listlogfiles.jsf"/>
			</rich:menuItem>
		</rich:dropDownMenu>
		<rich:dropDownMenu>
			<f:facet name="label">
				<h:panelGroup>
					<h:outputText value="Konfigurasjon" />
				</h:panelGroup>
			</f:facet>
			<rich:menuItem submitMode="none" value="Properties" onclick="document.location.href='/MLFAdmin/config/sdpconfig.jsf'">
				<h:outputLink value="/MLFAdmin/config/sdpconfig.jsf"/>
			</rich:menuItem>
		</rich:dropDownMenu>	
	</rich:toolBar>
</body>
</html>