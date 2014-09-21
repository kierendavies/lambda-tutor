package za.ac.uct.cs.ddd.lambda.tutor;

public class Lesson {

    private String lessonText;
    private ProblemSet problems;

    public Lesson(String text, ProblemSet problems){
        this.lessonText = text;
        this.problems = problems;
    }

    public void setLessonText(String text){
        lessonText = text;
    }

    public String getLessonText(){
        return lessonText;
    }

}
