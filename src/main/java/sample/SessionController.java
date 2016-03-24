package sample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by xin on 15/1/7.
 */
@Controller
public class SessionController {
	@RequestMapping("/mySession")
	public String index(final Model model, final HttpServletRequest request, final String action,
			final String msg) {
		HttpSession session = request.getSession();
		session.setAttribute("testSession", "yeah");
		return "showSession";
	}
}
