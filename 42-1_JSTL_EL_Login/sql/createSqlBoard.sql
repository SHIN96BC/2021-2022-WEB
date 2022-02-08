drop sequence BOARDCLIENT_SEQ;
drop table BOARDCLIENT;

purge recyclebin;

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
