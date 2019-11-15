package no.si.sdp;

import static java.util.Arrays.asList;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.jboss.logging.Logger;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import no.difi.sdp.client.KlientKonfigurasjon;
import no.difi.sdp.client.SikkerDigitalPostKlient;
import no.difi.sdp.client.domain.Behandlingsansvarlig;
import no.difi.sdp.client.domain.Dokument;
import no.difi.sdp.client.domain.Dokumentpakke;
import no.difi.sdp.client.domain.Forsendelse;
import no.difi.sdp.client.domain.Mottaker;
import no.difi.sdp.client.domain.Noekkelpar;
import no.difi.sdp.client.domain.Prioritet;
import no.difi.sdp.client.domain.Sertifikat;
import no.difi.sdp.client.domain.TekniskAvsender;
import no.difi.sdp.client.domain.digital_post.DigitalPost;
import no.difi.sdp.client.domain.digital_post.EpostVarsel;
import no.difi.sdp.client.domain.digital_post.Sikkerhetsnivaa;
import no.difi.sdp.client.domain.digital_post.SmsVarsel;
import no.difi.sdp.client.domain.kvittering.ForretningsKvittering;
import no.difi.sdp.client.domain.kvittering.KvitteringForespoersel;
import no.si.sdp.beans.ForsendelseBean;
import no.si.sdp.service.MLFService;
import no.si.sdp.utils.MFServices;
import no.si.sdp.utils.PropertiesServices;

public class MeldingsformidlerClient {
	private PropertiesServices properties = new PropertiesServices(); 
    //private static final String MELDINGSFORMIDLER_URI = "https://qaoffentlig.meldingsformidler.digipost.no/api/ebms";
    private String MELDINGSFORMIDLER_URI =  properties.getPropertyValue("MELDINGSFORMIDLER_URI");
    //private static final String AVSENDER_ORGNUMMER = "971648198";    
    private String AVSENDER_ORGNUMMER = properties.getPropertyValue("AVSENDER_ORGNUMMER");    
	//private static final String MCPID = "SI_DEV_002";
    private int  MAX_KVITTERINGER = new Integer(properties.getPropertyValue("MAX_KVITTERINGER")).intValue();
	private String MCPID = properties.getPropertyValue("MCPID");
    private final SikkerDigitalPostKlient klient;
	public static final Prioritet MCP_PRIORITY = Prioritet.NORMAL;
	//static Logger log = Logger.getLogger(MeldingsformidlerClient.class);
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MeldingsformidlerClient.class);
	
	public MeldingsformidlerClient()throws Exception {
		Noekkelpar noekkelpar;
		try {
			KeyStore keystore = KeyStore.getInstance("JCEKS");
			//keystore.load(new FileInputStream("/home/janmj/Dokumenter/Certs/BP/TEST/SI-TEST-SIGN.jceks"), "Kongen123".toCharArray());
			keystore.load(new FileInputStream(properties.getPropertyValue("MLF_KEYSTORE_PATH")), properties.getPropertyValue("MLF_KEYSTORE_PASSWORD").toCharArray());
			//noekkelpar = Noekkelpar.fraKeyStore(keystore, "statens innkrevingssentral", "Kongen123");
			noekkelpar = Noekkelpar.fraKeyStore(keystore, properties.getPropertyValue("MLF_KEYSTORE_ALIAS"), properties.getPropertyValue("MLF_KEYSTORE_ALIAS_PASSWORD"));

			
			KlientKonfigurasjon klientKonfigurasjon_debug = KlientKonfigurasjon.builder()
				    .soapInterceptors(new ClientInterceptor() {
				        //@Override
				        public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
				            Source payloadSource = messageContext.getRequest().getPayloadSource();
				            try {
				                Transformer transformer = TransformerFactory.newInstance().newTransformer();
				                StringWriter writer = new StringWriter();
				                transformer.transform(payloadSource, new StreamResult(writer));
				                System.out.println(writer.toString());
				            } catch (Exception e) {
				                System.err.print("Klarte ikke logge request");
				                //e.printStackTrace();
				            }
				            return true;
				        }

				        public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException { return true; }
				        public boolean handleFault(MessageContext messageContext) throws WebServiceClientException { return true; }
				        public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException { }
				    })
				    .meldingsformidlerRoot(MELDINGSFORMIDLER_URI)
				    .build();

			KlientKonfigurasjon klientKonfigurasjon_normal = KlientKonfigurasjon.builder().meldingsformidlerRoot(MELDINGSFORMIDLER_URI).build();
			klient = new SikkerDigitalPostKlient(TekniskAvsender.builder(AVSENDER_ORGNUMMER, noekkelpar).build(), klientKonfigurasjon_normal);
			//klient = new SikkerDigitalPostKlient(TekniskAvsender.builder(AVSENDER_ORGNUMMER, noekkelpar).build(), klientKonfigurasjon_debug);

		} catch (Exception e) {
			log.error(e);
			throw new Exception("Kunne ikke initiere keystore! " + e.getMessage());
		}		
	}

	public static void main(String[] args) {	
		try {
			MeldingsformidlerClient test = new MeldingsformidlerClient();
			//test.sendBrev("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendBrev(String forsendelsexml)throws Exception{
		MFServices mfs = new MFServices();
		ForsendelseBean forsendelsebean = null;
		try {
			forsendelsebean = unmarshallForsendelse(forsendelsexml);
			Forsendelse forsendelse = lagBrev(forsendelsebean);
			klient.send(forsendelse);
			mfs.updateForsendelsesStatus("1", forsendelsebean.getConversationid());
			try {
				no.si.sdp.utils.log.Logger.logMesageType("1", forsendelsebean.getConversationid());
			} catch (Exception e) {
				log.error("Feilet i loggin av avsendt brev: " + e);
			}
		} catch (Exception e) {
			mfs.updateForsendelsesStatus("2", forsendelsebean.getConversationid());			
			log.error(e);
			no.si.sdp.utils.log.Logger.logErrorType("1", forsendelsebean.getConversationid());
			throw new Exception("Feilet i forsendelse av brev: " + e.getMessage());
		}
	}
	
	public ArrayList<ForretningsKvittering> getKvitteringer()throws Exception{
		ArrayList<ForretningsKvittering> retval = new ArrayList<ForretningsKvittering>();
		boolean hasMore=true;
		String logconversationid = "";
		try {
			while(hasMore){
				ForretningsKvittering kvittering = klient.hentKvittering(KvitteringForespoersel.builder(MCP_PRIORITY).mpcId(MCPID).build());
				try {
					logconversationid = kvittering.getKonversasjonsId();
				} catch (Exception e) {
					hasMore=false;
					logconversationid = "";
					return retval;
				}
				retval.add(kvittering);
				if(retval.size()>= MAX_KVITTERINGER){
					hasMore = false;
				}else{
					hasMore=true;
				}
			}
		} catch (Exception e) {
			no.si.sdp.utils.log.Logger.logError("Feilet i mottak av kitteringer: " + e.getMessage(), logconversationid);
			log.error(e);
			throw new Exception("Feilet i getKvitteringer: " + e.getMessage());
		}
		return retval;
	}
	
	public void bekreftKvittering(ForretningsKvittering kvittering) throws Exception{
		try {
			klient.bekreft(kvittering);
		} catch (Exception e) {
			throw new Exception("Feilet i bekreft av mottatt kvittering: " + e.getMessage());
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
	
    private Forsendelse lagBrev(ForsendelseBean forsendelse)throws Exception{
    	boolean sendepostvarsel = false;
    	boolean sendsmsvarsel = false;
    	
		Mottaker mottaker = getMottaker(forsendelse);
		
    	Behandlingsansvarlig behandlingsansvarlig;
		DigitalPost post;
		Dokumentpakke dokumentpakke;
		SmsVarsel smsvarsel = null;
		EpostVarsel epostvarsel = null;
		
		try {
			try {
				if(forsendelse.getEpostvarsel()!=null && !forsendelse.getEpostvarsel().getEpostadresse().equals(null)){
					List<Integer> dageretterliste = new ArrayList<Integer>();
					for(int x=0;x<forsendelse.getEpostvarsel().getDageretter().size();x++){
						dageretterliste.add(new Integer(forsendelse.getEpostvarsel().getDageretter().get(x)));
					}
					
					epostvarsel = EpostVarsel.builder(forsendelse.getEpostvarsel().getEpostadresse(), 
																  forsendelse.getEpostvarsel().getVarslingstekst())
											  .varselEtterDager(dageretterliste)
											  .build();
					sendepostvarsel = true;
				}
			} catch (Exception e) {
				log.error("Feilet i oppretting av Epost varsel: " + e.getMessage());
				no.si.sdp.utils.log.Logger.logError("Feilet i oppretting av Epost varsel: " + e.getMessage(), forsendelse.getConversationid());
			}
			
			try {
				if(forsendelse.getSmsvarsel()!=null&&!forsendelse.getSmsvarsel().getMobilnummer().equals(null)){					
					List<Integer> dageretterliste = new ArrayList<Integer>();
					for(int x=0;x<forsendelse.getSmsvarsel().getDageretter().size();x++){
						dageretterliste.add(new Integer(forsendelse.getSmsvarsel().getDageretter().get(x)));
					}
					smsvarsel = SmsVarsel.builder(forsendelse.getSmsvarsel().getMobilnummer(), forsendelse.getSmsvarsel().getVarseltekst())
										  .varselEtterDager(dageretterliste)
										  .build();
					sendsmsvarsel = true;
				}
			} catch (Exception e) {
				log.error("Feilet i oppretting av SMS varsel: " + e.getMessage());
				no.si.sdp.utils.log.Logger.logError("Feilet i oppretting av SMS varsel: " + e.getMessage(), forsendelse.getConversationid());
			}
			
			try{
				behandlingsansvarlig = Behandlingsansvarlig.builder(forsendelse.getBehandlingsansvarlig().getOrganisasjonsnummer()).
						avsenderIdentifikator(forsendelse.getBehandlingsansvarlig().getAvsenderIdentifikator()).build();
			}catch(Exception e){
				behandlingsansvarlig = Behandlingsansvarlig.builder(AVSENDER_ORGNUMMER).build();
				log.error("Feilet med bygging av behandlingsansvaarlig. Sender default.");
			}
			
			if(sendsmsvarsel && sendepostvarsel){
				post = DigitalPost.builder(mottaker, forsendelse.getDigitalpost().getIkkeSensitivTittel())
						   .sikkerhetsnivaa(Sikkerhetsnivaa.NIVAA_4)
						   //.virkningsdato(new Date(System.currentTimeMillis()))
						   .virkningsdato(forsendelse.getDigitalpost().getVirkningsdato())
						   .epostVarsel(epostvarsel)
						   .smsVarsel(smsvarsel)
						   .build();
			}else if(sendepostvarsel){
				post = DigitalPost.builder(mottaker, forsendelse.getDigitalpost().getIkkeSensitivTittel())
						   .sikkerhetsnivaa(Sikkerhetsnivaa.NIVAA_4)
						   .virkningsdato(forsendelse.getDigitalpost().getVirkningsdato())
						   .epostVarsel(epostvarsel)
						   .build();				
			}else if(sendsmsvarsel){
				post = DigitalPost.builder(mottaker, forsendelse.getDigitalpost().getIkkeSensitivTittel())
						   .sikkerhetsnivaa(Sikkerhetsnivaa.NIVAA_4)
						   .virkningsdato(forsendelse.getDigitalpost().getVirkningsdato())
						   .smsVarsel(smsvarsel)
						   .build();				
			}else{
				post = DigitalPost.builder(mottaker, forsendelse.getDigitalpost().getIkkeSensitivTittel())
						   .sikkerhetsnivaa(Sikkerhetsnivaa.NIVAA_4)
						   .virkningsdato(forsendelse.getDigitalpost().getVirkningsdato())
						   .build();				
			}
			//TODO Legg til støtte for vedlegg!
			dokumentpakke = Dokumentpakke.builder(Dokument.builder(forsendelse.getDokument().getBeskyttettittel(), 
																	"brev.pdf",
																	new ByteArrayInputStream(forsendelse.getDokument().getDokument())).build())
																	.build();
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return Forsendelse.digital(behandlingsansvarlig, post, dokumentpakke).konversasjonsId(forsendelse.getConversationid()).spraakkode(forsendelse.getSpraakkode()).mpcId(MCPID).build();
    }
    
    private Mottaker getMottaker(ForsendelseBean forsendelse){
    	
        //Person person = //Hent personinfo fra noe sted... personRespons.getPerson().get(0);
    	String personidentifikator = forsendelse.getMottaker().getPersonidentifikator();//"01014337314";
        String postkasseadresse = forsendelse.getMottaker().getSikkerDigitalPostAdresse().getPostkasseadresse();//"testbruker.f.twdpar#4T1X";//person.getSikkerDigitalPostAdresse().getPostkasseadresse();
        //Sertifikat sertifikat = Sertifikat.fraByteArray(forsendelse.getMottaker().getX509Certificate().getBytes());
        Sertifikat sertifikat = Sertifikat.fraByteArray(forsendelse.getMottaker().getX509Sertifikat());
        //Sertifikat sertifikat = Sertifikat.fraBase64X509String(forsendelse.getMottaker().getX509Sertifikat());
        
        String orgnummerPostkasse = forsendelse.getMottaker().getSikkerDigitalPostAdresse().getPostkasseleverandoerAdresse();//"984661185";//person.getSikkerDigitalPostAdresse().getPostkasseleverandoerAdresse();
    	
    	return Mottaker.builder(personidentifikator, postkasseadresse, sertifikat, orgnummerPostkasse).build();
    }
    
    //Kun for test!
    private static Sertifikat mottakerSertifikat() {
        return Sertifikat.fraBase64X509String("LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tDQpNSUlFN2pDQ0E5YWdBd0lCQWdJS0dCWnJtRWd6VEh6ZUpqQU5CZ2txaGtpRzl3MEJBUXNGQURCUk1Rc3dDUVlEDQpWUVFHRXdKT1R6RWRNQnNHQTFVRUNnd1VRblY1Y0dGemN5QkJVeTA1T0RNeE5qTXpNamN4SXpBaEJnTlZCQU1NDQpHa0oxZVhCaGMzTWdRMnhoYzNNZ015QlVaWE4wTkNCRFFTQXpNQjRYRFRFME1EUXlOREV5TXpBMU1Wb1hEVEUzDQpNRFF5TkRJeE5Ua3dNRm93VlRFTE1Ba0dBMVVFQmhNQ1RrOHhHREFXQmdOVkJBb01EMUJQVTFSRlRpQk9UMUpIDQpSU0JCVXpFWU1CWUdBMVVFQXd3UFVFOVRWRVZPSUU1UFVrZEZJRUZUTVJJd0VBWURWUVFGRXdrNU9EUTJOakV4DQpPRFV3Z2dFaU1BMEdDU3FHU0liM0RRRUJBUVVBQTRJQkR3QXdnZ0VLQW9JQkFRQ0xDeFU0b0JodEdtSnhYWldiDQpkV2R6TzJ1QTNlUk5XL2tQZGRkTDFIWWwxaVhMVi9nK0gyUTBFTGFkV0xnZ2tTKzFrT2Q4L2pLeEVOKytiaU1tDQptRHFxQ1diek5kbUVkMWo0bGN0U2xINk03dHQweXdtWElZZFpNejVreGNMQU1OWHNhcW5QZGlrSTl1UEpaUUVMDQozS2M4aFhoWElTdnB6UDdnWU92S0hnNDF1Q3h1MXhDWlFPTTZwVGxOYnhlbUJZcXZFUzRmUmgyeHZCOWFNandrDQpCNE56OGpySXN5b1BJODlpMDVPbUdNa0k1QlBadDhOVGE0MFlmM3lVK1NRRUNXMEdXYWxCNWN4YVRNZUIwMXRxDQpzbFV6QkpQVjNjUXgrQWh0UUc0aGtPaFFuQU1ESnJhbVNQVnR3YkVucU9qUStseU5tZzVHUTRGSk8wMkFwS0pUDQpaRFRIQWdNQkFBR2pnZ0hDTUlJQnZqQUpCZ05WSFJNRUFqQUFNQjhHQTFVZEl3UVlNQmFBRkQrdTlYZ0xrcU53DQpJRFZmV3ZyM0pLQlNBZkJCTUIwR0ExVWREZ1FXQkJRMWdzSmZWQzdLWUdpV1ZMUDdad3pwcHlWWVRUQU9CZ05WDQpIUThCQWY4RUJBTUNCTEF3RmdZRFZSMGdCQTh3RFRBTEJnbGdoRUlCR2dFQUF3SXdnYnNHQTFVZEh3U0JzekNCDQpzREEzb0RXZ000WXhhSFIwY0RvdkwyTnliQzUwWlhOME5DNWlkWGx3WVhOekxtNXZMMk55YkM5Q1VFTnNZWE56DQpNMVEwUTBFekxtTnliREIxb0hPZ2NZWnZiR1JoY0RvdkwyeGtZWEF1ZEdWemREUXVZblY1Y0dGemN5NXVieTlrDQpZejFDZFhsd1lYTnpMR1JqUFU1UExFTk9QVUoxZVhCaGMzTWxNakJEYkdGemN5VXlNRE1sTWpCVVpYTjBOQ1V5DQpNRU5CSlRJd016OWpaWEowYVdacFkyRjBaVkpsZG05allYUnBiMjVNYVhOME1JR0tCZ2dyQmdFRkJRY0JBUVIrDQpNSHd3T3dZSUt3WUJCUVVITUFHR0wyaDBkSEE2THk5dlkzTndMblJsYzNRMExtSjFlWEJoYzNNdWJtOHZiMk56DQpjQzlDVUVOc1lYTnpNMVEwUTBFek1EMEdDQ3NHQVFVRkJ6QUNoakZvZEhSd09pOHZZM0owTG5SbGMzUTBMbUoxDQplWEJoYzNNdWJtOHZZM0owTDBKUVEyeGhjM016VkRSRFFUTXVZMlZ5TUEwR0NTcUdTSWIzRFFFQkN3VUFBNElCDQpBUUNlNjdVT1ovVlN3Y0gyb3YxY09TYVdzbEw3Sk5mcWh5TlpXR3BmZ1gxYzBHaCtLa08zZVZrTVNvenBnWDZNDQo0ZWVXQldKR0VMTWlWTjFMaE5hR3hCVTlUQk1kZVEzU3FLMjE5VzZEWFJKMnljQnRhVndRMjZWNXRXS1JONFVsDQpSb3ZZWWlZK25NTHg5VnJMT0Q0dW9QNmZtOUdFNUZqMHZTTU1Qdk9FWGkwTnNOKzhNVW0zSFdvQmVVQ0x5RnBlDQo3L0VQc1MvV3VkNWJiMGFzL0Uyekl6dFJvZHhmTnNvaVhOdldhUDJaaVBXRnVuSWpLMUgvOEVja3RFVzFwYWlQDQpkOEFaZWsvUVFvRzBNS1BmUElKdXFIK1dKVTNhOEo4ZXBNRHlWZmFlays0K2w5WE9lS3dWWE5TT1AvSlN3Z3BPDQpKTnpUZGFET00rdVZ1azc1bjIxOTFGZDcNCi0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0NCg==");
    }
    
}
