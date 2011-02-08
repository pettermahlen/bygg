/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

import com.pettermahlen.bygg.configuration.ByggConfiguration;

import java.lang.reflect.Method;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 04/02/2011
 */
public class Configuration implements ByggConfiguration {
    public String vardet() {
        return "värdet";
    }

    public static ByggConfiguration configuration() {
        try {
            Class pluginClass = Class.forName("com.pettermahlen.byggTestPlugin.ByggTestPlugin");

            Object plugin = pluginClass.newInstance();

            Method execute = plugin.getClass().getMethod("execute", null);

            execute.invoke(plugin, null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Configuration();
    }
}
