package com.myclass.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.repository.RoleRepository;
import com.myclass.repository.UserRepository;
@WebServlet (urlPatterns = {"/user" , "/user/add"})
public class UserServlet extends HttpServlet {
	
	private UserRepository userRepository = new UserRepository();
	private RoleRepository roleRepository = new RoleRepository();
	
	
	
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = req.getServletPath();
		switch (action) {
		case "/user":
			List<User> userList = userRepository.findAll();
			req.setAttribute("userList", userList);
			req.getRequestDispatcher("/WEB-INF/view/user/list.jsp").forward(req, resp);
			
			
			break;
		case "/user/add":
			List<Role> roleList = roleRepository.findAll();
			req.setAttribute("roles", roleList);
			req.getRequestDispatcher("/WEB-INF/view/user/add.jsp").forward(req, resp);
			
			break;


		default:
			break;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();
		User user = extractUserFromRequest(req);
		switch (action) {
		case "/user/add":
			
			
			int result = UserRepository.addUser(user);
			
			if (result == -1) {
				req.setAttribute("message", "Thêm user không thành công");
				req.getRequestDispatcher("/WEB-INF/view/user/add.jsp").forward(req, resp);
			}
			else {
				resp.sendRedirect(req.getContextPath() + "/user");
			}
			
			
			
			
			break;
		case "/user/edit":
			
			break;

		default:
			break;
		}
	}

	private User extractUserFromRequest(HttpServletRequest req) {
		// TODO Auto-generated method stub
		User user = new User();
		
		user.setEmail(req.getParameter("email"));
		user.setPassword(req.getParameter("password"));
		user.setFullname(req.getParameter("fullname"));
		user.setAvatar(req.getParameter("avatar"));
		user.setRoleId(Integer.parseInt(req.getParameter("roleId")));
		
		return user;
	}
	

}
