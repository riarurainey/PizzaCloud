This project use to learning about the oauth2. Using the Spring framework to create an authorization server (Authorization Code type)


<h3>URL to interact with the authorization server</h3>
localhost:9000/oauth2/authorize?response_type=code&client_id=pizza-admin-client&redirect_uri=http://127.0.0.1:9090/login/oauth2/code/pizza-admin-client&scope=writeIngredients+deleteIngredients

<h3>Command to exchange token from code</h3>
curl localhost:9000/oauth2/token \
-H"Content-type: application/x-www-form-urlencoded" \
-d"grant_type=authorization_code" \
-d"redirect_uri=http://127.0.0.1:9090/login/oauth2/code/pizza-admin-client" \
-d"code=$code" \
-u pizza-admin-client:secret

<h3>Command to refresh token</h3>
curl localhost:9000/oauth2/token \
-H"Content-type: application/x-www-form-urlencoded" \
-d"grant_type=refresh_token&refresh_token=$refresh_token" \
-u pizza-admin-client:secret

<h3> Command to test client</h3>

curl localhost:8080/api/ingredients \
-H"Content-type: application/json" \
-d'{"id":"FISH", "name":"Salmo salar", "type":"PROTEIN"}' \
-H"Authorization: Bearer $token"