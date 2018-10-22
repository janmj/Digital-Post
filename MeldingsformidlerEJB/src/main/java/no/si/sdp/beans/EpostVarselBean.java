package no.si.sdp.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="sdpepostvarsel", propOrder={"epostadresse","varslingstekst","dageretter"})
public class EpostVarselBean implements Serializable{
	
	private static final long serialVersionUID = -1668161514444379213L;
	
	@XmlAttribute(required=true)
	private String epostadresse;
	@XmlAttribute(required=true)
	private String varslingstekst;
	@XmlAttribute(required=true)
	private List<String> dageretter;
	
	public EpostVarselBean() {
		
	}

	public EpostVarselBean(String epostadresse, String varslingstekst, List<String> dageretter) {
		super();
		this.epostadresse = epostadresse;
		this.varslingstekst = varslingstekst;
		this.dageretter = dageretter;
	}

	public String getEpostadresse() {
		return epostadresse;
	}

	public void setEpostadresse(String epostadresse) {
		this.epostadresse = epostadresse;
	}

	public String getVarslingstekst() {
		return varslingstekst;
	}

	public void setVarslingstekst(String varslingstekst) {
		this.varslingstekst = varslingstekst;
	}

	public List<String> getDageretter() {
		return dageretter;
	}

	public void setDageretter(List<String> dageretter) {
		this.dageretter = dageretter;
	}

}
