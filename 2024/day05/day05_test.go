package day05

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 143 {
    fmt.Println(result)
  	t.Fail()
  }
}
