package main;

import org.json.simple.JSONObject;

public class PDUInterpreter
{
	public void interpretPDU(JSONObject PDU)
	{
		String title = (String) PDU.get("title");
		String status = (String) ((JSONObject) PDU.get("idTagInfo")).get("status");
		System.out.println(title + ": " + status);
	}
}
