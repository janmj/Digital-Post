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
		<rich:panel>
			<rich:dataTable value="#{kvitteringsListBean.kvitteringslist}" var="kvitteringsitem"
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
						<h:outputText value="#{kvitteringsitem.kvitteringsid}" />
						<rich:toolTip showEvent="onmouseover" style="width:400px">
							<h:outputText value="#{kvitteringsitem.kvittering}" />
						</rich:toolTip>
					</rich:column>
					<rich:column>
						<h:outputLink id="cidlnkid" value="listconversationid.jsf">
						<f:param name="conversationid" value="#{kvitteringsitem.conversationid}"/>
							<h:outputText value="#{kvitteringsitem.conversationid}" />
						</h:outputLink>
					</rich:column>
					<rich:column>
						<h:outputText value="#{kvitteringsitem.statusdesc}" />
					</rich:column>
					<rich:column>
						<h:outputText value="#{kvitteringsitem.odato}" />
					</rich:column>
					<rich:column>
						<h:outputText value="#{kvitteringsitem.edato}" />
					</rich:column>
					<f:facet name="footer">
						<rich:datascroller ajaxSingle="false"/>
					</f:facet>
			</rich:dataTable>
		</rich:panel>
	</h:form>
</f:view>
</body>
</html>