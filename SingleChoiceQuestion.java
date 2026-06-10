import java.util.List;

public class SingleChoiceQuestion extends Question{

    public SingleChoiceQuestion(String questionText, List<String> candidateAnswers){
        super(questionText, candidateAnswers);
    }

    //Override
    public boolean isValidSubmission(List<String> submission){
        //must choose exactly one option, and it must be a valid candiate answer
        if(submission == null || submission.size() != 1){
            return false;
        }
        return getCandiateAnswer().contains(submission.get(0))
    }
}