-- ============================================================
-- V2: Seed Data
-- 6 departments, 59 courses, 20 students with majors/minors
-- and sample enrollments (mix of ENROLLED, COMPLETED, DROPPED).
-- ============================================================

-- ----------------------------
-- Departments
-- ----------------------------
INSERT INTO departments (name, campus) VALUES
('Biology', 'Busch'),
('Chemistry', 'College Ave'),
('Computer Science', 'Busch'),
('Engineering', 'Livingston'),
('Mathematics', 'Livingston'),
('Physics', 'Cook/Douglass');

-- ----------------------------
-- Courses
-- ----------------------------
-- Biology
INSERT INTO courses (name, credits, max_enrollment, department_id) VALUES
('General Biology', 4, 60, 1),
('Cell Biology', 3, 40, 1),
('Genetics', 3, 40, 1),
('Ecology', 3, 35, 1),
('Evolutionary Biology', 3, 35, 1),
('Microbiology', 3, 30, 1),
('Anatomy and Physiology', 4, 50, 1),
('Neurobiology', 3, 30, 1),
('Immunology', 3, 30, 1);

-- Chemistry
INSERT INTO courses (name, credits, max_enrollment, department_id) VALUES
('General Chemistry', 4, 60, 2),
('Organic Chemistry', 4, 50, 2),
('Physical Chemistry', 3, 30, 2),
('Analytical Chemistry', 3, 30, 2),
('Inorganic Chemistry', 3, 25, 2),
('Biochemistry', 3, 40, 2),
('Polymer Chemistry', 3, 25, 2),
('Environmental Chemistry', 3, 30, 2),
('Medicinal Chemistry', 3, 25, 2),
('Material Chemistry', 3, 25, 2);

-- Computer Science
INSERT INTO courses (name, credits, max_enrollment, department_id) VALUES
('Data Structures and Algorithms', 4, 50, 3),
('Computer Networks', 3, 40, 3),
('Operating Systems', 3, 40, 3),
('Artificial Intelligence', 3, 35, 3),
('Machine Learning', 3, 35, 3),
('Database Systems', 3, 40, 3),
('Computer Security', 3, 30, 3),
('Web Programming', 3, 40, 3),
('Software Engineering', 3, 40, 3),
('Computer Graphics', 3, 30, 3);

-- Engineering
INSERT INTO courses (name, credits, max_enrollment, department_id) VALUES
('Statics', 3, 45, 4),
('Thermodynamics', 3, 40, 4),
('Fluid Mechanics', 3, 35, 4),
('Dynamics and Control', 3, 35, 4),
('Materials Science and Engineering', 3, 30, 4),
('Electric Circuits and Electronics', 4, 40, 4),
('Computer Design and Manufacturing', 3, 30, 4),
('Structural Analysis and Design', 3, 30, 4),
('Environmental Engineering', 3, 35, 4),
('Robotics and Automation', 3, 30, 4);

-- Mathematics
INSERT INTO courses (name, credits, max_enrollment, department_id) VALUES
('Calculus', 4, 60, 5),
('Linear Algebra', 3, 50, 5),
('Probability and Statistics', 3, 50, 5),
('Differential Equations', 3, 40, 5),
('Number Theory', 3, 30, 5),
('Real Analysis', 3, 25, 5),
('Complex Analysis', 3, 25, 5),
('Topology', 3, 20, 5),
('Graph Theory', 3, 30, 5),
('Numerical Analysis', 3, 30, 5);

-- Physics
INSERT INTO courses (name, credits, max_enrollment, department_id) VALUES
('Classical Mechanics', 4, 50, 6),
('Electromagnetism', 3, 40, 6),
('Quantum Mechanics', 3, 30, 6),
('Thermodynamics and Statistical Mechanics', 3, 30, 6),
('Astrophysics', 3, 35, 6),
('Atomic and Molecular Physics', 3, 25, 6),
('Condensed Matter Physics', 3, 25, 6),
('Nuclear Physics', 3, 20, 6),
('Relativity', 3, 25, 6),
('Optics and Waves', 3, 30, 6);

-- ----------------------------
-- Students (20 sample students)
-- ----------------------------
INSERT INTO students (first_name, last_name, email) VALUES
('Matthew', 'McCaughan', 'matt.mccaughan@university.edu'),
('Emma', 'Davis', 'emma.davis@university.edu'),
('Olivia', 'Carter', 'olivia.carter@university.edu'),
('Sophia', 'Wilson', 'sophia.wilson@university.edu'),
('Ava', 'Taylor', 'ava.taylor@university.edu'),
('Isabella', 'Nelson', 'isabella.nelson@university.edu'),
('Mia', 'Robinson', 'mia.robinson@university.edu'),
('Charlotte', 'Lee', 'charlotte.lee@university.edu'),
('Amelia', 'Harris', 'amelia.harris@university.edu'),
('Harper', 'Jackson', 'harper.jackson@university.edu'),
('Joseph', 'Adams', 'joseph.adams@university.edu'),
('Evelyn', 'Parker', 'evelyn.parker@university.edu'),
('Abigail', 'Scott', 'abigail.scott@university.edu'),
('Emily', 'Fisher', 'emily.fisher@university.edu'),
('Ella', 'Bennett', 'ella.bennett@university.edu'),
('Scarlett', 'Cooper', 'scarlett.cooper@university.edu'),
('Grace', 'Quinn', 'grace.quinn@university.edu'),
('Chloe', 'Gonzalez', 'chloe.gonzalez@university.edu'),
('Aria', 'Kim', 'aria.kim@university.edu'),
('Luna', 'Zhang', 'luna.zhang@university.edu');

-- ----------------------------
-- Student Majors
-- ----------------------------
INSERT INTO student_majors (student_id, department_id) VALUES
(1, 3),   -- Matthew → CS
(2, 1),   -- Emma → Bio
(3, 2),   -- Olivia → Chem
(4, 3),   -- Sophia → CS
(5, 5),   -- Ava → Math
(6, 4),   -- Isabella → Engineering
(7, 1),   -- Mia → Bio
(8, 6),   -- Charlotte → Physics
(9, 3),   -- Amelia → CS
(10, 2),  -- Harper → Chem
(11, 4),  -- Joseph → Engineering
(12, 5),  -- Evelyn → Math
(13, 1),  -- Abigail → Bio
(14, 3),  -- Emily → CS
(15, 6),  -- Ella → Physics
(16, 2),  -- Scarlett → Chem
(17, 5),  -- Grace → Math
(18, 4),  -- Chloe → Engineering
(19, 3),  -- Aria → CS
(20, 6);  -- Luna → Physics

-- ----------------------------
-- Student Minors
-- ----------------------------
INSERT INTO student_minors (student_id, department_id) VALUES
(1, 5),   -- Matthew → Math minor
(2, 2),   -- Emma → Chem minor
(4, 5),   -- Sophia → Math minor
(5, 3),   -- Ava → CS minor
(6, 5),   -- Isabella → Math minor
(8, 5),   -- Charlotte → Math minor
(9, 5),   -- Amelia → Math minor
(11, 3),  -- Joseph → CS minor
(14, 5),  -- Emily → Math minor
(19, 5);  -- Aria → Math minor

-- ----------------------------
-- Enrollments — COMPLETED (with grades)
-- ----------------------------
INSERT INTO enrollments (student_id, course_id, status, grade) VALUES
-- Matthew (CS major): completed foundational courses
(1, 20, 'COMPLETED', 'A'),   -- Data Structures
(1, 25, 'COMPLETED', 'A'),   -- Database Systems
(1, 28, 'COMPLETED', 'B'),   -- Software Engineering
(1, 40, 'COMPLETED', 'A'),   -- Calculus
(1, 41, 'COMPLETED', 'B'),   -- Linear Algebra
(1, 42, 'COMPLETED', 'A'),   -- Prob & Stats

-- Emma (Bio major)
(2, 1, 'COMPLETED', 'A'),    -- General Bio
(2, 2, 'COMPLETED', 'B'),    -- Cell Bio
(2, 3, 'COMPLETED', 'A'),    -- Genetics
(2, 10, 'COMPLETED', 'B'),   -- General Chem
(2, 11, 'COMPLETED', 'C'),   -- Organic Chem

-- Olivia (Chem major)
(3, 10, 'COMPLETED', 'A'),   -- General Chem
(3, 11, 'COMPLETED', 'A'),   -- Organic Chem
(3, 12, 'COMPLETED', 'B'),   -- Physical Chem
(3, 1, 'COMPLETED', 'B'),    -- General Bio

-- Sophia (CS major)
(4, 20, 'COMPLETED', 'B'),   -- Data Structures
(4, 21, 'COMPLETED', 'A'),   -- Computer Networks
(4, 22, 'COMPLETED', 'B'),   -- Operating Systems
(4, 40, 'COMPLETED', 'C'),   -- Calculus

-- Ava (Math major)
(5, 40, 'COMPLETED', 'A'),   -- Calculus
(5, 41, 'COMPLETED', 'A'),   -- Linear Algebra
(5, 43, 'COMPLETED', 'A'),   -- Differential Equations
(5, 42, 'COMPLETED', 'B'),   -- Prob & Stats
(5, 20, 'COMPLETED', 'B'),   -- Data Structures

-- Isabella (Engineering major)
(6, 30, 'COMPLETED', 'B'),   -- Statics
(6, 31, 'COMPLETED', 'C'),   -- Thermodynamics
(6, 40, 'COMPLETED', 'B'),   -- Calculus
(6, 35, 'COMPLETED', 'A'),   -- Electric Circuits

-- Joseph (Engineering major)
(11, 30, 'COMPLETED', 'A'),  -- Statics
(11, 31, 'COMPLETED', 'A'),  -- Thermodynamics
(11, 32, 'COMPLETED', 'B'),  -- Fluid Mechanics
(11, 20, 'COMPLETED', 'B'),  -- Data Structures
(11, 40, 'COMPLETED', 'A'),  -- Calculus

-- Charlotte (Physics major)
(8, 49, 'COMPLETED', 'A'),   -- Classical Mechanics
(8, 50, 'COMPLETED', 'B'),   -- Electromagnetism
(8, 51, 'COMPLETED', 'A'),   -- Quantum Mechanics
(8, 40, 'COMPLETED', 'A'),   -- Calculus

-- Amelia (CS major)
(9, 20, 'COMPLETED', 'A'),   -- Data Structures
(9, 23, 'COMPLETED', 'A'),   -- AI
(9, 24, 'COMPLETED', 'A'),   -- Machine Learning
(9, 40, 'COMPLETED', 'B'),   -- Calculus

-- Grace (Math major)
(17, 40, 'COMPLETED', 'A'),  -- Calculus
(17, 41, 'COMPLETED', 'A'),  -- Linear Algebra
(17, 45, 'COMPLETED', 'B'),  -- Real Analysis
(17, 44, 'COMPLETED', 'A'),  -- Number Theory

-- Luna (Physics major)
(20, 49, 'COMPLETED', 'B'),  -- Classical Mechanics
(20, 50, 'COMPLETED', 'A'),  -- Electromagnetism
(20, 40, 'COMPLETED', 'B'),  -- Calculus
(20, 41, 'COMPLETED', 'A');  -- Linear Algebra

-- ----------------------------
-- Enrollments — ENROLLED (currently taking)
-- ----------------------------
INSERT INTO enrollments (student_id, course_id, status) VALUES
-- Matthew currently taking
(1, 23, 'ENROLLED'),  -- AI
(1, 24, 'ENROLLED'),  -- Machine Learning
(1, 27, 'ENROLLED'),  -- Web Programming

-- Emma currently taking
(2, 5, 'ENROLLED'),   -- Evolutionary Bio
(2, 6, 'ENROLLED'),   -- Microbiology

-- Sophia currently taking
(4, 23, 'ENROLLED'),  -- AI
(4, 25, 'ENROLLED'),  -- Database Systems

-- Ava currently taking
(5, 44, 'ENROLLED'),  -- Number Theory
(5, 45, 'ENROLLED'),  -- Real Analysis

-- Joseph currently taking
(11, 33, 'ENROLLED'), -- Dynamics and Control
(11, 39, 'ENROLLED'), -- Robotics

-- Charlotte currently taking
(8, 52, 'ENROLLED'),  -- Thermo & Stat Mech
(8, 41, 'ENROLLED'),  -- Linear Algebra

-- Amelia currently taking
(9, 25, 'ENROLLED'),  -- Database Systems
(9, 26, 'ENROLLED'),  -- Computer Security

-- Luna currently taking
(20, 51, 'ENROLLED'), -- Quantum Mechanics
(20, 53, 'ENROLLED'); -- Astrophysics

-- ----------------------------
-- Enrollments — DROPPED (a few samples)
-- ----------------------------
INSERT INTO enrollments (student_id, course_id, status) VALUES
(4, 24, 'DROPPED'),   -- Sophia dropped Machine Learning
(6, 32, 'DROPPED'),   -- Isabella dropped Fluid Mechanics
(2, 15, 'DROPPED');   -- Emma dropped Biochemistry
