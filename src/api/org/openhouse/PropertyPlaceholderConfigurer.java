package org.openhouse;

import org.openhouse.api.context.Context;

/**
 * Extends the Spring PropertyPlaceholderConfigurer
 * which allows you to use variables ${} in your 
 * applicationContext that reference properties in
 * the OPENHOUSE_RUNTIME.property file.
 *
 * @author Samuel Mbugua
 *
 */
public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

    public PropertyPlaceholderConfigurer() {
        super();
        this.setProperties(Context.getRuntimeProperties());
    }
}
