package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));

        log.debug("{} : ", user);
        String url = "";
        if (user != null) {
            if (user.getPassword().equals(req.getParameter("password"))) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                url = "/index.html";
            } else {
                url = "/user/login_failed.html";
            }
        } else {
            url = "/user/login_failed.html";
        }
        resp.sendRedirect(url);
    }
}
