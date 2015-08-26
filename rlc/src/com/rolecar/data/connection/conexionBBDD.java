package com.rolecar.data.connection;


import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Properties;



import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.rolecar.beans.City;
import com.rolecar.beans.Country;
import com.rolecar.data.constantes.Atributos;


public class conexionBBDD
{	
//    private static final String url = Configuracion.getInstance().getProperty("url");
//	private static final String dbUsername = Configuracion.getInstance().getProperty("dbUsername");
//	private static final String dbPassword = Configuracion.getInstance().getProperty("dbPassword");
//	private static final Properties propiedades = Configuracion.getInstance().getProperties();
	public static Logger informe = Logger.getLogger(conexionBBDD.class.getName());
	private static String nombresch = Atributos.ESQUEMA;
	private static String nombretabla = Atributos.TABLASWITCH;
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
	
	/**
	 * Get on-line tables
	 * @return String - suffix of online tables
	 * @throws SQLException
	 */
	public static String getSchemaOnline() throws SQLException{
		String schemaReturn = "";
		Connection con = null;
    	try
    	{
    		con = getConnectionWeb();
    		String sql = "select * from ".concat(nombresch).concat(".").concat(nombretabla);
            PreparedStatement pStm= con.prepareStatement(sql);
            ResultSet rs = pStm.executeQuery();
            while(rs!=null && rs.next())
            {
            	//schemaReturn = "_c";
            	String online = rs.getString("schema_on");
            	if ("B".equals(online)){
            		schemaReturn = "_b";
            	}
//            	else if("C".equals(online)){
//            		schemaReturn = "_c";
//            	}
            }
		}
		catch(SQLException e){informe.error(e.getMessage());con.close(); ;return schemaReturn;}
        catch(Exception e){informe.error(e.getMessage());con.close();return schemaReturn;}
		finally
        {
        	if (con!=null)
        	{
        		con.close();con=null;
        	}
        }
    	//System.out.println("Esquema Online:: " + schemaReturn);
		return schemaReturn;
	}
}