package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Book;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String puName = "my-persistence-unit";
        Map<String, String> props = new HashMap<>();

        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "none"); // none create update

        EntityManagerFactory emf =
//                Persistence.createEntityManagerFactory("my-persistence-unit");
                new HibernatePersistenceProvider()
                        .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager em = emf.createEntityManager(); // manager of a context. you can imagine context as a collection of entities

        try {
            em.getTransaction().begin();
            // find
            // persist
            // remove
            // merge. put an object into the context.
            // detach. you don't want to remove it. you just want to detach it from the context

            Book b1 = em.find(Book.class, 3); // get from the database and put into the context
            System.out.println(b1);

            Book b2 = new Book();
            b2.setId(2);
            em.persist(b2); // An insert goes to the db?? noooo - an instance goes into the context
            em.remove(b2);

            em.getTransaction().commit(); // where the insert might go to a db. context is mirrored to the db
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}