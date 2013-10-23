package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trainingdiary.database.HibernateUtil;


public class ProgramTypes implements Serializable 
{
	static Logger log = Logger.getLogger(DiaryBean.class);
	private String programName;
	private ArrayList<ProgramTypes> pts = new ArrayList<ProgramTypes>();

	
	//----------------------------------------------------	
	public ArrayList<ProgramTypes> getPts() 
	{
		return pts;
	}

	public void setPts(ArrayList<ProgramTypes> pts) 
	{
		this.pts = pts;
	}

	
	public String getProgramName() 
	{
		return programName;
	}

	public void setProgramName(String programName) 
	{
		this.programName = programName;
	}
	
//----------------------------------------------------
	
	   public ArrayList<ProgramTypes> ProgramTypesList() 
	   {
		   
		   Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try {
	           transaction = session.beginTransaction();
	                       List programTypes = session.createQuery("from ProgramTypes").list();
	           for (Iterator iterator = programTypes.iterator(); iterator.hasNext();)
	           {
	               ProgramTypes pts = (ProgramTypes) iterator.next();
	               log.debug("Aktualnie wczytuje : " + pts.getProgramName().toString());
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
	      
	       return pts;
	   }
}

					