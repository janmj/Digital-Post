
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
 *         &lt;element name="postkasseleverandoerAdresse" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="X509Sertifikat" type="{http://begrep.difi.no}X509Sertifikat"/>
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
    "postkasseleverandoerAdresse",
    "x509Sertifikat"
})
@XmlRootElement(name = "HentPrintSertifikatRespons")
public class HentPrintSertifikatRespons {

    @XmlElement(required = true)
    protected String postkasseleverandoerAdresse;
    @XmlElement(name = "X509Sertifikat", required = true)
    protected byte[] x509Sertifikat;

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
