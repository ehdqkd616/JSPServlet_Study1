package lab.web.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.management.RuntimeErrorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class EmpDAO {
	
	static {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("드라이버 로드 성공");
		}catch(SQLException e) {
			System.out.println("드라이버 로드 실패");
			System.out.println(e.getMessage());
		}
	}
	
	private Connection getConnection() {
		DataSource ds = null;
		Connection con = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			con = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	private void closeConnection(Connection con) {
		if(con!=null) {
			try {con.close();}catch(SQLException e) {}
		}
	}
	
	public EmpVO selectEmp(int empId) {
		Connection con = null;
		EmpVO emp = new EmpVO();
		try {
			con = getConnection();
			String sql = "select * from employees where employee_id=?";
			PreparedStatement stmt = con.prepareStatement(sql); 
			stmt.setInt(1, empId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
			}
		}catch(SQLException e) {
			throw new RuntimeException("selctEmployee메서드 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return emp;
	}
	
	public ArrayList<EmpVO> selectEmployeeList(){
		Connection con = null;
		ArrayList<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "Select * from employees";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				list.add(emp);
			}
		}catch(SQLException e) {
			throw new RuntimeException("selctEmployeeList에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public EmpDetailVO selectEmpDetails(int empId) {
		Connection con = null;
		EmpDetailVO emp = new EmpDetailVO();
		try {
			con = getConnection();
			String sql = "select employee_id, first_name, last_name,"
					+ "email, phone_number, hire_date, e.job_id, job_title,"
					+ "salary, commission_pct, e.manager_id, manager_name,"
					+ "e.department_id, department_name " 
					+ "from employees e "
					+ "left join departments d "
					+ "on e.department_id=d.department_id "
					+ "left join jobs j "
					+ "on e.job_id=j.job_id "
					+ "left join (select employee_id as manager_id, "
					+ "first_name||' '||last_name as manager_name "
					+ "from employees where employee_id in "
					+ "(select distinct manager_id from employees)) m "
					+ "on e.manager_id = m.manager_id "
					+ "where employee_id=?";	
			PreparedStatement stmt = con.prepareStatement(sql); 
			stmt.setInt(1, empId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt(13));
				emp.setJobTitle(rs.getString("job_title"));
				emp.setDepartmentName(rs.getString("department_name"));
				emp.setManagerName(rs.getString("manager_name"));
			}
		}catch(SQLException e) {
			throw new RuntimeException("selectEmpDetails에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return emp;
	}
	
	public ArrayList<EmpVO> selectEmployeeByDept(int dept){
		Connection con = null;
		ArrayList<EmpVO> list = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select * from employees where department_id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, dept);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				list.add(emp);
			}
		}catch(SQLException e) {
			throw new RuntimeException("selectDepartment에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return list;
	}
	
	public ArrayList<DeptVO> selectDepts(){
		Connection con = null;
		ArrayList<DeptVO> deptList = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select distinct department_name, department_id from departments";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				DeptVO dept = new DeptVO();
				dept.setDepartmentId(rs.getInt("department_id"));
				dept.setDepartmentName(rs.getString("department_name"));
				deptList.add(dept);
			}
		}catch(SQLException e) {
			throw new RuntimeException("selectDepts에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return deptList;
	}
	
		public ArrayList<JobVO> selectJobs(){
		Connection con = null;
		ArrayList<JobVO> jobList = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select job_id, job_title from jobs";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				JobVO job = new JobVO();
				job.setJobId(rs.getString("job_id"));
				job.setJobTitle(rs.getString("job_title"));
				jobList.add(job);
			}
		}catch(SQLException e) {
			throw new RuntimeException("selectJobs에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return jobList;
	}
		
	public ArrayList<EmpVO> selectMans(){
		Connection con = null;
		ArrayList<EmpVO> manList = new ArrayList<>();
		try {
			con = getConnection();
			String sql = "select employee_id, first_name||' ' ||last_name "
					+ "as manager_name from employees "
					+ "where employee_id in "
					+ "(select distinct manager_id from employees)";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				EmpVO emp = new EmpVO();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("manager_name"));
				manList.add(emp);
			}
		}catch(SQLException e) {
			throw new RuntimeException("SelectMans에서 예외 발생 :"+e.getMessage());
		}finally {
			closeConnection(con);
		}
		return manList;
	}
	
	public void insertEmployees(EmpVO emp) {
		Connection con = null;
		try {
			con = getConnection();
			String sql = "insert into employees values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(sql); 
			stmt.setInt(1, emp.getEmployeeId());
			stmt.setString(2, emp.getFirstName());
			stmt.setString(3, emp.getLastName());
			stmt.setString(4, emp.getEmail());
			stmt.setString(5, emp.getPhoneNumber());
			stmt.setDate(6, emp.getHireDate());
			stmt.setString(7, emp.getJobId());
			stmt.setDouble(8, emp.getSalary());
			stmt.setDouble(9, emp.getCommissionPct());
			stmt.setInt(10, emp.getManagerId());
			stmt.setInt(11, emp.getDepartmentId());
			int result = stmt.executeUpdate();
			if(result==0) {
				throw new RuntimeException("사원정보가 입력되지 않았습니다.");
			}
			
		}catch(SQLException e) {
			if(e.getMessage().contains("unique")) {
				throw new RuntimeException("사원번호 또는 이메일 중복");
			}else {
				throw new RuntimeException("insertEmployee예외 발생 :"+e.getMessage());
			}
		}finally {
			closeConnection(con);
		}
	}
	public void updateEmployees(EmpVO emp) {
		Connection con = null;
		try {
			con = getConnection();
			//update 쿼리문 사용해서 적기
			String sql = "update employees "
					+ "set employee_id = ?,"
					+ "first_name= ?,"
					+ "last_name = ?,"
					+ "email = ?,"
					+ "phone_number= ?,";
			PreparedStatement stmt = con.prepareStatement(sql); 
			stmt.setInt(1, emp.getEmployeeId());
			stmt.setString(2, emp.getFirstName());
			stmt.setString(3, emp.getLastName());
			stmt.setString(4, emp.getEmail());
			stmt.setString(5, emp.getPhoneNumber());
			stmt.setDate(6, emp.getHireDate());
			stmt.setString(7, emp.getJobId());
			stmt.setDouble(8, emp.getSalary());
			stmt.setDouble(9, emp.getCommissionPct());
			stmt.setInt(10, emp.getManagerId());
			stmt.setInt(11, emp.getDepartmentId());
			int result = stmt.executeUpdate();
			if(result==0) {
				throw new RuntimeException("사원정보가 입력되지 않았습니다.");
			}
			
		}catch(SQLException e) {
			if(e.getMessage().contains("unique")) {
				throw new RuntimeException("사원번호 또는 이메일 중복");
			}else {
				throw new RuntimeException("insertEmployee예외 발생 :"+e.getMessage());
			}
		}finally {
			closeConnection(con);
		}
	}
	
}




