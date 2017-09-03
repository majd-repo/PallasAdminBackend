package services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistence.Company;
import persistence.Transfer;
import persistence.Visitor;
import services.interfaces.CompanyServiceLocal;

@Stateless
@LocalBean
public class CompanyService implements CompanyServiceLocal {

	@PersistenceContext
	private EntityManager em;

	public CompanyService() {

	}

	@Override
	public void addCompany(Company company) {
		company.setDcr(new Date());
		em.persist(company);

	}

	@Override
	public void saveCompany(Company company) {
		company.setDmj(new Date());
		em.merge(company);

	}

	@Override
	public List<Company> findAllCompanies() {
		return em.createQuery("SELECT c FROM Company c", Company.class)
				.getResultList();

	}

	@Override
	public Company findCompanyById(Integer id) {
		return em.find(Company.class, id);
	}

	@Override
	public void deleteCompany(Integer id) {

		List<Transfer> transfers = em.createQuery("SELECT t FROM Transfer t",
				Transfer.class).getResultList();

		List<Visitor> visitors = em
				.createQuery(
						"SELECT v FROM Visitor v WHERE v.company.id = :param",
						Visitor.class).setParameter("param", id)
				.getResultList();
		for (Visitor visitor : visitors) {
			for (Transfer transfer : transfers) {
				transfer.getVisitors().remove(
						em.merge(em.find(Visitor.class, visitor.getId())));
			}
			em.remove(em.merge(em.find(Visitor.class, visitor.getId())));
		}
		em.remove(em.find(Company.class, id));

	}

}
