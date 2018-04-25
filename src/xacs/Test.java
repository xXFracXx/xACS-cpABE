package xacs;

import cn.edu.pku.ss.crypto.abe.api.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsecretsharing.*;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Test() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		System.out.println("start ----");
		
		String[] args = new String[0]; 
		//CPABE.main(args);
		
		// build 10 shares, of which 2 are required to recover the secret
		SecureRandom random = new SecureRandom();
		ShareBuilder builder = new ShareBuilder("I am Batman. Seriously.".getBytes(), 2, 512, random);
		List<Share> shares = builder.build(10);

		// takes 2 shares, recovers secret
		List<Share> someShares = new ArrayList<Share>();
		someShares.add(shares.get(2));
		someShares.add(shares.get(7));
		ShareCombiner combiner = new ShareCombiner(someShares);
		System.out.println(new String(combiner.combine()));

		// omg I'm batman
		
		response.sendRedirect("index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
