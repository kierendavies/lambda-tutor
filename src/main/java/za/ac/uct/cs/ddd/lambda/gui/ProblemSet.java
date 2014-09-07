package za.ac.uct.cs.ddd.lambda.gui;

public class ProblemSet {

    private Problem[] problems;

    private class Problem{
        private int problemNumber;
        private String text;

        private Problem(int problemNumber, String text){
            this.problemNumber = problemNumber;
            this.text = text;
        }
    }

    public ProblemSet(String[] problemText){
        for (int i = 0; i < problemText.length; i++) {
            problems[i] = new Problem(i+1, problemText[i]);
        }
    }

    public String[] getTitles(){
        String[] titles = new String[problems.length];
        for (int i = 0; i < problems.length; i++) {
            titles[i] = "Problem " + problems[i].problemNumber;
        }
        return titles;
    }

    public String getText(int problemNumber){
        return problems[problemNumber-1].text;
    }
}
