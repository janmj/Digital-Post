package si.sdp.admin.mlf.jsf.beans;

import java.util.ArrayList;

import org.jboss.logging.Logger;

import si.sdp.admin.mlf.DAO.PropertyBean;
import si.sdp.admin.mlf.utils.DBHandler;

public class ConfiglistBean {
	Logger log = Logger.getLogger(ConfiglistBean.class);
	private ArrayList<PropertyBean> propertieslist;
	
	public ConfiglistBean() {
		// TODO Auto-generated constructor stub
	}

	private void loadProperties(){
		DBHandler dbh = new DBHandler();
		try {
			setPropertieslist(dbh.getProperties());
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public ArrayList<PropertyBean> getPropertieslist() {
		loadProperties();
		return propertieslist;
	}

	public void setPropertieslist(ArrayList<PropertyBean> propertieslist) {
		this.propertieslist = propertieslist;
	}
	
}
