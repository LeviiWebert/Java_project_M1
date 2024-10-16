package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import basicObjects.Project;

public class DBToProject {
	
	public static int getMaxProjectID() {
        int maxID = 0;

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT MAX(projectID) FROM project";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxID;
    }

	
	
	
    public static List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM project";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int projectID = resultSet.getInt("projectID");
                    String subject = resultSet.getString("subject");
                    String theme = resultSet.getString("theme");
                    Date expectedDeliveryDate = resultSet.getDate("expected_Delivery_Date");

                    Project project = new Project(projectID, subject, theme, expectedDeliveryDate);
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return projects;
    }


    public static List<Integer> getProjectIDs() {
        List<Integer> projectIDs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT projectID FROM project";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int projectID = resultSet.getInt("projectID");
                    projectIDs.add(projectID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return projectIDs;
    }



    public static Project getProjectForID(int selectedProjectID) {
        Project project = null;

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM project WHERE projectID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, selectedProjectID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int projectID = resultSet.getInt("projectID");
                        String subject = resultSet.getString("subject");
                        String theme = resultSet.getString("theme");
                        Timestamp expectedDeliveryDate = resultSet.getTimestamp("expected_delivery_date");

                        project = new Project(projectID, subject, theme, expectedDeliveryDate);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return project;
    }





    public static List<Project> getProjectsForTheme(String themeFilter) {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM project WHERE theme LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + themeFilter + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int projectID = resultSet.getInt("projectID");
                        String subject = resultSet.getString("subject");
                        String theme = resultSet.getString("theme");
                        Timestamp expectedDeliveryDate = resultSet.getTimestamp("expected_delivery_date");

                        Project project = new Project(projectID, subject, theme, expectedDeliveryDate);
                        projects.add(project);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    


}