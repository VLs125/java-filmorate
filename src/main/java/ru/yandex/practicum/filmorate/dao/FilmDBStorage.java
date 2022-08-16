package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class FilmDBStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Film film) {
        String sql = "insert into FILM (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) " +
                "values (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa_id().getId());
            return stmt;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM FILM WHERE ID = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setInt(1, id);
            return stmt;
        }, keyHolder);
        if (result != 1) {
            throw new ObjectNotFoundException(String.format("Фильм с id = %s , не найден", id));
        }
    }

    @Override
    public void update(Film film) {
        String sql = "UPDATE FILM SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, " +
                "DURATION = ?, MPA_ID = ?" +
                " WHERE ID = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            final LocalDate releaseDate = film.getReleaseDate();
            if (releaseDate == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(releaseDate));
            }
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa_id().getId());
            stmt.setInt(6, film.getId());
            return stmt;
        }, keyHolder);
        if (result != 1) {
            throw new ObjectNotFoundException();
        }
    }

    @Override
    public List<Film> getAll() {
        final String sqlQuery = "SELECT * FROM FILM";
        return jdbcTemplate.query(sqlQuery, FilmDBStorage::makeFilm);
    }

    @Override
    public Film getById(int id) {
        final String sqlQuery = "SELECT f.*," +
                "M.TITLE mpa_name, " +
                "ARRAY_AGG(FG.genre_id || ':' || g.name) AS genres_id " +
                "FROM FILM as F " +
                "LEFT JOIN FILM_RATE FR on F.id = FR.film_id " +
                "LEFT JOIN FILM_GENRES FG ON F.id = FG.film_id  " +
                "LEFT JOIN GENRE G ON FG.genre_id = G.id " +
                "LEFT JOIN MPA M ON F.mpa_id  = M.id " +
                "WHERE f.id = ? " +
                "GROUP BY f.ID ";
        List<Film> films = jdbcTemplate.query(sqlQuery, FilmDBStorage::makeFilm, id);
        if (films.size() != 1) {
            throw new ObjectNotFoundException(String.format("Фильм с id = %s , не найден", id));
        }

        return films.get(0);

    }

    public static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = new Mpa(rs.getInt("mpa_id"), rs.getString("mpa_name"));
        Set<Genre> genres_id = null;
        Object genreFromRow = rs.getArray("genres_id");
        if (genreFromRow != null) {
            genres_id = new HashSet<>();
            Object[] arr = (Object[]) rs.getArray("genres_id").getArray();
            for (Object genre : arr) {
                String[] mapper = genre.toString().split(":");
                genres_id.add(new Genre(Integer.parseInt(mapper[0]), mapper[1]));
            }
        }

        return new Film(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                rs.getInt("duration"),
                mpa,
                genres_id);
    }
}
