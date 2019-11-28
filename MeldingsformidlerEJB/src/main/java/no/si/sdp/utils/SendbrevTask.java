package no.si.sdp.utils;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import no.si.sdp.service.util.MLFServiceUtilRemote;

import org.jboss.logging.Logger;
//import org.jboss.varia.scheduler.Schedulable;

//public class SendbrevTask implements Schedulable{
public class SendbrevTask{
	static Logger log = Logger.getLogger(SendbrevTask.class);
	
	public SendbrevTask() {
		// TODO Auto-generated constructor stub
	}

	
	public void perform(Date arg0, long arg1) {
		sendForsendelser();
	}
	
	public static void main(String[] args){
		SendbrevTask test = new SendbrevTask();
		try {
			test.sendForsendelser();
		} catch (Exception e) {
			log.error(e.getMessage());
			//System.out.println(e.getMessage());
		}
	}
	
	private void sendForsendelser(){
		try {
			Context ctx = getContext();
			MLFServiceUtilRemote sdputil = (MLFServiceUtilRemote)ctx.lookup("MFEjbEAR/MeldingsformidlerEJB/MLFServiceUtil!no.si.sdp.service.util.MLFServiceUtilRemote"); 
			sdputil.sendForsendelser();
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			//System.err.println(e.getMessage());
		}
	}
	
	private Context getContext()throws NamingException{
		Context ctx = null;
		try {
			Properties prop = new Properties();
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			prop.put("jboss.naming.client.ejb.context", true);
			prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			prop.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			ctx = new InitialContext(prop);
		} catch (Exception e) {
			throw new NamingException(e.getMessage());
		}
		return ctx;
	}
}
