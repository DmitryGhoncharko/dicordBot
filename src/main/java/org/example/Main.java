package org.example;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class Main {
    public static void main(String[] args) {
        DiscordClient.create("MTA3MzcxMjEwNjg5MDg2MjYzMg.G4cadB.5oWZbXAQzqUsUncKh9s4i13Ve5JwBHKYuWTw-k")
                .withGateway(client ->
                        client.on(MessageCreateEvent.class, event -> {
                            Message message = event.getMessage();
                            if (message.getContent()!=null && message.getContent().startsWith("0x") && !message.getContent().contains("YOU ARE IN VIBELIST") && !message.getContent().contains("YOU ARE NOT IN VIBELIST")) {
                                FindDataInGoogleSheets findDataInGoogleSheets = new FindDataInGoogleSheets();
                                if(findDataInGoogleSheets.isPresentInGoogleSheets(message.getContent())){
                                    return message.getChannel()
                                            .flatMap(channel -> channel.createMessage(message.getAuthor().get().getTag() + " YOU ARE IN VIBELIST"));
                                }else {
                                    return message.getChannel().flatMap(channel -> channel.createMessage(message.getAuthor().get().getTag() + " YOU ARE NOT IN VIBELIST"));
                                }
                            }
                            return Mono.empty();

                        }))
                .block();
    }
}