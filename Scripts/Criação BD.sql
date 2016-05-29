create database PROJETO_ANDROID;

use PROJETO_ANDROID;

-----------------------------
create table USER_CONFIGURACAO(
	codigo int not null auto_increment,
    user_id varchar(30) not null,
	idioma tinyint not null,
	fluencia tinyint not null,
	status varchar(150),
    primary key(codigo, user_id)
);

SELECT * FROM USER_CONFIGURACAO WHERE IDIOMA=IDIOMA;

DELETE FROM USER_CONFIGURACAO WHERE CODIGO = 18;

INSERT INTO USER_CONFIGURACAO (USER_ID, idioma, fluencia, status) VALUES (4, 1, 1, '...');
INSERT INTO USER_CONFIGURACAO (USER_ID, idioma, fluencia, status) VALUES (100003739598747, 2, 2, '...');
INSERT INTO USER_CONFIGURACAO (USER_ID, idioma, fluencia, status) VALUES (100000568684996, 3, 4, '...');
INSERT INTO USER_CONFIGURACAO (USER_ID, idioma, fluencia, status) VALUES (100004273724604, 2, 2, '...');
INSERT INTO USER_CONFIGURACAO (USER_ID, idioma, fluencia, status) VALUES (100000165845676, 2, 3, '...');
UPDATE USER_CONFIGURACAO SET IDIOMA = 3, FLUENCIA = 4 WHERE USER_ID = 100000568684996;
DELETE FROM USER_CONFIGURACAO WHERE CODIGO = 6;
SELECT l.USER_ID, l.COORDENADA_ULTIMO_ACESSO, C.IDIOMA, C.FLUENCIA, C.STATUS FROM USER_LOGIN l INNER JOIN USER_CONFIGURACAO c ON l.USER_ID = c.USER_ID WHERE SITUACAO = 1  AND IDIOMA = IDIOMA  AND FLUENCIA = FLUENCIA  AND l.USER_ID <> '784897128313375';

---------------------------
create table USER_LOGIN(
	codigo int not null auto_increment,
	user_id varchar(30) not null,
	situacao bit(1) not null,
	data_ultimo_acesso date not null,
    coordenada_ultimo_acesso varchar(50) not null,
    primary key(codigo, user_id)
);

SELECT * FROM USER_LOGIN;
DELETE FROM USER_LOGIN WHERE CODIGO = 7;
INSERT INTO USER_LOGIN (USER_ID, SITUACAO, DATA_ULTIMO_ACESSO, coordenada_ultimo_acesso) VALUES (4, 1, '2016-04-14', '-23.5192|-46.8367'); -- Carapicuiba  --Mark
INSERT INTO USER_LOGIN (USER_ID, SITUACAO, DATA_ULTIMO_ACESSO, coordenada_ultimo_acesso) VALUES (100003739598747, 1, '2016-04-14', '-23.5192|-46.8367'); ---- Pedrosa
INSERT INTO USER_LOGIN (USER_ID, SITUACAO, DATA_ULTIMO_ACESSO, coordenada_ultimo_acesso) VALUES (100000568684996, 1, '2016-04-14', '-23.5192|-46.8367'); ---- Jeff
INSERT INTO USER_LOGIN (USER_ID, SITUACAO, DATA_ULTIMO_ACESSO, coordenada_ultimo_acesso) VALUES (100004273724604, 1, '2016-04-14', '-23.5192|-46.8367'); ---- Rodrigo
INSERT INTO USER_LOGIN (USER_ID, SITUACAO, DATA_ULTIMO_ACESSO, coordenada_ultimo_acesso) VALUES (100000165845676, 1, '2016-04-14', '-23.5192|-46.8367'); ---- Priscilla
DELETE FROM USER_LOGIN WHERE CODIGO = 12;
------------------------
DROP TABLE IDIOMA;

create table IDIOMA(
	codigo int not null auto_increment,
	descricao varchar(30) not null,
    primary key(codigo)
);

insert into IDIOMA (descricao) values ("Todos"), ("Inglês"), ("Espanhol");
SELECT * FROM IDIOMA;

ALTER TABLE IDIOMA add column descricao_en varchar(30) not null;
UPDATE IDIOMA SET DESCRICAO_EN = 'All' WHERE CODIGO = 1;
UPDATE IDIOMA SET DESCRICAO_EN = 'English' WHERE CODIGO = 2;
UPDATE IDIOMA SET DESCRICAO_EN = 'Spanish' WHERE CODIGO = 3;

ALTER TABLE IDIOMA change descricao descricao_ptbr varchar(30) not null;
--------------------------

DROP TABLE FLUENCIA;

create table FLUENCIA(
	codigo int not null auto_increment,
	descricao varchar(30) not null,
    primary key(codigo)
);

insert into FLUENCIA (descricao) values ("Todos"), ("Básico"), ("Intermediário"), ("Avançado"), ("Fluente");
SELECT * FROM FLUENCIA;

ALTER TABLE FLUENCIA add column descricao_en varchar(30) not null;
UPDATE FLUENCIA SET DESCRICAO_EN = 'All' WHERE CODIGO = 1;
UPDATE FLUENCIA SET DESCRICAO_EN = 'Basic' WHERE CODIGO = 2;
UPDATE FLUENCIA SET DESCRICAO_EN = 'Intermediate' WHERE CODIGO = 3;
UPDATE FLUENCIA SET DESCRICAO_EN = 'Advanced' WHERE CODIGO = 4;
UPDATE FLUENCIA SET DESCRICAO_EN = 'Fluent' WHERE CODIGO = 5;

ALTER TABLE FLUENCIA change descricao descricao_ptbr varchar(30) not null;
------------------------------

CREATE TABLE DISCUSSAO(
	codigo bigint not null auto_increment,
	titulo varchar(30) not null,
	descricao varchar(250) not null,
	data_hora datetime not null,
    autor varchar(30) not null,
    situacao bit(1) not null,
	primary key(codigo)
);

CREATE TABLE DISCUSSAO_RESPOSTA(
	codigo bigint not null auto_increment,
    discussao bigint not null,
    data_hora datetime not null,
    resposta varchar(250) not null,
    autor varchar(30) not null,
    primary key(codigo),
    foreign key(discussao) references DISCUSSAO(codigo)
);
------------------------------

-- Tratamento para o banco aceitar emoticons, porém, é preciso tratar também na aplicação.
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