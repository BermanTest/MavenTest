package main;

public class ElectronicVehicle
{

	private ElectronicVehicleBrand electronicVehicleBrand;
	private Certificate electronicVehicleCertificate;

	/**
	 * The users electronic vehicle
	 * 
	 * @param electronicVehicleBrand
	 * 			the brand of the electronic vehicle
	 * @param electronicVehicleCertificate
	 * 			the authentication certificate of the electronic vehicle
	 */
	public ElectronicVehicle(ElectronicVehicleBrand electronicVehicleBrand, Certificate electronicVehicleCertificate)
	{
		this.electronicVehicleBrand = electronicVehicleBrand;
		this.electronicVehicleCertificate = electronicVehicleCertificate;
	}

	/**
	 * Sets the brand of the electronic vehicle
	 * 
	 * @param electronicVehicleBrand
	 * 			the brand of the electronic vehicle
	 */
	public void setElectronicVehicleBrand(ElectronicVehicleBrand electronicVehicleBrand)
	{
		this.electronicVehicleBrand = electronicVehicleBrand;
	}
	
	/**
	 * Returns the brand of the electronic vehicle
	 * 
	 * @return the brand of the electronic vehicle
	 */
	public ElectronicVehicleBrand getElectronicVehicleBrand()
	{
		return electronicVehicleBrand;
	}

	/**
	 * Sets the authentication certificate of the electronic vehicle 
	 * 
	 * @param electronicVehicleCertificate
	 * 			the authentication certificate of the electronic vehicle
	 */
	public void setElectronicVehicleCertificate(Certificate electronicVehicleCertificate)
	{
		this.electronicVehicleCertificate = electronicVehicleCertificate;
	}
	
	/**
	 * Returns the authentication certificate of the electronic vehicle
	 * 
	 * @return the authentication certificate of the electronic vehicle
	 */
	public Certificate getElectronicVehicleCertificate()
	{
		return electronicVehicleCertificate;
	}

}
