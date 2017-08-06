CREATE TABLE cherryDB.CUSTOMERS
(
    CUSTOMER_ID INT PRIMARY KEY AUTO_INCREMENT,
    FIRST_NAME   VARCHAR(100),
    MIDDLE_INITIAL   VARCHAR(30),
    LAST_NAME    VARCHAR(100),
    EMAIL_ADDRESS    VARCHAR(100),
    USER_ID   VARCHAR(30),
    USER_PASSWORD VARCHAR(100),
    PHONE_NUMBER VARCHAR(15),
    REGISTRATION_DATE DATETIME,
    LAST_LOGIN_DATE DATETIME,
    CUSTOMER_TYPE VARCHAR(30),  
    BUSINESS_NAME VARCHAR(50),
    USER_ROLES INT,
    ACTIVATION_CODE VARCHAR(150),
    ACCOUNT_STATUS_REASON VARCHAR(20),
    ACCOUNT_STATUS_CODE VARCHAR(3),
    TEMP_PASSWORD VARCHAR(100)
);

CREATE TABLE cherryDB.CUSTOMER_ADDRESS
(
    ADDRESS_ID INT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID INT,
    ADDRESS_TYPE VARCHAR(30),
    ADDRESS_FIRST_NAME VARCHAR(150),
    ADDRESS_LAST_NAME VARCHAR(150),
    COMPANY_NAME VARCHAR(150),
    ADDRESS_CONTACT_PHONE_NUMBER VARCHAR(15),
    ADDRESS_LINE_1 VARCHAR(100),
    ADDRESS_LINE_2 VARCHAR(100),
    CITY VARCHAR(100),
    STATE VARCHAR(100),
    POSTAL_ZIPCODE VARCHAR(15),
    COUNTRY VARCHAR(100),
    FOREIGN KEY(CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID)
);


CREATE TABLE cherryDB.CUSTOMER_PAYMENT_INFORMATION
(
    PAYMENT_CARD_ID INT PRIMARY KEY AUTO_INCREMENT,
    CUSTOMER_ID INT,
    NAME_ON_CARD VARCHAR(150),
    BILLING_CONTACT_PHONE_NUMBER VARCHAR(15),
    CARD_TYPE VARCHAR(50),
    CARD_EXPIRATION_MONTH VARCHAR(2),
    CARD_EXPIRATION_YEAR VARCHAR(4),
    CARD_NUMBER VARCHAR(25),
    CVV VARCHAR(5),
    FOREIGN KEY(CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID)
);


CREATE TABLE cherryDB.ITEMS
(
    ITEM_ID INT PRIMARY KEY AUTO_INCREMENT,
    ITEM_OWNER_CUSTOMER_ID INT,
    ITEM_NAME VARCHAR(150),
    ITEM_DESCRIPTION VARCHAR(900),
    ITEM_PRICE VARCHAR(15),
    BUSINESS_NAME VARCHAR(50),
	ITEM_CURRENT_BID_PRICE VARCHAR(15),
	ITEM_QUANTITY_AVAILABLE VARCHAR(15),
    ITEM_IMAGE_LOCATION1 VARCHAR(100),
    ITEM_IMAGE_LOCATION2 VARCHAR(100),
    ITEM_IMAGE_LOCATION3 VARCHAR(100),
    ITEM_IMAGE_LOCATION4 VARCHAR(100),
    ITEM_IMAGE_LOCATION5 VARCHAR(100),
    ITEM_IMAGE_LOCATION6 VARCHAR(100),
    ITEM_ADDED_DATE DATETIME,
    ITEM_MODIFIED_DATE DATETIME,
    ITEM_SOLD_DATE DATETIME,
    ITEM_FINAL_TIME_TO_BID DATETIME,
    TEMP_ITEM_FINAL_TIME_TO_BID VARCHAR(150),
    ITEM_FINAL_TIME_TO_BID_TIMEZONE VARCHAR(100),    
    ITEM_STATUS_REASON VARCHAR(120),
    ITEM_STATUS_CODE VARCHAR(3),
    FOREIGN KEY(ITEM_OWNER_CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID)
);



CREATE TABLE cherryDB.CUSTOMER_BIDS
(
    BID_ID INT PRIMARY KEY AUTO_INCREMENT,
    ITEM_ID INT,
    CUSTOMER_ID INT,
    BID_PRICE VARCHAR(15),
	WINNGIN_BID VARCHAR(5),	
    BID_DATE DATETIME,    
    FOREIGN KEY(ITEM_ID) REFERENCES ITEMS(ITEM_ID),
    FOREIGN KEY(CUSTOMER_ID) REFERENCES CUSTOMERS(CUSTOMER_ID)
);




create database IF NOT EXISTS cherryDB


drop table cherryDB.customers
drop table cherryDB.CUSTOMER_BIDS
drop table cherryDB.ITEMS
drop table cherryDB.CUSTOMER_ADDRESS
drop table cherryDB.CUSTOMER_PAYMENT_INFORMATION

ALTER TABLE cherryDB.customers ADD USER_ROLES INTEGER NOT NULL 
ALTER TABLE cherryDB.customers MODIFY USER_PASSWORD VARCHAR(100);

delete from cherryDB.customers
delete from cherryDB.customer_bids
delete from cherryDB.items

delete from cherryDB.customer_address
delete from cherryDB.customer_payment_information

select * from cherrydb.items where ITEM_FINAL_TIME_TO_BID > '2013-09-7 13:06:00' order by ITEM_FINAL_TIME_TO_BID asc
select * from cherrydb.items where ITEM_FINAL_TIME_TO_BID < '2013-09-10 22:00:00' and ITEM_STATUS_CODE=100 order by ITEM_FINAL_TIME_TO_BID asc

select * from cherrydb.customer_bids where ITEM_ID=2 and BID_PRICE=(select max(BID_PRICE) from cherrydb.customer_bids where ITEM_ID=2)

