package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.CentralSystem;
import main.Chargepoint;

public class CentralSystemTest {

	CentralSystem cs = new CentralSystem();
	Chargepoint cp = new Chargepoint(null);
	
	@Test
	public void test() {
		cs.sendLocalList(cp, "Full");
		// TODO useful Tests
	}

}
