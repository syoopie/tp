package nustracker.logic.commands;

import static java.util.Objects.requireNonNull;

import nustracker.commons.core.Messages;
import nustracker.model.Model;
import nustracker.model.student.NusNetIdContainsKeywordsPredicate;

/**
 * Filters and lists all students in address book whose NUS NetID contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterIdCommand extends FilterCommand {

    private final NusNetIdContainsKeywordsPredicate predicate;

    public FilterIdCommand(NusNetIdContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterIdCommand // instanceof handles nulls
                && predicate.equals(((FilterIdCommand) other).predicate)); // state check
    }
}
