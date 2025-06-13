#Matthew McCaughan
#Uni Database Data Generator

import random
import math
import time

def tables():
    print()
    print("CREATE TABLE Departments(name varchar(255), campus varchar(255), PRIMARY KEY (name));")
    print("CREATE TABLE Students(first_name varchar(255), last_name varchar(255), id int, PRIMARY KEY(id));")
    print("CREATE TABLE Classes(name varchar(255), credits int, PRIMARY KEY(name));")
    print("CREATE TABLE Majors(sid int, dname varchar(255), FOREIGN KEY(sid) REFERENCES Students(id), FOREIGN KEY(dname) REFERENCES Departments(name));")
    print("CREATE TABLE Minors(sid int, dname varchar(255), FOREIGN KEY(sid) REFERENCES Students(id), FOREIGN KEY(dname) REFERENCES Departments(name));")
    print("CREATE TABLE IsTaking(sid int, name varchar(255), FOREIGN KEY(sid) REFERENCES Students(id), FOREIGN KEY(name) REFERENCES Classes(name));")
    print("CREATE TABLE HasTaken(sid int, name varchar(255), grade varchar(255), FOREIGN KEY(sid) REFERENCES Students(id), FOREIGN KEY(name) REFERENCES Classes(name));")
    print()



firstnames = [ "Gina","Deanna","Gaby", "Joseph", "Matthew","Emma", "Olivia",  "Ava",  "Isabella",  "Sophia",  "Mia",  "Charlotte",  "Amelia",  "Harper",  "Evelyn",  "Abigail",  "Emily",  "Elizabeth",  "Mila",  "Ella",  "Avery",  "Sofia",  "Camila",  "Aria",  "Scarlett",  "Victoria",  "Madison",  "Luna",  "Grace",  "Chloe",  "Penelope",  "Layla",  "Riley",  "Zoey",  "Nora",  "Lily",  "Eleanor",  "Hannah",  "Lillian",  "Addison",  "Aubrey",  "Ellie",  "Stella",  "Natalie",  "Zoe",  "Leah",  "Hazel",  "Violet",  "Aurora",  "Savannah",  "Audrey",  "Brooklyn",  "Bella",  "Claire",  "Skylar",  "Lucy",  "Paisley",  "Everly",  "Anna",  "Caroline",  "Nova",  "Genesis",  "Emilia",  "Kennedy",  "Samantha",  "Maya",  "Willow",  "Kinsley",  "Naomi",  "Aaliyah",  "Elena",  "Sarah",  "Ariana",  "Allison",  "Gabriella",  "Alice",  "Madelyn",  "Cora",  "Ruby",  "Eva",  "Serenity",  "Autumn",  "Adeline",  "Hailey",  "Gianna",  "Valentina",  "Isla",  "Eliana",  "Quinn",  "Nevaeh",  "Ivy",  "Sadie",  "Piper",  "Lydia",  "Alexa",  "Josephine",  "Emery",  "Julia",  "Delilah",  "Arianna"]

lastnames = [ "Dimoski" ,"Ahmed", "Decarlo", "Adams",  "Baker",  "Carter",  "Davis",  "Edwards",  "Ford",  "Graham",  "Harris",  "Irwin",  "Jackson",  "Keller",  "Lee",  "Mason",  "Nelson",  "Owens",  "Parker",  "Quinn",  "Robinson",  "Scott",  "Taylor",  "Underwood",  "Vaughn",  "Wilson",  "Yates",  "Zimmerman",  "Allen",  "Bennett",  "Cooper",  "Dixon",  "Ellis",  "Fisher",  "Gibson",  "Henderson",  "Ingram",  "Jennings",  "Kelly",  "Lawson",  "Matthews",  "Nichols",  "Oliver",  "Phillips",  "Reynolds",  "Simpson",  "Thomas",  "Underhill",  "Vargas",  "Wheeler",  "Young",  "Avery",  "Black",  "Chan",  "Douglas",  "Emerson",  "Fletcher",  "Gonzalez",  "Holloway",  "Iverson",  "Jefferson",  "Kim",  "Lambert",  "Marshall",  "Nash",  "Brien",  "Porter",  "Quintana",  "Ramsey",  "Sullivan",  "Tucker",  "Ulrich",  "Vega",  "Wallace",  "Xu",  "Yamamoto",  "Zhang",  "Armstrong",  "Barnes",  "Chapman",  "Dean",  "Fleming",  "Greene",  "Hawkins",  "Isaacs",  "Jenkins",  "Kennedy",  "Lawrence",  "McGuire",  "Norton",  "Ortega",  "Peterson",  "Reid",  "Santos",  "Turner",  "Vasquez",  "Wright",  "Xiong",  "Youngblood",  "Ziegler"]


Departments = ["Bio", "Chem", "CS", "Engineering", "Math", "Physics"]
DepartLoc = ["Busch", "CAC", "Busch", "Livi", "Livi", "CD"]

bioClasses = [  "General Biology",
                "Cell Biology",
                "Genetics",
                "Ecology",
                "Evolutionary Biology",
                "Microbiology",
                "Anatomy and Physiology",
                "Neurobiology",
                "Immunology"]

chemClasses = [  "General Chemistry",
                 "Organic Chemistry",
                 "Physical Chemistry",
                 "Analytical Chemistry",
                 "Inorganic Chemistry",
                 "Biochemistry",
                 "Polymer Chemistry",
                 "Environmental Chemistry",
                 "Medicinal Chemistry",
                 "Material Chemistry"]

csClasses = [  "Data Structures and Algorithms",
             "Computer Networks",
             "Operating Systems",
             "Artificial Intelligence",
             "Machine Learning",
             "Database Systems",
             "Computer Security",
             "Web Programming",
             "Software Engineering",
             "Computer Graphics"]

engineeringClasses = [  "Statics",
                        "Thermodynamics",
                        "Fluid Mechanics",
                        "Dynamics and Control",
                        "Materials Science and Engineering",
                        "Electric Circuits and Electronics",
                        "Computer Design and Manufacturing",
                        "Structural Analysis and Design",
                        "Environmental Engineering",
                        "Robotics and Automation"]

mathClasses = [  "Calculus",
                 "Linear Algebra",
                 "Probability and Statistics",
                 "Differential Equations",
                 "Number Theory",
                 "Real Analysis",
                 "Complex Analysis",
                 "Topology",
                 "Graph Theory",
                 "Numerical Analysis"]


physicsClasses = [  "Classical Mechanics",
                    "Electromagnetism",
                    "Quantum Mechanics",
                    "Thermodynamics and Statistical Mechanics",
                    "Astrophysics",
                    "Atomic and Molecular Physics",
                    "Condensed Matter Physics",
                    "Nuclear Physics",
                    "Relativity",
                    "Optics and Waves"]

grades = ["A","B","C","D","F"]

allClasses = []
allClasses.extend(bioClasses)
allClasses.extend(chemClasses)
allClasses.extend(csClasses)
allClasses.extend(engineeringClasses)
allClasses.extend(mathClasses)
allClasses.extend(physicsClasses)
random.shuffle(allClasses)

def main():
#print("DEPARTMENTS:")
    for i in range(6):
             print("INSERT INTO Departments VALUES(" +
                "'"+ Departments[i] +"'" + ", " + "'" + DepartLoc[i]
                   + "'" + ");" )
    print()
    for i in range(len(allClasses)):
        print("INSERT INTO Classes VALUES("+ "'" + str(allClasses[i]) + "'" + "," + "'" + str(3) + "');")


             
    #print("STUDENTS:")
    print()
    for i in range(100):
        choice1 = random.randint(0,99)
        choice2 = random.randint(0,99)
        idC = random.randint(100000,200000)
        majorC = random.randint(0,5)
        classesC = random.randint(4,40)
        AttemptedCredits = 3*classesC
    
        if majorC == 0:
            minorC = 1
        else:
            minorC = majorC - 1
        print("INSERT INTO Students VALUES(" + "'" + str(firstnames[choice1]) +
              "'" + "," + "'" + str(lastnames[choice2])+ "'" + "," + "'"+ str(idC) + "');")
    
        print("INSERT INTO Majors VALUES ("+ "'" + str(idC) + "'" + "," + "'" + str(Departments[majorC]) + "');")
        print("INSERT INTO Minors VALUES ("+ "'" + str(idC) + "'" + "," + "'" + str(Departments[minorC]) + "');")
        #print("credits:", creditsC)
    
    # for classes, remove while credits > 0
        tempList = allClasses.copy()
        tempA = AttemptedCredits
        classList = []
        size = 58
        while tempA > 0:
            classChoice = random.randint(0,size)
            classList.append(tempList[classChoice])
            tempList.remove(tempList[classChoice])
            tempA = tempA - 3
            size = size - 1
    #print(classesC ,"CLASSES:")
    #for i in range(classesC):
     #   print(classList[i])
    #print("Classes Taking:")
        for i in range(4):
            print("INSERT INTO IsTaking VALUES(" + "'" + str(idC) + "'" + "," + "'"
              + str(classList[i]) + "');")

    #print("Classes Taken:")
        for i in range(len(classList[4:])):
            gradesC = random.randint(0,4)
            print("INSERT INTO HasTaken VALUES(" + "'" + str(idC) + "'" + "," + "'"
              + str(classList[i]) + "'" + "," + "'" + str(grades[gradesC]) + "');")
        print()
        print()
        
print("********************************************************")
print("             RANDOM DATA GENERATOR")
print("             UNI DATABASE PROJECT")
print("                APRIL 7TH 2023")
print("********************************************************")
print(" COMMANDS:")
print("'generate': Generates Random Data")
print("'tables' : Provides schema tables")
print("'about' : extra information")
print("'exit' : exit program")
print("********************************************************")


while True:
    user_input = input("Enter a command: ")

    if user_input == "generate":
        print("generating...")
        time.sleep(1)
        main()

        time.sleep(1)
        print()
        print("Done!")
    if user_input == "tables":
        tables()
    if user_input == "about":
        print("Matthew McCaughan")
        print("Uni Database Random Data Generator")
        print("This Python program is designed to generate SQL queries for a linked database, this program spews them all out into the terminal where they then can be copied and pasted into a schema.)")
    if user_input == "exit":
        break


print("Exiting Program")


    

