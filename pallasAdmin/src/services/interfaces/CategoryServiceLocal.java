package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import persistence.Category;

@Local
public interface CategoryServiceLocal {

	public void addCategory(Category category);

	public void saveCategory(Category category);

	public List<Category> findAllCategories();

	public Category findCategoryById(Integer id);

	public void deleteCategory(Integer id);

}
