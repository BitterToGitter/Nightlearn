package jakimovich.nightlearn;

public class Quiz {

    private int questionsNum, answerTimeSec, numForLearnedCards, questionType;

    public Quiz (int questionsNum,int answerTimeSec, int numForLearnedCards, int questionType){
        this.questionsNum = questionsNum;
        this.answerTimeSec = answerTimeSec;
        this.numForLearnedCards = numForLearnedCards;
        this.questionType = questionType;
    }

    public int getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(int questionsNum) {
        this.questionsNum = questionsNum;
    }

    public int getAnswerTimeSec() {
        return answerTimeSec;
    }

    public void setAnswerTimeSec(int answerTimeSec) {
        this.answerTimeSec = answerTimeSec;
    }

    public int getNumForLearnedCards() {
        return numForLearnedCards;
    }

    public void setNumForLearnedCards(int numForLearnedCards) {
        this.numForLearnedCards = numForLearnedCards;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }
}
