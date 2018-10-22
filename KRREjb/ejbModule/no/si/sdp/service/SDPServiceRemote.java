package no.si.sdp.service;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Response;

import no.difi.begrep.Person;
import no.si.sdp.beans.SjekkpersonBean;

@Remote
@WebService(targetNamespace="urn:si.sdp.service", name="SDPService")
public interface SDPServiceRemote { 
	@WebMethod(action="getPerson")
	public Person getPerson(@WebParam(name="fonr") String fonr) throws RemoteException;
	@WebMethod(action="getPersoner")
	public List<Person> getPersoner(@WebParam(name="fonr") List<String> fonr)throws RemoteException;
	@WebMethod(action="sjekkPerson")
	public Boolean sjekkPerson(@WebParam(name="fonr") String fonr)throws RemoteException;
	@WebMethod(action="sjekkPersoner")
	public List<SjekkpersonBean> sjekkPersoner(@WebParam(name="fonr") List<String> fonr)throws RemoteException;
	/*
	@WebMethod(action="sendBrev")
	public void sendBrev(@WebParam(name="fonr") String fonr, @WebParam(name="brev")Object brev)throws RemoteException;
	@WebMethod(action="getKvitteringer")
	public Object getKvitteringer()throws RemoteException;
	*/
}
