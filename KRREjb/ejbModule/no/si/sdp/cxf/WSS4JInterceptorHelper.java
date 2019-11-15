package no.si.sdp.cxf;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;


public class WSS4JInterceptorHelper {

	private static final WSS4JInInterceptor wss4jininterceptor;
	private static final WSS4JOutInterceptor wss4outinterceptor;
	
	static{
		final Map<String, Object> outProps = new HashMap<String, Object>();
		final Map<String, Object> inProps = new HashMap<String, Object>();
		
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP + " " + WSHandlerConstants.SIGNATURE);
		outProps.put(WSHandlerConstants.USER, "statens innkrevingssentral");
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientKeystorePasswordCallbackHandler.class.getName());
		outProps.put(WSHandlerConstants.SIG_PROP_FILE, "client_sec.properties");
		
		//outProps.put(WSHandlerConstants.SIG_KEY_ID, "X509KeyIdentifier");
		outProps.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
		
		outProps.put(WSHandlerConstants.SIGNATURE_PARTS, "{}{}Body;{}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp}");
		outProps.put(WSHandlerConstants.SIG_ALGO, "http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
		
		inProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.TIMESTAMP + " "  + WSHandlerConstants.SIGNATURE + " " + WSHandlerConstants.ENCRYPT);
		inProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientKeystorePasswordCallbackHandler.class.getName());
		inProps.put(WSHandlerConstants.SIG_PROP_FILE, "server_sec_difi.properties");
		inProps.put(WSHandlerConstants.DEC_PROP_FILE, "client_sec.properties");
		
		wss4outinterceptor = new WSS4JOutInterceptor(outProps);
		wss4jininterceptor = new WSS4JInInterceptor(inProps);
		
	}
	
	public static void addWs4jInterceptors(InterceptorProvider interceptorProvider){
		interceptorProvider.getInInterceptors().add(wss4jininterceptor);
		interceptorProvider.getInInterceptors().add(new LoggingInInterceptor());
		interceptorProvider.getOutInterceptors().add(wss4outinterceptor);
		interceptorProvider.getOutInterceptors().add(new LoggingInInterceptor());
	}
}
