package seedu.summoners.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.summoners.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.summoners.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.summoners.testutil.Assert.assertThrows;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIFTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FIRST_TEAM;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_FOURTH_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_SECOND_PLAYER;
import static seedu.summoners.testutil.TypicalIndexes.INDEX_THIRD_PLAYER;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.summoners.commons.core.index.Index;
import seedu.summoners.logic.commands.AddCommand;
import seedu.summoners.logic.commands.AddStatsCommand;
import seedu.summoners.logic.commands.ClearCommand;
import seedu.summoners.logic.commands.DeleteCommand;
import seedu.summoners.logic.commands.DeleteStatsCommand;
import seedu.summoners.logic.commands.EditCommand;
import seedu.summoners.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.summoners.logic.commands.ExitCommand;
import seedu.summoners.logic.commands.ExportCommand;
import seedu.summoners.logic.commands.FilterCommand;
import seedu.summoners.logic.commands.FilterCommand.FilterPlayerDescriptor;
import seedu.summoners.logic.commands.FindCommand;
import seedu.summoners.logic.commands.GroupCommand;
import seedu.summoners.logic.commands.HelpCommand;
import seedu.summoners.logic.commands.ImportCommand;
import seedu.summoners.logic.commands.ListCommand;
import seedu.summoners.logic.commands.ListTeamCommand;
import seedu.summoners.logic.commands.LoseCommand;
import seedu.summoners.logic.commands.MakeGroupCommand;
import seedu.summoners.logic.commands.ViewCommand;
import seedu.summoners.logic.commands.ViewTeamCommand;
import seedu.summoners.logic.commands.WinCommand;
import seedu.summoners.logic.parser.exceptions.ParseException;
import seedu.summoners.model.player.NameContainsKeywordsPredicate;
import seedu.summoners.model.player.Player;
import seedu.summoners.testutil.EditPlayerDescriptorBuilder;
import seedu.summoners.testutil.FilterPlayerDescriptorBuilder;
import seedu.summoners.testutil.PlayerBuilder;
import seedu.summoners.testutil.PlayerUtil;

public class SummonersBookParserTest {

    private final SummonersBookParser parser = new SummonersBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Player player = new PlayerBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PlayerUtil.getAddCommand(player));
        assertEquals(new AddCommand(player), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYER.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PLAYER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Player player = new PlayerBuilder().build();
        EditPlayerDescriptor descriptor = new EditPlayerDescriptorBuilder(player).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PLAYER.getOneBased() + " " + PlayerUtil.getEditPlayerDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PLAYER, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_export() throws Exception {
        // no path -> default path inside command
        ExportCommand expected1 = new ExportCommand(ExportCommand.Target.PLAYERS, null);
        assertEquals(expected1, parser.parseCommand("export players"));

        // with path
        ExportCommand expected2 =
                new ExportCommand(ExportCommand.Target.TEAMS, Paths.get("data/teams.csv"));
        assertEquals(expected2, parser.parseCommand("export teams to/data/teams.csv"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportCommand expected =
                new ImportCommand(Paths.get("data/players.csv"));
        assertEquals(expected, parser.parseCommand("import players from data/players.csv"));
    }

    @Test
    public void parseCommand_filter() throws Exception {
        FilterPlayerDescriptor descriptor = new FilterPlayerDescriptorBuilder()
                .withChampions("annie", "leblanc").build();
        assertTrue(parser.parseCommand(FilterCommand.COMMAND_WORD + " rk/gold") instanceof FilterCommand);
        assertEquals(parser.parseCommand(FilterCommand.COMMAND_WORD + " c/annie c/leblanc"),
                new FilterCommand(descriptor));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listTeam() throws Exception {
        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD) instanceof ListTeamCommand);
        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD + " 3") instanceof ListTeamCommand);
    }

    @Test
    public void parseCommand_makeGroup() throws Exception {
        List<Index> indices = Arrays.asList(INDEX_FIRST_PLAYER, INDEX_SECOND_PLAYER, INDEX_THIRD_PLAYER,
                INDEX_FOURTH_PLAYER, INDEX_FIFTH_PLAYER);

        String arguments = indices.stream().map(index -> String.valueOf(index.getOneBased()))
                .collect(Collectors.joining(" "));

        MakeGroupCommand command = (MakeGroupCommand) parser.parseCommand(
                MakeGroupCommand.COMMAND_WORD + " " + arguments);
        assertEquals(new MakeGroupCommand(indices), command);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYER.getOneBased());
        assertEquals(new ViewCommand(INDEX_FIRST_PLAYER), command);
    }

    @Test
    public void parseCommand_viewTeam() throws Exception {
        ViewTeamCommand command = (ViewTeamCommand) parser.parseCommand(
                ViewTeamCommand.COMMAND_WORD + " " + INDEX_FIRST_TEAM.getOneBased());
        assertEquals(new ViewTeamCommand(INDEX_FIRST_TEAM), command);
    }

    @Test
    public void parseCommand_win() throws Exception {
        WinCommand command = (WinCommand) parser.parseCommand(
                WinCommand.COMMAND_WORD + " " + INDEX_FIRST_TEAM.getOneBased());
        assertEquals(new WinCommand(INDEX_FIRST_TEAM), command);
    }

    @Test
    public void parseCommand_lose() throws Exception {
        LoseCommand command = (LoseCommand) parser.parseCommand(
                LoseCommand.COMMAND_WORD + " " + INDEX_FIRST_TEAM.getOneBased());
        assertEquals(new LoseCommand(INDEX_FIRST_TEAM), command);
    }

    @Test
    public void parseCommand_deleteStats() throws Exception {
        String input = DeleteStatsCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYER.getOneBased();
        assertTrue(parser.parseCommand(input) instanceof DeleteStatsCommand);
    }

    @Test
    public void parseCommand_addStats() throws Exception {
        String input = AddStatsCommand.COMMAND_WORD + " " + INDEX_FIRST_PLAYER.getOneBased()
                + " " + "cpm/10.2 gd15/2000 kda/2.2";
        assertTrue(parser.parseCommand(input) instanceof AddStatsCommand);
    }

    @Test
    public void parseCommand_group() throws Exception {
        assertTrue(parser.parseCommand(GroupCommand.COMMAND_WORD) instanceof GroupCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
