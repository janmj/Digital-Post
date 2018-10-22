package si.sdp.admin.mlf.jsf.beans;

import java.util.ArrayList;

import org.jboss.logging.Logger;

import si.sdp.admin.mlf.DAO.LogEntryBean;
import si.sdp.admin.mlf.utils.DBHandler;

public class EventslistBean {
	Logger log = Logger.getLogger(EventslistBean.class);
	
	private ArrayList<LogEntryBean> messageslist;
	private ArrayList<LogEntryBean> errorslist;
	private ArrayList<LogEntryBean> cidlist;
	private String conversationid;
	
	public EventslistBean() {
	}

	private void loadMessagesList(){
		DBHandler dbh = new DBHandler();
		try {
			setMessageslist(dbh.getTodaysMessages());
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	private void loadErrorsList(){
		DBHandler dbh = new DBHandler();
		try {
			setErrorslist(dbh.getTodaysErrors());
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public void loadCidList(){
		DBHandler dbh = new DBHandler();
		try {
			setCidlist(dbh.getConversationEvents(getConversationid()));
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public ArrayList<LogEntryBean> getMessageslist() {
		loadMessagesList();
		return messageslist;
	}

	public void setMessageslist(ArrayList<LogEntryBean> messageslist) {
		this.messageslist = messageslist;
	}

	public ArrayList<LogEntryBean> getErrorslist() {
		loadErrorsList();
		return errorslist;
	}

	public void setErrorslist(ArrayList<LogEntryBean> errorslist) {
		this.errorslist = errorslist;
	}

	public ArrayList<LogEntryBean> getCidlist() {
		loadCidList();
		return cidlist;
	}

	public void setCidlist(ArrayList<LogEntryBean> cidlist) {
		this.cidlist = cidlist;
	}

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}
	
}
