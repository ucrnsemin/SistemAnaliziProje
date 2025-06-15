package com.enes.arackiralama.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/arac_kiralama";
    private static final String USER = "postgres";       // kendi kullanıcı adın
    private static final String PASSWORD = "1";       // kendi şifren

    public static Connection getConnection() throws SQLException {
        try {
            // PostgreSQL JDBC sürücüsünü yükle
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Eğer sürücü bulunamazsa hata yazdır
            e.printStackTrace();
        }

        // Bağlantıyı kur
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
