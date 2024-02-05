
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if (str.length() <= 1) {
            return "";
        }
        return str.substring(1);
	}

	public static int levenshtein(String word1, String word2) {
		int len1 = word1.length();
        int len2 = word2.length();
        if (len1 == 0) return len2;
        if (len2 == 0) return len1;
        int cost = (Character.toLowerCase(word1.charAt(0)) == Character.toLowerCase(word2.charAt(0))) ? 0 : 1;
        int delete = levenshtein(tail(word1), word2) + 1;
        int insert = levenshtein(word1, tail(word2)) + 1;
        int substitute = levenshtein(tail(word1), tail(word2)) + cost;
        return Math.min(delete, Math.min(insert, substitute));
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];
		In in = new In(fileName);
		for(int i=0; i<3000; i++){
			dictionary[i]= in.readLine();
		}	
		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
        String closestWord = word;
        int minDistance = Integer.MAX_VALUE;
        for (String dictWord : dictionary) {
            int distance = levenshtein(word.toLowerCase(), dictWord.toLowerCase());
            if (distance < minDistance) {
                minDistance = distance;
                closestWord = dictWord;
            }
        }
        return (minDistance <= threshold) ? closestWord : word;
	}


}
