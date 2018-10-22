package no.si.sdp.utils.fallback;

import java.util.ArrayList;
import java.util.List;

import no.difi.begrep.Person;
import no.si.sdp.utils.KRRServices;

public class FallbackFacade {

	public FallbackFacade() {
		// TODO Auto-generated constructor stub
	}
	
	public Person getLocalperson(String personidentifikator)throws Exception {
		Person person = null;
		try{
			KRRServices krrs = new KRRServices();
			person = krrs.getLocalPerson(personidentifikator);
		}catch(Exception e){
			throw new Exception("Feilet i getLocalperson: " + e.getMessage());
		}
		return person;
	}
	
	public List<Person> getLocalpersoner(List<String> personidentifikator)throws Exception {
		List<Person> retval = new ArrayList<Person>();
		try {
			KRRServices krrs = new KRRServices();
				for(int x=0;x<personidentifikator.size();x++){
					String tmpfonr = personidentifikator.get(x);
					Person person = krrs.getLocalPerson(tmpfonr);
					retval.add(person);
				}
		} catch (Exception e) {
			throw new Exception("Feilet i getLocalPersoner: " + e.getMessage());
		}
		return retval;
	}
}
