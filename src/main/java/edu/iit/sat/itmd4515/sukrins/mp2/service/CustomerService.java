/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.sukrins.mp2.service;

import edu.iit.sat.itmd4515.sukrins.mp2.model.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

/**
 *
 * @author sukrins
 */
@Stateless
public class CustomerService {

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(CustomerService.class.getName());

    @Resource(lookup = "jdbc/sshres14Mp2DS")
    private DataSource ds;

    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();
        // try with resources that Connectino c is ALWAYS closed
        try (Connection c = ds.getConnection()) {

            // JDBC has both Statement and PreparedStatement
            Statement s = c.createStatement();
            //PrepatedStatement ps;
            ResultSet rs = s.executeQuery("select * from customer");

            while (rs.next()) {
                //LOG.info(rs.getString("last_name"));
                customers.add(new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")));
            }

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        /*if (customers.isEmpty()) {
            return null;
        } else {
            return customers;
        }*/
        
        return customers;
    }
}
