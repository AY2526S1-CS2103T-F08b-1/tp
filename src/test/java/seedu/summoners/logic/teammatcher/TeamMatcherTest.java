package seedu.summoners.logic.teammatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.summoners.model.player.Player;
import seedu.summoners.model.team.Team;
import seedu.summoners.testutil.PlayerBuilder;

public class TeamMatcherTest {

    private final TeamMatcher teamMatcher = new TeamMatcher();

    @Test
    public void matchTeams_validPlayers_formsOneTeam() throws Exception {
        // Create 5 players with different roles
        Player top = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player adc = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player support = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Player> players = Arrays.asList(top, jungle, mid, adc, support);
        List<Team> teams = teamMatcher.matchTeams(players);

        assertEquals(1, teams.size());
        assertEquals(5, teams.get(0).getPlayers().size());
    }

    @Test
    public void matchTeams_tenPlayers_formsTwoTeams() throws Exception {
        // Create 10 players - 2 per role
        Player top1 = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Challenger").withChampion("Garen").build();
        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Gold").withChampion("Darius").build();

        Player jungle1 = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Master").withChampion("Lee Sin").build();
        Player jungle2 = new PlayerBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Player mid1 = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Diamond").withChampion("Ahri").build();
        Player mid2 = new PlayerBuilder().withName("Mid2").withRole("mid")
                .withRank("Bronze").withChampion("Zed").build();

        Player adc1 = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Platinum").withChampion("Jinx").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Iron").withChampion("Ashe").build();

        Player support1 = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Emerald").withChampion("Leona").build();
        Player support2 = new PlayerBuilder().withName("Support2").withRole("support")
                .withRank("Gold").withChampion("Thresh").build();

        List<Player> players = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(players);

        assertEquals(2, teams.size());

        // First team should have higher ranked players
        Team team1 = teams.get(0);
        assertTrue(team1.getPlayers().contains(top1)); // Challenger
        assertTrue(team1.getPlayers().contains(jungle1)); // Master
        assertTrue(team1.getPlayers().contains(mid1)); // Diamond
    }

    @Test
    public void matchTeams_duplicateChampion_avoidsConflict() throws Exception {
        // Create scenario where top2 has same champion as top1
        // TeamMatcher should pick top1 for first team and top2 for second team
        Player top1 = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Garen").build();

        Player jungle1 = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player jungle2 = new PlayerBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Player mid1 = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player mid2 = new PlayerBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Player adc1 = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Player support1 = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Player support2 = new PlayerBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Player> players = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(players);

        // Should form 2 teams successfully
        assertEquals(2, teams.size());

        // Verify no duplicate champions in each team
        for (Team team : teams) {
            List<Player> members = team.getPlayers();
            long uniqueChampions = members.stream()
                    .map(Player::getChampion)
                    .distinct()
                    .count();
            assertEquals(5, uniqueChampions);
        }
    }

    @Test
    public void matchTeams_missingRole_throwsInsufficientPlayersException() {
        // Create players but missing support role
        Player top = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player adc = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();

        List<Player> players = Arrays.asList(top, jungle, mid, adc);

        InsufficientPlayersException exception = assertThrows(
                InsufficientPlayersException.class, () -> teamMatcher.matchTeams(players));

        assertTrue(exception.getMessage().contains("Support"));
    }

    @Test
    public void matchTeams_emptyList_throwsInsufficientPlayersException() {
        List<Player> players = Arrays.asList();

        assertThrows(
                InsufficientPlayersException.class, () -> teamMatcher.matchTeams(players));
    }

    @Test
    public void matchTeams_allPlayersSameRole_throwsInsufficientPlayersException() {
        // All 5 players are top laners - impossible to form a team
        Player top1 = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Gold").withChampion("Darius").build();
        Player top3 = new PlayerBuilder().withName("Top3").withRole("top")
                .withRank("Gold").withChampion("Sett").build();
        Player top4 = new PlayerBuilder().withName("Top4").withRole("top")
                .withRank("Gold").withChampion("Gwen").build();
        Player top5 = new PlayerBuilder().withName("Top5").withRole("top")
                .withRank("Gold").withChampion("Jax").build();

        List<Player> players = Arrays.asList(top1, top2, top3, top4, top5);

        InsufficientPlayersException exception = assertThrows(
                InsufficientPlayersException.class, () -> teamMatcher.matchTeams(players));

        // Should complain about missing Jungle (or any other missing role)
        assertTrue(exception.getMessage().contains("No players available for role"));
    }

    @Test
    public void matchTeams_sevenPlayers_formsOneTeamLeavesTwo() throws Exception {
        // 7 players total - should form 1 complete team, leaving 2 unmatched
        Player top1 = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Challenger").withChampion("Garen").build();
        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Gold").withChampion("Darius").build();

        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();

        Player adc1 = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Player support = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Player> players = Arrays.asList(top1, top2, jungle, mid, adc1, adc2, support);
        List<Team> teams = teamMatcher.matchTeams(players);

        // Should form exactly 1 team
        assertEquals(1, teams.size());

        // Team should contain highest-ranked top (top1 Challenger, not top2 Gold)
        Team team = teams.get(0);
        assertTrue(team.getPlayers().contains(top1));
        // Team should contain highest-ranked adc (adc1 Gold, not adc2 Silver)
        assertTrue(team.getPlayers().contains(adc1));
    }

    @Test
    public void matchTeams_fifteenPlayers_formsThreeTeams() throws Exception {
        // 15 players (3 per role) - should form 3 complete teams
        List<Player> players = new ArrayList<>();

        // Create 3 players per role with different ranks
        String[] roles = {"top", "jungle", "mid", "adc", "support"};
        String[] champions = {
            "Garen,Darius,Sett",
            "Lee Sin,Jarvan IV,Elise",
            "Ahri,Zed,Yasuo",
            "Jinx,Ashe,Caitlyn",
            "Leona,Thresh,Lulu"
        };
        String[] ranks = {"Challenger", "Gold", "Bronze"};

        for (int roleIndex = 0; roleIndex < roles.length; roleIndex++) {
            String[] champs = champions[roleIndex].split(",");
            for (int i = 0; i < 3; i++) {
                Player player = new PlayerBuilder()
                        .withName(roles[roleIndex] + (i + 1))
                        .withRole(roles[roleIndex])
                        .withRank(ranks[i])
                        .withChampion(champs[i])
                        .build();
                players.add(player);
            }
        }

        List<Team> teams = teamMatcher.matchTeams(players);

        // Should form exactly 3 teams
        assertEquals(3, teams.size());
    }

    @Test
    public void matchTeams_championConflictPreventsSecondTeam_formsOneTeamOnly() throws Exception {
        // Create 10 players where all players in each role use the same champion
        // This should only allow forming 1 team
        Player top1 = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player top2 = new PlayerBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Garen").build(); // Same champion

        Player jungle1 = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player jungle2 = new PlayerBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Garen").build(); // Conflict with top2!

        Player mid1 = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player mid2 = new PlayerBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Player adc1 = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player adc2 = new PlayerBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Player support1 = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Player support2 = new PlayerBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Player> players = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(players);

        // Should only form 1 team due to champion conflicts
        assertEquals(1, teams.size());
    }

    @Test
    public void matchTeams_exactlyFivePlayers_formsOneTeam() throws Exception {
        // Test covers the case where we have exactly 5 players (one per role)
        Player top = new PlayerBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player adc = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player support = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Player> players = Arrays.asList(top, jungle, mid, adc, support);
        List<Team> teams = teamMatcher.matchTeams(players);

        // Should form exactly 1 team
        assertEquals(1, teams.size());
        assertEquals(5, teams.get(0).getPlayers().size());
    }

    @Test
    public void matchTeams_roleKeyMissingInMap_throwsInsufficientPlayersException() {
        // Create players with only 4 out of 5 required roles (no Top role at all)
        Player jungle = new PlayerBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Player mid = new PlayerBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Player adc = new PlayerBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Player support = new PlayerBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Player> players = Arrays.asList(jungle, mid, adc, support);

        InsufficientPlayersException exception = assertThrows(
                InsufficientPlayersException.class, () -> teamMatcher.matchTeams(players));

        // Should complain about missing Top role
        assertTrue(exception.getMessage().contains("Top"));
    }
}
