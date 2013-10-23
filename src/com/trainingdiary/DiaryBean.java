package com.trainingdiary;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
public class DiaryBean implements Serializable
{
	static Logger log = Logger.getLogger(DiaryBean.class);
	private static final long serialVersionUID = 1L;
	private String nameOfDiary;
	private Date diaryCreationDate;
	private String diaryDescription;
	private String choosedTrainingPlan;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
	private Integer id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDiaryCreationDate() {
		return diaryCreationDate;
	}
	public void setDiaryCreationDate(Date diaryCreationDate) {
		this.diaryCreationDate = diaryCreationDate;
	}
	public String getNameOfDiary() {
		return nameOfDiary;
	}
	public void setNameOfDiary(String nameOfDiary) {
		this.nameOfDiary = nameOfDiary;
	}
	public String getDiaryDescription() {
		return diaryDescription;
	}
	public void setDiaryDescription(String diaryDescription) {
		this.diaryDescription = diaryDescription;
	}
	public String getChoosedTrainingPlan() {
		return choosedTrainingPlan;
	}
	public void setChoosedTrainingPlan(String choosedTrainingPlan) {
		this.choosedTrainingPlan = choosedTrainingPlan;
	}
//------------------------------------------------------------------
	  public String SaveDiary() //method which save  new User 
	   {
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try 
	       {
	       log.debug("Session.beginTransaction process started");
	          transaction = session.beginTransaction();
	          DiaryBean diaryBean = new DiaryBean();
	       log.debug("Setting properties of new diary : "+choosedTrainingPlan+" , " + diaryCreationDate + ", " + diaryDescription + ", " + nameOfDiary);
	          diaryBean.setChoosedTrainingPlan(choosedTrainingPlan);
	          diaryBean.setDiaryCreationDate(diaryCreationDate);
	          diaryBean.setDiaryDescription(diaryDescription);
	          diaryBean.setNameOfDiary(nameOfDiary);
	          
	          session.save(diaryBean);
	           transaction.commit();
	        
	       log.debug("Diary created succesfully");
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
	
	
    
}
                    