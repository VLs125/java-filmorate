# java-filmorate

## Диаграмма базы данных
![Диаграмма](https://github.com/VLs125/java-filmorate/blob/main/DatabaseDiagram.png)

## Основные запросы для БД
#### Получения списка всех пользователей
``` 
SELECT * FROM user
```

#### Получения списка всех фильмов
```
SELECT * FROM film
```

#### Получения конкретного пользователя
```
SELECT * FROM user
WHERE user.id = id
 ````
  
#### Получения конкретного фильма
```
SELECT * FROM film
WHERE film.id = id 
```
  
#### Добавление пользователя
``` 
INSERT INTO user (id, name, email, login, birthday) 
VALUES (id, name, email, login, birthday)
```

#### Добавление фильма
```
INSERT INTO film (id, name, description, release_date, duration, rating, genre) 
VALUES (id, name, description, release_date, duration, rating, genre)
```

#### Получение 10 самых популярных фильмов
```  
SELECT f.name,  
COUNT(lf.user) AS like_count  
FROM film AS f  
LEFT JOIN liked_film AS lm ON film.id = lm.id  
ORDER BY COUNT(like_count) ASC LIMIT 10;  
```
  
