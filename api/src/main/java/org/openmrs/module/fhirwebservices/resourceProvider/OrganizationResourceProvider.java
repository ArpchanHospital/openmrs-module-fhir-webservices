package org.openmrs.module.fhirwebservices.resourceProvider;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hibernate.metamodel.source.annotations.entity.IdType;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.instance.model.api.IBaseResource;

public class OrganizationResourceProvider implements IResourceProvider {
    public Class<? extends IBaseResource> getResourceType() {
        return Organization.class;
    }

    @Read
    public Organization getOrganization(@IdParam IdType organizationId) {
//        LocationService locationService = Context.getLocationService();
//        Location location = locationService.getLocationByUuid(organizationId.getValue());
        return new Organization();
    }
}
