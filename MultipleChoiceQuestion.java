import java.util.List;

public class MultipleChoiceQuestion extends Question{

    public MultipleChoiceQuestion(String questionText, List<String> candidateAnswers){
        super(questionText, candidateAnswers);
    }

    @Override
    public boolean isValidSubmission(List<String> submission){
        if(submission == null || submission.isEmpty()){
            return false; 
        }
        //Every choice must be one of the configured answers.  
        for(String choice : submission){
            if(!getCandidateAnswer().contains(choice)){
                return false;
            }
        }
        return true;
    }
}