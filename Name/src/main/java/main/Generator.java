package main;

import java.util.Random;

public class Generator
{
	/**
	 * Generates a random user who wants to charge his electric vehicle at a chargepoint
	 * 
	 * @return a random user
	 */
	public User generateUser()
	{
		return new User(generateIdToken(), generateElectronicVehicle());
	}
	
	/**
	 * Generates a random IdToken
	 * 
	 * @return a random IdToken
	 */
	private String generateIdToken()
	{
		return String.valueOf(new Random().nextInt(100));
	}
	
	/**
	 * Generates a random electronic vehicle which needs to be charged at a chargepoint
	 * 
	 * @return a random electronic vehicle
	 */
	private ElectronicVehicle generateElectronicVehicle()
	{
		return new ElectronicVehicle(generateElectronicVehicleBrand(), generateCertificate());
	}
	
	/**
	 * Generates a random electronic vehicle brand
	 * 
	 * @return a random electronic vehicle brand
	 */
	private ElectronicVehicleBrand generateElectronicVehicleBrand()
	{
		return ElectronicVehicleBrand.values()[new Random().nextInt(ElectronicVehicleBrand.values().length)];
	}
	
	/**
	 * Generates a random certificate for an electronic vehicle
	 * 
	 * @return a random certificate
	 */
	private Certificate generateCertificate()
	{
		return new Certificate(CertificateType.values()[new Random().nextInt(CertificateType.values().length)]);
	}
}
