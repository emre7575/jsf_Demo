package jsf_Demo.emreozturk.jsfProject.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jsf_Demo.emreozturk.jsfProject.model.Employee;
public class EmployeesJDBC {
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static final String USER="root";
	static final String PASS="root";
	public String createEmployeeTable() {
		Connection conn=null;
		Statement stmt=null;
		String sql;
		String result;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting to statament");
			stmt=conn.createStatement();
			DatabaseMetaData dbm=conn.getMetaData();
			ResultSet tables=dbm.getTables(null,null,"EMPLOYEES",null);
			if (tables.next()) {
				System.out.println("EMPLOYEES already table exits");
				result ="Exiting";
			}
			else {
				System.out.println("Creating table");
				sql="CREATE TABLE employees (" + 
						"id INT NOT NULL AUTO_INCREMENT," + 
						"first_name VARCHAR(100)," + 
						"last_name VARCHAR(200)," + 
						"company VARCHAR(100),"+
						"empl_number VARCHAR(100),"+
						"salary DOUBLE"+
						");";
				stmt.executeUpdate(sql);
				
				System.out.println("Employee table created");
				result = "Created";
			}
			stmt.close();
			conn.close();
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error creating employees table");
			return "error";
		}finally {
			try {
				if (stmt!=null) {
					stmt.close();
				}
			} catch (SQLException se2) {
			}
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
	}
	public List<Employee> findAllEmployees(){
		List<Employee> emplyoyeeList=new ArrayList<Employee>();
		Connection conn=null;
		Statement stmt=null;
		String sql;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting to statament");
			stmt=conn.createStatement();
			sql="SELECT * FROM employees";
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next()) {
				Employee currentEmployee =new Employee();
				currentEmployee.setId(rs.getInt("id"));
				currentEmployee.setFirstName(rs.getString("first_name"));
				currentEmployee.setLastName(rs.getString("last_name"));
				currentEmployee.setCompany(rs.getString("company"));
				currentEmployee.setEmplNumber(rs.getString("empl_number"));
				currentEmployee.setSalary(rs.getDouble("salary"));
				
				emplyoyeeList.add(currentEmployee);
			}
			rs.close();
			stmt.close();
			conn.close();
			return emplyoyeeList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error reading employees table");
			return emplyoyeeList;
		}finally {
			try {
				if (stmt!=null) {
					stmt.close();
				}
			} catch (SQLException se2) {
			}
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
	}
	
	public long insertEmployee(Employee employee){
		Connection conn=null;
		PreparedStatement preparedStatement=null;
		String sql;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("insertEmployee(): Connecting to database");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting to statament");
			sql="INSERT INTO employees"+
					"(first_name,last_name,company,empl_number,salary) VALUES"+
					"(?, ?, ?, ?, ?)";
			
			preparedStatement=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Creating prepared statment...");
			preparedStatement.setString(1, employee.getFirstName());
			preparedStatement.setString(2, employee.getLastName());
			preparedStatement.setString(3, employee.getCompany());
			preparedStatement.setString(4, employee.getEmplNumber());
			preparedStatement.setDouble(5, employee.getSalary());
			Integer affectedRows=preparedStatement.executeUpdate();
			
			Long idNewRow;
			if (affectedRows==0) {
				throw new SQLException("Creating row failed, no rows affected.");
			}
			try(ResultSet generatedKeys=preparedStatement.getGeneratedKeys()){
				if (generatedKeys.next()) {
					idNewRow=generatedKeys.getLong(1);
					System.out.println("Id of new object:"+idNewRow);
				}
				else {
					throw new SQLException("Creating row failed, no ID obtained.");
				}
				
			}
			preparedStatement.close();
			conn.close();
			return idNewRow;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error reading employees table");
			return 0;
		}finally {
			try {
				if (preparedStatement!=null) {
					preparedStatement.close();
				}
			} catch (SQLException se2) {
			}
			try {
				if (conn!=null) {
					conn.close();
				}
			} catch (Exception se) {
				se.printStackTrace();
			}
		}
	}
}
