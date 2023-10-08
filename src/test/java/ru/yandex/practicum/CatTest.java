package ru.yandex.practicum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CatTest {
    @Mock
    Feline felineMock;
    Cat cat;

    @Test // Проверка, что метод getSound возвращает нужное значение
    public void getSoundCat() {
        String expectedSound = "Мяу";
        cat = new Cat(felineMock);
        String actual = cat.getSound();
        assertEquals(expectedSound, actual);
    }
    @Test // Проверка, что метод getFood получает список еды для хищника
    public void getFoodForPredator2() throws Exception {
        cat = new Cat(felineMock);
        List <String> expected = List.of("Животные", "Птицы", "Рыба");
        Mockito.when(felineMock.eatMeat()).thenReturn(List.of("Животные", "Птицы", "Рыба"));
        List<String> actual = cat.getFood();
        assertEquals(expected, actual);
    }
    @Test // Проверка, что метод getFood вызывает eatMeat без параметров один раз
    public void getFoodWithoutParameter () throws Exception {
        cat = new Cat(felineMock);
        cat.getFood();
        Mockito.verify(felineMock, Mockito.times(1)).eatMeat();
    }
}
