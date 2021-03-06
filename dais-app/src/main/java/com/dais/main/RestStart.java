package com.dais.main;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by admin on 2017/3/1.
 */
public class RestStart {
    public static void main(String[] args) throws Exception{
            long startTime = System.currentTimeMillis();
            int port = 8081;
            if (args.length > 0) {
                port = Integer.valueOf(args[0]);
            }

            Server server = new Server();
            ServerConnector connector = new ServerConnector(server);
            connector.setPort(port);
            server.setConnectors(new Connector[] { connector });
            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setWar(System.getProperty("user.dir") + "/dais-app/src/main/webapp");


            webapp.setConfigurationDiscovered(true);
            webapp.setParentLoaderPriority(true);

            HandlerCollection handlerCollection = new HandlerCollection();
            handlerCollection.setHandlers(new Handler[]{webapp, new DefaultHandler()});

            Configuration.ClassList classlist = Configuration.ClassList.setServerDefault( server );
            classlist.addBefore("org.eclipse.jetty.webapp.JettyWebXmlConfiguration", "org.eclipse.jetty.annotations.AnnotationConfiguration" );

            webapp.setAttribute(
                    "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
                    ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/[^/]*taglibs.*\\.jar$" );


            server.setHandler(handlerCollection);

            server.start();
            System.out.printf("server started take %d ms, open your in browser http://localhost:%d\n", (System.currentTimeMillis() - startTime), port);
            server.join();

    }
}
