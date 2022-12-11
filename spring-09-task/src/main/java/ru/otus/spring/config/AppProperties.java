package ru.otus.spring.config;

import java.util.Locale;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties implements LocaleProvider {

    private Locale locale;
}
