drop sequence BOARDCOMMENT_SEQ;
drop table BOARDCOMMENT;
drop sequence BOARDCLIENT_SEQ;
drop table BOARDCLIENT;
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

insert into BOARDCLIENTMEMBER values(BOARDCLIENTMEMBER_SEQ.nextval, 'aaa@aaa.com', '12345678', '包府磊', '包府', '01077777777', '辑匡矫', 0, SYSDATE);

create table BOARDCLIENT(
	POSTNUMBER number constraint BOARDCLIENT_PK primary key,
	WRITERNICKNAME varchar(30),
	WRITERID varchar(20),
	POSTSUBJECT varchar(100),
	POSTCONTENT varchar(1000),
	VIEWS number,
	PDATE date,
	REFER number,
	LEV number,
	SUNBUN number,
	constraint BOARDCLIENT_FK foreign key(WRITERID) references BOARDCLIENTMEMBER(ID) on delete cascade
);
create sequence BOARDCLIENT_SEQ increment by 1 start with 1 nocache;
create sequence BOARDCLIENT_REFER_SEQ increment by 1 start with 1 nocache;

create table BOARDCOMMENT(
	COMMENTNUMBER number constraint BOARDCOMMENT_PK primary key,
	COMMENTPOSTNUMBER number,
	COMMENTSUBJECT varchar2(100),
	COMMENTCONTENT varchar(1000),
	CODATE date,
	constraint BOARDCOMMENT_FK foreign key(COMMENTPOSTNUMBER) references BOARDCLIENT(POSTNUMBER) on delete cascade
);
create sequence BOARDCOMMENT_SEQ increment by 1 start with 1 nocache;