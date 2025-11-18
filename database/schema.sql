CREATE DATABASE IF NOT EXISTS my_fridge_db
    CHARACTER SET = 'utf8mb4'
    COLLATE = 'utf8mb4_general_ci';

USE my_fridge_db;

CREATE TABLE IF NOT EXISTS word (
    word_id         BIGINT          NOT NULL AUTO_INCREMENT,   
    label_en        VARCHAR(100)    NOT NULL UNIQUE,           
    name_ko         VARCHAR(100)    NOT NULL,                  
    created_at      DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6), 
    updated_at      DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6), 
    PRIMARY KEY (word_id)
);

CREATE TABLE IF NOT EXISTS translation (
    translation_id  BIGINT          NOT NULL AUTO_INCREMENT,    
    word_id         BIGINT          NOT NULL,                   
    language_code   VARCHAR(10)     NOT NULL,                   
    translated_word VARCHAR(255)    NOT NULL,                   
    created_at      DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at      DATETIME(6)     NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (translation_id),
    FOREIGN KEY (word_id) REFERENCES word (word_id)             
);
