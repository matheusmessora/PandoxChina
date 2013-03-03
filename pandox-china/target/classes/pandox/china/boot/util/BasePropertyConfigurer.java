package pandox.china.boot.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Carrega o arquivo de configurações da aplicação <br>config.properties</br>
 * @author lcsilva
 *
 */
public class BasePropertyConfigurer extends PropertyPlaceholderConfigurer {

	Properties mergedProperties;

	public Properties getMergedProperties() throws IOException {
		if (mergedProperties == null) {
			mergedProperties = mergeProperties();
		}
		return mergedProperties;
	}
}
