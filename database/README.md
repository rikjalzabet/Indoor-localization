# SETUP INSTRUCTIONS

- [Installing and configuring Docker containers](#installing-and-configuring-docker-containers)
  - [Installing Docker Desktop](#installing-docker-desktop)
  - [Cloning the repository](#cloning-the-repository)
- [Setting up local database](#setting-up-local-database)
  - [Start Docker containers](#start-docker-containers)
  - [Access pgAdmin](#access-pgadmin)
  - [Register the PostgreSQL server](#register-the-postgresql-server)
  - [Verify Database Setup](#verify-database-setup)
- [Managing Docker containers](#managing-docker-containers)
- [(Optional) Node-RED simulator](#optional-node-red-simulator)


## Installing and configuring Docker containers

### Installing Docker Desktop

To begin, download and install [Get Started | Docker](https://www.docker.com/get-started/) based on your operating system. This guide assumes installation on Windows. Ensure that Docker Desktop is running before proceeding to the next steps. Make sure Docker Desktop is running while initializing the database.



### Cloning the repository

Clone the repository from GitHub: [GitHub - rikjalzabet/Indoor-localization](https://github.com/rikjalzabet/Indoor-localization). Once checked out, you should see four folders, including `DB` folder containing the necessary configuration files.

- `.env`: Stores environment variables.
    
- `.flows.json`: Defines Node-RED flow for interacting with PostgreSQL.
    
- `docker-compose.yml`: Configures Docker services.
    
- `Dockerfile`: Builds the Node-RED container.
    
- `init.sql`: Creates database schema.
    
- `populate.sql`: Inserts initial data into tables.
    

## Setting up local database

### Start Docker containers

Open a terminal inside the `DB` folder and execute `docker-compose up -d --build`. This will initialize the Docker containers running PostgreSQL, pgAdmin, and Node-RED.


### Access pgAdmin

Open any browser and navigate to `http://localhost:15433`. The interface may take a few moments to load. Use the following credentials to log in:

- **Email:** `admin@admin.com`
    
- **Password:** `admin`
    

### Register the PostgreSQL server

- Right-click on **Servers → Register → Server...**
    
- In the **General** tab, set the **Name** to `database`.

- In the **Connection** tab, enter the following details:
    
    - **Host:** `postgres_db`
        
    - **Username:** `username`
        
    - **Password:** `password`
        
- Click **Save**.
    
A new database named `il_database` should now appear.



### Verify Database Setup

To check if the database is correctly configured:

- Right-click `il_database` → **Query Tool**.
    
- Run the following SQL queries individually and execute them (F5):
        
    ```
    SELECT * FROM assets;
    SELECT * FROM assetPositionHistory;
    SELECT * FROM assetZoneHistory;
    SELECT * FROM floorMaps;
    SELECT * FROM zones;
    ```
        
- If successful, it should return sample data.

    
## Managing Docker containers

- To completely remove and reset all Docker containers, including any stored data:
    
     ```docker-compose down -v ```
    
    You will need to reinitialize the database following the steps in [Setting up local database](#setting-up-local-database) .
    
- To stop the container while retaining the data:
    
     ```docker-compose stop ```
    
- To restart existing containers:
    
     ```docker-compose start ```
    
    You will still need to re-enter the pgAdmin credentials (`admin@admin.com` / `admin`) and, if prompted, the database password (`password`).
    

## (Optional) Node-RED simulator

The database configuration includes a Node-RED simulator, which serves as a foundation for asset simulation. While it does not currently play a significant role in the database setup, it can be used to generate simulated data.

To use the simulator:

1. Ensure the Docker container is running. 

2. Navigate to `http://localhost:1880`

3. Click the **left button next to the Start node** to generate data for the `assetPositionHistory` and `assetZoneHistory` tables. The generated data will be stored in the local database.
