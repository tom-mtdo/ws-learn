/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtdo.learn.mycdiproducerfield.db;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thangdo
 */
@Singleton
public class DBEntityManager {
    @Produces
    @UserDatabase
    @PersistenceContext    
    private EntityManager em;
    
    /*
    @PersistenceContext
    private EntityManager em;
    @Produces
    @UserDatabase
    public EntityManager create(){
        return em;
    }
    
    
    public void close(@Disposes @UserDatabase EntityManager em){
        em.close();
    }
*/
}
