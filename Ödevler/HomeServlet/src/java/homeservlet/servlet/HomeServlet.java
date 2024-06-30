/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package homeservlet.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Personel Kayıt Anasayfa</title>");
            out.println("<link rel='stylesheet' href='css/styles.css'/>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Personel Kayıt Anasayfa</h1>");
            out.println("<ul>");
            out.println("<li><a href='personel_ekle.html'>Personel Ekle</a></li>");
            out.println("<li><a href='PersonelListeleServlet'>Personel Listele</a></li>");
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
