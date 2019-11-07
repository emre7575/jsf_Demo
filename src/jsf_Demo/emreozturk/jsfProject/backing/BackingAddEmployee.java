package jsf_Demo.emreozturk.jsfProject.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import jsf_Demo.emreozturk.jsfProject.JDBC.CompaniesJDBC;
import jsf_Demo.emreozturk.jsfProject.JDBC.EmployeesJDBC;
import jsf_Demo.emreozturk.jsfProject.model.Company;
import jsf_Demo.emreozturk.jsfProject.model.Employee;

@ManagedBean(name = "backingAddEmployee")
@ViewScoped
public class BackingAddEmployee {
	private Employee employee=new Employee();
	private EmployeesJDBC employeesJDBC=new EmployeesJDBC();
	private List<Company> companiesList=new ArrayList<Company>();
	private CompaniesJDBC companiesJDBC=new CompaniesJDBC();
	public List<Company> getCompaniesList() {
		return companiesList;
	}

	public void setCompaniesList(List<Company> companiesList) {
		this.companiesList = companiesList;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String saveEmployee() {
		employeesJDBC.insertEmployee(employee);
		return "employees";
	}
	public void init() {
		this.companiesList=companiesJDBC.findAllCompanies();
		Company company=new Company();
		company.setName("");
		companiesList.add(0,company);
		
	}
}
