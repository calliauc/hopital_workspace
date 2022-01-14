package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DAOVisite implements IDAO<Visite,Integer> {

	@Override
	public List<Visite> findAll() {
		List<Visite> visites = new ArrayList<>();
		DAOpatient daop = new DAOpatient();
		DAOCompte daoC = new DAOCompte();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hopital?characterEncoding=UTF-8","root","");

			PreparedStatement ps = conn.prepareStatement("SELECT * from visite");
			ResultSet rs = ps.executeQuery();

			Visite v=null;

			while(rs.next()) 
			{
				Patient p = (Patient) daoC.findById(rs.getInt("id_patient"));
				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				v = new Visite(rs.getInt("numero"), p, m, rs.getInt("prix"), rs.getInt("salle"), rs.getDate("date_visite"));

				visites.add(v);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return visites;
	}
	
	
	
	
	@Override
	public void insert(Visite o) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hopital?characterEncoding=UTF-8","root","");

			PreparedStatement ps= conn.prepareStatement("INSERT INTO visite (id_patient,id_medecin) VALUES (?,?)");
			ps.setInt(1,o.getPatient().getId());
			ps.setInt(2,o.getMedecin().getId());
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Visite> VisitefindByPatient(Integer patientId){
		List<Visite> visitesPatient = new ArrayList<>();
		DAOpatient daop = new DAOpatient();
		DAOCompte daoC = new DAOCompte();
		Visite v=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/visite?characterEncoding=UTF-8","root","");

			PreparedStatement ps = conn.prepareStatement("SELECT * from fiche WHERE id_patient = ?;");
			ps.setInt(1,patientId);
			ResultSet rs = ps.executeQuery();


			while(rs.next()) 
			{

				Patient p = (Patient) daoC.findById(rs.getInt("id_patient"));
				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				v = new Visite(rs.getInt("numero"), p, m, rs.getInt("prix"), rs.getInt("salle"), rs.getDate("date_visite"));

				visitesPatient.add(v);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

		}
		return visitesPatient;
	}
	
	
}
