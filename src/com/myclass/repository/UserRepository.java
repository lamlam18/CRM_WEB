package com.myclass.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myclass.connecttion.DbConnection;
import com.myclass.entity.Role;
import com.myclass.entity.User;

public class UserRepository {

	public List<User> findAll() {
		List<User> userList = new ArrayList<User>();
		Connection conn = DbConnection.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM users");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User entity = new User();
				entity.setId(resultSet.getInt("id"));
				entity.setEmail(resultSet.getString("email"));
				entity.setPassword(resultSet.getString("password"));
				entity.setFullname(resultSet.getString("fullname"));
				entity.setAvatar(resultSet.getString("avatar"));
				entity.setRoleId(resultSet.getInt("role_id"));

				userList.add(entity);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userList;
	}

	public static int addUser(User user) {
		// TODO Auto-generated method stub
		Connection conn = DbConnection.getConnection();
		String query = "INSERT INTO users (email , password , fullname , avatar , role_id) value ( ? , ? , ? , ? , ?)";

		try {
			PreparedStatement statement = conn.prepareStatement(query);
			
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFullname());
			statement.setString(4, user.getAvatar());
			statement.setInt(5, user.getRoleId());
			
			return statement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}



}
