
package ru.otus.spring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private Shell shell;

    @MockBean
    private IOMultiLanguageService ioMultiLanguageService;

    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_RUN = "run";
    private static final String COMMAND_SCORE = "score";

    @DisplayName("Команда run не работает если не выполнен login")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void runIsNotWorkingWithoutLogin() {
        Object res =  shell.evaluate(() -> COMMAND_RUN);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Команда score не работает если не выполнен run")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void scoreIsNotWorkingWithoutRun() {
        Object res =  shell.evaluate(() -> COMMAND_SCORE);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Тест проходит - 100 очков")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void execute() {

        when(ioMultiLanguageService.readLn("enter_surname")).thenReturn("Pupkin");
        when(ioMultiLanguageService.readLn("enter_name")).thenReturn("Vasya");
        when(ioMultiLanguageService.readInt("choose_answer")).thenReturn(0, 1, 2);

        verify(ioMultiLanguageService).out("hello");
        verify(ioMultiLanguageService).out("hello_1");

        shell.evaluate(() -> COMMAND_LOGIN);
        shell.evaluate(() -> COMMAND_RUN);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioMultiLanguageService, times(13)).write(captor.capture());

        List<String> capturedStrings = captor.getAllValues();

        assertIterableEquals(List.of(
                "2+2",
                "0) 1",
                "1) 2",
                "2) 4",
                "1*0",
                "0) 1",
                "1) 2",
                "2) 4",
                "3*2",
                "0) 18",
                "1) 3",
                "2) 6",
                System.lineSeparator()
        ), capturedStrings);

        verify(ioMultiLanguageService).out("result_1", "Vasya", "Pupkin", 100);
        verify(ioMultiLanguageService).out("result_passed");
    }
}
