package com.enes.arackiralama.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // aktif session varsa
        if (session != null) {
            session.invalidate(); // oturumu sonlandır
        }

        response.sendRedirect("login.html"); // login sayfasına yönlendir
    }
}
