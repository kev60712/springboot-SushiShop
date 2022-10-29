CREATE TABLE IF NOT EXISTS sushi (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(30),
                       time_to_make INT DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS sushi_order (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             status_id INT NOT NULL,
                             sushi_id INT NOT NULL,
                             createdAt TIMESTAMP NOT NULL default CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS status (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(30) NOT NULL
);

