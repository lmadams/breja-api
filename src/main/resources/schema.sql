CREATE TABLE cerveja
(
  id           INTEGER      NOT NULL AUTO_INCREMENT,
  nome         VARCHAR(255) NOT NULL UNIQUE,
  temp_inicial INTEGER      NOT NULL,
  temp_final   INTEGER      NOT NULL,
  PRIMARY KEY (id)
);