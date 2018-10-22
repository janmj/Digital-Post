package no.si.sdp.beans;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import no.difi.sdp.client.domain.digital_post.Sikkerhetsnivaa;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="digitalpost")

public class DigitalpostBean implements Serializable{
	
	private static final long serialVersionUID = 4973215243186196062L;
	
	@XmlAttribute(required=true)
	private Date virkningsdato;
	@XmlAttribute(required=false)
	private boolean aapningskvittering = false;
	@XmlAttribute(required=false)
	private Sikkerhetsnivaa  sikkerhetsnivaa = Sikkerhetsnivaa.NIVAA_4;
	@XmlAttribute(required=true)
	private String ikkeSensitivTittel;
	
	public DigitalpostBean() {
	}

	public DigitalpostBean(Date virkningsdato, boolean aapningskvittering, Sikkerhetsnivaa sikkerhetsnivaa, String ikkeSensitivTittel) {
		super();
		this.virkningsdato = virkningsdato;
		this.aapningskvittering = aapningskvittering;
		this.sikkerhetsnivaa = sikkerhetsnivaa;
		this.ikkeSensitivTittel = ikkeSensitivTittel;
	}

	public DigitalpostBean(Date virkningsdato, String ikkeSensitivTittel) {
		super();
		this.virkningsdato = virkningsdato;
		this.ikkeSensitivTittel = ikkeSensitivTittel;
	}

	public Date getVirkningsdato() {
		return virkningsdato;
	}

	public void setVirkningsdato(Date virkningsdato) {
		this.virkningsdato = virkningsdato;
	}

	public boolean isAapningskvittering() {
		return aapningskvittering;
	}

	public void setAapningskvittering(boolean aapningskvittering) {
		this.aapningskvittering = aapningskvittering;
	}

	public Sikkerhetsnivaa getSikkerhetsnivaa() {
		return sikkerhetsnivaa;
	}

	public void setSikkerhetsnivaa(Sikkerhetsnivaa sikkerhetsnivaa) {
		this.sikkerhetsnivaa = sikkerhetsnivaa;
	}

	public String getIkkeSensitivTittel() {
		return ikkeSensitivTittel;
	}

	public void setIkkeSensitivTittel(String ikkeSensitivTittel) {
		this.ikkeSensitivTittel = ikkeSensitivTittel;
	}	

}
