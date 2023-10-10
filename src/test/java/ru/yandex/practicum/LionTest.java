package ru.yandex.practicum;

import org.junit.Assume;
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
    @Mock
    Feline felineMock;
    String lionSex;
    Lion lion;
    enum Type {POSITIVE, ALL}
    Type type;

    public LionTest(Type type, String lionSex){
        this.type = type;
        this.lionSex = lionSex;
    }

    @Parameterized.Parameters
    public static Object[][] getDataEntry() {
        return new Object[][]{
                {Type.POSITIVE, "Самец"},
                {Type.POSITIVE, "Самка"},
                {Type.ALL, "Самец"},
                {Type.ALL, "Самка"},
                {Type.ALL, "Другое"},
        };
    }
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test // Проверка, что метод feline.getKittens вызывается без параметров один раз
    public void getKittensWithoutParameter() throws Exception {
        Assume.assumeTrue(type == Type.POSITIVE);
        lion = new Lion(felineMock, lionSex);
        lion.getKittens();
        Mockito.verify(felineMock, Mockito.times(1)).getKittens();
    }
    @Test // Проверка, что метод getKittens возвращает правильное количество котят(1)
    public void getKittensReturnCount() throws Exception {
        int expected = 1;
        Assume.assumeTrue(type == Type.POSITIVE);
        lion = new Lion(felineMock, lionSex);
        Mockito.when(felineMock.getKittens()).thenReturn(1);
        int actual = lion.getKittens();
        assertEquals(expected, actual);
    }
    @Test // Возвращается корректное значение hasMane в зависимости от пола
    public void doesHaveMane() throws Exception {
        String expectedMessageErrorSex = "Используйте допустимые значения пола животного - самей или самка";
        Assume.assumeTrue(type == Type.ALL);
        try {
            lion = new Lion(felineMock, lionSex);
            if ("Самец".equals(lionSex)) {
                assertTrue(lion.doesHaveMane());
            } else if ("Самка".equals(lionSex)) {
                assertFalse(lion.doesHaveMane());
            } else {
                throw new Exception ("Отсутствует ошибка: " + expectedMessageErrorSex);
            }
        } catch (Exception exception) {
            if (exception.getMessage().equals(expectedMessageErrorSex)) {
            } else {
                throw new Exception(exception.getMessage());
            }
        }
    }
    @Test // Проверка, что метод getFood получает список еды для хищника
    public void getFoodForPredator() throws Exception {
        Assume.assumeTrue(type == Type.POSITIVE);
        List<String> expected = List.of("Животные", "Птицы", "Рыба");
        lion = new Lion(felineMock, lionSex);
        Mockito.when(felineMock.getFood("Хищник")).thenReturn(expected);
        List<String> actual = lion.getFood();
        assertEquals(expected, actual);
    }
    @Test // Проверка, что метод getFood вызывает feline.getFood с параметром "Хищник" один раз
    public void getFoodWithoutParameter () throws Exception {
        Assume.assumeTrue(type == Type.POSITIVE);
        lion = new Lion(felineMock, lionSex);
        lion.getFood();
        Mockito.verify(felineMock, Mockito.times(1)).getFood("Хищник");
    }
}