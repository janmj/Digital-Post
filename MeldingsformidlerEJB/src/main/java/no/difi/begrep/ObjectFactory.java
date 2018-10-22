
package no.difi.begrep;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the no.difi.begrep package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Person_QNAME = new QName("http://begrep.difi.no", "Person");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: no.difi.begrep
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link SikkerDigitalPostAdresse }
     * 
     */
    public SikkerDigitalPostAdresse createSikkerDigitalPostAdresse() {
        return new SikkerDigitalPostAdresse();
    }

    /**
     * Create an instance of {@link Epostadresse }
     * 
     */
    public Epostadresse createEpostadresse() {
        return new Epostadresse();
    }

    /**
     * Create an instance of {@link Kontaktinformasjon }
     * 
     */
    public Kontaktinformasjon createKontaktinformasjon() {
        return new Kontaktinformasjon();
    }

    /**
     * Create an instance of {@link Mobiltelefonnummer }
     * 
     */
    public Mobiltelefonnummer createMobiltelefonnummer() {
        return new Mobiltelefonnummer();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://begrep.difi.no", name = "Person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

}
