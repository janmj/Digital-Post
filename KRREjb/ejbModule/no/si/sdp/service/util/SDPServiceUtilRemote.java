package no.si.sdp.service.util;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;

@Remote
@WebService(targetNamespace="urn:si.sdp.service", name="SDPServiceUtil")
public interface SDPServiceUtilRemote {
	@WebMethod(action="updateKRR")
	public void updateKRR()throws Exception;
	@WebMethod(action="makeKRR")	
	public void makeKRR()throws Exception;
	@WebMethod(action="resetDBPool")		
	public void resetDBPool()throws Exception;
}
