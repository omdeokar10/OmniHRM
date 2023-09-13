package main

var Testserver string
var MicroserviceDBurl string
var TestDBURL string

func init() {
	Testserver = "http://localhost:8081"
	MicroserviceDBurl = "/microservice/db/UserAttendance"
	TestDBURL = Testserver + MicroserviceDBurl
}
