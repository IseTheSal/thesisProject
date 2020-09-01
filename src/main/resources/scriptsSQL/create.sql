create schema finalshop;

alter schema finalshop owner to postgres;

create table shop_user
(
	user_id uuid not null,
	login varchar,
	password varchar,
	name varchar,
	phonenumber varchar,
	address varchar
);

alter table shop_user owner to postgres;

create unique index usernew_id_uindex
	on shop_user (user_id);

create table shop_moderator
(
	moderator_id uuid not null,
	login varchar,
	password varchar
);

alter table shop_moderator owner to postgres;

create unique index shop_moderator_id_uindex
	on shop_moderator (moderator_id);

create table shop_admin
(
	admin_id uuid not null,
	login varchar,
	password varchar
);

alter table shop_admin owner to postgres;

create unique index shop_admin_id_uindex
	on shop_admin (admin_id);

create table shop_courier
(
	courier_id uuid not null,
	login varchar,
	password varchar,
	phonenumber varchar,
	name varchar
);

alter table shop_courier owner to postgres;

create unique index shop_courier_id_uindex
	on shop_courier (courier_id);

create table smartphone
(
	phone_id uuid not null,
	serialnumber varchar,
	brand varchar,
	available varchar,
	model varchar,
	memory integer,
	storage integer,
	microcd boolean,
	minijack boolean,
	faceid boolean,
	fastcharge boolean,
	wirelesscharge boolean,
	material varchar,
	batterycapacity integer,
	warrantyyears varchar,
	camera_camera_id uuid,
	shop_order_user_user_id uuid,
	shop_order_order_id uuid
);

alter table smartphone owner to postgres;

create unique index smartphone_id_uindex
	on smartphone (phone_id);

create table camera
(
	camera_id uuid not null
);

alter table camera owner to postgres;

create unique index camera_id_uindex
	on camera (camera_id);

create table lens
(
	lens_id uuid not null,
	megapixels integer,
	maximumzoom integer,
	photoresolution varchar,
	videoresolution varchar,
	nightmode boolean,
	camera_camera_id uuid
);

alter table lens owner to postgres;

create unique index lens_id_uindex
	on lens (lens_id);

create table shoporder
(
	order_id uuid not null,
	deliverystatus varchar,
	user_user_id uuid
);

alter table shoporder owner to postgres;

create unique index shoporder_order_id_uindex
	on shoporder (order_id);

