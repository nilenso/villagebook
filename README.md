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

### Dockerize
```bash
docker-compose up
```

### Run tests using Docker
If you are running docker for running postgres
add host entry (/etc/hosts) for postgres database
`127.0.0.1       villagebook-postgres`


```bash
docker-compose up
lein test
```

To add:

- Add CIDER/REPL setup
