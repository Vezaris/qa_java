package ru.yandex.practicum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FelineTest {
    @Spy
    Feline felineSpy;

    @Test // Метод eatMeat вызывает метод getFood с параметром "Хищник" один раз
    public void eatMeatGetFoodWithParameter() throws Exception {
        felineSpy.eatMeat();
        Mockito.verify(felineSpy, Mockito.times(1)).getFood("Хищник");
    }
    @Test // Метод eatMeat получает список еды для хищника из метода getFood
    public void eatMeatGetFoodForPredator() throws Exception {
        List<String> expectedFoodForPredator = List.of("Животные", "Птицы", "Рыба");
        assertEquals(expectedFoodForPredator, felineSpy.eatMeat());
    }
    @Test // Метод getFood получает ожидаемую ошибку
    public void getFoodReturnError() {
        String expectedMessageErrorType = "Неизвестный вид животного, используйте значение Травоядное или Хищник";
        try {
            felineSpy.getFood("Другое");
            throw new Exception("Отсутствует ожидаемая ошибка");
        } catch (Exception exception) {
            assertEquals(expectedMessageErrorType, exception.getMessage());
        }
    }
    @Test // Метод getFamily вернул "Кошачьи"
    public void getFamilyReturnFeline() {
        assertEquals("Кошачьи", felineSpy.getFamily());
    }
    @Test // Метод getKittens без параметра вызывает getKittens с параметром "1" один раз
    public void getKittensGetKittensWithParameter() {
        felineSpy.getKittens();
        Mockito.verify(felineSpy, Mockito.times(1)).getKittens(1);
    }
    @Test // Метод getKittens с параметром возвращает кол-во котят
    public void getKittensWithParameterReturnCount() {
        int countKittens = 2;
        int actual =  felineSpy.getKittens(countKittens);
        assertEquals(countKittens, actual);
    }
}