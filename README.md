# Araç Kiralama Web Projesi 🚗

## Proje Özeti
Bu Java tabanlı web uygulaması, kullanıcıların araç kiralayabilmesini ve yöneticilerin araç yönetimini yapabilmesini sağlar.

## Özellikler
- Kullanıcı ve admin girişi
- Araç ekleme / silme / durum güncelleme
- Kiralama formu (TC, tarih, iletişim vb.)
- Kiralama geçmişi ve ücret hesaplama
- Bootstrap ile responsive tasarım

## Kullanılan Teknolojiler
- Java Servlet
- JDBC (PostgreSQL)
- HTML / CSS / Bootstrap
- JavaScript (fetch API)

## Kurulum
1. PostgreSQL veritabanı oluştur.
2. `DBUtil.java` içindeki bağlantı bilgilerini güncelle.
3. Tomcat ile deploy et.
4. `http://localhost:8080/AracKiralama/` adresinden eriş.

## Veritabanı Yedeği
Bu proje için kullanılan PostgreSQL veritabanı `sql/arac_kiralama.backup` dosyasındadır.

### Geri Yükleme (pgAdmin):
1. Yeni bir veritabanı oluşturun (ad: arac_kiralama).
2. Sağ tıklayın → Restore...
3. `arac_kiralama.backup` dosyasını seçin ve yükleyin.

Alternatif: Terminalden
pg_restore -U postgres -d arac_kiralama -1 arac_kiralama.sql

## Geliştirici
Enes Emin Uçar – [LinkedIn](#) – [GitHub](#)
