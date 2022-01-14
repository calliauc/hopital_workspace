package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Compte;
import model.Medecin;
import model.Secretaire;

public class DAOCompte implements IDAO<Compte, Integer> {
	
	public Compte seConnecter(String login, String password) {
		Compte c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM compte WHERE login=? AND password=? ");
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				if (rs.getString("type_compte").equals("Medecin")) 
				{
					c = new Medecin (rs.getInt("id"),rs.getString("login"),rs.getString("password"));
				}
				else if (rs.getString("type_compte").equals("Secretaire"))
				{
					c = new Secretaire (rs.getInt("id"),rs.getString("login"),rs.getString("password"));
				}
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problème de connection au Compte");
		}
		
		
		return c;
	}

	@Override
	public Compte findById(Integer id) {
		Compte c =null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM compte WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				if (rs.getString("type_compte").equals("Medecin")) 
				{
					c = new Medecin (rs.getInt("id"),rs.getString("login"),rs.getString("password"));
				}
				else if (rs.getString("type_compte").equals("Secretaire"))
				{
					c = new Secretaire (rs.getInt("id"),rs.getString("login"),rs.getString("password"));
				}
			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return c;
	}


	@Override
	public List<Compte> findAll() {
		List<Compte> comptes = new ArrayList();


		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM compte");
			ResultSet rs = ps.executeQuery();

			while (rs.next())
			{
				if (rs.getString("type_compte").equals("Medecin")) 
				{
					Medecin m = new Medecin (rs.getInt("id"),rs.getString("login"),rs.getString("password"));
					comptes.add(m);
				}
				else if (rs.getString("type_compte").equals("Secretaire"))
				{
					Secretaire s = new Secretaire (rs.getInt("id"),rs.getString("login"),rs.getString("password"));
					comptes.add(s);
				}

			}
			rs.close();
			ps.close();
			conn.close();
		}
		catch (Exception e) { 
			e.printStackTrace();
		}

		return comptes;
	}

	@Override
	public Integer insert(Compte o) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("INSERT INTO compte (id, login, password, type_compte) VALUES (?,?,?,?)");
			ps.setInt(1, o.getId());
			ps.setString(2, o.getLogin());
			ps.setString(3, o.getPassword());
			
			if (o instanceof Medecin) {
				ps.setString(4, "Medecin");
			}
			else if (o instanceof Secretaire) {
				ps.setString(4, "Secretaire");
			}
			
			ps.executeUpdate();
			ps.close();
			conn.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Compte o) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("UPDATE compte SET login=? ,password=? WHERE id=?");
			ps.setInt(3, o.getId());
			ps.setString(1, o.getLogin());
			ps.setString(2, o.getPassword());
			ps.executeUpdate();

			ps.close();
			conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void delete(Integer id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(urlBdd,loginBdd,passwordBdd);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM compte WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();

			ps.close();
			conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
