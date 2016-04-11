create database PROJETO_ANDROID;

use PROJETO_ANDROID;

create table USER_CONFIGURACAO(
	codigo int not null auto_increment,
    user_id varchar(30) not null,
	idioma tinyint not null,
	fluencia tinyint not null,
	status varchar(150),
    primary key(codigo, user_id)
);

create table USER_LOGIN(
	codigo int not null auto_increment,
	user_id varchar(30) not null,
	situacao bit(1) not null,
	data_ultimo_acesso date not null,
    coordenada_ultimo_acesso varchar(50) not null,
    primary key(codigo, user_id)
);

SELECT * FROM USER_LOGIN;
DROP TABLE USER_LOGIN;

create table IDIOMA(
	codigo int not null auto_increment,
	descricao varchar(30) not null,
    primary key(codigo)
);

insert into IDIOMA (descricao) values("Inglês"), ("Espanhol");

create table FLUENCIA(
	codigo int not null auto_increment,
	descricao varchar(30) not null,
    primary key(codigo)
);

insert into FLUENCIA (descricao) values("Básico"), ("Intermediário"), ("Avançado"), ("Fluente");
