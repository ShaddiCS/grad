### A simple voting system for deciding where to have lunch today. 

Implementation Stack:
- Spring Boot
- Spring Data Jpa 

## Install:
```
git clone https://github.com/ShaddiCS/grad
```
## Run (from project directory)
```
$ mvn spring-boot:run
```

or
```
$ mvn clean package
$ java -Dfile.encoding=UTF8 -jar target/grad.jar
```

### Users
403 Forbidden
```
'curl -s http://localhost:8080/rest/users --user user@yandex.ru:password'
```
##### Curl
```
'curl -s http://localhost:8080/rest/users --user admin@gmail.com:admin'
'curl -s http://localhost:8080/rest/users/100000 --user admin@gmail.com:admin'
'curl -s -X POST -d '{"email":"some@email.ru", "password":"newpassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/users --user admin@gmail.com:admin'
'curl -s -X DELETE --user admin@gmail.com:admin'
```

### Restaurants
##### Curl
```
'curl -s http://localhost:8080/rest/restaurants --user user@yandex.ru:password'
'curl -s http://localhost:8080/rest/restaurants/100002 --user user@yandex.ru:password'
```
403 Forbidden
```
'curl -s -X POST -d '{"name":"new place"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/restaurants --user user@yandex.ru:password'
```
Allowed for Admin
```
'curl -s -X POST -d '{"name":"new place"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/restaurants --user admin@gmail.com:admin'
'curl -s -X DELETE --user admin@gmail.com:admin'
'curl -s -X PUT -d '{"id":100002, "name":"updated name"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/restaurants/100002 --user admin@gmail.com:admin'
```

### Menus
##### Curl
```
'curl -s http://localhost:8080/rest/menus --user user@yandex.ru:password'
'curl -s http://localhost:8080/rest/menus/100005 --user user@yandex.ru:password'
'curl -s http://localhost:8080/rest/menus/by-restaurant?restaurant=100002 --user user@yandex.ru:password'
'curl -s http://localhost:8080/rest/menus/by-date?date=2020-09-02 --user@yandex.ru:password'
```
403 Forbidden
```
'curl -s -X POST -d '{"date":"2020-09-02", "restaurant":100002}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/menus --user user@yandex.ru:password'
```
Allowed for Admin
```
'curl -s -X POST -d '{"date":"2020-09-02", "restaurant":100002}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/menus --user admin@gmail.com:admin'
'curl -s -X PUT -d '{"id":100005, "date": "2020-09-02", "restaurant": 100002}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/menus/100005 --user admin@gmail.com:admin'
'curl -s -X DELETE http://localhost:8080/rest/menus --user admin@gmail.com:admin'
```

### Dishes
##### Curl
```
'curl -s http://localhost:8080/rest/dishes/100008 --user user@yander.ru:password'
```
403 Fordbidden
```
'curl -s -X POST -d '{"name":"new dish", "price":400, "menuId": 100005}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes --user user@yandex.ru:password'
```
Allowed for Admin
```
'curl -s -X POST -d '{"name":"new dish", "price":400, "menuId": 100005}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes --user admin@gmail.com:admin'
'curl -s -X PUT -d '{"id":100008, "name":"updated name", "price": 250, "menuId": 100005}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/dishes --user admin@gmail.com:admin'
'curl -s -X DELETE http://localhost:8080/rest/dishes/100009 --user admin@gmail.com:admin'
```

### Votes
##### Curl
Current vote
```
'curl -s http://localhost:8080/rest/vote --user user@yandex.ru:password'
```
Vote
```
'curl -s -X POST http://localhost:8080/rest/vote/100002 --user user@yandex.ru:password'
```
Update vote (works until 11:00 server time)
```
'curl -s -X PUT http://localhost:8080/rest/vote/100003 --user user@yandex.ru:password'
```



