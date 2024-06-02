package jakimovich.nightlearn.classes;

public class Learncard {
    private String definition;
    private String explanation;
    private Boolean learned;
    private int timesSeen;
    private int timesAnsweredRight;

    public Learncard(String definition, String explanation) {
        this.definition = definition;
        this.explanation = explanation;
        this.timesSeen = 0;
        this.timesAnsweredRight = 0;
        this.learned = false;
    }

    public Learncard(String definition, String explanation, int timesSeen, int timesAnsweredRight, boolean learned){

        this.definition = definition;
        this.explanation = explanation;
        this.timesSeen = timesSeen;
        this.timesAnsweredRight = timesAnsweredRight;
        this.learned = learned;

    }


    public String getDefinition() {
        return definition;
    }

    public String getExplanation() {
        return explanation;
    }

    public Boolean getLearned() {
        return learned;
    }

    public int getTimesSeen() {
        return timesSeen;
    }

    public int getTimesAnsweredRight() { return timesAnsweredRight; }

    public void setDefinition(String definition) {this.definition = Character.toUpperCase(definition.charAt(0)) + definition.substring(1);;}

    public void setExplanation(String explanation) { this.explanation = Character.toUpperCase(explanation.charAt(0)) + explanation.substring(1); }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

    public void setTimesAnsweredRight(int timesAnsweredRight) {this.timesAnsweredRight = timesAnsweredRight;}

    public void setTimesSeen(int timesSeen) {this.timesSeen = timesSeen;}

    public void onSeen(){
        timesSeen++;
    }

    public void onAnsweredRight() {
        timesAnsweredRight++;
    }

    public boolean checkLearned(int rightAnswersNumToBeLearned){
        if (timesAnsweredRight >= rightAnswersNumToBeLearned){
            learned = true;
            return true;
        }
        return false;
    }
}


