<h2>Requirements:</h2>

Java version: 17

Postgresql version: 14

---

<h2>How to run:</h2>

login to postgresql
```bash
psql postgresl
```
Create postgres user if not exist
```bash
CREATE USER postgres WITH PASSWORD "postgres" SUPERUSER;
```

populate data by running the following script:
```bash
./project/server/office-queue/src/main/resources/init-db.sh 
```

Run the project using play green button in the editor you have

