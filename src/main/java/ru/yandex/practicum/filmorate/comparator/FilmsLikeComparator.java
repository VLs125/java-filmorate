package ru.yandex.practicum.filmorate.comparator;

import org.springframework.stereotype.Component;

import java.util.Comparator;


@Component
public class FilmsLikeComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer o1,
                       Integer o2) {
        return Integer.compare(o1, o2);
    }
}

