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
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.rolecar.beans.City;
import com.rolecar.beans.Country;
import com.rolecar.beans.Station;
import com.rolecar.data.connection.conexionBBDD;
import com.rolecar.data.constantes.Atributos;

public class JdbcStationsDao
{
	private static Log logger = LogFactory.getLog(JdbcStationsDao.class);
	private static String nombresch = Atributos.ESQUEMA;
	private static String nombretabla = Atributos.TABLASTATIONS;
	private static Hashtable<String,Station> htstations = new Hashtable<String,Station>();
	//private static Connection conn = conexionBBDD.getConnectionWeb();
	
	/**
	 * Recorro las ciudades para obtener las estaciones
	 * @param vcities
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<Station> recogeEstaciones(Vector<City> vcities) throws Exception
	{
		String request = "";
		Vector<Station> vstations = new Vector<Station>();
		int idprovincia = 0;
		try
		{
			for (int i=0;i<vcities.size();i++)
			{
				City city = vcities.elementAt(i);
				request = getRequestStations(city);
				idprovincia = i + 1;
				if (request!=null && !request.equalsIgnoreCase(""))
				{
					NodeList listastations = getResponseStations(request);
					if (listastations!=null && listastations.getLength()>0)
					{
						insertaEstaciones(listastations, city, idprovincia);
						String condicion = " order by descr";
						vstations = getStations(condicion);
					}
				}
			}
		}
		catch (Exception e)
		{
			return vstations;
		}
		return vstations;
	}
	
	/**
	 * CBC Construyo el request para enviar al WS
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public static synchronized String getRequestStations(City city) throws SQLException
	{
		String request = "";
		
		try
		{
			
			request += Atributos.CABECERAXML;
			request +=		"<message>"
								+ "<serviceRequest serviceCode=\"getStations\">"
								+ Atributos.LANGUAGE_ES
								+ "<serviceParameters>"
								+ "<station countryCode =\""+city.getCountry().getCodcountry()
								+ "\" cityName=\""+city.getCodcity()
								+ "\" language=\"ES\"/>"
								+ "</serviceParameters>"
								+ "</serviceRequest>"
							+ "</message>";
    		request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch(Exception e){logger.error(e.getMessage());return request;}
		
		return request;
	}
	
	/**
     * Obtengo la lista de nodos de estaciones
     * @param texto
     * @throws Exception
     */
	public static synchronized NodeList getResponseStations(String texto)
    {
    	String responseStr = "";
    	NodeList listaciudades = null;
    	HttpsURLConnection con =null;
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
    		if (!responseStr.contains("stationList"))
    		{
    			logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			logger.info("Obtengo el listado de estaciones de las ciudades.");
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			listaciudades = doc.getElementsByTagName("station");
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
     * Inserto la lista de estaciones en base de datos
     * @param liststations
     * @param city
     * @param numprovincia
     * @throws Exception
     */
    public static synchronized boolean insertaEstaciones(NodeList liststations, City city, int idprovincia)
    {
    	boolean result = false;
    	try
    	{
    		for (int i = 0; i < liststations.getLength() ; i ++) 
			{
				Element el = (Element) liststations.item(i);
				Station station = new Station();
				
				String codstation = el.getAttribute("stationCode").trim();
				station.setCodstation(codstation);
				String stationName = el.getAttribute("stationName").trim();
				station.setDescr(stationName);
				station.setCity(city);
				station.setIdprovincia(idprovincia);
				
				//Relleno una hash con los códigos de pais para saber cuáles tengo que borrar
				htstations.put(codstation, station);
				String condicion = "";
				Station st = existeStation(station, condicion);
				if (st!=null)
				{
					//Actualizo sólo la provincia de la ciudad cuando ya existe
					result = updateProvinciaByCity(city,st.getIdprovincia(),Atributos.TABLACITIES);
				}
				else
				{
					result = insertStation(station);
					//Actualizo la provincia de la ciudad que no existe
					result = updateProvinciaByCity(city,idprovincia,Atributos.TABLACITIES);
				}
			}
    	}
    	catch (Exception e)
    	{
    		return result;
    	}
    	return result;
    }
    
    /**
     * Inserto la estación en la base de datos
     * @param station
     * @throws SQLException 
     * @throws Exception
     */
    public static synchronized boolean insertStation(Station station) throws SQLException
    {
    	boolean result = false;
    	Connection conn=null;
		try
		{
			conn = conexionBBDD.getConnectionWeb();
    		String sql = "insert into ".concat(nombresch).concat(".").concat(nombretabla);
    		sql += " (codstation,descr,codcity,idprovincia)";
			sql += " values (?,?,?,?)";
            PreparedStatement pStm= conn.prepareStatement(sql);
            pStm.setString(1, station.getCodstation());
            pStm.setString(2, station.getDescr());
            pStm.setString(3, station.getCity().getCodcity());
            pStm.setInt(4, station.getIdprovincia());
            
            int numfilas = pStm.executeUpdate();
            if (numfilas>0) 
            {
            	result = true;
            }
		}
		catch(SQLException e){logger.error(e.getMessage());conn.close();return result;}
        catch(Exception e){logger.error(e.getMessage());conn.close();return result;}
		finally
        {
        	if (conn!=null)
        	{
        		conn.close();conn=null;
        	}
        }
    	return result;
    }
    
    /**
     * Obtengo la lista de estaciones en base de datos
     * @param condicion
     * @throws SQLException 
     * @throws Exception
     */
    public static synchronized Vector<Station> getStations(String condicion) throws SQLException
    {
    	Vector<Station> vstations = new Vector<Station>();
    	Connection conn=null;
		try
		{
			conn = conexionBBDD.getConnectionWeb();
    		String sql = "select * from ".concat(nombresch).concat(".").concat(nombretabla);
    		sql += condicion;
            PreparedStatement pStm= conn.prepareStatement(sql);
            ResultSet rs = pStm.executeQuery();
            while(rs!=null && rs.next())
            {
            	String codstation = rs.getString("codstation");
            	Station station = new Station();
            	station.setIdstation(rs.getInt("idstation"));
            	station.setDescr(rs.getString("descr"));
            	station.setIdprovincia(rs.getInt("idprovincia"));
            	String condcity = " where codcity = '" + rs.getString("codcity")+"'";
            	City city = JdbcCitiesDao.getCity(condcity);
            	station.setCity(city);
            	
            	if (htstations.get(codstation)!=null)
            	{
            		vstations.add(station);
            	}
            	//Si no existe, no lo añado al vector de ciudades y lo borro de base de datos
            	else
            	{
            		String cond = "";
            		deleteStation(station,cond);
            	}
            }
		}
		catch(SQLException e){logger.error(e.getMessage());conn.close();return vstations;}
        catch(Exception e){logger.error(e.getMessage());conn.close();return vstations;}
		finally
        {
        	if (conn!=null)
        	{
        		conn.close();conn=null;
        	}
        }
    	return vstations;
    }
    
    /**
     * Compruebo si existe la estación en base de datos
     * @param station
     * @param condicion
     * @throws SQLException 
     * @throws Exception
     */
    public static synchronized Station existeStation(Station station, String condicion) throws Exception
    {
    	Station stationnew = null;
    	Connection conn=null;
		try
		{
			conn = conexionBBDD.getConnectionWeb();
    		String sql = "select * from ".concat(nombresch).concat(".").concat(nombretabla);
    		sql += " where codstation = ?";
    		
//    		String sql = "select s.*, c.codcountry  from ".concat(nombresch).concat(".").concat(nombretabla).concat(" s, ").concat(nombresch).concat(".cities c");
//    		sql += " where codstation = ? and s.idprovincia=c.idprovincia and s.codcity=c.codcity";
    		
    		sql += condicion;
            PreparedStatement pStm= conn.prepareStatement(sql);
            pStm.setString(1, station.getCodstation());
            ResultSet rs = pStm.executeQuery();
            if(rs!=null && rs.next())
            {
            	stationnew = new Station();
            	stationnew.setIdprovincia(rs.getInt(1));
            	
            	String codstation = rs.getString("codstation");
            	stationnew.setIdstation(rs.getInt("idstation"));
            	stationnew.setCodstation(codstation);
            	stationnew.setDescr(rs.getString("descr"));
            	stationnew.setIdprovincia(rs.getInt("idprovincia"));
            	
//            	stationnew.setCodCountry(rs.getString("c.codcountry"));
            }
		}
        catch(Exception e){logger.error(e.getMessage());conn.close();return stationnew;}
		finally
        {
        	if (conn!=null)
        	{
        		conn.close();conn=null;
        	}
        }
    	return stationnew;
    }
    
    /**
     * Borro una estación
     * @param station
     * @param condicion
     * @throws SQLException 
     * @throws Exception
     */
    public static synchronized boolean deleteStation(Station station, String condicion) throws SQLException
    {
    	boolean result = false;
    	Connection conn=null;
		try
		{
			conn = conexionBBDD.getConnectionWeb();
    		String sql = "delete from ".concat(nombresch).concat(".").concat(nombretabla);
    		sql += " where codstation = ?";
    		sql += condicion;
            PreparedStatement pStm= conn.prepareStatement(sql);
            pStm.setString(1, station.getCodstation());
            int numfilas = pStm.executeUpdate();
        	if (numfilas>0)
        	{
        		result = true;
        	}
		}
		catch(SQLException e){logger.error(e.getMessage());conn.close();return result;}
        catch(Exception e){logger.error(e.getMessage());conn.close();return result;}
		finally
        {
        	if (conn!=null)
        	{
        		conn.close();conn=null;
        	}
        }
    	return result;
    }
    
    /**
     * ANM Función que obtiene un vector de Stations de la bbdd y completa
     * la informacion del objeto Station con los datos una hashtable de ciudades.
     * @param htcityProv
     * @return
     * @throws SQLException 
     */
    public static synchronized Vector<Station> getAllStation(Hashtable<String,City> htcity ) throws Exception
    {
    	Vector<Station> vstat= new Vector<Station>();
    		
    	Connection conn=null;
		try
		{
			conn = conexionBBDD.getConnectionWeb();
     		
     		String sql ="SELECT c.idcity,c.codcity,c.descr,c.codcountry,c.idprovincia,s.idstation,s.codstation,s.descr "+
     					" FROM  rolecar.cities c, rolecar.stations s "+
     				    " where c.codcity=s.codcity "+
     					" and c.idprovincia=s.idprovincia "+// and s.idprovincia=743 "+ 
     					//" and s.descr like CONCAT('%',c.codcity ,'%') "+  
     					//" group by s.codstation "+
     					" order by c.codcity, s.descr "; 							
     				//"select * from ".concat(nombresch).concat(".").concat(nombretabla).concat(" Order By idprovincia");
            PreparedStatement pStm= conn.prepareStatement(sql);
            ResultSet rs = pStm.executeQuery();
            while(rs!=null && rs.next())
            {
             	
             	Station stat = new Station();
             	City city= new City();
             	Country country= new Country();
             	
             	stat.setCodCity(rs.getString("c.codcity"));
             	city=htcity.get(stat.getCodCity()+rs.getString("codcountry"));
             	country=city.getCountry();
             	stat.setIdstation(rs.getInt("s.idstation"));
             	stat.setDescr(rs.getString("s.descr"));
             	stat.setIdprovincia(rs.getInt("c.idprovincia"));
             	stat.setCodstation(rs.getString("s.codstation"));
             	stat.setCodCountry(country.getCodcountry());
             	stat.setCity(city);
             	stat.setDescrCity(city.getDescr());
             	stat.setDescrCountry(country.getDescr());           
             	vstat.add(stat);
            }
 		}
 		catch(SQLException e){logger.error(e.getMessage());conn.close();return vstat;}
        catch(Exception e){logger.error(e.getMessage());conn.close();return vstat;}
 		finally
         {
         	if (conn!=null)
         	{
         		conn.close();conn=null;
         	}
         }    	
    	return vstat;
    
    }
    /**
     * Actualizo la provincia para tenerla en cuenta de cara a las estaciones
     * @param city
     * @throws SQLException 
     * @throws Exception
     */
    public static synchronized boolean updateProvinciaByCity(City city, int idprovincia, String nombretabla) throws Exception
    {
    	boolean result = false;
    	Connection conn=null;
		try
		{
			conn = conexionBBDD.getConnectionWeb();
    		String sql = "update ".concat(nombresch).concat(".").concat(nombretabla);
    		sql += " set idprovincia = ? where idcity = ?";
            PreparedStatement pStm= conn.prepareStatement(sql);
            pStm.setInt(1, idprovincia);
            pStm.setInt(2, city.getIdcity());
            
            int numfilas = pStm.executeUpdate();
            if (numfilas>0) 
            {
            	result = true;
            }
		}
        catch(Exception e){logger.error(e.getMessage());conn.close();return result;}
		finally
        {
        	if (conn!=null)
        	{
        		conn.close();conn=null;
        	}
        }
    	return result;
    }
    
    
    /**
     * AHP Función que obtiene un vector de las oficinas mas cercanas a la oficina donde se
     * esta realizando la busqueda para Europcar
     * @param htcityProv
     * @return
     * @throws SQLException 
     */
    public static synchronized Vector<String> getClosestation(String codstation,String provincia) throws Exception
    {
    	Vector<String> vstat= new Vector<String>();
    	//String caracteridentificador = codstation.substring(0, 4);
    	Connection con = conexionBBDD.getConnectionWeb(); 	
     	try
     	{
     		
     		String sql ="SELECT s.codstation,s.descr as estacion,c.codcountry,co.descr as pais,c.descr as ciudad"+
     					" FROM  rolecar.cities c, rolecar.stations s, rolecar.countries co "+
     					" where c.idprovincia=s.idprovincia "+//and s.idprovincia="+idprovincia+
     					" and s.idprovincia="+provincia+
     					" and c.codcity=s.codcity "+
     					//" and s.descr like CONCAT('%',c.codcity ,'%') and s.codstation like '"+caracteridentificador+"%' and "+//si pones ___ sustituye caracter
     					" and s.codstation <> '"+codstation+"' "+
     					" and c.codcountry=co.codcountry "+
     					" group by s.codstation "+
     					" order by c.codcity, s.descr "; 							
     				//"select * from ".concat(nombresch).concat(".").concat(nombretabla).concat(" Order By idprovincia");
            PreparedStatement pStm= con.prepareStatement(sql);
            ResultSet rs = pStm.executeQuery();
            while(rs!=null && rs.next())
            {
             	
             	String cod = rs.getString("codstation").concat("::").concat(rs.getString("estacion"))
		             			.concat("::").concat(rs.getString("codcountry"))
		             			.concat("::").concat(rs.getString("pais"))
		             			.concat("::").concat(rs.getString("ciudad"));       
             	vstat.add(cod);
            }
 		}
 		catch(SQLException e){logger.error(e.getMessage());con.close();return vstat;}
        catch(Exception e){logger.error(e.getMessage());con.close();return vstat;}
     	finally
         {
         	if (con!=null)
         	{
         		con.close();con=null;
         	}
         }    	
    	return vstat;
    
    }
    
    
}