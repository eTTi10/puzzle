CREATE TABLE quiz
(
    id             VARCHAR(64) NOT NULL PRIMARY KEY ,
    quiz_image_url VARCHAR(64) NOT NULL,
    quiz_answer    VARCHAR(64) NOT NULL,
    category       VARCHAR(64) NOT NULL,
    hint           VARCHAR(64) NOT NULL,
);

