package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.Company;

@Local
public interface CompanyServiceLocal {

	public void addCompany(Company company);

	public void saveCompany(Company company);

	public List<Company> findAllCompanies();

	public Company findCompanyById(Integer id);

	public void deleteCompany(Integer id);
}
