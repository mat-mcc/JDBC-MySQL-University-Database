# pyrefly: ignore [missing-import]
import mysql.connector
import argparse
import random
import os

DEPARTMENTS = [
    ("Biology", "Busch"),
    ("Chemistry", "College Ave"),
    ("Computer Science", "Busch"),
    ("Engineering", "Livingston"),
    ("Mathematics", "Livingston"),
    ("Physics", "Cook/Douglass")
]

# (Name, Credits, MaxEnrollment, DeptIndex)
COURSES = [
    ("General Biology", 4, 60, 0), ("Cell Biology", 3, 40, 0), ("Genetics", 3, 40, 0),
    ("General Chemistry", 4, 60, 1), ("Organic Chemistry", 4, 50, 1), ("Physical Chemistry", 3, 30, 1),
    ("Data Structures and Algorithms", 4, 50, 2), ("Computer Networks", 3, 40, 2), ("Operating Systems", 3, 40, 2),
    ("Statics", 3, 45, 3), ("Thermodynamics", 3, 40, 3), ("Fluid Mechanics", 3, 35, 3),
    ("Calculus", 4, 60, 4), ("Linear Algebra", 3, 50, 4), ("Probability and Statistics", 3, 50, 4),
    ("Classical Mechanics", 4, 50, 5), ("Electromagnetism", 3, 40, 5), ("Quantum Mechanics", 3, 30, 5)
]

STUDENT_NAMES = [
    ("Matthew", "McCaughan"), ("Emma", "Davis"), ("Olivia", "Carter"),
    ("Sophia", "Wilson"), ("Ava", "Taylor"), ("Isabella", "Nelson"),
    ("Joseph", "Adams"), ("Evelyn", "Parker"), ("Abigail", "Scott"),
    ("Emily", "Fisher"), ("Ella", "Bennett"), ("Scarlett", "Cooper")
]

GRADES = ["A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F"]
GRADE_WEIGHTS = [0.05, 0.20, 0.15, 0.15, 0.15, 0.10, 0.05, 0.05, 0.02, 0.02, 0.02, 0.02, 0.02]

def generate_sql():
    sql = []
    
    # Departments
    sql.append("INSERT INTO departments (name, campus) VALUES")
    dept_values = [f"('{name}', '{campus}')" for name, campus in DEPARTMENTS]
    sql.append(",\n".join(dept_values) + ";")
    
    # Courses
    sql.append("\nINSERT INTO courses (name, credits, max_enrollment, department_id) VALUES")
    course_values = [f"('{name}', {credits}, {max_enrollment}, {dept + 1})" for name, credits, max_enrollment, dept in COURSES]
    sql.append(",\n".join(course_values) + ";")
    
    # Students
    sql.append("\nINSERT INTO students (first_name, last_name, email) VALUES")
    student_values = []
    for first, last in STUDENT_NAMES:
        email = f"{first.lower()}.{last.lower()}@university.edu"
        student_values.append(f"('{first}', '{last}', '{email}')")
    sql.append(",\n".join(student_values) + ";")
    
    # Majors & Minors
    sql.append("\nINSERT INTO student_majors (student_id, department_id) VALUES")
    major_values = []
    minor_values = []
    for i in range(len(STUDENT_NAMES)):
        student_id = i + 1
        major = random.randint(1, len(DEPARTMENTS))
        minor = random.randint(1, len(DEPARTMENTS))
        while minor == major:
            minor = random.randint(1, len(DEPARTMENTS))
            
        major_values.append(f"({student_id}, {major})")
        # 50% chance of a minor
        if random.random() > 0.5:
            minor_values.append(f"({student_id}, {minor})")
            
    sql.append(",\n".join(major_values) + ";")
    if minor_values:
        sql.append("\nINSERT INTO student_minors (student_id, department_id) VALUES")
        sql.append(",\n".join(minor_values) + ";")
        
    # Enrollments
    sql.append("\nINSERT INTO enrollments (student_id, course_id, status, grade) VALUES")
    enrollment_values = []
    
    # We want unique student/course pairs
    for student_id in range(1, len(STUDENT_NAMES) + 1):
        num_courses = random.randint(3, 8)
        chosen_courses = random.sample(range(1, len(COURSES) + 1), num_courses)
        
        for course_id in chosen_courses:
            status_rand = random.random()
            if status_rand < 0.6:
                status = "COMPLETED"
                grade = random.choices(GRADES, GRADE_WEIGHTS)[0]
                val = f"({student_id}, {course_id}, '{status}', '{grade}')"
            elif status_rand < 0.9:
                status = "ENROLLED"
                val = f"({student_id}, {course_id}, '{status}', NULL)"
            else:
                status = "DROPPED"
                val = f"({student_id}, {course_id}, '{status}', NULL)"
                
            enrollment_values.append(val)
            
    sql.append(",\n".join(enrollment_values) + ";")
    
    return "\n".join(sql)

def execute_sql(host, port, user, password, database, sql_script):
    try:
        conn = mysql.connector.connect(
            host=host,
            port=port,
            user=user,
            password=password,
            database=database
        )
        cursor = conn.cursor()
        
        # Split statements by ';' and execute
        statements = sql_script.split(";")
        for statement in statements:
            if statement.strip():
                cursor.execute(statement)
        
        conn.commit()
        print("Data successfully generated and inserted into the database.")
        
    except mysql.connector.Error as err:
        print(f"Error: {err}")
    finally:
        if 'conn' in locals() and conn.is_connected():
            cursor.close()
            conn.close()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Generate university seed data.')
    parser.add_argument('--sql-only', action='store_true', help='Output SQL to stdout instead of inserting to DB')
    parser.add_argument('--host', default='localhost')
    parser.add_argument('--port', default='3306')
    parser.add_argument('--user', default='root')
    parser.add_argument('--password', default='')
    parser.add_argument('--database', default='university')
    
    args = parser.parse_args()
    
    sql_output = generate_sql()
    
    if args.sql_only:
        print(sql_output)
    else:
        execute_sql(args.host, args.port, args.user, args.password, args.database, sql_output)
