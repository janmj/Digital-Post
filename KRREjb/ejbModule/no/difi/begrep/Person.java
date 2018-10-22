
package no.difi.begrep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Person complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Person">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="personidentifikator" type="{http://begrep.difi.no}personidentifikator"/>
 *         &lt;element name="reservasjon" type="{http://begrep.difi.no}reservasjon" minOccurs="0"/>
 *         &lt;element name="status" type="{http://begrep.difi.no}status" minOccurs="0"/>
 *         &lt;element name="beskrivelse" type="{http://begrep.difi.no}beskrivelse" minOccurs="0"/>
 *         &lt;element name="Kontaktinformasjon" type="{http://begrep.difi.no}Kontaktinformasjon" minOccurs="0"/>
 *         &lt;element name="SikkerDigitalPostAdresse" type="{http://begrep.difi.no}SikkerDigitalPostAdresse" minOccurs="0"/>
 *         &lt;element name="X509Sertifikat" type="{http://begrep.difi.no}X509Sertifikat" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", propOrder = {
    "personidentifikator",
    "reservasjon",
    "status",
    "beskrivelse",
    "kontaktinformasjon",
    "sikkerDigitalPostAdresse",
    "x509Sertifikat"
})
public class Person {

    @XmlElement(required = true)
    protected String personidentifikator;
    protected Reservasjon reservasjon;
    protected Status status;
    protected String beskrivelse;
    @XmlElement(name = "Kontaktinformasjon")
    protected Kontaktinformasjon kontaktinformasjon;
    @XmlElement(name = "SikkerDigitalPostAdresse")
    protected SikkerDigitalPostAdresse sikkerDigitalPostAdresse;
    @XmlElement(name = "X509Sertifikat")
    protected byte[] x509Sertifikat;

    /**
     * Gets the value of the personidentifikator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonidentifikator() {
        return personidentifikator;
    }

    /**
     * Sets the value of the personidentifikator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonidentifikator(String value) {
        this.personidentifikator = value;
    }

    /**
     * Gets the value of the reservasjon property.
     * 
     * @return
     *     possible object is
     *     {@link Reservasjon }
     *     
     */
    public Reservasjon getReservasjon() {
        return reservasjon;
    }

    /**
     * Sets the value of the reservasjon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reservasjon }
     *     
     */
    public void setReservasjon(Reservasjon value) {
        this.reservasjon = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the beskrivelse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeskrivelse() {
        return beskrivelse;
    }

    /**
     * Sets the value of the beskrivelse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeskrivelse(String value) {
        this.beskrivelse = value;
    }

    /**
     * Gets the value of the kontaktinformasjon property.
     * 
     * @return
     *     possible object is
     *     {@link Kontaktinformasjon }
     *     
     */
    public Kontaktinformasjon getKontaktinformasjon() {
        return kontaktinformasjon;
    }

    /**
     * Sets the value of the kontaktinformasjon property.
     * 
     * @param value
     *     allowed object is
     *     {@link Kontaktinformasjon }
     *     
     */
    public void setKontaktinformasjon(Kontaktinformasjon value) {
        this.kontaktinformasjon = value;
    }

    /**
     * Gets the value of the sikkerDigitalPostAdresse property.
     * 
     * @return
     *     possible object is
     *     {@link SikkerDigitalPostAdresse }
     *     
     */
    public SikkerDigitalPostAdresse getSikkerDigitalPostAdresse() {
        return sikkerDigitalPostAdresse;
    }

    /**
     * Sets the value of the sikkerDigitalPostAdresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link SikkerDigitalPostAdresse }
     *     
     */
    public void setSikkerDigitalPostAdresse(SikkerDigitalPostAdresse value) {
        this.sikkerDigitalPostAdresse = value;
    }

    /**
     * Gets the value of the x509Sertifikat property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getX509Sertifikat() {
        return x509Sertifikat;
    }

    /**
     * Sets the value of the x509Sertifikat property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setX509Sertifikat(byte[] value) {
        this.x509Sertifikat = value;
    }

}
