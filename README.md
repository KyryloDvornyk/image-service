## Installation
1. Fork and copy this project
2. Run application

## Usage
Test Tags will be created at the start of program with ids: 1, 2, 3.  
For tests used Postman.  
Register user using following endpoint:  
POST /register body : {"login" : "login", "password" : "password"}  
Next use Authorization tab and in Basic auth set your login and password that you set in register.  
Now you can use all following endpoints:  
POST /images body : form-data key=files value={select files} - create new image for current account  
PUT /images/{id} body : same as in POST - change image for current account  
PUT /images/{imageId}/tags?tagId={tagId} - add tag for image  
GET /images/{id} - get image by id  
GET /images?parameter={stringParameter}&page={numberOfPage}&size={sizeNumber} - get all images by parameter. Search for 
images fields like tags or name.
DELETE /images/{id} - delete image by id
