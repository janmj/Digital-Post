package si.sdp.admin.mlf.jsf.beans;

import java.io.Serializable;

import si.sdp.admin.mlf.utils.LogfileReader;

public class LogFileBean implements Serializable{
	private static final long serialVersionUID = -2371757799140561933L;
	private String logfile;
	private String logfilepath;

	public LogFileBean() {
		// TODO Auto-generated constructor stub
	}

	private void readLogfile(){
		LogfileReader lfr = new LogfileReader();
		try {
			setLogfile(lfr.getLogfile(getLogfilepath()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String getLogfile() {
		readLogfile();
		return logfile;
	}

	public void setLogfile(String logfile) {
		this.logfile = logfile;
	}

	public String getLogfilepath() {
		return logfilepath;
	}

	public void setLogfilepath(String logfilepath) {
		this.logfilepath = logfilepath;
	}
	
	
}
