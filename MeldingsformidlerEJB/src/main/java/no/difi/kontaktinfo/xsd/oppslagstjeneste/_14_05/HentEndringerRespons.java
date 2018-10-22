
package no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import no.difi.begrep.Person;


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
 *         &lt;element ref="{http://begrep.difi.no}Person" maxOccurs="1000" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fraEndringsNummer" use="required" type="{http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05}fraEndringsNummer" />
 *       &lt;attribute name="tilEndringsNummer" use="required" type="{http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05}tilEndringsNummer" />
 *       &lt;attribute name="senesteEndringsNummer" use="required" type="{http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05}senesteEndringsNummer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "person"
})
@XmlRootElement(name = "HentEndringerRespons")
public class HentEndringerRespons {

    @XmlElement(name = "Person", namespace = "http://begrep.difi.no")
    protected List<Person> person;
    @XmlAttribute(name = "fraEndringsNummer", required = true)
    protected long fraEndringsNummer;
    @XmlAttribute(name = "tilEndringsNummer", required = true)
    protected long tilEndringsNummer;
    @XmlAttribute(name = "senesteEndringsNummer", required = true)
    protected long senesteEndringsNummer;

    /**
     * Gets the value of the person property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the person property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Person }
     * 
     * 
     */
    public List<Person> getPerson() {
        if (person == null) {
            person = new ArrayList<Person>();
        }
        return this.person;
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

    /**
     * Gets the value of the tilEndringsNummer property.
     * 
     */
    public long getTilEndringsNummer() {
        return tilEndringsNummer;
    }

    /**
     * Sets the value of the tilEndringsNummer property.
     * 
     */
    public void setTilEndringsNummer(long value) {
        this.tilEndringsNummer = value;
    }

    /**
     * Gets the value of the senesteEndringsNummer property.
     * 
     */
    public long getSenesteEndringsNummer() {
        return senesteEndringsNummer;
    }

    /**
     * Sets the value of the senesteEndringsNummer property.
     * 
     */
    public void setSenesteEndringsNummer(long value) {
        this.senesteEndringsNummer = value;
    }

}
