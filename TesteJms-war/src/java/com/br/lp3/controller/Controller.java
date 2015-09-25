package com.br.lp3.controller;

import com.br.lp3.jms.sessionbeans.ProducerSBLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 31427782
 */
public class Controller extends HttpServlet {

    @EJB
    private ProducerSBLocal producerSB;
    private String msg;
    private String tipo;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            List<String> msgs;
            if (request.getSession().getAttribute("listaMSG") == null) {
                msgs = new ArrayList<>();
                request.getSession().setAttribute("listaMSG", msgs);
            } else {
                msgs = (ArrayList<String>) request.getSession().getAttribute("listaMSG");
            }
            String msg3 = msg;
            if ("sinc".equalsIgnoreCase(tipo)) {
                msg3 = processoSincrono(msg);
            } else {
                msg3 = processoAssincrono(msg);
            }
            msgs.add(msg3);
            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }

    public String processoSincrono(String msg) {
        String msg2 = msg;
        try {
            producerSB.enviarMensagem(msg2);
        } catch (JMSException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg2;
    }

    public String processoAssincrono(String msg) {
        String msg2 = msg;
        try {
            for (int i = 0; i < 10; i++) {
                producerSB.enviarMensagem(msg2);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return msg2;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        msg = request.getParameter("mensagem");
        tipo = request.getParameter("tipoJMS");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
