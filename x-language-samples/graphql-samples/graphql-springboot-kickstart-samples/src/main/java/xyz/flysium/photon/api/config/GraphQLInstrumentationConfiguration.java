package xyz.flysium.photon.api.config;

import graphql.kickstart.execution.context.DefaultGraphQLContext;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import graphql.kickstart.spring.web.boot.GraphQLWebAutoConfiguration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Configuration
@AutoConfigureBefore(GraphQLWebAutoConfiguration.class)
public class GraphQLInstrumentationConfiguration {

    @Bean
    public DataLoaderRegistry dataLoaderRegistry(List<DataLoader<?, ?>> loaderList) {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        for (DataLoader<?, ?> loader : loaderList) {
            registry.register(loader.getClass().getSimpleName(), loader);
        }
        return registry;
    }

    @Bean
    public GraphQLServletContextBuilder graphQLContextBuilder(DataLoaderRegistry dataLoaderRegistry) {
        return new CustomGraphQLServletContextBuilder(dataLoaderRegistry);
    }

    static class CustomGraphQLServletContextBuilder implements GraphQLServletContextBuilder {

        private final DataLoaderRegistry dataLoaderRegistry;

        CustomGraphQLServletContextBuilder(DataLoaderRegistry dataLoaderRegistry) {
            this.dataLoaderRegistry = dataLoaderRegistry;
        }

        private DataLoaderRegistry buildDataLoaderRegistry() {
            return dataLoaderRegistry;
        }

        @Override
        public GraphQLContext build() {
            return new DefaultGraphQLContext();
        }

        @Override
        public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
            return DefaultGraphQLServletContext.createServletContext().with(httpServletRequest)
                .with(httpServletResponse).with(buildDataLoaderRegistry()).build();
        }

        @Override
        public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
            return DefaultGraphQLWebSocketContext.createWebSocketContext().with(session).with(handshakeRequest)
                .with(buildDataLoaderRegistry()).build();
        }

    }
}
