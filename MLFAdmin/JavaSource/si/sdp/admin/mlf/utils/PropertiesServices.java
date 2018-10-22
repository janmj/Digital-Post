package si.sdp.admin.mlf.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PropertiesServices {

	public PropertiesServices() {
	}

	public String getPropertyValue(String property)throws Exception{
		String retval="";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select value from sdp.properties where property=?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, property);
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = rs.getString("value");
			}
		} catch (Exception e) {
			throw new Exception("Feilet i henting av value for property: " + property + " Med feilmelding: " + e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
		return retval;
	}
	
	private Connection dbConnect()throws Exception{
		Connection retval = null;
		InitialContext ctx = new InitialContext();
		try {
			DataSource ds = (DataSource)ctx.lookup("java:/SDPDS");
			retval = ds.getConnection();
		} catch (Exception e) {
			throw new Exception("Feilet i database connect for PropertiesServices. Feilmelding: " + e.getMessage());
		}
		return retval;
	}
}
