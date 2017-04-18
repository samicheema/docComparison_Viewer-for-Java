package com.docs.jetty;

import java.io.File;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.webapp.WebAppContext;

import com.docs.utilities.Utilities;

public class Main {

	public static void main(String[] args) throws Exception {
		
	    Server server = new Server(8088);
	    
	    WebAppContext webappcontext = new WebAppContext();
	    webappcontext.setContextPath("/docComparison");	
	    File warPath = new File(Utilities.getProjectBaseDir().toString(), "src/main/webapp");
	    webappcontext.setWar(warPath.getAbsolutePath());	    
	    webappcontext.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());	
	    
	    ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);
	    sch.addBean(new JspStarter(sch));	
	    
	    HandlerList handlers = new HandlerList();
	    handlers.setHandlers(new Handler[] { webappcontext,sch, new DefaultHandler() });
	    
	    server.setHandler(handlers);
	    server.start();
	}
	
	public static class JspStarter extends AbstractLifeCycle 
					implements ServletContextHandler.ServletContainerInitializerCaller{
		
	    JettyJasperInitializer jspinitializer;
	    ServletContextHandler context;
	    
	    public JspStarter (ServletContextHandler context)  {
	        this.jspinitializer = new JettyJasperInitializer();
	        this.context = context;
	        this.context.setAttribute("org.apache.tomcat.JarScanner", new StandardJarScanner());
	    }
	
	    @Override
	    protected void doStart() throws Exception    {
	        ClassLoader  classloader= Thread.currentThread().getContextClassLoader();
	        Thread.currentThread().setContextClassLoader(context.getClassLoader());
	        try{
	        	jspinitializer.onStartup(null, context.getServletContext());   
	            super.doStart();
	        }finally{
	            Thread.currentThread().setContextClassLoader(classloader);
	        }
	    }
	}

}