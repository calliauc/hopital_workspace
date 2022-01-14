package dao;

import java.sql.*;
import java.util.List;

import model.Compte;
import model.Medecin;
import model.Secretaire;

public class DAOCompte implements IDAO<Compte, Integer> {

	@Override
	public Compte findById(Integer id) {
		Compte c =null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM compte where id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()){
				if (rs.getString("type_compte").equals("Medecin")) {
					c = new Medecin (rs.getInt("id"),rs.getString("login"),rs.getString("password"))
							;			}
				else if (rs.getString("type_compte").equals("Medecin")) {
					c = new Secretaire (rs.getInt("id"),rs.getString("login"),rs.getString("password"))

				}

			}
			catch(Exception e) {
				e.printStackTrace();
			}

			return c;
	}

	@Override
	public List<Compte> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Compte o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Compte o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
