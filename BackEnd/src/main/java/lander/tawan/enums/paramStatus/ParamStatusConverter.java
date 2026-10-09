package lander.tawan.enums.paramStatus;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ParamStatusConverter implements Converter<String, ParamStatus> {

    @Override
    public ParamStatus convert(String source) {
        return ParamStatus.valueOf(source.trim().toUpperCase(Locale.ROOT));
    }
}