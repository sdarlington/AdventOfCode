package day07

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 3749 {
    fmt.Println(result)
  	t.Fail()
  }
}
