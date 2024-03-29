/*
 * Lambda Tutor
 * Copyright (C) 2014  Kieren Davies, David Dunn, Matthew Dunk
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package za.ac.uct.cs.ddd.lambda;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import za.ac.uct.cs.ddd.lambda.tutor.Problem;
import za.ac.uct.cs.ddd.lambda.tutor.ProblemSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A collection of static methods for marking lambda reductions in a given text file.
 */
public class Marker {

    /**
     Marks the file at answerFilename according to the problems found in the problemset defined in problemSetFilename.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSet The problemset containing the problems.
     * @param answerFilename The path to the file containing the lambda reductions to be marked.
     * @return The mark for the reductions in the answer file.
     * @throws java.io.IOException If the file at answerFilename doesn't exist.
     */
    private static double markReductionsFromPSet(ProblemSet problemSet, String answerFilename, String opts) throws IOException{
        // Determine options
        boolean debug = opts.contains("d");
        boolean verbose = opts.contains("v") || debug;

        // Submit the answers to the problems

        Problem currentProblem = problemSet.nextProblem();
        BufferedReader answerReader = new BufferedReader(new FileReader(answerFilename));

        // Eat empty lines at beginning of file
        String line = answerReader.readLine();
        while (line != null && line.isEmpty()) {
            line = answerReader.readLine();
        }
        List<String> messages;

        while (line != null) {
            // Submit lines to currentProblem until an empty line is found.

            if (line.isEmpty()) {
                // End of an answer
                if(debug) System.out.println("  Empty line - new problem");
                currentProblem = problemSet.nextProblem();
                while (line != null && line.isEmpty()) {
                    line = answerReader.readLine();
                }
            } else if(currentProblem != null){
                // There is a line ready to be submitted to a problem.
                currentProblem.submitStep(line);
                if(debug) System.out.println("  Line submitted: " + line);
                line = answerReader.readLine();
                if(debug) System.out.println("  Line read: "+line);
            } else {
                // No more problems
                break;
            }
            if(currentProblem != null && verbose){
                messages = currentProblem.getMessages();
                System.out.println(messages.size() > 0 ? messages.get(messages.size()-1) : "");
            }
        }

        return problemSet.getMark();
    }

    /**
     * Marks the file at answerFilename according to the problems found in the problemset defined in problemSetFilename.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSetFilename The path to the xml file containing the problemset with the problems.
     * @param answerFilename The path to the file containing the lambda reductions to be marked.
     * @return The mark for the reductions in the answer file.
     * @throws java.io.IOException If the file at answerFilename doesn't exist.
     */
    public static double markReductionsFromFile(String problemSetFilename, String answerFilename, String opts) throws IOException {
        ProblemSet problemSet = new ProblemSet(new File(problemSetFilename));

        return markReductionsFromPSet(problemSet, answerFilename, opts);
    }

    /**
     * Marks all of the files in the answerFolder according to the problems found in the problemset defined in
     * problemSetFilename.
     * This assumes the answer file consists only of newline-separated paragraphs of reductions and that the answers are
     * in the same order as found in the ProblemSet.
     *
     * @param problemSetFilename The path to the xml file containing the problemset with the problems.
     * @param answerFolder Path to the directory containing answer files.
     */
    public static void markReductionsFromDir(String problemSetFilename, String answerFolder, String opts) throws IOException {
        // Read in the questions
        ProblemSet problemSet = new ProblemSet(new File(problemSetFilename));

        // Find all of the .lam files in answerFolder
        if(!answerFolder.substring(answerFolder.length()-1).equals("/"))
            answerFolder += "/";

        ArrayList<String> answerFiles;
        try {
            answerFiles = new ArrayList<>(Arrays.asList(new File(answerFolder).list()));
        } catch (NullPointerException e){
            System.out.println("No such folder found. Please check that the folder "+answerFolder+" exists.");
            return;
        }

        for (int i = 0; i < answerFiles.size(); i++) {
            String filename = answerFiles.get(i);
            if(filename.length()<4 || !filename.substring(filename.length()-4).equals(".lam")){
                answerFiles.set(i, null);
            }
        }

        // Check the answers
        double mark;
        StringBuilder markList = new StringBuilder();
        for (String filename : answerFiles) {
            if(filename != null){
                mark = 0;
                try {
                    problemSet.reset();
                    mark = markReductionsFromPSet(problemSet, answerFolder+filename, opts);
                } catch (IOException e) {
                    System.out.println("The file "+filename+" could not be found in folder "+answerFolder+".");
                }

                markList.append(filename.substring(0, filename.length()-4));
                markList.append(" - ");
                markList.append(mark);
                markList.append("\n");
                if(opts.contains("v") || opts.contains("d")) System.out.println("***"+filename+" complete.\n");
            }
        }

        System.out.println("\n"+markList);
    }

    /**
     * Marks a given file's reductions
     * @param args Command-line arguments. The first is the path to the problem set, the second is the path to the
     *             answer file.
     */
    public static void main(String[] args) {

        if(args.length < 4){
            System.out.println("Too few options supplied. Please specify a problem set file and a file or folder for " +
                    "marking by supplying one of '-p <problemset> -f <filename>' or '-p <problemset> -d <directory>'. ");
        }

        OptionParser parser = new OptionParser();
        parser.acceptsAll(Arrays.asList("p", "pset"),
                "Supply the path to the problem set file for marking.")
                .withRequiredArg()
                .ofType(String.class);
        parser.acceptsAll(Arrays.asList("f", "file"),
                          "Supply the path to a single .lam file for marking.")
                .withRequiredArg()
                .ofType(String.class);
        parser.acceptsAll(Arrays.asList("d", "dir"),
                          "Supply the path to a directory containing .lam files for marking.")
                .withRequiredArg()
                .ofType(String.class);
        parser.acceptsAll(Arrays.asList("v", "verbose"),
                          "Print out each filename followed by the messages associated with each reduction" +
                          "(to see mistakes in reductions)");
        parser.acceptsAll(Arrays.asList("g", "debug"),
                          "As verbose, with additional debug messages. Shows all messages associated with reductions.");

        OptionSet options = parser.parse(args);

        String opts = "";
        if(options.has("debug"))
            opts += "d";
        if(options.has("verbose"))
            opts += "v";

        // Check that the problem set specification is an xml file
        if(options.has("pset")){
            String psetFilePath = (String)options.valueOf("pset");
            if(!psetFilePath.contains(".xml")){
                System.out.println("Invalid problem set file: "+psetFilePath+
                                   "\nShould be an xml file (*.xml)");
                System.exit(1);
            }
        }

        // Mark a single file
        if(options.has("pset") && options.has("file")) {
            try {
                System.out.println("\nScore for reductions in " + options.valueOf("file") +
                        "\nusing problem set " + options.valueOf("pset") + ": " +
                        markReductionsFromFile((String)options.valueOf("pset"), (String)options.valueOf("file"), opts)
                        +" %");
            } catch(FileNotFoundException e){
                System.out.println("File not found: " + e.getMessage() +
                        "\nPlease check that the specified file exists and that the filename passed is correct.");
            } catch (IOException e) {
                System.out.println("Problem reading file (or IOException): "+e.getMessage());
            }
        }
        // Mark all files in a directory
        if(options.has("pset") && options.has("dir")){
            try {
                System.out.println("Scores for .lam files in "+options.valueOf("dir"));
                System.out.println("Using problem set "+options.valueOf("pset")+":\n");
                markReductionsFromDir((String)options.valueOf("pset"), (String)options.valueOf("dir"), opts);
            } catch(FileNotFoundException e){
                System.out.println("File not found: " + e.getMessage() +
                        "\nPlease check that the specified file exists and that the filename passed is correct.");
            } catch (IOException e) {
                System.out.println("Problem reading file (or IOException): "+e.getMessage());
            }
        }
    }
}
