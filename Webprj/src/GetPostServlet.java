

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetPost.do")
public class GetPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetPostServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Post 예제</title>");
		out.println("</head/");
		out.println("<body>");
		out.printf("아이디 : %s, 비밀번호 : %s", id, pw);
		out.println("<body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Post 예제</title>");
		out.println("</head/");
		out.println("<body>");
		out.printf("아이디 : %s, 비밀번호 : %s", id, pw);
		out.println("<body></html>");
	}

}
