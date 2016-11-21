package main;

public class User
{
	private String idToken;
	private ElectronicVehicle electronicVehicle;
	
	/**
	 * A user of a chargepoint with his electronic vehicle
	 * 
	 * @param electronicVehicle
	 * 			the users electronic vehicle
	 */
	public User(String idToken, ElectronicVehicle electronicVehicle)
	{
		this.idToken = idToken;
		this.electronicVehicle = electronicVehicle;
	}
	
	/**
	 * Sets the IdToken of the user for identification purposes
	 * 
	 * @param IdToken
	 * 			an identifier with a maximum length of 20 chars
	 */
	public void setIdToken(String idToken)
	{
		this.idToken = idToken;
	}
	
	/**
	 * Returns the IdToken
	 * 
	 * @return an identifier with a maximum length of 20 chars
	 */
	public String getIdToken()
	{
		return idToken;
	}
	
	/**
	 * Sets the users electronic vehicle
	 * 
	 * @param electronicVehicle
	 * 			the users electronic vehicle
	 * 			
	 */
	public void setElectronicVehicle(ElectronicVehicle electronicVehicle)
	{
		this.electronicVehicle = electronicVehicle;
	}
	
	/**
	 * Returns the users electronic vehicle
	 * 
	 * @return the users electronic vehicle
	 */
	public ElectronicVehicle getElectronicVehicle()
	{
		return electronicVehicle;
	}
	
	
}
