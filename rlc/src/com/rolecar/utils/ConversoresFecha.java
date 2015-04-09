package com.rolecar.utils;

import java.sql.*;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
public class ConversoresFecha 
{
	public static final Log logger = LogFactory.getLog(ConversoresFecha.class);
	
	/**
	 * Devuelve la fecha actual en formato dd/mm/aaaa
	 * @return java.lang.String
	 */
	public static String dameFecha(Timestamp fechaSistema)
	{
		String fecha = "";
		 
		java.util.Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(fechaSistema);
	
		int dia=cal.get(Calendar.DAY_OF_MONTH);
		fecha = (dia<10?"0"+dia:""+dia);
		
		int mes = cal.get(Calendar.MONTH) + 1;
		fecha += "/" + (mes<10?"0"+mes:""+mes);
	
		fecha += "/" + cal.get(Calendar.YEAR);
	
		fecha += " " ;
		
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		fecha += (hora<10?"0"+hora:""+hora);
	
		int minutos = cal.get(Calendar.MINUTE);
		fecha += ":" + (minutos<10?"0"+minutos:""+minutos);
	
		return fecha;
	}
	
	/**
	 * Devuelve la fecha actual en formato dd/mm/aaaa
	 * @return java.lang.String
	 */
	public static String dameFechaOrdenada(Timestamp fechaSistema)
	{
		String fecha = "";
		java.util.Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(fechaSistema);
	
		fecha =  cal.get(Calendar.YEAR)+"";
		
		int mes = cal.get(Calendar.MONTH) + 1;
		fecha +=  (mes<10?"0"+mes:""+mes);
			
		int dia=cal.get(Calendar.DAY_OF_MONTH);
		fecha += (dia<10?"0"+dia:""+dia);
		
		return fecha;
	}
	
	public static Date dameFechaDatePuntos(String fecha)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.set(Integer.parseInt(fecha.substring(6,10)), Integer.parseInt(fecha.substring(3,5))-1, Integer.parseInt(fecha.substring(0,2)));
		Date dfecha = new Date(calendar.getTimeInMillis());
		return dfecha;
	}
	
	public static Date dameFechaDateGuiones(String fecha)
	{
		Date dfecha;
		int anio;
		int mes;
		int dia;
		
		StringTokenizer st = new StringTokenizer(fecha, "-", false);
		
		try 
		{
			logger.info("Formateamos");
			anio = Integer.parseInt(st.nextToken());
			logger.info("anio:"+anio);
			mes = Integer.parseInt(st.nextToken());
			logger.info("mes:"+mes);
			dia = Integer.parseInt(st.nextToken());
			logger.info("dia:"+dia);
			
			Calendar calendar = new GregorianCalendar();
			calendar.set(anio, mes-1, dia);
			dfecha = new Date(calendar.getTimeInMillis());
			logger.info("dfecha:"+dfecha);
			
		} 
		catch (Throwable t)
		{
			logger.error("ERROR al formatear fecha "+ fecha);
			dfecha = null;
		}
		return dfecha;
	}
	
	public static String convertirDD7MM7AAAAtoDDMMAA(String cadena_fecha)
	{
		String result = "";
		result = cadena_fecha.substring(0,2) + cadena_fecha.substring(3,5) + cadena_fecha.substring(8,10);
		return result;
	}
	
	public static Date getFechaSincro(String fecha)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.set(Integer.parseInt(fecha.substring(6,10)), Integer.parseInt(fecha.substring(3,5))-1, Integer.parseInt(fecha.substring(0,2)));
		Date dfecha = new Date(calendar.getTimeInMillis());
		return dfecha;
	}
	//Formateo fecha de yyyy-mm-dd a dd-mm-yyyy
	public static String formateaFecha(Date fecha)
	{
		String fechaS = "";
		try 
		{
			if (fecha!=null && !fecha.equals(""))
			{
				fechaS = String.valueOf(fecha);
				String[] arrFecha = fechaS.split("-");
				fechaS = arrFecha[2] + "-" + arrFecha[1] + "-" + arrFecha[0];
			}
			
		} 
		catch (Exception e) 
		{
			return fechaS;
		}
		return fechaS;
	}
	//Formateo fecha de dd-mm-yyyy a yyyy-mm-dd
	public static Date formateaFechaSQL(String fechaS)
	{
		Date fecha = null;
		String fechaux = "";
		try 
		{
			if (fechaS!=null && !fechaS.equals(""))
			{
				fechaux = String.valueOf(fechaS);
				String[] arrFecha = fechaux.split("-");
				fechaux = arrFecha[2] + "-" + arrFecha[1] + "-" + arrFecha[0];
				fecha = Date.valueOf(fechaux);
			}
		} 
		catch (Exception e) 
		{
			return fecha;
		}
		return fecha;
	}
	//Formateo fecha de yyyy-mm-dd a dd/mm/yyyy
	public static String formateaFechaBarras(Date fecha)
	{
		String fechaS = "";
		try 
		{
			if (fecha!=null && !fecha.equals(""))
			{
				fechaS = String.valueOf(fecha);
				String[] arrFecha = fechaS.split("-");
				fechaS = arrFecha[2] + "/" + arrFecha[1] + "/" + arrFecha[0];
			}
			
		} 
		catch (Exception e) 
		{
			return fechaS;
		}
		return fechaS;
	}
	
	//Formateo fecha de dd/mm/yyyy a yyyy-mm-dd
	public static Date formateaFechaBarrasSQL(String fechaS)
	{
		Date fecha = new Date(System.currentTimeMillis());
		try 
		{
			if (fecha!=null && !fecha.equals(""))
			{
				String[] arrFecha = fechaS.split("/");
				fechaS = arrFecha[2] + "-" + arrFecha[1] + "-" + arrFecha[0];
				fecha = Date.valueOf(fechaS);
			}
			
		} 
		catch (Exception e) 
		{
			return fecha;
		}
		return fecha;
	}
	//Formateo fecha de dd/mm/yyyy a yyyy-mm--dd
		public static String formateaFechaBarrasSQLSERVER(String fechaS)
		{
			//Date fecha = new Date(System.currentTimeMillis());
			String fecha="";
			try 
			{
				if (fechaS!=null && !fechaS.equals(""))
				{
					String[] arrFecha = fechaS.split("/");
					fecha = arrFecha[2] + "-" + arrFecha[1] + "-" + arrFecha[0];
					//fecha = Date.valueOf(fechaS);
				}
				
			} 
			catch (Exception e) 
			{
				return fecha;
			}
			return fecha;
		}
		
		//Formateo fecha de   yyyymmdd a dd/mm/yyyy
		public static String formateaFechaBarras(String fechaS)
		{
			//Date fecha = new Date(System.currentTimeMillis());
			String fecha="";
			try 
			{
				if (fechaS!=null && !fechaS.equals(""))
				{
					fecha=fechaS.substring(6, 8).concat("/").concat(fechaS.substring(4,6)).concat("/").concat(fechaS.substring(0, 4));
					//fecha = Date.valueOf(fechaS);
				}
				
			} 
			catch (Exception e) 
			{
				return fecha;
			}
			return fecha;
		}
		
		//pone dos puntos en numeros 1200  a 12:00
		public static String formateahora(String horaS)
		{
			//Date fecha = new Date(System.currentTimeMillis());
			String hora="";
			try 
			{
				if (horaS!=null && !horaS.equals(""))
				{
					hora=horaS.substring(0, 2).concat(":").concat(horaS.substring(2,4));
					//fecha = Date.valueOf(fechaS);
				}
				
			} 
			catch (Exception e) 
			{
				return hora;
			}
			return hora;
		}
		
		
	//Formateo hora y le quito los ceros de milisegundos
	public static String formateaZero(Time hora)
	{
		String horaS = "00:00";
		try 
		{
			if (hora!=null && !hora.equals(""))
			{
				horaS = hora.toString();
				String[] arrHora = horaS.split(":");
				horaS = arrHora[0] + ":" + arrHora[1];
			}
		} 
		catch (Exception e) 
		{
			return horaS;
		}
		return horaS;
	}
	
	 public static String FormateaFechaHoraIdioma(String language, String country,Timestamp today)
	 {
		 String dateOut = "";
		 try
		 {
			 language = new String(language);
			 country = new String(country);
			 Locale currentLocale;
			 currentLocale = new Locale(language, country);
			  
			 DateFormat dateFormatter;
			 dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
			 //today = new Date();
			 dateOut = dateFormatter.format(today);
		 }
		 catch (Exception e)
		 {
			 return dateOut;
		 }	
		 return dateOut;
	 }
	 /**
	  * Formateamos una fecha de tipo date.
	  * @param language
	  * @param country
	  * @param today
	  * @return
	  */
	 public static String FormateaFechaHoraIdiomaDate(String language, String country,Date today)
	 {
		 String dateOut = "";
		 try
		 {
			 language = new String(language);
			 country = new String(country);
			 Locale currentLocale;
			 currentLocale = new Locale(language, country);
			  
			 DateFormat dateFormatter;
			 dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
			 //today = new Date();
			 dateOut = dateFormatter.format(today);
		 }
		 catch (Exception e)
		 {
			 return dateOut;
		 }	
		 return dateOut;
	 }
	 //Formateo de fechas en timestamp
	 public static Timestamp FormateaFechaTs(String fechaS)
	 {
		 Timestamp fecha = null;
		 try
		 {
			if (fechaS!=null && !fechaS.equalsIgnoreCase("") && !fechaS.equalsIgnoreCase("null"))
			{
				fecha = Timestamp.valueOf(fechaS);
			}
		 }
		 catch (Exception e)
		 {
			 return fecha;
		 }
		 return fecha;
	 }
	
	 /**
	  * Diferencia del número de días entre dos fechas.
	  * @param fechaInicial
	  * @param fechaFinal
	  * @return número de días
	  */
	 public static synchronized int diferenciasDeFechas(Date fechaInicial, Date fechaFinal) {

	        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	        String fechaInicioString = df.format(fechaInicial);
	        try {
	            fechaInicial = (Date) df.parse(fechaInicioString);
	        } catch (Exception ex) {
	        }

	        String fechaFinalString = df.format(fechaFinal);
	        try {
	            fechaFinal = (Date) df.parse(fechaFinalString);
	        } catch (Exception ex) {
	        }

	        long fechaInicialMs = fechaInicial.getTime();
	        long fechaFinalMs = fechaFinal.getTime();
	        long diferencia = fechaFinalMs - fechaInicialMs;
	        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
	        return ((int) dias);
	    }
	 
	 
	 
}