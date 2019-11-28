package no.si.sdp.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
//import org.jboss.ws.annotation.SchemaValidation;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.xml.sax.SAXException;

import no.si.sdp.beans.ForsendelseBean;
import no.si.sdp.beans.KvitteringBean;
import no.si.sdp.utils.MFServices;

/**
 * Session Bean implementation class SDPService
 */

@WebService(endpointInterface="no.si.sdp.service.MLFServiceRemote",
			targetNamespace="urn:si.sdp.service",
			name="MLFService",
			serviceName="MLFService")
@SOAPBinding(style=Style.DOCUMENT)
//@SchemaValidation(enabled=true)
@Validated
public @Stateless class MLFService implements MLFServiceRemote {
	//static Logger log = Logger.getLogger(MLFService.class);
	static org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(MLFService.class);

	public MLFService() {
    	
    }

	@WebMethod
	public String sendBrev(ForsendelseBean forsendelse) throws RemoteException {
		String conversationid = "";
		
		try {
			conversationid = UUID.randomUUID().toString();
			forsendelse.setConversationid(conversationid);
			String xml = marshallForsendelse(forsendelse);
			MFServices mfs = new MFServices();
			mfs.saveForsendelse(conversationid, xml);
			//validere...
			try {
				//validateInput(forsendelse);
				//validateXML(xml);
			} catch (Exception e) {
				System.out.println("Feilet i validering! " + e.getMessage());
			}
			
			/**
			 * Ikke helt sikker på bruken av disse enda... Mao. hvorvidt de skal brukes :)
			 */
			//ForsendelseBean valf = unmarshallForsendelse(xml);
			//generateSchema();
			
			
			//log.debug(xml);
			/*
			System.out.println(xml);
			ForsendelseBean tmpFB = unmarshallForsendelse(xml);
			System.out.println(tmpFB.getMottaker().getPersonidentifikator());
			*/
			no.si.sdp.utils.log.Logger.logMesageType("2", conversationid);
		} catch (Exception e) {
			no.si.sdp.utils.log.Logger.logError("Feilet i mottak av brev for intern avsender: " + e.getMessage(), conversationid);
			throw new RemoteException("Feilet i sendbrev: " + e.getMessage());
		}
		return conversationid;
	}

	@WebMethod
	public ArrayList<KvitteringBean> getKvitteringer(String conversationid) throws RemoteException {
		ArrayList<KvitteringBean> retval = new ArrayList<KvitteringBean>();
		try {
			MFServices mfs = new MFServices();
			ArrayList<String> kvits = mfs.getKvitteringer(conversationid);
			for(int x=0;x<kvits.size();x++){
				String xml = kvits.get(x);
				KvitteringBean tmpKvit = unmarshallKvittering(xml);
				retval.add(tmpKvit);
			}
			mfs.updateKvitteringsStatus("1", conversationid);
		} catch (Exception e) {
			no.si.sdp.utils.log.Logger.logError("Feilet i avlevering av kvittering: " + e.getMessage(), conversationid);
			throw new RemoteException(e.getMessage());
		}
		return retval;
	}
	
	private void validateXML(String xml) throws Exception{
		try {
			SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = sf.newSchema(new File("schema1.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xml)));
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	private void validateInput(ForsendelseBean forsendelse)throws JAXBException, SAXException,IOException,Exception{
		try {
			JAXBContext jbctx = JAXBContext.newInstance(ForsendelseBean.class);
			JAXBSource jbsrc = new JAXBSource(jbctx, forsendelse);
			SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = sf.newSchema(new File("schema1.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(jbsrc);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	private String marshallForsendelse(ForsendelseBean forsendelse)throws Exception{
		String xml="";
		try {
			StringWriter sw = new StringWriter();
			JAXBContext jaxbcontext = JAXBContext.newInstance(ForsendelseBean.class);
			Marshaller marshaller = jaxbcontext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//marshaller.marshal(forsendelse, System.out);
			marshaller.marshal(forsendelse, sw);
			xml = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Feilet i marshalling av forsendelse: " + e.getMessage());
		}
		return xml;
	}
	
	private void generateSchema()throws Exception{
		JAXBContext jbctx = JAXBContext.newInstance(ForsendelseBean.class);
		SchemaOutputResolver sor = new MySchemaOutputResolver();
		jbctx.generateSchema(sor);
		sor.createOutput("urn:test.si.sdp", "/home/janmj/test/SDP/tes.xsd");		
	}

	class MySchemaOutputResolver extends SchemaOutputResolver{
		public Result createOutput(String namespaceuri, String filename) throws IOException {
			File file = new File(filename);
			StreamResult result = new StreamResult(file);
			result.setSystemId(file.toURI().toURL().toString());
			return result;
		}
	}
	
	private ForsendelseBean unmarshallForsendelse(String xml)throws Exception{
		ForsendelseBean retval = null;
		try {
			JAXBContext jaxbcontext = JAXBContext.newInstance(ForsendelseBean.class);
			Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
			retval = (ForsendelseBean) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			throw new Exception("Feilet i unmarshall: " + e.getMessage());
		}
		return retval;
	}
	private KvitteringBean unmarshallKvittering(String xml)throws Exception{
		KvitteringBean retval = null;
		try {
			JAXBContext jaxbcontext = JAXBContext.newInstance(KvitteringBean.class);
			Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
			retval = (KvitteringBean) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			throw new Exception("Feilet i unmarshall: " + e.getMessage());
		}
		return retval;
	}
	
}
