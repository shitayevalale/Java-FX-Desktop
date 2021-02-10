package az.javafx.dao.impl;

import az.javafx.config.DBConfig;
import az.javafx.dao.StudentDao;
import az.javafx.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean saveStudent(Student student) {

        boolean isAdded = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        String insertIntoStudentContactInfo = "INSERT INTO s_contact_info(email,phone) VALUES (?,?)";
        String getlastStudentInfoId = "SELECT MAX(id) id FROM s_contact_info";
        String insertIntoStudent = "Insert Into student(s_name,s_surname,s_age,s_seriaNum,gender,contact_info_id ) VALUES (?,?,?,?,?,?)";


        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(insertIntoStudentContactInfo);
                ps.setString(1, student.getEmail());
                ps.setString(2, student.getPhone());
                ps.execute();

                ps = c.prepareStatement(getlastStudentInfoId);
                rs = ps.executeQuery();
                long lastStudentInfoId = 0;
                if (rs.next()) {
                    lastStudentInfoId = rs.getLong("id");
                }


                ps = c.prepareStatement(insertIntoStudent);
                ps.setString(1, student.getName());
                ps.setString(2, student.getSurname());
                ps.setString(3, student.getDOB());
                ps.setString(4, student.getSeriaNum());
                ps.setString(5, student.getGender());
                ps.setLong(6, lastStudentInfoId);
                ps.execute();
                isAdded = true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            } finally {
                try {
                    ps.close();
                    c.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());

                }
            }
        }
        return isAdded;

    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "  SELECT\n" +
                "                s.s_id id,\n" +
                "                s.s_name name,\n" +
                "                s.s_surname surname,\n" +
                "                s.s_age age,\n" +
                "                s.s_seriaNum seria_num,\n" +
                "                sci.email email,\n" +
                "                sci.phone phone\n" +
                "                FROM\n" +
                "                student s \n" +
                "                LEFT JOIN s_contact_info sci ON s.contact_info_id = sci.id WHERE s.active = 1";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getLong("id"));
                    student.setName(rs.getString("name"));
                    student.setSurname(rs.getString("surname"));
                    student.setSeriaNum(rs.getString("seria_num"));
                    student.setDOB(rs.getString("age"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));
                    students.add(student);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                c.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());

            }
        }

        return students;

    }

    @Override
    public boolean softDeleteStudent(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "UPDATE student SET active=0 WHERE s_id=?";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                ps.execute();
                isDeleted = true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                ps.close();
                c.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return isDeleted;

    }

    @Override
    public boolean hardDeleteStudent(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM student WHERE s_id=?";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                ps.execute();
                isDeleted = true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                ps.close();
                c.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return isDeleted;

    }

    @Override
    public Student getStudentById(Long id) {
        Student student = new Student();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "  SELECT\n" +
                "                s.s_id id,\n" +
                "                s.s_name name,\n" +
                "                s.s_surname surname,\n" +
                "                s.s_age age,\n" +
                "                s.s_seriaNum seria_num,\n" +
                "                sci.email email,\n" +
                "                sci.phone phone\n" +
                "                FROM\n" +
                "                student s \n" +
                "                LEFT JOIN s_contact_info sci ON s.contact_info_id = sci.id WHERE s.active = 1 and s.s_id=" + id;

        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();



                if (rs.next()) {

                    student.setId(rs.getLong("id"));
                    student.setName(rs.getString("name"));
                    student.setSurname(rs.getString("surname"));
                    student.setSeriaNum(rs.getString("seria_num"));
                    student.setDOB(rs.getString("age"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));


                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ps.close();
                c.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());

            }
        }

        return student;

    }

    @Override
    public boolean updateStudentById(Student student) {
        boolean isUpdated = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        String updateStudent = "UPDATE student SET s_name = ? , s_surname = ? , s_age =? , s_seriaNum = ?,gender=? WHERE s_id=?";
       String selectContactInfoIdById="SELECT contact_info_id FROM student WHERE id= "+student.getId();
        String updateStudentContactInfo="UPDATE s_contact_info SET email = ? , phone = ?  WHERE s_id=?";
        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(updateStudent);
                ps.setString(1, student.getName());
                ps.setString(2, student.getSurname());
                ps.setString(3, student.getDOB());
                ps.setString(4, student.getSeriaNum());
                ps.setString(5, student.getGender());
                ps.setLong(6, student.getId());
                ps.execute();


                ps = c.prepareStatement(selectContactInfoIdById);
                rs = ps.executeQuery();
                long contactInfoId = 0;
                if (rs.next()) {
                    contactInfoId = rs.getLong("contact_info_id");
                }
                ps = c.prepareStatement(updateStudentContactInfo);
                ps.setString(1, student.getEmail());
                ps.setString(2, student.getPhone());
                ps.setLong(3, contactInfoId);
                ps.execute();


                isUpdated = true;
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            } finally {
                try {
                    ps.close();
                    c.close();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());

                }
            }
        }
        return isUpdated;
    }
}
