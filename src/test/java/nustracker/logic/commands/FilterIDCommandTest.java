package nustracker.logic.commands;

import nustracker.commons.core.Messages;
import nustracker.model.Model;
import nustracker.model.ModelManager;
import nustracker.model.UserPrefs;
import nustracker.model.student.NusNetIdContainsKeywordsPredicate;
import nustracker.testutil.TypicalStudents;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static nustracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterIDCommand}.
 */
public class FilterIDCommandTest {
    private Model model = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NusNetIdContainsKeywordsPredicate firstPredicate =
                new NusNetIdContainsKeywordsPredicate(Collections.singletonList("first"));
        NusNetIdContainsKeywordsPredicate secondPredicate =
                new NusNetIdContainsKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterIDCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterIDCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterIDCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 0);
        NusNetIdContainsKeywordsPredicate predicate = preparePredicate(" ");
        FilterCommand command = new FilterIDCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredStudentList());
    }

    @Test
    public void execute_multipleKeywords_multipleStudentsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        NusNetIdContainsKeywordsPredicate predicate = preparePredicate("e9034800 e8123198 e9012390");
        FilterCommand command = new FilterIDCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        CommandTestUtil.assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalStudents.ALICE, TypicalStudents.BENSON, TypicalStudents.DANIEL),
                model.getFilteredStudentList());
    }

    /**
     * Parses {@code userInput} into a {@code NusNetIdContainsKeywordsPredicate}.
     */
    private NusNetIdContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NusNetIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
