package ueb05;

import java.util.*;

class CorpusAnalyzer {
	private List<String> theses;

	CorpusAnalyzer(Iterator<String> thesesIterator) {
		theses = new LinkedList<>();
		while(thesesIterator.hasNext())
			this.theses.add(thesesIterator.next());
	}

	/**
	 * Gibt die Anzahl der angefertigten Theses zurück
	 */
	int countTheses() {
		return theses.size();
	}

	/**
	 * Gibt die durchschnittliche Länge von Titeln in Worten zurück
	 */
	int averageThesisTitleLength() {
		List<String> totalWords = new LinkedList<>();
		Iterator<String> iterator = theses.iterator();
		while(iterator.hasNext()){
			for(String string: iterator.next().split(" "))
				totalWords.add(string);
		}
		return totalWords.size()/countTheses();
	}

	/**
	 * Gibt eine alphabetisch absteigend sortierte und duplikatfreie
	 * Liste der ersten Wörter der Titel zurück.
	 */
	List<String> uniqueFirstWords() {
		Set<String> set = new TreeSet<>();
		for(String thesis: theses)
			set.add(thesis.split(" ")[0]);
		List<String> uniqueFirstWords = new LinkedList<>(set);
		uniqueFirstWords.sort(Collections.reverseOrder());
		return uniqueFirstWords;
	}

	/**
	 * Gibt einen Iterator auf Titel zurück; dabei werden alle Woerter, welche
	 * in `blackList` vorkommen durch Sternchen ersetzt (so viele * wie Buchstaben).
	 */
	Iterator<String> censoredIterator(Set<String> blackList) {
		List<String> censoredTheses = new LinkedList<>();
		for(String thesis: theses) {
			String censoredThesis = "";
			for (String string : thesis.split(" ")) {
				if (blackList.contains(string)) {
					String s = "";
					for (char c : string.toCharArray()) s += "*";
					string = s;
				}
				if(censoredThesis.isEmpty()) censoredThesis = string;
				else censoredThesis = censoredThesis + " " + string;
			}
			censoredTheses.add(censoredThesis);
		}
		return censoredTheses.iterator();
	}

	/**
	 * Gibt eine Liste von allen Titeln zurueck, wobei Woerter so ersetzt werden,
	 * wie sie in der Map abgebildet werden, und die Liste nach Stringlaenge absteigend sortiert ist.
	 */
	List<String> normalizedTheses(Map<String, String> replace) {
		List<String> normalizedTheses = new LinkedList<>();
		for(String thesis: theses) {
			String normalizedThesis = "";
			for (String string : thesis.split(" ")) {
				if (replace.containsKey(string)) {
					string = replace.get(string);
				}
				if(normalizedThesis.isEmpty()) normalizedThesis = string;
				else normalizedThesis = normalizedThesis + " " + string;
			}
			normalizedTheses.add(normalizedThesis);
		}
		normalizedTheses.sort(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return o1.length()-o2.length();
			}
		});
		return normalizedTheses;
	}
}
