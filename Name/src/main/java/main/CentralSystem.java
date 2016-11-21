package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CentralSystem
{
	private JSONParser parser = new JSONParser();

	/**
	 * Verifies an authorization request send by a chargepoint
	 * 
	 * @param authorizationRequest
	 *            the authorization request send by the chargepoint
	 * @return the response of the central system
	 */
	public JSONObject verifyAuthorization(JSONObject authorizationRequest)
	{
		try
		{
			// Create an empty authorization response PDU
			//JSONObject authorizeResponsePDU = (JSONObject) parser.parse(new FileReader(CentralSystem.class.getResource("/resources/AuthorizeResponse.json").getFile()));
			JSONObject authorizeResponsePDU = createJSONObject("AuthorizeResponse.json");

			// Put the necessary information into the PDU
			JSONObject idTagInfo = new JSONObject();
			idTagInfo.put("status", "Accepted");
			authorizeResponsePDU.put("idTagInfo", idTagInfo);

			System.out.println("Central System: Authorization is correct.");

			return authorizeResponsePDU;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Verifies an transaction start request send by a chargepoint
	 * 
	 * @param startTransactionRequest
	 *            the transaction start request send by the chargepoint
	 * @return the response of the central system
	 */
	public JSONObject verifyTransactionStart(JSONObject startTransactionRequest)
	{
		try
		{
			// Create an empty transaction response PDU
			//JSONObject startTransactionResponsePDU = (JSONObject) parser.parse(new FileReader(CentralSystem.class.getResource("/resources/StartTransactionResponse.json").getFile()));
			JSONObject startTransactionResponsePDU = createJSONObject("StartTransactionResponse.json");
			
			// Put the necessary information into the PDU				
			JSONObject idTagInfo = new JSONObject();
			idTagInfo.put("status", "Accepted");
			startTransactionResponsePDU.put("idTagInfo", idTagInfo);
			startTransactionResponsePDU.put("transactionId", 1);

			System.out.println("Central System: Transaction start is correct.");

			return startTransactionResponsePDU;

		}
		catch(IOException | ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Verifies an transaction stop request send by a chargepoint
	 * 
	 * @param stopTransactionRequest
	 * 			the transaction stop request send by the chargepoint
	 * @return the response of the central system
	 */
	public JSONObject verifyTransactionStop(JSONObject stopTransactionRequest)
	{
		try
		{
			// Create an empty transaction response PDU
			//JSONObject stopTransactionResponsePDU = (JSONObject) parser.parse(new FileReader(CentralSystem.class.getResource("/resources/StopTransactionResponse.json").getFile()));
			JSONObject stopTransactionResponsePDU = createJSONObject("StopTransactionResponse.json");
			
			// Put the necessary information into the PDU				
			JSONObject idTagInfo = new JSONObject();
			idTagInfo.put("status", "Accepted");
			stopTransactionResponsePDU.put("idTagInfo", idTagInfo);
			stopTransactionResponsePDU.put("transactionId", 1);

			System.out.println("Central System: Transaction stop is correct.");

			return stopTransactionResponsePDU;

		}
		catch(IOException | ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * send a new LocalList to the chargepoint
	 * @param chargepoint
	 * @param updateType "Full" for replacing, "Differential" for adding
	 */
	public void sendLocalList(Chargepoint chargepoint, String updateType)
	{
		try
		{
			// Create an empty PDU
			JSONObject sendLocalListPDU = createJSONObject("SendLocalList.json");
			
			sendLocalListPDU.put("listVersion", 42); // random test Version
			sendLocalListPDU.put("updateType", updateType);
			
			// The actual List, just a test could be added to params
			JSONArray localAuthorizationList = new JSONArray();
			JSONObject firstItem = new JSONObject();
			JSONObject firstIdTagInfo =new JSONObject();
			firstIdTagInfo.put("status", "Accepted");
			firstItem.put("idTag", "testTag1");
			firstItem.put("idTagInfo", firstIdTagInfo);
			JSONObject secondItem = new JSONObject();
			JSONObject secondIdTagInfo =new JSONObject();
			secondIdTagInfo.put("status", "Blocked");
			secondItem.put("idTag", "testTag2");
			secondItem.put("idTagInfo", secondIdTagInfo);
			localAuthorizationList.add(firstItem);
			localAuthorizationList.add(secondItem);
			
			sendLocalListPDU.put("localAuthorizationList", localAuthorizationList);
			
			// Send the local list PDU to the chargepoint and store their  response PDU
			JSONObject localListResponsePDU = chargepoint.receiveLocalList(sendLocalListPDU);
			
			// Extract the status out of the response PDU
			String localListResponse = (String) localListResponsePDU.get("status");

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	
	/**
	 * get the version number of the current LocalList of the chargepoint
	 * @param chargepoint
	 * @return version number
	 */
	public int getLocalListVersion(Chargepoint chargepoint)
	{
		try
		{
			// Create an empty PDU
			JSONObject getLocalListVersionPDU = createJSONObject("GetLocalListVersion.json");
			

			// Send the PDU to the central system and store their response PDU
			JSONObject localListVersionPDU = chargepoint.getLocalListVersion(getLocalListVersionPDU);
			
			// Extract the version of the List out of the response PDU
			int localListVersion = (int) ( localListVersionPDU.get("listVersion"));

			return localListVersion;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}

	}

	
	
	private JSONObject createJSONObject(String JSONFIleName) throws FileNotFoundException, IOException, ParseException
	{
		return (JSONObject) parser.parse(new FileReader(Chargepoint.class.getResource("../" + JSONFIleName).getFile()));
	}


}
