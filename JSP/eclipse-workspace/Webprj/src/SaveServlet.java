

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Save.do")
public class SaveServlet extends HttpServlet {
	static HashMap<String,String> map = new HashMap<>();
	private static final long serialVersionUID = 1L;
 	
    public SaveServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String url = "";
		if(action.equals("list")) {
			request.setAttribute("map", map);
			url = "/Savelist.jsp";
		}else if(action.equals("search")) {
			String name = request.getParameter("name");
			if(map.containsKey(name)) {
				request.setAttribute("tel", map.get(name));
			}else {
				request.setAttribute("message", "해당 데이터가 없습니다.");
			}
			url = "/SaveView.jsp";
		}
		request.getRequestDispatcher(url).forward(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		map.put(name, tel);
		response.sendRedirect("/SaveResult.jsp");
	}
}
