package no.si.sdp.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="sdpDokument")
public class DokumentBean implements Serializable{
	
	private static final long serialVersionUID = 8383445065793988708L;
	
	@XmlElement(required = true)
	private String beskyttettittel;
	@XmlElement(required = true)
	private String mimetype;
	@XmlElement(required = true)
	private byte[] dokument;
	
	public DokumentBean() {
		super();
	}
	
	public DokumentBean(String beskyttettittel, String mimetype, byte[] dokument) {
		super();
		this.beskyttettittel = beskyttettittel;
		this.mimetype = mimetype;
		this.dokument = dokument;
	}


	public String getBeskyttettittel() {
		return beskyttettittel;
	}

	public void setBeskyttettittel(String beskyttettittel) {
		this.beskyttettittel = beskyttettittel;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public byte[] getDokument() {
		return dokument;
	}

	public void setDokument(byte[] dokument) {
		this.dokument = dokument;
	}

	
}
