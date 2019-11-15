package no.si.sdp.service.util;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.jboss.logging.Logger;

import no.si.sdp.utils.KRRServices;

/**
 * Session Bean implementation class SDPServiceUtil
 */
@WebService(endpointInterface="no.si.sdp.service.util.SDPServiceUtilRemote",
			targetNamespace="urn:si.sdp.service",
			name="SDPServiceUtil",
			serviceName="SDPServiceUtil")
@SOAPBinding(style=Style.DOCUMENT)

public @Stateless class SDPServiceUtil implements SDPServiceUtilRemote {
	Logger log = Logger.getLogger(SDPServiceUtilRemote.class);
    /**
     * Default constructor. 
     */
    public SDPServiceUtil() {
        // TODO Auto-generated constructor stub
    }
    
    @Asynchronous
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
    //@Schedule(hour="*",minute="*/1", info="Dette er en timer for oppdatering av KRR", persistent=false)
	public void updateKRR() throws Exception {
    	log.info("Scedulert oppdatering av KRR..");
		try {
			KRRServices service = new KRRServices();
			service.maintainKRR();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
    
	@Asynchronous
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void makeKRR() throws Exception {
		try {
			KRRServices service = new KRRServices();
			service.makeLocalKRR();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Asynchronous
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Schedule(hour="*", minute="*/2", persistent=false, info="Timer for å rydde oppi database poolen")
	public void resetDBPool() throws Exception {
		try {
			KRRServices service = new KRRServices();
			service.resetDBPool();
			log.info("Flushet dbPoolen...");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
