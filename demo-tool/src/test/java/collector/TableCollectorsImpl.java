package collector;

import com.google.common.collect.HashBasedTable;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * @author ccy
 * @description
 * @time 2023/6/5 17:16
 */
public class TableCollectorsImpl<T, A, R> implements Collector<T, A, R> {
    private final Supplier<A> supplier;
    private final BiConsumer<A, T> accumulator;
    private final BinaryOperator<A> combiner;
    private final Function<A, R> finisher;
    private final Set<Characteristics> characteristics;


    /**
     * @param rowMapper
     * @param colMapper
     * @param valueMapper
     * @param <ROW>
     * @param <COL>
     * @param <U>
     * @param <T>         入参,stream流中的对象
     * @param <A>
     * @param <M>
     * @return
     */
    public static <ROW, COL, U, T, A extends HashBasedTable<ROW, COL, U>, M extends HashBasedTable<ROW, COL, U>>
    Collector<T, A, M> ofTableCollectors(Function<? super T, ? extends ROW> rowMapper,
                                         Function<? super T, ? extends COL> colMapper,
                                         Function<? super T, ? extends U> valueMapper
    ) {
        Supplier tableSupplier = HashBasedTable::create;
        BiConsumer<M, T> accumulator
                = (map, element) -> map.put(rowMapper.apply(element), colMapper.apply(element), valueMapper.apply(element));
        BinaryOperator<M> mergerCombiner = (m1, m2) -> {
            m1.putAll(m2);
            return m1;
        };
        Function<A, M> finisher = i -> (M) i;
        Set<Collector.Characteristics> characteristicsSet
                = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
        return new TableCollectorsImpl(tableSupplier, accumulator, mergerCombiner, finisher, characteristicsSet);
    }


    TableCollectorsImpl(Supplier<A> supplier,
                        BiConsumer<A, T> accumulator,
                        BinaryOperator<A> combiner,
                        Function<A, R> finisher,
                        Set<Characteristics> characteristics) {
        this.supplier = supplier;
        this.accumulator = accumulator;
        this.combiner = combiner;
        this.finisher = finisher;
        this.characteristics = characteristics;
    }


    @Override
    public Supplier<A> supplier() {
        return supplier;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return accumulator;
    }

    @Override
    public BinaryOperator<A> combiner() {
        return combiner;
    }

    @Override
    public Function<A, R> finisher() {
        return finisher;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return characteristics;
    }
}