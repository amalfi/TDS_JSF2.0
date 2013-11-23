package com.trainingdiary;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;

import com.trainingdiary.database.HibernateUtil;


@javax.persistence.Entity
@ManagedBean
@RequestScoped
public class DiaryBean implements Serializable
{
	@ManagedProperty(value="#{programType}") 
	private ProgramType programType;
	
	public ProgramType getProgramType() 
	{
		return programType;
	}
	public void setProgramType(ProgramType programType) 
	{
		this.programType = programType;
	}
//---------------------------------------------------------	
	
	
	static Logger log = Logger.getLogger(DiaryBean.class);
	private static final long serialVersionUID = 1L;
	private String nameOfDiary;
	private Date diaryCreationDate;
	private String diaryDescription;
	private String choosedTrainingPlan;
    public HashMap<String,Object> allDiaries = new HashMap<String,Object>();
    public String choosedDiary;
    boolean editable;
//Lists of diaries properties 
    private List<DiaryBean> diaryDescriptions;
    
    

	
    public List<DiaryBean> getDiaryDescriptions() {
		return diaryDescriptions;
	}
	public void setDiaryDescriptions(List<DiaryBean> diaryDescriptions) {
		this.diaryDescriptions = diaryDescriptions;
	}
    
//

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
	
	public String getChoosedDiary() {
		return choosedDiary;
	}

	public void setChoosedDiary(String choosedDiary) {
		this.choosedDiary = choosedDiary;
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
	
	public HashMap<String, Object> getAllDiaries() {
		return allDiaries;
	}

	public void setAllDiaries(HashMap<String, Object> allDiaries) {
		this.allDiaries = allDiaries;
	}
	
//------------------------------------------------------------------	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
//------------------------------------------------------------------
	  public String SaveDiary() //method which save diary0++
	   {
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try 
	       {
	       log.debug("Session.beginTransaction process started");
	          transaction = session.beginTransaction();
	          DiaryBean diaryBean = new DiaryBean();
	       log.debug("Setting properties of new diary : "+choosedTrainingPlan+" , " + diaryCreationDate + ", " + diaryDescription + ", " + nameOfDiary);
	          diaryBean.setChoosedTrainingPlan(programType.getProgramName());
	          diaryBean.setDiaryCreationDate(diaryCreationDate);
	          diaryBean.setDiaryDescription(diaryDescription);
	          diaryBean.setNameOfDiary(nameOfDiary);
	          
	          session.save(diaryBean);
	           transaction.commit();
	        
	       log.debug("Diary created succesfully");

    	   FacesContext context = FacesContext.getCurrentInstance();  
    	   context.addMessage(null, new FacesMessage("Diary saved successfully")); 
			
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

		//getChoosedTrainingDiaryProperties
		public HashMap<String,Object> getChoosedTrainingDiaryProperties()
		{
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        try 
	        { 
	            transaction = session.beginTransaction();
	            List programtypes = session.createQuery("from DiaryBean ").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
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
		
		public void onEdit(RowEditEvent event)
		{
	        FacesMessage msg = new FacesMessage("Row edited");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	    
	    public void onCancel(RowEditEvent event) 
	    {
	        FacesMessage msg = new FacesMessage("Car Cancelled");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	    
}
                    