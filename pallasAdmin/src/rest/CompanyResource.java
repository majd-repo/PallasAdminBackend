package rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import persistence.Company;
import security.Role;
import security.Secured;
import services.interfaces.CompanyServiceLocal;

@Secured({ Role.ADMIN })
@RequestScoped
@Path("company")
public class CompanyResource {
	@Inject
	private CompanyServiceLocal companyServiceLocal;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> doFindAllCompanies() {
		return companyServiceLocal.findAllCompanies();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company doFindCompanyById(@PathParam("id") Integer id) {
		return companyServiceLocal.findCompanyById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAddCompany(Company company) {
		companyServiceLocal.addCompany(company);
	}

	@PUT
	@Path("{id}")
	public void doSaveCompany(Company company) {
		companyServiceLocal.saveCompany(company);
	}

	@DELETE
	@Path("{id}")
	public void doDeleteCompany(@PathParam("id") Integer id) {
		companyServiceLocal.deleteCompany(id);
	}
}
