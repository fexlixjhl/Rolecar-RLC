package com.rolecar.utils;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rolecar.beans.Car;



public class Formatea
{
	public static Log logger = LogFactory.getLog(Formatea.class);
	
	/**
	 * Función que elimina acentos y caracteres especiales de
	 * una cadena de texto.
	 * @param input
	 * @return cadena de texto limpia de acentos y caracteres especiales.
	 */
	public static String removeCarEsp(String input)
	{
	    String output="";
		try
	    {
	    	// Cadena de caracteres original a sustituir.
		    //String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜçÇ\\/\"";
		    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜçÇ\\/\"";
		    // Cadena de caracteres ASCII que reemplazarán los originales.
		    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUcC--'";
		    output = input;
		    for (int i=0; i<original.length(); i++)
		    {
		        // Reemplazamos los caracteres especiales.
		        output = output.replace(original.charAt(i), ascii.charAt(i));
		    }
		    output = getPrimerParrafo(output);
		    	
		}
	    catch (Exception e)
		{
	    	return output;
		}
	    return output;
		
	}//removeCarEsp
	/**
	 * Función que formatea un numero pasado como String, con n decimales y lo saca con dos decimales
	 * (#.##) tiene en cuenta el lenguaje y el país para utilizar los signos de puntuación.(, ó .)
	 * @param language es, en, fr, etc.. 
	 * @param country ES, GB,FR, etc...
	 * @param valor en String
	 * @return devuelve resultado String, si valor es null o vacio ("") devuelve vacio.
	 */
	public static String dosDecimalesIdiomas(String valor, int decimales, String language, String country)
	{
	       String respuesta="";
	       if(valor!=null && !valor.equals(""))
	       {
		       Float number = Float.parseFloat(valor);
		       NumberFormat nf = NumberFormat.getInstance(new Locale(language,country));
		       nf.setMinimumFractionDigits(decimales);
		       nf.setMaximumFractionDigits(decimales);
		       respuesta = nf.format(number);
	       }
	       return respuesta;
	}

	
	
	/**
	 * Formatea un String para devolverlo con 2 decimales dependiendo del lenguage,
	 * si es EN o en busca el punto como separador de decimales, si no busca la coma
	 * @param valor
	 * @return String formateado con 2 decimales.
	 */
	public static String Importe2Decimales(String valor, String language)
	{	
		String valorS = "";
		int posicion = 0;
		try 
		{
			valorS = String.valueOf(valor);
			if(language.equalsIgnoreCase("en"))
				posicion = valorS.lastIndexOf(".");
			else
				posicion = valorS.lastIndexOf(",");
			
			if (posicion!=0)
			{
				String entero=valorS.substring(0,posicion);
				String decimales=valorS.substring(posicion);
				if(decimales.length()==2)
					decimales=decimales.concat("0");
				valorS=entero.concat(decimales);
			}	
		} 
		catch (Exception e) 
		{
			return valorS;
		}
		return valorS;
	}
	
	/**
	 * Función que formatea un numero pasado como String, con n decimales 
	 * teniendo en cuenta el lenguaje y el país para utilizar los signos de puntuación.(, ó .)
	 * @param language es, en, fr, etc..
	 * @param country ES, GB,FR, etc...
	 * @param valor en String
	 * @return devuelve resultado String, si valor es null o vacio ("") devuelve vacio.
	 */
	public static String numeroIdioma(String language, String country,String valor)
	{
		String resultado="";
		if(valor!=null && !valor.equals(""))
		{
			float fval=Float.parseFloat(valor);
			resultado = NumberFormat.getInstance(new Locale(language, country)).format(fval);
		}
		return resultado;
	}
	/**
	 * Función que formatea un numero pasado como String, con n decimales, y se los quita 
	 * teniendo en cuenta el lenguaje y el país para utilizar los signos de puntuación.(, ó .)
	 * @param language es, en, fr, etc..
	 * @param country ES, GB,FR, etc...
	 * @param valor en String
	 * @return devuelve resultado String, si valor es null o vacio ("") devuelve vacio.
	 */
	public static String nSinDecimalesIdioma(String language, String country,String valor)
	{
		String resultado="";
		if(valor!=null && !valor.equals(""))
		{
			try
			{
				valor=valor.substring(0, valor.indexOf("."));
				int val=Integer.parseInt(valor);
				resultado = NumberFormat.getInstance(new Locale(language, country)).format(val);
			}
			catch(NumberFormatException e){logger.error(e.getMessage());return resultado;}
			catch(Exception e){logger.error(e.getMessage());return resultado;}
		}
		return resultado;
	}
	
	public static String signoMas(String valor)
	{
		String resultado="";
		if(valor!=null && !valor.equals(""))
		{
			try
			{
				//double val=Double.parseDouble(valor);
				if(!valor.startsWith("-"))
					resultado="+"+valor;
				else
					resultado=valor;
			}
			catch(NumberFormatException e){logger.error(e.getMessage());return resultado;}
			catch(Exception e){logger.error(e.getMessage());return resultado;}
		
		}
		return resultado;
	}
	
	public static String getPrimerParrafo(String parrafo)
	{
		if (parrafo.indexOf("\n")!=-1)
			parrafo = parrafo.substring(0,parrafo.indexOf("\n"));
		if (parrafo.indexOf("\r")!=-1)
			parrafo = parrafo.substring(0,parrafo.indexOf("\r"));
		return parrafo;
	}
	
	/**
	 * CBC Función que comprueba si el parámetro es un número o no
	 * incluyendo decimales
	 * @param num
	 * @return
	 */
	
	public static boolean comprobarNumero(String num)
	{
		
		@SuppressWarnings("unused")
		int comp;
		
		try
		{
			num = num.replace(",","");
			num = num.replace(".","");
			num = num.replace("+","");
			num = num.replace("-","");
			if (num!=null && !num.equalsIgnoreCase(""))
				comp=Integer.parseInt(num);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * CBC Función para extrar enteros de una cadena
	 * @param c
	 * @return
	 */
	public static int extraeNumeros(String s)
	{
		int clave = 0;
		String claveS = "";
		try 
		{
			for (int i=0;i<s.length();i++)
			{
				char c = s.charAt(i);
				String aux = String.valueOf(c);
				//Compruebo si es entero, si es así lo concateno
				if (comprobarEntero(aux))
				{
					claveS = claveS.concat(aux);
				}
			}
			clave = Integer.parseInt(claveS);
			
		} 
		catch (Exception e) 
		{
			return clave;
		}
		return clave;
	}
	
	/**
	 * Función para comprobar si el número es un entero
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean comprobarEntero(String c)
	{
		try 
		{
			int numentero = Integer.parseInt(c);
		} 
		catch (Exception e) 
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Formatea un String para devolverlo con n decimales sin depender del language
	 * @param valor
	 * @return String formateado con n decimales.
	 */
	public static String Importe2Decimales(String valor, int ndec)
	{	
		int posicion = 0;
		boolean haycoma = false;
		String entero = "";
		String decimales= "";
		ndec = ndec ;
		String numformat = "";
		try 
		{
			if (valor.contains(","))
				haycoma = true;
			valor = valor.replace(",",".");
			posicion = valor.lastIndexOf(".");
			
			if (posicion!=-1)
			{
				entero=valor.substring(0,posicion);
				decimales=valor.substring(posicion+1);
				if (decimales.length()>ndec)
				{
					while (decimales.length()>ndec)
					{
						decimales = decimales.substring(0,decimales.length()-1);
					}
				}
				else
				{
					while (decimales.length()<ndec)
					{
						decimales = decimales.concat("0");
					}
				}
				
			}
			else
			{
				entero = valor;
				if (decimales.length()>ndec)
				{
					while (decimales.length()>ndec)
					{
						decimales = decimales.substring(0,decimales.length()-1);
					}
				}
				else
				{
					while(decimales.length()<ndec)
					{
						decimales = decimales.concat("0");
					}
				}
				
			}
			if (haycoma)
				numformat = entero.concat(",").concat(decimales);
			else
				numformat = entero.concat(".").concat(decimales);
		} 
		catch (Exception e)
		{
			return numformat;
		}
		return numformat;
	}
	
	public static String rellenaEspacios(String input, int numposiciones)
	{

	    String output = input;
	   // numposiciones = numposiciones;
		try
	    {
			if (input.equalsIgnoreCase(null) || input.equalsIgnoreCase("null"))
			{
				input = "";
			}
			StringBuffer sbo = new StringBuffer(input);
		    if (input.length()>numposiciones)
		    	sbo = new StringBuffer(input.substring(0,numposiciones));
		    else
		    {
		    	for (int i=sbo.length(); i<numposiciones; i++)
			    {
		    		sbo = sbo.append(" ");
			    }
		    } 
		    output = sbo.toString();
		}
	    catch (Exception e)
		{
	    	return output;
		}
	    return output;
		
	}//removeCarEsp

	public static String rellenaCerosIzq(String input, int numposiciones)
	{
	    StringBuffer sbo = new StringBuffer(input);
	    String output = input;
		try
	    {
		    if (input.length()>numposiciones)
		    	sbo = new StringBuffer(input.substring(0,numposiciones));
		    else
		    {
		    	for (int i=sbo.length(); i<numposiciones; i++)
			    {
		    		sbo = new StringBuffer("0".concat(sbo.toString()));
			    }
		    } 
		    output = sbo.toString();
		}
	    catch (Exception e)
		{
	    	return output;
		}
	    return output;
		
	}
	
	
	 public static boolean isNumeric(String cadena){
	    	try 
	    	{
	    		Float.parseFloat(cadena);
	    		return true;
	    	} catch (Exception nfe){
	    		return false;
	    	}
	    }
	 
	 public static boolean isNumericEntero(String cadena){
	    	try 
	    	{
	    		Integer.parseInt(cadena);
	    		return true;
	    	} catch (Exception nfe){
	    		return false;
	    	}
	    }
	
	 /**
	  * Comprueba que dos cadenas son numericas y cual es la mayor
	  * @param cadena1
	  * @param cadena2
	  * @return
	  */
	 public static  int comparar(String cadena1,String cadena2)
	 {
		 float numero1=0f;
		 float numero2=0f;
		 int num=-2;
		 if(isNumeric(cadena1) && isNumeric(cadena2))
		 {
			 numero1 = Float.parseFloat(cadena1);
			 numero2 = Float.parseFloat(cadena2);
			 if(numero1-numero2<0)
			 {
				 num=-1;
			 }
			 else if (numero1-numero2==0)
			     num=0;
			 else
				 num=1;
		 }
		 return num;
	 }
	 
	 /**
	  * Comprueba cual es precio menor de los dos precios(online u oficina) que tiene un coche 
	  * @param c1
	  * @return
	  */
	 public static float preciomasbajo(Car c1)
	 {
		 float precionline = 0f;
		 float precioficina = 0f;
		 float preciobajo = 0f;
		 
		 try {
			if(c1.getQuote().getTotalRateEstimateInBookingCurrency()!=null && 
					  isNumeric(c1.getQuote().getTotalRateEstimateInBookingCurrency()))
			 {
				   precionline=Float.parseFloat(c1.getQuote().getTotalRateEstimateInBookingCurrency());
			 }
			 
			 if(c1.getQuote().getTotalRateEstimateInRentingCurrency()!=null && 
					  isNumeric(c1.getQuote().getTotalRateEstimateInRentingCurrency()))
			 {
				   precioficina=Float.parseFloat(c1.getQuote().getTotalRateEstimateInRentingCurrency());
			 }
			
			 
			 if(precionline>0 && precioficina>0)
			 {	 
				   preciobajo = (precionline>=precioficina)?precioficina:precionline;
			 }
			 else if(precionline>0 && precioficina==0)
			 {
				 preciobajo=precionline;
			 }
			 else if(precionline==0 && precioficina>0)
			 {
				 preciobajo=precioficina;
			 }
			 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
			return -1f;
		}
		 
		 return preciobajo;
		     
	 }
	
}