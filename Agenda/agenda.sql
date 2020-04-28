DROP DATABASE if EXISTS agenda;
CREATE DATABASE agenda CHARSET latin1 COLLATE latin1_general_cs;
USE agenda;

CREATE TABLE usuario(
	id_user INT PRIMARY KEY AUTO_INCREMENT,
	login VARCHAR(30) NOT NULL,
	senha VARCHAR(20) NOT NULL
);

CREATE TABLE contato(
	id_contato INT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	fone VARCHAR(15) NOT NULL,
	id_usuario INT NOT NULL,
	FOREIGN KEY (id_usuario) REFERENCES usuario(id_user)
);

CREATE TABLE compromisso(
	id_compromisso INT PRIMARY KEY AUTO_INCREMENT,
	data_hora TIMESTAMP NOT NULL,
	local_compromisso VARCHAR(50) NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	id_usuario INT NOT NULL,
	FOREIGN KEY (id_usuario) REFERENCES usuario(id_user)
);

CREATE TABLE participante(
	id_compromisso INT NOT NULL,
	FOREIGN KEY (id_compromisso) REFERENCES compromisso(id_compromisso),
	id_contato INT NOT NULL,
	FOREIGN KEY (id_contato) REFERENCES contato(id_contato),
	PRIMARY KEY (id_compromisso, id_contato)
);