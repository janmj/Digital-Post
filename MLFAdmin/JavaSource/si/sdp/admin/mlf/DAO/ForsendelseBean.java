package si.sdp.admin.mlf.DAO;

import java.io.Serializable;

public class ForsendelseBean implements Serializable{
	
	private static final long serialVersionUID = 4693996423360290432L;
	private String forsendelsesid;
	private String conversationid;
	private String forsendelse;
	private String status;
	private String statusdesc;	
	private String odato;
	private String edato;
	
	public ForsendelseBean() {
	}

	public String getForsendelsesid() {
		return forsendelsesid;
	}

	public void setForsendelsesid(String forsendelsesid) {
		this.forsendelsesid = forsendelsesid;
	}

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

	public String getForsendelse() {
		return forsendelse;
	}

	public void setForsendelse(String forsendelse) {
		this.forsendelse = forsendelse;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}

	public String getOdato() {
		return odato;
	}

	public void setOdato(String odato) {
		this.odato = odato;
	}

	public String getEdato() {
		return edato;
	}

	public void setEdato(String edato) {
		this.edato = edato;
	}
	
}
