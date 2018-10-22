package si.sdp.admin.mlf.jsf.beans;

import java.util.ArrayList;

import org.jboss.logging.Logger;

import si.sdp.admin.mlf.DAO.LogfileBean;
import si.sdp.admin.mlf.utils.LogfileReader;

public class LogListBean {
	Logger log = Logger.getLogger(LogListBean.class);
	private ArrayList<LogfileBean> logfileslist;
	
	public LogListBean() {
	}

	private void getLogfiles(){
		LogfileReader lfr = new LogfileReader();
		try {
			setLogfileslist(lfr.getLogfiles());
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public ArrayList<LogfileBean> getLogfileslist() {
		getLogfiles();
		return logfileslist;
	}

	public void setLogfileslist(ArrayList<LogfileBean> logfileslist) {
		this.logfileslist = logfileslist;
	}
	
}
