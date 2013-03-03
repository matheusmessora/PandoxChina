package pandox.china.boot.util;

import java.beans.Introspector;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class NameGenerator extends AnnotationBeanNameGenerator {
	
	Logger log = Logger.getLogger(NameGenerator.class);
	
	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String name = definition.getBeanClassName();
		log.debug("Criando bean: " + name);
		return Introspector.decapitalize(name);
	}

}
