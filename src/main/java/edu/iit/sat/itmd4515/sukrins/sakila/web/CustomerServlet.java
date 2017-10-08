/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.sukrins.sakila.web;

import edu.iit.sat.itmd4515.sukrins.sakila.model.Customer;
import edu.iit.sat.itmd4515.sukrins.sakila.service.CustomerService;
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
@WebServlet(name = "CustomerServlet",
        urlPatterns = {"/customer",
            "/customers", "/readOnly"})
public class CustomerServlet extends HttpServlet {

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
        switch (request.getServletPath()) {
            //display all the customers
            case "/customers":
                LOG.info("GET request to /customers");
                request.setAttribute("customers", svc.findAll());
                request.getRequestDispatcher("/WEB-INF/pages/customers.jsp").forward(request, response);
                break;
            //display a single customer or potentially provide the ability a new customer
            case "/customer":
                LOG.info("GET request to /customer");
                if (!WebUtil.isEmpty(request.getParameter("customerId"))) {
                    Long customerId = Long.parseLong(WebUtil.trimParam(request.getParameter("customerId")));
                    Customer c = svc.findByID(customerId);
                    request.setAttribute("customer", c);
                }
                request.getRequestDispatcher("/WEB-INF/pages/customer.jsp").forward(request, response);

                break;

            case "/readOnly":
                LOG.info("GET request to /readOnly");
                if (!WebUtil.isEmpty(request.getParameter("customerId"))) {
                    Long customerId = Long.parseLong(WebUtil.trimParam(request.getParameter("customerId")));
                    Customer c = svc.findByID(customerId);
                    request.setAttribute("customer", c);
                }
                request.getRequestDispatcher("/WEB-INF/pages/readOnly.jsp").forward(request, response);

                break;
        }
        LOG.info("Inside doGet method. ");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/customer.jsp");
        dispatcher.forward(request, response);
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

        Long customerID = null;

        if (!WebUtil.isEmpty(request.getParameter("customerId"))) {
            customerID = Long.parseLong(WebUtil.trimParam(request.getParameter("customerId")));
        }

        String firstName = WebUtil.trimParam(request.getParameter("firstName"));
        String lastName = WebUtil.trimParam(request.getParameter("lastName"));
        String email = WebUtil.trimParam(request.getParameter("email"));

        Customer c = new Customer(customerID, firstName, lastName, email);

        Set<ConstraintViolation<Customer>> violations = validator.validate(c);

        if (violations.isEmpty()) {
            LOG.info("Received the following customer from user form:\t" + c.toString());

            //TODO
            if (svc.save(c)) {
                messages.put("sucess", "Successfully saved customer");
                request.setAttribute("customers", svc.findAll());
                request.getRequestDispatcher("/WEB-INF/pages/customers.jsp").forward(request, response);
            } else if (request.getParameter("deleteCustomer").contains("Delete")) {
                svc.delete(c.getId());
                messages.put("sucess", "Successfully deleted customer");
                request.setAttribute("customers", svc.findAll());
                request.getRequestDispatcher("/WEB-INF/pages/customers.jsp").forward(request, response);
            }
        } else {
            LOG.info("There are " + violations.size() + " violations.");

            for (ConstraintViolation<Customer> violation : violations) {
                LOG.info("#### " + violation.getRootBeanClass().getSimpleName()
                        + ", " + violation.getPropertyPath() + " failed violations:\t"
                        + violation.getInvalidValue() + " failed with message:\t"
                        + violation.getMessage());
            }

            request.setAttribute("violations", violations);
            request.setAttribute("customer", c);

            request.getRequestDispatcher("/WEB-INF/pages/customer.jsp").forward(request, response);
        }

    }

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CustomerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerServlet at " + request.getContextPath() + "</h1>");

            out.println("<ol>");
            for (Customer c : svc.findAll()) {
                out.println("<li>" + c.toString() + "</li>");
            }
            out.println("</ol");

            out.println("</body>");
            out.println("</html>");
        }
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
