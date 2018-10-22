package no.si.sdp.utils;

import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import no.si.sdp.service.util.SDPServiceUtil;
import no.si.sdp.service.util.SDPServiceUtilRemote;

import org.jboss.logging.Logger;
//import org.jboss.varia.scheduler.Schedulable;

//public class UpdateKRRTask implements Schedulable{
public class UpdateKRRTask{
	Logger log = Logger.getLogger(UpdateKRRTask.class);
	
	public void perform(Date arg0, long arg1) {
		updateKRR();
	}

	public UpdateKRRTask() {
	}

	private void updateKRR(){
		try {
			Context ctx = getContext();
			SDPServiceUtilRemote krrutil = (SDPServiceUtilRemote)ctx.lookup("SDPServiceUtil/remote");
			krrutil.updateKRR();
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
	/*
	private Context getContext()throws NamingException{
		Context ctx = null;
		try {
			Properties prop = new Properties();
			prop.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
			prop.put("java.naming.factory.url.pkgs", "=org.jboss.naming:org.jnp.interfaces");
			prop.put("java.naming.provider.url", "localhost:1099");
			ctx = new InitialContext(prop);
		} catch (Exception e) {
			throw new NamingException(e.getMessage());
		}
		return ctx;
	}
  */
}
