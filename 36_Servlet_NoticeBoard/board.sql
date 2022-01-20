--alter session set "_oracle_script"=true;
--create user servlet identified by java;
--grant connect, resource, unlimited tablespace to servlet;
--conn servlet/java;


drop table BOARD;
drop sequence BOARD_SEQ;
purge recyclebin;

create table BOARD(
   SEQ number constraint BOARD_PK primary key, 
   WRITER varchar2(12), 
   EMAIL varchar2(20),
   SUBJECT varchar2(20), 
   CONTENT varchar2(20), 
   RDATE date default SYSDATE
); 
create sequence BOARD_SEQ increment by 1 start with 1 nocache;

insert into BOARD values(BOARD_SEQ.nextval, '±è¹Î°æ', 'kmj@hanmail.net','Á¦¸ñ1', '³»¿ë1', SYSDATE);
insert into BOARD values(BOARD_SEQ.nextval, '±è¼öÈ¯', 'ksh@hanmail.net','Á¦¸ñ2', '³»¿ë2', SYSDATE);
insert into BOARD values(BOARD_SEQ.nextval, '±èÁö¼ö', 'kjs@hanmail.net','Á¦¸ñ3', '³»¿ë3', SYSDATE);
insert into BOARD values(BOARD_SEQ.nextval, '±èÁöÇù', 'kjh@hanmail.net','Á¦¸ñ4', '³»¿ë4', SYSDATE);

commit;

select CONSTRAINT_NAME, CONSTRAINT_TYPE from user_constraints where TABLE_NAME='BOARD';
select * from BOARD order by SEQ desc;

