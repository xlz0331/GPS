package com.hwagain.eagle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

import org.springframework.boot.web.servlet.ServletComponentScan;
//import com.hwagain.eagle.util.JwtUtil;
import com.hwagain.framework.web.common.conf.ForameworkBeanDefinitionRegistryPostProcessor;

@SpringBootApplication(scanBasePackages = { "com.hwagain" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@MapperScan({ "com.hwagain.eagle.**.mapper*" })
@ImportResource({"classpath:dubboConsumer.xml"})
@ServletComponentScan(basePackages = "com.hwagain.eagle.listener.*")
public class ApplicationMain {

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(FrameworkApplicationMain.class, args);
		SpringApplication springApplication = new SpringApplication(new Object[] { ApplicationMain.class });
		springApplication.addInitializers(new ApplicationContextInitializer<ConfigurableApplicationContext>() {
			@Override
			public void initialize(ConfigurableApplicationContext applicationContext) {
				SimpleCommandLinePropertySource ps = new SimpleCommandLinePropertySource(args);
				ForameworkBeanDefinitionRegistryPostProcessor processor = new ForameworkBeanDefinitionRegistryPostProcessor(
						applicationContext, ps);
				applicationContext.addBeanFactoryPostProcessor(processor);
			}
			
			@Bean 
			 public EmbeddedServletContainerCustomizer containerCustomizer(){ 
			      return new EmbeddedServletContainerCustomizer() { 
			 @Override 
			 public void customize(ConfigurableEmbeddedServletContainer container) { 
			       container.setSessionTimeout(5);//单位为S 
			            } 
			       }; 
			   } 
		});
		springApplication.run(args);
		System.err.println("启动成功");
	}
}