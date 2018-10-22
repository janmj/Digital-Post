<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="rich" uri="http://richfaces.org/rich"%>
<%@ taglib prefix="a4j" uri="http://richfaces.org/a4j" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SDP admin</title>
</head>
<body>
<f:view>
	<h:form>
	<jsp:include page="../menu.jsp"/>
		<h:panelGrid columns="2" columnClasses="top,top">
			<rich:dataTable value="#{configlistBean.propertieslist}" var="propitem"
							rowClasses="rad1 rad2" id="propertieslist" rows="25"
							columnClasses="50,100,100,100"
							onRowMouseOver="this.style.backgroundColor='B5F3FB'"
							onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
							>
				<f:facet name="header">
					<rich:columnGroup>
						<rich:column>
							<h:outputText value="ID" />
						</rich:column>
						<rich:column>
							<h:outputText value="Property" />
						</rich:column>
						<rich:column>
							<h:outputText value="Verdi" />
						</rich:column>
						<rich:column>
							<h:outputText value="Beskrivelse" />
						</rich:column>
						<rich:column>
							<h:outputText value="Opprettet" />
						</rich:column>
						<rich:column>
							<h:outputText value="Endret" />
						</rich:column>
						<rich:column>
							<h:outputText value="---" />
						</rich:column>
					</rich:columnGroup>
				</f:facet>
				<rich:column>
					<h:outputText value="#{propitem.id}" />
				</rich:column>
				<rich:column>
					<rich:inplaceInput value="#{propitem.property}"
									   layout="block"
									   required="true"
									   requiredMessage="Må fylles ut!"
									   editEvent="ondblclick"
									   showControls="true"
									   valueChangeListener="#{propitem.processPropertyChange}"
									   >
						<a4j:support event="onviewactivated" reRender="propertieslist"/>
					</rich:inplaceInput>
				</rich:column>
				<rich:column>
					<rich:inplaceInput value="#{propitem.value}"
									   layout="block"
									   required="true"
									   requiredMessage="Må fylles ut!"
									   editEvent="ondblclick"
									   showControls="true"
									   valueChangeListener="#{propitem.processPropertyValueChange}"
									   >
						<a4j:support event="onviewactivated" reRender="propertieslist"/>
					</rich:inplaceInput>
				</rich:column>
				<rich:column>
					<rich:inplaceInput value="#{propitem.beskrivelse}"
									   layout="block"
									   required="true"
									   requiredMessage="Må fylles ut!"
									   editEvent="ondblclick"
									   showControls="true"
									   valueChangeListener="#{propitem.processPropertyDescriptionChange}"									   
									   >
						<a4j:support event="onviewactivated" reRender="propertieslist"/>
					</rich:inplaceInput>
				</rich:column>
				<rich:column>
					<h:outputText value="#{propitem.odato}"/>
				</rich:column>
				<rich:column>
					<h:outputText value="#{propitem.edato}"/>
				</rich:column>
				<rich:column>
					<a4j:commandButton action="#{propitem.deleteproperty}" value="Slett" reRender="propertieslist" />
				</rich:column>
				
				<f:facet name="footer">
					<rich:datascroller ajaxSingle="false"/>
				</f:facet>
			</rich:dataTable>
			<rich:panel id="meldinger">
				<f:facet name="header">
					<h:outputText value="Meldinger:" />
				</f:facet>
				<rich:messages style="color:red">
					<h:outputText value="..."  rendered="#{facesContext.maximumSeverity==null}"/>
				</rich:messages>
			</rich:panel>
		</h:panelGrid>
	</h:form>
	<h:form>
		<rich:panel style="width:400px">
		<f:facet name="header">
			<h:outputText value="Opprett ny property" />
		</f:facet>
			<h:panelGrid columns="2" columnClasses="top,top">
				<h:outputText value="Property:" styleClass="inputlbl"/>
				<h:inputText required="true" requiredMessage="Du må fylle inn navn på property!" value="#{propertyBean.property}" >
					<f:validateLength minimum="2" maximum="250"/>
				</h:inputText>
				<h:outputText value="Verdi:" styleClass="inputlbl"/>
				<h:inputText required="true" requiredMessage="Du må fylle inn verdi!" value="#{propertyBean.value}" >
					<f:validateLength minimum="2" maximum="250"/>
				</h:inputText>
				<h:outputText value="Beskrivelse:" styleClass="inputlbl"/>
				<h:inputText required="true" requiredMessage="Du må fylle inn beskrivelse!" value="#{propertyBean.beskrivelse}" >
					<f:validateLength minimum="2" maximum="250"/>
				</h:inputText>
				<a4j:commandButton value="Lagre" reRender="propertieslist" action="#{propertyBean.saveProperty}"/>
			</h:panelGrid>
		</rich:panel>
	</h:form>
</f:view>
</body>
</html>