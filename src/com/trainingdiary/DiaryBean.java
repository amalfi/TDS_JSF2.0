package com.trainingdiary;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@javax.persistence.Entity
@ManagedBean
@SessionScoped
public class DiaryBean implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String nameOfDiary;
	private String diaryCreationDate;
	private String diaryDescription;
	private String choosedTrainingPlan;
	
	
	public String getNameOfDiary() {
		return nameOfDiary;
	}
	public void setNameOfDiary(String nameOfDiary) {
		this.nameOfDiary = nameOfDiary;
	}
	public String getDiaryCreationDate() {
		return diaryCreationDate;
	}
	public void setDiaryCreationDate(String diaryCreationDate) {
		this.diaryCreationDate = diaryCreationDate;
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
	
	
	
    
}
                    