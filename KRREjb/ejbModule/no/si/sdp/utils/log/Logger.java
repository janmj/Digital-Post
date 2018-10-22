package no.si.sdp.utils.log;

public final class Logger {
	
	private static DbLogHandler dblog = null;
	
	public Logger() {
	
	}
	
	public static void logMessage(String message, String conversationid){
		getDblog().LogMessage(message, conversationid);
	}
	
	public static void logMesageType(String messageid, String conversationid){
		getDblog().LogMessageType(messageid, conversationid);
	}
	
	public static void logError(String error, String conversationid){
		getDblog().LogError(error, conversationid);
	}
	
	public static void logErrorType(String errorid, String conversationid){
		getDblog().LogErrorType(errorid, conversationid);
	}
	
	public static void logErrorTypeAndMessage(String errorid, String conversationid, String error){
		getDblog().LogErrorTypeAndError(errorid, conversationid, error);
	}
	
	private static DbLogHandler getDblog(){
		try {
			if(dblog.equals(null)){
				dblog = new DbLogHandler();
			}
		} catch (Exception e) {
			dblog = new DbLogHandler();
		}
		return dblog;
	}
}
