package com.trainingdiary;

import java.io.Serializable;










import java.util.Iterator;
import java.util.List;

import javax.faces.bean.*;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Entity;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.trainingdiary.database.HibernateUtil;
import com.trainingdiary.database.SaveUser;


@javax.persistence.Entity
@ManagedBean
@SessionScoped
public class User implements Serializable {

   static Logger log = Logger.getLogger(User.class);
   private static final long serialVersionUID = 1L;
   
   public static String sUserName; //test data, normally this data will be taken from database
   public static String sUserPassword;
   
   public String name;
   public String password;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
   public Integer id;

   public Integer getId() 
   {
	return id;
   }

   public void setId(Integer id) 
   {
	this.id = id;
   }

   public String getName() 
   {
      return name;
   }
 
   public void setName(String name) {
      this.name = name;
   }

   public String getPassword() {
      return password;
   } 

   public void setPassword(String password) {
      this.password = password;
   }
 
   public String login() //method which check if user which try to log in exist in database
   {
	   boolean bEquals=false;
	   Session session = HibernateUtil.getSessionFactory().openSession();
       Transaction transaction = null;
       try {
           transaction = session.beginTransaction();
                       List userList = session.createQuery("from User").list();
           for (Iterator iterator = userList.iterator(); iterator.hasNext();)
           {
               User user = (User) iterator.next();
               if((user.getName().toString().equals(name))&&(user.getPassword().toString().equals(password)))
               {
            	   bEquals=true;
               }
               log.debug("User : " + user.getName().toString());
           }         
           transaction.commit();
       } catch (HibernateException e) 
       {
           transaction.rollback();
           e.printStackTrace();
       } finally 
       {
           session.close();
       }
      
       if(bEquals==true)
       {
    	   return "user-panel";
    	         
       }
      
       else 
      {
         return "wrong-password-page";
      }
      
	   
   }
   
   public String SaveUser() //method which save  new User 
   {
       Session session = HibernateUtil.getSessionFactory().openSession();
       Transaction transaction = null;
       try 
       {
           transaction = session.beginTransaction();
          User user = new User();
          user.setName(name);
		  user.setPassword(password);
          
          session.save(user);
           transaction.commit();
           System.out.println("Records inserted sucessessfully");
       } catch (HibernateException e) 
       
       {
           transaction.rollback();
           e.printStackTrace();
       } 
       
       finally 
       {
           session.close();
       }
	return "";
		  
		
   }
   
 
}