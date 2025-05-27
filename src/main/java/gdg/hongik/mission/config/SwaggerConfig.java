package gdg.hongik.mission.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 설정 클래스
 */
@Configuration
public class SwaggerConfig {

    /**
     * OpenAPI 설정
     * 
     * @return OpenAPI 객체
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("쇼핑몰 API")
                        .description("쇼핑몰 상품 관리 API 문서")
                        .version("1.0.0"));
    }
} 