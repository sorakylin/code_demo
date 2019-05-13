
CREATE TABLE IF NOT EXISTS user
(
    id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(40),
    name VARCHAR(20),
    age INT(3),
    balance DECIMAL(10,2)
);

INSERT INTO user VALUES
(NULL ,'account1','张三',25,100.00),
(NULL ,'account2','王五',22,120.00)