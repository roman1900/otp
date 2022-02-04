# otp
A One Time Password micro service using H2 in memory database.  
application.properties holds your configuration settings.  
  
## Endpoints

### GET /opt/create/{id}  

Pass an id to the service and receive back a OTP code in JSON as below.  
This code has a default expiry of 5 minutes.
    
  ```
  {
    "id": 2222222,  
    "code": 987826,  
    "expires": "2022-02-04T02:25:39.596+00:00"  
  }
 ```

### GET /opt/create/{id}/{lifetime}  

Pass an id and a custom lifetime to the service and receive back a OTP code in JSON as above.
The lifetime should be the time in milliseconds that the code will be valid for. eg. 60000 is 1 minute.

### POST /opt/verify  

Accepts a json encoded body in the following format:  
  
```
{
    "id": 2222222,
    "code": 516971
}
```
  
 Returns a string response indicating failed or ok.


