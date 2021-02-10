package az.javafx.dao.impl;

import az.javafx.config.DBConfig;
import az.javafx.dao.GroupDao;
import az.javafx.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDao {
    @Override
    public boolean saveGroup(Group group) {
        boolean isAdded = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = " Insert Into group1 (group_name) VALUES(?)";
        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.setString(1, group.getGroup_name());
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
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = " SELECT id,group_name  FROM group1 WHERE active=1";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Group group = new Group();
                    group.setId(rs.getLong("id"));
                    group.setGroup_name(rs.getString("group_name"));
                    groups.add(group);
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

        return groups;

    }

    @Override
    public boolean softDeleteGroups(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "  UPDATE group1 SET active=0 WHERE id=?";
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
    public boolean hardDeleteGroup(Long id) {
        boolean isDeleted = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM group1 WHERE id=?";
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
    public Group getGroupById(Long id) {
        Group group = new Group();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT id,group_name FROM group1 WHERE active=1 and id=?";
        try {
            c = DBConfig.getconnection();
            if (c != null) {
                ps = c.prepareStatement(sql);
                ps.setLong(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    group.setId(rs.getLong("id"));
                    group.setGroup_name(rs.getString("group_name"));
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

        return group;

    }

    @Override
    public boolean updateGroupById(Group group) {
        boolean isUpdated = false;
        Connection c = null;
        PreparedStatement ps = null;
        String sql = "UPDATE group1 SET group_name = ? WHERE ID=?";
        c = DBConfig.getconnection();
        if (c != null) {
            try {
                ps = c.prepareStatement(sql);
                ps.setString(1, group.getGroup_name());
                ps.setLong(2, group.getId());
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
