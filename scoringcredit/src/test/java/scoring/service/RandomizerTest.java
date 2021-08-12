package scoring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


public class RandomizerTest {

    @Mock
    Randomizer randomizer = Mockito.mock(Randomizer.class);

    @Test
    void randomize() {
        when(randomizer.randomize()).thenReturn(true);
        assertTrue(randomizer.randomize());
    }
}
