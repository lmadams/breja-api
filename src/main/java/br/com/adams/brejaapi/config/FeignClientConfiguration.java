package br.com.adams.brejaapi.config;

import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfiguration {

  private final ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
    return new BasicAuthRequestInterceptor(
        "013c2d83b0764d8d986d8228ea0a4432", "d9b15670fa8e40159af404af47ce1841");
  }

  @Bean
  FormEncoder feignFormEncoder() {
    return new FormEncoder(new SpringEncoder(this.messageConverters));
  }
}
