# Conslator
A simple console app to practice foreign language words.

## About
This is a simple console application for a foreign language practice. At the moment the app is created for a practice
of Polish - German pairs of words. The user can create new set of pairs of words (lessons), which are stored in a 
local database (file), and then practice these words in the console. The program requests to choose a practice 
direction (from Polish to German or from German to Polish) and then prints the word and waits for the translation to
be provided by the user. If the user provided incorrect translation then the program requests to translate this word
again in the following "round". The practice does not finish until the user provides all translations correct.

## Used technologies
- Java
- Spring Boot
- H2 database
- Maven

## Execution
To run the program run the following command in the project root folder:  
`mvn -q spring-boot:run`

## Features
- Adding new lessons with new sets of pairs of words
- Practicing the words from German to Polish of from Polish to German
- Considering cases and absence of special Polish or German characters 
  while checking the correctness of provided translation
- Practice until the user does not make any mistakes

## Possible future works
- Handling more languages
- The possibility of lesson and word editions and removals

## Licence
This is an open source repo licensed as [MIT License](LICENSE.md).