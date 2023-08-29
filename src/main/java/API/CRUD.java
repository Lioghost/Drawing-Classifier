/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import MODEL.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
public class CRUD extends HttpServlet {
    
    private UserDAO userDAO;
    private PrintWriter outter;
    
    public CRUD() {
        this.userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        this.doGet(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        outter = response.getWriter();
        response.setContentType("application/json");
        response.addHeader("Access-Control-Allow-Origin", "*");
        /*List<User> usuarios = new UserDAO().listar();
        System.out.println("usuarios ="+usuarios);
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);*/
        
        String action = request.getParameter("action");
        System.out.println("Holaaaaaaaaaaaaaaaaa" +action);
        
        switch(action) {
            case "insert":
                try {
                    insertUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
                break;
            case "delete":
                try {
                    deleteUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
                break;
            case "update":
                try {
                    updateUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
                break;
            case "edit":
                try {
                    editUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
                break;
            default:
                try {
                    listUsers(request, response);
                } catch (SQLException e) {
                    e.printStackTrace(System.out);
                }
                break;
        }
        
    }
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {
        int idUser = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("name");
        String password = request.getParameter("prediction");
        String email = request.getParameter("percent");
        User user = new User(idUser, username, password, email);
        System.out.println(user);
        //JsonObject obj = new JsonObject();
        int registUpdate = new UserDAO().updateUser(user);
        outter.write(devolverJSON(String.valueOf(registUpdate)));
        //response.sendRedirect("list");
        //this.listUsers(request, response);
    }
    
    private void editUser(HttpServletRequest request, HttpServletResponse response)//showEditForm  editarUser
        throws ServletException, SQLException, IOException {
        
        int idUser = Integer.parseInt(request.getParameter("id"));
        User user = new UserDAO().selectUser(new User(idUser));
        String JsonUser = new Gson().toJson(user);
        outter.write(JsonUser);
        //request.setAttribute("user", user);
    }
    
    private void insertUser(HttpServletRequest request, HttpServletResponse response) //        insertarUsuario
        throws SQLException, IOException {
        
        String username = request.getParameter("name");
        String password = request.getParameter("prediction");
        String email = request.getParameter("percent");
        
        User newUser = new User(username, password, email);
       
        int registroInsertado = userDAO.insertUser(newUser);
        outter.write(devolverJSON(String.valueOf(registroInsertado)));
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, IOException {
        
        int idUser = Integer.parseInt(request.getParameter("id"));
        User user = new User(idUser);
        int flag = userDAO.deleteUser(idUser);
        outter.write(devolverJSON(String.valueOf(flag)));
        //response.sendRedirect("list");
        //this.listUsers(request, response);
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, SQLException, IOException {
        
        List<User> listUsers = new UserDAO().selectAllUsers();
        String jsonList = new Gson().toJson(listUsers);
        System.out.println(jsonList);
        outter.write(jsonList);
        //request.setAttribute("listUser", listUsers);
        
    }
    
    private String devolverJSON(String usuario) {
        StringBuilder json = new StringBuilder();

        json.append("[");
        json.append("{");
        json.append(jsonValue("response", usuario));
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
