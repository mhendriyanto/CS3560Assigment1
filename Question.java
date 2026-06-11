import java.util.List;

public abstract class Question{
    private final String questionText;
    private final List<String> candidateAnswers;

    public Question(String questionText, List<String> candidateAnswers){
        this.questionText = questionText;
        this.candidateAnswers = candidateAnswers;
    }

    public String getQuestionText(){
        return questionText;
    }

    public List<String> getCandidateAnswer(){
        return candidateAnswers;
    }

    //Method validates if a student submission matches the questions rules
    //Overidden by subclasses to handle single vs multiple choice validation
    public abstract boolean isValidSubmission(List<String> submission);
}