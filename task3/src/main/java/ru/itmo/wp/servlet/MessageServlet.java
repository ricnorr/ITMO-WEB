package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MessageServlet extends HttpServlet {
    private static final Gson jsonParser = new Gson();
    private static class UserTextPair implements Serializable {
        private String user;
        private String text;

        public UserTextPair(String user, String text) {
            this.user = user;
            this.text = text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        List<UserTextPair> list = (List<UserTextPair>)session.getAttribute("list");
        if (list == null) {
            list = new ArrayList<>();
            session.setAttribute("list", list);
        }
        switch (uri) {
            case ("/message/auth"):
                if (request.getParameter("user") != null) {
                    session.setAttribute("user", request.getParameter("user"));
                    response.getWriter().print(jsonParser.toJson(request.getParameter("user")));
                } else {
                    response.getWriter().print(jsonParser.toJson(""));
                }
                break;
            case ("/message/add"):
                list.add(new UserTextPair((String)session.getAttribute("user"), request.getParameter("text")));
                break;
            case ("/message/findAll"):
                response.getWriter().print(jsonParser.toJson(list));
                break;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
}
