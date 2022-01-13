create table nagato_department
(
    department_no   bigint unsigned auto_increment
        primary key,
    department_name varchar(30)                        not null,
    department_info text                               null,
    gmt_create      datetime default CURRENT_TIMESTAMP null,
    gmt_modified    datetime                           null
);

create table nagato_department_icon
(
    id            bigint unsigned auto_increment
        primary key,
    department_no bigint unsigned null,
    icon_url      text            null,
    constraint nagato_department_icon_ibfk_1
        foreign key (department_no) references nagato_department (department_no)
            on delete cascade
);

create index department_no
    on nagato_department_icon (department_no);

create table nagato_device
(
    device_no                     bigint unsigned auto_increment
        primary key,
    device_serial                 varchar(50)                                null,
    device_name                   varchar(30)                                null,
    device_production_date        datetime                                   null,
    device_manufacturer           varchar(30)                                null,
    device_service_life_from_date datetime                                   null,
    device_service_life_to_date   datetime                                   null,
    is_key_device                 tinyint unsigned default '0'               null,
    gmt_create                    datetime         default CURRENT_TIMESTAMP null,
    gmt_modified                  datetime                                   null
);

create table nagato_device_category
(
    category_no        bigint unsigned auto_increment
        primary key,
    device_no          bigint unsigned                    null,
    category_type      tinyint unsigned                   null,
    category_name      varchar(20)                        null,
    is_key_device_type tinyint  default 0                 null,
    gmt_create         datetime default CURRENT_TIMESTAMP null,
    gmt_modified       datetime                           null,
    constraint nagato_device_category_ibfk_1
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade
);

create index device_no
    on nagato_device_category (device_no);

create table nagato_device_in_use_notice
(
    id                   bigint unsigned auto_increment
        primary key,
    device_no            bigint unsigned                    null,
    device_in_use_notice text                               null,
    gmt_create           datetime default CURRENT_TIMESTAMP null,
    gmt_modified         datetime                           null,
    constraint nagato_device_in_use_notice_ibfk_1
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade
);

create index device_no
    on nagato_device_in_use_notice (device_no);

create table nagato_device_in_use_notice_pic_url
(
    id           bigint unsigned auto_increment
        primary key,
    notice_id    bigint unsigned                    null,
    pic_url      text                               null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_device_in_use_notice_pic_url_ibfk_1
        foreign key (notice_id) references nagato_device_in_use_notice (id)
            on delete cascade
);

create index notice_id
    on nagato_device_in_use_notice_pic_url (notice_id);

create table nagato_device_pic
(
    id           bigint unsigned auto_increment
        primary key,
    device_no    bigint unsigned                    not null,
    pic_url      text                               not null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_device_pic_ibfk_1
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade
);

create index device_no
    on nagato_device_pic (device_no);

create table nagato_device_rfid
(
    id           bigint unsigned auto_increment
        primary key,
    device_no    bigint unsigned                    null,
    rfid_no      bigint unsigned                    null,
    rfid_serial  varchar(50)                        null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_device_rfid_ibfk_1
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade
);

create index device_no
    on nagato_device_rfid (device_no);

create table nagato_inspection
(
    inspection_no       bigint unsigned auto_increment
        primary key,
    inspection_title    varchar(30)                        null,
    inspection_abstract varchar(50)                        null,
    gmt_create          datetime default CURRENT_TIMESTAMP null,
    gmt_modified        datetime                           null
);

create table nagato_device_inspection
(
    id                      bigint unsigned auto_increment
        primary key,
    device_no               bigint unsigned                    null,
    inspection_no           bigint unsigned                    null,
    device_inspection_cycle varchar(20)                        null,
    gmt_create              datetime default CURRENT_TIMESTAMP null,
    gmt_modified            datetime                           null,
    constraint nagato_device_inspection_ibfk_1
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade,
    constraint nagato_device_inspection_ibfk_2
        foreign key (inspection_no) references nagato_inspection (inspection_no)
            on delete cascade
);

create index device_no
    on nagato_device_inspection (device_no);

create index inspection_no
    on nagato_device_inspection (inspection_no);

create table nagato_inspection_device_status
(
    id            bigint unsigned auto_increment
        primary key,
    inspection_no bigint unsigned                    null,
    device_no     bigint unsigned                    null,
    device_status varchar(30)                        null,
    gmt_create    datetime default CURRENT_TIMESTAMP null,
    gmt_modified  datetime                           null,
    constraint nagato_inspection_device_status_ibfk_1
        foreign key (inspection_no) references nagato_inspection (inspection_no)
            on delete cascade,
    constraint nagato_inspection_device_status_ibfk_2
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade
);

create table nagato_device_status_pic_url
(
    id           bigint unsigned auto_increment
        primary key,
    status_id    bigint unsigned                    null,
    pic_url      text                               null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_device_status_pic_url_ibfk_1
        foreign key (status_id) references nagato_inspection_device_status (id)
            on delete cascade
);

create index status_id
    on nagato_device_status_pic_url (status_id);

create index device_no
    on nagato_inspection_device_status (device_no);

create index inspection_no
    on nagato_inspection_device_status (inspection_no);

create table nagato_inspection_device_status_detail
(
    id            bigint unsigned auto_increment
        primary key,
    status_id     bigint unsigned                    null,
    status_detail text                               null,
    gmt_create    datetime default CURRENT_TIMESTAMP null,
    gmt_modified  datetime                           null,
    constraint nagato_inspection_device_status_detail_ibfk_1
        foreign key (status_id) references nagato_inspection_device_status (id)
            on delete cascade
);

create index status_id
    on nagato_inspection_device_status_detail (status_id);

create table nagato_inspection_location
(
    id                    bigint unsigned auto_increment
        primary key,
    inspection_no         bigint unsigned null,
    type                  varchar(20)     null,
    geometry_type         varchar(20)     null,
    geometry_coordinate_x decimal         null,
    geometry_coordinate_y decimal         null,
    properties_name       varchar(30)     null,
    constraint nagato_inspection_location_ibfk_1
        foreign key (inspection_no) references nagato_inspection (inspection_no)
            on delete cascade
);

create index inspection_no
    on nagato_inspection_location (inspection_no);

create table nagato_plain_user
(
    user_no                bigint unsigned auto_increment
        primary key,
    user_no_generate       varchar(36)      default '0'               null,
    user_real_name         varchar(20)                                null,
    user_nick              varchar(20)                                null,
    user_gender            enum ('M', 'F')                            null,
    user_hire_date         datetime                                   null,
    user_credit            decimal(3, 2)                              null,
    user_favorite_digit    int unsigned     default '0'               null,
    user_face_id           varchar(30)                                null,
    user_current_mood      varchar(20)                                null,
    is_inspector           tinyint unsigned default '0'               null,
    access_property_device tinyint unsigned default '4'               null,
    access_property_user   tinyint unsigned default '5'               null,
    access_property_log    tinyint unsigned default '3'               null,
    gmt_create             datetime         default CURRENT_TIMESTAMP null,
    gmt_modified           datetime                                   null
);

create table nagato_department_user
(
    id            bigint unsigned auto_increment
        primary key,
    department_no bigint unsigned not null,
    user_no       bigint unsigned not null,
    constraint nagato_department_user_ibfk_1
        foreign key (department_no) references nagato_department (department_no)
            on delete cascade,
    constraint nagato_department_user_ibfk_2
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index department_no
    on nagato_department_user (department_no);

create index user_no
    on nagato_department_user (user_no);

create table nagato_device_user
(
    id                       bigint unsigned auto_increment
        primary key,
    device_no                bigint unsigned                    null,
    user_no                  bigint unsigned                    null,
    is_in_use                tinyint unsigned                   null,
    is_on_lending            tinyint unsigned                   null,
    user_lending_no          tinyint unsigned                   null,
    device_lending_from_date datetime                           null,
    device_lending_to_date   datetime                           null,
    gmt_create               datetime default CURRENT_TIMESTAMP null,
    gmt_modified             datetime                           null,
    constraint nagato_device_user_ibfk_1
        foreign key (device_no) references nagato_device (device_no)
            on delete cascade,
    constraint nagato_device_user_ibfk_2
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index device_no
    on nagato_device_user (device_no);

create index user_no
    on nagato_device_user (user_no);

create table nagato_inspection_user
(
    id            bigint unsigned auto_increment
        primary key,
    inspection_no bigint unsigned                    null,
    user_no       bigint unsigned                    null,
    gmt_create    datetime default CURRENT_TIMESTAMP null,
    gmt_modified  datetime                           null,
    constraint nagato_inspection_user_ibfk_1
        foreign key (inspection_no) references nagato_inspection (inspection_no)
            on delete cascade,
    constraint nagato_inspection_user_ibfk_2
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index inspection_no
    on nagato_inspection_user (inspection_no);

create index user_no
    on nagato_inspection_user (user_no);

create table nagato_inspector
(
    id                  bigint unsigned auto_increment
        primary key,
    user_no             bigint unsigned                    not null,
    inspector_uuid      text                               not null,
    inspector_name      varchar(30)                        null,
    inspect_area_name   varchar(30)                        not null,
    inspect_area_detail text                               not null,
    gmt_create          datetime default CURRENT_TIMESTAMP null,
    gmt_modified        datetime                           null,
    constraint nagato_inspector_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index user_no
    on nagato_inspector (user_no);

create table nagato_supervisor
(
    user_no                bigint unsigned auto_increment
        primary key,
    user_no_generate       bigint unsigned  default '0'               null,
    user_nick              varchar(20)                                null,
    user_password          varchar(30)                                null,
    access_property_device tinyint unsigned default '1'               null,
    access_property_user   tinyint unsigned default '1'               null,
    access_property_log    tinyint unsigned default '2'               null,
    gmt_create             datetime         default CURRENT_TIMESTAMP null,
    gmt_modified           datetime                                   null
);

create table nagato_todo_list
(
    list_no          bigint unsigned auto_increment
        primary key,
    list_no_generate bigint unsigned                    null,
    list_title       varchar(30)                        null,
    list_content     varchar(50)                        null,
    list_from_time   datetime                           null,
    list_to_time     datetime                           null,
    gmt_create       datetime default CURRENT_TIMESTAMP null,
    gmt_modified     datetime                           null
);

create table nagato_list_user
(
    id           bigint unsigned auto_increment
        primary key,
    list_no      bigint unsigned                    null,
    user_no      bigint unsigned                    null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_list_user_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade,
    constraint nagato_list_user_ibfk_2
        foreign key (list_no) references nagato_todo_list (list_no)
            on delete cascade
);

create index list_no
    on nagato_list_user (list_no);

create index user_no
    on nagato_list_user (user_no);

create index list_no
    on nagato_todo_list (list_no);

create table nagato_user_credit
(
    id           bigint unsigned auto_increment
        primary key,
    user_no      bigint unsigned                    not null,
    user_credit  decimal(3, 2)                      null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_user_credit_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index nagato_user_credit
    on nagato_user_credit (user_no);

create table nagato_user_email
(
    id           bigint unsigned auto_increment
        primary key,
    user_no      bigint unsigned                    not null,
    user_email   varchar(50)                        null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_user_email_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index nagato_user_email
    on nagato_user_email (user_no);

create table nagato_user_icon
(
    id           bigint unsigned auto_increment
        primary key,
    user_no      bigint unsigned                    null,
    icon_url     text                               null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_user_icon_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index plain_user_icon_index
    on nagato_user_icon (user_no);

create index supervisor_icon_index
    on nagato_user_icon (user_no);

create table nagato_user_password
(
    id            bigint unsigned auto_increment
        primary key,
    user_no       bigint unsigned                    not null,
    gmt_create    datetime default CURRENT_TIMESTAMP null,
    gmt_modified  datetime                           null,
    user_password varchar(50)                        not null,
    constraint nagato_user_password_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index user_no
    on nagato_user_password (user_no);

create table nagato_user_sign
(
    id           bigint unsigned auto_increment
        primary key,
    user_no      bigint unsigned                    not null,
    user_sign    varchar(50)                        null,
    gmt_create   datetime default CURRENT_TIMESTAMP null,
    gmt_modified datetime                           null,
    constraint nagato_user_sign_ibfk_1
        foreign key (user_no) references nagato_plain_user (user_no)
            on delete cascade
);

create index nagato_user_sign
    on nagato_user_sign (user_no);

create table oauth_client_details
(
    client_id               varchar(256)  not null
        primary key,
    resource_ids            varchar(256)  null,
    client_secret           varchar(256)  null,
    scope                   varchar(256)  null,
    authorized_grant_types  varchar(256)  null,
    web_server_redirect_uri varchar(256)  null,
    authorities             varchar(256)  null,
    access_token_validity   int           null,
    refresh_token_validity  int           null,
    additional_information  varchar(4096) null,
    autoapprove             varchar(256)  null
);


