package dao;

import java.util.List;

public interface IDAO<T,K> {
	
	String urlBdd = "jdbc:mysql://localhost:3306/hopital?characterEncoding=UTF-8";
	String loginBdd = "root";
	String passwordBdd = "";
	
	
	public T findById(K id);
	public List<T> findAll();
	public void insert(T o);
	public void update(T o);
	public void delete(K id);
	

}
