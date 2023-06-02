
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author dflam
 */
class CourseQueries {

    private static Connection connection;
    private static PreparedStatement addCourseEntry;
    private static PreparedStatement query;
    private static PreparedStatement getCoursesCodeList;
    private static PreparedStatement getCoursesDescriptionList;
    private static PreparedStatement getCoursesSeatsList;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;

    public static void addCourse(CourseEntry entry) {
        connection = DBConnection.getConnection();
        try {
            addCourseEntry = connection.prepareStatement("insert into app.course(semester, coursecode, coursedescription, courseseats) values (?, ?, ?, ?)");
            addCourseEntry.setString(1, entry.getSemester());
            addCourseEntry.setString(2, entry.getCourseCode());
            addCourseEntry.setString(3, entry.getCourseDescription());
            addCourseEntry.setInt(4, entry.getSeats());
            addCourseEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> coursesEntryList = new ArrayList<>();
        try {
            query = connection.prepareStatement("select * from app.course where semester = ?");
            query.setString(1, semester);
            resultSet = query.executeQuery();

            while (resultSet.next()) {
                CourseEntry entry = new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                coursesEntryList.add(entry);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return coursesEntryList;
    }

    public static ArrayList<String> getCoursesCodeList(String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<String> coursesCode = new ArrayList<String>();
        try {
            getCoursesCodeList = connection.prepareStatement("select coursecode from app.course where semester = ? order by coursecode");
            getCoursesCodeList.setString(1, courseCode);
            resultSet = getCoursesCodeList.executeQuery();

            while (resultSet.next()) {
                coursesCode.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return coursesCode;
    }

    public static ArrayList<String> getCoursesDescriptionList() {
        connection = DBConnection.getConnection();
        ArrayList<String> coursesDescription = new ArrayList<String>();
        try {
            getCoursesDescriptionList = connection.prepareStatement("select * from app.course order by coursedescription");
            resultSet = getCoursesDescriptionList.executeQuery();

            while (resultSet.next()) {
                coursesDescription.add(resultSet.getString(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return coursesDescription;
    }

    public static Integer getCoursesSeatsList(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<String> coursesSeats = new ArrayList<String>();
        try {
            getCoursesSeatsList = connection.prepareStatement("select courseseats from app.course where semester = ? and coursecode = ?");
            getCoursesSeatsList.setString(1, semester);
            getCoursesSeatsList.setString(2, courseCode);
            resultSet = getCoursesSeatsList.executeQuery();
            Integer courseSeats = 0;
            while (resultSet.next()) {
                courseSeats = resultSet.getInt(1);
            }
            return courseSeats;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }
    
    public static void dropCourse(String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from app.course where COURSECODE = ?");
            dropCourse.setString(1, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
