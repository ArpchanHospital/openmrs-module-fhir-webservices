package org.openmrs.module.fhirwebservices.server;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.EncodingEnum;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.rest.server.interceptor.LoggingInterceptor;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import org.openmrs.module.fhirwebservices.resourceProvider.LocationResourceProvider;

@WebServlet(urlPatterns = "/fhirServlet")
public class FhirRESTServer extends RestfulServer {
    private static final String MODULE_SERVELET_PREFIX = "/fhirServlet";

    @Override
    protected void initialize() throws ServletException {

        List<IResourceProvider> resourceProviders = new ArrayList<IResourceProvider>();
        resourceProviders.add(new LocationResourceProvider());

        this.setFhirContext(FhirContext.forDstu3());
        setResourceProviders(resourceProviders);
        setDefaultPrettyPrint(true);
        setDefaultResponseEncoding(EncodingEnum.JSON);
        ResponseHighlighterInterceptor responseHighlighter = new ResponseHighlighterInterceptor();
        registerInterceptor(responseHighlighter);
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
        registerInterceptor(loggingInterceptor);
        loggingInterceptor.setLoggerName("test.accesslog");
        loggingInterceptor
                .setMessageFormat("Source[${remoteAddr}] Operation[${operationType} ${idOrResourceName}] UA[${requestHeader.user-agent}] Params[${requestParameters}]");
    }

    @Override
    protected String getRequestPath(String requestFullPath, String servletContextPath, String servletPath) {
       return requestFullPath.substring(escapedLength(servletContextPath) + escapedLength(servletPath) + escapedLength(
                MODULE_SERVELET_PREFIX));
    }
}
