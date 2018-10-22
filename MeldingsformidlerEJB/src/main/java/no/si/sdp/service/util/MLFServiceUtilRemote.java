package no.si.sdp.service.util;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;

@Remote
@WebService(targetNamespace="urn:si.sdp.service", name="MLFServiceUtil")
public interface MLFServiceUtilRemote {
	@WebMethod(action="sendForsendelser")
	public void sendForsendelser() throws Exception;
	@WebMethod(action="hentKvitteringer")
	public void hentKvitteringer() throws Exception;
}
