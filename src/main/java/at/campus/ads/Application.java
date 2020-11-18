package at.campus.ads;

import at.campus.ads.domain.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * A very simple class that shows how to load the driver, create a database,
 * create a table, and insert some data.
 */
public class Application {

    public static void main(String[] args) {

        User user = new User("Omid", "Nazifi", "onazifi", "pass");
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

}