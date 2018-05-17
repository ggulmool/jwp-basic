package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();

        ModelAndView mav;
        try {
            answerDao.delete(answerId);
            mav = jsonView().addObject("result", Result.ok());
        } catch (Exception e) {
            mav = jsonView().addObject("result", Result.fail(e.getMessage()));
        }

        return mav;
    }
}
