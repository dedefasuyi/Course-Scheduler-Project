
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
class StudentQueries {
    private static Connection connection;
    private static ResultSet resultSet;
    private static PreparedStatement addStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    
    
    public void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudents = connection.prepareStatement("insert into app.student (STUDENTID, STUDENTFIRSTNAME, STUDENTLASTNAME) values (?, ?, ?)");
            addStudents.setString(1, student.getStudentID());
            addStudents.setString(2, student.getFirstname());
            addStudents.setString(3, student.getLastname());
            addStudents.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntryList = new ArrayList<>();
        try
        {
            PreparedStatement query = connection.prepareStatement("select * from app.student");
            
            resultSet = query.executeQuery();

            while(resultSet.next())
            {
                StudentEntry entry = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                studentEntryList.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentEntryList;
    }
    
    public static StudentEntry getStudent(String StudentID){
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try
        {
            getStudent = connection.prepareStatement("select studentID, studentfirstname, studentlastname from app.student where STUDENTID = ?");
            getStudent.setString(1, StudentID);
            resultSet = getStudent.executeQuery();
            resultSet.next();
            student = new StudentEntry(resultSet.getString("StudentID"), resultSet.getString("studentfirstname"), resultSet.getString("studentlastname"));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    }
    
    public static void dropStudent(String StudentID){
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where STUDENTID = ?");
            dropStudent.setString(1, StudentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}