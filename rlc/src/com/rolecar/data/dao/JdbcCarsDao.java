//****************************************
// Nombre: JdbcCarsDao
// Descrfipción: Gestión de los vehiculos
// Autor : Zendos
//****************************************
package com.rolecar.data.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.rolecar.beans.Car;
import com.rolecar.beans.Insurance;
import com.rolecar.beans.Quote;
import com.rolecar.beans.Reservation;
import com.rolecar.beans.Station;
import com.rolecar.beans.Tax;
import com.rolecar.data.constantes.Atributos;
import com.rolecar.utils.CarComparator;
import com.rolecar.utils.Formatea;


public class JdbcCarsDao
{
	private static Log logger = LogFactory.getLog(JdbcCarsDao.class);
	private static String carTypeG = "";
	private static boolean haycontrato=true;
	//private static Vector<Car> vcars;
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Obtengo todos los vehículos en base a los parámetros seleccionados
	 * fecha de recogida, hora de recogida, lugar de recogida
	 * fecha de devolución, hora de devolución, lugar de devolución 
	 * @param checkinstationid
	 * @param checkindate
	 * @param checkintime
	 * @param checkoutstationid
	 * @param checkoutdate
	 * @param checkouttime
	 * @param carType
	 * @param orden
	 * @return
	 * @throws Exception
	 */
	public static synchronized Vector<Car> recogeVehiculos(String checkinstationid, String checkindate, String checkintime, String checkoutstationid, String checkoutdate, String checkouttime, String carType, int orden) throws Exception
	{
		Set<String> contractIds = new HashSet<String>(); 
		contractIds.add("52112176");
		contractIds.add("52480793");
		String request = "";
		haycontrato=true;		
		Vector<Car> vcars = new Vector<Car>();
		Vector<Car> vcarsAll = new Vector<Car>();
		try
		{
			Reservation reservation = new Reservation();
			reservation.setCheckinstationId(checkinstationid);
			reservation.setCheckindate(checkindate);
			reservation.setCheckintime(checkintime);
			reservation.setCheckoutstationId(checkoutstationid);
			reservation.setCheckoutdate(checkoutdate);
			reservation.setCheckouttime(checkouttime);
			carTypeG =carType;
			reservation.setCarType(carType);
			//request = getRequestCars(reservation, carType,haycontrato);//true comrpuebo con contract id, aquí utilizaba el antiguo servicio getQuote
			for (String contract : contractIds) {
					
				
				request = getRequestMultipleRatesList(reservation, carType, haycontrato, contract);
				if (request!=null && !request.equalsIgnoreCase(""))
				{
					logger.info("Empieza la busqueda del listado de coches");
					//Compruebo que mi listado con contractid devuelve coches, si no devuelve coches, entonces  envio una petición
					//nueva para recoger un listado de coches sin contractid
					vcars = getResponseCarsList(request,reservation,haycontrato);//Nuevo metodo con getMultiplerates 
					//vcars = getResponseCars(request,reservation,haycontrato);//antiguo metodo con getQuotes
					if(vcars.size()==0)
					{
						haycontrato=false;
						//request=getRequestCars(reservation, carType,haycontrato);//false elimino el contratid, obtengo una lista general de automoviles de europcar, antiguo metodo getQuote
						request = getRequestMultipleRatesList(reservation, carType, haycontrato, null);//Nuevo metodo con getMultipleRates
						vcars = getResponseCarsList(request,reservation,haycontrato);//Nuevo metodo con getMultiplerates 
						//vcars = getResponseCars(request,reservation,haycontrato);//antiguo metodo con getQuotes
					}
					
				}
				if (vcars.size() > 0){
					vcarsAll.addAll(vcars);
				}
			}
			Collections.sort(vcarsAll,new CarComparator(orden));
			removeDuplicates(vcarsAll);
			
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return vcars;
		}
		return vcarsAll;
	}
	
	private static void removeDuplicates(Vector<Car> vcarsAll) {
		String data;
		//System.out.println("Size vcar:: " + vcarsAll.size());
		List<Car> removeData = new ArrayList<Car>();
		for (int i = 0; i < vcarsAll.size(); i++) {
			data = vcarsAll.get(i).getCarCategoryCode();
			for (int x = i+1; x < vcarsAll.size(); x++) {
				if (vcarsAll.get(x).getCarCategoryCode().equals(data)){
					removeData.add(vcarsAll.get(x));
				}
			}
		}
		for (Car idx : removeData) {
			vcarsAll.remove(idx);
		}
	}

	/**
     * Inserto el listado de vehículos en base de datos, metodo antiguo con getQuote
     * @param listcars
     * @throws Exception
     */
    public static synchronized Vector<Car> recogeVehiculos(NodeList listcars, Reservation reservation,boolean haycontractid)
    {
    	Vector<Car> vcars = new Vector<Car>();
    	String cantidadC02="";
    	try
    	{
    			
	    		for (int i = 0; i < listcars.getLength() ; i ++) 
				{
					Element el = (Element) listcars.item(i);
					Car car = new Car();
					car.setCarCategoryAirCond(el.getAttribute("carCategoryAirCond"));
					car.setCarCategoryAutomatic(el.getAttribute("carCategoryAutomatic"));
					cantidadC02=el.getAttribute("carCategoryCO2Quantity");
					if(Formatea.isNumericEntero(cantidadC02))
					{
					car.setCarCategoryCO2Quantity(Integer.parseInt(el.getAttribute("carCategoryCO2Quantity")));
					}
					else
					{
						car.setCarCategoryCO2Quantity(0);	
					}
					car.setCarCategoryCode(el.getAttribute("carCategoryCode"));
					car.setCarCategoryDoors(el.getAttribute("carCategoryDoors"));
					car.setCarCategoryName(el.getAttribute("carCategoryName"));
					car.setCarCategorySample(el.getAttribute("carCategorySample"));
					car.setCarCategorySeats(el.getAttribute("carCategorySeats"));
					car.setCarCategoryStatusCode(el.getAttribute("carCategoryStatusCode"));
					car.setCarCategoryType(el.getAttribute("carCategoryType"));
					car.setCarCategoryBaggageQuantity(el.getAttribute("carCategoryBaggageQuantity"));
					car.setCarType(el.getAttribute("carType"));
					String requestquotes = getRequestQuotes(reservation,car,haycontractid);
					//String requestmultiplerates = getRequestMultipleRates(reservation, car);
					//Car car2 = new Car();
					//car2 = getResponseQuotes(car, reservation, requestmultiplerates );
					//String ho=getResponseMultiplesRates(getRequestMultipleRatesPrueba(reservation, car,haycontractid));
					car = getResponseQuotes(car, reservation, requestquotes);
					
					//Sólo lo añado si la categoría coincide
					if (car.getCarType().equalsIgnoreCase(carTypeG))
					{
						//Y además tiene tarifa disponible, por lo que está disponible
						if (car.isHaytarifas())
						{
							//Recogemos el precio de la oficina de Multiple rates, ya que en el servicio getQuote puede no devolverlo
							car.getQuote().setTotalRateEstimateInRentingCurrency(getResponseMultiplesRates(getRequestMultipleRates(reservation, car,haycontractid)));
							//Comparo los dos precios , el online y el de oficina, si el de oficina es menor, se colocan los dos iguales como el de oficina
							if(Formatea.comparar(car.getQuote().getTotalRateEstimateInBookingCurrency(), car.getQuote().getTotalRateEstimateInRentingCurrency())==1)
								car.getQuote().setTotalRateEstimateInBookingCurrency(car.getQuote().getTotalRateEstimateInRentingCurrency());
							vcars.add(car);
						}
					}
					
				}
    		
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return vcars;
    	}
    	return vcars;
    }
    
    /**
     * consigo el listado de vehiculos con nuevo servicio de getMultipleRate
     * @param listcars
     * @param reservation
     * @param haycontractid
     * @return
     */
    public static synchronized Vector<Car> getVehiculosList(NodeList listcars, Reservation reservation,boolean haycontractid)
    {
    	Vector<Car> vcars = new Vector<Car>();
    	String cantidadC02="";
    	String currency = "";
    	Car car = new Car();
    	
//    	boolean todosanullol = false;
//    	boolean algunoanullol = false;
//    	boolean todosanullof = false;
//    	boolean algunoanullof = false;
//    	int conanullol = 0;
//    	int conanullof = 0;
    	try
    	{
    			int cont = 0;
	    		for (int i = 0; i < listcars.getLength() ; i ++) 
				{
					Element el = (Element) listcars.item(i);
					boolean haytarifas = false;
					if(car.getCarCategoryCode()==null || !car.getCarCategoryCode().equalsIgnoreCase(el.getAttribute("carCategoryCode")))
					{	
						car = new Car();
						Quote tarifa = new Quote();
						if (haycontractid){
							car.setContractId(el.getAttribute("contractID"));
						}
						car.setCarCategoryAirCond(el.getAttribute("carCategoryAirCond"));
						car.setCarCategoryAutomatic(el.getAttribute("carCategoryAutomatic"));
						cantidadC02=el.getAttribute("carCategoryCO2Quantity");
						if(Formatea.isNumericEntero(cantidadC02))
						{
						car.setCarCategoryCO2Quantity(Integer.parseInt(el.getAttribute("carCategoryCO2Quantity")));
						}
						else
						{
							car.setCarCategoryCO2Quantity(0);	
						}
						car.setCarCategoryCode(el.getAttribute("carCategoryCode"));
						car.setCarCategoryDoors(el.getAttribute("carCategoryDoors"));
						car.setCarCategoryName(el.getAttribute("carCategoryName"));
						car.setCarCategorySample(el.getAttribute("carCategorySample"));
						car.setCarCategorySeats(el.getAttribute("carCategorySeats"));
						car.setCarCategoryStatusCode(el.getAttribute("carCategoryStatusCode"));
						car.setCarCategoryType(el.getAttribute("carCategoryType"));
						car.setCarCategoryBaggageQuantity(el.getAttribute("carCategoryBaggageQuantity"));
						car.setCarType(el.getAttribute("carType"));
						
						//Compruebo si es online o de oficina y asigno el precio
						if(el.getAttribute("isPrepaid").equalsIgnoreCase("Y"))
		    			{	
							if(el.getAttribute("totalRateEstimateInBookingCurrency")!=null && !el.getAttribute("totalRateEstimateInBookingCurrency").equalsIgnoreCase(""))
								tarifa.setTotalRateEstimateInBookingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
							else
							{
								tarifa.setTotalRateEstimateInBookingCurrency("");
//								algunoanullol = true;
//								conanullol++;
							}
								
		    			}
		    			else if(el.getAttribute("isPrepaid").equalsIgnoreCase("N"))
		    			{
		    				if(el.getAttribute("totalRateEstimateInBookingCurrency")!=null && !el.getAttribute("totalRateEstimateInBookingCurrency").equalsIgnoreCase(""))
		    					tarifa.setTotalRateEstimateInRentingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
		    				else
		    				{
		    					//algunoanullof = true;
		    					tarifa.setTotalRateEstimateInRentingCurrency("");
		    					//conanullof++;
		    				}
		    					
		    			}
		    			
		    			currency = el.getAttribute("bookingCurrencyOfTotalRateEstimate");
		    			
		    			tarifa.setCurrency(currency);
		    			tarifa.setIncludedKm(el.getAttribute("includedKm"));
		    			car.setQuote(tarifa);
						
		    			//****************Edad minima para la reserva del vehículo*************************
		    			NodeList listage = el.getElementsByTagName("ageLimit");
		    			
		    			Element elementoage = (Element) listage.item(0);
		    			Reservation reser =(Reservation) reservation.clone();
		    			reser.setMinAgeForCountry(elementoage.getAttribute("minAgeForCountry"));
		    			reser.setMinAgeForCategory(elementoage.getAttribute("minAgeForCategory"));
		    			
		    			//Incluimos el objeto reserva en el objeto coche
		    			car.setCarType(reser.getCarType());
		    			car.setReservation(reser);
		    			
		    			//Obtenemos los datos de la estación de checkout o recogida de vehículo
		    			Station stcheckout = new Station();
		    			stcheckout.setCodstation(reser.getCheckoutstationId());
		    			stcheckout = JdbcStationsDao.existeStation(stcheckout, "");
		    			car.setStationcheckout(stcheckout);
		    			
		    			//Obtenemos los datos de la estación de checkin o devolución de vehículo
		    			Station stcheckin = new Station();
		    			stcheckin.setCodstation(reser.getCheckinstationId());
		    			stcheckin = JdbcStationsDao.existeStation(stcheckin, "");
		    			car.setStationcheckin(stcheckin);
		    			
		    			//Recogemos el nodo de tasas
		    			NodeList listtax = el.getElementsByTagName("tax");
		    			Vector<Tax> vtaxes = recogeTaxes(listtax);
		    			
		    			//Incluimos el vector de tasas en el objeto coche
		    			car.setVtaxes(vtaxes);
		    			car.setHaytarifas(true);
		    			
						//car=getResponseMultiplesRatesList(car, reservation,getRequestMultipleRates(reservation, car,haycontractid));
					
		    			
		    			//seguro del coche
		    			NodeList listseguro = el.getElementsByTagName("insurance");
		    			car.setVinsurances(recogeInsurances(listseguro));
		    			
						//Sólo lo añado si la categoría coincide
						if (car.getCarType().equalsIgnoreCase(carTypeG))
						{
							//Y además tiene tarifa disponible, por lo que está disponible
							if (car.isHaytarifas())
							{
								
								haytarifas = true;
							}
						}
						
					}
					else
					{
						Car caraux = vcars.elementAt(cont);
						if (vcars.elementAt(cont)!=null)
						{
							if(el.getAttribute("isPrepaid").equalsIgnoreCase("Y"))
			    			{	
								if(el.getAttribute("totalRateEstimateInBookingCurrency")!=null && !el.getAttribute("totalRateEstimateInBookingCurrency").equalsIgnoreCase(""))
									caraux.getQuote().setTotalRateEstimateInBookingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
								else
									caraux.getQuote().setTotalRateEstimateInBookingCurrency("");
			    			}
			    			else if(el.getAttribute("isPrepaid").equalsIgnoreCase("N"))
			    			{
			    				if(el.getAttribute("totalRateEstimateInBookingCurrency")!=null && !el.getAttribute("totalRateEstimateInBookingCurrency").equalsIgnoreCase(""))
			    					caraux.getQuote().setTotalRateEstimateInRentingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
			    				else
			    					caraux.getQuote().setTotalRateEstimateInRentingCurrency("");
			    			}
							vcars.remove(cont);
							vcars.add(caraux);
							cont++;
						}
					}
					if (haytarifas)
					{
						vcars.add(car);
						haytarifas = false;
						
					}
//					else
//						Car caraux = new Car();
				}
    		
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return vcars;
    	}
    	return vcars;
    }
	
    //*****************************************************FINAL DE FUNCIONES DE LISTADO DE VEHICULOS******************************
    
    
   
	
	/**
     * Obtengo la lista de nodos de coches metodo que utilizo con el servicio getQuotes
     * @param texto
     * @throws Exception
     */
	public static synchronized Vector<Car> getResponseCars(String texto, Reservation reservation,boolean haycontractid)
    {
    	String responseStr = "";
    	Vector<Car> vcars = new Vector<Car>();
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
    		if (!responseStr.contains("carCategoryList"))
    		{
    			logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			NodeList listcars = doc.getElementsByTagName("carCategory");
    			vcars = recogeVehiculos(listcars, reservation,haycontractid);
    		}
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return vcars;
       }
       return vcars;
    }
	
//*************************************************Se crea esta función para crear el listado desde getMultipleRates, se elimina el servicio getQuote	
	/**
	 * Obtengo la lista de nodos de coches para el listado desde el servicio getMultipleRates
	 * @param texto
	 * @param reservation
	 * @param haycontractid
	 * @return
	 */
	public static synchronized Vector<Car> getResponseCarsList(String texto, Reservation reservation,boolean haycontractid)
    {
    	String responseStr = "";
    	Vector<Car> vcars = new Vector<Car>();
    	HttpsURLConnection con = null;
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

    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuilder response = new StringBuilder();
   
    		while ((inputLine = in.readLine()) != null)
    		{
    			response.append(inputLine);
    		}
    		in.close();
    		responseStr = response.toString();
    		//System.out.println("XML:: " + responseStr);
    		con.disconnect();
    		if (!responseStr.contains("returnCode=\"OK\""))
    		{
    			logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			NodeList listcars = doc.getElementsByTagName("reservationRate");
    			vcars = getVehiculosList(listcars, reservation,haycontractid);
    		}
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return vcars;
       }
       return vcars;
    }

/////PRUEBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	public static synchronized Map<String, ArrayList<Car>> getResponseCarsListMap(String texto, Reservation reservation,boolean haycontractid)
    {
    	String responseStr = "";
    	Map<String, ArrayList<Car>> mCars =  new HashMap<String, ArrayList<Car>>();
    	List<Car> arrCar = new ArrayList<Car>();
    	
    	
    	Vector<Car> vcars = new Vector<Car>();
    	HttpsURLConnection con = null;
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

    		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String inputLine;
    		StringBuilder response = new StringBuilder();
   
    		while ((inputLine = in.readLine()) != null)
    		{
    			response.append(inputLine);
    		}
    		in.close();
    		responseStr = response.toString();
    		//System.out.println("XML:: " + responseStr);
    		con.disconnect();
    		if (!responseStr.contains("returnCode=\"OK\""))
    		{
    			logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			NodeList listcars = doc.getElementsByTagName("reservationRate");
    			vcars = getVehiculosList(listcars, reservation, haycontractid);
    		}
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return mCars;
       }
       return mCars;
    }

//****************************************************************************************************************************************
    
//**********************************************************REQUEST XML**********************
	/**
	 * CBC Construyo el request de vehículos para enviar al WS
	 * 
	 * @return
	 */
	public static String getRequestCars(Reservation reservation, String carType, boolean haycontractid)
	{
		String request = "";
		String contractid ="";
		String contractidsincar ="";
		try
		{
			request += Atributos.CABECERAXML;
			//Se recibe el tipo de coche
			if(haycontractid)
			{
				contractid=" contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"";
				contractidsincar= "<reservation contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"  >" ;
			}
			if (carType!=null && !carType.equalsIgnoreCase(""))
			{
				
				
				request += "<message>"
							+ "<serviceRequest serviceCode=\"getCarCategories\">"
							+ Atributos.LANGUAGE_ES
								+ "<serviceParameters>"
								   //+ "<reservation contractID=\""+Atributos.CONTRACTID+"\">"
									+ "<reservation carType=\""+carType+"\""+contractid+" >"// contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"  >"
										+ "<checkout stationID=\""+reservation.getCheckoutstationId()+"\" date=\""+reservation.getCheckoutdate()+"\" time=\""+reservation.getCheckouttime()+"\" language=\"ES\"/>"
										+ "<checkin stationID=\""+reservation.getCheckinstationId()+"\" date=\""+reservation.getCheckindate()+"\" time=\""+reservation.getCheckintime()+"\" language=\"ES\"/>"
									+ "</reservation>"
								+ "</serviceParameters>"
							+ "</serviceRequest>"
						+ "</message>";
			}
			//No se recibe tipo de coche
			else
			{
				
				
				request += "<message>"
							+ "<serviceRequest serviceCode=\"getCarCategories\">"
								+ "<serviceParameters>"
								   //+ "<reservation contractID=\""+Atributos.CONTRACTID+"\">"
								    + contractidsincar 
										+ "<checkout stationID=\""+reservation.getCheckoutstationId()+"\" date=\""+reservation.getCheckoutdate()+"\" time=\""+reservation.getCheckouttime()+"\" language=\"ES\"/>"
										+ "<checkin stationID=\""+reservation.getCheckinstationId()+"\" date=\""+reservation.getCheckindate()+"\" time=\""+reservation.getCheckintime()+"\" language=\"ES\"/>"
							        + "</reservation>"
								 + "</serviceParameters>"
							+ "</serviceRequest>"
						+ "</message>";
			}
			
    		request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return request;
		}
		return request;
	}
    
    /**
	 * CBC Construyo el request de Quotes para enviar al WS
	 * y devuelvo el mismo objeto car con quote, reservation y delivery relleno
	 * @param car
	 * @param reservation
	 * @return
	 */
	public static String getRequestQuotes(Reservation reservation, Car car,boolean haycontractid)
	{
		String request = "";
		String contractid ="";
		
		try
		{
			if(haycontractid)
			{
				contractid ="contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"";
			}
			
			request += Atributos.CABECERAXML;
			//Enviamos para recibir quote
			request += "<message>"
						+ "<serviceRequest serviceCode=\"getQuote\">"
						+ Atributos.LANGUAGE_ES
							+ "<serviceParameters>"
								+ "<reservation carCategory=\""+car.getCarCategoryCode()+"\" carType=\""+car.getCarType()+"\" " +contractid+ ">"
									+ "<checkout stationID=\""+reservation.getCheckoutstationId()+"\" date=\""+reservation.getCheckoutdate()+"\" time=\""+reservation.getCheckouttime()+"\" language=\"ES\"/>"
									+ "<checkin stationID=\""+reservation.getCheckinstationId()+"\" date=\""+reservation.getCheckindate()+"\" time=\""+reservation.getCheckintime()+"\" language=\"ES\"/>"
								+ "</reservation>"
								//Por el momento, por defecto, el conductor será de España
								+ "<driver countryOfResidence=\"ES\" />"
							+ "</serviceParameters>"
						+ "</serviceRequest>"
					+ "</message>";
			request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return request;
		}
		return request;
	}
	
	
	/**
	 * AHP para poder acceder a los precios tanto de oficina como online
	 * @param reservation
	 * @param car
	 * @return
	 */
	public static String getRequestMultipleRates(Reservation reservation, Car car,boolean haycontractid)
	{
		String request = "";
		String contractid ="";
		
		try
		{
			if(haycontractid)
			{
				contractid =" contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"";
			}
			request += Atributos.CABECERAXML;
			//Enviamos para recibir Multiples rates
			request += "<message>"
						+ "<serviceRequest serviceCode=\"getMultipleRates\">"
						+ Atributos.LANGUAGE_ES
							+ "<serviceParameters>"
								+ "<reservation carCategoryPattern=\""+car.getCarCategoryCode()+"\" carType=\""+car.getCarType()+"\" prepaidMode=\"B\""+contractid+" >"//prepaidMode=\"B\" contractID=\""+Atributos.CONTRACTID+"\" type=\"C\" >"
									+ "<checkout stationID=\""+reservation.getCheckoutstationId()+"\" date=\""+reservation.getCheckoutdate()+"\" time=\""+reservation.getCheckouttime()+"\" language=\"ES\"/>"
									+ "<checkin stationID=\""+reservation.getCheckinstationId()+"\" date=\""+reservation.getCheckindate()+"\" time=\""+reservation.getCheckintime()+"\" language=\"ES\"/>"
								+ "</reservation>"
								//Por el momento, por defecto, el conductor será de España
								+ "<driver countryOfResidence=\"ES\" />"
							+ "</serviceParameters>"
						+ "</serviceRequest>"
					+ "</message>";
			request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return request;
		}
		return request;
	}
	
	/**
	 * AHP para poder acceder al listado segun el tipo de coche y en contractid
	 * @param reservation
	 * @param car
	 * @return
	 */
	public static String getRequestMultipleRatesList(Reservation reservation, String cartype,boolean haycontractid, String contractId)
	{
		String request = "";
		String contractid ="";
		
		try
		{
			if(haycontractid)
			{
				//contractid =" contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"";
				contractid =" contractID=\""+contractId+"\" type=\"C\"";
			}
			request += Atributos.CABECERAXML;
			//Enviamos para recibir Multiples rates
			request += "<message>"
						+ "<serviceRequest serviceCode=\"getMultipleRates\">"
						+ Atributos.LANGUAGE_ES
							+ "<serviceParameters>"
								+ "<reservation  chargesDetail=\"TRE\" rateDetails=\"Y\" carType=\""+cartype+"\" prepaidMode=\"B\""+contractid+" >"//prepaidMode=\"B\" contractID=\""+Atributos.CONTRACTID+"\" type=\"C\" >"
									+ "<checkout stationID=\""+reservation.getCheckoutstationId()+"\" date=\""+reservation.getCheckoutdate()+"\" time=\""+reservation.getCheckouttime()+"\" language=\"ES\"/>"
									+ "<checkin stationID=\""+reservation.getCheckinstationId()+"\" date=\""+reservation.getCheckindate()+"\" time=\""+reservation.getCheckintime()+"\" language=\"ES\"/>"
								+ "</reservation>"
								//Por el momento, por defecto, el conductor será de España
								+ "<driver countryOfResidence=\"ES\" />"
							+ "</serviceParameters>"
						+ "</serviceRequest>"
					+ "</message>";
			request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return request;
		}
		return request;
	}
	
	
	//************************************************************************************************************************
	//************************************************************************************************************************
	/**
	 * AHP prueba a fuego de europcar
	 * @param reservation
	 * @param car
	 * @return
	 */
	public static String getRequestMultipleRatesPrueba(Reservation reservation, Car car,boolean haycontractid)
	{
		String request = "";
				
		try
		{
			request += Atributos.CABECERAXML;
			request += "<message>"+
			          "<serviceRequest serviceCode=\"getMultipleRates\">"+
			          "<serviceContext>"+
			          "<localisation active=\"true\">"+
			          "<language code=\"fr_FR\"/>"+
			          "</localisation>"+
			          "</serviceContext>"+
			          "<serviceParameters>"+
			          "<reservation contractID=\"52112176\" type=\"C\" IATANumber=\"01522830\" carType=\"TR\"  carCategoryPattern=\"VPIW\" rateDetails=\"Y\">"+
			          "<checkout stationID=\"MADL02\" date=\"20141110\" time=\"1200\" />"+
			          "<checkin stationID=\"MADL02\" date=\"20141112\" time=\"1200\" />"+
			          "</reservation>"+
			          "<driver countryOfResidence=\"ES\" />"+
			          "</serviceParameters>"+
			          "</serviceRequest>"+
			          "</message>";
			request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return request;
		}
		return request;
	}
	//*******************************************************************************************************
	//*******************************************************************************************************
	
	/**
     * Obtengo la lista de nodos de reserva, quotes e insurance
     * @param car
     * @param reservation
     * @param texto
     * @throws Exception
     */
	public static synchronized Car getResponseQuotes(Car car, Reservation reservation, String texto)
    {
    	String responseStr = "";
    	HttpsURLConnection con = null;
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
    		if (!responseStr.contains("quote"))
    		{
    			if (responseStr.contains("noratefound"))
    			{
    				car.setHaytarifas(false);
    			}
    			else
    				logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			logger.info("Obtengo el listado de vehículos según los criterios seleccionados.");
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			
    			//Recogemos el nodo de reserva
    			NodeList listreservation = doc.getElementsByTagName("reservation");
    			reservation = recogeReservation(listreservation, reservation);
    			
    			//Recogemos la edad mínima para reservar
    			NodeList listage = doc.getElementsByTagName("ageLimit");
    			Element el = (Element) listage.item(0);
    			reservation.setMinAgeForCategory(el.getAttribute("minAgeForCategory"));
    			reservation.setMinAgeForCountry(el.getAttribute("minAgeForCountry"));
    			
    			//Incluimos el objeto reserva en el objeto coche
    			car.setReservation(reservation);
    			
    			//Obtenemos los datos de la estación de checkout o recogida de vehículo
    			Station stcheckout = new Station();
    			stcheckout.setCodstation(reservation.getCheckoutstationId());
    			stcheckout = JdbcStationsDao.existeStation(stcheckout, "");
    			car.setStationcheckout(stcheckout);
    			
    			//Obtenemos los datos de la estación de checkin o devolución de vehículo
    			Station stcheckin = new Station();
    			stcheckin.setCodstation(reservation.getCheckinstationId());
    			stcheckin = JdbcStationsDao.existeStation(stcheckin, "");
    			car.setStationcheckin(stcheckin);
    			
    			//Recogemos el nodo de quote
    			NodeList listquote = doc.getElementsByTagName("quote");
    			Quote quote = recogeQuote(listquote);
    			
    			//Incluimos el objeto quote en el objeto coche
    			car.setQuote(quote);
    			
    			//Recogemos el nodo de seguro
    			NodeList listinsurances = doc.getElementsByTagName("insurance");
    			Vector<Insurance> vinsurances = recogeInsurances(listinsurances);
    			
    			//Incluimos el vector de insurances en el objeto coche
    			car.setVinsurances(vinsurances);
    			
    			//Recogemos el nodo de tasas
    			NodeList listaxes = doc.getElementsByTagName("tax");
    			Vector<Tax> vtaxes = recogeTaxes(listaxes);
    			
    			//Incluimos el vector de tasas en el objeto coche
    			car.setVtaxes(vtaxes);
    			car.setHaytarifas(true);
    		}
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return car;
       }
       return car;
    }
	
	/**
	 * Consigue multiple coches con sus datos segun la petición xml
	 * @param xmlpeticion
	 * @return
	 */
	public static synchronized String getResponseMultiplesRates( String xmlpeticion)
    {
    	String responseStr = "";
    	String preciooficina = "";
    	HttpsURLConnection con = null;
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
    		wr.writeBytes(xmlpeticion);
    		wr.flush();
    		wr.close();

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
			logger.info("Obtengo el listado de vehículos según los criterios seleccionados.");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseStr));
			Document doc = db.parse(is);
			
			//Recogemos el nodo de reserva
			NodeList listreservationrate = doc.getElementsByTagName("reservationRate");
			preciooficina = recogeReservationRate(listreservationrate);
    		
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return preciooficina;
       }
       return preciooficina;
    }
	
	/**
	 * 
	 * @param xmlpeticion
	 * @return
	 */
	public static Car getResponseMultiplesRatesCar(Car car, Reservation reservation, String xmlpeticion)
    {
    	String responseStr = "";
    	Quote tarifa = new Quote();
    	String currency = "";
    	HttpsURLConnection con = null;
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
    		wr.writeBytes(xmlpeticion);
    		wr.flush();
    		wr.close();

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
			logger.info("Obtengo el listado de vehículos según los criterios seleccionados.");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(responseStr));
			Document doc = db.parse(is);
			
			//Recogemos el nodo de reservarate
			NodeList listreservationrate = doc.getElementsByTagName("reservationRate");
			//preciooficina = recogeReservationRate(listreservationrate);
			for(int i=0;i<listreservationrate.getLength();i++)
			{	
    			Element el = (Element) listreservationrate.item(i);
    			if(el.getAttribute("isPrepaid").equalsIgnoreCase("Y"))
    			{	
    			    tarifa.setTotalRateEstimateInBookingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
    			}
    			else if(el.getAttribute("isPrepaid").equalsIgnoreCase("N"))
    			{
    				tarifa.setTotalRateEstimateInRentingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
    			}
    			
    			currency = el.getAttribute("bookingCurrencyOfTotalRateEstimate");
    			
			}
			tarifa.setCurrency(currency);
			car.setQuote(tarifa);
			
			//Recogemos la edad mínima para reservar
			NodeList listage = doc.getElementsByTagName("ageLimit");
			Element el = (Element) listage.item(0);
			reservation.setMinAgeForCountry(el.getAttribute("minAgeForCountry"));
			
			//Incluimos el objeto reserva en el objeto coche
			car.setReservation(reservation);
			
			//Obtenemos los datos de la estación de checkout o recogida de vehículo
			Station stcheckout = new Station();
			stcheckout.setCodstation(reservation.getCheckoutstationId());
			stcheckout = JdbcStationsDao.existeStation(stcheckout, "");
			car.setStationcheckout(stcheckout);
			
			//Obtenemos los datos de la estación de checkin o devolución de vehículo
			Station stcheckin = new Station();
			stcheckin.setCodstation(reservation.getCheckinstationId());
			stcheckin = JdbcStationsDao.existeStation(stcheckin, "");
			car.setStationcheckin(stcheckin);
			
			//Recogemos el nodo de tasas
			NodeList listaxes = doc.getElementsByTagName("tax");
			Vector<Tax> vtaxes = recogeTaxes(listaxes);
			
			//Incluimos el vector de tasas en el objeto coche
			car.setVtaxes(vtaxes);
			car.setHaytarifas(true);
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return car;
       }
       return car;
    }
	
	
	
	/**
     * Recogemos precio oficina
     * @param listreservation
     * @throws Exception
     */
    public static String recogeReservationRate(NodeList listreservation)
    {
    	String precio="";
    	try
    	{
//    		Element ele = (Element) listreservation.item(0);
//    		System.out.println(""+ele.getAttribute("totalRateEstimateInBookingCurrency"));
    		
    		for(int i=0;i<listreservation.getLength();i++)
    		{
    			Element el = (Element) listreservation.item(i);
    			if(el.getAttribute("isPrepaid").equalsIgnoreCase("N"))
    			{
    				precio = el.getAttribute("totalRateEstimateInBookingCurrency");
    				break;
    			}
    			
    		}
			
			
    		
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return precio;
    	}
    	return precio;
    }
    
	
	/**
     * Recogemos la reserva
     * @param listcars
     * @throws Exception
     */
    public static synchronized Reservation recogeReservation(NodeList listreservation, Reservation reservation)
    {
    	try
    	{
			Element el = (Element) listreservation.item(0);
			reservation.setCarCategory(el.getAttribute("carCategory"));
			reservation.setCarCategoryCO2Quantity(el.getAttribute("carCategoryCO2Quantity"));
			reservation.setCarCategoryType(el.getAttribute("carCategoryType"));
			reservation.setCountryOfReservation(el.getAttribute("countryOfReservation"));
			reservation.setDuration(Integer.parseInt(el.getAttribute("duration")));
			reservation.setProductfamily(el.getAttribute("productFamily"));
			reservation.setRateId(el.getAttribute("rateId"));
			reservation.setReservationDate(el.getAttribute("reservationDate"));
			reservation.setReservationTime(el.getAttribute("reservationTime"));
			reservation.setStatusCode(el.getAttribute("statusCode"));
			reservation.setType(el.getAttribute("type"));
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return reservation;
    	}
    	return reservation;
    }
    
    /**
     * Recogemos la reserva
     * @param listcars
     * @throws Exception
     */
    public static Quote recogeQuote(NodeList lisquote)
    {
    	Quote quote = new Quote();
    	try
    	{
			Element el = (Element) lisquote.item(0);
			quote.setIncludedKmUnit(el.getAttribute("IncludedKmUnit"));
			quote.setAreTaxesIncluded(el.getAttribute("areTaxesIncluded"));
			quote.setBaseCount(el.getAttribute("baseCount"));
			quote.setBasePrice(el.getAttribute("basePrice"));
			quote.setBaseType(el.getAttribute("baseType"));
			quote.setBookingCurrencyOfTotalRateEstimate(el.getAttribute("bookingCurrencyOfTotalRateEstimate"));
			quote.setCurrency(el.getAttribute("currency"));
			quote.setExtraKmPrice(el.getAttribute("extraKmPrice"));
			quote.setIncludedKm(el.getAttribute("includedKm"));
			quote.setIncludedKmType(el.getAttribute("includedKmType"));
			quote.setIsAuthorizedForPublicUse(el.getAttribute("isAuthorizedForPublicUse"));
			quote.setIsPrepaid(el.getAttribute("isPrepaid"));
			quote.setIsPriceSecret(el.getAttribute("isPriceSecret"));
			quote.setRentingCurrencyOfTotalRateEstimate(el.getAttribute("rentingCurrencyOfTotalRateEstimate"));
			quote.setTotalRateEstimate(el.getAttribute("totalRateEstimate"));
			quote.setTotalRateEstimateInBookingCurrency(el.getAttribute("totalRateEstimateInBookingCurrency"));
			quote.setTotalRateEstimateInRentingCurrency(el.getAttribute("totalRateEstimateInRentingCurrency"));
			quote.setXrsBasePrice(el.getAttribute("xrsBasePrice"));
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return quote;
    	}
    	return quote;
    }
    
    /**
     * Recogemos insurance
     * @param listcars
     * @throws Exception
     */
    public static Vector<Insurance> recogeInsurances(NodeList listinsurances)
    {
    	Vector<Insurance> vinsurances = new Vector<Insurance>();
    	try
    	{
    		for (int i = 0; i < listinsurances.getLength() ; i ++) 
			{
				Insurance insurance = new Insurance();
    			Element el = (Element) listinsurances.item(i);
    			insurance.setBkExcessWithPOM(el.getAttribute("bkExcessWithPOM"));
    			insurance.setCode(el.getAttribute("code"));
    			insurance.setDescr(el.getAttribute("descr"));
    			insurance.setExcessWithPOM(el.getAttribute("excessWithPOM"));
    			insurance.setPrice(el.getAttribute("price"));
    			insurance.setPriceInBookingCurrency(el.getAttribute("priceInBookingCurrency"));
    			insurance.setRentalPriceAI(el.getAttribute("rentalPriceAI"));
    			insurance.setRentalPriceInBookingCurrencyAI(el.getAttribute("rentalPriceInBookingCurrencyAI"));
    			insurance.setType(el.getAttribute("type"));
    			vinsurances.add(insurance);
			}
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return vinsurances;
    	}
    	return vinsurances;
    }
    
    /**
     * Recogemos las tasas
     * @param listaxes
     * @throws Exception
     */
    public static Vector<Tax> recogeTaxes(NodeList listaxes)
    {
    	Vector<Tax> vtaxes = new Vector<Tax>();
    	try
    	{
    		for (int i = 0; i < listaxes.getLength() ; i ++) 
			{
				Tax tax = new Tax();
    			Element el = (Element) listaxes.item(i);
    			tax.setTaxCode(el.getAttribute("taxCode"));//bkExcessWithPOM"));
    			tax.setTaxRate(el.getAttribute("taxRate"));
    			vtaxes.add(tax);
			}
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return vtaxes;
    	}
    	return vtaxes;
    }
    
    /**
     * Recogemos el seguro del coche
     * @param listinsurance
     * @return
     */
    public static Vector<Insurance> recogeSeguro(NodeList listinsurance)
    {
    	Vector<Insurance> vseguros = new Vector<Insurance>();
    	try
    	{
    		for (int i = 0; i < listinsurance.getLength() ; i ++) 
			{
				Insurance seguro = new Insurance();
    			Element el = (Element) listinsurance.item(i);
    			seguro.setBkExcessWithPOM(el.getAttribute("bkExcessWithPOM"));
    			seguro.setCode(el.getAttribute("code"));
    			seguro.setExcessWithPOM(el.getAttribute("excessWithPOM"));
    			seguro.setPrice(el.getAttribute("price"));
    			seguro.setPriceInBookingCurrency(el.getAttribute("priceInBookingCurrency"));
    			seguro.setRentalPriceAI(el.getAttribute("rentalPriceAI"));
    			seguro.setRentalPriceInBookingCurrencyAI("rentalPriceInBookingCurrencyAI");
    			seguro.setType(el.getAttribute("type"));
    			
    			vseguros.add(seguro);
			}
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return vseguros;
    	}
    	return vseguros;
    }
    
    
    
    /******************************************Para coches en otras localidades*************************************************************/
    public static synchronized Car recogeprimerVehiculolocalidad(String checkinstationid, String checkindate, String checkintime, String checkoutstationid, String checkoutdate, String checkouttime, String carType, int orden, String codpais,String pais,String ciudad) throws Exception
	{
		String request = "";
		Car car = new Car();
		haycontrato=true;
		try
		{
			logger.info("Recoge primer vehiculo mas barato");;
			Reservation reservation = new Reservation();
			reservation.setCheckinstationId(checkinstationid);
			reservation.setCheckindate(checkindate);
			reservation.setCheckintime(checkintime);
			reservation.setCheckoutstationId(checkoutstationid);
			reservation.setCheckoutdate(checkoutdate);
			reservation.setCheckouttime(checkouttime);
			carTypeG =carType;
			request = getRequestMultipleRateslocalidad(reservation,haycontrato);
			if (request!=null && !request.equalsIgnoreCase(""))
			{
				car = getResponsePreciolocalidad(request,reservation);
				car.getStationcheckout().setCodCountry(codpais);
				car.getStationcheckout().setDescrCountry(pais);
				car.getStationcheckout().setDescrCity(ciudad);
			}
			//Collections.sort(vcars,new CarComparator(orden));
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return car;
		}
		return car;
	}
    
    
    public static synchronized String getRequestMultipleRateslocalidad(Reservation reservation,boolean haycontractid)
	{
		String request = "";
		String contractid="";
		try
		{
			if(haycontractid)
			{
				contractid=" contractID=\""+Atributos.CONTRACTID+"\" type=\"C\"";
			}
			request += Atributos.CABECERAXML;
			//Enviamos para recibir quote
			request += "<message>"
						+ "<serviceRequest serviceCode=\"getMultipleRates\">"
						+ Atributos.LANGUAGE_ES
							+ "<serviceParameters>"
								+ "<reservation  "+contractid+" carType=\""+carTypeG+"\" >"//antes B
									+ "<checkout stationID=\""+reservation.getCheckoutstationId()+"\" date=\""+reservation.getCheckoutdate()+"\" time=\""+reservation.getCheckouttime()+"\" language=\"ES\"/>"
									+ "<checkin stationID=\""+reservation.getCheckinstationId()+"\" date=\""+reservation.getCheckindate()+"\" time=\""+reservation.getCheckintime()+"\" language=\"ES\"/>"
								+ "</reservation>"
								//Por el momento, por defecto, el conductor será de España
								+ "<driver countryOfResidence=\"ES\" />"
							+ "</serviceParameters>"
						+ "</serviceRequest>"
					+ "</message>";
			request += "&callerCode=".concat(Atributos.USER).concat("&password=").concat(Atributos.PASS);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			return request;
		}
		return request;
	}
    
    public static synchronized Car getResponsePreciolocalidad(String texto,Reservation reservation)
    {
    	String responseStr = "";
    	Car car=new Car();
    	HttpsURLConnection con = null;
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
    		if (!responseStr.contains("reservationRateList"))
    		{
    			logger.error("No se ha podido obtener la información, error en el formato del xml.");
    		}
    		else
    		{
    			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    			DocumentBuilder db = dbf.newDocumentBuilder();
    			InputSource is = new InputSource();
    			is.setCharacterStream(new StringReader(responseStr));
    			Document doc = db.parse(is);
    			NodeList listcars = doc.getElementsByTagName("reservationRate");
    			car = recogePrecioVehiculolocalidad(listcars,reservation);
    		}
       }
       catch (Exception e)
       {
    	   if(con!=null)
    		   con.disconnect();
    	   logger.error(e.getMessage());
    	   return car;
       }
       return car;
    }
    
    public static synchronized Car recogePrecioVehiculolocalidad(NodeList listcars,Reservation reservation)
    {
    	//String precioprimero="";
    	Car car = new Car();
    	
    	ArrayList<Float> arrayListInt = new ArrayList<Float>();
    	try
    	{
    		for (int i = 0; i < listcars.getLength() ; i ++) 
			{
    			
				Element el = (Element) listcars.item(i);
				
				
				
				if(car.getCarCategoryCode()==null || car.getCarCategoryCode().equalsIgnoreCase("") 
						|| (car.getCarCategoryCode()!=null && !el.getAttribute("carCategoryCode").equalsIgnoreCase(car.getCarCategoryCode())))//el.getAttribute("isPrepaid").equalsIgnoreCase("Y") && )
				{
					
					car.setCarCategoryCode(el.getAttribute("carCategoryCode"));
					car.setCarType(carTypeG);
					
					String requestquotes = getRequestMultipleRates(reservation, car, true);        //getRequestQuotes(reservation,car,true);
					//String requestmultiplerates = getRequestMultipleRates(reservation, car);
					//Car car2 = new Car();
					//car2 = getResponseQuotes(car, reservation, requestmultiplerates );
					car = getResponseMultiplesRatesCar(car, reservation, requestquotes);//getResponseQuotes(car, reservation, requestquotes);
					if(car.isHaytarifas())
					{
						  String valor = el.getAttribute("totalRateEstimateInBookingCurrency");
						  
					      arrayListInt.add(Float.valueOf(valor));
					}
				}
				
				
			}
    		Collections.sort(arrayListInt);
    		car.getQuote().setTotalRateEstimateInBookingCurrency(arrayListInt.get(0).toString());
    		car.getQuote().setTotalRateEstimateInRentingCurrency(arrayListInt.get(0).toString());
    	}
    	catch (Exception e)
    	{
    		logger.error(e.getMessage());
    		return car;
    	}
    	return car;
    }
    
    
    
    /*******************************************************************************************************/
//*************************************Ordenar*************************************************/
//    public static Vector<Car> ordenaVehiculosSegun(int orden) throws Exception
//	{
//		String request = "";
//		haycontrato=true;		
//		vcars = new Vector<Car>();
//		try
//		{
//		    Collections.sort(vcars,new CarComparator(orden));
//		}
//		catch (Exception e)
//		{
//			return vcars;
//		}
//		return vcars;
//	}
}