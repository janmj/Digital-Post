
package no.difi.begrep;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for reservasjon.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="reservasjon">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="JA"/>
 *     &lt;enumeration value="NEI"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "reservasjon")
@XmlEnum
public enum Reservasjon {

    JA,
    NEI;

    public String value() {
        return name();
    }

    public static Reservasjon fromValue(String v) {
        return valueOf(v);
    }

}
