package day07

import (
	"aoc2024/helper"
	"math"
	"strconv"
	"strings"
// 	"log"
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
        } else {
            result *= v
        }
    }
    return result
}

func powInt(x, y int) int {
    return int(math.Pow(float64(x), float64(y)))
}

func (e equation)permutations() int {
    ops := make([]int, len(e.input))
    count := 0
//     log.Println("len ", len(e.input), " / ", powInt(2, len(e.input)-1))
    for i := 0; i < powInt(2, len(e.input)-1); i++ {
      // the first op is always add
//       log.Println("i = ", i)
      ops[0] = 0
      intermediate := i
      for d := 1; d < len(e.input); d++ {
//           log.Println("Int: " , intermediate)
          ops[d] = intermediate & 1
          intermediate /= 2
      }
//       log.Println(ops)
      c := e.calculate(ops)
      if c == e.answer {
//           log.Println("Yay")
          count++
//       } else {
//           log.Println("Boo!")
      }
    }
    return count
}

func Part1(inputFilename string) (result int) {
  output := parseInput(inputFilename)

//   log.Println(output)
  for _,eq := range output {
//       log.Println("**")
      r := eq.permutations()
      if r > 0 {
         result += eq.answer
      }
  }

  return
}
