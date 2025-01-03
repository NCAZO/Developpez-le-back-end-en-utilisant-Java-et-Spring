# Châtop

Châtop est une application JAVA et Angular

The frontend part is available here: [Châtop Frontend](https://github.com/AntoineCanda/Developpez-le-back-end-en-utilisant-Java-et-Spring), forked from OpenClassRooms projects.

## Prerequisites

Les installations suivantes sont nécessaires pour lancer le projet :

- [Maven](https://maven.apache.org/install.html)
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/)

## Getting Started

Suivez les instructions afin de faire une copy et la déployer en local sur votre machine.

1. Clone du repository:

    ```bash
    git clone https://github.com/NCAZO/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
    ```
    
4. Creation de la base de donnée:

    ```mysql
    CREATE DATABASE db_chatop;
    USE db_chatop;
    SOURCE resources/sql/schemaDatabase.sql;
    ```

5. Installation des dependences:

    ```bash
    mvn clean install
    ```

6. Lancer le projet:

    ```bash
    mvn spring-boot:run
    ```

7. La documentation API est accessible ici :[http://localhost:3001/swagger-ui/index.html](http://localhost:3001/swagger-ui/index.html).

## Models, Repositories, et DTOs

- **Models:** Tous les models sont stocké dans le package `models`.

- **Repositories:** Tous les repositories sont stocké dans le package `repository`.

- **DTOs:** Tous les DTOs sont stocké dans le package `dto`.

## Controllers

Le `controllers` package contient toutes les classes controlleurs.

## Services

Le `services` package contient toutes les classes services.

## About the Security

La sécurité est assuré par [Spring Security](https://spring.io/projects/spring-security) et [JWT](https://jwt.io/).

La configuration de sécurité est configurée dans la classe `SpringSecurityConfig`.

## API Endpoints

- **POST** `/api/auth/register` - Enregistrer un nouvel utilisateur
- **POST** `/api/auth/login` - Connecter un utilisateur
- **GET** `/api/auth/me` - Récupérer les informations d'un utilisateur
- **POST** `/api/messages` - Envoyé un message depuis une rental
- **GET** `/api/rentals` - Récupérer la liste de toutes les rentals
- **GET** `/api/rentals/{id}` - Récupérer des information sur une rental par son ID
- **POST** `/api/rentals` - Créer une nouvelle rental
- **PUT** `/api/rentals/{id}` - Modifier les informations d'une rental

## Participation

Pour participer au projet :

1. Fork le repository.
2. Créer une nouvelle branche (`git checkout -b feature/your-feature`).
3. Effectuer vos changements et faites le commit (`git commit -m 'Add some feature'`).
4. Pousser sur la branche (`git push origin feature/your-feature`).
5. Créer une nouvelle Pull Request.
