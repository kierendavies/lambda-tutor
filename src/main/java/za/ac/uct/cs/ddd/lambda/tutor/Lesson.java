package za.ac.uct.cs.ddd.lambda.tutor;

public class Lesson {

    String lessonText;
    ProblemSet problems;

    public Lesson(String text, ProblemSet problems){
        this.lessonText = text;
        this.problems = problems;
    }
}
