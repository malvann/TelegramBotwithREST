package com.example.TelegramBot.bot;

import com.example.TelegramBot.service.CityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@PropertySource("classpath:properties.properties")
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    private final CityService cityService;
    private static int index = 0;

    public Bot(CityService cityService) {
        this.cityService = cityService;
    }

    public String getBotUsername() {return botName;}

    public String getBotToken() {return botToken;}

    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage()
                .enableMarkdown(true)
                .setChatId(message.getChatId().toString())
                .setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        Message message = update.getMessage();
        if (message.getText().equalsIgnoreCase("/start")) sendMsg(message, "Hi! Inpun the city u want to visit..");
        else if (message.getText().equalsIgnoreCase("bye")) sendMsg(message, "Bye!");
        else {
            List<String> replyList = cityService.getCityInfoByCity(message.getText());
            if (replyList.isEmpty()) sendMsg(message, "Sorry.. But I don't know such city..");
            else {
                if (index >= replyList.size()) index = 0;
                sendMsg(message, replyList.get(index));
                index++;
            }
        }
    }
}

