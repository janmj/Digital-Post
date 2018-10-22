package no.si.sdp;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.jboss.logging.Logger;

import no.difi.begrep.Person;
import no.difi.kontaktinfo.wsdl.oppslagstjeneste_14_05.Oppslagstjeneste1405;
import no.difi.kontaktinfo.wsdl.oppslagstjeneste_14_05.Oppslagstjeneste1405Impl;
import no.difi.kontaktinfo.wsdl.oppslagstjeneste_14_05.Oppslagstjeneste1405_Service;
import no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentEndringerForespoersel;
import no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentEndringerRespons;
import no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentPersonerForespoersel;
import no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentPersonerRespons;
import no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.Informasjonsbehov;
import no.si.sdp.cxf.WSS4JInterceptorHelper;
import no.si.sdp.utils.PropertiesServices;

public class KRRClient {
    private static final QName SERVICE_NAME = new QName("http://kontaktinfo.difi.no/wsdl/oppslagstjeneste-14-05", "oppslagstjeneste-14-05");    
    private Oppslagstjeneste1405 oppslagstjenesteport;
    static Logger log = Logger.getLogger(KRRClient.class);
    PropertiesServices prop = new PropertiesServices();
    
    public KRRClient() {
		try {
			//PropertyConfigurator.configure("log4j.properties");			
		} catch (Exception e) {
			System.out.println("Feilet i Constructor for KRRClient: " + e.getMessage());
		}

	}
	
	private void init(long timeout)throws Exception{
		try {
			
			String serviceadress = prop.getPropertyValue("KRR_URL");
			//String serviceadress = "https://serah-test:7443/difi/krr";
			//String serviceadress = "https://kontaktinfo-ws-ver2.difi.no/kontaktinfo-external/ws-v3";
			//String serviceadress = "https://eid-inttest.difi.no/kontaktinfo-external/ws-v3";
			
			JaxWsProxyFactoryBean jaxwsproxyFactoryBean = new JaxWsProxyFactoryBean();
			jaxwsproxyFactoryBean.setServiceClass(Oppslagstjeneste1405.class);
			jaxwsproxyFactoryBean.setAddress(serviceadress);
			
			WSS4JInterceptorHelper.addWs4jInterceptors(jaxwsproxyFactoryBean);
			
			oppslagstjenesteport = (Oppslagstjeneste1405) jaxwsproxyFactoryBean.create();
			
			Client client = ClientProxy.getClient(oppslagstjenesteport);
			HTTPConduit httpconduit = (HTTPConduit) client.getConduit();
			
			//Justere timeout--
			HTTPClientPolicy policy = httpconduit.getClient();
			log.info("Timeout: " + timeout);
			policy.setReceiveTimeout(timeout);
			
			TLSClientParameters tlclientsparam = new TLSClientParameters();
			tlclientsparam.setDisableCNCheck(true);
			httpconduit.setTlsClientParameters(tlclientsparam);
			System.setProperty("com.sun.net.ssl.checkRevocation", "false");
			
		} catch (Exception e) {
			log.debug(e);
			throw new Exception(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		KRRClient test = new KRRClient();
		try {
			/*
			ArrayList<String> input = new ArrayList<String>();
			input.add("01010317467");
			input.add("01010750241");
			//input.add("26126641590");
			
		 	ArrayList<Person> personer = test.getPersoner(input);
		 	
		 	
		 	for(int x=0;x<personer.size();x++){
		 		Person tmpPers = personer.get(x);
		 		System.out.println(tmpPers.getStatus());
		 	}
		 	*/
			
			
			HentEndringerRespons resp = test.getEndringer("0");
			ArrayList<Person> personer = new ArrayList<Person>(resp.getPerson());
			int total = 0;
			for(int x=0;x<personer.size();x++){
				Person tmpPerson = personer.get(x);
				
				//System.out.println(tmpPerson.getPersonidentifikator());
				try {
					//System.out.println(tmpPerson.getKontaktinformasjon().getMobiltelefonnummer().getValue());
				} catch (Exception e) {
					//System.out.println("intet nummer");
				}
				try {
					System.out.println(tmpPerson.getSikkerDigitalPostAdresse().getPostkasseleverandoerAdresse());				
				} catch (Exception e) {
					//System.out.println("Ingen SDP registrert");
				}
				total++;
			}
			System.out.println(total);
			System.out.println("Siste endringsnummer: " + resp.getTilEndringsNummer());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	public ArrayList<Person> getPersoner(ArrayList<String> fonr)throws Exception{
		ArrayList<Person> retval = null;
		try {
			
			HentPersonerForespoersel req = new HentPersonerForespoersel();
			List<Informasjonsbehov> reqinf = new ArrayList<Informasjonsbehov>();
			Informasjonsbehov infpers = Informasjonsbehov.PERSON;
			Informasjonsbehov infkontakt = Informasjonsbehov.KONTAKTINFO;
			Informasjonsbehov infSDP = Informasjonsbehov.SIKKER_DIGITAL_POST;
			Informasjonsbehov infcert = Informasjonsbehov.SERTIFIKAT;
			reqinf.add(infpers);
			reqinf.add(infkontakt);
			reqinf.add(infSDP);
			reqinf.add(infcert);
			
			req.getInformasjonsbehov().addAll(reqinf);
			req.getPersonidentifikator().addAll(fonr);
			
			init(new Long(prop.getPropertyValue("KRR_TIMEOUT")).longValue());
			
			HentPersonerRespons resp = oppslagstjenesteport.hentPersoner(req);
			retval = new ArrayList<Person>(resp.getPerson());
		} catch (Exception e) {
			e.printStackTrace();
			//log.error(e);
			throw new Exception(e.getMessage());
		}
		return retval;
	}
	
	public HentEndringerRespons getEndringer(String fraendrnummer)throws Exception{
		HentEndringerRespons retval = null;
		try {
			HentEndringerForespoersel endringsforesporsel = new HentEndringerForespoersel();
			endringsforesporsel.getInformasjonsbehov().add(Informasjonsbehov.KONTAKTINFO);
			endringsforesporsel.getInformasjonsbehov().add(Informasjonsbehov.PERSON);
			endringsforesporsel.getInformasjonsbehov().add(Informasjonsbehov.SERTIFIKAT);
			endringsforesporsel.getInformasjonsbehov().add(Informasjonsbehov.SIKKER_DIGITAL_POST);
			
			endringsforesporsel.setFraEndringsNummer(new Long(fraendrnummer).longValue());
			
			init(0);
			
			retval = oppslagstjenesteport.hentEndringer(endringsforesporsel); 
		} catch (Exception e) {
			log.debug(e);
			throw new Exception(e.getMessage());
		}
		return retval;
	}
	
}
