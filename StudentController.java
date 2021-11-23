import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentController {
    StudentView view;
    StudentModel model;

    public StudentController(StudentView v, StudentModel m) throws SQLException{
        this.view = v;
        this.model = m;
        this.model.connectToStudentData();
        this.model.CreateStatement();
        this.view.CourseName = getCourseNames();
        this.view.CourseYear = getCourseYear();
        this.view.Students = getStudentNames();
        this.view.FindCourseInfo.setOnAction(e->PrintCourseInfo(view.theCourseNameCombo.getValue(), view.theCourseYearCombo.getValue(), view.text));
        this.view.FindStudentInfo.setOnAction(e->PrintStudentInfo(view.theStudentCombo.getValue(), view.text));

        view.configure();
    }

    public ObservableList<String> getCourseNames() throws SQLException {
        ArrayList<String> courseNames= model.SQLQueryCourseNames();
        ObservableList<String> CourseName= FXCollections.observableArrayList(courseNames);
        return CourseName;
    }

    public ObservableList<Integer> getCourseYear() throws SQLException {
        ArrayList<Integer> courseYears= model.SQLQueryCourseYear();
        ObservableList<Integer> CourseYear= FXCollections.observableArrayList(courseYears);
        return CourseYear;
    }

    public ObservableList<String> getStudentNames() throws SQLException {
        ArrayList<String> studentNames= model.SQLQueryStudentNames();
        ObservableList<String> Students= FXCollections.observableArrayList(studentNames);
        return Students;
    }

    public void PrintCourseInfo(String Name, Integer Year, TextArea textArea){
        textArea.clear();
        textArea.appendText("Students on this course: \n");

        try{
           ArrayList<StudentCourseInfo> info = model.QueryCourseInfo(Name, Year);

           for (int i = 0; i < info.size(); i++){
               String firstName = info.get(i).Firstname;
               String lastName = info.get(i).Lastname;
               String coursename = info.get(i).Coursename;
               Integer courseyear = info.get(i).Courseyear;
               Integer grade = info.get(i).Grade;
               textArea.appendText(firstName + " " + lastName + " got grade " + grade + " in course: " + coursename + " " + courseyear + "\n");
           }

           float AVG = model.QueryCourseAVG(Name, Year);
           textArea.appendText("In course " + Name + " " + Year + " the grade average is: " + AVG);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void PrintStudentInfo(String name, TextArea textArea){
        textArea.clear();
        textArea.appendText("The Student: \n");

        try{
            ArrayList<StudentCourseInfo> info = model.QueryStudentInfo(name);

            for (int i = 0; i < info.size(); i++){
                Integer id = info.get(i).userID;
                String firstName = info.get(i).Firstname;
                String lastName = info.get(i).Lastname;
                String coursename = info.get(i).Coursename;
                Integer courseyear = info.get(i).Courseyear;
                Integer grade = info.get(i).Grade;
                textArea.appendText("Student ID: " + id + " " + firstName + " " + lastName + " got grade " + grade + " in course: " + coursename + " " + courseyear + "\n");
            }

            float AVG = model.QueryStudentAVG(name);
            textArea.appendText("The grade average for " + name + " is: " + AVG);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
