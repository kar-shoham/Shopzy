package com.shopzy.product_service.config;

import com.shopzy.product_service.entity.Product;
import com.shopzy.product_service.filters.JwtFilter;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig
{
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    protected ModelMapper modelMapper()
    {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Product, Product> productProductTypeMap = mapper.createTypeMap(Product.class, Product.class);
        productProductTypeMap.setPropertyCondition(Conditions.isNotNull());
        productProductTypeMap.addMappings(m -> m.skip(Product::setReviews));
        return mapper;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> null;
    }
}
