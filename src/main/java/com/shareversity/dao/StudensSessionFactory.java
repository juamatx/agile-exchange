package com.shareversity.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StudensSessionFactory {
    private static SessionFactory factory;

    private StudensSessionFactory() {
    }

    static {
        factory = new Configuration()
                .configure("/hibernate.cfg.xml").buildSessionFactory();
    }

    public static synchronized SessionFactory getFactory() {
        return factory;
    }
}
