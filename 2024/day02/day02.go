package day02

import (
	"bufio"
// 	"fmt"
	"log"
	"os"
// 	"sort"
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

func testRow(row []int) (result bool) {
  result = true
  inc := false
  for i := 1; i < len(row); i++ {
    diff := row[i] - row[i-1]
    if i == 1 {
      inc = diff > 0
    } else {
      if (diff < 0 && inc) || (diff > 0 && !inc) {
        result = false
        return
      }
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
    if testRow(v) {
      result++
    }
  }

  return
}
