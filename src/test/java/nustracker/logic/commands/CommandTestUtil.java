package nustracker.logic.commands;

import static nustracker.logic.parser.CliSyntax.PREFIX_DATE;
import static nustracker.logic.parser.CliSyntax.PREFIX_NAME;
import static nustracker.logic.parser.CliSyntax.PREFIX_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nustracker.commons.core.index.Index;
import nustracker.logic.commands.exceptions.CommandException;
import nustracker.logic.parser.CliSyntax;
import nustracker.model.AddressBook;
import nustracker.model.Model;
import nustracker.model.student.NameContainsKeywordsPredicate;
import nustracker.model.student.Student;
import nustracker.testutil.Assert;
import nustracker.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_YEAR_AMY = "1";
    public static final String VALID_YEAR_BOB = "2";
    public static final String VALID_MAJOR_AMY = "CS";
    public static final String VALID_MAJOR_BOB = "IS";
    public static final String VALID_NUSNETID_AMY = "e1283901";
    public static final String VALID_NUSNETID_BOB = "e1238010";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + CliSyntax.PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + CliSyntax.PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + CliSyntax.PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String YEAR_DESC_AMY = " " + CliSyntax.PREFIX_YEAR + VALID_YEAR_AMY;
    public static final String YEAR_DESC_BOB = " " + CliSyntax.PREFIX_YEAR + VALID_YEAR_BOB;
    public static final String MAJOR_DESC_AMY = " " + CliSyntax.PREFIX_MAJOR + VALID_MAJOR_AMY;
    public static final String MAJOR_DESC_BOB = " " + CliSyntax.PREFIX_MAJOR + VALID_MAJOR_BOB;
    public static final String NUSNETID_DESC_AMY = " " + CliSyntax.PREFIX_NUSNETID + VALID_NUSNETID_AMY;
    public static final String NUSNETID_DESC_AMY_WO_LEADING_SPACE = CliSyntax.PREFIX_NUSNETID + VALID_NUSNETID_AMY;
    public static final String NUSNETID_DESC_BOB = " " + CliSyntax.PREFIX_NUSNETID + VALID_NUSNETID_BOB;
    public static final String NUSNETID_DESC_BOB_WO_LEADING_SPACE = CliSyntax.PREFIX_NUSNETID + VALID_NUSNETID_BOB;
    public static final String TAG_DESC_FRIEND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + CliSyntax.PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + CliSyntax.PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + CliSyntax.PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_YEAR_DESC = " " + CliSyntax.PREFIX_YEAR + "a"; //letters not allowed
    public static final String INVALID_MAJOR_DESC = " "
            + CliSyntax.PREFIX_MAJOR + "computer science"; //use abbreviation, not ff
    public static final String INVALID_NUSNETID_DESC = " " + CliSyntax.PREFIX_NUSNETID + "e123123123"; //e and 7 digits
    public static final String INVALID_TAG_DESC = " " + CliSyntax.PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    // For Events
    public static final String VALID_EVENTNAME_TEST = "Test event";
    public static final String VALID_EVENTNAME_FINAL = "Final event";
    public static final String VALID_EVENTDATE_TEST = "09-10-2021";
    public static final String VALID_EVENTDATE_FINAL = "20-11-2021";
    public static final String VALID_EVENTTIME_TEST = "1800";
    public static final String VALID_EVENTTIME_FINAL = "2010";
    public static final String VALID_PARTICIPANT_TEST = "Joel";
    public static final String VALID_PARTICIPANT_FINAL = "Sung";

    public static final String NAME_DESC_TEST = " " + PREFIX_NAME + VALID_EVENTNAME_TEST;
    public static final String NAME_DESC_FINAL = " " + PREFIX_NAME + VALID_EVENTNAME_FINAL;
    public static final String DATE_DESC_TEST = " " + PREFIX_DATE + VALID_EVENTDATE_TEST;
    public static final String DATE_DESC_FINAL = " " + PREFIX_DATE + VALID_EVENTDATE_FINAL;
    public static final String TIME_DESC_TEST = " " + PREFIX_TIME + VALID_EVENTTIME_TEST;
    public static final String TIME_DESC_FINAL = " " + PREFIX_TIME + VALID_EVENTTIME_FINAL;

    public static final String INVALID_EVENTNAME_DESC = " " + PREFIX_NAME + "Event&"; // '&' not allowed in names
    public static final String INVALID_EVENTDATE_DESC = " " + PREFIX_DATE + "09102021"; // missing dashes
    public static final String INVALID_EVENTTIME_DESC = " " + PREFIX_TIME + "12:30 pm"; // needs to be 24hr format

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withYear(VALID_YEAR_AMY)
                .withMajor(VALID_MAJOR_AMY)
                .withNusNetId(VALID_NUSNETID_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withYear(VALID_YEAR_BOB)
                .withMajor(VALID_MAJOR_BOB)
                .withNusNetId(VALID_NUSNETID_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

}
