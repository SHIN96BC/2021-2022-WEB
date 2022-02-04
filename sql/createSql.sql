drop sequence BOARDCLIENTMEMBER_SEQ;
drop table BOARDCLIENTMEMBER;

purge recyclebin;

create table BOARDCLIENTMEMBER(
   CLIENTNUMBER number, 
   ID varchar2(50) constraint BOARDCLIENTMEMBER_PK primary key, 
   PASSWORD varchar2(20),
   NICKNAME varchar2(30),
   NAME varchar2(20), 
   PHONENUMBER varchar2(11),
   ADDRESS varchar2(100),
   AUTHORITY number(1)  default 2 check(AUTHORITY in(0 , 1 , 2)),
   CDATE date
); 
create sequence BOARDCLIENTMEMBER_SEQ increment by 1 start with 1 nocache;

insert into BOARDCLIENTMEMBER values(BOARDCLIENTMEMBER_SEQ.nextval, 'aaa@aaa.com', '12345678', '관리자', '관리', '01077777777', '서울시', 0, SYSDATE);

