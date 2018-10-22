package no.si.sdp.utils.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbLogHandler {

	public DbLogHandler() {
		// TODO Auto-generated constructor stub
	}

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
	
	public void LogMessage(String message, String conversationid){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdplog.log (message, conversationid) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, message);
			pstmt.setString(2, conversationid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Feilet i logging av melding: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			}catch (SQLException e) {}
		}
		
	}

	public void LogMessageType(String messageid, String conversationid){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdplog.log (messageid, conversationid) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(messageid).intValue());
			pstmt.setString(2, conversationid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Feilet i logging av melding: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			}catch (SQLException e) {}
		}
		
	}

	public void LogError(String error, String conversationid){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdplog.log (error, conversationid) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, error);
			pstmt.setString(2, conversationid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Feilet i logging av feil: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			}catch (SQLException e) {}
		}
		
	}
	
	public void LogErrorType(String errorid, String conversationid){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "insert into sdplog.log (errorid, conversationid) values(?,?)";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(errorid).intValue());
			pstmt.setString(2, conversationid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Feilet i logging av melding: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			}catch (SQLException e) {}
		}
		
	}


}
