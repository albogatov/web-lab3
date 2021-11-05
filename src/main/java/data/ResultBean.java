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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultBean implements Serializable {
    private Result newResult = new Result();

    private List<Result> results = new ArrayList<Result>();

    private SessionFactory hibernateSessionFactory;
    private Session session;
    private Transaction transaction;

    public ResultBean() {
        newResult = new Result();
        results = new ArrayList<Result>();
        hibernateSessionFactory = HibernateUtility.getSessionFactory();
        session = hibernateSessionFactory.openSession();
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

    public void addResult() {
        transaction = session.beginTransaction();
        session.save(newResult);
        transaction.commit();
    }

    public void eraseResults() {
        transaction = session.beginTransaction();
        session.flush();
        transaction.commit();
    }
}
