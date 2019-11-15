
package no.difi.kontaktinfo.xsd.oppslagstjeneste._16_02;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="informasjonsbehov" type="{http://kontaktinfo.difi.no/xsd/oppslagstjeneste/16-02}informasjonsbehov" maxOccurs="unbounded"/>
 *         &lt;element name="personidentifikator" type="{http://begrep.difi.no}personidentifikator" maxOccurs="1000"/>
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
    "informasjonsbehov",
    "personidentifikator"
})
@XmlRootElement(name = "HentPersonerForespoersel")
public class HentPersonerForespoersel {

    @XmlElement(required = true)
    protected List<Informasjonsbehov> informasjonsbehov;
    @XmlElement(required = true)
    protected List<String> personidentifikator;

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
     * Gets the value of the personidentifikator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personidentifikator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonidentifikator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPersonidentifikator() {
        if (personidentifikator == null) {
            personidentifikator = new ArrayList<String>();
        }
        return this.personidentifikator;
    }

}
