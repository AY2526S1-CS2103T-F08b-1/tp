package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.person.Stats;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Faker"), new Role("mid"), new Rank("Diamond"),
                    new Champion("Azir"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.4", "3400", "4.6")
                            .addLatestStats("4.8", "1100", "4.8")
                            .addLatestStats("8.9", "1850", "3.9")),
            new Person(new Name("Oner"), new Role("jungle"), new Rank("Diamond"),
                    new Champion("Xin zhao"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.5", "-1150", "5.7")
                            .addLatestStats("7.4", "-3850", "4.8")
                            .addLatestStats("4.4", "4950", "5.3")),
            new Person(new Name("Gumayusi"), new Role("adc"), new Rank("Diamond"),
                    new Champion("Xayah"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.9", "5000", "4.7")
                            .addLatestStats("9.5", "3350", "6.9")
                            .addLatestStats("6.5", "-1800", "5.4")),
            new Person(new Name("Zeus"), new Role("top"), new Rank("Master"),
                    new Champion("Jayce"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.5", "1600", "5.2")
                            .addLatestStats("3.7", "2850", "6.3")
                            .addLatestStats("10.3", "-2750", "3.6")),
            new Person(new Name("Keria"), new Role("support"), new Rank("Challenger"),
                    new Champion("Bard"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.5", "-200", "4.0")
                            .addLatestStats("3.8", "-2450", "5.5")
                            .addLatestStats("4.7", "1350", "4.7")),
            new Person(new Name("Deft"), new Role("adc"), new Rank("Master"),
                    new Champion("Caitlyn"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.8", "-2600", "5.9")
                            .addLatestStats("10.7", "-2800", "6.5")
                            .addLatestStats("3.7", "1000", "6.5")),
            new Person(new Name("Chovy"), new Role("mid"), new Rank("Grandmaster"),
                    new Champion("Yone"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.8", "-1650", "3.8")
                            .addLatestStats("4.7", "-3500", "3.4")
                            .addLatestStats("9.4", "3200", "4.4")),
            new Person(new Name("ShowMaker"), new Role("mid"), new Rank("Challenger"),
                    new Champion("Katarina"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.3", "150", "5.0")
                            .addLatestStats("6.6", "650", "4.1")
                            .addLatestStats("8.1", "-450", "6.4")),
            new Person(new Name("Canyon"), new Role("jungle"), new Rank("Emerald"),
                    new Champion("Aatrox"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.4", "2700", "3.5")
                            .addLatestStats("3.9", "-250", "4.9")
                            .addLatestStats("5.6", "2400", "5.7")),
            new Person(new Name("Ruler"), new Role("adc"), new Rank("Platinum"),
                    new Champion("Caitlyn"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.7", "3650", "3.9")
                            .addLatestStats("5.6", "3450", "2.6")
                            .addLatestStats("9.6", "50", "5.1")),
            new Person(new Name("Scout"), new Role("mid"), new Rank("Gold"),
                    new Champion("Kassadin"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("3.6", "-1450", "3.7")
                            .addLatestStats("7.0", "-3250", "5.1")
                            .addLatestStats("5.9", "-1400", "4.9")),
            new Person(new Name("Knight"), new Role("mid"), new Rank("Silver"),
                    new Champion("Zoe"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("10.1", "2550", "6.2")
                            .addLatestStats("6.7", "-2900", "8.0")
                            .addLatestStats("4.0", "-3800", "6.8")),
            new Person(new Name("Meiko"), new Role("support"), new Rank("Bronze"),
                    new Champion("Sona"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.3", "3500", "4.4")
                            .addLatestStats("4.5", "-3650", "4.6")
                            .addLatestStats("7.9", "900", "4.4")),
            new Person(new Name("Peanut"), new Role("jungle"), new Rank("Grandmaster"),
                    new Champion("Teemo"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.3", "1250", "4.7")
                            .addLatestStats("3.9", "3900", "5.8")
                            .addLatestStats("7.5", "-850", "6.8")),
            new Person(new Name("Lehends"), new Role("support"), new Rank("Challenger"),
                    new Champion("Blitzcrank"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.1", "800", "3.9")
                            .addLatestStats("5.3", "2400", "3.8")
                            .addLatestStats("5.1", "-1550", "6.1")),
            new Person(new Name("Viper"), new Role("adc"), new Rank("Emerald"),
                    new Champion("Ezreal"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.3", "-1800", "4.1")
                            .addLatestStats("5.1", "-4750", "5.1")
                            .addLatestStats("3.6", "3150", "4.4")),
            new Person(new Name("Tarzan"), new Role("jungle"), new Rank("Platinum"),
                    new Champion("Nasus"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.5", "50", "4.9")
                            .addLatestStats("4.0", "1100", "3.9")
                            .addLatestStats("5.0", "250", "6.5")),
            new Person(new Name("Doinb"), new Role("mid"), new Rank("Gold"),
                    new Champion("Yasuo"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.1", "700", "5.3")
                            .addLatestStats("6.8", "-4450", "3.3")
                            .addLatestStats("7.3", "-3000", "4.4")),
            new Person(new Name("TheShy"), new Role("top"), new Rank("Silver"),
                    new Champion("Darius"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.9", "0", "4.3")
                            .addLatestStats("6.0", "4900", "5.3")
                            .addLatestStats("9.3", "-850", "4.4")),
            new Person(new Name("Rookie"), new Role("mid"), new Rank("Bronze"),
                    new Champion("Talon"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.7", "-550", "4.5")
                            .addLatestStats("6.8", "4800", "5.5")
                            .addLatestStats("9.3", "2750", "5.2")),
            new Person(new Name("Uzi"), new Role("adc"), new Rank("Iron"),
                    new Champion("Ashe"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.4", "1300", "4.5")
                            .addLatestStats("10.7", "2700", "6.0")
                            .addLatestStats("5.9", "4000", "4.2")),
            new Person(new Name("JackeyLove"), new Role("adc"), new Rank("Diamond"),
                    new Champion("Jinx"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.0", "4950", "3.8")
                            .addLatestStats("6.1", "-2350", "5.4")
                            .addLatestStats("7.2", "900", "6.9")),
            new Person(new Name("Ming"), new Role("support"), new Rank("Master"),
                    new Champion("Nautilus"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.5", "1300", "5.1")
                            .addLatestStats("4.5", "-2100", "5.1")
                            .addLatestStats("7.7", "1250", "6.8")),
            new Person(new Name("Karsa"), new Role("jungle"), new Rank("Grandmaster"),
                    new Champion("Shen"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.6", "-1000", "5.2")
                            .addLatestStats("7.3", "-1500", "3.6")
                            .addLatestStats("8.8", "4300", "5.7")),
            new Person(new Name("Baolan"), new Role("support"), new Rank("Challenger"),
                    new Champion("Soraka"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.0", "-150", "5.6")
                            .addLatestStats("8.3", "-5000", "6.2")
                            .addLatestStats("6.6", "3150", "5.1")),
            new Person(new Name("Clid"), new Role("jungle"), new Rank("Emerald"),
                    new Champion("Renekton"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("3.7", "-1050", "7.0")
                            .addLatestStats("4.3", "3050", "2.9")
                            .addLatestStats("8.4", "-1150", "4.3")),
            new Person(new Name("BeryL"), new Role("support"), new Rank("Platinum"),
                    new Champion("Nami"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.4", "-4550", "5.1")
                            .addLatestStats("6.4", "2650", "4.7")
                            .addLatestStats("9.3", "4750", "4.5")),
            new Person(new Name("Zeka"), new Role("mid"), new Rank("Gold"),
                    new Champion("Syndra"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("4.8", "4100", "5.8")
                            .addLatestStats("6.6", "1300", "6.1")
                            .addLatestStats("9.8", "3750", "5.6")),
            new Person(new Name("Gala"), new Role("adc"), new Rank("Silver"),
                    new Champion("Vayne"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("4.8", "-50", "5.4")
                            .addLatestStats("10.8", "3000", "6.7")
                            .addLatestStats("8.5", "2100", "6.2")),
            new Person(new Name("Wei"), new Role("jungle"), new Rank("Bronze"),
                    new Champion("Tryndamere"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.7", "-300", "4.7")
                            .addLatestStats("8.2", "1850", "7.3")
                            .addLatestStats("10.6", "-750", "6.7")),
            new Person(new Name("Crisp"), new Role("support"), new Rank("Iron"),
                    new Champion("Janna"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.0", "4700", "6.1")
                            .addLatestStats("9.2", "4400", "3.1")
                            .addLatestStats("9.3", "4050", "2.2")),
            new Person(new Name("Lwx"), new Role("adc"), new Rank("Master"),
                    new Champion("Caitlyn"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.8", "-250", "5.1")
                            .addLatestStats("6.4", "1700", "5.2")
                            .addLatestStats("8.6", "4950", "5.2")),
            new Person(new Name("FoFo"), new Role("mid"), new Rank("Grandmaster"),
                    new Champion("Galio"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.8", "-2600", "4.6")
                            .addLatestStats("7.3", "3400", "8.1")
                            .addLatestStats("9.7", "3850", "4.9")),
            new Person(new Name("Maple"), new Role("mid"), new Rank("Challenger"),
                    new Champion("Ahri"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.5", "4200", "5.3")
                            .addLatestStats("5.2", "4150", "4.1")
                            .addLatestStats("8.1", "4300", "7.4")),
            new Person(new Name("SwordArt"), new Role("support"), new Rank("Emerald"),
                    new Champion("Thresh"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.0", "3150", "6.0")
                            .addLatestStats("5.5", "4800", "4.8")
                            .addLatestStats("7.6", "4050", "3.6")),
            new Person(new Name("Hanabi"), new Role("top"), new Rank("Platinum"),
                    new Champion("Riven"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("10.5", "2800", "4.1")
                            .addLatestStats("9.9", "4350", "5.6")
                            .addLatestStats("5.3", "4450", "5.0")),
            new Person(new Name("River"), new Role("jungle"), new Rank("Gold"),
                    new Champion("Aatrox"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.5", "750", "3.9")
                            .addLatestStats("3.6", "3900", "3.0")
                            .addLatestStats("8.8", "-2600", "4.5")),
            new Person(new Name("CoreJJ"), new Role("support"), new Rank("Silver"),
                    new Champion("Rakan"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.8", "-3250", "5.6")
                            .addLatestStats("9.7", "-4100", "6.7")
                            .addLatestStats("7.8", "2850", "4.5")),
            new Person(new Name("Bjergsen"), new Role("mid"), new Rank("Bronze"),
                    new Champion("Annie"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.6", "4350", "4.6")
                            .addLatestStats("5.1", "-750", "3.8")
                            .addLatestStats("4.4", "1050", "6.5")),
            new Person(new Name("Doublelift"), new Role("adc"), new Rank("Iron"),
                    new Champion("Xayah"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.9", "4750", "4.9")
                            .addLatestStats("10.7", "4200", "6.5")
                            .addLatestStats("4.8", "-4100", "3.7")),
            new Person(new Name("Sneaky"), new Role("adc"), new Rank("Diamond"),
                    new Champion("Ezreal"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("4.0", "-600", "5.9")
                            .addLatestStats("7.8", "-2950", "4.9")
                            .addLatestStats("4.9", "-3200", "4.3")),
            new Person(new Name("Meteos"), new Role("jungle"), new Rank("Master"),
                    new Champion("Teemo"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("10.6", "-1350", "5.6")
                            .addLatestStats("10.1", "4800", "4.6")
                            .addLatestStats("5.2", "-1300", "3.7")),
            new Person(new Name("Impact"), new Role("top"), new Rank("Grandmaster"),
                    new Champion("Malphite"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("3.6", "-4650", "6.1")
                            .addLatestStats("7.4", "-250", "3.1")
                            .addLatestStats("3.7", "2400", "5.3")),
            new Person(new Name("Xmithie"), new Role("jungle"), new Rank("Challenger"),
                    new Champion("Nasus"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.6", "-4650", "5.6")
                            .addLatestStats("6.3", "2350", "4.6")
                            .addLatestStats("6.9", "-4300", "7.0")),
            new Person(new Name("Aphromoo"), new Role("support"), new Rank("Emerald"),
                    new Champion("Lulu"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("10.3", "300", "6.6")
                            .addLatestStats("6.4", "1900", "5.8")
                            .addLatestStats("5.1", "3450", "6.3")),
            new Person(new Name("Blaber"), new Role("jungle"), new Rank("Platinum"),
                    new Champion("Shen"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.1", "300", "4.9")
                            .addLatestStats("6.6", "-3950", "4.6")
                            .addLatestStats("9.1", "-3300", "5.2")),
            new Person(new Name("Vulcan"), new Role("support"), new Rank("Gold"),
                    new Champion("Sona"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.8", "3850", "4.2")
                            .addLatestStats("6.3", "4450", "4.7")
                            .addLatestStats("6.2", "1500", "7.3")),
            new Person(new Name("Zven"), new Role("support"), new Rank("Silver"),
                    new Champion("Leona"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.9", "4000", "6.2")
                            .addLatestStats("6.7", "-4200", "5.6")
                            .addLatestStats("10.3", "600", "5.4")),
            new Person(new Name("Spica"), new Role("jungle"), new Rank("Bronze"),
                    new Champion("Renekton"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("10.3", "-2450", "4.4")
                            .addLatestStats("8.3", "-2050", "5.8")
                            .addLatestStats("7.8", "4050", "3.7")),
            new Person(new Name("Inspired"), new Role("jungle"), new Rank("Iron"),
                    new Champion("Tryndamere"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("4.4", "2200", "1.9")
                            .addLatestStats("5.3", "-750", "4.2")
                            .addLatestStats("7.3", "2050", "5.3")),
            new Person(new Name("Untara"), new Role("top"), new Rank("Grandmaster"),
                    new Champion("Teemo"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("9.1", "-2250", "4.9")
                            .addLatestStats("3.9", "-2750", "3.3")
                            .addLatestStats("6.8", "-3950", "3.3")),
            new Person(new Name("Kiin"), new Role("top"), new Rank("Challenger"),
                    new Champion("K'sante"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("8.7", "-4100", "5.4")
                            .addLatestStats("10.3", "-3400", "4.5")
                            .addLatestStats("4.5", "-4950", "4.3")),
            new Person(new Name("Morgan"), new Role("top"), new Rank("Emerald"),
                    new Champion("Renekton"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.9", "2200", "5.1")
                            .addLatestStats("6.5", "-3150", "3.7")
                            .addLatestStats("8.4", "3150", "2.9")),
            new Person(new Name("Doran"), new Role("top"), new Rank("Platinum"),
                    new Champion("Malphite"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("7.4", "2350", "4.5")
                            .addLatestStats("6.7", "1500", "4.5")
                            .addLatestStats("9.7", "850", "5.1")),
            new Person(new Name("Ireking"), new Role("top"), new Rank("Gold"),
                    new Champion("Irelia"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.0", "-1700", "4.8")
                            .addLatestStats("6.7", "1400", "6.6")
                            .addLatestStats("3.6", "-1550", "4.5")),
            new Person(new Name("Adam"), new Role("top"), new Rank("Silver"),
                    new Champion("Garen"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.9", "4600", "5.2")
                            .addLatestStats("9.5", "-4750", "4.1")
                            .addLatestStats("5.5", "-300", "6.4")),
            new Person(new Name("Kingen"), new Role("top"), new Rank("Bronze"),
                    new Champion("Aatrox"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("6.7", "-4350", "4.6")
                            .addLatestStats("6.0", "-3250", "6.0")
                            .addLatestStats("4.8", "3550", "6.3")),
            new Person(new Name("Driver"), new Role("top"), new Rank("Iron"),
                    new Champion("Sion"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("5.5", "-1150", "5.8")
                            .addLatestStats("6.8", "-4750", "4.9")
                            .addLatestStats("9.0", "-200", "5.4")),
            new Person(new Name("Bwipbo"), new Role("top"), new Rank("Diamond"),
                    new Champion("Mordekaiser"), getTagSet(), 0, 0,
                    new Stats()
                            .addLatestStats("4.4", "-1550", "5.5")
                            .addLatestStats("8.5", "-3850", "4.7")
                            .addLatestStats("9.8", "-2800", "4.9")),

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        Person[] samplePersons = getSamplePersons();

        for (Person samplePerson : samplePersons) {
            sampleAb.addPerson(samplePerson);
        }

        List<Person> teamRoster = Arrays.asList(Arrays.copyOfRange(samplePersons, 0, 5));
        Team sampleTeam = new Team(teamRoster);
        sampleAb.addTeam(sampleTeam);

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
