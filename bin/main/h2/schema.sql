CREATE TABLE quiz
(
    id             VARCHAR(64) NOT NULL PRIMARY KEY ,
    quiz_image_url VARCHAR(64) NOT NULL,
    quiz_answer    VARCHAR(64) NOT NULL,
    category       VARCHAR(64) NOT NULL
);

CREATE TABLE puzzler
(
    google_id          VARCHAR(64) NOT NULL PRIMARY KEY ,
    nick_name          VARCHAR(14) NOT NULL,
    solved_quizs       VARCHAR(10000) ,
    solving_quizs      VARCHAR(50000) ,
    solving_count      INT ,
    hint_count         INT ,
    max_quiz_count     INT,
    score              INT
);
