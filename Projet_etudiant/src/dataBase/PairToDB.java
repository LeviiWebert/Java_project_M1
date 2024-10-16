package dataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import basicObjects.Pair;

public class PairToDB {
	    public void addPair(Pair p) {
	    	
	      
	        try (Connection connection = DBConnection.getConnection()) {
	        	 String Query = "INSERT INTO pair (groupID, projectID, studentID_1, studentID_2, effective_delivery_date, report_grade, defense_grade_stu_1, defense_grade_stu_2, final_grade_stu_1,final_grade_stu_2) VALUES (?,?,?,?,?, ?,?,?,?,?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(Query)) {
                    preparedStatement.setInt(1, p.getGroupID());
                    preparedStatement.setInt(2, p.getProject().getProjectID());
                    preparedStatement.setInt(3, p.getStudent1().getStudentID());
                    preparedStatement.setInt(4, p.getStudent2().getStudentID());
                    
                    
                    
             
                    java.util.Date utilDate = p.getEffectiveDeliveryDate();
                    java.sql.Date sqlDate = (utilDate != null) ? new java.sql.Date(utilDate.getTime()) : null;
                    preparedStatement.setDate(5, sqlDate);


                    preparedStatement.setDouble(6, p.getReportGrade());
                    preparedStatement.setDouble(7, p.getDefenseGradeStu1());
                    preparedStatement.setDouble(8, p.getDefenseGradeStu2());
                    preparedStatement.setDouble(9, p.getFinalGradeStu1());
                    preparedStatement.setDouble(10, p.getFinalGradeStu2());
	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	    }
	    
	    public void deletePair(Pair pair) {
		    
	        try (Connection connection = DBConnection.getConnection()) {
	           
	            String deletePairQuery = "DELETE FROM pair WHERE groupID  = ? AND projectID = ? ";
	            try (PreparedStatement deletePairStatement = connection.prepareStatement(deletePairQuery)) {
	                deletePairStatement.setInt(1, pair.getGroupID());
	                deletePairStatement.setInt(2, pair.getProject().getProjectID());
	                deletePairStatement.executeUpdate();
	            	
	            }

	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	    }
	    
		
		public static void updatePair(Pair pair) throws SQLException {
		try (Connection connection = DBConnection.getConnection()) {
	        String query = "UPDATE pair SET studentID_1 = ?, studentID_2 = ?, effective_delivery_date = ?, report_grade = ?, defense_grade_stu_1 = ?, defense_grade_stu_2 = ?, final_grade_stu_1 = ?, final_grade_stu_2 = ?  WHERE groupID = ? AND projectID=?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	        	
	    
	        	 statement.setInt(1, pair.getStudent1().getStudentID());
	        	 statement.setInt(2, pair.getStudent2().getStudentID());
	        	 
	          
	             if (pair.getEffectiveDeliveryDate() != null) {
	                 statement.setDate(3, new java.sql.Date(pair.getEffectiveDeliveryDate().getTime()));
	             } else {
	                 statement.setNull(3, java.sql.Types.DATE);
	             }

	        	 statement.setDouble(4, pair.getReportGrade());
	             statement.setDouble(5, pair.getDefenseGradeStu1());
	             statement.setDouble(6, pair.getDefenseGradeStu2());

	             statement.setDouble(7, pair.getFinalGradeStu1());
	             statement.setDouble(8, pair.getFinalGradeStu2());
	             statement.setInt(9, pair.getGroupID());
	        	 statement.setInt(10, pair.getProject().getProjectID());
	           
	         
	             statement.executeUpdate();
	
	        
	        
	        } catch (SQLException e) {
	            e.printStackTrace();
	            
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
		}
		
		

	    
	    

}
