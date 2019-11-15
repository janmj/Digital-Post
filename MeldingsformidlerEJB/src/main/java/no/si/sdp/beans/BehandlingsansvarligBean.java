package no.si.sdp.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="behandlingsansvarlig")
public class BehandlingsansvarligBean implements Serializable{

	private static final long serialVersionUID = 2613835945880898413L;
	
	@XmlElement(required = true)
	private String organisasjonsnummer;
	@XmlElement(required = true)
	private String avsenderIdentifikator;
	@XmlElement(required = false)
	private String fakturaReferanse;
	
	
	
	public BehandlingsansvarligBean(String organisasjonsnummer, String avsenderIdentifikator, String fakturaReferanse) {
		super();
		this.organisasjonsnummer = organisasjonsnummer;
		this.avsenderIdentifikator = avsenderIdentifikator;
		this.fakturaReferanse = fakturaReferanse;
	}
	
	public BehandlingsansvarligBean(String organisasjonsnummer, String avsenderIdentifikator) {
		super();
		this.organisasjonsnummer = organisasjonsnummer;
		this.avsenderIdentifikator = avsenderIdentifikator;
	}

	public String getOrganisasjonsnummer() {
		return organisasjonsnummer;
	}
	public void setOrganisasjonsnummer(String organisasjonsnummer) {
		this.organisasjonsnummer = organisasjonsnummer;
	}
	public String getAvsenderIdentifikator() {
		return avsenderIdentifikator;
	}
	public void setAvsenderIdentifikator(String avsenderIdentifikator) {
		this.avsenderIdentifikator = avsenderIdentifikator;
	}
	public String getFakturaReferanse() {
		return fakturaReferanse;
	}
	public void setFakturaReferanse(String fakturaReferanse) {
		this.fakturaReferanse = fakturaReferanse;
	}
	
	
}
