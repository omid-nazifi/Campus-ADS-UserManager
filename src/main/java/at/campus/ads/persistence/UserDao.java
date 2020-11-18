package at.campus.ads.persistence;

import at.campus.ads.HibernateUtil;
import at.campus.ads.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    private SessionFactory factory;

    public UserDao() {
        try {
            factory = HibernateUtil.getSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public Optional<User> get(int id) {
        User selectedUser = null;
        HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            selectedUser = session.get(User.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(selectedUser);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Session session = factory.openSession()) {
            users = (List<User>) session.createQuery("FROM User").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Integer save(User user) {
        Transaction transaction = null;
        Integer id = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            id = (Integer) session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User selectedUser = session.get(User.class, user.getId());
            session.delete(selectedUser);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
