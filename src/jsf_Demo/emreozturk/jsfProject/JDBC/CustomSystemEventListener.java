package jsf_Demo.emreozturk.jsfProject.JDBC;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import jsf_Demo.emreozturk.jsfProject.model.Company;

public class CustomSystemEventListener implements SystemEventListener {
	@Override
	public boolean isListenerForSource(Object value) {
		return (value instanceof Application);
	}
	
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if (event instanceof PostConstructApplicationEvent) {
			System.out.println("Application Starter");
			EmployeesJDBC employeesJDBC=new EmployeesJDBC();
			employeesJDBC.createEmployeeTable();
			CompaniesJDBC companiesJDBC=new CompaniesJDBC();
			companiesJDBC.createCompaniesTable();
			String result=companiesJDBC.createCompaniesTable();
			if (result.equals("Created")) {
				Company company=new Company();
				company.setName("Mc Neil Corp.");
				companiesJDBC.insertCompany(company);
				company.setName("Sanders");
				companiesJDBC.insertCompany(company);
				company.setName("Microvawe");
				companiesJDBC.insertCompany(company);
				company.setName("HoldMart Corp.");
				companiesJDBC.insertCompany(company);
				company.setName("Genesys");
				companiesJDBC.insertCompany(company);
				company.setName("Mosquitos");
				companiesJDBC.insertCompany(company);
			}
		}
		if (event instanceof PreDestroyApplicationEvent) {
			System.out.println("Application Starter");
		}
	}
	

}
