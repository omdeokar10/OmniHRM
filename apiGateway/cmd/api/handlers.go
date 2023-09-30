package main

import (
	"apiGateway/internal/models"
	"errors"
	"fmt"
	"net/http"
	"strconv"
	"time"

	"github.com/golang-jwt/jwt/v4"
)

// Home displays the status of the api, as JSON.
func (app *application) Home(w http.ResponseWriter, r *http.Request) {
	var payload = struct {
		Status  string `json:"status"`
		Message string `json:"message"`
		Version string `json:"version"`
	}{
		Status:  "active",
		Message: "Go Movies up and running",
		Version: "1.0.0",
	}

	_ = app.writeJSON(w, http.StatusOK, payload)
}

// authenticate authenticates a user, and returns a JWT.
func (app *application) authenticate(w http.ResponseWriter, r *http.Request) {
	// read json payload
	var requestPayload struct {
		Email    string `json:"email"`
		Password string `json:"password"`
	}

	err := app.readJSON(w, r, &requestPayload)
	if err != nil {
		app.errorJSON(w, err, http.StatusBadRequest)
		return
	}

	// validate user against database
	// user, err := app.DB.GetUserByEmail(requestPayload.Email)
	// if err != nil {
	// 	app.errorJSON(w, errors.New("invalid credentials"), http.StatusBadRequest)
	// 	return
	// }

	type User struct {
		ID        int       `json:"id"`
		FirstName string    `json:"first_name"`
		LastName  string    `json:"last_name"`
		Email     string    `json:"email"`
		Password  string    `json:"password"`
		CreatedAt time.Time `json:"-"`
		UpdatedAt time.Time `json:"-"`
	}
	user := models.User{
		ID:        123,
		FirstName: "Max",
		LastName:  "John",
		Email:     "abc@xyz.com",
		Password:  "12345",
	}
	// check password
	fmt.Printf("Password Recieved: %+v", requestPayload.Password)
	valid, err := user.PasswordMatches(requestPayload.Password)
	if err != nil || !valid {
		app.errorJSON(w, errors.New("invalid credentials"), http.StatusBadRequest)
		return
	}

	// create a jwt user
	u := jwtUser{
		ID:        user.ID,
		FirstName: user.FirstName,
		LastName:  user.LastName,
	}

	// generate tokens
	tokens, err := app.auth.GenerateTokenPair(&u)
	if err != nil {
		app.errorJSON(w, err)
		return
	}

	refreshCookie := app.auth.GetRefreshCookie(tokens.RefreshToken)
	http.SetCookie(w, refreshCookie)

	app.writeJSON(w, http.StatusAccepted, tokens)
}

// refreshToken checks for a valid refresh cookie, and returns a JWT if it finds one.
func (app *application) refreshToken(w http.ResponseWriter, r *http.Request) {
	fmt.Println("Enter refresh Token 11")

	// Debug: Print received cookies
	fmt.Println("Received Cookies:")
	for _, cookie := range r.Cookies() {
		fmt.Printf("Name: %s, Value: %s\n", cookie.Name, cookie.Value)
	}

	for _, cookie := range r.Cookies() {
		fmt.Println("Enter Loop")
		fmt.Printf("cookie.Name: %+v\n", cookie.Name)
		fmt.Printf("app.auth.CookieName: %+v\n", app.auth.CookieName)
		if cookie.Name == app.auth.CookieName {
			fmt.Println("Cookie Test")
			claims := &Claims{}
			refreshToken := cookie.Value

			// parse the token to get the claims
			_, err := jwt.ParseWithClaims(refreshToken, claims, func(token *jwt.Token) (interface{}, error) {
				fmt.Println("parse the token")
				return []byte(app.JWTSecret), nil
			})
			if err != nil {
				app.errorJSON(w, errors.New("unauthorized"), http.StatusUnauthorized)
				fmt.Println("parse the token to get the claims Fail")
				return
			}
			fmt.Println("parse the token to get the claims Success")

			// get the user id from the token claims
			userID, err := strconv.Atoi(claims.Subject)
			fmt.Println("userID: ", userID)
			if err != nil {
				app.errorJSON(w, errors.New("unknown user"), http.StatusUnauthorized)
				return
			}

			//user, err := app.DB.GetUserByID(userID)
			user := models.User{
				ID:        123,
				FirstName: "Max",
				LastName:  "John",
				Email:     "abc@xyz.com",
				Password:  "12345",
			}
			if err != nil {
				app.errorJSON(w, errors.New("unknown user"), http.StatusUnauthorized)
				return
			}

			u := jwtUser{
				ID:        user.ID,
				FirstName: user.FirstName,
				LastName:  user.LastName,
			}

			tokenPairs, err := app.auth.GenerateTokenPair(&u)
			if err != nil {
				app.errorJSON(w, errors.New("error generating tokens"), http.StatusUnauthorized)
				return
			}

			http.SetCookie(w, app.auth.GetRefreshCookie(tokenPairs.RefreshToken))
			fmt.Printf("New Tokens: %+v", tokenPairs)
			app.writeJSON(w, http.StatusOK, tokenPairs)

		}
	}
	fmt.Println("Enter refresh Token ** Exit")
}

// logout logs the user out by sending an expired cookie to delete the refresh cookie.
func (app *application) logout(w http.ResponseWriter, r *http.Request) {
	http.SetCookie(w, app.auth.GetExpiredRefreshCookie())
	w.WriteHeader(http.StatusAccepted)
}

func (app *application) testRoutes(w http.ResponseWriter, r *http.Request) {
	fmt.Println("Enter testRoutes Handler")
	var payload = struct {
		Status  string `json:"status"`
		Message string `json:"message"`
		Version string `json:"version"`
	}{
		Status:  "active",
		Message: "Test Working",
		Version: "1.0.0",
	}

	_ = app.writeJSON(w, http.StatusOK, payload)
}
