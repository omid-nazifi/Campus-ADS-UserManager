package at.campus.ads.persistence;

import at.campus.ads.utils.HibernateUtil;
import at.campus.ads.domain.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
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
            throw new ExceptionInInitializerError("Failed to create sessionFactory object.");
        }
    }

    @Override
    public Optional<User> findById(int id) {
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
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Session session = factory.openSession()) {
            users = (List<User>) session.createQuery("FROM User").list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> findByUsername(String username) {
        User selectedUser = null;
        HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Query query = session.createQuery("FROM User WHERE username = :username");
            query.setParameter("username", username);
            selectedUser = (User) query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(selectedUser);
    }

    public boolean existsUsername(String username) {
        Optional<User> user = findByUsername(username);
        return user.isPresent();
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
