package no.si.sdp.cxf;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import no.si.sdp.utils.PropertiesServices;
import no.si.sdp.utils.log.Logger;

//import org.apache.ws.security.WSPasswordCallback;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ClientKeystorePasswordCallbackHandler implements CallbackHandler {
	String alias = "statens innkrevingssentral";
	String password = "Kongen123";
	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		PropertiesServices prop = new PropertiesServices();
		try {
			alias = prop.getPropertyValue("KRR_CLIENT_KEYSTORE_ALIAS");
			password = prop.getPropertyValue("KRR_CLIENT_KEYSTORE_PW");
		} catch (Exception e) {
			Logger.logError("Feilet i henting av passord og eller ailas for klient keystore" + e.getMessage(), "");
		}
		
		for(Callback callback : callbacks){
			WSPasswordCallback wpc = (WSPasswordCallback) callback;
			if(wpc.getIdentifier().equals(alias)){
				wpc.setPassword(password);
				return;
			}
		}
	}	
}
