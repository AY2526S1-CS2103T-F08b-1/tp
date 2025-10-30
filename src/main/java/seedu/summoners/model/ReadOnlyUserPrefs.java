package seedu.summoners.model;

import java.nio.file.Path;

import seedu.summoners.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getSummonersBookFilePath();

}
