package jsf_Demo.emreozturk.jsfProject.JDBC;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jsf_Demo.emreozturk.jsfProject.model.Company;

public class CompaniesJDBC {
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static final String USER="root";
	static final String PASS="root";
	public String createCompaniesTable() {
		Connection conn=null;
		Statement stmt=null;
		String sql;
		String result;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Create Companies Table(): connecting to database...");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Creating to statament");
			stmt=conn.createStatement();
			DatabaseMetaData dbm=conn.getMetaData();
			ResultSet tables=dbm.getTables(null,null,"COMPANIES",null);
			if (tables.next()) {
				System.out.println("COMPANIES already table exits");
				result ="Exiting";
			}
			else {
				System.out.println("Creating table");
				sql="CREATE TABLE companies (" + 
						"id INT," + 
						"name VARCHAR(100)" + 
						");";
				stmt.executeUpdate(sql);
				
				System.out.println("Companies table created");
				result = "Created";
			}
			stmt.close();
			conn.close();
			return result;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error creating companies table");
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
	
	public long insertCompany(Company company){
		Connection conn=null;
		PreparedStatement preparedStatement=null;
		String sql;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("insertCompany(): Connecting to database");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting to statament");
			sql="INSERT INTO companies"+
					"(name) VALUES"+
					"(?)";
			
			preparedStatement=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Creating prepared statment...");
			preparedStatement.setString(1, company.getName());
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
	
	public List<Company> findAllCompanies(){
		List<Company> companiesList=new ArrayList<Company>();
		Connection conn=null;
		Statement stmt=null;
		String sql;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database");
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting to statament");
			stmt=conn.createStatement();
			sql="SELECT * FROM companies";
			ResultSet rs=stmt.executeQuery(sql);
			while (rs.next()) {
				Company currentCompany =new Company();
				currentCompany.setId(rs.getLong("id"));
				currentCompany.setName(rs.getString("name"));
				companiesList.add(currentCompany);
			}
			rs.close();
			stmt.close();
			conn.close();
			return companiesList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Error reading companies table");
			return null;
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
}
