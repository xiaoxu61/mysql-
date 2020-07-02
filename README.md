# mysql_note -

# 关系型数据库
```shell
使用终端操作数据库
如何登陆数据库服务器 ：在dos系统下输入：mysql -uroot -proot
如何查询数据库服务器中所有的数据库
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
```

## 一定记住:INSERT(增),DELETE(删),UPDATE(改),SELECT(查) 

````
#-- 查看数据表结构
-- describe pet;
desc pet;

#-- 查询表
SELECT * from pet;

#-- 插入数据
INSERT INTO pet VALUES ('puffball', 'Diane', 'hamster', 'f', '1990-03-30', NULL);

#-- 修改数据
UPDATE pet SET name = 'squirrel' where owner = 'Diane';

#-- 删除数据
DELETE FROM pet where name = 'squirrel';

#-- 删除表
DROP TABLE myorder;
````



# mysql建表中的约束
## 1.主键约束:primary key 
```
它能够唯一确定一张表中的一条记录,增加主键约束之后,就可以使得字段不重复而且不为空
create table user(
	id int PRIMARY KEY,
	name VARCHAR(20)
);
```
## 2.联合主键约束:primary key(id,name)
````
只要联合主键值加起来不重复就可以，并且联合主键里面任何一个字段都不可以为空
说明了复合主键只要所有的字段都不是相同的情况下可以允许其中的字段重复:
CREATE TABLE user2(
	id INT,
	name VARCHAR(20),
	password VARCHAR(20),
	PRIMARY key(id,name)
);
运行DESCRIBE user2;
+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| id       | int(11)     | NO   | PRI | NULL    |       |
| name     | varchar(20) | NO   | PRI | NULL    |       |
| password | varchar(20) | YES  |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+

INSERT INTO user2 VALUES (1,'老王','123456');
INSERT INTO user2 VALUES (2,'老王','123456');

+----+------+----------+
| id | name | password |
+----+------+----------+
|  1 | 老王 | 123456   |
|  2 | 老王 | 123456   |
+----+------+----------+
说明了复合主键只要所有的字段都不是相同的情况下可以允许其中的字段重复:
INSERT INTO user2 VALUES (1,'老李','123456');

SELECT * FROM user2;
+----+------+----------+
| id | name | password |
+----+------+----------+
|  1 | 老李 | 123456   |
|  1 | 老王 | 123456   |
|  2 | 老王 | 123456   |
+----+------+----------+
				
**场景:表中有班级号以及学生座位号,我们可以用班级号+学生的座位号可以准确的定位一个学生,
  如:(1班5号可以准确的确定一个学生)
````

## 3.自增约束:auto_increment
````
CREATE TABLE user3(
	id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(20)
);
运行DESCRIBE user3;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   | PRI | NULL    | auto_increment |
| name  | varchar(20) | YES  |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+

INSERT INTO user3(name) VALUES('张三');
INSERT INTO user3(name) VALUES('李四');
+----+------+
| id | name |
+----+------+
|  1 | 张三 |
|  2 | 李四 |
+----+------+
没有自定义id值 但是自动生成了id

如果创建表的时候忘记了创建主键约束，该怎么办
CREATE TABLE user4(
					id INT,
					name VARCHAR(20)
				);
alter table user4 add primary key(id);
-- 添加主键约束
-- 如果忘记设置主键，还可以通过SQL语句设置（两种方式）：
ALTER TABLE user ADD PRIMARY KEY(id);
ALTER TABLE user MODIFY id INT PRIMARY KEY;

-- 删除主键
ALTER TABLE user drop PRIMARY KEY;
````

## 4.唯一约束
````
约束修饰的字段的值不可以重复
(1) 创建时未添加唯一约束 可用语句ALTER TABLE user5 ADD UNIQUE(需要约束的字段);				
CREATE TABLE user5(
    id int,
    name VARCHAR(20)
);
运行 DESCRIBE user5;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   |     | NULL    | auto_increment |
| name  | varchar(20) | YES  |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+

新增name为唯一约束:
ALTER TABLE user5 ADD UNIQUE(name);
运行 DESCRIBE user5;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   |     | NULL    | auto_increment |
| name  | varchar(20) | YES  | UNI | NULL    |                |
+-------+-------------+------+-----+---------+----------------+
测试:插入数据
INSERT INTO user5(name) VALUES ('cc');
运行 SELECT * FROM user5; 查看结果:
+----+------+
| id | name |
+----+------+
|  1 | cc   |
+----+------+
再次插入INSERT INTO user5(name) VALUES ('cc');
出现:ERROR 1062 (23000): Duplicate entry 'cc' for key 'name'

换个试试 INSERT INTO user5(name) VALUES ('aa');
运行 SELECT * FROM user5; 查看结果:
+----+------+
| id | name |
+----+------+
|  3 | aa   |
|  1 | cc   |
+----+------+
(2)创建时直接添加唯一约束
CREATE TABLE user (
    id INT,
    name VARCHAR(20),
    UNIQUE(id,name) //两个在一起不重复就行
);

-- 删除唯一约束
ALTER TABLE user DROP INDEX name;
-- 添加唯一约束
-- 如果建表时没有设置唯一约束，还可以通过SQL语句设置（两种方式）：
ALTER TABLE user ADD UNIQUE(name);
ALTER TABLE user MODIFY name VARCHAR(20) UNIQUE;

**场景:业务需求:设计一张用户注册表,用户姓名必须要用手机号来注册,而且手机号和用户名称都不能为空,那么:
CREATE TABLE user_test(
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT'主键id',
    name VARCHAR(20) NOT NULL COMMENT'用户姓名,不能为空',
    phone_number VARCHAR(20) UNIQUE NOT NULL COMMENT'用户手机,不能重复且不能为空'
);
运行 DESCRIBE user_test;
+--------------+-------------+------+-----+---------+----------------+
| Field        | Type        | Null | Key | Default | Extra          |
+--------------+-------------+------+-----+---------+----------------+
| id           | int(11)     | NO   | PRI | NULL    | auto_increment |
| name         | varchar(20) | NO   |     | NULL    |                |
| phone_number | int(11)     | NO   | UNI | NULL    |                |
+--------------+-------------+------+-----+---------+----------------+
这样的话就达到了每一个手机号都只能出现一次,达到了每个手机号只能被注册一次.
用户姓名可以重复,但是手机号码却不能重复,复合正常的逻辑需求

		总结约束的添加与删除：
			1、 建表的时候就添加约束
			2、 可以使用 alter。。。add。。。
			3、 alter。。。modify。。。
			4、 删除约束 alter。。。drop。。。
````

## 5.非空约束:
````
-- 建表时添加非空约束
-- 约束某个字段不能为空
CREATE TABLE user (
    id INT,
    name VARCHAR(20) NOT NULL
);

-- 移除非空约束
ALTER TABLE user MODIFY name VARCHAR(20);
````
## 6.默认约束
````
就是当我们插入字段值的时候，如果没有传值，就会使用默认值
-- 建表时添加默认约束
-- 约束某个字段的默认值
CREATE TABLE user2 (
    id INT,
    name VARCHAR(20),
    age INT DEFAULT 10
);

-- 移除非空约束
ALTER TABLE user MODIFY age INT;
````
## 7.外键约束  foreign key(class_id) reference classes(id)
````
涉及两个表： 父表，子表
-- 班级
CREATE TABLE classes (
    id INT PRIMARY KEY,
    name VARCHAR(20)
);

-- 学生表
CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(20),
    -- 这里的 class_id 要和 classes 中的 id 字段相关联
    class_id INT,
    -- 表示 class_id 的值必须来自于 classes 中的 id 字段值
    FOREIGN KEY(class_id) REFERENCES classes(id)
);

-- 1. 主表（父表）classes 中没有的数据值，在副表（子表）students 中，是不可以使用的；
-- 2. 主表中的记录被副表引用时，主表不可以被删除。
班级插入数据:
INSERT INTO CLASSES VALUES (1,'一班');
INSERT INTO CLASSES VALUES (2,'二班');
INSERT INTO CLASSES VALUES (3,'三班');
INSERT INTO CLASSES VALUES (4,'四班');

学生插入数据:
INSERT INTO students VALUES (1001,'小赵',1);
INSERT INTO students VALUES (1002,'小钱',2);
INSERT INTO students VALUES (1003,'小孙',3);
INSERT INTO students VALUES (1004,'小李',4);

DELETE classes WHERE name = '四班'; //不可删除，因为副表中已使用
````

# 数据库的三大设计范式
##1.第一范式
````
只要字段值还可以继续拆分，就不满足第一范式。
范式设计得越详细，对某些实际操作可能会更好，但并非都有好处，需要对项目的实际情况进行设定。

	数据表中的所有字段都是不可分割的原子值
	create table student(
		id int primary key,
		name varchar(20),
		address varchar(30)
	);
	insert into student values(1,'张三','中国四川省成都市武侯区武侯大道100号');
	insert into student values(2,'李四','中国四川省成都市武侯区武侯大道200号');
	insert into student values(3,'王五','中国四川省成都市武侯区武侯大道40号');
以上数据表中的字段值还可以继续拆分的，就不满足第一范式

	create table student2(
		id int primary key,
		name varchar(20),
		country varchar(30),
		province varchar(30),
		city varchar(30),
		details varchar(30)
	);
	insert into student2 values(1,'张三','中国','四川省','成都市','武侯区武侯大道100号');
	insert into student2 values(2,'李四','中国','四川省','成都市','武侯区武侯大道200号');
	insert into student2 values(3,'王五','中国','四川省','成都市','武侯区武侯大道40号');
	如此创建假如满足第一范式，那么后期统计国家或者省份的时候就比较方便
范式设计的越详细，对于某些实际操作可能更好，但是不一定都是好处
````
## 2.第二范式
````
必须是满足第一范式的前提下，第二范式要求，除主键外的每一列都必须完全依赖于主键
如果要出现不完全依赖，只可能发生在联合主键的情况下

-- 订单表
CREATE TABLE myorder (
    product_id INT,
    customer_id INT,
    product_name VARCHAR(20),
    customer_name VARCHAR(20),
    PRIMARY KEY (product_id, customer_id)
);
实际上，在这张订单表中，product_name 只依赖于 product_id ，
customer_name 只依赖于 customer_id 。也就是说，
product_name 和 customer_id 是没用关系的，
customer_name 和 product_id 也是没有关系的。
这就不满足第二范式：其他列都必须完全依赖于主键列！

则需要进行拆表：
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
拆分之后，myorder 表中的 product_id 和 customer_id 完全依赖于 order_id 主键，
而 product 和 customer 表中的其他字段又完全依赖于主键。满足了第二范式的设计！
````
## 3.第三范式
````
在满足第二范式的前提下，除了主键列之外，其他列之间不能有传递依赖关系。
CREATE TABLE myorder (
    order_id INT PRIMARY KEY,
    product_id INT,
    customer_id INT,
    customer_phone VARCHAR(15)
);
表中的 customer_phone 有可能依赖于 order_id 、 customer_id 两列，
也就不满足了第三范式的设计：其他列之间不能有传递依赖关系。

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
修改后就不存在其他列之间的传递依赖关系，其他列都只依赖于主键列，满足了第三范式的设计！
````

# mysql查询练习

## 1.创建表
### 学生表Student
````
学号
姓名
性别
出生年月日
所在班级
create table Student(
	sno varchar(20) primary key,
	sname varchar(20) not null,
	ssex varchar(10) not null,
	sbirthday datetime,
	class varchar(20)
);
````


### 课程表Course
````
课程号
课程名称
教师编号
		create table Course(
			cno varchar(20) primary key,
			cname varchar(20) not null,
			tno varchar(20) not null,
			foreign key(tno) references teacher(tno)
		);
````
### 成绩表Score
````
学号
课程号
成绩
		create table score(
			sno varchar(20),
			cno varchar(20) not null,
			degree decimal,
			foreign key(sno) references student(sno),
			foreign key(cno) references course(cno),
			primary key(sno,cno)
		);
````
### 教师表Teacher
````
教师编号
教师名字
教师性别
出生年月日
职称
所在部门
		create table teacher(
			tno varchar(20) primary key,
			tname varchar(20) not null,
			tsex varchar(10) not null,
			tbirthday datetime,
			tprof varchar(20) not null,
			depart varchar(20) not null
		);
````
## 2.往数据表中添加数据
### 添加信息
````
-- 添加学生表数据
INSERT INTO student VALUES('101', '曾华', '男', '1977-09-01', '95033');
INSERT INTO student VALUES('102', '匡明', '男', '1975-10-02', '95031');
INSERT INTO student VALUES('103', '王丽', '女', '1976-01-23', '95033');
INSERT INTO student VALUES('104', '李军', '男', '1976-02-20', '95033');
INSERT INTO student VALUES('105', '王芳', '女', '1975-02-10', '95031');
INSERT INTO student VALUES('106', '陆军', '男', '1974-06-03', '95031');
INSERT INTO student VALUES('107', '王尼玛', '男', '1976-02-20', '95033');
INSERT INTO student VALUES('108', '张全蛋', '男', '1975-02-10', '95031');
INSERT INTO student VALUES('109', '赵铁柱', '男', '1974-06-03', '95031');	

-- 添加教师表数据
INSERT INTO teacher VALUES('804', '李诚', '男', '1958-12-02', '副教授', '计算机系');
INSERT INTO teacher VALUES('856', '张旭', '男', '1969-03-12', '讲师', '电子工程系');
INSERT INTO teacher VALUES('825', '王萍', '女', '1972-05-05', '助教', '计算机系');
INSERT INTO teacher VALUES('831', '刘冰', '女', '1977-08-14', '助教', '电子工程系');

-- 添加课程表数据
INSERT INTO course VALUES('3-105', '计算机导论', '825');
INSERT INTO course VALUES('3-245', '操作系统', '804');
INSERT INTO course VALUES('6-166', '数字电路', '856');
INSERT INTO course VALUES('9-888', '高等数学', '831');

-- 添加添加成绩表数据
INSERT INTO score VALUES('103', '3-105', '92');
INSERT INTO score VALUES('103', '3-245', '86');
INSERT INTO score VALUES('103', '6-166', '85');
INSERT INTO score VALUES('105', '3-105', '88');
INSERT INTO score VALUES('105', '3-245', '75');
INSERT INTO score VALUES('105', '6-166', '79');
INSERT INTO score VALUES('109', '3-105', '76');
INSERT INTO score VALUES('109', '3-245', '68');
INSERT INTO score VALUES('109', '6-166', '81');

-- 查看表结构
SELECT * FROM course;
SELECT * FROM score;
SELECT * FROM student;
SELECT * FROM teacher;
````
# 十大查询练习
1.-- 查询 student 表的所有列
SELECT * FROM student;

2.-- 查询 student 表中的 name、sex 和 class 字段的列
SELECT name, sex, class FROM student;

3.-- 查询 teacher 所有单位即不重复的部门，表中不重复的 department 列
-- department: 去重查询
SELECT DISTINCT department FROM teacher;

4.-- 查询 score 表中成绩在60-80之间的所有行（区间查询和运算符查询）
-- BETWEEN xx AND xx: 查询区间, AND 表示 "并且"
SELECT * FROM score WHERE degree BETWEEN 60 AND 80;
SELECT * FROM score WHERE degree > 60 AND degree < 80;

5.-- 查询 score 表中成绩为 85, 86 或 88 的行
-- IN: 查询规定中的多个值
SELECT * FROM score WHERE degree IN (85, 86, 88);

6.-- 查询 student 表中 '95031' 班或性别为 '女' 的所有行
-- or: 表示或者关系
SELECT * FROM student WHERE class = '95031' or sex = '女';

7.-- 以 class 降序的方式查询 student 表的所有行
-- DESC: 降序，从高到低
-- ASC（默认）: 升序，从低到高
SELECT * FROM student ORDER BY class DESC;
SELECT * FROM student ORDER BY class ASC;

8.-- 以 cno 升序、degree 降序查询 score 表的所有行
SELECT * FROM score ORDER BY c_no ASC, degree DESC;

9.-- 查询 "95031" 班的学生人数
-- COUNT: 统计
SELECT COUNT(*) FROM student WHERE class = '95031';

10.-- 查询 score 表中的最高分的学生学号和课程编号（子查询或排序查询）。
-- (SELECT MAX(degree) FROM score): 子查询，算出最高分
SELECT sno, cno FROM score WHERE degree = (SELECT MAX(degree) FROM score);
	首先找到最高分 SELECT MAX(degree) FROM score
	找最高分的sno和cno   SELECT sno, cno FROM score WHERE degree = (?)
	SELECT sno, cno,degree FROM score order by degree;

11.--  排序查询最高分或者最低分有缺陷，以为可能存在多个最高分或者最低分
-- LIMIT r, n: 表示从第r行开始，查询n条数据
SELECT sno, cno, degree FROM score ORDER BY degree DESC LIMIT 0, 1;

#分组计算平均成绩
#查询每门课的平均成绩。

11.-- AVG: 平均值
SELECT AVG(degree) FROM score WHERE cno = '3-105';
SELECT AVG(degree) FROM score WHERE cno = '3-245';
SELECT AVG(degree) FROM score WHERE cno = '6-166';

12.-- GROUP BY: 分组查询
SELECT cno, AVG(degree) FROM score GROUP BY cno;

13.--查询score表中至少有2名选手选修的并以3开头的课程的平均分数
分析表发现，至少有 2 名学生选修的课程是 3-105 、3-245 、6-166 ，以 3 开头的课程是 3-105 、3-245 。
也就是说，我们要查询所有 3-105 和 3-245 的 degree 平均分。

-- 首先把 cno, AVG(degree) 通过分组查询出来
SELECT cno, AVG(degree) FROM score GROUP BY cno
+-------+-------------+
| c_no  | AVG(degree) |
+-------+-------------+
| 3-105 |     85.3333 |
| 3-245 |     76.3333 |
| 6-166 |     81.6667 |
+-------+-------------+

-- 再查询出至少有 2 名学生选修的课程
-- HAVING: 表示持有
HAVING COUNT(c_no) >= 2

-- 并且是以 3 开头的课程
-- LIKE 表示模糊查询，"%" 是一个通配符，匹配 "3" 后面的任意字符。
AND c_no LIKE '3%';

-- 把前面的SQL语句拼接起来，
-- 后面加上一个 COUNT(*)，表示将每个分组的个数也查询出来。
SELECT c_no, AVG(degree), COUNT(*) FROM score GROUP BY cno
HAVING COUNT(cno) >= 2 AND cno LIKE '3%';
+-------+-------------+----------+
| c_no  | AVG(degree) | COUNT(*) |
+-------+-------------+----------+
| 3-105 |     85.3333 |        3 |
| 3-245 |     76.3333 |        3 |
+-------+-------------+----------+

14.查询分数大于70，小于90的sno
select sno,degree from score where degree between 70 and 90;

多表查询：
查询所有学生的sname、cno、和degree
select sname from student;

select cno,degree from score;

select sname,cno,degree from student,score where student.sno=score.sno;

查询所有学生的 name，以及该学生在 score 表中对应的 c_no 和 degree 。

SELECT sno, sname FROM student;
+-----+-----------+
| no  | name      |
+-----+-----------+
| 101 | 曾华      |
| 102 | 匡明      |
| 103 | 王丽      |
| 104 | 李军      |
| 105 | 王芳      |
| 106 | 陆军      |
| 107 | 王尼玛    |
| 108 | 张全蛋    |
| 109 | 赵铁柱    |
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

通过分析可以发现，只要把 score 表中的 s_no 字段值替换成 student 表中对应的 name 字段值就可以了，如何做呢？

-- FROM...: 表示从 student, score 表中查询
-- WHERE 的条件表示为，只有在 student.no 和 score.s_no 相等时才显示出来。
SELECT name, c_no, degree FROM student, score 
WHERE student.no = score.s_no;
+-----------+-------+--------+
| name      | c_no  | degree |
+-----------+-------+--------+
| 王丽      | 3-105 |     92 |
| 王丽      | 3-245 |     86 |
| 王丽      | 6-166 |     85 |
| 王芳      | 3-105 |     88 |
| 王芳      | 3-245 |     75 |
| 王芳      | 6-166 |     79 |
| 赵铁柱    | 3-105 |     76 |
| 赵铁柱    | 3-245 |     68 |
| 赵铁柱    | 6-166 |     81 |
+-----------+-------+--------+

15.查询所有学生的sno、cname和degree列

select sno,cname,degree from course,score where course.cno=score.cno;

select cno,cname from course;

select cno,sno,degree from score;
查询所有学生的 no 、课程名称 ( course 表中的 name ) 和成绩 ( score 表中的 degree ) 列。

只要 score 关联学生的 sno ，因此只要查询 score 表，就能找出所有和学生相关的 sno 和 degree ：

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
然后查询 course 表：
 
select cno,cname from course;
+-------+-----------------+
| no    | name            |
+-------+-----------------+
| 3-105 | 计算机导论      |
| 3-245 | 操作系统        |
| 6-166 | 数字电路        |
| 9-888 | 高等数学        |
+-------+-----------------+

只要把 score 表中的 c_no 替换成 course 表中对应的 name 字段值就可以了。

-- 增加一个查询字段 name，分别从 score、course 这两个表中查询。
-- as 表示取一个该字段的别名。
SELECT s_no, name as c_name, degree FROM score, course
		WHERE score.c_no = course.no;
+------+-----------------+--------+
| s_no | c_name          | degree |
+------+-----------------+--------+
| 103  | 计算机导论      |     92 |
| 105  | 计算机导论      |     88 |
| 109  | 计算机导论      |     76 |
| 103  | 操作系统        |     86 |
| 105  | 操作系统        |     75 |
| 109  | 操作系统        |     68 |
| 103  | 数字电路        |     85 |
| 105  | 数字电路        |     79 |
| 109  | 数字电路        |     81 |
+------+-----------------+--------+

16.查询所有学生的sname、cname和degree列
sname -> student
cname -> course
degree-> score

SELECT sname,cname,degree FROM student,course,score 
	WHERE student.sno=score.sno AND course.cno=score.cno;
+-----------+-----------------+--------+
| sname     | cname           | degree |
+-----------+-----------------+--------+
| 王丽      | 计算机导论      |     92 |
| 王芳      | 计算机导论      |     88 |
| 赵铁柱    | 计算机导论      |     76 |
| 王丽      | 操作系统        |     86 |
| 王芳      | 操作系统        |     75 |
| 赵铁柱    | 操作系统        |     68 |
| 王丽      | 数字电路        |     85 |
| 王芳      | 数字电路        |     79 |
| 赵铁柱    | 数字电路        |     81 |
+-----------+-----------------+--------+
9 rows in set (0.00 sec)


select sname,cname,degree,student.sno, course.cno from student,course,score 
	where student.sno=score.sno and course.cno=score.cno;
+-----------+-----------------+--------+-----+-------+
| sname     | cname           | degree | sno | cno   |
+-----------+-----------------+--------+-----+-------+
| 王丽      | 计算机导论      |     92 | 103 | 3-105 |
| 王芳      | 计算机导论      |     88 | 105 | 3-105 |
| 赵铁柱    | 计算机导论      |     76 | 109 | 3-105 |
| 王丽      | 操作系统        |     86 | 103 | 3-245 |
| 王芳      | 操作系统        |     75 | 105 | 3-245 |
| 赵铁柱    | 操作系统        |     68 | 109 | 3-245 |
| 王丽      | 数字电路        |     85 | 103 | 6-166 |
| 王芳      | 数字电路        |     79 | 105 | 6-166 |
| 赵铁柱    | 数字电路        |     81 | 109 | 6-166 |
+-----------+-----------------+--------+-----+-------+

select sname,cname,degree,student.sno as stu_sno, score.sno, course.cno as cou_cno 
		from student,course,score where student.sno=score.sno and course.cno=score.cno;
+-----------+-----------------+--------+---------+-----+---------+
| sname     | cname           | degree | stu_sno | sno | cou_cno |
+-----------+-----------------+--------+---------+-----+---------+
| 王丽      | 计算机导论      |     92 | 103     | 103 | 3-105   |
| 王芳      | 计算机导论      |     88 | 105     | 105 | 3-105   |
| 赵铁柱    | 计算机导论      |     76 | 109     | 109 | 3-105   |
| 王丽      | 操作系统        |     86 | 103     | 103 | 3-245   |
| 王芳      | 操作系统        |     75 | 105     | 105 | 3-245   |
| 赵铁柱    | 操作系统        |     68 | 109     | 109 | 3-245   |
| 王丽      | 数字电路        |     85 | 103     | 103 | 6-166   |
| 王芳      | 数字电路        |     79 | 105     | 105 | 6-166   |
| 赵铁柱    | 数字电路        |     81 | 109     | 109 | 6-166   |
+-----------+-----------------+--------+---------+-----+---------+
9 rows in set (0.00 sec)		

17.查询‘95031’班学生每门课的平均分
select*from student where class='95031';
mysql> select*from student where class='95031';
+-----+-----------+------+---------------------+-------+
| sno | sname     | ssex | sbirthday           | class |
+-----+-----------+------+---------------------+-------+
| 102 | 匡明      | 男   | 1975-10-02 00:00:00 | 95031 |
| 105 | 王芳      | 女   | 1975-02-10 00:00:00 | 95031 |
| 106 | 陆军      | 男   | 1974-06-03 00:00:00 | 95031 |
| 108 | 张全蛋    | 男   | 1975-02-10 00:00:00 | 95031 |
| 109 | 赵铁柱    | 男   | 1974-06-03 00:00:00 | 95031 |
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

18.查询选修‘3-105’课程的成绩高于‘109’号同学‘3-105’成绩的所有同学的记录
查询在 3-105 课程中，所有成绩高于 109 号同学的记录。
select degree from score where sno='109' and cno='3-105';
首先筛选出课堂号为 3-105 ，在找出所有成绩高于 109 号同学的的行。

SELECT * FROM score 
WHERE cno = '3-105'
AND degree > (SELECT degree FROM score WHERE sno = '109' AND cno = '3-105');
+-----+-------+--------+
| sno | cno   | degree |
+-----+-------+--------+
| 103 | 3-105 |     92 |
| 105 | 3-105 |     88 |
+-----+-------+--------+

19.查询成绩高于学号为‘109’、课程号为‘3-105’的成绩的所有记录

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

20.查询和学号为108、101的同学同年出生的所有学生的sno、sname和sbirthday列
-- YEAR(..): 取出日期中的年份

SELECT sno, sname, sbirthday FROM student
WHERE YEAR(sbirthday) IN (SELECT YEAR(sbirthday) FROM student WHERE sno IN (101, 108));

21.查询‘张旭’教师任课的学生成绩
先在teacher表中拿到张旭老师的教师编号
select tno from teacher where tname='张旭';
拿到course表中教师编号对应的课程编号
select cno from course where tno=(select tno from teacher where tname='张旭');
在score表中拿到相应课程编号对应相关信息
select*from score cno=(select cno from course where tno=(select tno from teacher where tname='张旭'));

22.查询某选修课程多于5个同学的教师姓名
逐渐套娃，先把最小的子查询写好，然后放到范围较大的查询中以此类推
select cno from score group by cno having count(*)>5;

select*from teacher;

select*from course;

select tno from course where cno=
		(select cno from score group by cno having count(*)>5);

select tname from teacher where tno=
		(select tno from course where cno=(select cno from score group by cno having count(*)>5));
		
23.查询95033班和95031班全体学生记录
select*from student where class in ('95031','95033');

24.查询存在有85分以上成绩的cno
select*from score where degree>85;

25.查询 计算机系 教师所教课程的成绩表

select*from teacher where depart='计算机系';

tno	tname	tsex	tbirthday	tprof	depart
804	李诚	男	1958-12-02 00:00:00	副教授	计算机系
825	王萍	女	1972-05-05 00:00:00	助教	计算机系

select*from course where tno in(select tno from teacher where depart='计算机系');

cno	cname	tno
3-105	计算机导论	825
3-245	操作系统	804

select*from score where cno in 
		(select cno from course where tno in(select tno from teacher where depart='计算机系'));
		
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

#UNION 和 NOTIN 的使用
26.查询 计算机系 与 电子工程系 中的不同职称的教师。
-- UNION 求并集
-- NOT: 代表逻辑非
--先查询计算机系中职称中与电子工程系中职称不同的教师
SELECT * FROM teacher WHERE depart = '计算机系' AND tprof NOT IN (
    SELECT tprof FROM teacher WHERE depart = '电子工程系'
)
--查询电子工程系中职称中与计算机系中职称不同的教师
-- 合并两个集，上下合并
UNION
SELECT * FROM teacher WHERE depart = '电子工程系' AND tprof NOT IN (
    SELECT tprof FROM teacher WHERE depart = '计算机系'
);

ANY 表示至少一个 - DESC ( 降序 )
27.查询课程 3-105 且成绩 至少 高于 3-245 的 score 表。

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

至少？大于其中至少一个

-- ANY: 符合SQL语句中的任意条件。
-- 也就是说，在 3-105 成绩中，只要有一个大于从 3-245 筛选出来的任意行就符合条件，
-- 最后根据降序查询结果。
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

表示所有的 ALL
28.查询课程 3-105 且成绩高于 3-245 的 score 表。

-- 只需对上一道题稍作修改。
-- ALL: 符合SQL语句中的所有条件。
-- 也就是说，在 3-105 每一行成绩中，都要大于从 3-245 筛选出来全部行才算符合条件。
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

29.查询所有教师和同学的name、sex和birthday
select tname as name,tsex as sex,tbirthday as birthday from teacher
union
select sname,ssex,sbirthday from student;

查询所有女教师和女同学的name、sex和birthday
select tname as name,tsex as sex,tbirthday as birthday from teacher where tsex='女'
union
select sname,ssex,sbirthday from student where ssex='女';


30.查询成绩比该课程平均成绩低的同学的 score 表。

-- 查询平均分
SELECT cno, AVG(degree) FROM score GROUP BY cno;
+-------+-------------+
| c_no  | AVG(degree) |
+-------+-------------+
| 3-105 |     87.6667 |
| 3-245 |     76.3333 |
| 6-166 |     81.6667 |
+-------+-------------+

-- 查询 score 表
SELECT degree FROM score;
a表			b表
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

-- 将表 b 作用于表 a 中查询数据
-- score a (b): 将表声明为 a (b)，
-- 如此就能用 a.c_no = b.c_no 作为条件执行查询了。
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

31.查询所有任课 ( 在 course 表里有课程 ) 教师的 name 和 department 。

SELECT name, department FROM teacher WHERE no IN (SELECT tno FROM course);
+--------+-----------------+
| name   | department      |
+--------+-----------------+
| 李诚   | 计算机系        |
| 王萍   | 计算机系        |
| 刘冰   | 电子工程系      |
| 张旭   | 电子工程系      |
+--------+-----------------+

条件加组筛选
32.查询 student 表中至少有 2 名男生的 class
-- 查看学生表信息
SELECT * FROM student;
+-----+-----------+-----+------------+-------+
| no  | name      | sex | birthday   | class |
+-----+-----------+-----+------------+-------+
| 101 | 曾华      | 男  | 1977-09-01 | 95033 |
| 102 | 匡明      | 男  | 1975-10-02 | 95031 |
| 103 | 王丽      | 女  | 1976-01-23 | 95033 |
| 104 | 李军      | 男  | 1976-02-20 | 95033 |
| 105 | 王芳      | 女  | 1975-02-10 | 95031 |
| 106 | 陆军      | 男  | 1974-06-03 | 95031 |
| 107 | 王尼玛    | 男  | 1976-02-20 | 95033 |
| 108 | 张全蛋    | 男  | 1975-02-10 | 95031 |
| 109 | 赵铁柱    | 男  | 1974-06-03 | 95031 |
| 110 | 张飞      | 男  | 1974-06-03 | 95038 |
+-----+-----------+-----+------------+-------+

-- 只查询性别为男，然后按 class 分组，并限制 class 行大于 1。
SELECT class FROM student WHERE ssex = '男' GROUP BY class HAVING COUNT(*) > 1;
+-------+
| class |
+-------+
| 95033 |
| 95031 |
+-------+

NOTLIKE 模糊查询取反
33.查询 student 表中不姓 "王" 的同学记录。

-- NOT: 取反
-- LIKE: 模糊查询
mysql> SELECT * FROM student WHERE sname NOT LIKE '王%';
+-----+-----------+-----+------------+-------+
| no  | name      | sex | birthday   | class |
+-----+-----------+-----+------------+-------+
| 101 | 曾华      | 男  | 1977-09-01 | 95033 |
| 102 | 匡明      | 男  | 1975-10-02 | 95031 |
| 104 | 李军      | 男  | 1976-02-20 | 95033 |
| 106 | 陆军      | 男  | 1974-06-03 | 95031 |
| 108 | 张全蛋    | 男  | 1975-02-10 | 95031 |
| 109 | 赵铁柱    | 男  | 1974-06-03 | 95031 |
| 110 | 张飞      | 男  | 1974-06-03 | 95038 |
+-----+-----------+-----+------------+-------+

YEAR 与 NOW 函数
34.查询 student 表中每个学生的姓名和年龄。
SELECT YEAR(NOW());

year(now())
2020

-- 使用函数 YEAR(NOW()) 计算出当前年份，减去出生年份后得出年龄。
SELECT name, YEAR(NOW()) - YEAR(sbirthday) as age FROM student;
+-----------+------+
| name      | age  |
+-----------+------+
| 曾华      |   42 |
| 匡明      |   44 |
| 王丽      |   43 |
| 李军      |   43 |
| 王芳      |   44 |
| 陆军      |   45 |
| 王尼玛    |   43 |
| 张全蛋    |   44 |
| 赵铁柱    |   45 |
| 张飞      |   45 |
+-----------+------+

MAX 与 MIN 函数
35.查询 student 表中最大和最小的 birthday 值。

SELECT MAX(sbirthday), MIN(sbirthday) FROM student;
+---------------+---------------+
| MAX(birthday) | MIN(birthday) |
+---------------+---------------+
| 1977-09-01    | 1974-06-03    |
+---------------+---------------+

多段排序
以 class 和 birthday 从大到小的顺序查询 student 表。

SELECT * FROM student ORDER BY class DESC, sbirthday;
+-----+-----------+-----+------------+-------+
| no  | name      | sex | birthday   | class |
+-----+-----------+-----+------------+-------+
| 110 | 张飞      | 男  | 1974-06-03 | 95038 |
| 103 | 王丽      | 女  | 1976-01-23 | 95033 |
| 104 | 李军      | 男  | 1976-02-20 | 95033 |
| 107 | 王尼玛    | 男  | 1976-02-20 | 95033 |
| 101 | 曾华      | 男  | 1977-09-01 | 95033 |
| 106 | 陆军      | 男  | 1974-06-03 | 95031 |
| 109 | 赵铁柱    | 男  | 1974-06-03 | 95031 |
| 105 | 王芳      | 女  | 1975-02-10 | 95031 |
| 108 | 张全蛋    | 男  | 1975-02-10 | 95031 |
| 102 | 匡明      | 男  | 1975-10-02 | 95031 |
+-----+-----------+-----+------------+-------+

查询 "男" 教师及其所上的课程。

SELECT * FROM course WHERE tno in (SELECT tno FROM teacher WHERE sex = '男');
+-------+--------------+------+
| no    | name         | t_no |
+-------+--------------+------+
| 3-245 | 操作系统     | 804  |
| 6-166 | 数字电路     | 856  |
+-------+--------------+------+

SELECT cno, cname, course.tno, tname,tsex FROM course,teacher WHERE 
		course.tno=teacher.tno and 
		course.tno in (SELECT tno from teacher WHERE tsex = '男');

cno	cname	tno	tname	tsex
3-245	操作系统	804	李诚	男
6-166	数字电路	856	张旭	男


MAX 函数与子查询
36.查询最高分同学的 score 表。

-- 找出最高成绩（该查询只能有一个结果）
SELECT MAX(degree) FROM score;

-- 根据上面的条件筛选出所有最高成绩表，
-- 该查询可能有多个结果，假设 degree 值多次符合条件。
SELECT * FROM score WHERE degree = (SELECT MAX(degree) FROM score);
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 103  | 3-105 |     92 |
+------+-------+--------+


37.查询和 "李军" 同性别的所有同学 name 。
-- 首先将李军的性别作为条件取出来
SELECT sex FROM student WHERE name = '李军';
+-----+
| sex |
+-----+
| 男  |
+-----+

-- 根据性别查询 name 和 sex
SELECT name, sex FROM student WHERE ssex = (
    SELECT sex FROM student WHERE name = '李军'
);
+-----------+-----+
| name      | sex |
+-----------+-----+
| 曾华      | 男  |
| 匡明      | 男  |
| 李军      | 男  |
| 陆军      | 男  |
| 王尼玛    | 男  |
| 张全蛋    | 男  |
| 赵铁柱    | 男  |
| 张飞      | 男  |
+-----------+-----+

37.查询和 "李军" 同性别且同班的同学 name 。

SELECT name, sex, class FROM student WHERE sex = (
    SELECT sex FROM student WHERE name = '李军'
) AND class = (
    SELECT class FROM student WHERE name = '李军'
);
+-----------+-----+-------+
| name      | sex | class |
+-----------+-----+-------+
| 曾华      | 男  | 95033 |
| 李军      | 男  | 95033 |
| 王尼玛    | 男  | 95033 |
+-----------+-----+-------+
		
38.查询所有选修 "计算机导论" 课程的 "男" 同学成绩表。

需要的 "计算机导论" 和性别为 "男" 的编号可以在 course 和 student 表中找到。

SELECT * FROM score WHERE cno = (
    SELECT no FROM course WHERE name = '计算机导论'
) AND sno IN (
    SELECT sno FROM student WHERE sex = '男'
);
+------+-------+--------+
| s_no | c_no  | degree |
+------+-------+--------+
| 101  | 3-105 |     90 |
| 102  | 3-105 |     91 |
| 104  | 3-105 |     89 |
| 109  | 3-105 |     76 |
+------+-------+--------+

#按等级查询
建立一个 grade 表代表学生的成绩等级，并插入数据：

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

39.查询所有学生的 s_no 、c_no 和 grade 列。

思路是，使用区间 ( BETWEEN ) 查询，判断学生的成绩 ( degree ) 在 grade 表的 low 和 upp 之间。

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

#sql的四种连接查询
内连接
inner join 或者 join

外连接
	左连接 left join 或者 left outer join
	右连接 right join 或者 right outer join
	完全外连接 full join 或者 full outer join
	
准备用于测试连接查询的数据：

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
INSERT INTO card VALUES (1, '饭卡'), (2, '建行卡'), (3, '农行卡'), (4, '工商卡'), (5, '邮政卡');
INSERT INTO person VALUES (1, '张三', 1), (2, '李四', 3), (3, '王五', 6);

分析两张表发现，person 表并没有为 cardId 字段设置一个在 card 表中对应的 id 外键。
	如果设置了的话，person 中 cardId 字段值为 6 的行就插不进去，
	因为该 cardId 值在 card 表中并没有。
	
#内连接
要查询这两张表中有关系的数据，可以使用 INNER JOIN ( 内连接 ) 将它们连接在一起。
内联查询，其实就是通过两张表中的数据，通过某个字段相等，查询出相关记录

-- INNER JOIN: 表示为内连接，将两张表拼接在一起。
-- on: 表示要执行某个条件。
SELECT * FROM person INNER JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | 张三   |      1 |    1 | 饭卡      |
|    2 | 李四   |      3 |    3 | 农行卡    |
+------+--------+--------+------+-----------+

-- 将 INNER 关键字省略掉，结果也是一样的。
-- SELECT * FROM person JOIN card on person.cardId = card.id;
注意：card 的整张表被连接到了右边。

#左外连接
完整显示左边的表 ( person ) ，右边的表如果符合条件就显示，不符合则补 NULL 。

-- LEFT JOIN 也叫做 LEFT OUTER JOIN，用这两种方式的查询结果是一样的。
SELECT * FROM person LEFT JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | 张三   |      1 |    1 | 饭卡      |
|    2 | 李四   |      3 |    3 | 农行卡    |
|    3 | 王五   |      6 | NULL | NULL      |
+------+--------+--------+------+-----------+

#右外链接
完整显示右边的表 ( card ) ，左边的表如果符合条件就显示，不符合则补 NULL 。

SELECT * FROM person RIGHT JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | 张三   |      1 |    1 | 饭卡      |
|    2 | 李四   |      3 |    3 | 农行卡    |
| NULL | NULL   |   NULL |    2 | 建行卡    |
| NULL | NULL   |   NULL |    4 | 工商卡    |
| NULL | NULL   |   NULL |    5 | 邮政卡    |
+------+--------+--------+------+-----------+

#全外链接
完整显示两张表的全部数据。

-- MySQL 不支持这种语法的全外连接
-- SELECT * FROM person FULL JOIN card on person.cardId = card.id;
-- 出现错误：
-- ERROR 1054 (42S22): Unknown column 'person.cardId' in 'on clause'

-- MySQL全连接语法，使用 UNION 将两张表合并在一起。
SELECT * FROM person LEFT JOIN card on person.cardId = card.id
UNION
SELECT * FROM person RIGHT JOIN card on person.cardId = card.id;
+------+--------+--------+------+-----------+
| id   | name   | cardId | id   | name      |
+------+--------+--------+------+-----------+
|    1 | 张三   |      1 |    1 | 饭卡      |
|    2 | 李四   |      3 |    3 | 农行卡    |
|    3 | 王五   |      6 | NULL | NULL      |
| NULL | NULL   |   NULL |    2 | 建行卡    |
| NULL | NULL   |   NULL |    4 | 工商卡    |
| NULL | NULL   |   NULL |    5 | 邮政卡    |
+------+--------+--------+------+-----------+

#mysql事务
事务其实是最小的不可分割的工作单元，能够保证业务的完整性

比如我们的银行转账：
-- a -> -100
UPDATE user set money = money - 100 WHERE name = 'a';
-- b -> +100
UPDATE user set money = money + 100 WHERE name = 'b';
在实际项目中，假设只有一条 SQL 语句执行成功，而另外一条执行失败了，就会出现数据前后不一致。
因此，实际开发当中在执行多条有关联 SQL 语句时，事务可能会要求这些 SQL 语句要么同时执行成功，
																要么就都执行失败。
																
如何控制事务 - COMMIT / ROLLBACK
在 MySQL 中，事务的自动提交状态默认是开启的。

-- 查询事务的自动提交状态
SELECT @@AUTOCOMMIT;
+--------------+
| @@AUTOCOMMIT |
+--------------+
|            1 |
+--------------+
自动提交的作用：当我们执行一条 SQL 语句的时候，其产生的效果就会立即体现出来，且不能回滚。
什么是回滚？举个例子：
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


可以看到，在执行插入语句后数据立刻生效，原因是 MySQL 中的事务自动将它提交到了数据库中。
那么所谓回滚的意思就是，撤销执行过的所有 SQL 语句，使其回滚到最后一次提交数据时的状态。

在 MySQL 中使用 ROLLBACK 执行回滚：

-- 回滚到最后一次提交
ROLLBACK;

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+

由于所有执行过的 SQL 语句都已经被提交过了，所以数据并没有发生回滚。那如何让数据可以发生回滚？

-- 关闭自动提交
SET AUTOCOMMIT = 0;

-- 查询自动提交状态
SELECT @@AUTOCOMMIT;
+--------------+
| @@AUTOCOMMIT |
+--------------+
|            0 |
+--------------+

//将自动提交关闭后，测试数据回滚：
INSERT INTO user VALUES (2, 'b', 1000);

-- 关闭 AUTOCOMMIT 后，数据的变化是在一张虚拟的临时数据表中展示，
-- 发生变化的数据并没有真正插入到数据表中。
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+

-- 数据表中的真实数据其实还是：
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+

-- 由于数据还没有真正提交，可以使用回滚
ROLLBACK;

-- 再次查询
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
+----+------+-------+

//那如何将虚拟的数据真正提交到数据库中？使用 COMMIT :

INSERT INTO user VALUES (2, 'b', 1000);
-- 手动提交数据（持久性），
-- 将数据真正提交到数据库中，执行后不能再回滚提交过的数据。
COMMIT;

-- 提交后测试回滚
ROLLBACK;

-- 再次查询（回滚无效了）
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+

事务就是 
	自动提交 @@autocommit = 1
	手动提交 commit
	手动回滚 rollback
  总结
	自动提交
	查看自动提交状态：SELECT @@AUTOCOMMIT ；
	设置自动提交状态：SET AUTOCOMMIT = 0 。
	手动提交
	@@AUTOCOMMIT = 0 时，使用 COMMIT 命令提交事务。
	事务回滚
	@@AUTOCOMMIT = 0 时，使用 ROLLBACK 命令回滚事务。
	
	如果这个时候转账：
		UPDATE user set money = money - 100 WHERE name = 'a';
		UPDATE user set money = money + 100 WHERE name = 'b';
事务的实际应用，让我们再回到银行转账项目：

-- 转账
UPDATE user set money = money - 100 WHERE name = 'a';

-- 到账
UPDATE user set money = money + 100 WHERE name = 'b';

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |   900 |
|  2 | b    |  1100 |
+----+------+-------+
这时假设在转账时发生了意外，就可以使用 ROLLBACK 回滚到最后一次提交的状态：

-- 假设转账发生了意外，需要回滚。
ROLLBACK;

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+
这时我们又回到了发生意外之前的状态，也就是说，事务给我们提供了一个可以反悔的机会。
	假设数据没有发生意外，这时可以手动将数据真正提交到数据表中：COMMIT 。
	
#手动开启事务 - BEGIN / START TRANSACTION
事务的默认提交被开启 ( @@AUTOCOMMIT = 1 ) 后，此时就不能使用事务回滚了。但是我们还可以手动开启一个事务处理事件，使其可以发生回滚：

-- 使用 BEGIN 或者 START TRANSACTION 手动开启一个事务
-- START TRANSACTION;
BEGIN;
UPDATE user set money = money - 100 WHERE name = 'a';
UPDATE user set money = money + 100 WHERE name = 'b';

-- 由于手动开启的事务没有开启自动提交，
-- 此时发生变化的数据仍然是被保存在一张临时表中。
SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |   900 |
|  2 | b    |  1100 |
+----+------+-------+

-- 测试回滚
ROLLBACK;

SELECT * FROM user;
+----+------+-------+
| id | name | money |
+----+------+-------+
|  1 | a    |  1000 |
|  2 | b    |  1000 |
+----+------+-------+
使用 COMMIT 提交数据后，提交后无法再发生本次事务的回滚，因为该事务已经结束了。

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

-- 提交数据
COMMIT;

-- 测试回滚（无效，因为表的数据已经被提交）
ROLLBACK;

#事务的 ACID 特征与使用
#事务的四大特征：
A 原子性：事务是最小的单位，不可以再分割；
C 一致性：要求同一事务中的 SQL 语句，必须保证同时成功或者失败；
I 隔离性：事务1 和 事务2 之间是具有隔离性的；
D 持久性：事务一旦结束 ( COMMIT ) ，就不可以再返回了 ( ROLLBACK ) 。
● 原子性（Atomicity）：操作这些指令时，要么全部执行成功，要么全部不执行。
			只要其中一个指令执行失败，所有的指令都执行失败，数据进行回滚，回到执行指令前的数据状态。
eg：拿转账来说，假设用户A和用户B两者的钱加起来一共是20000，那么不管A和B之间如何转账，
			转几次账，事务结束后两个用户的钱相加起来应该还得是20000，这就是事务的一致性。
● 一致性（Consistency）：事务的执行使数据从一个状态转换为另一个状态，
			但是对于整个数据的完整性保持稳定。
● 隔离性（Isolation）：隔离性是当多个用户并发访问数据库时，比如操作同一张表时，
			数据库为每一个用户开启的事务，不能被其他事务的操作所干扰，多个并发事务之间要相互隔离。
			即要达到这么一种效果：对于任意两个并发的事务T1和T2，
			在事务T1看来，T2要么在T1开始之前就已经结束，要么在T1结束之后才开始，
			这样每个事务都感觉不到有其他事务在并发地执行。			
● 持久性（Durability）：当事务正确完成后，它对于数据的改变是永久性的。
eg： 例如我们在使用JDBC操作数据库时，在提交事务方法后，提示用户事务操作完成，
			当我们程序执行完成直到看到提示后，就可以认定事务以及正确提交，
			即使这时候数据库出现了问题，也必须要将我们的事务完全执行完成，
			否则就会造成我们看到提示事务处理完毕，但是数据库因为故障而没有执行事务的重大错误。
#事务开启
	1、修改默认提交 set autocommit=0；
	2、begin；
	3、start transaction；
事务手动提交：commit
事务手动回滚：rollback(在关闭了自动提交时可用)

--事务的隔离性
事务的隔离性可分为四种 ( 性能从低到高 ) ：
1.READ UNCOMMITTED; ( 读取未提交 )
	如果有多个事务，那么任意事务都可以看见其他事务的未提交数据。
2.READ COMMITTED; ( 读取已提交 )
	只能读取到其他事务已经提交的数据。
3.REPEATABLE READ; ( 可被重复读 )
	如果有多个连接都开启了事务，那么事务之间不能共享数据记录，否则只能共享已提交的记录。
4.SERIALIZABLE; ( 串行化 )
	所有的事务都会按照固定顺序执行，执行完一个事务后再继续执行下一个事务的写入操作。

#查看当前数据库的默认隔离级别：
-- MySQL 8.x, GLOBAL 表示系统级别，不加表示会话级别。
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
SELECT @@TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| REPEATABLE-READ                | -- MySQL的默认隔离级别，可以重复读。
+--------------------------------+

-- MySQL 5.x
SELECT @@GLOBAL.TX_ISOLATION;
SELECT @@TX_ISOLATION;

#修改隔离级别：
-- 设置系统隔离级别，LEVEL 后面表示要设置的隔离级别 (READ UNCOMMITTED)。
SET GLOBAL TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
-- 查询系统隔离级别，发现已经被修改。
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| READ-UNCOMMITTED               |
+--------------------------------+

//测试 READ UNCOMMITTED ( 读取未提交 ) 的隔离性：
INSERT INTO user VALUES (3, '小明', 1000);
INSERT INTO user VALUES (4, '淘宝店', 1000);

SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |  1000 |
|  4 | 淘宝店    |  1000 |
+----+-----------+-------+

-- 开启一个事务操作数据
-- 假设小明在淘宝店买了一双800块钱的鞋子：
START TRANSACTION;
UPDATE user SET money = money - 800 WHERE name = '小明';
UPDATE user SET money = money + 800 WHERE name = '淘宝店';

-- 然后淘宝店在另一方查询结果，发现钱已到账。
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |   200 |
|  4 | 淘宝店    |  1800 |
+----+-----------+-------+

由于小明的转账是在新开启的事务上进行操作的，而该操作的结果是可以被其他事务（另一方的淘宝店）看见的，
		因此淘宝店的查询结果是正确的，淘宝店确认到账。
		但就在这时，如果小明在它所处的事务上又执行了 ROLLBACK 命令，会发生什么？

-- 小明所处的事务
ROLLBACK;

-- 此时无论对方是谁，如果再去查询结果就会发现：
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |  1000 |
|  4 | 淘宝店    |  1000 |
+----+-----------+-------+
这就是所谓的脏读，一个事务读取到另外一个事务还未提交的数据。这在实际开发中是不允许出现的。

#读取已提交
#把隔离级别设置为 READ COMMITTED ：
SET GLOBAL TRANSACTION ISOLATION LEVEL READ COMMITTED;
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| READ-COMMITTED                 |
+--------------------------------+
这样，再有新的事务连接进来时，它们就只能查询到已经提交过的事务数据了。
		但是对于当前事务来说，它们看到的还是未提交的数据，例如：
-- 正在操作数据事务（当前事务）
START TRANSACTION;
UPDATE user SET money = money - 800 WHERE name = '小明';
UPDATE user SET money = money + 800 WHERE name = '淘宝店';

-- 虽然隔离级别被设置为了 READ COMMITTED，但在当前事务中，
-- 它看到的仍然是数据表中临时改变数据，而不是真正提交过的数据。
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |   200 |
|  4 | 淘宝店    |  1800 |
+----+-----------+-------+

-- 假设此时在远程开启了一个新事务，连接到数据库。
$ mysql -u root -p12345612

-- 此时远程连接查询到的数据只能是已经提交过的
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |  1000 |
|  4 | 淘宝店    |  1000 |
+----+-----------+-------+
但是这样还有问题，那就是假设一个事务在操作数据时，其他事务干扰了这个事务的数据。例如：

-- 小张在查询数据的时候发现：
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |   200 |
|  4 | 淘宝店    |  1800 |
+----+-----------+-------+

-- 在小张求表的 money 平均值之前，小王做了一个操作：
登录了另一个mysql添加了一个账户
START TRANSACTION;
INSERT INTO user VALUES (5, 'c', 100);
COMMIT;

-- 此时表的真实数据是：
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |  1000 |
|  4 | 淘宝店    |  1000 |
|  5 | c         |   100 |
+----+-----------+-------+

-- 这时小张再求平均值的时候，就会出现计算不相符合的情况：
SELECT AVG(money) FROM user;
+------------+
| AVG(money) |
+------------+
|  820.0000  |
+------------+
虽然 READ COMMITTED 让我们只能读取到其他事务已经提交的数据，但还是会出现问题，
		就是在读取同一个表的数据时，可能会发生前后不一致的情况。
		这被称为不可重复读现象 ( READ COMMITTED ) 。
		
#幻读
#将隔离级别设置为 REPEATABLE READ ( 可被重复读取 ) :
SET GLOBAL TRANSACTION ISOLATION LEVEL REPEATABLE READ;
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| REPEATABLE-READ                |
+--------------------------------+
测试 REPEATABLE READ ，假设在两个不同的连接上分别执行 START TRANSACTION :

-- 小张 - 成都
START TRANSACTION;
INSERT INTO user VALUES (6, 'd', 1000);

-- 小王 - 北京
START TRANSACTION;

-- 小张 - 成都
COMMIT;
当前事务开启后，没提交之前，查询不到，提交后可以被查询到。
		但是，在提交之前其他事务被开启了，那么在这条事务线上，
		就不会查询到当前有操作事务的连接。相当于开辟出一条单独的线程。

无论小张是否执行过 COMMIT ，在小王这边，都不会查询到小张的事务记录，而是只会查询到自己所处事务的记录：
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |  1000 |
|  4 | 淘宝店    |  1000 |
|  5 | c         |   100 |
+----+-----------+-------+
这是因为小王在此之前开启了一个新的事务 ( START TRANSACTION ) ，
		那么在他的这条新事务的线上，跟其他事务是没有联系的，
		也就是说，此时如果其他事务正在操作数据，它是不知道的。

然而事实是，在真实的数据表中，小张已经插入了一条数据。
	但是小王此时并不知道，也插入了同一条数据，会发生什么呢？

INSERT INTO user VALUES (6, 'd', 1000);
-- ERROR 1062 (23000): Duplicate entry '6' for key 'PRIMARY'
	报错了，操作被告知已存在主键为 6 的字段。
	这种现象也被称为幻读，一个事务提交的数据，不能被其他事务读取到。
	
#串行化
#顾名思义，就是所有事务的写入操作全都是串行化的。什么意思？把隔离级别修改成 SERIALIZABLE :

SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE;
SELECT @@GLOBAL.TRANSACTION_ISOLATION;
+--------------------------------+
| @@GLOBAL.TRANSACTION_ISOLATION |
+--------------------------------+
| SERIALIZABLE                   |
+--------------------------------+
还是拿小张和小王来举例：

-- 小张 - 成都
START TRANSACTION;

-- 小王 - 北京
START TRANSACTION;

-- 开启事务之前先查询表，准备操作数据。
SELECT * FROM user;
+----+-----------+-------+
| id | name      | money |
+----+-----------+-------+
|  1 | a         |   900 |
|  2 | b         |  1100 |
|  3 | 小明      |  1000 |
|  4 | 淘宝店    |  1000 |
|  5 | c         |   100 |
|  6 | d         |  1000 |
+----+-----------+-------+

-- 发现没有 7 号王小花，于是插入一条数据：
INSERT INTO user VALUES (7, '王小花', 1000);
此时会发生什么呢？由于现在的隔离级别是 SERIALIZABLE ( 串行化 ) ，
		串行化的意思就是：假设把所有的事务都放在一个串行的队列中，
		那么所有的事务都会按照固定顺序执行，执行完一个事务后再继续执行下一个事务的写入操作 
		( 这意味着队列中同时只能执行一个事务的写入操作 ) 。

根据这个解释，小王在插入数据时，会出现等待状态，直到小张执行 COMMIT 结束它所处的事务，
			或者出现等待超时。
	
--如何使用可视化工具操作数据库
	
--如何在编程语言中操作数据库
