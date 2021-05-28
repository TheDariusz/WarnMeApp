## Twitter Alerts Feature

### Story:
As a user, I want to see meteo alerts so that I will be informed about the newest warnings

### Acceptance Criteria:

- User can trigger sync on demand
- Synchronize the latest changes from Twitter to database
- Display 5 meteo alerts per page

### Story:
As a user, I want to see details of alerts and map of Poland with the area of warnings marked so 
that I can check if the alert concern me

### Acceptance Criteria:

- posts with meteo alerts should have information about: short description of alert, level of alert (if provided),
  time of validation and map of Poland with impact area marked
  

### Story:
Application needs to fetch changes every 10 minutes

### Acceptance Criteria:

- Application has working scheduler that synchronize all changes every 10 minutes
- Changes are saved inside database
- Application retries synchronization 3 times when service is not available

### Story:
Application needs to fetch and store image files with meteo warnings maps

### Acceptance Criteria:

- Application will store image files of meteo alerts in the database
- The maximum size of image file should be set
