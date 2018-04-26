package xacs;

import cn.edu.pku.ss.crypto.abe.api.*;
import cn.edu.pku.ss.crypto.abe.serialize.SerializeUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsecretsharing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
		
		int n = 10, t = 2;
		
		// build 10 shares, of which 2 are required to recover the secret
		SecureRandom random = new SecureRandom();
		ShareBuilder builder = new ShareBuilder("I am Batman. Seriously.".getBytes(), t, 512, random);
		List<Share> shares = builder.build(n);		
		
		
		
		// create a new Gson instance
		 Gson gson = new Gson();
		 // convert your list to json
		 String jsonCartList = gson.toJson(shares);
		 // print your generated json
		 System.out.println("jsonCartList: " + jsonCartList);
		 
		 
		 Type type = new TypeToken<List<Share>>(){}.getType();
		 List<Share> prodList = gson.fromJson(jsonCartList, type);

		 // print your List<Product>
		 System.out.println("prodList: " + prodList);
		 
		 

		// takes 2 shares, recovers secret
		List<Share> someShares = new ArrayList<Share>();
		someShares.add(prodList.get(2));
		someShares.add(prodList.get(7));
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
