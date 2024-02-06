package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
public class StaffDatabaseManager {
	
	
	public static StringBuilder selectAllStaffRows(ResultSet rs) throws SQLException
	{
		StringBuilder sb = new StringBuilder("");
		ResultSetMetaData rsmeta = rs.getMetaData();
		int count = 0;
		while(rs.next())
		{
			String s = "Row: " +count+" ";
			for(int i=1;i<=rsmeta.getColumnCount();i++)
			{
				s= s+" ," + rs.getObject(i); 
			}
			sb.append("\n");
			sb.append(s);
			count++;
			
		}
		
		return sb;
	}
	public static void insertNewRow(PreparedStatement ss,String id,String last,String name,String mi,String add,String city,String state,String Tele,String email) throws SQLException
	{
		ss.setString(1, id);
		ss.setString(2, last);
		ss.setString(3, name);
		ss.setString(4, mi);
		ss.setString(5, add);
		ss.setString(6, city);
		ss.setString(7, state);
		ss.setString(8, Tele);
		ss.setString(9, email);
		ss.executeUpdate();
	}
	public static void update(Connection con) throws SQLException {

		PreparedStatement state = con.prepareStatement("SELECT * from Staff");
		ResultSet rs = state.executeQuery();
		
		ResultSetMetaData meta = rs.getMetaData();
		System.out.println("CHOOSE WHICH FIELD ");
		for(int i=1;i<=meta.getColumnCount();i++)
		{
			System.out.println(meta.getColumnName(i));
		}
		Scanner scan = new Scanner(System.in);
		String old = scan.next();
		System.out.println("choose the new value");
		String k = scan.next();
		String mod = "UPDATE Staff SET "+ old+"='"+ k+"'"+"WHERE "+ old+"='SU'";
//		String mod = "UPDATE Staff SET lastName='SU' WHERE lastName='Lakkis'";
		PreparedStatement s2 = con.prepareStatement(mod);
		s2.executeUpdate();
		s2.close();
	}
	public static void deleteRowithID(Connection con,String id) throws SQLException
	{
		String mod = "DELETE from Staff where Id='"+id+"'";
		PreparedStatement ps = con.prepareStatement(mod);
		ps.executeUpdate();
		ps.close();
	}
	public static void clearall(Connection con) throws SQLException
	{
		String mod = "DELETE from Staff";
		PreparedStatement ps = con.prepareStatement(mod);
		ps.executeUpdate();
		ps.close();
		
	}
	public static void merging(Connection con) throws SQLException
	{
		String mod = "CREATE TABLE merged (lastName, firstName)";
		PreparedStatement ps  = con.prepareStatement(mod);
		ps.executeUpdate();
		
	}
	
	public static void main(String[] args) throws SQLException {
		Connection connection =  DriverManager.getConnection("jdbc:sqlite:Staff.db");
		Statement statement = connection.createStatement();
		statement.executeUpdate("create table if not exists Staff ("
		+
		"Id char(9) not null,"
		+ "lastName varchar(15),"+
		"firstName varchar(15),"+
		"Mi char(1),"+
		"Address varchar(20),"+
		"City varchar(20),"+
		"State char(20),"+
		"Telephone char(10),"+
		"Email varchar(40),"+
		"primary key (Id))");
		String s  = "insert into Staff (Id,lastname,firstName,Mi,Address,City,State,Telephone,Email) "+"values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement ss = connection.prepareStatement(s);
		ss.setString(1, "09swwsw32");
		ss.setString(2, "Lakkis");
		ss.setString(3, "ip");
		ss.setString(4, "c");
		ss.setString(5, "saida");
		ss.setString(6, "MY SIU");
		ss.setString(7, "beirut");
		ss.setString(8, "03961422");
		ss.setString(9, "@mail.com");
		
		
		int k = ss.executeUpdate();
		System.out.println(k);
		PreparedStatement ss2 = connection.prepareStatement("SELECT * FROM Staff");
		ResultSet rs = ss2.executeQuery();
		StringBuilder sb = StaffDatabaseManager.selectAllStaffRows(rs);
		System.out.println(sb);
//		StaffDatabaseManager.update(connection);
		StaffDatabaseManager.deleteRowithID(connection,"202023");
		StaffDatabaseManager.merging(connection);
		//StaffDatabaseManager.clearall(connection);
		//
//		
//		
//		
//		connection.close();
	}

}
