package com.tui.proof.ws.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.format.DateTimeFormatter;

@SpringBootConfiguration
public class SerializerConfiguration {

    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return jackson2ObjectMapperBuilder -> {
            jackson2ObjectMapperBuilder.serializers(new LocalTimeSerializer(TIME_FORMAT));
            jackson2ObjectMapperBuilder.deserializers(new LocalTimeDeserializer(TIME_FORMAT));
        };
    }
}
