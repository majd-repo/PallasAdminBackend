package services;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import persistence.Category;
import persistence.Transfer;
import persistence.Visitor;
import services.interfaces.CategoryServiceLocal;

@Stateless
@LocalBean
public class CategoryService implements CategoryServiceLocal {

	@PersistenceContext
	private EntityManager em;

	public CategoryService() {

	}

	@Override
	public List<Category> findAllCategories() {

		return em.createQuery("SELECT c FROM Category c", Category.class)
				.getResultList();

	}

	@Override
	public void addCategory(Category category) {
		category.setDcr(new Date());
		em.persist(category);
	}

	@Override
	public Category findCategoryById(Integer id) {

		Category category = em.find(Category.class, id);
		if (category == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return category;

	}

	@Override
	public void saveCategory(Category category) {
		category.setDmj(new Date());
		em.merge(category);

	}

	@Override
	public void deleteCategory(Integer id) {
		List<Transfer> transfers = em.createQuery("SELECT t FROM Transfer t",
				Transfer.class).getResultList();
		List<Visitor> visitors = em
				.createQuery(
						"SELECT v FROM Visitor v WHERE v.category.id = :param",
						Visitor.class).setParameter("param", id)
				.getResultList();
		for (Visitor visitor : visitors) {
			for (Transfer transfer : transfers) {
				transfer.getVisitors().remove(
						em.merge(em.find(Visitor.class, visitor.getId())));
			}
			em.remove(em.merge(em.find(Visitor.class, visitor.getId())));
		}
		em.remove(em.merge(em.find(Category.class, id)));

	}

}
