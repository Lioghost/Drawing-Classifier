
package API;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {

    private PrintWriter outter;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            outter = response.getWriter();
            response.setContentType("application/json");
            response.addHeader("Access-Control-Allow-Origin", "*");
            String usr = request.getParameter("user");
            String pass = request.getParameter("password");
            PrintWriter out = response.getWriter();
              
            boolean b = true;
    
        /*
        try {
            DB bd= new DB();
            bd.setConnection("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost/usuarios");
            ResultSet rs=bd.executeQuery("select * from login where USERNAME='"+usr+"' and PASSWORD='"+pass+"';");
            if(rs.next()) {
                String usuario=rs.getString("USERNAME");
                b = false;
                outter.write(devolverJSON(usuario));
            }
        
            if(b) {
                out.write(devolverJSONError());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement("select * from login where USERNAME='"+usr+"' and PASSWORD='"+pass+"';");
            rs = stmt.executeQuery();
            if(rs.next()) {
                String usuario=rs.getString("USERNAME");
                b = false;
                System.out.println(devolverJSON(usuario));
                
                outter.write(devolverJSON(usuario));
            }
        
            if(b) {
                out.write(devolverJSONError());
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DB.Close(stmt);
            DB.Close(conn);
            DB.Close(rs);
        }
        
    } 

    private String devolverJSON(String usuario) {
        StringBuilder json = new StringBuilder();

        json.append("[");
        json.append("{");
        json.append(jsonValue("usuario", usuario));
        json.append("}");
        json.append("]");
        return json.toString();
    }
    
        private String devolverJSONError() {
            StringBuilder json = new StringBuilder();
            json.append("[");
            json.append("{");
            json.append(jsonValue("usuario", "error"));
            json.append("}");
            json.append("]");
            return json.toString();
        }

        private String jsonValue(String key, Object value) {
            return new StringBuilder()
                .append("\"")
                .append(key)
                .append("\" : \"")
                .append(value)
                .append("\"")
                .toString();
    }

}
