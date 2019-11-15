
package no.difi.kontaktinfo.xsd.oppslagstjeneste._16_02;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaaVegneAv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "paaVegneAv"
})
@XmlRootElement(name = "Oppslagstjenesten")
public class Oppslagstjenesten {

    @XmlElement(name = "PaaVegneAv", required = true)
    protected String paaVegneAv;

    /**
     * Gets the value of the paaVegneAv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaaVegneAv() {
        return paaVegneAv;
    }

    /**
     * Sets the value of the paaVegneAv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaaVegneAv(String value) {
        this.paaVegneAv = value;
    }

}
