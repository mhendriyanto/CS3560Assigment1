import java.util.ArrayList;
import java.util.List;

public class VotingService {
    private Question question;
    private List<Submission> submissionList;

    //inner class to group a student's ID and their answers together
    private static class Submission{
        private final String studentId;
        private List<String> answers;

        public Submission(String studentId, List<String> answers){
            this.studentId = studentId;
            this.answers = answers;
        }

        public String getStudentId(){ 
            return studentId; 
        }
        public List<String> getAnswers(){ 
            return answers; 
        }
        public void setAnswers(List<String> answers){ 
            this.answers = answers; 
            }
    }

    public VotingService(){
        this.submissionList = new ArrayList<>();
    }

    //Configures the service with a specific question type and options.
    //Clears previous submissions to ensure a fresh session.
    public void configureQuestion(Question question){
        this.question = question;
        this.submissionList.clear(); 
    }

    //Receives and processes a student's submission.
    //Manually checks for duplicate student IDs to overwrite old votes.
    public void submitAnswer(Student student){
        if (question == null){
            System.out.println("Error: No question configured in the Voting Service.");
            return;
        }

        // 1. Validate submission rules first
        if (!question.isValidSubmission(student.getChosenAnswers())){
            System.out.println("Rejected invalid submission from student: " + student.getId());
            return;
        }

        // 2. Search to check if this student has already voted
        boolean dynamicUpdateSet = false;
        for (Submission existingSubmission : submissionList){
            if (existingSubmission.getStudentId().equals(student.getId())) {
                // Student found: Overwrite their previous answer with the latest one
                existingSubmission.setAnswers(student.getChosenAnswers());
                dynamicUpdateSet = true;
                break; // Break early since IDs are unique
            }
        }

        // 3. If the student hasn't voted yet, add them as a new submission record
        if (!dynamicUpdateSet){
            submissionList.add(new Submission(student.getId(), student.getChosenAnswers()));
        }
    }

    //Computes statistics and prints them clearly to standard output.
    public void displayResults(){
        if (question == null) {
            System.out.println("No data to display.");
            return;
        }

        // Standard Output Print Header
        System.out.println("\n--- POLL RESULTS (LIST-BASED) ---");
        System.out.println("Question: " + question.getQuestionText());
        System.out.println("Total Unique Submissions: " + submissionList.size());

        // For each candidate answer, iterate through the submission list to calculate the count
        for (String candidateOption : question.getCandidateAnswers()){
            int currentOptionCount = 0;
            
            for (Submission sub : submissionList){
                if (sub.getAnswers().contains(candidateOption)){
                    currentOptionCount++;
                }
            }
            
            System.out.println(candidateOption + " : " + currentOptionCount);
        }
        System.out.println("\n");
    }
}