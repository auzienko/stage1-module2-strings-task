package com.epam.mjc;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StringSplitter {

    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source     source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        String tmp = source;
        String tmpDelimiter = delimiters.stream().findAny().orElse("");

        for (String d : delimiters) {
            if (!d.equals(tmp)) {
                String[] array = tmp.split(d);
                tmp = String.join(tmpDelimiter, array);
            }
        }

        String[] array = tmp.split(tmpDelimiter);

        return Arrays.stream(array)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }
}
