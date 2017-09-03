package security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		responseContext.getHeaders().putSingle("Access-Control-Allow-Origin",
				"*");
		responseContext
				.getHeaders()
				.putSingle(
						"Access-Control-Allow-Headers",
						"origin, X-Requested-With, content-type, accept, authorization, X-Content-Type-Options, Strict-Transport-Security");
		responseContext.getHeaders().putSingle(
				"Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().putSingle("Access-Control-Allow-Methods",
				"POST, PUT, DELETE, OPTIONS, HEAD");
		responseContext.getHeaders().putSingle("Access-Control-Max-Age",
				"1209600");
	}
}