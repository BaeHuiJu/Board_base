select * from files
order by fno desc;

--drop table comment;
/*
create table comment 
(
    cno number not null,
    bno number not null,
    content varchar2(4000),
    delete_at varchar2(50), --삭제 여부 'Y'삭제 'N' 
    writer varchar2(20),
    reg_date date default sysdate not null,
    update_date date,
    delete_date date
);
*/
-- drop table board_comment;

create table board_comment 
(
    cno number not null,
    bno number not null,
    content varchar2(4000),
    writer varchar2(20),
    reg_date date default sysdate not null
);
/*
create sequence SQ_CNO 
increment by 1
start with 1
minvalue 1
;
*/

create table board 
(
  BNO number, 
  SUBJECT varchar2(4000), 
  CONTENT varchar2(4000), 
  WRITER varchar2(50), 
  REG_DATE date
);

create table files
(
    fno number,
    bno number,
    filename varchar2(4000),
    fileoriname varchar2(4000),
    fileurl varchar2(4000)
);

create sequence SQ_BNO 
increment by 1
start with 1
minvalue 1
;