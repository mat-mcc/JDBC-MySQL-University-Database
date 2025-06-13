import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 *      MATTHEW MCCAUGHAN
 *      JDBC APPLICATION & MySQL DATABASE : UNI DATABASE PROJECT
 *      APRIL 18th, 2023
 */

public class DataBase {

    static double Get_GPA(int sid, Connection conn) {
        try {
            String sql = "Select grade, credits FROM HasTaken JOIN Classes ON Hastaken.name = Classes.name WHERE sid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();
            int totalCredits = 0;
            int totalGradePoints = 0;
            while (rs.next()) {
                String grade = rs.getString("grade");
                int credits = rs.getInt("credits");
                int gradeValue = 0;
                switch (grade) {
                    case "A":
                        gradeValue = 4;
                        break;
                    case "B":
                        gradeValue = 3;
                        break;
                    case "C":
                        gradeValue = 2;
                        break;
                    case "D":
                        gradeValue = 1;
                        break;
                    case "F":
                        gradeValue = 0;
                        break;
                }
                totalCredits = totalCredits + credits;
                totalGradePoints += (credits * gradeValue);
            }
            if (totalCredits == 0) {
                throw new ArithmeticException("Cannot calculate GPA for student with no credits");
            }

            double gpa = (double) totalGradePoints / totalCredits;

            return gpa;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

    static int Get_Credits(int sid, Connection conn) {
        try {
            String sql = "Select grade, credits FROM HasTaken JOIN Classes ON Hastaken.name = Classes.name WHERE sid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sid);
            ResultSet rs = pstmt.executeQuery();
            int totalCredits = 0;
            while (rs.next()) {
                String grade = rs.getString("grade");
                int credits = rs.getInt("credits");
                int gradeValue = 0;
                switch (grade) {
                    case "A":
                        gradeValue = 4;
                        break;
                    case "B":
                        gradeValue = 3;
                        break;
                    case "C":
                        gradeValue = 2;
                        break;
                    case "D":
                        gradeValue = 1;
                        break;
                    case "F":
                        gradeValue = 0;
                        break;
                }
                if (gradeValue == 0) {
                    continue;
                } else {
                    totalCredits = totalCredits + credits;
                }

            }
            if (totalCredits == 0) {
                throw new ArithmeticException("Cannot calculate GPA for student with no credits");
            }

            // Close the resources

            // Return the GPA
            return totalCredits;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    };


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String urlinput = args[0];
        String unameinput = args[1];        
        String passinput = args[2];
        
        //String url = "jdbc:mysql://localhost:3306/data";
        //String uname = "root";

        

        Connection conn = DriverManager.getConnection(urlinput, unameinput, passinput);

        System.out.println("********************************************");
        System.out.println("Welcome to the university database! Queries available:");
        System.out.println("1. Search Students by name.");
        System.out.println("2. Search students by year.");
        System.out.println("3. Search for students with a GPA >= threshold");
        System.out.println("4. Search for students with a GPA <= threshold");
        System.out.println("5. Get department statistics");
        System.out.println("6. Get class statistics");
        System.out.println("7. Execute an arbitrary SQL query");
        System.out.println("8. Exit the application");

        System.out.println("********************************************");
        String input = "";

        // MAIN WHILE LOOP
        while (!input.equals("exit")) {
            System.out.print("WHICH QUERY WOULD YOU LIKE TO RUN? (1-8): ");
            input = scanner.nextLine();

            // QUERY 1: SEARCH BY NAME
            if (input.equals("1")) {
                System.out.println("Searching by name, please enter keyword to match:");
                String nameC = scanner.nextLine();
                String nameQ = "%" + nameC + "%";
                System.out.println();
                String sql = "SELECT * FROM Students WHERE first_name LIKE ? OR last_name LIKE ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nameQ);
                pstmt.setString(2, nameQ);

                ResultSet Q1 = pstmt.executeQuery();
                int lengthrs = 0;

                while (Q1.next()) {

                    lengthrs++;
                    int StudID = Q1.getInt("id");
                    System.out.println("Student #" + lengthrs);
                    System.out.println("Name: " + Q1.getString("first_name") + " " + Q1.getString("last_name"));
                    System.out.println("ID: " + Q1.getString("id"));

                    System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                    System.out.println("Credits: " + Get_Credits(StudID, conn));
                    String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                    String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                    String Bar = "";
                    String Bar2 = "";
                    PreparedStatement MajStatement = conn.prepareStatement(Maj);
                    MajStatement.setInt(1, StudID);
                    ResultSet Q00 = MajStatement.executeQuery(); 
                    int MajCount = 0;

                     while (Q00.next()) {
                        if(MajCount > 0){
                        Bar = Bar + ", ";
                        }
                        String MM = Q00.getString("Majors.dname");
                        Bar = Bar + MM + "";
                        MajCount++;
                     }
                     PreparedStatement MinStatement = conn.prepareStatement(Min);
                     MinStatement.setInt(1, StudID);
                     ResultSet Q01 = MinStatement.executeQuery(); 
                     int MinCount = 0;
                     while (Q01.next()) {
                        if(MinCount >0){
                            Bar2 = Bar2 + ", ";
                        }
                     String mm = Q01.getString("Minors.dname");
                     Bar2 = Bar2+ mm + "";
                     MinCount++;
                     }
                    if (MajCount == 0) {
                        System.out.println("Major(s): None");
                    }
                    else{
                    System.out.println("Major(s): " + Bar);
                    }
                    if (MinCount == 0){
                        System.out.println("Minor(s): None");
                    }
                    else{
                    System.out.println("Minor(s): " + Bar2);
                    }
                    }
                    System.out.println();


                if (lengthrs == 0) {
                    System.out.println("No students found matching input");
                }
                System.out.println("Returned " + lengthrs + " results");
                System.out.println();

            }

            if (input.equals("2")) {
                int count = 0;
                System.out.println("Searching by year, please enter year (Fr, So, Ju, Sr):");
                String nameC = scanner.nextLine();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Students");

                if (nameC.equals("Fr")) {
                    System.out.println("Searching for Freshmen...");
                    System.out.println();
                }

                if (nameC.equals("So")) {
                    System.out.println("Searching for Sophomores...");
                    System.out.println();
                }
                if (nameC.equals("Ju")) {
                    System.out.println("Searching for Juniors...");
                    System.out.println();
                }
                if (nameC.equals("Sr")) {
                    System.out.println("Searching for Seniors...");
                    System.out.println();
                }

                while (rs.next()) {
                    int StudID = rs.getInt("id");
                    int Threshhold = Get_Credits(StudID, conn);

                    if (nameC.equals("Fr")) {

                        if (Threshhold < 30) {
                            count++;
                            System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                            System.out.println("ID: " + rs.getString("id"));

                            System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                            System.out.println("Credits: " + Get_Credits(StudID, conn));
                            String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                    String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                    String Bar = "";
                    String Bar2 = "";
                    PreparedStatement MajStatement = conn.prepareStatement(Maj);
                    MajStatement.setInt(1, StudID);
                    ResultSet Q00 = MajStatement.executeQuery(); 
                    int MajCount = 0;

                    while (Q00.next()) {
                        if(MajCount > 0){
                        Bar = Bar + ", ";
                        }
                        String MM = Q00.getString("Majors.dname");
                        Bar = Bar + MM + "";
                        MajCount++;
                     }
                     PreparedStatement MinStatement = conn.prepareStatement(Min);
                     MinStatement.setInt(1, StudID);
                     ResultSet Q01 = MinStatement.executeQuery(); 
                     int MinCount = 0;
                     while (Q01.next()) {
                        if(MinCount >0){
                            Bar2 = Bar2 + ", ";
                        }
                     String mm = Q01.getString("Minors.dname");
                     Bar2 = Bar2+ mm + " ";
                     MinCount++;
                     }
                    if (MajCount == 0) {
                        System.out.println("Major(s): None");
                    }
                    else{
                    System.out.println("Major(s): " + Bar);
                    }
                    if (MinCount == 0){
                        System.out.println("Minor(s): None");
                    }
                    else{
                    System.out.println("Minor(s): " + Bar2);
                    }

                            
                        }
                    }
                    if (nameC.equals("So")) {
                        if (Threshhold >= 30 & Threshhold < 60) {
                            count++;
                            System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                            System.out.println("ID: " + rs.getString("id"));

                            System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                            System.out.println("Credits: " + Get_Credits(StudID, conn));
                            String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                            String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                            String Bar = "";
                            String Bar2 = "";
                            PreparedStatement MajStatement = conn.prepareStatement(Maj);
                            MajStatement.setInt(1, StudID);
                            ResultSet Q00 = MajStatement.executeQuery(); 
                            int MajCount = 0;
        
                            while (Q00.next()) {
                                if(MajCount > 0){
                                Bar = Bar + ", ";
                                }
                                String MM = Q00.getString("Majors.dname");
                                Bar = Bar + MM + "";
                                MajCount++;
                             }
                             PreparedStatement MinStatement = conn.prepareStatement(Min);
                             MinStatement.setInt(1, StudID);
                             ResultSet Q01 = MinStatement.executeQuery(); 
                             int MinCount = 0;
                             while (Q01.next()) {
                                if(MinCount >0){
                                    Bar2 = Bar2 + ", ";
                                }
                             String mm = Q01.getString("Minors.dname");
                             Bar2 = Bar2+ mm + " ";
                             MinCount++;
                             }
                            if (MajCount == 0) {
                                System.out.println("Major(s): None");
                            }
                            else{
                            System.out.println("Major(s): " + Bar);
                            }
                            if (MinCount == 0){
                                System.out.println("Minor(s): None");
                            }
                            else{
                            System.out.println("Minor(s): " + Bar2);
                            }
        
                                    
                                }
                            }
                    if (nameC.equals("Ju")) {
                        if (Threshhold >= 60 & Threshhold < 90) {
                            count++;
                            System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                            System.out.println("ID: " + rs.getString("id"));

                            System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                            System.out.println("Credits: " + Get_Credits(StudID, conn));
                            String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                            String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                            String Bar = "";
                            String Bar2 = "";
                            PreparedStatement MajStatement = conn.prepareStatement(Maj);
                            MajStatement.setInt(1, StudID);
                            ResultSet Q00 = MajStatement.executeQuery(); 
                            int MajCount = 0;
        
                            while (Q00.next()) {
                                if(MajCount > 0){
                                Bar = Bar + ", ";
                                }
                                String MM = Q00.getString("Majors.dname");
                                Bar = Bar + MM + "";
                                MajCount++;
                             }
                             PreparedStatement MinStatement = conn.prepareStatement(Min);
                             MinStatement.setInt(1, StudID);
                             ResultSet Q01 = MinStatement.executeQuery(); 
                             int MinCount = 0;
                             while (Q01.next()) {
                                if(MinCount >0){
                                    Bar2 = Bar2 + ", ";
                                }
                             String mm = Q01.getString("Minors.dname");
                             Bar2 = Bar2+ mm + " ";
                             MinCount++;
                             }
                            if (MajCount == 0) {
                                System.out.println("Major(s): None");
                            }
                            else{
                            System.out.println("Major(s): " + Bar);
                            }
                            if (MinCount == 0){
                                System.out.println("Minor(s): None");
                            }
                            else{
                            System.out.println("Minor(s): " + Bar2);
                            }

                            

                        }
                    }
                    if (nameC.equals("Sr")) {
                        if (Threshhold >= 90) {
                            count++;
                            System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                            System.out.println("ID: " + rs.getString("id"));

                            System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                            System.out.println("Credits: " + Get_Credits(StudID, conn));
                            String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                    String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                    String Bar = "";
                    String Bar2 = "";
                    PreparedStatement MajStatement = conn.prepareStatement(Maj);
                    MajStatement.setInt(1, StudID);
                    ResultSet Q00 = MajStatement.executeQuery(); 
                    int MajCount = 0;

                    while (Q00.next()) {
                        if(MajCount > 0){
                        Bar = Bar + ", ";
                        }
                        String MM = Q00.getString("Majors.dname");
                        Bar = Bar + MM + "";
                        MajCount++;
                     }
                     PreparedStatement MinStatement = conn.prepareStatement(Min);
                     MinStatement.setInt(1, StudID);
                     ResultSet Q01 = MinStatement.executeQuery(); 
                     int MinCount = 0;
                     while (Q01.next()) {
                        if(MinCount >0){
                            Bar2 = Bar2 + ", ";
                        }
                     String mm = Q01.getString("Minors.dname");
                     Bar2 = Bar2+ mm + " ";
                     MinCount++;
                     }
                    if (MajCount == 0) {
                        System.out.println("Major(s): None");
                    }
                    else{
                    System.out.println("Major(s): " + Bar);
                    }
                    if (MinCount == 0){
                        System.out.println("Minor(s): None");
                    }
                    else{
                    System.out.println("Minor(s): " + Bar2);
                    }

                            
                        }
                    }

                }
                if (count == 0) {
                    System.out.println("No students found at this grade level");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Returned " + count + " results");
                    System.out.println();
                }

            }

            if (input.equals("3")) {
                int count = 0;
                System.out.println("Searching by GPA = or >, please enter GPA:");
                String Input = scanner.nextLine();
                Float gpaInput = Float.parseFloat(Input);
                Statement stmt = conn.createStatement();
                System.out.println("Searching for GPA at " + Input + " or higher");
                System.out.println();

                ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
                while (rs.next()) {
                    int StudID = rs.getInt("id");

                    if (Get_GPA(StudID, conn) >= gpaInput) {
                        count++;
                        System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                        System.out.println("ID: " + rs.getString("id"));

                        System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                        System.out.println("Credits: " + Get_Credits(StudID, conn));
                        String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                    String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                    String Bar = "";
                    String Bar2 = "";
                    PreparedStatement MajStatement = conn.prepareStatement(Maj);
                    MajStatement.setInt(1, StudID);
                    ResultSet Q00 = MajStatement.executeQuery(); 
                    int MajCount = 0;

                    while (Q00.next()) {
                        if(MajCount > 0){
                        Bar = Bar + ", ";
                        }
                        String MM = Q00.getString("Majors.dname");
                        Bar = Bar + MM + "";
                        MajCount++;
                     }
                     PreparedStatement MinStatement = conn.prepareStatement(Min);
                     MinStatement.setInt(1, StudID);
                     ResultSet Q01 = MinStatement.executeQuery(); 
                     int MinCount = 0;
                     while (Q01.next()) {
                        if(MinCount >0){
                            Bar2 = Bar2 + ", ";
                        }
                     String mm = Q01.getString("Minors.dname");
                     Bar2 = Bar2+ mm + " ";
                     MinCount++;
                     }
                    if (MajCount == 0) {
                        System.out.println("Major(s): None");
                    }
                    else{
                    System.out.println("Major(s): " + Bar);
                    }
                    if (MinCount == 0){
                        System.out.println("Minor(s): None");
                    }
                    else{
                    System.out.println("Minor(s): " + Bar2);
                    }
                        

                    }
                }
                if (count == 0) {
                    System.out.println("No students found at this GPA or higher");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Returned " + count + " results");
                    System.out.println();
                }
            }

            if (input.equals("4")) {
                int count = 0;
                System.out.println("Searching by GPA = or <, please enter GPA:");
                String Input = scanner.nextLine();
                Float gpaInput = Float.parseFloat(Input);
                Statement stmt = conn.createStatement();
                System.out.println("Searching for GPA at " + Input + " or lower");
                System.out.println();

                ResultSet rs = stmt.executeQuery("SELECT * FROM Students");
                while (rs.next()) {
                    int StudID = rs.getInt("id");

                    if (Get_GPA(StudID, conn) <= gpaInput) {
                        count++;
                        System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name"));
                        System.out.println("ID: " + rs.getString("id"));

                        System.out.printf("GPA: %.2f \n", Get_GPA(StudID, conn));
                        System.out.println("Credits: " + Get_Credits(StudID, conn));
                        String Maj = "SELECT Majors.dname FROM Majors,Students where Students.id = Majors.sid AND Students.id = ?";
                    String Min = "SELECT Minors.dname FROM Minors,Students where Students.id = Minors.sid AND Students.id = ?";
                    String Bar = "";
                    String Bar2 = "";
                    PreparedStatement MajStatement = conn.prepareStatement(Maj);
                    MajStatement.setInt(1, StudID);
                    ResultSet Q00 = MajStatement.executeQuery(); 
                    int MajCount = 0;

                    while (Q00.next()) {
                        if(MajCount > 0){
                        Bar = Bar + ", ";
                        }
                        String MM = Q00.getString("Majors.dname");
                        Bar = Bar + MM + "";
                        MajCount++;
                     }
                     PreparedStatement MinStatement = conn.prepareStatement(Min);
                     MinStatement.setInt(1, StudID);
                     ResultSet Q01 = MinStatement.executeQuery(); 
                     int MinCount = 0;
                     while (Q01.next()) {
                        if(MinCount >0){
                            Bar2 = Bar2 + ", ";
                        }
                     String mm = Q01.getString("Minors.dname");
                     Bar2 = Bar2+ mm + " ";
                     MinCount++;
                     }
                    if (MajCount == 0) {
                        System.out.println("Major(s): None");
                    }
                    else{
                    System.out.println("Major(s): " + Bar);
                    }
                    if (MinCount == 0){
                        System.out.println("Minor(s): None");
                    }
                    else{
                    System.out.println("Minor(s): " + Bar2);
                    }

                        
                        System.out.println();
                    }
                }
                if (count == 0) {
                    System.out.println("No students found at this GPA or lower");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Returned " + count + " results");
                    System.out.println();
                }
            }

            if (input.equals("5")) {
                System.out.println("Searching by Department, please enter department name: ");
                int Majors = 0;
                int Minors = 0;
                String Input = scanner.nextLine();

                String sql = "SELECT COUNT(*) FROM Majors, Departments WHERE Majors.dname = Departments.name AND Departments.name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, Input);

                ResultSet Majoring = pstmt.executeQuery();
                while (Majoring.next()) {
                    Majors = Majoring.getInt(1);
                }

                String sql2 = "SELECT COUNT(*) FROM Minors, Departments WHERE Minors.dname = Departments.name AND Departments.name = ?";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, Input);
                ResultSet Minoring = pstmt2.executeQuery();
                while (Minoring.next()) {
                    Minors = Minoring.getInt(1);
                }

                int Totalling = Majors + Minors;
                if (Totalling == 0) {
                    System.out.println("No Students In Department");
                } else {
                    System.out.println("Total students enrolled: " + Totalling);
                }

                String averageGPAs = "SELECT sid FROM Majors WHERE dname = ? UNION SELECT sid FROM Minors WHERE dname = ?;";

                PreparedStatement AddDepartment = conn.prepareStatement(averageGPAs);
                AddDepartment.setString(1, Input);
                AddDepartment.setString(2, Input);

                ResultSet rs = AddDepartment.executeQuery();
                double rollingsum = 0;
                while (rs.next()) {
                    String getsids = rs.getString("sid");
                    int nowint = Integer.parseInt(getsids);
                    Double nowGPA = Get_GPA(nowint, conn);
                    rollingsum += nowGPA;

                }
                Double averageGPA = rollingsum / Totalling;
                System.out.printf("Average GPA: %.2f \n", averageGPA);

            }

            if (input.equals("6")) {
                System.out.println("Searching by Class, please enter class name: ");

                String Input = scanner.nextLine();

                String InClass = "SELECT COUNT(IsTaking.sid) FROM Classes, IsTaking WHERE IsTaking.name = Classes.name AND Classes.name = ?";
                PreparedStatement holy = conn.prepareStatement(InClass);
                holy.setString(1, Input);
                ResultSet StudentsTaking = holy.executeQuery();
                if (StudentsTaking.next()) {
                    int AmountStudents = StudentsTaking.getInt(1);
                    if (AmountStudents == 1) {
                        System.out.println("There is " + AmountStudents + " student currently enrolled");
                    } else {
                        System.out.println("There are " + AmountStudents + " students currently enrolled");
                    }

                    String sql = "Select grade FROM HasTaken, Classes WHERE Classes.name = HasTaken.name AND Classes.name = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, Input);
                    ResultSet rs = pstmt.executeQuery();
                    int AValue = 0;
                    int BValue = 0;
                    int CValue = 0;
                    int DValue = 0;
                    int FValue = 0;
                    while (rs.next()) {
                        String grade = rs.getString("grade");
                        switch (grade) {
                            case "A":
                                AValue++;
                                break;
                            case "B":
                                BValue++;
                                break;
                            case "C":
                                CValue++;
                                break;
                            case "D":
                                DValue++;
                                break;
                            case "F":
                                FValue++;
                                break;
                        }
                    }
                    int TotalValue = AValue + BValue + CValue + DValue + FValue;
                    if (TotalValue == 0) {
                        System.out.println("No Previous Enrollees");
                    } else {
                        System.out.println("Grades of Previous Enrollees:");
                        System.out.println("A: " + AValue);
                        System.out.println("B: " + BValue);
                        System.out.println("C: " + CValue);
                        System.out.println("D: " + DValue);
                        System.out.println("F: " + FValue);
                    }

                }
            }

            if (input.equals("7")) {
                System.out.println("Abitrary SQL Query, please enter query: ");
                String Input = scanner.nextLine();
                PreparedStatement pstmt = conn.prepareStatement(Input);
                ResultSet rs = pstmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int numColumns = rsmd.getColumnCount();

                // print column names
                for (int i = 1; i <= numColumns; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
                System.out.println();

                // print rows
                while (rs.next()) {
                    for (int i = 1; i <= numColumns; i++) {
                        System.out.print(rs.getString(i) + "\t");
                    }
                    System.out.println("");
                }
            }

            // QUERY 8: EXIT
            if (input.equals("8")) {
                break;
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
    }
}
// The end!
