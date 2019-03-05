package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.ChatMessageForm;
import com.diasonti.descriptiontinder.data.form.MmMatchForm;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.ChatService;
import com.diasonti.descriptiontinder.service.MatchmakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class AfterMatchController {

    private final MatchmakingService matchmakingService;

    private final ChatService chatService;

    @Autowired
    public AfterMatchController(MatchmakingService matchmakingService, ChatService chatService) {
        this.matchmakingService = matchmakingService;
        this.chatService = chatService;
    }

    @GetMapping("/chat/list")
    public RestMessage getMatches(UserAccount userAccount) {
        List<MmMatchForm> matches = matchmakingService.getMatches(userAccount.getId());
        return RestMessage.ok(matches);
    }

    @GetMapping("/chat/get")
    public RestMessage getMessages(@RequestParam Long matchId, @RequestParam int from, @RequestParam int to) {
        final List<ChatMessageForm> messages = chatService.getMessages(matchId, from,  to);
        return RestMessage.ok(messages);
    }

    @PostMapping("/chat/send")
    public RestMessage getMatches(@RequestParam Long matchId, @RequestParam String text) {
        final boolean isSuccess = chatService.saveMessage(matchId, text);
        if(isSuccess)
            return RestMessage.ok();
        else
            return RestMessage.error();
    }

}
