package com.mine.mh.resource.config;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * response过滤器
 */
@Provider
public class PoweredByResponseFilter implements ContainerResponseFilter {

	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		responseContext.getHeaders().add("X-Powered-By", "LtTecknology :-)");
	}
}
