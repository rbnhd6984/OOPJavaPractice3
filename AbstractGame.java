package practice3;

import java.util.List;
import java.util.Random;
import java.util.logging.*;
import java.io.IOException;

public abstract class AbstractGame implements Game {

    abstract List<String> generateCharList();
    private String word;
    Integer tryCount;
    Integer sizeWord;
    GameStatus gameStatus = GameStatus.INIT;
    private final static Logger LOGGER = Logger.getLogger(AbstractGame.class.getName());
    private FileHandler fileHandler;

    @Override
    public void start(Integer sizeWord, Integer tryCount) {
        this.sizeWord = sizeWord;
        word = generateWord(sizeWord);
        this.tryCount = tryCount;
        gameStatus = GameStatus.START;
        try {
            if (fileHandler != null) {
                LOGGER.removeHandler(fileHandler);
                fileHandler.close();
            }
            fileHandler = new FileHandler("game.log", false);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
            LOGGER.log(Level.INFO, "Игра началась с размером слова: " +
                    sizeWord + " и количеством попыток: " + tryCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateWord(Integer sizeWord) {
        List<String> alphabet = generateCharList();
        Random rnd = new Random();
        String res = "";
        for (int i = 0; i < sizeWord; i++) {
            int randomIndex = rnd.nextInt(alphabet.size());
            res += alphabet.get(randomIndex);
            alphabet.remove(randomIndex);
        }
        return res;
    }

    @Override
    public Answer inputValue(String value) {
        if (!getGameStatus().equals(GameStatus.START)) {
            throw new RuntimeException("Игра не запущена");
        }
        int cowCounter = 0;
        int bullCounter = 0;
        for (int i = 0; i < word.length(); i++) {
            if (value.charAt(i) == word.charAt(i)) {
                cowCounter++;
                bullCounter++;
            } else if (word.contains((String.valueOf(value.charAt(i))))) {
                cowCounter++;
            }
        }
        tryCount--;
        if (tryCount == 0) {
            gameStatus = GameStatus.LOOSE;
        }
        if (bullCounter == word.length()) {
            gameStatus = GameStatus.WIN;
        }
        Answer answer = new Answer(cowCounter, bullCounter, tryCount);
        LOGGER.log(Level.INFO, "Введено значение: " + value + ", ответ: " + answer);
        return answer;
    }

    @Override
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
