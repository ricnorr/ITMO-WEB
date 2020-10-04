package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov"),
            new User(6, "pashka", "Pavel Mavrin"),
            new User(9, "geranazarov555", "Georgiy Nazarov"),
            new User(11, "tourist", "Gennady Korotkevich")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(10, 1, "Post from MikeMirzayanov", "Today all students will get ban for Java. More details are waiting you in 331"),
            new Post(60, 6, "Post from pashka", "A new lab starts today. Deadline in one week! Hurry up"),
            new Post(99, 9, "Post from gera", "News! Kotlin heroes without kotlin. If you are interested, write me in tg"),
            new Post(111, 11, "Post from tourist", "ICPC training is cab. 100 at 17:00. Entrance is free!ICPC training is cab. 100 at 17:00. " +
                    "Entrance is free!ICPC training is cab. 100 at 17:00. Entrance is free!ICPC training is cab. 100 at 17:00. " +
                    "Entrance is free!ICPC training is cab. 100 at 17:00. Entrance is free!ICPC training is cab. 100 at 17:00." +
                    " Entrance is free!ICPC training is cab. 100 at 17:00. Entrance is free!ICPC training is cab. 100 at 17:00. Entrance is free!"),
            new Post(100, 9, "Privet from gera", "News! Kotlin heroes without kotlin. If you are interested, write me in tg")

    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

        data.put("posts", POSTS);
    }
}
