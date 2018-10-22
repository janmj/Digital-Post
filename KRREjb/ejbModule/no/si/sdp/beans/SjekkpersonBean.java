package no.si.sdp.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="sjekkpersonbean")
public class SjekkpersonBean implements Serializable{
	
	private static final long serialVersionUID = -6739497533252938187L;

	@XmlAttribute(required=true)
	private String personidentifikator;
	@XmlAttribute(required=true)
	private Boolean status;
	
	public SjekkpersonBean() {
		
	}
	
	public SjekkpersonBean(String personidentifikator, Boolean status) {
		super();
		this.personidentifikator = personidentifikator;
		this.status = status;
	}
	
	public String getPersonidentifikator() {
		return personidentifikator;
	}
	
	public void setPersonidentifikator(String personidentifikator) {
		this.personidentifikator = personidentifikator;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
