package it.unisa.zyphyksoprt.gestioneUtente.servlet;

import static org.mockito.Mockito.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Test;

import it.unisa.zyphyksport.gestioneUtente.servlet.LogoutServlet;

import java.io.IOException;

public class LogoutServletTest {
	@Test public void testDoGetLogout() throws ServletException, IOException {
		LogoutServlet servlet = new LogoutServlet();
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getContextPath()).thenReturn("http://localhost/zyphyk-sport-web");
		when(request.getSession()).thenReturn(session);
		servlet.doGet(request, response);
		
		verify(session).removeAttribute("roles");
		verify(session).invalidate();
		verify(response).sendRedirect("http://localhost/zyphyk-sport-web/login.jsp");
	}
}