package jakimovich.nightlearn.classes;

public class Quiz {

    private int questionsAmount, answerTimeSec, rightAnswersNumToBeLearned, questionType;

    public Quiz (int questionsAmount,int answerTimeSec, int rightAnswersNumToBeLearned, int questionType){
        this.questionsAmount = questionsAmount;
        this.answerTimeSec = answerTimeSec;
        this.rightAnswersNumToBeLearned = rightAnswersNumToBeLearned;
        this.questionType = questionType;
    }

    public Quiz(){
        this.questionsAmount = 5;
        this.answerTimeSec = 10;
        this.rightAnswersNumToBeLearned = 4;
        questionType = 1;
    }

    public int getQuestionsAmount() {
        return questionsAmount;
    }

    public void setQuestionsAmount(int questionsAmount) {
        this.questionsAmount = questionsAmount;
    }

    public int getAnswerTimeSec() {
        return answerTimeSec;
    }

    public void setAnswerTimeSec(int answerTimeSec) {
        this.answerTimeSec = answerTimeSec;
    }

    public int getRightAnswersNumToBeLearned() {
        return rightAnswersNumToBeLearned;
    }

    public void setRightAnswersNumToBeLearned(int rightAnswersNumToBeLearned) {
        this.rightAnswersNumToBeLearned = rightAnswersNumToBeLearned;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }
}
