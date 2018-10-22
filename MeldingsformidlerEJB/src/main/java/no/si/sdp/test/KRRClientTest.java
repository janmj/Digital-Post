/**
 * 
 */
package no.si.sdp.test;

import static org.junit.Assert.*;

import java.util.ArrayList;



import org.junit.Before;
import org.junit.Test;

/**
 * @author janmj
 *
 */
public class KRRClientTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link no.si.sdp.KRRClient#getPersoner(java.util.ArrayList)}.
	 */
	/*
	@Test
	public void testGetPersoner() {
		KRRClient client = new KRRClient();
		
		ArrayList<String> fonr = new ArrayList<String>();
		fonr.add("26126641590");
		
		try {
			assertEquals("26126641590", client.getPersoner(fonr).get(0).getPersonidentifikator());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	*/
	/**
	 * Test method for {@link no.si.sdp.KRRClient#getEndringer(java.util.ArrayList)}.
	 */
	@Test
	public void testGetEndringer() {
		//fail("Not yet implemented");
	}

}
