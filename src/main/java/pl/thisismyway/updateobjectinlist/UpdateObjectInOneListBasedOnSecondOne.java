package pl.thisismyway.updateobjectinlist;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;


@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class UpdateObjectInOneListBasedOnSecondOne {

    @Param({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39","40"})
    private int blackholeTokens;

    private Set<BookOverallData> getListWithBooks(){

        return Set.of(new BookOverallData(1L, "1984", "George Orwell", BigDecimal.valueOf(25.6), 10),
                new BookOverallData(2L, "Sentimental Education", "Gustave Flaubert", BigDecimal.valueOf(12.3), 0),
                new BookOverallData(3L, "Absalom, Absalom!", "William Faulkner", BigDecimal.valueOf(32.62), 30),
                new BookOverallData(4L, "The Hunger Games", "Suzanne Collins", BigDecimal.valueOf(12.45), 40),
                new BookOverallData(5L, "To Kill a Mockingbird", "Harper Lee", BigDecimal.valueOf(10.6), 10),
                new BookOverallData(6L, "Pride and Prejudice", "Jane Austen", BigDecimal.valueOf(15.4), 0),
                new BookOverallData(7L, "The Book Thief", "Markus Zusak", BigDecimal.valueOf(9.99), 15),
                new BookOverallData(8L, "Animal Farm", "George Orwell", BigDecimal.valueOf(22.0), 24),
                new BookOverallData(9L, "Gone with the Wind", "Margaret Mitchell", BigDecimal.valueOf(12.4), 27),
                new BookOverallData(10L, "The Fault in Our Stars", "John Green", BigDecimal.valueOf(16.00), 17),
                new BookOverallData(11L, "The Giving Tree", "Shel Silverstein", BigDecimal.valueOf(19.99), 15),
                new BookOverallData(12L, "Wuthering Heights", "Emily Brontë", BigDecimal.valueOf(20.0), 11),
                new BookOverallData(13L, "The Da Vinci Code", "Dan Brown", BigDecimal.valueOf(10.0), 7),
                new BookOverallData(14L, "Memoirs of a Geisha", "Arthur Golden", BigDecimal.valueOf(45.0), 4),
                new BookOverallData(15L, "The Picture of Dorian Gray", "Oscar Wilde", BigDecimal.valueOf(23.99), 16),
                new BookOverallData(16L, "Les Misérables", "Victor Hugo", BigDecimal.valueOf(12.00), 24),
                new BookOverallData(17L, "Jane Eyre", "Charlotte Brontë", BigDecimal.valueOf(55.99), 12),
                new BookOverallData(18L, "Lord of the Flies", "William Golding", BigDecimal.valueOf(24.00), 6),
                new BookOverallData(19L, "Crime and Punishment", "Fyodor Dostoyevsky", BigDecimal.valueOf(17.55), 0),
                new BookOverallData(20L, "The Perks of Being a Wallflower", "Stephen Chbosky", BigDecimal.valueOf(15.99), 0));
    }

    private Set<TimeDiscount> getListWithDiscounts(){
        return Set.of(new TimeDiscount(1L, 20), new TimeDiscount(8L, 20), new TimeDiscount(19L, 20));
    }

    @Benchmark
    public int baseLine(){
        Blackhole.consumeCPU(blackholeTokens);
        return 31;
    }


    @Benchmark
    public Set<BookOverallData> usedForLoop() {
        Blackhole.consumeCPU(blackholeTokens);

        final Set<BookOverallData> listWithBooks = getListWithBooks();
        final Set<TimeDiscount> listWithDiscounts = getListWithDiscounts();

        Blackhole.consumeCPU(blackholeTokens);

        for (BookOverallData bookOverallData : listWithBooks){
            for (TimeDiscount timeDiscount : listWithDiscounts){
                if (timeDiscount.getIdOfBook().equals(bookOverallData.getIdOfBook())){
                    bookOverallData.setDiscountRate(timeDiscount.getDiscountRate() + bookOverallData.getDiscountRate());
                }
            }
        }

        Blackhole.consumeCPU(blackholeTokens);

        return listWithBooks;
    }


    @Benchmark
    public Set<BookOverallData> streamOfStream() {
        Blackhole.consumeCPU(blackholeTokens);

        final Set<BookOverallData> listWithBooks = getListWithBooks();
        final Set<TimeDiscount> listWithDiscounts = getListWithDiscounts();

        Blackhole.consumeCPU(blackholeTokens);

        listWithBooks.forEach(
                p -> {
                    final Optional<TimeDiscount> promotion = listWithDiscounts.stream().filter(ap -> Objects.equals(ap.getIdOfBook(), p.getIdOfBook())).findFirst();
                    promotion.ifPresent(ap -> p.setDiscountRate(ap.getDiscountRate() + p.getDiscountRate()));
                }
        );

        Blackhole.consumeCPU(blackholeTokens);

        return listWithBooks;
    }


    @Benchmark
    public Set<BookOverallData> streamOptimized() {
        Blackhole.consumeCPU(blackholeTokens);

        final Set<BookOverallData> listWithBooks = getListWithBooks();
        final Set<TimeDiscount> listWithDiscounts = getListWithDiscounts();

        Blackhole.consumeCPU(blackholeTokens);

        Map<Long, TimeDiscount> accumulator =
                listWithDiscounts.stream()
                        .collect(Collectors.toMap(TimeDiscount::getIdOfBook, Function.identity()));

        listWithBooks.forEach(e ->
                Optional.ofNullable(accumulator.get(e.getIdOfBook()))
                        .ifPresent(p -> e.setDiscountRate(e.getDiscountRate() + p.getDiscountRate()))
        );

        Blackhole.consumeCPU(blackholeTokens);

        return listWithBooks;
    }
}
