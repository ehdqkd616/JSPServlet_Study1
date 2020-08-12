

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Input.do")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InputServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String date = request.getParameter("date");
		SimpleDateFormat tool = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date birth = null;
		try {
			birth = tool.parse(date);
		} catch (ParseException e) {
			
		}
		String[] hobbys = request.getParameterValues("hobby");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		int grade = Integer.parseInt(request.getParameter("grade"));
		String intro = request.getParameter("intro");
		String area = request.getParameter("area");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Input 예제</title>");
		out.println("</head/");
		out.println("<body>");
		out.printf("아이디 : %s<br>", id);
		out.printf("비밀번호 : %s<br>", pw);
		out.printf("생년월일 : %s<br>", birth.toString());
		HashMap<String,String> hobbyMap = new HashMap<>();
		hobbyMap.put("1", "노래"); hobbyMap.put("2", "피아노");
		hobbyMap.put("3", "운동"); hobbyMap.put("4", "독서");
		hobbyMap.put("5", "춤");
		out.print("취미 : ");
		for(String h : hobbys) {
			out.printf("%s ", hobbyMap.get(h));
		}
		out.println("<br>");
		out.printf("성별 : %s<br>", gender.contentEquals("M") ? "남성" : "여성");
		out.printf("이메일 : %s<br>", email);
		out.printf("연락처 : %s<br>", tel);
		out.printf("학년 : %d<br>", grade);
		out.printf("<pre>%s</pre><br>", intro);
		HashMap<String,String> areaMap = new HashMap<>();
		areaMap.put("1", "서울"); areaMap.put("2", "경기도");
		areaMap.put("3", "강원도"); areaMap.put("4", "충청북도");
		areaMap.put("5", "충청남도"); areaMap.put("6", "전라북도");
		areaMap.put("7", "전라남도"); areaMap.put("8", "경상북도");
		areaMap.put("9", "경상남도");
		out.printf("지역 : %s<br>", areaMap.get(area));
		out.println("</body></html>");
	}
}
