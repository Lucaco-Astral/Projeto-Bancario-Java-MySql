CREATE TABLE usuario (
  id_user int(11) NOT NULL AUTO_INCREMENT,
  email_user varchar(100) NOT NULL,
  nome_user varchar(100) NOT NULL,
  senha_user varchar(50) NOT NULL,
  img_user longblob NOT NULL,
  adm boolean NOT NULL
)

CREATE TABLE contaCorrente (
  id_corrente int(11) NOT NULL AUTO_INCREMENT,
  id_user int(11) NOT NULL,
  limiteEspecial double NOT NULL,
  saldo double NOT NULL
  FOREIGN KEY (id_user)
  REFERENCES usuario (id_user)
)

CREATE TABLE contaPoupanca (
  id_poupanca int(11) NOT NULL,
  id_user int(11) NOT NULL,
  reajusteMensal double NOT NULL,
  saldo double NOT NULL,
  FOREIGN KEY (id_user)
  REFERENCES usuario(id_user)
)