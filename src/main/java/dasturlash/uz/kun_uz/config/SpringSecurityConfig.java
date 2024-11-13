    package dasturlash.uz.kun_uz.config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.provisioning.InMemoryUserDetailsManager;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


    import java.util.Arrays;


    @EnableWebSecurity
    @Configuration
    public class SpringSecurityConfig {
        @Autowired
        private UserDetailsService userDetailsService;
        @Autowired
        JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public AuthenticationProvider authenticationProvider() {
            // authentication - Foydalanuvchining identifikatsiya qilish.
            // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.


            final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            return authenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http.authorizeHttpRequests(hrr -> {
                        hrr
                                // Authentication APIs - open to all
                                .requestMatchers(HttpMethod.GET,"/article/last8/exclude").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/post", "post/**").permitAll()
                                .requestMatchers("/category/lang").permitAll()
                                .requestMatchers("/article-type/lang").permitAll()
                                .requestMatchers("/region/lang").permitAll()
                                .requestMatchers("/attach/upload").permitAll()
                                .requestMatchers("/articleType/getAll").permitAll()
                                .requestMatchers("/article/{type}/{n}").permitAll()
                                .requestMatchers("/article/{typeId}/{n}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/attach/open/{fileName}",
                                                                "/attach/openGeneral/{fileName}",
                                                                "/attach/download/{fileName}").permitAll()

                                // APIs - open PUBLISHER
                                .requestMatchers("article/changeStatus/{id}/{statusPublish}").hasRole("PUBLISHER")

                                // APIs - open MODERATOR
                                .requestMatchers("/article/**").hasRole("MODERATOR")

                                // APIs - open ADMIN
                                .requestMatchers("/articleType/**").hasRole("ADMIN")
                                .requestMatchers("category/**").hasRole("ADMIN")




                                .anyRequest()
                                .authenticated();
                    })
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            http.csrf(AbstractHttpConfigurer::disable);
            http.cors(httpSecurityCorsConfigurer -> { // cors konfiguratsiya qilingan
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOriginPatterns(Arrays.asList("*"));
                configuration.setAllowedMethods(Arrays.asList("*"));
                configuration.setAllowedHeaders(Arrays.asList("*"));

                org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            });


            return http.build();
        }


    }

