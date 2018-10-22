package si.sdp.admin.mlf.jsf.beans;

import java.util.ArrayList;
import java.util.Date;

import si.sdp.admin.mlf.DAO.ForsendelseBean;
import si.sdp.admin.mlf.utils.DBHandler;

public class ForsendelsesListBean {
	
	private ArrayList<ForsendelseBean> forsendelserlist;
	
	public ForsendelsesListBean() {
	}
	
	private void getForsendelser(){
		DBHandler dbh = new DBHandler();
		try {
			setForsendelserlist(dbh.getForsendelser(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public ArrayList<ForsendelseBean> getForsendelserlist() {
		getForsendelser();
		return forsendelserlist;
	}

	public void setForsendelserlist(ArrayList<ForsendelseBean> forsendelserlist) {
		this.forsendelserlist = forsendelserlist;
	}

}
