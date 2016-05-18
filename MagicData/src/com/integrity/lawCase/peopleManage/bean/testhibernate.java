package com.integrity.lawCase.peopleManage.bean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class testhibernate {
	public static void main(String[] args) {
		Configuration cfg = new Configuration();

		 cfg.configure("hibernate.cfg.xml");


		 SessionFactory sessionFactory = cfg.buildSessionFactory();


		Session session = null;
		 Transaction tx = null;

		try{

		session = sessionFactory.openSession();

		tx = session.beginTransaction();

		//…你的代码save,delete,update,get…
		Phonenumberobject phonenumberobject=new Phonenumberobject();
		phonenumberobject.setPoid(111);
		phonenumberobject.setPnid(1122);;
		session.save(phonenumberobject);
		tx.commit();

		}catch(Exception e){

		if(tx !=null)tx.rollback();throw e;

		}finally{

		if(session != null)session.close();

		} 
	}
}
