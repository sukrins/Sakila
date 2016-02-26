/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.sukrins.mp2.web;

import edu.iit.sat.itmd4515.sukrins.mp2.model.Customer;
import edu.iit.sat.itmd4515.sukrins.mp2.service.CustomerService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author sukrins
 */

@WebServlet(name = "CustomerDelete", urlPatterns = {"/delete"})
public class DeleteServlet extends HttpServlet {
     private static final Logger LOG = Logger.getLogger(CustomerServlet.class.getName());

    @Resource
    Validator validator;

    @EJB
    CustomerService svc;
   
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
        
                LOG.info("Inside doGet.");
                
       
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
        LOG.info("Inside doGet method");

        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        if (!WebUtil.isEmpty(request.getParameter("customerId"))) {
            LOG.warning("Customer's id was not passed.");
        }

        Long id = Long.parseLong(request.getParameter("id"));
        String firstName = WebUtil.trimParam(request.getParameter("firstName"));
        String lastName = WebUtil.trimParam(request.getParameter("lastName"));
        String email = WebUtil.trimParam(request.getParameter("email"));
        
        boolean rs = svc.delete(id);
            if(rs){
                request.getRequestDispatcher("/WEB-INF/pages/customers.jsp").forward(request, response);

            }else{
            
            PrintWriter out = response.getWriter();
            out.println("ID error");
            }


        

        }

    }
