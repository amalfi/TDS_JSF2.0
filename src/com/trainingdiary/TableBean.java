package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.RowEditEvent;

import com.trainingdiary.database.HibernateUtil;

@javax.persistence.Entity
@ManagedBean
@RequestScoped
public class TableBean implements Serializable {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(TableBean.class);
	
	/*
	private String[] diaryDescriptions;
	private String[] choosedTrainingPlans;*/
	  private List<String> diaryDescriptions = new ArrayList<String>();
    private List<DiaryBean> allDiaries = new ArrayList<DiaryBean>();
    static int counter=0;

//-----------------------------------------------------------------------------------


//-----------------------------------------------------------------------------------
	public List<DiaryBean> LoadDiaries()
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
                allDiaries.add(diary);
                diaryDescriptions.add(String.valueOf(diary.getDiaryDescription().toString()));
                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
                counter++;
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

	public List<String> getDiaryDescriptions() {
		return diaryDescriptions;
	}

	public void setDiaryDescriptions(List<String> diaryDescriptions) {
		this.diaryDescriptions = diaryDescriptions;
	}

	public List<DiaryBean> getAllDiaries() {
		return allDiaries;
	}

	public void setAllDiaries(List<DiaryBean> allDiaries) {
		this.allDiaries = allDiaries;
	}

	public TableBean() 
	{
		allDiaries = LoadDiaries();	
	}


    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("DiaryBean Edited");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("DiaryBean Cancelled");

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
				