package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import basicObjects.Project;
import managementAppUI.PairPanel;

public class ProjectToDB {

    public void addProject(Project project) {
       
        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO project (projectID, subject, theme, expected_delivery_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, project.getProjectID());
                preparedStatement.setString(2, project.getSubject());
                preparedStatement.setString(3, project.getTheme());

                java.util.Date utilDate = project.getExpectedDeliveryDate();
                java.sql.Date sqlDate = (utilDate != null) ? new java.sql.Date(utilDate.getTime()) : null;
                preparedStatement.setDate(4, sqlDate);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public void deleteProject(int projectID) {
        
        try (Connection connection = DBConnection.getConnection()) {
           
            String deletePairQuery = "DELETE FROM pair WHERE projectID = ?";
            try (PreparedStatement deletePairStatement = connection.prepareStatement(deletePairQuery)) {
                deletePairStatement.setInt(1, projectID);
                deletePairStatement.executeUpdate();
            }

          
            String deleteProjectQuery = "DELETE FROM project WHERE projectID = ?";
            try (PreparedStatement deleteProjectStatement = connection.prepareStatement(deleteProjectQuery)) {
                deleteProjectStatement.setInt(1, projectID);
                deleteProjectStatement.executeUpdate();
            }
            
         // Refresh PairTable
            PairPanel.updateTable(DBToPair.getPairs());
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }


    public static void updateProject(Project project) {
    	 try (Connection connection = DBConnection.getConnection()) {
        String query = "UPDATE project SET subject=?, theme=?, expected_delivery_date=? WHERE projectID=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, project.getSubject());
            preparedStatement.setString(2, project.getTheme());

           
            if (project.getExpectedDeliveryDate() != null) {
                preparedStatement.setDate(3, new java.sql.Date(project.getExpectedDeliveryDate().getTime()));
            } else {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            }

            preparedStatement.setInt(4, project.getProjectID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }catch (SQLException e) {
        e.printStackTrace(); 
    }

 

}
    

    
}