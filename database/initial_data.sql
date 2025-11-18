USE my_fridge_db;

INSERT INTO word (label_en, name_ko) VALUES
('apple', '사과'),
('grape', '포도'),
('cabbage', '양배추'),
('milk', '우유'),
('egg', '계란');

INSERT INTO translation (word_id, language_code, translated_word) VALUES
(1, 'es', 'Manzana'),  
(1, 'fr', 'Pomme'),    
(1, 'de', 'Apfel');    

INSERT INTO translation (word_id, language_code, translated_word) VALUES
(2, 'es', 'Uva'),
(2, 'fr', 'Raisin'),
(2, 'de', 'Traube');

INSERT INTO translation (word_id, language_code, translated_word) VALUES
(3, 'es', 'Col'),
(3, 'fr', 'Chou'),
(3, 'de', 'Kohl');

INSERT INTO translation (word_id, language_code, translated_word) VALUES
(4, 'es', 'Leche'),
(4, 'fr', 'Lait'),
(4, 'de', 'Milch');

INSERT INTO translation (word_id, language_code, translated_word) VALUES
(5, 'es', 'Huevo'),
(5, 'fr', 'Œuf'),
(5, 'de', 'Ei');