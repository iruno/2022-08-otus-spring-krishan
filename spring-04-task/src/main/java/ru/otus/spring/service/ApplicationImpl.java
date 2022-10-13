package ru.otus.spring.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

@RequiredArgsConstructor
@Service
public class ApplicationImpl implements Application {

    private final QuizDao quizDao;

    private final IOMultiLanguageService ioService;

    private final QuizConfiguration quizConfiguration;

    @Override
    public void run() {

        Student student = getStudent();
        List<Question> questions = quizDao.findAll();

        int correctAnswersCount = 0;
        for (Question question : questions) {
            ioService.write(question.getQuestion());
            List<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                ioService.write(String.format("%d) %s", i, answers.get(i)));
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
        int answer = ioService.readInt("choose_answer");
        while (answer < 0 || answer > maxAnswerAllowed) {
            answer = ioService.readInt("choose_answer_2", 0, maxAnswerAllowed);
        }
        return answer;
    }

    private Student getStudent() {
        ioService.out("hello");
        Student student = new Student();
        student.setSurname(ioService.readLn("enter_surname"));
        student.setName(ioService.readLn("enter_name"));
        return student;
    }

    private void printResult(Student student, double score) {
        ioService.write(System.lineSeparator());
        ioService.out("result_1", student.getName(), student.getSurname(), score);
        ioService.out(score >= quizConfiguration.getMinScore() ? "result_passed" : "result_failed");
    }
}
