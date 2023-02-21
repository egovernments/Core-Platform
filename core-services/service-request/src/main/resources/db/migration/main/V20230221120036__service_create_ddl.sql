CREATE TABLE eg_service(
    id character varying(64),
    tenantId character varying(64),
    serviceDefId character varying(64),
    referenceId character varying(64),
    createdBy character varying(64),
    lastModifiedBy character varying(64),
    createdTime bigint,
    lastModifiedTime bigint,
    additionalDetails JSONB,
    CONSTRAINT uk_eg_service UNIQUE (id)
);

CREATE TABLE eg_service_attribute_value(
    id character varying(64),
    referenceId character varying(64),
    attributeCode character varying(64),
    "value" JSONB,
    code character varying(64),
    createdBy character varying(64),
    lastModifiedBy character varying(64),
    createdTime bigint,
    lastModifiedTime bigint,
    additionalDetails JSONB,
    CONSTRAINT uk_eg_attribute_value UNIQUE (id)
);
