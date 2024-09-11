package com.example.assignment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
public class StudentsDatabase implements StudentsDatabaseInterface, TableInterface {
    String url;
    String username;
    String password;
    Connection connection;
    Map <Character, Integer> grades = new HashMap<Character, Integer>();

    StudentsDatabase (String url, String username, String password) throws SQLException
    {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = getConnection(url, username, password);
    }

    public Connection getConnection (String url, String username, String password)
    {
        Connection connection = null;

        try
        {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("You are successfully connected!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());

        }
        return connection;
    }

    class Schedule {
        String sqlTable;
        String populateTable;

        Schedule(String sqlTable, String populateTable) {
            this.sqlTable = sqlTable;
            try {
                connection.prepareStatement(sqlTable).executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            this.populateTable = populateTable;
            try {
                connection.prepareStatement(populateTable).executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public void insertSchedule(String courseID, String sectionNumber, String title, String year, String semester, String instructor, String department, String program) {
            String sqlInsert = StudentsDatabaseInterface.insertTableSchedule(courseID, sectionNumber, title, year, semester, instructor, department, program);
            try {
                connection.prepareStatement(sqlInsert).executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public void updateScheduleInstructor(String instructor, String courseID)
        {
            String sqlInsert = StudentsDatabaseInterface.UpdateTableScheduleInstructor(instructor, courseID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void deleteSchedule(String courseID)
        {
            String sqlInsert = StudentsDatabaseInterface.DeleteTableSchedule(courseID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    class Courses
    {
        String sqlTable;
        String populateTable;
        Courses(String sqlTable, String populateTable)
        {
            this.sqlTable = sqlTable;
            try
            {
                connection.prepareStatement(sqlTable).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
            this.populateTable = populateTable;
            try
            {
                connection.prepareStatement(populateTable).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void insertCourses (String courseID, String title, String department)
        {
            String sqlInsert = StudentsDatabaseInterface.insertTableCourses(courseID, title, department);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void updateCourses (String column, String input, String courseID)
        {
            String sqlInsert = StudentsDatabaseInterface.updateTableCourses(column, input, courseID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void deleteCourse(String courseID)
        {
            String sqlInsert = StudentsDatabaseInterface.deleteTableCourses(courseID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    class Students
    {
        String sqlTable;
        Students (String sqlTable)
        {
            this.sqlTable = sqlTable;
            try
            {
                connection.prepareStatement(sqlTable).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void insertStudents (String empID, String firstName, String lastName, String email, String gender)
        {
            String sqlInsert = StudentsDatabaseInterface.insertTableStudents(empID, firstName, lastName, email, gender);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void updateStudents (String column, String input, String empID)
        {
            String sqlInsert = StudentsDatabaseInterface.updateTableStudents(column, input, empID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void deleteStudents (String empID)
        {
            String sqlInsert = StudentsDatabaseInterface.deleteTableStudents(empID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    class Classes
    {
        String sqlTable;
        Classes (String sqlTable)
        {
            this.sqlTable = sqlTable;
            try
            {
                connection.prepareStatement(sqlTable).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void insertClasses (String courseID, String studentID, String sectionNo, String year, String semester, String grade)
        {
            String sqlInsert = StudentsDatabaseInterface.insertTableClasses(courseID, studentID, sectionNo, year, semester, grade);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void updateClasses (String column, String input, String studentID)
        {
            String sqlInsert = StudentsDatabaseInterface.updateTableCourses(column, input, studentID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public void deleteClasses (String studentID)
        {
            String sqlInsert = StudentsDatabaseInterface.deleteTableClasses(studentID);
            try
            {
                connection.prepareStatement(sqlInsert).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    class AggregateGrades
    {
        String sqlTable;
        String populateTable;
        AggregateGrades(String sqlTable, String populateTable)
        {
            this.sqlTable = sqlTable;
            try
            {
                connection.prepareStatement(sqlTable).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }

            this.populateTable = populateTable;
            try
            {
                connection.prepareStatement(populateTable).executeUpdate();
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
        }

        public Map <Character, Integer> getAggregateGrades (String sqlQuery)
        {
            Map <Character, Integer> mapAggregateGrades = new HashMap<Character, Integer>();
            String sqlQueryAggregateGrades = sqlQuery;
            try
            {
                ResultSet RS = connection.prepareStatement (sqlQueryAggregateGrades).executeQuery();
                while (RS.next())
                {
                    mapAggregateGrades.put(RS.getString("Grade").charAt(0), RS.getInt("numberStudents"));
                }
            }
            catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }
            return mapAggregateGrades;
        }
    }
}
