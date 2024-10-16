package com.example.truecallertest.utils

object Util {

    fun findCharactersAtMultiplesOf15(input: String): List<Char> {
        val result = mutableListOf<Char>()

        // Loop through the string and find characters at positions that are multiples of 15
        for (i in 15..input.length step 15) {
            result.add(input[i - 1]) // Adjusting for 0-based indexing
        }

        return result
    }

    fun countWords(input: String): Map<String, Int> {
        // Split the string by whitespace characters (space, tab, line break, etc.)
        val words = input.split("\\s+".toRegex())

        // Create a mutable map to hold the word counts
        val wordCountMap = mutableMapOf<String, Int>()

        // Loop through the words and count their occurrences (case insensitive)
        for (word in words) {
            if (word.isNotEmpty()) {
                val lowerCaseWord = word.lowercase() // Convert to lowercase for case insensitivity
                wordCountMap[lowerCaseWord] = wordCountMap.getOrDefault(lowerCaseWord, 0) + 1
            }
        }

        return wordCountMap
    }
}