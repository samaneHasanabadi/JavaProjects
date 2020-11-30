package model.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class CRUDOperation<T> {
    private Class<T> typeOfT;

    @SuppressWarnings("unchecked")
    public CRUDOperation() {
        this.typeOfT = (Class<T>)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }
    public void creat(T t){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(t);
        transaction.commit();
        session.close();
    }

    public T read(int id){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        T t = (T) session.get(typeOfT, id);
        session.close();
        return t;
    }

    public void update(T t){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(t);
        transaction.commit();
        session.close();
    }

    public void delete(T t){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(t);
        transaction.commit();
        session.close();
    }

    public List<T> selectAll(){
        Session session = DatabaseConnection.connectionRepository.getSessionFactory().openSession();
        Query query = session.createQuery("select from " + typeOfT.getCanonicalName(), typeOfT);
        List<T> list = query.list();
        return list;
    }
}
