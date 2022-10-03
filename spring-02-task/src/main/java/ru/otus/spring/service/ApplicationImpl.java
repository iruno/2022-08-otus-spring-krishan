package ru.otus.spring.service;

import java.util.InputMismatchException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

@RequiredArgsConstructor
@Service
public class ApplicationImpl implements Application {

    private final QuizService quizService;

    private final IOService ioService;

    private final QuizConfiguration quizConfiguration;

    @Override
    public void run() {

        Student student = getStudent();
        List<Question> questions = quizService.findAll();

        int correctAnswersCount = 0;
        for (Question question : questions) {
            ioService.out(question.getQuestion());
            List<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                ioService.out(String.format("%d) %s", i, answers.get(i)));
            }

            int answer = readAnswer(answers.size() - 1);
            if (question.getRightChoice().equals(answer)) {
                correctAnswersCount++;
            }
        }

        double score = ((double) correctAnswersCount / (double) questions.size()) * 100;
        printResult(student, score);
    }

    private int readAnswer(int maxAnswerAllowed) {
        int answer = ioService.readInt("Please choose a valid answer:");
        while (answer < 0 || answer > maxAnswerAllowed) {
            answer = ioService.readInt(
                    String.format("Please enter digit from [%d..%d] range", 0, maxAnswerAllowed));
        }
        return answer;
    }

    private Student getStudent() {
        ioService.out("Hello! Welcome to the test service!");

        Student student = new Student();
        student.setSurname(ioService.readLn("Please enter your surname:"));
        student.setName(ioService.readLn("Please enter your name:"));
        return student;
    }

    private void printResult(Student student, double score) {
        ioService.out(System.lineSeparator());
        ioService.out(String.format("%s %s: your score is %.2f", student.getName(), student.getSurname(), score));
        ioService.out(score > quizConfiguration.getMinScore() ? "Congratulations! You have passed the test!"
                : "Unfortunately you have not passed the test. Please try again later!");
    }
}
