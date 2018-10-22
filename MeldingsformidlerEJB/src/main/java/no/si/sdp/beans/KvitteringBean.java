package no.si.sdp.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import no.difi.sdp.client.domain.kvittering.Feil.Feiltype;
import no.difi.sdp.client.domain.kvittering.VarslingFeiletKvittering.Varslingskanal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Kvittering")
@XmlRootElement
public class KvitteringBean implements Serializable {
	private static final long serialVersionUID = 7479983553066026693L;
	
	@XmlElement(required = true)
	private String conversationid;
	@XmlElement(required = true)
	private Kvitteringstyper type; //Type kvittering
	@XmlElement(required = true)
	private String docscopeinstanceIdentifier;
	@XmlElement(required = true)
	private String bizscopeinstanceIdentifier;
	@XmlElement(required = true)
	private Date creationdatetime;
	@XmlElement(required = true)
	private Date tidspunkt;
	@XmlElement(required = true)
	private String avsenderorgnr;
	/**
	 * For varslingfeilet kvittering
	 */
	@XmlElement(required = false)
	private String beskrivelse;
	@XmlElement(required = false)
	private Varslingskanal varslingskanal;
	/**
	 * For feilmeldinger
	 */
	@XmlElement(required = false)
	private Feiltype feiltype;
	//@XmlElement(required = false)
	//private String feilkode;
	@XmlElement(required = false)
	private String detaljer;	

	public enum Kvitteringstyper{
		Leveringskvittering("Leveringskvittering"),		
		Feil("Feil"),
		VarslingfeiletKvittering("VarslingfeiletKvittering"),
		Åpningskvittering("Åpningskvittering");
		
		private final String value;
		
		private Kvitteringstyper(String value){
			this.value = value;
		}
		public String getValue(){
			return this.value;
		}
	}
	
	public KvitteringBean() {
		
	}

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

	public Kvitteringstyper getType() {
		return type;
	}

	public void setType(Kvitteringstyper type) {
		this.type = type;
	}

	public String getDocscopeinstanceIdentifier() {
		return docscopeinstanceIdentifier;
	}

	public void setDocscopeinstanceIdentifier(String docscopeinstanceIdentifier) {
		this.docscopeinstanceIdentifier = docscopeinstanceIdentifier;
	}

	public String getBizscopeinstanceIdentifier() {
		return bizscopeinstanceIdentifier;
	}

	public void setBizscopeinstanceIdentifier(String bizscopeinstanceIdentifier) {
		this.bizscopeinstanceIdentifier = bizscopeinstanceIdentifier;
	}

	public Date getCreationdatetime() {
		return creationdatetime;
	}

	public void setCreationdatetime(Date creationdatetime) {
		this.creationdatetime = creationdatetime;
	}

	public Date getTidspunkt() {
		return tidspunkt;
	}

	public void setTidspunkt(Date tidspunkt) {
		this.tidspunkt = tidspunkt;
	}

	public String getAvsenderorgnr() {
		return avsenderorgnr;
	}

	public void setAvsenderorgnr(String avsenderorgnr) {
		this.avsenderorgnr = avsenderorgnr;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}


	public Varslingskanal getVarslingskanal() {
		return varslingskanal;
	}

	public void setVarslingskanal(Varslingskanal varslingskanal) {
		this.varslingskanal = varslingskanal;
	}

	public Feiltype getFeiltype() {
		return feiltype;
	}

	public void setFeiltype(Feiltype feiltype) {
		this.feiltype = feiltype;
	}
	/*
	public String getFeilkode() {
		return feilkode;
	}

	public void setFeilkode(String feilkode) {
		this.feilkode = feilkode;
	}
	*/
	public String getDetaljer() {
		return detaljer;
	}

	public void setDetaljer(String detaljer) {
		this.detaljer = detaljer;
	}	
}
