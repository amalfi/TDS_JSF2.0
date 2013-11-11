package com.trainingdiary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trainingdiary.database.HibernateUtil;


@javax.persistence.Entity
@ManagedBean(name="training")
@SessionScoped
public class Training 
{
	
  static Logger log = Logger.getLogger(Training.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
	  
  public static String id;
  public static String description;
  public static String choosedDiary;
  public HashMap<String,Object> allDiaries = new HashMap<String,Object>();
	
  
	public HashMap<String, Object> getAllDiaries() 
	{
		return allDiaries;
	}
	
	public void setAllDiaries(HashMap<String, Object> allDiaries) 
	{
		this.allDiaries = allDiaries;
	}
	
	public static String getChoosedDiary() 
	{
		return choosedDiary;
	}
	public static void setChoosedDiary(String choosedDiary) 
	{
		Training.choosedDiary = choosedDiary;
	}
	
	public static String getId() 
	{
		return id;
	}
	public static void setId(String id) 
	{
		Training.id = id;
	}
	
	public static String getDescription() 
	{
		return description;
	}
	public static void setDescription(String description) 
	{
		Training.description = description;
	}
	  
//------------------------------------------------------------
	public String SaveTraining() //method which save  new User 
	   {
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try 
	       {
	       log.debug("Session.beginTransaction process started");   
	          transaction = session.beginTransaction();
	          Training training = new Training();
	          Training.setChoosedDiary(choosedDiary);
	          Training.setDescription(description);
	          session.save(training);
	           transaction.commit();
	       log.debug("Records inserted sucessessfully");
	       } catch (HibernateException e) 
	       
	       {
	           transaction.rollback();
	           e.printStackTrace();
	           log.debug(e.getMessage());
	       } 
	       
	       finally 
	       {
	           session.close();
	       }
		return "";
	   }
	
	public HashMap<String,Object> getLoadDiaries()
	{
		allDiaries=LoadDiaries();
		return allDiaries;
	}
	
	
	public HashMap<String,Object> LoadDiaries()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try 
        { 
            transaction = session.beginTransaction();
            List programtypes = session.createQuery("from DiaryBean").list();
            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
            {
                DiaryBean diary = (DiaryBean) iterator.next();
                allDiaries.put(diary.getNameOfDiary().toString(), diary);
                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
            }          	
            transaction.commit();
        } 
        catch (HibernateException e) 
        {
            transaction.rollback();
            e.printStackTrace();  
            log.debug(e.getMessage());
        } 
        finally 
        {
            session.close();
        }
		return allDiaries;
	}
	
}
