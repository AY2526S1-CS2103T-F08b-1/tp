---
layout: default.md
title: "User Guide"
pageNav: 3
---

# SummonersBook User Guide

**Are you a League of Legends esports coach or team manager** spending hours juggling rosters, balancing skill levels, and tracking player performance across scrimmages?

SummonersBook is built specifically for you.

## What SummonersBook Does

Manage your player roster and form balanced 5v5 teams **in seconds** instead of hours:
- **Auto-group balanced teams** based on rank, role, and champion pool
- **Track player performance** with built-in stats and visualizations
- **Fast keyboard commands** optimized for coaches who type quickly

If you're comfortable with typing commands (like using Slack or Discord), SummonersBook will be 3x faster than spreadsheets or traditional apps.

---

## Quick Start

### Step 1: Install (One-Time Setup)

1. Ensure you have Java `17` or above installed on your computer.<br>
   **Mac users:** Follow [this Mac-specific guide](https://se-education.org/guides/tutorials/javaInstallationMac.html) for JDK installation.

2. Download the latest `summonersbook.jar` file from [here](https://github.com/AY2526S1-CS2103T-F08b-1/tp/releases).

3. Place the file in a folder you want to use as the home folder for SummonersBook.

4. Open a command terminal, navigate (`cd`) to the folder containing the jar file, and run:
   ```
   java -jar summonersbook.jar
   ```

5. The application window should appear in a few seconds, showing some sample data:
   ![Ui](images/Ui.png)

### Step 2: Your First Team (2-Minute Tutorial)

Let's form your first balanced team to see SummonersBook in action!

**a) Add 5 players** (copy-paste these into the command box):

```
add n/Faker rk/Grandmaster rl/Mid c/Azir
add n/Zeus rk/Diamond rl/Top c/Gnar
add n/Oner rk/Master rl/Jungle c/Lee Sin
add n/Gumayusi rk/Challenger rl/ADC c/Aphelios
add n/Keria rk/Master rl/Support c/Thresh
```

**b) Auto-create a balanced team:**

```
group
```

🎉 **Done!** You just formed a balanced team in under 30 seconds. The team will appear in the team panel on the right.

### Step 3: Learn the Essentials

Now that you've seen the magic, explore these core commands:
- `filter rl/Mid rk/Diamond` — Find specific players by role and rank
- `view 1` — Check detailed player stats and performance trends
- `help` — Open the full command reference guide

Refer to the [Features](#features) section below for complete details on all commands.

[Back to Top](#summonersbook-user-guide)

---

## Common Workflows

These workflows show you how to accomplish typical coaching tasks with SummonersBook.

### Workflow 1: Preparing for Scrimmage Night

**Your goal:** You have 15 players and need to form 3 balanced teams for practice matches.

**Steps:**

1. **Add your players** (if not already in the system):
   ```
   add n/Faker rk/Grandmaster rl/Mid c/Azir
   add n/Zeus rk/Diamond rl/Top c/Renekton
   add n/ShowMaker rk/Master rl/Mid c/Syndra
   ... (continue adding remaining players)
   ```

2. **Auto-create balanced teams:**
   ```
   group
   ```
   → SummonersBook instantly creates up to 3 teams, balanced by rank and ensuring unique roles and champions.
   → Teams appear automatically in the team panel on the right side of the window.

**Time saved:** ~45 minutes compared to manual balancing in spreadsheets.

### Workflow 2: Finding Substitutes Mid-Tournament

**Your goal:** Your support player is unavailable. Find high-rank support substitutes quickly.

**Steps:**

1. **Filter by role and rank:**
   ```
   filter rl/Support rk/Diamond rk/Master
   ```
   → Shows only Diamond and Master support players.

2. **Check detailed stats for the top candidate:**
   ```
   view 1
   ```
   → Opens performance window showing KDA, CS/min, win rate, and recent form.

**Time saved:** ~10 minutes compared to manually scrolling through your entire roster.

### Workflow 3: Post-Match Performance Tracking

**Your goal:** Record player performance after a scrimmage to track improvement over time.

**Steps:**

1. **Add performance stats for a player:**
   ```
   addStats 5 cpm/8.2 gd15/450 kda/4.5
   ```
   → Records CS per minute (8.2), gold difference at 15 min (+450), and KDA (4.5).

2. **View performance trends:**
   ```
   view 5
   ```
   → Charts show improvement or decline over the last 10 matches.

**Insight:** Spot underperforming players or rising stars instantly with visual trends.

[Back to Top](#summonersbook-user-guide)

---

## Feature Summary

Click on any command to jump to its detailed explanation.

### Player Management

Command | Purpose | Format
--------|---------|-------
[`add`](#adding-a-player-add) | Add a new player to roster | `add n/NAME rk/RANK rl/ROLE c/CHAMPION`
[`list`](#listing-all-players-list) | Show all players | `list`
[`find`](#finding-players-by-name-find) | Search players by name | `find KEYWORD [MORE_KEYWORDS...]`
[`filter`](#filtering-players-filter) | Filter by role/rank | `filter [rl/ROLE ...] [rk/RANK ...]`
[`view`](#viewing-detailed-player-information-view) | View detailed player stats | `view INDEX`
[`edit`](#editing-a-player-edit) | Update player details | `edit INDEX [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION] [t/TAG]`
[`delete`](#deleting-a-player-delete) | Remove a player | `delete INDEX`

### Performance Tracking

Command | Purpose | Format
--------|---------|-------
[`addStats`](#adding-performance-stats-addstats) | Record match performance | `addStats INDEX cpm/CPM gd15/GD15 kda/KDA`
[`deleteStats`](#removing-performance-stats-deletestats) | Remove latest stats entry | `deleteStats INDEX`

### Team Management

Command | Purpose | Format
--------|---------|-------
[`group`](#auto-grouping-players-into-teams-group) | Auto-create balanced teams | `group`
[`makeGroup`](#manually-creating-a-team-makegroup) | Manually create a team | `makeGroup INDEX_1 INDEX_2 INDEX_3 INDEX_4 INDEX_5`
[`viewTeam`](#viewing-team-details-viewteam) | View detailed team stats | `viewteam INDEX`
[`win`](#recording-a-team-win-win) | Record a win for a team | `win TEAM_INDEX`
[`lose`](#recording-a-team-loss-lose) | Record a loss for a team | `lose TEAM_INDEX`
[`ungroup`](#disbanding-teams-ungroup) | Disband team(s) | `ungroup INDEX` or `ungroup all`

### Data Import/Export

Command | Purpose | Format
--------|---------|-------
[`export`](#exporting-data-export) | Export players or teams to CSV | `export players [to/PATH]` `export teams [to/PATH]`
[`import`](#importing-player-data-import) | Import players from CSV | `import players from/PATH` `import players from/PATH`

### Utility

Command | Purpose | Format
--------|---------|-------
[`help`](#viewing-help-help) | Open help window | `help`
[`clear`](#clearing-all-data-clear) | Delete all data | `clear`
[`exit`](#exiting-the-program-exit) | Close application | `exit`

[Back to Top](#summonersbook-user-guide)

---

## Features

**Notes about the command format:**

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME rk/RANK rl/ROLE c/CHAMPION`, `NAME` , `RANK`, `ROLE`, `CHAMPION` are parameters which can be used
  as `add n/Faker rk/Grandmaster rl/ADC c/Sivir`.

* Parameters are _**case-insensitive**_ (except for `name`)

* Items in square brackets are optional.<br>
  e.g., `find [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION]` can be used as `find rk/Gold`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g., for `add n/NAME rk/RANK rl/ROLE c/CHAMPION`, `add rk/RANK n/NAME rl/ROLE c/CHAMPION` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Indices refer to the **currently displayed** list (people or teams), starting from 1.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines
  as space characters surrounding line-breaks may be omitted when copied over to the application.

---

## Player Management

### Adding a player: `add`

Adds a new player with mandatory details to your roster.

**Format:**
`add n/NAME rk/RANK rl/ROLE c/CHAMPION`

**Examples:**

* `add n/Faker rk/Grandmaster rl/ADC c/Sivir`
* `add n/Imissher rk/Gold rl/Support c/Thresh`

### Listing all players: `list`

Shows a list of all players in your SummonersBook roster.

**Format:** `list`

<box type="tip" seamless>

**Pro Tip:** Use this command to reset any filters and see your full roster again.

</box>

### Finding players by name: `find`

Searches for players by **keyword(s)** in their **name**.
If multiple keywords are given, players with at least 1 keyword in their name will be shown.

**Format:**
`find KEYWORD [MORE_KEYWORDS...]`

**How it works:**
- Matching is **case-insensitive**
- Based on **whole words only** (not partial matches)
- OR logic: returns players matching ANY keyword

**Examples:**
- `find joanne lim` — finds **Joanne Koh**, **Joanne Lim**, and **June Lim**, but **not** **John Kim**.
- `find john` — finds **John Doe** and **John Smith**.
- `find jo` — finds **Jo Lin**, but **not** "John Doe" or "John Smith" (whole word match).

### Filtering players: `filter`

Narrows the player list using one or more filters.
Matching is **case-insensitive**.

You can filter by:
- `rl/` — role (exact match)
- `rk/` — rank (exact match)

**Format (any order, at least one filter):**
`filter [rl/ROLE ...] [rk/RANK ...]`

**How filtering works:**
- Filters of **different types** (e.g. `rl/` and `rk/`) are combined with **AND**.
- Filters **within the same type** (e.g. multiple `rl/` values) are combined with **OR**.
- Matching is **case-insensitive** and **exact**

**Examples:**
- `filter rl/Mid Jungle` — players who are **Mid OR Jungle**.
- `filter rl/Mid rk/Gold` — players who are **Mid AND Gold**.
- `filter rl/Mid rl/Jungle rk/Gold rk/Silver` — players who are **(Mid OR Jungle) AND (Gold OR Silver)**.

### Viewing detailed player information: `view`

Opens a detailed window showing comprehensive information about a player, including their performance statistics visualized in graphs.

**Format:**
`view INDEX`

**Notes:**
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* The window displays:
    - Basic information (name, role, rank, champion, tags)
    - Win/loss record
    - Performance score over time
    - CS per minute trends
    - KDA trends
    - Gold difference at 15 minutes trends
* Up to the latest 10 matches are shown in the performance graphs.

**Examples:**
* `view 1`
  Opens a detailed window for the 1st player in the list.

* `view 5`
  Opens a detailed window for the 5th player in the list.

### Editing a player: `edit`

Updates an existing player's details in your roster.

**Format:**
`edit INDEX [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION] [t/TAG]`

<box type="warning" seamless>

**Important:** Players that have already been added to a team cannot be edited. Remove them from the team first using `ungroup`.

</box>

**Notes:**
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* At least one field to edit must be provided.
* Existing values are **overwritten** by the new input.
* Tags are **replaced**, not added. To clear all tags, type `t/` with no tag values.

**Examples:**
* `edit 1 n/John Doe rl/Mid rk/Diamond c/Ahri`
  Updates the 1st player's name, role, rank, and champion.

* `edit 2 t/`
  Clears all tags of the 2nd player.

* `edit 3 rl/Top rk/Gold`
  Updates the 3rd player's role and rank.

### Deleting a player: `delete`

Removes a player permanently from your roster.

**Format:**
`delete INDEX`

<box type="warning" seamless>

**Important:** You cannot delete a player who is currently on a team. Remove them from the team first using `ungroup`.

</box>

**Notes:**
* Deletes the player at the specified `INDEX`.
* The index refers to the index number shown in the displayed player list.
* The index **must be a positive integer** 1, 2, 3, …

**Example:**
`delete 3`

**Success output:**
`Deleted Player: <NAME>.`

**Failure outputs:**
- `The player index provided is invalid.`
- `Cannot delete player. <NAME> is currently on team '<TEAM NAME>'.`

[Back to Top](#summonersbook-user-guide)

---

## Performance Tracking

### Adding performance stats: `addStats`

Records a new set of performance values for a player after a match:
- Creep score per minute (CPM)
- Gold difference at 15th minute (GD15)
- Kill/Death/Assist score (KDA)

**Format:**
`addStats INDEX cpm/CPM gd15/GD15 kda/KDA`

**Notes:**
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* All fields must be provided.
* CPM and KDA can be decimals or integers e.g., `cpm/9.8`, `kda/2`
* Decimal point is a dot `.`
* GD15 must be an integer e.g., `gd15/560`
* These values will be recorded and the player's average performance score will be updated automatically.

**Example with context:**
After Faker's latest match, you recorded:
- 8.8 CS per minute (excellent farming!)
- +2000 gold lead at 15 min (dominant lane phase)
- 6.0 KDA (great performance)

```
addStats 1 cpm/8.8 gd15/2000 kda/6.0
```

→ Faker's overall performance score updates automatically.
→ View trends with `view 1`

### Removing performance stats: `deleteStats`

Deletes the most recent performance record for a player (useful for correcting mistakes).

**Format:**
`deleteStats INDEX`

**Notes:**
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* The most recent set of performance values (cpm, gd15, kda) will be deleted.
* The player's average score will be recalculated automatically.

**Example:**
```
deleteStats 1
```
Removes the latest performance entry for the 1st player in the list.

[Back to Top](#summonersbook-user-guide)

---

## Team Management

### Auto-grouping players into teams: `group`

Automatically forms as many balanced teams of five as possible from **unassigned** players using an intelligent matching algorithm.

**Format:**
`group`

**How it works:**
The algorithm follows these steps:
1. Groups all unassigned players by their roles (Top, Jungle, Mid, ADC, Support).
2. Sorts each role group by rank (highest to lowest) to prioritize balanced skill distribution.
3. Iteratively forms teams by selecting one player from each role.
4. Ensures no duplicate champions within each team to avoid conflicts.
5. Continues creating teams until there are insufficient players to form a complete team.

<box type="warning" seamless>

**Common Mistake:** Make sure you have at least 1 player per role (Top, Jungle, Mid, ADC, Support). The algorithm can't form teams with missing roles.

</box>

**Notes:**
* At least one unassigned player for each of the five roles is required to form a team.
* Only players **not already in a team** are considered.
* If champion conflicts prevent forming a team, the algorithm stops and reports how many teams were created.
* Any remaining unassigned players stay in the pool and can be grouped later.

**Examples:**
* `group`
  Forms balanced teams from all unassigned players.

### Manually creating a team: `makeGroup`

Creates a new team with the specified players by their index numbers.

**Format:**
`makeGroup INDEX_1 INDEX_2 INDEX_3 INDEX_4 INDEX_5`

<box type="info" seamless>

**Rules & Notes:**
- Each index must refer to a player in the **currently displayed** player list.
- Players **cannot already belong** to another team.
- All five players must have **unique roles** (no duplicates).
- All five players must have **unique champions** (no duplicates).
- Exactly 5 index numbers must be provided.
- Index numbers must not be duplicated.
- If any index is invalid or player is unavailable, the command will fail with a clear error message.

</box>

**Examples:**
- `makeGroup 1 2 3 4 5`
  Creates a new team with the 1st, 2nd, 3rd, 4th, and 5th players from the currently displayed list.

- `makeGroup 10 11 12 13 14`
  Creates a team with players at indices 10-14.

---

### Viewing team details : `viewTeam`

Opens a detailed window showing comprehensive information about a team, including all members and team statistics.

**Format:**
`viewTeam INDEX`

#### Notes
* `INDEX` refers to the number shown in the displayed team list. Must be a positive integer (1, 2, 3…).
* The window displays team member details and win/loss record.

#### Examples
* `viewTeam 1`
  Opens a detailed window for the 1st team in the list.

---

### 🏆 Recording a team win : `win`

Records a win for a team, updating both the team's record and all team members' individual records.

**Format:**
`win TEAM_INDEX`

#### Notes
* `TEAM_INDEX` refers to the number shown in the displayed team list. Must be a positive integer (1, 2, 3…).
* Both the team and all its members will have their win count incremented.

#### Examples
* `win 1`
  Records a win for the 1st team in the list.

---

### 💔 Recording a team loss : `lose`

Records a loss for a team, updating both the team's record and all team members' individual records.

**Format:**
`lose TEAM_INDEX`

#### Notes
* `TEAM_INDEX` refers to the number shown in the displayed team list. Must be a positive integer (1, 2, 3…).
* Both the team and all its members will have their loss count incremented.

#### Examples
* `lose 2`
  Records a loss for the 2nd team in the list.

### Disbanding teams: `ungroup`

Removes one or more teams from the system, returning their members to the unassigned pool.

**Format:**
`ungroup INDEX` or `ungroup all`

**Notes:**
* `INDEX` refers to the team number shown in the displayed team list. Must be a positive integer (1, 2, 3…).
* Use `all` (case-insensitive) to disband all teams at once.
* After ungrouping, all team members become available for forming new teams.
* If there are no teams, the command will show an error.

**Examples:**
* `ungroup 1`
  Disbands the 1st team in the displayed team list.

* `ungroup all`
  Disbands all teams, making all players unassigned.

[Back to Top](#summonersbook-user-guide)

---

## Data Import/Export

### Exporting data: `export`

Exports player or team data from SummonersBook into a CSV file. You can export either players or teams, optionally specifying a custom file path.

**Format:**
```
export players [to/CUSTOM_PATH]
export teams [to/CUSTOM_PATH]
```

**Notes:**
* If no path is provided, exports are saved to:
    - `data/players.csv` for player data
    - `data/teams.csv` for team data
* Each CSV file contains structured data for easy re-import or analysis (e.g., Excel, Google Sheets).
* Custom paths must end with `.csv`.

**Examples:**
* `export players`
  Exports all player data to `data/players.csv`.

* `export teams to/data/myTeams.csv`
  Exports all team data to a custom location.

**Success message:**
```
Exported players to data/players.csv
```

### Importing player data: `import`

Imports player information from a CSV file into SummonersBook. This is useful for restoring saved data or onboarding new players quickly.

**Format:**
`import players from/FILE_PATH`

**Notes:**
* The CSV file must be properly formatted. Supported headers include:
    - `Name,Role,Rank,Champion`
    - or `Name,Role,Rank,Champion,Wins,Losses`
* Duplicate player entries (by name and role) will be ignored automatically.
* The file path must point to a valid `.csv` file (e.g., `data/players.csv`).

**Examples:**
* `import players from/data/players.csv`
  Imports players from the default export location.

* `import players from/data/backup.csv`
  Imports players from a backup file.

**Success message:**
```
Imported 10 players, skipped 0 duplicates, 0 invalid row(s).
```

[Back to Top](#summonersbook-user-guide)

---

## Utility Commands

### Viewing help: `help`

Opens a help window displaying the full User Guide.

**Format:** `help`

### Clearing all data: `clear`

Deletes all players and teams from SummonersBook.

**Format:** `clear`

<box type="warning" seamless>

**Warning:** This action cannot be undone! Make sure to back up your data file before clearing.

</box>

### Exiting the program: `exit`

Closes the application.

**Format:** `exit`

### Saving the data

Data is saved automatically to disk after any command that changes data. No manual save is required.

### Editing the data file

SummonersBook data is saved automatically as a JSON file located at `[JAR file location]/data/addressbook.json`.
Advanced users can edit this file directly if needed.

**Caution:**
Editing the data file incorrectly can **corrupt your data**, causing SummonersBook to start with an empty file.
Always **back up the file** before making changes, and only edit it if you are confident about the updates.

[Back to Top](#summonersbook-user-guide)

---

## Troubleshooting

### "Cannot delete player - currently on team"

**Problem:** You tried to delete a player who is assigned to a team.

**Solution:** Remove them from the team first:
1. `ungroup 1` (disband their team)
2. `delete 5` (now you can delete the player)

### "No teams could be formed"

**Problem:** Not enough players or missing a required role when running `group`.

**What to check:**
- Do you have at least 5 unassigned players?
- Do you have at least 1 player per role (Top, Jungle, Mid, ADC, Support)?
- Run `list` to see who's available (unassigned players show in the player list).

**Quick fix:** Add missing role players with `add n/... rl/Support ...`

### "Invalid index" or "The player index provided is invalid"

**Problem:** You referenced a player number that doesn't exist in the current displayed list.

**Solution:**
1. Always run `list` first to see current player numbers.
2. Remember: Numbers change after filtering or deleting!
3. The index must refer to the **displayed list**, not the total roster.

**Example:**
```
filter rl/Support    # Now you're seeing only 3 players
view 5               # ERROR - only 3 players are displayed
view 2               # OK - refers to 2nd player in filtered list
```

### "Duplicate champion detected" when creating teams

**Problem:** Multiple players in the same team play the same champion.

**Solution:**
- SummonersBook requires unique champions within each team
- Edit one player's champion: `edit 3 c/Yasuo`
- Or use `group` (auto-grouping avoids champion conflicts automatically)

[Back to Top](#summonersbook-user-guide)

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file from your previous SummonersBook home folder (located at `[JAR file location]/data/addressbook.json`).

**Q:** Why can't I delete a player who's on a team?<br>
**A:** Team rosters must always have 5 players. Remove the player from their team (via `ungroup` and re-group) before deleting.

**Q:** How are teams named when using `group`?<br>
**A:** Sequentially (`Team A`, `Team B`, …), skipping any existing names to avoid conflicts.

**Q:** Can I undo a command?<br>
**A:** SummonersBook does not currently support undo. However, data is auto-saved after each command, so you can manually revert by editing the data file (advanced users only) or re-entering the data.

**Q:** What's the difference between `find` and `filter`?<br>
**A:** `find` searches by player **name** (keywords), while `filter` narrows by **attributes** (role, rank). Use `find` for "Who was that player called John?" and `filter` for "Show me all Diamond supports".

[Back to Top](#summonersbook-user-guide)

---

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut
   `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to
   manually restore the minimized Help Window.

[Back to Top](#summonersbook-user-guide)

---

## Command Summary

### Player Management
Action | Format | Example
-------|--------|--------
**Add player** | `add n/NAME rk/RANK rl/ROLE c/CHAMPION` | `add n/Faker rk/Grandmaster rl/Mid c/Azir`
**List all players** | `list` | `list`
**Find by name** | `find KEYWORD [MORE_KEYWORDS...]` | `find john`
**Filter players** | `filter [rl/ROLE ...] [rk/RANK ...]` | `filter rl/Mid rk/Diamond`
**View player details** | `view INDEX` | `view 1`
**Edit player** | `edit INDEX [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION] [t/TAG]` | `edit 1 rl/Top rk/Diamond`
**Delete player** | `delete INDEX` | `delete 3`

### Performance Tracking
Action | Format | Example
-------|--------|--------
**Add stats** | `addStats INDEX cpm/CPM gd15/GD15 kda/KDA` | `addStats 1 cpm/8.8 gd15/450 kda/4.5`
**Delete stats** | `deleteStats INDEX` | `deleteStats 1`

### Team Management
Action | Format | Example
-------|--------|--------
**Auto-group teams** | `group` | `group`
**Manually create team** | `makeGroup INDEX_1 INDEX_2 INDEX_3 INDEX_4 INDEX_5` | `makeGroup 1 2 3 4 5`
**View team details** | `viewTeam INDEX` | `viewTeam 1`
**Record win** | `win TEAM_INDEX` | `win 2`
**Record loss** | `lose TEAM_INDEX` | `lose 2`
**Disband team(s)** | `ungroup INDEX` or `ungroup all` | `ungroup 1` or `ungroup all`

### Data Import/Export
Action | Format                     | Example
-------|----------------------------|--------
**Export players** | `export players [to/PATH]` | `export players`
**Export teams** | `export teams [to/PATH]`   | `export teams to/data/myTeams.csv`
**Import players** | `import players from/PATH` | `import players from/data/players.csv`

### Utility Commands
Action | Format | Example
-------|--------|--------
**Help** | `help` | `help`
**Clear all** | `clear` | `clear`
**Exit** | `exit` | `exit`

[Back to Top](#summonersbook-user-guide)
