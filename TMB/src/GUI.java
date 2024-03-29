import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JRadioButton;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GUI {

	private JFrame frame;

	// SESSION INFORMATION
	public String fName;
	public String lName;
	public String ID;
	public boolean isAdmin;
	public Integer count = 0;


	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));

		makeLoginPanel();
	}

	private void initPanel(JPanel panel, String constraint) {
		frame.getContentPane().add(panel, constraint);
		panel.setVisible(true);
		panel.setLayout(null);
	}

	private JPanel makeLoginPanel() {
		JPanel panelLogin = new JPanel();
		initPanel(panelLogin, "name_75292580775046");

		// Creates Labels/Text Fields
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(6, 6, 61, 16);
		panelLogin.add(lblLogin);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(112, 52, 61, 16);
		panelLogin.add(lblId);

		JTextField txtid = new JTextField();
		txtid.setBounds(174, 47, 130, 26);
		panelLogin.add(txtid);
		txtid.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(73, 90, 61, 16);
		panelLogin.add(lblPassword);

		JTextField txtpw = new JTextField();
		txtpw.setBounds(174, 85, 130, 26);
		panelLogin.add(txtpw);
		txtpw.setColumns(10);

		// Creates Registration Button
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(e -> {
			panelLogin.setVisible(false);
			makeRegistrationPanel();
		});
		btnRegister.setBounds(73, 198, 117, 74);
		panelLogin.add(btnRegister);

		// Creates Login Button
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(255, 198, 117, 74);
		panelLogin.add(btnLogin);
		btnLogin.addActionListener(e -> {
			String s = e.getActionCommand();
			if (s.equals("Login")) {
				String loginidattempt = txtid.getText();
				String loginpwattempt = txtpw.getText();
				ArrayList<String> ids = Queries.getUserIDs();
				boolean idExists = false;
				for (String id : ids) {
					if (id.equals(loginidattempt)) {
						idExists = true;
					}
				}

				if (idExists) {
					if (loginpwattempt.equals(Queries.getPassword(loginidattempt))) {
						isAdmin = Queries.isAdmin(loginidattempt);
						fName = Queries.getUserName(loginidattempt)[0];
						// JOptionPane.showMessageDialog(panelLogin,
						// Queries.getUserName(loginidattempt)[0]);
						lName = Queries.getUserName(loginidattempt)[1];
						ID = loginidattempt;

						if (isAdmin) {
							panelLogin.setVisible(false);
							makeAdminLandingPanel();
						} else {
							panelLogin.setVisible(false);
							makePassengerLandingPanel();
						}
					} else {
						JOptionPane.showMessageDialog(panelLogin, "Incorrect password!");
					}
				} else {
					JOptionPane.showMessageDialog(panelLogin,
							"User ID does not exist! Haven't created an account yet? Click 'register'!");
				}
			}
		});
		return panelLogin;
	}

	private JPanel makePassengerLandingPanel() {
		JPanel panelPassengerLanding = new JPanel();
		initPanel(panelPassengerLanding, "name_75573884258365");

		// Label
		JLabel lblWelcomename = new JLabel("Welcome " + fName + " " + lName);
		lblWelcomename.setBounds(6, 6, 200, 16);
		panelPassengerLanding.add(lblWelcomename);

		// Buttons
		JButton btnLeaveReview = new JButton("Leave Review");
		btnLeaveReview.setBounds(56, 34, 117, 66);
		panelPassengerLanding.add(btnLeaveReview);
		btnLeaveReview.addActionListener(e -> {
			panelPassengerLanding.setVisible(false);
			makeLeaveReviewPanel();
		});

		JButton btnViewReviews = new JButton("View Reviews");
		btnViewReviews.setBounds(56, 119, 117, 70);
		panelPassengerLanding.add(btnViewReviews);
		btnViewReviews.addActionListener(e -> {
			panelPassengerLanding.setVisible(false);
			makeViewReviewPanel();
		});

		JButton btnBuyCards = new JButton("Buy Card");
		btnBuyCards.addActionListener(e -> {
			panelPassengerLanding.setVisible(false);
			makeBuyCardPanel();
		});
		btnBuyCards.setBounds(56, 201, 117, 71);
		panelPassengerLanding.add(btnBuyCards);

		JButton btnGoOnATrip = new JButton("Go On Trip");
		btnGoOnATrip.addActionListener(e -> {
			panelPassengerLanding.setVisible(false);
			makeGoOnATripPanel();
		});
		btnGoOnATrip.setBounds(245, 37, 117, 66);
		panelPassengerLanding.add(btnGoOnATrip);

		JButton btnViewTrips = new JButton("View Trips");
		btnViewTrips.addActionListener(e -> {
			panelPassengerLanding.setVisible(false);
			makeViewTripsPanel("start_date_time", "ASC");
		});
		btnViewTrips.setBounds(245, 119, 117, 70);
		panelPassengerLanding.add(btnViewTrips);

		JButton btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.addActionListener(e -> {
			panelPassengerLanding.setVisible(false);
			makeEditProfilePanel();
		});
		btnEditProfile.setBounds(245, 201, 117, 71);
		panelPassengerLanding.add(btnEditProfile);

		return panelPassengerLanding;
	}

	private JPanel makeRegistrationPanel() {
		JPanel panelRegistration = new JPanel();
		initPanel(panelRegistration, "name_75295235661086");

		// Labels/Text Fields
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(16, 6, 61, 16);
		panelRegistration.add(lblRegister);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(16, 34, 61, 16);
		panelRegistration.add(lblName);

		JTextField txtFirst = new JTextField();
		txtFirst.setText("First");
		txtFirst.setBounds(16, 62, 130, 26);
		panelRegistration.add(txtFirst);
		txtFirst.setColumns(10);

		JTextField txtMi = new JTextField();
		txtMi.setText("MI");
		txtMi.setBounds(158, 62, 130, 26);
		panelRegistration.add(txtMi);
		txtMi.setColumns(10);

		JTextField txtLast = new JTextField();
		txtLast.setText("Last");
		txtLast.setBounds(300, 62, 130, 26);
		panelRegistration.add(txtLast);
		txtLast.setColumns(10);

		JTextField txtEmail = new JTextField();
		txtEmail.setText("Email");
		txtEmail.setBounds(16, 119, 414, 26);
		panelRegistration.add(txtEmail);
		txtEmail.setColumns(10);

		JTextField txtUID = new JTextField();
		txtUID.setText("User ID (must be unique)");
		txtUID.setBounds(16, 169, 414, 26);
		panelRegistration.add(txtUID);
		txtUID.setColumns(10);

		JTextField txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setBounds(16, 207, 193, 26);
		panelRegistration.add(txtPassword);
		txtPassword.setColumns(10);

		JTextField txtPasswordagain = new JTextField();
		txtPasswordagain.setText("Password (again)");
		txtPasswordagain.setBounds(237, 207, 193, 26);
		panelRegistration.add(txtPasswordagain);
		txtPasswordagain.setColumns(10);

		// Button
		JButton btnRegister_1 = new JButton("Register");
		btnRegister_1.setBounds(313, 243, 117, 29);
		panelRegistration.add(btnRegister_1);
		btnRegister_1.addActionListener(e -> {
			String first = txtFirst.getText();
			String mi = txtMi.getText();
			String last = txtLast.getText();
			String email = txtEmail.getText();
			String uid = txtUID.getText();
			String pw = txtPassword.getText();
			String pw2 = txtPasswordagain.getText();
			ArrayList<String> ids = Queries.getUserIDs();
			boolean idExists = false;
			for (String id : ids) {
				if (id.equals(uid)) {
					idExists = true;
				}
			}
			if (first.isEmpty() || last.isEmpty() || email.isEmpty() || uid.isEmpty()) {
				JOptionPane.showMessageDialog(panelRegistration, "All fields except middle initial required.");

			}
			else if (!pw.contentEquals(pw2)) {
				JOptionPane.showMessageDialog(panelRegistration, "Passwords do not match!");
			}
			else if ((first).matches("[a-zA-Z]+") == false || (last).matches("[a-zA-Z]+") == false ){
				JOptionPane.showMessageDialog(panelRegistration, "Enter a valid name.");
			}
			else if ((mi.isEmpty() == false) && (mi).matches("[a-zA-Z]") == false){
					JOptionPane.showMessageDialog(panelRegistration, "Enter a valid name.");
			}
			else if (pw.length() < 8) {
				JOptionPane.showMessageDialog(panelRegistration, "Password must have at least 8 characters!");
			}
			else if (idExists) {
				JOptionPane.showMessageDialog(panelRegistration, "User ID already exists!");
			}
			else {
				Queries.addUser(uid, first, mi, last, pw, email);
				fName = first;
				lName = last;
				ID = uid;
				JOptionPane.showMessageDialog(panelRegistration, "Registration Successful!");
				panelRegistration.setVisible(false);
				makePassengerLandingPanel();
			}
		});
		return panelRegistration;
	}

	private JPanel makeViewReviewPanel() {
		return makeViewReviewPanel("rid", "ASC");
	}

	private JPanel makeViewReviewPanel(String order, String direction) {
		JPanel panelViewReviews = new JPanel();
		initPanel(panelViewReviews, "name_76504429508073");

		// Labels
		JLabel viewreviews = new JLabel(fName + " " + lName + "'s Reviews");
		viewreviews.setBounds(16, 6, 243, 16);
		panelViewReviews.add(viewreviews);

		// Table
		Object[][] rData = new Object[20][6];
		Object columnNames[] = { "ID", "Station", "Shopping", "Connection Speed", "Comment", "Approval Status" }; //rData, columnNames
		
		// Initial Data
		int revcount = populateReviewTable(order, direction, rData);
		
		//TABLE MODEL
		DefaultTableModel tableModel = new DefaultTableModel(rData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(6, 54);
		scrollPane.setSize(444, 218);
		panelViewReviews.add(scrollPane, BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        if (col == 0 && row < revcount) {
		        	int val = (Integer) table.getValueAt(row, col);
		        	panelViewReviews.setVisible(false);
					makeEditReviewPanel(val);
		        } else if (col == 1 && row < revcount) {
		        	String val = (String) table.getValueAt(row, col);
		        	panelViewReviews.setVisible(false);
		        	makeStationInfoPanel(val);
		        }
			}
		});

		// Sort Radio Buttons
		ButtonGroup rdbtnViewReviews = new ButtonGroup();

		JRadioButton rdbtnID = new JRadioButton();
		rdbtnID.addActionListener(e -> {
			panelViewReviews.setVisible(false);
			if (order.equals("rid")) {
				if (direction.equals("ASC")) {
					makeViewReviewPanel("rid", "DESC");
				} else {
					makeViewReviewPanel("rid", "ASC");
				}
			} else {
				makeViewReviewPanel("rid", "ASC");
			}
		});
		rdbtnID.setBounds(26, 34, 37, 23);
		panelViewReviews.add(rdbtnID);

		JRadioButton rdbtnStation = new JRadioButton();
		rdbtnStation.addActionListener(e -> {
			panelViewReviews.setVisible(false);
			if (order.equals("station_name")) {
				if (direction.equals("ASC")) {
					makeViewReviewPanel("station_name", "DESC");
				} else {
					makeViewReviewPanel("station_name", "ASC");
				}
			} else {
				makeViewReviewPanel("station_name", "ASC");
			}
		});
		rdbtnStation.setBounds(90, 34, 51, 23);
		panelViewReviews.add(rdbtnStation);

		JRadioButton rdbtnShopping = new JRadioButton();
		rdbtnShopping.addActionListener(e -> {
			panelViewReviews.setVisible(false);
			if (order.equals("shopping")) {
				if (direction.equals("ASC")) {
					makeViewReviewPanel("shopping", "DESC");
				} else {
					makeViewReviewPanel("shopping", "ASC");
				}
			} else {
				makeViewReviewPanel("shopping", "ASC");
			}
		});
		rdbtnShopping.setBounds(170, 34, 37, 23);
		panelViewReviews.add(rdbtnShopping);

		JRadioButton rdbtnConnectionSpeed = new JRadioButton();
		rdbtnConnectionSpeed.addActionListener(e -> {
			panelViewReviews.setVisible(false);
			if (order.equals("connection_speed")) {
				if (direction.equals("ASC")) {
					if (direction.equals("ASC")) {
						makeViewReviewPanel("connection_speed", "DESC");
					} else {
						makeViewReviewPanel("connection_speed", "ASC");
					}
				}
			} else {
				makeViewReviewPanel("connection_speed", "ASC");
			}
		});
		rdbtnConnectionSpeed.setBounds(242, 34, 28, 23);
		panelViewReviews.add(rdbtnConnectionSpeed);

		JRadioButton rdbtnApprovalStatus = new JRadioButton();
		rdbtnApprovalStatus.addActionListener(e -> {
			panelViewReviews.setVisible(false);
			if (order.equals("approval_status")) {
				if (direction.equals("ASC")) {
					makeViewReviewPanel("approval_status", "DESC");
				} else {
					makeViewReviewPanel("approval_status", "ASC");
				}
			} else {
				makeViewReviewPanel("approval_status", "ASC");
			}
		});
		rdbtnApprovalStatus.setBounds(394, 34, 28, 23);
		panelViewReviews.add(rdbtnApprovalStatus);

		rdbtnViewReviews.add(rdbtnID);
		rdbtnViewReviews.add(rdbtnStation);
		rdbtnViewReviews.add(rdbtnShopping);
		rdbtnViewReviews.add(rdbtnConnectionSpeed);
		rdbtnViewReviews.add(rdbtnApprovalStatus);

		return panelViewReviews;
	}

	private int populateReviewTable(String sort, String direction, Object[][] rData) {
		ArrayList<Object[]> revs = Queries.getReviewsByUser(ID, sort, direction, "passenger_ID", "rid", "station_name", "shopping",
				"connection_speed", "comment", "approval_status");
		for (int i = 0; i < revs.size(); i++) {
			Object[] tuple = revs.get(i); //(Integer) tuple[1]
			rData[i][0] = (Integer) tuple[1];
			rData[i][1] = (String) tuple[2];
			rData[i][2] = (Integer) tuple[3];
			rData[i][3] = (Integer) tuple[4];
			rData[i][4] = (String) tuple[5];
			rData[i][5] = (String) tuple[6];
		}
		return revs.size();
	}

	private JPanel makeEditReviewPanel(int revID) {
		JPanel panelEditReview = new JPanel();
		initPanel(panelEditReview, "name_110293474217147");
		
		Object[] revi = Queries.getReview(ID, revID, "shopping", "connection_speed", "comment", "approval_status", "station_name");
		Integer shop = (Integer) revi[0];
		Integer conx = (Integer) revi[1];
		String comment = (String) revi[2];
		String apst = (String) revi[3];
		String name = (String) revi[4];

		// Labels
		JLabel lblEditReview = new JLabel("Edit Review : " + name);
		lblEditReview.setBounds(17, 18, 270, 16);
		panelEditReview.add(lblEditReview);

		JLabel lblReviewStatus = new JLabel(apst);
		lblReviewStatus.setBounds(352, 18, 61, 16);
		panelEditReview.add(lblReviewStatus);

		JLabel lblId_1 = new JLabel("ID:");
		lblId_1.setBounds(27, 56, 61, 16);
		panelEditReview.add(lblId_1);

		JLabel lblShopping = new JLabel("Shopping:");
		lblShopping.setBounds(27, 107, 61, 16);
		panelEditReview.add(lblShopping);

		JLabel lblConnectionSpeed_1 = new JLabel("Connection Speed:");
		lblConnectionSpeed_1.setBounds(27, 145, 155, 16);
		panelEditReview.add(lblConnectionSpeed_1);

		JLabel lblComment_1 = new JLabel("Comment");
		lblComment_1.setBounds(27, 161, 61, 16);
		panelEditReview.add(lblComment_1);
		JTextField txtOldComment = new JTextField();

		txtOldComment.setText(comment);
		txtOldComment.setBounds(17, 182, 396, 50);
		panelEditReview.add(txtOldComment);
		txtOldComment.setColumns(10);

		JLabel lblidNum = new JLabel("" + revID);
		lblidNum.setBounds(242, 56, 61, 16);
		panelEditReview.add(lblidNum);

		JLabel lblRatingstars_1 = new JLabel("Rating (stars)");
		lblRatingstars_1.setBounds(239, 75, 136, 16);
		panelEditReview.add(lblRatingstars_1);

		// ComboBoxes
		String[] stars = { "--", "1", "2", "3", "4", "5" };
		
		JComboBox comboBox_1 = new JComboBox(stars);
		comboBox_1.setBounds(259, 103, 52, 27);
		comboBox_1.setSelectedIndex(shop);
		panelEditReview.add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox(stars);
		comboBox_2.setBounds(259, 141, 52, 27);
		comboBox_2.setSelectedIndex(conx);
		panelEditReview.add(comboBox_2);

		// Buttons
		JButton btnDeleteReview = new JButton("Delete Review");
		btnDeleteReview.addActionListener(e -> {
			Queries.deleteReview(ID, revID);
			panelEditReview.setVisible(false);
			makeViewReviewPanel("rid", "ASC");
		});
		btnDeleteReview.setBounds(45, 244, 117, 29);
		panelEditReview.add(btnDeleteReview);

		JButton btnSaveReview = new JButton("Save Review");
		btnSaveReview.addActionListener(e -> {
			//JOptionPane.showMessageDialog(panelEditReview, comboBox_1.getSelectedItem().toString());
			String shopping = comboBox_1.getSelectedItem().toString();
			String speed = comboBox_2.getSelectedItem().toString();
			String newCom = txtOldComment.getText();
			if (shopping.equals("--") || speed.equals("--")) {
			} else {
				Timestamp d = new Timestamp(System.currentTimeMillis());
				int shopp = Integer.parseInt(shopping);
				int conn = Integer.parseInt(speed);
				Queries.updateReview(ID, revID, shopp, conn, newCom, null, "Pending", d);
				panelEditReview.setVisible(false);
				makeViewReviewPanel("rid", "ASC");
			}
		});
		btnSaveReview.setBounds(245, 244, 117, 29);
		panelEditReview.add(btnSaveReview);
		return panelEditReview;
	}

	private JPanel makeLeaveReviewPanel() {
		JPanel panelLeaveReview = new JPanel();
		initPanel(panelLeaveReview, "name_112539121522733");

		// Labels
		JLabel lblLeaveAReview = new JLabel("Leave a Review");
		lblLeaveAReview.setBounds(35, 10, 93, 16);
		panelLeaveReview.add(lblLeaveAReview);

		JLabel lblShoppimg = new JLabel("Shopping");
		lblShoppimg.setBounds(35, 94, 59, 16);
		panelLeaveReview.add(lblShoppimg);

		JLabel lblConnectionSpeed = new JLabel("Connection Speed");
		lblConnectionSpeed.setBounds(35, 122, 113, 16);
		panelLeaveReview.add(lblConnectionSpeed);

		JLabel lblComment = new JLabel("Comment (optional)");
		lblComment.setBounds(35, 150, 125, 16);
		panelLeaveReview.add(lblComment);

		JLabel lblRatingstars = new JLabel("Rating (stars)");
		lblRatingstars.setBounds(190, 62, 83, 16);
		panelLeaveReview.add(lblRatingstars);

		// ComboBox
		ArrayList<String> stations = Queries.getStationNames();
		JComboBox comboBox = new JComboBox(stations.toArray());
		comboBox.addItem("Station");
		comboBox.setSelectedItem("Station");
		comboBox.setBounds(35, 38, 143, 27);
		panelLeaveReview.add(comboBox);

		String[] stars = { "--", "1", "2", "3", "4", "5" };
		JComboBox shoppingstars = new JComboBox(stars);
		shoppingstars.setBounds(189, 90, 64, 27);
		panelLeaveReview.add(shoppingstars);

		JComboBox csstars = new JComboBox(stars);
		csstars.setBounds(189, 118, 64, 27);
		panelLeaveReview.add(csstars);

		// Text Field
		JTextField txtComment = new JTextField();
		txtComment.setBounds(35, 173, 392, 69);
		panelLeaveReview.add(txtComment);
		txtComment.setColumns(10);

		// Button
		JButton btnLeaveReview_1 = new JButton("Submit Review");
		btnLeaveReview_1.setBounds(302, 243, 135, 29);
		panelLeaveReview.add(btnLeaveReview_1);
		btnLeaveReview_1.addActionListener(e -> {
			String sName = comboBox.getSelectedItem().toString();
			String shopping = shoppingstars.getSelectedItem().toString();
			String speed = csstars.getSelectedItem().toString();
			String comment = txtComment.getText();
			if (sName.equals("Station")) {
				JOptionPane.showMessageDialog(panelLeaveReview, "Station must be selected");
			} else if (shopping.equals("--") || speed.equals("--")) {
				JOptionPane.showMessageDialog(panelLeaveReview, "Please complete both star reviews.");
			} else {
				Timestamp d = new Timestamp(System.currentTimeMillis());
				int nextID = Queries.getReviews("rid", "rid").size() + 1;
				int shop = Integer.parseInt(shopping);
				int conn = Integer.parseInt(speed);
				Queries.addReview(ID, nextID, shop, conn, comment, null, "Pending", d, sName);
				panelLeaveReview.setVisible(false);
				makePassengerLandingPanel();
			}
		});
		return panelLeaveReview;
	}

	private JPanel makeStationInfoPanel(String sName) {
		JPanel panelStationInfo = new JPanel();
		initPanel(panelStationInfo, "name_116178640692044");
		
		Object[] stat = Queries.getStation(sName, "status", "address");
		float[] avgs = Queries.getAvgRatings(sName);
		String status = (String) stat[0];
		String addy = (String) stat[1];
		float aShop = avgs[0];
		float aConn = avgs[1];

		// Labels
		JLabel lblStationName = new JLabel(sName);
		lblStationName.setBounds(6, 6, 127, 16);
		panelStationInfo.add(lblStationName);

		JLabel lblStatus = new JLabel("Status : " + status);
		lblStatus.setBounds(324, 6, 120, 16);
		panelStationInfo.add(lblStatus);

		JLabel lblAddress = new JLabel("Address : " + addy);
		lblAddress.setBounds(27, 34, 396, 16);
		panelStationInfo.add(lblAddress);

		JLabel lblLinesLines = new JLabel("Lines : " + "");
		lblLinesLines.setBounds(29, 65, 255, 16);
		panelStationInfo.add(lblLinesLines);
		
		makeLinesList(sName, panelStationInfo);

		JLabel lblAverageShopping = new JLabel("Average Shopping : " + ((aShop < 0) ? "N/A" : aShop));
		lblAverageShopping.setBounds(6, 93, 234, 16);
		panelStationInfo.add(lblAverageShopping);

		JLabel lblAverageConnectionSpeed = new JLabel("Average Connection Speed : " + ((aConn < 0) ? "N/A" : aConn));
		lblAverageConnectionSpeed.setBounds(217, 93, 227, 16);
		panelStationInfo.add(lblAverageConnectionSpeed);

		JLabel lblReviews = new JLabel("Reviews");
		lblReviews.setBounds(27, 124, 61, 16);
		panelStationInfo.add(lblReviews);

		// Table
		Object rowData[][] = new Object[20][4];
		ArrayList<Object[]> reviews = Queries.getReviewsByStation(sName, "passenger_ID", "shopping", "connection_speed", "comment");
		for (int i = 0; i < reviews.size(); i++) {
			Object[] tuple = reviews.get(i);
			rowData[i][0] = (String) tuple[0];
			rowData[i][1] = (Integer) tuple[1];
			rowData[i][2] = (Integer) tuple[2];
			rowData[i][3] = (String) tuple[3];
		}
		
		Object columnNames[] = { "User", "Shopping", "Connection Speed", "Comment" };
		//BELOW MODEL MAKES TABLE NOT EDITABLE
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(6, 142);
		scrollPane.setSize(444, 130);
		panelStationInfo.add(scrollPane, BorderLayout.CENTER);
		return panelStationInfo;
	}
	
	private void makeLinesList(String station, JPanel panel) {
		//LINES LIST
		ArrayList<String> lines = Queries.getLinesFromStation(station);
		JList<String> lisLines = new JList(lines.toArray());
		lisLines.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lisLines.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lisLines.setVisibleRowCount(-1);
		lisLines.setBounds(65, 65, 125, 16);
		panel.add(lisLines);
		lisLines.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String val = lisLines.getSelectedValue();
		        if (val != null) {
		        	panel.setVisible(false);
		        	if (!isAdmin) {
		        		makeLineSummaryPanel(val, "order_number", "ASC");
		        	} else {
		        		makeLineSummaryADPanel(val, "order_number", "ASC");
		        	}
		        }
			}
		});
	}
	
	private int populateStationTable(String line, Object[][] rData, String sort, String direction) {
		ArrayList<Object[]> stations = Queries.getStationsFromLine(line, sort, direction);
		for (int i = 0; i < stations.size(); i++) {
			Object[] tuple = stations.get(i);
			rData[i][0] = (String) tuple[0];
			rData[i][1] = (Integer) tuple[1];
		}
		return stations.size();
	}

	private JPanel makeLineSummaryPanel(String lnum, String order, String direction) {
		JPanel panelLineSummary = new JPanel();
		initPanel(panelLineSummary, "name_116847732626867");
		
		ArrayList<Object[]> stations = Queries.getStationsFromLine(lnum, "order_number", "ASC");
		long numstops = Queries.getNumStops(lnum);

		// Labels
		JLabel lblLineLine = new JLabel("Line : " + lnum);
		lblLineLine.setBounds(6, 6, 145, 16);
		panelLineSummary.add(lblLineLine);

		JLabel lblStops = new JLabel(numstops + " Stops");
		lblStops.setBounds(366, 6, 61, 16);
		panelLineSummary.add(lblStops);

		// Table
		Object rowData[][] = new Object[20][2];
		int numstats = populateStationTable(lnum, rowData, order, direction);
		Object columnNames[] = { "Station", "Order" };
		//BELOW MODEL MAKES TABLE NOT EDITABLE
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(6, 50);
		scrollPane.setSize(434, 2228);
		panelLineSummary.add(scrollPane, BorderLayout.CENTER);
		
		//Clickable Stations
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        if (col == 0 && row < numstats) {
		        	String val = (String) table.getValueAt(row, col);
		        	panelLineSummary.setVisible(false);
					makeStationInfoPanel(val);
		        }
			}
		});

		// Radio Buttons
		ButtonGroup rdbtnLineSummary = new ButtonGroup();

		JRadioButton rdbtnStationLS = new JRadioButton("");
		rdbtnStationLS.setBounds(93, 26, 46, 23);
		panelLineSummary.add(rdbtnStationLS);
		rdbtnStationLS.addActionListener(e -> {
			panelLineSummary.setVisible(false);
			if (order.equals("station_name")) {
				if (direction.equals("ASC")) {
					makeLineSummaryPanel(lnum, "station_name", "DESC");
				} else {
					makeLineSummaryPanel(lnum, "station_name", "ASC");
				}
			} else {
				makeLineSummaryPanel(lnum, "station_name", "ASC");
			}
		});

		JRadioButton rdbtnOrder = new JRadioButton("");
		rdbtnOrder.setBounds(275, 26, 33, 23);
		panelLineSummary.add(rdbtnOrder);
		rdbtnOrder.addActionListener(e -> {
			panelLineSummary.setVisible(false);
			if (order.equals("order_number")) {
				if (direction.equals("ASC")) {
					makeLineSummaryPanel(lnum, "order_number", "DESC");
				} else {
					makeLineSummaryPanel(lnum, "order_number", "ASC");
				}
			} else {
				makeLineSummaryPanel(lnum, "order_number", "ASC");
			}
		});

		rdbtnLineSummary.add(rdbtnStationLS);
		rdbtnLineSummary.add(rdbtnOrder);

		return panelLineSummary;
	}

	private JPanel makeBuyCardPanel() {
		JPanel panelBuyCard = new JPanel();
		initPanel(panelBuyCard, "name_118269236130479");

		// Labels
		JLabel lblBuyCard = new JLabel("Buy Card");
		lblBuyCard.setBounds(22, 6, 61, 16);
		panelBuyCard.add(lblBuyCard);

		// Buttons
		JButton btnTmes = new JButton("T-mes");
		btnTmes.setBounds(40, 58, 117, 77);
		panelBuyCard.add(btnTmes);
		btnTmes.addActionListener(e -> {
			JOptionPane.showMessageDialog(panelBuyCard, "T-mes Card Purchased");
			buyTmesCard();
			panelBuyCard.setVisible(false);
			if (Queries.isAdmin(ID)) {
				makeAdminLandingPanel();	
			}
			else {
				makePassengerLandingPanel();
			}
		});

		JButton btnT = new JButton("T-10");
		btnT.setBounds(245, 58, 117, 71);
		panelBuyCard.add(btnT);
		btnT.addActionListener(e -> {
			JOptionPane.showMessageDialog(panelBuyCard, "T-10 Card Purchased");
			buyT10Card();
			panelBuyCard.setVisible(false);
			if (Queries.isAdmin(ID)) {
				makeAdminLandingPanel();	
			}
			else {
				makePassengerLandingPanel();
			}
		});

		JButton btnT_1 = new JButton("T-50/30");
		btnT_1.setBounds(40, 170, 117, 71);
		panelBuyCard.add(btnT_1);
		btnT_1.addActionListener(e -> {
			JOptionPane.showMessageDialog(panelBuyCard, "T-50/30 Card Purchased");
			buyT5030Card();
			panelBuyCard.setVisible(false);
			if (Queries.isAdmin(ID)) {
				makeAdminLandingPanel();	
			}
			else {
				makePassengerLandingPanel();
			}
		});

		JButton btnTjove = new JButton("T-jove");
		btnTjove.setBounds(245, 170, 117, 77);
		panelBuyCard.add(btnTjove);
		btnTjove.addActionListener(e -> {
			JOptionPane.showMessageDialog(panelBuyCard, "T-jove Card Purchased");
			buyTjoveCard();
			panelBuyCard.setVisible(false);
			if (Queries.isAdmin(ID)) {
				makeAdminLandingPanel();	
			}
			else {
				makePassengerLandingPanel();
			}
		});

		return panelBuyCard;
	}


	public java.sql.Date convertUtilToSql(java.util.Date uDate){
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public void buyTmesCard() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentts = Queries.getCurrentTimestamp2();
		calendar.setTimeInMillis(currentts.getTime());
		calendar.add(Calendar.MONTH, 1);
		Timestamp expTimestamp = new Timestamp(calendar.getTime().getTime());
		Date expDate = new Date(expTimestamp.getTime());
        java.sql.Date sDate = convertUtilToSql(expDate);
		Queries.buyCard(ID, "T-mes", currentts, null, sDate);
		//done
	}

	public void buyT10Card() {
		Queries.buyCard(ID, "T-10", Queries.getCurrentTimestamp2(), 10, null);
		//done
	}

	public void buyT5030Card() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentts = Queries.getCurrentTimestamp2();
		calendar.setTimeInMillis(currentts.getTime());
		calendar.add(Calendar.DAY_OF_WEEK, 30);
		Timestamp expTimestamp = new Timestamp(calendar.getTime().getTime());
		Date expDate = new Date(expTimestamp.getTime());
        java.sql.Date sDate = convertUtilToSql(expDate);
		Queries.buyCard(ID, "T-50/30", currentts, 50, sDate);
		//done
	}

	public void buyTjoveCard() {
		Calendar calendar = Calendar.getInstance();
		Timestamp currentts = Queries.getCurrentTimestamp2();
		calendar.setTimeInMillis(currentts.getTime());
		calendar.add(Calendar.DAY_OF_WEEK, 90);
		Timestamp expTimestamp = new Timestamp(calendar.getTime().getTime());
		Date expDate = new Date(expTimestamp.getTime());
        java.sql.Date sDate = convertUtilToSql(expDate);
		Queries.buyCard(ID, "T-jove", currentts, null, sDate);
		//done

	}
	
	
	//DONE
	private JPanel makeEditProfilePanel() {
		JPanel panelEditProfile = new JPanel();
		initPanel(panelEditProfile, "name_117153285834010");

		// Labels
		JLabel lblEditProfile = new JLabel("Edit Profile");
		lblEditProfile.setBounds(17, 6, 118, 16);
		panelEditProfile.add(lblEditProfile);

		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(17, 23, 61, 16);
		panelEditProfile.add(lblName_1);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(17, 84, 61, 16);
		panelEditProfile.add(lblEmail);

		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setBounds(27, 165, 61, 16);
		panelEditProfile.add(lblPassword_1);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(270, 165, 130, 16);
		panelEditProfile.add(lblConfirmPassword);

		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setBounds(17, 127, 61, 16);
		panelEditProfile.add(lblUserId);

		// Text Fields
		JTextField txtFirstName = new JTextField(fName);
		txtFirstName.setBounds(5, 46, 130, 26);
		panelEditProfile.add(txtFirstName);
		txtFirstName.setColumns(10);

		JTextField txtMiddlein = new JTextField((String)Queries.getUserInfo(ID, "minit")[0]);
		txtMiddlein.setBounds(159, 46, 130, 26);
		panelEditProfile.add(txtMiddlein);
		txtMiddlein.setColumns(10);

		JTextField txtLastname = new JTextField(lName);
		txtLastname.setBounds(301, 46, 130, 26);
		panelEditProfile.add(txtLastname);
		txtLastname.setColumns(10);

		JTextField txtEmail_1 = new JTextField((String)Queries.getUserInfo(ID,"passenger_email")[0]);
		txtEmail_1.setBounds(90, 79, 130, 26);
		panelEditProfile.add(txtEmail_1);
		txtEmail_1.setColumns(10);

		JTextField txtUserid = new JTextField(ID);
		txtUserid.setBounds(100, 122, 130, 26);
		panelEditProfile.add(txtUserid);
		txtUserid.setColumns(10);

		JTextField txtPassword_1 = new JTextField((String)Queries.getUserInfo(ID, "password")[0]);
		txtPassword_1.setBounds(27, 193, 130, 26);
		panelEditProfile.add(txtPassword_1);
		txtPassword_1.setColumns(10);

		JTextField txtPassword_2 = new JTextField((String)Queries.getUserInfo(ID, "password")[0]);
		txtPassword_2.setBounds(253, 188, 130, 26);
		panelEditProfile.add(txtPassword_2);
		txtPassword_2.setColumns(10);

		// Buttons
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(e -> {
			Queries.deleteUser(ID);
			panelEditProfile.setVisible(false);
			makeLoginPanel();
		});
		btnDelete.setBounds(34, 229, 117, 29);
		panelEditProfile.add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(e -> {
			String first = txtFirstName.getText();
			String mi = txtMiddlein.getText();
			String last = txtLastname.getText();
			String email = txtEmail_1.getText();
			String uid = txtUserid.getText();
			String pw = txtPassword_1.getText();
			String pw2 = txtPassword_2.getText();
			ArrayList<String> ids = Queries.getUserIDs();
			boolean idExists = false;
			for (String id : ids) {
				if (id.equals(uid)) {
					idExists = true;
				}
			}
			if (first.isEmpty() || last.isEmpty() || email.isEmpty() || uid.isEmpty()) {
				JOptionPane.showMessageDialog(panelEditProfile, "All fields except middle initial required.");
			}
			else if ((first).matches("[a-zA-Z]+") == false || (last).matches("[a-zA-Z]+") == false ){
				JOptionPane.showMessageDialog(panelEditProfile, "Enter a valid name.");
			}
			else if (mi.isEmpty() == false && ((mi).matches("[a-zA-Z]") == false)) {
					JOptionPane.showMessageDialog(panelEditProfile, "Enter a valid name.");
			}
			else if (pw.contentEquals(pw2) == false) {
				JOptionPane.showMessageDialog(panelEditProfile, "Passwords do not match!");
			}
			else if (pw.length() < 8 && (pw.equals((String)Queries.getUserInfo(ID, "password")[0]))== false) {
				JOptionPane.showMessageDialog(panelEditProfile, "Password must have at least 8 characters!");
			}
			else if (idExists && (ID.equals(uid) == false)) {
				JOptionPane.showMessageDialog(panelEditProfile, "User ID already exists!");
			}
			else{
				Queries.updateUser(ID, uid, first, mi, last, pw, email);
				fName = first;
				lName = last;
				ID = uid;
				JOptionPane.showMessageDialog(panelEditProfile, "Update Successful!");
				panelEditProfile.setVisible(false);
				makePassengerLandingPanel();
			}
		});
		btnUpdate.setBounds(249, 229, 117, 29);
		panelEditProfile.add(btnUpdate);
		return panelEditProfile;
	}

	private int populateTripTable(String sort, String direction, Object[][] rData) {
		ArrayList<Object[]> trips = Queries.getAllTrips(ID, sort, direction, "start_date_time", "end_date_time", "card_type", "from_station_name", "to_station_name");
		for (int i = 0; i < trips.size(); i++) {
			Object[] tuple = trips.get(i);
			rData[i][0] = (Timestamp) tuple[0];
			//checking to see if there's not an endtime yet
			if(tuple[1] == null) {
				rData[i][1] = "N/A";
			} else {
				rData[i][1] = (Timestamp) tuple[1];
			}
			rData[i][2] = (String) tuple[2];
			rData[i][3] = (String) tuple[3];
			//checking to see if there's no tostation yet
			if(tuple[4] == null) {
				rData[i][4] = "N/A";
			} else {
				rData[i][4] = (String) tuple[4];
			}
		}
		return trips.size();
	}

	private JPanel makeViewTripsPanel(String order, String direction) {
		JPanel panelViewTrips = new JPanel();
		initPanel(panelViewTrips, "name_118708450389034");

		// Labels
		JLabel lblMyTrips = new JLabel("My Trips");
		lblMyTrips.setBounds(6, 6, 61, 16);
		panelViewTrips.add(lblMyTrips);

		// Table
		Object rowData[][] = new Object[20][5];
		int numTrips = populateTripTable(order, direction, rowData);
		Object columnNames[] = { "Start DateTime", "End DateTime", "CardUsed", "From", "To" };
		//BELOW MODEL MAKES TABLE NOT EDITABLE
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		ButtonGroup rdbtnMyTrips = new ButtonGroup();
		
		JRadioButton rdbtnStartDate = new JRadioButton();
		rdbtnStartDate.addActionListener(e -> {
			panelViewTrips.setVisible(false);
			if (order.equals("start_date_time")) {
				if (direction.equals("ASC")) {
					makeViewTripsPanel("start_date_time", "DESC");
				} else {
					makeViewTripsPanel("start_date_time", "ASC");
				}
			} else {
				makeViewTripsPanel("start_date_time", "ASC");
			}
		});
		rdbtnStartDate.setBounds(40,25,37,23);
		panelViewTrips.add(rdbtnStartDate);
		
		JRadioButton rdbtnFrom = new JRadioButton();
		rdbtnFrom.addActionListener(e -> {
			panelViewTrips.setVisible(false);
			if (order.equals("from_station_name")) {
				if (direction.equals("ASC")) {
					makeViewTripsPanel("from_station_name", "DESC");
				} else {
					makeViewTripsPanel("from_station_name", "ASC");
				}
			} else {
				makeViewTripsPanel("from_station_name", "ASC");
			}
		});
		rdbtnFrom.setBounds(285,25,37,23);
		panelViewTrips.add(rdbtnFrom);
		
		JRadioButton rdbtnTo = new JRadioButton();
		rdbtnTo.addActionListener(e -> {
			panelViewTrips.setVisible(false);
			if (order.equals("to_station_name")) {
				if (direction.equals("ASC")) {
					makeViewTripsPanel("to_station_name", "DESC");
				} else {
					makeViewTripsPanel("to_station_name", "ASC");
				}
			} else {
				makeViewTripsPanel("to_station_name", "ASC");
			}
		});
		rdbtnTo.setBounds(375,25,37,23);
		panelViewTrips.add(rdbtnTo);
		
		
		rdbtnMyTrips.add(rdbtnStartDate);
		rdbtnMyTrips.add(rdbtnFrom);
		rdbtnMyTrips.add(rdbtnTo);

		
		
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(6, 50);
		scrollPane.setSize(444, 249);
		panelViewTrips.add(scrollPane, BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        if (col >= 0 && col <= 2 && row < numTrips) {
		        	if (col==0 && table.getValueAt(row,1).equals("N/A")) {
		        		String type = (String)table.getValueAt(row,2);
		        		Timestamp startdatetime = (Timestamp)table.getValueAt(row, col);
		        		String startStation = (String)table.getValueAt(row, 3);		        		
		        		panelViewTrips.setVisible(false);
		        		makeUpdateTripPanel(startdatetime, type, startStation);
		        	}
		        	//UPDATE TRIP STUFF
		        } else if (row < numTrips) {
		        	String val = (String) table.getValueAt(row, col);
		        	if (!val.equals("N/A")) {
			        	panelViewTrips.setVisible(false);
			        	if (!isAdmin) {
			        		makeStationInfoPanel(val);
			        	} else {
			        		makeStationInfoADPanel(val);
			        	}
					}
		        }
			}
		});

		return panelViewTrips;
	}

	private JPanel makeGoOnATripPanel() {
		JPanel panelGoOnATrip = new JPanel();
		initPanel(panelGoOnATrip, "name_118392332897193");

		// Labels
		JLabel lblGoonatrip = new JLabel("Go on a Trip");
		lblGoonatrip.setBounds(17, 6, 187, 16);
		panelGoOnATrip.add(lblGoonatrip);

		JLabel lblStartStation = new JLabel("Start Station");
		lblStartStation.setBounds(60, 50, 144, 16);
		panelGoOnATrip.add(lblStartStation);

		JLabel lblCardUsed = new JLabel("Card Used");
		lblCardUsed.setBounds(60, 151, 109, 16);
		panelGoOnATrip.add(lblCardUsed);

		// ComboBoxes
		ArrayList<String> stations = Queries.getStationNames();
		JComboBox comboBoxStation = new JComboBox(stations.toArray());
		comboBoxStation.setBounds(200, 46, 250, 25);
		panelGoOnATrip.add(comboBoxStation);

		ArrayList<Object[]> validCards = Queries.getUserValidCards(ID, "type", "purchase_date_time");
		ArrayList<String> validC = new ArrayList<>();
		
		for (Object[] c: validCards) {
			validC.add(((String) c[0]) + " (" + ((Timestamp) c[1]).toString() + ")");
		}
		JComboBox comboBoxCard = new JComboBox(validC.toArray());
		comboBoxCard.setBounds(200, 150, 250, 25);
		panelGoOnATrip.add(comboBoxCard);
		//JOptionPane.showMessageDialog(panelGoOnATrip, comboBoxCard.getItemAt(0));
		//THIS RETURNS OBJECTS.... NEED A TO STRING METHOD FOR VALID CARDS

		// Button
		JButton btnEmbark = new JButton("Embark");
		btnEmbark.addActionListener(e -> {
			if (validC.size() ==0) {
				JOptionPane.showMessageDialog(panelGoOnATrip, "No valid cards!");
			}
			else {
			String startStation = comboBoxStation.getSelectedItem().toString();
			String tripTicket = comboBoxCard.getSelectedItem().toString();
			String[] splitString = tripTicket.split("\\s+");
			String tripTicketType = splitString[0];
			String tripTicketPurchaseDate = (splitString[1].substring(1,11)+" "+splitString[2].substring(0,10));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date parsedDate = Queries.getCurrentTimestamp2();
			try {
				parsedDate = dateFormat.parse(tripTicketPurchaseDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Timestamp timestampPD = new java.sql.Timestamp(parsedDate.getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestampPD.getTime());
			Timestamp finalTimestamp = new Timestamp(calendar.getTime().getTime());
			SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
			String tsstring  = dateFormat5.format(finalTimestamp);
			Queries.embark(ID, tripTicketType, tsstring, Queries.getCurrentTimestamp2(), null, startStation, null);
			JOptionPane.showMessageDialog(panelGoOnATrip, "Embark successful!");
			panelGoOnATrip.setVisible(false);
			if (Queries.isAdmin(ID)) {
				makeAdminLandingPanel();	
			}
			else {
				makePassengerLandingPanel();
			}
		}
		});
		btnEmbark.setBounds(235, 203, 153, 55);
		panelGoOnATrip.add(btnEmbark);

		return panelGoOnATrip;
		
	}

	

	private JPanel makeUpdateTripPanel(Timestamp startDateTime, String type, String startStation) {
		ArrayList<Object[]> purchaseDate = Queries.getTripsFromStartTime(startDateTime, "card_purchase_date_time");
		Timestamp purchaseDate2 = (Timestamp)(purchaseDate.get(0)[0]);
		
		JPanel panelUpdateTrip = new JPanel();
		initPanel(panelUpdateTrip, "name_118936376777311");

		// Labels
		JLabel lblUpdateTrip = new JLabel("Update Trip");
		lblUpdateTrip.setBounds(6, 6, 142, 16);
		panelUpdateTrip.add(lblUpdateTrip);

		JLabel lblStartStationUpdate = new JLabel("Start Station    :     "+ startStation);
		lblStartStationUpdate.setBounds(40, 48, 223, 16);
		panelUpdateTrip.add(lblStartStationUpdate);

		
		JLabel lblEndStation = new JLabel("End Station");
		lblEndStation.setBounds(40, 106, 108, 16);
		panelUpdateTrip.add(lblEndStation);

		JLabel lblCardUsedUpdate = new JLabel("Card Used : "+ type+ " (" + purchaseDate2 + ")");
		lblCardUsedUpdate.setBounds(40, 180, 330, 16);
		panelUpdateTrip.add(lblCardUsedUpdate);

		ArrayList<String> stations = Queries.getStationNames();
		JComboBox comboBoxEndStation = new JComboBox(stations.toArray());
		comboBoxEndStation.setBounds(237, 102, 150, 27);
		panelUpdateTrip.add(comboBoxEndStation);

		// Button
		JButton btnUpdateTrip = new JButton("Update");
		btnUpdateTrip.addActionListener(e -> {
			Queries.updateTrip(ID,type,purchaseDate2, startDateTime, Queries.getCurrentTimestamp(), comboBoxEndStation.getSelectedItem().toString());
			JOptionPane.showMessageDialog(panelUpdateTrip, "Update successful!");
			panelUpdateTrip.setVisible(false);
			if (Queries.isAdmin(ID)) {
				makeAdminLandingPanel();	
			}
			else {
				makePassengerLandingPanel();
			}
		
		});
		btnUpdateTrip.setBounds(263, 222, 117, 50);
		panelUpdateTrip.add(btnUpdateTrip);

		

		return panelUpdateTrip;
	}

	private JPanel makeAdminLandingPanel() {
		JPanel panelAdminLanding = new JPanel();
		initPanel(panelAdminLanding, "name_119110396471857");

		// Labels
		JLabel lblWelcomeName = new JLabel("Welcome " + fName + " " + lName);
		lblWelcomeName.setBounds(6, 6, 297, 16);
		panelAdminLanding.add(lblWelcomeName);

		JLabel lblAdmin = new JLabel("Admin");
		lblAdmin.setBounds(365, 6, 61, 16);
		panelAdminLanding.add(lblAdmin);

		// Buttons
		JButton btnViewTripsAD = new JButton("View Trips");
		btnViewTripsAD.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makeViewTripsPanel("start_date_time", "ASC");
		});
		btnViewTripsAD.setBounds(31, 34, 117, 51);
		panelAdminLanding.add(btnViewTripsAD);

		JButton btnBuyCard = new JButton("Buy Card");
		btnBuyCard.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makeBuyCardPanel();
		});
		btnBuyCard.setBounds(31, 95, 117, 51);
		panelAdminLanding.add(btnBuyCard);

		JButton btnGoOnATrip = new JButton("Go on Trip");
		btnGoOnATrip.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makeGoOnATripPanel();
		});
		btnGoOnATrip.setBounds(31, 158, 117, 51);
		panelAdminLanding.add(btnGoOnATrip);

		JButton btnReviewPassengerReviews = new JButton("Review Passenger Reviews");
		btnReviewPassengerReviews.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makePendingReviewsPanel();
		});
		btnReviewPassengerReviews.setBounds(31, 225, 194, 47);
		panelAdminLanding.add(btnReviewPassengerReviews);

		JButton btnEditProfile = new JButton("Edit Profile");
		btnEditProfile.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makeEditProfileADPanel();
		});
		btnEditProfile.setBounds(276, 30, 117, 59);
		panelAdminLanding.add(btnEditProfile);

		JButton btnAddStation = new JButton("Add Station");
		btnAddStation.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makeAddStationPanel();
		});
		btnAddStation.setBounds(276, 107, 117, 59);
		panelAdminLanding.add(btnAddStation);

		JButton btnAddLine = new JButton("Add Line");
		btnAddLine.addActionListener(e -> {
			panelAdminLanding.setVisible(false);
			makeAddLinePanel();
		});
		btnAddLine.setBounds(276, 178, 117, 51);
		panelAdminLanding.add(btnAddLine);

		return panelAdminLanding;
	}

	private JPanel makePendingReviewsPanel() {
		JPanel panelPendingReviews = new JPanel();
		initPanel(panelPendingReviews, "name_119320537598432");

		// Label
		JLabel lblPendingReviews = new JLabel("Pending Reviews");
		lblPendingReviews.setBounds(6, 6, 128, 16);
		panelPendingReviews.add(lblPendingReviews);

		Object rData[][] = new Object[20][7];
		Object columnNames[] = { "User", "Station", "Shopping", "Connection Speed", "Comment", "Approve", "Reject"};
		
		ArrayList<Object[]> reviews = populatePendingReviewTable(rData);
		int revcount = reviews.size();
		
		// Table
		//BELOW MODEL MAKES TABLE NOT EDITABLE
		DefaultTableModel tableModel = new DefaultTableModel(rData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(10, 23);
		scrollPane.setSize(444, 249);
		panelPendingReviews.add(scrollPane, BorderLayout.CENTER);
		
		
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        if (row < revcount) {
		        	if (col == 5) {
		        		int rid = (Integer) reviews.get(row)[0];
		        		String userID = (String) rData[row][0];
		        		Queries.updateReviewStatus(userID, rid, ID, "Approved");
		        		panelPendingReviews.setVisible(false);
		        		makePendingReviewsPanel();
		        	} else if (col == 6) {
		        		int rid = (Integer) reviews.get(row)[0];
		        		String userID = (String) rData[row][0];
		        		Queries.updateReviewStatus(userID, rid, ID, "Rejected");
		        		panelPendingReviews.setVisible(false);
		        		makePendingReviewsPanel();
		        	}
		        }
			}
		});
		

		return panelPendingReviews;
	}
	
	private ArrayList<Object[]> populatePendingReviewTable(Object[][] rData) {
		ArrayList<Object[]> revs = Queries.getPendingReviews("rid", "passenger_ID", "station_name", "shopping", "connection_speed", "comment");
		for (int i = 0; i < revs.size(); i++) {
			Object[] tuple = revs.get(i); //(Integer) tuple[1]
			rData[i][0] = (String) tuple[1];
			rData[i][1] = (String) tuple[2];
			rData[i][2] = (Integer) tuple[3];
			rData[i][3] = (Integer) tuple[4];
			rData[i][4] = (String) tuple[5];
			rData[i][5] = "Approve";
			rData[i][6] = "Reject";
		}
		return revs;
	}

	
	//DONEEEEE
	private JPanel makeEditProfileADPanel() {
		JPanel panelEditProfileAD = new JPanel();
		initPanel(panelEditProfileAD, "name_119594530220165");

		// Labels
		JLabel lblEditProfile_1 = new JLabel("Edit Profile");
		lblEditProfile_1.setBounds(6, 6, 124, 16);
		panelEditProfileAD.add(lblEditProfile_1);

		JLabel lblAdmin_1 = new JLabel("Admin");
		lblAdmin_1.setBounds(383, 6, 61, 16);
		panelEditProfileAD.add(lblAdmin_1);

		JLabel lblName_2 = new JLabel("Name");
		lblName_2.setBounds(6, 38, 61, 16);
		panelEditProfileAD.add(lblName_2);

		JLabel lblPassword_2 = new JLabel("Password");
		lblPassword_2.setBounds(6, 147, 61, 16);
		panelEditProfileAD.add(lblPassword_2);

		JLabel lblConfirmPassword_1 = new JLabel("Confirm Password");
		lblConfirmPassword_1.setBounds(218, 147, 146, 16);
		panelEditProfileAD.add(lblConfirmPassword_1);

		JLabel lblUserId_1 = new JLabel("User ID");
		lblUserId_1.setBounds(6, 95, 61, 16);
		panelEditProfileAD.add(lblUserId_1);

		// Text Fields
		JTextField txtFirstname = new JTextField(fName);
		txtFirstname.setBounds(16, 57, 130, 26);
		panelEditProfileAD.add(txtFirstname);
		txtFirstname.setColumns(10);

		JTextField txtMiddlein_1 = new JTextField((String)Queries.getUserInfo(ID,"minit")[0]);
		txtMiddlein_1.setBounds(158, 57, 130, 26);
		panelEditProfileAD.add(txtMiddlein_1);
		txtMiddlein_1.setColumns(10);

		JTextField txtLastname_1 = new JTextField(lName);
		txtLastname_1.setBounds(314, 57, 130, 26);
		panelEditProfileAD.add(txtLastname_1);
		txtLastname_1.setColumns(10);

		JTextField txtUserid_1 = new JTextField(ID);
		txtUserid_1.setBounds(103, 95, 130, 26);
		panelEditProfileAD.add(txtUserid_1);
		txtUserid_1.setColumns(10);

		JTextField txtPassword_3 = new JTextField((String)Queries.getUserInfo(ID,"password")[0]);
		txtPassword_3.setBounds(218, 169, 130, 26);
		panelEditProfileAD.add(txtPassword_3);
		txtPassword_3.setColumns(10);

		JTextField txtPassword_4 = new JTextField((String)Queries.getUserInfo(ID,"password")[0]);
		txtPassword_4.setBounds(6, 169, 130, 26);
		panelEditProfileAD.add(txtPassword_4);
		txtPassword_4.setColumns(10);

		// Buttons

		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.addActionListener(e -> {
			Queries.deleteAdminStationsAndLines(ID);
			Queries.deleteAdmin(ID);
			Queries.deleteUser(ID);
			panelEditProfileAD.setVisible(false);
			makeLoginPanel();
		});
		btnDelete_1.setBounds(33, 218, 117, 54);
		panelEditProfileAD.add(btnDelete_1);

		JButton btnUpdate_1 = new JButton("Update");
		btnUpdate_1.addActionListener(e -> {
			String first = txtFirstname.getText();
			String mi = txtMiddlein_1.getText();
			String last = txtLastname_1.getText();
			String uid = txtUserid_1.getText();
			String pw = txtPassword_3.getText();
			String pw2 = txtPassword_4.getText();
			String oldpw = (String)Queries.getUserInfo(ID,"password")[0];
			ArrayList<String> ids = Queries.getUserIDs();
			boolean idExists = false;
			for (String id : ids) {
				if (id.equals(uid)) {
					idExists = true;
				}
			}
			if (pw.contentEquals(pw2) == false) {
				JOptionPane.showMessageDialog(panelEditProfileAD, "Passwords do not match!");
			}
			else if (pw.length() < 8 && !(oldpw.equals(pw))) {
				JOptionPane.showMessageDialog(panelEditProfileAD, "Password must have at least 8 characters!");
			}
			else if (idExists && !(ID.equals(uid))) {
				JOptionPane.showMessageDialog(panelEditProfileAD, "User ID already exists!");
			}
			else if ((first).matches("[a-zA-Z]+") == false || (last).matches("[a-zA-Z]+") == false ){
				JOptionPane.showMessageDialog(panelEditProfileAD, "Enter a valid name.");
			}
			else if (mi.isEmpty() == false && ((mi).matches("[a-zA-Z]") == false)) {
				JOptionPane.showMessageDialog(panelEditProfileAD, "Enter a valid name.");
			}

			else if (mi.isEmpty() == false && ((mi).matches("[a-zA-Z]") == false)) {
				JOptionPane.showMessageDialog(panelEditProfileAD, "Enter a valid name.");
			}
			else {
				Queries.updateUser(ID, uid, first, mi, last, pw, null);
				Queries.updateAdminID(ID, uid);
				fName = first;
				lName = last;
				ID = uid;
				JOptionPane.showMessageDialog(panelEditProfileAD, "Update Successful!");
				panelEditProfileAD.setVisible(false);
				makeAdminLandingPanel();
			}
		});
		btnUpdate_1.setBounds(228, 218, 117, 54);
		panelEditProfileAD.add(btnUpdate_1);

		return panelEditProfileAD;
	}

	private JPanel makeAddStationPanel() {
		JPanel panelAddStation = new JPanel();
		initPanel(panelAddStation, "name_120015002666853");

		// Labels
		JLabel lblAddStation = new JLabel("Add Station");
		lblAddStation.setBounds(6, 6, 97, 16);
		panelAddStation.add(lblAddStation);

		// TextFields
		JTextField txtStationName = new JTextField();
		txtStationName.setText("Station Name");
		txtStationName.setBounds(6, 26, 130, 26);
		panelAddStation.add(txtStationName);
		txtStationName.setColumns(10);

		JTextField txtStreetAddress = new JTextField();
		txtStreetAddress.setText("Street Address");
		txtStreetAddress.setBounds(266, 26, 130, 26);
		panelAddStation.add(txtStreetAddress);
		txtStreetAddress.setColumns(10);

		JTextField txtCity = new JTextField();
		txtCity.setText("City");
		txtCity.setBounds(6, 64, 130, 26);
		panelAddStation.add(txtCity);
		txtCity.setColumns(10);

		JTextField txtState = new JTextField();
		txtState.setText("State");
		txtState.setBounds(164, 64, 130, 26);
		panelAddStation.add(txtState);
		txtState.setColumns(10);

		JTextField txtPostalCode = new JTextField();
		txtPostalCode.setText("Postal/Zip Code");
		txtPostalCode.setBounds(314, 64, 130, 26);
		panelAddStation.add(txtPostalCode);
		txtPostalCode.setColumns(10);

		JTextField txtOrder = new JTextField();
		txtOrder.setText("Order");
		txtOrder.setBounds(134, 102, 130, 26);
		panelAddStation.add(txtOrder);
		txtOrder.setColumns(10);

		// ComboBox
		ArrayList<String> lineNames = Queries.getLineNames();
		JComboBox comboBox_3 = new JComboBox(lineNames.toArray());
		comboBox_3.setBounds(16, 102, 75, 27);
		panelAddStation.add(comboBox_3);
		

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Line");
		model.addColumn("Order");
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(26, 140);
		scrollPane.setSize(353, 100);
		panelAddStation.add(scrollPane, BorderLayout.CENTER);
		ArrayList<String> addinglines = new ArrayList<String>();
		ArrayList<String> addingstations = new ArrayList<String>();
		
		// Buttons
		JButton btnAddLine_1 = new JButton("Add Line");
		btnAddLine_1.addActionListener(e -> {
			String selectedLine = comboBox_3.getSelectedItem().toString();
			String selectedOrder = txtOrder.getText();
			ArrayList<Integer> linenums = Queries.getLineOrderNumbers(selectedLine);
			String newStationName = txtStationName.getText();
			if (Queries.getStationNames().contains(newStationName)) {
				JOptionPane.showMessageDialog(panelAddStation, "Station Name not Unique.");
			}
			else if (isNumeric(selectedOrder) == false) {
				JOptionPane.showMessageDialog(panelAddStation, "Invalid Order Number");
			}
			else if (linenums.contains((Integer.valueOf(selectedOrder))) || Integer.valueOf(selectedOrder)<1){
				JOptionPane.showMessageDialog(panelAddStation, "Invalid Order Number");
			}
			else if (addinglines.contains(selectedLine)){
				JOptionPane.showMessageDialog(panelAddStation, ("Station already added on Line " + selectedLine));

			}
			else {
				DefaultTableModel model2 = (DefaultTableModel) table.getModel();
				model2.addRow(new Object[]{selectedLine,selectedOrder});
				addinglines.add(selectedLine);
				addingstations.add(selectedOrder);

			}
			
		});
		btnAddLine_1.setBounds(314, 102, 117, 29);
		panelAddStation.add(btnAddLine_1);

		
		JButton btnAddStation_1 = new JButton("Add Station");
		btnAddStation_1.addActionListener(e -> {
			String newStationName = txtStationName.getText();
			ArrayList<String[]> badAddresses = Queries.getStationAddresses();
			String[] newAddress = {txtState.getText(), txtStreetAddress.getText(), txtPostalCode.getText(), txtCity.getText()};
			boolean flag = true;
			for (String[] str: badAddresses) {
				int countingflag = 0;
				for (int i=0; i<4; i++) {
					if (str[i].equals(newAddress[i])) {
						countingflag++;
					}
				}
				if (countingflag==4) {
					flag = false;
					break;
				}
			}
			if (Queries.getStationNames().contains(newStationName)) {
				JOptionPane.showMessageDialog(panelAddStation, "Station Name not Unique.");
			}
			else if (addinglines.size()==0) {
				JOptionPane.showMessageDialog(panelAddStation, "Station must be on a line.");

			}
			else if (isNumeric(txtPostalCode.getText()) == false)
					{
				JOptionPane.showMessageDialog(panelAddStation, "Enter Numeric PostalCode.");

			}
			else if (flag == false) {
				JOptionPane.showMessageDialog(panelAddStation, "Address is not unique.");

			}
		
			else {
				Queries.addStation(newStationName, "Open", txtState.getText(), txtStreetAddress.getText(), Integer.valueOf(txtPostalCode.getText()), txtCity.getText(), ID, Queries.getCurrentTimestamp2());
				for (int i=0; i<addinglines.size(); i++) {
					Queries.addLineToStation(newStationName, addinglines.get(i), Integer.valueOf(addingstations.get(i)));
				}
				JOptionPane.showMessageDialog(panelAddStation, "Station Added!");
				panelAddStation.setVisible(false);
				makeAdminLandingPanel();
			}
		});
		btnAddStation_1.setBounds(314, 243, 117, 29);
		panelAddStation.add(btnAddStation_1);

		return panelAddStation;
	}
	
	
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	
	private JPanel makeAddLinePanel() {
		JPanel panelAddLine = new JPanel();
		initPanel(panelAddLine, "name_120405071029218");

		// Labels
		JLabel lblAddLine = new JLabel("Add Line");
		lblAddLine.setBounds(6, 6, 61, 16);
		panelAddLine.add(lblAddLine);

		// Text Fields
		JTextField txtLineName = new JTextField();
		txtLineName.setText("Line Name");
		txtLineName.setBounds(6, 34, 130, 26);
		panelAddLine.add(txtLineName);
		txtLineName.setColumns(10);

		JTextField txtOrder_1 = new JTextField();
		txtOrder_1.setText("Order");
		txtOrder_1.setBounds(172, 72, 130, 26);
		panelAddLine.add(txtOrder_1);
		txtOrder_1.setColumns(10);

		// Table
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Station");
		model.addColumn("Order");
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(26, 125);
		scrollPane.setSize(353, 115);
		panelAddLine.add(scrollPane, BorderLayout.CENTER);


		// ComboBox
		ArrayList<String> stationNames = Queries.getStationNames();
		JComboBox comboBox = new JComboBox(stationNames.toArray());
		comboBox.setBounds(6, 73, 140, 27);
		panelAddLine.add(comboBox);
		ArrayList<String> ordernums = new ArrayList<String>();
		ArrayList<String> addedstations = new ArrayList<String>();

		// Buttons
		JButton btnAddStation_2 = new JButton("Add Station");
		btnAddStation_2.addActionListener(e -> {
			String newLineName = txtLineName.getText();	
			String selectedStation = comboBox.getSelectedItem().toString();
			String selectedOrder = txtOrder_1.getText();
			if (Queries.getLineNames().contains(newLineName)) {
				JOptionPane.showMessageDialog(panelAddLine, "Line Name not Unique.");
			}
			else if (isNumeric(selectedOrder) == false || Integer.valueOf(selectedOrder)<1) {
				JOptionPane.showMessageDialog(panelAddLine, "Invalid Order Number");
			}
			else if (ordernums.contains(selectedOrder)) {
				JOptionPane.showMessageDialog(panelAddLine, "Invalid Order Number");
			}
			else if (addedstations.contains(selectedStation)) {
				JOptionPane.showMessageDialog(panelAddLine, "Station already added to Line");
			}
			else {
				DefaultTableModel model2 = (DefaultTableModel) table.getModel();
				model2.addRow(new Object[]{selectedStation,selectedOrder});
				ordernums.add(selectedOrder);
				addedstations.add(selectedStation);
			}
			
			
		});
		btnAddStation_2.setBounds(327, 72, 117, 29);
		panelAddLine.add(btnAddStation_2);

		JButton btnAddLine_2 = new JButton("Add Line");
		btnAddLine_2.addActionListener(e -> {
			
			String newLineName = txtLineName.getText();	
			if (Queries.getLineNames().contains(newLineName)) {
				JOptionPane.showMessageDialog(panelAddLine, "Line Name not Unique.");
			}
			else {
				Queries.addLine(newLineName, ID, Queries.getCurrentTimestamp2());
				for (int i=0; i<addedstations.size(); i++) {
					Queries.addLineToStation(addedstations.get(i), newLineName, Integer.valueOf(ordernums.get(i)));
					JOptionPane.showMessageDialog(panelAddLine, "Line Added!");
					panelAddLine.setVisible(false);
					makeAdminLandingPanel();
				}
			}
		});
		btnAddLine_2.setBounds(314, 243, 117, 29);
		panelAddLine.add(btnAddLine_2);

		return panelAddLine;
	}
	
	private int populateStationTableAD(String line, Object[][] rData, String sort, String direction) {
		ArrayList<Object[]> stations = Queries.getStationsFromLine(line, sort, direction);
		for (int i = 0; i < stations.size(); i++) {
			Object[] tuple = stations.get(i);
			rData[i][0] = (String) tuple[0];
			rData[i][1] = (Integer) tuple[1];
			rData[i][2] = "[↑]";
			rData[i][3] = "[↓]";
			rData[i][4] = "[Delete]";
		}
		return stations.size();
	}

	private JPanel makeLineSummaryADPanel(String line, String order, String direction) {
		JPanel panelLineSummaryAD = new JPanel();
		initPanel(panelLineSummaryAD, "name_120586777795513");
		
		long num = Queries.getNumStops(line);

		// Label
		JLabel lblLineNum = new JLabel("Line: " + line);
		lblLineNum.setBounds(6, 6, 146, 16);
		panelLineSummaryAD.add(lblLineNum);

		JLabel lblNumStop = new JLabel(num + " Stops");
		lblNumStop.setBounds(327, 8, 100, 16);
		panelLineSummaryAD.add(lblNumStop);

		// Table

		Object rowData[][] = new Object[20][5];
		Object columnNames[] = { "Station", "Order", "", "", ""};
		int numStats = populateStationTableAD(line, rowData, order, direction);
		//BELOW MODEL MAKES TABLE NOT EDITABLE
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(17, 50);
		scrollPane.setSize(400, 163); //265
		panelLineSummaryAD.add(scrollPane, BorderLayout.CENTER);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
		        int col = table.getSelectedColumn();
		        if (col == 0 && row < numStats) { //set values to be updated later
		        	String val = (String) table.getValueAt(row, col);
		        	panelLineSummaryAD.setVisible(false);
					makeStationInfoADPanel(val);
		        } else if (col == 2 && row < numStats) {
		        	Integer val = (Integer) table.getValueAt(row, col - 1);
		        	table.setValueAt(val + 1, row, col - 1);
		        } else if (col == 3 && row < numStats) {
		        	Integer val = (Integer) table.getValueAt(row, col - 2);
		        	table.setValueAt(val - 1, row, col - 2);
		        } else if (col == 4 && row < numStats) {
		        	table.setValueAt("(Will Delete)", row, col);
		        }
			}
		});

		// Button
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(327, 225, 117, 47);
		panelLineSummaryAD.add(btnUpdate);
		btnUpdate.addActionListener(e -> { //delete and update in table
			boolean flag = true;
			ArrayList<Integer> lineOrderNums = new ArrayList<Integer>();
			for (int i = 0; i<numStats; i++) {
				if (lineOrderNums.contains((Integer)table.getValueAt(i,1))) {
					JOptionPane.showMessageDialog(panelLineSummaryAD, "Stations cannot have same order number.");
					flag = false;
				}
				else {
					lineOrderNums.add((Integer)table.getValueAt(i,1));
				}
			}
			if (flag == true) {
				for(int i = 0; i < numStats; i++) {
					if(table.getValueAt(i, 4).equals("(Will Delete)")) {
						Queries.deleteStation((String) table.getValueAt(i, 0));
					} else {
						Queries.updateStationOrderNumber((String) table.getValueAt(i, 0), line, (Integer) table.getValueAt(i, 1));
					}
				}
			panelLineSummaryAD.setVisible(false);
			makeLineSummaryADPanel(line, order, direction);
			JOptionPane.showMessageDialog(panelLineSummaryAD, "Line Updated!");
			}
		});

		// Radio Buttons
		ButtonGroup LineSummary = new ButtonGroup();

		JRadioButton rdbtnStationLineNum = new JRadioButton("");
		rdbtnStationLineNum.setBounds(45, 20, 20, 20);
		panelLineSummaryAD.add(rdbtnStationLineNum);
		rdbtnStationLineNum.addActionListener(e -> {
			panelLineSummaryAD.setVisible(false);
			if (order.equals("station_name")) {
				if (direction.equals("ASC")) {
					makeLineSummaryADPanel(line, "station_name", "DESC");
				} else {
					makeLineSummaryADPanel(line, "station_name", "ASC");
				}
			} else {
				makeLineSummaryADPanel(line, "station_name", "ASC");
			}
		});

		JRadioButton rdbtnOrderLineNum = new JRadioButton("");
		rdbtnOrderLineNum.setBounds(120, 20, 20, 20);
		panelLineSummaryAD.add(rdbtnOrderLineNum);
		rdbtnOrderLineNum.addActionListener(e -> {
			panelLineSummaryAD.setVisible(false);
			if (order.equals("order_number")) {
				if (direction.equals("ASC")) {
					makeLineSummaryADPanel(line, "order_number", "DESC");
				} else {
					makeLineSummaryADPanel(line, "order_number", "ASC");
				}
			} else {
				makeLineSummaryADPanel(line, "order_number", "ASC");
			}
		});

		LineSummary.add(rdbtnStationLineNum);
		LineSummary.add(rdbtnOrderLineNum);

		return panelLineSummaryAD;
	}

	private JPanel makeStationInfoADPanel(String sName) {
		JPanel panelStationInfoAD = new JPanel();
		initPanel(panelStationInfoAD, "name_120703525918700");

		Object[] stat = Queries.getStation(sName, "status", "address");
		float[] avgs = Queries.getAvgRatings(sName);

		String status = (String) stat[0];
		String addy = (String) stat[1];
		float aShop = avgs[0];
		float aConn = avgs[1];
		
		// Labels
		JLabel lblStationName_1 = new JLabel(sName);
		lblStationName_1.setBounds(6, 6, 122, 16);
		panelStationInfoAD.add(lblStationName_1);

		JLabel lblStatus_1 = new JLabel("Status :");
		lblStatus_1.setBounds(256, 6, 61, 16);
		panelStationInfoAD.add(lblStatus_1);

		JLabel lblAddressAddress = new JLabel("Address : " + addy);
		lblAddressAddress.setBounds(16, 34, 264, 16);
		panelStationInfoAD.add(lblAddressAddress);

		JLabel lblLines = new JLabel("Lines : ");
		lblLines.setBounds(6, 62, 284, 16);
		panelStationInfoAD.add(lblLines);
		
		makeLinesList(sName, panelStationInfoAD);

		JLabel lblAverageShopping_1 = new JLabel("Average Shopping : " + ((aShop < 0) ? "N/A" : aShop));
		lblAverageShopping_1.setBounds(6, 94, 185, 16);
		panelStationInfoAD.add(lblAverageShopping_1);

		JLabel lblAVGCS = new JLabel("Average Connection Speed : " + ((aConn < 0) ? "N/A" : aConn));
		lblAVGCS.setBounds(224, 90, 202, 16);
		panelStationInfoAD.add(lblAVGCS);

		JLabel lblReviews_1 = new JLabel("Reviews");
		lblReviews_1.setBounds(6, 122, 61, 16);
		panelStationInfoAD.add(lblReviews_1);

		// ComboBox
		JComboBox stationStatus = new JComboBox();
		stationStatus.setBounds(332, 2, 94, 27);
		panelStationInfoAD.add(stationStatus);
		stationStatus.addItem("Open");
		stationStatus.addItem("Closed");
		stationStatus.addItem("Half Capacity");
		if (status.equalsIgnoreCase("Open")) {
			stationStatus.setSelectedItem("Open");
		} else if (status.equalsIgnoreCase("Closed")) {
			stationStatus.setSelectedItem("Closed");
		} else {
			stationStatus.setSelectedItem("Half Capacity");
		}
		stationStatus.addActionListener(e -> {
			String newStatus = (String) stationStatus.getSelectedItem();
			if (!status.equalsIgnoreCase(newStatus)) {
				Queries.updateStationStatus(sName, newStatus);
				panelStationInfoAD.setVisible(false);
				makeStationInfoADPanel(sName);
			}
		});

		// Table
		Object rowData[][] = new Object[20][4];
		ArrayList<Object[]> reviews = Queries.getReviewsByStation(sName, "passenger_ID", "shopping", "connection_speed", "comment");
		for (int i = 0; i < reviews.size(); i++) {
			Object[] tuple = reviews.get(i);
			rowData[i][0] = (String) tuple[0];
			rowData[i][1] = (Integer) tuple[1];
			rowData[i][2] = (Integer) tuple[2];
			rowData[i][3] = (String) tuple[3];
		}
		Object columnNames[] = { "User", "Shopping", "Connection Speed", "Comment" };
		
		//BELOW MODEL MAKES TABLE NOT EDITABLE
		DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(6, 142);
		scrollPane.setSize(444, 130);
		panelStationInfoAD.add(scrollPane, BorderLayout.CENTER);
		
		return panelStationInfoAD;
	}
}