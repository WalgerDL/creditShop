package scoring.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Randomizer {

    private final Random random = new Random();

    public boolean randomize() {
        return random.nextBoolean();
    }

    public int randomGroupAge(int min, int max) {
        int diff = max - min;
        return random.nextInt(diff + 1);
    }

}
