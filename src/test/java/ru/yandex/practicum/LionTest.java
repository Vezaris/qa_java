package ru.yandex.practicum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class LionTest {
    Lion lion;
    @Mock
    Feline felineMock;
    String lionSex;

    public LionTest (String lionSex) {
        this.lionSex = lionSex;
    }

    @Parameterized.Parameters
    public static Object[][] getDataEntry() {
        return new Object[][]{
                {"Самец"},
                {"Самка"},
                {"Другое"},
        };
    }
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test // Проверка, что метод feline.getKittens вызывается без параметров один раз
    public void getKittensWithoutParameter() {
        lion = new Lion(felineMock);
        lion.getKittens();
        Mockito.verify(felineMock, Mockito.times(1)).getKittens();
    }
    @Test // Проверка, что метод getKittens возвращает правильное количество котят(1)
    public void getKittensReturnCount() {
        lion = new Lion(felineMock);
        int expected = 1;
        Mockito.when(felineMock.getKittens()).thenReturn(1);
        int actual = lion.getKittens();
        assertEquals(expected, actual);
    }
    @Test // Возвращается корректное значение hasMane в зависимости от пола
    public void doesHaveMane() throws Exception {
        String expectedException = "Используйте допустимые значения пола животного - самей или самка";
        try {
            lion = new Lion(lionSex);
            if ("Самец".equals(lionSex)) {
                assertTrue(lion.doesHaveMane());
            } else if ("Самка".equals(lionSex)) {
                assertFalse(lion.doesHaveMane());
            }
        } catch (Exception exception){
            assertEquals(expectedException, exception.getMessage());
        }
    }
    @Test // Проверка, что метод getFood получает список еды для хищника
    public void getFoodForPredator() throws Exception {
        lion = new Lion(felineMock);
        List<String> expected = List.of("Животные", "Птицы", "Рыба");
        Mockito.when(felineMock.getFood("Хищник")).thenReturn(List.of("Животные", "Птицы", "Рыба"));
        List<String> actual = lion.getFood();
        assertEquals(expected, actual);
    }
    @Test // Проверка, что метод getFood вызывает feline.getFood с параметром "Хищник" один раз
    public void getFoodWithoutParameter () throws Exception {
        lion = new Lion(felineMock);
        lion.getFood();
        Mockito.verify(felineMock, Mockito.times(1)).getFood("Хищник");
    }

}