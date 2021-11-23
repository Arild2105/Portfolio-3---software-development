import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.SQLException;

public class Main extends Application {

    public void start(Stage primaryStage){
        String url = "jdbc:sqlite:/Users/louis/IdeaProjects/Software Development/StudentGrade/StudentGrades.db";
        StudentView view = new StudentView();
        StudentModel model = new StudentModel(url);
        StudentController control = null;

        try{
            control = new StudentController(view, model);

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        primaryStage.setTitle("Courses and Grades");
        primaryStage.setScene(new Scene(view.asParent(), 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
	launch(args);
    }
}
