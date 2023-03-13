CREATE TABLE eg_wf_processinstance_v2_history AS (SELECT * FROM eg_wf_processinstance_v2);

CREATE UNIQUE INDEX uk_eg_wf_processinstance_history ON eg_wf_processinstance_v2_history (id);
CREATE UNIQUE INDEX idx_pi_wf_processinstance_history ON eg_wf_processinstance_v2_history (businessId,lastModifiedTime);
CREATE INDEX idx_tenant_status_eg_wf_processinstance_v2_history ON eg_wf_processinstance_v2_history USING btree ((tenantid || ':' || status));

CREATE TABLE eg_wf_processinstance_v2_active (LIKE eg_wf_processinstance_v2 INCLUDING ALL);

ALTER TABLE eg_wf_document_v2 DROP CONSTRAINT fk_eg_wf_document;
ALTER TABLE eg_wf_document_v2 ADD CONSTRAINT fk_eg_wf_document FOREIGN KEY (processinstanceid) REFERENCES eg_wf_processinstance_v2_active(id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE eg_wf_assignee_v2 DROP CONSTRAINT fk_eg_wf_assignee_v2;
ALTER TABLE eg_wf_assignee_v2 ADD CONSTRAINT fk_eg_wf_assignee_v2 FOREIGN KEY (processinstanceid) REFERENCES eg_wf_processinstance_v2_active(id) ON UPDATE CASCADE ON DELETE CASCADE;
