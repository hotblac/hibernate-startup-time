package org.dontpanic.hibernate;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAEntityManagerFactory {
    private static EntityManagerFactory entityManagerFactory;

    static {
        long startTime = System.currentTimeMillis();
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
            long endTime = System.currentTimeMillis();
            System.out.printf("EntityManagerFactory initialization took %d ms%n", endTime - startTime);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initial EntityManagerFactory failed: " + e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory has not been initialized");
        }
        return entityManagerFactory;
    }
}
