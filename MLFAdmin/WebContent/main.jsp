<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Meldingsformidler admin</title>
</head>
<body>
	<f:view>
		<h:form>
			<jsp:include page="menu.jsp"/>
			<rich:panel>
				<!--   <h:commandButton action="#{userconfBean.logout}" value="Logg ut" /> -->
				<!-- <h:commandLink action="#{userconfBean.logout}" value="Logg ut" onclick="document.location.href='/MLFAdmin/logout.jsf'"/> -->
				<rich:panel id="statistikk">
					<f:facet name="header">
						<h:outputText value="Statistikk" />
					</f:facet>
					<rich:dataTable value="#{statistikklistBean.statistikkBean}" var="statistikk">
						<f:facet name="header">
							<rich:columnGroup>
								<rich:column rowspan="2">
									<rich:spacer/>
								</rich:column>
								<rich:column colspan="6">
									<h:outputText value="Nøkkeltall inneværende døgn" />
								</rich:column>
								<rich:column breakBefore="true">
									<h:outputText value="Forsendelser" />
								</rich:column>
								<rich:column>
									<h:outputText value="sist motatte" />
								</rich:column>
								<rich:column>
									<h:outputText value="Kvitteringer" />
								</rich:column>
								<rich:column>
									<h:outputText value="sist motatte" />
								</rich:column>
								<rich:column>
									<h:outputText value="Feilet utsending" />
								</rich:column>
								<rich:column>
									<h:outputText value="Sist feilet" />
								</rich:column>
							</rich:columnGroup>
						</f:facet>
						<rich:column>
							<rich:spacer/>
						</rich:column>
						<rich:column>
							<h:outputLink id="meldingerlnk" value="listforsendelser.jsf" >
								<h:outputText value="#{statistikk.antmeldinger}" />
							</h:outputLink>
						</rich:column>
						<rich:column>
							<h:outputText value="#{statistikk.lastmlddato}" />
						</rich:column>
						<rich:column>
							<h:outputLink id="kvitteringerlnk" value="listkvitteringer.jsf">
								<h:outputText value="#{statistikk.antkvitteringer}" />
							</h:outputLink>
						</rich:column>
						<rich:column>
							<h:outputText value="#{statistikk.lastkvitdato}" />
						</rich:column>
						<rich:column>
							<h:outputLink id="failedforsendelser" value="listfailedforsendelser.jsf">
								<h:outputText value="#{statistikk.antuavsent}" />
							</h:outputLink>
						</rich:column>
						<rich:column>
							<h:outputText value="#{statistikk.lastuavsent}" />
						</rich:column>
					</rich:dataTable>
				</rich:panel>
				<table>
					<tr>
						<th> <!-- Hendelser for de siste 24 timer  --></th>
						<th> <!-- Feilmeldinger for de siste 24 timer --></th>
						<!-- <th>fyll på med flere..</th> -->
					</tr>
					<tr valign="top">
						<h:panelGroup id="logtable">
							<td>
								<rich:dataTable value="#{eventslistBean.messageslist}" var="msgitem"
												rowClasses="rad1 rad2" id="messageslist" rows="25"
												columnClasses="50,100,100,100"
												onRowMouseOver="this.style.backgroundColor='B5F3FB'"
												onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
												reRender="msgds"
												>
									<f:facet name="header">
										<h:outputText value="Hendelser for de siste 24 timer" />
									</f:facet>
									<rich:column>
										<f:facet name="header">
											<h:outputText value="LogId" />
										</f:facet>
										<h:outputText value="#{msgitem.logid}" />
									</rich:column>
									<rich:column sortBy="#{msgitem.logtime}">
										<f:facet name="header">
											<h:outputText value="Tidspunkt" />
										</f:facet>
										<h:outputText value="#{msgitem.logtime}" />
									</rich:column>
									<rich:column>
										<f:facet name="header">
											<h:outputText value="Melding" />
										</f:facet>
										<h:outputText value="#{msgitem.message}" />
									</rich:column>
									<rich:column>
										<f:facet name="header">
											<h:outputText value="Conversationid" />
										</f:facet>
										<h:outputLink id="maineventcidlnk" value="listconversationid.jsf">
											<f:param name="conversationid" value="#{msgitem.conversationid}"/>
											<h:outputText value="#{msgitem.conversationid}" />
										</h:outputLink>
									</rich:column>
									<f:facet name="footer">
										<rich:datascroller id="msgds"/>
									</f:facet>
								</rich:dataTable>
							</td>
							<td>
								<rich:dataTable value="#{eventslistBean.errorslist}" var="erritem"
												rowClasses="rad1 rad2" id="errorslist" rows="25"
												columnClasses="50,100,100,100"
												onRowMouseOver="this.style.backgroundColor='B5F3FB'"
												onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
												reRender="errds"
												>
								<f:facet name="header">
									<h:outputText value="Feilmeldinger for de siste 24 timer" />
								</f:facet>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Logid" />
									</f:facet>
									<h:outputText value="#{erritem.logid}" />
								</rich:column>
								<rich:column sortBy="#{erritem.logtime}">
									<f:facet name="header">
										<h:outputText value="Tidspunkt" />
									</f:facet>
									<h:outputText value="#{erritem.logtime}" />
								</rich:column>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Melding" />
									</f:facet>
									<h:outputText value="#{erritem.error}" />
									<rich:toolTip showEvent="onclick">
										<h:outputText value="#{erritem.message}" />
									</rich:toolTip>
								</rich:column>
								<rich:column>
									<f:facet name="header">
										<h:outputText value="Conversationid" />
									</f:facet>
									<h:outputLink id="mainerrcidlnk" value="listconversationid.jsf">
										<f:param name="conversationid" value="#{erritem.conversationid}"/>
										<h:outputText value="#{erritem.conversationid}" />
									</h:outputLink>
								</rich:column>
								<f:facet name="footer">
									<rich:datascroller id="errds"/>
								</f:facet>
								</rich:dataTable>
							</td>
						</h:panelGroup>
					</tr>
				</table>
			</rich:panel>
		</h:form>
	</f:view>	
</body>
</html>