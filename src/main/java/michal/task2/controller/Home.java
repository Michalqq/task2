package michal.task2.controller;

import michal.task2.model.User;
import michal.task2.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class Home {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHome(ModelAndView modelAndView) {
        List<User> testList = userRepository.findAll();
        modelAndView.setViewName("home");
        modelAndView.addObject("name", testList.toString());
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView submit(@RequestParam("file") MultipartFile file, ModelAndView modelAndView) throws IOException {
        modelAndView.addObject("file", file);
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
        String myString = IOUtils.toString(stream, "UTF-8");
        for (int i = 0; i < myString.split("\\n").length; i++) {
            String[] arr = myString.split("\\n");
            System.out.println(arr[i]);
            fillUserData(arr[i]);
        }
        modelAndView.addObject("name", myString);
        modelAndView.addObject("users", userRepository.findAll());
        modelAndView.setViewName("home");
        System.out.println("TEEEEEEEEST");
        System.out.println(userRepository.findAll().size());
        return modelAndView;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return multipartResolver;
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
                if (inputValue[3].replaceAll("\\D+", "").length() > 8) {
                    phoneNumber = Integer.parseInt(inputValue[3].replaceAll("\\D+", ""));
                    addNewUserToArr(name, surName, dateOfBirth, phoneNumber);
                }
            }
        }
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
//        if (phoneNumber != 0 && phoneNumber < 100000000) {
//            System.out.println("Phone number is incorrect. This user will have empty phone number");
//            phoneNumber = 0;
//        }
        this.addNew(name, surName, dateOfBirth, phoneNumber);
        return true;
    }

    public void addNew(String name, String surName, LocalDate dateOfBirth, int phoneNumber) {
        User user = new User(name, surName, dateOfBirth, phoneNumber);
        if (!userRepository.findAll().contains(user)) {
            userRepository.save(user);
        }
    }

}
