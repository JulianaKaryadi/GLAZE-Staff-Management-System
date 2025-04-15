/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.session;

import com.entities.Users;
import com.interfaces.UserInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LENOVO
 */
@Stateless
public class UserSessionBean implements UserInterface {
    @PersistenceContext(unitName = "ProjectMiniReuse-ejbPU")
    private EntityManager em;

    @Override
    public Users createUser(Users user) {
        em.persist(user);
        return user;
    }

    @Override
    public Users updateUser(Users user) {
        return em.merge(user);
    }

    @Override
    public void deleteUser(Integer userId) {
        Users user = em.find(Users.class, userId);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public Users getUserById(Integer userId) {
        return em.find(Users.class, userId);
    }

    @Override
    public Users getUserByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return em.createQuery("SELECT u FROM Users u", Users.class).getResultList();
    }

    @Override
    public List<Users> getUsersByRole(String role) {
        return em.createQuery("SELECT u FROM Users u WHERE u.role = :role", Users.class)
                 .setParameter("role", role)
                 .getResultList();
    }

    @Override
    public List<Users> getActiveUsers() {
        return em.createQuery("SELECT u FROM Users u WHERE u.isActive = true", Users.class)
                 .getResultList();
    }
    
    @Override
    public boolean authenticateUser(String username, String password) {
        try {
            Users user = getUserByUsername(username);
            System.out.println("Username found: " + username);
            System.out.println("Stored password: " + user.getPassword());
            System.out.println("Input password: " + password);
            return user != null && user.getPassword().equals(password);
        } catch (Exception e) {
            return false;
        }
    }
}