package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.MatchmakingService;
import com.diasonti.descriptiontinder.service.exceptions.MatchmakingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mm")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    @Autowired
    public MatchmakingController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @GetMapping("/next")
    public RestMessage nextCandidate(UserAccount user) {
        try {
            return RestMessage.ok(matchmakingService.getNextCandidate(user.getId()));
        } catch (MatchmakingException e) {
            return RestMessage.error(e.getMessage());
        }
    }

    @PostMapping("/like")
    public RestMessage like(UserAccount user, @RequestParam(name = "id") Long candidateId) {
        try {
            matchmakingService.saveLike(user.getId(), candidateId);
            matchmakingService.checkAndSaveMatch(user.getId(), candidateId);
            return RestMessage.ok();
        } catch (MatchmakingException e) {
            return RestMessage.error(e.getMessage());
        }
    }

    @PostMapping("/dislike")
    public RestMessage dislike(UserAccount user, @RequestParam(name = "id") Long candidateId) {
        try {
            matchmakingService.saveDislike(user.getId(), candidateId);
            return RestMessage.ok();
        } catch (MatchmakingException e) {
            return RestMessage.error(e.getMessage());
        }
    }

}
