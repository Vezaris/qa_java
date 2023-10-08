package ru.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.*;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FelineTest {
    @Spy
    Feline felineSpy;
    String animalType;

    public FelineTest(String animalType) {
        this.animalType = animalType;
    }

    @Parameterized.Parameters
    public static Object[][] getDataEntry() {
        return new Object[][]{
                {"Травоядное"},
                {"Хищник"},
                {"Другое"},
        };
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test // Метод eatMeat вызывает метод getFood с параметром "Хищник" один раз
    public void eatMeatGetFoodWithParameter() throws Exception {
        felineSpy.eatMeat();
        Mockito.verify(felineSpy, Mockito.times(1)).getFood("Хищник");
    }
    @Test // Метод eatMeat получает список еды для хищника из метода getFood
    public void eatMeatGetFoodForPredator() throws Exception {
        List<String> expectedFoodForPredator = List.of("Животные", "Птицы", "Рыба");
        List<String> actualFoodForPredator = felineSpy.eatMeat();
        assertEquals(expectedFoodForPredator, actualFoodForPredator);
    }
    @Test // Метод getFood получает список еды для хищника, травоядного и неизвестного значения
    public void getFoodReturnFood() throws Exception {
        List<String> expectedFoodForPredator = List.of("Животные", "Птицы", "Рыба");
        List<String> expectedFoodForHerbivore = List.of("Трава", "Различные растения");
        String expectedException = "Неизвестный вид животного, используйте значение Травоядное или Хищник";
        try {
            List<String> actualFood = felineSpy.getFood(animalType);
            if ("Травоядное".equals(animalType)) {
                assertEquals(expectedFoodForHerbivore, actualFood);
            } else if ("Хищник".equals(animalType)) {
                assertEquals(expectedFoodForPredator, actualFood);
            }
        } catch (Exception exception) {
            assertEquals(expectedException, exception.getMessage());
        }
    }
    @Test // Метод getFamily вернул "Кошачьи"
    public void getFamilyReturnFeline() {
        String expected = "Кошачьи";
        String actual =  felineSpy.getFamily();
        assertEquals(expected, actual);
    }
    @Test // Метод getKittens без параметра вызывает getKittens с параметром "1" один раз
    public void getKittensGetKittensWithParameter() {
        felineSpy.getKittens();
        Mockito.verify(felineSpy, Mockito.times(1)).getKittens(1);
    }
    @Test // Метод getKittens с параметром возвращает кол-во котят
    public void getKittensWithParameterReturnCount() {
        int countKittens = 2;;
        int actual =  felineSpy.getKittens(countKittens);
        assertEquals(countKittens, actual);
    }
}