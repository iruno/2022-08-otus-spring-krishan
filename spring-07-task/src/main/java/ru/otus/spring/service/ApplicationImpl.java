package ru.otus.spring.service;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.config.QuizConfiguration;
import ru.otus.spring.dao.QuizDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

@RequiredArgsConstructor
@ShellComponent
public class ApplicationImpl implements Application {

    private final QuizDao quizDao;

    private final IOMultiLanguageService ioService;

    private final TranslationService translationService;

    private final QuizConfiguration quizConfiguration;

    private final QuizSession quizSession = new QuizSession();

    @PostConstruct
    void init() {
        ioService.out("hello");
        ioService.out("hello_1");
    }

    @ShellMethod(value = "Login", key = {"l", "login"})
    private void logon() {
        Student student = new Student();
        student.setSurname(ioService.readLn("enter_surname"));
        student.setName(ioService.readLn("enter_name"));
        quizSession.setStudent(student);
    }

    @ShellMethod(value = "Logout", key = {"x", "logout", "logoff"})
    @ShellMethodAvailability(value = "isLoggedIn")
    private void logout() {
        quizSession.clear();
    }

    @ShellMethod(value = "Print quiz result", key = {"s", "score"})
    @ShellMethodAvailability(value = "isQuizFinished")
    private void printResult() {
        ioService.write(System.lineSeparator());
        Student student = quizSession.getStudent();
        if (student != null) {
            ioService.out("result_1", student.getName(), student.getSurname(),
                    (int) quizSession.getScore());
            ioService.out(
                    quizSession.getScore() >= quizConfiguration.getMinScore() ? "result_passed" : "result_failed");
        }
    }

    @Override
    @ShellMethod(value = "Run quiz", key = {"r", "run"})
    @ShellMethodAvailability(value = "isLoggedIn")
    public void run() {
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

        quizSession.setScore(((double) correctAnswersCount / (double) questions.size()) * 100);
        printResult();
    }

    private int readAnswer(int maxAnswerAllowed) {
        int answer = ioService.readInt("choose_answer");
        while (answer < 0 || answer > maxAnswerAllowed) {
            answer = ioService.readInt("choose_answer_2", 0, maxAnswerAllowed);
        }
        return answer;
    }

    private Availability isLoggedIn() {
        return quizSession.isLoggedIn() ? Availability.available()
                : Availability.unavailable(translationService.translate("enter_login_command"));
    }

    private Availability isQuizFinished() {
        return quizSession.isQuizFinished() ? Availability.available()
                : Availability.unavailable(translationService.translate("enter_run_command"));
    }
}
