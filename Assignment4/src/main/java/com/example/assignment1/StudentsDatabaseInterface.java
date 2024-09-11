package com.example.assignment1;

public interface StudentsDatabaseInterface {

    String TableSchedule = "CREATE TABLE Schedule(\n"
            + "courseID CHAR(12) NOT NULL UNIQUE, \n"
            + "sectionNumber VARCHAR(8) NOT NULL UNIQUE, \n"
            + "title VARCHAR(64), \n"
            + "department CHAR(16), \n"
            + "program VARCHAR(48), \n"
            + "year INT, \n"
            + "semester CHAR(6), \n"
            + "instructor VARCHAR(24), \n"
            + "PRIMARY KEY(courseID, sectionNumber))";

    String TableStudents = "Create TABLE Students(\n"
            + "empID INT, \n"
            + "firstName VARCHAR(32) NOT NULL, \n"
            + "lastName VARCHAR(32) NOT NULL, \n"
            + "email VARCHAR(50), \n"
            + "gender CHAR(1), \n"
            + "PRIMARY KEY(empID))";

    String TableCourses = "CREATE TABLE Courses(\n"
            + "courseID CHAR(12) NOT NULL UNIQUE, \n"
            + "courseTitle VARCHAR(64), \n"
            + "department CHAR(16), \n"
            + "PRIMARY KEY(courseID))";

    String TableClasses = "CREATE TABLE Classes(\n"
            + "courseID CHAR(12), \n"
            + "studentID INT, \n"
            + "sectionNo VARCHAR(8), \n"
            + "year INT, \n"
            + "semester CHAR(6), \n"
            + "grade CHAR CHECK (grade = 'A' OR grade = 'B' OR grade = 'C' OR grade = 'D' OR grade = 'F' OR grade = 'W'), \n"
            + "PRIMARY KEY(courseID, studentID, sectionNo))";

    String TableAggregateGrades = "CREATE TABLE AggregateGrades(\n"
            + "grade CHAR PRIMARY KEY, \n"
            + "numberStudents INT)";

    public static String FillTableSchedule(String filename) {
        return "LOAD DATA INFILE '" + filename + "'\n"
                + "INTO TABLE Schedule \n"
                + "FIELDS TERMINATED BY '\t'\n"
                + "LINES TERMINATED BY '\n'\n"
                + "IGNORE 1 LINES;";
    }

    public static String insertTableSchedule(String courseID, String sectionNumber, String title, String year, String semester, String instructor, String department, String program)
    {
        return "INSERT INTO Schedule (courseId, sectionNumber, title, department, program, year, semester, instructor)\n"
                + "VALUES (" + "\'" + courseID + "\', "
                + "\'" + sectionNumber + "\', "
                + "\'" + title + "\', "
                + "\'" + department + "\', "
                + "\'" + program + "\', "
                + "\'" + year + "\', "
                + "\'" + semester + "\', "
                + "\'" + instructor + "\'" + ")";
    }

    public static String UpdateTableScheduleInstructor(String instructor, String courseID)
    {
        return "UPDATE Schedule\n" + "SET instructor = "
                + "\'" + instructor + "\'" + "\n"
                + "WHERE courseID = " + "\'" + courseID + "\'";
    }

    public static String DeleteTableSchedule(String courseID)
    {
        return "DELETE Schedule\n" + "WHERE courseID = "
                + "\'" + courseID + "\'";
    }
    public static String FillTableCourses()
    {
        return "INSERT INTO Courses\n"
                + "SELECT courseID, title, department\n" + "FROM Schedule";
    }
    public static String insertTableCourses(String courseID, String title, String department)
    {
        return "INSERT INTO Courses (courseID, courseTitle, department)\n" + "VALUES (" + "\'" + courseID + "\', "
                + "\'" + title + "\', "
                + "\'" + department + "\'" + ")";
    }
    public static String updateTableCourses(String column, String input, String courseID)
    {
        return "UPDATE COURSES\n" + "SET" + column + " = " + "\'"
                + input + "\'" + "\n"
                + "WHERE courseID = " + "\'" + courseID
                + "\'";
    }
    public static String deleteTableCourses(String courseID)
    {
        return "DELETE Courses\n" + "WHERE courseID = "
                + "\'" + courseID + "\'";
    }
    public static String insertTableStudents(String empID, String firstName, String lastName, String email, String gender)
    {
        return "INSERT INTO Students (empID, firstName, lastName, email, gender)\n"
                + "VALUES (" + "\'" + empID + "\', "
                + "\'" + firstName + "\', "
                + "\'" + lastName + "\', "
                + "\'" + email + "\', "
                + "\'" + gender + "\'" + ")";
    }
    public static String updateTableStudents(String column, String input, String empID)
    {
        return "UPDATE Students\n" + "SET\n" + column + " = " + "\'"
                + input + "\'" + "\n"
                + "WHERE empID = " + "\'" + empID
                + "\'";
    }
    public static String deleteTableStudents(String empID)
    {
        return "DELETE Students\n" + "WHERE empID = "
                + "\'" + empID + "\'";
    }
    public static String insertTableClasses(String courseID, String studentID, String sectionNo, String year, String semester, String grade)
    {
        return "INSERT INTO Classes (courseID, studentID, sectionNo, year, semester, grade)\n"
                + "VALUES (" + "\'" + courseID + "\', "
                + "\'" + studentID + "\', "
                + "\'" + sectionNo + "\', "
                + "\'" + year + "\', "
                + "\'" + semester + "\', "
                + "\'" + grade + "\'" + ")";
    }
    public static String updateTableClasses(String column, String input, String studentID)
    {
        return "UPDATE Classes\n" + "SET " + column + " = " + "\'" + input + "\'" + "\n"
                + "WHERE studentID = " + "\'" + studentID
                + "\'";
    }

    public static String deleteTableClasses(String studentID)
    {
        return "DELETE Students\n" + "WHERE studentID = "
                + "\'" + studentID + "\'";
    }
    public static String FillTableAggregateGrades()
    {         return "INSERT INTO AggregateGrades\n"
            + "Select grade, count(grade)\n"
            + "FROM Classes\n"
            + "GROUP BY grade";
    }
}
