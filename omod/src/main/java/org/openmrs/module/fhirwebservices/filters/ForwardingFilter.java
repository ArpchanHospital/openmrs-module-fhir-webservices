/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.fhirwebservices.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ForwardingFilter implements Filter {

	private String openmrsPath;

	@Override
	public void init(FilterConfig fc) throws ServletException {
		openmrsPath = fc.getServletContext().getContextPath();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String requestURI = request.getRequestURI();

		if (requestURI.startsWith(openmrsPath + "/ws/fhir")) {
			String newURI = requestURI.replace("/openmrs/ws/fhir", "/ms/fhirServlet");
			req.getRequestDispatcher(newURI).forward(req, res);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}

}
