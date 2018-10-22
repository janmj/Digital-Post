
package no.difi.begrep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Kontaktinformasjon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Kontaktinformasjon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mobiltelefonnummer" type="{http://begrep.difi.no}Mobiltelefonnummer" minOccurs="0"/>
 *         &lt;element name="Epostadresse" type="{http://begrep.difi.no}Epostadresse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kontaktinformasjon", propOrder = {
    "mobiltelefonnummer",
    "epostadresse"
})
public class Kontaktinformasjon {

    @XmlElement(name = "Mobiltelefonnummer")
    protected Mobiltelefonnummer mobiltelefonnummer;
    @XmlElement(name = "Epostadresse")
    protected Epostadresse epostadresse;

    /**
     * Gets the value of the mobiltelefonnummer property.
     * 
     * @return
     *     possible object is
     *     {@link Mobiltelefonnummer }
     *     
     */
    public Mobiltelefonnummer getMobiltelefonnummer() {
        return mobiltelefonnummer;
    }

    /**
     * Sets the value of the mobiltelefonnummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mobiltelefonnummer }
     *     
     */
    public void setMobiltelefonnummer(Mobiltelefonnummer value) {
        this.mobiltelefonnummer = value;
    }

    /**
     * Gets the value of the epostadresse property.
     * 
     * @return
     *     possible object is
     *     {@link Epostadresse }
     *     
     */
    public Epostadresse getEpostadresse() {
        return epostadresse;
    }

    /**
     * Sets the value of the epostadresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Epostadresse }
     *     
     */
    public void setEpostadresse(Epostadresse value) {
        this.epostadresse = value;
    }

}
