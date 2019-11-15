
package no.difi.begrep;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for varslingsstatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="varslingsstatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="KAN_IKKE_VARSLES"/>
 *     &lt;enumeration value="KAN_VARSLES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "varslingsstatus")
@XmlEnum
public enum Varslingsstatus {

    KAN_IKKE_VARSLES,
    KAN_VARSLES;

    public String value() {
        return name();
    }

    public static Varslingsstatus fromValue(String v) {
        return valueOf(v);
    }

}
