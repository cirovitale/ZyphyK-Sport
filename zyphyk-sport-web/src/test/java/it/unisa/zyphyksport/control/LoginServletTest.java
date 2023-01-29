package it.unisa.zyphyksport.control;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

public class LoginServletTest {

	@Test
	public void testDoPost() throws ServletException, IOException{
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		// eventuali altri mock
		Mockito.when(request.getParameter("username"))
		.thenReturn("Emanuele");
		Mockito.when(request.getParameter("password"))
		.thenReturn("12345");
		LoginServlet serv = new LoginServlet();
		serv.doPost(request, response);
		assertEquals(response.getHeader("Location"),"index.jsp");
		
	}
}
