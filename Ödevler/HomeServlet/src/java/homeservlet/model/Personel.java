package homeservlet.model;

import java.util.Date;

public class Personel {

    private String ad;
    private String soyad;
    private String sicilNo;
    private String[] departman;
    private String telefon;
    private Date iseGirisTarihi;
    private double maas;
    private boolean aktif;

    public Personel(String ad, String soyad, String sicilNo, String[] departman, String telefon, Date iseGirisTarihi, double maas, boolean aktif) {
        this.ad = ad;
        this.soyad = soyad;
        this.sicilNo = sicilNo;
        this.departman = departman;
        this.telefon = telefon;
        this.iseGirisTarihi = iseGirisTarihi;
        this.maas = maas;
        this.aktif = aktif;
    }

    public Personel() {
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getSicilNo() {
        return sicilNo;
    }

    public void setSicilNo(String sicilNo) {
        this.sicilNo = sicilNo;
    }

    public String[] getDepartman() {
        return departman;
    }

    public void setDepartman(String[] departman) {
        this.departman = departman;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Date getIseGirisTarihi() {
        return iseGirisTarihi;
    }

    public void setIseGirisTarihi(Date iseGirisTarihi) {
        this.iseGirisTarihi = iseGirisTarihi;
    }

    public double getMaas() {
        return maas;
    }

    public void setMaas(double maas) {
        this.maas = maas;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

}
