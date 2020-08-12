

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Ex3.do")
public class Ex3Servelet extends HttpServlet {
	static HashMap<Integer,String> colorMap = new HashMap<>();
	private static final long serialVersionUID = 1L;

    public Ex3Servelet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		colorMap.put(1, "검정"); colorMap.put(2, "빨강"); colorMap.put(3, "주황"); colorMap.put(4, "노랑");
		colorMap.put(5, "초록"); colorMap.put(6, "파랑"); colorMap.put(7, "보라"); colorMap.put(8, "카키"); 
		request.setAttribute("color", colorMap.get((int)(Math.random()*8)+1));
		request.getRequestDispatcher("/Ex3Result.jsp").forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

}
