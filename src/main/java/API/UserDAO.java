/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import MODEL.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class UserDAO {
    
    /*
    private static final String SQL_SELECT = "SELECT idLOGIN, USERNAME, PASSWORD, EMAIL FROM login";
    private static final String  SQL_INSEERT = "INSERT INTO login(USERNAME, PASSWORD, EMAIL) VALUES(?,?,?)";
    private static final String SQL_UPDATE = "UPDATE login SET USERNAME=?, PASSWORD=?, EMAIL=? WHERE idLOGIN=?";
    private static final String SQL_DELETE = "DELETE FROM login WHERE idLOGIN=?";
    private static final String SQL_SELECT_BY_ID = "SELECT idLOGIN, USERNAME, PASSWORD, EMAIL FROM login WHERE idLOGIN=?";
    */
    
    private static final String SQL_SELECT = "SELECT idCANVAS, NAME, CANVAS, COMMENT FROM canvas";
    private static final String  SQL_INSEERT = "INSERT INTO canvas(NAME, CANVAS, COMMENT) VALUES(?,?,?)";
    private static final String SQL_UPDATE = "UPDATE canvas SET NAME=?, CANVAS=?, COMMENT=? WHERE idCANVAS=?";
    private static final String SQL_DELETE = "DELETE FROM canvas WHERE idCANVAS=?";
    private static final String SQL_SELECT_BY_ID = "SELECT idCANVAS, NAME, CANVAS, COMMENT FROM canvas WHERE idCANVAS=?";
    
    public List<User> selectAllUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()) {
                int idLogin = rs.getInt("idCANVAS");
                String username = rs.getString("NAME");
                String password = rs.getString("CANVAS");
                String email = rs.getString("COMMENT");
                System.out.println("Hola, soy goku " +username+ " "+password+" "+email);
                
                users.add(new User(idLogin, username, password, email));
            }
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DB.Close(conn);
            DB.Close(stmt);
            DB.Close(rs);
        }
        
        return users;
    }
    
    public User selectUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, user.getId());
            rs = stmt.executeQuery();
            rs.next();
            String username = rs.getString("NAME");
            String pass = rs.getString("CANVAS");
            String email = rs.getString("COMMENT");
            
            user.setName(username);
            user.setPassword(pass);
            user.setEmail(email);
            
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DB.Close(conn);
            DB.Close(stmt);
            DB.Close(rs);
        }
        
        return user;
    }
    
    public int insertUser(User usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int flag = 0;
        
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement(SQL_INSEERT);
            stmt.setString(1, usuario.getName());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());
            flag = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DB.Close(conn);
            DB.Close(stmt);
        }
        
        return flag;
    }
    
    public int updateUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int flag = 0;
        
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, user.getId());
            //Para saber que registro se va actualizar se necesita el ID
            flag = stmt.executeUpdate(); 
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DB.Close(conn);
            DB.Close(stmt);
        }
        
        return flag;
    }
    
    public int deleteUser(int usuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int flag = 0;
        
        try {
            conn = DB.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            //stmt.setInt(1, usuario.getId());
            stmt.setInt(1, usuario);
            flag = stmt.executeUpdate(); 
            
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            DB.Close(stmt);
            DB.Close(conn);
        }
        
        return flag;
    }
    
    /*public static void main(String[] args) {
        UserDAO user = new UserDAO();
        user.selectAllUsers();
    }*/
}
