package com.fo0.twitchbot.bot.pool;

import java.util.List;
import java.util.function.Consumer;

import com.fo0.twitchbot.model.FAQItem;
import com.fo0.twitchbot.model.QuestionAnswerMatch;
import com.fo0.twitchbot.utils.Logger;
import com.google.api.client.util.Lists;

public class FAQStore {

    private List<FAQItem> questions = Lists.newArrayList();

    private Consumer<String> outputListener;

    public FAQStore(List<FAQItem> questions, Consumer<String> outputListener) {
        this.questions = questions;
        this.outputListener = outputListener;

        init();
    }

    private void init() {
    }

    public void askQuestion(String question) {
        QuestionAnswerMatch answer = questions
                                              .stream()
                                              .filter(e -> e.getQuestion()
                                                            .containsQuestion(question))
                                              .map(e -> QuestionAnswerMatch.builder()
                                                                           .question(e.getQuestion().getQuestion(question))
                                                                           .answer(e.getAnswer())
                                                                           .build())
                                              .filter(e -> e != null)
                                              .peek(e -> Logger.trace("FAQ Match: " + e.info()))
                                              .sorted((e1, e2) -> Double.compare(e2.getQuestion().getScore(), e1.getQuestion().getScore()))
                                              .findFirst()
                                              .orElse(null);

        if (answer != null) {
            Logger.debug("Found FAQ Answer: " + answer.getAnswer() + " [" + answer.getQuestion().getScore() + "]");
            outputListener.accept(answer.getAnswer());
        }
    }

}
