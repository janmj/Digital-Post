package si.sdp.admin.mlf.DAO;

import java.io.Serializable;

public class LogfileBean implements Serializable{

	private static final long serialVersionUID = 6900723572069980496L;
	private String logfilename;
	private String logfilepath;
	private String logfiledata;
	
	public LogfileBean() {
	}

	public String getLogfilename() {
		return logfilename;
	}

	public void setLogfilename(String logfilename) {
		this.logfilename = logfilename;
	}

	public String getLogfilepath() {
		return logfilepath;
	}

	public void setLogfilepath(String logfilepath) {
		this.logfilepath = logfilepath;
	}

	public String getLogfiledata() {
		return logfiledata;
	}

	public void setLogfiledata(String logfiledata) {
		this.logfiledata = logfiledata;
	}
}
