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
            // persist --> adds to the context and marks the entity as to be inserted
            // remove  --> generates a deletion
            // merge   --> adds to the context a detached instance ( an instance outside of the context) put an object into the context.
            // detach  --> only take the instance out of the context; you don't want to remove it. you just want to detach it from the context
            // getReference()  -->  only gets you a proxy to a specific instance
            // refresh() --> reestablish the data in the instance the way it is in the DB (undo)

//            Book b1 = em.find(Book.class, 3); // get from the database and put into the context
//            b1.setTitle("something else");
//            System.out.println(b1);
//
//            Book b2 = new Book();
//            b2.setId(2);
//            em.persist(b2); // An insert goes to the db?? noooo - an instance goes into the context
//            em.detach(b2);

//            Book b1 = new Book();
//            b1.setId(1);
//
//            em.merge(b1);
//            b1.setTitle("Troubleshooting Java");

            Book b1  = em.getReference(Book.class, 1);
            System.out.println(b1.getTitle());


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