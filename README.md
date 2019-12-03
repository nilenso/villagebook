# villagebook
villagebook helps people keep track of their livestock.

## Steps to run locally

### Backend
- Install PostgreSQL [https://www.postgresql.org/download/](https://www.postgresql.org/download/)
- Install Lein https://leiningen.org/#install.
- Tweak config (if required) in src/config.clj
- Start the server
```bash
createdb villagebook
lein run migrate
lein ring server
```

### Frontend

```bash
npm install
shadow-cljs watch app
```

### Run application through Docker
```bash
docker-compose up --build
```

### Run tests
By running docker-compose up, postgres will be running with test db.
```bash
docker-compose up --build
```

Add host entry (/etc/hosts) to connect postgres db (running via docker)
```bash
127.0.0.1   villagebook-postgres
```


```bash
lein test
```

To add:

- Add CIDER/REPL setup
