import java.sql.*;
import java.util.ArrayList;

public class StudentModel {

    Connection conn = null;
    String url = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    StudentModel(String url){
        this.url=url;
    }

    public void connectToStudentData() throws SQLException{
        conn = DriverManager.getConnection(url);
    }

    public void closeStudentConnection() throws SQLException{
        if(conn != null)
            conn.close();
    }

    public void CreateStatement() throws SQLException{
        this.stmt = conn.createStatement();
    }

    public ArrayList<String> SQLQueryCourseNames() throws SQLException{
        ArrayList<String> courseNames = new ArrayList<>();
        String sql = "Select distinct courseName from courses;";
        rs = stmt.executeQuery(sql);

        while(rs != null && rs.next()){
            String courseName = rs.getString(1);
            courseNames.add(courseName);
        }

        return courseNames;
    }

    public ArrayList<Integer> SQLQueryCourseYear() throws SQLException{
        ArrayList<Integer> courseYears = new ArrayList<>();
        String sql = "Select distinct courseYear from courses;";
        rs = stmt.executeQuery(sql);

        while(rs != null && rs.next()){
            Integer courseyear = rs.getInt(1);
            courseYears.add(courseyear);
        }

        return courseYears;
    }

    public ArrayList<String> SQLQueryStudentNames() throws SQLException{
        ArrayList<String> studentNames = new ArrayList<>();
        String sql = "Select firstName from User where userType = 'Student';";
        rs = stmt.executeQuery(sql);

        while(rs != null && rs.next()) {
            String studentName = rs.getString(1);
            studentNames.add(studentName);
        }

        return studentNames;
    }

    public ArrayList<StudentCourseInfo> QueryStudentInfo(String name) throws SQLException{
        ArrayList<StudentCourseInfo> studentInfos = new ArrayList<>();
        String sql = "Select U.userID, U.firstName, U.lastName, c.courseName, c.courseYear, cR.grade, cR.studentCourseID" +
                " from User as U Join courseRegistration cR on U.userID = cR.studentID join courses c on cR.studentCourseID = c.courseID" +
                " where U.userType = 'Student' and U.firstName = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()){
            Integer userID = rs.getInt(1);
            String First = rs.getString(2);
            String LastName = rs.getString(3);
            String course = rs.getString(4);
            Integer year = rs.getInt(5);
            Integer Grade = rs.getInt(6);
            StudentCourseInfo Info = new StudentCourseInfo(userID, First, LastName, course, year, Grade);
            studentInfos.add(Info);
        }

        return studentInfos;
    }

    public float QueryStudentAVG(String name) throws SQLException{
        float AVG = -1;
        String sql = "SELECT AVG(cR.Grade)" +
                " from User as U join courseRegistration as cR on U.userID = cR.studentID join courses as c on cR.studentCourseID = c.courseID " +
                " where U.userType = 'Student' and U.firstName = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()){
            AVG = rs.getFloat(1);
        }

        return AVG;
    }

    public ArrayList<StudentCourseInfo> QueryCourseInfo(String name, Integer year) throws SQLException{
        ArrayList<StudentCourseInfo> courseInfos = new ArrayList<>();

        String sql = "SELECT U.userID, U.firstName, U.lastName, c.courseName, c.courseYear, cR.grade, cR.studentCourseID" +
                " from User as U join courseRegistration as cR on U.userID = cR.studentID join courses as c on cR.studentCourseID = c.courseID " +
                " where U.userType = 'Student' and c.courseName = ? and c.courseYear = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setInt(2,year);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()){
            Integer userID = rs.getInt(1);
            String First = rs.getString(2);
            String Last = rs.getString(3);
            String CourseName = rs.getString(4);
            Integer CourseYear = rs.getInt(5);
            Integer Grade = rs.getInt(6);
            StudentCourseInfo Info = new StudentCourseInfo(userID, First, Last, CourseName, CourseYear, Grade);
            courseInfos.add(Info);
        }

        return courseInfos;
    }
    public float QueryCourseAVG(String name, Integer year) throws SQLException{
        float AVG = -1;
        String sql = "SELECT AVG(cR.Grade)" +
                " from User as U join courseRegistration as cR on U.userID = cR.studentID join courses as c on cR.studentCourseID = c.courseID " +
                " where U.userType = 'Student' and c.courseName = ? and c.courseYear = ?;";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setInt(2,year);
        rs = pstmt.executeQuery();

        while (rs != null && rs.next()){
            AVG = rs.getFloat(1);
        }

        return AVG;
    }
}

class StudentCourseInfo {
    Integer userID = null;
    String Firstname = null;
    String Lastname = null;
    String Coursename = null;
    Integer Courseyear = null;
    Integer Grade = null;
    StudentCourseInfo(Integer userID, String First, String Last, String CourseName, Integer CourseYear, Integer grade){
        this.userID = userID;
        this.Firstname = First;
        this.Lastname = Last;
        this.Coursename = CourseName;
        this.Courseyear = CourseYear;
        this.Grade = grade;
    }
}

