CREATE DATABASE `skypyb-demo-test_0` CHARACTER SET UTF8MB4;
CREATE DATABASE `skypyb-demo-test_1` CHARACTER SET UTF8MB4;

create table `skypyb-demo-test_0`.`tb_order0`
(
    order_id    BIGINT primary key,
    order_no    varchar(64) not null,
    item_id     BIGINT      not null,
    user_id     BIGINT      not null,
    provider_id BIGINT      not null
);

create table `skypyb-demo-test_0`.`tb_order1`
(
    order_id    BIGINT primary key,
    order_no    varchar(64) not null,
    item_id     BIGINT      not null,
    user_id     BIGINT      not null,
    provider_id BIGINT      not null
);

create table `skypyb-demo-test_1`.`tb_order0`
(
    order_id    BIGINT primary key,
    order_no    varchar(64) not null,
    item_id     BIGINT      not null,
    user_id     BIGINT      not null,
    provider_id BIGINT      not null
);

create table `skypyb-demo-test_1`.`tb_order1`
(
    order_id    BIGINT primary key,
    order_no    varchar(64) not null,
    item_id     BIGINT      not null,
    user_id     BIGINT      not null,
    provider_id BIGINT      not null
);

