package com.example.accessingdatamysql.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.accessingdatamysql.Entity.Client;

@Repository("mysql")
public class MySqlClientDaoImpl implements ClientDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static class ClientRowMapper implements RowMapper<Client>{

		@Override
		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			Client client = new Client();
			client.setFname(rs.getString("Fname"));
			client.setMinit(rs.getString("Minit"));
			client.setLname(rs.getString("Lname"));
			client.setId(rs.getInt("id"));
			client.setBdate(rs.getString("Bdate"));
			client.setAddress(rs.getString("Address"));
			client.setSex(rs.getString("Sex"));
			return client;	
		}
		
	}
	@Override
	public Collection<Client> getAllClients() {
		
		final String sql = "SELECT * FROM Client";
		List<Client> clients = jdbcTemplate.query(sql,new ClientRowMapper());
		return clients;
	}

	@Override
	public Client getClientById(int id) {
		final String sql = "SELECT Fname, Minit, Lname, Id, Bdate,Address,Sex FROM Client WHERE id = ?";
		Client client = jdbcTemplate.queryForObject(sql, new ClientRowMapper(),id);
		return client;
	}

	@Override
	public void removeClienttById(int id) {
		final String sql = "SELECT Fname, Minit, Lname, Id, Bdate,Address,Sex FROM Client WHERE id = ?";
		jdbcTemplate.update("DELETE FROM Client WHERE id =?", id);
	}

	@Override
	public void updateClient(Client client) {
		final String sql = "UPDATE Client SET Fname = ?, Lname = ? WHERE id = ?";
		final String Fname = client.getFname();
//		final char Minit = client.getMinit();
		final String Lname = client.getLname();
		final int id = client.getId();
//		final String Bdate = client.getBdate();
//		final String Address = client.getAddress();
//		final char Sex = client.getSex();
		jdbcTemplate.update(sql,new Object[] {Fname, Lname, id});
	}

	@Override
	public void insertClientToDb(Client client) {
		final String sql = "INSERT INTO Client (Fname, Lname) VALUES (?,?)";
		final String Fname = client.getFname();
//		final char Minit = client.getMinit();
		final String Lname = client.getLname();
//		final int id = client.getId();
//		final String Bdate = client.getBdate();
//		final String Address = client.getAddress();
//		final char Sex = client.getSex();
		jdbcTemplate.update(sql,new Object[] {Fname,Lname});
	}

	
}
