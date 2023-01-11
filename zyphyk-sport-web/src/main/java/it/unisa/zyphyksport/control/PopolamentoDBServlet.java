package it.unisa.zyphyksport.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class PopolamentoDBServlet
 */
public class PopolamentoDBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopolamentoDBServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource ds = (DataSource) getServletContext().getAttribute("DataSource");
		
		PrintWriter out = response.getWriter();
        out.println("<p>" + ds + "</p>");
        
        Connection connection = null;

        PreparedStatement preparedStmt = null;

         

        String insertSQL = "INSERT INTO " + "GESTORI_CATALOGO"

        + " (USERNAME, NAME, SURNAME, EMAIL, PASS_WORD, RAL) VALUES (?, ?, ?, ?, ?, ?)";

         

        try {

	        connection = ds.getConnection();
	
	        preparedStmt = connection.prepareStatement(insertSQL);
	
	        preparedStmt.setString(1, "PIPPO");
	
	        preparedStmt.setString(2, "GIUSEPPE");
	
	        preparedStmt.setString(3, "GUARRACINO");
	
	        preparedStmt.setString(4, "feofeodkewdwed@gmail.com");
	
	        preparedStmt.setString(5, "maidne2234");
	
	        preparedStmt.setInt(6, 2000);
	
	         
	
	        preparedStmt.executeUpdate();
	
	         
	
	        connection.setAutoCommit(false);
	
	        connection.commit();

        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

        try {

	        if (preparedStmt != null)
	
	        	preparedStmt.close();
	
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	
	        if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	        }

        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
