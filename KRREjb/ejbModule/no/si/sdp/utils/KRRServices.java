package no.si.sdp.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.ejb.EJB;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.jboss.logging.Logger;
//import org.jboss.mx.util.MBeanServerLocator;
import org.jboss.security.util.MBeanServerLocator;

import no.difi.begrep.Epostadresse;
import no.difi.begrep.Kontaktinformasjon;
import no.difi.begrep.Mobiltelefonnummer;
import no.difi.begrep.Person;
import no.difi.begrep.Reservasjon;
import no.difi.begrep.SikkerDigitalPostAdresse;
import no.difi.begrep.Status;
import no.difi.kontaktinfo.xsd.oppslagstjeneste._14_05.HentEndringerRespons;
import no.si.sdp.KRRClient;

public class KRRServices {
	static Logger log = Logger.getLogger(KRRServices.class);
	//static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(KRRServices.class);
	public KRRServices() {
		try {
			//Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	public static void main(String[] args ){
		KRRServices test = new KRRServices();
		try {
			//test.makeLocalKRR();
			//test.updateKRR();			
			//test.maintainKRR();
			test.resetDBPool();
		} catch (Exception e) {
			//log.error(e);
			System.out.println(e.getMessage());
		}
	}
	
	public Person getLocalPerson(String personidentifikator) throws Exception{
		Person retval = null;
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql = "select * from sdp.person where personidentifikator = ?";		
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, personidentifikator);
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = new Person();
				retval.setPersonidentifikator(rs.getString("personidentifikator"));
				retval.setReservasjon(Reservasjon.fromValue(rs.getString("reservasjon")));
				retval.setStatus(Status.fromValue(rs.getString("status")));
				retval.setBeskrivelse(rs.getString("beskrivelse"));
				retval.setX509Sertifikat(rs.getBytes("x509sertifikat"));
				try {
					retval.setKontaktinformasjon(getKontaktinformasjon(personidentifikator));
				} catch (Exception e) {
					log.error("feilet i henting av kontaktinfo: " + e.getMessage());
				}
				try {
					SikkerDigitalPostAdresse adr = getSikkerDigitalPostAdresse(personidentifikator);
					if(!adr.getPostkasseadresse().isEmpty()){
						retval.setSikkerDigitalPostAdresse(getSikkerDigitalPostAdresse(personidentifikator));						
					}
				} catch (Exception e) {
					System.out.println("feilet i henting av SikkerDigitalPostAdresse: " + e.getMessage());
				}
			}
			//Tester om Personobjektet inneholder data. Hvis ikke lager vi et tomt, IKKE_REGISTRERT objekt
			try{
				if(retval.equals(null)&&retval!=null){
				}	
			}catch (NullPointerException e) {
				log.info("Lag lokalt tomt objekt");
				retval = new Person();
				retval.setPersonidentifikator(personidentifikator);
				retval.setStatus(Status.IKKE_REGISTRERT);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}
	
	private Kontaktinformasjon getKontaktinformasjon(String personidentifikator)throws Exception{
		Kontaktinformasjon kontaktinfo = new Kontaktinformasjon();
			try {
				kontaktinfo.setEpostadresse(getEpostadresse(personidentifikator));
				kontaktinfo.setMobiltelefonnummer(getMobiltelefonnummer(personidentifikator));
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		return kontaktinfo;
	}
	
	private Mobiltelefonnummer getMobiltelefonnummer(String personidentifikator)throws Exception{
		Mobiltelefonnummer retval = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select mobiltelefonnummer, mob_sistopdatert, mob_sistverifisert from sdp.kontaktinformasjon where personidentifikator = ?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, personidentifikator);
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = new Mobiltelefonnummer();
				try {
					retval.setSistOppdatert(getXmlGregoriancalendar(rs.getTimestamp("mob_sistopdatert")));
				} catch (Exception e) {}
				try {
					retval.setSistVerifisert(getXmlGregoriancalendar(rs.getTimestamp("mob_sistverifisert")));
				} catch (Exception e) {}
				retval.setValue(rs.getString("mobiltelefonnummer"));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}
	
	private Epostadresse getEpostadresse(String personidentifikator)throws Exception{
		Epostadresse retval = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select epostadresse, epost_sistoppdatert, epost_sistverifisert from sdp.kontaktinformasjon where personidentifikator = ?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, personidentifikator);
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = new Epostadresse();
				try {
					retval.setSistOppdatert(getXmlGregoriancalendar(rs.getTimestamp("epost_sistoppdatert")));
				} catch (Exception e) {}
				try {
					retval.setSistVerifisert(getXmlGregoriancalendar(rs.getTimestamp("epost_sistverifisert")));
				} catch (Exception e) {}
				retval.setValue(rs.getString("epostadresse"));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		
		return retval;
	}
	
	private SikkerDigitalPostAdresse getSikkerDigitalPostAdresse(String personidentifikator)throws Exception{
		SikkerDigitalPostAdresse retval = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from sdp.sikkerdigitalpostadresse where personidentifikator = ?";
		try {
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, personidentifikator);
			rs = pstmt.executeQuery();
			while(rs.next()){
				retval = new SikkerDigitalPostAdresse();
				retval.setPostkasseadresse(rs.getString("postkasseadresse"));
				retval.setPostkasseleverandoerAdresse(rs.getString("postkasseleverandoeradresse"));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		
		return retval;
	}
	/**
	 * Metode for vedlikehold av lokal kopi av kontakt og reservasjonsregisteret
	 * 
	 * @throws Exception
	 */
	public void maintainKRR()throws Exception{
		
		try {
			String newupdateref = "";
			//Henter sist brukte oppdaterings referanse
			String lastupdateref = getLastupdateRef();
			//får nyeste oppdateringsreferanse ved avsluttet oppdatering av nye personer
			newupdateref = insertLocalKRR(lastupdateref);
			//Bruker første referanse til å oppdatere med ny endringer
			updateKRR(lastupdateref);
			//Oppdaterer til nyeste oppdateringsreferanse
			updateLastupdateRef(newupdateref);
			//System.out.println("oppdaterte til ny updaterefid: " + newupdateref);
			log.info("KRR -- oppdaterte til ny updaterefid: " + newupdateref);
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		}
		
		
	}
	
	private void updateKRR(String lastupdateref)throws Exception{
		KRRClient client = new KRRClient();
		Connection con = null;
		PreparedStatement pstmtPers = null;
		PreparedStatement pstmtkontakt = null;
		PreparedStatement pstmtsdp = null;
		String sqlPersonUpdate = "update sdp.person set reservasjon=?, status=?, beskrivelse=?, x509sertifikat=? where personidentifikator = ?";
		String sqlKontaktinfoUpdate = "update sdp.kontaktinformasjon set mobiltelefonnummer=?, mob_sistopdatert=?, mob_sistverifisert=?, epostadresse=?, epost_sistoppdatert=?, epost_sistverifisert=? where personidentifikator=?";
		String sqlPostkasseUpdate = "update sdp.sikkerdigitalpostadresse set postkasseadresse=?, postkasseleverandoeradresse=? where personidentifikator=?";
		try {
			HentEndringerRespons resp = client.getEndringer(lastupdateref);
			ArrayList<Person> pers = new ArrayList<Person>(resp.getPerson());
			con = dbConnect();
			for(int x=0;x<pers.size();x++){
				Person tmpPers = pers.get(x);
				if(persExist(tmpPers.getPersonidentifikator())){
					//System.out.println("Person " + tmpPers.getPersonidentifikator() + " oppdateres..");
					log.info("KRR -- Person " + tmpPers.getPersonidentifikator() + " oppdateres..");
					//con = getConncetion();
					//con = dbConnect();
					/**
					 * Oppdaterer Person objektet.
					 */
					pstmtPers = con.prepareStatement(sqlPersonUpdate);
					try {
						pstmtPers.setString(1, tmpPers.getReservasjon().value());
					} catch (Exception e2) {
						pstmtPers.setString(1, "");
					}
					try {
						pstmtPers.setString(2, tmpPers.getStatus().value());
					} catch (Exception e2) {
						pstmtPers.setString(2, "");
					}
					try {
						pstmtPers.setString(3, tmpPers.getBeskrivelse());
					} catch (Exception e2) {
						pstmtPers.setString(3, "");
					}
					try {
						pstmtPers.setBytes(4, tmpPers.getX509Sertifikat());
					} catch (Exception e2) {
						pstmtPers.setBytes(4, new byte[0]);
					}
					pstmtPers.setString(5, tmpPers.getPersonidentifikator());
					try {
						pstmtPers.executeUpdate();
					} catch (SQLException sqle) {
						log.debug(sqle);
					}
					
					/**
					 * Oppdaterer kontaktinfo objektet
					 */
					pstmtkontakt = con.prepareStatement(sqlKontaktinfoUpdate);
					try {
						pstmtkontakt.setString(1, tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getValue());
					} catch (Exception e) {
						pstmtkontakt.setString(1, "");
					}
					try {
						pstmtkontakt.setTimestamp(2, new Timestamp(tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getSistOppdatert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(2, null);
					}
					try {
						pstmtkontakt.setTimestamp(3, new Timestamp(tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getSistVerifisert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(3, null);
					}
					try {
						pstmtkontakt.setString(4, tmpPers.getKontaktinformasjon().getEpostadresse().getValue());
					} catch (Exception e) {
						pstmtkontakt.setString(4, "");
					}
					try {
						pstmtkontakt.setTimestamp(5, new Timestamp(tmpPers.getKontaktinformasjon().getEpostadresse().getSistOppdatert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(5, null);
					}
					try {
						pstmtkontakt.setTimestamp(6, new Timestamp(tmpPers.getKontaktinformasjon().getEpostadresse().getSistVerifisert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(6, null);
					}
					
					pstmtkontakt.setString(7, tmpPers.getPersonidentifikator());
					try {
						pstmtkontakt.executeUpdate();
					} catch (SQLException sqle) {
						log.debug(sqle);
					}
					/**
					 * Oppdaterer Digital postkasse objektet
					 */
					pstmtsdp = con.prepareStatement(sqlPostkasseUpdate);
					try {
						pstmtsdp.setString(1, tmpPers.getSikkerDigitalPostAdresse().getPostkasseadresse());
					} catch (Exception e) {
						pstmtsdp.setString(1, null);
					}
					try {
						pstmtsdp.setString(2, tmpPers.getSikkerDigitalPostAdresse().getPostkasseleverandoerAdresse());
					} catch (Exception e) {
						pstmtsdp.setString(2, null);
					}
					
					pstmtsdp.setString(3, tmpPers.getPersonidentifikator());
					try {
						pstmtsdp.executeUpdate();
					} catch (SQLException sqle) {
						log.debug(sqle);
					}
				}else{
					//System.out.println(tmpPers.getPersonidentifikator() + " Fantes ikke");
					log.info("KRR -- " + tmpPers.getPersonidentifikator() + " Fantes ikke");
				}
				
			}
		} catch (Exception e) {
			throw new Exception();
		}finally{
			try {
				pstmtPers.close();
				pstmtkontakt.close();
				pstmtsdp.close();
				con.close();
			} catch (Exception e2) {;}
		}
		
	}
	
	private boolean persExist(String fonr) throws Exception{
		boolean retval = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from sdp.person where personidentifikator = ?";
		try {
			//con = getConncetion();
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fonr);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String tmpfonr = rs.getString("personidentifikator");
				if(tmpfonr.equalsIgnoreCase(fonr)){
					retval=true;
				}
			}
		} catch (Exception e) {
			log.debug(e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {;}
		}
		return retval;
	}
	
	
	public void makeLocalKRR() throws Exception{
		KRRClient client = null;
		boolean bDone = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtkontakt = null;
		PreparedStatement pstmtsdp = null;
		String lastupdateRef = "";
		String sqlmain = "insert into sdp.person values (?,?,?,?,?)";
		String sqlkontakt = "insert into sdp.kontaktinformasjon values(?,?,?,?,?,?,?)";
		String sqlSDP = "insert into sdp.sikkerdigitalpostadresse values(?,?,?)";
		try {
			con = dbConnect();
			while (!bDone) {
				lastupdateRef = getLastupdateRef();
				log.info("KRR -- Starter ny runde med Ref: "+ lastupdateRef);
				//System.out.println("Starter ny runde med Ref: "+ lastupdateRef);
				if(con.isClosed()){
					con = dbConnect();
				}
				client = new KRRClient();
				
				HentEndringerRespons resp = client.getEndringer(lastupdateRef);
				ArrayList<Person> pers = new ArrayList<Person>(resp.getPerson());
				for(int x=0;x<pers.size();x++){
					Person tmpPers = pers.get(x);
					pstmt = con.prepareStatement(sqlmain);
					try {
						pstmt.setString(1, tmpPers.getPersonidentifikator());
					} catch (Exception e2) {
						pstmt.setString(1, "0");
					}
					try {
						pstmt.setString(2, tmpPers.getReservasjon().value());
					} catch (Exception e2) {
						pstmt.setString(2, "");
					}
					try {
						pstmt.setString(3, tmpPers.getStatus().value());
					} catch (Exception e2) {
						pstmt.setString(3, "");
					}
					try {
						pstmt.setString(4, tmpPers.getBeskrivelse());
					} catch (Exception e2) {
						pstmt.setString(4, "");
					}
					try {
						pstmt.setBytes(5, tmpPers.getX509Sertifikat());
					} catch (Exception e2) {
						pstmt.setBytes(5, new byte[0]);
					}
					try {
						pstmt.execute();
					} catch (SQLException sqle) {
						//System.out.println(sqle.getMessage());
					}
					
					pstmtkontakt = con.prepareStatement(sqlkontakt);
					pstmtkontakt.setString(1, tmpPers.getPersonidentifikator());
					try {
						pstmtkontakt.setString(2, tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getValue());
					} catch (Exception e1) {
						pstmtkontakt.setString(2, "");
					}
					try {
						pstmtkontakt.setTimestamp(3, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getSistOppdatert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(3, null);
					}
					
					try {
						pstmtkontakt.setTimestamp(4, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getSistVerifisert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(4, null);
					}
					try {
						pstmtkontakt.setString(5, tmpPers.getKontaktinformasjon().getEpostadresse().getValue());
					} catch (Exception e1) {
						pstmtkontakt.setString(5, "");
					}
					try {
						pstmtkontakt.setTimestamp(6, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getEpostadresse().getSistOppdatert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(6, null);
					}
					try {
						pstmtkontakt.setTimestamp(7, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getEpostadresse().getSistVerifisert().toGregorianCalendar().getTimeInMillis()));
					} catch (Exception e) {
						pstmtkontakt.setTimestamp(7, null);
					}
					try {
						pstmtkontakt.execute();
					} catch (SQLException sqle) {
						//System.out.println(sqle.getMessage());
					}
					
					
					pstmtsdp = con.prepareStatement(sqlSDP);
					pstmtsdp.setString(1, tmpPers.getPersonidentifikator());
					try {
						pstmtsdp.setString(2, tmpPers.getSikkerDigitalPostAdresse().getPostkasseadresse());
					} catch (Exception e) {
						pstmtsdp.setString(2, "");
					}
					try {
						pstmtsdp.setString(3, tmpPers.getSikkerDigitalPostAdresse().getPostkasseleverandoerAdresse());
					} catch (Exception e) {
						pstmtsdp.setString(3, "");
					}
					try {
						pstmtsdp.execute();
					} catch (SQLException sqle) {
						//System.out.println(sqle.getMessage());
					}
					
				}
				
				String currentUpdateRef = new Long(resp.getTilEndringsNummer()).toString();
				if(currentUpdateRef.equals(lastupdateRef)){
					bDone = true;
				}else{
					updateLastupdateRef(currentUpdateRef);
				}
				log.info("KRR -- Oppdaterte KRR update ref til Ref: " + currentUpdateRef);
			}
		} catch (Exception e) {
			log.debug(e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				pstmtkontakt.close();
				pstmtsdp.close();
				con.close();
			} catch (Exception e) {}
		}
		
	}

	public String insertLocalKRR(String lastupdateRef) throws Exception{
		String retval="";
		KRRClient client = null;
		boolean bDone = false;
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtkontakt = null;
		PreparedStatement pstmtsdp = null;
		String sqlmain = "insert into sdp.person values (?,?,?,?,?)";
		String sqlkontakt = "insert into sdp.kontaktinformasjon values(?,?,?,?,?,?,?)";
		String sqlSDP = "insert into sdp.sikkerdigitalpostadresse values(?,?,?)";
		
		try {
			log.info("Starter med Ref: "+ lastupdateRef);
			con = dbConnect();			
			client = new KRRClient();
			HentEndringerRespons resp = client.getEndringer(lastupdateRef);
			ArrayList<Person> pers = new ArrayList<Person>(resp.getPerson());
			for(int x=0;x<pers.size();x++){
				Person tmpPers = pers.get(x);
				pstmt = con.prepareStatement(sqlmain);
				try {
					pstmt.setString(1, tmpPers.getPersonidentifikator());
				} catch (Exception e2) {
					pstmt.setString(1, "0");
				}
				try {
					pstmt.setString(2, tmpPers.getReservasjon().value());
				} catch (Exception e2) {
					pstmt.setString(2, "");
				}
				try {
					pstmt.setString(3, tmpPers.getStatus().value());
				} catch (Exception e2) {
					pstmt.setString(3, "");
				}
				try {
					pstmt.setString(4, tmpPers.getBeskrivelse());
				} catch (Exception e2) {
					pstmt.setString(4, "");
				}
				try {
					pstmt.setBytes(5, tmpPers.getX509Sertifikat());
				} catch (Exception e2) {
					pstmt.setBytes(5, new byte[0]);
				}
				try {
					pstmt.execute();
				} catch (SQLException sqle) {
					log.debug(sqle);
				}
				
				pstmtkontakt = con.prepareStatement(sqlkontakt);
				pstmtkontakt.setString(1, tmpPers.getPersonidentifikator());
				try {
					pstmtkontakt.setString(2, tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getValue());
				} catch (Exception e1) {
					pstmtkontakt.setString(2, "");
				}
				try {
					pstmtkontakt.setTimestamp(3, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getSistOppdatert().toGregorianCalendar().getTimeInMillis()));
				} catch (Exception e) {
					pstmtkontakt.setTimestamp(3, null);
				}
				try {
					pstmtkontakt.setTimestamp(4, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getMobiltelefonnummer().getSistVerifisert().toGregorianCalendar().getTimeInMillis()));
				} catch (Exception e) {
					pstmtkontakt.setTimestamp(4, null);
				}
				try {
					pstmtkontakt.setString(5, tmpPers.getKontaktinformasjon().getEpostadresse().getValue());
				} catch (Exception e1) {
					pstmtkontakt.setString(5, "");
				}
				try {
					pstmtkontakt.setTimestamp(6, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getEpostadresse().getSistOppdatert().toGregorianCalendar().getTimeInMillis()));
				} catch (Exception e) {
					pstmtkontakt.setTimestamp(6, null);
				}
				try {
					pstmtkontakt.setTimestamp(7, new java.sql.Timestamp(tmpPers.getKontaktinformasjon().getEpostadresse().getSistVerifisert().toGregorianCalendar().getTimeInMillis()));
				} catch (Exception e) {
					pstmtkontakt.setTimestamp(7, null);
				}
				try {
					pstmtkontakt.execute();
				} catch (SQLException sqle) {
					log.debug(sqle);
				}
				
				
				pstmtsdp = con.prepareStatement(sqlSDP);
				pstmtsdp.setString(1, tmpPers.getPersonidentifikator());
				try {
					pstmtsdp.setString(2, tmpPers.getSikkerDigitalPostAdresse().getPostkasseadresse());
				} catch (Exception e) {
					pstmtsdp.setString(2, "");
				}
				try {
					pstmtsdp.setString(3, tmpPers.getSikkerDigitalPostAdresse().getPostkasseleverandoerAdresse());
				} catch (Exception e) {
					pstmtsdp.setString(3, "");
				}
				try {
					pstmtsdp.execute();
				} catch (SQLException sqle) {
					log.debug(sqle);
				}
				
			}
			retval = new Long(resp.getTilEndringsNummer()).toString();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				pstmtkontakt.close();
				pstmtsdp.close();
				con.close();
			} catch (Exception e) {}
		}
		return retval;
	}
	
	private void updateLastupdateRef(String newRef)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "update sdp.properties set value = ? where property = 'lastupdateref'";
		try {
			//con = getConncetion();
			con = dbConnect();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, new Integer(newRef).intValue());
			pstmt.executeUpdate();
		} catch (Exception e) {
			log.debug(e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {}
		}
	}
	
	private String getLastupdateRef()throws Exception{
		String retval="";
		Connection con = null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql = "select value from sdp.properties where property = 'lastupdateref'";
		try {
			//con = getConncetion();
			con = dbConnect();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				retval = rs.getString("value");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			try {
				stmt.close();
				con.close();
			} catch (Exception e2) {}
		}
		return retval;
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
	
	public void resetDBPool(){
		try {
			MBeanServer server = MBeanServerLocator.locateJBoss();
			server.invoke(new ObjectName("jboss.jca:name=SDPDS,service=ManagedConnectionPool"), "flush", null, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.error(e);
		}
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
	
	private XMLGregorianCalendar getXmlGregoriancalendar(Timestamp timestamp)throws Exception{
		XMLGregorianCalendar retval = null;
		try {
			GregorianCalendar gcal = new GregorianCalendar();
			gcal.setTimeInMillis(timestamp.getTime());
			retval = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		} catch (Exception e) {
			log.debug("Feilet i convertering til XMLGregorianCalendar: " + e.getMessage());
		}
		return retval;
	}
}
