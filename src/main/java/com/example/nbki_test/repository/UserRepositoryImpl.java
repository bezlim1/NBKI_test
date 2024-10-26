package com.example.nbki_test.repository;

import com.example.nbki_test.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl {

    private List<User> userList = new ArrayList<>();

    public List<User> addUser(User user) {
        userList.add(user);
        return userList;
    }

    public User getUserById(int id) {
        for (User u : userList) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    public List<User> removeUserById(int id) {
        for (User us : userList) {
            if (us.getId() == id) {
                userList.remove(us);
            }
        }
        return null;
    }

    public User updateUser (int id, String updateName) {
        for (User use : userList) {
            if (use.getId() == id)
                use.setName(updateName);
            return use;
        }
        return null;
    }
}
