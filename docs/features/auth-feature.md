## Auth Feature

### Story: 
As a user, I want to create account in WarnMe application so that I can see alerts

### Acceptance Criteria:

- a user can't create account based on email that already exists in the database (appropriate message should be displayed)
- in the registration process, a new user should set his password according to a password policy
- display error message when password does not match the policy
- the policy of password: at least one number, min length of password is 8 signs
- authorized user can save custom preferences in the profile configuration
- authorized user can view alerts from selected period of time
- authorized user can view information about storms (from `burze.dzis.net`)
- unauthorized user can see only the newest alerts on the main page
- unauthorized user can't select alerts from the specific time 
- display error message when unauthorized user tries access restricted resource


### Story:
As a logged user, I want to change my password to the app

### Acceptance Criteria:

- an authorized user can access configuration page where that can change his password
- an authorized user can set new password
- a new password must not be the same as previous one
- a new password must meet a password policy


### Story:
As a logged user, I want to change my preferences in the app

### Acceptance Criteria:

- an authorized user can access configuration page where there will be displayed user preferences
- an authorized user can set and change: data sources of meteo warnings, subscribe the alerts newsletter,
  coordinates for burze.dzis.net warnings
- an unauthorized user can't save preferences
- an unauthorized user can't see link to the preferences page


