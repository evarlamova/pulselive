package com.pulsive.task.league;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeagueTable {

    private static final Comparator<LeagueTableEntry> LEAGUE_TABLE_ENTRY_COMPARATOR = new LeagueTableComparator();
    private final List<LeagueTableEntry> entries;

    public LeagueTable(final List<Match> matches) {
        if (matches == null) {
            throw new IllegalArgumentException("Matches can't be null");
        }

        Map<String, LeagueTableEntry.Builder> builderMap = new HashMap<>();
        for (Match match : matches) {
            LeagueTableEntry.Builder homeEntry = builderMap.computeIfAbsent(match.getHomeTeam(),
                    m -> LeagueTableEntry.builder(match.getHomeTeam()));
            homeEntry.addHomeMatch(match);
            LeagueTableEntry.Builder awayEntry = builderMap.computeIfAbsent(match.getAwayTeam(),
                    m -> LeagueTableEntry.builder(match.getAwayTeam()));
            awayEntry.addAgainstMatch(match);
        }
        this.entries = builderMap.values().stream()
                .map(LeagueTableEntry.Builder::build)
                .sorted(LEAGUE_TABLE_ENTRY_COMPARATOR)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<LeagueTableEntry> getTableEntries() {
        return entries;
    }
}
