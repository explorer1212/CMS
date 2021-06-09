-- 校园卡管理系统

-- CREATE DATABASE CMS;

USE CMS; 

-- ALTER TABLE STUDENTS
-- ALTER COLUMN Ssex varchar(2);\

-- ALTER TABLE CARDS
--    ADD CONSTRAINT CHK_Cmoney CHECK(Cmoney >= 0 );

-- 删掉表中的所有列
-- DELETE FROM STUDENTS
-- DBCC CHECKIDENT ('CMS.dbo.STUDENTS',RESEED, 0)
-- DELETE students
-- FROM students


-- update cards 
-- set Cpassword = 1802003105
-- where Sid = 18020031058;

-- alter table cards
-- add constraint FK__CARDS__Sid__6 foreign key 
-- (
--     Sid 
-- ) references students (Sid) 
-- on update cascade on delete cascade

-- delete from students where Sid = 180200310;
select * from students;
select * from cards;
select * from record;

-- select * from cards;

-- CREATE TABLE STUDENTS
-- (
--     Sname varchar(20),
--     Sid varchar(20),

--     PRIMARY KEY(Sid)
-- );

-- CREATE TABLE CARDS
-- (
--     Cmoney int,
--     Sid varchar(20),

--     FOREIGN KEY (Sid) REFERENCES STUDENTS (Sid) 
-- );

-- DROP TABLE RECORD;

-- CREATE TABLE RECORD 
-- (
--     Sid VARCHAR(20),
--     Rmoney int,
--     Rdate DATE,

--     FOREIGN KEY (Sid) REFERENCES STUDENTS(Sid)
-- );
