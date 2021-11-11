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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResultBean implements Serializable {
    private Result newResult = new Result();
    private String persistenceUnitName = "result";
    private List<Result> results = new ArrayList<Result>();

    private SessionFactory hibernateSessionFactory;
    private Session session;
    private Transaction transaction;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    public ResultBean() {
//        System.out.println("INIT");
        newResult = new Result(0,0,1);
        results = new ArrayList<Result>();
        hibernateSessionFactory = HibernateUtility.getSessionFactory();
        session = hibernateSessionFactory.openSession();
//        transaction = session.getTransaction();
        loadResults();
        session.close();
    }

    public void loadResults() {
        try {
            session = hibernateSessionFactory.openSession();
            transaction = session.beginTransaction();
            results = (ArrayList<Result>) session.createSQLQuery("SELECT * FROM RESULT_TBL").addEntity(Result.class).list();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            session.close();
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
//        System.out.println("CALLED");
        try {
            long begin = System.nanoTime();
            String currentTime = formatter.format(new Date(System.currentTimeMillis()));
            session = hibernateSessionFactory.openSession();
            transaction = session.beginTransaction();
            newResult.setCurrTime(currentTime);
            newResult.checkHit();
            double exeTime = (System.nanoTime() - begin) * Math.pow(10, -9);
            newResult.setExecutionTime(exeTime);
            results.add(newResult);
            session.save(newResult);
//        entityManager.persist(newResult);
            transaction.commit();
            newResult = new Result(0,0,1);
            session.close();
//            System.out.println("success?");
//            System.out.println(results.get(0).toString());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            session.close();
        }
        return "update";
    }

    public void deleteResult(Result result) {
        try {
//            session = hibernateSessionFactory.openSession();
//            transaction = session.beginTransaction();
            session.delete(result);
//            transaction.commit();
//            session.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            session.close();
        }
    }

    public String eraseResults() {
        try {
            session = hibernateSessionFactory.openSession();
            transaction = session.beginTransaction();
            results.clear();
            List<Result> toDelete = (ArrayList<Result>) session.createSQLQuery("SELECT * FROM RESULT_TBL").addEntity(Result.class).list();
            for (Result erased : toDelete) {
                deleteResult(erased);
            }
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            session.close();
        }
        return "update";
    }
}
