-- ================ HISTORY ===================
-- use tpcc
-- select * from history where H_C_W_ID > 4 
-- into outfile '/Users/Stephen/Desktop/HISTORY.csv' fields terminated by ',';

use test3
-- drop table HISTORY;

create table HISTORY (
	H_C_ID  int(11),
	H_C_D_ID int(11),
	H_C_W_ID int(11),
	H_D_ID  int(11),
	H_W_ID  int(11),
	H_DATE  timestamp,
	H_AMOUNT decimal(6,2),
	H_DATA  varchar(24)
);


LOAD DATA LOCAL INFILE "/Users/Stephen/Desktop/HISTORY.csv" INTO TABLE HISTORY
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(H_C_ID, H_C_D_ID, H_C_W_ID, H_D_ID, H_W_ID, H_DATE, H_AMOUNT, H_DATA);


-- ================ item ===================
-- use tpcc
-- select * from item where I_IM_ID< 1000
-- into outfile '/Users/Stephen/Desktop/item.csv' fields terminated by ',';

use test3
-- drop table item;

create table item (
	I_ID int(11),
	I_NAME  varchar(24),  
	I_PRICE decimal(5,2), 
	I_DATA  varchar(50),  
	I_IM_ID int(11)
);


LOAD DATA LOCAL INFILE "/Users/Stephen/Desktop/item.csv" INTO TABLE item
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(I_ID, I_NAME,  I_PRICE, I_DATA,  I_IM_ID);


-- ================ STOCK ===================
-- use tpcc
-- SELECT S_W_ID, S_I_ID, S_DATA, S_REMOTE_CNT FROM STOCK WHERE S_ORDER_CNT = 2 AND S_I_ID < 23000
-- into outfile '/Users/Stephen/Desktop/STOCK.csv' fields terminated by ',';


use test3
-- drop table STOCK;
create table STOCK (
	S_W_ID int(11),
	S_I_ID int(11),
	S_DATA varchar(50), 
	S_REMOTE_CNT int(11)
);


LOAD DATA LOCAL INFILE "/Users/Stephen/Desktop/STOCK.csv" INTO TABLE STOCK
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(S_W_ID, S_I_ID, S_DATA, S_REMOTE_CNT);


-- ================ OORDER ===================
-- use tpcc
-- SELECT * FROM OORDER WHERE O_C_ID < 36
-- into outfile '/Users/Stephen/Desktop/OORDER.csv' fields terminated by ',';

use test3
-- drop table OORDER;

create table OORDER (
	O_W_ID   int(11),             
    O_D_ID   int(11),             
    O_ID     int(11),             
    O_C_ID   int(11),       
    O_CARRIER_ID  int(11),       
    O_OL_CNT decimal(2,0),        
    O_ALL_LOCAL      decimal(1,0),        
    O_ENTRY_Dtimestamp timestamp
);


LOAD DATA LOCAL INFILE "/Users/Stephen/Desktop/OORDER.csv" INTO TABLE OORDER
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(O_W_ID,O_D_ID ,O_ID, O_C_ID ,O_CARRIER_ID, O_OL_CNT, O_ALL_LOCAL,O_ENTRY_Dtimestamp);



-- ================ NEW_ORDER ===================
-- use tpcc
-- SELECT * FROM NEW_ORDER WHERE NO_W_ID < 5
-- into outfile '/Users/Stephen/Desktop/NEW_ORDER.csv' fields terminated by ',';

use test3
-- drop table NEW_ORDER;

create table NEW_ORDER (
	NO_W_ID   int(11),             
    NO_D_ID   int(11),             
    NO_O_ID   int(11)
);


LOAD DATA LOCAL INFILE "/Users/Stephen/Desktop/NEW_ORDER.csv" INTO TABLE NEW_ORDER
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
(NO_W_ID,NO_D_ID,NO_O_ID);

