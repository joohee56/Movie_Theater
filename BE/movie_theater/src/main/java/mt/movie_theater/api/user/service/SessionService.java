package mt.movie_theater.api.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mt.movie_theater.api.user.constants.SessionConstants;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    public void createLoginSession(HttpServletRequest request, String loginId) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*10);
        session.setAttribute(SessionConstants.LOGIN_USER_ID, loginId);
    }

    public void removeSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
