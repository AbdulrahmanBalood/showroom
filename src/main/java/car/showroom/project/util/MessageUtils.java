package car.showroom.project.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtils {
    private final MessageSource messageSource;
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, SessionUtils.getCurrentLocale());
    }
}
