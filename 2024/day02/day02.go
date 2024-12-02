package day02

import (
    "aoc2024/helper"
	"strconv"
	"strings"
)

func parseInput(filename string)(reports [][]int)  {
  reports = helper.ParseInput(filename, func (each_line string) (reports []int) {
    report_line := strings.Fields(each_line)
    for _,v := range report_line {
      val, err := strconv.Atoi(v)
      if err == nil {
        reports = append(reports, val)
      }
    }
    return
  })

  return
}

func testRow(row []int, skip int) (result bool) {
  result = true
  inc := false
  for i := 0; i < len(row) - 1; i++ {
    var diff int
    if skip == -1 {    
      diff = row[i + 1] - row[i]
    } else {
      var i1, i2 int
      if i == skip {
        i1 = i + 1
        i2 = i + 2
      } else {
        i1 = i
        if i + 1 == skip {
          i2 = i + 2
        } else {
          i2 = i + 1
        }
      }
      if i2 == len(row) {
        continue
      }
      diff = row[i2] - row[i1]
    }
    if i == 0 {
      if diff > 0 {
        inc = true
      }
    }
    if (diff < 0 && inc) || (diff > 0 && !inc) {
        result = false
        return
    }
    if diff == 0 || diff > 3 || diff < -3 {
      result = false
      return
    }
  }
  
  return
}

func Part1(inputFilename string) (result int) {
  reports := parseInput(inputFilename)

  result = 0
  for _,v := range reports {
    if testRow(v, -1) {
      result++
    }
  }

  return
}

func Part2(inputFilename string) (result int) {
  reports := parseInput(inputFilename)

  result = 0
  for _,v := range reports {
    for d := -1; d < len(v); d++ {
      if testRow(v, d) {
        result++
        break
      }
    }
  }

  return
}
