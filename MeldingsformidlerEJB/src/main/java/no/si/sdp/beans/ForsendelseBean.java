package no.si.sdp.beans;

import java.io.Serializable;

import javax.validation.Validator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

//import no.difi.begrep.DifiPerson;
import no.difi.begrep.Person;
import no.difi.sdp.client.domain.Prioritet;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="sdpforsendelse", propOrder={"spraakkode","prioritet","mottaker","digitalpost","dokument","epostvarsel","smsvarsel","conversationid","behandlingsansvarlig"})
@XmlRootElement(namespace="urn:no.si.sdp.mlf")
public class ForsendelseBean implements Serializable {
	
	private static final long serialVersionUID = 8949741044312794128L;
	@XmlElement(required = false)
	private String spraakkode = "NO";
	@XmlElement(required=false)
	private Prioritet prioritet = Prioritet.NORMAL;
	@XmlElement(required = true)
	private Person mottaker;
	//private Person mottaker;
	@XmlElement(required=true)
	private DigitalpostBean digitalpost;
	@XmlElement(required = true)
	private DokumentBean dokument;
	@XmlElement(required = false)
	private EpostVarselBean epostvarsel;
	@XmlElement(required = false)
	private SmsVarselBean smsvarsel;
	@XmlElement(required = false)
	private String conversationid="";
	@XmlElement(required = false)
	private BehandlingsansvarligBean behandlingsansvarlig;
	
	public ForsendelseBean() {
		super();
	}

	public String getSpraakkode() {
		return spraakkode;
	}

	public void setSpraakkode(String spraakkode) {
		this.spraakkode = spraakkode;
	}

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

	public Person getMottaker() {
		return mottaker;
	}


	public void setMottaker(Person mottaker) {
		this.mottaker = mottaker;
	}

	public DigitalpostBean getDigitalpost() {
		return digitalpost;
	}

	public void setDigitalpost(DigitalpostBean digitalpost) {
		this.digitalpost = digitalpost;
	}
	
	public DokumentBean getDokument() {
		return dokument;
	}

	public void setDokument(DokumentBean dokument) {
		this.dokument = dokument;
	}

	public EpostVarselBean getEpostvarsel() {
		return epostvarsel;
	}

	public void setEpostvarsel(EpostVarselBean epostvarsel) {
		this.epostvarsel = epostvarsel;
	}

	public SmsVarselBean getSmsvarsel() {
		return smsvarsel;
	}

	public void setSmsvarsel(SmsVarselBean smsvarsel) {
		this.smsvarsel = smsvarsel;
	}

	public BehandlingsansvarligBean getBehandlingsansvarlig() {
		return behandlingsansvarlig;
	}

	public void setBehandlingsansvarlig(BehandlingsansvarligBean behandlingsansvarlig) {
		this.behandlingsansvarlig = behandlingsansvarlig;
	}
	
}
