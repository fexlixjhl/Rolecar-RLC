package com.rolecar.data.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.rolecar.data.connection.conexionBBDD;
import com.rolecar.data.constantes.Atributos;
import com.rolecar.beans.City;
import com.rolecar.beans.Country;


public class JdbcCitiesDao
{
	private static Log logger = LogFactory.getLog(JdbcCitiesDao.class);
	private static String nombresch = Atributos.ESQUEMA;
	private static String nombretabla = Atributos.TABLACITIES;
		
	/**
	 * ANM Obtenemos todas las Ciudades de la base de datos ordenadas por idprovincia
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<City> getAllCities(Hashtable<String,Country> htcountries) throws Exception
	{
			
    	Vector<City> vcities = new Vector<City>();
    	Connection con = conexionBBDD.getConnectionWeb();
    	try
    	{
    		
    		String sql = "select * from ".concat(nombresch).concat(".").concat(nombretabla).concat(" Order By idprovincia");
            PreparedStatement pStm= con.prepareStatement(sql);
            ResultSet rs = pStm.executeQuery();
            while(rs!=null && rs.next())
            {
            	String codcity = rs.getString("codcity");
            	City city = new City();
            	city.setCodcity(codcity);
            	city.setIdcity(rs.getInt("idcity"));
            	city.setDescr(rs.getString("descr"));
            	city.setIdprovincia(rs.getInt("idprovincia") );            	
            	Country country=htcountries.get(rs.getString("codcountry"));            	
            	city.setCountry(country);
            	vcities.add(city);           	
            }
		}
		catch(SQLException e){logger.error(e.getMessage());con.close(); ;return vcities;}
        catch(Exception e){logger.error(e.getMessage());con.close();return vcities;}
		finally
        {
        	if (con!=null)
        	{
        		con.close();con=null;
        	}
        }
    	return vcities;
		
	}
	
	/**
	 * ANM Función que devuelve una Hastable de todas las ciudades como clave
	 * el id de la provincia, y como dato un vector de ciudades de dicha provincia.
	 * @param vcity
	 * @return
	 */
	public static synchronized Hashtable<Integer,Vector<City>> getHTCitiesbyProv(Vector<City> vcity)
	{
		Hashtable<Integer,Vector<City>> htcityProv = new Hashtable<Integer,Vector<City>>();
		//Recorremos el vector de ciudades
		try
		{
			Vector<City> vcty = new Vector<City>();
			Integer idprovAnt=vcity.get(0).getIdcity();
			for (City city : vcity)
			{
				Integer idprov=city.getIdprovincia();
				if(htcityProv.get(idprov)==null && idprov!=idprovAnt)
				{
					htcityProv.put(vcty.get(0).getIdcity(), vcty);
					vcty= new Vector<City>();
				}
				vcty.add(idprov, city);
				idprovAnt=city.getIdcity();
			}			
			
		}
		catch(Exception e){logger.error(e.getMessage());return htcityProv;}
		return htcityProv;
	}
	
	public static synchronized Hashtable<String,City> getHTCities(Vector<City> vcity)
	{
		Hashtable<String,City> htcity = new Hashtable<String,City>();
		//Recorremos el vector de ciudades
		try
		{
			for (City city : vcity)
			{				
				htcity.put(city.getCodcity().concat(city.getCountry().getCodcountry()), city);		
			}						
		}
		catch(Exception e){logger.error(e.getMessage());return htcity;}
		return htcity;
	}
	
	
	
	
	
	/**
	 * CBC Construyo el request para enviar al WS
	 * @return
	 */
	public static synchronized String getRequestCities(Country country)
	{
		String request = "";
		try
		{
			request += Atributos.CABECERAXML;
			request +=		"<message>"
								+ "<serviceRequest serviceCode=\"getCities\">"
								+ Atributos.LANGUAGE_ES
								+ "<serviceParameters>"
								+ "<brand code =\"EP\"/>"
								+ "<country countryCode =\""+country.getCodcountry()+"\"/>"
								+ "</serviceParameters>"
								+ "</serviceRequest>"
							+ "</message>";
    		request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch (Exception e)
		{
			return request;
		}
		return request;
	}
	
	/**
     * Obtengo la lista de nodos de paises
     * @param texto
     * @throws Exception
     */
	public static synchronized NodeList getResponseCities(String texto)
    {
    	String responseStr = "";
    	NodeList listaciudades = null;
    	HttpsURLConnection con=null;
    	try
    	{
    		URL obj = new URL(Atributos.PUSH_URL_AUTH);
    		con = (HttpsURLConnection) obj.openConnection();
           
    		//add request header
    		con.setRequestMethod("POST");
    		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    		//Send post request
    		con.setDoOutput(true);
    		con.setDoInput(true);
    		
    		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    		wr.writeBytes(texto);
    		wr.flush();
    		wr.close();
           
    		int responseCode = con.getResponseCode();
    		System.out.println("Response Code : " + responseCode);

    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuilder response = new StringBuilder();
   
    		while ((inputLine = in.readLine()) != null)
    		{
    			response.append(inputLine);
    		}
    		in.close();
    		responseStr = response.toString();
    		con.disconnect();
    		if (!responseStr.contains("cityList"))
    		{
    			logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			logger.info("Obtengo el listado de ciudades.");
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			listaciudades = doc.getElementsByTagName("city");	
    		}
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return listaciudades;
       }
       return listaciudades;
    }
   
    
    /**
     * Obtengo la ciudad en base a una condición
     * @throws SQLException 
     * @throws Exception
     */
	public static synchronized City getCity(String condicion) throws SQLException,Exception
    {
    	City city = null;
    	Connection con = conexionBBDD.getConnectionWeb();//conexionBBDD.getConnectionWeb();
    	try
    	{
    		
    		String sql = "select * from ".concat(nombresch).concat(".").concat(nombretabla);
    		sql += condicion;
            PreparedStatement pStm= con.prepareStatement(sql);
            ResultSet rs = pStm.executeQuery();
            if(rs!=null && rs.next())
            {
            	city = new City();
            	city.setIdcity(rs.getInt("idcity"));
            	city.setCodcity(rs.getString("codcity"));
            	city.setDescr(rs.getString("descr"));
            	city.setIdprovincia(rs.getInt("idprovincia"));
            	String condcountry = " where codcountry = '" + rs.getString("codcountry")+"'";
            	Country country = JdbcCountriesDao.getCountry(condcountry,con);
            	city.setCountry(country);
            }
		}
		catch(SQLException e){logger.error(e.getMessage());con.close();return city;}
        catch(Exception e){logger.error(e.getMessage());con.close();return city;}
		finally
        {
        	if (con!=null)
        	{
        		con.close();con=null;
        	}
        }
    	return city;
    }
    
  
   
}