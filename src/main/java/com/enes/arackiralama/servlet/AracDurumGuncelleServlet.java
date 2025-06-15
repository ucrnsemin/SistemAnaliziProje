package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/arac-durum-guncelle")
public class AracDurumGuncelleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aracId = request.getParameter("id");
        String yeniDurum = request.getParameter("durum");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE arac SET durum = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, yeniDurum);
            stmt.setInt(2, Integer.parseInt(aracId));
            stmt.executeUpdate();

            response.setStatus(200);
            response.getWriter().write("Durum güncellendi");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("Hata: " + e.getMessage());
        }
    }
}
