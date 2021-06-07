--4Doctors database SQL schema ---

DROP SCHEMA IF EXISTS Doctors CASCADE;
create schema Doctors;

DROP TYPE IF EXISTS GENDER CASCADE;
DROP TYPE IF EXISTS TIPOFARMACO CASCADE;
DROP TYPE IF EXISTS STATUS CASCADE;
DROP TYPE IF EXISTS TIPORICETTA CASCADE;

CREATE TYPE GENDER AS  ENUM ('M', 'F');
CREATE TYPE TIPOFARMACO AS  ENUM ('ETICI', 'OTC', 'SOP');
CREATE TYPE STATUS AS ENUM ('PENDING', 'REJECTED', 'APPROVED');
CREATE TYPE TIPORICETTA AS ENUM ('FARMACO', 'ESAME');

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-------------------------

CREATE TABLE doctors.Paziente (
  cf VARCHAR(16) PRIMARY KEY CHECK (char_length(cf)=16),
  nome VARCHAR(30) NOT NULL,
  cognome VARCHAR(30) NOT NULL,
  email TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL,
  sesso GENDER NOT NULL,
  datanascita DATE NOT NULL,
  luogonascita VARCHAR(50) NOT NULL, --- nome della città (sigla provincia/nazione estera)
  indirizzoresidenza VARCHAR(50) --- via/piazza , nr.civ, Città (PROV.), cap
);

CREATE TABLE doctors.Medico (
  cf VARCHAR(16) PRIMARY KEY CHECK (char_length(cf)=16),
  nome VARCHAR(30) NOT NULL,
  cognome VARCHAR(30) NOT NULL,
  email TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL,
  sesso GENDER NOT NULL,
  datanascita DATE NOT NULL,
  luogonascita VARCHAR(50) NOT NULL, --- nome della città (sigla provincia/nazione estera)
  CodiceASL VARCHAR(30) NOT NULL, --- nome della città (sigla provincia)
  indirizzoresidenza VARCHAR(50) --- via/piazza , nr.civ, Città(PROV.), cap
);

CREATE TABLE doctors.Admin (
  username VARCHAR(30) PRIMARY KEY,
  nome VARCHAR(30) NOT NULL,
  cognome VARCHAR(30) NOT NULL,
  email TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL
);

CREATE TABLE doctors.Farmaco (
  codice VARCHAR(16) PRIMARY KEY,
  nome VARCHAR(30) NOT NULL,
  classe TIPOFARMACO NOT NULL,
  azienda VARCHAR(30),
  descrizione VARCHAR(200) NOT NULL
);

CREATE TABLE doctors.Esame (
  codice VARCHAR(16) PRIMARY KEY,
  nome VARCHAR(30)
);

CREATE TABLE doctors.Ricetta (
  id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
  medico VARCHAR(16) NOT NULL,
  paziente VARCHAR(16) NOT NULL,
  data DATE NOT NULL,
  descrizione VARCHAR(200) NOT NULL,
  numeroprestazioni INT,
  tipo TIPORICETTA NOT NULL,
  status STATUS NOT NULL,
  FOREIGN KEY (medico) REFERENCES doctors.Medico(cf) ON DELETE CASCADE ON UPDATE CASCADE ,
  FOREIGN KEY (paziente) REFERENCES doctors.Paziente(cf) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE doctors.Visita (
  medico VARCHAR(16) NOT NULL,
  paziente VARCHAR(16) NOT NULL,
  data DATE NOT NULL,
  ora TIME NOT NULL,
  esito TEXT,
  PRIMARY KEY (medico, data, ora),
  FOREIGN KEY (medico) REFERENCES doctors.Medico(cf) ON DELETE CASCADE ON UPDATE CASCADE ,
  FOREIGN KEY (paziente) REFERENCES doctors.Paziente(cf) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE doctors.Segue (
  medico VARCHAR(16) NOT NULL,
  paziente VARCHAR(16) NOT NULL,
  attivo BOOLEAN,
  PRIMARY KEY (medico, paziente),
  FOREIGN KEY (medico) REFERENCES doctors.Medico(cf) ON DELETE CASCADE ON UPDATE CASCADE ,
  FOREIGN KEY (paziente) REFERENCES doctors.Paziente(cf) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE doctors.FarmaciRicetta(
  farmaco VARCHAR(16) NOT NULL,
  ricetta uuid NOT NULL,
  qta INT NOT NULL,
  PRIMARY KEY (farmaco,ricetta),
  FOREIGN KEY (farmaco) REFERENCES doctors.Farmaco(codice) ON DELETE CASCADE ON UPDATE CASCADE ,
  FOREIGN KEY (ricetta) REFERENCES doctors.Ricetta(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE doctors.RicettaEsame(
  esame VARCHAR(16) NOT NULL,
  ricetta uuid NOT NULL,
  data DATE,
  esito VARCHAR(300),
  PRIMARY KEY (ricetta, esame),
  FOREIGN KEY (ricetta) REFERENCES doctors.Ricetta(id) ON DELETE CASCADE ON UPDATE CASCADE ,
  FOREIGN KEY (esame) REFERENCES doctors.Esame(codice) ON DELETE CASCADE ON UPDATE CASCADE
);