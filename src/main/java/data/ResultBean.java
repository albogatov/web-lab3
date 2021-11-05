package data;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import utils.HibernateUtility;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResultBean implements Serializable {
    private Result newResult = new Result();
    private String persistenceUnitName = "result";
    private List<Result> results = new ArrayList<Result>();

    private SessionFactory hibernateSessionFactory;
    private Session session;
    private Transaction transaction;

    public ResultBean() {
        newResult = new Result();
        results = new ArrayList<Result>();
        hibernateSessionFactory = HibernateUtility.getSessionFactory();
        session = hibernateSessionFactory.openSession();
        transaction = session.getTransaction();
        loadResults();
    }

    public void loadResults() {
        try {
            transaction.begin();
            results = (ArrayList<Result>) session.createSQLQuery("SELECT * FROM RESULT_TBL").addEntity(Result.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Result getNewResult() {
        return this.newResult;
    }

    public void setNewResult(Result newResult) {
        this.newResult = newResult;
    }

    public List<Result> getResults() {
        return this.results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String addResult() {
        try {
            transaction.begin();
            newResult.checkHit();
            session.save(newResult);
//        entityManager.persist(newResult);
            transaction.commit();
            newResult = new Result();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "update";
    }

    public String eraseResults() {
        try {
            transaction.begin();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "update";
    }
}
