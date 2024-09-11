package com.example.assignment1;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class StudentsDatabaseApplication extends Application {
        @Override
        public void start(Stage stage) {
                stage.setTitle("Assignment 4");
                double width = 900;
                double height = 600;

                double radius = 300;

                Group root = new Group();
                Canvas canvas = new Canvas(width, height);
                GraphicsContext GC = canvas.getGraphicsContext2D();
                root.getChildren().add(canvas);
                stage.setScene(new Scene(root));
                stage.show();

                MyPoint center = new MyPoint(width / 2, height / 2, MyColor.BLACK);

                String server = "jdbc:mysql://localhost:3306/assignment4";
                String username = "root";
                String password = "password";

                try {
                        StudentsDatabase DataBase = new StudentsDatabase(server, username, password);
                        String TextFile = "C:/Users/zixua/OneDrive/Desktop/ScheduleFall2023.txt";

                        String sqlTable = StudentsDatabaseInterface.TableSchedule;
                        String populateTable = StudentsDatabaseInterface.FillTableSchedule(TextFile);
                        StudentsDatabase.Schedule schedule = DataBase.new Schedule(sqlTable, populateTable);

                        sqlTable = StudentsDatabaseInterface.TableCourses;
                        populateTable = StudentsDatabaseInterface.FillTableCourses();
                        StudentsDatabase.Courses courses = DataBase.new Courses(sqlTable, populateTable);

                        sqlTable = StudentsDatabaseInterface.TableStudents;
                        StudentsDatabase.Students students = DataBase.new Students(sqlTable);

                        students.insertStudents("378965124", "Emma", "Lee", "emma.lee3789@gmail.com", "F");
                        students.insertStudents("576814329", "Aiden", "Garcia", "aiden.garcia5768@gmail.com", "M");
                        students.insertStudents("912473086", "Chloe", "Harris", "chloe.harris9124@gmail.com", "F");
                        students.insertStudents("235609187", "Landon", "Wang", "landon.wang2356@gmail.com", "M");
                        students.insertStudents("789201536", "Avery", "Gonzalez", "avery.gonzalez7892@gmail.com", "F");
                        students.insertStudents("401285937", "Noah", "Rodriguez", "noah.rodriguez4012@gmail.com", "M");
                        students.insertStudents("857392614", "Ella", "Scott", "ella.scott8573@gmail.com", "F");
                        students.insertStudents("142859736", "Cameron", "Nguyen", "cameron.nguyen1428@gmail.com", "M");
                        students.insertStudents("936254710", "Lila", "Rivera", "lila.rivera9362@gmail.com", "F");
                        students.insertStudents("510276349", "Logan", "Hernandez", "logan.hernandez5102@gmail.com", "M");
                        students.insertStudents("267319048", "Mia", "Gomez", "mia.gomez2673@gmail.com", "F");
                        students.insertStudents("803562149", "Ethan", "Cruz", "ethan.cruz8035@gmail.com", "M");
                        students.insertStudents("459827613", "Aria", "Perez", "aria.perez4598@gmail.com", "F");
                        students.insertStudents("641275098", "Daniel", "Campbell", "daniel.campbell6412@gmail.com", "M");
                        students.insertStudents("298710345", "Sophia", "Reed", "sophia.reed2987@gmail.com", "F");
                        students.insertStudents("736429081", "Adam", "Allen", "adam.allen7364@gmail.com", "M");
                        students.insertStudents("184753629", "Arianna", "Davis", "arianna.davis1847@gmail.com", "F");
                        students.insertStudents("512649307", "Jackson", "Parker", "jackson.parker5126@gmail.com", "M");
                        students.insertStudents("897013246", "Mila", "Evans", "mila.evans8970@gmail.com", "F");
                        students.insertStudents("325978164", "William", "Moore", "william.moore3259@gmail.com", "M");
                        students.insertStudents("670413258", "Lily", "Taylor", "lily.taylor6704@gmail.com", "F");
                        students.insertStudents("983142657", "Owen", "Green", "owen.green9831@gmail.com", "M");
                        students.insertStudents("176429835", "Hailey", "Baker", "hailey.baker1764@gmail.com", "F");
                        students.insertStudents("542098361", "Caleb", "Gutierrez", "caleb.gutierrez5420@gmail.com", "M");
                        students.insertStudents("319087542", "Aubrey", "Ramirez", "aubrey.ramirez3190@gmail.com", "F");

                        sqlTable = StudentsDatabaseInterface.TableClasses;
                        StudentsDatabase.Classes classes = DataBase.new Classes(sqlTable);

                        classes.insertClasses("10000 TU", "378965124", "11830", "2021", "Spring", "A");
                        classes.insertClasses("10200 MM1", "576814329", "13859", "2021", "Spring", "A");
                        classes.insertClasses("10200 MM2", "912473086", "13860", "2021", "Spring", "A");
                        classes.insertClasses("10200 MM3", "235609187", "13861", "2021", "Spring", "A");
                        classes.insertClasses("10200 MM4", "789201536", "14519", "2021", "Spring", "B");
                        classes.insertClasses("10300 CC1", "401285937", "11833", "2021", "Spring", "D");
                        classes.insertClasses("10300 CC2", "857392614", "11834", "2021", "Spring", "D");
                        classes.insertClasses("10300 MM1", "142859736", "11831", "2021", "Spring", "D");
                        classes.insertClasses("10300 MM2", "936254710", "11832", "2021", "Spring", "F");
                        classes.insertClasses("10400 EF1", "510276349", "11836", "2021", "Spring", "C");
                        classes.insertClasses("10400 EF2", "267319048", "11837", "2021", "Spring", "D");
                        classes.insertClasses("10400 PR1", "803562149", "11838", "2021", "Spring", "F");
                        classes.insertClasses("10400 PR2", "459827613", "11839", "2021", "Spring", "F");
                        classes.insertClasses("10000 TU", "641275098", "11830", "2021", "Spring", "A");
                        classes.insertClasses("10200 MM1", "298710345", "13859", "2021", "Spring", "C");
                        classes.insertClasses("10200 MM2", "736429081", "13860", "2021", "Spring", "B");
                        classes.insertClasses("10200 MM3", "184753629", "13861", "2021", "Spring", "D");
                        classes.insertClasses("10200 MM4", "512649307", "14519", "2021", "Spring", "B");
                        classes.insertClasses("10300 CC1", "897013246", "11833", "2021", "Spring", "A");
                        classes.insertClasses("10300 CC2", "325978164", "11834", "2021", "Spring", "B");
                        classes.insertClasses("10300 MM1", "670413258", "11831", "2021", "Spring", "D");
                        classes.insertClasses("10300 MM2", "983142657", "11832", "2021", "Spring", "D");
                        classes.insertClasses("10400 EF1", "176429835", "11836", "2021", "Spring", "F");
                        classes.insertClasses("10400 EF2", "542098361", "11837", "2021", "Spring", "D");
                        classes.insertClasses("10400 PR1", "319087542", "11838", "2021", "Spring", "F");

                        schedule.updateScheduleInstructor("Zi Xuan Li","10000 TU");

                        sqlTable = StudentsDatabaseInterface.TableAggregateGrades;
                        populateTable = StudentsDatabaseInterface.FillTableAggregateGrades();
                        StudentsDatabase.AggregateGrades aggregateGrades = DataBase.new AggregateGrades(sqlTable, populateTable);

                        String sqlQuery = "SELECT * FROM AggregateGrades";
                        HistogramAlphabet histogram = new HistogramAlphabet(aggregateGrades.getAggregateGrades(sqlQuery));
                        HistogramAlphabet.MyPieChart pieChart = histogram.new MyPieChart(6, 6, center, width / 2, height / 2, 360);
                        pieChart.draw(GC);

                        // Add legend
                        double legendWidth = 150;
                        double legendHeight = 200;
                        double legendX = width - legendWidth - 20;
                        double legendY = 20;

                        Canvas legendCanvas = addCanvasLegend(legendWidth, legendHeight, histogram);
                        legendCanvas.setLayoutX(legendX);
                        legendCanvas.setLayoutY(legendY);
                        root.getChildren().add(legendCanvas);

                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
        }

        public Canvas addCanvasLegend(double widthCanvas, double heightCanvas, HistogramAlphabet H) {
                String information;
                Canvas cv = new Canvas(widthCanvas, heightCanvas);
                GraphicsContext gc = cv.getGraphicsContext2D();
                MyColor colorLeftCanvas = MyColor.LINEN;
                gc.setFill(colorLeftCanvas.getJavaFXColor());
                gc.fillRect(0.0, 0.0, widthCanvas, heightCanvas);

                double xText = 20;
                double yText = 0.03625 * heightCanvas;
                MyColor colorStroke = MyColor.GRAY;
                gc.setStroke(colorStroke.invertColor());
                gc.setFont(Font.font("Calibre", 13));

                yText += 20;

                Map<Character, Integer> sortedFrequency = H.sortDownFrequency();

                double yStep = 20;
                for (Character K : sortedFrequency.keySet()) {
                        yText += yStep;
                        information = K + ": " + sortedFrequency.get(K) + " students";
                        gc.strokeText(information, xText, yText);
                }

                return cv;
        }

        public static void main(String[] args) {
                launch();
        }
}
