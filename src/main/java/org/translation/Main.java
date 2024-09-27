package org.translation;

import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {
    public static final String QUIT = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */

    public static void main(String[] args) {

        // TODO Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        // Translator translator = new JSONTranslator(null);
        Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */

    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            // TODO CheckStyle: The String "quit" appears 3 times in the file.
            // TODO Checkstyle: String literal expressions should be on the left side of an equals comparison
            if (QUIT.equals(country)) {
                break;
            }
            // TODO Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            String language = promptForLanguage(translator, country);
            if (QUIT.equals(language)) {
                break;
            }
            // TODO Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        String[] countryNames = new String[countries.size()];
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        for (int i = 0; i < countries.size(); i++) {
            countryNames[i] = countryCodeConverter.fromCountryCode(countries.get(i));
        }
        countryNames = sort(countryNames);
        for (String countryName : countryNames) {
            System.out.println(countryName);
        }
        System.out.println("Select a country from above:");

        Scanner s = new Scanner(System.in);
        String countryInput = s.nextLine();
        for (String country : countries) {
            if (countryCodeConverter.fromCountryCode(country).equals(countryInput)) {
                return country;
            }
        }
        return countryInput;

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);
        String[] languageNames = new String[languages.size()];
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
        for (int i = 0; i < languages.size(); i++) {
            languageNames[i] = languageCodeConverter.fromLanguageCode(languages.get(i));
        }
        languageNames = sort(languageNames);
        for (String languageName : languageNames) {
            System.out.println(languageName);
        }
        System.out.println("Select a language from above:");

        Scanner s = new Scanner(System.in);
        String languageInput = s.nextLine();
        for (String language : languages) {
            if (languageCodeConverter.fromLanguageCode(language).equals(languageInput)) {
                return language;
            }
        }
        return languageInput;
    }
    private static String[] sort(String[] array) {
        String temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].compareTo(array[j]) > 0) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }
}
