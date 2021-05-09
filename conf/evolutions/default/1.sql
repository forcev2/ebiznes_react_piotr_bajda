# --- !Ups

CREATE TABLE "category"
(
    "id"   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL
);

 CREATE TABLE "vendor"
 (
     "id"           INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     "company_name" VARCHAR NOT NULL,
     "user"         INT     NOT NULL,
     FOREIGN KEY (user) references user (id),
     "vendor_info"  INT     NOT NULL,
     FOREIGN KEY (vendor_info) references vendor_info (id)
 );

CREATE TABLE "product"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"        VARCHAR NOT NULL,
    "description" TEXT    NOT NULL,
    "category"    INT     NOT NULL,
    FOREIGN KEY (category) references category (id),
    "vendor"      INT     NOT NULL,
    FOREIGN KEY (vendor) references vendor (id)
);

CREATE TABLE "user"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "username" VARCHAR NOT NULL,
    "password" VARCHAR NOT NULL,
    "email"    VARCHAR NOT NULL
);

CREATE TABLE "client"
(
    "id"      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"    VARCHAR NOT NULL,
    "surname" VARCHAR NOT NULL,
    "user"    INT     NOT NULL,
    FOREIGN KEY (user) references user (id)
);

CREATE TABLE "vendor_info"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "description" VARCHAR NOT NULL
);


CREATE TABLE "item_comment"
(
    "id"           INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "comment_body" VARCHAR NOT NULL,
    "product"      INT     NOT NULL,
    FOREIGN KEY (product) references product (id),
    "client"       INT     NOT NULL,
    FOREIGN KEY (client) references user (id)
);

CREATE TABLE "vendor_comment"
(
    "id"           INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "comment_body" VARCHAR NOT NULL,
    "vendor"       INT     NOT NULL,
    FOREIGN KEY (vendor) references vendor (id),
    "client"       INT     NOT NULL,
    FOREIGN KEY (client) references user (id)
);

CREATE TABLE "buy_info"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "date"        DATE    NOT NULL,
    "address"     TEXT    NOT NULL,
    "total_price" INT     NOT NULL
);

CREATE TABLE "transaction_info"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "date"     DATE    NOT NULL,
    "product"  INT     NOT NULL,
    FOREIGN KEY (product) references product (id),
    "client"   INT     NOT NULL,
    FOREIGN KEY (client) references client (id),
    "buy_info" INT     NOT NULL,
    FOREIGN KEY (buy_info) references buy_info (id)
);



# --- !Downs

DROP TABLE "category";
DROP TABLE "product";
DROP TABLE "user";
DROP TABLE "client";
DROP TABLE "vendor";
DROP TABLE "item_comment";
DROP TABLE "buy_info";
DROP TABLE "transaction_info";
DROP TABLE "vendor_info";
DROP TABLE "vendor_comment";