import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Teacher should quiz existing bots
 * It does not modify bots
 */
public class Teacher {
    File data_source;
    ArrayList<String> questions;
    ArrayList<Boolean> answers;

    double threshold = 0.2;

    public Teacher(String datafile) {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        data_source = new File(datafile);

        try (Scanner myReader = new Scanner(data_source)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String q = data.split(",")[0].toLowerCase();
                Parser.parseWords(q); // Add all the words early so we know how many data points we'll need initially
                questions.add(q);
                answers.add((Objects.equals(data.split(",")[1], "1")));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public double testBrain(Brain brain) {
        double[] discrepancies = new double[questions.size()];
        int i = 0;
        for (String question : questions) {
            double response = brain.process(Parser.parseWords(question));
            discrepancies[i] = Math.abs(response - (answers.get(i) ? 1.0 : 0.0));
            i++;
        }
        return Statistics.avg(discrepancies);
    }

    public static void main(String[] args) {
        Teacher church = new Teacher("data/emails.csv");
        for (int i = 0; i < church.questions.size(); i++) {
            System.out.print(church.answers.get(i));
            System.out.print("\t");
            System.out.println(church.questions.get(i));
        }
    }
}
