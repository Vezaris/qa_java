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
public class LionParamTest {
    @Mock
    Feline felineMock;
    String lionSex;
    Lion lion;
    boolean hasMane;
    enum Type {POSITIVE, NEGATIVE}
    Type type;

    public LionParamTest(Type type, String lionSex, boolean hasMane){
        this.type = type;
        this.lionSex = lionSex;
        this.hasMane = hasMane;
    }

    @Parameterized.Parameters(name = "{index}: {0} - {1} - {2}")
    public static Object[][] getDataEntry() {
        return new Object[][]{
            {Type.POSITIVE, "Самец", true},
            {Type.POSITIVE, "Самка", false},
            {Type.NEGATIVE, "Другое", false},
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
        Assume.assumeTrue(type == Type.POSITIVE);
        int expected = 1;
        lion = new Lion(felineMock, lionSex);
        Mockito.when(felineMock.getKittens()).thenReturn(1);
        int actual = lion.getKittens();
        assertEquals(expected, actual);
    }
    @Test // Возвращается корректное значение hasMane в зависимости от пола
    public void getHasMane() throws Exception{
        Assume.assumeTrue(type == Type.POSITIVE);
            lion = new Lion(felineMock, lionSex);
            assertEquals(hasMane, lion.doesHaveMane());
    }
    @Test // Возвращается ожидаемое исключение
    public void getExpectedErrorCreateObject() {
        Assume.assumeTrue(type == Type.NEGATIVE);
        String expectedErrorMessage = "Используйте допустимые значения пола животного - самей или самка";
        try {
            lion = new Lion(felineMock, "Другое");
            throw new Exception("Отсутствует ожидаемая ошибка");
        } catch (Exception exception) {
            assertEquals(expectedErrorMessage, exception.getMessage());
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