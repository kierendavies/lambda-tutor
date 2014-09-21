package za.ac.uct.cs.ddd.lambda.tutor;

/**
 * A wrapper around Problem set to add some explanatory text about the problems encountered.
 */
public class Lesson {

    private String name;
    private String text;
    private ProblemSet problems;

    public Lesson(String name, String text, ProblemSet problems){
        this.name = name;
        this.text = text;
        this.problems = problems;
    }

    public Lesson(String name, String text, String url){
        this.name = name;
        this.text = text;
        this.problems = new ProblemSet("Lesson problem set", url);
    }

    public void setLessonText(String text){
        this.text = text;
    }

    public String getLessonText(){
        return text;
    }

    public String getLessonName(){
        return name;
    }

    public ProblemSet getProblemSet(){
        return new ProblemSet(problems);
    }

}
