package az.javafx.dao.impl;

import az.javafx.config.DBConfig;
import az.javafx.dao.SubjectDao;
import az.javafx.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDaoImpl implements SubjectDao  {
    @Override
    public boolean saveSubject(Subject subject) {
        boolean isAdded = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "Insert Into subjects (subject_name) VALUES(?)";
        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.setString(1, subject.getSubject_name());
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
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id,subject_name  FROM subjects WHERE active=1";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Subject subject = new Subject();
                    subject.setId(rs.getLong("id"));
                    subject.setSubject_name(rs.getString("subject_name"));
                    subjects.add(subject);
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

        return subjects;

    }

    @Override
    public boolean softDeleteSubjects(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = " UPDATE subjects SET active=0 WHERE id=?";
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
    public boolean hardDeleteSubject(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM subjects WHERE id=?";
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
    public Subject getSubjectById(Long id) {
        Subject subject = new Subject();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id,subject_name FROM subjects WHERE active=1 and id=?";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    subject.setId(rs.getLong("id"));
                    subject.setSubject_name(rs.getString("s_name"));

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

        return subject;
    }

    @Override
    public boolean updateSubjectById(Subject subject) {
        boolean isUpdated = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "UPDATE subjects SET subject_name = ? WHERE ID=?";
        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.setString(1, subject.getSubject_name());
                ps.setLong(2, subject.getId());
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
