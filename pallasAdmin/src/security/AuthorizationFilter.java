package security;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import persistence.User;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	@AuthenticatedUser
	User authenticatedUser;

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		// Get the resource class which matches with the requested URL
		// Extract the roles declared by it
		Class<?> resourceClass = resourceInfo.getResourceClass();
		List<Role> classRoles = extractRoles(resourceClass);
		// Get the resource method which matches with the requested URL
		// Extract the roles declared by it
		Method resourceMethod = resourceInfo.getResourceMethod();
		List<Role> methodRoles = extractRoles(resourceMethod);
		try {
			// Check if the user is allowed to execute the method
			// The method annotations override the class annotations
			if (methodRoles.isEmpty()) {
				for (Role role : classRoles) {
					System.out.println("classRoles : " + role.name());
				}
				checkPermissions(classRoles);
			} else {
				for (Role role : methodRoles) {
					System.out.println("methodRoles : " + role.name());
				}
				checkPermissions(methodRoles);
			}
		} catch (Exception e) {
			requestContext.abortWith(Response.status(
					Response.Status.UNAUTHORIZED).build());
		}
	}

	private void checkPermissions(List<Role> roles) throws Exception {
		Boolean exist = false;
		// Check if the user have one of the allowed roles
		// Throw an Exception if the user has not permission to execute the
		// method
		System.out.println("check permissions : authenticatedUser.getRole() : "
				+ authenticatedUser.getRole());
		for (Role role : roles) {

			if (role.name().equals(authenticatedUser.getRole())) {
				System.out.println("authenticatedUser have permission role :)");
				// throw new Exception();
				exist = true;
				return;
			}

		}
		if (exist == false) {
			throw new Exception();
		}
	}

	// Extract the roles from the annotated element
	private List<Role> extractRoles(AnnotatedElement annotatedElement) {
		if (annotatedElement == null) {
			return new ArrayList<Role>();
		} else {
			Secured secured = annotatedElement.getAnnotation(Secured.class);
			if (secured == null) {
				return new ArrayList<Role>();
			} else {
				Role[] allowedRoles = secured.value();
				return Arrays.asList(allowedRoles);
			}
		}
	}
}