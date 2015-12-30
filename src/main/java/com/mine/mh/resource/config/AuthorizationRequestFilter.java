package com.mine.mh.resource.config;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.mine.mh.resource.bean.ResponseMessage;

/**
 * request过滤器
 */
@Provider
public class AuthorizationRequestFilter implements ContainerRequestFilter {

	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		ResponseMessage.CurrentUriInfo = requestContext.getUriInfo();

		// final SecurityContext securityContext =
		// requestContext.getSecurityContext();
		// if (securityContext == null ||
		// !securityContext.isUserInRole("privileged"))
		// {
		//
		// requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User cannot access the resource.").build());
		// }
	}
}
