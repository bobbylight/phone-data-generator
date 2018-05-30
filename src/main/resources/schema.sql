drop table if exists phone cascade;
create table phone (
  number varchar(10) not null,
  type varchar(10) not null,
  constraint phone_pkey primary key (number)
);

create index phone_type_idx on phone (type);

drop table if exists phone_call cascade;
create table phone_call (
  phone_call_id serial,
  source_number varchar(10) not null,
  dest_number varchar(10) not null,
  time timestamp not null,
  duration int not null,
  constraint phone_call_pkey primary key (phone_call_id)
);

create index phone_call_source_number_idx on phone_call (source_number);
create index phone_call_dest_number_idx on phone_call (dest_number);
