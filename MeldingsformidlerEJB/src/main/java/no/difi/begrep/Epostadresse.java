
package no.difi.begrep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Epostadresse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Epostadresse">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://begrep.difi.no>epostadresse">
 *       &lt;attribute name="sistOppdatert" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="sistVerifisert" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Epostadresse", propOrder = {
    "value"
})
public class Epostadresse {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "sistOppdatert")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sistOppdatert;
    @XmlAttribute(name = "sistVerifisert")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sistVerifisert;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the sistOppdatert property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSistOppdatert() {
        return sistOppdatert;
    }

    /**
     * Sets the value of the sistOppdatert property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSistOppdatert(XMLGregorianCalendar value) {
        this.sistOppdatert = value;
    }

    /**
     * Gets the value of the sistVerifisert property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSistVerifisert() {
        return sistVerifisert;
    }

    /**
     * Sets the value of the sistVerifisert property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSistVerifisert(XMLGregorianCalendar value) {
        this.sistVerifisert = value;
    }

}
