package com.enes.arackiralama.servlet;

import com.enes.arackiralama.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.sql.ResultSet;



@WebServlet("/kiralama-gecmisi")
public class KiralamaGecmisiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT k.id, a.plaka, k.isim, k.soyisim, k.kiralama_tarihi, k.teslim_tarihi, a.gunluk_ucret " +
                    "FROM kiralama k JOIN arac a ON k.arac_id = a.id ORDER BY k.kiralama_tarihi DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.print("[");
            boolean first = true;
            while (rs.next()) {
                if (!first) out.print(",");

                Date kiralamaDate = rs.getDate("kiralama_tarihi");
                Date teslimDate = rs.getDate("teslim_tarihi");

                long gun = ChronoUnit.DAYS.between(kiralamaDate.toLocalDate(), teslimDate.toLocalDate());
                double toplamUcret = rs.getDouble("gunluk_ucret") * gun;

                out.print("{");
                out.print("\"id\":" + rs.getInt("id") + ",");
                out.print("\"plaka\":\"" + rs.getString("plaka") + "\",");
                out.print("\"isim\":\"" + rs.getString("isim") + "\",");
                out.print("\"soyisim\":\"" + rs.getString("soyisim") + "\",");
                out.print("\"kiralamaTarihi\":\"" + kiralamaDate + "\",");
                out.print("\"teslimTarihi\":\"" + teslimDate + "\",");
                out.print("\"gunSayisi\":" + gun + ",");
                out.print("\"toplamUcret\":" + toplamUcret);
                out.print("}");
                first = false;
            }
            out.print("]");
        } catch (Exception e) {
            resp.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
