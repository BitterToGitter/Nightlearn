package jakimovich.nightlearn.classes;

public class Learncard {
    private String definition;
    private String explanation;
    private Boolean learned = false;
    private int timesSeen = 0;
    private int timesAnsweredRight = 0;

    public Learncard(String definition, String explanation) {
        this.definition = definition;
        this.explanation = explanation;
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

    public void hasBeenSeen(){
        timesSeen++;
    }

    public int getTimesAnsweredRight() {
        return timesAnsweredRight;
    }

    public void answeredRight() {
        timesAnsweredRight++;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

}


