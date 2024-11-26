DROP DATABASE IF EXISTS sprdrp;

CREATE DATABASE sprdrp;

USE sprdrp;

/*
*************
CREATE
***************
*/

CREATE TABLE Users(
	Id_Users INT AUTO_INCREMENT,
	firstname VARCHAR(50),
	lastname VARCHAR(50) NOT NULL,
	mail VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	areacode VARCHAR(15) NOT NULL,
	city VARCHAR(50) NOT NULL,
	phone VARCHAR(15),
	PRIMARY KEY(Id_Users)
);

CREATE TABLE Speciality(
	Id_Speciality INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY(Id_Speciality),
	UNIQUE(name)
);

CREATE TABLE MedicamentCategory(
	Id_MedicamentCategory INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	PRIMARY KEY(Id_MedicamentCategory),
	UNIQUE(name)
);

CREATE TABLE State(
	Id_State INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	code VARCHAR(4) NOT NULL,
	PRIMARY KEY(Id_State),
	UNIQUE(name),
	UNIQUE(code)
);

CREATE TABLE Doctor(
	Id_Doctor INT AUTO_INCREMENT,
	registrationnb VARCHAR(10) NOT NULL,
	Id_Users INT NOT NULL,
	PRIMARY KEY(Id_Doctor),
	UNIQUE(Id_Users),
	UNIQUE(registrationnb),
	FOREIGN KEY(Id_Users) REFERENCES Users(Id_Users)
);

CREATE TABLE HealthMutual(
	Id_HealthMutual INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	areacode VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL,
	phone VARCHAR(15) NOT NULL,
	mail VARCHAR(70) NOT NULL,
	rate DECIMAL(15,2),
	Id_State INT NOT NULL,
	PRIMARY KEY(Id_HealthMutual),
	UNIQUE(name),
	FOREIGN KEY(Id_State) REFERENCES State(Id_State)
);

CREATE TABLE Medicament(
	Id_Medicament INT AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	date_first VARCHAR(50) NOT NULL,
	price VARCHAR(50) NOT NULL,
	needprescription BOOLEAN NOT NULL,
	Id_MedicamentCategory INT NOT NULL,
	PRIMARY KEY(Id_Medicament),
	UNIQUE(name),
	FOREIGN KEY(Id_MedicamentCategory) REFERENCES MedicamentCategory(Id_MedicamentCategory)
);

CREATE TABLE DoctorGeneral(
	Id_DoctorGeneral INT AUTO_INCREMENT,
	Id_Doctor INT NOT NULL,
	PRIMARY KEY(Id_DoctorGeneral),
	UNIQUE(Id_Doctor),
	FOREIGN KEY(Id_Doctor) REFERENCES Doctor(Id_Doctor) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE DoctorSpecialized(
	Id_DoctorSpecialized INT AUTO_INCREMENT,
	Id_Speciality INT NOT NULL,
	Id_Doctor INT NOT NULL,
	PRIMARY KEY(Id_DoctorSpecialized),
	UNIQUE(Id_Doctor),
	FOREIGN KEY(Id_Speciality) REFERENCES Speciality(Id_Speciality),
	FOREIGN KEY(Id_Doctor) REFERENCES Doctor(Id_Doctor) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Stock(
	Id_Stock INT AUTO_INCREMENT,
	qty INT NOT NULL,
	Id_Medicament INT NOT NULL,
	PRIMARY KEY(Id_Stock),
	UNIQUE(Id_Medicament),
	FOREIGN KEY(Id_Medicament) REFERENCES Medicament(Id_Medicament)
);

CREATE TABLE Patient(
	Id_Patient INT AUTO_INCREMENT,
	secuid VARCHAR(50) NOT NULL,
	birthdate DATE,
	Id_DoctorGeneral INT,
	Id_HealthMutual INT,
	Id_Users INT NOT NULL,
	PRIMARY KEY(Id_Patient),
	UNIQUE(Id_Users),
	UNIQUE(secuid),
	FOREIGN KEY(Id_DoctorGeneral) REFERENCES DoctorGeneral(Id_DoctorGeneral),
	FOREIGN KEY(Id_HealthMutual) REFERENCES HealthMutual(Id_HealthMutual),
	FOREIGN KEY(Id_Users) REFERENCES Users(Id_Users)
);

CREATE TABLE Prescription(
	Id_Prescription INT AUTO_INCREMENT,
	date_prescription DATE NOT NULL,
	Id_Patient INT NOT NULL,
	Id_Doctor INT NOT NULL,
	PRIMARY KEY(Id_Prescription),
	FOREIGN KEY(Id_Patient) REFERENCES Patient(Id_Patient),
	FOREIGN KEY(Id_Doctor) REFERENCES Doctor(Id_Doctor)
);

CREATE TABLE Purchase(
	Id_Purchase INT AUTO_INCREMENT,
	price DECIMAL(15,2),
	price_mutual DECIMAL(15,2),
	date_buy DATE NOT NULL,
	purchase_ref VARCHAR(50) NOT NULL,
	is_paid BOOLEAN NOT NULL,
	Id_Prescription INT,
	PRIMARY KEY(Id_Purchase),
	UNIQUE(Id_Prescription),
	UNIQUE(purchase_ref),
	FOREIGN KEY(Id_Prescription) REFERENCES Prescription(Id_Prescription)
);

CREATE TABLE Prescription_line(
	Id_Prescription INT,
	Id_Medicament INT,
	qty INT,
	PRIMARY KEY(Id_Prescription, Id_Medicament),
	FOREIGN KEY(Id_Prescription) REFERENCES Prescription(Id_Prescription),
	FOREIGN KEY(Id_Medicament) REFERENCES Medicament(Id_Medicament)
);

CREATE TABLE Purchase_item(
	Id_Medicament INT,
	Id_Purchase INT,
	qty INT NOT NULL,
	unit_price DECIMAL(15,2) NOT NULL,
	PRIMARY KEY(Id_Medicament, Id_Purchase),
	FOREIGN KEY(Id_Medicament) REFERENCES Medicament(Id_Medicament),
	FOREIGN KEY(Id_Purchase) REFERENCES Purchase(Id_Purchase)
);

/*
***********
ALTER
***********
*/

ALTER TABLE Purchase
    MODIFY  purchase_ref VARCHAR(50) NOT NULL DEFAULT (UUID()),
    MODIFY  date_buy DATETIME NOT NULL DEFAULT NOW()
;


ALTER TABLE Purchase_item
	DROP FOREIGN KEY purchase_item_ibfk_1 ,
    DROP FOREIGN KEY purchase_item_ibfk_2,
	DROP PRIMARY KEY,  
    ADD  id_purchase_item INT AUTO_INCREMENT PRIMARY KEY,
	ADD FOREIGN KEY(Id_Medicament) REFERENCES Medicament(Id_Medicament),
    ADD FOREIGN KEY(Id_Purchase) REFERENCES Purchase(Id_Purchase)
;

ALTER TABLE Prescription_line
	DROP FOREIGN KEY prescription_line_ibfk_1 ,
    DROP FOREIGN KEY prescription_line_ibfk_2,
	DROP PRIMARY KEY,  
    ADD  id_prescription_line INT AUTO_INCREMENT PRIMARY KEY,
	ADD FOREIGN KEY(Id_Medicament) REFERENCES Medicament(Id_Medicament),
    ADD FOREIGN KEY(Id_Prescription) REFERENCES Prescription(Id_Prescription)
;

/*
***********
TRIGGER
***********
*/

DELIMITER |
CREATE TRIGGER del_purchase BEFORE DELETE ON Purchase
    FOR EACH ROW
BEGIN
    DELETE FROM purchase_item WHERE id_purchase = OLD.id_purchase ;
END |
DELIMITER ;


DELIMITER |
CREATE TRIGGER del_doctor BEFORE DELETE ON Doctor
    FOR EACH ROW
BEGIN
    DELETE FROM Prescription WHERE id_doctor = OLD.id_doctor;
END |
DELIMITER ;

DELIMITER |
CREATE TRIGGER del_prescription BEFORE DELETE ON Prescription
    FOR EACH ROW
BEGIN
    DELETE FROM prescription_line WHERE id_prescription = OLD.id_prescription;
END |
DELIMITER ;


/*
*************
INSERT
***************
*/
INSERT INTO State(name, code) VALUES
	('Ain', '01'),
	('Aisne', '02'),
	('Allier', '03'),
	('Alpes-de-Haute-Provence', '04'),
	('Hautes-Alpes', '05'),
	('Alpes-Maritimes', '06'),
	('Ardèche', '07'),
	('Ardennes', '08'),
	('Ariège', '09'),
	('Aube', '10'),
	('Aude', '11'),
	('Aveyron', '12'),
	('Bouches-du-Rhône', '13'),
	('Calvados', '14'),
	('Cantal', '15'),
	('Charente', '16'),
	('Charente-Maritime', '17'),
	('Cher', '18'),
	('Corrèze', '19'),
	('Côte-d\'Or', '21'),
	('Côtes-d\'Armor', '22'),
	('Creuse', '23'),
	('Dordogne', '24'),
	('Doubs', '25'),
	('Drôme', '26'),
	('Eure', '27'),
	('Eure-et-Loir', '28'),
	('Finistère', '29'),
	('Gard', '30'),
	('Haute-Garonne', '31'),
	('Gers', '32'),
	('Gironde', '33'),
	('Hérault', '34'),
	('Ille-et-Vilaine', '35'),
	('Indre', '36'),
	('Indre-et-Loire', '37'),
	('Isère', '38'),
	('Jura', '39'),
	('Landes', '40'),
	('Loir-et-Cher', '41'),
	('Loire', '42'),
	('Haute-Loire', '43'),
	('Loire-Atlantique', '44'),
	('Loiret', '45'),
	('Lot', '46'),
	('Lot-et-Garonne', '47'),
	('Lozère', '48'),
	('Maine-et-Loire', '49'),
	('Manche', '50'),
	('Marne', '51'),
	('Haute-Marne', '52'),
	('Mayenne', '53'),
	('Meurthe-et-Moselle', '54'),
	('Meuse', '55'),
	('Morbihan', '56'),
	('Moselle', '57'),
	('Nièvre', '58'),
	('Nord', '59'),
	('Oise', '60'),
	('Orne', '61'),
	('Pas-de-Calais', '62'),
	('Puy-de-Dôme', '63'),
	('Pyrénées-Atlantiques', '64'),
	('Hautes-Pyrénées', '65'),
	('Pyrénées-Orientales', '66'),
	('Bas-Rhin', '67'),
	('Haut-Rhin', '68'),
	('Rhône', '69'),
	('Haute-Saône', '70'),
	('Saône-et-Loire', '71'),
	('Sarthe', '72'),
	('Savoie', '73'),
	('Haute-Savoie', '74'),
	('Paris', '75'),
	('Seine-Maritime', '76'),
	('Seine-et-Marne', '77'),
	('Yvelines', '78'),
	('Deux-Sèvres', '79'),
	('Somme', '80'),
	('Tarn', '81'),
	('Tarn-et-Garonne', '82'),
	('Var', '83'),
	('Vaucluse', '84'),
	('Vendée', '85'),
	('Vienne', '86'),
	('Haute-Vienne', '87'),
	('Vosges', '88'),
	('Yonne', '89'),
	('Territoire de Belfort', '90'),
	('Essonne', '91'),
	('Hauts-de-Seine', '92'),
	('Seine-Saint-Denis', '93'),
	('Val-de-Marne', '94'),
	('Val-d\'Oise', '95'),
	('Guadeloupe', '971'),
	('Martinique', '972'),
	('Guyane', '973'),
	('La Réunion', '974'),
	('Mayotte', '976')
;



INSERT INTO MedicamentCategory(name) VALUES
	('Analgésiques'),('Antibiotiques'),('Antituberculeux'),('Antimycosiques'),('Antiviraux'),('Antihistaminiques'),('Antipyrétiques'),('Antispasmodiques'),('Cardiologie'),('Dermatologie'),('Endocrinologie'),('Gastro-entérologie'),('Hématologie'),('Neurologie'),('Oncologie'),('Psychiatrie'),('Rhumatologie'),('Urologie')
;


INSERT INTO HealthMutual(name, address, areacode, city, phone, mail, rate,id_state) VALUES
	('Acoris Mutuelles', '123 Rue de Paris', '75001', 'Paris', '0123456789', 'contact@acoris.fr', 15.99,75),
	('ADREA Mutuelle', '456 Avenue de Lyon', '69001', 'Lyon', '0456789012', 'info@adrea.fr', 12.50,69),
	('APREVA', '789 Boulevard de Marseille', '13001', 'Marseille', '0412345678', 'support@apreva.fr', 10.00,13),
	('Avenir Mutuelle', '321 Route de Nice', '06000', 'Nice', '0498765432', 'contact@avenir.fr', 14.75,6),
	('Avenir Santé Mutuelle', '654 Chemin de Toulouse', '31000', 'Toulouse', '0512345678', 'info@avenir-sante.fr', 11.25,31),
	('CCMO', '987 Rue de Lille', '59000', 'Lille', '0345678901', 'contact@ccmo.fr', 13.50,59),
	('France Mutuelle', '159 Avenue de Bordeaux', '33000', 'Bordeaux', '0567890123', 'info@france-mutuelle.fr', 9.99,33),
	('GFP', '753 Boulevard de Nantes', '44000', 'Nantes', '0234567890', 'support@gfp.fr', 8.50,44),
	('Harmonie Mutuelle', '852 Rue de Strasbourg', '67000', 'Strasbourg', '0321436587', 'contact@harmonie.fr', 16.00,67)
;


INSERT INTO Users(firstname, lastname, mail, address, areacode, city, phone) VALUES
	('Jean', 'Dupont', 'jean.dupont@example.com', '10 rue de la Paix', '75002', 'Paris', '0123456789'),
	('Marie', 'Curie', 'marie.curie@example.com', '15 avenue des Champs-Élysées', '75008', 'Paris', '0198765432'),
	('Pierre', 'Martin', 'pierre.martin@example.com', '20 boulevard Saint-Germain', '75005', 'Paris', '0112345678'),
	('Sophie', 'Durand', 'sophie.durand@example.com', '5 rue de Rivoli', '75001', 'Paris', '0134567890'),
	('Luc', 'Leroy', 'luc.leroy@example.com', '12 rue de la République', '69002', 'Lyon', '0412345678'),
	('Claire', 'Bernard', 'claire.bernard@example.com', '8 rue de la Liberté', '13001', 'Marseille', '0456787012'),
	('Julien', 'Moreau', 'julien.moreau@example.com', '25 avenue de la Gare', '59000', 'Lille', '0312345678'),
	('Emma', 'Garnier', 'emma.garnier@example.com', '30 rue des Fleurs', '44000', 'Nantes', '0212345678'),
	('Thomas', 'Rousseau', 'thomas.rousseau@example.com', '18 rue du Faubourg', '75010', 'Paris', '0123456790'),
	('Alice', 'Boucher', 'alice.boucher@example.com', '22 rue de l’Église', '31000', 'Toulouse', '0512345678'),
	('Antoine', 'Lemoine', 'antoine.lemoine@example.com', '14 rue de la Mer', '06000', 'Nice', '0493123456'),
	('Chloé', 'Fournier', 'chloe.fournier@example.com', '9 rue de la Montagne', '67000', 'Strasbourg', '0388123456'),
	('Nicolas', 'Giraud', 'nicolas.giraud@example.com', '11 rue des Écoles', '75005', 'Paris', '0145678901'),
	('Laura', 'Pichon', 'laura.pichon@example.com', '7 rue de la Paix', '75002', 'Paris', '0123456789'),
	('Victor', 'Lemoine', 'victor.lemoine@example.com', '3 rue de la Liberté', '75003', 'Paris', '0134567890'),
	('Camille', 'Renaud', 'camille.renaud@example.com', '4 rue des Jardins', '69001', 'Lyon', '0478901234'),
	('Gabriel', 'Blanc', 'gabriel.blanc@example.com', '6 avenue de la République', '75011', 'Paris', '0123456788'),
	('Léa', 'Collet', 'lea.collet@example.com', '2 rue de la Gare', '59000', 'Lille', '0320304050'),
	('Maxime', 'Leroux', 'maxime.leroux@example.com', '13 rue de la Mer', '06000', 'Nice', '0493456789'),
	('Inès', 'Meyer', 'ines.meyer@example.com', '17 rue des Acacias', '75017', 'Paris', '0156789012'),
	('Louis', 'Gauthier', 'louis.gauthier@example.com', '19 rue de la Paix', '75002', 'Paris', '0123456701'),
	('Zoé', 'Bourgeois', 'zoe.bourgeois@example.com', '21 rue des Lilas', '75012', 'Paris', '0134567890'),
	('Paul', 'Lemoine', 'paul.lemoine@example.com', '23 rue de la Liberté', '75003', 'Paris', '0145678901'),
	('Juliette', 'Garnier', 'juliette.garnier@example.com', '26 rue des Fleurs', '44000', 'Nantes', '0242345678'),
	('Théo', 'Roussel', 'theo.roussel@example.com', '28 rue de la Mer', '06000', 'Nice', '0493123456'),
	('Clara', 'Leroy', 'clara.leroy@example.com', '29 rue de la Montagne', '67000', 'Strasbourg', '0388123456'),
	('Simon', 'Boucher', 'simon.boucher@example.com', '31 rue de la Paix', '75002', 'Paris', '0123456789'),
	('Alice', 'Bernard', 'alice.bernard@example.com', '32 avenue des Champs-Élysées', '75008', 'Paris', '0198765432')
;



INSERT INTO Speciality(name) VALUES
	('Andrologie'),('Urologie'),('Cardiologie'),('Gynécologie'),('Obstétrique'),('Pédiatrie'),
	('Otorhinolaryngologie'),('Neurologie'),('Dermatologie'),('Gastro-entérologie'),('Rhumatologie'),('Néphrologie'),
	('Hématologie'),('Ophtalmologie'),('Pneumologie'),('Psychiatrie')
;



INSERT INTO Doctor(registrationnb,id_users) VALUES
	(1271206374,1),
	(1882760707,2),
	(1008289227,3),
	(9370922170,4),
	(4103421737,5),
	(8811463468,6),
	(6666016758,7),
	(1512414614,8),
	(4988382360,9),
	(2427141906,10)
;


INSERT INTO DoctorGeneral(Id_Doctor) VALUES
	(1),
	(2),
	(3),
	(4),
	(5)
;

INSERT INTO DoctorSpecialized(Id_Doctor,Id_speciality) VALUES
	(6,1),
	(7,2),
	(8,3),
	(9,4),
	(10,5)
;

INSERT INTO Patient
(
    `secuid`,
    `birthdate`,
    `Id_DoctorGeneral`,
    `Id_HealthMutual`,
    `Id_Users`)
VALUES
	('1851234567899','1985-06-15',1,3,10),
	('1902345678124','1990-02-20',2,5,11),
	('1783456789010','1978-11-30',3,1,12),
	('1004567890234','2000-04-10',4,6,13),
	('1955678901342','1995-08-25',5,2,14),
	('1826789012452','1982-12-05',1,4,15),
	('1937890123566','1993-03-18',2,7,16),
	('1888901234674','1988-09-22',3,8,17),
	('1759012345782','1975-01-01',4,3,18),
	('1990123456894','1999-07-14',5,5,19)
;


INSERT INTO Medicament
(`name`,
 `date_first`,
 `price`,
 `needPrescription`,
 `Id_MedicamentCategory`)
VALUES
	('Paracétamol', '2000-01-01', 2.50, 0, 1),
	('Ibuprofène', '2005-05-15', 3.00, 0, 2),
	('Amoxicilline', '2010-03-20', 10.00, 1, 3),
	('Aspirine', '1995-07-30', 1.50, 0, 4),
	('Loratadine', '2012-11-10', 4.00, 0, 5),
	('Oméprazole', '2008-09-05', 8.50, 1, 6),
	('Metformine', '2015-02-14', 12.00, 1, 7),
	('Atorvastatine', '2013-06-25', 15.00, 1, 8),
	('Simvastatine', '2011-04-18', 14.00, 1, 9),
	('Cétirizine', '2014-08-22', 5.00, 0, 10),
	('Fluoxetine', '2009-12-01', 20.00, 1, 11),
	('Clopidogrel', '2016-10-30', 18.00, 1, 12),
	('Salbutamol', '2007-03-15', 7.00, 0, 13),
	('Naproxène', '2018-01-10', 9.00, 1, 14),
	('Dexaméthasone', '2019-05-20', 11.00, 1, 15)
;

INSERT INTO Stock
(`qty`,
 `Id_Medicament`)
VALUES
    (50,1),
    (50,2),
    (50,3),
    (50,4),
    (50,5),
    (50,6),
    (50,7),
    (50,8),
    (50,9),
    (50,10),
    (50,11),
    (50,12),
    (50,13),
    (50,14),
    (50,15)
;

INSERT INTO Prescription(date_prescription, id_patient, id_doctor) VALUES
	('2020-11-21', 1, 1),
	('2021-01-15', 2, 2),
	('2021-03-10', 3, 3),
	('2021-05-05', 4, 4),
	('2021-07-20', 5, 5),
	('2022-02-14', 1, 6),
	('2022-04-18', 2, 7),
	('2022-06-30', 3, 8),
	('2022-09-12', 4, 9),
	('2022-11-25', 5, 10),
	('2023-01-05', 1, 6),
	('2023-03-15', 2, 7),
	('2023-05-22', 3, 8),
	('2023-08-10', 4, 9),
	('2023-10-30', 5, 10),
	('2024-01-12', 6, 6),
	('2024-03-20', 7, 7),
	('2024-06-15', 8, 8),
	('2024-09-05', 9, 9),
	('2024-11-19', 10, 10)
;


INSERT INTO Prescription_line (id_prescription,id_medicament,qty)
VALUES
    (10,3,5),
    (9,6,4),
    (14,8,3),
    (11,3,2),
    (3,15,5),
    (16,2,4),
    (17,3,6),
    (6,10,6),
    (11,12,4),
    (5,2,3),
    (14,9,2),
    (6,9,3),
    (17,1,3),
    (6,2,1),
    (16,7,5),
    (8,10,4),
    (8,13,3),
    (14,14,4),
    (16,4,3),
    (13,4,3),
    (12,5,5),
    (2,10,4),
    (4,13,3),
    (1,14,4),
    (4,4,3),
    (3,5,5),
    (7,10,4),
    (7,13,3),
    (6,14,4),
    (7,4,3),
    (7,5,5),
    (18,10,4),
    (15,13,3),
    (19,14,4),
    (20,4,3),
    (19,5,5)
;








