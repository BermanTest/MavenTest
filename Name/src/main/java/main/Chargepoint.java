package main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.ChargepointGUI;

public class Chargepoint
{
	private JSONParser parser = new JSONParser();
	private CentralSystem genericCentralSystem = new CentralSystem();
	private PDUInterpreter PDUInterpreter = new PDUInterpreter();
	private ChargepointGUI chargepointGUI;
	
	private String currentUserIdToken;
	
	private boolean authorizationCacheEnabled=true;
	private HashMap<String, String> authorizationCache = new HashMap<String, String>();
	private boolean localAuthListEnabled=true;
	private int localListVersion=0;
	private HashMap<String, String> localAuthentificationList = new HashMap<String, String>();
	
	public Chargepoint(ChargepointGUI chargepointGUI)
	{
		this.chargepointGUI = chargepointGUI;
	}
	
	/**
	 * Checks if an electronic vehicle is authorized in general by the central system
	 * 
	 * @param electronicVehicle
	 *            an electronic vehicle
	 */
	public void authorizeElectronicVehicle(User user)
	{
		currentUserIdToken=user.getIdToken();
		try
		{
			// Create an empty authorization PDU			
			JSONObject authorizePDU = createJSONObject("Authorize.json");
			int textAreaID = -1;
			
			// Put the necessary information into the PDU
			authorizePDU.put("idTag", user.getIdToken());

			//System.out.println("Chargepoint: Request authorization check.");
			
			if(chargepointGUI != null)
			{
				textAreaID = chargepointGUI.editTextArea("Request authorization check for IdToken: " + user.getIdToken() + " \n", Color.YELLOW);		
				System.out.println("textAreaID = " + textAreaID);
			}			
			
			// Send the authorization PDU to the central system and store their authorization response PDU
			JSONObject authorizeResponsePDU = genericCentralSystem.verifyAuthorization(authorizePDU);

			// Extract the status out of the response PDU
			String authorizationStatus = (String) ((JSONObject) authorizeResponsePDU.get("idTagInfo")).get("status");

			// updates the authorization Cache
			if(authorizationCacheEnabled)
			{
				authorizationCache.put(currentUserIdToken, authorizationStatus);
			}
			
			if(chargepointGUI != null)
			{
				chargepointGUI.editTextArea(textAreaID, "Accepted. \n", Color.GREEN);				
			}
			//PDUInterpreter.interpretPDU(authorizeResponsePDU);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Checks if an electronic vehicle is authorized by the central system to stop charging
	 */
	public void startTransaction()
	{
		try
		{
			// Create an empty transaction PDU			
			JSONObject startTransactionPDU = createJSONObject("StartTransaction.json");
			
			// Put the necessary information into the PDU
			startTransactionPDU.put("connectorId", 1);
			startTransactionPDU.put("idTag", "aTestTag");
			startTransactionPDU.put("meterStart", 15);
			startTransactionPDU.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));

			System.out.println("Chargepoint: Request transaction start check.");
			
			// Send the transaction PDU to the central system and store their transaction response PDU
			JSONObject startTransactionResponsePDU = genericCentralSystem.verifyTransactionStart(startTransactionPDU);

			// Extract the status out of the response PDU
			String startTransactionStatus = (String) ((JSONObject) startTransactionResponsePDU.get("idTagInfo")).get("status");			
			

			// updates the authorization Cache
			if(authorizationCacheEnabled)
			{
				authorizationCache.put(currentUserIdToken, startTransactionStatus);
			}
			
			PDUInterpreter.interpretPDU(startTransactionResponsePDU);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if an electronic vehicle is authorized by the central system to stop charging
	 */
	public void stopTransaction()
	{				
		try
		{
			// Create an empty transaction PDU			
			JSONObject stopTransactionPDU = createJSONObject("StopTransaction.json");
			
			// Put the necessary information into the PDU
			stopTransactionPDU.put("transactionId", 1);
			stopTransactionPDU.put("meterStart", 15);
			stopTransactionPDU.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
			
			System.out.println("Chargepoint: Request transaction stop check.");
			
			// Send the transaction PDU to the central system and store their transaction response PDU
			JSONObject stopTransactionResponsePDU = genericCentralSystem.verifyTransactionStop(stopTransactionPDU);
			
			// Extract the status out of the response PDU
			String stopTransactionStatus = (String) ((JSONObject) stopTransactionResponsePDU.get("idTagInfo")).get("status");

			
			// updates the authorization Cache
			if(authorizationCacheEnabled)
			{
				authorizationCache.put(currentUserIdToken, stopTransactionStatus);
			}			
			
			PDUInterpreter.interpretPDU(stopTransactionResponsePDU);				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * update the localAuthorizationList
	 * @param localAuthorizationListPDU a list with authorization dates
	 * @return indicates if updated successfully
	 */
	public JSONObject receiveLocalList(JSONObject localAuthorizationListPDU)
	{
		try
		{
			JSONObject localListResponse = createJSONObject("SendLocalListResponse.json");

			if(localAuthListEnabled)
			{
				localListVersion=(int) localAuthorizationListPDU.get("listVersion");
				String updateType = (String) localAuthorizationListPDU.get("updateType");
				List<JSONObject> newAuthentificationPDUs = (List<JSONObject>) localAuthorizationListPDU.get("localAuthorizationList");
				HashMap<String, String> newAuthentifications = new HashMap<String, String>();
				
				for(JSONObject item : newAuthentificationPDUs)
				{
					String authorizationStatus = (String) ((JSONObject) item.get("idTagInfo")).get("status");
					String idTag = (String) (item.get("idTag"));
					newAuthentifications.put(idTag, authorizationStatus);
				}
				if(updateType.equals("Differential")) // add new Items to list
				{
					localAuthentificationList.putAll(newAuthentifications);
				}
				else if(updateType.equals("Full")) // replace localAuthorizationList
				{
					localAuthentificationList=newAuthentifications;
				}
				localListResponse.put("status", "Accepted");
				System.out.println("Successfully updated the localAuthorizationList");
			}
			else
			{
				localListResponse.put("status", "NotSupported");				
			}
			return localListResponse;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * return the current version number
	 */
	public JSONObject getLocalListVersion(JSONObject getLocalListVersionReq)
	{
		try 
		{
			JSONObject localListVersionPDU=createJSONObject("GetLocalListVersionResponse.json");
			localListVersionPDU.put("listVersion", localListVersion);
			return localListVersionPDU;
		} catch (IOException | ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	private JSONObject createJSONObject(String JSONFIleName) throws FileNotFoundException, IOException, ParseException
	{
		return (JSONObject) parser.parse(new FileReader(Chargepoint.class.getResource("../" + JSONFIleName).getFile()));
	}
	
	//for testing
	public HashMap<String, String> getAutCache()
	{
		return authorizationCache;
	}
}
