import java.util.List;

 //Represents a student participating in the poll.
 //Holds a unique identifier and their chosen answers

public class Student {
    private final String id;
    private List<String> chosenAnswers;

    public Student(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<String> getChosenAnswers() {
        return chosenAnswers;
    }

    public void setChosenAnswers(List<String> chosenAnswers) {
        this.chosenAnswers = chosenAnswers;
    }
}