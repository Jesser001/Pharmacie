/*DROP DATABASE IF EXISTS pharmacie;


CREATE DATABASE pharmacie
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

USE pharmacie;
CREATE TABLE utilisateur (
    id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL UNIQUE,
    mot_de_passe VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);
CREATE TABLE client (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    telephone VARCHAR(20)
);
CREATE TABLE fournisseur (
    id_fournisseur INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    contact VARCHAR(100)
);
CREATE TABLE produit (
    id_produit INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DOUBLE NOT NULL,
    quantite_stock INT NOT NULL,
    seuil_alerte INT NOT NULL
);
CREATE TABLE vente (
    id_vente INT AUTO_INCREMENT PRIMARY KEY,
    date_vente DATE NOT NULL,
    montant_total DOUBLE NOT NULL,
    id_client INT,

    CONSTRAINT fk_vente_client
        FOREIGN KEY (id_client)
        REFERENCES client(id_client)
);
CREATE TABLE ligne_vente (
    id_vente INT,
    id_produit INT,
    quantite INT NOT NULL,
    prix_unitaire DOUBLE NOT NULL,

    PRIMARY KEY (id_vente, id_produit),

    CONSTRAINT fk_lv_vente
        FOREIGN KEY (id_vente)
        REFERENCES vente(id_vente),

    CONSTRAINT fk_lv_produit
        FOREIGN KEY (id_produit)
        REFERENCES produit(id_produit)
);
CREATE TABLE commande_fournisseur (
    id_commande INT AUTO_INCREMENT PRIMARY KEY,
    date_commande DATE NOT NULL,
    statut VARCHAR(30) NOT NULL,
    id_fournisseur INT,

    CONSTRAINT fk_commande_fournisseur
        FOREIGN KEY (id_fournisseur)
        REFERENCES fournisseur(id_fournisseur)
);
CREATE TABLE ligne_commande (
    id_commande INT,
    id_produit INT,
    quantite INT NOT NULL,

    PRIMARY KEY (id_commande, id_produit),

    CONSTRAINT fk_lc_commande
        FOREIGN KEY (id_commande)
        REFERENCES commande_fournisseur(id_commande),

    CONSTRAINT fk_lc_produit
        FOREIGN KEY (id_produit)
        REFERENCES produit(id_produit)
);
INSERT INTO utilisateur (login, mot_de_passe, role)
VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO produit (nom, prix, quantite_stock, seuil_alerte) VALUES
('Paracetamol', 5.99, 150, 20),
('Ibuprofene', 7.50, 89, 15),
('Vitamine C', 12.99, 45, 10);
INSERT INTO client (nom, prenom, telephone)
VALUES ('Dupont', 'Jean', '0612345678');
SELECT login, mot_de_passe, role FROM utilisateur WHERE login = 'admin';
SELECT * FROM produit;/*
