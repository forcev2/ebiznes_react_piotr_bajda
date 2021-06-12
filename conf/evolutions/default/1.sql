# --- !Ups

CREATE TABLE "category"
(
    "id"   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" VARCHAR NOT NULL
);

CREATE TABLE "user"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "providerId"  VARCHAR NOT NULL,
    "providerKey" VARCHAR NOT NULL,
    "email"       VARCHAR NOT NULL
);

CREATE TABLE "authToken"
(
    "id"     INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "userId" INT     NOT NULL,
    FOREIGN KEY (userId) references user (id)
);

CREATE TABLE "passwordInfo"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "providerId"  VARCHAR NOT NULL,
    "providerKey" VARCHAR NOT NULL,
    "hasher"      VARCHAR NOT NULL,
    "password"    VARCHAR NOT NULL,
    "salt"        VARCHAR
);

CREATE TABLE "oAuth2Info"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "providerId"  VARCHAR NOT NULL,
    "providerKey" VARCHAR NOT NULL,
    "accessToken" VARCHAR NOT NULL,
    "tokenType"   VARCHAR,
    "expiresIn"   INTEGER
);

CREATE TABLE "vendor_info"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "description" VARCHAR NOT NULL
);


 CREATE TABLE "vendor"
 (
     "id"           INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     "company_name" VARCHAR NOT NULL,
     "user"         INTEGER     NOT NULL,
     "vendor_info"  INTEGER     NOT NULL,
     FOREIGN KEY ("user") references "user" (id),
     FOREIGN KEY (vendor_info) references vendor_info (id)
 );

CREATE TABLE "product"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"        VARCHAR NOT NULL,
    "description" TEXT    NOT NULL,
    "category"    INTEGER     NOT NULL,
    "vendor"      INTEGER     NOT NULL,
    FOREIGN KEY (category) references category (id),
    FOREIGN KEY (vendor) references vendor (id)
);


CREATE TABLE "client"
(
    "id"      INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name"    VARCHAR NOT NULL,
    "surname" VARCHAR NOT NULL,
    "user"    INTEGER     NOT NULL,
    FOREIGN KEY ("user") references "user" (id)
);


CREATE TABLE "item_comment"
(
    "id"           INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "comment_body" VARCHAR NOT NULL,
    "product"      INTEGER     NOT NULL,
    "client"       INTEGER     NOT NULL,
    FOREIGN KEY (product) references product (id),
    FOREIGN KEY (client) references client (id)
);

CREATE TABLE "vendor_comment"
(
    "id"           INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "comment_body" VARCHAR NOT NULL,
    "vendor"       INTEGER     NOT NULL,
    "client"       INTEGER     NOT NULL,
    FOREIGN KEY (vendor) references vendor (id),
    FOREIGN KEY (client) references client (id)
);

CREATE TABLE "buy_info"
(
    "id"          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "date"        DATE    NOT NULL,
    "address"     TEXT    NOT NULL,
    "total_price" INTEGER     NOT NULL
);

CREATE TABLE "transaction_info"
(
    "id"       INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "date"     DATE    NOT NULL,
    "product"  INTEGER     NOT NULL,
    "client"   INTEGER     NOT NULL,
    "buy_info" INTEGER     NOT NULL,
    FOREIGN KEY (product) references product (id),
    FOREIGN KEY (client) references client (id),
    FOREIGN KEY (buy_info) references buy_info (id)
);



# --- !Downs

DROP TABLE "transaction_info";
DROP TABLE "buy_info";
DROP TABLE "vendor_comment";
DROP TABLE "item_comment";
DROP TABLE "client";
DROP TABLE "product";
DROP TABLE "vendor";
DROP TABLE "vendor_info";
DROP TABLE "authToken";
DROP TABLE "passwordInfo";
DROP TABLE "oAuth2Info";
DROP TABLE "user";
DROP TABLE "category";