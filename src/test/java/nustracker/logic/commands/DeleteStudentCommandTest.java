package nustracker.logic.commands;

import static nustracker.logic.commands.CommandTestUtil.assertCommandFailure;
import static nustracker.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nustracker.logic.commands.CommandTestUtil.showStudentAtIndex;
import static nustracker.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static nustracker.testutil.TypicalStudents.NUSNETID_MISSING;
import static nustracker.testutil.TypicalStudents.NUSNETID_ONE;
import static nustracker.testutil.TypicalStudents.NUSNETID_TWO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nustracker.commons.core.Messages;
import nustracker.logic.commands.exceptions.CommandException;
import nustracker.model.Model;
import nustracker.model.ModelManager;
import nustracker.model.UserPrefs;
import nustracker.model.student.Student;
import nustracker.testutil.TypicalStudents;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteStudentCommand}.
 */
public class DeleteStudentCommandTest {

    private Model model = new ModelManager(TypicalStudents.getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNusNetIdUnfilteredList_success() {
        Student studentToDelete = model.getStudent(NUSNETID_ONE);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(NUSNETID_ONE);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNusNetIdUnfilteredList_throwsCommandException() {
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(NUSNETID_MISSING);
        try {
            deleteStudentCommand.execute(model);
        } catch (CommandException e) {
            e.printStackTrace();
        }
        assertCommandFailure(deleteStudentCommand, model,
                String.format(Messages.MESSAGE_INVALID_STUDENT_NUSNETID, NUSNETID_MISSING));
    }

    @Test
    public void execute_validNusNetIdFilteredList_success() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        Student studentToDelete = model.getStudent(NUSNETID_ONE);
        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(NUSNETID_ONE);

        String expectedMessage = String.format(DeleteStudentCommand.MESSAGE_DELETE_STUDENT_SUCCESS, studentToDelete);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteStudent(studentToDelete);
        showNoStudent(expectedModel);

        assertCommandSuccess(deleteStudentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNusNetIdFilteredList_throwsCommandException() {
        showStudentAtIndex(model, INDEX_FIRST_STUDENT);

        DeleteStudentCommand deleteStudentCommand = new DeleteStudentCommand(NUSNETID_MISSING);

        assertCommandFailure(deleteStudentCommand, model,
                String.format(Messages.MESSAGE_INVALID_STUDENT_NUSNETID, NUSNETID_MISSING));
    }

    @Test
    public void equals() {
        DeleteStudentCommand deleteFirstCommand = new DeleteStudentCommand(NUSNETID_ONE);
        DeleteStudentCommand deleteSecondCommand = new DeleteStudentCommand(NUSNETID_TWO);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteStudentCommand deleteFirstCommandCopy = new DeleteStudentCommand(NUSNETID_ONE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudent(Model model) {
        model.updateFilteredStudentList(p -> false);

        assertTrue(model.getFilteredStudentList().isEmpty());
    }
}
