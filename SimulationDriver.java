import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SimulationDriver{
    private static final Random rand = new Random();

    public static void main(String[] args){
        VotingService votinService = new VotingService();

        //Simulation 1: Single choice question (true/flase)
        System.out.println("STARTING SIMULATION 1: SINGLE CHOICE QUESTION");
        List<String> singleOptions = Arrays.asList("1. Right", "2. Wrong");
        Question SingleChoiceQuestion = new SingleChoiceQuestion(
            "Is Java a purely object-oriented programming language?",
            singleOptions
        );
        votinService.configureQuestion(SingleChoiceQuestion);
        //Randomly generate between 15 and 50 students for this session
        int randomStudentCount1 = rand.nextInt(26)+15;
        simulateSubmissions(votinService, singleOptions, false, randomStudentCount1);
        votinService.displayResults();

        //Simulation 2: Multiple choice question (Select all that apply)
        System.out.println("STARTING SIMULATION 2: MULTIPLE CHOICE QUESTION");
        List<String> multiOptions = Arrays.asList("A", "B", "C", "D");
        Question multipleChoiceQuestion = new MultipleChoiceQuestion(
            "Which of the following are Java OOP principles?",
            multiOptions
        );
        votinService.configureQuestion(multipleChoiceQuestion);
        //Randomly generate between 15 to 50 students for second session
        int randomStudentCount2 = rand.nextInt(36)+15;
        simulateSubmissions(votinService, multiOptions, true, randomStudentCount2);
        votinService.displayResults();
    }

    //Helper method to generate random student groups, handle thier answers
    //and purposefully simulate duplicate submissions
    private static void simulateSubmissions(VotingService service, List<String>  choices, boolean isMultiple, int totalStudents){
        List<Student> studentList = new ArrayList<>();

        //1, Generate unique random students
        for(int i = 1; i <= totalStudents; i++){
            Student student = new Student("STU_" + String.format("%04d", rand.nextInt(1000)));
            generateRandomAnswerForStudent(student, choices, isMultiple);
            studentList.add(student);
        }

        //2, Initial round of submissions
        for(Student s : studentList){
            service.submitAnswer(s);
        }

        //3, Duplicate simulation: pick a few student to change their mind and re-submit
        System.out.println("...Simulating a few students changing their votes (testing duplicate override)...");
        int duplicateCount = Math.min(5, studentList.size());
        for(int i = 0; i < duplicateCount; i++){
            Student studentChangingVote = studentList.get(i);
            //Change their answer.
            generateRandomAnswerForStudent(studentChangingVote, choices, isMultiple);
            //Resubmit
            service.submitAnswer(studentChangingVote);
        }
    }

    //Generate a valid answer array
    private static void generateRandomAnswerForStudent(Student student, List<String> choices, boolean isMultiple){
        List<String> chosen = new ArrayList<>();
        if(!isMultiple){
            //Pick exactly one.
            chosen.add(choices.get(rand.nextInt(choices.size())));
        }else{
            //Pick a random number of choices
            int numberOfChoicesToPick = rand.nextInt(choices.size()) + 1;
            while(chosen.size() < numberOfChoicesToPick){
                String pick = choices.get(rand.nextInt(choices.size()));
                if(!chosen.contains(pick)){
                    chosen.add(pick);
                }
            }
        }
        student.setChosenAnswers(chosen);
    }
}