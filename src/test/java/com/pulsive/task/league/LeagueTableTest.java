package com.pulsive.task.league;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class LeagueTableTest {

    @Test
    public void getTableEntries() {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("Manchester City", "Real Madrid", 3, 2));
        matches.add(new Match("Real Madrid", "Manchester City", 2, 2));

        LeagueTable leagueTable = new LeagueTable(matches);
        List<LeagueTableEntry> entries = leagueTable.getTableEntries();
        LeagueTableEntry manchester = new LeagueTableEntry("Manchester City", 2, 1, 1, 0, 5, 4, 1, 4);
        //sort by points
        Assertions.assertEquals(entries.get(0), manchester);
        matches.add(new Match("Inter Milan", "Arsenal", 2, 0));
        matches.add(new Match("Arsenal", "Inter Milan", 1, 1));
        leagueTable = new LeagueTable(matches);
        entries = leagueTable.getTableEntries();
        //sort by goals diff
        LeagueTableEntry inter = new LeagueTableEntry("Inter Milan", 2, 1, 1, 0, 3, 1, 2, 4);
        Assertions.assertEquals(entries.get(0), inter);
        Assertions.assertEquals(entries.get(1), manchester);
        matches.add(new Match("Real Madrid", "Napoli", 5, 3));
        matches.add(new Match("Napoli", "Real Madrid", 0, 0));
        leagueTable = new LeagueTable(matches);
        entries = leagueTable.getTableEntries();
        LeagueTableEntry napoli = new LeagueTableEntry("Napoli", 2, 0, 1, 1, 3, 5, -2, 1);
        LeagueTableEntry arsenal = new LeagueTableEntry("Arsenal", 2, 0, 1, 1, 1, 3, -2, 1);
        //sort by score
        Assertions.assertEquals(entries.get(entries.size() - 1), arsenal);
        Assertions.assertEquals(entries.get(entries.size() - 2), napoli);
        //the same as Inter Milan vs Arsenal
        matches.add(new Match("Barcelona", "Liverpool", 2, 0));
        matches.add(new Match("Liverpool", "Barcelona", 1, 1));
        LeagueTableEntry liverpool = new LeagueTableEntry("Liverpool", 2, 0, 1, 1, 1, 3, -2, 1);
        leagueTable = new LeagueTable(matches);
        entries = leagueTable.getTableEntries();
        //sort by name
        Assertions.assertEquals(entries.get(entries.size() - 1), liverpool);
        Assertions.assertEquals(entries.get(entries.size() - 2), arsenal);
    }

    //Sort rules:
    // Sort by total points (descending)
    // Then by goal difference (descending)
    // Then by goals scored (descending)
    // Then by team name (in alphabetical order)
    @Test
    public void getTableEntries_checkSorting() {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("Real Madrid", "Napoli", 5, 3));
        Assertions.assertEquals(getTeamNamesSort(matches), List.of("Real Madrid", "Napoli"));
        //Test by goal difference. Barcelona and Madrid has same points, but different goal difference
        matches.add(new Match("Barcelona", "Napoli", 6, 3));
        Assertions.assertEquals(getTeamNamesSort(matches), List.of("Barcelona", "Real Madrid", "Napoli"));
        //Test by goal scored. Totenham and Barcelona has the same points and goal difference,
        //but Totenham has more goals
        matches.add(new Match("Totenham", "Napoli", 8, 5));
        Assertions.assertEquals(getTeamNamesSort(matches), List.of("Totenham", "Barcelona", "Real Madrid", "Napoli"));
        //Test alphabetic sort.PSG and Real Madrid has the same total points, goal difference and goals scored however
        //P is predecessor of R in alphabet
        matches.add(new Match("PSG", "Napoli", 5, 3));
        Assertions.assertEquals(getTeamNamesSort(matches), List.of("Totenham", "Barcelona", "PSG", "Real Madrid", "Napoli"));
    }

    @Test
    public void getTableEntries_checkNull() {
        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () -> new LeagueTable(null),
                        "Expected assertion exception");
        Assertions.assertEquals("Matches can't be null", thrown.getMessage());
    }

    @Test
    public void getTableEntries_checkEmpty() {
        Assertions.assertEquals(new LeagueTable(List.of()).getTableEntries(), List.of());
    }

    private List<String> getTeamNamesSort(List<Match> matches) {
        LeagueTable leagueTable = new LeagueTable(matches);
        List<LeagueTableEntry> entries = leagueTable.getTableEntries();
        return entries.stream().map(LeagueTableEntry::getTeamName).collect(Collectors.toList());
    }
}