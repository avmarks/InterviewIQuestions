package com.marksalexandra.interviewquestions;

import org.w3c.dom.Text;

public class InterviewQuestionsModel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }

    public String getTermMeaning() {
        return TermMeaning;
    }

    public void setTermMeaning(String termMeaning) {
        TermMeaning = termMeaning;
    }

    public String Term;
    public String TermMeaning;



    public InterviewQuestionsModel() {
    }


    public InterviewQuestionsModel(int id, String term, String termMeaning) {
        this.id = id;
        Term = term;
        TermMeaning = termMeaning;
    }
}
