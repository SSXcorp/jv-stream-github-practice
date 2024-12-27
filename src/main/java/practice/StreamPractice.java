package practice;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {
    private static final String COMMA = ",";

    private static final Predicate<Candidate> VALIDATOR = new CandidateValidator();

    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
                .flatMap(s -> Stream.of(s.split(COMMA)))
                .mapToInt(Integer::parseInt)
                .filter(n -> n % 2 == 0)
                .min()
                .orElseThrow(() -> new RuntimeException("Can't get min value from list: "
                        + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .mapToDouble(i -> {
                    int value = numbers.get(i);
                    return i % 2 != 0 ? value - 1 : value;
                })
                .filter(n -> n % 2 != 0.0)
                .average()
                .getAsDouble();
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
                .filter(p -> p.getAge() >= fromAge
                        && p.getAge() <= toAge
                        && p.getSex() == Person.Sex.MAN)
                .toList();
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        return peopleList.stream()
                .filter(person -> person.getAge() >= fromAge
                        && (person.getSex() == Person.Sex.MAN ? person.getAge() <= maleToAge
                                : person.getAge() <= femaleToAge))
                .toList();
    }

    public List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(p -> p.getSex() == Person.Sex.WOMAN
                        && p.getAge() >= femaleAge)
                .flatMap(p -> p.getCats().stream())
                .map(Cat::getName)
                .toList();
    }

    public List<String> validateCandidates(List<Candidate> candidates) {
        return candidates.stream()
                .filter(VALIDATOR)
                .map(Candidate::getName)
                .sorted()
                .toList();
    }
}
