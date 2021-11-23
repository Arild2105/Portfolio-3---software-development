import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

public class StudentView {

    GridPane startview;
    Label Student;
    Label Course;
    ComboBox<String> theCourseNameCombo;
    ComboBox<Integer> theCourseYearCombo;
    ComboBox<String> theStudentCombo;
    Button FindCourseInfo;
    Button FindStudentInfo;
    TextArea text;

    ObservableList<String> CourseName;
    ObservableList<Integer> CourseYear;
    ObservableList<String> Students;

    public StudentView(){
        startview = new GridPane();
        CreateView();
    }

    public void CreateView(){
        startview.setMinSize(500,500);
        startview.setPadding(new Insets(10,10,10,10));
        startview.setHgap(10);
        startview.setVgap(10);

        Student = new Label("Select student");
        startview.add(Student,10,5);
        Course = new Label("Select Course");
        startview.add(Course, 5,5);

        theCourseNameCombo = new ComboBox<>();
        startview.add(theCourseNameCombo,5,8);
        theCourseYearCombo = new ComboBox<Integer>();
        startview.add(theCourseYearCombo, 6,8);
        theStudentCombo = new ComboBox<>();
        startview.add(theStudentCombo, 10, 8);

        FindCourseInfo = new Button("Find info on course");
        startview.add(FindCourseInfo, 5,13);
        FindStudentInfo = new Button("Find info on Student");
        startview.add(FindStudentInfo,10,13);

        text = new TextArea();
        startview.add(text,1,20,30,10);
    }

    public void configure(){
        theCourseNameCombo.setItems(CourseName);
        theCourseNameCombo.getSelectionModel().selectFirst();

        theCourseYearCombo.setItems(CourseYear);
        theCourseYearCombo.getSelectionModel().selectFirst();

        theStudentCombo.setItems(Students);
        theStudentCombo.getSelectionModel().selectFirst();
    }

    public Parent asParent(){
        return  startview;
    }
}
