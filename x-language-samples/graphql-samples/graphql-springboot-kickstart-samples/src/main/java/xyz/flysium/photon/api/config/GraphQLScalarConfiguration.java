package xyz.flysium.photon.api.config;

import graphql.kickstart.tools.boot.GraphQLJavaToolsAutoConfiguration;
import graphql.scalars.ExtendedScalars;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

/**
 *  for graphql-java-extended-scalars
 *
 *  https://github.com/graphql-java/graphql-java-extended-scalars
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Configuration
@ConditionalOnClass(ExtendedScalars.class)
@ConditionalOnMissingClass
@AutoConfigureBefore({ GraphQLJavaToolsAutoConfiguration.class })
public class GraphQLScalarConfiguration {

    //    # java.util.Date OffsetDateTime
    //    scalar DateTime
    // TODO 不是很好用，暂时用 https://github.com/donbeave/graphql-java-datetime
    //    @Bean
    //    @ConditionalOnMissingBean
    //    public DateTimeScalar dateTimeScalar() {
    //        return new DateTimeScalar();
    //    }

}


