package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConnectionFactory {
    
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("agenda");
    
    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    static void closeEntityManagerFactory() {
        EMF.close();
    }
    
}