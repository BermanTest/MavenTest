package main;

public class Certificate
{
	private CertificateType m_certificateType;

	/**
	 * A certificate for an electronic vehicle
	 * 
	 * @param certificateType
	 * 			the certificate type of the electronic vehicle
	 */
	Certificate(CertificateType certificateType)
	{
		m_certificateType = certificateType;
	}

	/**
	 * Sets the certificate type of the electronic vehicle 
	 * 
	 * @param certificateType
	 * 			the certificate type of the electronic vehicle
	 */
	public void setCertificateType(CertificateType certificateType)
	{
		m_certificateType = certificateType;
	}
	
	/**
	 * Returns the certificate type of the electronic vehicle
	 * 
	 * @return the certificate type of the electronic vehicle
	 */
	public CertificateType getCertificateType()
	{
		return m_certificateType;
	}
}
