package day06

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 41 {
    fmt.Println(result)
  	t.Fail()
  }
}
