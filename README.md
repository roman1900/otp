# otp
A One Time Password micro service using H2 in memory database.  
application.properties holds your configuration settings.  
  
## Endpoints

### GET /opt/create/{id}  

Pass an id to the service and receive back a OTP code in JSON eg.  
    
  ```
  {
    "id": 2222222,  
    "code": 987826,  
    "expires": "2022-02-04T02:25:39.596+00:00"  
  }
 ```
  
### POST /opt/verify  

Accepts a json encoded body in the following format:  
  
```
{
    "id": 2222222,
    "code": 516971
}
```
  
 Returns a string response indicating failed or ok.


