package day07

import (
	"aoc2024/helper"
	"math"
	"strconv"
	"strings"
)

type equation struct {
  answer int
  input []int
}

func parseInput(filename string) (output []equation) {
	output = helper.ParseInput(filename, func(each_line string) (reports equation) {
	    qa := strings.Split(each_line, ":")
	    reports.answer,_ = strconv.Atoi(qa[0])
	    
	    input := strings.Fields(qa[1])
	    reports.input = make([]int, len(input))
	    for i,v := range input {
	      reports.input[i],_ = strconv.Atoi(v)
	    }

		return
	})

	return
}

func (e equation)calculate(op []int) int {
    result := 0
    for i,v := range e.input {
        if op[i] == 0 {
            result += v
        } else if op[i] == 1 {
            result *= v
        } else {
            result = v + result * powInt(10, int(1 + math.Log10(float64(v))))
        }
    }
    return result
}

func powInt(x, y int) int {
    return int(math.Pow(float64(x), float64(y)))
}

func (e equation)permutations(permutations int) int {
    ops := make([]int, len(e.input))
    count := 0
    for i := 0; i < powInt(permutations, len(e.input)-1); i++ {
      // the first op is always add
      ops[0] = 0
      intermediate := i
      for d := 1; d < len(e.input); d++ {
          ops[d] = intermediate % permutations
          intermediate /= permutations
      }
      c := e.calculate(ops)
      if c == e.answer {
          count++
          break
      }
    }
    return count
}

func Part1(inputFilename string) (result int) {
  output := parseInput(inputFilename)

  for _,eq := range output {
      r := eq.permutations(2)
      if r > 0 {
         result += eq.answer
      }
  }

  return
}

func Part2(inputFilename string) (result int) {
  output := parseInput(inputFilename)

  for _,eq := range output {
      r := eq.permutations(3)
      if r > 0 {
         result += eq.answer
      }
  }

  return
}
