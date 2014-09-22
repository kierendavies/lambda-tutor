package za.ac.uct.cs.ddd.lambda.tutor;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper around Problem set to add some explanatory text about the problemSet encountered.
 */
public class Lesson {

    private String name;
    private String text;
    private ProblemSet problemSet;

    public Lesson(String name, String text, ProblemSet problems){
        this.name = name;
        this.text = text;
        this.problemSet = problems;
    }

    /**
     * Create a lesson from a given xml file. The general format for the file is as follows:
     * <lesson>
     *     <name>Lesson 1</name>
     *     <text>The first lesson entails...</text>
     *     <problemset>
     *         <name>Problem Set 1</name>
     *         <problems>
     *             ...
     *         </problems>
     *     </problemset>
     * </lesson>
     *
     * Where each problem set follows the same format as found in the problem set class.
     * @param url A string containing a path to an xml file with a problem set defined.
     */
    public Lesson(String url){
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(url);

        try {
            Document doc = builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            name = rootNode.getChildText("name");
            text = rootNode.getChildText("text");

            Element problemSetNode = rootNode.getChild("problemset");
            List<Element> problemNodes = problemSetNode.getChild("problems").getChildren();
            List<Problem> problems = new ArrayList<>();
            for (Element problemNode : problemNodes) {
                problems.add(Problem.parseProblemNode(problemNode));
            }

            problemSet = new ProblemSet(problemSetNode.getChildText("name"), problems);
        } catch (JDOMException | IOException | NoSuchFieldException e) {
            e.printStackTrace();
        }
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
        return new ProblemSet(problemSet);
    }

}
