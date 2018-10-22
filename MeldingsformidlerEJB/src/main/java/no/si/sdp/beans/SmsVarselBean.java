package no.si.sdp.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="sdpsmsvarsel")
public class SmsVarselBean implements Serializable{
	
	private static final long serialVersionUID = 7260204381320486449L;
	
	@XmlAttribute(required=true)
	private String mobilnummer;
	@XmlAttribute(required=true)
	private String varseltekst;
	@XmlAttribute(required=true)
	private List<String> dageretter;
	
	public SmsVarselBean() {
		// TODO Auto-generated constructor stub
	}

	public SmsVarselBean(String mobilnummer, String varseltekst, List<String> dageretter) {
		super();
		this.mobilnummer = mobilnummer;
		this.varseltekst = varseltekst;
		this.dageretter = dageretter;
	}

	public String getMobilnummer() {
		return mobilnummer;
	}

	public void setMobilnummer(String mobilnummer) {
		this.mobilnummer = mobilnummer;
	}

	public String getVarseltekst() {
		return varseltekst;
	}

	public void setVarseltekst(String varseltekst) {
		this.varseltekst = varseltekst;
	}

	public List<String> getDageretter() {
		return dageretter;
	}

	public void setDageretter(List<String> dageretter) {
		this.dageretter = dageretter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
