package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
public class ProgramType implements Serializable 
{
	static Logger log = Logger.getLogger(DiaryBean.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
	private Integer id;
	private String programName;
	private ArrayList<ProgramType> pts = new ArrayList<ProgramType>();


	//----------------------------------------------------	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public ArrayList<ProgramType> getPts() 
	{
		return pts;
	}

	public void setPts(ArrayList<ProgramType> pts) 
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
	public String SaveProgram() //method which save  new User 
	   {
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try 
	       {
	       log.debug("Session.beginTransaction process started");
	          transaction = session.beginTransaction();
	          
	          ProgramType pm = new ProgramType();
	          pm.setProgramName(programName);
	 
	          session.save(pm);
	           transaction.commit();
	        
	       log.debug("New Training Program Type saved succesfully");
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
	
	
//----------------------------------------------------
	
	public ProgramType()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try { 
            transaction = session.beginTransaction();
                        List programtypes = session.createQuery("from programTypes").list();
            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
            {
                ProgramType programType = (ProgramType) iterator.next();
                pts.add(programType);
                System.out.println(pts.toArray().toString());
            }          
            
            System.out.println(pts.toArray().toString());
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

					