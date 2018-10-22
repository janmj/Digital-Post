package si.sdp.admin.mlf.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import si.sdp.admin.mlf.DAO.ForsendelseBean;
import si.sdp.admin.mlf.DAO.KvitteringBean;
import si.sdp.admin.mlf.DAO.LogEntryBean;
import si.sdp.admin.mlf.DAO.PropertyBean;

public class DBHandler {

	public DBHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<LogEntryBean>getTodaysMessages()throws Exception{
		ArrayList<LogEntryBean> retval = new ArrayList<LogEntryBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT distinct(log.logid),log.logtime,log.message,log.messageid,logmessages.messagedesc, log.conversationid "
				  + "FROM "
				  + "sdplog.log, sdplog.logmessages "
				  + "WHERE "
				  + "logtime>= now() - interval '24 hours' AND log.errorid=0  AND log.messageid = logmessages.messageid AND log.error='' "
				  + "order by log.logtime";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				LogEntryBean tmplog = new LogEntryBean();
				tmplog.setLogid(rs.getString("logid"));
				tmplog.setLogtime(rs.getString("logtime"));
				tmplog.setConversationid(rs.getString("conversationid"));
				tmplog.setMessageid(rs.getString("messageid"));
				if(tmplog.getMessageid().equals("0")){
					tmplog.setMessage(rs.getString("message"));					
				}else{
					tmplog.setMessage(rs.getString("messagedesc"));
				}
				retval.add(tmplog);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getTodaysMessages: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		
		return retval;
	}

	public ArrayList<LogEntryBean>getTodaysErrors()throws Exception{
		ArrayList<LogEntryBean> retval = new ArrayList<LogEntryBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT distinct(log.logid),log.logtime,log.error,log.errorid,logerrors.errdesc, log.conversationid "
					  + "FROM "
					  + "sdplog.log, sdplog.logerrors "
					  + "WHERE "
					  + "logtime >= now() - interval '24 hours' AND log.errorid = logerrors.errorid AND log.messageid = 0 AND log.message='' "
					  + "order by log.logtime";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				LogEntryBean tmplog = new LogEntryBean();
				tmplog.setLogid(rs.getString("logid"));
				tmplog.setConversationid(rs.getString("conversationid"));
				tmplog.setLogtime(rs.getString("logtime"));
				tmplog.setErrorid(rs.getString("errorid"));
				if(tmplog.getErrorid().equals("0")){
					tmplog.setMessage(rs.getString("error"));
					tmplog.setError("klikk for feilmelding..");
				}else{
					tmplog.setError(rs.getString("errdesc"));
				}
				retval.add(tmplog);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getTodaysErrors: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		
		return retval;
	}
	
	public ArrayList<PropertyBean> getProperties()throws Exception{
		ArrayList<PropertyBean> retval = new ArrayList<PropertyBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * from sdp.properties order by id";
		try{
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				PropertyBean prop = new PropertyBean();
				prop.setId(rs.getString("id"));
				prop.setProperty(rs.getString("property"));
				prop.setValue(rs.getString("value"));
				prop.setBeskrivelse(rs.getString("beskrivelse"));
				prop.setEdato(rs.getString("edato"));
				prop.setOdato(rs.getString("odato"));
				retval.add(prop);
			}
		}catch(Exception e){
			throw new Exception("Feilet i henting av properties: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}
	
	public void updateProperty(String id, String field, String newvalue)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update sdp.properties set "+ field  +"=?" + "where id=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newvalue);
			pstmt.setInt(2, new Integer(id).intValue());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Feilet i updateProperty: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
	}
	
	public void newProperty(String property, String value, String description)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql ="insert into sdp.properties(property,value,beskrivelse) values(?,?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, property);
			pstmt.setString(2, value);
			pstmt.setString(3, description);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Feilet i oppretting av ny property: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
	}
	
	public void deleteProperty(String id)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "delete from sdp.properties where id=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(id).intValue());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Feilet i sletting av property: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
	}
	
	public String getAntMeldinger(Date date)throws Exception{
		String retval = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(forsendelsesid) from sdp.forsendelser where date(odato)=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(date.getTime()));
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = rs.getString("count");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getAntMeldinger: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}

	public String getAntKvitteringer(Date date)throws Exception{
		String retval = "";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(kvitteringsid) from sdp.kvitteringer where date(odato)=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(date.getTime()));
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = rs.getString("count");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getAntKvitteringer: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;		
	}
	
	public String getLastMeldingDato()throws Exception{
		String retval = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select max(odato) from sdp.forsendelser";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				retval = rs.getString("max");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getLastMeldingDato: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}

	public String getLastKvitteringsdato()throws Exception{
		String retval = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select max(odato) from sdp.kvitteringer";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				retval = rs.getString("max");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getLastKvitteringsdato: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
		
	}
	
	public String getLastUavsent()throws Exception{
		String retval = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select max(odato) from sdp.forsendelser where status = 2";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				retval = rs.getString("max");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getLastUavsent: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}
	
	public String getAntuavsent()throws Exception{
		String retval = "";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select count(forsendelsesid) from sdp.forsendelser where status = 2";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				retval = rs.getString("count");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getAntuavsent: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;				
	}
	
	public void updateForsendelseStatus(String forsendelsesid, String newstatus)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update sdp.forsendelser set status=? where forsendelsesid=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(newstatus).intValue());
			pstmt.setLong(2, new Long(forsendelsesid).longValue());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Feilet i updateForsendelseStatus: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<ForsendelseBean> getForsendelser(Date dato)throws Exception{
		ArrayList<ForsendelseBean> retval = new ArrayList<ForsendelseBean>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select forsendelsesid,conversationid,status,description,odato,edato"
				  + " from sdp.forsendelser , sdp.forsendelser_status"
				  + " where date(odato)=?"
				  + " and forsendelser.status = forsendelser_status.id";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(dato.getTime()));
			rs = pstmt.executeQuery();
			while(rs.next()){
				ForsendelseBean tmp = new ForsendelseBean();
				tmp.setConversationid(rs.getString("conversationid"));
				tmp.setEdato(rs.getString("edato"));
				//tmp.setForsendelse(forsendelse);
				tmp.setForsendelsesid(rs.getString("forsendelsesid"));
				tmp.setOdato(rs.getString("odato"));
				tmp.setStatus(rs.getString("status"));
				tmp.setStatusdesc(rs.getString("description"));
				retval.add(tmp);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getForsendelser"+ e);
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}

	public ArrayList<ForsendelseBean> getFailedForsendelser()throws Exception{
		ArrayList<ForsendelseBean> retval = new ArrayList<ForsendelseBean>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql ="select forsendelsesid,conversationid,status,description,odato,edato"
				  + " from sdp.forsendelser , sdp.forsendelser_status"
				  + " where status = 2"
				  + " and forsendelser.status = forsendelser_status.id";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				ForsendelseBean tmp = new ForsendelseBean();
				tmp.setConversationid(rs.getString("conversationid"));
				tmp.setEdato(rs.getString("edato"));
				//tmp.setForsendelse(forsendelse);
				tmp.setForsendelsesid(rs.getString("forsendelsesid"));
				tmp.setOdato(rs.getString("odato"));
				tmp.setStatus(rs.getString("status"));
				tmp.setStatusdesc(rs.getString("description"));
				retval.add(tmp);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getFailedForsendelser"+ e);
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}
	
	public ArrayList<KvitteringBean> getKvitteringer(Date dato)throws Exception{
		ArrayList<KvitteringBean> retval = new ArrayList<KvitteringBean>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="select kvitteringsid,conversationid,status,odato,edato,kvittering_text,description"
				   +" from sdp.kvitteringer, sdp.kvitteringer_status"
				   +" where date(odato) = ?"
				   +" and kvitteringer.status=kvitteringer_status.id"
				   ;
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(dato.getTime()));
			rs = pstmt.executeQuery();
			while(rs.next()){
				KvitteringBean tmp = new KvitteringBean();
				tmp.setConversationid(rs.getString("conversationid"));
				tmp.setEdato(rs.getString("edato"));
				tmp.setKvittering(rs.getString("kvittering_text"));
				tmp.setKvitteringsid(rs.getString("kvitteringsid"));
				tmp.setOdato(rs.getString("odato"));
				tmp.setStatus(rs.getString("status"));
				tmp.setStatusdesc(rs.getString("description"));
				retval.add(tmp);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getKvitteringer"+ e);
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}

	public ArrayList<LogEntryBean>getConversationEvents(String conversationid)throws Exception{
		ArrayList<LogEntryBean> retval = new ArrayList<LogEntryBean>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT log.logid,log.logtime,log.message,log.error,logmessages.messagedesc,logerrors.errdesc,log.messageid,log.errorid,log.conversationid"
				  + " FROM sdplog.log,sdplog.logmessages,sdplog.logerrors"
				  + " WHERE log.messageid=logmessages.messageid AND log.errorid=logerrors.errorid and log.conversationid=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, conversationid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				LogEntryBean tmplog = new LogEntryBean();
				tmplog.setLogid(rs.getString("logid"));
				tmplog.setConversationid(rs.getString("conversationid"));
				tmplog.setLogtime(rs.getString("logtime"));
				tmplog.setErrorid(rs.getString("errorid"));
				if(tmplog.getErrorid().equals("0")){
					tmplog.setError(rs.getString("error"));					
				}else{
					tmplog.setError(rs.getString("errdesc"));
				}
				tmplog.setMessageid(rs.getString("messageid"));
				if(tmplog.getMessageid().equals("0")){
					tmplog.setMessage(rs.getString("message"));					
				}else{
					tmplog.setMessage(rs.getString("messagedesc"));										
				}
				retval.add(tmplog);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getConversationEvents: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		
		return retval;
	}
	
	
	private Connection dbConnect()throws Exception{
		Connection con = null;
		InitialContext ic = new InitialContext();
		try {
			DataSource ds = (DataSource)ic.lookup("java:/SDPAdminDS");
			con = ds.getConnection();
		} catch (Exception e) {
			throw new Exception("Feilet i å hente connect fra db poolen: " + e.getMessage());
		}
		return con;
	}
}
