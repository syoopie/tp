package nustracker.testutil;

import java.util.Set;

import nustracker.logic.commands.AddCommand;
import nustracker.logic.commands.EditCommand;
import nustracker.logic.parser.CliSyntax;
import nustracker.model.student.Student;
import nustracker.model.tag.Tag;

/**
 * A utility class for Student.
 */
public class StudentUtil {

    /**
     * Returns an add command string for adding the {@code student}.
     */
    public static String getAddCommand(Student student) {
        return AddCommand.COMMAND_WORD + " " + getStudentDetails(student);
    }

    /**
     * Returns the part of command string for the given {@code student}'s details.
     */
    public static String getStudentDetails(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + student.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE + student.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + student.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_YEAR + student.getYear().value + " ");
        sb.append(CliSyntax.PREFIX_MAJOR + student.getMajor().value + " ");
        sb.append(CliSyntax.PREFIX_NUSNETID + student.getNusNetId().value + " ");
        student.getTags().stream().forEach(s ->
                sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudentDescriptor}'s details.
     */
    public static String getEditStudentDescriptorDetails(EditCommand.EditStudentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(CliSyntax.PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(CliSyntax.PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(CliSyntax.PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getYear().ifPresent(year -> sb.append(CliSyntax.PREFIX_YEAR).append(year.value).append(" "));
        descriptor.getMajor().ifPresent(major -> sb.append(CliSyntax.PREFIX_MAJOR).append(major.value).append(" "));
        descriptor.getNusNetId().ifPresent(nusNetId -> sb.append(CliSyntax.PREFIX_NUSNETID).append(
                nusNetId.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(CliSyntax.PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(CliSyntax.PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
