package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import main.Chargepoint;
import main.ElectronicVehicle;
import main.Generator;
import main.User;

public class ChargepointTest
{	
	private Chargepoint m_genericChargepoint = new Chargepoint(null);
	private Generator m_generator = new Generator();
	private User m_genericUser;	

	@Test
	public void testConnect()
	{	
		// Create a user who uses the chargepoint with his electric vehicle
		m_genericUser = m_generator.generateUser();
		// User wants to start charging his electric vehicle
		// Chargepoint needs to check if he is authorized
		m_genericChargepoint.authorizeElectronicVehicle(m_genericUser);
		// Chargepoint needs to check if he is authorized to start charging
		m_genericChargepoint.startTransaction();
		// User wants to stop charging his electric vehicle
		// Chargepoint needs to check if he is authorized
		m_genericChargepoint.authorizeElectronicVehicle(m_genericUser);
		// Chargepoint needs to check if he is authorized to stop charging
		m_genericChargepoint.stopTransaction();
		// Check if Cache is updated correctly
		HashMap<String, String> expectedCache = new HashMap<>();
		expectedCache.put(m_genericUser.getIdToken(), "Accepted");
		assertEquals(expectedCache, m_genericChargepoint.getAutCache());
		
	}
}
