
-#-��ϵ�����ݿ�
	----���ʹ���ն˲������ݿ�
	1����ε�½���ݿ������ ����dosϵͳ�����룺mysql -uroot -proot
	2����β�ѯ���ݿ�����������е����ݿ�
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| gjp                |
| gmall_study        |
| miaosha            |
| mysql              |
| performance_schema |
| test               |
+--------------------+
7 rows in set (0.00 sec)
#-- �鿴���ݱ�ṹ
-- describe pet;
desc pet;

#-- ��ѯ��
SELECT * from pet;

#-- ��������
INSERT INTO pet VALUES ('puffball', 'Diane', 'hamster', 'f', '1990-03-30', NULL);

#-- �޸�����
UPDATE pet SET name = 'squirrel' where owner = 'Diane';

#-- ɾ������
DELETE FROM pet where name = 'squirrel';

#-- ɾ����
DROP TABLE myorder;

һ����ס:INSERT(��),DELETE(ɾ),UPDATE(��),SELECT(��) 


#mysql�����е�Լ��
1.����Լ��:primary key
                 ���ܹ�Ψһȷ��һ�ű��е�һ����¼,��������Լ��֮��,�Ϳ���ʹ���ֶβ��ظ����Ҳ�Ϊ��
				create table user(
						id int PRIMARY KEY,
						name VARCHAR(20)
				);
2.��������Լ��:primary key(id,name)
				ֻҪ��������ֵ���������ظ��Ϳ��ԣ������������������κ�һ���ֶζ�������Ϊ��
				˵���˸�������ֻҪ���е��ֶζ�������ͬ������¿����������е��ֶ��ظ�:
				CREATE TABLE user2(
					id INT,
					name VARCHAR(20),
					password VARCHAR(20),
					PRIMARY key(id,name)
				);
����DESCRIBE user2;
+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| id       | int(11)     | NO   | PRI | NULL    |       |
| name     | varchar(20) | NO   | PRI | NULL    |       |
| password | varchar(20) | YES  |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+

INSERT INTO user2 VALUES (1,'����','123456');
INSERT INTO user2 VALUES (2,'����','123456');

+----+------+----------+
| id | name | password |
+----+------+----------+
|  1 | ���� | 123456   |
|  2 | ���� | 123456   |
+----+------+----------+
˵���˸�������ֻҪ���е��ֶζ�������ͬ������¿����������е��ֶ��ظ�:
INSERT INTO user2 VALUES (1,'����','123456');

SELECT * FROM user2;
+----+------+----------+
| id | name | password |
+----+------+----------+
|  1 | ���� | 123456   |
|  1 | ���� | 123456   |
|  2 | ���� | 123456   |
+----+------+----------+
				
**����:�����а༶���Լ�ѧ����λ��,���ǿ����ð༶��+ѧ������λ�ſ���׼ȷ�Ķ�λһ��ѧ��,
  ��:(1��5�ſ���׼ȷ��ȷ��һ��ѧ��)
					
3.����Լ��:auto_increment
				CREATE TABLE user3(
					id INT PRIMARY KEY AUTO_INCREMENT,
					name VARCHAR(20)
				);
����DESCRIBE user3;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   | PRI | NULL    | auto_increment |
| name  | varchar(20) | YES  |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+

INSERT INTO user3(name) VALUES('����');
INSERT INTO user3(name) VALUES('����');
+----+------+
| id | name |
+----+------+
|  1 | ���� |
|  2 | ���� |
+----+------+
û���Զ���idֵ �����Զ�������id

����������ʱ�������˴�������Լ��������ô��
CREATE TABLE user4(
					id INT,
					name VARCHAR(20)
				);
alter table user4 add primary key(id);
-- �������Լ��
-- �����������������������ͨ��SQL������ã����ַ�ʽ����
ALTER TABLE user ADD PRIMARY KEY(id);
ALTER TABLE user MODIFY id INT PRIMARY KEY;

-- ɾ������
ALTER TABLE user drop PRIMARY KEY;


4.ΨһԼ��
Լ�����ε��ֶε�ֵ�������ظ�
(1) ����ʱδ���ΨһԼ�� �������ALTER TABLE user5 ADD UNIQUE(��ҪԼ�����ֶ�);				
CREATE TABLE user5(
    id int,
    name VARCHAR(20)
);
���� DESCRIBE user5;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   |     | NULL    | auto_increment |
| name  | varchar(20) | YES  |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+

����nameΪΨһԼ��:
ALTER TABLE user5 ADD UNIQUE(name);
���� DESCRIBE user5;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   |     | NULL    | auto_increment |
| name  | varchar(20) | YES  | UNI | NULL    |                |
+-------+-------------+------+-----+---------+----------------+
����:��������
INSERT INTO user5(name) VALUES ('cc');
���� SELECT * FROM user5; �鿴���:
+----+------+
| id | name |
+----+------+
|  1 | cc   |
+----+------+
�ٴβ���INSERT INTO user5(name) VALUES ('cc');
����:ERROR 1062 (23000): Duplicate entry 'cc' for key 'name'

�������� INSERT INTO user5(name) VALUES ('aa');
���� SELECT * FROM user5; �鿴���:
+----+------+
| id | name |
+----+------+
|  3 | aa   |
|  1 | cc   |
+----+------+
(2)����ʱֱ�����ΨһԼ��
CREATE TABLE user (
    id INT,
    name VARCHAR(20),
    UNIQUE(id,name) //������һ���ظ�����
);

-- ɾ��ΨһԼ��
ALTER TABLE user DROP INDEX name;
-- ���ΨһԼ��
-- �������ʱû������ΨһԼ����������ͨ��SQL������ã����ַ�ʽ����
ALTER TABLE user ADD UNIQUE(name);
ALTER TABLE user MODIFY name VARCHAR(20) UNIQUE;

**����:ҵ������:���һ���û�ע���,�û���������Ҫ���ֻ�����ע��,�����ֻ��ź��û����ƶ�����Ϊ��,��ô:
CREATE TABLE user_test(
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT'����id',
    name VARCHAR(20) NOT NULL COMMENT'�û�����,����Ϊ��',
    phone_number VARCHAR(20) UNIQUE NOT NULL COMMENT'�û��ֻ�,�����ظ��Ҳ���Ϊ��'
);
���� DESCRIBE user_test;
+--------------+-------------+------+-----+---------+----------------+
| Field        | Type        | Null | Key | Default | Extra          |
+--------------+-------------+------+-----+---------+----------------+
| id           | int(11)     | NO   | PRI | NULL    | auto_increment |
| name         | varchar(20) | NO   |     | NULL    |                |
| phone_number | int(11)     | NO   | UNI | NULL    |                |
+--------------+-------------+------+-----+---------+----------------+
�����Ļ��ʹﵽ��ÿһ���ֻ��Ŷ�ֻ�ܳ���һ��,�ﵽ��ÿ���ֻ���ֻ�ܱ�ע��һ��.
�û����������ظ�,�����ֻ�����ȴ�����ظ�,�����������߼�����

		�ܽ�Լ���������ɾ����
			1�� �����ʱ������Լ��
			2�� ����ʹ�� alter������add������
			3�� alter������modify������
			4�� ɾ��Լ�� alter������drop������

5.�ǿ�Լ��:
-- ����ʱ��ӷǿ�Լ��
-- Լ��ĳ���ֶβ���Ϊ��
CREATE TABLE user (
    id INT,
    name VARCHAR(20) NOT NULL
);

-- �Ƴ��ǿ�Լ��
ALTER TABLE user MODIFY name VARCHAR(20);

6.Ĭ��Լ��
���ǵ����ǲ����ֶ�ֵ��ʱ�����û�д�ֵ���ͻ�ʹ��Ĭ��ֵ
-- ����ʱ���Ĭ��Լ��
-- Լ��ĳ���ֶε�Ĭ��ֵ
CREATE TABLE user2 (
    id INT,
    name VARCHAR(20),
    age INT DEFAULT 10
);

-- �Ƴ��ǿ�Լ��
ALTER TABLE user MODIFY age INT;

7.���Լ��  foreign key(class_id) reference classes(id)
�漰������ �����ӱ�
-- �༶
CREATE TABLE classes (
    id INT PRIMARY KEY,
    name VARCHAR(20)
);

-- ѧ����
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(20),
    -- ����� class_id Ҫ�� classes �е� id �ֶ������
    class_id INT,
    -- ��ʾ class_id ��ֵ���������� classes �е� id �ֶ�ֵ
    FOREIGN KEY(class_id) REFERENCES classes(id)
);

-- 1. ��������classes ��û�е�����ֵ���ڸ����ӱ�students �У��ǲ�����ʹ�õģ�
-- 2. �����еļ�¼����������ʱ���������Ա�ɾ����
�༶��������:
INSERT INTO CLASSES VALUES (1,'һ��');
INSERT INTO CLASSES VALUES (2,'����');
INSERT INTO CLASSES VALUES (3,'����');
INSERT INTO CLASSES VALUES (4,'�İ�');

ѧ����������:
INSERT INTO students VALUES (1001,'С��',1);
INSERT INTO students VALUES (1002,'СǮ',2);
INSERT INTO students VALUES (1003,'С��',3);
INSERT INTO students VALUES (1004,'С��',4);

DELETE classes WHERE name = '�İ�'; //����ɾ������Ϊ��������ʹ��

#���ݿ��������Ʒ�ʽ
1.��һ��ʽ
ֻҪ�ֶ�ֵ�����Լ�����֣��Ͳ������һ��ʽ��
��ʽ��Ƶ�Խ��ϸ����ĳЩʵ�ʲ������ܻ���ã������Ƕ��кô�����Ҫ����Ŀ��ʵ����������趨��

	���ݱ��е������ֶζ��ǲ��ɷָ��ԭ��ֵ
	create table student(
		id int primary key,
		name varchar(20),
		address varchar(30)
	);
	insert into student values(1,'����','�й��Ĵ�ʡ�ɶ�������������100��');
	insert into student values(2,'����','�й��Ĵ�ʡ�ɶ�������������200��');
	insert into student values(3,'����','�й��Ĵ�ʡ�ɶ�������������40��');
�������ݱ��е��ֶ�ֵ�����Լ�����ֵģ��Ͳ������һ��ʽ

	create table student2(
		id int primary key,
		name varchar(20),
		country varchar(30),
		province varchar(30),
		city varchar(30),
		details varchar(30)
	);
	insert into student2 values(1,'����','�й�','�Ĵ�ʡ','�ɶ���','����������100��');
	insert into student2 values(2,'����','�й�','�Ĵ�ʡ','�ɶ���','����������200��');
	insert into student2 values(3,'����','�й�','�Ĵ�ʡ','�ɶ���','����������40��');
	��˴������������һ��ʽ����ô����ͳ�ƹ��һ���ʡ�ݵ�ʱ��ͱȽϷ���
��ʽ��Ƶ�Խ��ϸ������ĳЩʵ�ʲ������ܸ��ã����ǲ�һ�����Ǻô�

2.�ڶ���ʽ
�����������һ��ʽ��ǰ���£��ڶ���ʽҪ�󣬳��������ÿһ�ж�������ȫ����������
���Ҫ���ֲ���ȫ������ֻ���ܷ��������������������

-- ������
CREATE TABLE myorder (
    product_id INT,
    customer_id INT,
    product_name VARCHAR(20),
    customer_name VARCHAR(20),
    PRIMARY KEY (product_id, customer_id)
);
ʵ���ϣ������Ŷ������У�product_name ֻ������ product_id ��
customer_name ֻ������ customer_id ��Ҳ����˵��
product_name �� customer_id ��û�ù�ϵ�ģ�
customer_name �� product_id Ҳ��û�й�ϵ�ġ�
��Ͳ�����ڶ���ʽ�������ж�������ȫ�����������У�

����Ҫ���в��
CREATE TABLE myorder (
    order_id INT PRIMARY KEY,
    product_id INT,
    customer_id INT
);

CREATE TABLE product (
    id INT PRIMARY KEY,
    name VARCHAR(20)
);

CREATE TABLE customer (
    id INT PRIMARY KEY,
    name VARCHAR(20)
);
���֮��myorder ���е� product_id �� customer_id ��ȫ������ order_id ������
�� product �� customer ���е������ֶ�����ȫ�����������������˵ڶ���ʽ����ƣ�

3.������ʽ
������ڶ���ʽ��ǰ���£�����������֮�⣬������֮�䲻���д���������ϵ��
CREATE TABLE myorder (
    order_id INT PRIMARY KEY,
    product_id INT,
    customer_id INT,
    customer_phone VARCHAR(15)
);
���е� customer_phone �п��������� order_id �� customer_id ���У�
Ҳ�Ͳ������˵�����ʽ����ƣ�������֮�䲻���д���������ϵ��

CREATE TABLE myorder (
    order_id INT PRIMARY KEY,
    product_id INT,
    customer_id INT
);

CREATE TABLE customer (
    id INT PRIMARY KEY,
    name VARCHAR(20),
    phone VARCHAR(15)
);
�޸ĺ�Ͳ�����������֮��Ĵ���������ϵ�������ж�ֻ�����������У������˵�����ʽ����ƣ�

#mysql��ѯ��ϰ
1.������
ѧ����Student
ѧ��
����
�Ա�
����������
���ڰ༶
		create table Student(
			sno varchar(20) primary key,
			sname varchar(20) not null,
			ssex varchar(10) not null,
			sbirthday datetime,
			class varchar(20)
		);
�γ̱�Course
�γ̺�
�γ�����
��ʦ���
		create table Course(
			cno varchar(20) primary key,
			cname varchar(20) not null,
			tno varchar(20) not null,
			foreign key(tno) references teacher(tno)
		);

�ɼ���Score
ѧ��
�γ̺�
�ɼ�
		create table score(
			sno varchar(20),
			cno varchar(20) not null,
			degree decimal,
			foreign key(sno) references student(sno),
			foreign key(cno) references course(cno),
			primary key(sno,cno)
		);

��ʦ��Teacher
��ʦ���
��ʦ����
��ʦ�Ա�
����������
ְ��
���ڲ���
		create table teacher(
			tno varchar(20) primary key,
			tname varchar(20) not null,
			tsex varchar(10) not null,
			tbirthday datetime,
			tprof varchar(20) not null,
			depart varchar(20) not null
		);
2.�����ݱ����������
���ѧ����Ϣ
-- ���ѧ��������
INSERT INTO student VALUES('101', '����', '��', '1977-09-01', '95033');
INSERT INTO student VALUES('102', '����', '��', '1975-10-02', '95031');
INSERT INTO student VALUES('103', '����', 'Ů', '1976-01-23', '95033');
INSERT INTO student VALUES('104', '���', '��', '1976-02-20', '95033');
INSERT INTO student VALUES('105', '����', 'Ů', '1975-02-10', '95031');
INSERT INTO student VALUES('106', '½��', '��', '1974-06-03', '95031');
INSERT INTO student VALUES('107', '������', '��', '1976-02-20', '95033');
INSERT INTO student VALUES('108', '��ȫ��', '��', '1975-02-10', '95031');
INSERT INTO student VALUES('109', '������', '��', '1974-06-03', '95031');	

-- ��ӽ�ʦ������
INSERT INTO teacher VALUES('804', '���', '��', '1958-12-02', '������', '�����ϵ');
INSERT INTO teacher VALUES('856', '����', '��', '1969-03-12', '��ʦ', '���ӹ���ϵ');
INSERT INTO teacher VALUES('825', '��Ƽ', 'Ů', '1972-05-05', '����', '�����ϵ');
INSERT INTO teacher VALUES('831', '����', 'Ů', '1977-08-14', '����', '���ӹ���ϵ');

-- ��ӿγ̱�����
INSERT INTO course VALUES('3-105', '���������', '825');
INSERT INTO course VALUES('3-245', '����ϵͳ', '804');
INSERT INTO course VALUES('6-166', '���ֵ�·', '856');
INSERT INTO course VALUES('9-888', '�ߵ���ѧ', '831');

-- �����ӳɼ�������
INSERT INTO score VALUES('103', '3-105', '92');
INSERT INTO score VALUES('103', '3-245', '86');
INSERT INTO score VALUES('103', '6-166', '85');
INSERT INTO score VALUES('105', '3-105', '88');
INSERT INTO score VALUES('105', '3-245', '75');
INSERT INTO score VALUES('105', '6-166', '79');
INSERT INTO score VALUES('109', '3-105', '76');
INSERT INTO score VALUES('109', '3-245', '68');
INSERT INTO score VALUES('109', '6-166', '81');

-- �鿴��ṹ
SELECT * FROM course;
SELECT * FROM score;
SELECT * FROM student;
SELECT * FROM teacher;

#ʮ���ѯ��ϰ
1.-- ��ѯ student ���������
SELECT * FROM student;

2.-- ��ѯ student ���е� name��sex �� class �ֶε���
SELECT name, sex, class FROM student;

3.-- ��ѯ teacher ���е�λ�����ظ��Ĳ��ţ����в��ظ��� department ��
-- department: ȥ�ز�ѯ
SELECT DISTINCT department FROM teacher;

4.-- ��ѯ score ���гɼ���60-80֮��������У������ѯ���������ѯ��
-- BETWEEN xx AND xx: ��ѯ����, AND ��ʾ "����"
SELECT * FROM score WHERE degree BETWEEN 60 AND 80;
SELECT * FROM score WHERE degree > 60 AND degree < 80;

5.-- ��ѯ score ���гɼ�Ϊ 85, 86 �� 88 ����
-- IN: ��ѯ�涨�еĶ��ֵ
SELECT * FROM score WHERE degree IN (85, 86, 88);

6.-- ��ѯ student ���� '95031' ����Ա�Ϊ 'Ů' ��������
-- or: ��ʾ���߹�ϵ
SELECT * FROM student WHERE class = '95031' or sex = 'Ů';

7.-- �� class ����ķ�ʽ��ѯ student ���������
-- DESC: ���򣬴Ӹߵ���
-- ASC��Ĭ�ϣ�: ���򣬴ӵ͵���
SELECT * FROM student ORDER BY class DESC;
SELECT * FROM student ORDER BY class ASC;

8.-- �� cno ����degree �����ѯ score ���������
SELECT * FROM score ORDER BY c_no ASC, degree DESC;

9.-- ��ѯ "95031" ���ѧ������
-- COUNT: ͳ��
SELECT COUNT(*) FROM student WHERE class = '95031';

10.-- ��ѯ score ���е���߷ֵ�ѧ��ѧ�źͿγ̱�ţ��Ӳ�ѯ�������ѯ����
-- (SELECT MAX(degree) FROM score): �Ӳ�ѯ�������߷�
SELECT sno, cno FROM score WHERE degree = (SELECT MAX(degree) FROM score);
	�����ҵ���߷� SELECT MAX(degree) FROM score
	����߷ֵ�sno��cno   SELECT sno, cno FROM score WHERE degree = (?)
	SELECT sno, cno,degree FROM score order by degree;

11.--  �����ѯ��߷ֻ�����ͷ���ȱ�ݣ���Ϊ���ܴ��ڶ����߷ֻ�����ͷ�
-- LIMIT r, n: ��ʾ�ӵ�r�п�ʼ����ѯn������
SELECT sno, cno, degree FROM score ORDER BY degree DESC LIMIT 0, 1;

#�������ƽ���ɼ�
#��ѯÿ�ſε�ƽ���ɼ���

11.-- AVG: ƽ��ֵ
SELECT AVG(degree) FROM score WHERE cno = '3-105';
SELECT AVG(degree) FROM score WHERE cno = '3-245';
SELECT AVG(degree) FROM score WHERE cno = '6-166';

12.-- GROUP BY: �����ѯ
SELECT cno, AVG(degree) FROM score GROUP BY cno;

13.--��ѯscore����������2��ѡ��ѡ�޵Ĳ���3��ͷ�Ŀγ̵�ƽ������
�������֣������� 2 ��ѧ��ѡ�޵Ŀγ��� 3-105 ��3-245 ��6-166 ���� 3 ��ͷ�Ŀγ��� 3-105 ��3-245 ��
Ҳ����˵������Ҫ��ѯ���� 3-105 �� 3-245 �� degree ƽ���֡�

-- ���Ȱ� cno, AVG(degree) ͨ�������ѯ����
SELECT cno, AVG(degree) FROM score GROUP BY cno
+-------+-------------+
| c_no  | AVG(degree) |
+-------+-------------+
| 3-105 |     85.3333 |
| 3-245 |     76.3333 |
| 6-166 |     81.6667 |
+-------+-------------+

-- �ٲ�ѯ�������� 2 ��ѧ��ѡ�޵Ŀγ�
-- HAVING: ��ʾ����
HAVING COUNT(c_no) >= 2

-- �������� 3 ��ͷ�Ŀγ�
-- LIKE ��ʾģ����ѯ��"%" ��һ��ͨ�����ƥ�� "3" ����������ַ���
AND c_no LIKE '3%';

-- ��ǰ���SQL���ƴ��������
-- �������һ�� COUNT(*)����ʾ��ÿ������ĸ���Ҳ��ѯ������
SELECT c_no, AVG(degree), COUNT(*) FROM score GROUP BY cno
HAVING COUNT(cno) >= 2 AND cno LIKE '3%';
+-------+-------------+----------+
| c_no  | AVG(degree) | COUNT(*) |
+-------+-------------+----------+
| 3-105 |     85.3333 |        3 |
| 3-245 |     76.3333 |        3 |
+-------+-------------+----------+

14.��ѯ��������70��С��90��sno
select sno,degree from score where degree between 70 and 90;

����ѯ��
��ѯ����ѧ����sname��cno����degree
select sname from student;

select cno,degree from score;

select sname,cno,degree from student,score where student.sno=score.sno;

��ѯ����ѧ���� name���Լ���ѧ���� score ���ж�Ӧ�� c_no �� degree ��

SELECT sno, sname FROM student;
+-----+-----------+
| no  | name      |
+-----+-----------+
| 101 | ����      |
| 102 | ����      |
| 103 | ����      |
| 104 | ���      |
| 105 | ����      |
| 106 | ½��      |
| 107 | ������    |
| 108 | ��ȫ��    |
| 109 | ������    |
+-----+-----------+

SELECT sno, cno, degree FROM score;
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 103  | 3-105 |     92 |
| 103  | 3-245 |     86 |
| 103  | 6-166 |     85 |
| 105  | 3-105 |     88 |
| 105  | 3-245 |     75 |
| 105  | 6-166 |     79 |
| 109  | 3-105 |     76 |
| 109  | 3-245 |     68 |
| 109  | 6-166 |     81 |
+------+-------+--------+

ͨ���������Է��֣�ֻҪ�� score ���е� s_no �ֶ�ֵ�滻�� student ���ж�Ӧ�� name �ֶ�ֵ�Ϳ����ˣ�������أ�

-- FROM...: ��ʾ�� student, score ���в�ѯ
-- WHERE ��������ʾΪ��ֻ���� student.no �� score.s_no ���ʱ����ʾ������
SELECT name, c_no, degree FROM student, score 
WHERE student.no = score.s_no;
+-----------+-------+--------+
| name      | c_no  | degree |
+-----------+-------+--------+
| ����      | 3-105 |     92 |
| ����      | 3-245 |     86 |
| ����      | 6-166 |     85 |
| ����      | 3-105 |     88 |
| ����      | 3-245 |     75 |
| ����      | 6-166 |     79 |
| ������    | 3-105 |     76 |
| ������    | 3-245 |     68 |
| ������    | 6-166 |     81 |
+-----------+-------+--------+

15.��ѯ����ѧ����sno��cname��degree��

select sno,cname,degree from course,score where course.cno=score.cno;

select cno,cname from course;

select cno,sno,degree from score;
��ѯ����ѧ���� no ���γ����� ( course ���е� name ) �ͳɼ� ( score ���е� degree ) �С�

ֻҪ score ����ѧ���� sno �����ֻҪ��ѯ score �������ҳ����к�ѧ����ص� sno �� degree ��

SELECT sno, cno, degree FROM score;
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 103  | 3-105 |     92 |
| 103  | 3-245 |     86 |
| 103  | 6-166 |     85 |
| 105  | 3-105 |     88 |
| 105  | 3-245 |     75 |
| 105  | 6-166 |     79 |
| 109  | 3-105 |     76 |
| 109  | 3-245 |     68 |
| 109  | 6-166 |     81 |
+------+-------+--------+
Ȼ���ѯ course ��
 
select cno,cname from course;
+-------+-----------------+
| no    | name            |
+-------+-----------------+
| 3-105 | ���������      |
| 3-245 | ����ϵͳ        |
| 6-166 | ���ֵ�·        |
| 9-888 | �ߵ���ѧ        |
+-------+-----------------+

ֻҪ�� score ���е� c_no �滻�� course ���ж�Ӧ�� name �ֶ�ֵ�Ϳ����ˡ�

-- ����һ����ѯ�ֶ� name���ֱ�� score��course ���������в�ѯ��
-- as ��ʾȡһ�����ֶεı�����
SELECT s_no, name as c_name, degree FROM score, course
		WHERE score.c_no = course.no;
+------+-----------------+--------+
| s_no | c_name          | degree |
+------+-----------------+--------+
| 103  | ���������      |     92 |
| 105  | ���������      |     88 |
| 109  | ���������      |     76 |
| 103  | ����ϵͳ        |     86 |
| 105  | ����ϵͳ        |     75 |
| 109  | ����ϵͳ        |     68 |
| 103  | ���ֵ�·        |     85 |
| 105  | ���ֵ�·        |     79 |
| 109  | ���ֵ�·        |     81 |
+------+-----------------+--------+

16.��ѯ����ѧ����sname��cname��degree��
sname -> student
cname -> course
degree-> score

SELECT sname,cname,degree FROM student,course,score 
	WHERE student.sno=score.sno AND course.cno=score.cno;
+-----------+-----------------+--------+
| sname     | cname           | degree |
+-----------+-----------------+--------+
| ����      | ���������      |     92 |
| ����      | ���������      |     88 |
| ������    | ���������      |     76 |
| ����      | ����ϵͳ        |     86 |
| ����      | ����ϵͳ        |     75 |
| ������    | ����ϵͳ        |     68 |
| ����      | ���ֵ�·        |     85 |
| ����      | ���ֵ�·        |     79 |
| ������    | ���ֵ�·        |     81 |
+-----------+-----------------+--------+
9 rows in set (0.00 sec)


select sname,cname,degree,student.sno, course.cno from student,course,score 
	where student.sno=score.sno and course.cno=score.cno;
+-----------+-----------------+--------+-----+-------+
| sname     | cname           | degree | sno | cno   |
+-----------+-----------------+--------+-----+-------+
| ����      | ���������      |     92 | 103 | 3-105 |
| ����      | ���������      |     88 | 105 | 3-105 |
| ������    | ���������      |     76 | 109 | 3-105 |
| ����      | ����ϵͳ        |     86 | 103 | 3-245 |
| ����      | ����ϵͳ        |     75 | 105 | 3-245 |
| ������    | ����ϵͳ        |     68 | 109 | 3-245 |
| ����      | ���ֵ�·        |     85 | 103 | 6-166 |
| ����      | ���ֵ�·        |     79 | 105 | 6-166 |
| ������    | ���ֵ�·        |     81 | 109 | 6-166 |
+-----------+-----------------+--------+-----+-------+

select sname,cname,degree,student.sno as stu_sno, score.sno, course.cno as cou_cno 
		from student,course,score where student.sno=score.sno and course.cno=score.cno;
+-----------+-----------------+--------+---------+-----+---------+
| sname     | cname           | degree | stu_sno | sno | cou_cno |
+-----------+-----------------+--------+---------+-----+---------+
| ����      | ���������      |     92 | 103     | 103 | 3-105   |
| ����      | ���������      |     88 | 105     | 105 | 3-105   |
| ������    | ���������      |     76 | 109     | 109 | 3-105   |
| ����      | ����ϵͳ        |     86 | 103     | 103 | 3-245   |
| ����      | ����ϵͳ        |     75 | 105     | 105 | 3-245   |
| ������    | ����ϵͳ        |     68 | 109     | 109 | 3-245   |
| ����      | ���ֵ�·        |     85 | 103     | 103 | 6-166   |
| ����      | ���ֵ�·        |     79 | 105     | 105 | 6-166   |
| ������    | ���ֵ�·        |     81 | 109     | 109 | 6-166   |
+-----------+-----------------+--------+---------+-----+---------+
9 rows in set (0.00 sec)		

17.��ѯ��95031����ѧ��ÿ�ſε�ƽ����
select*from student where class='95031';
mysql> select*from student where class='95031';
+-----+-----------+------+---------------------+-------+
| sno | sname     | ssex | sbirthday           | class |
+-----+-----------+------+---------------------+-------+
| 102 | ����      | ��   | 1975-10-02 00:00:00 | 95031 |
| 105 | ����      | Ů   | 1975-02-10 00:00:00 | 95031 |
| 106 | ½��      | ��   | 1974-06-03 00:00:00 | 95031 |
| 108 | ��ȫ��    | ��   | 1975-02-10 00:00:00 | 95031 |
| 109 | ������    | ��   | 1974-06-03 00:00:00 | 95031 |
+-----+-----------+------+---------------------+-------+
5 rows in set (0.00 sec)

select *from score where sno in(select sno from student where class='95031');
mysql> select *from score where sno in(select sno from student where class='95031');
+-----+-------+--------+
| sno | cno   | degree |
+-----+-------+--------+
| 105 | 3-105 |     88 |
| 105 | 3-245 |     75 |
| 105 | 6-166 |     79 |
| 109 | 3-105 |     76 |
| 109 | 3-245 |     68 |
| 109 | 6-166 |     81 |
+-----+-------+--------+
6 rows in set (0.01 sec)

select cno,avg(degree) from score where sno in(select sno from student where class='95031')
		group by cno;
+-------+-------------+
| cno   | avg(degree) |
+-------+-------------+
| 3-105 |     82.0000 |
| 3-245 |     71.5000 |
| 6-166 |     80.0000 |
+-------+-------------+
3 rows in set (0.00 sec)

18.��ѯѡ�ޡ�3-105���γ̵ĳɼ����ڡ�109����ͬѧ��3-105���ɼ�������ͬѧ�ļ�¼
��ѯ�� 3-105 �γ��У����гɼ����� 109 ��ͬѧ�ļ�¼��
select degree from score where sno='109' and cno='3-105';
����ɸѡ�����ú�Ϊ 3-105 �����ҳ����гɼ����� 109 ��ͬѧ�ĵ��С�

SELECT * FROM score 
WHERE cno = '3-105'
AND degree > (SELECT degree FROM score WHERE sno = '109' AND cno = '3-105');
+-----+-------+--------+
| sno | cno   | degree |
+-----+-------+--------+
| 103 | 3-105 |     92 |
| 105 | 3-105 |     88 |
+-----+-------+--------+

19.��ѯ�ɼ�����ѧ��Ϊ��109�����γ̺�Ϊ��3-105���ĳɼ������м�¼

SELECT * FROM score 
WHERE degree > (SELECT degree FROM score WHERE sno = '109' AND cno = '3-105');

+-----+-------+--------+
| sno | cno   | degree |
+-----+-------+--------+
| 103 | 3-105 |     92 |
| 103 | 3-245 |     86 |
| 103 | 6-166 |     85 |
| 105 | 3-105 |     88 |
| 105 | 6-166 |     79 |
| 109 | 6-166 |     81 |
+-----+-------+--------+
6 rows in set (0.00 sec)

20.��ѯ��ѧ��Ϊ108��101��ͬѧͬ�����������ѧ����sno��sname��sbirthday��
-- YEAR(..): ȡ�������е����

SELECT sno, sname, sbirthday FROM student
WHERE YEAR(sbirthday) IN (SELECT YEAR(sbirthday) FROM student WHERE sno IN (101, 108));

21.��ѯ�����񡯽�ʦ�οε�ѧ���ɼ�
����teacher�����õ�������ʦ�Ľ�ʦ���
select tno from teacher where tname='����';
�õ�course���н�ʦ��Ŷ�Ӧ�Ŀγ̱��
select cno from course where tno=(select tno from teacher where tname='����');
��score�����õ���Ӧ�γ̱�Ŷ�Ӧ�����Ϣ
select*from score cno=(select cno from course where tno=(select tno from teacher where tname='����'));

22.��ѯĳѡ�޿γ̶���5��ͬѧ�Ľ�ʦ����
�����ޣ��Ȱ���С���Ӳ�ѯд�ã�Ȼ��ŵ���Χ�ϴ�Ĳ�ѯ���Դ�����
select cno from score group by cno having count(*)>5;

select*from teacher;

select*from course;

select tno from course where cno=
		(select cno from score group by cno having count(*)>5);

select tname from teacher where tno=
		(select tno from course where cno=(select cno from score group by cno having count(*)>5));
		
23.��ѯ95033���95031��ȫ��ѧ����¼
select*from student where class in ('95031','95033');

24.��ѯ������85�����ϳɼ���cno
select*from score where degree>85;

25.��ѯ �����ϵ ��ʦ���̿γ̵ĳɼ���

select*from teacher where depart='�����ϵ';

tno	tname	tsex	tbirthday	tprof	depart
804	���	��	1958-12-02 00:00:00	������	�����ϵ
825	��Ƽ	Ů	1972-05-05 00:00:00	����	�����ϵ

select*from course where tno in(select tno from teacher where depart='�����ϵ');

cno	cname	tno
3-105	���������	825
3-245	����ϵͳ	804

select*from score where cno in 
		(select cno from course where tno in(select tno from teacher where depart='�����ϵ'));
		
sno	cno	degree
101	3-105	90
102	3-105	91
103	3-105	92
103	3-245	86
104	3-105	89
105	3-105	88
105	3-245	75
109	3-105	76
109	3-245	68

#UNION �� NOTIN ��ʹ��
26.��ѯ �����ϵ �� ���ӹ���ϵ �еĲ�ְͬ�ƵĽ�ʦ��
-- UNION �󲢼�
-- NOT: �����߼���
--�Ȳ�ѯ�����ϵ��ְ��������ӹ���ϵ��ְ�Ʋ�ͬ�Ľ�ʦ
SELECT * FROM teacher WHERE depart = '�����ϵ' AND tprof NOT IN (
    SELECT tprof FROM teacher WHERE depart = '���ӹ���ϵ'
)
--��ѯ���ӹ���ϵ��ְ����������ϵ��ְ�Ʋ�ͬ�Ľ�ʦ
-- �ϲ������������ºϲ�
UNION
SELECT * FROM teacher WHERE depart = '���ӹ���ϵ' AND tprof NOT IN (
    SELECT tprof FROM teacher WHERE depart = '�����ϵ'
);

ANY ��ʾ����һ�� - DESC ( ���� )
27.��ѯ�γ� 3-105 �ҳɼ� ���� ���� 3-245 �� score ��

SELECT * FROM score WHERE c_no = '3-105';
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 101  | 3-105 |     90 |
| 102  | 3-105 |     91 |
| 103  | 3-105 |     92 |
| 104  | 3-105 |     89 |
| 105  | 3-105 |     88 |
| 109  | 3-105 |     76 |
+------+-------+--------+

SELECT * FROM score WHERE c_no = '3-245';
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 103  | 3-245 |     86 |
| 105  | 3-245 |     75 |
| 109  | 3-245 |     68 |
+------+-------+--------+

���٣�������������һ��

-- ANY: ����SQL����е�����������
-- Ҳ����˵���� 3-105 �ɼ��У�ֻҪ��һ�����ڴ� 3-245 ɸѡ�����������оͷ���������
-- �����ݽ����ѯ�����
SELECT * FROM score WHERE cno = '3-105' AND degree > ANY(
    SELECT degree FROM score WHERE cno = '3-245'
) ORDER BY degree DESC;
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 103  | 3-105 |     92 |
| 102  | 3-105 |     91 |
| 101  | 3-105 |     90 |
| 104  | 3-105 |     89 |
| 105  | 3-105 |     88 |
| 109  | 3-105 |     76 |
+------+-------+--------+

��ʾ���е� ALL
28.��ѯ�γ� 3-105 �ҳɼ����� 3-245 �� score ��

-- ֻ�����һ���������޸ġ�
-- ALL: ����SQL����е�����������
-- Ҳ����˵���� 3-105 ÿһ�гɼ��У���Ҫ���ڴ� 3-245 ɸѡ����ȫ���в������������
SELECT * FROM score WHERE cno = '3-105' AND degree > ALL(
    SELECT degree FROM score WHERE cno = '3-245'
);
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 101  | 3-105 |     90 |
| 102  | 3-105 |     91 |
| 103  | 3-105 |     92 |
| 104  | 3-105 |     89 |
| 105  | 3-105 |     88 |
+------+-------+--------+

29.��ѯ���н�ʦ��ͬѧ��name��sex��birthday
select tname as name,tsex as sex,tbirthday as birthday from teacher
union
select sname,ssex,sbirthday from student;

��ѯ����Ů��ʦ��Ůͬѧ��name��sex��birthday
select tname as name,tsex as sex,tbirthday as birthday from teacher where tsex='Ů'
union
select sname,ssex,sbirthday from student where ssex='Ů';


30.��ѯ�ɼ��ȸÿγ�ƽ���ɼ��͵�ͬѧ�� score ��

-- ��ѯƽ����
SELECT cno, AVG(degree) FROM score GROUP BY cno;
+-------+-------------+
| c_no  | AVG(degree) |
+-------+-------------+
| 3-105 |     87.6667 |
| 3-245 |     76.3333 |
| 6-166 |     81.6667 |
+-------+-------------+

-- ��ѯ score ��
SELECT degree FROM score;
a��			b��
+--------+	+--------+
| degree |	| degree |
+--------+	+--------+
|     90 |	|     90 |
|     91 |	|     91 |
|     92 |	|     92 |
|     86 |	|     86 |
|     85 |	|     85 |
|     89 |	|     89 |
|     88 |	|     88 |
|     75 |	|     75 |
|     79 |	|     79 |
|     76 |	|     76 |
|     68 |	|     68 |
|     81 |	|     81 |
+--------+	+--------+

-- ���� b �����ڱ� a �в�ѯ����
-- score a (b): ��������Ϊ a (b)��
-- ��˾����� a.c_no = b.c_no ��Ϊ����ִ�в�ѯ�ˡ�
SELECT * FROM score a WHERE degree < (
    (SELECT AVG(degree) FROM score b WHERE a.cno = b.cno)
);
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 105  | 3-245 |     75 |
| 105  | 6-166 |     79 |
| 109  | 3-105 |     76 |
| 109  | 3-245 |     68 |
| 109  | 6-166 |     81 |
+------+-------+--------+

31.��ѯ�����ο� ( �� course �����пγ� ) ��ʦ�� name �� department ��

SELECT name, department FROM teacher WHERE no IN (SELECT tno FROM course);
+--------+-----------------+
| name   | department      |
+--------+-----------------+
| ���   | �����ϵ        |
| ��Ƽ   | �����ϵ        |
| ����   | ���ӹ���ϵ      |
| ����   | ���ӹ���ϵ      |
+--------+-----------------+

��������ɸѡ
32.��ѯ student ���������� 2 �������� class
-- �鿴ѧ������Ϣ
SELECT * FROM student;
+-----+-----------+-----+------------+-------+
| no  | name      | sex | birthday   | class |
+-----+-----------+-----+------------+-------+
| 101 | ����      | ��  | 1977-09-01 | 95033 |
| 102 | ����      | ��  | 1975-10-02 | 95031 |
| 103 | ����      | Ů  | 1976-01-23 | 95033 |
| 104 | ���      | ��  | 1976-02-20 | 95033 |
| 105 | ����      | Ů  | 1975-02-10 | 95031 |
| 106 | ½��      | ��  | 1974-06-03 | 95031 |
| 107 | ������    | ��  | 1976-02-20 | 95033 |
| 108 | ��ȫ��    | ��  | 1975-02-10 | 95031 |
| 109 | ������    | ��  | 1974-06-03 | 95031 |
| 110 | �ŷ�      | ��  | 1974-06-03 | 95038 |
+-----+-----------+-----+------------+-------+

-- ֻ��ѯ�Ա�Ϊ�У�Ȼ�� class ���飬������ class �д��� 1��
SELECT class FROM student WHERE ssex = '��' GROUP BY class HAVING COUNT(*) > 1;
+-------+
| class |
+-------+
| 95033 |
| 95031 |
+-------+

NOTLIKE ģ����ѯȡ��
33.��ѯ student ���в��� "��" ��ͬѧ��¼��

-- NOT: ȡ��
-- LIKE: ģ����ѯ
mysql> SELECT * FROM student WHERE sname NOT LIKE '��%';
+-----+-----------+-----+------------+-------+
| no  | name      | sex | birthday   | class |
+-----+-----------+-----+------------+-------+
| 101 | ����      | ��  | 1977-09-01 | 95033 |
| 102 | ����      | ��  | 1975-10-02 | 95031 |
| 104 | ���      | ��  | 1976-02-20 | 95033 |
| 106 | ½��      | ��  | 1974-06-03 | 95031 |
| 108 | ��ȫ��    | ��  | 1975-02-10 | 95031 |
| 109 | ������    | ��  | 1974-06-03 | 95031 |
| 110 | �ŷ�      | ��  | 1974-06-03 | 95038 |
+-----+-----------+-----+------------+-------+

YEAR �� NOW ����
34.��ѯ student ����ÿ��ѧ�������������䡣
SELECT YEAR(NOW());

year(now())
2020

-- ʹ�ú��� YEAR(NOW()) �������ǰ��ݣ���ȥ������ݺ�ó����䡣
SELECT name, YEAR(NOW()) - YEAR(sbirthday) as age FROM student;
+-----------+------+
| name      | age  |
+-----------+------+
| ����      |   42 |
| ����      |   44 |
| ����      |   43 |
| ���      |   43 |
| ����      |   44 |
| ½��      |   45 |
| ������    |   43 |
| ��ȫ��    |   44 |
| ������    |   45 |
| �ŷ�      |   45 |
+-----------+------+

MAX �� MIN ����
35.��ѯ student ����������С�� birthday ֵ��

SELECT MAX(sbirthday), MIN(sbirthday) FROM student;
+---------------+---------------+
| MAX(birthday) | MIN(birthday) |
+---------------+---------------+
| 1977-09-01    | 1974-06-03    |
+---------------+---------------+

�������
�� class �� birthday �Ӵ�С��˳���ѯ student ��

SELECT * FROM student ORDER BY class DESC, sbirthday;
+-----+-----------+-----+------------+-------+
| no  | name      | sex | birthday   | class |
+-----+-----------+-----+------------+-------+
| 110 | �ŷ�      | ��  | 1974-06-03 | 95038 |
| 103 | ����      | Ů  | 1976-01-23 | 95033 |
| 104 | ���      | ��  | 1976-02-20 | 95033 |
| 107 | ������    | ��  | 1976-02-20 | 95033 |
| 101 | ����      | ��  | 1977-09-01 | 95033 |
| 106 | ½��      | ��  | 1974-06-03 | 95031 |
| 109 | ������    | ��  | 1974-06-03 | 95031 |
| 105 | ����      | Ů  | 1975-02-10 | 95031 |
| 108 | ��ȫ��    | ��  | 1975-02-10 | 95031 |
| 102 | ����      | ��  | 1975-10-02 | 95031 |
+-----+-----------+-----+------------+-------+

��ѯ "��" ��ʦ�������ϵĿγ̡�

SELECT * FROM course WHERE tno in (SELECT tno FROM teacher WHERE sex = '��');
+-------+--------------+------+
| no    | name         | t_no |
+-------+--------------+------+
| 3-245 | ����ϵͳ     | 804  |
| 6-166 | ���ֵ�·     | 856  |
+-------+--------------+------+

SELECT cno, cname, course.tno, tname,tsex FROM course,teacher WHERE 
		course.tno=teacher.tno and 
		course.tno in (SELECT tno from teacher WHERE tsex = '��');

cno	cname	tno	tname	tsex
3-245	����ϵͳ	804	���	��
6-166	���ֵ�·	856	����	��


MAX �������Ӳ�ѯ
36.��ѯ��߷�ͬѧ�� score ��

-- �ҳ���߳ɼ����ò�ѯֻ����һ�������
SELECT MAX(degree) FROM score;

-- �������������ɸѡ��������߳ɼ���
-- �ò�ѯ�����ж����������� degree ֵ��η���������
SELECT * FROM score WHERE degree = (SELECT MAX(degree) FROM score);
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 103  | 3-105 |     92 |
+------+-------+--------+


37.��ѯ�� "���" ͬ�Ա������ͬѧ name ��
-- ���Ƚ�������Ա���Ϊ����ȡ����
SELECT sex FROM student WHERE name = '���';
+-----+
| sex |
+-----+
| ��  |
+-----+

-- �����Ա��ѯ name �� sex
SELECT name, sex FROM student WHERE ssex = (
    SELECT sex FROM student WHERE name = '���'
);
+-----------+-----+
| name      | sex |
+-----------+-----+
| ����      | ��  |
| ����      | ��  |
| ���      | ��  |
| ½��      | ��  |
| ������    | ��  |
| ��ȫ��    | ��  |
| ������    | ��  |
| �ŷ�      | ��  |
+-----------+-----+

37.��ѯ�� "���" ͬ�Ա���ͬ���ͬѧ name ��

SELECT name, sex, class FROM student WHERE sex = (
    SELECT sex FROM student WHERE name = '���'
) AND class = (
    SELECT class FROM student WHERE name = '���'
);
+-----------+-----+-------+
| name      | sex | class |
+-----------+-----+-------+
| ����      | ��  | 95033 |
| ���      | ��  | 95033 |
| ������    | ��  | 95033 |
+-----------+-----+-------+
		
38.��ѯ����ѡ�� "���������" �γ̵� "��" ͬѧ�ɼ���

��Ҫ�� "���������" ���Ա�Ϊ "��" �ı�ſ����� course �� student �����ҵ���

SELECT * FROM score WHERE cno = (
    SELECT no FROM course WHERE name = '���������'
) AND sno IN (
    SELECT sno FROM student WHERE sex = '��'
);
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 101  | 3-105 |     90 |
| 102  | 3-105 |     91 |
| 104  | 3-105 |     89 |
| 109  | 3-105 |     76 |
+------+-------+--------+

#���ȼ���ѯ
����һ�� grade �����ѧ���ĳɼ��ȼ������������ݣ�

CREATE TABLE grade (
    low INT(3),
    upp INT(3),
    grade char(1)
);

INSERT INTO grade VALUES (90, 100, 'A');
INSERT INTO grade VALUES (80, 89, 'B');
INSERT INTO grade VALUES (70, 79, 'C');
INSERT INTO grade VALUES (60, 69, 'D');
INSERT INTO grade VALUES (0, 59, 'E');

SELECT * FROM grade;
+------+------+-------+
| low  | upp  | grade |
+------+------+-------+
|   90 |  100 | A     |
|   80 |   89 | B     |
|   70 |   79 | C     |
|   60 |   69 | D     |
|    0 |   59 | E     |
+------+------+-------+

39.��ѯ����ѧ���� s_no ��c_no �� grade �С�

˼·�ǣ�ʹ������ ( BETWEEN ) ��ѯ���ж�ѧ���ĳɼ� ( degree ) �� grade ��� low �� upp ֮�䡣

SELECT sno, cno, grade FROM score, grade 
WHERE degree BETWEEN low AND upp;
+------+-------+-------+
| s_no | c_no  | grade |
+------+-------+-------+
| 101  | 3-105 | A     |
| 102  | 3-105 | A     |
| 103  | 3-105 | A     |
| 103  | 3-245 | B     |
| 103  | 6-166 | B     |
| 104  | 3-105 | B     |
| 105  | 3-105 | B     |
| 105  | 3-245 | C     |
| 105  | 6-166 | C     |
| 109  | 3-105 | C     |
| 109  | 3-245 | D     |
| 109  | 6-166 | B     |
+------+-------+-------+

#sql���������Ӳ�ѯ
������
inner join ���� join

������
	������ left join ���� left outer join
	������ right join ���� right outer join
	��ȫ������ full join ���� full outer join
	
׼�����ڲ������Ӳ�ѯ�����ݣ�

CREATE DATABASE testJoin;

CREATE TABLE person (
    id INT,
    name VARCHAR(20),
    cardId INT
);


CREATE TABLE card (
    id INT,
    name VARCHAR(20)
);
INSERT INTO card VALUES (1, '����'), (2, '���п�'), (3, 'ũ�п�'), (4, '���̿�'), (5, '������');
INSERT INTO person VALUES (1, '����', 1), (2, '����', 3), (3, '����', 6);

�������ű��֣�person ��û��Ϊ cardId �ֶ�����һ���� card ���ж�Ӧ�� id �����
	��������˵Ļ���person �� cardId �ֶ�ֵΪ 6 ���оͲ岻��ȥ��
	��Ϊ�� cardId ֵ�� card ���в�û�С�
	
#������
Ҫ��ѯ�����ű����й�ϵ�����ݣ�����ʹ�� INNER JOIN ( ������ ) ������������һ��
������ѯ����ʵ����ͨ�����ű��е����ݣ�ͨ��ĳ���ֶ���ȣ���ѯ����ؼ�¼

-- INNER JOIN: ��ʾΪ�����ӣ������ű�ƴ����һ��
-- on: ��ʾҪִ��ĳ��������
SELECT * FROM person INNER JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | ����   |      1 |    1 | ����      |
|    2 | ����   |      3 |    3 | ũ�п�    |
+------+--------+--------+------+-----------+

-- �� INNER �ؼ���ʡ�Ե������Ҳ��һ���ġ�
-- SELECT * FROM person JOIN card on person.cardId = card.id;
ע�⣺card �����ű����ӵ����ұߡ�

#��������
������ʾ��ߵı� ( person ) ���ұߵı����������������ʾ���������� NULL ��

-- LEFT JOIN Ҳ���� LEFT OUTER JOIN���������ַ�ʽ�Ĳ�ѯ�����һ���ġ�
SELECT * FROM person LEFT JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | ����   |      1 |    1 | ����      |
|    2 | ����   |      3 |    3 | ũ�п�    |
|    3 | ����   |      6 | NULL | NULL      |
+------+--------+--------+------+-----------+

#��������
������ʾ�ұߵı� ( card ) ����ߵı����������������ʾ���������� NULL ��

SELECT * FROM person RIGHT JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | ����   |      1 |    1 | ����      |
|    2 | ����   |      3 |    3 | ũ�п�    |
| NULL | NULL   |   NULL |    2 | ���п�    |
| NULL | NULL   |   NULL |    4 | ���̿�    |
| NULL | NULL   |   NULL |    5 | ������    |
+------+--------+--------+------+-----------+

#ȫ������
������ʾ���ű��ȫ�����ݡ�

-- MySQL ��֧�������﷨��ȫ������
-- SELECT * FROM person FULL JOIN card on person.cardId = card.id;
-- ���ִ���
-- ERROR 1054 (42S22): Unknown column 'person.cardId' in 'on clause'

-- MySQLȫ�����﷨��ʹ�� UNION �����ű�ϲ���һ��
SELECT * FROM person LEFT JOIN card on person.cardId = card.id
UNION
SELECT * FROM person RIGHT JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | ����   |      1 |    1 | ����      |
|    2 | ����   |      3 |    3 | ũ�п�    |
|    3 | ����   |      6 | NULL | NULL      |
| NULL | NULL   |   NULL |    2 | ���п�    |
| NULL | NULL   |   NULL |    4 | ���̿�    |
| NULL | NULL   |   NULL |    5 | ������    |
+------+--------+--------+------+-----------+

#mysql����
������ʵ����С�Ĳ��ɷָ�Ĺ�����Ԫ���ܹ���֤ҵ���������

�������ǵ�����ת�ˣ�
-- a -> -100
UPDATE user set money = money - 100 WHERE name = 'a';
-- b -> +100
UPDATE user set money = money + 100 WHERE name = 'b';
��ʵ����Ŀ�У�����ֻ��һ�� SQL ���ִ�гɹ���������һ��ִ��ʧ���ˣ��ͻ��������ǰ��һ�¡�
��ˣ�ʵ�ʿ���������ִ�ж����й��� SQL ���ʱ��������ܻ�Ҫ����Щ SQL ���Ҫôͬʱִ�гɹ���
																Ҫô�Ͷ�ִ��ʧ�ܡ�
																
��ο������� - COMMIT / ROLLBACK
�� MySQL �У�������Զ��ύ״̬Ĭ���ǿ����ġ�

-- ��ѯ������Զ��ύ״̬
SELECT @@AUTOCOMMIT;
+--------------+
| @@AUTOCOMMIT |
+--------------+
|            1 |
+--------------+
�Զ��ύ�����ã�������ִ��һ�� SQL ����ʱ���������Ч���ͻ��������ֳ������Ҳ��ܻع���
ʲô�ǻع����ٸ����ӣ�
CREATE DATABASE bank;
USE bank;
CREATE TABLE user (
    id INT PRIMARY KEY,
    name VARCHAR(20),
    money INT
);

INSERT INTO user VALUES (1, 'a', 1000);

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+


���Կ�������ִ�в�����������������Ч��ԭ���� MySQL �е������Զ������ύ�������ݿ��С�
��ô��ν�ع�����˼���ǣ�����ִ�й������� SQL ��䣬ʹ��ع������һ���ύ����ʱ��״̬��

�� MySQL ��ʹ�� ROLLBACK ִ�лع���

-- �ع������һ���ύ
ROLLBACK;

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+

��������ִ�й��� SQL ��䶼�Ѿ����ύ���ˣ��������ݲ�û�з����ع�������������ݿ��Է����ع���

-- �ر��Զ��ύ
SET AUTOCOMMIT = 0;

-- ��ѯ�Զ��ύ״̬
SELECT @@AUTOCOMMIT;
+--------------+
| @@AUTOCOMMIT |
+--------------+
|            0 |
+--------------+

//���Զ��ύ�رպ󣬲������ݻع���
INSERT INTO user VALUES (2, 'b', 1000);

-- �ر� AUTOCOMMIT �����ݵı仯����һ���������ʱ���ݱ���չʾ��
-- �����仯�����ݲ�û���������뵽���ݱ��С�
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+

-- ���ݱ��е���ʵ������ʵ���ǣ�
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+

-- �������ݻ�û�������ύ������ʹ�ûع�
ROLLBACK;

-- �ٴβ�ѯ
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+

//����ν���������������ύ�����ݿ��У�ʹ�� COMMIT :

INSERT INTO user VALUES (2, 'b', 1000);
-- �ֶ��ύ���ݣ��־��ԣ���
-- �����������ύ�����ݿ��У�ִ�к����ٻع��ύ�������ݡ�
COMMIT;

-- �ύ����Իع�
ROLLBACK;

-- �ٴβ�ѯ���ع���Ч�ˣ�
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+

������� 
	�Զ��ύ @@autocommit = 1
	�ֶ��ύ commit
	�ֶ��ع� rollback
  �ܽ�
	�Զ��ύ
	�鿴�Զ��ύ״̬��SELECT @@AUTOCOMMIT ��
	�����Զ��ύ״̬��SET AUTOCOMMIT = 0 ��
	�ֶ��ύ
	@@AUTOCOMMIT = 0 ʱ��ʹ�� COMMIT �����ύ����
	����ع�
	@@AUTOCOMMIT = 0 ʱ��ʹ�� ROLLBACK ����ع�����
	
	������ʱ��ת�ˣ�
		UPDATE user set money = money - 100 WHERE name = 'a';
		UPDATE user set money = money + 100 WHERE name = 'b';
�����ʵ��Ӧ�ã��������ٻص�����ת����Ŀ��

-- ת��
UPDATE user set money = money - 100 WHERE name = 'a';

-- ����
UPDATE user set money = money + 100 WHERE name = 'b';

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |   900 |
|  2 | b    |  1100 |
+----+------+-------+
��ʱ������ת��ʱ���������⣬�Ϳ���ʹ�� ROLLBACK �ع������һ���ύ��״̬��

-- ����ת�˷��������⣬��Ҫ�ع���
ROLLBACK;

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+
��ʱ�����ֻص��˷�������֮ǰ��״̬��Ҳ����˵������������ṩ��һ�����Է��ڵĻ��ᡣ
	��������û�з������⣬��ʱ�����ֶ������������ύ�����ݱ��У�COMMIT ��
	
#�ֶ��������� - BEGIN / START TRANSACTION
�����Ĭ���ύ������ ( @@AUTOCOMMIT = 1 ) �󣬴�ʱ�Ͳ���ʹ������ع��ˡ��������ǻ������ֶ�����һ���������¼���ʹ����Է����ع���

-- ʹ�� BEGIN ���� START TRANSACTION �ֶ�����һ������
-- START TRANSACTION;
BEGIN;
UPDATE user set money = money - 100 WHERE name = 'a';
UPDATE user set money = money + 100 WHERE name = 'b';

-- �����ֶ�����������û�п����Զ��ύ��
-- ��ʱ�����仯��������Ȼ�Ǳ�������һ����ʱ���С�
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |   900 |
|  2 | b    |  1100 |
+----+------+-------+

-- ���Իع�
ROLLBACK;

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+
ʹ�� COMMIT �ύ���ݺ��ύ���޷��ٷ�����������Ļع�����Ϊ�������Ѿ������ˡ�

BEGIN;
UPDATE user set money = money - 100 WHERE name = 'a';
UPDATE user set money = money + 100 WHERE name = 'b';

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |   900 |
|  2 | b    |  1100 |
+----+------+-------+

-- �ύ����
COMMIT;

-- ���Իع�����Ч����Ϊ��������Ѿ����ύ��
ROLLBACK;

#����� ACID ������ʹ��
#������Ĵ�������
A ԭ���ԣ���������С�ĵ�λ���������ٷָ
C һ���ԣ�Ҫ��ͬһ�����е� SQL ��䣬���뱣֤ͬʱ�ɹ�����ʧ�ܣ�
I �����ԣ�����1 �� ����2 ֮���Ǿ��и����Եģ�
D �־��ԣ�����һ������ ( COMMIT ) ���Ͳ������ٷ����� ( ROLLBACK ) ��
�� ԭ���ԣ�Atomicity����������Щָ��ʱ��Ҫôȫ��ִ�гɹ���Ҫôȫ����ִ�С�
			ֻҪ����һ��ָ��ִ��ʧ�ܣ����е�ָ�ִ��ʧ�ܣ����ݽ��лع����ص�ִ��ָ��ǰ������״̬��
eg����ת����˵�������û�A���û�B���ߵ�Ǯ������һ����20000����ô����A��B֮�����ת�ˣ�
			ת�����ˣ���������������û���Ǯ�������Ӧ�û�����20000������������һ���ԡ�
�� һ���ԣ�Consistency���������ִ��ʹ���ݴ�һ��״̬ת��Ϊ��һ��״̬��
			���Ƕ����������ݵ������Ա����ȶ���
�� �����ԣ�Isolation�����������ǵ�����û������������ݿ�ʱ���������ͬһ�ű�ʱ��
			���ݿ�Ϊÿһ���û����������񣬲��ܱ���������Ĳ��������ţ������������֮��Ҫ�໥���롣
			��Ҫ�ﵽ��ôһ��Ч��������������������������T1��T2��
			������T1������T2Ҫô��T1��ʼ֮ǰ���Ѿ�������Ҫô��T1����֮��ſ�ʼ��
			����ÿ�����񶼸о����������������ڲ�����ִ�С�			
�� �־��ԣ�Durability������������ȷ��ɺ����������ݵĸı��������Եġ�
eg�� ����������ʹ��JDBC�������ݿ�ʱ�����ύ���񷽷�����ʾ�û����������ɣ�
			�����ǳ���ִ�����ֱ��������ʾ�󣬾Ϳ����϶������Լ���ȷ�ύ��
			��ʹ��ʱ�����ݿ���������⣬Ҳ����Ҫ�����ǵ�������ȫִ����ɣ�
			����ͻ�������ǿ�����ʾ��������ϣ��������ݿ���Ϊ���϶�û��ִ��������ش����
#������
	1���޸�Ĭ���ύ set autocommit=0��
	2��begin��
	3��start transaction��
�����ֶ��ύ��commit
�����ֶ��ع���rollback(�ڹر����Զ��ύʱ����)

--����ĸ�����
����ĸ����Կɷ�Ϊ���� ( ���ܴӵ͵��� ) ��
1.READ UNCOMMITTED; ( ��ȡδ�ύ )
	����ж��������ô�������񶼿��Կ������������δ�ύ���ݡ�
2.READ COMMITTED; ( ��ȡ���ύ )
	ֻ�ܶ�ȡ�����������Ѿ��ύ�����ݡ�
3.REPEATABLE READ; ( �ɱ��ظ��� )
	����ж�����Ӷ�������������ô����֮�䲻�ܹ������ݼ�¼������ֻ�ܹ������ύ�ļ�¼��
4.SERIALIZABLE; ( ���л� )
	���е����񶼻ᰴ�չ̶�˳��ִ�У�ִ����һ��������ټ���ִ����һ�������д�������

#�鿴��ǰ���ݿ��Ĭ�ϸ��뼶��
-- MySQL 8.x, GLOBAL ��ʾϵͳ���𣬲��ӱ�ʾ�Ự����
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
SELECT @@TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| REPEATABLE-READ                | -- MySQL��Ĭ�ϸ��뼶�𣬿����ظ�����
+--------------------------------+

-- MySQL 5.x
SELECT @@GLOBAL.TX_ISOLATION;
SELECT @@TX_ISOLATION;

#�޸ĸ��뼶��
-- ����ϵͳ���뼶��LEVEL �����ʾҪ���õĸ��뼶�� (READ UNCOMMITTED)��
SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
-- ��ѯϵͳ���뼶�𣬷����Ѿ����޸ġ�
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| READ-UNCOMMITTED               |
+--------------------------------+

//���� READ UNCOMMITTED ( ��ȡδ�ύ ) �ĸ����ԣ�
INSERT INTO user VALUES (3, 'С��', 1000);
INSERT INTO user VALUES (4, '�Ա���', 1000);

SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |  1000 |
|  4 | �Ա���    |  1000 |
+----+-----------+-------+

-- ����һ�������������
-- ����С�����Ա�������һ˫800��Ǯ��Ь�ӣ�
START TRANSACTION;
UPDATE user SET money = money - 800 WHERE name = 'С��';
UPDATE user SET money = money + 800 WHERE name = '�Ա���';

-- Ȼ���Ա�������һ����ѯ���������Ǯ�ѵ��ˡ�
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |   200 |
|  4 | �Ա���    |  1800 |
+----+-----------+-------+

����С����ת�������¿����������Ͻ��в����ģ����ò����Ľ���ǿ��Ա�����������һ�����Ա��꣩�����ģ�
		����Ա���Ĳ�ѯ�������ȷ�ģ��Ա���ȷ�ϵ��ˡ�
		��������ʱ�����С��������������������ִ���� ROLLBACK ����ᷢ��ʲô��

-- С������������
ROLLBACK;

-- ��ʱ���۶Է���˭�������ȥ��ѯ����ͻᷢ�֣�
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |  1000 |
|  4 | �Ա���    |  1000 |
+----+-----------+-------+
�������ν�������һ�������ȡ������һ������δ�ύ�����ݡ�����ʵ�ʿ������ǲ�������ֵġ�

#��ȡ���ύ
#�Ѹ��뼶������Ϊ READ COMMITTED ��
SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| READ-COMMITTED                 |
+--------------------------------+
�����������µ��������ӽ���ʱ�����Ǿ�ֻ�ܲ�ѯ���Ѿ��ύ�������������ˡ�
		���Ƕ��ڵ�ǰ������˵�����ǿ����Ļ���δ�ύ�����ݣ����磺
-- ���ڲ����������񣨵�ǰ����
START TRANSACTION;
UPDATE user SET money = money - 800 WHERE name = 'С��';
UPDATE user SET money = money + 800 WHERE name = '�Ա���';

-- ��Ȼ���뼶������Ϊ�� READ COMMITTED�����ڵ�ǰ�����У�
-- ����������Ȼ�����ݱ�����ʱ�ı����ݣ������������ύ�������ݡ�
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |   200 |
|  4 | �Ա���    |  1800 |
+----+-----------+-------+

-- �����ʱ��Զ�̿�����һ�����������ӵ����ݿ⡣
$ mysql -u root -p12345612

-- ��ʱԶ�����Ӳ�ѯ��������ֻ�����Ѿ��ύ����
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |  1000 |
|  4 | �Ա���    |  1000 |
+----+-----------+-------+
���������������⣬�Ǿ��Ǽ���һ�������ڲ�������ʱ��������������������������ݡ����磺

-- С���ڲ�ѯ���ݵ�ʱ���֣�
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |   200 |
|  4 | �Ա���    |  1800 |
+----+-----------+-------+

-- ��С������ money ƽ��ֵ֮ǰ��С������һ��������
��¼����һ��mysql�����һ���˻�
START TRANSACTION;
INSERT INTO user VALUES (5, 'c', 100);
COMMIT;

-- ��ʱ�����ʵ�����ǣ�
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |  1000 |
|  4 | �Ա���    |  1000 |
|  5 | c         |   100 |
+----+-----------+-------+

-- ��ʱС������ƽ��ֵ��ʱ�򣬾ͻ���ּ��㲻����ϵ������
SELECT AVG(money) FROM user;
+------------+
| AVG(money) |
+------------+
|  820.0000  |
+------------+
��Ȼ READ COMMITTED ������ֻ�ܶ�ȡ�����������Ѿ��ύ�����ݣ������ǻ�������⣬
		�����ڶ�ȡͬһ���������ʱ�����ܻᷢ��ǰ��һ�µ������
		�ⱻ��Ϊ�����ظ������� ( READ COMMITTED ) ��
		
#�ö�
#�����뼶������Ϊ REPEATABLE READ ( �ɱ��ظ���ȡ ) :
SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| REPEATABLE-READ                |
+--------------------------------+
���� REPEATABLE READ ��������������ͬ�������Ϸֱ�ִ�� START TRANSACTION :

-- С�� - �ɶ�
START TRANSACTION;
INSERT INTO user VALUES (6, 'd', 1000);

-- С�� - ����
START TRANSACTION;

-- С�� - �ɶ�
COMMIT;
��ǰ��������û�ύ֮ǰ����ѯ�������ύ����Ա���ѯ����
		���ǣ����ύ֮ǰ�������񱻿����ˣ���ô�������������ϣ�
		�Ͳ����ѯ����ǰ�в�����������ӡ��൱�ڿ��ٳ�һ���������̡߳�

����С���Ƿ�ִ�й� COMMIT ����С����ߣ��������ѯ��С�ŵ������¼������ֻ���ѯ���Լ���������ļ�¼��
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |  1000 |
|  4 | �Ա���    |  1000 |
|  5 | c         |   100 |
+----+-----------+-------+
������ΪС���ڴ�֮ǰ������һ���µ����� ( START TRANSACTION ) ��
		��ô��������������������ϣ�������������û����ϵ�ģ�
		Ҳ����˵����ʱ��������������ڲ������ݣ����ǲ�֪���ġ�

Ȼ����ʵ�ǣ�����ʵ�����ݱ��У�С���Ѿ�������һ�����ݡ�
	����С����ʱ����֪����Ҳ������ͬһ�����ݣ��ᷢ��ʲô�أ�

INSERT INTO user VALUES (6, 'd', 1000);
-- ERROR 1062 (23000): Duplicate entry '6' for key 'PRIMARY'
	�����ˣ���������֪�Ѵ�������Ϊ 6 ���ֶΡ�
	��������Ҳ����Ϊ�ö���һ�������ύ�����ݣ����ܱ����������ȡ����
	
#���л�
#����˼�壬�������������д�����ȫ���Ǵ��л��ġ�ʲô��˼���Ѹ��뼶���޸ĳ� SERIALIZABLE :

SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE;
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| SERIALIZABLE                   |
+--------------------------------+
������С�ź�С����������

-- С�� - �ɶ�
START TRANSACTION;

-- С�� - ����
START TRANSACTION;

-- ��������֮ǰ�Ȳ�ѯ��׼���������ݡ�
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | С��      |  1000 |
|  4 | �Ա���    |  1000 |
|  5 | c         |   100 |
|  6 | d         |  1000 |
+----+-----------+-------+

-- ����û�� 7 ����С�������ǲ���һ�����ݣ�
INSERT INTO user VALUES (7, '��С��', 1000);
��ʱ�ᷢ��ʲô�أ��������ڵĸ��뼶���� SERIALIZABLE ( ���л� ) ��
		���л�����˼���ǣ���������е����񶼷���һ�����еĶ����У�
		��ô���е����񶼻ᰴ�չ̶�˳��ִ�У�ִ����һ��������ټ���ִ����һ�������д����� 
		( ����ζ�Ŷ�����ͬʱֻ��ִ��һ�������д����� ) ��

����������ͣ�С���ڲ�������ʱ������ֵȴ�״̬��ֱ��С��ִ�� COMMIT ����������������
			���߳��ֵȴ���ʱ��
	
--���ʹ�ÿ��ӻ����߲������ݿ�
	
--����ڱ�������в������ݿ�
