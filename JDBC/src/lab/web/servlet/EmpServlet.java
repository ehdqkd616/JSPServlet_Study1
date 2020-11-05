package lab.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.web.model.EmpDAO;
import lab.web.model.EmpDetailVO;
import lab.web.model.EmpVO;


@WebServlet("/Emp.do")
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmpDAO dao;
    public EmpServlet() {
        super();

    }


	@Override
	public void init(ServletConfig config) throws ServletException {
		dao = new EmpDAO();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String url = "";
		if("search".equals(action)) {
			String sempId = request.getParameter("empId");
			int empId = 100;
			if(sempId!=null) {
				empId = Integer.parseInt(sempId);
				if(empId<100 || empId>207) {
					empId=100;
				}
			}
			EmpVO emp = dao.selectEmp(empId);
			request.setAttribute("emp", emp);
			url = "/SearchEmployee.jsp";
		}else if("list".equals(action)) {
			ArrayList<EmpVO> list = dao.selectEmployeeList();
			request.setAttribute("list", list);
			url = "/hr/ListEmployee.jsp";
		}else if("view".equals(action)) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			EmpDetailVO emp = dao.selectEmpDetails(empId);
			request.setAttribute("emp", emp);
			url = "/hr/ViewEmployee.jsp";
		}else if("dept".equals(action)) {
			int deptId = Integer.parseInt(request.getParameter("deptId"));
			ArrayList<EmpVO> list = dao.selectEmployeeByDept(deptId);
			request.setAttribute("list", list);
			url = "/hr/SearchDepartment.jsp";
		}else if("insert".equals(action)) {
			request.setAttribute("jobList", dao.selectJobs());
			request.setAttribute("deptList", dao.selectDepts());
			request.setAttribute("manList", dao.selectMans());
			request.setAttribute("action", action);
			url = "/hr/InsertEmployee.jsp";
		}else if("update".equals(action)) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			EmpVO emp = dao.selectEmp(empId);
			request.setAttribute("jobList", dao.selectJobs());
			request.setAttribute("deptList", dao.selectDepts());
			request.setAttribute("manList", dao.selectMans());
			request.setAttribute("emp", emp);
			request.setAttribute("action", action);
			url = "/hr/InsertEmployee.jsp";
		}
		
		request.getRequestDispatcher(url).forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if("insert".equals(action)) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			String sdate = request.getParameter("hireDate");
			SimpleDateFormat tool = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date hireDate = null;
			try {
				hireDate = new java.sql.Date(tool.parse(sdate).getTime());
			}catch(ParseException e) {
				
			}
			String jobId = request.getParameter("jobId");
			double salary = Double.parseDouble(request.getParameter("salary"));
			double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
			int managerId = Integer.parseInt(request.getParameter("managerId"));
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			EmpVO emp = new EmpVO();
			emp.setEmployeeId(empId);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);
			emp.setPhoneNumber(phoneNumber);
			emp.setJobId(jobId);
			emp.setHireDate(hireDate);
			emp.setSalary(salary);
			emp.setCommissionPct(commissionPct);
			emp.setManagerId(managerId);
			emp.setDepartmentId(departmentId);
			dao.insertEmployees(emp);
			response.sendRedirect("/JDBC/Emp.do?action=list");
		}else if("update".contentEquals(action)) {
			int empId = Integer.parseInt(request.getParameter("empId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			String sdate = request.getParameter("hireDate");
			SimpleDateFormat tool = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date hireDate = null;
			try {
				hireDate = new java.sql.Date(tool.parse(sdate).getTime());
			}catch(ParseException e) {
				
			}
			String jobId = request.getParameter("jobId");
			double salary = Double.parseDouble(request.getParameter("salary"));
			double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
			int managerId = Integer.parseInt(request.getParameter("managerId"));
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			EmpVO emp = new EmpVO();
			emp.setEmployeeId(empId);
			emp.setFirstName(firstName);
			emp.setLastName(lastName);
			emp.setEmail(email);
			emp.setPhoneNumber(phoneNumber);
			emp.setJobId(jobId);
			emp.setHireDate(hireDate);
			emp.setSalary(salary);
			emp.setCommissionPct(commissionPct);
			emp.setManagerId(managerId);
			emp.setDepartmentId(departmentId);
			dao.updateEmployees(emp);
			response.sendRedirect("/JDBC/Emp.do?action=list");
		}

	}
}






