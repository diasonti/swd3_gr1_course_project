package com.diasonti.descriptiontinder.data.entity;

import com.diasonti.descriptiontinder.data.enums.MatchmakingDecision;

import javax.persistence.*;

@Entity
@Table(name = "matchmaking_choice")
public class MatchmakingChoice extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_user_id")
    private UserAccount source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_user_id")
    private UserAccount target;

    @Column(name = "decision")
    @Enumerated(EnumType.STRING)
    private MatchmakingDecision decision;

    public UserAccount getSource() {
        return source;
    }

    public void setSource(UserAccount source) {
        this.source = source;
    }

    public UserAccount getTarget() {
        return target;
    }

    public void setTarget(UserAccount target) {
        this.target = target;
    }

    public MatchmakingDecision getDecision() {
        return decision;
    }

    public void setDecision(MatchmakingDecision decision) {
        this.decision = decision;
    }
}
