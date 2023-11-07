drop table enrolled_in;
drop table has_category;
drop table grade_on;
drop table class;
drop table assignment;
drop table category;
drop table student;

CREATE TABLE class (
class_id integer NOT NULL primary key AUTO_INCREMENT,
course_num VARCHAR(500) NOT NULL,
term VARCHAR(500) NOT NULL,
section_num integer NOT NULL,
class_desc VARCHAR(500) NOT NULL
);

CREATE TABLE category (
category_name VARCHAR(500) NOT NULL,
weight double(3,2) NOT NULL,
category_id integer NOT NULL primary key auto_increment
);

CREATE TABLE assignment (
assignment_id integer NOT NULL primary key AUTO_INCREMENT,
assignment_name VARCHAR(500) NOT NULL,
category_id integer NOT NULL,
assignment_desc VARCHAR(500) NOT NULL,
point_value integer NOT NULL,
FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE student (
student_id integer NOT NULL primary key,
student_last VARCHAR(500) NOT NULL,
student_first VARCHAR(500) NOT NULL,
username VARCHAR(500) NOT NULL unique
);

CREATE TABLE enrolled_in (
enroll_id INTEGER PRIMARY KEY AUTO_INCREMENT,
class_id integer NOT NULL references class,
student_id integer NOT NULL references student,
FOREIGN KEY (class_id) REFERENCES class (class_id),
FOREIGN KEY (student_id) REFERENCES student (student_id),
INDEX (class_id),
INDEX (student_id)
);

CREATE TABLE has_category (
has_id INTEGER PRIMARY KEY AUTO_INCREMENT,
class_id integer NOT NULL references class,
category_id integer NOT NULL references category,
FOREIGN KEY (class_id) REFERENCES class (class_id),
FOREIGN KEY (category_id) REFERENCES category (category_id),
INDEX (class_id),
INDEX (category_id)
);

CREATE TABLE grade_on (
grade_id INTEGER PRIMARY KEY AUTO_INCREMENT,
student_id integer NOT NULL references student,
assignment_id integer NOT NULL references assignment,
grade_score integer NOT NULL,
FOREIGN KEY (student_id) REFERENCES student (student_id),
FOREIGN KEY (assignment_id) REFERENCES assignment (assignment_id),
INDEX (student_id),
INDEX (assignment_id)
);