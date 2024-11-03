## Development Environment

This project uses **PostgreSQL** as the database for development. Please ensure you have PostgreSQL installed and configured if you are running the application locally.

## Keycloak Setup for Development

This project uses Keycloak as an Identity Provider to handle authentication and authorization. Follow these steps to set up Keycloak for local development.

### Prerequisites

- Ensure Docker and Docker Compose are installed on your system.

### Steps

1. **Start Keycloak**:  
   Navigate to the `docker/keycloak` folder and run the following command to start Keycloak with the predefined configuration:

   ```bash
   cd docker/keycloak
   docker-compose up -d

This command will:

- Start a Keycloak container on port **8080**.
- Import a Keycloak realm named **showroom** with the necessary clients and roles from `realm-export.json`.

### Access Keycloak:

1. Open your browser and go to [http://localhost:8080](http://localhost:8080).
2. Log in to the Keycloak Admin Console with the following credentials:
   - **Username**: `admin`
   - **Password**: `admin`

### Client Secret:
1. Once logged in to keycloak select Realm `showroom`
2. Click on the `Clients` option from the side menu
3. Click on `backend` client ID
4. Navigate to Credentials and copy the Client Secret to use it in the backend application
   
### Integrate with Spring Boot:

In  Spring Boot application’s `application.yml`, configure Keycloak to connect to this instance.



```yaml
project:
  keycloak:
    client-id: backend
    admin-url: http://localhost:8080
    realm: showroom
    client-secret: <backend_client_secret>
```

### API Testing with Postman

A Postman collection with example requests for this project can be found in the [`postman/`](postman/cars.postman_collection.json) folder. To use it:

1. Download the file.
2. Open Postman and use the **Import** option to add the collection.

please make sure to update the `clientSecret` value by following these steps:
Click on the Collection ('cars') -> Scripts -> Pre-request
You will find the `clientSecret`, you can update it and use the `username` and `password` of the users you created   


### Stop Keycloak:
```bash
docker-compose down
```
### Notes
1. The realm-export.json file preconfigures Keycloak with two clients (backend and frontend) and relevant roles.
2. Please make sure to sign up to be able to craete showrooms and cars, you can sign up from the frontend or from Postman request
