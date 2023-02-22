ALTER TABLE egbs_bill_v1 ADD COLUMN additionaldetails jsonb;

ALTER TABLE egbs_billdetail_v1 ADD COLUMN additionaldetails jsonb;

ALTER TABLE egbs_billaccountdetail_v1 ADD COLUMN additionaldetails jsonb;