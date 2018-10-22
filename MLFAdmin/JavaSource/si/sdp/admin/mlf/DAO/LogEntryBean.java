package si.sdp.admin.mlf.DAO;

import java.io.Serializable;

public class LogEntryBean implements Serializable {
	private static final long serialVersionUID = 690482841013703528L;
	
	private String logid;
	private String logtime;
	private String error;
	private String message;
	private String messageid;
	private String errorid;
	private String conversationid;
	
	public LogEntryBean() {
		// TODO Auto-generated constructor stub
	}

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getErrorid() {
		return errorid;
	}

	public void setErrorid(String errorid) {
		this.errorid = errorid;
	}

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

}
