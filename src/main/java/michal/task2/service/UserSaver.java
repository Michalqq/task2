package michal.task2.service;

import michal.task2.model.User;
import michal.task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserSaver {

    private UserRepository userRepository;

    @Autowired
    UserSaver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addNewUserToArr(String name, String surName, LocalDate dateOfBirth, int phoneNumber) {
        if (name.length() < 1 || surName.length() < 1) {
            System.out.println("Input data are incomplete. Name or Surname is empty. This user won't be added");
            return false;
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            System.out.println("Birthdate is after today's day. Data are incorrect. This user won't be added");
            return false;
        }
        this.addNew(name, surName, dateOfBirth, phoneNumber);
        return true;
    }

    public void addNew(String name, String surName, LocalDate dateOfBirth, int phoneNumber) {
        User user = new User(name, surName, dateOfBirth, phoneNumber);
        int flag = 0;
        for (User userFromDB : userRepository.findAll()) {
            if (userFromDB.getPhoneNumber().equals(user.getPhoneNumber())) flag = 1;
        }
        if (!userRepository.findAll().contains(user) && flag == 0) {
            userRepository.save(user);
        }
    }
}
