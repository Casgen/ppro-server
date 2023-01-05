# ppro-server

## Setup

This section provides the setup process for the API

### Steps

1. Install Docker (The desktop version or Terminal one)
2. Pull MariaDB from the Docker hub (`docker pull mariadb`)
3. Start a MariaDB server instance through the Desktop app or terminal. Make sure you also setup the apropriate variables (`docker run --detach --name some-mariadb --env MARIADB_USER=example-user --env MARIADB_PASSWORD=my_cool_secret --env MARIADB_ROOT_PASSWORD=my-secret-pw  mariadb:latest`) and map the host ports to container's ports properly (3306:3306)
4. Start Intellij idea with this repository
5. In the Database tab (View -> Tool Windows -> Database) create add a new MariaDB data source (Through the + sign)
6. Setup the new data source with the proper settings (username and password, port, address that you could have passed in the `docker run` command) and test the connection
7. Create a new schema (ideally `film_db`)
8. In the Persistance tab (View -> Tool windows) persistence, make sure you assign the apropriate Data Source to the newly created database
9. create a `application.properties` file in the package `resources` and describe following parameters

`spring.datasource.url=jdbc:mariadb://localhost:3306/film_db`<br>
`spring.datasource.username=root`<br>
`spring.datasource.password=rootpassword`<br>

Here you can describe what to do with the schema after the applications stops or starts
`spring.jpa.hibernate.ddl-auto=create-drop`<br>
`spring.jpa.show-sql=true`<br>
`spring.jpa.properties.hibernate.format_sql=true`<br>

`server.error.include-message=always`<br>
