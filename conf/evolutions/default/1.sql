# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table boulder (
  id                        bigint not null,
  climb_name                varchar(255),
  first_assent              varchar(255),
  grade                     varchar(255),
  sent                      boolean,
  climbed_date              timestamp,
  crag_crag_name            varchar(255),
  constraint pk_boulder primary key (id))
;

create table climber (
  username                  varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_climber primary key (username))
;

create table crag (
  crag_name                 varchar(255) not null,
  location                  varchar(255),
  constraint pk_crag primary key (crag_name))
;


create table boulder_climber (
  boulder_id                     bigint not null,
  climber_username               varchar(255) not null,
  constraint pk_boulder_climber primary key (boulder_id, climber_username))
;

create table crag_climber (
  crag_crag_name                 varchar(255) not null,
  climber_username               varchar(255) not null,
  constraint pk_crag_climber primary key (crag_crag_name, climber_username))
;
create sequence boulder_seq;

create sequence climber_seq;

create sequence crag_seq;

alter table boulder add constraint fk_boulder_crag_1 foreign key (crag_crag_name) references crag (crag_name) on delete restrict on update restrict;
create index ix_boulder_crag_1 on boulder (crag_crag_name);



alter table boulder_climber add constraint fk_boulder_climber_boulder_01 foreign key (boulder_id) references boulder (id) on delete restrict on update restrict;

alter table boulder_climber add constraint fk_boulder_climber_climber_02 foreign key (climber_username) references climber (username) on delete restrict on update restrict;

alter table crag_climber add constraint fk_crag_climber_crag_01 foreign key (crag_crag_name) references crag (crag_name) on delete restrict on update restrict;

alter table crag_climber add constraint fk_crag_climber_climber_02 foreign key (climber_username) references climber (username) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists boulder;

drop table if exists boulder_climber;

drop table if exists climber;

drop table if exists crag;

drop table if exists crag_climber;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists boulder_seq;

drop sequence if exists climber_seq;

drop sequence if exists crag_seq;

