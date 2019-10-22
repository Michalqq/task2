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

    private UserRepository userRepository;
    private UserSaver userSaver;

    @Autowired
    public FileEncoder(UserRepository userRepository, UserSaver userSaver){
        this.userRepository = userRepository;
        this.userSaver = userSaver;
    }

    public List<User> fileRead(MultipartFile file) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String myString = IOUtils.toString(stream, "UTF-8");
        for ( int i = 0; i < myString.split("\\n").length; i++) {
            String[] arr = myString.split("\\n");
            fillUserData(arr[i]);
        }
        List<User> users = userRepository.findAll();
        this.setFileNameAndSaveToDB(users, file);
        return users;
    }

    public void fillUserData(String text) {
        String[] inputValue = text.replaceAll(" ", "").split(";");
        if (inputValue.length > 4) System.out.println("Plik wejściowy ma zbyt dużo danych w 1 linii");
        if (inputValue.length > 1) {
            String name = getOnlyAlphabeticLetters(inputValue[0]);
            String surName = getOnlyAlphabeticLetters(inputValue[1]);
            if (getOnlyNumeric(inputValue[2]).length() > 7) {
                System.out.println(inputValue[2]);
                LocalDate dateOfBirth = LocalDate.parse(getOnlyNumeric(inputValue[2]), DateTimeFormatter.ofPattern("yyyyMMdd"));
                Integer phoneNumber = 0;
                if (getOnlyNumeric(inputValue[3]).length() == 9) {
                    phoneNumber = Integer.parseInt(getOnlyNumeric(inputValue[3]));
                }
                userSaver.addNewUserToArr(name, surName, dateOfBirth, phoneNumber);
            }
        }
    }

    public void setFileNameAndSaveToDB(List<User> users, MultipartFile file){
        for (User user : users) {
            if (user.getFileName() == null) {
                user.setFileName(file.getOriginalFilename());
            }
        }
        for (User user : users) userRepository.save(user);
    }

    public String capitalizeText(String text){
        if (text.length()>1) return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
        else return text.toUpperCase();
    }

    public String getOnlyAlphabeticLetters(String text){
        return capitalizeText(text.replaceAll("[^a-zA-Z]", ""));
    }
    public String getOnlyNumeric(String text){
        return text.replaceAll("\\D+", "");
    }
}
