-- phpMyAdmin SQL Dump
-- version 4.5.4.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 03 Décembre 2023 à 18:02
-- Version du serveur :  5.7.11
-- Version de PHP :  5.6.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `project_java_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `education`
--

CREATE TABLE `education` (
  `educationID` int(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `promo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `education`
--

INSERT INTO `education` (`educationID`, `name`, `promo`) VALUES
(1, 'MIAGE-ID', 'Initial'),
(2, 'MIAGE-IF', 'Alternance'),
(3, 'MIAGE-SITN', 'Initial'),
(4, 'MODO', 'Initial'),
(5, 'IASD', 'Continue'),
(6, 'MIAGE-ID', 'Alternance'),
(7, 'MIAGE-SITN', 'Alternance'),
(8, 'IASD', 'initial'),
(9, 'MODO', 'alternance');

-- --------------------------------------------------------

--
-- Structure de la table `pair`
--

CREATE TABLE `pair` (
  `groupID` int(255) NOT NULL,
  `projectID` int(255) NOT NULL,
  `studentID_1` int(255) NOT NULL,
  `studentID_2` int(255) NOT NULL,
  `effective_delivery_date` date DEFAULT NULL,
  `report_grade` double DEFAULT NULL,
  `defense_grade_stu_1` double DEFAULT NULL,
  `defense_grade_stu_2` double DEFAULT NULL,
  `final_grade_stu_1` double DEFAULT NULL,
  `final_grade_stu_2` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `pair`
--

INSERT INTO `pair` (`groupID`, `projectID`, `studentID_1`, `studentID_2`, `effective_delivery_date`, `report_grade`, `defense_grade_stu_1`, `defense_grade_stu_2`, `final_grade_stu_1`, `final_grade_stu_2`) VALUES
(1, 1, 1, 2, '2023-12-04', 10, 12, 13, 10.5, 11),
(1, 2, 1, 2, '2023-12-05', 10, 11, 11, 10.5, 10.5),
(1, 3, 1, 2, '2023-12-01', 10, 14, 14, 12, 12),
(2, 1, 6, 7, '2023-12-05', 14, 15, 16, 13.5, 14),
(2, 2, 4, 5, '2023-12-05', 5, 10, 12, 7.5, 8.5),
(2, 3, 3, 4, '2023-12-04', 10, 12, 13, 11, 11.5),
(3, 1, 17, 35, '2023-12-03', 10, 15, 13, 12.5, 11.5),
(3, 2, 26, 27, '2023-12-05', 10, 11, 13, 10.5, 11.5),
(3, 3, 6, 7, '2023-12-12', 10, 11, 15, 10.5, 12.5),
(4, 1, 49, 50, '2023-12-11', 10, 12, 13, 7, 7.5),
(4, 2, 7, 8, '2023-12-02', 10, 17, 18, 13.5, 14),
(4, 3, 30, 35, '2023-12-06', 14, 18, 19, 16, 16.5),
(5, 1, 16, 18, '2023-12-01', 10, 13, 12, 11.5, 11),
(5, 2, 19, 20, '2023-12-05', 10, 12, 13, 11, 11.5),
(6, 1, 28, 33, '2023-12-01', 10, 11, 9, 10.5, 9.5),
(7, 1, 30, 32, '2023-12-05', 10, 12, 13, 10, 10.5),
(8, 1, 43, 45, '2023-12-12', 10, 12, 13, 6.5, 7);

-- --------------------------------------------------------

--
-- Structure de la table `project`
--

CREATE TABLE `project` (
  `projectID` int(255) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `theme` varchar(255) DEFAULT NULL,
  `expected_delivery_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `project`
--

INSERT INTO `project` (`projectID`, `subject`, `theme`, `expected_delivery_date`) VALUES
(1, 'Appli Gestion Projets', 'Java', '2023-12-03'),
(2, 'Analyse Entreprise', 'Finance', '2023-12-12'),
(3, 'Produit innovant', 'Marketing', '2023-12-14');

-- --------------------------------------------------------

--
-- Structure de la table `student`
--

CREATE TABLE `student` (
  `studentID` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `educationID` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `student`
--

INSERT INTO `student` (`studentID`, `name`, `firstname`, `educationID`) VALUES
(1, 'Dupont', 'Marie', 3),
(2, 'Chen', 'Li', 7),
(3, 'Smith', 'John', 2),
(4, 'Garcia', 'Sofia', 5),
(5, 'Wang', 'Jing', 1),
(6, 'Mendoza', 'Carlos', 6),
(7, 'Tanaka', 'Yuki', 4),
(8, 'Abadi', 'Leila', 2),
(9, 'Singh', 'Arun', 7),
(10, 'Moretti', 'Luca', 1),
(11, 'Gomez', 'Elena', 4),
(12, 'Miyamoto', 'Hiroshi', 3),
(13, 'Rahman', 'Aisha', 5),
(14, 'Nguyen', 'Mia', 6),
(15, 'Lopez', 'Mateo', 2),
(16, 'Petrovic', 'Anya', 3),
(17, 'Chang', 'Mei', 1),
(18, 'Ivanov', 'Andrei', 5),
(19, 'Kapoor', 'Priya', 4),
(20, 'Nakamura', 'Hiroshi', 6),
(21, 'Silva', 'Eduardo', 7),
(22, 'Fischer', 'Olivia', 2),
(23, 'Sato', 'Hiroki', 3),
(24, 'Hassan', 'Ibrahim', 5),
(25, 'Kuznetsova', 'Anastasia', 6),
(26, 'Patel', 'Rohan', 4),
(27, 'Costa', 'Elena', 1),
(28, 'Al-Mansoori', 'Abdullah', 7),
(29, 'Mansour', 'Fatima', 2),
(30, 'Petrov', 'Andrei', 5),
(31, 'Lee', 'Jasmine', 1),
(32, 'Kapoor', 'Amit', 4),
(33, 'Khan', 'Aaliyah', 3),
(34, 'Wong', 'Adrian', 7),
(35, 'Ali', 'Yasmine', 2),
(36, 'Costa', 'Gabriel', 5),
(37, 'Tanaka', 'Rina', 6),
(38, 'Vasiliev', 'Dimitri', 3),
(39, 'Abadi', 'Nadia', 5),
(40, 'Gupta', 'Raj', 7),
(41, 'Rodriguez', 'Luna', 4),
(42, 'Verma', 'Dinesh', 6),
(43, 'Novak', 'Mila', 1),
(44, 'Kumar', 'Arun', 2),
(45, 'Ito', 'Yuki', 5),
(46, 'Zhang', 'Wei', 3),
(47, 'Abe', 'Sakura', 6),
(48, 'Choi', 'Jin', 7),
(49, 'Osei', 'Kwame', 4),
(50, 'Nanzou', 'Chris', 1),
(51, 'brahimi', 'yannis', 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `education`
--
ALTER TABLE `education`
  ADD PRIMARY KEY (`educationID`);

--
-- Index pour la table `pair`
--
ALTER TABLE `pair`
  ADD PRIMARY KEY (`groupID`,`projectID`),
  ADD UNIQUE KEY `uc_pair` (`groupID`,`projectID`),
  ADD KEY `studentID_1` (`studentID_1`) USING BTREE,
  ADD KEY `studentID_2` (`studentID_2`) USING BTREE,
  ADD KEY `projectID` (`projectID`) USING BTREE;

--
-- Index pour la table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`projectID`);

--
-- Index pour la table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`studentID`),
  ADD KEY `numero_formation` (`educationID`) USING BTREE;

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `student`
--
ALTER TABLE `student`
  MODIFY `studentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `pair`
--
ALTER TABLE `pair`
  ADD CONSTRAINT `pair_ibfk_1` FOREIGN KEY (`studentID_1`) REFERENCES `student` (`studentID`),
  ADD CONSTRAINT `pair_ibfk_2` FOREIGN KEY (`studentID_2`) REFERENCES `student` (`studentID`),
  ADD CONSTRAINT `pair_ibfk_3` FOREIGN KEY (`projectID`) REFERENCES `project` (`projectID`),
  ADD CONSTRAINT `pair_ibfk_4` FOREIGN KEY (`studentID_1`) REFERENCES `student` (`studentID`) ON DELETE CASCADE,
  ADD CONSTRAINT `pair_ibfk_5` FOREIGN KEY (`studentID_2`) REFERENCES `student` (`studentID`) ON DELETE CASCADE;

--
-- Contraintes pour la table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `student_ibfk_2` FOREIGN KEY (`educationID`) REFERENCES `education` (`educationID`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
