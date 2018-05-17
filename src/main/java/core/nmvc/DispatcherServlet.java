package core.nmvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import core.mvc.Controller;
import core.mvc.LegacyHandlerMapping;
import core.mvc.ModelAndView;
import core.mvc.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings = Lists.newArrayList();

    @Override
    public void init() throws ServletException {
        handlerMappings.add(new LegacyHandlerMapping());
        handlerMappings.add(new AnnotationHandlerMapping("next.controller"));
        handlerMappings.stream().forEach(hm -> hm.initialize());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        for (HandlerMapping hm : handlerMappings) {
            Object handler = hm.getHandler(req);
            if (handler instanceof Controller) {
                try {
                    render(req, resp, ((Controller) handler).execute(req, resp));
                } catch (Exception e) {
                }
            } else if (handler instanceof HandlerExecution){
                try {
                    render(req, resp, ((HandlerExecution)handler).handle(req, resp));
                } catch (Exception e) {
                }
            }
        }
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws ServletException {
        try {
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
