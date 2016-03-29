create database PROJETO_ANDROID;

use PROJETO_ANDROID;

create table USER_CONFIGURACAO(
	codigo int not null auto_increment,
    user_id varchar(20) not null,
	idioma varchar(20) not null,
	fluencia bit(1) not null,
	status varchar(150),
    primary key(codigo, user_id)
);

create table USER_LOGIN(
	codigo int not null auto_increment,
	user_id varchar(20) not null,
	situacao bit(1) not null,
	data_ultimo_acesso date not null,
    coordenada_ultimo_acesso varchar(10) not null,
    primary key(codigo, user_id)
);

drop table USER_CONFIGURACAO