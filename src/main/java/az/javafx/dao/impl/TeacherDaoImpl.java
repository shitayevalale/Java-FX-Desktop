package az.javafx.dao.impl;

import az.javafx.config.DBConfig;
import az.javafx.dao.TeacherDao;
import az.javafx.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {
    @Override
    public boolean saveTeacher(Teacher teacher) {

        boolean isAdded = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String insertIntoTeacherContactInfo = "INSERT INTO t_contact_info(email,phone) VALUES (?,?)";
        String getlastTeacherInfoId = "SELECT MAX(id) id FROM t_contact_info";
        String insertIntoTeacher = "Insert Into teacher(t_name,t_surname,t_age,t_seriaNum,gender,contact_info_id ) VALUES (?,?,?,?,?,?)";


        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(insertIntoTeacherContactInfo);
                ps.setString(1, teacher.getEmail());
                ps.setString(2, teacher.getPhone());
                ps.execute();

                ps = c.prepareStatement(getlastTeacherInfoId);
                rs = ps.executeQuery();
                long lastTeacherInfoId = 0;
                if (rs.next()) {
                    lastTeacherInfoId = rs.getLong("id");
                }

                ps = c.prepareStatement(insertIntoTeacher);
                ps.setString(1, teacher.getName());
                ps.setString(2, teacher.getSurname());
                ps.setString(3, teacher.getDOB());
                ps.setString(4, teacher.getSeriaNum());
                ps.setString(5, teacher.getGender());
                ps.setLong(6, lastTeacherInfoId);
//                System.out.println(teacher);
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

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "  SELECT\n" +
                "                t.t_id id,\n" +
                "                t.t_name name,\n" +
                "                t.t_surname surname,\n" +
                "                t.t_age age,\n" +
                "                t.t_seriaNum seria_num,\n" +
                "                tci.email email,\n" +
                "                tci.phone phone\n" +
                "                FROM\n" +
                "                teacher t \n" +
                "                LEFT JOIN t_contact_info tci ON t.contact_info_id = tci.id WHERE t.active = 1";


        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setId(rs.getLong("id"));
                    teacher.setName(rs.getString("name"));
                    teacher.setSurname(rs.getString("surname"));
                    teacher.setSeriaNum(rs.getString("seria_num"));
                    teacher.setDOB(rs.getString("age"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPhone(rs.getString("phone"));
                    teachers.add(teacher);
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

        return teachers;

    }

    @Override
    public boolean softDeleteTeacher(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "UPDATE teacher SET active=0 WHERE t_id=?";
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
    public boolean hardDeleteTeacher(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM teacher WHERE t_id=?";
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
    public Teacher getTeacherById(Long id) {
        Teacher teacher = new Teacher();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "  SELECT\n" +
                "                t.t_id id,\n" +
                "                t.t_name name,\n" +
                "                t.t_surname surname,\n" +
                "                t.t_age age,\n" +
                "                t.t_seriaNum seria_num,\n" +
                "                tci.email email,\n" +
                "                tci.phone phone\n" +
                "                FROM\n" +
                "                teacher t \n" +
                "                LEFT JOIN t_contact_info tci ON t.contact_info_id = tci.id WHERE  t.active = 1 and t.t_id=" + id;

        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {

                    teacher.setId(rs.getLong("t_id"));
                    teacher.setName(rs.getString("t_name"));
                    teacher.setSurname(rs.getString("t_surname"));
                    teacher.setSeriaNum(rs.getString("t_seriaNum"));
                    teacher.setDOB(rs.getString("t_age"));
                    teacher.setEmail(rs.getString("email"));
                    teacher.setPhone(rs.getString("phone"));
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
        return teacher;
    }

    @Override
    public boolean updateTeacherById(Teacher teacher) {
        boolean isUpdated = false;
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String updateTeacher = "UPDATE teacher SET t_name = ? , t_surname = ? , t_age =? , t_seriaNum = ?,gender=? WHERE t_id=?";
        String selectContactInfoIdById = "SELECT contact_info_id FROM teacher WHERE id= " + teacher.getId();
        String updateTeacherContactInfo = "UPDATE t_contact_info SET email = ? , phone = ?  WHERE t_id=?";

        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(updateTeacher);
                ps.setString(1, teacher.getName());
                ps.setString(2, teacher.getSurname());
                ps.setString(3, teacher.getDOB());
                ps.setString(4, teacher.getSeriaNum());
                ps.setString(5, teacher.getGender());
                ps.setLong(6, teacher.getId());
                ps.execute();

                ps = c.prepareStatement(selectContactInfoIdById);
                rs = ps.executeQuery();
                long contactInfoId = 0;
                if (rs.next()) {
                    contactInfoId = rs.getLong("contact_info_id");
                }
                ps = c.prepareStatement(updateTeacherContactInfo);
                ps.setString(1, teacher.getEmail());
                ps.setString(2, teacher.getPhone());
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
