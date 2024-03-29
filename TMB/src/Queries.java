import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Statement;

public class Queries {
	
	public static ArrayList<String> getUserIDs() {
		String query = ""
				+ "SELECT ID "
				+ "FROM user;";
		ArrayList<String> IDs = new ArrayList<>();
		TMB.makeStatement();
		ArrayList<Object[]> result = TMB.executeQuery(query, "ID");
		for (Object[] arr: result) {
			IDs.add((String) arr[0]);
		}
		return IDs;
	}
	
	public static String getPassword(String ID) {
		String query = ""
				+ "SELECT password "
				+ "FROM user "
				+ "WHERE ID=(?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return (String) TMB.executePreparedQuery("password").get(0)[0];
	}
	
	public static boolean isAdmin(String ID) {
		String query = ""
				+ "SELECT ID "
				+ "FROM admin "
				+ "WHERE ID = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery("ID").size() > 0;
	}
	
	public static void addUser(String ID, String fName, String mInit, String lName, String password, String email) {
		String query = ""
				+ "INSERT INTO user "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setString(2, fName);
			psmt.setString(3, mInit);
			psmt.setString(4, lName);
			psmt.setString(5, password);
			psmt.setString(6, email);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static String[] getUserName(String ID) {
		String query = ""
				+ "SELECT first_name, last_name "
				+ "FROM user "
				+ "WHERE ID = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		Object[] result = TMB.executePreparedQuery("first_name", "last_name").get(0);
		return new String[] {(String) result[0], (String) result[1]};
	}
	
	public static ArrayList<String> getStationNames() {
		String query = ""
				+ "SELECT name "
				+ "FROM station "
				+ "ORDER BY name ASC;";
		ArrayList<String> names = new ArrayList<>();
		TMB.makeStatement();
		ArrayList<Object[]> result = TMB.executeQuery(query, "name");
		for (Object[] arr: result) {
			names.add((String) arr[0]);
		}
		return names;
	}
	
	public static void addReview(String ID, int rID, int shopping, int connectionSpeed, String comment, String adminID, String status, Timestamp timestamp, String stationName) {
		String query = ""
				+ "INSERT INTO review "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setInt(2, rID);
			psmt.setInt(3, shopping);
			psmt.setInt(4, connectionSpeed);
			psmt.setString(5, comment);
			psmt.setString(6, adminID);
			psmt.setString(7, status);
			psmt.setTimestamp(8, timestamp);
			psmt.setString(9, stationName);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static ArrayList<Object[]> getReviews(String order, String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM review "
				+ "ORDER BY %s ASC;";
		query = String.format(query, order);
		TMB.makeStatement();
		return TMB.executeQuery(query, attributes);
	}
	
	public static ArrayList<Object[]> getReviewsByUser(String ID, String order, String direction, String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM review "
				+ "WHERE passenger_ID = (?) "
				+ "ORDER BY %s %s;";
		query = String.format(query, order, direction);
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes);
	}
	
	public static Object[] getReview(String ID, int rid, String... attributes) {
		String query = ""
				+ "SELECT *"
				+ "FROM review "
				+ "WHERE passenger_ID = (?) AND rid = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setInt(2, rid);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes).get(0);
	}
	
	public static void updateReview(String ID, int rid, int shopping, int connectionSpeed, String comment, String approverID, String status, Timestamp timestamp) {
		String query = ""
				+ "UPDATE review "
				+ "SET shopping = (?), connection_speed = (?), comment = (?), approver_ID = (?), approval_status = (?), edit_timestamp = (?) "
				+ "WHERE passenger_ID = (?) AND rid = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setInt(1, shopping);
			psmt.setInt(2, connectionSpeed);
			psmt.setString(3, comment);
			psmt.setString(4, approverID);
			psmt.setString(5, status);
			psmt.setTimestamp(6, timestamp);
			psmt.setString(7, ID);
			psmt.setInt(8, rid);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void deleteReview(String ID, int rid) {
		String query = ""
				+ "DELETE FROM review "
				+ "WHERE passenger_ID = (?) AND rid = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setInt(2, rid);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static Object[] getStation(String name, String... attributes) {
		String query = ""
				+ "SELECT *"
				+ "FROM station "
				+ "WHERE name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, name);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes).get(0);
	}
	
	public static ArrayList<String> getLinesFromStation(String stationName) {
		String query = ""
				+ "SELECT line_name "
				+ "FROM station_on_line "
				+ "WHERE station_name = (?) "
				+ "ORDER BY line_name ASC;";
		ArrayList<String> names = new ArrayList<>();
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, stationName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Object[]> result = TMB.executePreparedQuery("line_name");
		for (Object[] arr: result) {
			names.add((String) arr[0]);
		}
		return names;
	}
	
	public static float[] getAvgRatings(String stationName) {
		String query = ""
				+ "SELECT AVG(shopping) AS avg_shopping, AVG(connection_speed) AS avg_speed "
				+ "FROM review "
				+ "WHERE station_name = (?) AND approval_status = 'Approved';";
		ArrayList<int[]> ratings = new ArrayList<>();
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, stationName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Object[] result = TMB.executePreparedQuery("avg_shopping", "avg_speed").get(0);
		if (result[0] == null || result[1] == null) {
			return new float[] {-1, -1};
		}
		return new float[] {((BigDecimal) result[0]).floatValue(), ((BigDecimal) result[1]).floatValue()};
	}
	
	public static long getNumStops(String lineName) {
		String query = ""
				+ "SELECT COUNT(station_name) AS num_stations "
				+ "FROM station_on_line "
				+ "WHERE line_name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, lineName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Object[] result = TMB.executePreparedQuery("num_stations").get(0);
		return (long) result[0];
	}
	
	public static ArrayList<Object[]> getStationsFromLine(String lineName, String order, String direction) {
		String query = ""
				+ "SELECT station_name, order_number "
				+ "FROM station_on_line "
				+ "WHERE line_name = (?) "
				+ "ORDER BY %s %s;";
		query = String.format(query, order, direction);
		ArrayList<Object[]> stations = new ArrayList<>();
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, lineName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery("station_name", "order_number");
	}
	
	public static Object[] getUserInfo(String ID, String... attributes) {
		String query = ""
				+ "SELECT *"
				+ "FROM user "
				+ "WHERE ID = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes).get(0);
	}
	
	public static void updateUser(String originalID, String newID, String fName, String mInit, String lName, String password, String email) {
		String query = ""
				+ "UPDATE user "
				+ "SET ID = (?), first_name = (?), minit= (?), last_name = (?), password = (?), passenger_email = (?) "
				+ "WHERE ID = (?);";		
		
				
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, newID);
			psmt.setString(2, fName);
			psmt.setString(3, mInit);
			psmt.setString(4, lName);
			psmt.setString(5, password);
			psmt.setString(6, email);
			psmt.setString(7, originalID);
			
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void deleteUser(String ID) {
		String query = ""
				+ "DELETE FROM user "
				+ "WHERE ID = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void buyCard(String ID, String type, Timestamp purchaseDate, Object usesLeft, Date expirationDate) {
		String query = ""
				+ "INSERT INTO card "
				+ "VALUES ((?), (?), (?), (?), (?));";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setString(2, type);
			psmt.setTimestamp(3, purchaseDate);
			psmt.setObject(4, usesLeft);
			psmt.setDate(5, expirationDate);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static ArrayList<Object[]> getUserValidCards(String ID, String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM card "
				+ "WHERE user_ID = (?) AND (uses_left > 0 OR uses_left IS NULL) AND (expiration_date >= CURDATE() OR expiration_date IS NULL);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes);
	}
	
	public static void embark(String ID, String type, String purchaseDate, Timestamp startTime, Timestamp endTime, String startStation, String endStation) {
		String query = ""
				+ "UPDATE card "
				+ "SET uses_left = uses_left - 1 "
				+ "WHERE user_ID = (?) AND type = (?) AND purchase_date_time = (?) AND uses_left IS NOT NULL;";	
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setString(2, type);
			psmt.setString(3, purchaseDate);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
		query = ""
				+ "INSERT INTO trip "
				+ "VALUES ((?), (?), (?), (?), (?), (?), (?));";
		psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setString(2, type);
			psmt.setString(3, purchaseDate);
			psmt.setTimestamp(4, startTime);
			psmt.setTimestamp(5, endTime);
			psmt.setString(6, startStation);
			psmt.setString(7, endStation);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static ArrayList<Object[]> getAllTrips(String ID, String order, String direction, String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM trip "
				+ "WHERE user_ID = (?) "
				+ "ORDER BY %s %s;";
		query = String.format(query, order, direction);
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes);
	}
	
	public static ArrayList<Object[]> getTrips(String ID, String type, Timestamp purchaseDate, String order, String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM trip "
				+ "WHERE user_ID = (?) AND card_type = (?) AND card_purchase_date_time = (?) "
				+ "ORDER BY %s ASC;";
		query = String.format(query, order);
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setString(2, type);
			psmt.setTimestamp(3, purchaseDate);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes);
	}
	
	public static String getTripStartStation(String ID, String type, Timestamp purchaseDate, Timestamp startDate) {
		String query = ""
				+ "SELECT from_station_name "
				+ "FROM trip "
				+ "WHERE user_ID = (?) AND card_type = (?) AND card_purchase_date_time = (?) AND start_date_time = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
			psmt.setString(2, type);
			psmt.setTimestamp(3, purchaseDate);
			psmt.setTimestamp(4, startDate);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return (String) TMB.executePreparedQuery("from_station_name").get(0)[0];
	}
	
	public static void updateTrip(String ID, String type, Timestamp purchaseDate, Timestamp startDate, Timestamp endDate, String endStation) {
		String query = ""
				+ "UPDATE trip "
				+ "SET end_date_time = (?), to_station_name = (?) "
				+ "WHERE user_ID = (?) AND card_type = (?) AND card_purchase_date_time = (?) AND start_date_time = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setTimestamp(1, endDate);
			psmt.setString(2, endStation);
			psmt.setString(3, ID);
			psmt.setString(4, type);
			psmt.setTimestamp(5, purchaseDate);
			psmt.setTimestamp(6, startDate);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	//getCurrentTimeStamp is throwing an index out of bound error!
	//"INDEX 0 out of bounds for length 0"
	public static Timestamp getCurrentTimestamp() {	
		String query = ""
				+ "SELECT current_timestamp() AS timestamp;";
		TMB.makeStatement();
		return (Timestamp) TMB.executeQuery(query, "timestamp").get(0)[0];
	}
	
	public static Timestamp getCurrentTimestamp2() {
		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		return date;
	}
	
	public static ArrayList<Object[]> getPendingReviews(String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM review "
				+ "WHERE approval_status = 'Pending';";
		TMB.makeStatement();
		return TMB.executeQuery(query, attributes);
	}
	
	public static void updateReviewStatus(String reviewerID, int rid, String adminID, String status) {
		String query = ""
				+ "UPDATE review "
				+ "SET approval_status = (?), approver_ID = (?) "
				+ "WHERE passenger_ID = (?) AND rid = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, status);
			psmt.setString(2, adminID);
			psmt.setString(3, reviewerID);
			psmt.setInt(4, rid);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void updateAdminID(String originalID, String newID) {
		String query = ""
				+ "UPDATE admin "
				+ "SET ID = (?) "
				+ "WHERE ID = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, newID);
			psmt.setString(2, originalID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void deleteAdmin(String ID) {
		String query = ""
				+ "DELETE FROM admin "
				+ "WHERE ID = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, ID);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void addStation(String name, String status, String state, String address, int zipcode, String city, String ID, Timestamp date) {
		String query = ""
				+ "INSERT INTO station "
				+ "VALUES ((?), (?), (?), (?), (?), (?));";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, name);
			psmt.setString(2, status);
			psmt.setString(3, state);
			psmt.setString(4, address);
			psmt.setInt(5, zipcode);
			psmt.setString(6, city);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
		query = ""
				+ "INSERT INTO admin_add_station "
				+ "VALUES ((?), (?), (?));";
		psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, name);
			psmt.setString(2, ID);
			psmt.setTimestamp(3, date);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static ArrayList<String> getLineNames() {
		String query = ""
				+ "SELECT name "
				+ "FROM line;";
		TMB.makeStatement();
		ArrayList<Object[]> result = TMB.executeQuery(query, "name");
		ArrayList<String> names = new ArrayList<>();
		for (Object[] arr: result) {
			names.add((String) arr[0]);
		}
		return names;
	}
	
	/**
	
	public static ArrayList<String> getLineAddresses() {
		String query = ""
				+"SELECT address, city, state_province, zipcode"
				+"FROM station";
		TMB.makeStatement();
		ArrayList<Object[]> result = TMB.executeQuery(query, "name");
		ArrayList<String> names = new ArrayList<>();
		for (Object[] arr: result) {
			names.add((String) arr[0]);
		}
		return names;
	}
	*/
	//I NEED TO SEE WHETHER THE COMBINATION OF THE ADDRESS IS UNIQUE
	
	public static void addLineToStation(String stationName, String lineName, int orderNumber) {
		String query = ""
				+ "INSERT INTO station_on_line "
				+ "VALUES ((?), (?), (?));";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, stationName);
			psmt.setString(2, lineName);
			psmt.setInt(3, orderNumber);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static ArrayList<Integer> getLineOrderNumbers(String lineName) {
		String query = ""
				+ "SELECT order_number "
				+ "FROM station_on_line "
				+ "WHERE line_name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, lineName);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		ArrayList<Object[]> result = TMB.executePreparedQuery("order_number");
		ArrayList<Integer> numbers = new ArrayList<>();
		for (Object[] arr: result) {
			numbers.add((Integer) arr[0]);
		}
		return numbers;
	}
	
	public static void addLine(String name, String ID, Timestamp date) {
		String query = ""
				+ "INSERT INTO line "
				+ "VALUES ((?));";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, name);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
		query = ""
				+ "INSERT INTO admin_add_line "
				+ "VALUES ((?), (?), (?));";
		psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, name);
			psmt.setString(2, ID);
			psmt.setTimestamp(3, date);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void deleteStation(String name) {
		String query = ""
				+ "DELETE FROM station "
				+ "WHERE name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, name);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void updateStationOrderNumber(String stationName, String lineName, int orderNumber) {
		String query = ""
				+ "UPDATE station_on_line "
				+ "SET order_number = (?) "
				+ "WHERE station_name = (?) AND line_name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setInt(1, orderNumber);
			psmt.setString(2, stationName);
			psmt.setString(3, lineName);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static void updateStationStatus(String name, String status) {
		String query = ""
				+ "UPDATE station "
				+ "SET status = (?) "
				+ "WHERE name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, status);
			psmt.setString(2, name);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
	
	public static ArrayList<String[]> getStationAddresses() {	
		String query = ""
				+ "SELECT state_province, address, zipcode, city "
				+ "FROM station;";
		TMB.makeStatement();
		ArrayList<Object[]> result = TMB.executeQuery(query, "state_province", "address", "zipcode", "city");
		ArrayList<String[]> addresses = new ArrayList<>();
		for(Object[] arr: result) {
			addresses.add(new String[] {(String) arr[0], (String) arr[1], Integer.toString((Integer)arr[2]), (String) arr[3]});
		}
		return addresses;
	}
	
	public static ArrayList<Object[]> getTripsFromStartTime(Timestamp date, String... attributes) {	
		String query = ""
				+ "SELECT * "
				+ "FROM trip "
				+ "WHERE start_date_time = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setTimestamp(1, date);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes);
	}
	
	public static ArrayList<Object[]> getReviewsByStation(String station, String... attributes) {
		String query = ""
				+ "SELECT * "
				+ "FROM review "
				+ "WHERE station_name = (?);";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, station);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return TMB.executePreparedQuery(attributes);
	}
	
	public static void deleteAdminStationsAndLines(String id) {
		String query = ""
				+ "DELETE FROM station "
				+ "WHERE (name IN (SELECT station_name "
				+ "FROM admin_add_station "
				+ "WHERE admin_ID = (?)));";
		PreparedStatement psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
		query = ""
				+ "DELETE FROM line "
				+ "WHERE (name IN (SELECT line_name "
				+ "FROM admin_add_line "
				+ "WHERE admin_ID = (?)));";
		psmt = TMB.makePreparedStatement(query);
		try {
			psmt.setString(1, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		TMB.executePreparedModification();
	}
}