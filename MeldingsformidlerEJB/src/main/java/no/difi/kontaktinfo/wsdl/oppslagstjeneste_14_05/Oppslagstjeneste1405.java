package no.difi.kontaktinfo.wsdl.oppslagstjeneste_14_05;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.3.1-patch-01
 * Thu Apr 10 07:17:43 CEST 2014
 * Generated source version: 2.3.1-patch-01
 * 
 */
@WebService(targetNamespace = "http://kontaktinfo.difi.no/wsdl/oppslagstjeneste-14-05", name = "oppslagstjeneste-14-05")
@XmlSeeAlso({no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.ObjectFactory.class, no.difi.begrep.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface Oppslagstjeneste1405 {

    @WebResult(name = "HentEndringerRespons", targetNamespace = "http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05", partName = "HentEndringerRespons")
    @WebMethod(operationName = "HentEndringer")
    public no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentEndringerRespons hentEndringer(
        @WebParam(partName = "HentEndringerForespoersel", name = "HentEndringerForespoersel", targetNamespace = "http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05")
        no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentEndringerForespoersel hentEndringerForespoersel
    );

    @WebResult(name = "HentPersonerRespons", targetNamespace = "http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05", partName = "HentPersonerRespons")
    @WebMethod(operationName = "HentPersoner")
    public no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentPersonerRespons hentPersoner(
        @WebParam(partName = "HentPersonerForespoersel", name = "HentPersonerForespoersel", targetNamespace = "http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05")
        no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentPersonerForespoersel hentPersonerForespoersel
    );

    @WebResult(name = "HentPrintSertifikatRespons", targetNamespace = "http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05", partName = "HentPrintSertifikatRespons")
    @WebMethod(operationName = "HentPrintSertifikat")
    public no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentPrintSertifikatRespons hentPrintSertifikat(
        @WebParam(partName = "HentPrintSertifikatForespoersel", name = "HentPrintSertifikatForespoersel", targetNamespace = "http://kontaktinfo.difi.no/xsd/oppslagstjeneste/14-05")
        no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentPrintSertifikatForespoersel hentPrintSertifikatForespoersel
    );
}
