package si.sdp.admin.mlf.DAO;

import java.io.Serializable;

public class KvitteringBean implements Serializable{
	
	private static final long serialVersionUID = -5765474912511469052L;
	private String kvitteringsid;
	private String conversationid;
	private String kvittering;
	private String status;
	private String statusdesc;	
	private String odato;
	private String edato;
	private String kvittering_text;
		
	public KvitteringBean() {
		// TODO Auto-generated constructor stub
	}

	public String getKvitteringsid() {
		return kvitteringsid;
	}

	public void setKvitteringsid(String kvitteringsid) {
		this.kvitteringsid = kvitteringsid;
	}

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

	public String getKvittering() {
		return kvittering;
	}

	public void setKvittering(String kvittering) {
		this.kvittering = kvittering;
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

	public String getKvittering_text() {
		return kvittering_text;
	}

	public void setKvittering_text(String kvittering_text) {
		this.kvittering_text = kvittering_text;
	}
}
