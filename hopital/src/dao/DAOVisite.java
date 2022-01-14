package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Medecin;
import model.Patient;
import model.Visite;


public class DAOVisite implements IDAO<Visite,Integer> {

	@Override
	public List<Visite> findAll() {
		List<Visite> visites = new ArrayList<>();
		DAOPatient daoP = new DAOPatient();
		DAOCompte daoC = new DAOCompte();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hopital?characterEncoding=UTF-8","root","");

			PreparedStatement ps = conn.prepareStatement("SELECT * from visite");
			ResultSet rs = ps.executeQuery();

			Visite v=null;

			while(rs.next()) 
			{
				Patient p = daoP.findById(rs.getInt("id_patient"));
				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				v = new Visite(rs.getInt("numero"), p, m, rs.getInt("prix"), rs.getInt("salle"), LocalDate.parse(rs.getString("date_visite")));

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

			PreparedStatement ps= conn.prepareStatement("INSERT INTO visite (id_patient,id_medecin,prix,salle,date_visite) VALUES (?,?,?,?,?)");
			ps.setInt(1,o.getPatient().getId());
			ps.setInt(2,o.getMedecin().getId());
			ps.setInt(3,o.getPrix());
			ps.setInt(4,o.getSalle());
			ps.setString(5,o.getDateVisite().toString());
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Visite> VisitefindByPatient(Integer patientId){
		List<Visite> visitesPatient = new ArrayList<>();
		DAOPatient daoP = new DAOPatient();
		DAOCompte daoC = new DAOCompte();
		Visite v=null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hopital?characterEncoding=UTF-8","root","");

			PreparedStatement ps = conn.prepareStatement("SELECT * from fiche WHERE id_patient = ?;");
			ps.setInt(1,patientId);
			ResultSet rs = ps.executeQuery();


			while(rs.next()) 
			{

				Patient p = daoP.findById(rs.getInt("id_patient"));
				Medecin m = (Medecin) daoC.findById(rs.getInt("id_medecin"));
				v = new Visite(rs.getInt("numero"), p, m, rs.getInt("prix"), rs.getInt("salle"), LocalDate.parse(rs.getString("date_visite")));

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




	@Override
	public Visite findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void update(Visite o) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
