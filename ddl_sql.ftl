-- Create table
create table %s
(
%s                       %s ,
)
tablespace GZMPCYSJF pctfree 10 initrans 1 maxtrans 255 storage ( initial 64K next 1M minextents 1 maxextents unlimited );

-- Add comments to the table
comment on table %s is '%s';
-- Add comments to the columns
comment on column %s.%s is '%s';

-- Create/Recreate primary, unique and foreign key constraints
alter table %s add constraint %s_IDX primary key (%s) using index tablespace GZMPCYSJF pctfree 10 initrans 2 maxtrans 255 storage ( initial 64K next 1M minextents 1 maxextents unlimited );
