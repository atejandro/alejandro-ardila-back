package com.atejandro.examples.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by atejandro on 13/06/17.
 */
public abstract class AbstractRepository {

    protected SessionFactory sessionFactory;

    public AbstractRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session session(){
        return sessionFactory.getCurrentSession();
    }

}
