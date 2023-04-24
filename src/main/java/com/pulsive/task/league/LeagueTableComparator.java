package com.pulsive.task.league;

import java.util.Comparator;

public class LeagueTableComparator implements Comparator<LeagueTableEntry>{

    private final Comparator<LeagueTableEntry> compare = Comparator
            .comparing(LeagueTableEntry::getPoints)
            .thenComparing(LeagueTableEntry::getGoalDifference)
            .thenComparing(LeagueTableEntry::getGoalsFor)
            .reversed()
            .thenComparing(LeagueTableEntry::getTeamName);

    @Override
    public int compare(LeagueTableEntry firstEntry, LeagueTableEntry secondEntry) {
        return compare.compare(firstEntry, secondEntry);
    }

}
