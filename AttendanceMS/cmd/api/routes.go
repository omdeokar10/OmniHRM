package main

import (
	"net/http"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

func (app *ApplicationAttendance) routes() http.Handler {
	// create a router mux
	mux := chi.NewRouter()

	mux.Use(middleware.Recoverer)
	mux.Use(app.enableCORS)
	//mux.Get("/", app.Home)
	mux.Get("/microservice/userAttendance", app.Home)
	mux.Post("/microservice/userAttendance/signin", app.SignIn)
	mux.Post("/microservice/userAttendance/signout", app.SignOut)

	return mux
}
