
CREATE DATABASE ztbdemo;
USE ztbdemo;
---
CREATE TABLE user
(
    user_id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    id_card VARCHAR(20),
    phone VARCHAR(20),
    sex VARCHAR(2),
    authority VARCHAR(20)
);
CREATE UNIQUE INDEX user_user_name_uindex ON user (user_name);

INSERT INTO user VALUES (null,'admin','123456','43310019880505666x','18823231454','男','管理员');

---

CREATE TABLE authority
(
    authority_id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    authority_name VARCHAR(20) NOT NULL
);
CREATE UNIQUE INDEX authority_authority_name_uindex ON authority (authority_name);

INSERT INTO authority VALUES (null,'管理员'),(null,'普通用户');

---

CREATE TABLE feature
(
    feature_id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    feature_name VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX feature_feature_name_uindex ON feature (feature_name);

---

CREATE TABLE feature_to_authority
(
    fta_id INT(10) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    authority_id INT(10) NOT NULL,
    feature_id INT(10) NOT NULL
);




