package org.example;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordStats {

    public String[] top_3_words(InputStream inputStream) {
        // Scanning an inputStream will enable us to process arbitrarily long inputs
        Scanner scanner = new Scanner(inputStream).useDelimiter("[^a-zA-Z']+");

        // Scanner implements Iterator, let's turn it into a Stream
        Stream<String> tokenStream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(scanner, 0), false);
        // Group all tokens by token (case-insensitive) and count them
        Map<String, Integer> wordCount = tokenStream
                .collect(Collectors.toMap(String::toLowerCase, word -> 1, Integer::sum));

        // Map.Entry provides a handy comparator for our case
        Comparator<Map.Entry<String, Integer>> compareByCount = Map.Entry.comparingByValue();

        return wordCount.entrySet().stream()
                .sorted(compareByCount.reversed()) // highest count first
                .map(Map.Entry::getKey) // extract the word
                .limit(3) // top 3 entries
                .toArray(String[]::new);
    }
}
