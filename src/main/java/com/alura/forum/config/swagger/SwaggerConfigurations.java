package com.alura.forum.config.swagger;

import com.alura.forum.model.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;

@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket forumApi() {
            //  Qual documentação
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //A partir de qual pacote será lido
                .apis(RequestHandlerSelectors.basePackage("com.alura.forum"))
                //Quais endpoints serão analisados - por não ter nada restrito será tudo
                .paths(PathSelectors.ant("/**"))
                .build()
                // Ignorar todas as URLs que trabalham com usuário
                .ignoredParameterTypes(Usuario.class)
                //A partir daqui é a configuração necessária para habilitar a autenticação via token
                //São parameters que serão apresentados em todos os end-points
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                        .name("Authorization")
                        .description("Header para token JWT")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()
                ));
    }
}