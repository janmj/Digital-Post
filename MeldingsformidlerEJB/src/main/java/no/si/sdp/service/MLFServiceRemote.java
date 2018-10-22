package no.si.sdp.service;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import no.si.sdp.beans.ForsendelseBean;
import no.si.sdp.beans.KvitteringBean;

@Remote
@WebService(targetNamespace="urn:si.sdp.service", name="MLFService")
public interface MLFServiceRemote { 
	@WebMethod(action="sendBrev")
	public String sendBrev(@WebParam(name="forsendelse") ForsendelseBean forsendelse)throws RemoteException;
	@WebMethod(action="getKvitteringer")
	public ArrayList<KvitteringBean> getKvitteringer(@WebParam(name="conversationid")String conversationid) throws RemoteException;

}
