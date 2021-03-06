package com.trainingdiary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean
@SessionScoped
public class Training implements Serializable 
{

private static final long serialVersionUID = 1L;

static Logger log = Logger.getLogger(Training.class);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
	  
  public Integer id;
  public String description;
  public String choosedDiary;
  public HashMap<String,Object> allDiaries = new HashMap<String,Object>();
	



public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getChoosedDiary() {
	return choosedDiary;
}

public void setChoosedDiary(String choosedDiary) {
	this.choosedDiary = choosedDiary;
}

public HashMap<String, Object> getAllDiaries() {
	return allDiaries;
}

public void setAllDiaries(HashMap<String, Object> allDiaries) {
	this.allDiaries = allDiaries;
}

	//------------------------------------------------------------
	public void SaveTraining() //method which save  new User 
	   {
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try 
	       {
	       log.debug("Session.beginTransaction process started");   
	          transaction = session.beginTransaction();
	          Training training = new Training();
	          training.setChoosedDiary(choosedDiary);
	          training.setDescription(description);
	          session.save(training);
	           transaction.commit();
	       log.debug("Records inserted sucessessfully");
	       FacesContext context = FacesContext.getCurrentInstance();  
    	   context.addMessage(null, new FacesMessage("Training saved succesfull")); 
    	   
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
            List programtypes = session.createQuery("from DiaryBean").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
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
