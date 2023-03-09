package ro.unibuc.hello.config;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ModelMapperConfig {

        @Bean
        @Primary
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setFieldMatchingEnabled(true)
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setAmbiguityIgnored(true)
                    .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                    .setImplicitMappingEnabled(true)
                    .setFullTypeMatchingRequired(true)
                    .setPropertyCondition(Conditions.isNotNull())
                    .setSkipNullEnabled(true);

            modelMapper.addConverter((Converter<String, Integer>) context -> Integer.valueOf(context.getSource()));
            return modelMapper;
        }
}
