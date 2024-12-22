package day22

import (
	"fmt"
    "testing"
    )

var sample = "sample.txt"

func TestMix(t *testing.T) {
	result := mix(42, 15)
	if result != 37 {
		fmt.Println(result)
		t.Fail()
	}
}

func TestPrune(t *testing.T) {
	result := prune(100000000)
	if result != 16113920 {
		fmt.Println(result)
		t.Fail()
	}
}

func TestCalculate(t *testing.T) {
	result1 := calculate(123,1)
	if result1 != 15887950 {
		fmt.Println(result1)
		t.Fail()
	}
}

func TestPart1(t *testing.T) {
  result := Part1(sample)
  if result != 37327623 {
    fmt.Println(result)
  	t.Fail()
  }
}
