package com.appsdeveloperblog;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math operations in Calculator class")
class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void setup() {
        //System.out.println("@BeforeAll Executado antes de qualquer método de teste ser executado");
    }

    @AfterAll
    static void cleanup() {
        //System.out.println("@AfterAll Executado após todos os métodos de teste terem sido executados");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        calculator = new Calculator();
        //System.out.println("@BeforeEach Executado antes de cada método de teste ser executado");
    }

    @AfterEach
    void afterEachTestMethod() {
        //System.out.println("@AfterEach Executado após cada método de teste ser executado");
    }

    @DisplayName("Test 4/2 = 2")
    @Test
    void integerDivision() {
        //Arrange  //Given
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;

        //Act  //When
        int actualResult = calculator.integerDivision(dividend, divisor);

        //Assert //Then
        assertEquals(expectedResult, actualResult);
    }

    @DisplayName("Division by zero")
    @Test
    void testIntergerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException() {
        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";

        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            calculator.integerDivision(dividend, divisor);
        }, "Division by zero should have thrown an Arithmetic exception");

        assertEquals(expectedExceptionMessage, actualException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jonh", "Kate", "Alice"})
    void valueSourceDemonstration(String firtName) {
        System.out.println(firtName);
        assertNotNull(firtName);
    }


    @DisplayName("Test subtraction with a list of value")
    @ParameterizedTest
    //@MethodSource("integerSubtractionInputParameters")
   /* @CsvSource({"33, 1, 32",
    "24, 1, 23",
    "54, 1, 53"})*/
    @CsvFileSource(resources = "/integerSubtraction.csv")
    void integerSubtraction(int minuend, int subtrahend, int expectedResult) {

        int actualResult = calculator.integerSubtraction(minuend, subtrahend);

        assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

   /* private static Stream<Arguments> integerSubtractionInputParameters() {
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(54, 1, 53),
                Arguments.of(24, 1, 23));
    }*/
}
