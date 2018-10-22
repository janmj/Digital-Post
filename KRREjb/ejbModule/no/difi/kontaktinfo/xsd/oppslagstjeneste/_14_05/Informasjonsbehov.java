
package no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for informasjonsbehov.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="informasjonsbehov">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Person"/>
 *     &lt;enumeration value="Kontaktinfo"/>
 *     &lt;enumeration value="Sertifikat"/>
 *     &lt;enumeration value="SikkerDigitalPost"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "informasjonsbehov")
@XmlEnum
public enum Informasjonsbehov {

    @XmlEnumValue("Person")
    PERSON("Person"),
    @XmlEnumValue("Kontaktinfo")
    KONTAKTINFO("Kontaktinfo"),
    @XmlEnumValue("Sertifikat")
    SERTIFIKAT("Sertifikat"),
    @XmlEnumValue("SikkerDigitalPost")
    SIKKER_DIGITAL_POST("SikkerDigitalPost");
    private final String value;

    Informasjonsbehov(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Informasjonsbehov fromValue(String v) {
        for (Informasjonsbehov c: Informasjonsbehov.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
