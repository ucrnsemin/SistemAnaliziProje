package com.enes.arackiralama.model;

public class Arac {
    private int id;
    private String plaka;
    private String marka;
    private String model;
    private String yakitTuru;
    private String vitesTuru;
    private double gunlukUcret;

    // Boş constructor (JDBC için gereklidir)
    public Arac() {}

    // Dolu constructor (kullanışlı)
    public Arac(String plaka, String marka, String model, String yakitTuru, String vitesTuru, double gunlukUcret) {
        this.plaka = plaka;
        this.marka = marka;
        this.model = model;
        this.yakitTuru = yakitTuru;
        this.vitesTuru = vitesTuru;
        this.gunlukUcret = gunlukUcret;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlaka() { return plaka; }
    public void setPlaka(String plaka) { this.plaka = plaka; }

    public String getMarka() { return marka; }
    public void setMarka(String marka) { this.marka = marka; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getYakitTuru() { return yakitTuru; }
    public void setYakitTuru(String yakitTuru) { this.yakitTuru = yakitTuru; }

    public String getVitesTuru() { return vitesTuru; }
    public void setVitesTuru(String vitesTuru) { this.vitesTuru = vitesTuru; }

    public double getGunlukUcret() { return gunlukUcret; }
    public void setGunlukUcret(double gunlukUcret) { this.gunlukUcret = gunlukUcret; }
}
