

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Ex2.do")
public class Ex2Servelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Ex2Servelet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num =Integer.parseInt(request.getParameter("num"));
		String message = null;
		if(num%2==0) {
			if(num%3==0) {
				if(num%5==0 ) {
					message = "2와 3과 5의 공배수입니다.";
				}else {
					message = "2와 3의 공배수입니다.";
				}
			}else if(num%5==0) {
				message = "2와 5의 공배수입니다.";
			}else {
				message = "2의 배수입니다.";
			}
		}else if(num%3==0) {
			if(num%5==0) {
				message = "3과 5의 공배수입니다.";
			}else {
				message = "3의 배수 입니다.";
			}
		}else if(num%5==0) {
			message = "5의 배수입니다.";			
		}else {
			message = "2와 3과 5의 공배수입니다.";
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/Ex2Result.jsp").forward(request, response);;
	}
	
}
