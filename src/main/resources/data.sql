insert into user values(90000,sysdate(),'User1','password1','80800-1011');
insert into user values(90001,sysdate(),'User2','password2','80800-1022');
insert into user values(90002,sysdate(),'User3','password3','80800-1033');
<!-- 유저는 id만 넣어주면 된다.-->
insert into post values(10001,'First post',90001);
insert into post values(10002,'second post',90001);
