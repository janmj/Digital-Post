
package no.difi.begrep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SikkerDigitalPostAdresse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SikkerDigitalPostAdresse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postkasseadresse" type="{http://begrep.difi.no}postkasseadresse"/>
 *         &lt;element name="postkasseleverandoerAdresse" type="{http://begrep.difi.no}postkasseleverandoerAdresse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SikkerDigitalPostAdresse", propOrder = {
    "postkasseadresse",
    "postkasseleverandoerAdresse"
})
public class SikkerDigitalPostAdresse {

    @XmlElement(required = true)
    protected String postkasseadresse;
    @XmlElement(required = true)
    protected String postkasseleverandoerAdresse;

    /**
     * Gets the value of the postkasseadresse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostkasseadresse() {
        return postkasseadresse;
    }

    /**
     * Sets the value of the postkasseadresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostkasseadresse(String value) {
        this.postkasseadresse = value;
    }

    /**
     * Gets the value of the postkasseleverandoerAdresse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostkasseleverandoerAdresse() {
        return postkasseleverandoerAdresse;
    }

    /**
     * Sets the value of the postkasseleverandoerAdresse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostkasseleverandoerAdresse(String value) {
        this.postkasseleverandoerAdresse = value;
    }

}
