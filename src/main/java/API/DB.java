package API;

import java.io.*;
import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class DB implements java.io.Serializable
{
    private static final String JDBC_URL = "jdbc:mysql://localhost/usuarios";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "12345";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static BasicDataSource dataSource;
    /*private String url;
    private String driver;
    private transient Connection con;
    private Statement stmtquery;
    private Statement stmtupdate;
    private ResultSet rs;*/
    
    public static DataSource getDataSource() {
        if(dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(JDBC_DRIVER);
            dataSource.setUsername(JDBC_USER);
            dataSource.setPassword(JDBC_PASS);
            dataSource.setUrl(JDBC_URL);
        }
        
        return dataSource;
    }
    
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
    
    public static void Close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public static void Close(PreparedStatement stmt) {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
    
    public static void Close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    /*
    public void setConnection(String driver,String url)
      throws IOException,java.sql.SQLException {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, "root", "12345");
            this.url=	url;
            this.driver=driver;
        }
        catch(ClassNotFoundException e)
        {
            throw new IOException(e.getMessage());
        }
        catch(SQLException e)
        {
            throw e;
        }

    }

    public void closeConnection()
      throws java.sql.SQLException {
        if(con!=null)
            con.close();
        url=driver=null;
        if(stmtupdate!=null)stmtupdate.close();
        if(stmtquery!=null)stmtquery.close();
        stmtupdate=stmtquery= null;
        rs=null;

    }
    
//------------------------------------------
    public int executeUpdate(String sql)
      throws java.sql.SQLException {
        if(con==null)
            throw new SQLException("No ha configurado correctamente la conexion Source:Bean handledb");

        stmtupdate = null;
        int affecrows=0;

        try {
            stmtupdate=con.createStatement();
            affecrows=stmtupdate.executeUpdate(sql);
        }

        finally {
            if(stmtupdate != null) stmtupdate.close();
        }
        
      return affecrows;
    }


 //-----------------------------------------
    public ResultSet executeQuery(String sql)
      throws java.sql.SQLException {
        if(con==null)
            throw new SQLException("No ha configurado correctamente la conexion Source:Bean handledb");

        stmtquery = null;
        rs=null;

        try {
            stmtquery=con.createStatement();
            rs=stmtquery.executeQuery(sql);
        }

        finally {

        }
        
        return rs;
      }
      
 //---------------------------------------------
 public String getUrl()
 {
        return url;
 }

 public String getDriver()
 {
        return driver;
 }
    */
}
