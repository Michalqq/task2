package michal.task2.service;

import michal.task2.model.LogsGenerator;
import michal.task2.model.User;
import michal.task2.repository.LogsRepository;
import michal.task2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserSaver {

    private UserRepository userRepository;
    private LogsRepository logsRepository;

    @Autowired
    UserSaver(UserRepository userRepository, LogsRepository logsRepository) {
        this.userRepository = userRepository;
        this.logsRepository = logsRepository;
    }

    public boolean addNewUserToArr(String name, String surName, LocalDate dateOfBirth, int phoneNumber) {
        if (name.length() < 1 || surName.length() < 1) {
            logsRepository.save(new LogsGenerator("Input data are incomplete. Name or Surname is empty. This user won't be added"));
            return false;
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            logsRepository.save(new LogsGenerator("Birthdate is after today's day. Data are incorrect. This user won't be added"));
            return false;
        }
        this.addNew(name, surName, dateOfBirth, phoneNumber);
        return true;
    }

    public void addNew(String name, String surName, LocalDate dateOfBirth, int phoneNumber) {
        User user = new User(name, surName, dateOfBirth, phoneNumber);
        int flag = 0;
        for (User userFromDB : userRepository.findAll()) {
            if (userFromDB.getPhoneNumber().equals(user.getPhoneNumber()) && user.getPhoneNumber().toString().length()==9) {
                flag = 1;
                logsRepository.save(new LogsGenerator(user.getName() + " " + user.getSurName() +
                        " this user has the same phone number as user:(" +
                        userFromDB.getName() + " " + userFromDB.getSurName()+")"));
            }
        }
        if (!userRepository.findAll().contains(user)) {
            if (flag==0) userRepository.save(user);
        } else logsRepository.save(new LogsGenerator(user.getName() + " " + user.getSurName() + " this user is saved in database already"));
    }
}
