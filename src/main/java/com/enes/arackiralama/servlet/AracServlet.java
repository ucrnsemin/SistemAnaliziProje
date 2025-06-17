package com.enes.arackiralama.servlet;

import com.enes.arackiralama.model.Arac;
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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/arac")
public class AracServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String plaka = request.getParameter("plaka");
        String marka = request.getParameter("marka");
        String model = request.getParameter("model");
        String yakit = request.getParameter("yakit");
        String vites = request.getParameter("vites");
        double ucret = Double.parseDouble(request.getParameter("ucret"));

        Arac arac = new Arac(plaka, marka, model, yakit, vites, ucret);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO arac (plaka, marka, model, yakit_turu, vites_turu, gunluk_ucret) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, arac.getPlaka());
            stmt.setString(2, arac.getMarka());
            stmt.setString(3, arac.getModel());
            stmt.setString(4, arac.getYakitTuru());
            stmt.setString(5, arac.getVitesTuru());
            stmt.setDouble(6, arac.getGunlukUcret());

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                out.println("Araç başarıyla eklendi!");
            } else {
                out.println("Araç eklenemedi!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Veritabanı hatası: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM arac";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.print("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) out.print(",");
                out.print("{");
                out.print("\"id\":" + rs.getInt("id") + ",");
                out.print("\"plaka\":\"" + rs.getString("plaka") + "\",");
                out.print("\"marka\":\"" + rs.getString("marka") + "\",");
                out.print("\"model\":\"" + rs.getString("model") + "\",");
                out.print("\"yakit\":\"" + rs.getString("yakit_turu") + "\",");
                out.print("\"vites\":\"" + rs.getString("vites_turu") + "\",");
                out.print("\"ucret\":" + rs.getDouble("gunluk_ucret") + ",");
                String durum = rs.getString("durum");
                if (durum == null) durum = "Müsait";
                out.print("\"durum\":\"" + durum + "\"");
                out.print("}");
                first = false;
            }

            out.print("]");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            out.print("{\"error\":\"Veritabanı hatası: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        if (idStr == null) {
            response.setStatus(400);
            response.getWriter().println("ID parametresi eksik.");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            // Aracın kiralama geçmişi var mı kontrolü
            String kontrolSQL = "SELECT 1 FROM kiralama WHERE arac_id = ?";
            PreparedStatement kontrolStmt = conn.prepareStatement(kontrolSQL);
            kontrolStmt.setInt(1, Integer.parseInt(idStr));
            ResultSet rs = kontrolStmt.executeQuery();

            if (rs.next()) {
                response.setStatus(400);
                response.getWriter().println("Bu araç silinemez, çünkü kiralama geçmişi bulunuyor.");
                return;
            }

            String sql = "DELETE FROM arac WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idStr));
            int etkilenen = stmt.executeUpdate();

            if (etkilenen > 0) {
                response.getWriter().println("Araç başarıyla silindi.");
            } else {
                response.setStatus(404);
                response.getWriter().println("Araç bulunamadı.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().println("Silme hatası: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");

        if (idStr == null) {
            response.setStatus(400);
            response.getWriter().println("ID parametresi eksik.");
            return;
        }

        String plaka = request.getParameter("plaka");
        String marka = request.getParameter("marka");
        String model = request.getParameter("model");
        String yakit = request.getParameter("yakit");
        String vites = request.getParameter("vites");
        double ucret = Double.parseDouble(request.getParameter("ucret"));

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE arac SET plaka = ?, marka = ?, model = ?, yakit_turu = ?, vites_turu = ?, gunluk_ucret = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, plaka);
            stmt.setString(2, marka);
            stmt.setString(3, model);
            stmt.setString(4, yakit);
            stmt.setString(5, vites);
            stmt.setDouble(6, ucret);
            stmt.setInt(7, Integer.parseInt(idStr));
            int etkilenen = stmt.executeUpdate();

            if (etkilenen > 0) {
                response.getWriter().println("Araç başarıyla güncellendi.");
            } else {
                response.setStatus(404);
                response.getWriter().println("Araç bulunamadı.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().println("Güncelleme hatası: " + e.getMessage());
        }
    }
}
