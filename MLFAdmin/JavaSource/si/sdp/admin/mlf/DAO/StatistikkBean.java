package si.sdp.admin.mlf.DAO;

import java.io.Serializable;

public class StatistikkBean implements Serializable{

	private static final long serialVersionUID = -1481390836626379963L;
	
	private String antmeldinger;
	private String antkvitteringer;
	private String lastmlddato;
	private String lastkvitdato;
	private String antuavsent;
	private String lastuavsent;
	
	public StatistikkBean() {
		// TODO Auto-generated constructor stub
	}

	public String getAntmeldinger() {
		return antmeldinger;
	}

	public void setAntmeldinger(String antmeldinger) {
		this.antmeldinger = antmeldinger;
	}

	public String getAntkvitteringer() {
		return antkvitteringer;
	}

	public void setAntkvitteringer(String antkvitteringer) {
		this.antkvitteringer = antkvitteringer;
	}

	public String getLastmlddato() {
		return lastmlddato;
	}

	public void setLastmlddato(String lastmlddato) {
		this.lastmlddato = lastmlddato;
	}

	public String getLastkvitdato() {
		return lastkvitdato;
	}

	public void setLastkvitdato(String lastkvitdato) {
		this.lastkvitdato = lastkvitdato;
	}

	public String getAntuavsent() {
		return antuavsent;
	}

	public void setAntuavsent(String antuavsent) {
		this.antuavsent = antuavsent;
	}

	public String getLastuavsent() {
		return lastuavsent;
	}

	public void setLastuavsent(String lastuavsent) {
		this.lastuavsent = lastuavsent;
	}
}
