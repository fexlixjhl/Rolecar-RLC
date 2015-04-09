package com.rolecar.data.connection;


import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


public class conexionBBDD
{	
//    private static final String url = Configuracion.getInstance().getProperty("url");
//	private static final String dbUsername = Configuracion.getInstance().getProperty("dbUsername");
//	private static final String dbPassword = Configuracion.getInstance().getProperty("dbPassword");
//	private static final Properties propiedades = Configuracion.getInstance().getProperties();
	public static Logger informe = Logger.getLogger(conexionBBDD.class.getName());
	
//  Antigua conexión a traves de fichero properties	
//	public static Connection getConnection() 
//	{
//	    try
//	    {
//	    	informe.info("Conexión en producción");
//	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
//	    	Connection conn = DriverManager.getConnection(url,dbUsername,dbPassword);
//	    	informe.info("Después de conexión en producción");
//	    	return conn;
//		}
//	    catch (Exception e)
//	    {
//			informe.error("Error en producción: " + e.getMessage().toString());
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public static Connection getConnectionWeb() throws Exception
	{
		
			DataSource ds = null;	    
        	// Get DataSource
            Context initContext  = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
                   
            //Conexión a servidor de rolecar
            ds = (DataSource)envContext.lookup("jdbc/rolecar");
           
            if(ds != null )
            {
            	return ds.getConnection();
            }
            else
            {
            	throw new Exception("Fuente de datos no disponible");
            }

	}
	
	public static void limpieza(ResultSet rs,Statement stmt,Connection con)
	{
		try
		{
			if (rs != null) rs.close();
		}
		catch (Exception e)
		{
			informe.error("Error: " + e.getMessage().toString());
		}
		try
		{
			if (stmt != null)
				stmt.close();
		}
		catch (Exception e)
		{
			informe.error("Error: " + e.getMessage().toString());
		}
		try
		{
			if (con != null)
			{
				con.setAutoCommit(true);
				con.close();
			}
		}
		catch (Exception e)
		{
			informe.error("Error: " + e.getMessage().toString());
		}
	}
}