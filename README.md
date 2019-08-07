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

To add:

- Add CIDER/REPL setup