package com.youyou.math;

public class QA {

    private String questions;

    private String answers;

    public QA(String questions, String answers) {
        this.questions = questions;
        this.answers = answers;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
