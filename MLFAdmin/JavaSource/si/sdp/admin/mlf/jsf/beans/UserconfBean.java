package si.sdp.admin.mlf.jsf.beans;

import javax.faces.context.FacesContext;

public class UserconfBean {
	String logout;
	public UserconfBean() {
		// TODO Auto-generated constructor stub
	}
	
	public void logout(){
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	public String getLogout() {
		logout();
		return logout;
	}

	public void setLogout(String logout) {
		this.logout = logout;
	}
	
}
