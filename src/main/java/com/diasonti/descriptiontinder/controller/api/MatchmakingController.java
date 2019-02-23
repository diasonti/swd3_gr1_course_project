package com.diasonti.descriptiontinder.controller.api;

import com.diasonti.descriptiontinder.data.entity.UserAccount;
import com.diasonti.descriptiontinder.data.form.UserProfileForm;
import com.diasonti.descriptiontinder.data.util.RestMessage;
import com.diasonti.descriptiontinder.service.MatchmakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mm")
public class MatchmakingController {

    @Autowired
    private MatchmakingService matchmakingService;

    @GetMapping("/next")
    public RestMessage nextCandidate(UserAccount user) {
        final UserProfileForm candidate = matchmakingService.getNextCandidate(user.getId());
        if(candidate != null) {
            return RestMessage.ok(candidate);
        } else {
            return RestMessage.error("no.candidate.available");
        }
    }

    @PostMapping("/like")
    public RestMessage like(UserAccount user, @RequestParam(name = "id") Long candidateId) {
        matchmakingService.saveLike(user.getId(), candidateId);
        return RestMessage.ok();
    }

    @PostMapping("/dislike")
    public RestMessage dislike(UserAccount user, @RequestParam(name = "id") Long candidateId) {
        matchmakingService.saveDislike(user.getId(), candidateId);
        return RestMessage.ok();
    }

}
