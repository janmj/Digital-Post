package no.si.sdp.utils;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MFServices {

	public MFServices() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void saveForsendelse(String conversationid, String xmlforsendelse)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdp.forsendelser(conversationid, forsendelse) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, conversationid);
			pstmt.setBytes(2, xmlforsendelse.getBytes("UTF-8"));
			pstmt.execute();
		} catch (Exception e) {
			throw new Exception("Feilet i lagring av forsendelse: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
		
	}
	
	public void saveKvitteringXML(String conversationid, String kvitteringsxml)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdp.kvitteringer(conversationid, kvittering) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, conversationid);
			pstmt.setBytes(2, kvitteringsxml.getBytes("UTF-8"));
			pstmt.execute();
		} catch (Exception e) {
			throw new Exception("Feilet i lagring av kvittering: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
		
	}

	public void saveKvitteringText(String conversationid, String kvitteringtxt)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdp.kvitteringer(conversationid, kvittering_text) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, conversationid);
			pstmt.setString(2, kvitteringtxt);
			pstmt.execute();
		} catch (Exception e) {
			throw new Exception("Feilet i lagring av kvittering: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
		
	}
	
	public ArrayList<String> getQuedForsendelser()throws Exception{
		ArrayList<String> retval = new ArrayList<String>();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select forsendelsesid from sdp.forsendelser where status = 0";
		try {
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				retval.add(rs.getString("forsendelsesid"));
			}
		} catch (Exception e) {
			throw new Exception("Feilet i henting av forsendelsesliste: " + e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e2) {}
		}
		return retval;
	}
	
	public String getForsendelseXML(String forsendelseid)throws Exception{
		String retval="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select forsendelse from sdp.forsendelser where forsendelsesid = ?";		
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, new Long(forsendelseid).longValue());
			rs = pstmt.executeQuery();
			while(rs.next()){
				byte[] tmpb = rs.getBytes("forsendelse");
				retval = new String(tmpb,"UTF-8");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getForsendelseXML: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}	
		return retval;
	}
	
	public String getConversationid(String forsendelseid)throws Exception{
		String retval="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select conversationid from sdp.forsendelser where forsendelsesid = ?";		
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, new Long(forsendelseid).longValue());
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = rs.getString("conversationid");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getConversationid: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}	
		return retval;
	}
	
	public ArrayList<String> getKvitteringer(String conversationid)throws Exception{
		ArrayList<String> retval = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select kvittering_text from sdp.kvitteringer where conversationid= ? and status = 0";		
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, conversationid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String kvittXML = rs.getString("kvittering_text");
				retval.add(kvittXML);
			}
		} catch (Exception e) {
			throw new Exception("Feilet i getKvitteringer: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}	
		
		return retval;
	}
	
	public void updateForsendelsesStatus(String newstatus, String conversationid)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update sdp.forsendelser set status = ? where conversationid = ?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(newstatus).intValue());
			pstmt.setString(2, conversationid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Feilet i oppdatering av ForsendelsesStatus for conversationid: " + conversationid + " Med feilmelding: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
	}
	
	public void updateKvitteringsStatus(String newstatus, String conversationid)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update sdp.kvitteringer set status = ? where conversationid = ?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(newstatus).intValue());
			pstmt.setString(2, conversationid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Feilet i oppdatering av KvitteringsStatus for conversationid: " + conversationid + " Med feilmelding: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
	}
	/*
	private Connection getConncetion()throws Exception{
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SDP", "sdp", "sdp");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return conn;
	}
	*/
	
	private Connection dbConnect()throws Exception{
		Connection con = null;
		InitialContext ctx = new InitialContext();
		try {
			DataSource ds = (DataSource)ctx.lookup("java:/SDPDS");
			con = ds.getConnection();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return con;
	}
}
