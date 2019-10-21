package michal.task2.service;

import michal.task2.model.User;
import michal.task2.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileEncoder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSaver userSaver;

    public List<User> fileRead(MultipartFile file) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String myString = IOUtils.toString(stream, "UTF-8");
        for ( int i = 0; i < myString.split("\\n").length; i++) {
            String[] arr = myString.split("\\n");
            fillUserData(arr[i]);
        }
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getFileName() == null) {
                user.setFileName(file.getOriginalFilename());
            }
        }
        for (User user : users) userRepository.save(user);
        return users;
    }

    public void fillUserData(String text) {
        String[] inputValue = text.replaceAll(" ", "").split(";");
        if (inputValue.length > 4) System.out.println("Plik wejściowy ma zbyt dużo danych w 1 linii");
        if (inputValue.length > 2) {
            String name = inputValue[0].replaceAll("\\p{IsDigit}", "");
            String surName = inputValue[1].replaceAll("\\p{IsDigit}", "");
            if (inputValue[2].replaceAll("\\D+", "").length() > 7) {
                LocalDate dateOfBirth = LocalDate.parse(inputValue[2].replaceAll("[^0-9]", ""), DateTimeFormatter.ofPattern("yyyyMMdd"));
                Integer phoneNumber = 0;
                if (inputValue[3].replaceAll("\\D+", "").length() == 9) {
                    phoneNumber = Integer.parseInt(inputValue[3].replaceAll("\\D+", ""));
                    userSaver.addNewUserToArr(name, surName, dateOfBirth, phoneNumber);
                }
            }
        }
    }
}
