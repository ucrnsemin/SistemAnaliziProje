package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("kullanici_adi");
        String password = request.getParameter("sifre");

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT email, rol FROM kullanici WHERE kullanici_adi = ? AND sifre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("rol");
                String email = rs.getString("email");

                // Oturum başlat
                HttpSession session = request.getSession(true);
                session.setAttribute("email", email);
                session.setAttribute("kullanici_adi", username);
                session.setAttribute("rol", role);

                // Giriş başarılıysa yönlendirilecek sayfa adını gönderiyoruz
                if ("admin".equalsIgnoreCase(role)) {
                    out.print("admin.html");
                } else {
                    out.print("kullanici.html");
                }
            } else {
                out.print("Geçersiz kullanıcı adı veya şifre");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.print("Giriş sırasında hata: " + e.getMessage());
        }
    }
}
