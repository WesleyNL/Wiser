CREATE DATABASE PROJETO_ANDROID;

USE PROJETO_ANDROID;

---------------------------
CREATE TABLE USER_LOGIN(
	codigo int not null auto_increment,
	user_id varchar(30) not null,
	situacao bit(1) not null,
	data_ultimo_acesso date not null,
    coordenada_ultimo_acesso varchar(50) not null,
    primary key(codigo, user_id)
);
---------------------------

---------------------------
CREATE TABLE USER_CONFIGURACAO(
	codigo int not null auto_increment,
    user_id varchar(30) not null,
	idioma tinyint not null,
	fluencia tinyint not null,
	status varchar(150),
    primary key(codigo, user_id)
);
---------------------------

---------------------------
CREATE TABLE IDIOMA(
	codigo int not null auto_increment,
	descricao_ptbr varchar(30) not null,
    descricao_en varchar(30) not null,
    primary key(codigo)
);

INSERT INTO IDIOMA (descricao_ptbr) VALUES ("Todos"), ("Inglês"), ("Espanhol");
INSERT INTO IDIOMA (descricao_en) VALUES ("All"), ("English"), ("Spanhol");
---------------------------

---------------------------
CREATE TABLE FLUENCIA(
	codigo int not null auto_increment,
	descricao_ptbr varchar(30) not null,
    descricao_en varchar(30) not null,
    primary key(codigo)
);

INSERT INTO FLUENCIA (descricao_ptbr) VALUES ("Todos"), ("Básico"), ("Intermediário"), ("Avançado"), ("Fluente");
INSERT INTO FLUENCIA (descricao_en) VALUES ("All"), ("Basic"), ("Intermediate"), ("Advanced"), ("Fluent");
---------------------------

---------------------------
CREATE TABLE DISCUSSAO(
	codigo bigint not null auto_increment,
	titulo varchar(30) not null,
	descricao varchar(250) not null,
	data_hora datetime not null,
    autor varchar(30) not null,
    situacao bit(1) not null,
	primary key(codigo)
);
---------------------------

---------------------------
CREATE TABLE DISCUSSAO_RESPOSTA(
	codigo bigint not null auto_increment,
    discussao bigint not null,
    data_hora datetime not null,
    resposta varchar(250) not null,
    autor varchar(30) not null,
    primary key(codigo),
    foreign key(discussao) references DISCUSSAO(codigo)
);
---------------------------

---------------------------
CREATE TABLE MENSAGEM_ITEM(
	codigo bigint not null auto_increment,
	user_id_destinatario varchar(30) not null,
    user_id_carregar varchar(30) not null,
    data_hora datetime not null,
	primary key(codigo, user_id_destinatario, user_id_carregar)
);
---------------------------

---------------------------
CREATE TABLE MENSAGEM(
	codigo bigint not null auto_increment,
    codigo_mensagem_item bigint not null,
    user_id_remetente varchar(30) not null,
    data_hora datetime not null,
    mensagem varchar(250) not null,
    lido tinyint not null,
    primary key(codigo, codigo_mensagem_item),
    foreign key(codigo_mensagem_item) references MENSAGEM_ITEM(codigo)
);
---------------------------

---------------------------
-- Tratamento para o banco aceitar qualquer caractere, porém, é preciso tratar também na aplicação.
SET NAMES utf8mb4;
ALTER DATABASE PROJETO_ANDROID CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

ALTER TABLE USER_CONFIGURACAO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE USER_CONFIGURACAO CHANGE STATUS STATUS VARCHAR(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;
REPAIR TABLE USER_CONFIGURACAO;
OPTIMIZE TABLE USER_CONFIGURACAO;

ALTER TABLE DISCUSSAO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE DISCUSSAO CHANGE titulo titulo VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;
REPAIR TABLE DISCUSSAO;
OPTIMIZE TABLE DISCUSSAO;

ALTER TABLE DISCUSSAO CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE DISCUSSAO CHANGE descricao descricao VARCHAR(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;
REPAIR TABLE DISCUSSAO;
OPTIMIZE TABLE DISCUSSAO;

ALTER TABLE DISCUSSAO_RESPOSTA CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE DISCUSSAO_RESPOSTA CHANGE resposta resposta VARCHAR(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;
REPAIR TABLE DISCUSSAO_RESPOSTA;
OPTIMIZE TABLE DISCUSSAO_RESPOSTA;

ALTER TABLE MENSAGEM CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE MENSAGEM CHANGE mensagem mensagem VARCHAR(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL;
REPAIR TABLE MENSAGEM;
OPTIMIZE TABLE MENSAGEM;