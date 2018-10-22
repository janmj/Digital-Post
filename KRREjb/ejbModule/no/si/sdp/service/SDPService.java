package no.si.sdp.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;



import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import no.difi.begrep.Person;
import no.si.sdp.KRRClient;
import no.si.sdp.beans.SjekkpersonBean;	
import no.si.sdp.utils.PropertiesServices;
import no.si.sdp.utils.fallback.FallbackFacade;
import no.si.sdp.utils.log.Logger;

/**
 * Session Bean implementation class SDPService
 */

@WebService(endpointInterface="no.si.sdp.service.SDPServiceRemote",
			targetNamespace="urn:si.sdp.service",
			name="SDPService",
			serviceName="SDPService")
@SOAPBinding(style=Style.DOCUMENT)
public @Stateless class SDPService implements SDPServiceRemote {
	
	org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(SDPServiceRemote.class);
	PropertiesServices prop = new PropertiesServices();
	boolean online = true;
    /**
     * Default constructor. 
     */
    public SDPService() {
    }

	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Person getPerson(String fonr) throws RemoteException {
		Person retval = null;
		 try {
			online = new Boolean(prop.getPropertyValue("KRR_ONLINE"));
		} catch (Exception e1) {
			log.error(e1);
		}
		try {
			if(online){
				try {
					long starttime = System.currentTimeMillis();
					KRRClient client = new KRRClient();
					ArrayList<String> tmplist = new ArrayList<String>();
					tmplist.add(fonr);
					ArrayList<Person> perslist = client.getPersoner(tmplist);
					retval = perslist.get(0);
					long endtime = System.currentTimeMillis();
					long usedtime = endtime - starttime;
					log.info("Tid medgått for getperson: " + usedtime);
				} catch (Exception e) {
					FallbackFacade fallback =  new FallbackFacade();
					retval = fallback.getLocalperson(fonr);
					Logger.logMesageType("3", "");
					log.info("Gikk i fallback for getperson! Feilmelding: " + e);
				}					
			}else{
				FallbackFacade fallback =  new FallbackFacade();
				retval = fallback.getLocalperson(fonr);
			}
		} catch (Exception e) {
			Logger.logError("Feilet i getPerson: " + e.getMessage(), "");
			log.error(e);
			throw new RemoteException(e.getMessage());
		}
		return retval;
	}

	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<Person> getPersoner(List<String> fonr) throws RemoteException {
		List<Person> retval = null;
		 try {
			online = new Boolean(prop.getPropertyValue("KRR_ONLINE"));
		} catch (Exception e1) {
			log.error(e1);
		}
		try {
			if(online){
				try {
					long starttime = System.currentTimeMillis();
					KRRClient client = new KRRClient();
					retval = client.getPersoner(new ArrayList<String>(fonr));
					long endtime = System.currentTimeMillis();
					long usedtime = endtime - starttime;
					log.info("Tid medgått for getpersoner: " + usedtime);
				} catch (Exception e) {
					FallbackFacade fallback =  new FallbackFacade();
					retval = fallback.getLocalpersoner(fonr);
					Logger.logMesageType("3", "");
					log.info("Gikk i fallback for getpersoner! Feilmelding: " + e);
				}				
			}else{
				FallbackFacade fallback =  new FallbackFacade();
				retval = fallback.getLocalpersoner(fonr);				
			}
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
		return retval;
	}

	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public Boolean sjekkPerson(String fonr) throws RemoteException {
		Boolean retval = new Boolean(false);
		try {
			/*
			KRRClient client = new KRRClient();
			ArrayList<String> tmplist = new ArrayList<String>();
			tmplist.add(fonr);
			ArrayList<Person> perslist = client.getPersoner(tmplist);
			Person person = perslist.get(0);
			*/
			
			Person person = getPerson(fonr);
			
			if(person.getReservasjon().value().equalsIgnoreCase("NEI")){
				try {
					String adr = person.getSikkerDigitalPostAdresse().getPostkasseadresse();
					if(adr.length()>1){
						retval = new Boolean(true);
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
		return retval;
	}

	@WebMethod
	public List<SjekkpersonBean> sjekkPersoner(List<String> fonr)throws RemoteException {
		List<SjekkpersonBean> retval = new ArrayList<SjekkpersonBean>();
		try {
			/*
			KRRClient client = new KRRClient();
			ArrayList<Person> tmplist = client.getPersoner(new ArrayList<String>(fonr));
			*/
			List<Person> tmplist = getPersoner(fonr);
					
			for(int x=0;x<tmplist.size();x++){
				SjekkpersonBean spb = new SjekkpersonBean();
				Boolean SDPStatus = new Boolean(false);
				Person tmppers = tmplist.get(x);
				spb.setPersonidentifikator(tmppers.getPersonidentifikator());
				if(tmppers.getReservasjon().value().equalsIgnoreCase("NEI")){
					try {
						String adr = tmppers.getSikkerDigitalPostAdresse().getPostkasseadresse();
						if(adr.length()>1){
							SDPStatus = new Boolean(true);
						}
					} catch (Exception e) {
					}
				}
				spb.setStatus(SDPStatus);
				retval.add(spb);
			}
		} catch (Exception e) {
			throw new RemoteException();
		}
		return retval;
	}
	/*
	@WebMethod
	public void sendBrev(String fonr, Object brev) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@WebMethod
	public Object getKvitteringer() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
