package com.rolecar.beans;

public class Station {
	private String descrCity, descrCountry, codCity, codCountry, codstation, descr;
	private int idstation, idprovincia;
	private City city;

	public String getDescrCity() {
		return descrCity;
	}

	public void setDescrCity(String descrCity) {
		this.descrCity = descrCity;
	}

	public String getDescrCountry() {
		return descrCountry;
	}

	public void setDescrCountry(String descrCountry) {
		this.descrCountry = descrCountry;
	}

	public String getCodCity() {
		return codCity;
	}

	public void setCodCity(String codCity) {
		this.codCity = codCity;
	}

	public String getCodCountry() {
		return codCountry;
	}

	public void setCodCountry(String codCountry) {
		this.codCountry = codCountry;
	}

	public String getCodstation() {
		return codstation;
	}

	public void setCodstation(String codstation) {
		this.codstation = codstation;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public int getIdstation() {
		return idstation;
	}

	public void setIdstation(int idstation) {
		this.idstation = idstation;
	}

	public int getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(int idprovincia) {
		this.idprovincia = idprovincia;
	}

	/**
	 * @return the city
	 */
	public City getCity()
	{
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(City city)
	{
		this.city = city;
	}
}
