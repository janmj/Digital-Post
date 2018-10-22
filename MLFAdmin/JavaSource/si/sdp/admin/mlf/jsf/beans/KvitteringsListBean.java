package si.sdp.admin.mlf.jsf.beans;

import java.util.ArrayList;
import java.util.Date;

import si.sdp.admin.mlf.DAO.KvitteringBean;
import si.sdp.admin.mlf.utils.DBHandler;

public class KvitteringsListBean {
	
	private ArrayList<KvitteringBean> kvitteringslist;
	
	public KvitteringsListBean() {
	}
	
	private void getkvitteringerlist(){
		DBHandler dbh = new DBHandler();
		try {
			setKvitteringslist(dbh.getKvitteringer(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public ArrayList<KvitteringBean> getKvitteringslist() {
		getkvitteringerlist();
		return kvitteringslist;
	}

	public void setKvitteringslist(ArrayList<KvitteringBean> kvitteringslist) {
		this.kvitteringslist = kvitteringslist;
	}

	
	
}
