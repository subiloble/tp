package seedu.mentorstack.logic.parser;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import seedu.mentorstack.logic.commands.Command;
import seedu.mentorstack.logic.parser.exceptions.ParseException;
import seedu.mentorstack.logic.parser.exceptions.ParseWithHintException;

// Original command for parsing missing arguments from a string
// New method uses argMultiMap
// public static String missing(String ui) {
//     String[] splitted = ui.split(" ");

//     Map<String, String> ideal = new HashMap<>();
//     ideal.put("n", "John Doe");
//     ideal.put("e", "johnd@example.com");
//     ideal.put("p", "98765432");
//     ideal.put("s", "maths computer science");
//     ideal.put("g", "F");

//     int indOrg = 0;
//     int indMap = 0;
//     String[] resultArray = new String[splitted.length * 2];

//     while (indOrg < splitted.length) {
//         if (splitted[indOrg].equals("add")) {
//             indOrg++;
//             continue;
//         }

//         if (splitted[indOrg].contains("/")) {
//             String[] slashed = splitted[indOrg].split("/");
//             resultArray[indMap++] = slashed[0];
//             resultArray[indMap++] = String.join(" ", java.util.Arrays.copyOfRange(slashed, 1, slashed.length));
//         } else {
//             resultArray[indMap - 1] += " " + splitted[indOrg];
//         }

//         indOrg++;
//     }

//     Map<String, String> res = new HashMap<>();
//     for (int i = 0; i < indMap; i += 2) {
//         res.put(resultArray[i], resultArray[i + 1]);
//     }

//     StringBuilder toreturn = new StringBuilder();
//     for (Map.Entry<String, String> entry : ideal.entrySet()) {
//         if (!res.containsKey(entry.getKey())) {
//             toreturn.append(entry.getKey()).append("/").append(entry.getValue()).append(" ");
//         }
//     }
//     return " " + toreturn.toString().trim();
// }

public abstract class CommandParser {

    public abstract Command parse(String args) throws ParseException, ParseWithHintException;

    public String getMissingArgs(ArgumentMultimap argMultimap, Map<String, String> ideal) {
        Set<String> hset = new HashSet<String>();
        for (Map.Entry<Prefix, List<String>> entry : argMultimap.getArgMap().entrySet()) {
            hset.add(entry.getKey().toString());
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : ideal.entrySet()) { 
            if (!hset.contains(entry.getKey())) {
                sb.append(" " + entry.getKey() + entry.getValue());
            }
        }
        return sb.toString().trim();
    }
}
