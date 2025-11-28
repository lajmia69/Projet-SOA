package e_library.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Create the Spring application context
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class, CXFConfig.class);
        
        // Add the ContextLoaderListener
        servletContext.addListener(new ContextLoaderListener(context));
        
        // Register the Spring DispatcherServlet for REST
        ServletRegistration.Dynamic restServlet = servletContext.addServlet(
                "restServlet", new DispatcherServlet(context));
        restServlet.setLoadOnStartup(1);
        restServlet.addMapping("/api/*");
        
        // Register the CXF Servlet for SOAP
        CXFServlet cxfServlet = new CXFServlet();
        ServletRegistration.Dynamic soapServlet = servletContext.addServlet("cxfServlet", cxfServlet);
        soapServlet.setLoadOnStartup(1);
        soapServlet.addMapping("/soap/*");
    }
}