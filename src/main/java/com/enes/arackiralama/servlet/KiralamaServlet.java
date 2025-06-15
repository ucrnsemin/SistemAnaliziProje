package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/kirala")
public class KiralamaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=UTF-8");

        String aracId = request.getParameter("aracId");
        String isim = request.getParameter("isim");
        String soyisim = request.getParameter("soyisim");
        String tc = request.getParameter("tc");
        String iletisim = request.getParameter("iletisim");
        String kiralamaTarihi = request.getParameter("kiralama_tarihi");
        String teslimTarihi = request.getParameter("teslim_tarihi");

        try (Connection conn = DBUtil.getConnection()) {

            // 1. Kiralama bilgilerini kaydet
            String sqlInsert = "INSERT INTO kiralama (arac_id, isim, soyisim, tc, iletisim, kiralama_tarihi, teslim_tarihi) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(sqlInsert)) {
                insertStmt.setInt(1, Integer.parseInt(aracId));
                insertStmt.setString(2, isim);
                insertStmt.setString(3, soyisim);
                insertStmt.setString(4, tc);
                insertStmt.setString(5, iletisim);
                insertStmt.setDate(6, java.sql.Date.valueOf(kiralamaTarihi));
                insertStmt.setDate(7, java.sql.Date.valueOf(teslimTarihi));
                insertStmt.executeUpdate();
            }

            // 2. Aracın durumunu güncelle
            String sqlUpdate = "UPDATE arac SET durum = 'Kiralık' WHERE id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(sqlUpdate)) {
                updateStmt.setInt(1, Integer.parseInt(aracId));
                updateStmt.executeUpdate();
            }

            // Başarılı sonuç yazdır
            try (PrintWriter out = response.getWriter()) {
                out.println("Araç başarıyla kiralandı!");
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.println("Veritabanı hatası: " + e.getMessage());
            }
            e.printStackTrace();
        }
    }
}
