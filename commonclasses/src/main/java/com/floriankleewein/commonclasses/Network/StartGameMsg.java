package com.floriankleewein.commonclasses.Network;

public class StartGameMsg extends BaseMessage {

    private int feedbackUI;

    public int getFeedbackUI() {
        return feedbackUI;
    }

    public void setFeedbackUI(int feedbackUI) {
        this.feedbackUI = feedbackUI;
    }

}
