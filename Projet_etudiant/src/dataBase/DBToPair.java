package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import basicObjects.Pair;

public class DBToPair {
	
	public static int getMaxGroupID(int projectID) {
	    int maxID = 0;

	    try (Connection connection = DBConnection.getConnection()) {
	        String query = "SELECT MAX(groupID) FROM pair WHERE projectID = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, projectID); 
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    maxID = resultSet.getInt(1);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return maxID;
	}


    public static List<Pair> getPairs() {
        List<Pair> pairs = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM pair";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                	int groupID = resultSet.getInt("groupID");
                    int projectID = resultSet.getInt("projectID");
                	int studentID1 = resultSet.getInt("studentID_1");
                    int studentID2 = resultSet.getInt("studentID_2");
                    Date effectiveDeliveryDate = resultSet.getDate("effective_Delivery_Date");
                    Double reportGrade = resultSet.getDouble("report_grade");
                    Double defenseGradeStu1 = resultSet.getDouble("defense_grade_stu_1");
                    Double defenseGradeStu2 = resultSet.getDouble("defense_grade_stu_2");
                    Double finalGradeStu1 = resultSet.getDouble("final_grade_stu_1");
                    Double finalGradeStu2 = resultSet.getDouble("final_grade_stu_2");
                    
                 

                    Pair pair = new Pair(groupID, projectID, studentID1, studentID2, effectiveDeliveryDate,reportGrade, defenseGradeStu1,defenseGradeStu2,finalGradeStu1,finalGradeStu2);
                    pairs.add(pair);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return pairs;
    }

    
	public static List<Pair> getPairsForProject(int projectID) {
	    List<Pair> pairs = new ArrayList<>();

	    try (Connection connection = DBConnection.getConnection()) {
	        String query = "SELECT * FROM pair WHERE projectID = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1,projectID);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    int groupID = resultSet.getInt("groupID");
	                    int studentID1 = resultSet.getInt("studentID_1");
	                    int studentID2 = resultSet.getInt("studentID_2");
	                    Date effectiveDeliveryDate = resultSet.getDate("effective_delivery_date");
	                    double reportGrade = resultSet.getDouble("report_grade");
	                    double defenseGradeStu1 = resultSet.getDouble("defense_grade_stu_1");
	                    double defenseGradeStu2 = resultSet.getDouble("defense_grade_stu_2");
	                    double finalGradeStu1 = resultSet.getDouble("final_grade_stu_1");
	                    double finalGradeStu2 = resultSet.getDouble("final_grade_stu_2");

	                    Pair pair = new Pair(groupID, projectID, studentID1, studentID2, effectiveDeliveryDate, reportGrade, defenseGradeStu1, defenseGradeStu2, finalGradeStu1, finalGradeStu2);
	                    

	                    pairs.add(pair);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        
	    }

	    return pairs;
	}


	public static List<Pair> getPairsForStudent(int studentID) {
	    List<Pair> pairs = new ArrayList<>();

	    try (Connection connection = DBConnection.getConnection()) {
	        String query = "SELECT * FROM pair WHERE studentID_1 = ? OR studentID_2 = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, studentID);
	            statement.setInt(2, studentID);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    int groupID = resultSet.getInt("groupID");
	                    int projectID = resultSet.getInt("projectID");
	                    int studentID1 = resultSet.getInt("studentID_1");
	                    int studentID2 = resultSet.getInt("studentID_2");
	                    Date effectiveDeliveryDate = resultSet.getDate("effective_delivery_date");
	                    double reportGrade = resultSet.getDouble("report_grade");
	                    double defenseGradeStu1 = resultSet.getDouble("defense_grade_stu_1");
	                    double defenseGradeStu2 = resultSet.getDouble("defense_grade_stu_2");

	               
	                    double finalGradeStu1 = resultSet.getDouble("final_grade_stu_1");
	                    double finalGradeStu2 = resultSet.getDouble("final_grade_stu_2");

	                    Pair pair = new Pair(groupID, projectID, studentID1, studentID2, effectiveDeliveryDate, reportGrade, defenseGradeStu1, defenseGradeStu2, finalGradeStu1, finalGradeStu2);
	                    pairs.add(pair);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	 
	    }

	    return pairs;
	}

	public static double getMaxFinalGradeForProject(int projectID) {
	    double maxFinalGrade = 0.0;

	    try (Connection connection = DBConnection.getConnection()) {
	        String query = "SELECT MAX(final_grade) AS max_final_grade FROM " +
	                       "(SELECT GREATEST(final_grade_stu_1, final_grade_stu_2) AS final_grade " +
	                       "FROM pair WHERE projectID = ?) AS subquery";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, projectID);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    maxFinalGrade = resultSet.getDouble("max_final_grade");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	       
	    }

	    return maxFinalGrade;
	}
	
	public static double getMinFinalGradeForProject(int projectID) {
	    double minFinalGrade = 0.0;

	    try (Connection connection = DBConnection.getConnection()) {
	        String query = "SELECT MIN(final_grade) AS min_final_grade FROM " +
	                       "(SELECT LEAST(final_grade_stu_1, final_grade_stu_2) AS final_grade " +
	                       "FROM pair WHERE projectID = ?) AS subquery";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, projectID);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    minFinalGrade = resultSet.getDouble("min_final_grade");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	      
	    }

	    return minFinalGrade;
	}
	
	public static double getAverageFinalGradeForProject(int projectID) throws SQLException {
	    double averageFinalGrade = 0.0;

	    try (Connection connection = DBConnection.getConnection()) {
	        String query = "SELECT AVG(final_grade) AS avg_final_grade FROM " +
	                       "(SELECT (final_grade_stu_1 + final_grade_stu_2) / 2 AS final_grade " +
	                       "FROM pair WHERE projectID = ?) AS subquery";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, projectID);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    averageFinalGrade = resultSet.getDouble("avg_final_grade");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }

	    return averageFinalGrade;
	}







    
}