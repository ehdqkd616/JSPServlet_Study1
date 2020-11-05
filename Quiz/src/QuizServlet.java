

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Quiz.do")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public QuizServlet() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String[] fruit = request.getParameterValues("fruit");
		String gender = request.getParameter("gender");
		String job = request.getParameter("job");
		String data = request.getParameter("data");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Quiz</title>");
		out.println("</head/");
		out.println("<body>");
		out.printf("아이디 : %s<br>", id);
		out.printf("비밀번호 : %s<br>", pw);
		HashMap<String,String> fruitMap = new HashMap<>();
		fruitMap.put("1", "사과"); fruitMap.put("2", "귤");
		fruitMap.put("3", "감");
		
		out.print("과일 : ");
		for(String f : fruit) {
			out.printf("%s ", fruitMap.get(f));
		}
		out.println("<br>");
		out.printf("성별 : %s<br>", gender.contentEquals("M") ? "남성" : "여성");
		out.printf("직업 : %s<br>", job);
		out.printf("<pre>%s</pre><br>", data);
		out.println("<br>");
		out.println("</body></html>");
	}
}
