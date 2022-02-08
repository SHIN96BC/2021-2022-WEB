drop sequence BOARDCOMMENT_SEQ;
drop table BOARDCOMMENT;

purge recyclebin;

create table BOARDCOMMENT(
	COMMENTNUMBER number constraint BOARDCOMMENT_PK primary key,
	COMMENTPOSTNUMBER number,
	COMMENTSUBJECT varchar2(100),
	COMMENTCONTENT varchar(1000).
	constraint BOARDCOMMENT_FK foreign key(COMMENTPOSTNUMBER) references BOARDCLIENT(POSTNUMBER) on delete cascade
);