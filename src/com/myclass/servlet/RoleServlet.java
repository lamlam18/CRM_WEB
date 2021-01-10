package com.myclass.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;

@WebServlet(urlPatterns = {"/role","/role/add","/role/edit", "/role/delete"})

public class RoleServlet extends HttpServlet{
	private RoleRepository roleRepository;
	public RoleServlet() {
		roleRepository = new RoleRepository();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String action = req.getServletPath();
		switch (action) {
		case "/role":
			
			List<Role> roleList = roleRepository.findAll();
			
			req.setAttribute("listRole", roleList);
			req.getRequestDispatcher("/WEB-INF/view/role/index.jsp").forward(req, resp);
			break;
		case "/role/add":
			
			req.getRequestDispatcher("/WEB-INF/view/role/add.jsp").forward(req, resp);
			break;
		case "/role/edit":
			int idEdit = Integer.parseInt(req.getParameter("id"));
			Role entity = roleRepository.findById(idEdit);
			
			req.setAttribute("role", entity);
			req.getRequestDispatcher("/WEB-INF/view/role/edit.jsp").forward(req, resp);
			break;
		case "/role/delete":
			int idRemove = Integer.parseInt(req.getParameter("id"));
			roleRepository.removeRole(idRemove);
			
			resp.sendRedirect(req.getContextPath() + "/role");
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String action = req.getServletPath();
		
		String name = req.getParameter("name");
		String desc = req.getParameter("desc");
		
		Role role = new Role(name , desc);
		switch (action) {
		case "/role/add":
			int result =  roleRepository.addRole(role);
			if (result == -1) {
				req.setAttribute("message", "Thêm thất bại");
				req.getRequestDispatcher("/WEB-INF/view/role/add.jsp").forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/role");
			}
			break;
		case "/role/edit":
			int id = Integer.parseInt(req.getParameter("id"));
			role.setId(id);
			if (roleRepository.editRole(role) == -1) {
				req.setAttribute("message", "Cập nhật thất bại");
				req.getRequestDispatcher("WEB-INF/view/role/edit.jsp").forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/role");
			}
			break;

		default:
			break;
		}
		
	}
	
	

}
