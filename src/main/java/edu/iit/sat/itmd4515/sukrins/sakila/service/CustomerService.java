/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.sukrins.sakila.service;

import edu.iit.sat.itmd4515.sukrins.sakila.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     *
     * @return
     */
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

    /*
     * Find a customer by their id.
     */
    /**
     *
     * @param id
     * @return
     */
    public Customer findByID(Long id) {

        // try with resources that Connectino c is ALWAYS closed
        try (Connection c = ds.getConnection()) {

            // JDBC has both Statement and PreparedStatement
            PreparedStatement ps = c.prepareStatement("select * from customer where customer_id = ?");
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getLong("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"));
            }

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        return null;

    }

    /**
     *
     * @param id
     * @return
     */
    public void delete(Long id) {
        try (Connection c = ds.getConnection()) {

            PreparedStatement preparedStatement = c.prepareStatement("delete from customer where customer_id= ? ");
            // Parameters start with 1
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public boolean create(Customer customer) {
        String insertSql = "insert into customer("
                + "customer_id,"
                + "first_name,"
                + "last_name,"
                + "email,"
                + " values(?,?,?,?)";
        try (Connection c = ds.getConnection()) {

            PreparedStatement ps = c.prepareStatement(insertSql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setLong(3, customer.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        /**
         *
         * @param customer
         * @return
         */
    public boolean save(Customer customer) {

        String insertSql = "insert into customer("
                + "customer_id,"
                + "first_name,"
                + "last_name,"
                + "email,"
                + " values(?,?,?,?)";

        String updateSql = "update customer set "
                + "first_name=?, "
                + "last_name=?, "
                + "email=? "
                + "where customer_id=?";

        int returnVal = 0;

        try (Connection c = ds.getConnection()) {

            PreparedStatement ps = c.prepareStatement(updateSql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setLong(4, customer.getId());

            LOG.info("Updating a new record " + customer.toString());
            returnVal = ps.executeUpdate();

            // if nothing was updated, that means we need to insert
            if (returnVal == 0) {
                LOG.info("Nothing to update.  Inserting a new record " + customer.toString());
                ps = c.prepareStatement(insertSql);
                ps.setLong(1, customer.getId());
                ps.setString(2, customer.getFirstName());
                ps.setString(3, customer.getLastName());
                ps.setString(4, customer.getEmail());
                returnVal = ps.executeUpdate();
            }

        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return false;
        }

        return returnVal > 0;
    }

}
