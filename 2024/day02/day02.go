package day02

import (
	"bufio"
// 	"fmt"
	"log"
	"os"
// 	"slices"
	"strconv"
	"strings"
)

func parseInput(filename string)(reports [][]int)  {
  file, err := os.Open(filename)
  if err != nil {
  	log.Fatal(err)
  }
  defer file.Close()
  
  scanner := bufio.NewScanner(file)
 
  for scanner.Scan() {
    each_line := scanner.Text()
    
    report_line := strings.Fields(each_line)
    var int_report []int
    for _,v := range report_line {
      val, err := strconv.Atoi(v)
      if err == nil {
        int_report = append(int_report, val)
      }
    }
    reports = append(reports, int_report)
  }

  return
}

func testRow(row []int, skip int) (result bool, i int) {
  result = true
  inc := false
  for i = 0; i < len(row) - 1; i++ {
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
          i2 = i+ 2
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
    r,_ := testRow(v, -1)
    if r {
      result++
    }
  }

  return
}

func remove(slice []int, i int) []int {
  copy(slice[i:], slice[i+1:])
  return slice[:len(slice)-1]
}

func Part2(inputFilename string) (result int) {
  reports := parseInput(inputFilename)

  result = 0
  for _,v := range reports {
    r,_ := testRow(v, -1)
    if r {
      result++
    } else {
      for d := 0; d < len(v); d++ {
        r,_ = testRow(v, d)
        if r {
          result++
          break
        }
      }
    }
  }

  return
}
