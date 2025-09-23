package com.library.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory enMaFa = Persistence.createEntityManagerFactory("LibraryJPA");

    public static EntityManager getEntityManager(){
        return enMaFa.createEntityManager();
    }
}