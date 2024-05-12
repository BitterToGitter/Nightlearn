package jakimovich.nightlearn.classes;

import java.util.ArrayList;

public class Learnset {

    private String name;
    private ArrayList<Learncard> learncards;
    private Quiz quizSettings;

    public Learnset(String name, Quiz quizSettings) {
        this.name = name;
        this.learncards = new ArrayList<Learncard>();
        this.quizSettings = quizSettings;
    }

    public Learnset(String name, Quiz quizSettings, ArrayList<Learncard> learncards) {
        this.name = name;
        this.learncards = learncards;
        this.quizSettings = quizSettings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int cardsLearned() {
        int learned = 0;
        for (Learncard learncard : learncards){
            if (learncard.getLearned())
                learned++;
        }
        return learned;
    }

    public int cardsSeen(){
        int seen = 0;
        for (Learncard learncard : learncards){
            if (learncard.getTimesSeen() > 0){
                seen++;
            }
        }
        return seen;
    }

    public int countProgress(){
        if(learncards.size() != 0) {
            return cardsLearned() * 100 / learncards.size();
        } else {
            return 0;
        }
    }

    public ArrayList<Learncard> getLearncards() {
        return learncards;
    }

    public void addLearncard(Learncard learncard){
        learncards.add(learncard);
    }
    public void deleteLearncard(int n){
        learncards.remove(n);
    }

    public void setQuizSettings(Quiz quizSettings) {
        this.quizSettings = quizSettings;
    }


    public Quiz getQuizSettings() {
        return quizSettings;
    }
}
