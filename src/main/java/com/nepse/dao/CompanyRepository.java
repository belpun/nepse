package com.nepse.dao;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import com.nepse.domain.Company;
import com.nepse.domain.CompanyData;

public class CompanyRepository extends GenericRepository implements ICompanyRepository{
	
	@Transactional
	@Override
	public Company getCompanyBySymbol(String symbol) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

//			session.save(entity);
			
			String hql = "FROM Company c where c.symbol = :symbol";
			Query query = session.createQuery(hql);
			query.setParameter("symbol", symbol);
			Company result = (Company) query.uniqueResult();

			// Committing the change in the database.
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();

			// Rolling back the changes to make the data consistent in case of
			// any failure
			// in between multiple database write operations.
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public CompanyData getCompanyData(String symbol, Date date) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

//			session.save(entity);
			
			String hql = "FROM CompanyData c where c.company.symbol = :symbol and c.date = :date";
			Query query = session.createQuery(hql);
			query.setParameter("symbol", symbol);
			query.setDate("date", date);
			CompanyData result = (CompanyData) query.uniqueResult();

			// Committing the change in the database.
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();

			// Rolling back the changes to make the data consistent in case of
			// any failure
			// in between multiple database write operations.
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	@Override
	public Date getLatestDate(String symbol) {
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

//			session.save(entity);
			
			String hql = "select max(c.date) FROM CompanyData c where c.company.symbol = :symbol ";
			Query query = session.createQuery(hql);
			query.setParameter("symbol", symbol);
			Date result = (Date) query.uniqueResult();

			// Committing the change in the database.
			return result;

		} catch (Exception ex) {
			ex.printStackTrace();

			// Rolling back the changes to make the data consistent in case of
			// any failure
			// in between multiple database write operations.
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

}
