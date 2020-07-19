package utility;

import model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface UserData {
    List<User> users = new ArrayList<>(Arrays.asList(
            new User("a@a.pl" , "a", true),
            new User("b@b.pl" , "b", true),
            new User("c@a.pl" , "c", true),
            new User("d@a.pl" , "d", true),
            new User("e@a.pl" , "e", true)

            ));
}
