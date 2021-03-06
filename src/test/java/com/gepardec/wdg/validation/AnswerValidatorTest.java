package com.gepardec.wdg.validation;

import com.gepardec.wdg.challenge.model.Answer;
import com.gepardec.wdg.challenge.validation.AnswerValidator;
import com.gepardec.wdg.client.personio.Source;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnswerValidatorTest {

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @Mock
    private ConstraintValidatorContext context;

    private AnswerValidator validator;

    @BeforeEach
    void beforeEach() {
        validator = new AnswerValidator();
    }

    @ParameterizedTest
    //@WINStage3: Ist Dein Name auch dabei? ;)
    //@EnumSource(value = Source.class, names = {"ANNA", "JULIAN", "THOMAS", "LENA"})
    //@EnumSource(value = Source.class, names = {"FACEBOOK","INSTAGRAM","XING"})
    
    //Niederschick: ich lassen den Testfall nur für jene Sources durchführen, bei denen KEINE emptyOtherSource erwünscht ist
    //Niederschick: Bei den anderen würde die empty-Source auf True validieren - da ja richtig
    //Niederschick: Den Hinweise "Ist dein Name auch dabei?" verstehe ich nicht. Wüsste, nicht wie ich meinen Namen in die enum Source bringen würde. Auch in der otherSource müsste ja ein Mitarbeiter von euch stehen.
    //Niederschick: bin gespannt, wie es gedacht war :)
    @EnumSource(value = Source.class, names = {"SONSTIGES","MESSEN","MEETUPS","EMPFEHLUNG"})
    //@EnumSource(value = Source.class)
    void isValid_withValidSourcesEmptyOtherSource_thenFalse(final Source source) {
        when(constraintViolationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilderCustomizableContext);
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(constraintViolationBuilder);
        final Answer given = new Answer();
        given.setSource(source);
        given.setOtherSource(null);
        Assertions.assertFalse(validator.isValid(given, context));

        verify(context, times(1)).buildConstraintViolationWithTemplate(anyString());
        verify(constraintViolationBuilder, times(1)).addPropertyNode(anyString());
        verify(nodeBuilderCustomizableContext, times(1)).addConstraintViolation();
    }

    @Test
    void isValid_withNull_thenFalse() {
        Assertions.assertFalse(validator.isValid(null, context));
    }

    @Test
    void isValid_withNullSource_thenTrue() {
        final Answer given = new Answer();
        given.setSource(null);
        given.setOtherSource(null);
        Assertions.assertTrue(validator.isValid(given, context));
    }

    @Test
    void isValid_withSourceLINKEDIN_thenTrue() {
        final Answer given = new Answer();
        given.setSource(Source.LINKEDIN);
        given.setOtherSource(null);
        Assertions.assertTrue(validator.isValid(given, context));
    }

    @ParameterizedTest
    //@WINStage3: Na, deinen Namen schon gefunden? Eine Chance gibts noch. ;)
    //@EnumSource(value = Source.class, names = {"Josef", "Heinz", "Ilse", "Heidi"})
    
    //Niederschick: wie oben - weiß nicht, wie das mit dem eigenen Namen gemeint ist
    //Niederschick: Durchführung mit allen Sources, die werden alle true. Evtl. bei den nicht-otherSource-Einträgen unerwünscht. Aber läuft :)
    @EnumSource(value = Source.class)
    void isValid_withValidSourceAndOtherSource_thenTrue(final Source source) {
        final Answer given = new Answer();
        given.setSource(source);
        given.setOtherSource("My friend");
        Assertions.assertTrue(validator.isValid(given, context));
    }
}
