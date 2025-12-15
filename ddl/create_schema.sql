CREATE DATABASE IF NOT EXISTS moviedb;
USE moviedb;


-- Movie table
CREATE TABLE Movie (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Star table
CREATE TABLE Star (
    id BIGINT NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Many-to-many join table movie_star
CREATE TABLE movie_star (
    movie_id BIGINT NOT NULL,
    star_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, star_id),
    CONSTRAINT fk_movie_star_movie FOREIGN KEY (movie_id) REFERENCES Movie(id),
    CONSTRAINT fk_movie_star_star FOREIGN KEY (star_id) REFERENCES Star(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
