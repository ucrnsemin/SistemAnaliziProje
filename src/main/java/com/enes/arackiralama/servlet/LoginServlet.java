package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("kullanici_adi");
        String password = request.getParameter("sifre");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT email, rol FROM kullanici WHERE kullanici_adi = ? AND sifre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("rol");
                String email = rs.getString("email");

                // 🔐 Giriş yapan kullanıcı için oturum başlat
                HttpSession session = request.getSession(true);
                session.setAttribute("email", email);

                // Role göre yönlendir
                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("admin.html");
                } else {
                    response.sendRedirect("kullanici.html");
                }

            } else {
                // Hatalı giriş
                response.setContentType("text/plain");
                response.getWriter().println("Geçersiz kullanıcı adı veya şifre.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().println("Giriş sırasında hata: " + e.getMessage());
        }
    }
}
