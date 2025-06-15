package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/admin-ekle")
public class AdminEkleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String kullaniciAdi = request.getParameter("kullanici_adi");
        String sifre = request.getParameter("sifre");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO kullanici (kullanici_adi, sifre, rol) VALUES (?, ?, 'admin')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kullaniciAdi);
            stmt.setString(2, sifre);
            stmt.executeUpdate();

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("Yeni admin başarıyla eklendi!");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().println("Hata oluştu: " + e.getMessage());
        }
    }
}
