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

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

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

---

### Step 2: Your First Team (2-Minute Tutorial)

Let's form your first balanced team to see SummonersBook in action!

**a) Add 5 players** (copy-paste these into the command box):

```
add n/Faker rk/Grandmaster rl/Mid c/Azir
add n/Zeus rk/Diamond rl/Top c/Gnar
add n/Oner rk/Master rl/Jungle c/LeeSin
add n/Gumayusi rk/Challenger rl/ADC c/Aphelios
add n/Keria rk/Master rl/Support c/Thresh
```

**b) Auto-create a balanced team:**

```
group
```

**c) View your new team:**

```
listteam
```

🎉 **Done!** You just formed a balanced team in under 30 seconds.

---

### Step 3: Learn the Essentials

Now that you've seen the magic, explore these core commands:
- `filter rl/Mid rk/Diamond` — Find specific players by role and rank
- `view 1` — Check detailed player stats and performance trends
- `help` — Open the full command reference guide

Refer to the [Features](#features) section below for complete details on all commands.

--------------------------------------------------------------------------------------------------------------------

## Common Workflows

These workflows show you how to accomplish typical coaching tasks with SummonersBook.

### 🎯 Workflow 1: Preparing for Scrimmage Night

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

3. **View all team rosters:**
   ```
   listteam
   ```

**Time saved:** ~45 minutes compared to manual balancing in spreadsheets.

---

### 🎯 Workflow 2: Finding Substitutes Mid-Tournament

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

---

### 🎯 Workflow 3: Post-Match Performance Tracking

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

--------------------------------------------------------------------------------------------------------------------

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

## Core Daily Commands

These are the commands you'll use most often as a coach.

### 🎯 Auto-grouping players into teams: `group`

Automatically forms as many balanced teams of five as possible from **unassigned** players using an intelligent matching algorithm.

**Format:**
`group`

#### How it works
The algorithm follows these steps:
1. Groups all unassigned players by their roles (Top, Jungle, Mid, ADC, Support).
2. Sorts each role group by rank (highest to lowest) to prioritize balanced skill distribution.
3. Iteratively forms teams by selecting one player from each role.
4. Ensures no duplicate champions within each team to avoid conflicts.
5. Continues creating teams until there are insufficient players to form a complete team.

<box type="tip" seamless>

**💡 Pro Tip:** Run `listteam` after grouping to see your new teams instantly. Most coaches keep the team list visible during scrims.

</box>

<box type="warning" seamless>

**⚠️ Common Mistake:** Make sure you have at least 1 player per role (Top, Jungle, Mid, ADC, Support). The algorithm can't form teams with missing roles.

</box>

#### Notes
* At least one unassigned player for each of the five roles is required to form a team.
* Only players **not already in a team** are considered.
* If champion conflicts prevent forming a team, the algorithm stops and reports how many teams were created.
* Any remaining unassigned players stay in the pool and can be grouped later.

#### Examples
* `group`
  Forms balanced teams from all unassigned players.

---

### 👀 Viewing detailed player information : `view`

Opens a detailed window showing comprehensive information about a player, including their performance statistics visualized in graphs.

**Format:**
`view INDEX`

#### Notes
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* The window displays:
  - Basic information (name, role, rank, champion, tags)
  - Win/loss record
  - Performance score over time
  - CS per minute trends
  - KDA trends
  - Gold difference at 15 minutes trends
* Up to the latest 10 matches are shown in the performance graphs.

#### Examples
* `view 1`
  Opens a detailed window for the 1st player in the list.

* `view 5`
  Opens a detailed window for the 5th player in the list.

---

### 🔍 Filtering players : `filter`

Narrows the player list using one or more filters.
Matching is **case-insensitive**.

You can filter by:
- `rl/` — role (exact match)
- `rk/` — rank (exact match)

**Format (any order, at least one filter):**
`filter [rl/ROLE ...] [rk/RANK ...]`

#### How filtering works
- Filters of **different types** (e.g. `rl/` and `rk/`) are combined with **AND**.
- Filters **within the same type** (e.g. multiple `rl/` values) are combined with **OR**.
- Matching is **case-insensitive** and **exact**

#### Examples
- `filter rl/Mid Jungle` — players who are **Mid OR Jungle**.
- `filter rl/Mid rk/Gold` — players who are **Mid AND Gold**.
- `filter rl/Mid rl/Jungle rk/Gold rk/Silver` — players who are **(Mid OR Jungle) AND (Gold OR Silver)**.

---

### ➕ Adding a player: `add`

Adds a new player with mandatory details to your roster.

**Format:**
`add n/NAME rk/RANK rl/ROLE c/CHAMPION`

**Examples:**

* `add n/Faker rk/Grandmaster rl/ADC c/Sivir`
* `add n/Imissher rk/Gold rl/Support c/Thresh`

---

## Team Management

### 📋 Listing all teams: `listteam`

Shows a list of all teams in SummonersBook.

**Format:** `listteam`

---

### 🛠️ Manually creating a team: `makeGroup`

Creates a new team with the specified players.

**Format:**
`makeGroup n/PLAYER_1 n/PLAYER_2 n/PLAYER_3 n/PLAYER_4 n/PLAYER_5`

<box type="info" seamless>

**Rules & Notes:**
- Each player must **already exist** in the player list.
- Players **cannot already belong** to another team.
- All five players must have **unique roles** (no duplicates).
- Name field is **case-sensitive!**
- If any player is invalid or unavailable, the command will fail with a clear error message.

</box>

**Examples:**
- `makeGroup n/Faker n/Oner n/Zeus n/Gumayusi n/Keria`
  Creates a new team with those five members.

---

### 💥 Disbanding teams : `ungroup`

Removes one or more teams from the system, returning their members to the unassigned pool.

**Format:**
`ungroup INDEX` or `ungroup all`

#### Notes
* `INDEX` refers to the team number shown in the displayed team list. Must be a positive integer (1, 2, 3…).
* Use `all` (case-insensitive) to disband all teams at once.
* After ungrouping, all team members become available for forming new teams.
* If there are no teams, the command will show an error.

#### Examples
* `ungroup 1`
  Disbands the 1st team in the displayed team list.

* `ungroup all`
  Disbands all teams, making all players unassigned.

---

## Player Management

### 📝 Editing a player : `edit`

Updates an existing player's details in your roster.

**Format:**
`edit INDEX [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION] [t/TAG]`

<box type="warning" seamless>

**⚠️ Important:** Players that have already been added to a team cannot be edited. Remove them from the team first using `ungroup`.

</box>

#### Notes
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* At least one field to edit must be provided.
* Existing values are **overwritten** by the new input.
* Tags are **replaced**, not added. To clear all tags, type `t/` with no tag values.

#### Examples
* `edit 1 n/John Doe rl/Mid rk/Diamond c/Ahri`
  Updates the 1st player's name, role, rank, and champion.

* `edit 2 t/`
  Clears all tags of the 2nd player.

* `edit 3 rl/Top rk/Gold`
  Updates the 3rd player's role and rank.

---

### 🗑️ Deleting a player: `delete`

Removes a player permanently from your roster.

**Format:**
`delete INDEX`

<box type="warning" seamless>

**⚠️ Important:** You cannot delete a player who is currently on a team. Remove them from the team first using `ungroup`.

</box>

#### Notes
* Deletes the player at the specified `INDEX`.
* The index refers to the index number shown in the displayed player list.
* The index **must be a positive integer** 1, 2, 3, …

#### Example:
`delete 3`

**Success output:**
`Deleted Player: <NAME>.`

**Failure outputs:**
- `The player index provided is invalid.`
- `Cannot delete player. <NAME> is currently on team '<TEAM NAME>'.`

---

### 📊 Adding performance stats : `addStats`

Records a new set of performance values for a player after a match:
- Creep score per minute (CPM)
- Gold difference at 15th minute (GD15)
- Kill/Death/Assist score (KDA)

**Format:**
`addStats INDEX cpm/CPM gd15/GD15 kda/KDA`

#### Notes
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* All fields must be provided.
* CPM and KDA can be decimals or integers e.g., `cpm/9.8`, `kda/2`
* Decimal point is a dot `.`
* GD15 must be an integer e.g., `gd15/560`
* These values will be recorded and the player's average performance score will be updated automatically.

#### Example with context:
After Faker's latest match, you recorded:
- 8.8 CS per minute (excellent farming!)
- +2000 gold lead at 15 min (dominant lane phase)
- 6.0 KDA (great performance)

```
addStats 1 cpm/8.8 gd15/2000 kda/6.0
```

→ Faker's overall performance score updates automatically.
→ View trends with `view 1`

---

### ❌ Removing performance stats : `deleteStats`

Deletes the most recent performance record for a player (useful for correcting mistakes).

**Format:**
`deleteStats INDEX`

#### Notes
* `INDEX` refers to the number shown in the current displayed player list. Must be a positive integer (1, 2, 3…).
* The most recent set of performance values (cpm, gd15, kda) will be deleted.
* The player's average score will be recalculated automatically.

#### Example:
```
deleteStats 1
```
Removes the latest performance entry for the 1st player in the list.

---

## Utility Commands

### 📋 Listing all players : `list`

Shows a list of all players in your SummonersBook roster.

**Format:** `list`

<box type="tip" seamless>

**💡 Pro Tip:** Use this command to reset any filters and see your full roster again.

</box>

---

### 🔎 Finding players by name : `find`

Searches for players by **keyword(s)** in their **name**.
If multiple keywords are given, players with at least 1 keyword in their name will be shown.

**Format:**
`find KEYWORD [MORE_KEYWORDS...]`

#### How it works
- Matching is **case-insensitive**
- Based on **whole words only** (not partial matches)
- OR logic: returns players matching ANY keyword

#### Examples
- `find joanne lim` — finds **Joanne Koh**, **Joanne Lim**, and **June Lim**, but **not** **John Kim**.
- `find john` — finds **John Doe** and **John Smith**.
- `find jo` — finds **Jo Lin**, but **not** "John Doe" or "John Smith" (whole word match).

---

### 📖 Viewing help : `help`

Opens a help window displaying the full User Guide.

**Format:** `help`

---

### 🗑️ Clearing all data : `clear`

Deletes all players and teams from SummonersBook.

**Format:** `clear`

<box type="warning" seamless>

**⚠️ Warning:** This action cannot be undone! Make sure to back up your data file before clearing.

</box>

---

### 🚪 Exiting the program : `exit`

Closes the application.

**Format:** `exit`

### Saving the data

Data is saved automatically to disk after any command that changes data. No manual save is required.

### Editing the data file

SummonersBook data is saved automatically as a JSON file located at `[JAR file location]/data/summonersbook.json`.  
Advanced users can edit this file directly if needed.

**Caution:**  
Editing the data file incorrectly can **corrupt your data**, causing SummonersBook to start with an empty file.  
Always **back up the file** before making changes, and only edit it if you are confident about the updates.

--------------------------------------------------------------------------------------------------------------------

## Troubleshooting

### "Cannot delete player - currently on team"

**Problem:** You tried to delete a player who is assigned to a team.

**Solution:** Remove them from the team first:
1. `ungroup 1` (disband their team)
2. `delete 5` (now you can delete the player)

---

### "No teams could be formed"

**Problem:** Not enough players or missing a required role when running `group`.

**What to check:**
- Do you have at least 5 unassigned players?
- Do you have at least 1 player per role (Top, Jungle, Mid, ADC, Support)?
- Run `list` to see who's available (unassigned players show in the player list).

**Quick fix:** Add missing role players with `add n/... rl/Support ...`

---

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

---

### "Duplicate champion detected" when creating teams

**Problem:** Multiple players in the same team play the same champion.

**Solution:**
- SummonersBook requires unique champions within each team
- Edit one player's champion: `edit 3 c/Yasuo`
- Or use `group` (auto-grouping avoids champion conflicts automatically)

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file from your previous SummonersBook home folder (located at `[JAR file location]/data/summonersbook.json`).

**Q:** Why can't I delete a player who's on a team?<br>
**A:** Team rosters must always have 5 players. Remove the player from their team (via `ungroup` and re-group) before deleting.

**Q:** How are teams named when using `group`?<br>
**A:** Sequentially (`Team A`, `Team B`, …), skipping any existing names to avoid conflicts.

**Q:** Can I undo a command?<br>
**A:** SummonersBook does not currently support undo. However, data is auto-saved after each command, so you can manually revert by editing the data file (advanced users only) or re-entering the data.

**Q:** What's the difference between `find` and `filter`?<br>
**A:** `find` searches by player **name** (keywords), while `filter` narrows by **attributes** (role, rank). Use `find` for "Who was that player called John?" and `filter` for "Show me all Diamond supports".

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only
   the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the
   application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut
   `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to
   manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

### Core Daily Commands
Action | Format | Example
-------|--------|--------
**Auto-group teams** | `group` | `group`
**View player details** | `view INDEX` | `view 1`
**Filter players** | `filter [rl/ROLE ...] [rk/RANK ...]` | `filter rl/Mid rk/Diamond`
**Add player** | `add n/NAME rk/RANK rl/ROLE c/CHAMPION` | `add n/Faker rk/Grandmaster rl/Mid c/Azir`

### Team Management
Action | Format | Example
-------|--------|--------
**List all teams** | `listteam` | `listteam`
**Manually create team** | `makeGroup n/P1 n/P2 n/P3 n/P4 n/P5` | `makeGroup n/Faker n/Oner n/Zeus n/Gumayusi n/Keria`
**Disband team(s)** | `ungroup INDEX` or `ungroup all` | `ungroup 1` or `ungroup all`

### Player Management
Action | Format | Example
-------|--------|--------
**Edit player** | `edit INDEX [n/NAME] [rl/ROLE] [rk/RANK] [c/CHAMPION] [t/TAG]` | `edit 1 rl/Top rk/Diamond`
**Delete player** | `delete INDEX` | `delete 3`
**Add stats** | `addStats INDEX cpm/CPM gd15/GD15 kda/KDA` | `addStats 1 cpm/8.8 gd15/450 kda/4.5`
**Delete stats** | `deleteStats INDEX` | `deleteStats 1`

### Utility Commands
Action | Format | Example
-------|--------|--------
**List all players** | `list` | `list`
**Find by name** | `find KEYWORD [MORE_KEYWORDS...]` | `find john`
**Help** | `help` | `help`
**Clear all** | `clear` | `clear`
**Exit** | `exit` | `exit`
