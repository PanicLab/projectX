package com.github.paniclab.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class GameSessionImpl implements GameSession {
    private final String number;
    private int stageCount;
    private boolean isOver;
    private List<Stage> stageList = new ArrayList<>();

    GameSessionImpl() {
        this.number = createNumber();
        this.stageCount = 0;
        this.isOver = false;
    }

    private String createNumber() {
        List<Integer> digits = new ArrayList<>(4);
        Random rndGenerator = new Random();
        while (digits.size() < 4) {
            int digit = rndGenerator.nextInt(9 + 1);
            if (!(digits.contains(digit))) digits.add(digit);
        }
        return digits.stream().map(String::valueOf).collect(Collectors.joining());
    }



    @Override
    public void makeNewAttempt(int attempt) {
        stageCount++;
        checkWinConditions(attempt);
        Stage stage = createStage(attempt);
        stageList.add(stage);
    }

    private void checkWinConditions(int attempt) {
        if(Integer.parseInt(number) == attempt) isOver = true;
    }

    private Stage createStage(int attempt) {
        Stage stage = Stage.newInstance();
        stage.setAttemptNumber(stageCount);
        String legend = createLegend(attempt);
        stage.setLegend(legend);
        if (this.isOver()) stage.setWonStage(true);
        return stage;
    }

    private String createLegend(int attempt) {
        String attemptToString = String.valueOf(attempt);
        checkAndThrow(attemptToString);

        Integer bull = 0;
        Integer cow = 0;
        bull = checkBulls(attemptToString);
        cow = checkCows(attemptToString);
        return bull.toString() + "Б" + cow.toString() + "К";
    }

    private void checkAndThrow(String attempt) {
        if (attempt.length() != 4 ) throw new GameException("Неверное число, отображающее попытку: " + attempt);
        for (Character ch : attempt.toCharArray()) {
            if(!(Character.isDigit(ch))) throw new GameException("Неверное число, отображающее попытку: " + attempt);
        }
    }

    private int checkBulls(String attempt) {
        int result = 0;
        for(int x=0; x <= 3; x++) {
            if(attempt.charAt(x) == number.charAt(x)) result++;
        }
        return result;
    }

    private int checkCows(String attempt) {
        int result = 0;
        for(int x=0; x <= 3; x++) {
            char attemptChar = attempt.charAt(x);
            char numberChar = number.charAt(x);
            if ((attemptChar != numberChar) && (number.indexOf(attemptChar) >= 0)) result++;
        }
        return result;
    }


    @Override
    public int getStageCount() {
        return stageCount;
    }

    @Override
    public String getNumber() {
        return this.number;
    }

    @Override
    public List<Stage> getStageList() {
        return stageList;
    }

    @Override
    public boolean isOver() {
        return this.isOver;
    }
}
