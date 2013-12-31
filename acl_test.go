package main

import (
	"net/http"
	"testing"
)

func TestInit(t *testing.T) {
	_, err := http.Get("http://localhost:7474/db/data/acl/init")
	if err != nil {
		t.Fatal(err)
	}
}
