package jsf_Demo.emreozturk.jsfProject.backing;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import jsf_Demo.emreozturk.jsfProject.JDBC.EmployeesJDBC;
import jsf_Demo.emreozturk.jsfProject.model.Employee;

@ManagedBean(name = "backingEmployees")
@SessionScoped
public class BackingEmployees {
	EmployeesJDBC empployeesJdbc=new EmployeesJDBC();
	public List<Employee>findAllEmployees() {
		return empployeesJdbc.findAllEmployees();
	}
}
