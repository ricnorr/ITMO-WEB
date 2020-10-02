package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        int random = new Random().nextInt(999);
        HttpSession session = request.getSession();
        if (session.isNew()) {
            session.setAttribute("captchaDone", false);
            session.setAttribute("captchaAnswer", random);
            session.setAttribute("baseRequestURI", request.getRequestURI());
            session.setAttribute("baseMethod", request.getMethod());
        }

        boolean captchaDone = (boolean) session.getAttribute("captchaDone");
        Integer captchaAnswer = (Integer) session.getAttribute("captchaAnswer");
        if (request.getMethod().equals("POST") || (boolean)session.getAttribute("captchaDone")) {
            chain.doFilter(request, response);
            return;
        }

        if (request.getMethod().equals("GET") && request.getParameter("captcha") != null) {
            Integer captchaAttempt = new Integer(request.getParameter("captcha"));
            if (captchaAttempt.equals(captchaAnswer)) {
                session.setAttribute("captchaDone", true);
                response.sendRedirect((String)session.getAttribute("baseRequestURI"));
                return;
            }
        }

        if (!captchaDone) {
            response.setContentType("text/html");
            byte[] imgString = "<img id=\"profileImage\" src=\"data:image/png;base64, ".getBytes();
            byte[] img = Base64.getEncoder().encode(ImageUtils.toPng(Integer.toString(captchaAnswer)));
            byte[] imgStringPart2 = ("\"> <form method=\"get\" action=\"#\">\n" +
                    "        <label for=\"captcha\">Enter captcha:</label>\n" +
                    "        <input id=\"captcha\" name=\"captcha\" >\n" +
                    "    </form>").getBytes();
            byte[] result = new byte[imgString.length + img.length + imgStringPart2.length];
            System.arraycopy(imgString, 0, result, 0, imgString.length);
            System.arraycopy(img, 0, result, imgString.length, img.length);
            System.arraycopy(imgStringPart2, 0, result, imgString.length + img.length, imgStringPart2.length);
            response.getOutputStream().write(result);
            response.getOutputStream().flush();
            response.setStatus(200);
            return;
        }


        response.setStatus(404);
    }
}