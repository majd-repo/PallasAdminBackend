package rest;

import java.util.List;

import javax.faces.bean.RequestScoped;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import persistence.Category;
import security.Role;
import security.Secured;
import services.interfaces.CategoryServiceLocal;

@Secured({ Role.ADMIN })
@RequestScoped
@Path("categories")
public class CategoryResource {

	@Inject
	private CategoryServiceLocal categoryServiceLocal;

	@GET
	@Path("json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response allCategories(@PathParam("id") Integer id) {
		if (id == null || id.intValue() == 0) {
			return Response.serverError().entity("Id Cannot be blank").build();
		}
		Category category = categoryServiceLocal.findCategoryById(id);
		if (category == null) {
			return Response.status(Status.NOT_FOUND)
					.entity("Category not found").build();
		}

		return Response.status(Status.OK)
				.entity(categoryServiceLocal.findCategoryById(id)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> dofindAllCategories() {
		return categoryServiceLocal.findAllCategories();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Category doFindCategoryById(@PathParam("id") Integer id) {
		return categoryServiceLocal.findCategoryById(id);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void doAddCategory(Category category) {
		categoryServiceLocal.addCategory(category);
	}

	@PUT
	@Path("{id}")
	public void doSaveCategory(Category category) {
		categoryServiceLocal.saveCategory(category);
	}

	@DELETE
	@Path("{id}")
	public void doDeleteCategory(@PathParam("id") Integer id) {
		categoryServiceLocal.deleteCategory(id);
	}
}
