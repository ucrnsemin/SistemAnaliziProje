package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String kullaniciAdi = request.getParameter("kullanici_adi");
        String sifre = request.getParameter("sifre");
        String rol = "user"; // 👈 kullanıcıya rol seçtirmiyoruz, doğrudan "user"

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO kullanici (kullanici_adi, sifre, rol) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kullaniciAdi);
            stmt.setString(2, sifre);
            stmt.setString(3, rol);
            stmt.executeUpdate();

            // Kayıt başarılıysa login sayfasına yönlendir
            response.sendRedirect("login.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("Kayıt sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}
