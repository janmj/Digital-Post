package si.sdp.admin.mlf.jsf.beans;

import java.util.ArrayList;
import java.util.Date;

import si.sdp.admin.mlf.DAO.ForsendelseBean;
import si.sdp.admin.mlf.utils.DBHandler;

public class FailedForsendelseListBean {
	
	private ArrayList<ForsendelseBean> failedforsendelserlist;
	private java.lang.String conversationid;
	
	public FailedForsendelseListBean() {
	}
	
	private void getForsendelser(){
		DBHandler dbh = new DBHandler();
		try {
			setFailedForsendelserlist(dbh.getFailedForsendelser());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public ArrayList<ForsendelseBean> getFailedForsendelserlist() {
		getForsendelser();
		return failedforsendelserlist;
	}

	public void setFailedForsendelserlist(ArrayList<ForsendelseBean> forsendelserlist) {
		this.failedforsendelserlist = forsendelserlist;
	}

	public java.lang.String getConversationid() {
		return conversationid;
	}

	public void setConversationid(java.lang.String conversationid) {
		this.conversationid = conversationid;
	}

}
