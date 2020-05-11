
declare
userexist integer;
begin
  select count(*) into userexist from dba_users where username='DIPLOMA';
  if (userexist = 0) then
    execute immediate 'CREATE USER DIPLOMA IDENTIFIED BY DIPLOMA123';
    execute immediate 'GRANT CONNECT, RESOURCE, DBA TO DIPLOMA';
    execute immediate 'CREATE OR REPLACE DIRECTORY BLKLKUP_DIR AS ''/opt/oracle/oradata/processing/''';
    execute immediate 'GRANT READ, WRITE ON DIRECTORY BLKLKUP_DIR TO DIPLOMA';
  end if;
end;
/