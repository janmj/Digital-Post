package si.sdp.admin.mlf.jsf.beans;

import java.util.Date;

import org.jboss.logging.Logger;

import si.sdp.admin.mlf.DAO.StatistikkBean;
import si.sdp.admin.mlf.utils.DBHandler;

public class StatistikklistBean {
	Logger log = Logger.getLogger(getClass());
	
	private StatistikkBean statistikkBean;
	
	public StatistikklistBean() {
		// TODO Auto-generated constructor stub
	}

	private void getStatistikk(){
		DBHandler dbh = new DBHandler();
		try {
			statistikkBean = new StatistikkBean();
			statistikkBean.setAntkvitteringer(dbh.getAntKvitteringer(new Date(System.currentTimeMillis())));
			statistikkBean.setAntmeldinger(dbh.getAntMeldinger(new Date(System.currentTimeMillis())));
			statistikkBean.setLastkvitdato(dbh.getLastKvitteringsdato());
			statistikkBean.setLastmlddato(dbh.getLastMeldingDato());
			statistikkBean.setAntuavsent(dbh.getAntuavsent());
			statistikkBean.setLastuavsent(dbh.getLastUavsent());
			setStatistikkBean(statistikkBean);
		} catch (Exception e) {
			log.error(e);
		}
		
	}

	public StatistikkBean getStatistikkBean() {
		getStatistikk();
		return statistikkBean;
	}

	public void setStatistikkBean(StatistikkBean statistikkBean) {
		this.statistikkBean = statistikkBean;
	}
	
}
