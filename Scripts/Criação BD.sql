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

SELECT * FROM USER_CONFIGURACAO;
INSERT INTO USER_CONFIGURACAO (USER_ID, idioma, fluencia, status) VALUES (4, 1, 1, '...');

create table USER_LOGIN(
	codigo int not null auto_increment,
	user_id varchar(30) not null,
	situacao bit(1) not null,
	data_ultimo_acesso date not null,
    coordenada_ultimo_acesso varchar(50) not null,
    primary key(codigo, user_id)
);

SELECT * FROM USER_LOGIN;
INSERT INTO USER_LOGIN (USER_ID, SITUACAO, DATA_ULTIMO_ACESSO, coordenada_ultimo_acesso) VALUES (4, 1, '2016-04-14', '-23.5192|-46.8367'); -- Carapicuiba

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