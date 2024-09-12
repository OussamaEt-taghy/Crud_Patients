-- Création de la table users
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
    );

-- Création de la table authorities avec une clé étrangère vers la table users
CREATE TABLE authorities IF NOT EXISTS (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users(username)
    );

-- Création d'un index unique sur la table authorities
CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username ON authorities (username, authority);
