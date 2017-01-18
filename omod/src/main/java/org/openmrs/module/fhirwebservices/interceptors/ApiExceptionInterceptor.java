package org.openmrs.module.fhirwebservices.interceptors;

import ca.uhn.fhir.rest.method.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;
import ca.uhn.fhir.rest.server.exceptions.ForbiddenOperationException;
import ca.uhn.fhir.rest.server.exceptions.InternalErrorException;
import ca.uhn.fhir.rest.server.interceptor.InterceptorAdapter;
import org.openmrs.api.APIAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class ApiExceptionInterceptor extends InterceptorAdapter {
    @Override
    public BaseServerResponseException preProcessOutgoingException(RequestDetails theRequestDetails, Throwable theException, HttpServletRequest theServletRequest) throws ServletException {
        Throwable cause = theException.getCause();
        if (theException instanceof InternalErrorException && cause != null && cause instanceof InvocationTargetException) {
            Throwable apiException = cause.getCause();
            if (apiException != null && apiException instanceof APIAuthenticationException) {
                return new ForbiddenOperationException(apiException.getMessage());
            }
        }
        return null;
    }

}
