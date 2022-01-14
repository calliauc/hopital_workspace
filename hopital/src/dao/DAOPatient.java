package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Patient;

public class DAOPatient implements Serializable, IDAO<Patient, Integer>{

	@Override
	public Patient findById(Integer id) {
		// TODO 

		Patient p = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager
					.getConnection(urlBdd, loginBdd, passwordBdd);

			PreparedStatement ps = conn.prepareStatement("SELECT * from patient where id=?");
			ps.setInt(1,id);

			ResultSet rs = ps.executeQuery();


			while (rs.next())
			{

				
				p = new Patient(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
				
			}
			rs.close();
			ps.close();
			conn.close();



		} catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		return p;
		
	}

	@Override
	public List<Patient> findAll() {
		
		List<Patient> patients = new ArrayList();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd, loginBdd, passwordBdd);

			PreparedStatement ps = conn.prepareStatement("SELECT * from patient");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) 
			{	
				
				Patient p = new Patient(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
				patients.add(p);
				
			}


			rs.close();
			ps.close();
			conn.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();


		}

		return patients;
		
	}

	@Override
	public Integer insert(Patient p) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd, loginBdd, passwordBdd);
		
			PreparedStatement ps = conn.prepareStatement("INSERT INTO patient (nom, prenom) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,p.getNom());
			ps.setString(2,p.getPrenom());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			Integer id=null;
			if(rs.next()) {
				id = rs.getInt(1);
			}
			ps.close();
			conn.close();
			return id;
		}
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(Patient p) {
		// TODO Auto-generated method stub
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd, loginBdd, passwordBdd);
			PreparedStatement ps = conn.prepareStatement("Update patient set nom=?,prenom=? where id=?");
			
			ps.setString(1,p.getNom());
			ps.setString(2,p.getPrenom());
			ps.setObject(3,  p.getId());
			
			ps.executeUpdate();
			
			
			ps.close();
			conn.close();
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd, loginBdd, passwordBdd);
			
			PreparedStatement ps = conn.prepareStatement("delete from patient where id=?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}

}
