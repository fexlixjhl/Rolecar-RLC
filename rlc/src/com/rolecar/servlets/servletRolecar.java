package com.rolecar.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.Locale;
import java.util.Vector;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rolecar.beans.*;
import com.rolecar.data.connection.Configuracion;
import com.rolecar.data.connection.conexionBBDD;
import com.rolecar.data.constantes.Atributos;
import com.rolecar.data.dao.*;
import com.rolecar.utils.CarComparator;
//import com.sun.imageio.plugins.common.I18N;
//import com.sun.imageio.plugins.common.I18NImpl;


/**
 * Servlet implementation class Rolecar
 */
@WebServlet("/rlc")
public class servletRolecar extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(getClass());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletRolecar()
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{   
		String accion = request.getParameter("accion");
		if (accion != null)
		{
			if (accion.equals("reservar"))
			{
				//Locale oLocale = null;
				String lang = null;
				lang = readCookie(response, request, "idioma");
//				System.out.println("XXX:: " + lang);
				if (request.getParameter("idioma") != null ){
					lang =request.getParameter("idioma");
				}
				
				if (!StringUtils.isEmpty(lang)){
					gestionarCookie(lang, response, request,"add");
					//oLocale = new Locale(request.getParameter("idioma"));
					//response.setLocale(oLocale);
					//Locale.setDefault(oLocale);
					request.setAttribute("idioma", lang);
				}
				else{
					gestionarCookie(null, response, request,"ini");	
				}
				//System.out.println("RESERVAR:: " + request.getLocale() + "::" +  request.getParameter("idioma"));
								
				processRequestReservar(request, response);
			}
			else if(accion.equals("buscar"))
			{
				processRequestBuscar(request,response);
			}
			else if(accion.equals("contratar"))
			{
				processRequestContratar(request,response);
			}
			else if(accion.equals("ordenar"))
			{
				//processRequestOrdenar(request,response);
				processRequestOrdenarpor(request,response);
			}
			else if(accion.equals("cerrarSesion"))
			{
				//processRequestOrdenar(request,response);
				processRequestCerrarSesion(request,response);
			}
		}
	}
	/**
	 * Gestiona cookies
	 * @param pIdioma
	 * @param pResponse
	 * @param pRequest
	 * @param pMode
	 */
	private void gestionarCookie(String pIdioma, HttpServletResponse pResponse, HttpServletRequest pRequest,String pMode) {
		boolean lFound = false;
		if (pMode == "add"){
			Cookie cookie = new Cookie("idioma", pIdioma);
			cookie.setMaxAge(24 * 60 * 60);  // 24 hours. 
			//cookie.setMaxAge(0);  // 24 hours. 
			pResponse.addCookie(cookie);
		}else if (pMode == "ini"){
			Cookie[] cookies = pRequest.getCookies();
			if (cookies != null){
				for (Cookie ck : cookies) {
				    if ("idioma".equals(ck.getName())) {
				      ck.setValue(pIdioma);
				      //String idioma = ck.getValue();
				      lFound = true;
				    }
				  }
			}
			if (!lFound){
				gestionarCookie("es", pResponse, pRequest, "add");
			}
		}
		
	}
	private String readCookie(HttpServletResponse pResponse, HttpServletRequest pRequest, String pCookieName){
		Cookie[] cookies = pRequest.getCookies();
		String lang = null;
		if (cookies != null){
			for (Cookie ck : cookies) {
			    if (pCookieName.equals(ck.getName())) {
			      lang = ck.getValue();
			    }
			  }
		}
		return lang;
	}
	private void processRequestReservar (HttpServletRequest request,HttpServletResponse response ) throws ServletException, IOException
	{
		HttpSession sesion; 
		sesion=request.getSession(false);
		Hashtable<String,Country> htcountries= new Hashtable<String,Country>();
		Hashtable<String,City> htcities = new Hashtable<String,City>();
		Vector<Station> vstat = new Vector<Station>();
		Vector<City> vcty = new Vector<City>();
		String mensaje ="";
		try
		{
			if(request.getAttribute("mens")!=null)
			{
				mensaje = (String)request.getAttribute("mens"); 
				request.setAttribute("mensa", mensaje);
			}
			//Obtenemos todos los paises
			htcountries=JdbcCountriesDao.getAllCountries();
			//Obtenemos el vector de todas las ciudades
			vcty=JdbcCitiesDao.getAllCities(htcountries);
			//Obtenemos la Hastable de ciudades
			htcities=JdbcCitiesDao.getHTCities(vcty);
			//Obtenemos el vector de Stations pasandole la hashtable de ciudades
			vstat=JdbcStationsDao.getAllStation(htcities);
			if(vstat!=null && vstat.size()>0)
				sesion.setAttribute("vStations", vstat);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			RequestDispatcher dispacher=request.getRequestDispatcher("busqueda.jsp");
			dispacher.forward(request, response);
			return;
		}
		RequestDispatcher dispacher=request.getRequestDispatcher("busqueda.jsp");
		dispacher.forward(request, response);
	}
	
	private void processRequestBuscar (HttpServletRequest request,HttpServletResponse response ) throws ServletException, IOException
	{
		Reservation res= new Reservation();
		
		String carType, tsucursal, hor_ini, min_ini, hor_fin, min_fin, checkindate,checkoutdate,checkintime,checkouttime;
		String  FIni, FFin;
		String codStationOut, codStationIn;
		Country pais= new Country();
		String CodCountry="";
		String provincia="";
		checkindate="";
		checkoutdate="";
		Vector<Car> vcar= new Vector<Car>();
		RequestDispatcher dispacher;
		String[] arraycodestacion;
		
		try
		{
            logger.info("Inicio de la busqueda..");
			Connection connec= conexionBBDD.getConnectionWeb();
			//Obtener valores del formulario de busqueda			
			carType=request.getParameter("tvhiculo");
			tsucursal=request.getParameter("tsucursal");
			if(tsucursal.equalsIgnoreCase("tsucursal1"))//Misma sucursal de entrega y devolución
			{
				logger.info("Sucursal..");
				codStationOut=request.getParameter("station");
				request.setAttribute("oficina",codStationOut);
//				caracterinicial=codStationOut.indexOf(",")+1;
//				caracterfinal=codStationOut.lastIndexOf(";");
//				descrCountry=codStationOut.substring(caracterinicial , caracterfinal);
				codStationOut=request.getParameter("stationid");
				request.setAttribute("stationorigid",codStationOut);
				arraycodestacion=codStationOut.split("::");
				codStationOut=arraycodestacion[0];
				provincia=arraycodestacion[1];
				CodCountry=arraycodestacion[2];
				request.setAttribute("paisrecogida", CodCountry);
				request.setAttribute("paisentrega", CodCountry);
				request.setAttribute("provincia",provincia);
				//codStationOut=codStationOut.substring(codStationOut.indexOf("(")+1 , codStationOut.indexOf(")"));
				codStationIn=codStationOut;
				request.setAttribute("sucursal",true);
				logger.info("Sucursal.."+codStationOut);
			}
			else
			{
				//La sintaxis In/Out es la siguiente:
				//Out es donde Europcar (Rolecar) Entrega el vehículo al cliente.
				//In es donde el cliente devuelve el vehículo.
				codStationOut=request.getParameter("stationOrig");
				request.setAttribute("oficinaorigen", codStationOut);
//				caracterinicial=codStationOut.indexOf(",")+1;
//				caracterfinal=codStationOut.lastIndexOf(";");
//				descrCountry=codStationOut.substring(caracterinicial , caracterfinal);
				codStationOut=request.getParameter("stationOrigid");
				request.setAttribute("stationorigid", codStationOut);
				arraycodestacion=codStationOut.split("::");
				codStationOut=arraycodestacion[0];
				provincia=arraycodestacion[1];
				CodCountry=arraycodestacion[2];
				request.setAttribute("paisrecogida",CodCountry);
				request.setAttribute("provincia",provincia);
				//codStationOut=codStationOut.substring(codStationOut.indexOf("(")+1 , codStationOut.indexOf(")"));
				codStationIn=request.getParameter("stationDest");
				request.setAttribute("oficinadestino",codStationIn);
				codStationIn=request.getParameter("stationDestid");
				request.setAttribute("stationdestid", codStationIn);
				arraycodestacion=codStationIn.split("::");
				codStationIn=arraycodestacion[0];
//				provincia=arraycodestacion[1];
//				request.setAttribute("provincia",provincia);
				request.setAttribute("paisentrega", arraycodestacion[2]);
				request.setAttribute("sucursal",false);
				//codStationIn=codStationIn.substring(codStationIn.indexOf("(")+1 , codStationIn.indexOf(")"));
			}
			
			pais=JdbcCountriesDao.getCountry("where codcountry='".concat(CodCountry.trim()).concat("'"),connec);
			//System.out.println("BBPais: " + pais.getCodcountry());
			if(pais.getCodcountry()!=null)
			{
				request.setAttribute("Country", pais);
			}
			//Hora de Entrega del vehículo al cliente
			hor_ini=request.getParameter("hini");
			if(hor_ini.length()<2)
				hor_ini="0".concat(hor_ini);
			min_ini=request.getParameter("mini");
			if(min_ini.length()<2)
				min_ini="0".concat(min_ini);
			
			checkouttime=hor_ini.concat(min_ini);		
			//Hora de devolución del vehículo.
			hor_fin=request.getParameter("hfin");
			if(hor_fin.length()<2)
				hor_fin="0".concat(hor_fin);
			min_fin=request.getParameter("mfin");
			if(min_fin.length()<2)
				min_fin="0".concat(min_fin);
			
			checkintime=hor_fin.concat(min_fin);
			
			//Fecha de Entrea del vehículo al cliente
			FIni=request.getParameter("fini");
			String[] fechaIni=FIni.split("/");
			checkoutdate=checkoutdate.concat(fechaIni[2]).concat(fechaIni[1]).concat(fechaIni[0]);		
			//Fecha de devolución del vehículo.
			FFin=request.getParameter("ffin");
			String[] fechaFin=FFin.split("/");
			checkindate=checkindate.concat(fechaFin[2]).concat(fechaFin[1]).concat(fechaFin[0]);	
			
			//Obtener vector coches según parámetros, con precio ascendente.
			vcar=JdbcCarsDao.recogeVehiculos(codStationIn, checkindate, checkintime, codStationOut, checkoutdate, checkouttime, carType,1);
			res.setCheckinstationId(codStationIn);
			res.setCheckindate(checkindate);
			res.setCheckintime(checkintime);
			res.setCheckoutstationId(codStationOut);
			res.setCheckoutdate(checkoutdate);
			res.setCheckouttime(checkouttime);
			request.setAttribute("Reservation", res);
			request.setAttribute("CarType", carType);
			logger.info("listado de coches..:"+vcar.size());
			if (vcar!=null && vcar.size()>0) {
				request.setAttribute("Vehiculos", vcar);
//				res.setCheckinstationId(codStationIn);
//				res.setCheckindate(checkindate);
//				res.setCheckintime(checkintime);
//				res.setCheckoutstationId(codStationOut);
//				res.setCheckoutdate(checkoutdate);
//				res.setCheckouttime(checkouttime);
//				request.setAttribute("Reservation", res);
				
				request.setAttribute("haycoches",true);
				
			}
			else
			{
				request.setAttribute("mensaje", Atributos.MENSAJENOCOCHES);
				request.setAttribute("haycoches",false);
								
			}
			dispacher=request.getRequestDispatcher("vehiculoslist.jsp");
			dispacher.forward(request, response);
			
			
		}
		catch(Exception e)
		{
			//Si dá algun error indicarlo en un request Attribute
			//System.out.println(e.getMessage());
			String mensaje = e.getMessage();
			logger.error("Error:"+e.getMessage());
			if(mensaje.equalsIgnoreCase("String index out of range: -1") || mensaje.equalsIgnoreCase("1"))
			{
				request.setAttribute("mensaje", "Localidad erronea, introduzca un elemento del listado");
			}
			dispacher=request.getRequestDispatcher("principalinicial.jsp");
			request.setAttribute("Error", "Se ha producido un error en la busqueda");
			dispacher.forward(request, response);
			return;
		}
		
//		dispacher=request.getRequestDispatcher("vehiculoslist.jsp");
//		dispacher.forward(request, response);
	}
	
	private void processRequestContratar (HttpServletRequest request,HttpServletResponse response ) throws ServletException, IOException
	{
		String lang = readCookie(response, request, "idioma");
		String url = null;
		if (StringUtils.isEmpty(lang)){
			url = Configuracion.getInstance().getProperty("url_europcar");
		}
		else{
			url = Configuracion.getInstance().getProperty("url_europcar_"+lang);
		}
		//String ruta="http://www.europcar.es/DotcarClient/step2Direct.action?";
		//Redirect in order to lenguage
		String ruta="http://"+url+"/DotcarClient/step2Direct.action?";
		try
		{
			if(request.getParameter("CNTRY")!=null && ! request.getParameter("CNTRY").equals(""))
			{
				ruta=ruta.concat("countryOfResidence=").concat(request.getParameter("CNTRY"));
			}
			else 
			{
				ruta=ruta.concat("countryOfResidence=");
			}
			
//			if(request.getParameter("dateci")!=null && !request.getParameter("dateci").equals(""))
//			{
//				ruta=ruta.concat("&DATECI=").concat(request.getParameter("dateci"));
//			}
//			else
//			{
//				ruta=ruta.concat("&DATECI=");
//			}
//			if(request.getParameter("dateco")!=null && !request.getParameter("dateco").equals(""))
//			{
//				ruta=ruta.concat("&DATECO=").concat(request.getParameter("dateco"));
//			}
//			else
//			{
//				ruta=ruta.concat("&DATECO=");
//			}
			
			
			if(request.getParameter("stationco")!=null && !request.getParameter("stationco").equals(""))
			{
				ruta=ruta.concat("&checkoutLocation=").concat(request.getParameter("stationco"));
			}
			else
			{
				ruta=ruta.concat("&checkoutLocation=");
			}
			if(request.getParameter("stationci")!=null && !request.getParameter("stationci").equals(""))
			{
				ruta=ruta.concat("&checkinLocation=").concat(request.getParameter("stationci"));
			}
			else
			{
				ruta=ruta.concat("&checkinLocation: =");
			}
			//ruta=ruta.concat("&checkoutCountry=ES");
			
			if(request.getParameter("PAISRECOGIDA")!=null && ! request.getParameter("PAISRECOGIDA").equals(""))
			{
				ruta=ruta.concat("&checkoutCountry=").concat(request.getParameter("PAISRECOGIDA"));
			}
			else 
			{
				ruta=ruta.concat("&checkoutCountry=");
			}
			
			//*****************************************Entrega del coche al cliente**********************************
			if(request.getParameter("dateco")!=null && !request.getParameter("dateco").equals(""))
			{
				String fecha = request.getParameter("dateco");
			    String anio = fecha.substring(0,4);
			    String mes = fecha.substring(4,6);
			    String dia = fecha.substring(6,8);
			
				ruta=ruta.concat("&checkoutYear=").concat(anio);
				ruta=ruta.concat("&checkoutMonth=").concat(mes);
				ruta=ruta.concat("&checkoutDay=").concat(dia);
			}
			else
			{
				ruta=ruta.concat("&checkoutYear=");
				ruta=ruta.concat("&checkoutMonth=");
				ruta=ruta.concat("&checkoutDay=");
			}
			
			if(request.getParameter("timeco")!=null && !request.getParameter("timeco").equals(""))
			{
				String horas = request.getParameter("timeco");
			    String hora = horas.substring(0, 2);
			    String minuto = horas.substring(2,4);
			    
			
				ruta=ruta.concat("&checkoutHour=").concat(hora);
				ruta=ruta.concat("&checkoutMinute=").concat(minuto);
				
			}
			else
			{
				ruta=ruta.concat("&checkoutHour=");
				ruta=ruta.concat("&checkoutMinute=");
				
			}
			
			//****************************************Devolución del coche por el cliente********************************
			if(request.getParameter("PAISENTREGA")!=null && ! request.getParameter("PAISENTREGA").equals(""))
			{
				ruta=ruta.concat("&checkinCountry=").concat(request.getParameter("PAISENTREGA"));
			}
			else 
			{
				ruta=ruta.concat("&checkinCountry=");
			}
			
			
			if(request.getParameter("dateci")!=null && !request.getParameter("dateci").equals(""))
			{
				String fecha = request.getParameter("dateci");
			    String anio = fecha.substring(0,4);
			    String mes = fecha.substring(4,6);
			    String dia = fecha.substring(6,8);
			
				ruta=ruta.concat("&checkinYear=").concat(anio);
				ruta=ruta.concat("&checkinMonth=").concat(mes);
				ruta=ruta.concat("&checkinDay=").concat(dia);
			}
			else
			{
				ruta=ruta.concat("&checkinYear=");
				ruta=ruta.concat("&checkinMonth=");
				ruta=ruta.concat("&checkinDay=");
			}
			
			if(request.getParameter("timeci")!=null && !request.getParameter("timeci").equals(""))
			{
				String horas = request.getParameter("timeci");
			    String hora = horas.substring(0,2);
			    String minuto = horas.substring(2,4);
			    
			
				ruta=ruta.concat("&checkinHour=").concat(hora);
				ruta=ruta.concat("&checkinMinute=").concat(minuto);
				
			}
			else
			{
				ruta=ruta.concat("&checkinHour=");
				ruta=ruta.concat("&checkinMinute=");
				
			}
			
			
			if(request.getParameter("ACRISS")!=null && ! request.getParameter("ACRISS").equals(""))
			{
				ruta=ruta.concat("&selectedAcrissCode=").concat(request.getParameter("ACRISS"));
			}
			else 
			{
				ruta=ruta.concat("&selectedAcrissCode=");
			}
			
			
			if(request.getParameter("CTRCT")!=null && ! request.getParameter("CTRCT").equals(""))
			{
				ruta=ruta.concat("&promoCode=").concat(request.getParameter("CTRCT"));
			}
			else 
			{
				ruta=ruta.concat("&promoCode=");
			}
			
			ruta = ruta.concat("&selectedExtra=");
			ruta = ruta.concat("&sortBy=");
			
			if(request.getParameter("reservac")!=null && ! request.getParameter("reservac").equals(""))
			{
				ruta=ruta.concat("&isPrepaid=").concat(request.getParameter("reservac"));
			}
			else 
			{
				ruta=ruta.concat("&isPrepaid=");
			}
			
			
			
			if(request.getParameter("IATA")!=null && ! request.getParameter("IATA").equals(""))
			{
				ruta=ruta.concat("&Iata=").concat(request.getParameter("IATA"));
			}
			else 
			{
				ruta=ruta.concat("&Iata=");
			}
			

			request.setAttribute("ruta", ruta);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			RequestDispatcher dispacher=request.getRequestDispatcher("principalinicial.jsp");
			dispacher.forward(request, response);
			return;
		}
		RequestDispatcher dispacher=request.getRequestDispatcher("reserva.jsp");
		dispacher.forward(request, response);
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void processRequestOrdenarpor (HttpServletRequest request,HttpServletResponse response ) throws ServletException, IOException
	{
		
		Reservation res= new Reservation();
		String carType,  checkindate,checkoutdate,checkintime,checkouttime,origen,destino,provincia,stationorigid,stationdestid;
		String codStationOut, codStationIn;
		boolean mismasucursal=true;
		int orden;
		Vector<Car> vcars= new Vector<Car>();
		Country pais= null;
		RequestDispatcher dispacher;
		
		
		try
		{
			//Obtener request.
			codStationIn=request.getParameter("codStationIn");
			checkindate=request.getParameter("checkindate");
			checkintime=request.getParameter("checkintime");
			codStationOut=request.getParameter("codStationOut");
			checkoutdate=request.getParameter("checkoutdate");
			checkouttime=request.getParameter("checkouttime");
			origen=request.getParameter("origen");
			destino=request.getParameter("destino");
			carType=request.getParameter("carType");
			orden=Integer.parseInt(request.getParameter("Orden"));
			mismasucursal=Boolean.parseBoolean(request.getParameter("mismaoficina"));
			provincia = request.getParameter("provincia");
			request.setAttribute("sucursal",mismasucursal);
			stationorigid=request.getParameter("stationorigid");
			request.setAttribute("stationorigid",stationorigid);
			if(!mismasucursal)
			{
				stationdestid=request.getParameter("stationdestid");
				request.setAttribute("stationdestid", stationdestid);
			}
			
			
			if(request.getParameter("Country")!=null)
			{
				Connection connec= conexionBBDD.getConnectionWeb();
				pais= new Country();
				pais=JdbcCountriesDao.getCountry(" where codcountry='".concat(request.getParameter("Country").concat("'")), connec);
				//System.out.println("Pais: " + pais);
			}
			//System.out.println("Pais: " + pais);
			if(pais!=null)
			{
				request.setAttribute("Country", pais);
			}
			if(origen.equalsIgnoreCase(destino) || (!origen.equalsIgnoreCase("") && destino.equalsIgnoreCase("")))
			{
				request.setAttribute("oficina", origen);
			}
			else
			{
				request.setAttribute("oficinaorigen", origen);
				request.setAttribute("oficinadestino", destino);
			}
			//Obtener vector coches según parámetros, con precio ascendente.
			vcars=(Vector<Car>)request.getSession(false).getAttribute("Vehiculossesion");
			Collections.sort(vcars,new CarComparator(orden));
			request.setAttribute("Vehiculos", vcars);	
			res.setCheckinstationId(codStationIn);
			res.setCheckindate(checkindate);
			res.setCheckintime(checkintime);
			res.setCheckoutstationId(codStationOut);
			res.setCheckoutdate(checkoutdate);
			res.setCheckouttime(checkouttime);  
			request.setAttribute("provincia",provincia);
			request.setAttribute("Reservation",res);
			request.setAttribute("CarType",carType);
			request.setAttribute("haycoches",true);
		}
		catch(Exception e)
		{
			//Si dá algun error indicarlo en un request Attribute
			//System.out.println(e.getMessage());
			//dispacher=request.getRequestDispatcher("index.jsp");
			request.setAttribute("Error", "Se ha producido un error en la busqueda");
			//dispacher.forward(request, response);
			return;
		}
		
		dispacher=request.getRequestDispatcher("vehiculoslist.jsp");
		dispacher.forward(request, response);
	}
	
	private void processRequestCerrarSesion(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		long memoriaantes=0;
		long memoriadespues=0;
		try 
		{
			HttpSession session=request.getSession(false);
			String url = "";
			if (session!=null)
			{
				memoriaantes=Runtime.getRuntime().freeMemory();
				
				session.invalidate();
				Thread.currentThread().interrupt();
				Runtime.getRuntime().gc();
				
				memoriadespues=Runtime.getRuntime().freeMemory();
				
			}
			logger.info("Memoria libre de la JVM Antes de cerrar sesion:"+memoriaantes);
			logger.info("Memoria libre de la JVM Despues de cerrar la sesion:"+memoriadespues);
			
		} 
		catch (Exception e) 
		{
			
			logger.error("Error en el cierre de sesion:"+e.getMessage());
		}
	}
	
	
}
