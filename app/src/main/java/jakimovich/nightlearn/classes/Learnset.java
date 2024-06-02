package jakimovich.nightlearn.classes;

import java.util.ArrayList;
import java.util.Random;

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

    private ArrayList<Learncard> getLearnedCards(){
        ArrayList<Learncard> learnedCards = new ArrayList<>();
        for (Learncard learncard : learncards){
            if (learncard.getLearned()){
                learnedCards.add(learncard);
            }
        }
        return learnedCards;
    }

    private ArrayList<Learncard> getUnlearnedCards(){
        ArrayList<Learncard> unlearnedCards = new ArrayList<>();
        for (Learncard learncard : learncards){
            if (!learncard.getLearned()){
                unlearnedCards.add(learncard);
            }
        }
        return unlearnedCards;
    }

    /**
     * Returns a random card from the learnset. If unlearnedCardPriority is true, the method will return an unlearned card with a 70% probability.
     * @param unlearnedCardPriority
     * @return
     */
    public Learncard getRandomCard(boolean unlearnedCardPriority){
        if(unlearnedCardPriority){

            if(new Random().nextFloat() < 0.7f){
                if (!getUnlearnedCards().isEmpty()) {
                    return getUnlearnedCards().get((int) (Math.random() * getUnlearnedCards().size()));
                }
            } else {
                if (!getLearnedCards().isEmpty()){
                return getLearnedCards().get((int) (Math.random() * getLearnedCards().size()));
                }
            }

        }
        return learncards.get((int) (Math.random() * learncards.size()));
    }

    public int countCardsLearned() {
        int learned = 0;
        for (Learncard learncard : learncards){
            if (learncard.getLearned())
                learned++;
        }
        return learned;
    }

    public int countCardsSeen(){
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
            return countCardsLearned() * 100 / learncards.size();
        } else {
            return 0;
        }
    }

    public void updateLearncard(Learncard learncard){
        for (int i = 0; i < learncards.size(); i++){
            if (learncards.get(i).getDefinition().equals(learncard.getDefinition())){
                learncards.set(i, learncard);
            }
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

    public Quiz getQuizSettings() {
        return quizSettings;
    }

    public void setQuizSettings(Quiz quizSettings) {
        this.quizSettings = quizSettings;
    }

}
