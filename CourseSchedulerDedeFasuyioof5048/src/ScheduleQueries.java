
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
public class ScheduleQueries {

    private static Connection connection;
    private static PreparedStatement query;
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduledStudentCourse;
    private static PreparedStatement getWaitlistedStudentCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateSchedule;
    private static PreparedStatement dropScheduleByCourses;
    private static ResultSet resultSet;

    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntryList = new ArrayList<ScheduleEntry>();
        try {
            PreparedStatement query = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentID = ?");
            query.setString(1, semester);
            query.setString(2, studentID);
            resultSet = query.executeQuery();

            while (resultSet.next()) {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                scheduleEntryList.add(entry);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scheduleEntryList;
    }

    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int studentCount = 0;
        try {
            query = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            query.setString(1, currentSemester);
            query.setString(2, courseCode);
            resultSet = query.executeQuery();

            while (resultSet.next()) {
                studentCount = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return studentCount;
    }

    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduledStudent = new ArrayList<ScheduleEntry>();
        try {
            getScheduledStudentCourse = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = 'S'");
            getScheduledStudentCourse.setString(1, semester);
            getScheduledStudentCourse.setString(2, courseCode);
            resultSet = getScheduledStudentCourse.executeQuery();
            while (resultSet.next()) {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                scheduledStudent.add(entry);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return scheduledStudent;
    }

    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudent = new ArrayList<ScheduleEntry>();
        try {
            getWaitlistedStudentCourse = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = 'W'");
            getWaitlistedStudentCourse.setString(1, semester);
            getWaitlistedStudentCourse.setString(2, courseCode);
            resultSet = getWaitlistedStudentCourse.executeQuery();
            while (resultSet.next()) {
                ScheduleEntry entry = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                waitlistedStudent.add(entry);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return waitlistedStudent;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String courseCode, String studentID) {
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where SEMESTER = ? and COURSECODE = ? and STUDENTID = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.setString(3, studentID);
            dropScheduleByCourse.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropScheduleByCourses = connection.prepareStatement("delete from app.schedule where SEMESTER = ? and COURSECODE = ?");
            dropScheduleByCourses.setString(1, semester);
            dropScheduleByCourses.setString(2, courseCode);
            dropScheduleByCourses.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            updateSchedule = connection.prepareStatement("update app.schedule set status = 'S' where SEMESTER = ? and COURSECODE = ? and STUDENTID = ?");
            updateSchedule.setString(1, semester);
            updateSchedule.setString(2, entry.getCourseCode());
            updateSchedule.setString(3, entry.getStudentID());
            updateSchedule.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
