package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Person's champion in the address book.
 * Guarantees: immutable; valid if the name exists in champions.txt.
 */
public class Champion {

    public static final String MESSAGE_CONSTRAINTS =
            "Champion must be one of the valid champion's name in League of Legends";

    /** Path to the champions list file in resources. */
    private static final String CHAMPION_FILE = "/champions/champions.txt";

    /** Stores all valid champions loaded from the file. */
    private static final Set<String> VALID_CHAMPIONS = loadChampions();

    public final String value;

    /**
     * Constructs a {@code Champion}.
     *
     * @param champion A valid champion name.
     */
    public Champion(String champion) {
        requireNonNull(champion);
        checkArgument(isValidChampion(champion), MESSAGE_CONSTRAINTS);
        this.value = champion.trim().substring(0, 1).toUpperCase()
                + champion.trim().substring(1).toLowerCase();;
    }

    /**
     * Loads champion names from champions.txt into a HashSet.
     * The file should have one champion name per line.
     *
     * @return a set of valid champion names.
     */
    private static Set<String> loadChampions() {
        Set<String> champions = new HashSet<>();

        try {
            InputStream in = Champion.class.getResourceAsStream(CHAMPION_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String clean = line.trim();
                if (!clean.isEmpty()) {
                    champions.add(clean.toLowerCase());
                }
            }

        } catch (IOException | NullPointerException e) {
            System.err.println("⚠️ Warning: Unable to load champions from " + CHAMPION_FILE);
        }

        return champions;
    }

    /**
     * Returns true if the given string matches one of the champions loaded from the file.
     */
    public static boolean isValidChampion(String test) {
        return VALID_CHAMPIONS.contains(test.toLowerCase().trim());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Champion)) {
            return false;
        }
        Champion otherChampion = (Champion) other;
        return value.equalsIgnoreCase(otherChampion.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
