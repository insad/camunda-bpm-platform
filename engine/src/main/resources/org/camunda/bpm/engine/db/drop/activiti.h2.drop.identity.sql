--
-- Copyright © 2013-2018 camunda services GmbH and various authors (info@camunda.com)
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

alter table ACT_ID_MEMBERSHIP
    drop constraint ACT_FK_MEMB_GROUP;

alter table ACT_ID_MEMBERSHIP
    drop constraint ACT_FK_MEMB_USER;

alter table ACT_ID_TENANT_MEMBER
    drop constraint ACT_FK_TENANT_MEMB;

alter table ACT_ID_TENANT_MEMBER
    drop constraint ACT_FK_TENANT_MEMB_USER;

alter table ACT_ID_TENANT_MEMBER
    drop constraint ACT_FK_TENANT_MEMB_GROUP;

alter table ACT_ID_TENANT_MEMBER
    drop constraint ACT_UNIQ_TENANT_MEMB_USER;

alter table ACT_ID_TENANT_MEMBER
    drop constraint ACT_UNIQ_TENANT_MEMB_GROUP;

drop table ACT_ID_TENANT_MEMBER if exists;
drop table ACT_ID_TENANT if exists;
drop table ACT_ID_INFO if exists;
drop table ACT_ID_GROUP if exists;
drop table ACT_ID_MEMBERSHIP if exists;
drop table ACT_ID_USER if exists;
