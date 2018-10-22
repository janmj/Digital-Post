package no.si.sdp.utils;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import no.si.sdp.service.util.MLFServiceUtilRemote;

import org.jboss.logging.Logger;
import org.jboss.varia.scheduler.Schedulable;


public class HentKvitteringerTask implements Schedulable {
	static Logger log = Logger.getLogger(HentKvitteringerTask.class);
	public HentKvitteringerTask() {
		// TODO Auto-generated constructor stub
	}

	public void perform(Date arg0, long arg1) {
		hentkvitteringer();
	}
	public static void main(String[] args){
		HentKvitteringerTask test = new HentKvitteringerTask();
		try {
			test.hentkvitteringer();
		} catch (Exception e) {
			log.error(e.getMessage());
			//System.out.println(e.getMessage());
		}
	}
	
	private void hentkvitteringer(){
		try {
			Context ctx = getContext();
			MLFServiceUtilRemote sdputil = (MLFServiceUtilRemote)ctx.lookup("MFEjbEAR/MeldingsformidlerEJB/MLFServiceUtil!no.si.sdp.service.util.MLFServiceUtilRemote"); 
			sdputil.hentKvitteringer();
		} catch (NamingException e) {
			log.info(e);
		} catch (Exception e) {
			log.error(e);
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
