
package no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="informasjonsbehov" type="{http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05}informasjonsbehov" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fraEndringsNummer" use="required" type="{http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05}fraEndringsNummer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "informasjonsbehov"
})
@XmlRootElement(name = "HentEndringerForespoersel")
public class HentEndringerForespoersel {

    @XmlElement(required = true)
    protected List<Informasjonsbehov> informasjonsbehov;
    @XmlAttribute(name = "fraEndringsNummer", required = true)
    protected long fraEndringsNummer;

    /**
     * Gets the value of the informasjonsbehov property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the informasjonsbehov property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInformasjonsbehov().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Informasjonsbehov }
     * 
     * 
     */
    public List<Informasjonsbehov> getInformasjonsbehov() {
        if (informasjonsbehov == null) {
            informasjonsbehov = new ArrayList<Informasjonsbehov>();
        }
        return this.informasjonsbehov;
    }

    /**
     * Gets the value of the fraEndringsNummer property.
     * 
     */
    public long getFraEndringsNummer() {
        return fraEndringsNummer;
    }

    /**
     * Sets the value of the fraEndringsNummer property.
     * 
     */
    public void setFraEndringsNummer(long value) {
        this.fraEndringsNummer = value;
    }

}
