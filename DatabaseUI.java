package test1_proxorimenathemata_vasewn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class DatabaseUI extends JFrame implements ActionListener {
    /**
	 *explanation tou action Lisentener
	 */
	private static final long serialVersionUID = 1L;
	// Global variables
    private Connection conn;

    public static void main(String[] args)  {
        new DatabaseUI();
    }

    public DatabaseUI()  {
    	
        // Set up  UI
    	
        setSize(300, 200);
        setTitle("Database Menu");
        setLayout(new FlowLayout());

        JButton connectButton = new JButton("Connect to Database");
        connectButton.addActionListener(this);
        add(connectButton);

        JButton showButton = new JButton("Show Items");
        showButton.addActionListener(this);
        add(showButton);

        JButton insertButton = new JButton("Insert Data");
        insertButton.addActionListener(this);
        add(insertButton);

        JButton updateButton = new JButton("Update Data");
        updateButton.addActionListener(this);
        add(updateButton);

        JButton transactionButton = new JButton("Start Transaction");
        transactionButton.addActionListener(this);
        add(transactionButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        add(exitButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Connect to Database")) {
        try {
			openConn();
			JOptionPane.showMessageDialog(this, "You are connected to the database!" );
			System.out.println("connected");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("not connected");
			JOptionPane.showMessageDialog(this, "You were not able to connect" );
		}
        } else if (action.equals("Show Items")) {
            showItems();
        } else if (action.equals("Insert Data")) {
            insertData();
        } else if (action.equals("Update Data")) {
            updateData();
        } else if (action.equals("Start Transaction")) {
            startTransaction();
        } else if (action.equals("Exit")) {
            System.exit(0);
        }
    }

    private void startTransaction() {
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "You must connect to the database first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            conn.setAutoCommit(false);

            
            Statement stmt = conn.createStatement();
            String sql1 = "INSERT INTO country (Code, Name, Continent, Region, SurfaceArea, IndepYear, Population, LifeExpectancy, GNP, GNPOld, LocalName, GovernmentForm, HeadOfState, Capital, Code2) " +
                          "VALUES ('TST', 'Test Country', 'North America', 'Test Region', 10000.00, 2022, 1000000, 80.0, 1000000.00, 900000.00, 'Test Country', 'Test Government Form', 'Test Head of State', 1, 'TS')";
            stmt.executeUpdate(sql1);
            String sql2 = "UPDATE country SET SurfaceArea = 20000.00 WHERE Code = 'USA'";
            stmt.executeUpdate(sql2);

            // Commit transaction
            conn.commit();

            // Close statement connection
            stmt.close();
            conn.close();

            // Show success message
            JOptionPane.showMessageDialog(this, "Transaction was successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
    }


	private void updateData() {
	
		 try {
		        // Create  statement kai execute tis UPDATE query
		        Statement stmt = conn.createStatement();
		        String sql = "UPDATE country "
		                + "SET Name = 'New Country Name', "
		                + "Continent = 'North America', "
		                + "Population = 5000000 "
		                + "WHERE Code = 'USA'";

		        stmt.executeUpdate(sql);

		        // Close  statement kai  connection
		        System.out.println("Data has been updated successfully!"); // emfanizete an exei ginei to update dedomenon 
		        stmt.close();
		        conn.close();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		
	}

	//connection sthn vash gia antilish dedomenwn 
	public void openConn() throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root","1234");
	}
  
  

    private void showItems() {
    	if (conn == null) {
    	    JOptionPane.showMessageDialog(this, "You must connect to the database first!", "Error", JOptionPane.ERROR_MESSAGE);
    	    return;
    	}
    	  try {
    	        // Create  statement kai execute enos SELECT query
    	        Statement stmt = conn.createStatement();
    	        String sql = "SELECT * FROM country;";
    	        ResultSet rs = stmt.executeQuery(sql);

    	        // Iterate through the result set and print the item names
    	        while (rs.next()) {
    	            String name = rs.getString("name");
    	            System.out.println(name);
    	        }

    	        // Close result set, statement, kai connection
    	        rs.close();
    	        stmt.close();
    	        conn.close();
    	    } catch (SQLException ex) {
    	        ex.printStackTrace();
    	    }
    }

    private void insertData() {
    	if (conn == null) {
    	    JOptionPane.showMessageDialog(this, "You must connect to the database first!", "Error", JOptionPane.ERROR_MESSAGE);
    	    return;
    	}
    	try {
            // Create  statement kai execute ena INSERT query
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO country (`Code`, `Name`, `Continent`, `Region`, `SurfaceArea`, `IndepYear`, `Population`, `LifeExpectancy`, `GNP`, `GNPOld`, `LocalName`, `GovernmentForm`, `HeadOfState`, `Capital`, `Code2`)\r\n"
            		+ "VALUES ('USA', 'United States', 'North America', 'North America', 9372610.00, 1776, 278357000, 77.1, 8510700.00, 8110900.00, 'United States', 'Federal Republic', 'George W. Bush', 625, 'US')\r\n"
            		+ "ON DUPLICATE KEY UPDATE `LifeExpectancy` = 80.0, `GNP` = 10000000.00;";
            stmt.executeUpdate(sql);

            // Close  statement kai  connection
            System.out.println("the values have been inserted");
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}


