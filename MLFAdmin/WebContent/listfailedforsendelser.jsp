<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<f:view>
	<h:form>
		<jsp:include page="menu.jsp"/>
		<rich:panel>
			<rich:dataTable value="#{failedForsendelseListBean.failedForsendelserlist}" var="forsendelseitem"
							rowClasses="rad1 rad2" id="forsendelseslist" rows="25"
							columnClasses="50,100,100,100"
							onRowMouseOver="this.style.backgroundColor='B5F3FB'"
							onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
							>
				<f:facet name="header">
					<rich:columnGroup>
						<rich:column>
							<h:outputText value="Id" />
						</rich:column>
						<rich:column>
							<h:outputText value="ConversationId" />
						</rich:column>
						<rich:column>
							<h:outputText value="Status" />
						</rich:column>
						<rich:column>
							<h:outputText value="Opprettet" />
						</rich:column>
						<rich:column>
							<h:outputText value="Endret" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
					<rich:column>
						<h:outputText value="#{forsendelseitem.forsendelsesid}" />
					</rich:column>
					<rich:column>
						<h:outputLink id="cidlnkid" value="listfailedforsendelser.jsf">
						<f:param name="conversationid" value="#{forsendelseitem.conversationid}"/>
							<h:outputText value="#{forsendelseitem.conversationid}" />
							<a4j:support event="onclick" disableDefault="true" action="#{eventslistBean.loadCidList}" reRender="messageslist"/>
						</h:outputLink>
					</rich:column>
					<rich:column>
						<h:outputText value="#{forsendelseitem.statusdesc}" />
					</rich:column>
					<rich:column>
						<h:outputText value="#{forsendelseitem.odato}" />
					</rich:column>
					<rich:column>
						<h:outputText value="#{forsendelseitem.edato}" />
					</rich:column>
					<f:facet name="footer">
						<rich:datascroller ajaxSingle="false"/>
					</f:facet>
			</rich:dataTable>
		</rich:panel>
		<rich:panel>
			<rich:dataTable value="#{eventslistBean.cidlist}" var="ciditem"
									rowClasses="rad1 rad2" id="messageslist" rows="25"
									columnClasses="50,100,100,100"
									onRowMouseOver="this.style.backgroundColor='B5F3FB'"
									onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"					
									>
				<f:facet name="header">
					<h:outputText value="Log for conversationid " />
				</f:facet>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="ID" />
					</f:facet>
					<h:outputText value="#{ciditem.logid}" />
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Tidspunkt" />
					</f:facet>
					<h:outputText value="#{ciditem.logtime}" />
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Melding" />
					</f:facet>
					<h:outputText value="#{ciditem.message}" />
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="Feil" />
					</f:facet>
					<h:outputText value="#{ciditem.error}" />
				</rich:column>
				<rich:column>
					<f:facet name="header">
						<h:outputText value="conversationid" />
					</f:facet>
					<h:outputText value="#{ciditem.conversationid}" />
				</rich:column>				
			</rich:dataTable>
		</rich:panel>
	</h:form>
</f:view>
</body>
</html>