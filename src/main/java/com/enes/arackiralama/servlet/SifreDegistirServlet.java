package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/sifre-degistir")
public class SifreDegistirServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Oturum kontrolü
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            out.println("Şifre değiştirmek için giriş yapmalısınız.");
            return;
        }

        String email = (String) session.getAttribute("email");
        String eskiSifre = request.getParameter("eskiSifre");
        String yeniSifre = request.getParameter("yeniSifre");

        try (Connection conn = DBUtil.getConnection()) {
            // Eski şifreyi doğrula
            String kontrolSQL = "SELECT * FROM kullanici WHERE email = ? AND sifre = ?";
            PreparedStatement kontrolStmt = conn.prepareStatement(kontrolSQL);
            kontrolStmt.setString(1, email);
            kontrolStmt.setString(2, eskiSifre);
            ResultSet rs = kontrolStmt.executeQuery();

            if (rs.next()) {
                // Yeni şifreyi güncelle
                String guncelleSQL = "UPDATE kullanici SET sifre = ? WHERE email = ?";
                PreparedStatement updateStmt = conn.prepareStatement(guncelleSQL);
                updateStmt.setString(1, yeniSifre);
                updateStmt.setString(2, email);
                updateStmt.executeUpdate();

                out.println("Şifre başarıyla değiştirildi.");
            } else {
                out.println("Eski şifre yanlış!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            out.println("Veritabanı hatası: " + e.getMessage());
        }
    }
}
