CREATE TABLE countries (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Assuming Long is translated to BIGINT
                           code VARCHAR(2) NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           emoji VARCHAR(100),  -- Can likely hold most emoji characters
                           currency VARCHAR(50),
                           capital VARCHAR(255)
);