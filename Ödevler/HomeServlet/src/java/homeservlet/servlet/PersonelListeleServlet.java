
package homeservlet.servlet;


import homeservlet.model.Personel;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class PersonelListeleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Personel> personelListesi = PersonelEkleServlet.getPersonelListesi();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Personel Listeleme</title>");
            out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1 class='my-4'>Personel Listesi</h1>");
            out.println("<table class='table table-striped'>");
            out.println("<thead class='thead-dark'><tr><th>Ad</th><th>Soyad</th><th>Sicil No</th><th>Departman</th><th>Telefon</th><th>İşe Giriş Tarihi</th><th>Maaş</th><th>Aktif</th></tr></thead>");
            out.println("<tbody>");

            double toplamMaas = 0;
            for (Personel personel : personelListesi) {
                out.println("<tr>");
                out.println("<td>" + personel.getAd() + "</td>");
                out.println("<td>" + personel.getSoyad() + "</td>");
                out.println("<td>" + personel.getSicilNo() + "</td>");
                out.println("<td>" + String.join(", ", personel.getDepartman()) + "</td>");
                out.println("<td>" + personel.getTelefon() + "</td>");
                out.println("<td>" + new java.text.SimpleDateFormat("yyyy-MM-dd").format(personel.getIseGirisTarihi()) + "</td>");
                out.println("<td>" + personel.getMaas() + "</td>");
                out.println("<td>" + (personel.isAktif() ? "Aktif" : "Pasif") + "</td>");
                out.println("</tr>");
                toplamMaas += personel.getMaas();
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("<p>Toplam Personel Sayısı: " + personelListesi.size() + "</p>");
            out.println("<p>Toplam Maaş Tutarı: " + toplamMaas + "</p>");
            out.println("<a href=\"index.html\" class='btn btn-secondary'>Anasayfa</a>");
            out.println("</div>");
            out.println("<script src='https://code.jquery.com/jquery-3.5.1.slim.min.js'></script>");
            out.println("<script src='https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js'></script>");
            out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js'></script>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
