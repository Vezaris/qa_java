package ru.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

@RunWith(Parameterized.class)
public class FelineParamTest {
    Feline feline;
    String animalType;
    List<String> expectedFood;

    public FelineParamTest(String animalType, List<String> expectedFood) {
        this.animalType = animalType;
        this.expectedFood = expectedFood;
    }

    @Parameterized.Parameters(name = "{index}: {0} - {1}")
    public static Object[][] getDataEntry() {
        return new Object[][]{
            {"Травоядное", List.of("Трава", "Различные растения")},
            {"Хищник", List.of("Животные", "Птицы", "Рыба")},
        };
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test // Метод getFood получает список еды для хищника и травоядного
    public void getFoodReturnFood() throws Exception {
        feline = new Feline();
        assertEquals(expectedFood, feline.getFood(animalType));
    }
}
