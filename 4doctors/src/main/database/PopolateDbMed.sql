INSERT INTO doctors.Paziente VALUES ('MLNMTT96E16H816G','mattia','molinaro','email1@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'),'M','1996-05-16','San Daniele Del Fr.','Via zef,9,Forgaria Nel Friuli(UD),33030');
INSERT INTO doctors.Paziente VALUES ('GVNPVA94R44S347D', 'giovanni','piva','email4@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'), 'M', '1997-01-23' ,'Padova', 'Via Cesare Battisti, 124 (PD), 35121');
INSERT INTO doctors.Paziente VALUES ('VCNRRT96S13I775M','roberto', 'piva','email2@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'),'M', '1996-11-13', 'Soave', 'Via degli alpini, 29 (VR), 37030');
INSERT INTO doctors.Paziente VALUES ('MTTRSO97E67L781I','rosa', 'miotto','email3@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'),'F', '1998-07-23', 'Verona', 'Via degli boh, 33 (VR), 34876');
INSERT INTO doctors.Paziente VALUES ('BLLMCL94B14L736U','Marcello','Bullo','bullo.marcello@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'),'M','2021-04-08','Padova','via Sografi');
INSERT INTO doctors.Paziente VALUES ('SCCWLM97P23A001U', 'wiliam', 'scicchitano','wiliam.scicchitano@gmail.com', MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'), 'M', '1997-09-23' ,'Abano Terme', 'Via Trespole, 124 (PD), 35121');

INSERT INTO doctors.Medico VALUES ('RSSNDR97A21H347Q', 'andrea', 'rossi','andrea.rossi@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'), 'M', '1965-06-11', 'Venezia', 'A34VE', 'Via dei medici, 118 (VE), 34761');
INSERT INTO doctors.Medico VALUES ('BNCGLA94B14S617D', 'giulia', 'bianchi','giulia.bianchi@gmail.com',MD5('1e2e9fc2002b002d75198b7503210c05a1baac4560916a3c6d93bcce3a50d7f00fd395bf1647b9abb8d1afcc9c76c289b0c9383ba386a956da4b38934417789e'), 'F', '1979-04-06', 'Milano', 'A34VE', 'Via del corto, 143 (VE), 35676');

INSERT INTO doctors.Admin VALUES ('admin', 'Nicola', 'Levorato', 'nicola.levorato.2@studenti.unipd.it', MD5('a6818b8188b36c44d17784c5551f63accc5deaf8786f9d0ad1ae3cd8d887cbab4f777286dbb315fb14854c8774dc0d10b5567e4a705536cc2a1d61ec0a16a7a6'));

INSERT INTO doctors.Farmaco VALUES ('1322AB0001', 'Tachipirina', 'OTC', 'Angelini', 'gocce');
INSERT INTO doctors.Farmaco VALUES ('234DF21345', 'Broncovaleas', 'SOP', 'Valeant', 'compresse 100mg');
INSERT INTO doctors.Farmaco VALUES ('JS7EH284HD', 'Acamba', 'ETICI', 'Sooft Italia S.p.a.', 'Antiglaucoma');
INSERT INTO doctors.Farmaco VALUES ('H2E7G45KN9', 'Cabaser', 'ETICI', 'Pfizer', 'Antiparkinson, inibitori della prolattina');
INSERT INTO doctors.Farmaco VALUES ('W5EGH729KF', 'Tachidol', 'ETICI', 'Angelini', 'Antipiretici, analgesici FANS');

INSERT INTO doctors.Esame VALUES ('H01', 'Esame del sangue');
INSERT INTO doctors.Esame VALUES ('END1', 'Laringoscopia');
INSERT INTO doctors.Esame VALUES ('END2', 'Broncoscopia');
INSERT INTO doctors.Esame VALUES ('END3', 'Esofagoscopia');
INSERT INTO doctors.Esame VALUES ('END4', 'Gastroscopia');
INSERT INTO doctors.Esame VALUES ('END5', 'Endroscopia gastroduodenale');
INSERT INTO doctors.Esame VALUES ('END6', 'Colonscopia');
INSERT INTO doctors.Esame VALUES ('END7', 'Citoscopia');
INSERT INTO doctors.Esame VALUES ('END8', 'Isteroscopia');
INSERT INTO doctors.Esame VALUES ('END9', 'Artroscopia');
INSERT INTO doctors.Esame VALUES ('END10', 'Laparoscopia');
INSERT INTO doctors.Esame VALUES ('END11', 'Mediastinoscopia');
INSERT INTO doctors.Esame VALUES ('END12', 'Torascopia');
INSERT INTO doctors.Esame VALUES ('ECG1', 'Elettrocardiografia');
INSERT INTO doctors.Esame VALUES ('EEG', 'Elettroencefalografia');
INSERT INTO doctors.Esame VALUES ('BPS1', 'Biopsia');
INSERT INTO doctors.Esame VALUES ('RADGF1', 'Radiografia');
INSERT INTO doctors.Esame VALUES ('ECOGF1', 'Ecografia');
INSERT INTO doctors.Esame VALUES ('SCIGF1', 'Scintigrafia');
INSERT INTO doctors.Esame VALUES ('TC1', 'Tomografia Computerizzata');
INSERT INTO doctors.Esame VALUES ('RMI1', 'Risonanza magnetica immagini');
INSERT INTO doctors.Esame VALUES ('PET1', 'Tomografia emissione positroni');
INSERT INTO doctors.Esame VALUES ('ANGGF1', 'Angiografia');

INSERT INTO doctors.Ricetta VALUES ('19fbdf8d-f8fd-40ee-8be2-93e21e7b4f8e', 'BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2021-04-21', 'Gastroscopia per certificazione intolleranze', 1, 'ESAME', 'PENDING');
INSERT INTO doctors.Ricetta VALUES ('cbc4b4db-65bd-40d4-8b22-b6a1f0368375', 'BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2021-04-26', 'Radiografia al ginocchio', 1, 'ESAME', 'APPROVED');
INSERT INTO doctors.Ricetta VALUES ('b1a43014-c890-4ac3-9b47-af6e3b40b1bd', 'RSSNDR97A21H347Q', 'MLNMTT96E16H816G', '2021-03-09', 'Tachipirina gocce', 3, 'FARMACO', 'APPROVED' );
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2018-04-21', 'Gastroscopia per certificazione intolleranze', 1, 'ESAME', 'PENDING');
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('RSSNDR97A21H347Q', 'MLNMTT96E16H816G', '2019-05-09', 'Tachipirina gocce', 3, 'FARMACO', 'PENDING');
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('RSSNDR97A21H347Q', 'MLNMTT96E16H816G', '2021-04-07', 'Okitask', 1, 'FARMACO', 'PENDING');
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2021-04-06', 'Okitask', 1, 'FARMACO', 'REJECTED');
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('BNCGLA94B14S617D', 'GVNPVA94R44S347D', '2021-03-12', 'Tachifludec', 1, 'FARMACO', 'APPROVED');
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('BNCGLA94B14S617D', 'GVNPVA94R44S347D', '2021-01-07', 'Tachifludec', 1, 'FARMACO', 'PENDING');
INSERT INTO doctors.Ricetta (medico, paziente, data, descrizione, numeroprestazioni, tipo, status) VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2021-02-25', 'Stiproxal', 5, 'FARMACO', 'PENDING');

INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'MLNMTT96E16H816G', '2018-12-12', '17:00' , 'Probabile mononucleosi');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2019-05-30', '16:30' , 'tosse');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2019-10-05', '12:00' , 'descrizione');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2020-05-12', '10:30' , 'descrizione');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2020-12-01', '10:00' , 'descrizione');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'MLNMTT96E16H816G', '2021-02-21', '10:00' , 'descrizione');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2021-03-20', '17:00' , 'descrizione');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'MLNMTT96E16H816G', '2021-07-12', '15:30' , '--');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'MLNMTT96E16H816G', '2021-12-02', '11:30' , '--');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'GVNPVA94R44S347D', '2017-06-22', '11:30' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('BNCGLA94B14S617D', 'GVNPVA94R44S347D', '2018-10-10', '10:00' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2019-04-21', '16:30' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2019-08-12', '15:30' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2019-11-26', '10:00' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2020-09-06', '09:00' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2021-02-16', '17:30' , 'Pressione stabile');
INSERT INTO doctors.Visita VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', '2021-10-01', '15:00' , '--');

INSERT INTO doctors.FarmaciRicetta (farmaco, ricetta, qta) VALUES ('1322AB0001', 'b1a43014-c890-4ac3-9b47-af6e3b40b1bd', 2);

INSERT INTO doctors.RicettaEsame (esame, ricetta) VALUES ('END4', '19fbdf8d-f8fd-40ee-8be2-93e21e7b4f8e');
INSERT INTO doctors.RicettaEsame (esame, ricetta, data) VALUES ('RADGF1', 'cbc4b4db-65bd-40d4-8b22-b6a1f0368375', '2018-05-21');

INSERT INTO doctors.Segue VALUES ('RSSNDR97A21H347Q', 'GVNPVA94R44S347D', true);
INSERT INTO doctors.Segue VALUES ('BNCGLA94B14S617D', 'VCNRRT96S13I775M', true);
INSERT INTO doctors.Segue VALUES ('RSSNDR97A21H347Q', 'VCNRRT96S13I775M', true);