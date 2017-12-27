package com.osg.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class PurchaseRequisitionApplication extends WebMvcConfigurerAdapter {

    @Autowired
    private MessageSource messageSource;

    public static void main(String[] args) {
		SpringApplication.run(PurchaseRequisitionApplication.class, args);
	}
	

    /**
     * LocalValidatorFactoryBeanのsetValidationMessageSourceで
     * バリデーションメッセージをValidationMessages.propertiesからSpringの
     * MessageSource(messages.properties)に上書きする
     * 
     * @return localValidatorFactoryBean
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

    @Override
    public org.springframework.validation.Validator getValidator() {
        return validator();
    }

}
