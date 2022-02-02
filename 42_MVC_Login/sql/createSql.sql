drop sequence BOARDCLIENTMEMBER_SEQ;
drop table BOARDCLIENTMEMBER;

purge recyclebin;

create table BOARDCLIENTMEMBER(
   CLIENTNUMBER number, 
   ID varchar2(20) constraint BOARDCLIENTMEMBER_PK primary key, 
   PASSWORD varchar2(20),
   NICKNAME varchar2(30),
   NAME varchar2(20), 
   PHONENUMBER varchar2(11),
   ADDRESS varchar2(100),
   AUTHORITY number(1)  default 2 check(AUTHORITY in(0 , 1 , 2)),
   CDATE date
); 
create sequence BOARDCLIENTMEMBER_SEQ increment by 1 start with 1 nocache;