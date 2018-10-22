package si.sdp.admin.mlf.DAO;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import si.sdp.admin.mlf.utils.DBHandler;

public class PropertyBean implements ValueChangeListener{

	private String id;
	private String property;
	private String value;
	private String beskrivelse;
	private String odato;
	private String edato;
	
	public PropertyBean() {
	}

	@Override
	public void processValueChange(ValueChangeEvent event)throws AbortProcessingException {	
	}
	
	public void processPropertyChange(ValueChangeEvent event)throws AbortProcessingException{
		DBHandler dbh = new DBHandler();
		try {
			dbh.updateProperty(getId(), "property",  (String)event.getNewValue());
		} catch (Exception e) {
			throw new AbortProcessingException("Feilet i oppdatering av property: " + e.getMessage());
		}
	}

	public void processPropertyValueChange(ValueChangeEvent event)throws AbortProcessingException{
		DBHandler dbh = new DBHandler();
		try {
			dbh.updateProperty(getId(), "value",  (String)event.getNewValue());
		} catch (Exception e) {
			throw new AbortProcessingException("Feilet i oppdatering av value: " + e.getMessage());
		}
	}
	
	public void processPropertyDescriptionChange(ValueChangeEvent event)throws AbortProcessingException{
		DBHandler dbh = new DBHandler();
		try {
			dbh.updateProperty(getId(), "beskrivelse",  (String)event.getNewValue());
		} catch (Exception e) {
			throw new AbortProcessingException("Feilet i oppdatering av beskrivelse: " + e.getMessage());
		}
	}
	
	public void saveProperty()throws Exception{
		DBHandler dbh = new DBHandler();
		try {
			dbh.newProperty(getProperty(), getValue(), getBeskrivelse());
			setProperty("");
			setValue("");
			setBeskrivelse("");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void deleteproperty()throws Exception{
		DBHandler dbh = new DBHandler();
		try {
			dbh.deleteProperty(getId());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public String getOdato() {
		return odato;
	}

	public void setOdato(String odato) {
		this.odato = odato;
	}

	public String getEdato() {
		return edato;
	}

	public void setEdato(String edato) {
		this.edato = edato;
	}
}
