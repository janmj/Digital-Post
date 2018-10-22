package no.si.sdp.service.util;

import java.io.StringWriter;
import java.util.ArrayList;

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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.jboss.logging.Logger;

import no.difi.sdp.client.domain.kvittering.Feil;
import no.difi.sdp.client.domain.kvittering.ForretningsKvittering;
import no.difi.sdp.client.domain.kvittering.LeveringsKvittering;
import no.difi.sdp.client.domain.kvittering.VarslingFeiletKvittering;
import no.si.sdp.MeldingsformidlerClient;
import no.si.sdp.beans.KvitteringBean;
import no.si.sdp.beans.KvitteringBean.Kvitteringstyper;
import no.si.sdp.utils.MFServices;

/**
 * Session Bean implementation class SDPServiceUtil
 */

@WebService(endpointInterface="no.si.sdp.service.util.MLFServiceUtilRemote",
			targetNamespace="urn:si.sdp.service",
			name="MLFServiceUtil",
			serviceName="MLFServiceUtil")
@SOAPBinding(style=Style.DOCUMENT)

public @Stateless class MLFServiceUtil implements MLFServiceUtilRemote {
	static Logger log = Logger.getLogger(MLFServiceUtil.class);
    /**
     * Default constructor. 
     */
    public MLFServiceUtil() {
        // TODO Auto-generated constructor stub
    }
    
	@Asynchronous
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	@Schedule(persistent=false, hour="*", minute="*/3", info="Dette er en timer for utsending av brev til MLF")
	public void sendForsendelser() throws Exception {
		//System.out.println("Timer går :)");
		MFServices mfs = new MFServices();
		ArrayList<String> forsendelseridlist = mfs.getQuedForsendelser();
		if(forsendelseridlist.size()>0){
			MeldingsformidlerClient client = new MeldingsformidlerClient();
			for(int x=0;x<forsendelseridlist.size();x++){
				String forsendelseid = forsendelseridlist.get(x);
				String forsendelseXML = mfs.getForsendelseXML(forsendelseid);
				try {
					client.sendBrev(forsendelseXML);
				} catch (Exception e) {
					log.error(e);
					no.si.sdp.utils.log.Logger.logError("Feilet i sendForsendelser(): " + e.getMessage(), mfs.getConversationid(forsendelseid));
				}
			}			
		}
	}

	@Asynchronous
	@Schedule(persistent=false, hour="*", minute="*/3", info="Dette er en timer for å hente kvitteringer fra MLF")
	@WebMethod
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void hentKvitteringer() throws Exception {
		MeldingsformidlerClient client = null;
		MFServices mfs = new MFServices();
		String logconversationid ="";
		
		try {
			client = new MeldingsformidlerClient();
			ArrayList<ForretningsKvittering> kvitteringer = client.getKvitteringer();
			for(int x=0;x<kvitteringer.size();x++){
				ForretningsKvittering tmpkvit = kvitteringer.get(x);
				KvitteringBean kvittering = new KvitteringBean();
				logconversationid = tmpkvit.getKonversasjonsId();
				//eksempel
				//String tmp = tmpkvit.applikasjonsKvittering.messageId;
				//tmpkvit.applikasjonsKvittering.getStandardBusinessDocument().getInstanceIdentifier();
				//tmpkvit.applikasjonsKvittering.avsender.orgnr.toString();
				if(tmpkvit instanceof Feil){
					Feil feil = (Feil) tmpkvit;
					//Standardfeilter 
					kvittering.setConversationid(tmpkvit.getKonversasjonsId());
					kvittering.setType(Kvitteringstyper.Feil);
					kvittering.setDocscopeinstanceIdentifier(tmpkvit.applikasjonsKvittering.getStandardBusinessDocument().getInstanceIdentifier());
					kvittering.setBizscopeinstanceIdentifier(tmpkvit.applikasjonsKvittering.messageId);
					kvittering.setAvsenderorgnr(feil.applikasjonsKvittering.avsender.orgnr.toString());
					kvittering.setCreationdatetime(feil.getTidspunkt());
					kvittering.setTidspunkt(feil.getTidspunkt());
					//Feilfelter
					kvittering.setFeiltype(feil.getFeiltype());
					//kvittering.setFeilkode("Ikke implementert fra leverandør");
					kvittering.setDetaljer(feil.getDetaljer());
					if(feil.getFeiltype().equals(Feil.Feiltype.SERVER)){
						no.si.sdp.utils.log.Logger.logErrorType("2", feil.getKonversasjonsId());
					}
				}else if (tmpkvit instanceof LeveringsKvittering){
					//Standardfeilter
					kvittering.setConversationid(tmpkvit.getKonversasjonsId());
					kvittering.setType(Kvitteringstyper.Leveringskvittering);
					kvittering.setDocscopeinstanceIdentifier(tmpkvit.applikasjonsKvittering.getStandardBusinessDocument().getInstanceIdentifier());
					kvittering.setBizscopeinstanceIdentifier(tmpkvit.applikasjonsKvittering.messageId);
					kvittering.setAvsenderorgnr(tmpkvit.applikasjonsKvittering.avsender.orgnr.toString());
					kvittering.setCreationdatetime(tmpkvit.applikasjonsKvittering.getKvittering().kvittering.getTidspunkt().toDate());
					kvittering.setTidspunkt(tmpkvit.getTidspunkt());
				}else if(tmpkvit instanceof VarslingFeiletKvittering){
					VarslingFeiletKvittering vfeilkvit = (VarslingFeiletKvittering)tmpkvit;
					//Standardfeilter
					kvittering.setConversationid(vfeilkvit.getKonversasjonsId());
					kvittering.setType(Kvitteringstyper.VarslingfeiletKvittering);
					kvittering.setDocscopeinstanceIdentifier(vfeilkvit.applikasjonsKvittering.getStandardBusinessDocument().getInstanceIdentifier());
					kvittering.setBizscopeinstanceIdentifier(vfeilkvit.applikasjonsKvittering.messageId);
					kvittering.setAvsenderorgnr(vfeilkvit.applikasjonsKvittering.avsender.orgnr.toString());
					kvittering.setCreationdatetime(vfeilkvit.getTidspunkt());
					kvittering.setTidspunkt(vfeilkvit.getTidspunkt());
					//Varslingfeilet
					kvittering.setVarslingskanal(vfeilkvit.getVarslingskanal());
					kvittering.setBeskrivelse(vfeilkvit.getBeskrivelse());
				}
				String kvitXMl = marshallXml(kvittering);
				mfs.saveKvitteringText(tmpkvit.getKonversasjonsId(), kvitXMl);
				client.bekreftKvittering(tmpkvit);
				no.si.sdp.utils.log.Logger.logMesageType("4", logconversationid);
			}
		} catch (Exception e) {
			log.error(e);
			no.si.sdp.utils.log.Logger.logError("Feilet i hentKvitteringer: " + e.getMessage(), logconversationid);
			throw new Exception("Feilet i hentKvitteringer: " + e.getMessage());
		}
	}
	
	private String marshallXml(KvitteringBean kvittering)throws Exception{
		String xml="";
		try {
			StringWriter sw = new StringWriter();
			JAXBContext jaxbcontext = JAXBContext.newInstance(KvitteringBean.class);
			Marshaller marshaller = jaxbcontext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//marshaller.marshal(kvittering, System.out);
			marshaller.marshal(kvittering, sw);
			xml = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Feilet i marshalling av forsendelse: " + e.getMessage());
		}
		return xml;
	}	
}
