
package homeservlet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import homeservlet.model.Personel;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonelEkleServlet extends HttpServlet {
    private static List<Personel> personelListesi = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ad = request.getParameter("ad");
        String soyad = request.getParameter("soyad");
        String sicilNo = request.getParameter("sicilNo");
        String[] departman = request.getParameterValues("departman");
        String telefon = request.getParameter("telefon");
        String iseGirisTarihiStr = request.getParameter("iseGirisTarihi");
        double maas = Double.parseDouble(request.getParameter("maas"));
        boolean aktif = request.getParameter("aktif") != null;

        if(ad == null || ad.isEmpty() || soyad == null || soyad.isEmpty() || iseGirisTarihiStr == null || iseGirisTarihiStr.isEmpty() || maas <= 0) {
            response.sendRedirect("personel_ekle.html?error=Zorunlu alanları doldurunuz.");
            return;
        }

        Personel personel = new Personel();
        personel.setAd(ad);
        personel.setSoyad(soyad);
        personel.setSicilNo(sicilNo);
        personel.setDepartman(departman);
        personel.setTelefon(telefon);
        try {
            personel.setIseGirisTarihi(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(iseGirisTarihiStr));
        } catch (ParseException ex) {
            Logger.getLogger(PersonelEkleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        personel.setMaas(maas);
        personel.setAktif(aktif);

        personelListesi.add(personel);

        response.sendRedirect("index.html");
    }

    public static List<Personel> getPersonelListesi() {
        return personelListesi;
    }
}
