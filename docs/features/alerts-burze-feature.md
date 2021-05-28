## Burze.dzis.net feature

### Story: 
As a user, I want to see meteo alerts for my area so that I will be informed about the newest warnings

### Acceptance Criteria:

- User can trigger sync on demand
- Synchronize the latest changes from `burze.dzis.net` to database
- Information retrieve from the source: type of warning, level of warning, coordinates of warning
- User sets coordinates of warnings by preferences page
- Display 5 meteo alerts per page

### Story:
As a user, I want to check lightning discharges for my area so that I will be known about storms in my neighborhood 

### Acceptance Criteria:

- An authorized user can view on the main page information about storms
- User sets coordinates of the point that will be center of area with storms by preferences page
- User sets radius of area with storms by preferences page
- Synchronize the retrieved information about storms to database

### Story: 
Application needs to fetch changes every 10 minutes

### Acceptance Criteria:

- Application has working scheduler that synchronize all changes every 10 minutes
- Changes are saved inside database
- Application retries synchronization 3 times when service is not available

